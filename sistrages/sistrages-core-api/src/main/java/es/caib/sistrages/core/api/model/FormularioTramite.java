package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypeInterno;

/**
 *
 * Formulario trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FormularioTramite extends ModelApi {

	/** Id. */
	private Long id;

	/** Identificador del formulario. */
	private String codigo;

	/** Descripción formulario. */
	private Literal descripcion;

	/** Tipo: formulario trámite (T) o formulario captura (C). */
	private TypeFormulario tipo;

	/** Orden. **/
	private int orden;

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

	/** Indica si se debe firmar digitalmente (para formulario tipo Tramite) */
	private boolean debeFirmarse;

	/**
	 * Permite establecer quién debe firmar el formulario (para formulario tramite)
	 */
	private Script scriptFirma;

	/** Indica si se debe presentar en preregistro. */
	private boolean debePrerregistrarse;

	/** Script para establecer datos iniciales formulario. */
	private Script scriptPrerrigistro;

	/** Permite establecer parametros cada vez que se acceda al formulario */
	private Script scriptDatosIniciales;

	/**
	 * Este script se ejecutará tras el retorno del gestor de formulario y
	 * permitirá: - validar el formulario tras el retorno del gestor de formulario -
	 * alimentar datos de los otros formularios y cambiar su estado.
	 */
	private Script scriptRetorno;

	/** Indica tipo formulario: interno (I) / externo (E) */
	private TypeInterno tipoFormulario;

	/** Formulario gestor interno (si es interno) */
	private Gestor formularioGestorInterno;

	/** Formulario gestor externo (si es externo) */
	private Gestor formularioGestorExterno;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public FormularioTramite() {
		super();
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de idString.
	 *
	 * @return el valor de idString
	 */
	public String getIdString() {
		if (id == null) {
			return "";
		} else {
			return id.toString();
		}
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setIdString(final String id) {
		if (id == null) {
			this.id = null;
		} else {
			this.id = Long.valueOf(id);
		}

	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
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
	 * @return the debePrerregistrarse
	 */
	public boolean isDebePrerregistrarse() {
		return debePrerregistrarse;
	}

	/**
	 * @param debePrerregistrarse
	 *            the debePrerregistrarse to set
	 */
	public void setDebePrerregistrarse(final boolean debePrerregistrarse) {
		this.debePrerregistrarse = debePrerregistrarse;
	}

	/**
	 * @return the scriptPrerrigistro
	 */
	public Script getScriptPrerrigistro() {
		return scriptPrerrigistro;
	}

	/**
	 * @param scriptPrerrigistro
	 *            the scriptPrerrigistro to set
	 */
	public void setScriptPrerrigistro(final Script scriptPrerrigistro) {
		this.scriptPrerrigistro = scriptPrerrigistro;
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
	public TypeInterno getTipoFormulario() {
		return tipoFormulario;
	}

	/**
	 * @param tipoFormulario
	 *            the tipoFormulario to set
	 */
	public void setTipoFormulario(final TypeInterno tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	/**
	 * @return the formularioGestorInterno
	 */
	public Gestor getFormularioGestorInterno() {
		return formularioGestorInterno;
	}

	/**
	 * @param formularioGestorInterno
	 *            the formularioGestorInterno to set
	 */
	public void setFormularioGestorInterno(final Gestor formularioGestorInterno) {
		this.formularioGestorInterno = formularioGestorInterno;
	}

	/**
	 * @return the formularioGestorExterno
	 */
	public Gestor getFormularioGestorExterno() {
		return formularioGestorExterno;
	}

	/**
	 * @param formularioGestorExterno
	 *            the formularioGestorExterno to set
	 */
	public void setFormularioGestorExterno(final Gestor formularioGestorExterno) {
		this.formularioGestorExterno = formularioGestorExterno;
	}

}
