package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Datos almacenados en base de datos para un trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosPersistenciaTramite implements Serializable {
	/**
	 * Id sesión tramitación.
	 */
	private String idSesionTramitacion;
	/**
	 * Id trámite.
	 */
	private String idTramite;
	/**
	 * Número versión trámite.
	 */
	private int versionTramite;
	/**
	 * Descripción trámite.
	 */
	private String descripcionTramite;
	/**
	 * Codigo de áreas.
	 */
	private String idArea;
	/**
	 * Codigo de trámite Catalogo Procedimientos.
	 */
	private String idTramiteCP;
	/**
	 * Codigo de procedimiento Catalogo Propcedimientos.
	 */
	private String idProcedimientoCP;
	/**
	 * Indica si el procedimiento es un servicio.
	 */
	private boolean servicioCP;
	/**
	 * Codigo de procedimiento SIA.
	 */
	private String idProcedimientoSIA;
	/**
	 * Estado trámite.
	 */
	private TypeEstadoTramite estado;
	/**
	 * Indica nivel de autenticación.
	 */
	private TypeAutenticacion autenticacion;
	/**
	 * Indica metodo de autenticación inicio del tramite.
	 */
	private TypeMetodoAutenticacion metodoAutenticacionInicio;
	/**
	 * Nif iniciador trámite (en caso de autenticado).
	 */
	private String nifIniciador;
	/**
	 * Nombre iniciador trámite (en caso de autenticado).
	 */
	private String nombreIniciador;
	/**
	 * Primer apellido iniciador trámite (en caso de autenticado).
	 */
	private String apellido1Iniciador;
	/**
	 * Segundo apellido iniciador trámite (en caso de autenticado).
	 */
	private String apellido2Iniciador;
	/**
	 * Marca de tiempo de la última vez que se ha cogido el trámite.
	 */
	private Long timestamp;
	/**
	 * Idioma.
	 */
	private String idioma;
	/**
	 * Url inicio del tramite.
	 */
	private String urlInicio;
	/**
	 * Parámetros de inicio.
	 */
	private Map<String, String> parametrosInicio;
	/**
	 * Indica si el trámite es persistente.
	 */
	private boolean persistente;
	/**
	 * Indica si el plazo se ha establecido de forma dinámica.
	 */
	private boolean plazoDinamico;
	/**
	 * Fecha inicio trámite.
	 */
	private Date fechaInicio;
	/**
	 * Fecha último acceso (iniciar / cargar).
	 */
	private Date fechaUltimoAcceso;
	/**
	 * Fecha caducidad.
	 */
	private Date fechaCaducidad;
	/**
	 * Fecha finalización del trámite (registro o cancelación).
	 */
	private Date fechaFin;
	/**
	 * Indica si el trámite se ha cancelado.
	 */
	private boolean cancelado;
	/**
	 * Indica el nif del presentador.
	 */
	private String nifPresentador;
	/**
	 * Indica el nombre del presentador.
	 */
	private String nombreCompletoPresentador;
	/**
	 * Indica si esta marcado para purgar.
	 */
	private boolean marcadoPurgar;
	/**
	 * Indica si esta purgado (solo tiene cabecera del tramite).
	 */
	private boolean purgado;
	/**
	 * En caso de que este purgado indica la fecha de purgado.
	 */
	private Date fechaPurgado;
	/**
	 * Indica si no se ha podido purgar por tener pagos realizados.
	 */
	private boolean purgaPendientePorPagoRealizado;

	/**
	 * Método de acceso a idSesionTramitacion.
	 *
	 * @return idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Método para establecer idSesionTramitacion.
	 *
	 * @param idSesionTramitacion
	 *            idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	/**
	 * Método de acceso a idTramite.
	 *
	 * @return idTramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * Método para establecer idTramite.
	 *
	 * @param idTramite
	 *            idTramite a establecer
	 */
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * Método de acceso a versionTramite.
	 *
	 * @return versionTramite
	 */
	public int getVersionTramite() {
		return versionTramite;
	}

	/**
	 * Método para establecer versionTramite.
	 *
	 * @param versionTramite
	 *            versionTramite a establecer
	 */
	public void setVersionTramite(int versionTramite) {
		this.versionTramite = versionTramite;
	}

	/**
	 * Método de acceso a descripcionTramite.
	 *
	 * @return descripcionTramite
	 */
	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	/**
	 * Método para establecer descripcionTramite.
	 *
	 * @param descripcionTramite
	 *            descripcionTramite a establecer
	 */
	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}

	/**
	 * Método de acceso a idTramiteCP.
	 *
	 * @return idTramiteCP
	 */
	public String getIdTramiteCP() {
		return idTramiteCP;
	}

	/**
	 * Método para establecer idTramiteCP.
	 *
	 * @param idTramiteCP
	 *            idTramiteCP a establecer
	 */
	public void setIdTramiteCP(String idTramiteCP) {
		this.idTramiteCP = idTramiteCP;
	}

	/**
	 * Método de acceso a idProcedimientoCP.
	 *
	 * @return idProcedimientoCP
	 */
	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	/**
	 * Método para establecer idProcedimientoCP.
	 *
	 * @param idProcedimientoCP
	 *            idProcedimientoCP a establecer
	 */
	public void setIdProcedimientoCP(String idProcedimientoCP) {
		this.idProcedimientoCP = idProcedimientoCP;
	}

	/**
	 * Método de acceso a idProcedimientoSIA.
	 *
	 * @return idProcedimientoSIA
	 */
	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	/**
	 * Método para establecer idProcedimientoSIA.
	 *
	 * @param idProcedimientoSIA
	 *            idProcedimientoSIA a establecer
	 */
	public void setIdProcedimientoSIA(String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	/**
	 * Método de acceso a estado.
	 *
	 * @return estado
	 */
	public TypeEstadoTramite getEstado() {
		return estado;
	}

	/**
	 * Método para establecer estado.
	 *
	 * @param estado
	 *            estado a establecer
	 */
	public void setEstado(TypeEstadoTramite estado) {
		this.estado = estado;
	}

	/**
	 * Método de acceso a autenticacion.
	 *
	 * @return autenticacion
	 */
	public TypeAutenticacion getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 *
	 * @param autenticacion
	 *            autenticacion a establecer
	 */
	public void setAutenticacion(TypeAutenticacion autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacionInicio.
	 *
	 * @return metodoAutenticacionInicio
	 */
	public TypeMetodoAutenticacion getMetodoAutenticacionInicio() {
		return metodoAutenticacionInicio;
	}

	/**
	 * Método para establecer metodoAutenticacionInicio.
	 *
	 * @param metodoAutenticacionInicio
	 *            metodoAutenticacionInicio a establecer
	 */
	public void setMetodoAutenticacionInicio(TypeMetodoAutenticacion metodoAutenticacionInicio) {
		this.metodoAutenticacionInicio = metodoAutenticacionInicio;
	}

	/**
	 * Método de acceso a nifIniciador.
	 *
	 * @return nifIniciador
	 */
	public String getNifIniciador() {
		return nifIniciador;
	}

	/**
	 * Método para establecer nifIniciador.
	 *
	 * @param nifIniciador
	 *            nifIniciador a establecer
	 */
	public void setNifIniciador(String nifIniciador) {
		this.nifIniciador = nifIniciador;
	}

	/**
	 * Método de acceso a nombreIniciador.
	 *
	 * @return nombreIniciador
	 */
	public String getNombreIniciador() {
		return nombreIniciador;
	}

	/**
	 * Método para establecer nombreIniciador.
	 *
	 * @param nombreIniciador
	 *            nombreIniciador a establecer
	 */
	public void setNombreIniciador(String nombreIniciador) {
		this.nombreIniciador = nombreIniciador;
	}

	/**
	 * Método de acceso a apellido1Iniciador.
	 *
	 * @return apellido1Iniciador
	 */
	public String getApellido1Iniciador() {
		return apellido1Iniciador;
	}

	/**
	 * Método para establecer apellido1Iniciador.
	 *
	 * @param apellido1Iniciador
	 *            apellido1Iniciador a establecer
	 */
	public void setApellido1Iniciador(String apellido1Iniciador) {
		this.apellido1Iniciador = apellido1Iniciador;
	}

	/**
	 * Método de acceso a apellido2Iniciador.
	 *
	 * @return apellido2Iniciador
	 */
	public String getApellido2Iniciador() {
		return apellido2Iniciador;
	}

	/**
	 * Método para establecer apellido2Iniciador.
	 *
	 * @param apellido2Iniciador
	 *            apellido2Iniciador a establecer
	 */
	public void setApellido2Iniciador(String apellido2Iniciador) {
		this.apellido2Iniciador = apellido2Iniciador;
	}

	/**
	 * Método de acceso a timestamp.
	 *
	 * @return timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * Método para establecer timestamp.
	 *
	 * @param timestamp
	 *            timestamp a establecer
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma
	 *            idioma a establecer
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a urlInicio.
	 *
	 * @return urlInicio
	 */
	public String getUrlInicio() {
		return urlInicio;
	}

	/**
	 * Método para establecer urlInicio.
	 *
	 * @param urlInicio
	 *            urlInicio a establecer
	 */
	public void setUrlInicio(String urlInicio) {
		this.urlInicio = urlInicio;
	}

	/**
	 * Método de acceso a parametrosInicio.
	 *
	 * @return parametrosInicio
	 */
	public Map<String, String> getParametrosInicio() {
		return parametrosInicio;
	}

	/**
	 * Método para establecer parametrosInicio.
	 *
	 * @param parametrosInicio
	 *            parametrosInicio a establecer
	 */
	public void setParametrosInicio(Map<String, String> parametrosInicio) {
		this.parametrosInicio = parametrosInicio;
	}

	/**
	 * Método de acceso a persistente.
	 *
	 * @return persistente
	 */
	public boolean isPersistente() {
		return persistente;
	}

	/**
	 * Método para establecer persistente.
	 *
	 * @param persistente
	 *            persistente a establecer
	 */
	public void setPersistente(boolean persistente) {
		this.persistente = persistente;
	}

	/**
	 * Método de acceso a plazoDinamico.
	 *
	 * @return plazoDinamico
	 */
	public boolean isPlazoDinamico() {
		return plazoDinamico;
	}

	/**
	 * Método para establecer plazoDinamico.
	 *
	 * @param plazoDinamico
	 *            plazoDinamico a establecer
	 */
	public void setPlazoDinamico(boolean plazoDinamico) {
		this.plazoDinamico = plazoDinamico;
	}

	/**
	 * Método de acceso a fechaInicio.
	 *
	 * @return fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Método para establecer fechaInicio.
	 *
	 * @param fechaInicio
	 *            fechaInicio a establecer
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Método de acceso a fechaUltimoAcceso.
	 *
	 * @return fechaUltimoAcceso
	 */
	public Date getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	/**
	 * Método para establecer fechaUltimoAcceso.
	 *
	 * @param fechaUltimoAcceso
	 *            fechaUltimoAcceso a establecer
	 */
	public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	/**
	 * Método de acceso a fechaCaducidad.
	 *
	 * @return fechaCaducidad
	 */
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	/**
	 * Método para establecer fechaCaducidad.
	 *
	 * @param fechaCaducidad
	 *            fechaCaducidad a establecer
	 */
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	/**
	 * Método de acceso a fechaFin.
	 *
	 * @return fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * Método para establecer fechaFin.
	 *
	 * @param fechaFin
	 *            fechaFin a establecer
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Método de acceso a cancelado.
	 *
	 * @return cancelado
	 */
	public boolean isCancelado() {
		return cancelado;
	}

	/**
	 * Método para establecer cancelado.
	 *
	 * @param cancelado
	 *            cancelado a establecer
	 */
	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	/**
	 * Método de acceso a nifPresentador.
	 *
	 * @return nifPresentador
	 */
	public String getNifPresentador() {
		return nifPresentador;
	}

	/**
	 * Método para establecer nifPresentador.
	 *
	 * @param nifPresentador
	 *            nifPresentador a establecer
	 */
	public void setNifPresentador(String nifPresentador) {
		this.nifPresentador = nifPresentador;
	}

	/**
	 * Método de acceso a nombreCompletoPresentador.
	 *
	 * @return nombreCompletoPresentador
	 */
	public String getNombreCompletoPresentador() {
		return nombreCompletoPresentador;
	}

	/**
	 * Método para establecer nombreCompletoPresentador.
	 *
	 * @param nombreCompletoPresentador
	 *            nombreCompletoPresentador a establecer
	 */
	public void setNombreCompletoPresentador(String nombreCompletoPresentador) {
		this.nombreCompletoPresentador = nombreCompletoPresentador;
	}

	/**
	 * Método de acceso a marcadoPurgar.
	 *
	 * @return marcadoPurgar
	 */
	public boolean isMarcadoPurgar() {
		return marcadoPurgar;
	}

	/**
	 * Método para establecer marcadoPurgar.
	 *
	 * @param marcadoPurgar
	 *            marcadoPurgar a establecer
	 */
	public void setMarcadoPurgar(boolean marcadoPurgar) {
		this.marcadoPurgar = marcadoPurgar;
	}

	/**
	 * Método de acceso a purgado.
	 *
	 * @return purgado
	 */
	public boolean isPurgado() {
		return purgado;
	}

	/**
	 * Método para establecer purgado.
	 *
	 * @param purgado
	 *            purgado a establecer
	 */
	public void setPurgado(boolean purgado) {
		this.purgado = purgado;
	}

	/**
	 * Método de acceso a fechaPurgado.
	 *
	 * @return fechaPurgado
	 */
	public Date getFechaPurgado() {
		return fechaPurgado;
	}

	/**
	 * Método para establecer fechaPurgado.
	 *
	 * @param fechaPurgado
	 *            fechaPurgado a establecer
	 */
	public void setFechaPurgado(Date fechaPurgado) {
		this.fechaPurgado = fechaPurgado;
	}

	/**
	 * Método de acceso a purgaPendientePorPagoRealizado.
	 *
	 * @return purgaPendientePorPagoRealizado
	 */
	public boolean isPurgaPendientePorPagoRealizado() {
		return purgaPendientePorPagoRealizado;
	}

	/**
	 * Método para establecer purgaPendientePorPagoRealizado.
	 *
	 * @param purgaPendientePorPagoRealizado
	 *            purgaPendientePorPagoRealizado a establecer
	 */
	public void setPurgaPendientePorPagoRealizado(boolean purgaPendientePorPagoRealizado) {
		this.purgaPendientePorPagoRealizado = purgaPendientePorPagoRealizado;
	}

	/**
	 * Método de acceso a idArea.
	 *
	 * @return idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * Método para establecer idArea.
	 *
	 * @param idArea
	 *            idArea a establecer
	 */
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	/**
	 * Método de acceso a servicioCP.
	 * 
	 * @return servicioCP
	 */
	public boolean isServicioCP() {
		return servicioCP;
	}

	/**
	 * Método para establecer servicioCP.
	 * 
	 * @param servicioCP
	 *            servicioCP a establecer
	 */
	public void setServicioCP(boolean servicioCP) {
		this.servicioCP = servicioCP;
	}

}
