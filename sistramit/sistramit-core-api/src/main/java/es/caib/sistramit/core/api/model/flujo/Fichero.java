package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Fichero anexado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Fichero implements Serializable {

    /**
     * Nombre del fichero con extensión.
     */
    private String fichero;
    /**
     * Título particular del fichero (para genéricos).
     */
    private String titulo;

    /**
     * Firmas del fichero. Se generan según el orden de los firmantes.
     */
    private List<Firma> firmas = new ArrayList<>(); // TODO REPENSAR SI SE PASA FIRMA AL PASO DE REGISTRO

    /**
     * Indica nombre del fichero con extensión.
     *
     * @return nombre del fichero con extensión
     */
    public String getFichero() {
        return fichero;
    }

    /**
     * Indica nombre del fichero con extensión.
     *
     * @param pFichero
     *            nombre del fichero con extensión
     */
    public void setFichero(final String pFichero) {
        fichero = pFichero;
    }

    /**
     * Indica título particular del fichero (para genéricos).
     *
     * @return título particular del fichero (para genéricos)
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Indica título particular del fichero (para genéricos).
     *
     * @param pTitulo
     *            título particular del fichero (para genéricos)
     */
    public void setTitulo(final String pTitulo) {
        titulo = pTitulo;
    }

    /**
     * Indica firmas del fichero.
     *
     * @return firmas del fichero
     */
    public List<Firma> getFirmas() {
        return firmas;
    }

    /**
     * Indica firmas del fichero.
     *
     * @param pFirmas
     *            firmas del fichero
     */
    public void setFirmas(final List<Firma> pFirmas) {
        firmas = pFirmas;
    }

}
