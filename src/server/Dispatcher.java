package server;

import dependencies.*;
import pack.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

@WebService()
public class Dispatcher {
	private static HashMap<String, DBConnection> connections;
	private static LogHandler logs;

	//todo: Add a log register for all the queries to the dispatcher, to leave a witness of all the queries.
	@WebMethod
	public String writeSimpleCSV(String idDB, String idQuery, String path, Object... params) throws IOException {
		JSON jsonBuilder = new JSON();

		try {
			logs.write(Level.INFO, "Write Simple CSV method called");
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
					jsonBuilder.addAttribute("message", "Success");
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
		} catch (Exception e) {
			jsonBuilder.addAttribute("message", (Objects.equals(e.getClass().getName(), NullPointerException.class.getName())) ?
					"The requested query was not found" : e.getMessage());
			logs.write(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		return jsonBuilder.getJson();
	}

	@WebMethod
	public String writeEntireCSV(String idDB, String idQuery, String path, Object... params) throws IOException {
		JSON jsonBuilder = new JSON();

		try {
			logs.write(Level.INFO, "Write Entire CSV method called");
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
					jsonBuilder.addAttribute("message", "Success");
					csv.writeCompoundCSV(rs);
				} else
					jsonBuilder.addAttribute("message", "The query didn't retrieve data, because the ResultSet was empty");

				rs.close();
			}
			//In case it is a INSERT, UPDATE, or DELETE.
			else {
				jsonBuilder.addAttribute("message", "The query didn't retrieve data, because it didn't have a SELECT");
			}

			//Close everything
			close(pst, conn);
		} catch (Exception e) {
			jsonBuilder.addAttribute("message", (Objects.equals(e.getClass().getName(), NullPointerException.class.getName())) ?
					"The requested query was not found" : e.getMessage());
			e.printStackTrace();
			logs.write(Level.SEVERE, e.getMessage());
		}
		return jsonBuilder.getJson();
	}

	@WebMethod
	public String writeJSON(String idDB, String idQuery, String path, Object... params) throws IOException {
		JSON jsonBuilder = new JSON();

		try {
			logs.write(Level.INFO, "WriteJSON method was called");
			DBConnection dbConn = connections.get(idDB);
			JSONWriter jsonWriter = new JSONWriter(path);
			Connection conn = dbConn.getDataSourceProvider().getConnection();
			String query = dbConn.queries.get(idQuery).getSentence();
			PreparedStatement pst = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			//todo: add exception
			
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}

			//In case it is a SELECT.
			if (query.regionMatches(true, 0, "select", 0, 6)) {
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					jsonWriter.writeJSON(rs);
					jsonBuilder.addAttribute("message", "Success");
				} else
					jsonBuilder.addAttribute("message", "The query didn't retrieve data, because the ResultSet was empty");

				rs.close();
			}
			//In case it is a INSERT, UPDATE, or DELETE.
			else {
				//In case the query was Successfully executed.
				if (pst.execute())
					jsonBuilder.addAttribute("message", "Success");
				else
					jsonBuilder.addAttribute("message", "The query didn't retrieve data, because it didn't have a SELECT");
			}

