package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * RespuestaTipoTramitacion
 *
 * @author indra
 *
 */
public class RRespuestaTipoTramitacion extends RRespuestaBasicaRolsac {

	/** Resultado. **/
	private RTipoTramitacion[] resultado;

	public RRespuestaTipoTramitacion(String status, String mensaje, long l, RTipoTramitacion[] resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RRespuestaTipoTramitacion() {
		super();
	}

	public RTipoTramitacion[] getResultado() {
		return resultado;
	}

	public void setResultado(RTipoTramitacion[] resultado) {
		this.resultado = resultado;
	}
}