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

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeCampoFichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
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

	/**
	 * Servicio.
	 */
	@Inject
	private EntidadService entidadService;

	/** Datos elemento. */
	private Entidad data;

	/** Id entidad. */
	private Long idEntidad;

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

		final String message = UtilJSF.getLiteral("info.modificado.ok");
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

	}

	/**
	 * Cancelar.
	 */
	public void refrescar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Sin implementar");
	}

	/**
	 * Abre explorar asistente pie.
	 */
	public void explorarAssistentPie() {
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		UtilTraducciones.openDialogTraduccion(modoAccesoDlg, data.getPie(), idiomas, idiomas);
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
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		UtilTraducciones.openDialogTraduccion(modoAccesoDlg, data.getUrlCarpetaCiudadana(), idiomas, idiomas);
	}

	/**
	 * Abre explorar lopd.
	 */
	public void explorarLopd() {
		TypeModoAcceso modoAccesoDlg = TypeModoAcceso.CONSULTA;
		if (getPermiteEditar()) {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		UtilTraducciones.openDialogTraduccion(modoAccesoDlg, data.getLopd(), idiomas, idiomas);
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
	 * Retorno dialogo fichero.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoFichero(final SelectEvent event) {
		// recupera datos entidad activa
		setData(entidadService.loadEntidad(idEntidad));
		UtilJSF.getSessionBean().refrescarEntidad();
	}

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

	public void descargaFichero(final Fichero fichero) {
		if (fichero != null && fichero.getId() != null) {
			UtilJSF.redirectJsfPage(Constantes.DESCARGA_FICHEROS_URL + "?id=" + fichero.getId());
		}
	}

	public void gestionFicheroLogoGestor() {
		gestionFichero(TypeCampoFichero.LOGO_GESTOR_ENTIDAD);
	}

	public void gestionFicheroLogoAsistente() {
		gestionFichero(TypeCampoFichero.LOGO_ASISTENTE_ENTIDAD);
	}

	public void gestionFicheroCssAsistente() {
		gestionFichero(TypeCampoFichero.CSS_ENTIDAD);
	}

	private void gestionFichero(final TypeCampoFichero campoFichero) {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(data.getId()));
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
	 * @param data
	 *            el nuevo valor de data
	 */
	public void setData(final Entidad data) {
		this.data = data;
	}

}
