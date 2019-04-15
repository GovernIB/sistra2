package es.caib.sistramit.core.api.model.flujo;

/**
 *
 * Establece el contador de documentos según estado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ContadorDocumentos implements ModelApi {

	/**
	 * Número de documentos correctos.
	 */
	private int correctos;

	/**
	 * Número de documentos obligatorios pendientes de rellenar.
	 */
	private int obligatorios;

	/**
	 * Número de documentos opcionales pendientes de rellenar.
	 */
	private int opcionales;

	/**
	 * Número de documentos dependientes.
	 */
	private int dependientes;

	/**
	 * Número de documentos a subsanar.
	 */
	private int subsanables;

	/**
	 * Número de documentos a entregar presencialmente.
	 */
	private int presenciales;

	/**
	 * Método de acceso a correctos.
	 * 
	 * @return correctos
	 */
	public int getCorrectos() {
		return correctos;
	}

	/**
	 * Método para establecer correctos.
	 * 
	 * @param correctos
	 *            correctos a establecer
	 */
	public void setCorrectos(final int correctos) {
		this.correctos = correctos;
	}

	/**
	 * Método de acceso a obligatorios.
	 * 
	 * @return obligatorios
	 */
	public int getObligatorios() {
		return obligatorios;
	}

	/**
	 * Método para establecer obligatorios.
	 * 
	 * @param obligatorios
	 *            obligatorios a establecer
	 */
	public void setObligatorios(final int obligatorios) {
		this.obligatorios = obligatorios;
	}

	/**
	 * Método de acceso a opcionales.
	 * 
	 * @return opcionales
	 */
	public int getOpcionales() {
		return opcionales;
	}

	/**
	 * Método para establecer opcionales.
	 * 
	 * @param opcionales
	 *            opcionales a establecer
	 */
	public void setOpcionales(final int opcionales) {
		this.opcionales = opcionales;
	}

	/**
	 * Método de acceso a dependientes.
	 * 
	 * @return dependientes
	 */
	public int getDependientes() {
		return dependientes;
	}

	/**
	 * Método para establecer dependientes.
	 * 
	 * @param dependientes
	 *            dependientes a establecer
	 */
	public void setDependientes(final int dependientes) {
		this.dependientes = dependientes;
	}

	/**
	 * Método de acceso a subsanables.
	 * 
	 * @return subsanables
	 */
	public int getSubsanables() {
		return subsanables;
	}

	/**
	 * Método para establecer subsanables.
	 * 
	 * @param subsanables
	 *            subsanables a establecer
	 */
	public void setSubsanables(final int subsanables) {
		this.subsanables = subsanables;
	}

	/**
	 * Método de acceso a presenciales.
	 * 
	 * @return presenciales
	 */
	public int getPresenciales() {
		return presenciales;
	}

	/**
	 * Método para establecer presenciales.
	 * 
	 * @param presenciales
	 *            presenciales a establecer
	 */
	public void setPresenciales(final int presenciales) {
		this.presenciales = presenciales;
	}

}
