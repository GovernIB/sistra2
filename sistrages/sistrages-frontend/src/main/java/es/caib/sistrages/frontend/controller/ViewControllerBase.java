/*
 *
 */
package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;

/**
 * Clase de las que heredan los View.
 *
 * @author Indra
 *
 */
public abstract class ViewControllerBase {
	@Inject
	private SystemService systemService;
	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewControllerBase.class);

	/**
	 * Modo de acceso a la ventana.
	 */
	protected String modoAcceso;

	/**
	 * Referencia al bean de sesion
	 */
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sesion;

	/**
	 * Refresca la cache
	 */
	public void refrescarCache(final String urlBase, final String usuario, final String pwd, final String tipo,
			final String identificador) {
		refrescarCache(urlBase, usuario, pwd, tipo, identificador, true);
	}

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

	/**
	 * Refresca la cache
	 */
	public void refrescarCache(final String urlBase, final String usuario, final String pwd, final String tipo,
			final String identificador, final boolean mostrarMensaje) {

		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, tipo, identificador);
		if (mostrarMensaje) {
			if (resultado.getCodigo() == 1) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.refrescar"));
			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("error.refrescar") + ": " + resultado.getMensaje());
			}
		}
	}

	/**
	 * Refresca la cache devoliviendo resultado
	 */
	public ResultadoError refrescarCacheResultado(final String urlBase, final String usuario, final String pwd,
			final String tipo, final String identificador) {

		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, tipo, identificador);

		return resultado;
	}

	/**
	 * Obtiene el valor del bean de sesion.
	 *
	 * @return el valor del bean de sesion
	 */
	public SessionBean getSesion() {
		return sesion;
	}

	/**
	 * Establece el valor del bean de sesion.
	 *
	 * @param sesion el nuevo valor del bean de sesion
	 */
	public void setSesion(final SessionBean sesion) {
		this.sesion = sesion;
	}

	/**
	 * Establece el valor del titulo de la pantalla.
	 *
	 * @param titulo el nuevo valor del titulo de la pantalla.
	 */
	public void setLiteralTituloPantalla(final String titulo) {
		sesion.setLiteralTituloPantalla(titulo);
	}

	/**
	 * Devuelve logger.
	 *
	 * @return logger
	 */
	protected final Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Ventana principal de ayudar.
	 *
	 * @param event resultado
	 */
	public void ayudar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Ayuda");
	}

	/**
	 * Indica si la ventana se abre en modo alta.
	 *
	 * @return boolean
	 */
	public boolean isAlta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.ALTA);
	}

	/**
	 * Indica si la ventana se abre en modo edicion.
	 *
	 * @return boolean
	 */
	public boolean isEdicion() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.EDICION);
	}

	/**
	 * Indica si la ventana se abre en modo consulta.
	 *
	 * @return boolean
	 */
	public boolean isConsulta() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.CONSULTA);
	}

	/**
	 * Obtiene modo acceso a la ventana.
	 *
	 * @return modo acceso
	 */
	public String getModoAcceso() {
		return modoAcceso;
	}

	/**
	 * Establece modo acceso a la ventana.
	 *
	 * @param modoAcceso modo acceso
	 */
	public void setModoAcceso(final String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	/**
	 * Normaliza filtro
	 *
	 * @param filtro Filtro
	 * @return filtro
	 */
	protected String normalizarFiltro(String filtro) {
		if (filtro != null && StringUtils.isBlank(filtro)) {
			filtro = null;
		}
		return filtro;
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

	/** Es desarrollo? **/
	public boolean isDesarrollo() {
		return UtilJSF.checkEntorno(TypeEntorno.DESARROLLO);
	}

	/** Es desarrollo? **/
	public boolean isNotDesarrollo() {
		return !UtilJSF.checkEntorno(TypeEntorno.DESARROLLO);
	}

	/** Es preproduccion? **/
	public boolean isPreproduccion() {
		return UtilJSF.checkEntorno(TypeEntorno.PREPRODUCCION);
	}

	/** Es preproduccion? **/
	public boolean isProduccion() {
		return UtilJSF.checkEntorno(TypeEntorno.PRODUCCION);
	}
}
