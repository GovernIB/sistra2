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

	/** Campo. **/
	private FuenteDatosCampo campo;

	/** Valor. **/
	private String valor;

	/**
	 * Constructor básico.
	 */
	public FuenteDatosValor() {
		id = null;
		campo = null;
	}

	/**
	 * Constructor .
	 *
	 * @param id
	 * @param campo
	 */
	public FuenteDatosValor(final Long id, final FuenteDatosCampo campo, final String valor) {
		this.id = id;
		this.campo = campo;
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

}
