package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * RespuestaPlatTramitElectronica
 *
 * @author indra
 *
 */

public class RRespuestaPlatTramitElectronica extends RRespuestaBasicaRolsac {

	/** Resultado. **/
	private RPlatTramitElectronica[] items;

	public RRespuestaPlatTramitElectronica(String status, String mensaje, long totalCount, RPlatTramitElectronica[] items) {
		super(status, mensaje, totalCount);
		this.items = items;
	};

	public RRespuestaPlatTramitElectronica() {
		super();
	}

	public RPlatTramitElectronica[] getItems() {
		return items;
	}

	public void setItems(RPlatTramitElectronica[] items) {
		this.items = items;
	}
}