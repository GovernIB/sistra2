package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n registrar tramite.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionRegistrarTramite extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogDefinicionVersionRegistrarTramite.class);

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Componente service. */
	@Inject
	private ComponenteService componenteService;

	/** Entidad service. */
	@Inject
	private EntidadService entidadService;

	/** Id. **/
	private String id;

	/** Entidad. **/
	private Entidad entidad;

	/** Tramite Paso Registrar. **/
	private TramitePasoRegistrar data;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/** ID tramite version. **/
	private String idTramiteVersion;

	/** Oficinas. **/
	private List<OficinaRegistro> oficinas;

	/** Libros. **/
	private List<LibroOficina> libros;

	/** Plugin registro. **/
	private IRegistroPlugin iplugin;

	/** Idiomas. **/
	private List<String> idiomas;

	/**
	 * Init.
	 */
	public void init() {
		data = (TramitePasoRegistrar) tramiteService.getTramitePaso(Long.valueOf(id));
		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		idiomas = UtilTraducciones.getIdiomas(tramiteVersion.getIdiomasSoportados());
		cargarDatosRegistro();
	}

	/**
	 * Carga los datos de registro.
	 */
	private void cargarDatosRegistro() {
		iplugin = (IRegistroPlugin) componenteService.obtenerPluginEntidad(TypePlugin.REGISTRO, UtilJSF.getIdEntidad());
		entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());
		try {
			oficinas = iplugin.obtenerOficinasRegistro(entidad.getCodigoDIR3(), TypeRegistro.REGISTRO_ENTRADA);

			if (this.data.getCodigoOficinaRegistro() != null) {
				// Es muy marciano, pero por si el cod oficina registro no existe en el listado
				// de oficinas de la entidad, puede pasar
				for (final OficinaRegistro ofi : oficinas) {
					if (this.data.getCodigoOficinaRegistro().equals(ofi.getCodigo())) {
						libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(),
								this.data.getCodigoOficinaRegistro(), TypeRegistro.REGISTRO_ENTRADA);
						break;
					}
				}

			}

		} catch (final RegistroPluginException e) {
			UtilJSF.loggearErrorFront("Error obteniendo informacion de registro", e);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogDefinicionVersionRegistrarTramite.registro.error"));
		}
	}

	/**
	 * Evento on change de oficina que debería de recalcular el libro.
	 *
	 * @param event
	 */
	public void onChangeOficina() {
		try {
			libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(), this.data.getCodigoOficinaRegistro(),
					TypeRegistro.REGISTRO_ENTRADA);
		} catch (final RegistroPluginException e) {
			UtilJSF.loggearErrorFront("Error obteniendo informacion de registro", e);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogDefinicionVersionRegistrarTramite.registro.error"));
		}
	}

	/**
	 * Guarda los datos y cierra el dialog.
	 */
	public void aceptar() {

		if (data.getScriptAlFinalizar() == null && data.isAvisoAlFinalizar()) {
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogDefinicionVersionRegistrarTramite.registro.error.script"));
		} else {
			if (data.isValidaRepresentacion()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogDefinicionVersionRegistrarTramite.check.valida.no.implementado"));
			} else {
				tramiteService.updateTramitePaso(data);

				final DialogResult result = new DialogResult();
				result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
				result.setResult(data);
				UtilJSF.closeDialog(result);
			}
		}
	}

	/**
	 * Cierra el dialog sin guardar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("definicionVersionRegistrarTramiteDialog");
	}

	/**
	 * Editar descripcion
	 */
	public void editarInstPresentacion() {
		UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.EDICION, this.data.getInstruccionesPresentacion(),
				idiomas, idiomas, true);

	}

	/**
	 * Editar descripcion
	 */
	public void editarInstSubsanacion() {
		UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.EDICION, this.data.getInstruccionesSubsanacion(),
				idiomas, idiomas, true);

	}

	/**
	 * Editar descripcion
	 */
	public void editarInstTramitacion() {
		UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.EDICION, this.data.getInstruccionesFinTramitacion(),
				idiomas, idiomas, true);
	}

	/**
	 * Retorno dialogo de instrucciones.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoSubsanacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setInstruccionesSubsanacion((Literal) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo de instrucciones.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoInstrucciones(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setInstruccionesFinTramitacion((Literal) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo de presentacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoPresentacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setInstruccionesPresentacion((Literal) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de presentador
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptPresentador(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptPresentador((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script al finalizar
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptAlFinalizar(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptAlFinalizar((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de registro
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptValidarRegistro(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptValidarRegistrar((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de registro
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptRegistro(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptDestinoRegistro((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de registro
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptRepresentante(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptRepresentante((Script) respuesta.getResult());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Editar script
	 */
	public void editarScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(),
				UtilJSON.toJSON(TypeScriptFlujo.fromString(tipoScript)));
		if (script != null) {
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		}
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
		maps.put(TypeParametroVentana.TRAMITEPASO.toString(), id);

		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 700);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public TramitePasoRegistrar getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final TramitePasoRegistrar data) {
		this.data = data;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	/**
	 * @return the oficinas
	 */
	public List<OficinaRegistro> getOficinas() {
		return oficinas;
	}

	/**
	 * @param oficinas the oficinas to set
	 */
	public void setOficinas(final List<OficinaRegistro> oficinas) {
		this.oficinas = oficinas;
	}

	/**
	 * @return the libros
	 */
	public List<LibroOficina> getLibros() {
		return libros;
	}

	/**
	 * @param libros the libros to set
	 */
	public void setLibros(final List<LibroOficina> libros) {
		this.libros = libros;
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
	 * @return the entidad
	 */
	public final Entidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public final void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
	}
}
