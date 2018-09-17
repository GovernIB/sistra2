package es.caib.sistrages.core.api.service;

/**
 * Servicios de sistema.
 *
 * @author Indra
 *
 */
public interface SystemService {

    /**
     * Purgado de ficheros.
     *
     */
    public void purgarFicheros(String appId);

    /**
     * Obtiene propiedad configuración.
     *
     * @param propiedad
     *            propiedad
     * @return valor
     */
    public String obtenerPropiedadConfiguracion(String propiedad);

}
