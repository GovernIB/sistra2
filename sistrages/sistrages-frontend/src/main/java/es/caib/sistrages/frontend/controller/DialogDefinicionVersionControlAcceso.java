/*
 *
 */
package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * DialogDefinicionVersionControlAcceso.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionControlAcceso extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private Long id;

	/** Tramite version. */
	private TramiteVersion tramiteVersion;

	/**
	 * Inicialización.
	 */
	public void init() {
		tramiteVersion = tramiteService.getTramiteVersion(id);
	}

	/**
	 * Editar descripcion.
	 */
	public void editarMensajeDesactivacion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.tramiteVersion.getMensajeDesactivacion(),
				tramiteVersion);
	}

	/**
	 * Retorno dialogo de la traduccion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoMensajeDesactivacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:

				final Literal traducciones = (Literal) respuesta.getResult();
				this.tramiteVersion.setMensajeDesactivacion(traducciones);

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Realizamos alta o update
		if (modoAcceso != null && TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION) {
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

}
