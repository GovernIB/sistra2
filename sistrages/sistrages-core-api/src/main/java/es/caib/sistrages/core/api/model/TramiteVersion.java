package es.caib.sistrages.core.api.model;

import java.util.Date;
import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeFlujo;

/**
 * The Class TramiteVersion.
 */
@SuppressWarnings("serial")
public class TramiteVersion extends ModelApi {

	/**
	 * id.
	 */
	private Long id;

	/**
	 * id tramite.
	 */
	private Long idTramite;

	/**
	 * numero version.
	 */
	private int numeroVersion;

	/**
	 * tipo flujo.
	 */
	private TypeFlujo tipoFlujo;

	/**
	 * descripcion.
	 */
	private Traducciones descripcion;

	/**
	 * autenticado.
	 */
	private boolean autenticado;

	/**
	 * nivel QAA.
	 */
	private int nivelQAA;

	/**
	 * idiomas soportados.
	 */
	private String idiomasSoportados;

	/**
	 * persistencia.
	 */
	private boolean persistencia;

	/**
	 * persistencia infinita.
	 */
	private boolean persistenciaInfinita;

	/**
	 * persistencia dias.
	 */
	private int persistenciaDias;

	/**
	 * id script personalizacion.
	 */
	private Long idScriptPersonalizacion;

	/**
	 * id script inicializacion tramite.
	 */
	private Long idScriptInicializacionTramite;

	/**
	 * bloqueada.
	 */
	private int bloqueada;

	/**
	 * codigo usuario bloqueo.
	 */
	private String codigoUsuarioBloqueo;

	/**
	 * datos usuario bloqueo.
	 */
	private String datosUsuarioBloqueo;

	/**
	 * release.
	 */
	private int release;

	/**
	 * activa.
	 */
	private boolean activa;

	/**
	 * debug.
	 */
	private boolean debug;

	/**
	 * limite tramitacion.
	 */
	private boolean limiteTramitacion;

	/**
	 * num limite tramitacion.
	 */
	private int numLimiteTramitacion;

	/**
	 * int limite tramitacion.
	 */
	private int intLimiteTramitacion;

	/**
	 * desactivacion.
	 */
	private boolean desactivacion;

	/**
	 * plazo inicio desactivacion.
	 */
	private Date plazoInicioDesactivacion;

	/**
	 * plazo fin desactivacion.
	 */
	private Date plazoFinDesactivacion;

	/**
	 * mensaje desactivacion.
	 */
	private Traducciones mensajeDesactivacion;

	/**
	 * Lista de dominios.
	 */
	private List<Dominio> listaDominios;

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
	 * Obtiene el valor de numeroVersion.
	 *
	 * @return el valor de numeroVersion
	 */
	public int getNumeroVersion() {
		return numeroVersion;
	}

	/**
	 * Establece el valor de numeroVersion.
	 *
	 * @param numeroversion
	 *            el nuevo valor de numeroVersion
	 */
	public void setNumeroVersion(final int numeroversion) {
		this.numeroVersion = numeroversion;
	}

	/**
	 * Obtiene el valor de tipoFlujo.
	 *
	 * @return el valor de tipoFlujo
	 */
	public TypeFlujo getTipoFlujo() {
		return tipoFlujo;
	}

	/**
	 * Establece el valor de tipoFlujo.
	 *
	 * @param flujo
	 *            el nuevo valor de tipoFlujo
	 */
	public void setTipoFlujo(final TypeFlujo flujo) {
		this.tipoFlujo = flujo;
	}

	/**
	 * Verifica si es activa.
	 *
	 * @return true, si es activa
	 */
	public boolean isActiva() {
		return activa;
	}

	/**
	 * Establece el valor de activa.
	 *
	 * @param activa
	 *            el nuevo valor de activa
	 */
	public void setActiva(final boolean activa) {
		this.activa = activa;
	}

	/**
	 * Obtiene el valor de release.
	 *
	 * @return el valor de release
	 */
	public int getRelease() {
		return release;
	}

	/**
	 * Establece el valor de release.
	 *
	 * @param release
	 *            el nuevo valor de release
	 */
	public void setRelease(final int release) {
		this.release = release;
	}

	/**
	 * Obtiene el valor de bloqueada.
	 *
	 * @return el valor de bloqueada
	 */
	public int getBloqueada() {
		return bloqueada;
	}

	/**
	 * Establece el valor de bloqueada.
	 *
	 * @param bloqueada
	 *            el nuevo valor de bloqueada
	 */
	public void setBloqueada(final int bloqueada) {
		this.bloqueada = bloqueada;
	}

	/**
	 * Obtiene el valor de idTramite.
	 *
	 * @return el valor de idTramite
	 */
	public Long getIdTramite() {
		return idTramite;
	}

	/**
	 * Establece el valor de idTramite.
	 *
	 * @param codigotramite
	 *            el nuevo valor de idTramite
	 */
	public void setIdTramite(final Long codigotramite) {
		this.idTramite = codigotramite;
	}

