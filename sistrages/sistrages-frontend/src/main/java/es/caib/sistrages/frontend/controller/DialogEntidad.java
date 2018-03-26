package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogEntidad extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private EntidadService entidadService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private Entidad data;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Entidad();
		} else {
			data = entidadService.loadEntidad(Long.valueOf(id));
		}
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literal = (Literal) respuesta.getResult();
			data.setNombre(literal);
		}
	}

	/**
	 * Editar descripcion del dominio.
	 *
	 *
	 */
	public void editarNombre() {
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getNombre(), idiomas, idiomas);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			entidadService.addEntidad(data);
			break;
		case EDICION:
			entidadService.updateEntidadSuperAdministrador(data);
			break;
		case CONSULTA:
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

	public void mensaje() {
		UtilJSF.showMessageDialog(TypeNivelGravedad.INFO, "Atento", "Ojo al dato.");
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Entidad getData() {
		return data;
	}

	public void setData(final Entidad data) {
		this.data = data;
	}

}
