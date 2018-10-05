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
    public void purgarFicheros();

    /**
     * Obtiene propiedad configuración.
     *
     * @param propiedad
     *            propiedad
     * @return valor
     */
    public String obtenerPropiedadConfiguracion(String propiedad);

    /**
     * Verifica si es maestro
     *
     * @param appId
     *            aplicacion id
     */
    boolean verificarMaestro(String appId);

}
