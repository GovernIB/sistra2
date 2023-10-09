package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * RespuestaPlatTramitElectronica
 *
 * @author indra
 *
 */

public class RRespuestaPlatTramitElectronica extends RRespuestaBasicaRolsac {

	/** Resultado. **/
	private RPlatTramitElectronica[] resultado;

	public RRespuestaPlatTramitElectronica(String status, String mensaje, long l, RPlatTramitElectronica[] resultado) {
		super(status, mensaje, l);
		this.resultado = resultado;
	};

	public RRespuestaPlatTramitElectronica() {
		super();
	}

	public RPlatTramitElectronica[] getResultado() {
		return resultado;
	}

	public void setResultado(RPlatTramitElectronica[] resultado) {
		this.resultado = resultado;
	}
}