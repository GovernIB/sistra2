package es.caib.sistrages.core.api.model.comun;

import java.util.List;

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

	/** Hijos **/
	private List<DisenyoFormularioComponenteSimple> hijos;

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

	/**
	 * @return the hijos
	 */
	public List<DisenyoFormularioComponenteSimple> getHijos() {
		return hijos;
	}

	/**
	 * @param hijos the hijos to set
	 */
	public void setHijos(List<DisenyoFormularioComponenteSimple> hijos) {
		this.hijos = hijos;
	}

	/**
	 * Comprueba si tiene hijos.
	 */
	public boolean tieneHijos() {
		return hijos != null && !hijos.isEmpty();
	}
}
