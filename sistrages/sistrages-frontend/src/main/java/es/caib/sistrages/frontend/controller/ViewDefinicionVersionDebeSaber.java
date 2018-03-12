package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramiteVersion;
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

	/** Id. **/
	private String id;

	/** Data. **/
	private TramitePasoDebeSaber data;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionDebeSaber.
	 */
	public ViewDefinicionVersionDebeSaber() {
		super();
	}

	@PostConstruct
	public void init() {
		// TODO aqui se leería por BBDD a partir de la ID
		id = "1";
		data = new TramitePasoDebeSaber();
		data.setId(1l);
		final TramiteVersion tramiteVersion = new TramiteVersion();
		tramiteVersion.setId(1l);
		tramiteVersion.setIdiomasSoportados("ca;es;en");
		data.setTramiteVersion(tramiteVersion);
		final Traducciones traducciones = new Traducciones();
		traducciones.add(new Traduccion("ca", "<b>Debe saber</b>"));
		traducciones.add(new Traduccion("es", "<b>Debe saber</b>"));
		data.setInstruccionesIniciales(traducciones);
	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Traducciones traducciones = (Traducciones) respuesta.getResult();
				data.setInstruccionesIniciales(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Traducciones traduccionesMod = (Traducciones) respuesta.getResult();
				data.setInstruccionesIniciales(traduccionesMod);

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
	 *

	 */
	public void editarInstrucciones() {
		if (data.getInstruccionesIniciales() == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "info.traduccion.error");
		} else {
			final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(data.getTramiteVersion());
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, data.getInstruccionesIniciales(), idiomas,
					idiomas);
		}
	}

	/**
	 * Para editar el tramite paso.
	 */
	public void editar() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.id);
		UtilJSF.openDialog(DialogDefinicionVersionDebeSaber.class, TypeModoAcceso.EDICION, params, true, 700, 470);
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

}
