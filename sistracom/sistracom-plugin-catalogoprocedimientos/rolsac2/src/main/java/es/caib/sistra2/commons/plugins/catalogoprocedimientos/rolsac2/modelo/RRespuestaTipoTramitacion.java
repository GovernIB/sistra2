package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * RespuestaTipoTramitacion
 *
 * @author indra
 *
 */
public class RRespuestaTipoTramitacion extends RRespuestaBasicaRolsac {

	/** items. **/
	private RTipoTramitacion[] items;

	public RRespuestaTipoTramitacion(String status, String mensaje, long totalCount, RTipoTramitacion[] items) {
		super(status, mensaje, totalCount);
		this.items = items;
	};

	public RRespuestaTipoTramitacion() {
		super();
	}

	public RTipoTramitacion[] getItems() {
		return items;
	}

	public void setItems(RTipoTramitacion[] items) {
		this.items = items;
	}
}