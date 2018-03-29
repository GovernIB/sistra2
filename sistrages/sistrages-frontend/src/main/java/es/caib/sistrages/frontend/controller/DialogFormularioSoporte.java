package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.types.TypeFormularioSoporte;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogFormularioSoporte extends DialogControllerBase {

	/** Datos elemento. */
	private FormularioSoporte data;

	/** Formulario Soporte en formato JSON. **/
	private String iData;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new FormularioSoporte();

		} else {
			data = (FormularioSoporte) UtilJSON.fromJSON(iData, FormularioSoporte.class);
		}

	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoDescripcion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Literal traducciones = (Literal) respuesta.getResult();
				data.setDescripcion(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionesMod = (Literal) respuesta.getResult();
				data.setDescripcion(traduccionesMod);

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
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTipoIncidencia(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Literal traducciones = (Literal) respuesta.getResult();
				data.setTipoIncidencia(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionesTipoIncid = (Literal) respuesta.getResult();
				data.setTipoIncidencia(traduccionesTipoIncid);

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
	 * Editar descripcion del dominio.
	 *
	 * 
	 */
	public void editarDescripcion() {
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					idiomas, idiomas);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), idiomas, idiomas);
		}

	}

	/**
	 * Editar descripcion del dominio.
	 *
	 * 
	 */
	public void editarTipoIncidencia() {
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		if (data.getTipoIncidencia() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					idiomas, idiomas);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getTipoIncidencia(), idiomas, idiomas);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (data.getDestinatario() == TypeFormularioSoporte.LISTA_DE_EMAILS && data.getEmails().isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "dialogFormularioSoporte.error.emailVacio");
			return;
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
	 * @return the data
	 */
	public FormularioSoporte getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FormularioSoporte data) {
		this.data = data;
	}

	/**
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

}
