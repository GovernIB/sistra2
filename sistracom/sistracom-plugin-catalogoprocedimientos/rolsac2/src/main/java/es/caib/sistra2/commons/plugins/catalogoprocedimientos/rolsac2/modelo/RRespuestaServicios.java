package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaServicios extends RRespuestaBasicaRolsac {

	/** Resultado. **/
	private RServicioRolsac[] resultado;

	/** Url. **/
	private String url;

	/** Constructor. **/
	public RRespuestaServicios(final String status, final String mensaje, final Long numeroElementos,
			final RServicioRolsac[] resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	}

	/** Constructor. **/
	public RRespuestaServicios() {
		super();
	}

	/**
	 * @return the resultado
	 */
	public RServicioRolsac[] getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(final RServicioRolsac[] resultado) {
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
