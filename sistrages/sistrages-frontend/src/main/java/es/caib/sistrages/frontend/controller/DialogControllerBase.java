package es.caib.sistrages.frontend.controller;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.DialogResultMessage;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;

/**
 * Clase de las que heredan los Dialog.
 *
 * @author Indra
 *
 */
public abstract class DialogControllerBase {

	@Inject
	private SystemService systemService;
	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogControllerBase.class);

	/** Modo acceso ventana. */
	protected String modoAcceso;

	/**
	 *
	 * Devuelve logger.
	 *
	 * @return logger
	 */
	protected Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Refresca la cache
	 */
	public void refrescarCache(final String urlBase, final String usuario, final String pwd, final String tipo,
			final String identificador, final boolean mensaje) {

		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, tipo);

		if (mensaje) {
			if (resultado.getCodigo() == 1) {
				addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.refrescar"));
			} else {
				addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("error.refrescar") + ": " + resultado.getMensaje());
			}
		}
	}

	/**
	 * Refresca la cache
	 */
	public void refrescarCache(final String urlBase, final String usuario, final String pwd, final String tipo,
			final String identificador) {
		refrescarCache(urlBase, usuario, pwd, tipo, identificador, false);
	}

	/**
	 * Refresca la cache
	 */
	public ResultadoError refrescar() {

		final String urlBase = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_URL.toString());
		final String usuario = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_USER.toString());
		final String pwd = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_PWD.toString());

		return this.refrescarCacheResult(urlBase, usuario, pwd, Constantes.CACHE_COMPLETA, true);

	}

	/**
	 * Refresca la cache
	 */
	public ResultadoError refrescarCacheResult(final String urlBase, final String usuario, final String pwd,
			final String tipo, final boolean mensaje) {

		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, tipo);

		return resultado;
	}

	/** Es desarrollo? **/
	public boolean isDesarrollo() {
		return UtilJSF.checkEntorno(TypeEntorno.DESARROLLO);
	}

	/**
	 * Indica si el dialogo se abre en modo alta.
	 *
	 * @return boolean
	 */
	public boolean isAlta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.ALTA);
	}

	/**
	 * Indica si el dialogo se abre en modo edicion.
	 *
	 * @return boolean
	 */
	public boolean isEdicion() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.EDICION);
	}

	/**
	 * Indica si el dialogo se abre en modo consulta.
	 *
	 * @return boolean
	 */
	public boolean isConsulta() {
		final TypeModoAcceso modo;
		if (modoAcceso != null) {
			modo = TypeModoAcceso.valueOf(modoAcceso);
		} else {
			modo = null;
		}
		return (modo == TypeModoAcceso.CONSULTA);
	}

	/**
	 * Indica si el dialogo no se abre en modo consulta.
	 *
	 * @return boolean
	 */
	public boolean isNotConsulta() {
		return !isConsulta();
	}

	/**
	 * Obtiene modo acceso.
	 *
	 * @return modo acceso
	 */
	public String getModoAcceso() {
		return modoAcceso;
	}

	/**
	 * Establece modo acceso.
	 *
	 * @param modoAcceso modo acceso
	 */
	public void setModoAcceso(final String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	/**
	 * Ayudar.
	 */
	public void ayudar() {
		addMessageContext(TypeNivelGravedad.INFO, "Ayuda");
	}

	/**
	 * Sirve para actualizar el style del script, por si tiene que marcarse de
	 * colorp orque tiene valor.
	 *
	 * @param script
	 * @return
	 */
	public String getColorScript(final Script script) {
		String className = "";
		if (script != null) {
			className = "scriptRelleno";
		}
		return className;
	}

	/**
	 * Muestra el mensaje. Se encarga de ver si debe mostrarse por el growl del
	 * dialog o del padre.
	 *
	 * @param nivel
	 * @param title
	 * @param message
	 */
	public void addMessageContext(final TypeNivelGravedad nivel, final String title, final String message) {
		if (Constantes.GROWL_PADRE) {
			showMessagePadre(nivel, title, message);
		} else {
			UtilJSF.addMessageContext(nivel, title, message);
		}
	}

	/**
	 * Muestra el mensaje. Se encarga de ver si debe mostrarse por el growl del
	 * dialog o del padre.
	 *
	 * @param nivel
	 * @param title
	 * @param message
	 */
	public void addMessageContext(final TypeNivelGravedad nivel, final String message) {
		if (Constantes.GROWL_PADRE) {
			showMessagePadre(nivel, null, message);
		} else {
			UtilJSF.addMessageContext(nivel, message);
		}
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl,
	 * messages,...).
	 *
	 * @param nivel            Nivel gravedad
	 * @param message          Mensaje
	 * @param validationFailed añade la marca de error de validacion
	 */
	public void addMessageContext(final TypeNivelGravedad nivel, final String message, final boolean validationFailed) {
		addMessageContext(nivel, message, message, validationFailed);
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
	public void addMessageContext(final TypeNivelGravedad nivel, final String message, final String detail,
			final boolean validationFailed) {

		if (Constantes.GROWL_PADRE) {
			showMessagePadre(nivel, message, detail);
			if (validationFailed) {
				FacesContext.getCurrentInstance().validationFailed();
			}

		} else {
			UtilJSF.addMessageContext(nivel, message, detail);

			if (validationFailed) {
				FacesContext.getCurrentInstance().validationFailed();
			}
		}
	}

	/** Método que comprueba si dos scripts son iguales **/
	public boolean isCambioScripts(Script scr1, Script scr2) {
		if (scr1 == null) {
			if (scr2 == null) {
				return false;
			} else {
				return true;
			}
		} else if (scr2 == null) {
			return true;
		} else {
			if (scr1.getMensajes().size() != scr2.getMensajes().size()) {
				return true;
			} else {
				for (int i = 0; i < scr1.getMensajes().size(); i++) {
					if (isCambioLiterales(scr1.getMensajes().get(i).getLiteral(),
							scr2.getMensajes().get(i).getLiteral())) {
						return true;
					}
					if (!scr1.getMensajes().get(i).getIdentificador()
							.equals(scr2.getMensajes().get(i).getIdentificador())) {
						return true;
					}
				}
			}
			if (scr1.getContenido().equals(scr2.getContenido())) {
				return false;
			} else {
				return true;
			}
		}
	}

	/** Método que comprueba si dos literales son iguales **/
	public boolean isCambioLiterales(Literal l1, Literal l2) {
		boolean mod = false;
		if (l1 == null) {
			if (l2 != null) {
				mod = true;
			}
		} else if (l2 == null) {
			mod = true;
		} else {
			if (l1.getIdiomas().size() == l2.getIdiomas().size()) {
				for (String idioma : l1.getIdiomas()) {
					if (l2.getTraduccion(idioma) != null) {
						if (!l1.getTraduccion(idioma).equals(l2.getTraduccion(idioma))) {
							mod = true;
						}
					} else {
						mod = true;
					}
				}
			} else {
				mod = true;
			}
		}
		return mod;
	}

	/**
	 * Método privado que envia el growl al growl padre donde la cabecera.
	 *
	 * @param nivel
	 * @param title
	 * @param message
	 */
	private void showMessagePadre(final TypeNivelGravedad nivel, final String title, final String message) {
		UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_ERRORES_MSG, message);
		UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_ERRORES_NVL, nivel);
		if (title != null) {
			UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_ERRORES_TLE, title);
		}
		final RequestContext context = RequestContext.getCurrentInstance();
		final String evento = "window.parent.document.getElementById('formHeader:hiddenGrowl').click()";
		context.execute(evento);
	}
}
