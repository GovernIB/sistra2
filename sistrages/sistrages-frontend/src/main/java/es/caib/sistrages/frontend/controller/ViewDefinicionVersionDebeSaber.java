package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
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
public class ViewDefinicionVersionDebeSaber extends ViewControllerBase {

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionDebeSaber.
	 */
	public ViewDefinicionVersionDebeSaber() {
		super();
	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 **/
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Literal traducciones = (Literal) respuesta.getResult();
				// data.setInstruccionesIniciales(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionesMod = (Literal) respuesta.getResult();
				// data.setInstruccionesIniciales(traduccionesMod);

				// Mensaje
				message = UtilJSF.getLiteral("info.modificado.ok");
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 **/
	public void editarInstrucciones(final TramitePasoDebeSaber tramitePaso) {
		if (tramitePaso.getInstruccionesIniciales() == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "info.traduccion.error");
		} else {
			final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramitePaso.getTramiteVersion());
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, tramitePaso.getInstruccionesIniciales(),
					idiomas, idiomas);
		}
	}

	/**
	 * Para editar el tramite paso.
	 ***/
	public void editar(final TramitePasoDebeSaber tramitePaso) {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), tramitePaso.getId().toString());
		UtilJSF.openDialog(DialogDefinicionVersionDebeSaber.class, TypeModoAcceso.EDICION, params, true, 700, 470);
	}

}
