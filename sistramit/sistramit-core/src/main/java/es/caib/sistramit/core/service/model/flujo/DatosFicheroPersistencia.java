package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Datos de un fichero.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosFicheroPersistencia implements Serializable {

    /**
     * Nombre con extensión.
     */
    private String nombre;
    /**
     * Contenido.
     */
    private byte[] contenido;

    /**
     * Método de acceso a nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para establecer nombre.
     *
     * @param pNombre
     *            nombre a establecer
     */
    public void setNombre(final String pNombre) {
        nombre = pNombre;
    }

    /**
     * Método de acceso a contenido.
     *
     * @return contenido
     */
    public byte[] getContenido() {
        return contenido;
    }

    /**
     * Método para establecer contenido.
     *
     * @param pContenido
     *            contenido a establecer
     */
    public void setContenido(final byte[] pContenido) {
        contenido = pContenido;
    }

}
