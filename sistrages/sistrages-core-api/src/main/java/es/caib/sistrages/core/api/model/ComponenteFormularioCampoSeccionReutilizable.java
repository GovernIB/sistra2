package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormularioCampoCaptcha.
 */

public final class ComponenteFormularioCampoSeccionReutilizable extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Letra **/
	private String letra;

	/** Id seccion reutilizable **/
	private Long idSeccionReutilizable;

	/** Identificador seccion reutilizable **/
	private String identificadorSeccionReutilizable;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoCaptcha.
	 */
	public ComponenteFormularioCampoSeccionReutilizable() {
		this.setTipo(TypeObjetoFormulario.SECCION_REUTILIZABLE);
	}

	/**
	 * @return the letra
	 */
	public String getLetra() {
		return letra;
	}

	/**
	 * @param letra the letra to set
	 */
	public void setLetra(String letra) {
		this.letra = letra;
	}

	/**
	 * @return the idSeccionReutilizable
	 */
	public Long getIdSeccionReutilizable() {
		return idSeccionReutilizable;
	}

	/**
	 * @param idSeccionReutilizable the idSeccionReutilizable to set
	 */
	public void setIdSeccionReutilizable(Long idSeccionReutilizable) {
		this.idSeccionReutilizable = idSeccionReutilizable;
	}

	/**
	 * @return the identificadorSeccionReutilizable
	 */
	public String getIdentificadorSeccionReutilizable() {
		return identificadorSeccionReutilizable;
	}

	/**
	 * @param identificadorSeccionReutilizable the identificadorSeccionReutilizable to set
	 */
	public void setIdentificadorSeccionReutilizable(String identificadorSeccionReutilizable) {
		this.identificadorSeccionReutilizable = identificadorSeccionReutilizable;
	}

}
