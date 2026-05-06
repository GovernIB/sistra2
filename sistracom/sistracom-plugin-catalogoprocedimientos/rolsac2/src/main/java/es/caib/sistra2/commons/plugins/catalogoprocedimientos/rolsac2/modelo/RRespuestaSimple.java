package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Respuesta simple de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaSimple extends RRespuestaBasicaRolsac {

	/** Resultado. **/
	private String resultadoURL;

	/** Constructor. **/
	public RRespuestaSimple(final String status, final String mensaje, final Long totalCount,
			final String resultadoURL) {
		super(status, mensaje, totalCount);
		this.resultadoURL = resultadoURL;
	}

	/** Constructor. **/
	public RRespuestaSimple() {
		super();
	}

	/**
	 * @return the resultado
	 */
	public String getResultadoURL() {
		return resultadoURL;
	}

	/**
	 * @param resultadoURL
	 *            the resultado to set
	 */
	public void setResultadoURL(final String resultadoURL) {
		this.resultadoURL = resultadoURL;
	}
}
