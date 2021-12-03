package es.caib.sistrages.frontend.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.frontend.controller.DialogAyuda;
import es.caib.sistrages.frontend.controller.SessionBean;
import es.caib.sistrages.frontend.controller.ViewConfiguracionEntidad;
import es.caib.sistrages.frontend.controller.ViewDominios;
import es.caib.sistrages.frontend.controller.ViewEntidades;
import es.caib.sistrages.frontend.controller.ViewFormateadorFormulario;
import es.caib.sistrages.frontend.controller.ViewFuentes;
import es.caib.sistrages.frontend.controller.ViewMensajesAvisoEntidad;
import es.caib.sistrages.frontend.controller.ViewPlugins;
import es.caib.sistrages.frontend.controller.ViewPropiedadesConfiguracion;
import es.caib.sistrages.frontend.controller.ViewRolesPermisos;
import es.caib.sistrages.frontend.controller.ViewTramites;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeOpcionMenuAdmOper;
import es.caib.sistrages.frontend.model.types.TypeOpcionMenuSuperAdministrador;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;

/**
 * Utilidades.
 *
 * @author Indra
 *
 */
public final class UtilJSF {

	/** Log. */
	final static Logger LOG = LoggerFactory.getLogger(UtilJSF.class);

	/** Parametro de sesion para securizar apertura dialogs. */
	private static final String SEC_OPEN_DIALOG = "SEC_OPEN_DIALOG";

	/** Constructor privado para evitar problema. */
	private UtilJSF() {
		// not called
	}

	/** Path views. */
	public static final String PATH_VIEWS = "/secure/app/";
	/** Extensión .html **/
	private static final String EXTENSION_XHTML = ".xhtml";
	/** Url sin implementar. **/
	private static final String URL_SIN_IMPLEMENTAR = "/error/errorCode.xhtml?errorCode=404";

	/**
	 * Abre pantalla de dialogo
	 *
	 * @param dialog     Clase dialogo
	 * @param modoAcceso Modo de acceso
	 * @param params     parametros
	 * @param modal      si se abre en forma modal
	 * @param width      anchura
	 * @param heigth     altura
	 */
	public static void openDialog(final Class<?> clase, final TypeModoAcceso modoAcceso,
			final Map<String, String> params, final boolean modal, final int width, final int heigth) {
		openDialog(UtilJSF.getViewNameFromClass(clase), modoAcceso, params, modal, width, heigth);
	}

	/**
	 * Abre pantalla de dialogo
	 *
	 * @param dialog     Nombre pantalla dialogo (dialogo.xhtml o id navegacion)
	 * @param modoAcceso Modo de acceso
	 * @param params     parametros
	 * @param modal      si se abre en forma modal
	 * @param width      anchura
	 * @param heigth     altura
	 */
	public static void openDialog(final String dialog, final TypeModoAcceso modoAcceso,
			final Map<String, String> params, final boolean modal, final int width, final int heigth) {
		// Opciones dialogo
		final Map<String, Object> options = new HashMap<>();
		options.put("modal", modal);
		options.put("width", width);
		options.put("height", heigth);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");
		options.put("id", "someId");

		// Parametros
		String idParam = "";
		final Map<String, List<String>> paramsDialog = new HashMap<>();
		paramsDialog.put(TypeParametroVentana.MODO_ACCESO.toString(), Collections.singletonList(modoAcceso.toString()));
		if (params != null) {
			for (final String key : params.keySet()) {
				paramsDialog.put(key, Collections.singletonList(params.get(key)));
				if (TypeParametroVentana.ID.toString().equals(key)) {
					idParam = params.get(key);
				}
			}
		}

		// Metemos en sessionbean un parámetro de seguridad para evitar que se
		// pueda cambiar el modo de acceso
		final String secOpenDialog = modoAcceso.toString() + "-" + idParam + "-" + System.currentTimeMillis();
		getSessionBean().getMochilaDatos().put(SEC_OPEN_DIALOG, secOpenDialog);

		// Abre dialogo
		RequestContext.getCurrentInstance().openDialog(dialog, options, paramsDialog);
	}

