package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaTramites extends RRespuestaBasicaRolsac {

	/** Items. **/
	private RTramiteRolsac[] items;

	/** Constructor. **/
	public RRespuestaTramites(final String status, final String mensaje, final Long totalCount,
			final RTramiteRolsac[] items) {
		super(status, mensaje, totalCount);
		this.items = items;
	}

	/** Constructor. **/
	public RRespuestaTramites() {
		super();
	}

	/**
	 * @return the items
	 */
	public RTramiteRolsac[] getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(final RTramiteRolsac[] items) {
		this.items = items;
	}
}
