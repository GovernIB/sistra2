package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.service.AvisoEntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogMensajeAviso extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private AvisoEntidadService avisoEntidadService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Id entidad.
	 */
	private Long idEntidad;

	/**
	 * Datos elemento.
	 */
	private AvisoEntidad data;

	/**
	 * Inicialización.
	 */
	public void init() {

		// Id entidad
		idEntidad = UtilJSF.getSessionBean().getEntidad().getId();

		// Modo acceso
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new AvisoEntidad();
		} else {
			if (id != null) {
				data = avisoEntidadService.getAvisoEntidad(Long.valueOf(id));
			}

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
			avisoEntidadService.addAvisoEntidad(idEntidad, data);
			break;
		case EDICION:
			avisoEntidadService.updateAvisoEntidad(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getId());
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
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literal = (Literal) respuesta.getResult();
			data.setMensaje(literal);
		}
	}

	/**
	 * Editar descripcion
	 *
	 *
	 */
	public void editarMensaje() {
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		if (data.getMensaje() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					idiomas, idiomas);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getMensaje(), idiomas, idiomas);
		}
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
	public AvisoEntidad getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final AvisoEntidad data) {
		this.data = data;
	}

}
