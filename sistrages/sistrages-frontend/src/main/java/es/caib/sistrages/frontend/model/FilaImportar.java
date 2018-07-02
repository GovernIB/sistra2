package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;

/**
 * Fila dominio importar.
 *
 * @author slromero
 *
 */
public class FilaImportar {

	/** Dominio. **/
	private Dominio dominio;

	/** FormateadorFormulario . **/
	private FormateadorFormulario formateadorFormulario;

	/** Accion. **/
	private TypeImportarAccion accion;

	/** Estado. **/
	private TypeImportarEstado estado;

	/** TypeAccion. **/
	private TypeImportarResultado resultado;

	/** Area. **/
	private Area area;

	/** Area actual. **/
	private Area areaActual;

	/** Indica el valor de la descripción del area. **/
	private String areaResultado;

	/** Tramite. **/
	private Tramite tramite;

	/** Tramite actual. **/
	private Tramite tramiteActual;

	/** Indica el valor de la descripción del tramite. **/
	private String tramiteResultado;

	/** TramiteVersion. **/
	private TramiteVersion tramiteVersion;

	/** TramiteVersion actual. **/
	private TramiteVersion tramiteVersionActual;

	/** Indica el resultado de oficina. **/
	private String tramiteVersionResultadoOficina;

	/** Indica el resultado de libro. **/
	private String tramiteVersionResultadoLibro;

	/** Indica el resultado de tipo. **/
	private String tramiteVersionResultadoTipo;

	/**
	 * Constructor basico.
	 */
	public FilaImportar() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportar(final Dominio iDominio, final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion) {
		this.dominio = iDominio;
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
	}

	/**
	 * Constructor.
	 */
	public FilaImportar(final FormateadorFormulario iFormateador, final TypeImportarAccion iAccion,
			final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion) {
		this.setFormateadorFormulario(iFormateador);
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
	}

	/**
	 * Constructor.
	 */
	public FilaImportar(final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion, final Area area, final Area areaActual) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.area = area;
		this.areaActual = areaActual;
		this.areaResultado = area.getDescripcion();
	}

	/**
	 * Constructor.
	 */
	public FilaImportar(final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion, final Tramite tramite, final Tramite tramiteActual) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.tramite = tramite;
		this.tramiteActual = tramiteActual;
		this.tramiteResultado = tramite.getDescripcion();
	}

	/**
	 * Constructor.
	 */
	public FilaImportar(final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion, final TramiteVersion tramiteVersion,
			final TramiteVersion tramiteVersionActual) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.tramiteVersion = tramiteVersion;
		this.tramiteVersionActual = tramiteVersionActual;
		this.tramiteVersionResultadoLibro = tramiteVersion.getPasoRegistrar().getCodigoLibroRegistro();
		this.tramiteVersionResultadoOficina = tramiteVersion.getPasoRegistrar().getCodigoOficinaRegistro();
		this.tramiteVersionResultadoTipo = tramiteVersion.getPasoRegistrar().getCodigoTipoAsunto();
	}

	/**
	 * @return the dominio
	 */
	public Dominio getDominio() {
		return dominio;
	}

	/**
	 * @param dominio
	 *            the dominio to set
	 */
	public void setDominio(final Dominio dominio) {
		this.dominio = dominio;
	}

	/**
	 * @return the formateadorFormulario
	 */
	public FormateadorFormulario getFormateadorFormulario() {
		return formateadorFormulario;
	}

	/**
	 * @param formateadorFormulario
	 *            the formateadorFormulario to set
	 */
	public void setFormateadorFormulario(final FormateadorFormulario formateadorFormulario) {
		this.formateadorFormulario = formateadorFormulario;
	}

	/**
	 * @return the accion
	 */
	public TypeImportarAccion getAccion() {
		return accion;
	}

	/**
	 * @param accion
	 *            the accion to set
	 */
	public void setAccion(final TypeImportarAccion accion) {
		this.accion = accion;
	}

	/**
	 * @return the estado
	 */
	public TypeImportarEstado getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(final TypeImportarEstado estado) {
		this.estado = estado;
	}

	/**
	 * @return the resultado
	 */
	public TypeImportarResultado getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(final TypeImportarResultado typeAccion) {
		this.resultado = typeAccion;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * @return the areaActual
	 */
	public Area getAreaActual() {
		return areaActual;
	}

	/**
	 * @param areaActual
	 *            the areaActual to set
	 */
	public void setAreaActual(final Area areaActual) {
		this.areaActual = areaActual;
	}

	/**
	 * @return the areaResultado
	 */
	public String getAreaResultado() {
		return areaResultado;
	}

	/**
	 * @param areaResultado
	 *            the areaResultado to set
	 */
	public void setAreaResultado(final String areaResultado) {
		this.areaResultado = areaResultado;
	}

	/**
	 * @return the tramite
	 */
	public Tramite getTramite() {
		return tramite;
	}

	/**
	 * @param tramite
	 *            the tramite to set
	 */
	public void setTramite(final Tramite tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the tramiteActual
	 */
	public Tramite getTramiteActual() {
		return tramiteActual;
	}

	/**
	 * @param tramiteActual
	 *            the tramiteActual to set
	 */
	public void setTramiteActual(final Tramite tramiteActual) {
		this.tramiteActual = tramiteActual;
	}

	/**
	 * @return the tramiteResultado
	 */
	public String getTramiteResultado() {
		return tramiteResultado;
	}

	/**
	 * @param tramiteResultado
	 *            the tramiteResultado to set
	 */
	public void setTramiteResultado(final String tramiteResultado) {
		this.tramiteResultado = tramiteResultado;
	}

	/**
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the tramiteVersionActual
	 */
	public TramiteVersion getTramiteVersionActual() {
		return tramiteVersionActual;
	}

	/**
	 * @param tramiteVersionActual
	 *            the tramiteVersionActual to set
	 */
	public void setTramiteVersionActual(final TramiteVersion tramiteVersionActual) {
		this.tramiteVersionActual = tramiteVersionActual;
	}

	/**
	 * @return the tramiteVersionResultadoOficina
	 */
	public String getTramiteVersionResultadoOficina() {
		return tramiteVersionResultadoOficina;
	}

	/**
	 * @param tramiteVersionResultadoOficina
	 *            the tramiteVersionResultadoOficina to set
	 */
	public void setTramiteVersionResultadoOficina(final String tramiteVersionResultadoOficina) {
		this.tramiteVersionResultadoOficina = tramiteVersionResultadoOficina;
	}

	/**
	 * @return the tramiteVersionResultadoLibro
	 */
	public String getTramiteVersionResultadoLibro() {
		return tramiteVersionResultadoLibro;
	}

	/**
	 * @param tramiteVersionResultadoLibro
	 *            the tramiteVersionResultadoLibro to set
	 */
	public void setTramiteVersionResultadoLibro(final String tramiteVersionResultadoLibro) {
		this.tramiteVersionResultadoLibro = tramiteVersionResultadoLibro;
	}

	/**
	 * @return the tramiteVersionResultadoTipo
	 */
	public String getTramiteVersionResultadoTipo() {
		return tramiteVersionResultadoTipo;
	}

	/**
	 * @param tramiteVersionResultadoTipo
	 *            the tramiteVersionResultadoTipo to set
	 */
	public void setTramiteVersionResultadoTipo(final String tramiteVersionResultadoTipo) {
		this.tramiteVersionResultadoTipo = tramiteVersionResultadoTipo;
	}
}
