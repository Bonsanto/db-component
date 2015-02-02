package pack;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.Validator;
import java.util.Properties;

public class DBConnection {
// todo probably will be deleted.
//	//Default values.
//	public static final Boolean DEFAULT_JMXENABLED = true,
//			DEFAULT_TESTWHILEIDLE = true,
//			DEFAULT_TESTONBORROW = true,
//			DEFAULT_TESTONRETURN = false,
//			DEFAULT_LOGABANDONED = true,
//			DEFAULT_REMOVEABANDONED = true;
//	public static final Integer DEFAULT_VALIDATIONINTERVAL = 30000,
//			DEFAULT_TIMEBETWEENEVICTIONRUNSMILLIS = 30000,
//			DEFAULT_MAXACTIVE = 100,
//			DEFAULT_INITIALSIZE = 10,
//			DEFAULT_MAXWAIT = 10000,
//			DEFAULT_REMOVEABANDONEDTIMEOUT = 60,
//			DEFAULT_MINEVICTABLEIDLETIMEMILLIS = 30000,
//			DEFAULT_MINIDLE = 10;
//	public static final String DEFAULT_VALIDATIONQUERY = "SELECT 1",
//			DEFAULT_JDBCINTERCEPTORS = "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
//					"org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer";

	//Real attributes.
	private Boolean accessToUnderlyingConnectionAllowed,
			alternateUsernameAllowed,
			commitOnReturn,
			defaultAutoCommit,
			defaultReadOnly,
			fairQueue,
			ignoreExceptionOnPreLoad,
			jmxEnabled,
			logAbandoned,
			logValidationErrors,
			propagateInterruptState,
			removeAbandoned,
			rollbackOnReturn,
			testOnBorrow,
			testOnConnect,
			testWhileIdle,
			testOnReturn,
			useDisposableConnectionFacade,
			useEquals,
			useLock;

	private Integer abandonWhenPercentageFull,
			defaultTranslationIsolation,
			initialSize,
			maxActive,
			maxAge,
			maxIdle,
			maxWait,
			minEvictableIdleTimeMillis,
			minIdle,
			numTestsPerEvictionRun,
			removeAbandonedTimeout,
			suspectTimeout,
			timeBetweenEvictionsRunMillis,
			validationInterval,
			validationQueryTimeout;

	private String connectionProperties,
			dataSourceJNDI,
			defaultCatalog,
			driverClassName,
			initSQL,
			jdbcInterceptors,
			name,
			password,
			url,
			userName,
			validationQuery,
			validatorClassName;

	private DataSource dataSource,
			dataSourceProvider;
	private PoolProperties poolProperties;
	private Properties dbProperties;
	private Validator validator;

	public DBConnection() {
	}

	public DBConnection(Boolean accessToUnderlyingConnectionAllowed, Boolean alternateUsernameAllowed, Boolean commitOnReturn,
	                    Boolean defaultAutoCommit, Boolean defaultReadOnly, Boolean fairQueue, Boolean ignoreExceptionOnPreLoad,
	                    Boolean jmxEnabled, Boolean logAbandoned, Boolean logValidationErrors, Boolean propagateInterruptState,
	                    Boolean removeAbandoned, Boolean rollbackOnReturn, Boolean testOnBorrow, Boolean testOnConnect,
	                    Boolean testWhileIdle, Boolean testOnReturn, Boolean useDisposableConnectionFacade, Boolean useEquals,
	                    Boolean useLock, Integer abandonWhenPercentageFull, Integer defaultTranslationIsolation, Integer initialSize,
	                    Integer maxActive, Integer maxAge, Integer maxIdle, Integer maxWait, Integer minEvictableIdleTimeMillis,
	                    Integer minIdle, Integer numTestsPerEvictionRun, Integer removeAbandonedTimeout, Integer suspectTimeout,
	                    Integer timeBetweenEvictionsRunMillis, Integer validationInterval, Integer validationQueryTimeout,
	                    String connectionProperties, String dataSourceJNDI, String defaultCatalog, String driverClassName,
	                    String initSQL, String jdbcInterceptors, String name, String password, String url, String userName,
	                    String validationQuery, String validatorClassName, DataSource dataSource, PoolProperties poolProperties,
	                    Properties dbProperties, Validator validator) {
		this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
		this.alternateUsernameAllowed = alternateUsernameAllowed;
		this.commitOnReturn = commitOnReturn;
		this.defaultAutoCommit = defaultAutoCommit;
		this.defaultReadOnly = defaultReadOnly;
		this.fairQueue = fairQueue;
		this.ignoreExceptionOnPreLoad = ignoreExceptionOnPreLoad;
		this.jmxEnabled = jmxEnabled;
		this.logAbandoned = logAbandoned;
		this.logValidationErrors = logValidationErrors;
		this.propagateInterruptState = propagateInterruptState;
		this.removeAbandoned = removeAbandoned;
		this.rollbackOnReturn = rollbackOnReturn;
		this.testOnBorrow = testOnBorrow;
		this.testOnConnect = testOnConnect;
		this.testWhileIdle = testWhileIdle;
		this.testOnReturn = testOnReturn;
		this.useDisposableConnectionFacade = useDisposableConnectionFacade;
		this.useEquals = useEquals;
		this.useLock = useLock;
		this.abandonWhenPercentageFull = abandonWhenPercentageFull;
		this.defaultTranslationIsolation = defaultTranslationIsolation;
		this.initialSize = initialSize;
		this.maxActive = maxActive;
		this.maxAge = maxAge;
		this.maxIdle = maxIdle;
		this.maxWait = maxWait;
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		this.minIdle = minIdle;
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
		this.removeAbandonedTimeout = removeAbandonedTimeout;
		this.suspectTimeout = suspectTimeout;
		this.timeBetweenEvictionsRunMillis = timeBetweenEvictionsRunMillis;
		this.validationInterval = validationInterval;
		this.validationQueryTimeout = validationQueryTimeout;
		this.connectionProperties = connectionProperties;
		this.dataSourceJNDI = dataSourceJNDI;
		this.defaultCatalog = defaultCatalog;
		this.driverClassName = driverClassName;
		this.initSQL = initSQL;
		this.jdbcInterceptors = jdbcInterceptors;
		this.name = name;
		this.password = password;
		this.url = url;
		this.userName = userName;
		this.validationQuery = validationQuery;
		this.validatorClassName = validatorClassName;
		this.dataSource = dataSource;
		this.poolProperties = poolProperties;
		this.dbProperties = dbProperties;
		this.validator = validator;
	}