	/**
	 * Verifica si es autenticado.
	 *
	 * @return true, si es autenticado
	 */
	public boolean isAutenticado() {
		return autenticado;
	}

	/**
	 * Establece el valor de autenticado.
	 *
	 * @param autenticado
	 *            el nuevo valor de autenticado
	 */
	public void setAutenticado(final boolean autenticado) {
		this.autenticado = autenticado;
	}

	/**
	 * Obtiene el valor de nivelQAA.
	 *
	 * @return el valor de nivelQAA
	 */
	public int getNivelQAA() {
		return nivelQAA;
	}

	/**
	 * Establece el valor de nivelQAA.
	 *
	 * @param nivelQAA
	 *            el nuevo valor de nivelQAA
	 */
	public void setNivelQAA(final int nivelQAA) {
		this.nivelQAA = nivelQAA;
	}

	/**
	 * Obtiene el valor de idiomasSoportados.
	 *
	 * @return el valor de idiomasSoportados
	 */
	public String getIdiomasSoportados() {
		return idiomasSoportados;
	}

	/**
	 * Establece el valor de idiomasSoportados.
	 *
	 * @param idiomassoportados
	 *            el nuevo valor de idiomasSoportados
	 */
	public void setIdiomasSoportados(final String idiomassoportados) {
		this.idiomasSoportados = idiomassoportados;
	}

	/**
	 * Verifica si es persistencia.
	 *
	 * @return true, si es persistencia
	 */
	public boolean isPersistencia() {
		return persistencia;
	}

	/**
	 * Establece el valor de persistencia.
	 *
	 * @param persistencia
	 *            el nuevo valor de persistencia
	 */
	public void setPersistencia(final boolean persistencia) {
		this.persistencia = persistencia;
	}

	/**
	 * Verifica si es persistencia infinita.
	 *
	 * @return true, si es persistencia infinita
	 */
	public boolean isPersistenciaInfinita() {
		return persistenciaInfinita;
	}

	/**
	 * Establece el valor de persistenciaInfinita.
	 *
	 * @param persistenciaInfinita
	 *            el nuevo valor de persistenciaInfinita
	 */
	public void setPersistenciaInfinita(final boolean persistenciaInfinita) {
		this.persistenciaInfinita = persistenciaInfinita;
	}

	/**
	 * Obtiene el valor de persistenciaDias.
	 *
	 * @return el valor de persistenciaDias
	 */
	public int getPersistenciaDias() {
		return persistenciaDias;
	}

	/**
	 * Establece el valor de persistenciaDias.
	 *
	 * @param persistenciaDias
	 *            el nuevo valor de persistenciaDias
	 */
	public void setPersistenciaDias(final int persistenciaDias) {
		this.persistenciaDias = persistenciaDias;
	}

	/**
	 * Obtiene el valor de idScriptPersonalizacion.
	 *
	 * @return el valor de idScriptPersonalizacion
	 */
	public Long getIdScriptPersonalizacion() {
		return idScriptPersonalizacion;
	}

	/**
	 * Establece el valor de idScriptPersonalizacion.
	 *
	 * @param idScriptPersonalizacion
	 *            el nuevo valor de idScriptPersonalizacion
	 */
	public void setIdScriptPersonalizacion(final Long idScriptPersonalizacion) {
		this.idScriptPersonalizacion = idScriptPersonalizacion;
	}

	/**
	 * Obtiene el valor de idScriptInicializacionTramite.
	 *
	 * @return el valor de idScriptInicializacionTramite
	 */
	public Long getIdScriptInicializacionTramite() {
		return idScriptInicializacionTramite;
	}

	/**
	 * Establece el valor de idScriptInicializacionTramite.
	 *
	 * @param idScriptInicializacionTrámite
	 *            el nuevo valor de idScriptInicializacionTramite
	 */
	public void setIdScriptInicializacionTramite(final Long idScriptInicializacionTrámite) {
		this.idScriptInicializacionTramite = idScriptInicializacionTrámite;
	}

	/**
	 * Obtiene el valor de codigoUsuarioBloqueo.
	 *
	 * @return el valor de codigoUsuarioBloqueo
	 */
	public String getCodigoUsuarioBloqueo() {
		return codigoUsuarioBloqueo;
	}

	/**
	 * Establece el valor de codigoUsuarioBloqueo.
	 *
	 * @param idUsuarioBloqueo
	 *            el nuevo valor de codigoUsuarioBloqueo
	 */
	public void setCodigoUsuarioBloqueo(final String idUsuarioBloqueo) {
		this.codigoUsuarioBloqueo = idUsuarioBloqueo;
	}

	/**
	 * Obtiene el valor de datosUsuarioBloqueo.
	 *
	 * @return el valor de datosUsuarioBloqueo
	 */
	public String getDatosUsuarioBloqueo() {
		return datosUsuarioBloqueo;
	}

