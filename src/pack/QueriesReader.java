package pack;


import com.sun.xml.ws.server.provider.ProviderInvokerTube;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Bonsanto on 5/4/2015.
 */
public class QueriesReader {
	private ArrayList<Query> queries = new ArrayList<>();

	public void readQueries(String path) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(path));
		doc.getDocumentElement().normalize();

		//todo: probably a log here..
		if (Objects.equals(path, "")) throw new Exception("The file's path wasn't provided");

		else {
			NodeList queries = doc.getElementsByTagName("query");

			for (int i = 0; i < queries.getLength(); i++) {
				try {
					this.queries.add(readQuery((Element) queries.item(i)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Query readQuery(Element element) throws Exception {
		Query query = new Query();

		//Used to get the id "id" attribute of the parentNode of the tag, in this case to know for what DB it will be used.
		query.setDB(element.getParentNode().getAttributes().getNamedItem("id").getNodeValue());

		//Used to get the id of the query.
		query.setId(element.getAttribute("id"));

		//Used to get the content of the query.
		query.setSentence(element.getTextContent());

		return query;
	}

	public ArrayList<Query> getQueries() {
		return queries;
	}
}