			//Close everything
			close(pst, conn);
		} catch (Exception e) {
			e.printStackTrace();
			logs.write(Level.SEVERE, e.getMessage());
			jsonBuilder.addAttribute("message", e.getMessage());
		}
		return jsonBuilder.getJson();
	}

	@WebMethod
	public String queryJSON(String idDB, String idQuery, Object... params) throws IOException {
		JSON jsonBuilder = new JSON();

		try {
			logs.write(Level.INFO, "QueryJSON method was called");
			DBConnection dbConn = connections.get(idDB);
			Connection conn = dbConn.getDataSourceProvider().getConnection();
			String query = dbConn.queries.get(idQuery).getSentence();
			PreparedStatement pst = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ArrayList<Query> queries = new ArrayList<>();
			queries.add(dbConn.queries.get(idQuery));

			if (countParameters(queries) != params.length)
				throw new InvalidParameterException("The number of parameters passed doesn't match with the number of expected number of parameters");

			for (int i = 0; i < params.length; i++)
				pst.setObject(i + 1, params[i]);

			//In case it is a SELECT.
			if (query.regionMatches(true, 0, "select", 0, 6)) {
				ResultSet rs = pst.executeQuery();

				if (rs.next())
					jsonBuilder.addAttribute("response", rs);
				else
					jsonBuilder.addAttribute("message", "The query didn't retrieve data, because the ResultSet was empty");

				rs.close();
			}
			//In case it is a INSERT, UPDATE, or DELETE.
			else {
				//In case the query was Successfully executed.
				if (pst.execute())
					jsonBuilder.addAttribute("message", "Success");
				else
					jsonBuilder.addAttribute("message", "The query didn't retrieve data, because it didn't have a SELECT");
			}

			//Close everything
			close(pst, conn);
		} catch (Exception e) {
			e.printStackTrace();
			logs.write(Level.SEVERE, e.getMessage());
			jsonBuilder.addAttribute("message", e.getMessage());
		}
		return jsonBuilder.getJson();
	}

	@WebMethod
	public DataTable queryDataTable(String idDB, String idQuery, Object... params) throws IOException {
		DataTable dt = new DataTable();

		try {
			logs.write(Level.INFO, "Make Data Table query called");
			DBConnection dbConn = connections.get(idDB);
			Connection conn = dbConn.getDataSourceProvider().getConnection();

			Query query = dbConn.queries.get(idQuery);
			PreparedStatement pst = conn.prepareStatement(query.getSentence(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if (!compareParameterNumbers(dbConn, params, idQuery)) {
				ArrayList<Query> queries = new ArrayList<>();
				queries.add(query);

				throw new InvalidParameterException("The number of parameters passed doesn't match with the number of expected number of parameters. Expected: " + countParameters(queries) + " but received: " + params.length);
			}

			//In case it is a SELECT.
			if (query.getSentence().regionMatches(true, 0, "select", 0, 6)) {
				ResultSet rs = pst.executeQuery();

				dt.build(rs);
				rs.close();
			}
			//In case of INSERT, DELETE or UPDATE.
			else
				throw new Exception("INSERT, DELETE or UPDATES aren't supported by this method");

			//Close everything
			close(pst, conn);
		} catch (Exception e) {
			e.printStackTrace();
			logs.write(Level.SEVERE, e.getMessage());
		}
		return dt;
	}

	@WebMethod
	public String makeTransaction(String idDB, String[] idQueries, Object... params) throws IOException {
		JSON jsonBuilder = new JSON();

		try {
			logs.write(Level.INFO, "Make Transaction query called");
			DBConnection dbConn = connections.get(idDB);
			Connection conn = dbConn.getDataSourceProvider().getConnection();
			PreparedStatement pst;

			//In case the number of parameters doesn't match.
			if (!compareParameterNumbers(dbConn, params, idQueries)) {
				ArrayList<Query> queries = new ArrayList<>();
				for (String idQuery : idQueries)
					queries.add(dbConn.queries.get(idQuery));

				throw new InvalidParameterException("The number of parameters passed doesn't match with the number of expected number of parameters. Expected: " + countParameters(queries) + " but received: " + params.length);
			}

			//Begin the transaction.
			conn.setAutoCommit(false);
			int parametersIteration = 0;

			for (String query : idQueries) {
				Query q = dbConn.queries.get(query);
				pst = conn.prepareStatement(q.getSentence());

				//Count the number of parameters for this specific query.
				ArrayList<Query> currentQuery = new ArrayList<>();
				currentQuery.add(q);

				int numberOfParameters = countParameters(currentQuery);

				for (int i = 0; i < numberOfParameters; i++) {
					pst.setObject(i + 1, params[parametersIteration]);
					parametersIteration++;
				}
				pst.close();
			}
			jsonBuilder.addAttribute("message", "Success");
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			logs.write(Level.SEVERE, e.getMessage());
			jsonBuilder.addAttribute("message", e.getMessage());
		}
		return jsonBuilder.getJson();
	}

	//Compares the number of parameters and the number of expected parameters in queries.
	private boolean compareParameterNumbers(DBConnection dbc, Object[] params, String... idQueries) {
		ArrayList<Query> qs = new ArrayList<>();

		for (String query : idQueries)
			qs.add(dbc.queries.get(query));

		return countParameters(qs) == params.length;
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

	public static void main(String[] argv) throws IOException {
		try {
			ConnectionsReader connReader = new ConnectionsReader();
			QueriesReader queriesReader = new QueriesReader();
			SettingsReader settingsReader = new SettingsReader();
			HashMap<String, Object> config;
			logs = new LogHandler();

			config = settingsReader.readSettings(new File("").getAbsolutePath() +
					File.separator + "config" + File.separator + "settings.xml", logs);
			connections = connReader.readSettings(new File("").getAbsolutePath() +
					File.separator + "config" + File.separator + "connections.xml", logs);
			connections = queriesReader.readQueries(new File("").getAbsolutePath() +
					File.separator + "config" + File.separator + "queries.xml", connections, logs);

			Dispatcher implementor = new Dispatcher();
			String address = "http://" + config.get("ip") + ":" + config.get("port") + "/" + config.get("name");
			System.out.println("Server wsdl can be found on http://localhost:" + config.get("port") + "/" + config.get("name"));

			Endpoint.publish(address, implementor);
		} catch (Exception e) {
			e.printStackTrace();
			logs.write(Level.SEVERE, e.getMessage());
		}
	}
}