	/**
	 * Abre pantalla de dialogo (siempre al 95% de width)
	 *
	 * @param dialog     Clase dialogo
	 * @param modoAcceso Modo de acceso
	 * @param params     parametros
	 * @param modal      si se abre en forma modal
	 *
	 * @param heigth     altura
	 */
	public static void openDialog(final Class<?> clase, final TypeModoAcceso modoAcceso,
			final Map<String, String> params, final boolean modal, final int heigth) {
		openDialog(UtilJSF.getViewNameFromClass(clase), modoAcceso, params, modal, heigth);
	}

	/**
	 * Abre pantalla de dialogo
	 *
	 * @param dialog     Nombre pantalla dialogo (dialogo.xhtml o id navegacion)
	 * @param modoAcceso Modo de acceso
	 * @param params     parametros
	 * @param modal      si se abre en forma modal
	 * @param width      anchura
	 * @param height     altura
	 */
	public static void openDialog(final String dialog, final TypeModoAcceso modoAcceso,
			final Map<String, String> params, final boolean modal, final int height) {
		// Opciones dialogo
		final Map<String, Object> options = new HashMap<>();
		options.put("modal", modal);
		options.put("width", "95%");
		options.put("height", height);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");

		// Parametros
		String idParam = "";
		final Map<String, List<String>> paramsDialog = new HashMap<>();
		paramsDialog.put(TypeParametroVentana.MODO_ACCESO.toString(), Collections.singletonList(modoAcceso.toString()));
		if (params != null) {
			for (final String key : params.keySet()) {
				paramsDialog.put(key, Collections.singletonList(params.get(key)));
				if (TypeParametroVentana.ID.toString().equals(key)) {
					idParam = params.get(key);
				}
			}
		}

		// Metemos en sessionbean un parámetro de seguridad para evitar que se
		// pueda cambiar el modo de acceso
		final String secOpenDialog = modoAcceso.toString() + "-" + idParam + "-" + System.currentTimeMillis();
		getSessionBean().getMochilaDatos().put(SEC_OPEN_DIALOG, secOpenDialog);

		// Abre dialogo
		RequestContext.getCurrentInstance().openDialog(dialog, options, paramsDialog);
	}

	/**
	 * Chequea que no se ha cambiado modo de acceso en apertura dialog.
	 *
	 * @param modoAcceso modo acceso
	 * @param id         id
	 */
	public static void checkSecOpenDialog(final TypeModoAcceso modoAcceso, final String id) {
		// Buscamos si existe token
		final String secOpenDialog = (String) getSessionBean().getMochilaDatos().get(SEC_OPEN_DIALOG);
		if (secOpenDialog == null) {
			throw new FrontException("Se han modificado parametros de apertura dialog");
		}
		// Verificamos que coincida modo acceso e id
		final String[] items = secOpenDialog.split("-");
		final String paramId = StringUtils.defaultString(id);
		if (items.length != 3 || !modoAcceso.toString().equals(items[0]) || !paramId.equals(items[1])) {
			throw new FrontException("Se han modificado parametros de apertura dialog");
		}
		// Verificamos que no haya pasado mas de 1 min
		final Date tiempo = new Date(Long.parseLong(items[2]));
		final Date ahora = new Date();
		final long diffInMillies = Math.abs(ahora.getTime() - tiempo.getTime());
		if (diffInMillies > (ConstantesNumero.N60 * ConstantesNumero.N1000)) {
			throw new FrontException("Se han modificado parametros de apertura dialog");
		}
		// Eliminamos token de la sesion para que se pueda reusar
		getSessionBean().getMochilaDatos().remove(SEC_OPEN_DIALOG);
	}

	/**
	 * Cierra dialog
	 *
	 * @param result
	 */
	public static void closeDialog(final DialogResult result) {
		RequestContext.getCurrentInstance().closeDialog(result);
	}

