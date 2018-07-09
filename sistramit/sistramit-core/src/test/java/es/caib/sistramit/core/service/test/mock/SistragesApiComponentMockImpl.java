package es.caib.sistramit.core.service.test.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.util.XTestJson;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.integracion.SistragesApiComponent;

/**
 * Simula acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("sistragesApiComponentMock")
@Primary
public final class SistragesApiComponentMockImpl
        implements SistragesApiComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public RConfiguracionGlobal obtenerConfiguracionGlobal() {
        return recuperarConfiguracionGlobalFromSTG();
    }

    @Override
    public RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad) {
        final RConfiguracionEntidad c = XTestJson.crearEntidad();
        return c;
    }

    @Override
    public RVersionTramite recuperarDefinicionTramite(String idTramite,
            int version, String idioma) {
        final RVersionTramite definicionVersion = XTestJson
                .crearVersionTramite();
        return definicionVersion;
    }

    @Override
    public RAvisosEntidad obtenerAvisosEntidad(String idEntidad) {
        final RAvisosEntidad avisos = XTestJson.crearAvisos();
        return avisos;
    }

    // ------------- FUNCIONES PRIVADAS ---------------------------------

    /**
     * Invoca al STG para recuperar configuración global.
     *
     * @return propiedades
     */
    private RConfiguracionGlobal recuperarConfiguracionGlobalFromSTG() {

        final RConfiguracionGlobal configuracionGlobal = new RConfiguracionGlobal();

        configuracionGlobal.setTimestamp(generateTimestamp());

        final List<RValorParametro> parametros = new ArrayList<>();
        RValorParametro vp;
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.URL_SISTRAMIT.toString());
        vp.setValor("http://localhost:8080/sistramitfront");
        parametros.add(vp);
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS.toString());
        vp.setValor("es,ca,en");
        parametros.add(vp);
        vp = new RValorParametro();
        vp.setCodigo(TypePropiedadConfiguracion.TIMEOUT_CACHE_FLUJO.toString());
        vp.setValor("3600");
        parametros.add(vp);

        final RListaParametros propiedades = new RListaParametros();
        propiedades.setParametros(parametros);
        configuracionGlobal.setPropiedades(propiedades);

        return configuracionGlobal;

    }

    private static String generateTimestamp() {
        final Random rand = new Random();
        return System.currentTimeMillis() + "-" + rand.nextInt();
    }
}
