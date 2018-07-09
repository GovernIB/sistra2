package es.caib.sistramit.core.service.test;

import javax.naming.NamingException;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

public class JndiBean {

    public static void doSetup() {

        // public JndiBean() {
        try {

            final DriverAdapterCPDS cpds = new DriverAdapterCPDS();

            cpds.setDriver("org.h2.Driver");
            cpds.setUrl(
                    "jdbc:h2:mem:test;INIT=CREATE SCHEMA IF NOT EXISTS test;DB_CLOSE_DELAY=-1");
            cpds.setUser("");
            cpds.setPassword("");

            final SharedPoolDataSource dataSource = new SharedPoolDataSource();
            dataSource.setConnectionPoolDataSource(cpds);
            dataSource.setMaxActive(10);
            dataSource.setMaxWait(50);

            final SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            builder.bind("java:/es.caib.sistramit.db", dataSource);
            builder.activate();

        } catch (NamingException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}