	/**
	 * Añade mensaje a un componente para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel        Nivel de gravedad.
	 * @param message      Mensaje
	 * @param detail       Detalle
	 * @param idComponente Si el id componente es nulo, se enviará al growl.
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail,
			final String idComponente) {
		final Severity severity = getSeverity(nivel);
		final FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(idComponente, new FacesMessage(severity, message, detail));
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel   Nivel gravedad
	 * @param message Mensaje
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message) {
		addMessageContext(nivel, message, message, null);
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel   Nivel gravedad
	 * @param message Mensaje
	 * @param detail  Detalle
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail) {
		addMessageContext(nivel, message, message, null);
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel            Nivel gravedad
	 * @param message          Mensaje
	 * @param validationFailed añade la marca de error de validacion
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message,
			final boolean validationFailed) {
		addMessageContext(nivel, message, message, validationFailed, null);
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel            Nivel gravedad
	 * @param message          Mensaje
	 * @param validationFailed añade la marca de error de validacion
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message,
			final boolean validationFailed, final String idComponente) {
		addMessageContext(nivel, message, message, validationFailed, idComponente);
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel            Nivel gravedad
	 * @param message          Mensaje
	 * @param detail           Detalle
	 * @param validationFailed añade la marca de error de validacion
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail,
			final boolean validationFailed) {
		addMessageContext(nivel, message, detail, validationFailed, null);
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel            Nivel gravedad
	 * @param message          Mensaje
	 * @param detail           Detalle
	 * @param validationFailed añade la marca de error de validacion
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail,
			final boolean validationFailed, final String idComponente) {
		addMessageContext(nivel, message, detail, idComponente);

		if (validationFailed) {
			FacesContext.getCurrentInstance().validationFailed();
		}
	}

	/**
	 * Obtiene literal.
	 *
	 * @param key key
	 * @return literal
	 */
	public static String getLiteral(final String key) {
		final ResourceBundle text = ResourceBundle.getBundle("i18n.messages", getSessionBean().getLocale());
		return text.getString(key);
	}

	/**
	 * Obtiene el valor de literal.
	 *
	 * @param key        key
	 * @param parametros parametros para sustituir en el literal
	 * @return el valor de literal
	 */
	public static String getLiteral(final String key, final Object[] parametros) {
		final ResourceBundle text = ResourceBundle.getBundle("i18n.messages", getSessionBean().getLocale());
		return MessageFormat.format(text.getString(key), parametros);
	}

	/**
	 * Obtiene el valor de literal.
	 *
	 * @param key  key
	 * @param lang lang
	 * @return el valor de literal
	 */
	public static String getLiteral(final String key, final String lang) {
		final ResourceBundle text = ResourceBundle.getBundle("i18n.messages", new Locale(lang));
		return text.getString(key);
	}

