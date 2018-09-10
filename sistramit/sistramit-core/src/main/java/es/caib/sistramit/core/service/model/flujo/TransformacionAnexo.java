package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Datos transformación anexo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TransformacionAnexo implements Serializable {
    /**
     * Indica si se ha convertido.
     */
    private boolean convertido;
    /**
     * Nombre fichero anexo (con extensión).
     */
    private String nombreFichero;
    /**
     * Datos fichero.
     */
    private byte[] datosFichero;

    /**
     * Método de acceso a nombreFichero.
     *
     * @return nombreFichero
     */
    public String getNombreFichero() {
        return nombreFichero;
    }

    /**
     * Método para establecer nombreFichero.
     *
     * @param pNombreFichero
     *            nombreFichero a establecer
     */
    public void setNombreFichero(final String pNombreFichero) {
        nombreFichero = pNombreFichero;
    }

    /**
     * Método de acceso a datosFichero.
     *
     * @return datosFichero
     */
    public byte[] getDatosFichero() {
        return datosFichero;
    }

    /**
     * Método para establecer datosFichero.
     *
     * @param pDatosFichero
     *            datosFichero a establecer
     */
    public void setDatosFichero(final byte[] pDatosFichero) {
        datosFichero = pDatosFichero;
    }

    /**
     * Método de acceso a convertido.
     *
     * @return convertido
     */
    public boolean isConvertido() {
        return convertido;
    }

    /**
     * Método para establecer convertido.
     *
     * @param pConvertido
     *            convertido a establecer
     */
    public void setConvertido(final boolean pConvertido) {
        convertido = pConvertido;
    }

}
