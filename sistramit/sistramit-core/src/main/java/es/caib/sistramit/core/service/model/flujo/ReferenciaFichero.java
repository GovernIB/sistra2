package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Referencia a un fichero.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ReferenciaFichero implements Serializable {

    /**
     * Identificador fichero.
     */
    private long id;

    /**
     * Clave acceso.
     */
    private String clave;

    /**
     * Constructor.
     */
    public ReferenciaFichero() {
        super();
    }

    /**
     * Constructor.
     *
     * @param pId
     *            Identificador fichero
     * @param pClave
     *            Parámetro clave
     */
    public ReferenciaFichero(final long pId, final String pClave) {
        super();
        id = pId;
        clave = pClave;
    }

    /**
     * Método de acceso a id.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Método para establecer id.
     *
     * @param pId
     *            id a establecer
     */
    public void setId(final long pId) {
        id = pId;
    }

    /**
     * Método de acceso a clave.
     *
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * Método para establecer clave.
     *
     * @param pClave
     *            clave a establecer
     */
    public void setClave(final String pClave) {
        clave = pClave;
    }

    /**
     * Método para Crea new referencia fichero de la clase ReferenciaFichero.
     *
     * @return el referencia fichero
     */
    public static ReferenciaFichero createNewReferenciaFichero() {
        return new ReferenciaFichero();
    }

    /**
     * Método para Crea new referencia fichero de la clase ReferenciaFichero.
     * * @param
     *
     * @param pId
     *            Identificador fichero
     * @param pClave
     *            Parámetro clave
     * @return el referencia fichero
     */
    public static ReferenciaFichero createNewReferenciaFichero(final long pId,
            final String pClave) {
        return new ReferenciaFichero(pId, pClave);
    }

}
