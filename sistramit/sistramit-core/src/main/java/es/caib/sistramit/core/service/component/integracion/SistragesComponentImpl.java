package es.caib.sistramit.core.service.component.integracion;

import java.util.Properties;

import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;

/**
 * Implementación acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("sistragesComponent")
public final class SistragesComponentImpl implements SistragesComponent {

    // TODO PENDIENTE IMPLEMENTAR

    @Override
    public Properties recuperarPropiedadesGlobales() {
        // TODO PENDIENTE RECUPERAR SISTRAGES
        final Properties props = new Properties();
        props.put(TypePropiedadConfiguracion.URL_SISTRAMIT.toString(),
                "http://localhost:8080/sistramitfront");
        props.put(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS.toString(),
                "es,ca,en");
        props.put(TypePropiedadConfiguracion.REVISION_INVALIDACIONES.toString(),
                "1");

        return props;
    }

}
