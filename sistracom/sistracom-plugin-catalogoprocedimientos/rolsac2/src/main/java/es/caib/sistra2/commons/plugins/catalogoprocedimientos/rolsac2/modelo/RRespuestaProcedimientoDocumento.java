package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Respuesta ProcedimientoDocumento
 *
 * @author Indra
 *
 */

public class RRespuestaProcedimientoDocumento extends RRespuestaBasicaRolsac {
	/** Resultado. **/
	private RProcedimientoDocumento[] items;

	public RRespuestaProcedimientoDocumento(String status, String mensaje, Long totalCount, RProcedimientoDocumento[] items) {
		super(status, mensaje, totalCount);
		this.items = items;
	}

	public RRespuestaProcedimientoDocumento() {
		super();
	}

	public RProcedimientoDocumento[] getItems() {
		return items;
	}

	public void setItems(RProcedimientoDocumento[] items) {
		this.items = items;
	}
}