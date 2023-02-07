package es.caib.sistrages.core.api.model;

/**
 *
 * Indica un dato dentro de un campo.
 *
 * @author Indra
 *
 */

public class FuenteDatosValor extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo interno. **/
	private Long codigo;

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
		codigo = null;
	}

	/**
	 * Constructor .
	 *
	 * @param codigo
	 * @param codigo
	 */
	public FuenteDatosValor(final Long codigo, final String idCampo, final String valor) {
		this.codigo = codigo;
		this.setIdCampo(idCampo);
		this.valor = valor;
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
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

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           return valor;
     }

}
