package pack;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Bonsanto on 5/4/2015.
 */
public class QueriesReader {
	private HashMap<String, DBConnection> connections = new HashMap<>();

	public HashMap<String, DBConnection> readQueries(String path, HashMap<String, DBConnection> connections) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(path));
		doc.getDocumentElement().normalize();

		//todo: probably a log here..
		if (Objects.equals(path, "")) throw new Exception("The file's path wasn't provided");

		//todo: probably a log here
		if (connections.size() == 0) throw new Exception("There aren't DBs available, verify the settings.xml");

		this.connections = connections;
		NodeList queries = doc.getElementsByTagName("query");

		for (int i = 0; i < queries.getLength(); i++) {
			try {
				readQuery((Element) queries.item(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.connections;
	}

	private void readQuery(Element element) throws Exception {
		//Used to get the id of the query.
		String id = element.getAttribute("id");

		//Used to get the id of the db for those queries.
		String db = element.getParentNode().getAttributes().getNamedItem("id").getNodeValue();

		//Used to get the content of the query.
		String sentence = element.getTextContent();

		//Add the query to the Database.
		this.connections.get(db).queries.put(id, new Query(sentence));
	}
}
