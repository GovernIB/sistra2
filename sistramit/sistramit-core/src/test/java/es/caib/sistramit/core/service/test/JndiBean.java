package es.caib.sistramit.core.service.test;

import javax.naming.NamingException;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import java.sql.Connection;

public class JndiBean {

    public static void doSetup() {

        // public JndiBean() {
        try {

            final DriverAdapterCPDS cpds = new DriverAdapterCPDS();

            cpds.setDriver("org.h2.Driver");
            //cpds.setUrl("jdbc:h2:mem:test;INIT=CREATE SCHEMA IF NOT EXISTS test;DB_CLOSE_DELAY=-1;MODE=Oracle");
            cpds.setUrl("jdbc:h2:mem:test;INIT=CREATE SCHEMA IF NOT EXISTS test;DB_CLOSE_DELAY=-1;MODE=Oracle;LOCK_MODE=3;MULTI_THREADED=TRUE");


            cpds.setUser("");
            cpds.setPassword("");
            cpds.setAccessToUnderlyingConnectionAllowed(true);

            final SharedPoolDataSource dataSource = new SharedPoolDataSource();
            dataSource.setConnectionPoolDataSource(cpds);
            dataSource.setMaxActive(10);
            dataSource.setMaxWait(50);

            final SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            builder.bind("java:/es.caib.sistramit.db", dataSource);
            builder.activate();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error en JndiBean", ex);
        }
    }
}