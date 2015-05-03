package pack;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bonsanto on 1/19/2015.
 */

/**
 *
 * LINK --- http://alvinalexander.com/java/jdbc-connection-string-mysql-postgresql-sqlserver
 * JDBC connection string examples
 * Here’s a table showing the syntax for JDBC URLs and drivers that I've used on recent projects.
 * (If you'd like to see more detailed JDBC URL and Driver examples for each database, see the sections below.)
 * Database 	URL (JDBC Connection String)
 * JDBC Driver
 * MySQL 	jdbc:mysql://HOST/DATABASE
 * com.mysql.jdbc.Driver
 *
 * Postgresql 	jdbc:postgresql://HOST/DATABASE
 * org.postgresql.Driver
 *
 * SQL Server
 * jdbc:microsoft:sqlserver://HOST:1433;DatabaseName=DATABASE
 * com.microsoft.jdbc.sqlserver.SQLServerDriver
 * (see the Comments section below for more information and changes)
 *
 *  DB2 	jdbc:as400://HOST/DATABASE;
 *  com.ibm.as400.access.AS400JDBCDriver
 */

public class SettingsReader {
	private ArrayList<DBConnection> dBConnections = new ArrayList<DBConnection>();
	private Document dom;

	public void readSettings(String path) throws ParserConfigurationException, org.xml.sax.SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		dom = db.parse(new File(path));
		dom.getDocumentElement().normalize();
	}

	public void parseSettings() {

		//Probably this will be changed and used the log4js instead, or maybe throws an exception
		if (dom == null) System.out.println("The document hasn't been instantiated");
		else {
			Element doc = dom.getDocumentElement();
			NodeList nl = doc.getElementsByTagName("db");

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Element el = (Element) nl.item(i);
					try {
						DBConnection db = getDBConnection(el);
						db.setAllPoolProperties();
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
		DBConnection connection = new DBConnection();

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
				//TODO: URL shouldn't be provided?
				url = getTextValue(element, "url"),
				userName = getTextValue(element, "userName"),
				validationQuery = getTextValue(element, "validationQuery"),
				validatorClassName = getTextValue(element, "validatorClassName");

		//Set the properties using5 the XML read data.
		//Booleans
		connection.setAccessToUnderlyingConnectionAllowed(accessToUnderlyingConnectionAllowed);
		connection.setAlternateUsernameAllowed(alternateUsernameAllowed);
		connection.setCommitOnReturn(commitOnReturn);
		connection.setDefaultAutoCommit(defaultAutoCommit);
		connection.setDefaultReadOnly(defaultReadOnly);
		connection.setFairQueue(fairQueue);
		connection.setIgnoreExceptionOnPreLoad(ignoreExceptionOnPreLoad);
		connection.setJmxEnabled(jmxEnabled);
		connection.setLogAbandoned(logAbandoned);
		connection.setLogValidationErrors(logValidationErrors);
		connection.setPropagateInterruptState(propagateInterruptState);
		connection.setRemoveAbandoned(removeAbandoned);
		connection.setRollbackOnReturn(rollbackOnReturn);
		connection.setTestOnBorrow(testOnBorrow);
		connection.setTestOnConnect(testOnConnect);
		connection.setTestWhileIdle(testWhileIdle);
		connection.setTestOnReturn(testOnReturn);
		connection.setUseDisposableConnectionFacade(useDisposableConnectionFacade);
		connection.setUseEquals(useEquals);
		connection.setUseLock(useLock);

		//Integers.
		connection.setAbandonWhenPercentageFull(abandonWhenPercentageFull);
		connection.setDefaultTranslationIsolation(defaultTranslationIsolation);
		connection.setInitialSize(initialSize);
		connection.setMaxActive(maxActive);
		connection.setMaxAge(maxAge);
		connection.setMaxIdle(maxIdle);
		connection.setMaxWait(maxWait);
		connection.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		connection.setMinIdle(minIdle);
		connection.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		connection.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		connection.setSuspectTimeout(suspectTimeout);
		connection.setTimeBetweenEvictionsRunMillis(timeBetweenEvictionsRunMillis);
		connection.setValidationInterval(validationInterval);
		connection.setValidationQueryTimeout(validationQueryTimeout);

		//Strings.
		connection.setConnectionProperties(connectionProperties);
		connection.setDataSourceJNDI(dataSourceJNDI);
		connection.setDefaultCatalog(defaultCatalog);
		connection.setDriverClassName(driverClassName);
		connection.setInitSQL(initSQL);
		connection.setJdbcInterceptors(jdbcInterceptors);
		connection.setName(name);
		connection.setPassword(password);
		connection.setUrl(url);
		connection.setUserName(userName);
		connection.setValidationQuery(validationQuery);
		connection.setValidatorClassName(validatorClassName);

		//todo: commented because the validation should be done in the DBConnection class.
//		if (userName == null) throw new Exception("User Name not defined");
//		if (password == null) throw new Exception("Password not defined");

//		url = "jdbc:" + sgbd + "://" + ip + ":" + port + "/" + dbName + "?" + "user=" + userName + "&password=" + password;
		return connection;
//		TODO probably is better to use setters and getters
//		return new DBConnection(jmxEnabled, testWhileIdle, testOnBorrow, testOnReturn, logAbandoned, removeAbandoned, validationInterval, timeBetweenEvictionsRunMillis, maxActive, initialSize, maxWait, removeAbandonedTimeout, minEvictableIdleTimeMillis, minIdle, url, userName, password, validationQuery, jdbcInterceptors, "DRIVER CLASS NAME", "datasource", null);
	}

	//Provides the string value inside a tag
	private String getTextValue(Element element, String tag) {
		String val = null;
		NodeList nl = element.getElementsByTagName(tag);

		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			val = el.getFirstChild().getNodeValue();
		}
		return val;
	}

	//Provides the Integer value of a determined tag
	private Integer getIntValue(Element element, String tag) {
		return getTextValue(element, tag) == null ? null : Integer.parseInt(getTextValue(element, tag));
	}

	//Provides the Boolean value of a determined tag
	private Boolean getBooleanValue(Element element, String tag) {
		return getTextValue(element, tag) == null ? null : Boolean.parseBoolean(getTextValue(element, tag));
	}
}
