package es.caib.sistramit.core.service.test;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.api.exception.DominioNoExisteException;
import es.caib.sistramit.core.service.component.integracion.DominiosComponent;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
import es.caib.sistramit.core.service.test.mock.SistragesMock;

/**
 * Testing dominios.
 *
 * @author Indra
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DominiosTest extends BaseDbUnit {

    /** Acceso STG (simulado). */
    @Autowired
    private SistragesComponent sistragesComponent;

    /** Dominios component. **/
    @Autowired
    private DominiosComponent dominiosComponent;

    /** Publicación JNDI Datasource y creación de BBDD en memoria. */
    @BeforeClass
    public static void setUpClass() throws Exception {
        JndiBean.doSetup();
    }

    /**
     * El test es para ver que si no existe el dominio, provoca una excepcion de
     * tipo DominioException.
     */
    @Test
    public void testDominioNoExiste() {
        // Definicion tramite
        final RVersionTramite defTramite = sistragesComponent
                .recuperarDefinicionTramite(SistragesMock.ID_TRAMITE,
                        SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA);
        final DefinicionTramiteSTG defTramiteSTG = new DefinicionTramiteSTG(
                new java.util.Date(), defTramite);
        try {
            dominiosComponent.recuperarDominio("DOM_INEXISTENTE", null,
                    defTramiteSTG);
            fail();
        } catch (final DominioNoExisteException e) {
            // Debe devolver esta excepcion
        }
    }

    /**
     * El test es para ver que es capaz de cachear un mismo dominio con
     * distintos ParametrosDominio y que cuando volvemos a pedir uno de ellos,
     * ese Sí está ya cacheado.
     */
    @Test
    public void testDominioRecuperacionCache() {
        final RVersionTramite defTramite = sistragesComponent
                .recuperarDefinicionTramite(SistragesMock.ID_TRAMITE,
                        SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA);

        final DefinicionTramiteSTG defTramiteSTG = new DefinicionTramiteSTG(
                new java.util.Date(), defTramite);
        final ParametrosDominio parametrosDominio1 = new ParametrosDominio();
        parametrosDominio1.addParametro("COD1", "PARAM1");
        final ParametrosDominio parametrosDominio2 = new ParametrosDominio();
        parametrosDominio2.addParametro("COD2", "PARAM2");

        final String identificadorDominio = defTramite.getDominios().get(0)
                .getIdentificador();

        final ValoresDominio valores1 = dominiosComponent.recuperarDominio(
                identificadorDominio, parametrosDominio1, defTramiteSTG);
        Assert.isTrue(!valores1.isFromCache(),
                "El dominio con parametrosDominio1 tenia que NO estar cacheado");
        final ValoresDominio valores2 = dominiosComponent.recuperarDominio(
                identificadorDominio, parametrosDominio2, defTramiteSTG);
        Assert.isTrue(!valores2.isFromCache(),
                "El dominio con parametrosDominio2 tenia que NO estar cacheado");
        final ValoresDominio valores3 = dominiosComponent.recuperarDominio(
                identificadorDominio, parametrosDominio1, defTramiteSTG);
        Assert.isTrue(valores3.isFromCache(),
                "El dominio con parametrosDominio1 tenia que SI estar cacheado");
    }

    @Test
    public void testDominioInvalidacionCache() {
        final RVersionTramite defTramite = sistragesComponent
                .recuperarDefinicionTramite(SistragesMock.ID_TRAMITE,
                        SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA);
        final DefinicionTramiteSTG defTramiteSTG = new DefinicionTramiteSTG(
                new java.util.Date(), defTramite);
        final ParametrosDominio parametrosDominio1 = new ParametrosDominio();
        parametrosDominio1.addParametro("COD1", "PARAM1");

        final String identificadorDominio = defTramite.getDominios().get(0)
                .getIdentificador();

        final ValoresDominio valores1 = dominiosComponent.recuperarDominio(
                identificadorDominio, parametrosDominio1, defTramiteSTG);
        Assert.isTrue(!valores1.isFromCache(),
                "El dominio tenia que NO estar cacheado");
        final ValoresDominio valores2 = dominiosComponent.recuperarDominio(
                identificadorDominio, parametrosDominio1, defTramiteSTG);
        Assert.isTrue(valores2.isFromCache(),
                "El dominio tenia que SÍ estar cacheado");
        dominiosComponent.invalidarDominio(identificadorDominio);
        final ValoresDominio valores3 = dominiosComponent.recuperarDominio(
                identificadorDominio, parametrosDominio1, defTramiteSTG);
        Assert.isTrue(!valores3.isFromCache(),
                "El dominio tenia que NO estar cacheado");
    }

}