	/**
	 * Establece el valor de datosUsuarioBloqueo.
	 *
	 * @param datosUsuarioBloqueo
	 *            el nuevo valor de datosUsuarioBloqueo
	 */
	public void setDatosUsuarioBloqueo(final String datosUsuarioBloqueo) {
		this.datosUsuarioBloqueo = datosUsuarioBloqueo;
	}

	/**
	 * Verifica si es debug.
	 *
	 * @return true, si es debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * Establece el valor de debug.
	 *
	 * @param debug
	 *            el nuevo valor de debug
	 */
	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	/**
	 * Verifica si es limite tramitacion.
	 *
	 * @return true, si es limite tramitacion
	 */
	public boolean isLimiteTramitacion() {
		return limiteTramitacion;
	}

	/**
	 * Establece el valor de limiteTramitacion.
	 *
	 * @param limiteTramitacion
	 *            el nuevo valor de limiteTramitacion
	 */
	public void setLimiteTramitacion(final boolean limiteTramitacion) {
		this.limiteTramitacion = limiteTramitacion;
	}

	/**
	 * Obtiene el valor de numLimiteTramitacion.
	 *
	 * @return el valor de numLimiteTramitacion
	 */
	public int getNumLimiteTramitacion() {
		return numLimiteTramitacion;
	}

	/**
	 * Establece el valor de numLimiteTramitacion.
	 *
	 * @param numLimiteTramitacion
	 *            el nuevo valor de numLimiteTramitacion
	 */
	public void setNumLimiteTramitacion(final int numLimiteTramitacion) {
		this.numLimiteTramitacion = numLimiteTramitacion;
	}

	/**
	 * Obtiene el valor de intLimiteTramitacion.
	 *
	 * @return el valor de intLimiteTramitacion
	 */
	public int getIntLimiteTramitacion() {
		return intLimiteTramitacion;
	}

	/**
	 * Establece el valor de intLimiteTramitacion.
	 *
	 * @param intLimiteTramitacion
	 *            el nuevo valor de intLimiteTramitacion
	 */
	public void setIntLimiteTramitacion(final int intLimiteTramitacion) {
		this.intLimiteTramitacion = intLimiteTramitacion;
	}

	/**
	 * Verifica si es desactivacion.
	 *
	 * @return true, si es desactivacion
	 */
	public boolean isDesactivacion() {
		return desactivacion;
	}

	/**
	 * Establece el valor de desactivacion.
	 *
	 * @param desactivación
	 *            el nuevo valor de desactivacion
	 */
	public void setDesactivacion(final boolean desactivación) {
		this.desactivacion = desactivación;
	}

	/**
	 * Obtiene el valor de plazoInicioDesactivacion.
	 *
	 * @return el valor de plazoInicioDesactivacion
	 */
	public Date getPlazoInicioDesactivacion() {
		return (Date) plazoInicioDesactivacion.clone();
	}

	/**
	 * Establece el valor de plazoInicioDesactivacion.
	 *
	 * @param plazoInicioDesactivacion
	 *            el nuevo valor de plazoInicioDesactivacion
	 */
	public void setPlazoInicioDesactivacion(final Date plazoInicioDesactivacion) {
		this.plazoInicioDesactivacion = (Date) plazoInicioDesactivacion.clone();
	}

	/**
	 * Obtiene el valor de plazoFinDesactivacion.
	 *
	 * @return el valor de plazoFinDesactivacion
	 */
	public Date getPlazoFinDesactivacion() {
		return (Date) plazoFinDesactivacion.clone();
	}

	/**
	 * Establece el valor de plazoFinDesactivacion.
	 *
	 * @param plazoFinDesactivacion
	 *            el nuevo valor de plazoFinDesactivacion
	 */
	public void setPlazoFinDesactivacion(final Date plazoFinDesactivacion) {
		this.plazoFinDesactivacion = (Date) plazoFinDesactivacion.clone();
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public Traducciones getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final Traducciones descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de mensajeDesactivacion.
	 *
	 * @return el valor de mensajeDesactivacion
	 */
	public Traducciones getMensajeDesactivacion() {
		return mensajeDesactivacion;
	}

	/**
	 * Establece el valor de mensajeDesactivacion.
	 *
	 * @param mensajeDesactivacion
	 *            el nuevo valor de mensajeDesactivacion
	 */
	public void setMensajeDesactivacion(final Traducciones mensajeDesactivacion) {
		this.mensajeDesactivacion = mensajeDesactivacion;
	}

	/**
	 * Obtiene el valor de listaDominios.
	 *
	 * @return el valor de listaDominios
	 */
	public List<Dominio> getListaDominios() {
		return listaDominios;
	}

	/**
	 * Establece el valor de listaDominios.
	 *
	 * @param listaDominios
	 *            el nuevo valor de listaDominios
	 */
	public void setListaDominios(final List<Dominio> listaDominios) {
		this.listaDominios = listaDominios;
	}

}