	/**
	 * Obtiene bean de sesión.
	 *
	 * @return bean de sesión
	 */
	public static SessionBean getSessionBean() {
		return (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionBean");
	}

	/**
	 * Verifica si accede el superadministrador generando excepción en caso
	 * contrario.
	 *
	 */
	public static void verificarAccesoSuperAdministrador() {
		final SessionBean sb = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("sessionBean");
		if (sb.getActiveRole() != TypeRoleAcceso.SUPER_ADMIN) {
			throw new FrontException("No se está accediendo con perfil SuperAdministrador");
		}
	}

	/**
	 * Verifica si accede el administrador entidad
	 *
	 * @param idEntidad
	 */
	public static boolean isAdministradorDesarrolladorEntidad(final Long idEntidad) {
		final SessionBean sb = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("sessionBean");
		if (sb.getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Verifica si accede el administrador entidad o desarrollador entidad generando
	 * excepción en caso contrario.
	 *
	 * @param idEntidad
	 */
	public static void verificarAccesoAdministradorDesarrolladorEntidadByEntidad(final Long idEntidad) {
		final SessionBean sb = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("sessionBean");
		if (sb.getActiveRole() != TypeRoleAcceso.ADMIN_ENT && sb.getActiveRole() != TypeRoleAcceso.DESAR) {
			throw new FrontException("No se está accediendo con perfil Administrador Entidad o Desarrollador Entidad");
		}
		if (idEntidad == null) {
			throw new FrontException("No se ha seleccionado ninguna entidad");
		}
		boolean found = false;
		for (final Entidad e : sb.getListaEntidades()) {
			if (e.getCodigo().longValue() == idEntidad) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new FrontException("No tiene acceso a la entidad");
		}
	}

	/**
	 * Verifica si accede el administrador entidad o desarrollador entidad generando
	 * excepción en caso contrario.
	 *
	 * @param idArea
	 */
	public static void verificarAccesoAdministradorDesarrolladorEntidadByArea(final Long idArea) {

		final SessionBean sb = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("sessionBean");

		if (sb.getActiveRole() != TypeRoleAcceso.ADMIN_ENT && sb.getActiveRole() != TypeRoleAcceso.DESAR) {
			throw new FrontException("No se está accediendo con perfil Administrador Entidad o Desarrollador Entidad");
		}

		if (sb.getActiveRole() != TypeRoleAcceso.ADMIN_ENT) {

			if (idArea == null) {
				throw new FrontException("No se ha seleccionado ninguna area");
			}
			boolean found = false;
			found = sb.tieneAccesoArea(idArea);
			if (!found) {
				throw new FrontException("No tiene acceso al area");
			}

		}
	}

	/**
	 * Redirige pagina JSF.
	 *
	 * @param jsfPage path JSF page
	 */
	public static void redirectJsfPage(final String jsfPage) {
		try {
			final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext();
			final String contextPath = servletContext.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + jsfPage);
		} catch (final IOException e) {
			UtilJSF.LOG.error("Error redirigiendo", e);
		}
	}

	/**
	 * Redirect jsf page.
	 *
	 * @param jsfPage the jsf page
	 * @param params  the params
	 */
	public static void redirectJsfPage(final String jsfPage, final Map<String, List<String>> params) {
		try {
			final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext();
			final String contextPath = servletContext.getContextPath();
			final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(ec.encodeRedirectURL(contextPath + jsfPage, params));
			FacesContext.getCurrentInstance().responseComplete();
		} catch (final IOException e) {
			UtilJSF.LOG.error("Error redirigiendo", e);
		}
	}

	// FacesContext.getCurrentInstance().getExternalContext().encodeRedirectURL(baseUrl,
	// parameters)

	private static Severity getSeverity(final TypeNivelGravedad nivel) {
		Severity severity;
		switch (nivel) {
		case INFO:
			severity = FacesMessage.SEVERITY_INFO;
			break;
		case WARNING:
			severity = FacesMessage.SEVERITY_WARN;
			break;
		case ERROR:
			severity = FacesMessage.SEVERITY_ERROR;
			break;
		case FATAL:
			severity = FacesMessage.SEVERITY_FATAL;
			break;
		default:
			severity = FacesMessage.SEVERITY_INFO;
			break;
		}
		return severity;
	}

	/**
	 * Devuelve view name suponiendo que se llama igual que la clase.
	 *
	 * @param clase clase
	 * @return view name
	 */
	public static String getViewNameFromClass(final Class<?> clase) {
		final String className = clase.getSimpleName();
		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}

	/**
	 * Devuelve el title name de la clase.
	 *
	 * @param clase
	 * @return
	 */
	public static String getTitleViewNameFromClass(final Class<?> clase) {
		return getViewNameFromClass(clase) + ".titulo";
	}

	/**
	 * Devuelve url opcion menu super administrador.
	 *
	 * @param opcion opcion
	 * @return url
	 */
	public static String getUrlOpcionMenuSuperadministrador(final TypeOpcionMenuSuperAdministrador opcion) {
		String url = null;

		switch (opcion) {
		case ENTIDADES:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewEntidades.class) + EXTENSION_XHTML;
			break;
		case DOMINIOS_GLOBALES:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewDominios.class) + EXTENSION_XHTML + "?ambito=G";
			break;
		case PLUGINS_GLOBALES:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewPlugins.class) + EXTENSION_XHTML + "?ambito=G";
			break;
		case PROPIEDADES_GLOBALES:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewPropiedadesConfiguracion.class) + EXTENSION_XHTML;
			break;
		default:
			url = URL_SIN_IMPLEMENTAR;
			break;
		}

		return url;
	}

	/**
	 * Devuelve url opcion menu super administrador.
	 *
	 * @param opcion opcion
	 * @return url
	 */
	public static String getUrlOpcionMenuAdmOper(final TypeOpcionMenuAdmOper opcion, final Long idEntidad) {
		String url = null;
		final String ambitoEntidadURL = "?ambito=E&id=" + idEntidad;

		switch (opcion) {
		case CONFIGURACION_ENTIDAD:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewConfiguracionEntidad.class) + EXTENSION_XHTML;
			break;
		case FUENTES_DATOS:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewFuentes.class) + EXTENSION_XHTML + ambitoEntidadURL;
			break;
		case DOMINIOS_ENTIDAD:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewDominios.class) + EXTENSION_XHTML + ambitoEntidadURL;
			break;
		case PLUGINS_ENTIDAD:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewPlugins.class) + EXTENSION_XHTML + ambitoEntidadURL;
			break;
		case FORMATEADORFORMULARIO:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewFormateadorFormulario.class) + EXTENSION_XHTML;
			break;
		case TRAMITES:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewTramites.class) + EXTENSION_XHTML;
			break;
		case ROLES_PERMISOS:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewRolesPermisos.class) + EXTENSION_XHTML;
			break;
		case MENSAJES:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewMensajesAvisoEntidad.class) + EXTENSION_XHTML;
			break;
		default:
			url = URL_SIN_IMPLEMENTAR;
			break;
		}

		return url;
	}

	/**
	 * Devuelve opcion por defecto super administrador.
	 *
	 * @param opcion opcion
	 * @return opcion
	 */
	public static TypeOpcionMenuAdmOper getDefaultOpcionAdmOper() {
		return TypeOpcionMenuAdmOper.TRAMITES;
	}

	/**
	 * Devuelve opcion por defecto administrador entidad/desarrollador.
	 *
	 * @param opcion opcion
	 * @return opcion
	 */
	public static TypeOpcionMenuSuperAdministrador getDefaultOpcionSuperadministrador() {
		return TypeOpcionMenuSuperAdministrador.ENTIDADES;
	}

	/**
	 * Devuelve url por defecto segun role.
	 *
	 * @param role role
	 * @return url
	 */
	public static String getDefaultUrlRole(final TypeRoleAcceso role, final Long idEntidad) {
		String url = null;
		if (role == null) {
			url = "/error/errorUsuarioSinRol.xhtml";
		} else {
			switch (role) {
			case SUPER_ADMIN:
				url = getUrlOpcionMenuSuperadministrador(getDefaultOpcionSuperadministrador());
				break;
			case ADMIN_ENT:
				url = getUrlOpcionMenuAdmOper(getDefaultOpcionAdmOper(), idEntidad);
				break;
			case DESAR:
				url = getUrlOpcionMenuAdmOper(getDefaultOpcionAdmOper(), idEntidad);
				break;
			default:
				url = "/error/errorUsuarioSinRol.xhtml";
				break;
			}
		}
		return url;
	}

	/**
	 * Redirige pagina JSF por defecto para role.
	 *
	 * @param jsfPage path JSF page
	 */
	public static void redirectJsfDefaultPageRole(final TypeRoleAcceso role, final Long idEntidad) {
		redirectJsfPage(getDefaultUrlRole(role, idEntidad));
	}

	/**
	 * Get Url Arbol definicion version
	 *
	 * @param opcion
	 * @return
	 */
	public static String getUrlArbolDefinicionVersion(final String opcion) {
		return PATH_VIEWS + opcion + EXTENSION_XHTML;
	}

	/**
	 * Obtiene idioma.
	 *
	 * @return idioma
	 */
	public static TypeIdioma getIdioma() {
		return TypeIdioma.fromString(getSessionBean().getLang());
	}

	/**
	 * Obtiene id entidad.
	 *
	 * @return id entidad
	 */
	public static Long getIdEntidad() {
		Long idEntidad = null;
		final Entidad entidad = UtilJSF.getSessionBean().getEntidad();
		if (entidad != null) {
			idEntidad = entidad.getCodigo();
		}
		return idEntidad;
	}

	/**
	 * Obtiene context path.
	 *
	 * @return context path
	 */
	public static String getContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	/**
	 * Obtiene la url de una paso a partir del tipo de instancia que es.
	 *
	 * @param tramitePaso
	 * @return
	 */
	public static String getUrlTramitePaso(final TramitePaso tramitePaso) {
		String url = "#";
		if (tramitePaso instanceof TramitePasoDebeSaber) {
			url = "viewDefinicionVersionDebeSaber";
		} else if (tramitePaso instanceof TramitePasoRellenar) {
			url = "viewDefinicionVersionRellenar";
		} else if (tramitePaso instanceof TramitePasoAnexar) {
			url = "viewDefinicionVersionAnexarDocumentos";
		} else if (tramitePaso instanceof TramitePasoTasa) {
			url = "viewDefinicionVersionPagarTasas";
		} else if (tramitePaso instanceof TramitePasoRegistrar) {
			url = "viewDefinicionVersionRegistrarTramite";
		}
		return url;
	}

	/**
	 * Realiza el update desde ajax del componente como desde el xhtml.
	 *
	 * @param pIdComponente identificador del componente
	 */
	public static void doUpdateComponent(final String pIdComponente) {
		RequestContext.getCurrentInstance().update(pIdComponente);
	}

	/**
	 * Pone el estado validation failed.
	 */
	public static void doValidationFailed() {
		FacesContext.getCurrentInstance().validationFailed();
	}

	/**
	 * Comprueba si el entorno es el mismo (desarrollo, preproduccion y produccion).
	 *
	 * @return
	 */
	public static boolean checkEntorno(final TypeEntorno tipoEntorno) {
		final boolean mismo = false;
		if (tipoEntorno != null) {
			final String entorno = getEntorno();
			return tipoEntorno.toString().equals(entorno);
		}
		return mismo;
	}

	/**
	 * Devuelve el entorno.
	 *
	 * @return
	 */
	public static String getEntorno() {
		return FacesContext.getCurrentInstance().getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{frontModuleConfig}",
						es.caib.sistrages.frontend.ModuleConfig.class)
				.getEntorno();
	}

	/**
	 * Devuelve la revision de SVN o GIT.
	 *
	 * @return
	 */
	public static String getRevision() {
		final es.caib.sistrages.frontend.ModuleConfig moduleConfig = FacesContext.getCurrentInstance().getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{frontModuleConfig}",
						es.caib.sistrages.frontend.ModuleConfig.class);

		String revision;
		if (moduleConfig.getCommitGit() == null || moduleConfig.getCommitGit().isEmpty()) {
			revision = "SVN:" + moduleConfig.getCommitSvn();
		} else {
			revision = "GIT:" + moduleConfig.getCommitGit();
		}
		return revision;
	}

	/**
	 * Devuelve la version.
	 *
	 * @return
	 */
	public static String getVersion() {
		return FacesContext.getCurrentInstance().getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{frontModuleConfig}",
						es.caib.sistrages.frontend.ModuleConfig.class)
				.getVersion();
	}

	public static void openHelp(final String id) {
		final Map<String, String> params = new HashMap<>();
		if (StringUtils.isBlank(id)) {
			throw new FrontException("No existe identificador");
		}

		params.put(TypeParametroVentana.ID.toString(), id);

		UtilJSF.openDialog(DialogAyuda.class, TypeModoAcceso.CONSULTA, params, true, 900, 550);
	}

	/**
	 * Método que actualiza la paginación
	 */
	public static void setPaginacion(Integer paginacion, String pagina) {
		final SessionBean sb = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("sessionBean");
		sb.setPaginacion(paginacion);
	}

	/**
	 * Método que devuelve la paginación
	 */
	public static Integer getPaginacion(String pagina) {
		final SessionBean sb = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("sessionBean");
		return sb.getPaginacion();
	}
}
