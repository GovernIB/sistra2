package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeCampoFichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n Anexo.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionAnexo extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id. **/
	private String id;

	/** Data. **/
	private Documento data;

	/** Documento seleccioando. **/
	private Documento datoSeleccionado;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/** ID tramite version. **/
	private String idTramiteVersion;

	/** Id entidad. **/
	private String idEntidad;

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditar() {
		return (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
	}

	/** Init. **/
	public void init() {
		data = tramiteService.getDocumento(Long.valueOf(id));
		if (idTramiteVersion != null) {
			tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		}
	}

	/**
	 * Descarga fichero.
	 *
	 * @param fichero
	 */
	public void descargaFichero(final Fichero fichero) {
		if (fichero != null && fichero.getId() != null) {
			UtilJSF.redirectJsfPage(Constantes.DESCARGA_FICHEROS_URL + "?id=" + fichero.getId());
		}
	}

	/**
	 * Para subir el fichero.
	 */
	public void subirFichero() {

		// Hay que actualizar primero el documento.
		tramiteService.updateDocumentoTramite(data);

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(data.getId()));
		params.put(TypeParametroVentana.CAMPO_FICHERO.toString(), TypeCampoFichero.TRAMITE_DOC.toString());
		params.put(TypeParametroVentana.ENTIDAD.toString(), idEntidad);
		UtilJSF.openDialog(DialogFichero.class, TypeModoAcceso.EDICION, params, true, 750, 350);

	}

	/**
	 * Retorno dialogo de los botones de traducciones.
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

				final Literal traduccion = (Literal) respuesta.getResult();
				data.setDescripcion(traduccion);

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoAyudaTexto(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				final Literal traduccion = (Literal) respuesta.getResult();
				data.setAyudaTexto(traduccion);

				break;
			case CONSULTA:
			default:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Return dialogo.
	 *
	 * @param event
	 */
	public void returnDialogoObligatoriedad(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptObligatoriedad(script);
				break;
			case CONSULTA:
			default:
				// No hay que hacer nada
				break;
			}
		}
	}

	/**
	 * Return dialogo.
	 *
	 * @param event
	 */
	public void returnDialogoFirmarDigitalmente(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptFirmarDigitalmente(script);
				break;
			case CONSULTA:
			default:
				// No hay que hacer nada
				break;
			}
		}
	}

	/**
	 * Return dialogo.
	 *
	 * @param event
	 */
	public void returnDialogoValidacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:
			case EDICION:
				final Script script = (Script) respuesta.getResult();
				data.setScriptValidacion(script);
				break;
			case CONSULTA:
			default:
				// No hay que hacer nada
				break;
			}
		}
	}

	/**
	 * Editar script
	 */
	public void editarScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT.toString(), tipoScript);
		if (script != null) {
			maps.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(script));
		}
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 950, 700);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void editarDescripcion() {
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					tramiteVersion);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), tramiteVersion);
		}
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void editarAyudaTexto() {
		if (data.getAyudaTexto() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					tramiteVersion);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getAyudaTexto(), tramiteVersion);
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
	 * @return the datoSeleccionado
	 */
	public Documento getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Documento datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	/**
	 * @return the idEntidad
	 */
	public String getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param idEntidad
	 *            the idEntidad to set
	 */
	public void setIdEntidad(final String idEntidad) {
		this.idEntidad = idEntidad;
	}

}
