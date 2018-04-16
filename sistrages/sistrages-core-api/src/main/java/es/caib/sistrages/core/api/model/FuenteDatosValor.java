package es.caib.sistrages.core.api.model;

/**
 *
 * Indica un dato dentro de un campo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FuenteDatosValor extends ModelApi {

	/** Id. **/
	private Long id;

	/** Codigo del Campo. **/
	private String idCampo;

	/** Orden del campo. **/
	private Integer ordenCampo;

	/** Campo. **/
	private FuenteDatosCampo campo;

	/** Valor. **/
	private String valor;

	/**
	 * Constructor básico.
	 */
	public FuenteDatosValor() {
		id = null;
	}

	/**
	 * Constructor .
	 *
	 * @param id
	 * @param codigo
	 */
	public FuenteDatosValor(final Long id, final String idCampo, final String valor) {
		this.id = id;
		this.setIdCampo(idCampo);
		this.valor = valor;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

	/**
	 * @return the campo
	 */
	public FuenteDatosCampo getCampo() {
		return campo;
	}

	/**
	 * @param campo
	 *            the campo to set
	 */
	public void setCampo(final FuenteDatosCampo campo) {
		this.campo = campo;
	}

	/**
	 * @return the idCampo
	 */
	public String getIdCampo() {
		return idCampo;
	}

	/**
	 * @param idCampo
	 *            the idCampo to set
	 */
	public void setIdCampo(final String idCampo) {
		this.idCampo = idCampo;
	}

	/**
	 * @return the ordenCampo
	 */
	public Integer getOrdenCampo() {
		return ordenCampo;
	}

	/**
	 * @param ordenCampo
	 *            the ordenCampo to set
	 */
	public void setOrdenCampo(final Integer ordenCampo) {
		this.ordenCampo = ordenCampo;
	}

}
