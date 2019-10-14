package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Anexo trámite.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RAnexoTramite", description = "Descripcion de RAnexoTramite")
public class RAnexoTramite {

	/** Identificador. */
	@ApiModelProperty(value = "Identificador")
	private String identificador;

	/** Descripción. */
	@ApiModelProperty(value = "Descripción")
	private String descripcion;

	/** Obligatoriedad: Si (S) / No (N) / Depende (D). */
	@ApiModelProperty(value = "Obligatoriedad: Si (S) / No (N) / Depende (D)")
	private String obligatoriedad;

	/** Indica tiop documental: TD01 Resolución hasta TD99 Otros **/
	@ApiModelProperty(value = "Indica tiop documental: TD01 Resolución hasta TD99 Otros")
	private String tipoDocumental;

	/** Script dependencia. */
	@ApiModelProperty(value = "Script dependencia")
	private RScript scriptDependencia;

	/** Ayuda anexo. */
	@ApiModelProperty(value = "Ayuda anexo")
	private RAnexoTramiteAyuda ayuda;

	/** Tipo presentacion: Electrónica (E) / Presencial (P). */
	@ApiModelProperty(value = "Tipo presentacion: Electrónica (E) / Presencial (P)")
	private String presentacion;

	/** Propiedades para presentación electrónica. */
	@ApiModelProperty(value = "Propiedades para presentación electrónica")
	private RAnexoTramitePresentacionElectronica presentacionElectronica;

	/**
	 * Método de acceso a identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer identificador.
	 *
	 * @param identificador identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * Método de acceso a descripcion.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para establecer descripcion.
	 *
	 * @param descripcion descripcion a establecer
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método de acceso a obligatoriedad.
	 *
	 * @return obligatoriedad
	 */
	public String getObligatoriedad() {
		return obligatoriedad;
	}

	/**
	 * Método para establecer obligatoriedad.
	 *
	 * @param obligatoriedad obligatoriedad a establecer
	 */
	public void setObligatoriedad(final String obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}

	/**
	 * Método de acceso a scriptDependencia.
	 *
	 * @return scriptDependencia
	 */
	public RScript getScriptDependencia() {
		return scriptDependencia;
	}

	/**
	 * Método para establecer scriptDependencia.
	 *
	 * @param scriptDependencia scriptDependencia a establecer
	 */
	public void setScriptDependencia(final RScript scriptDependencia) {
		this.scriptDependencia = scriptDependencia;
	}

	/**
	 * Método de acceso a ayuda.
	 *
	 * @return ayuda
	 */
	public RAnexoTramiteAyuda getAyuda() {
		return ayuda;
	}

	/**
	 * Método para establecer ayuda.
	 *
	 * @param ayuda ayuda a establecer
	 */
	public void setAyuda(final RAnexoTramiteAyuda ayuda) {
		this.ayuda = ayuda;
	}

	/**
	 * Método de acceso a presentacion.
	 *
	 * @return presentacion
	 */
	public String getPresentacion() {
		return presentacion;
	}

	/**
	 * Método para establecer presentacion.
	 *
	 * @param presentacion presentacion a establecer
	 */
	public void setPresentacion(final String presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * Método de acceso a presentacionElectronica.
	 *
	 * @return presentacionElectronica
	 */
	public RAnexoTramitePresentacionElectronica getPresentacionElectronica() {
		return presentacionElectronica;
	}

	/**
	 * Método para establecer presentacionElectronica.
	 *
	 * @param presentacionElectronica presentacionElectronica a establecer
	 */
	public void setPresentacionElectronica(final RAnexoTramitePresentacionElectronica presentacionElectronica) {
		this.presentacionElectronica = presentacionElectronica;
	}

	/**
	 * @return the tipoDocumental
	 */
	public final String getTipoDocumental() {
		return tipoDocumental;
	}

	/**
	 * @param tipoDocumental the tipoDocumental to set
	 */
	public final void setTipoDocumental(final String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

}
