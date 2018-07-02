package es.caib.sistrages.core.api.model;

/**
 * La clase ObjetoFormulario.
 */
@SuppressWarnings("serial")
public abstract class ObjetoFormulario extends ModelApi {

	/**
	 * codigo.
	 */
	private Long codigo;

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

}
