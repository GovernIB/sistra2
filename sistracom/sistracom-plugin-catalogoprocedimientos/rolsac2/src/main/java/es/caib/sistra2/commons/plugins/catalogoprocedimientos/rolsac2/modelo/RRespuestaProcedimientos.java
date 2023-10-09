package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaProcedimientos extends RRespuestaBasicaRolsac {

    /** Resultado. **/
    private RProcedimientoRolsac[] resultado;

    /** Url. **/
    private String url;

    /** Constructor. **/
    public RRespuestaProcedimientos(final String status, final String mensaje,
            final Long numeroElementos,
            final RProcedimientoRolsac[] resultado) {
        super(status, mensaje, numeroElementos);
        this.resultado = resultado;
    }

    /** Constructor. **/
    public RRespuestaProcedimientos() {
        super();
    }

    /**
     * @return the resultado
     */
    public RProcedimientoRolsac[] getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *            the resultado to set
     */
    public void setResultado(final RProcedimientoRolsac[] resultado) {
        this.resultado = resultado;
    }

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
