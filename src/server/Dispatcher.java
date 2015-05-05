package server;

import dependencies.JSONBuilder;
import org.xml.sax.SAXException;
import pack.DBConnection;
import pack.QueriesReader;
import pack.Query;
import pack.SettingsReader;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Bonsanto on 4/30/2015.
 */
@WebService()
public class Dispatcher {
	private static SettingsReader reader;
	private static ArrayList<DBConnection> dBConnections;
//  @WebMethod
//  public String sayHelloWorldFrom(String from) {
//    String result = "Hello, world, from " + from;
//    System.out.println(result);
//    return result;
//  }


	@WebMethod
	public String sayHelloWorldFrom(String from) {
		String result = "Hello, world, from " + from;
		System.out.println(result);
		return result;
	}

	@WebMethod
	public String queryJSON(String parameters) {
		JSONBuilder jsonBuilder = new JSONBuilder();

		try {
			Connection connection = dBConnections.get(0).getDataSourceProvider().getConnection();
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM pokemon ORDER BY id_pokemon ASC");

			if (rs.next())
				jsonBuilder.addProperty(rs, "response");
			else
				jsonBuilder.addProperty("message", "Not Found");

			rs.close();
			st.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			jsonBuilder.addProperty("message", e.getErrorCode());
		}
		jsonBuilder.build();
		return jsonBuilder.JSON();
	}

	public static void main(String[] argv) {
		try {
			reader = new SettingsReader();
			reader.readSettings("E:\\Documents\\GitHub\\db-component\\config\\settings.xml");
			dBConnections = reader.getDBConnections();

			QueriesReader queriesReader = new QueriesReader();
			queriesReader.readQueries("E:\\Documents\\GitHub\\db-component\\config\\queries.xml");
			ArrayList<Query> queries = queriesReader.getQueries();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Connection connection = dBConnections.get(0).getDataSourceProvider().getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM pokemon ORDER BY id_pokemon ASC ");
			while (rs.next()) {
				System.out.println("id_pokemon = " + rs.getInt("id_pokemon") + ", na_pokemon = " + rs.getString("na_pokemon"));
			}
			rs.close();
			st.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Object implementor = new Dispatcher();
		String address = "http://localhost:9000/Dispatcher";
		System.out.println(address);
		Endpoint.publish(address, implementor);
	}
}
