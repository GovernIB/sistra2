package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de debe saber.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionDebeSaber extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id. **/
	private String id;

	/** Data. **/
	private TramitePasoDebeSaber data;

	/** ID tramite version. **/
	private String idTramiteVersion;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionDebeSaber.
	 */
	public DialogDefinicionVersionDebeSaber() {
		super();
	}

	public void init() {
		data = (TramitePasoDebeSaber) tramiteService.getTramitePaso(Long.valueOf(id));
		if (idTramiteVersion != null) {
			tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		}
	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:

				final Literal traduccionesMod = (Literal) respuesta.getResult();
				data.setInstruccionesIniciales(traduccionesMod);

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 *
	 *
	 */
	public void editar() {
		if (data.getInstruccionesIniciales() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, data.getInstruccionesIniciales(),
					tramiteVersion);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getInstruccionesIniciales(),
					tramiteVersion);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		if (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION) {
			result.setResult(data);
		}
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
	public TramitePasoDebeSaber getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final TramitePasoDebeSaber data) {
		this.data = data;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

}
