/*
 *
 */
package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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

	/**
	 * Servicio.
	 */
	@Inject
	private EntidadService entidadService;

	/** Datos elemento. */
	private Entidad data;

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		// recupera datos entidad activa
		setData(entidadService.loadEntidad(UtilJSF.getIdEntidad()));

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Guardar cambios de la entidad.
		entidadService.updateEntidadAdministradorEntidad(data);

		final String message = UtilJSF.getLiteral("info.modificado.ok");

		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		// Inicializar datos con los de la base de datos, o llamar a la misma url.
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
