package pack;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bonsanto on 1/19/2015.
 */
public class SettingsReader {
	private ArrayList<DBConnection> dBConnections = new ArrayList<DBConnection>();
	private Document dom;
	private Element doc;

	public void readSettings(String path) throws ParserConfigurationException, org.xml.sax.SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		dom = db.parse(path);
	}

	public void parseSettings() {
		if (dom == null) System.out.println("The document hasn't been instantiated");
		else {
			doc = dom.getDocumentElement();
			NodeList nl = doc.getElementsByTagName("db");

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Element el = (Element) nl.item(i);
					try {
						DBConnection db = getDBConnection(el);
						dBConnections.add(db);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public ArrayList<DBConnection> getDBConnections() {
		return dBConnections;
	}

	private DBConnection getDBConnection(Element element) throws Exception {
		boolean jmxEnabled = getBooleanValue(element, "jmxEnabled") != null ?
				getBooleanValue(element, "jmxEnabled") :
				DBConnection.DEFAULT_JMXENABLED,
				testWhileIdle = getBooleanValue(element, "testWhileIdle") != null ?
						getBooleanValue(element, "testWhileIdle") :
						DBConnection.DEFAULT_TESTWHILEIDLE,
				testOnBorrow = getBooleanValue(element, "testOnBorrow") != null ?
						getBooleanValue(element, "testOnBorrow") :
						DBConnection.DEFAULT_TESTONBORROW,
				testOnReturn = getBooleanValue(element, "testOnReturn") != null ?
						getBooleanValue(element, "testOnReturn") :
						DBConnection.DEFAULT_TESTONRETURN,
				logAbandoned = getBooleanValue(element, "logAbandoned") != null ?
						getBooleanValue(element, "logAbandoned") :
						DBConnection.DEFAULT_LOGABANDONED,
				removeAbandoned = getBooleanValue(element, "removeAbandoned") != null ?
						getBooleanValue(element, "removeAbandoned") :
						DBConnection.DEFAULT_REMOVEABANDONED;
		int validationInterval = getIntValue(element, "validationInterval") != null ?
				getIntValue(element, "validationInterval") :
				DBConnection.DEFAULT_VALIDATIONINTERVAL,
				timeBetweenEvictionsRunMillis = getIntValue(element, "timeBetweenEvictionsRunMillis") != null ?
						getIntValue(element, "timeBetweenEvictionsRunMillis") :
						DBConnection.DEFAULT_TIMEBETWEENEVICTIONRUNSMILLIS,
				maxActive = getIntValue(element, "maxActive") != null ?
						getIntValue(element, "maxActive") :
						DBConnection.DEFAULT_MAXACTIVE,
				initialSize = getIntValue(element, "initialSize") != null ?
						getIntValue(element, "initialSize") :
						DBConnection.DEFAULT_INITIALSIZE,
				maxWait = getIntValue(element, "maxWait") != null ?
						getIntValue(element, "maxWait") :
						DBConnection.DEFAULT_MAXWAIT,
				removeAbandonedTimeout = getIntValue(element, "removeAbandonedTimeout") != null ?
						getIntValue(element, "removeAbandonedTimeout") :
						DBConnection.DEFAULT_REMOVEABANDONEDTIMEOUT,
				minEvictableIdleTimeMillis = getIntValue(element, "minEvictableIdleTimeMillis") != null ?
						getIntValue(element, "minEvictableIdleTimeMillis") :
						DBConnection.DEFAULT_MINEVICTABLEIDLETIMEMILLIS,
				minIdle = getIntValue(element, "minIdle") != null ?
						getIntValue(element, "minIdle") :
						DBConnection.DEFAULT_MINIDLE;
		String validationQuery = getTextValue(element, "validationQuery") != null ?
				getTextValue(element, "validationQuery") :
				DBConnection.DEFAULT_VALIDATIONQUERY,
				jdbcInterceptors = getTextValue(element, "jdbcInterceptors") != null ?
						getTextValue(element, "jdbcInterceptors") :
						DBConnection.DEFAULT_JDBCINTERCEPTORS,
				sgbdDriver = getTextValue(element, "sgbd"),
				userName = getTextValue(element, "userName"),
				password = getTextValue(element, "password"),
				port = getTextValue(element, "port"),
				ip = getTextValue(element, "ip"),
				sgbd = getTextValue(element, "sgbd"),
				dbName = getTextValue(element, "dbName"),
				url;

		if (sgbdDriver.equals("postgresql")) sgbdDriver = "org.postgresql.Driver";
		else if (sgbdDriver.equals("mysql")) sgbdDriver = "com.mysql.jdbc.Driver";
		else if (sgbdDriver.equals("SQL Server")) sgbdDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		else throw new Exception("Data Base not supported or defined");

		if (userName == null) throw new Exception("User Name not defined");
		if (password == null) throw new Exception("Password not defined");
		if (port == null) throw new Exception("Port not defined");
		if (ip == null) throw new Exception("IP not defined");
		if (dbName == null) throw new Exception("DB not defined");

		url = "jdbc:" + sgbd + "://" + ip + ":" + port + "/" + dbName + "?" + "user=" + userName + "&password=" + password;
		return new DBConnection();
//		TODO
//		return new DBConnection(jmxEnabled, testWhileIdle, testOnBorrow, testOnReturn, logAbandoned, removeAbandoned, validationInterval, timeBetweenEvictionsRunMillis, maxActive, initialSize, maxWait, removeAbandonedTimeout, minEvictableIdleTimeMillis, minIdle, url, userName, password, validationQuery, jdbcInterceptors, "DRIVER CLASS NAME", "datasource", null);
	}

	private String getTextValue(Element element, String tag) {
		String val = null;
		NodeList nl = element.getElementsByTagName(tag);

		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			val = el.getFirstChild().getNodeValue();
		}
		return val;
	}

	private Integer getIntValue(Element element, String tag) {
		return getTextValue(element, tag) == null ? null : Integer.parseInt(getTextValue(element, tag));
	}

	private Boolean getBooleanValue(Element element, String tag) {
		return getTextValue(element, tag) == null ? null : Boolean.parseBoolean(getTextValue(element, tag));
	}
}
