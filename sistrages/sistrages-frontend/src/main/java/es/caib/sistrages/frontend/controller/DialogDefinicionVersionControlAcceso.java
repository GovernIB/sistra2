/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.TramiteVersion;
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

	/** Id elemento a tratar. */
	private Long id;

	/**
	 * tramite version.
	 */
	private TramiteVersion tramiteVersion;

	/**
	 * Inicialización.
	 */
	public void init() {
		recuperaTramiteVersion(Long.valueOf(1));
	}

	/**
	 * Editar descripcion.
	 */
	public void editarMensajeDesactivacion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.tramiteVersion.getMensajeDesactivacion(),
				null, null);
	}

	/**
	 * Retorno dialogo de la traduccion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoMensajeDesactivacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Traducciones traducciones = (Traducciones) respuesta.getResult();
				this.tramiteVersion.setMensajeDesactivacion(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Traducciones traduccionesMod = (Traducciones) respuesta.getResult();
				this.tramiteVersion.setMensajeDesactivacion(traduccionesMod);

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
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			break;
		case EDICION:
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		// result.setResult(data.getCodigo());
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
	 * Recupera tramite version.
	 *
	 * @param id
	 *            el id de tramite version
	 */
	private void recuperaTramiteVersion(final Long id) {
		tramiteVersion = new TramiteVersion();
		tramiteVersion.setId(id);

		tramiteVersion.setActiva(true);
		tramiteVersion.setDesactivacion(true);
		final Calendar dia = Calendar.getInstance();
		dia.set(2018, 1, 1);
		tramiteVersion.setPlazoInicioDesactivacion(dia.getTime());
		tramiteVersion.setPlazoFinDesactivacion(new Date());
		final Traducciones desact1 = new Traducciones();
		desact1.add(new Traduccion("es", "Mensaje de desactivacion"));
		desact1.add(new Traduccion("ca", "Missatge de desactivació"));
		tramiteVersion.setMensajeDesactivacion(desact1);
		tramiteVersion.setDebug(true);
		tramiteVersion.setLimiteTramitacion(true);
		tramiteVersion.setNumLimiteTramitacion(1);
		tramiteVersion.setIntLimiteTramitacion(2);

	}

}
