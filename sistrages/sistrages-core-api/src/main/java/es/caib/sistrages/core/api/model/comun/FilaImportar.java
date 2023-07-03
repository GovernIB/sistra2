package es.caib.sistrages.core.api.model.comun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Fichero;

/**
 * Fila importar.
 *
 * @author Indra
 *
 */
public class FilaImportar {

	/** Fila entidad. **/
	private FilaImportarEntidad filaEntidad;

	/** Fila area. **/
	private FilaImportarArea filaArea;

	/** Fila tramite. **/
	private FilaImportarTramite filaTramite;

	/** Fila tramite version. **/
	private FilaImportarTramiteVersion filaTramiteVersion;

	/** Fila tramite registro. **/
	private FilaImportarTramiteRegistro filaTramiteRegistro;

	/** Fila dominios. **/
	private List<FilaImportarDominio> filaDominios;

	/** Fila formateadores. **/
	private List<FilaImportarFormateador> filaFormateador;

	/** Fila gestor **/
	private List<FilaImportarGestor> filaGestor;

	/** Fila gestor **/
	private List<FilaImportarSeccion> filaSecciones;

	/** Formularios internos. **/
	private Map<Long, DisenyoFormulario> formularios = new HashMap<>();

	/** Ficheros. **/
	private Map<Long, Fichero> ficheros = new HashMap<>();

	/** Ficheros content. **/
	private Map<Long, byte[]> ficherosContent = new HashMap<>();

	/** Disenyos Lista Elementos **/
	private Map<Long, DisenyoFormulario> disenyosLE = new HashMap<>();

	/** Usuario . **/
	private String usuario;

	/** Id entidad. **/
	private Long idEntidad;

	/** Modo. **/
	private String modo;

	/**
	 * @return the filaEntidad
	 */
	public final FilaImportarEntidad getFilaEntidad() {
		return filaEntidad;
	}

	/**
	 * @param filaEntidad the filaEntidad to set
	 */
	public final void setFilaEntidad(final FilaImportarEntidad filaEntidad) {
		this.filaEntidad = filaEntidad;
	}

	/**
	 * @return the filaArea
	 */
	public final FilaImportarArea getFilaArea() {
		return filaArea;
	}

	/**
	 * @param filaArea the filaArea to set
	 */
	public final void setFilaArea(final FilaImportarArea filaArea) {
		this.filaArea = filaArea;
	}

	/**
	 * @return the filaTramite
	 */
	public final FilaImportarTramite getFilaTramite() {
		return filaTramite;
	}

	/**
	 * @param filaTramite the filaTramite to set
	 */
	public final void setFilaTramite(final FilaImportarTramite filaTramite) {
		this.filaTramite = filaTramite;
	}

	/**
	 * @return the filaTramiteVersion
	 */
	public final FilaImportarTramiteVersion getFilaTramiteVersion() {
		return filaTramiteVersion;
	}

	/**
	 * @param filaTramiteVersion the filaTramiteVersion to set
	 */
	public final void setFilaTramiteVersion(final FilaImportarTramiteVersion filaTramiteVersion) {
		this.filaTramiteVersion = filaTramiteVersion;
	}

	/**
	 * @return the filaTramiteRegistro
	 */
	public final FilaImportarTramiteRegistro getFilaTramiteRegistro() {
		return filaTramiteRegistro;
	}

	/**
	 * @param filaTramiteRegistro the filaTramiteRegistro to set
	 */
	public final void setFilaTramiteRegistro(final FilaImportarTramiteRegistro filaTramiteRegistro) {
		this.filaTramiteRegistro = filaTramiteRegistro;
	}

	/**
	 * @return the filaDominios
	 */
	public final List<FilaImportarDominio> getFilaDominios() {
		return filaDominios;
	}

	/**
	 * @param filaDominios the filaDominios to set
	 */
	public final void setFilaDominios(final List<FilaImportarDominio> filaDominios) {
		this.filaDominios = filaDominios;
	}

	/**
	 * @return the filaFormateador
	 */
	public final List<FilaImportarFormateador> getFilaFormateador() {
		return filaFormateador;
	}

	/**
	 * @param filaFormateador the filaFormateador to set
	 */
	public final void setFilaFormateador(final List<FilaImportarFormateador> filaFormateador) {
		this.filaFormateador = filaFormateador;
	}

	/**
	 * @return the usuario
	 */
	public final String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public final void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the idEntidad
	 */
	public final Long getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param idEntidad the idEntidad to set
	 */
	public final void setIdEntidad(final Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	/**
	 * @return the formularios
	 */
	public final Map<Long, DisenyoFormulario> getFormularios() {
		return formularios;
	}

	/**
	 * @return the ficheros
	 */
	public final Map<Long, Fichero> getFicheros() {
		return ficheros;
	}

	/**
	 * @return the ficherosContent
	 */
	public final Map<Long, byte[]> getFicherosContent() {
		return ficherosContent;
	}

	/**
	 * @param formularios the formularios to set
	 */
	public final void setFormularios(final Map<Long, DisenyoFormulario> formularios) {
		this.formularios = formularios;
	}

	/**
	 * @param ficheros the ficheros to set
	 */
	public final void setFicheros(final Map<Long, Fichero> ficheros) {
		this.ficheros = ficheros;
	}

	/**
	 * @param ficherosContent the ficherosContent to set
	 */
	public final void setFicherosContent(final Map<Long, byte[]> ficherosContent) {
		this.ficherosContent = ficherosContent;
	}

	/**
	 * @return the filaGestor
	 */
	public List<FilaImportarGestor> getFilaGestor() {
		return filaGestor;
	}

	/**
	 * @param filaGestor the filaGestor to set
	 */
	public void setFilaGestor(List<FilaImportarGestor> filaGestor) {
		this.filaGestor = filaGestor;
	}

	/**
	 * Es modo cuaderno carga
	 *
	 * @return the modo
	 */
	public boolean isModoCC() {
		return modo != null && modo.equals("CC");
	}

	/**
	 * Es modo Importar
	 *
	 * @return the modo
	 */
	public boolean isModoIM() {
		return modo != null && modo.equals("IM");
	}

	/**
	 * @return the modo
	 */
	public String getModo() {
		return modo;
	}

	/**
	 * @param modo the modo to set
	 */
	public void setModo(final String modo) {
		this.modo = modo;
	}

	/**
	 * @return the filaSecciones
	 */
	public List<FilaImportarSeccion> getFilaSecciones() {
		return filaSecciones;
	}

	/**
	 * @param filaSecciones the filaSecciones to set
	 */
	public void setFilaSecciones(List<FilaImportarSeccion> filaSecciones) {
		this.filaSecciones = filaSecciones;
	}

	/**
	 * @return the disenyosLE
	 */
	public Map<Long, DisenyoFormulario> getDisenyosLE() {
		return disenyosLE;
	}

	/**
	 * @param disenyosLE the disenyosLE to set
	 */
	public void setDisenyosLE(Map<Long, DisenyoFormulario> disenyosLE) {
		this.disenyosLE = disenyosLE;
	}


}
