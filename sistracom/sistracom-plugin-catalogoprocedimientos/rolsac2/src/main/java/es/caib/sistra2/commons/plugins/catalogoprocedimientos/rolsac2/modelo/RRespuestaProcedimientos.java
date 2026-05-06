package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaProcedimientos extends RRespuestaBasicaRolsac {

    /** items. **/
    private RProcedimientoRolsac[] items;

    /** Url. **/
    private String url;

    /** Constructor. **/
    public RRespuestaProcedimientos(final String status, final String mensaje,
            final Long totalCount,
            final RProcedimientoRolsac[] items) {
        super(status, mensaje, totalCount);
        this.items = items;
    }

    /** Constructor. **/
    public RRespuestaProcedimientos() {
        super();
    }

    /**
     * @return the items
     */
    public RProcedimientoRolsac[] getItems() {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(final RProcedimientoRolsac[] items) {
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
