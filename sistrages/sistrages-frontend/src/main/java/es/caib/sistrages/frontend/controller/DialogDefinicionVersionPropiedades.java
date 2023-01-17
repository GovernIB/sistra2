/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAutenticacion;
import es.caib.sistrages.core.api.model.types.TypePaso;
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
import es.caib.sistrages.frontend.util.UtilTraducciones;

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

	/** tramite version Inicial. */
	private TramiteVersion tramiteVersionI;

	/** tramite version idioma Es soportado. */
	private boolean tramiteVersionIdiomaEsSoportado;

	/** tramite version idioma Ca soportado. */
	private boolean tramiteVersionIdiomaCaSoportado;

	/** tramite version idioma En soportado. */
	private boolean tramiteVersionIdiomaEnSoportado;

	/** tramite version idioma De soportado. */
	private boolean tramiteVersionIdiomaDeSoportado;

	/** Idiomas. **/
	private List<String> idiomas;

	/** Tipos de autenticación **/
	private TypeAutenticacion[] tiposAutenticacion;

	/** Tipos de autenticacion **/
	private boolean tiposAutenticacionCER;
	private boolean tiposAutenticacionPIN;
	private boolean tiposAutenticacionPER;

	private boolean cambios = false;

	private String portapapeles;

	private int valMin;

	/**
	 * Inicialización.
	 */
	public void init() {

		/* recuperamos los datos */
		tramiteVersion = tramiteService.getTramiteVersion(id);
		tramiteVersionI = tramiteService.getTramiteVersion(id);
		if (tramiteVersion.isPersistenciaInfinita()) {
			valMin = 0;
		} else {
			valMin = 1;
		}
		idiomas = UtilTraducciones.getIdiomas(tramiteVersion.getIdiomasSoportados());
		if (idiomas.contains("ca") && UtilJSF.getSessionBean().getIdiomas().contains("ca")) {
			this.tramiteVersionIdiomaCaSoportado = true;
		}
		if (idiomas.contains("es") && UtilJSF.getSessionBean().getIdiomas().contains("es")) {
			this.tramiteVersionIdiomaEsSoportado = true;
		}
		if (idiomas.contains("en") && UtilJSF.getSessionBean().getIdiomas().contains("en")) {
			this.tramiteVersionIdiomaEnSoportado = true;
		}
		tiposAutenticacion = TypeAutenticacion.values();
		tiposAutenticacionCER = tramiteVersion.tieneTipoAutenticacion(TypeAutenticacion.CERTIFICADO.toString());
		tiposAutenticacionPIN = tramiteVersion.tieneTipoAutenticacion(TypeAutenticacion.CLAVE_PIN.toString());
		tiposAutenticacionPER = tramiteVersion.tieneTipoAutenticacion(TypeAutenticacion.CLAVE_PERMANENTE.toString());
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Realizamos alta o update
		if (modoAcceso != null && TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION) {
			if (!tramiteVersionIdiomaEsSoportado && !tramiteVersionIdiomaCaSoportado
					&& !tramiteVersionIdiomaEnSoportado) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.idioma"));
				return;
			}

			/*
			 * if (this.getTramiteVersion().getDescripcion().trim().isEmpty() ||
			 * this.getTramiteVersion().getDescripcion() == null) {
			 * addMessageContext(TypeNivelGravedad.WARNING,
			 * UtilJSF.getLiteral("warning.descripcionNull")); return; }
			 */

			if (!this.getTramiteVersion().isAutenticado() && !this.getTramiteVersion().isNoAutenticado()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.tipoAutenticacion"));
				return;
			}

			// Anyadido restricción para que cuando está persistencia y no es restricción
			// infinita (por días), tenga que tener valor el num. dias
			if (this.getTramiteVersion().isPersistencia() && !this.getTramiteVersion().isPersistenciaInfinita()
					&& this.getTramiteVersion().getPersistenciaDias() == null) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.numdias"));
				return;
			}

			if (this.getTramiteVersion().isAutenticado() && !tiposAutenticacionCER && !tiposAutenticacionPIN
					&& !tiposAutenticacionPER) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("warning.tipoautenticacion.obligatorio"));
				return;
			}

			if (this.getTramiteVersion().getTipoTramite().equals("T")) {
				this.getTramiteVersion().setNoAutenticado(false);
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
			final List<TypeAutenticacion> tipos = new ArrayList<>();
			if (tiposAutenticacionCER) {
				tipos.add(TypeAutenticacion.CERTIFICADO);
			}
			if (tiposAutenticacionPIN) {
				tipos.add(TypeAutenticacion.CLAVE_PIN);
			}
			if (tiposAutenticacionPER) {
				tipos.add(TypeAutenticacion.CLAVE_PERMANENTE);
			}
			tramiteVersion.setTiposAutenticacion(tipos);
			if (tramiteVersion.getTipoTramite().equals("T")) {

				List<TramitePaso> pasos = tramiteService.getTramitePasos(tramiteVersion.getCodigo());
				for (TramitePaso paso : pasos) {
					if (paso.getTipo().equals(TypePaso.FINALIZAR)) {
						TramitePasoRegistrar reg = (TramitePasoRegistrar) paso;
						reg.setDestino("R");
						tramiteService.updateTramitePaso(reg);
					}
				}
			}
			if (cambios) {
				tramiteService.actualizarFechaTramiteVersion(tramiteVersion.getCodigo(),
						UtilJSF.getSessionBean().getUserName(), "Modificación propiedades");
			}
			tramiteService.updateTramiteVersion(tramiteVersion);
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(this.tramiteVersion);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Lanzar aviso.
	 */
	public void lanzarAviso() {
		addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noAut"));
	}

	/**
	 * Lanzar aviso.
	 */
	public void lanzarAviso2() {
		addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.servicio"));
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
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("definicionVersionPropiedadesDialog");
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
	 * @param event respuesta dialogo
	 */
	public void returnDialogoParamsIniciales(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				this.tramiteVersion.setScriptInicializacionTramite((Script) respuesta.getResult());
				if (tramiteVersionI != null && tramiteVersion != null) {
					if (this.isCambioScripts(tramiteVersion.getScriptInicializacionTramite(),
							tramiteVersionI.getScriptInicializacionTramite())) {
						cambios = true;
					}
				} else if (tramiteVersionI == null) {
					if (tramiteVersion != null) {
						cambios = true;
					}
				} else {
					if (tramiteVersionI != null) {
						cambios = true;
					}
				}
				break;
			default:
				break;
			}
		}

	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoPersonalizacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				this.tramiteVersion.setScriptPersonalizacion((Script) respuesta.getResult());
				if (tramiteVersionI != null && tramiteVersion != null) {
					if (this.isCambioScripts(tramiteVersion.getScriptPersonalizacion(),
							tramiteVersionI.getScriptPersonalizacion())) {
						cambios = true;
					}
				} else if (tramiteVersionI == null) {
					if (tramiteVersion != null) {
						cambios = true;
					}
				} else {
					if (tramiteVersionI != null) {
						cambios = true;
					}
				}
				break;
			default:
				break;
			}
		}
	}

	public void check() {
		tiposAutenticacionCER = tiposAutenticacionPIN = tiposAutenticacionPER = tramiteVersion.isAutenticado();
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
		params.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(), UtilJSON.toJSON(typeScript));
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), id.toString());
		if (id == null || iScript == null) {
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, params, true, 700);
		} else {
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(iScript));
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, params, true, 700);
		}
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	// ------- GETTERS / SETTERS --------------------------------

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

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
	 * @param id el nuevo valor de id
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
	 * @param tramiteVersion el nuevo valor de tramiteVersion
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
	 * @param tramiteVersionIdiomaEsSoportado el nuevo valor de
	 *                                        tramiteVersionIdiomaEsSoportado
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
	 * @param tramiteVersionIdiomaCaSoportado el nuevo valor de
	 *                                        tramiteVersionIdiomaCaSoportado
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
	 * @param tramiteVersionIdiomaEnSoportado el nuevo valor de
	 *                                        tramiteVersionIdiomaEnSoportado
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
	 * @param tramiteVersionIdiomaDeSoportado el nuevo valor de
	 *                                        tramiteVersionIdiomaDeSoportado
	 */
	public void setTramiteVersionIdiomaDeSoportado(final boolean tramiteVersionIdiomaDeSoportado) {
		this.tramiteVersionIdiomaDeSoportado = tramiteVersionIdiomaDeSoportado;
	}

	/**
	 * @return the idiomas
	 */
	public List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas the idiomas to set
	 */
	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * @return the tiposAutenticacion
	 */
	public TypeAutenticacion[] getTiposAutenticacion() {
		return tiposAutenticacion;
	}

	/**
	 * @param tiposAutenticacion the tiposAutenticacion to set
	 */
	public void setTiposAutenticacion(final TypeAutenticacion[] tiposAutenticacion) {
		this.tiposAutenticacion = tiposAutenticacion;
	}

	/**
	 * @return the tiposAutenticacionCER
	 */
	public final boolean isTiposAutenticacionCER() {
		return tiposAutenticacionCER;
	}

	/**
	 * @param tiposAutenticacionCER the tiposAutenticacionCER to set
	 */
	public final void setTiposAutenticacionCER(final boolean tiposAutenticacionCER) {
		this.tiposAutenticacionCER = tiposAutenticacionCER;
	}

	/**
	 * @return the tiposAutenticacionPIN
	 */
	public final boolean isTiposAutenticacionPIN() {
		return tiposAutenticacionPIN;
	}

	/**
	 * @param tiposAutenticacionPIN the tiposAutenticacionPIN to set
	 */
	public final void setTiposAutenticacionPIN(final boolean tiposAutenticacionPIN) {
		this.tiposAutenticacionPIN = tiposAutenticacionPIN;
	}

	/**
	 * @return the tiposAutenticacionPER
	 */
	public final boolean isTiposAutenticacionPER() {
		return tiposAutenticacionPER;
	}

	/**
	 * @param tiposAutenticacionPER the tiposAutenticacionPER to set
	 */
	public final void setTiposAutenticacionPER(final boolean tiposAutenticacionPER) {
		this.tiposAutenticacionPER = tiposAutenticacionPER;
	}

	public void setCambios() {
		this.cambios = true;
		if (this.getTramiteVersion().getTipoTramite().equals("T")) {
			PrimeFaces.current().executeScript(
					" function setVacio(){ let check1 = document.getElementById(\"formDialogDefinicionVersionPropiedades:sinAutenticacion\"); check1.children[1].classList.remove(\"ui-state-active\"); check1.children[1].children[0].classList.remove(\"ui-icon\"); check1.children[1].children[0].classList.remove(\"ui-icon-check\"); } setVacio();");
		}

		if (tramiteVersion.isPersistenciaInfinita()) {
			valMin = 0;
			tramiteVersion.setPersistenciaDias(null);
		} else if (!tramiteVersion.isPersistenciaInfinita()) {
			valMin = 1;
		}
	}

	public boolean isServicioActivado() {
		return UtilJSF.isServicioActivado();
	}

	/**
	 * @return the valMin
	 */
	public final int getValMin() {
		return valMin;
	}

	/**
	 * @param valMin the valMin to set
	 */
	public final void setValMin(int valMin) {
		this.valMin = valMin;
	}

}
