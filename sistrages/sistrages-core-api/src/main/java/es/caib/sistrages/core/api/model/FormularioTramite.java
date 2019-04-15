package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioGestor;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;

/**
 *
 * Formulario trámite.
 *
 * @author Indra
 *
 */

public class FormularioTramite extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Identificador del idFormularioInterno. */
	private String identificador;

	/** Descripción idFormularioInterno. */
	private Literal descripcion;

	/** Tipo: idFormularioInterno trámite (T) o idFormularioInterno captura (C). */
	private TypeFormulario tipo;

	/** Orden. **/
	private int orden;

	/** Formulario. **/
	private Long idFormularioInterno;

	/**
	 * Obligatorio:
	 * <ul>
	 * <li>Si (S)</li>
	 * <li>Opcional (N)</li>
	 * <li>Dependiente (D)</li>
	 */
	private TypeFormularioObligatoriedad obligatoriedad;

	/** En caso de ser dependiente establece obligatoriedad */
	private Script scriptObligatoriedad;

	/**
	 * Indica si se debe firmar digitalmente (para idFormularioInterno tipo Tramite)
	 */
	private boolean debeFirmarse;

	/**
	 * Permite establecer quién debe firmar el idFormularioInterno (para
	 * idFormularioInterno tramite)
	 */
	private Script scriptFirma;

	/**
	 * Permite establecer parametros cada vez que se acceda al idFormularioInterno
	 */
	private Script scriptParametros;

	/** Script para establecer datos iniciales idFormularioInterno. */
	private Script scriptDatosIniciales;

	/**
	 * Este script se ejecutará tras el retorno del gestor de idFormularioInterno y
	 * permitirá: - validar el idFormularioInterno tras el retorno del gestor de
	 * idFormularioInterno - alimentar datos de los otros formularios y cambiar su
	 * estado.
	 */
	private Script scriptRetorno;

	/** Indica tipo idFormularioInterno: interno (I) / externo (E) */
	private TypeFormularioGestor tipoFormulario;

	/** Formulario gestor interno (si es interno) */
	private GestorExternoFormularios formularioGestorInterno;

	/** Formulario gestor externo (si es externo) */
	private GestorExternoFormularios formularioGestorExterno;

	private DisenyoFormulario disenyoFormulario;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public FormularioTramite() {
		super();
	}

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

	/**
	 * Obtiene el valor de idString.
	 *
	 * @return el valor de idString
	 */
	public String getIdString() {
		if (codigo == null) {
			return "";
		} else {
			return codigo.toString();
		}
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setIdString(final String id) {
		if (id == null) {
			this.codigo = null;
		} else {
			this.codigo = Long.valueOf(id);
		}

	}

	/**
	 * Obtiene el valor de identificador.
	 *
	 * @return el valor de identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Establece el valor de identificador.
	 *
	 * @param identificador
	 *            el nuevo valor de identificador
	 */
	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public Literal getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de tipo.
	 *
	 * @return el valor de tipo
	 */
	public TypeFormulario getTipo() {
		return tipo;
	}

	/**
	 * Establece el valor de tipo.
	 *
	 * @param tipo
	 *            el nuevo valor de tipo
	 */
	public void setTipo(final TypeFormulario tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

	/**
	 * Obtiene el valor de obligatoriedad.
	 *
	 * @return el valor de obligatoriedad
	 */
	public TypeFormularioObligatoriedad getObligatoriedad() {
		return obligatoriedad;
	}

	/**
	 * Establece el valor de obligatoriedad.
	 *
	 * @param obligatoriedad
	 *            el nuevo valor de obligatoriedad
	 */
	public void setObligatoriedad(final TypeFormularioObligatoriedad obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}

	/**
	 * @return the scriptObligatoriedad
	 */
	public Script getScriptObligatoriedad() {
		return scriptObligatoriedad;
	}

	/**
	 * @param scriptObligatoriedad
	 *            the scriptObligatoriedad to set
	 */
	public void setScriptObligatoriedad(final Script scriptObligatoriedad) {
		this.scriptObligatoriedad = scriptObligatoriedad;
	}

	/**
	 * @return the debeFirmarse
	 */
	public boolean isDebeFirmarse() {
		return debeFirmarse;
	}

	/**
	 * @param debeFirmarse
	 *            the debeFirmarse to set
	 */
	public void setDebeFirmarse(final boolean debeFirmarse) {
		this.debeFirmarse = debeFirmarse;
	}

	/**
	 * @return the scriptFirma
	 */
	public Script getScriptFirma() {
		return scriptFirma;
	}

	/**
	 * @param scriptFirma
	 *            the scriptFirma to set
	 */
	public void setScriptFirma(final Script scriptFirma) {
		this.scriptFirma = scriptFirma;
	}

	/**
	 * @return the scriptPrerrigistro
	 */
	public Script getScriptParametros() {
		return scriptParametros;
	}

	/**
	 * @param scriptPrerrigistro
	 *            the scriptPrerrigistro to set
	 */
	public void setScriptParametros(final Script scriptPrerrigistro) {
		this.scriptParametros = scriptPrerrigistro;
	}

	/**
	 * @return the scriptDatosIniciales
	 */
	public Script getScriptDatosIniciales() {
		return scriptDatosIniciales;
	}

	/**
	 * @param scriptDatosIniciales
	 *            the scriptDatosIniciales to set
	 */
	public void setScriptDatosIniciales(final Script scriptDatosIniciales) {
		this.scriptDatosIniciales = scriptDatosIniciales;
	}

	/**
	 * @return the scriptRetorno
	 */
	public Script getScriptRetorno() {
		return scriptRetorno;
	}

	/**
	 * @param scriptRetorno
	 *            the scriptRetorno to set
	 */
	public void setScriptRetorno(final Script scriptRetorno) {
		this.scriptRetorno = scriptRetorno;
	}

	/**
	 * @return the tipoFormulario
	 */
	public TypeFormularioGestor getTipoFormulario() {
		return tipoFormulario;
	}

	/**
	 * @param tipoFormulario
	 *            the tipoFormulario to set
	 */
	public void setTipoFormulario(final TypeFormularioGestor tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	/**
	 * @return the formularioGestorInterno
	 */
	public GestorExternoFormularios getFormularioGestorInterno() {
		return formularioGestorInterno;
	}

	/**
	 * @param formularioGestorInterno
	 *            the formularioGestorInterno to set
	 */
	public void setFormularioGestorInterno(final GestorExternoFormularios formularioGestorInterno) {
		this.formularioGestorInterno = formularioGestorInterno;
	}

	/**
	 * @return the formularioGestorExterno
	 */
	public GestorExternoFormularios getFormularioGestorExterno() {
		return formularioGestorExterno;
	}

	/**
	 * @param formularioGestorExterno
	 *            the formularioGestorExterno to set
	 */
	public void setFormularioGestorExterno(final GestorExternoFormularios formularioGestorExterno) {
		this.formularioGestorExterno = formularioGestorExterno;
	}

	public Long getIdFormularioInterno() {
		return idFormularioInterno;
	}

	public void setIdFormularioInterno(final Long formulario) {
		this.idFormularioInterno = formulario;
	}

	public DisenyoFormulario getDisenyoFormulario() {
		return disenyoFormulario;
	}

	public void setDisenyoFormulario(final DisenyoFormulario disenyoFormulario) {
		this.disenyoFormulario = disenyoFormulario;
	}
}
