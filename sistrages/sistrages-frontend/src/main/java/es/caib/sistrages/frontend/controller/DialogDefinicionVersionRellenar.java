package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramiteVersion;
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
public class DialogDefinicionVersionRellenar extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Data. **/
	private FormularioTramite data;

	/** Id. **/
	private String id;

	/** ID tramite version. **/
	private String idTramiteVersion;

	private String idTramitePaso;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/** Idiomas. **/
	private List<String> idiomas;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionRellenar.
	 */
	public DialogDefinicionVersionRellenar() {
		super();

	}

	/**
	 * Init.
	 */
	public void init() {

		data = tramiteService.createFormularioTramiteDefault();
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

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				final Literal traducciones = (Literal) respuesta.getResult();
				data.setDescripcion(traducciones);
				break;

			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Editar descripcion del dominio.
	 *
	 *
	 */
	public void editarDescripcion() {
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, null, tramiteVersion);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), tramiteVersion);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (this.data.getDescripcion() == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.descripcion.norelleno"));
			return;
		}

		if (tramiteService.checkFormularioRepetido(tramiteVersion.getCodigo(), this.data.getIdentificador(),
				this.data.getCodigo())) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return;
		}

		final FormularioTramite formularioAlta = tramiteService.addFormularioTramite(data, Long.valueOf(idTramitePaso));

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(formularioAlta);
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
		UtilJSF.openHelp("definicionVersionRellenarDialog");
	}

	/**
	 * @return the data
	 */
	public FormularioTramite getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FormularioTramite data) {
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

	/**
	 * @return the idTramiteVersion
	 */
	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	/**
	 * @param idTramiteVersion
	 *            the idTramiteVersion to set
	 */
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
