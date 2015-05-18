package server;

import dependencies.CSVWriter;
import dependencies.JSON;
import pack.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@WebService()
public class Dispatcher {
	private static HashMap<String, DBConnection> connections;

	//todo: add support for csv, WRITE IN DISK, SEND STATUS AND CONVERT BINARY TO ASCII 64.
	//todo: Add a log register for all the queries to the dispatcher, to leave a witness of all the queries.

	@WebMethod
	public String writeSimpleCSV(String idDB, String idQuery, String path, Object... params) {
		JSON jsonBuilder = new JSON();

		try {
			CSVWriter csv = new CSVWriter(path);
			DBConnection dbConn = connections.get(idDB);
			Connection conn = dbConn.getDataSourceProvider().getConnection();
			String query = dbConn.queries.get(idQuery).getSentence();
			PreparedStatement pst = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ArrayList<Query> queries = new ArrayList<>();
			queries.add(new Query(query));

			if (countParameters(queries) != params.length)
				throw new InvalidParameterException("The number of parameters passed doesn't match with the number of expected number of parameters");

			for (int i = 0; i < params.length; i++)
				pst.setObject(i + 1, params[i]);

			//In case it is a SELECT.
			if (query.regionMatches(true, 0, "select", 0, 6)) {
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					jsonBuilder.addAttribute("response", "success");
					csv.writeCSV(rs);
				} else
					jsonBuilder.addAttribute("message", "not found");

				rs.close();
			}
			//In case it is a INSERT, UPDATE, or DELETE.
			else {
				jsonBuilder.addAttribute("message", "query wouldn't retrieve data");
			}

			//Close everything
			close(pst, conn);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			jsonBuilder.addAttribute("message", e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			jsonBuilder.addAttribute("message", "query not found");
		}
		return jsonBuilder.getJson();
	}

	@WebMethod
	public String writeEntireCSV(String idDB, String idQuery, String path, Object... params) {
		JSON jsonBuilder = new JSON();

		try {
			CSVWriter csv = new CSVWriter(path);
			DBConnection dbConn = connections.get(idDB);
			Connection conn = dbConn.getDataSourceProvider().getConnection();
			String query = dbConn.queries.get(idQuery).getSentence();
			PreparedStatement pst = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ArrayList<Query> queries = new ArrayList<>();
			queries.add(new Query(query));

			if (countParameters(queries) != params.length)
				throw new InvalidParameterException("The number of parameters passed doesn't match with the number of expected number of parameters");

			for (int i = 0; i < params.length; i++)
				pst.setObject(i + 1, params[i]);

			//In case it is a SELECT.
			if (query.regionMatches(true, 0, "select", 0, 6)) {
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					jsonBuilder.addAttribute("response", "success");
					csv.writeCompoundCSV(rs);
				} else
					jsonBuilder.addAttribute("message", "not found");

				rs.close();
			}
			//In case it is a INSERT, UPDATE, or DELETE.
			else {
				jsonBuilder.addAttribute("message", "query wouldn't retrieve data");
			}

			//Close everything
			close(pst, conn);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			jsonBuilder.addAttribute("message", e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			jsonBuilder.addAttribute("message", "query not found");
		}
		return jsonBuilder.getJson();
	}

