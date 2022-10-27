package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogSeccionDesbloquear extends DialogControllerBase {

	/**
	 * Enlace area.
	 */
	@Inject
	private SeccionReutilizableService seccionService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Usuario
	 */
	private String usuario;

	/**
	 * Detalle
	 */
	private String detalle;

	/**
	 * Datos elemento.
	 */
	private SeccionReutilizable data;

	/**
	 * Inicialización.
	 */
	public void init() {

		data = seccionService.getSeccionReutilizable(Long.valueOf(id));
		data.setRelease(data.getRelease() + 1);
		usuario = UtilJSF.getSessionBean().getUserName();

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		seccionService.desbloquearSeccion(Long.valueOf(this.id), usuario, detalle);

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
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
	public SeccionReutilizable getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final SeccionReutilizable data) {
		this.data = data;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the detalle
	 */
	public String getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle
	 *            the detalle to set
	 */
	public void setDetalle(final String detalle) {
		this.detalle = detalle;
	}

}
