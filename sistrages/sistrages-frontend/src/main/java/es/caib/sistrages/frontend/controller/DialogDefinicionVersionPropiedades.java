/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeScript;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDefinicionVersionPropiedades extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private Long id;

	/** tramite version. */
	private TramiteVersion tramiteVersion;

	/** tramite version idioma Es soportado. */
	private boolean tramiteVersionIdiomaEsSoportado;

	/** tramite version idioma Ca soportado. */
	private boolean tramiteVersionIdiomaCaSoportado;

	/** tramite version idioma En soportado. */
	private boolean tramiteVersionIdiomaEnSoportado;

	/** tramite version idioma De soportado. */
	private boolean tramiteVersionIdiomaDeSoportado;

	/**
	 * Inicialización.
	 */
	public void init() {

		/* recuperamos los datos */
		tramiteVersion = tramiteService.getTramiteVersion(id);
		final String idiomas = tramiteVersion.getIdiomasSoportados();
		if (idiomas.contains("ca")) {
			this.tramiteVersionIdiomaCaSoportado = true;
		}
		if (idiomas.contains("es")) {
			this.tramiteVersionIdiomaEsSoportado = true;
		}
		if (idiomas.contains("en")) {
			this.tramiteVersionIdiomaEnSoportado = true;
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Realizamos alta o update
		if (modoAcceso != null && TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION) {
			if (!tramiteVersionIdiomaEsSoportado && !tramiteVersionIdiomaCaSoportado
					&& !tramiteVersionIdiomaEnSoportado) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.idioma"));
				return;
			}

			if (!this.getTramiteVersion().isAutenticado() && !this.getTramiteVersion().isNoAutenticado()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.tipoAutenticacion"));
				return;
			}

			String idiomas = "";
			if (tramiteVersionIdiomaEsSoportado) {
				idiomas += "es;";
			}
			if (tramiteVersionIdiomaCaSoportado) {
				idiomas += "ca;";
			}
			if (tramiteVersionIdiomaEnSoportado) {
				idiomas += "en;";
			}

			idiomas = idiomas.substring(0, idiomas.length() - 1); // Quitamos el ; del final
			tramiteVersion.setIdiomasSoportados(idiomas);
			tramiteService.updateTramiteVersion(tramiteVersion);
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(this.tramiteVersion);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Editar Script personalizacion.
	 */
	public void editarScriptPersonalizacion() {

		cargarDialogScript(TypeScriptFlujo.SCRIPT_PERSONALIZACION_TRAMITE,
				this.tramiteVersion.getScriptPersonalizacion());

	}

	/**
	 * Editar Script inicialización.
	 */
	public void editarScriptInicializacion() {

		cargarDialogScript(TypeScriptFlujo.SCRIPT_PARAMETROS_INICIALES,
				this.tramiteVersion.getScriptInicializacionTramite());

	}

	/**
	 * Retorno dialogo del script params iniciales.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoParamsIniciales(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				this.tramiteVersion.setScriptInicializacionTramite((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}

	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoPersonalizacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				this.tramiteVersion.setScriptPersonalizacion((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	// ------- FUNCIONES PRIVADAS ------------------------------
	/**
	 * Método que se encarga de cargar el dialog de carga dependiendo de si existe
	 * ya el script o no.
	 *
	 * @param id
	 */
	private void cargarDialogScript(final TypeScript typeScript, final Script iScript) {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.TIPO_SCRIPT.toString(), typeScript.name());
		if (id == null || iScript == null) {
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, params, true, 950, 670);
		} else {
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(iScript));
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, params, true, 950, 670);
		}
	}

	// ------- GETTERS / SETTERS --------------------------------
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
	 * Obtiene el valor de tramiteVersion.
	 *
	 * @return el valor de tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * Establece el valor de tramiteVersion.
	 *
	 * @param tramiteVersion
	 *            el nuevo valor de tramiteVersion
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * Verifica si el idioma Es está soportado.
	 *
	 * @return true, si es tramite version idioma es soportado
	 */
	public boolean isTramiteVersionIdiomaEsSoportado() {
		return tramiteVersionIdiomaEsSoportado;
	}

	/**
	 * Establece el valor de tramiteVersionIdiomaEsSoportado.
	 *
	 * @param tramiteVersionIdiomaEsSoportado
	 *            el nuevo valor de tramiteVersionIdiomaEsSoportado
	 */
	public void setTramiteVersionIdiomaEsSoportado(final boolean tramiteVersionIdiomaEsSoportado) {
		this.tramiteVersionIdiomaEsSoportado = tramiteVersionIdiomaEsSoportado;
	}

	/**
	 * Verifica si el idioma Ca está soportado.
	 *
	 * @return true, si es tramite version idioma ca soportado
	 */
	public boolean isTramiteVersionIdiomaCaSoportado() {
		return tramiteVersionIdiomaCaSoportado;
	}

	/**
	 * Establece el valor de tramiteVersionIdiomaCaSoportado.
	 *
	 * @param tramiteVersionIdiomaCaSoportado
	 *            el nuevo valor de tramiteVersionIdiomaCaSoportado
	 */
	public void setTramiteVersionIdiomaCaSoportado(final boolean tramiteVersionIdiomaCaSoportado) {
		this.tramiteVersionIdiomaCaSoportado = tramiteVersionIdiomaCaSoportado;
	}

	/**
	 * Verifica si el idioma En está soportado.
	 *
	 * @return true, si es tramite version idioma en soportado
	 */
	public boolean isTramiteVersionIdiomaEnSoportado() {
		return tramiteVersionIdiomaEnSoportado;
	}

	/**
	 * Establece el valor de tramiteVersionIdiomaEnSoportado.
	 *
	 * @param tramiteVersionIdiomaEnSoportado
	 *            el nuevo valor de tramiteVersionIdiomaEnSoportado
	 */
	public void setTramiteVersionIdiomaEnSoportado(final boolean tramiteVersionIdiomaEnSoportado) {
		this.tramiteVersionIdiomaEnSoportado = tramiteVersionIdiomaEnSoportado;
	}

	/**
	 * Verifica si el idioma De está soportado.
	 *
	 * @return true, si es tramite version idioma de soportado
	 */
	public boolean isTramiteVersionIdiomaDeSoportado() {
		return tramiteVersionIdiomaDeSoportado;
	}

	/**
	 * Establece el valor de tramiteVersionIdiomaDeSoportado.
	 *
	 * @param tramiteVersionIdiomaDeSoportado
	 *            el nuevo valor de tramiteVersionIdiomaDeSoportado
	 */
	public void setTramiteVersionIdiomaDeSoportado(final boolean tramiteVersionIdiomaDeSoportado) {
		this.tramiteVersionIdiomaDeSoportado = tramiteVersionIdiomaDeSoportado;
	}

}
