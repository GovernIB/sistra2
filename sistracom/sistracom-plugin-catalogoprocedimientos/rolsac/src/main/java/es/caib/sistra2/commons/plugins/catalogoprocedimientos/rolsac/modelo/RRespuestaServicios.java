package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaServicios extends RRespuestaBasicaRolsac {

	/** Resultado. **/
	private RServicioRolsac[] resultado;

	/** Constructor. **/
	public RRespuestaServicios(final String status, final String mensaje, final Integer numeroElementos,
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
}
