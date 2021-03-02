package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Archivos de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaArchivos extends RRespuestaBasicaRolsac {

	/** Resultado. **/
	private RArchivoRolsac[] resultado;

	/** Constructor. **/
	public RRespuestaArchivos(final String status, final String mensaje, final Integer numeroElementos,
			final RArchivoRolsac[] resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	}

	/** Constructor. **/
	public RRespuestaArchivos() {
		super();
	}

	/**
	 * @return the resultado
	 */
	public RArchivoRolsac[] getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *                      the resultado to set
	 */
	public void setResultado(final RArchivoRolsac[] resultado) {
		this.resultado = resultado;
	}
}
