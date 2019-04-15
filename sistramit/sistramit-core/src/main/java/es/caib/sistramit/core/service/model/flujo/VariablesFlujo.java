package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Variables accesibles desde un paso de tramitación. Permiten el acceso desde
 * el paso a los elementos compartidos (información de sesión, formularios,
 * pagos, ...)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class VariablesFlujo implements Serializable {

	/**
	 * Entorno.
	 */
	private TypeEntorno entorno;

	/**
	 * Identificador de sesión de tramitación.
	 */
	private String idSesionTramitacion;

	/**
	 * Nivel autenticación del usuario actualmente autenticado.
	 */
	private TypeAutenticacion nivelAutenticacion;

	/**
	 * Metodo autenticación iniciador.
	 */
	private TypeMetodoAutenticacion metodoAutenticacion;

	/**
	 * Datos del usuario que esta actualmente autenticado.
	 */
	private DatosUsuario usuario;

	/**
	 * Idioma de tramitación.
	 */
	private String idioma;

	/**
	 * Url inicio tramite.
	 */
	private String urlInicioTramite;

	/**
	 * Id tramite.
	 */
	private String idTramite;

	/**
	 * Versión tramite.
	 */
	private int versionTramite;

	/**
	 * Titulo del trámite.
	 */
	private String tituloTramite;

	/**
	 * Parámetros de inicio.
	 */
	private Map<String, String> parametrosInicio;

	/**
	 * Datos provenientes de catálogo de procedimientos.
	 */
	private DefinicionTramiteCP datosTramiteCP;

	/**
	 * Documentos accesibles desde el paso pertenecientes a pasos anteriores.
	 */
	private List<DatosDocumento> documentos = new ArrayList<>();

	/**
	 * Inicio plazo del trámite.
	 */
	private Date fechaInicioPlazo;

	/**
	 * Fin plazo del tramite.
	 */
	private Date fechaFinPlazo;

	/**
	 * Indica si se debe debugear el trámite.
	 */
	private boolean debugEnabled;

	/**
	 * Usuario autenticado.
	 */
	private UsuarioAutenticadoInfo usuarioAutenticado;

	/**
	 * Método de acceso a entorno.
	 *
	 * @return entorno
	 */
	public TypeEntorno getEntorno() {
		return entorno;
	}

	/**
	 * Método para establecer entorno.
	 *
	 * @param entorno
	 *            entorno a establecer
	 */
	public void setEntorno(TypeEntorno entorno) {
		this.entorno = entorno;
	}

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
	 * Método de acceso a nivelAutenticacion.
	 *
	 * @return nivelAutenticacion
	 */
	public TypeAutenticacion getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	/**
	 * Método para establecer nivelAutenticacion.
	 *
	 * @param nivelAutenticacion
	 *            nivelAutenticacion a establecer
	 */
	public void setNivelAutenticacion(TypeAutenticacion nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 *
	 * @return metodoAutenticacion
	 */
	public TypeMetodoAutenticacion getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 *
	 * @param metodoAutenticacion
	 *            metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(TypeMetodoAutenticacion metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}

	/**
	 * Método de acceso a usuario.
	 *
	 * @return usuario
	 */
	public DatosUsuario getUsuario() {
		return usuario;
	}

	/**
	 * Método para establecer usuario.
	 *
	 * @param usuario
	 *            usuario a establecer
	 */
	public void setUsuario(DatosUsuario usuario) {
		this.usuario = usuario;
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
	 * Método de acceso a urlInicioTramite.
	 *
	 * @return urlInicioTramite
	 */
	public String getUrlInicioTramite() {
		return urlInicioTramite;
	}

	/**
	 * Método para establecer urlInicioTramite.
	 *
	 * @param urlInicioTramite
	 *            urlInicioTramite a establecer
	 */
	public void setUrlInicioTramite(String urlInicioTramite) {
		this.urlInicioTramite = urlInicioTramite;
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
	 * Método de acceso a tituloTramite.
	 *
	 * @return tituloTramite
	 */
	public String getTituloTramite() {
		return tituloTramite;
	}

	/**
	 * Método para establecer tituloTramite.
	 *
	 * @param tituloTramite
	 *            tituloTramite a establecer
	 */
	public void setTituloTramite(String tituloTramite) {
		this.tituloTramite = tituloTramite;
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
	 * Método de acceso a datosTramiteCP.
	 *
	 * @return datosTramiteCP
	 */
	public DefinicionTramiteCP getDatosTramiteCP() {
		return datosTramiteCP;
	}

	/**
	 * Método para establecer datosTramiteCP.
	 *
	 * @param datosTramiteCP
	 *            datosTramiteCP a establecer
	 */
	public void setDatosTramiteCP(DefinicionTramiteCP datosTramiteCP) {
		this.datosTramiteCP = datosTramiteCP;
	}

	/**
	 * Método de acceso a documentos.
	 *
	 * @return documentos
	 */
	public List<DatosDocumento> getDocumentos() {
		return documentos;
	}

	/**
	 * Método para establecer documentos.
	 *
	 * @param documentos
	 *            documentos a establecer
	 */
	public void setDocumentos(List<DatosDocumento> documentos) {
		this.documentos = documentos;
	}

	/**
	 * Método de acceso a fechaInicioPlazo.
	 *
	 * @return fechaInicioPlazo
	 */
	public Date getFechaInicioPlazo() {
		return fechaInicioPlazo;
	}

	/**
	 * Método para establecer fechaInicioPlazo.
	 *
	 * @param fechaInicioPlazo
	 *            fechaInicioPlazo a establecer
	 */
	public void setFechaInicioPlazo(Date fechaInicioPlazo) {
		this.fechaInicioPlazo = fechaInicioPlazo;
	}

	/**
	 * Método de acceso a fechaFinPlazo.
	 *
	 * @return fechaFinPlazo
	 */
	public Date getFechaFinPlazo() {
		return fechaFinPlazo;
	}

	/**
	 * Método para establecer fechaFinPlazo.
	 *
	 * @param fechaFinPlazo
	 *            fechaFinPlazo a establecer
	 */
	public void setFechaFinPlazo(Date fechaFinPlazo) {
		this.fechaFinPlazo = fechaFinPlazo;
	}

	/**
	 * Método de acceso a debugEnabled.
	 *
	 * @return debugEnabled
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * Método para establecer debugEnabled.
	 *
	 * @param debugEnabled
	 *            debugEnabled a establecer
	 */
	public void setDebugEnabled(boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	/**
	 * Devuelve si existe documentación presencial.
	 *
	 * @return boolean
	 */
	public boolean existeDocumentacionPresencial() {
		boolean res = false;
		for (final DatosDocumento dd : this.documentos) {
			if (dd.getPresentacion() == TypePresentacion.PRESENCIAL) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**
	 * Método de acceso a usuarioAutenticado.
	 *
	 * @return usuarioAutenticado
	 */
	public UsuarioAutenticadoInfo getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	/**
	 * Método para establecer usuarioAutenticado.
	 *
	 * @param usuarioAutenticado
	 *            usuarioAutenticado a establecer
	 */
	public void setUsuarioAutenticado(UsuarioAutenticadoInfo usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}

	/**
	 * Permite acceder a un documento.
	 *
	 * @param idDocumento
	 *            Id documento
	 * @param instancia
	 *            En caso de anexo genérico permite indicar la instancia
	 * @return Documento
	 */
	public DatosDocumento getDocumento(final String idDocumento, final int instancia) {
		DatosDocumento res = null;
		for (final DatosDocumento dd : getDocumentos()) {
			if (dd.getId().equals(idDocumento)) {
				if (dd.getTipo() == TypeDocumento.ANEXO) {
					if (((DatosDocumentoAnexo) dd).getInstancia() == instancia) {
						res = dd;
						break;
					}
				} else {
					res = dd;
					break;
				}
			}
		}
		return res;
	}

}
