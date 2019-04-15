package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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
public class DialogDefinicionVersionPagarTasas extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Data. **/
	private Tasa data;

	/** Id. **/
	private String id;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/** ID tramite version. **/
	private String idTramiteVersion;

	/** ID tramite paso. **/
	private String idTramitePaso;

	/** Idiomas. **/
	private List<String> idiomas;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionRellenar.
	 */
	public DialogDefinicionVersionPagarTasas() {
		super();

	}

	public void init() {
		data = new Tasa();
		data.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		data.setTipoPlugin(null);
		data.setSimulado(false);
		data.setOrden(0);
		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		idiomas = UtilTraducciones.getIdiomas(tramiteVersion.getIdiomasSoportados());
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
	 *
	 *
	 */
	public void editarDescripcion() {
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, null, tramiteVersion);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), tramiteVersion);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (this.data.getDescripcion() == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.descripcion.norelleno"));
			return;
		}

		if (tramiteService.checkTasaRepetida(tramiteVersion.getCodigo(), this.data.getIdentificador(),
				this.data.getCodigo())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return;
		}

		final Tasa tasaAlta = tramiteService.addTasaTramite(data, Long.valueOf(idTramitePaso));

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(tasaAlta);
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
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("definicionVersionPagarTasasDialog");
	}

	/**
	 * @return the data
	 */
	public Tasa getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Tasa data) {
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

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	public String getIdTramitePaso() {
		return idTramitePaso;
	}

	public void setIdTramitePaso(final String idTramitePaso) {
		this.idTramitePaso = idTramitePaso;
	}

	/**
	 * @return the idiomas
	 */
	public List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas
	 *            the idiomas to set
	 */
	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

}
