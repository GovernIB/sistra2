package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Respuesta ProcedimientoDocumento
 *
 * @author Indra
 *
 */

public class RRespuestaProcedimientoDocumento extends RRespuestaBasicaRolsac {
	/** Resultado. **/
	private RProcedimientoDocumento[] resultado;

	public RRespuestaProcedimientoDocumento(String status, String mensaje, Long numeroElementos, RProcedimientoDocumento[] resultado) {
		super(status, mensaje, numeroElementos);
		this.resultado = resultado;
	}

	public RRespuestaProcedimientoDocumento() {
		super();
	}

	public RProcedimientoDocumento[] getResultado() {
		return resultado;
	}

	public void setResultado(RProcedimientoDocumento[] resultado) {
		this.resultado = resultado;
	}
}