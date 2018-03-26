package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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

	/** Id. **/
	private String id;

	/** Data. **/
	private TramitePasoDebeSaber data;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionDebeSaber.
	 */
	public DialogDefinicionVersionDebeSaber() {
		super();
	}

	@PostConstruct
	public void init() {
		// TODO aqui se leería por BBDD a partir de la ID
		data = new TramitePasoDebeSaber();
		data.setId(1l);
		final TramiteVersion tramiteVersion = new TramiteVersion();
		tramiteVersion.setId(1l);
		tramiteVersion.setIdiomasSoportados("ca;es;en");
		data.setTramiteVersion(tramiteVersion);
		final Literal traducciones = new Literal();
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

				final Literal traducciones = (Literal) respuesta.getResult();
				data.setInstruccionesIniciales(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionesMod = (Literal) respuesta.getResult();
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
	 * 
	 */
	public void editar() {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(data.getTramiteVersion());
		if (data.getInstruccionesIniciales() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, data.getInstruccionesIniciales(), idiomas,
					idiomas);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getInstruccionesIniciales(), idiomas,
					idiomas);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			/*
			 * if (entidadService.load(data.getCodigo()) != null) {
			 * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
			 * "Ya existe dato con ese codigo"); return; } entidadService.add(data);
			 */

			break;
		case EDICION:
			// entidadService.update(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
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
