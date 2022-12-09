package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.HistorialSeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogHistorialSeccion extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private SeccionReutilizableService seccionService;

	/** Id de la version. */
	private String id;

	/** Historial de versiones. */
	private List<HistorialSeccionReutilizable> data;

	/** Historial de versiones seleccionado. */
	private HistorialSeccionReutilizable datoSeleccionado;

	private String portapapeles;

	/** Inicialización. */
	public void init() {
		data = seccionService.listHistorialSeccionReutilizable(Long.valueOf(id), null);
	}

	/**
	 * Borra el historial de versiones dejándolo a sólo 1, el de creación a hoy.
	 **/
	public void borradoHistorial() {
		seccionService.borradoHistorial(Long.valueOf(id), UtilJSF.getSessionBean().getUserName());
		data = seccionService.listHistorialSeccionReutilizable(Long.valueOf(id), null);
	}

	/**
	 * Permite borrar
	 * 
	 * @return
	 */
	public boolean isPermiteBorrar() {
		// Solo se puede bloquear/desbloquear en desarrollo
		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			return false;
		}

		// El ADM. ENTIDAD puede desbloquearlo
		return UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT;
	}

	/** Cancelar. */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("historialVersionDialog");
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

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
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
	public List<HistorialSeccionReutilizable> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final List<HistorialSeccionReutilizable> data) {
		this.data = data;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public HistorialSeccionReutilizable getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final HistorialSeccionReutilizable datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
