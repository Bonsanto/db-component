package pool;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Bonsanto on 1/10/2015.
 */
public class DBConnection {

	public static void main(String... args) throws Exception {
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:postgresql://localhost:5432/pokemon");
		p.setDriverClassName("org.postgresql.Driver");
		p.setUsername("postgres");
		p.setPassword("masterkey");
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors(
				"org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
						"org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);

		Connection con = datasource.getConnection();
		try {
			runQuery(con);
		} finally {
			if (con != null) try {
				con.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static void runQuery(Connection connection) throws Exception {
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM pokemon ORDER BY id_pokemon ASC ");
		while (rs.next()) {
			System.out.println("id_pokemon = " + rs.getInt("id_pokemon") + ", na_pokemon = " + rs.getString("na_pokemon"));
		}
		rs.close();
		st.close();
	}
}
