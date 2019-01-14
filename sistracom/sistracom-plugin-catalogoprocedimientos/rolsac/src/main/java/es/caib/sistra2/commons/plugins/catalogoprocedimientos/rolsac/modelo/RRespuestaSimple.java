package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Respuesta simple de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaSimple extends RRespuestaBasicaRolsac {

	/** Resultado. **/
	private String resultado;

	/** Constructor. **/
	public RRespuestaSimple(final String status, final String mensaje, final Integer numeroElementos,
			final String resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	}

	/** Constructor. **/
	public RRespuestaSimple() {
		super();
	}

	/**
	 * @return the resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(final String resultado) {
		this.resultado = resultado;
	}
}
