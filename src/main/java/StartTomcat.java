import org.apache.catalina.deploy.ContextResource;

import ch.ralscha.embeddedtc.EmbeddedTomcat;

public class StartTomcat {
	public static void main(String[] args) throws Exception {
		System.setProperty("spring.profiles.active", "development");
		
		ContextResource res = new ContextResource();
		res.setName("jdbc/ds");
		res.setType("javax.sql.DataSource");
		res.setAuth("Container");
		res.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
		res.setProperty("driverClassName", "org.h2.Driver");
		res.setProperty("username", "sa");
		res.setProperty("password", "");

		res.setProperty("url", "jdbc:h2:./target/e4ds");
		res.setProperty("maxActive", "5");

		res.setProperty("initialSize", "2");
		res.setProperty("minIdle", "2");
		res.setProperty("maxIdle", "4");
		res.setProperty("maxWait", "10000");

		res.setProperty("defaultAutoCommit", "false");
		
		EmbeddedTomcat.create().addContextResource(res).startAndWait();
	}

}