package pack;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DBConnection {

	//Default values.
	public static final Boolean DEFAULT_JMXENABLED = true,
			DEFAULT_TESTWHILEIDLE = true,
			DEFAULT_TESTONBORROW = true,
			DEFAULT_TESTONRETURN = false,
			DEFAULT_LOGABANDONED = true,
			DEFAULT_REMOVEABANDONED = true;
	public static final Integer DEFAULT_VALIDATIONINTERVAL = 30000,
			DEFAULT_TIMEBETWEENEVICTIONRUNSMILLIS = 30000,
			DEFAULT_MAXACTIVE = 100,
			DEFAULT_INITIALSIZE = 10,
			DEFAULT_MAXWAIT = 10000,
			DEFAULT_REMOVEABANDONEDTIMEOUT = 60,
			DEFAULT_MINEVICTABLEIDLETIMEMILLIS = 30000,
			DEFAULT_MINIDLE = 10;
	public static final String DEFAULT_VALIDATIONQUERY = "SELECT 1",
			DEFAULT_JDBCINTERCEPTORS = "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
					"org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer";

	//Real attributes.
	private Boolean jmxEnabled,
			testWhileIdle,
			testOnBorrow,
			testOnReturn,
			logAbandoned,
			removeAbandoned;
	private Integer validationInterval,
			timeBetweenEvictionsRunMillis,
			maxActive,
			initialSize,
			maxWait,
			removeAbandonedTimeout,
			minEvictableIdleTimeMillis,
			minIdle;
	private String url,
			userName,
			password,
			validationQuery,
			jdbcInterceptors,
			driverClassName;

	private DataSource dataSource;
	private PoolProperties poolProperties;


	public DBConnection() {
	}

	public DBConnection(boolean jmxEnabled, boolean testWhileIdle, boolean testOnBorrow,
	                    boolean testOnReturn, boolean logAbandoned, boolean removeAbandoned,
	                    int validationInterval, int timeBetweenEvictionsRunMillis, int maxActive,
	                    int initialSize, int maxWait, int removeAbandonedTimeout,
	                    int minEvictableIdleTimeMillis, int minIdle, String url,
	                    String userName, String password, String validationQuery,
	                    String jdbcInterceptors, String driverClassName, DataSource dataSource,
	                    PoolProperties poolProperties) {
		this.jmxEnabled = jmxEnabled;
		this.testWhileIdle = testWhileIdle;
		this.testOnBorrow = testOnBorrow;
		this.testOnReturn = testOnReturn;
		this.logAbandoned = logAbandoned;
		this.removeAbandoned = removeAbandoned;
		this.validationInterval = validationInterval;
		this.timeBetweenEvictionsRunMillis = timeBetweenEvictionsRunMillis;
		this.maxActive = maxActive;
		this.initialSize = initialSize;
		this.maxWait = maxWait;
		this.removeAbandonedTimeout = removeAbandonedTimeout;
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		this.minIdle = minIdle;
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.validationQuery = validationQuery;
		this.jdbcInterceptors = jdbcInterceptors;
		this.driverClassName = driverClassName;
		this.dataSource = dataSource;
		this.poolProperties = poolProperties;
	}

	public void setPoolProperties() {
		//WRONG
//		poolProperties.setUrl(); //todo
		poolProperties.setDriverClassName("org.postgresql.Driver"); //TODO: ADD THE OTHER LIBRARIES.
		poolProperties.setUsername(userName); //TODO: MANDATORY..
		poolProperties.setPassword(password); //TODO: MANDATORY..
		if (jmxEnabled != null) poolProperties.setJmxEnabled(jmxEnabled);
		if (testWhileIdle != null) poolProperties.setTestWhileIdle(testWhileIdle);
		if (testOnBorrow != null) poolProperties.setTestOnBorrow(testOnBorrow);
		if (validationQuery != null) poolProperties.setValidationQuery(validationQuery);
		if (testOnReturn != null) poolProperties.setTestOnReturn(testOnReturn);
		if (validationInterval != null) poolProperties.setValidationInterval(validationInterval);
		if (timeBetweenEvictionsRunMillis != null)
			poolProperties.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionsRunMillis);
		if (maxActive != null) poolProperties.setMaxActive(maxActive);
		if (initialSize != null) poolProperties.setInitialSize(initialSize);
		if (maxWait != null) poolProperties.setMaxWait(maxWait);
		if (removeAbandonedTimeout != null) poolProperties.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		if (minEvictableIdleTimeMillis != null) poolProperties.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		if (minIdle != null) poolProperties.setMinIdle(minIdle);
		if (logAbandoned != null) poolProperties.setLogAbandoned(logAbandoned);
		if (removeAbandoned != null) poolProperties.setRemoveAbandoned(removeAbandoned);
		if (jdbcInterceptors != null) poolProperties.setJdbcInterceptors(jdbcInterceptors);

	}
}

