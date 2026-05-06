package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaServicios extends RRespuestaBasicaRolsac {

	/** items. **/
	private RServicioRolsac[] items;

	/** Url. **/
	private String url;

	/** Constructor. **/
	public RRespuestaServicios(final String status, final String mensaje, final Long totalCount,
			final RServicioRolsac[] items) {
		super(status, mensaje, totalCount);
		this.items = items;
	}

	/** Constructor. **/
	public RRespuestaServicios() {
		super();
	}

	/**
	 * @return the items
	 */
	public RServicioRolsac[] getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(final RServicioRolsac[] items) {
		this.items = items;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
