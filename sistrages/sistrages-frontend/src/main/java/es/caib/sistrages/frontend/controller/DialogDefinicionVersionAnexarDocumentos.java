package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypePresentacion;
import es.caib.sistrages.core.api.model.types.TypeTamanyo;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de rellenar.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionAnexarDocumentos extends DialogControllerBase {

	/** Data. **/
	private Documento data;

	/** Id. **/
	private String id;

	/** JSON tramite version. **/
	private String jsonTramiteVersion;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionRellenar.
	 */
	public DialogDefinicionVersionAnexarDocumentos() {
		super();

	}

	public void init() {
		data = new Documento();
		data.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		data.setTipoTamanyo(TypeTamanyo.KILOBYTES);
		data.setTipoPresentacion(TypePresentacion.ELECTRONICA);
		data.setNumeroInstancia(1);
		data.setExtensiones("pdf;doc");
		data.setTamanyoMaximo(1024);
		if (jsonTramiteVersion != null) {
			tramiteVersion = (TramiteVersion) UtilJSON.fromJSON(jsonTramiteVersion, TramiteVersion.class);
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

		if (!respuesta.isCanceled()) {

			final Literal traducciones = (Literal) respuesta.getResult();
			data.setDescripcion(traducciones);
		}
	}

	/**
	 * Editar descripcion del dominio.
	 */
	public void editarDescripcion() {
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					tramiteVersion);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), tramiteVersion);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
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
	public Documento getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Documento data) {
		this.data = data;
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

	public String getJsonTramiteVersion() {
		return jsonTramiteVersion;
	}

	public void setJsonTramiteVersion(final String jsonTramiteVersion) {
		this.jsonTramiteVersion = jsonTramiteVersion;
	}

	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

}
