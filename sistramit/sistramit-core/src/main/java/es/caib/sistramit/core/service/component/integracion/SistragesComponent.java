package es.caib.sistramit.core.service.component.integracion;

import java.util.Properties;

/**
 * Acceso a Componente SISTRAGES.
 *
 * @author Indra
 *
 */
public interface SistragesComponent {

    /**
     * Recupera propiedades globales.
     * 
     * @return propiedades
     */
    Properties recuperarPropiedadesGlobales();

}
