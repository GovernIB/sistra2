/*
 *
 */
package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAvisoEntidad;
import es.caib.sistrages.core.api.service.AvisoEntidadService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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
				.getAvisoEntidadByTramite(tramite.getIdentificador() + "#" + tramiteVersion.getNumeroVersion()));
		if (avisoEntidad == null) {
			crearAvisoEntidad();
		}
	}

	/**
	 * Genera vacío el aviso entidad.
	 */
	private void crearAvisoEntidad() {
		avisoEntidad = new AvisoEntidad();
		avisoEntidad.setTipo(TypeAvisoEntidad.TRAMITE_VERSION);
		avisoEntidad.setBloqueado(false);
		avisoEntidad.setListaSerializadaTramites(tramite.getIdentificador() + "#" + tramiteVersion.getNumeroVersion());
	}

	/**
	 * Editar descripcion.
	 */
	public void editarMensajeDesactivacion() {

		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.avisoEntidad.getMensaje(), tramiteVersion);

	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			this.avisoEntidad.setMensaje(literales);
		}
	}

	/**
	 * Borra el aviso entidad
	 */
	public void eliminarAvisoEntidad() {
		if (avisoEntidad.getCodigo() != null) {
			avisoEntidadService.removeAvisoEntidad(avisoEntidad.getCodigo());
		}

		crearAvisoEntidad();
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Comprobamos que el mensaje de aviso está correcto.
		if (!checkMensajeAviso()) {
			return;
		}

		tramiteService.updateTramiteVersionControlAcceso(tramiteVersion, avisoEntidad, UtilJSF.getIdEntidad());

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(this.tramiteVersion);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Comprueba que el mensaje de aviso está correcto: <br />
	 * - Que si están rellenas las fechas, la fecha fin sea posterior a fecha ini.
	 * <br />
	 * - Que si no está relleno el mensaje, tampoco estén rellenos el resto de
	 * datos.
	 *
	 * @return
	 */
	private boolean checkMensajeAviso() {

		boolean retorno;
		if (avisoEntidad.getFechaFin() != null && avisoEntidad.getFechaInicio() != null
				&& avisoEntidad.getFechaFin().before(avisoEntidad.getFechaInicio())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogDefinicionVersionControlAcceso.error.fechas"));
			retorno = false;
		} else if (isVacio(avisoEntidad.getMensaje()) && (avisoEntidad.isBloqueado()
				|| avisoEntidad.getFechaFin() != null || avisoEntidad.getFechaInicio() != null)) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogDefinicionVersionControlAcceso.error.mensaje"));
			retorno = false;
		} else {

			retorno = true;
		}
		return retorno;
	}

	/**
	 * Comprueba que un literal no tiene traducciones.
	 *
	 * @param mensaje
	 * @return
	 */
	private boolean isVacio(final Literal mensaje) {
		boolean vacio = false;
		if (mensaje == null || mensaje.getTraducciones() == null || mensaje.getTraducciones().isEmpty()) {
			vacio = true;
		}
		return vacio;
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
