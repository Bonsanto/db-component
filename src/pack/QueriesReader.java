package pack;


import com.sun.org.apache.xerces.internal.dom.DeferredTextImpl;
import dependencies.LogHandler;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class QueriesReader {
	private HashMap<String, DBConnection> conns = new HashMap<>();

	public HashMap<String, DBConnection> readQueries(String path, HashMap<String, DBConnection> conns, LogHandler log) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(path));
		doc.getDocumentElement().normalize();
		this.conns = conns;

		//todo: probably a log here..
		if (Objects.equals(path, "")) {
			log.write(Level.SEVERE, "The file's path for queries wasn't provided");
			throw new Exception("The file's path for queries wasn't provided");
		}

		//todo: probably a log here
		if (conns.size() == 0) {
			log.write(Level.SEVERE, "There aren't DBs available, verify the connections.xml");
			throw new Exception("There aren't DBs available, verify the connections.xml");
		}

		NodeList dbs = doc.getElementsByTagName("db");

		for (int i = 0; i < dbs.getLength(); i++) {
			for (int j = 0; j < dbs.item(i).getChildNodes().getLength(); j++) {
				Node node = dbs.item(i).getChildNodes().item(j).getFirstChild();
				if (node != null) readSimpleQuery(node);
			}
		}

		return this.conns;
	}

	private void readSimpleQuery(Node node) {
		Node parent = node.getParentNode();

		//Used to get the id of the query.
		String id = parent.getAttributes().getNamedItem("id").getNodeValue();

		//Used to get the id of the db for those queries.
		String db = parent.getParentNode().getAttributes().getNamedItem("id").getNodeValue();

		//Sentences for the queries.
		String sentence = ((DeferredTextImpl) node).getData();

		this.conns.get(db).queries.put(id, new Query(sentence));
	}
}
