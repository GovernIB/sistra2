package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroDialogo;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de configuracion entidad.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewConfiguracionEntidad extends ViewControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private Entidad data;

	/**
	 * Inicializacion.
	 */
	public void init() {

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		data = new Entidad();
		data.setActivo(true);
		data.setEmail("prueba@caib.es");
		data.setLogoAsistente(new Fichero(1l, "logoAsistente.png"));
		data.setLogoGestor(new Fichero(2l, "logoGestor.png"));
		data.setCss(new Fichero(3l, "estilo.css"));
		data.setPie(new Fichero(4l, "pie.html"));
		data.setTelefono("telefono");
		data.setTelefonoHabilitado(true);
		data.setEmailHabilitado(true);
		data.setFormularioIncidenciasHabilitado(true);
		data.setUrlSoporte("http://soporte.caib.es");
		data.setUrlSoporteHabilitado(true);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (data.isTelefonoHabilitado() && (data.getTelefono() == null || data.getTelefono().isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Telefono activado y sin rellenar");
			return;
		}

		if (data.isEmailHabilitado() && (data.getEmail() == null || data.getEmail().isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Email activado y sin rellenar");
			return;
		}

		if (data.isFormularioIncidenciasHabilitado()
				&& (data.getFormularioIncidencias() == null || data.getFormularioIncidencias().isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Formulario activado y sin rellenar");
			return;
		}

		if (data.isUrlSoporteHabilitado() && (data.getUrlSoporte() == null || data.getUrlSoporte().isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Url soporte activado y sin rellenar");
			return;
		}

		// Guardar cambios de la entidad.

	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		// Inicializar datos con los de la base de datos, o llamar a la misma url.
	}

	/**
	 * Abre explorar gestion logo.
	 */
	public void explorarGestorLogo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
		// Muestra dialogo
		/*
		 * final Map<String, String> params = new HashMap<>();
		 * params.put(TypeParametroDialogo.ID.toString(),
		 * String.valueOf(this.data.getId())); UtilJSF.openDialog(DialogFichero.class,
		 * TypeModoAcceso.EDICION, params, true, 740, 650);
		 */

	}

	/**
	 * Abre explorar asistente logo.
	 */
	public void explorarAssistentLogo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
		// Muestra dialogo
		/*
		 * final Map<String, String> params = new HashMap<>();
		 * params.put(TypeParametroDialogo.ID.toString(),
		 * String.valueOf(this.data.getId())); UtilJSF.openDialog(DialogFichero.class,
		 * TypeModoAcceso.EDICION, params, true, 740, 650);
		 */

	}

	/**
	 * Abre explorar asistente css.
	 */
	public void explorarAssistentCss() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
		// Muestra dialogo
		/*
		 * final Map<String, String> params = new HashMap<>();
		 * params.put(TypeParametroDialogo.ID.toString(),
		 * String.valueOf(this.data.getId())); UtilJSF.openDialog(DialogFichero.class,
		 * TypeModoAcceso.EDICION, params, true, 740, 650);
		 */
	}

	/**
	 * Abre explorar asistente pie.
	 */
	public void explorarAssistentPie() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Abre explorar configuración del formulario de contacto.
	 */
	public void configFormulariContacte() {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroDialogo.ID.toString(), String.valueOf(this.data.getId()));
		UtilJSF.openDialog(DialogFormularioSoporte.class, TypeModoAcceso.EDICION, params, true, 740, 650);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		final String message = null;

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public Entidad getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Entidad data) {
		this.data = data;
	}

}