	public void setAllPoolProperties() {
		poolProperties = new PoolProperties();

		//Mandatory items.
		poolProperties.setUrl(url);
		poolProperties.setDriverClassName(driverClassName);
		poolProperties.setUsername(userName);
		poolProperties.setPassword(password);

		//Not Mandatory Items.
		if (abandonWhenPercentageFull != null) poolProperties.setAbandonWhenPercentageFull(abandonWhenPercentageFull);
		if (accessToUnderlyingConnectionAllowed != null)
			poolProperties.setAccessToUnderlyingConnectionAllowed(accessToUnderlyingConnectionAllowed);
		if (alternateUsernameAllowed != null) poolProperties.setAlternateUsernameAllowed(alternateUsernameAllowed);
		if (commitOnReturn != null) poolProperties.setCommitOnReturn(commitOnReturn);
		if (connectionProperties != null) poolProperties.setConnectionProperties(connectionProperties);
		if (dataSource != null) poolProperties.setDataSource(dataSource); //todo: probably a problem.
		if (dataSourceJNDI != null) poolProperties.setDataSourceJNDI(dataSourceJNDI);
		if (dbProperties != null) poolProperties.setDbProperties(dbProperties);
		if (defaultAutoCommit != null) poolProperties.setDefaultAutoCommit(defaultAutoCommit);
		if (defaultCatalog != null) poolProperties.setDefaultCatalog(defaultCatalog);
		if (defaultReadOnly != null) poolProperties.setDefaultReadOnly(defaultReadOnly);
		if (defaultTranslationIsolation != null)
			poolProperties.setDefaultTransactionIsolation(defaultTranslationIsolation);
		if (fairQueue != null) poolProperties.setFairQueue(fairQueue);
		if (ignoreExceptionOnPreLoad != null) poolProperties.setIgnoreExceptionOnPreLoad(ignoreExceptionOnPreLoad);
		if (initialSize != null) poolProperties.setInitialSize(initialSize);
		if (initSQL != null) poolProperties.setInitSQL(initSQL);
		if (jdbcInterceptors != null) poolProperties.setJdbcInterceptors(jdbcInterceptors);
		if (jmxEnabled != null) poolProperties.setJmxEnabled(jmxEnabled);
		if (logAbandoned != null) poolProperties.setLogAbandoned(logAbandoned);
		if (logValidationErrors != null) poolProperties.setLogValidationErrors(logValidationErrors);
		if (maxActive != null) poolProperties.setMaxActive(maxActive);
		if (maxAge != null) poolProperties.setMaxAge(maxAge);
		if (maxIdle != null) poolProperties.setMaxIdle(maxIdle);
		if (maxWait != null) poolProperties.setMaxWait(maxWait);
		if (minEvictableIdleTimeMillis != null)
			poolProperties.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		if (minIdle != null) poolProperties.setMinIdle(minIdle);
		if (name != null) poolProperties.setName(name);
		if (numTestsPerEvictionRun != null) poolProperties.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		if (propagateInterruptState != null) poolProperties.setPropagateInterruptState(propagateInterruptState);
		if (removeAbandoned != null) poolProperties.setRemoveAbandoned(removeAbandoned);
		if (removeAbandonedTimeout != null) poolProperties.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		if (rollbackOnReturn != null) poolProperties.setRollbackOnReturn(rollbackOnReturn);
		if (suspectTimeout != null) poolProperties.setSuspectTimeout(suspectTimeout);
		if (testOnBorrow != null) poolProperties.setTestOnBorrow(testOnBorrow);
		if (testOnConnect != null) poolProperties.setTestOnConnect(testOnConnect);
		if (testOnReturn != null) poolProperties.setTestOnReturn(testOnReturn);
		if (testWhileIdle != null) poolProperties.setTestWhileIdle(testWhileIdle);
		if (timeBetweenEvictionsRunMillis != null)
			poolProperties.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionsRunMillis);
		if (useDisposableConnectionFacade != null)
			poolProperties.setUseDisposableConnectionFacade(useDisposableConnectionFacade);
		if (useEquals != null) poolProperties.setUseEquals(useEquals);
		if (useLock != null) poolProperties.setUseLock(useLock);
		if (validationInterval != null) poolProperties.setValidationInterval(validationInterval);
		if (validationQuery != null) poolProperties.setValidationQuery(validationQuery);
		if (validationQueryTimeout != null) poolProperties.setValidationQueryTimeout(validationQueryTimeout);
		if (validator != null) poolProperties.setValidator(validator);
		if (validatorClassName != null) poolProperties.setValidatorClassName(validatorClassName);

		//Set the DataSource Provider's Properties.
		dataSourceProvider = new DataSource();
		dataSourceProvider.setPoolProperties(poolProperties);
	}

	//probably must be synchronized
	public DataSource getDataSource() {
		return dataSourceProvider;
	}
}

