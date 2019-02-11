package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeExtension;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeCampoFichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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

	/** Id paso. **/
	private String idPaso;

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

		if (data.getExtensiones().equals(Constantes.EXTENSIONES_TODAS)) {
			data.setExtensionSeleccion(TypeExtension.TODAS);
		} else {
			data.setExtensionSeleccion(TypeExtension.PERSONALIZADAS);
		}

	}

	/**
	 * Descarga fichero.
	 *
	 * @param fichero
	 */
	public void descargaFichero(final Fichero fichero) {
		if (fichero != null && fichero.getCodigo() != null) {
			UtilJSF.redirectJsfPage(Constantes.DESCARGA_FICHEROS_URL + "?id=" + fichero.getCodigo());
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
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(data.getCodigo()));
		params.put(TypeParametroVentana.CAMPO_FICHERO.toString(), TypeCampoFichero.TRAMITE_DOC.toString());
		params.put(TypeParametroVentana.ENTIDAD.toString(), idEntidad);
		UtilJSF.openDialog(DialogFichero.class, TypeModoAcceso.EDICION, params, true, 750, 350);

	}

	/**
	 * Retorno dialogo de cuando seleccionas un fichero.
	 *
	 * @param event
	 */
	public void returnDialogoFichero(final SelectEvent event) {

		data = tramiteService.getDocumento(Long.valueOf(id));

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
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(),
				UtilJSON.toJSON(TypeScriptFlujo.fromString(tipoScript)));
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
		maps.put(TypeParametroVentana.TRAMITEPASO.toString(), idPaso);
		if (script != null) {
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		}
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 700);
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
		// verificamos precondiciones
		if (!verificarGuardar()) {
			return;
		}

		//
		if (TypeExtension.TODAS.equals(data.getExtensionSeleccion())) {
			data.setExtensiones(Constantes.EXTENSIONES_TODAS);
		}

		switch (TypeModoAcceso.valueOf(modoAcceso)) {
		case ALTA:
		case EDICION:
			tramiteService.updateDocumentoTramite(data);
			break;
		case CONSULTA:
		default:
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
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {

		if (tramiteService.checkAnexoRepetido(tramiteVersion.getCodigo(), this.data.getIdentificador(),
				this.data.getCodigo())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return false;
		}

		if (TypeFormularioObligatoriedad.DEPENDIENTE.equals(data.getObligatoriedad())
				&& (data.getScriptObligatoriedad() == null
						|| StringUtils.isEmpty(data.getScriptObligatoriedad().getContenido()))) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.obligatorio.dependencia"));
			return false;
		}

		if (TypeExtension.PERSONALIZADAS.equals(data.getExtensionSeleccion())) {
			final String[] listaExtensiones = data.getExtensiones().split(Constantes.LISTAS_SEPARADOR);
			for (final String cadena : listaExtensiones) {
				if (!cadena.matches("^\\w{3}$")) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("error.extensiones.formato"));
					return false;
				}
			}
		}

		if (data.isDebeAnexarFirmado() && data.isDebeFirmarDigitalmente()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.firma.unicidad"));
			return false;
		}

		return true;
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

	/**
	 * @return the idPaso
	 */
	public String getIdPaso() {
		return idPaso;
	}

	/**
	 * @param idPaso
	 *            the idPaso to set
	 */
	public void setIdPaso(final String idPaso) {
		this.idPaso = idPaso;
	}

}
