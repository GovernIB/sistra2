package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteDesbloquear extends DialogControllerBase {

	/**
	 * Enlace area.
	 */
	@Inject
	private TramiteService tramiteService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private TramiteVersion data;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new TramiteVersion();
		} else {
			data = tramiteService.getTramiteVersion(Long.valueOf(id));
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);

		/*
		 * switch (acceso) { case ALTA: tramiteService.addArea(idEntidad, data); break;
		 * case EDICION: tramiteService.updateArea(data); break; case CONSULTA: // No
		 * hay que hacer nada break; }
		 */

		/*
		 * tramiteService.desbloquearTramiteVersion(this.datoSeleccionado.getCodigo(),
		 * UtilJSF.getSessionBean().getUserName(), "DESBLOQUEO");
		 *
		 * filtrar(); this.datoSeleccionado = null;
		 */

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
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
	public TramiteVersion getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final TramiteVersion data) {
		this.data = data;
	}

}
