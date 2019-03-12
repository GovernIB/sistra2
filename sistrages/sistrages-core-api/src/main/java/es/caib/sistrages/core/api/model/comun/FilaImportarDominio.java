package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;

/**
 * Fila dominio importar.
 *
 * @author Indra
 *
 */
public class FilaImportarDominio extends FilaImportar {

	/** Dominio. **/
	private Dominio dominio;

	/** Dominio. **/
	private Dominio dominioActual;

	/** Indica si es visibleBoton o no el boton. **/
	private boolean visibleBoton;

	/** Indica si el dominio y el dominio actual son del mismo tipo. */
	private boolean mismoTipo;

	/** Altura. **/
	private Integer altura = 330;

	/** Altura. **/
	private Integer anchura = 850;

	/** Resultado jdni. */
	private String resultadoJndi;

	/** Resultado SQL . */
	private String resultadoSQL;

	/** Resultado SQL decoded. */
	private String resultadoSQLdecoded;

	/** Resultado URL. */
	private String resultadoURL;

	/** Resultado Lista. **/
	private String resultadoLista;

	/** Resultado Fuente de datos. **/
	private String resultadoFD;

	/** Resultado distinto. **/
	private String resultadoDistinto;

	/** Fuente datos. **/
	private FuenteDatos fuenteDatos;

	/** Fuente datos actual. **/
	private FuenteDatos fuenteDatosActual;

	/** Fuente Datos content. */
	private byte[] fuenteDatosContent;

	/** Permite editar. **/
	private boolean permisosEdicion;

	/** En caso de dominio de tipo area, el idArea. **/
	private Long idArea;

	/** En caso de dominio de tipo entidad, el idEntidad. **/
	private Long idEntidad;

	/**
	 * Constructor basico.
	 */
	public FilaImportarDominio() {
		super();
	}

	/**
	 * @return the altura
	 */
	public Integer getAltura() {
		return altura;
	}

	/**
	 * @param altura
	 *            the altura to set
	 */
	public void setAltura(final Integer altura) {
		this.altura = altura;
	}

	/**
	 * @return the anchura
	 */
	public Integer getAnchura() {
		return anchura;
	}

	/**
	 * @param anchura
	 *            the anchura to set
	 */
	public void setAnchura(final Integer anchura) {
		this.anchura = anchura;
	}

	/**
	 * @return the resultadoJndi
	 */
	public String getResultadoJndi() {
		return resultadoJndi;
	}

	/**
	 * @param resultadoJndi
	 *            the resultadoJndi to set
	 */
	public void setResultadoJndi(final String resultadoJdni) {
		this.resultadoJndi = resultadoJdni;
	}

	/**
	 * @return the resultadoSQL
	 */
	public String getResultadoSQL() {
		return resultadoSQL;
	}

	/**
	 * @param resultadoSQL
	 *            the resultadoSQL to set
	 */
	public void setResultadoSQL(final String resultadoSQL) {
		this.resultadoSQL = resultadoSQL;
	}

	/**
	 * @return the resultadoURL
	 */
	public String getResultadoURL() {
		return resultadoURL;
	}

	/**
	 * @param resultadoURL
	 *            the resultadoURL to set
	 */
	public void setResultadoURL(final String resultadoURL) {
		this.resultadoURL = resultadoURL;
	}

	/**
	 * @return the resultadoLista
	 */
	public String getResultadoLista() {
		return resultadoLista;
	}

	/**
	 * @param resultadoLista
	 *            the resultadoLista to set
	 */
	public void setResultadoLista(final String resultadoLista) {
		this.resultadoLista = resultadoLista;
	}

	/**
	 * @return the resultadoFD
	 */
	public String getResultadoFD() {
		return resultadoFD;
	}

	/**
	 * @param resultadoFD
	 *            the resultadoFD to set
	 */
	public void setResultadoFD(final String resultadoFD) {
		this.resultadoFD = resultadoFD;
	}

	/**
	 * @return the resultadoDistinto
	 */
	public String getResultadoDistinto() {
		return resultadoDistinto;
	}

	/**
	 * @param resultadoDistinto
	 *            the resultadoDistinto to set
	 */
	public void setResultadoDistinto(final String resultadoDistinto) {
		this.resultadoDistinto = resultadoDistinto;
	}

	/**
	 * @return the fuenteDatos
	 */
	public FuenteDatos getFuenteDatos() {
		return fuenteDatos;
	}

	/**
	 * @param fuenteDatos
	 *            the fuenteDatos to set
	 */
	public void setFuenteDatos(final FuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	/**
	 * @return the fuenteDatosContent
	 */
	public byte[] getFuenteDatosContent() {
		return fuenteDatosContent;
	}

	/**
	 * @param fuenteDatosContent
	 *            the fuenteDatosContent to set
	 */
	public void setFuenteDatosContent(final byte[] fuenteDatosContent) {
		this.fuenteDatosContent = fuenteDatosContent;
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
	 * @return the visibleBoton
	 */
	public boolean isVisibleBoton() {
		return visibleBoton;
	}

	/**
	 * @return the visibleBoton
	 */
	public boolean getVisibleBoton() {
		return isVisibleBoton();
	}

	/**
	 * @param visibleBoton
	 *            the visibleBoton to set
	 */
	public void setVisibleBoton(final boolean visibleBoton) {
		this.visibleBoton = visibleBoton;
	}

	/**
	 * @return the dominioActual
	 */
	public Dominio getDominioActual() {
		return dominioActual;
	}

	/**
	 * @param dominioActual
	 *            the dominioActual to set
	 */
	public void setDominioActual(final Dominio dominioActual) {
		this.dominioActual = dominioActual;
	}

	/**
	 * @return the mismoTipo
	 */
	public boolean isMismoTipo() {
		return mismoTipo;
	}

	/**
	 * @return the mismoTipo
	 */
	public boolean getMismoTipo() {
		return isMismoTipo();
	}

	/**
	 * @param mismoTipo
	 *            the mismoTipo to set
	 */
	public void setMismoTipo(final boolean mismoTipo) {
		this.mismoTipo = mismoTipo;
	}

	/**
	 * @return the fuenteDatosActual
	 */
	public FuenteDatos getFuenteDatosActual() {
		return fuenteDatosActual;
	}

	/**
	 * @param fuenteDatosActual
	 *            the fuenteDatosActual to set
	 */
	public void setFuenteDatosActual(final FuenteDatos fuenteDatosActual) {
		this.fuenteDatosActual = fuenteDatosActual;
	}

	/**
	 * @return the idArea
	 */
	public Long getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(final Long idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the idEntidad
	 */
	public Long getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param idEntidad
	 *            the idEntidad to set
	 */
	public void setIdEntidad(final Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	/**
	 * @return the resultadoSQLdecoded
	 */
	public String getResultadoSQLdecoded() {
		return resultadoSQLdecoded;
	}

	/**
	 * @param resultadoSQLdecoded
	 *            the resultadoSQLdecoded to set
	 */
	public void setResultadoSQLdecoded(final String resultadoSQLdecoded) {
		this.resultadoSQLdecoded = resultadoSQLdecoded;
	}

	/**
	 * Is permisos edicion.
	 *
	 * @return
	 */
	public boolean isPermisosEdicion() {
		return permisosEdicion;
	}

	/**
	 * Get permisos edicion.
	 *
	 * @param permisosEdicion
	 */
	public void setPermisosEdicion(final boolean permisosEdicion) {
		this.permisosEdicion = permisosEdicion;
	}

}
