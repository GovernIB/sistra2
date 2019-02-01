package es.caib.sistrages.core.api.model.comun;

/**
 * Clase para formularios simplificado
 *
 * @author Indra
 *
 */
public class TramiteSimpleFormulario {

	/** Codigo. **/
	private Long codigo;

	/** Id formulario interno. **/
	private Long idFormularioInterno;

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
	 * @return the idFormularioInterno
	 */
	public Long getIdFormularioInterno() {
		return idFormularioInterno;
	}

	/**
	 * @param idFormularioInterno
	 *            the idFormularioInterno to set
	 */
	public void setIdFormularioInterno(final Long idFormularioInterno) {
		this.idFormularioInterno = idFormularioInterno;
	}
}
