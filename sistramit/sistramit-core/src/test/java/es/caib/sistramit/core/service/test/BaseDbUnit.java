package es.caib.sistramit.core.service.test;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Clase base para preparación de test. De ella extienden todas las clases de
 * test.
 *
 * @author Indra
 */
@ContextConfiguration(locations = {"classpath:sistramit-ac-core-test.xml",
        "classpath:sistramit-ac-core.xml"})
public class BaseDbUnit extends AbstractTransactionalJUnit4SpringContextTests {

    /**
     * Propiedades equivalentes a las establecidas en standalone-full.xml.
     */
    static {
        System.setProperty("es.caib.sistramit.properties.path",
                "/conf/sistramit/sistramit-test.properties");
    }

    /** DataSource. */
    @Autowired
    private DataSource dataSource;

    /** entity manager factory. */
    @Resource
    private EntityManagerFactory entityManagerFactory;

    /**
     * Obtiene entity manager factory.
     *
     * @return the entityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * Establece entity manager factory.
     *
     * @param entityManagerFactory
     *            the entityManagerFactory to set
     */
    public void setEntityManagerFactory(
            EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
