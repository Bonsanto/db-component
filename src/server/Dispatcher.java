package server;

import dependencies.JSON;
import pack.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@WebService()
public class Dispatcher {
	private static HashMap<String, DBConnection> dBConnections;

	@WebMethod
	public String queryJSON(String idDB, String idQuery, Object... parameters) {
		JSON jsonBuilder = new JSON();

		try {
			DBConnection dbConnection = dBConnections.get(idDB);
			Connection connection = dbConnection.getDataSourceProvider().getConnection();
			String query = dbConnection.queries.get(idQuery).getSentence();
			PreparedStatement pst = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < parameters.length; i++)
				pst.setObject(i + 1, parameters[i]);

			ResultSet rs = pst.executeQuery();

			if (rs.next())
				jsonBuilder.addAttribute("response", rs);
			else
				jsonBuilder.addAttribute("message", "Not Found");

			//Close everything
			rs.close();
			pst.close();
			connection.close();
			//todo: probably add a log here.
		} catch (Exception e) {
			e.printStackTrace();
			jsonBuilder.addAttribute("message", e.getMessage());
		}
		jsonBuilder.build();
		return jsonBuilder.getJson();
	}

	@WebMethod
	public String makeTransaction(String idDB, String[] idQueries, Object... parameters) {
		JSON jsonBuilder = new JSON();

		try {
			DBConnection dbConnection = dBConnections.get(idDB);
			Connection connection = dbConnection.getDataSourceProvider().getConnection();

			//Finds the queries that will be executed for the transaction.
			ArrayList<Query> queries = new ArrayList<>();

			for (String queryID : idQueries) {
				queries.add(dbConnection.queries.get(queryID));
			}

			//In case the number of parameters doesn't match.
			if (countParameters(queries) != parameters.length)
				throw new Exception("The number of parameters passed doesn't match with the number of expected number of parameters");

			//Begin the transaction.
			connection.setAutoCommit(false);

			int parametersIteration = 0;
			for (Query query : queries) {
				PreparedStatement preparedStatement = connection.prepareStatement(query.getSentence());

				//Count the number of parameters for this specific query.
				ArrayList<Query> currentQuery = new ArrayList<>();
				currentQuery.add(query);

				int numberOfParameters = countParameters(currentQuery);

				for (int i = 0; i < numberOfParameters; i++) {
					preparedStatement.setObject(i, parameters[parametersIteration]);
					parametersIteration++;
				}
				preparedStatement.executeUpdate();
				//Probably this will be problematic.
				preparedStatement.close();
			}
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			jsonBuilder.addAttribute("message", e.getMessage());
		}
		jsonBuilder.build();
		return jsonBuilder.getJson();
	}

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

	public static void main(String[] argv) {
		try {
			SettingsReader reader = new SettingsReader();
			QueriesReader queriesReader = new QueriesReader();
			JSON json = new JSON();
			dBConnections = reader.readSettings("E:\\Documents\\GitHub\\db-component\\config\\settings.xml");
			dBConnections = queriesReader.readQueries("E:\\Documents\\GitHub\\db-component\\config\\queries.xml", dBConnections);

			Connection connection = dBConnections.get("1").getDataSourceProvider().getConnection();
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM product ORDER BY id_product ASC");

			//todo: Create a console module to request the string of the locaiton of the files, or maybe It will load the files that are in it self folder.s

			if (rs.next())
				json.addAttribute("response", rs);
			else
				json.addAttribute("message", "Not Found");

			json.build();
			System.out.println(json.getJson());
			rs.close();
			st.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object implementor = new Dispatcher();
		String address = "http://localhost:9000/Dispatcher";
		System.out.println(address);
		Endpoint.publish(address, implementor);
	}
}
