package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogTramite extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	// @Inject
	// private TramiteService areaService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private Tramite data;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Tramite();
		} else {
			data = new Tramite();// areaService.load(id);
			data.setId(Long.valueOf(id));
			final Literal traducciones = new Literal();
			traducciones.add(new Traduccion("es", "Descripcion del trámite"));
			traducciones.add(new Traduccion("ca", "Descripció del tràmit"));
			data.setDescripcion(traducciones);
			data.setCodigo("codigo");
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
			/*
			 * if (areaService.load(data.getCodigo()) != null) {
			 * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
			 * "Ya existe dato con ese codigo"); return; } areaService.add(data);
			 */

			break;
		case EDICION:
			// areaService.update(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getCodigo());
		UtilJSF.closeDialog(result);
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Literal traducciones = (Literal) respuesta.getResult();
				data.setDescripcion(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionesMod = (Literal) respuesta.getResult();
				data.setDescripcion(traduccionesMod);

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
	 * Editar descripcion del dominio.
	 */
	public void editarDescripcion() {

		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					idiomas, idiomas);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), idiomas, idiomas);
		}

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

	public Tramite getData() {
		return data;
	}

	public void setData(final Tramite data) {
		this.data = data;
	}

}
