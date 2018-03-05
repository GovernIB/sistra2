package es.caib.sistrages.frontend.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.frontend.controller.DialogTraduccion;
import es.caib.sistrages.frontend.controller.SessionBean;
import es.caib.sistrages.frontend.controller.ViewConfiguracionEntidad;
import es.caib.sistrages.frontend.controller.ViewDominios;
import es.caib.sistrages.frontend.controller.ViewEntidades;
import es.caib.sistrages.frontend.controller.ViewFormulariosExternos;
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

	/** Constructor privado para evitar problema. */
	private UtilJSF() {
		// not called
	}

	/** Path views. */
	public static final String PATH_VIEWS = "/secure/app/";
	/** Extensión .html **/
	private static final String EXTENSION_XHTML = ".xhtml";
	/** Url sin implementar. **/
	private static final String URL_SIN_IMPLEMENTAR = "/error/errorCode.xhtml?errorCode=404";;

	/**
	 * Abre pantalla de dialogo
	 *
	 * @param dialog
	 *            Clase dialogo
	 * @param modoAcceso
	 *            Modo de acceso
	 * @param params
	 *            parametros
	 * @param modal
	 *            si se abre en forma modal
	 * @param width
	 *            anchura
	 * @param heigth
	 *            altura
	 */
	public static void openDialog(final Class<?> clase, final TypeModoAcceso modoAcceso,
			final Map<String, String> params, final boolean modal, final int width, final int heigth) {
		openDialog(UtilJSF.getViewNameFromClass(clase), modoAcceso, params, modal, width, heigth);
	}

	/**
	 * Abre pantalla de dialogo
	 *
	 * @param dialog
	 *            Nombre pantalla dialogo (dialogo.xhtml o id navegacion)
	 * @param modoAcceso
	 *            Modo de acceso
	 * @param params
	 *            parametros
	 * @param modal
	 *            si se abre en forma modal
	 * @param width
	 *            anchura
	 * @param heigth
	 *            altura
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
		// Parametros
		final Map<String, List<String>> paramsDialog = new HashMap<>();
		paramsDialog.put(TypeParametroVentana.MODO_ACCESO.toString(), Collections.singletonList(modoAcceso.toString()));
		if (params != null) {
			for (final String key : params.keySet()) {
				paramsDialog.put(key, Collections.singletonList(params.get(key)));
			}
		}
		// Abre dialogo
		RequestContext.getCurrentInstance().openDialog(dialog, options, paramsDialog);
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
	 * Muestra mensaje como ventana de dialogo.
	 *
	 * @param nivel
	 *            Nivel gravedad
	 * @param title
	 *            Titulo
	 * @param message
	 *            Mensaje
	 */
	public static void showMessageDialog(final TypeNivelGravedad nivel, final String title, final String message) {
		final Severity severity = getSeverity(nivel);
		RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(severity, title, message));
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel
	 *            Nivel gravedad
	 * @param message
	 *            Mensaje
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message) {
		final Severity severity = getSeverity(nivel);
		final FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity, message, message));
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel
	 *            Nivel gravedad
	 * @param message
	 *            Mensaje
	 * @param detail
	 *            Detalle
	 */
	public static void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail) {
		final Severity severity = getSeverity(nivel);
		final FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity, message, detail));
	}

	public static String getLiteral(final String key) {
		final FacesContext context = FacesContext.getCurrentInstance();
		// final ResourceBundle text =
		// context.getApplication().evaluateExpressionGet(context,
		// "#{msg}",ResourceBundle.class);

		final ResourceBundle text = ResourceBundle.getBundle("i18n.messages", ((SessionBean) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap().get("sessionBean")).getLocale());
		final String result = text.getString(key);
		return result;
	}

	/**
	 * Redirige pagina JSF.
	 *
	 * @param jsfPage
	 *            path JSF page
	 */
	public static void redirectJsfPage(final String jsfPage) {
		try {
			final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext();
			final String contextPath = servletContext.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + jsfPage);
		} catch (final IOException e) {
			// TODO Gestion Excepciones
		}
	}

	/**
	 * Redirect jsf page.
	 *
	 * @param jsfPage
	 *            the jsf page
	 * @param params
	 *            the params
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
			// TODO Gestion Excepciones
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
	 * @param clase
	 *            clase
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
	 * @param opcion
	 *            opcion
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
		case TEST:
			url = "/secure/test/viewTest.xhtml";
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
	 * @param opcion
	 *            opcion
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
		case GESTORES_FORMULARIOS:
			url = PATH_VIEWS + UtilJSF.getViewNameFromClass(ViewFormulariosExternos.class) + EXTENSION_XHTML;
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
	 * @param opcion
	 *            opcion
	 * @return opcion
	 */
	public static TypeOpcionMenuAdmOper getDefaultOpcionAdmOper() {
		return TypeOpcionMenuAdmOper.TRAMITES;
	}

	/**
	 * Devuelve opcion por defecto administrador entidad/desarrollador.
	 *
	 * @param opcion
	 *            opcion
	 * @return opcion
	 */
	public static TypeOpcionMenuSuperAdministrador getDefaultOpcionSuperadministrador() {
		return TypeOpcionMenuSuperAdministrador.ENTIDADES;
	}

	/**
	 * Devuelve url por defecto segun role.
	 *
	 * @param role
	 *            role
	 * @return url
	 */
	public static String getDefaultUrlRole(final TypeRoleAcceso role, final Long idEntidad) {
		String url = null;
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
			break;
		}
		return url;
	}

	/**
	 * Redirige pagina JSF por defecto para role.
	 *
	 * @param jsfPage
	 *            path JSF page
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
	 * Abre un dialog de tipo traduccion.
	 *
	 * @param modoAcceso
	 *            Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones
	 *            Dato en formato json de tipo Traducciones
	 * @param idiomas
	 *            La lista de idiomas que se puede introducir literal. Si no se
	 *            introduce, se supondrán que son los que tenga traducciones.
	 * @param idiomasObligatorios
	 *            La lista de idiomas obligatorios. Si no se introduce, se supondrán
	 *            que son los que tenga traducciones.
	 * @throws JsonProcessingException
	 */
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, Traducciones traducciones,
			List<String> idiomas, List<String> idiomasObligatorios) throws JsonProcessingException {

		final Map<String, String> params = new HashMap<>();

		if (traducciones == null || traducciones.getIdiomas() == null || traducciones.getIdiomas().isEmpty()) {
			traducciones = new Traducciones();
			traducciones.add(new Traduccion("ca", ""));
			traducciones.add(new Traduccion("es", ""));
		}

		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(traducciones));

		if (idiomas == null) {
			idiomas = traducciones.getIdiomas();
		}

		if (idiomasObligatorios == null) {
			idiomasObligatorios = traducciones.getIdiomas();
		}

		params.put("OBLIGATORIOS", UtilJSON.toJSON(idiomasObligatorios));
		params.put("IDIOMAS", UtilJSON.toJSON(idiomas));

		/** Calculamos la altura según el nº de idiomas a mostrar. **/
		int altura;
		switch (idiomas.size()) {
		case 1:
			altura = 120;
			break;
		case 2:
			altura = 200;
			break;
		case 3:
			altura = 280;
			break;
		case 4:
			altura = 360;
			break;
		default:
			altura = 100;
		}
		UtilJSF.openDialog(DialogTraduccion.class, modoAcceso, params, true, 460, altura);
	}
}
