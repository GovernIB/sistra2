/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.AvisoEntidadService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * DialogDefinicionVersionControlAcceso.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionControlAcceso extends DialogControllerBase {

	/** Service. **/
	@Inject
	private AvisoEntidadService avisoEntidadService;

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private Long id;

	/** Tramite. **/
	private Tramite tramite;

	/** Tramite version. */
	private TramiteVersion tramiteVersion;

	/** Aviso entidad. **/
	private AvisoEntidad avisoEntidad;

	/**
	 * Inicializacion.
	 */
	public void init() {
		tramiteVersion = tramiteService.getTramiteVersion(id);
		tramite = tramiteService.getTramite(tramiteVersion.getIdTramite());
		setAvisoEntidad(avisoEntidadService
				.getAvisoEntidadByTramite(tramite.getIdentificador() + "-" + tramiteVersion.getNumeroVersion()));
	}

	/**
	 * Editar descripcion.
	 */
	public void consultarMensajeDesactivacion() {

		if (this.avisoEntidad == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "No existeix el missatge d'avis");
			return;
		}

		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, this.avisoEntidad.getMensaje(), tramiteVersion);

	}

	/**
	 * Aviso entidad activo.
	 *
	 * @return
	 */
	public boolean avisoEntidadActivo() {
		if (avisoEntidad == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Aviso entidad activo.
	 *
	 * @return
	 */
	public boolean avisoEntidadInactivo() {
		if (avisoEntidad == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Crear aviso entidad.
	 */
	public void crearAvisoEntidad() {
		abrirAvisoEntidad(TypeModoAcceso.ALTA);
	}

	/**
	 * Modificar aviso entidad.
	 */
	public void modificarAvisoEntidad() {
		abrirAvisoEntidad(TypeModoAcceso.EDICION);
	}

	/**
	 * Consultar aviso entidad.
	 */
	public void consultarAvisoEntidad() {
		abrirAvisoEntidad(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoAvisoEntidad(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {

			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			setAvisoEntidad(avisoEntidadService
					.getAvisoEntidadByTramite(tramite.getIdentificador() + "-" + tramiteVersion.getNumeroVersion()));
		}
	}

	/**
	 * Abre dialog aviso entidad.
	 *
	 * @param modo
	 */
	private void abrirAvisoEntidad(final TypeModoAcceso modo) {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		if (avisoEntidad != null) {
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.avisoEntidad.getId()));
		}
		params.put(TypeParametroVentana.TRAMITE.toString(), tramite.getIdentificador());
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), String.valueOf(tramiteVersion.getNumeroVersion()));
		params.put(TypeParametroVentana.DATO.toString(), "DVCA");
		UtilJSF.openDialog(DialogMensajeAviso.class, modo, params, true, 600, 350);
	}

	/**
	 * Borra aviso entidad.
	 */
	public void borrarAvisoEntidad() {

		if (this.avisoEntidad == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "No existeix el missatge d'avis");
			return;
		}

		avisoEntidadService.removeAvisoEntidad(avisoEntidad.getId());
		avisoEntidad = null;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Realizamos alta o update
		if (modoAcceso != null && TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION) {
			tramiteService.updateTramiteVersion(tramiteVersion);
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(this.tramiteVersion);
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
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de tramiteVersion.
	 *
	 * @return el valor de tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * Establece el valor de tramiteVersion.
	 *
	 * @param tramiteVersion
	 *            el nuevo valor de tramiteVersion
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the avisoEntidad
	 */
	public AvisoEntidad getAvisoEntidad() {
		return avisoEntidad;
	}

	/**
	 * @param avisoEntidad
	 *            the avisoEntidad to set
	 */
	public void setAvisoEntidad(final AvisoEntidad avisoEntidad) {
		this.avisoEntidad = avisoEntidad;
	}

}
