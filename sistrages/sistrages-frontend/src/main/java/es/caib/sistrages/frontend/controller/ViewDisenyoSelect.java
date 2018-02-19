package es.caib.sistrages.frontend.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Disenyo.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDisenyoSelect extends ViewDisenyoComponentUI {

	/** Identificador del elemento. **/
	private String identificador;

	/** Columnas. **/
	private int columnas;

	/** Obligatorio. **/
	private boolean obligatorio;

	/** Solo lectura. **/
	private boolean soloLectura;

	/** Ayuda online. **/
	private String ayudaOnline;

	/** Tipo. **/
	private String tipo;

	/** Ordenar alfabéticamente. */
	private boolean indexAlfabetico;

	/** Tipo de valores. **/
	private String tipoValores;

	/** Dominio. **/
	private String dominio;

	/** Multilinea. **/
	private String dominioCampo;

	/** Estilos. **/
	private String dominioDescripcion;

	/**
	 * Crea una nueva instancia de view definicion version.
	 */
	public ViewDisenyoSelect() {
		super();
	}

	/**
	 * Inicializacion.
	 */
	@PostConstruct
	public void init() {
		this.identificador = javax.faces.context.FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("form:nodeId");

	}

	/**
	 * Sin implementar
	 */
	public void sinImplementar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
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
	 * @return the columnas
	 */
	public int getColumnas() {
		return columnas;
	}

	/**
	 * @param columnas
	 *            the columnas to set
	 */
	public void setColumnas(final int columnas) {
		this.columnas = columnas;
	}

	/**
	 * @return the obligatorio
	 */
	public boolean isObligatorio() {
		return obligatorio;
	}

	/**
	 * @param obligatorio
	 *            the obligatorio to set
	 */
	public void setObligatorio(final boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * @return the soloLectura
	 */
	public boolean isSoloLectura() {
		return soloLectura;
	}

	/**
	 * @param soloLectura
	 *            the soloLectura to set
	 */
	public void setSoloLectura(final boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	/**
	 * @return the ayudaOnline
	 */
	public String getAyudaOnline() {
		return ayudaOnline;
	}

	/**
	 * @param ayudaOnline
	 *            the ayudaOnline to set
	 */
	public void setAyudaOnline(final String ayudaOnline) {
		this.ayudaOnline = ayudaOnline;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the indexAlfabetico
	 */
	public boolean isIndexAlfabetico() {
		return indexAlfabetico;
	}

	/**
	 * @param indexAlfabetico
	 *            the indexAlfabetico to set
	 */
	public void setIndexAlfabetico(final boolean indexAlfabetico) {
		this.indexAlfabetico = indexAlfabetico;
	}

	/**
	 * @return the tipoValores
	 */
	public String getTipoValores() {
		return tipoValores;
	}

	/**
	 * @param tipoValores
	 *            the tipoValores to set
	 */
	public void setTipoValores(final String tipoValores) {
		this.tipoValores = tipoValores;
	}

	/**
	 * @return the dominio
	 */
	public String getDominio() {
		return dominio;
	}

	/**
	 * @param dominio
	 *            the dominio to set
	 */
	public void setDominio(final String dominio) {
		this.dominio = dominio;
	}

	/**
	 * @return the dominioCampo
	 */
	public String getDominioCampo() {
		return dominioCampo;
	}

	/**
	 * @param dominioCampo
	 *            the dominioCampo to set
	 */
	public void setDominioCampo(final String dominioCampo) {
		this.dominioCampo = dominioCampo;
	}

	/**
	 * @return the dominioDescripcion
	 */
	public String getDominioDescripcion() {
		return dominioDescripcion;
	}

	/**
	 * @param dominioDescripcion
	 *            the dominioDescripcion to set
	 */
	public void setDominioDescripcion(final String dominioDescripcion) {
		this.dominioDescripcion = dominioDescripcion;
	}
}
