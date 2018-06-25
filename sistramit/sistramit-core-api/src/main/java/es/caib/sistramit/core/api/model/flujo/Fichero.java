package es.caib.sistramit.core.api.model.flujo;

/**
 * Fichero anexado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Fichero implements ModelApi {

    /**
     * Nombre del fichero con extensión.
     */
    private String fichero;
    /**
     * Título particular del fichero (para genéricos).
     */
    private String titulo;

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

}
