package es.caib.sistrages.frontend.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.ToggleEvent;

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
public class ViewDisenyoTextbox extends ViewDisenyoComponentUI {

	/** Identificador del elemento. **/
	private String identificador;

	/** Etiqueta. **/
	private String etiqueta;

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

	/** Tamanyo maximo. **/
	private String tamanyoMaximo;

	/** Multilinea activo. **/
	private boolean multilineaActivo;

	/** Multilinea. **/
	private String multilinea;

	/** Estilos. **/
	private String estilos;

	/** Identificacion. **/
	private String identificacion;

	/**
	 * Crea una nueva instancia de view definicion version.
	 */
	public ViewDisenyoTextbox() {
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
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta
	 *            the etiqueta to set
	 */
	public void setEtiqueta(final String etiqueta) {
		this.etiqueta = etiqueta;
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
	 * @return the tamanyoMaximo
	 */
	public String getTamanyoMaximo() {
		return tamanyoMaximo;
	}

	/**
	 * @param tamanyoMaximo
	 *            the tamanyoMaximo to set
	 */
	public void setTamanyoMaximo(final String tamanyoMaximo) {
		this.tamanyoMaximo = tamanyoMaximo;
	}

	/**
	 * @return the multilinea
	 */
	public String getMultilinea() {
		return multilinea;
	}

	/**
	 * @param multilinea
	 *            the multilinea to set
	 */
	public void setMultilinea(final String multilinea) {
		this.multilinea = multilinea;
	}

	/**
	 * @return the multilineaActivo
	 */
	public boolean isMultilineaActivo() {
		return multilineaActivo;
	}

	/**
	 * @param multilineaActivo
	 *            the multilineaActivo to set
	 */
	public void setMultilineaActivo(final boolean multilineaActivo) {
		this.multilineaActivo = multilineaActivo;
	}

	/**
	 * @return the estilos
	 */
	public String getEstilos() {
		return estilos;
	}

	/**
	 * @param estilos
	 *            the estilos to set
	 */
	public void setEstilos(final String estilos) {
		this.estilos = estilos;
	}

	private boolean visiblePropiedades = false;
	private boolean visibleScripts = true;
	private boolean visibleEstilos = true;

	@Override
	public void onToggle(final ToggleEvent event) {
		if ("VISIBLE".equals(event.getVisibility().name())) {

			if ("panelPropietats".equals(event.getComponent().getId())) {
				setVisiblePropiedades(false);
				setVisibleScripts(true);
				setVisibleEstilos(true);
			}

			if ("panelScripts".equals(event.getComponent().getId())) {
				setVisiblePropiedades(true);
				setVisibleScripts(false);
				setVisibleEstilos(true);
			}

			if ("panelEstilos".equals(event.getComponent().getId())) {
				setVisiblePropiedades(true);
				setVisibleScripts(true);
				setVisibleEstilos(false);
			}
		}

	}

	/**
	 * @return the visiblePropiedades
	 */
	@Override
	public boolean isVisiblePropiedades() {
		return visiblePropiedades;
	}

	/**
	 * @param visiblePropiedades
	 *            the visiblePropiedades to set
	 */
	@Override
	public void setVisiblePropiedades(final boolean visiblePropiedades) {
		this.visiblePropiedades = visiblePropiedades;
	}

	/**
	 * @return the visibleScripts
	 */
	@Override
	public boolean isVisibleScripts() {
		return visibleScripts;
	}

	/**
	 * @param visibleScripts
	 *            the visibleScripts to set
	 */
	@Override
	public void setVisibleScripts(final boolean visibleScripts) {
		this.visibleScripts = visibleScripts;
	}

	/**
	 * @return the visibleEstilos
	 */
	@Override
	public boolean isVisibleEstilos() {
		return visibleEstilos;
	}

	/**
	 * @param visibleEstilos
	 *            the visibleEstilos to set
	 */
	@Override
	public void setVisibleEstilos(final boolean visibleEstilos) {
		this.visibleEstilos = visibleEstilos;
	}

	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion
	 *            the identificacion to set
	 */
	public void setIdentificacion(final String identificacion) {
		this.identificacion = identificacion;
	}
}
