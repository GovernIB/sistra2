/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeCampoFichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de configuracion entidad.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewConfiguracionEntidad extends ViewControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewConfiguracionEntidad.class);

	/** Entidad service. **/
	@Inject
	private EntidadService entidadService;

	/** System service. **/
	@Inject
	private SystemService systemService;

	/** Componente service. */
	@Inject
	private ComponenteService componenteService;

	/** Datos elemento. */
	private Entidad data;

	/** Id entidad. */
	private Long idEntidad;

	/** Oficinas. **/
	private List<OficinaRegistro> oficinas;

	/**
	 * Inicializacion.
	 */
	public void init() {

		// Entidad activa
		idEntidad = UtilJSF.getIdEntidad();
		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		// recupera datos entidad activa
		setData(entidadService.loadEntidad(idEntidad));

		try {
			final IRegistroPlugin iplugin = (IRegistroPlugin) componenteService
					.obtenerPluginEntidad(TypePlugin.REGISTRO, UtilJSF.getIdEntidad());
			oficinas = iplugin.obtenerOficinasRegistro(data.getCodigoDIR3(), TypeRegistro.REGISTRO_ENTRADA);
		} catch (final Exception e) {
			UtilJSF.loggearErrorFront("Error obteniendo informacion de registro", e);
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogDefinicionVersionRegistrarTramite.registro.error"));
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Verificamos opciones ayuda
		if (data.isEmailHabilitado() && StringUtils.isBlank(data.getEmail())) {
			final String message = UtilJSF.getLiteral("viewConfiguracionEntidad.error.faltaEmail");
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, message);
			return;
		}
		if (data.isTelefonoHabilitado() && StringUtils.isBlank(data.getTelefono())) {
			final String message = UtilJSF.getLiteral("viewConfiguracionEntidad.error.faltaTelSuport");
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, message);
			return;
		}
		if (data.isUrlSoporteHabilitado() && StringUtils.isBlank(data.getUrlSoporte())) {
			final String message = UtilJSF.getLiteral("viewConfiguracionEntidad.error.urlSuport");
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, message);
			return;
		}
		if (data.isFormularioIncidenciasHabilitado()
				&& entidadService.listOpcionesFormularioSoporte(idEntidad).isEmpty()) {
			final String message = UtilJSF.getLiteral("viewConfiguracionEntidad.error.formulariCont");
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, message);
			return;
		}
		if (data.getDiasPreregistro() == null) {
			final String message = UtilJSF.getLiteral("viewConfiguracionEntidad.error.diasPrereg");
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, message);
			return;
		}
		// Guardar cambios de la entidad.
		entidadService.updateEntidadAdministradorEntidad(data);

		String message = "";
		ResultadoError re = this.refrescar();
		if (re.getCodigo() != 1) {
			message = UtilJSF.getLiteral("info.modificado.ok") + ". " + UtilJSF.getLiteral("error.refrescarCache")
					+ ": " + re.getMensaje();
		} else {
			message = UtilJSF.getLiteral("info.modificado.ok") + ". " + UtilJSF.getLiteral("info.cache.ok");
		}
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

	}

	/**
	 * Abrir ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("configuracionEntidad");
	}

	private void explorarLiteral(final Literal pLiteral) {
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}
		UtilTraducciones.openDialogTraduccion(modoAccesoDlg, pLiteral, UtilJSF.getSessionBean().getIdiomas(),
				UtilJSF.getSessionBean().getIdiomas(), true, modoAcceso, modoAcceso);
	}

	/**
	 * Abre explorar asistente pie.
	 */
	public void explorarAssistentPie() {
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}
		UtilTraducciones.openDialogTraduccionHTML(modoAccesoDlg, data.getPie(), UtilJSF.getSessionBean().getIdiomas(),
				UtilJSF.getSessionBean().getIdiomas(), true);
	}

	/**
	 * Gestión de retorno asistente pie.
	 *
	 * @param event
	 */
	public void returnDialogoAsistentePie(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setPie(literales);
		}
	}

	/**
	 * Abre explorar url carpeta ciudadana.
	 */
	public void explorarUrlCarpetaCiudadana() {
		explorarLiteral(data.getUrlCarpetaCiudadana());
	}

	/**
	 * Abre explorar titulo asistente.
	 */
	public void explorarTituloAsistente() {
		explorarLiteral(data.getTituloAsistenteTramitacion());
	}

	/**
	 * Abre explorar url sede.
	 */
	public void explorarUrlSedeElectronica() {
		explorarLiteral(data.getUrlSede());
	}

	/**
	 * Gestión de retorno url carpeta ciudadana.
	 *
	 * @param event
	 */
	public void returnDialogoUrlCarpetaCiudadana(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setUrlCarpetaCiudadana(literales);
		}
	}

	/**
	 * Gestión de retorno titulo asistente
	 *
	 * @param event
	 */
	public void returnDialogoTituloAsistente(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setTituloAsistenteTramitacion(literales);
		}
	}

	/**
	 * Gestión de retorno url carpeta ciudadana.
	 *
	 * @param event
	 */
	public void returnDialogoUrlSedeElectronica(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setUrlSede(literales);
		}
	}

	/**
	 * Abre explorar lopd.
	 */
	public void explorarLopd() {
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}
		UtilTraducciones.openDialogTraduccionHTML(modoAccesoDlg, data.getLopd(), UtilJSF.getSessionBean().getIdiomas(),
				UtilJSF.getSessionBean().getIdiomas(), true);

	}

	/**
	 * Abre explorar lopd.
	 */
	public void explorarPlantillaMail() {
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.idEntidad));

		UtilJSF.openDialog(DialogPlantillaEntidad.class, modoAccesoDlg, params, true, 380, 200);
	}

	/**
	 * Gestión de retorno lopd.
	 *
	 * @param event
	 */
	public void returnDialogoLopd(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setLopd(literales);
		}
	}

	/**
	 * Abre explorar instrucciones subsanacion.
	 */
	public void explorarInstruccionesSubsanacion() {
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}
		UtilTraducciones.openDialogTraduccionHTML(modoAccesoDlg, data.getInstruccionesSubsanacion(),
				UtilJSF.getSessionBean().getIdiomas(), UtilJSF.getSessionBean().getIdiomas(), true);

	}

	/**
	 * Gestión de retorno instrucciones subsansacion.
	 *
	 * @param event
	 */
	public void returnDialogoInstruccionesSubsanacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setInstruccionesSubsanacion(literales);
		}
	}

	/**
	 * Abre explorar instrucciones presencial.
	 */
	public void explorarInstruccionesPresencial() {
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}
		UtilTraducciones.openDialogTraduccionHTML(modoAccesoDlg, data.getInstruccionesPresencial(),
				UtilJSF.getSessionBean().getIdiomas(), UtilJSF.getSessionBean().getIdiomas(), true);

	}

	/**
	 * Gestión de retorno instrucciones subsansacion.
	 *
	 * @param event
	 */
	public void returnDialogoInstruccionesPresencial(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setInstruccionesPresencial(literales);
		}
	}

	/**
	 * Abre explorar MapaWeb.
	 */
	public void explorarMapaWeb() {
		explorarLiteral(data.getMapaWeb());
	}

	/**
	 * Gestión de retorno MapaWeb.
	 *
	 * @param event
	 */
	public void returnDialogoMapaWeb(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setMapaWeb(literales);
		}
	}

	/**
	 * Abre explorar AvisoLegal.
	 */
	public void explorarAvisoLegal() {
		explorarLiteral(data.getAvisoLegal());
	}

	/**
	 * Gestión de retorno AvisoLegal.
	 *
	 * @param event
	 */
	public void returnDialogoAvisoLegal(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setAvisoLegal(literales);
		}
	}

	/**
	 * Abre explorar AvisoLegal.
	 */
	public void explorarRss() {
		explorarLiteral(data.getRss());
	}

	/**
	 * Gestión de retorno AvisoLegal.
	 *
	 * @param event
	 */
	public void returnDialogoRss(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setRss(literales);
		}
	}

	/**
	 * Retorno dialogo fichero.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoFichero(final SelectEvent event) {
		// recupera datos entidad activa
		setData(entidadService.loadEntidad(idEntidad));
		UtilJSF.getSessionBean().refrescarEntidad();
	}

	/**
	 * Abre el dialog con los formularios de soporte.
	 */
	public void abrirFormularioSoporte() {
		// Muestra dialogo
		TypeModoAcceso modo = null;

		if (getPermiteEditar()) {
			modo = TypeModoAcceso.ALTA;
		} else {
			modo = TypeModoAcceso.CONSULTA;
		}
		UtilJSF.openDialog(ViewFormularioSoporte.class, modo, null, true, 850, 350);
	}

	/** Abre el dialog con las valoraciones predefinidas. */
	public void abrirValoraciones() {
		// Muestra dialogo
		TypeModoAcceso modo = null;

		if (getPermiteEditar()) {
			modo = TypeModoAcceso.ALTA;
		} else {
			modo = TypeModoAcceso.CONSULTA;
		}
		UtilJSF.openDialog(DialogValoraciones.class, modo, null, true, 850, 350);
	}

	/**
	 * Descarga fichero
	 *
	 * @param fichero
	 */
	public void descargaFichero(final Fichero fichero) {
		if (fichero != null && fichero.getCodigo() != null) {
			UtilJSF.redirectJsfPage(Constantes.DESCARGA_FICHEROS_URL + "?id=" + fichero.getCodigo());
		}
	}

	/** Gestion de fichero del logo gestor. **/
	public void gestionFicheroLogoGestor() {
		gestionFichero(TypeCampoFichero.LOGO_GESTOR_ENTIDAD);
	}

	/** Gestion de fichero del logo asistente. **/
	public void gestionFicheroLogoAsistente() {
		gestionFichero(TypeCampoFichero.LOGO_ASISTENTE_ENTIDAD);
	}

	/** Gestion de fichero del css asistente. **/
	public void gestionFicheroCssAsistente() {
		gestionFichero(TypeCampoFichero.CSS_ENTIDAD);
	}

	/** Gestion de fichero del css asistente. **/
	public void gestionFicheroIconoAsistente() {
		gestionFichero(TypeCampoFichero.ICONO_ASISTENTE_ENTIDAD);
	}

	/**
	 * Método unificado para gestionar ficheros.
	 *
	 * @param campoFichero
	 */
	private void gestionFichero(final TypeCampoFichero campoFichero) {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(data.getCodigo()));
		params.put(TypeParametroVentana.CAMPO_FICHERO.toString(), campoFichero.toString());
		UtilJSF.openDialog(DialogFichero.class, TypeModoAcceso.EDICION, params, true, 750, 350);
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditar() {
		return (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
	}

	/**
	 * Obtiene el valor de data.
	 *
	 * @return el valor de data
	 */
	public Entidad getData() {
		return data;
	}

	/**
	 * Establece el valor de data.
	 *
	 * @param data el nuevo valor de data
	 */
	public void setData(final Entidad data) {
		this.data = data;
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

}
