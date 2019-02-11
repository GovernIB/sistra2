package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * Componente simple
 *
 * @author Indra
 *
 */
public class DisenyoFormularioComponenteSimple {

	/** Identificador. **/
	private Long codigo;

	/** Identificador. **/
	private String identificador;

	/** Tipo. **/
	private TypeObjetoFormulario tipo;

	/** Constructor. **/
	public DisenyoFormularioComponenteSimple() {
		// Vacio
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
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the tipo
	 */
	public TypeObjetoFormulario getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypeObjetoFormulario tipo) {
		this.tipo = tipo;
	}

}
