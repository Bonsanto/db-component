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


		Boolean accessToUnderlyingConnectionAllowed = getBooleanValue(element, "accessToUnderlyingConnectionAllowed"),
				alternateUsernameAllowed = getBooleanValue(element, "alternateUsernameAllowed"),
				commitOnReturn = getBooleanValue(element, "commitOnReturn"),
				defaultAutoCommit = getBooleanValue(element, "defaultAutoCommit"),
				defaultReadOnly = getBooleanValue(element, "defaultReadOnly"),
				fairQueue = getBooleanValue(element, "fairQueue"),
				ignoreExceptionOnPreLoad = getBooleanValue(element, "ignoreExceptionOnPreLoad"),
				jmxEnabled = getBooleanValue(element, "jmxEnabled"),
				logAbandoned = getBooleanValue(element, "logAbandoned"),
				logValidationErrors = getBooleanValue(element, "logValidationErrors"),
				propagateInterruptState = getBooleanValue(element, "propagateInterruptState"),
				removeAbandoned = getBooleanValue(element, "removeAbandoned"),
				rollbackOnReturn = getBooleanValue(element, "rollbackOnReturn"),
				testOnBorrow = getBooleanValue(element, "testOnBorrow"),
				testOnConnect = getBooleanValue(element, "testOnConnect"),
				testWhileIdle = getBooleanValue(element, "testWhileIdle"),
				testOnReturn = getBooleanValue(element, "testOnReturn"),
				useDisposableConnectionFacade = getBooleanValue(element, "useDisposableConnectionFacade"),
				useEquals = getBooleanValue(element, "useEquals"),
				useLock = getBooleanValue(element, "useLock");

		Integer abandonWhenPercentageFull = getIntValue(element, "abandonWhenPercentageFull"),
				defaultTranslationIsolation = getIntValue(element, "defaultTranslationIsolation"),
				initialSize = getIntValue(element, "initialSize"),
				maxActive = getIntValue(element, "maxActive"),
				maxAge = getIntValue(element, "maxAge"),
				maxIdle = getIntValue(element, "maxIdle"),
				maxWait = getIntValue(element, "maxWait"),
				minEvictableIdleTimeMillis = getIntValue(element, "minEvictableIdleTimeMillis"),
				minIdle = getIntValue(element, "minIdle"),
				numTestsPerEvictionRun = getIntValue(element, "numTestsPerEvictionRun"),
				removeAbandonedTimeout = getIntValue(element, "removeAbandonedTimeout"),
				suspectTimeout = getIntValue(element, "suspectTimeout"),
				timeBetweenEvictionsRunMillis = getIntValue(element, "timeBetweenEvictionsRunMillis"),
				validationInterval = getIntValue(element, "validationInterval"),
				validationQueryTimeout = getIntValue(element, "validationQueryTimeout");

		String connectionProperties = getTextValue(element, "connectionProperties"),
				dataSourceJNDI = getTextValue(element, "dataSourceJNDI"),
				defaultCatalog = getTextValue(element, "defaultCatalog"),
				driverClassName = getTextValue(element, "driverClassName"),
				initSQL = getTextValue(element, "initSQL"),
				jdbcInterceptors = getTextValue(element, "jdbcInterceptors"),
				name = getTextValue(element, "name"),
				password = getTextValue(element, "password"),
				url = getTextValue(element, "url"),
				userName = getTextValue(element, "userName"),
				validationQuery = getTextValue(element, "validationQuery"),
				validatorClassName = getTextValue(element, "validatorClassName");

		if (userName == null) throw new Exception("User Name not defined");
		if (password == null) throw new Exception("Password not defined");

//		url = "jdbc:" + sgbd + "://" + ip + ":" + port + "/" + dbName + "?" + "user=" + userName + "&password=" + password;
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
