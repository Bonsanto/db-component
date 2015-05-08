package pack;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.Validator;

import java.util.HashMap;
import java.util.Properties;

public class DBConnection {
	//Attribute that holds all the queries for this DB
	public HashMap<String, Query> queries = new HashMap<>();

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

	public Boolean getAccessToUnderlyingConnectionAllowed() {
		return accessToUnderlyingConnectionAllowed;
	}

	public void setAccessToUnderlyingConnectionAllowed(Boolean accessToUnderlyingConnectionAllowed) {
		this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
	}

	public Boolean getAlternateUsernameAllowed() {
		return alternateUsernameAllowed;
	}

	public void setAlternateUsernameAllowed(Boolean alternateUsernameAllowed) {
		this.alternateUsernameAllowed = alternateUsernameAllowed;
	}

	public Boolean getCommitOnReturn() {
		return commitOnReturn;
	}

	public void setCommitOnReturn(Boolean commitOnReturn) {
		this.commitOnReturn = commitOnReturn;
	}

	public Boolean getDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}

	public Boolean getDefaultReadOnly() {
		return defaultReadOnly;
	}

	public void setDefaultReadOnly(Boolean defaultReadOnly) {
		this.defaultReadOnly = defaultReadOnly;
	}

	public Boolean getFairQueue() {
		return fairQueue;
	}

	public void setFairQueue(Boolean fairQueue) {
		this.fairQueue = fairQueue;
	}

	public Boolean getIgnoreExceptionOnPreLoad() {
		return ignoreExceptionOnPreLoad;
	}

	public void setIgnoreExceptionOnPreLoad(Boolean ignoreExceptionOnPreLoad) {
		this.ignoreExceptionOnPreLoad = ignoreExceptionOnPreLoad;
	}

	public Boolean getJmxEnabled() {
		return jmxEnabled;
	}

	public void setJmxEnabled(Boolean jmxEnabled) {
		this.jmxEnabled = jmxEnabled;
	}

	public Boolean getLogAbandoned() {
		return logAbandoned;
	}

	public void setLogAbandoned(Boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}

	public Boolean getLogValidationErrors() {
		return logValidationErrors;
	}

	public void setLogValidationErrors(Boolean logValidationErrors) {
		this.logValidationErrors = logValidationErrors;
	}

	public Boolean getPropagateInterruptState() {
		return propagateInterruptState;
	}

	public void setPropagateInterruptState(Boolean propagateInterruptState) {
		this.propagateInterruptState = propagateInterruptState;
	}

	public Boolean getRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(Boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public Boolean getRollbackOnReturn() {
		return rollbackOnReturn;
	}

	public void setRollbackOnReturn(Boolean rollbackOnReturn) {
		this.rollbackOnReturn = rollbackOnReturn;
	}

	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Boolean getTestOnConnect() {
		return testOnConnect;
	}

	public void setTestOnConnect(Boolean testOnConnect) {
		this.testOnConnect = testOnConnect;
	}

	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public Boolean getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public Boolean getUseDisposableConnectionFacade() {
		return useDisposableConnectionFacade;
	}

	public void setUseDisposableConnectionFacade(Boolean useDisposableConnectionFacade) {
		this.useDisposableConnectionFacade = useDisposableConnectionFacade;
	}

	public Boolean getUseEquals() {
		return useEquals;
	}

	public void setUseEquals(Boolean useEquals) {
		this.useEquals = useEquals;
	}

	public Boolean getUseLock() {
		return useLock;
	}

	public void setUseLock(Boolean useLock) {
		this.useLock = useLock;
	}

	public Integer getAbandonWhenPercentageFull() {
		return abandonWhenPercentageFull;
	}

	public void setAbandonWhenPercentageFull(Integer abandonWhenPercentageFull) {
		this.abandonWhenPercentageFull = abandonWhenPercentageFull;
	}

	public Integer getDefaultTranslationIsolation() {
		return defaultTranslationIsolation;
	}

	public void setDefaultTranslationIsolation(Integer defaultTranslationIsolation) {
		this.defaultTranslationIsolation = defaultTranslationIsolation;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}

	public Integer getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public Integer getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(Integer removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public Integer getSuspectTimeout() {
		return suspectTimeout;
	}

	public void setSuspectTimeout(Integer suspectTimeout) {
		this.suspectTimeout = suspectTimeout;
	}

	public Integer getTimeBetweenEvictionsRunMillis() {
		return timeBetweenEvictionsRunMillis;
	}

	public void setTimeBetweenEvictionsRunMillis(Integer timeBetweenEvictionsRunMillis) {
		this.timeBetweenEvictionsRunMillis = timeBetweenEvictionsRunMillis;
	}

	public Integer getValidationInterval() {
		return validationInterval;
	}

	public void setValidationInterval(Integer validationInterval) {
		this.validationInterval = validationInterval;
	}

	public Integer getValidationQueryTimeout() {
		return validationQueryTimeout;
	}

	public void setValidationQueryTimeout(Integer validationQueryTimeout) {
		this.validationQueryTimeout = validationQueryTimeout;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public String getDataSourceJNDI() {
		return dataSourceJNDI;
	}

	public void setDataSourceJNDI(String dataSourceJNDI) {
		this.dataSourceJNDI = dataSourceJNDI;
	}

	public String getDefaultCatalog() {
		return defaultCatalog;
	}

	public void setDefaultCatalog(String defaultCatalog) {
		this.defaultCatalog = defaultCatalog;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getInitSQL() {
		return initSQL;
	}

	public void setInitSQL(String initSQL) {
		this.initSQL = initSQL;
	}

	public String getJdbcInterceptors() {
		return jdbcInterceptors;
	}

	public void setJdbcInterceptors(String jdbcInterceptors) {
		this.jdbcInterceptors = jdbcInterceptors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public String getValidatorClassName() {
		return validatorClassName;
	}

	public void setValidatorClassName(String validatorClassName) {
		this.validatorClassName = validatorClassName;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSourceProvider() {
		return dataSourceProvider;
	}

	public void setDataSourceProvider(DataSource dataSourceProvider) {
		this.dataSourceProvider = dataSourceProvider;
	}

	public PoolProperties getPoolProperties() {
		return poolProperties;
	}

	public void setPoolProperties(PoolProperties poolProperties) {
		this.poolProperties = poolProperties;
	}

	public Properties getDbProperties() {
		return dbProperties;
	}

	public void setDbProperties(Properties dbProperties) {
		this.dbProperties = dbProperties;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

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

	public void setAllPoolProperties() throws Exception {
		poolProperties = new PoolProperties();

		//todo: probably more ifs to provide more information of which wasn't defined.
		if (url == null || driverClassName == null || userName == null || password == null) {
			throw new Exception("A mandatory item wasn't defined correctly");
		} else {
			//Mandatory items.
			poolProperties.setUrl(url);
			poolProperties.setDriverClassName(driverClassName);
			poolProperties.setUsername(userName);
			poolProperties.setPassword(password);

			//Not Mandatory Items.
			if (abandonWhenPercentageFull != null)
				poolProperties.setAbandonWhenPercentageFull(abandonWhenPercentageFull);
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
	}

	//probably must be synchronized
	public DataSource getDataSource() {
		return dataSourceProvider;
	}
}

