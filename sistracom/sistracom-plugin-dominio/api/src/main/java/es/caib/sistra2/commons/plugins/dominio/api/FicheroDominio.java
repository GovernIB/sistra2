package es.caib.sistra2.commons.plugins.dominio.api;

import java.io.Serializable;

/**
 * Clase de acceso a un fichero devuelto por un dominio.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class FicheroDominio implements Serializable {

    /**
     * Nombre fichero con extensión.
     */
    private String nombre;

    /**
     * Descripción fichero.
     */
    private String descripcion;

    /**
     * Contenido fichero en base64.
     */
    private String contenidoB64;

    /**
     * Constructor.
     */
    public FicheroDominio() {
        super();
    }

    /**
     * Constructor.
     *
     * @param pNombre
     *            Nombre fichero
     * @param pContenidoB64
     *            Contenido b64
     */
    public FicheroDominio(final String pNombre, final String pContenidoB64) {
        super();
        nombre = pNombre;
        contenidoB64 = pContenidoB64;
    }

    /**
     * Nombre fichero con extensión.
     *
     * @return nombre fichero
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Nombre fichero con extensión.
     *
     * @param pNombre
     *            nombre a establecer
     */
    public void setNombre(final String pNombre) {
        nombre = pNombre;
    }

    /**
     * Método de acceso a contenidoB64.
     *
     * @return contenidoB64
     */
    public String getContenidoB64() {
        return contenidoB64;
    }

    /**
     * Método para establecer contenidoB64.
     *
     * @param pContenidoB64
     *            contenidoB64 a establecer
     */
    public void setContenidoB64(final String pContenidoB64) {
        contenidoB64 = pContenidoB64;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param pDescripcion
     *            the descripcion to set
     */
    public void setDescripcion(final String pDescripcion) {
        descripcion = pDescripcion;
    }

}
