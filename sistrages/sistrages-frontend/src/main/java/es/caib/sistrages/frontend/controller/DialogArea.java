package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogArea extends DialogControllerBase {

	/**
	 * Enlace area.
	 */
	@Inject
	private TramiteService areaService;

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
	private Area data;

	/**
	 * Inicialización.
	 */
	public void init() {

		idEntidad = UtilJSF.getIdEntidad();

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Area();
		} else {
			data = areaService.getArea(Long.valueOf(id));
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);

		// TODO Verificar unicidad dir3

		switch (acceso) {
		case ALTA:
			areaService.addArea(idEntidad, data);
			break;
		case EDICION:
			areaService.updateArea(data);
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

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Area getData() {
		return data;
	}

	public void setData(final Area data) {
		this.data = data;
	}

}
