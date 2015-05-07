package pack;


import com.sun.org.apache.xerces.internal.dom.DeferredTextImpl;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class QueriesReader {
	private HashMap<String, DBConnection> connections = new HashMap<>();

	public HashMap<String, DBConnection> readQueries(String path, HashMap<String, DBConnection> connections) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(path));
		doc.getDocumentElement().normalize();
		this.connections = connections;

		//todo: probably a log here..
		if (Objects.equals(path, "")) throw new Exception("The file's path wasn't provided");

		//todo: probably a log here
		if (connections.size() == 0) throw new Exception("There aren't DBs available, verify the settings.xml");

		NodeList dbs = doc.getElementsByTagName("db");

		for (int i = 0; i < dbs.getLength(); i++) {
			for (int j = 0; j < dbs.item(i).getChildNodes().getLength(); j++) {
				Node node = dbs.item(i).getChildNodes().item(j).getFirstChild();
				if (node != null) {
					readSimpleQuery(node);
				}
			}
		}

		NodeList transactions = doc.getElementsByTagName("transaction");

		for (int i = 0; i < transactions.getLength(); i++) {
			Node node = transactions.item(i);
			if (node != null) {
				readTransactions((Element) node);
			}
		}
		return this.connections;
	}

	private void readTransactions(Element element) {

		//Used to get the id of the Data base Connection.
		String idDBConnection = element.getParentNode().getAttributes().getNamedItem("id").getNodeValue();

		//Used to get the id of the Transaction.
		String idTransaction = element.getAttribute("id");

		//Sentences of the queries.
		Transaction transaction = new Transaction();

		for (int i = 0; i < element.getChildNodes().getLength(); i++) {
			Node node = element.getChildNodes().item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String idQuery = node.getAttributes().item(0).getNodeValue(),
						sentence = node.getChildNodes().item(0).getNodeValue();
				transaction.queries.put(idQuery, new Query(sentence));
			}
		}
		this.connections.get(idDBConnection).transactions.put(idTransaction, transaction);
	}

	private void readSimpleQuery(Node node) {
		Node parent = node.getParentNode();

		//Used to get the id of the query.
		String id = parent.getAttributes().getNamedItem("id").getNodeValue();

		//Used to get the id of the db for those queries.
		String db = parent.getParentNode().getAttributes().getNamedItem("id").getNodeValue();

		//Sentences for the queries.
		String sentence = ((DeferredTextImpl) node).getData();

		this.connections.get(db).queries.put(id, new Query(sentence));
	}
}