	@WebMethod
	public String queryJSON(String idDB, String idQuery, Object... params) {
		JSON jsonBuilder = new JSON();

		try {
			DBConnection dbConn = connections.get(idDB);
			Connection conn = dbConn.getDataSourceProvider().getConnection();
			String query = dbConn.queries.get(idQuery).getSentence();
			PreparedStatement pst = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < params.length; i++)
				pst.setObject(i + 1, params[i]);

			//In case it is a SELECT.
			if (query.regionMatches(true, 0, "select", 0, 6)) {
				ResultSet rs = pst.executeQuery();

				if (rs.next())
					jsonBuilder.addAttribute("response", rs);
				else
					jsonBuilder.addAttribute("message", "not found");

				rs.close();
			}
			//In case it is a INSERT, UPDATE, or DELETE.
			else {
				//In case the query was successfully executed.
				if (pst.execute())
					jsonBuilder.addAttribute("message", "success");
				else
					jsonBuilder.addAttribute("message", "query was unsuccessful");
			}

			//Close everything
			close(pst, conn);
			//todo: probably add a log here.
		} catch (Exception e) {
			e.printStackTrace();
			jsonBuilder.addAttribute("message", e.getMessage());
		}
		return jsonBuilder.getJson();
	}

	@WebMethod
	public String makeTransaction(String idDB, String[] idQueries, Object... parameters) {
		JSON jsonBuilder = new JSON();

		try {
			DBConnection dbConn = connections.get(idDB);
			Connection conn = dbConn.getDataSourceProvider().getConnection();
			PreparedStatement pst;

			//Finds the queries that will be executed for the transaction.
			ArrayList<Query> queries = new ArrayList<>();

			for (String idQuery : idQueries) {
				queries.add(dbConn.queries.get(idQuery));
			}

			//In case the number of parameters doesn't match.
			if (countParameters(queries) != parameters.length)
				throw new InvalidParameterException("The number of parameters passed doesn't match with the number of expected number of parameters");

			//Begin the transaction.
			conn.setAutoCommit(false);

			int parametersIteration = 0;

			for (Query query : queries) {
				pst = conn.prepareStatement(query.getSentence());

				//Count the number of parameters for this specific query.
				ArrayList<Query> currentQuery = new ArrayList<>();
				currentQuery.add(query);

				int numberOfParameters = countParameters(currentQuery);

				for (int i = 0; i < numberOfParameters; i++) {
					pst.setObject(i + 1, parameters[parametersIteration]);
					parametersIteration++;
				}
				pst.close();
			}
			jsonBuilder.addAttribute("message", "success");
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
		} catch (SQLException | InvalidParameterException e) {
			e.printStackTrace();
			jsonBuilder.addAttribute("message", e.getMessage());
		}
		return jsonBuilder.getJson();
	}

	//Counts the number of parameters in the array of transactions.
	private int countParameters(ArrayList<Query> queries) {
		int number = 0;

		//Counts the number of ? symbols in all the queries of this transaction.
		for (Query query : queries) {
			String sentence = query.getSentence();
			for (int i = 0; i < sentence.length(); i++) {
				if (sentence.charAt(i) == '?')
					number++;
			}
		}
		return number;
	}

	//Close prepared statement and connection
	private void close(PreparedStatement pst, Connection con) throws SQLException {
		pst.close();
		con.close();
	}

	public static void main(String[] argv) {
		try {
			String separator = File.separator;
			SettingsReader reader = new SettingsReader();
			QueriesReader queriesReader = new QueriesReader();
			JSON json = new JSON();
			File dir = new File("");
			System.out.println(dir.getAbsolutePath());
			connections = reader.readSettings(dir.getAbsolutePath() + (Objects.equals(separator, "\\") ? "\\config\\settings.xml" : "/config/settings.xml"));
			connections = queriesReader.readQueries(dir.getAbsolutePath() + (Objects.equals(separator, "\\") ? "\\config\\queries.xml" : "/config/queries.xml"), connections);

			//todo: remove this piece
//			Connection connection = connections.get("1").getDataSourceProvider().getConnection();
//			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			ResultSet rs = st.executeQuery("SELECT * FROM product ORDER BY id_product ASC");

			//todo: Create a console module to request the string of the location of the files, or maybe It will load the files that are in it self folder.
//			if (rs.next())
//				json.addAttribute("response", rs);
//			else
//				json.addAttribute("message", "not found");
//
//			rs.close();
//			st.close();
//			connection.close();
//			System.out.println(json.getJson());
			Dispatcher implementor = new Dispatcher();
			String address = "http://0.0.0.0:9000/Dispatcher";
			System.out.println(address);
			Endpoint.publish(address, implementor);
			//todo: add log for these errors
		} catch (BindException e) {
			e.printStackTrace();
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
