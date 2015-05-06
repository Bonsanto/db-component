package server;

import dependencies.JSONBuilder;
import pack.DBConnection;
import pack.QueriesReader;
import pack.SettingsReader;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by Bonsanto on 4/30/2015.
 */
@WebService()
public class Dispatcher {
	private static HashMap<String, DBConnection> dBConnections;

	@WebMethod
	public String queryJSON(String idDB, String idQuery, Object... parameters) {
		JSONBuilder jsonBuilder = new JSONBuilder();

		try {
			DBConnection dbConnection = dBConnections.get(idDB);
			Connection connection = dbConnection.getDataSourceProvider().getConnection();
			String query = dbConnection.queries.get(idQuery).getSentence();
			PreparedStatement pst = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < parameters.length; i++)
				pst.setObject(i + 1, parameters[i]);

			ResultSet rs = pst.executeQuery();

			if (rs.next())
				jsonBuilder.addProperty(rs, "response");
			else
				jsonBuilder.addProperty("message", "Not Found");

			//Close everything
			rs.close();
			pst.close();
			connection.close();
			//todo: probably add a log here.
		} catch (Exception e) {
			e.printStackTrace();
			jsonBuilder.addProperty("message", e.getMessage());
		}
		jsonBuilder.build();
		return jsonBuilder.JSON();
	}

	public static void main(String[] argv) {
		try {
			SettingsReader reader = new SettingsReader();
			QueriesReader queriesReader = new QueriesReader();
			dBConnections = reader.readSettings("E:\\Documents\\GitHub\\db-component\\config\\settings.xml");
			dBConnections = queriesReader.readQueries("E:\\Documents\\GitHub\\db-component\\config\\queries.xml", dBConnections);

			Connection connection = dBConnections.get("0").getDataSourceProvider().getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM pokemon ORDER BY id_pokemon ASC");

			while (rs.next()) {
				System.out.println("id_pokemon = " + rs.getInt("id_pokemon") + ", na_pokemon = " + rs.getString("na_pokemon"));
			}
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
