package server;

import org.xml.sax.SAXException;
import pack.DBConnection;
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


	public static void main(String[] argv) {
		try {
			reader = new SettingsReader();
			reader.readSettings("E:\\Documents\\GitHub\\db-component\\config\\settings.xml");
			reader.parseSettings();
			dBConnections = reader.getDBConnections();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(dBConnections.size());
		try {

			Connection connection = dBConnections.get(0).getDataSourceProvider().getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM pokemon ORDER BY id_pokemon ASC ");
			while (rs.next()) {
				System.out.println("id_pokemon = " + rs.getInt("id_pokemon") + ", na_pokemon = " + rs.getString("na_pokemon"));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Object implementor = new Dispatcher();
		String address = "http://localhost:9000/Dispatcher";
		System.out.println(address);
		Endpoint.publish(address, implementor);
	}
}
