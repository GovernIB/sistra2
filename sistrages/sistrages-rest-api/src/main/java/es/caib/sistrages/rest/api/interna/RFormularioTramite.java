package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Formulario trámite.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFormularioTramite", description = "Descripcion de RFormularioTramite")
public class RFormularioTramite {

	/** Identificador. */
	@ApiModelProperty(value = "Identificador")
	private String identificador;

	/** Descripción. */
	@ApiModelProperty(value = "Descripción")
	private String descripcion;

	/** Obligatoriedad: Si (S) / No (N) / Depende (D). */
	@ApiModelProperty(value = "Obligatoriedad: Si (S) / No (N) / Depende (D)")
	private String obligatoriedad;

	/** Script obligatoriedad. */
	@ApiModelProperty(value = "Script obligatoriedad")
	private RScript scriptObligatoriedad;

	/**
	 * Indica si hay que firmar electrónicamente (para presentación electrónica).
	 */
	@ApiModelProperty(value = "Indica si hay que firmar electrónicamente (para presentación electrónica)")
	private boolean firmar;

	/** Script firmantes. */
	@ApiModelProperty(value = "Script firmantes")
	private RScript scriptFirmantes;

	/** Script parámetros apertura. */
	@ApiModelProperty(value = "Script parámetros apertura")
	private RScript scriptParametrosApertura;

	/** Script datos iniciales. */
	@ApiModelProperty(value = "Script datos iniciales")
	private RScript scriptDatosIniciales;

	/** Script post guardar. */
	@ApiModelProperty(value = "Script post guardar")
	private RScript scriptPostguardar;

	/** Indica si es interno (si no externo). */
	@ApiModelProperty(value = "Indica si es interno (si no externo)")
	private boolean interno;

	/** Si es interno, se indica el diseño del formulario. */
	@ApiModelProperty(value = "Si es interno, se indica el diseño del formulario")
	private RFormularioInterno formularioInterno;

	/** Si es externo, se indica el formulario externo. */
	@ApiModelProperty(value = "Si es externo, se indica el formulario externo")
	private RFormularioExterno formularioExterno;

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
	 * @param identificador
	 *            identificador a establecer
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
	 * @param descripcion
	 *            descripcion a establecer
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
	 * @param obligatoriedad
	 *            obligatoriedad a establecer
	 */
	public void setObligatoriedad(final String obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}

	/**
	 * Método de acceso a scriptObligatoriedad.
	 *
	 * @return scriptDependencia
	 */
	public RScript getScriptObligatoriedad() {
		return scriptObligatoriedad;
	}

	/**
	 * Método para establecer scriptDependencia.
	 *
	 * @param scriptObligatoriedad
	 *            scriptDependencia a establecer
	 */
	public void setScriptObligatoriedad(final RScript scriptObligatoriedad) {
		this.scriptObligatoriedad = scriptObligatoriedad;
	}

	/**
	 * Método de acceso a firmar.
	 *
	 * @return firmar
	 */
	public boolean isFirmar() {
		return firmar;
	}

	/**
	 * Método para establecer firmar.
	 *
	 * @param firmar
	 *            firmar a establecer
	 */
	public void setFirmar(final boolean firmar) {
		this.firmar = firmar;
	}

	/**
	 * Método de acceso a scriptFirmantes.
	 *
	 * @return scriptFirmantes
	 */
	public RScript getScriptFirmantes() {
		return scriptFirmantes;
	}

	/**
	 * Método para establecer scriptFirmantes.
	 *
	 * @param scriptFirmantes
	 *            scriptFirmantes a establecer
	 */
	public void setScriptFirmantes(final RScript scriptFirmantes) {
		this.scriptFirmantes = scriptFirmantes;
	}

	/**
	 * Método de acceso a scriptParametrosApertura.
	 *
	 * @return scriptParametrosApertura
	 */
	public RScript getScriptParametrosApertura() {
		return scriptParametrosApertura;
	}

	/**
	 * Método para establecer scriptParametrosApertura.
	 *
	 * @param scriptParametrosApertura
	 *            scriptParametrosApertura a establecer
	 */
	public void setScriptParametrosApertura(final RScript scriptParametrosApertura) {
		this.scriptParametrosApertura = scriptParametrosApertura;
	}

	/**
	 * Método de acceso a scriptDatosIniciales.
	 *
	 * @return scriptDatosIniciales
	 */
	public RScript getScriptDatosIniciales() {
		return scriptDatosIniciales;
	}

	/**
	 * Método para establecer scriptDatosIniciales.
	 *
	 * @param scriptDatosIniciales
	 *            scriptDatosIniciales a establecer
	 */
	public void setScriptDatosIniciales(final RScript scriptDatosIniciales) {
		this.scriptDatosIniciales = scriptDatosIniciales;
	}

	/**
	 * Método de acceso a scriptPostguardar.
	 *
	 * @return scriptPostguardar
	 */
	public RScript getScriptPostguardar() {
		return scriptPostguardar;
	}

	/**
	 * Método para establecer scriptPostguardar.
	 *
	 * @param scriptPostguardar
	 *            scriptPostguardar a establecer
	 */
	public void setScriptPostguardar(final RScript scriptPostguardar) {
		this.scriptPostguardar = scriptPostguardar;
	}

	/**
	 * Método de acceso a interno.
	 *
	 * @return interno
	 */
	public boolean isInterno() {
		return interno;
	}

	/**
	 * Método para establecer interno.
	 *
	 * @param interno
	 *            interno a establecer
	 */
	public void setInterno(final boolean interno) {
		this.interno = interno;
	}

	/**
	 * Método de acceso a formularioInterno.
	 *
	 * @return formularioInterno
	 */
	public RFormularioInterno getFormularioInterno() {
		return formularioInterno;
	}

	/**
	 * Método para establecer formularioInterno.
	 *
	 * @param formularioInterno
	 *            formularioInterno a establecer
	 */
	public void setFormularioInterno(final RFormularioInterno formularioInterno) {
		this.formularioInterno = formularioInterno;
	}

	/**
	 * Método de acceso a formularioExterno.
	 *
	 * @return formularioExterno
	 */
	public RFormularioExterno getFormularioExterno() {
		return formularioExterno;
	}

	/**
	 * Método para establecer formularioExterno.
	 *
	 * @param formularioExterno
	 *            formularioExterno a establecer
	 */
	public void setFormularioExterno(final RFormularioExterno formularioExterno) {
		this.formularioExterno = formularioExterno;
	}

}
