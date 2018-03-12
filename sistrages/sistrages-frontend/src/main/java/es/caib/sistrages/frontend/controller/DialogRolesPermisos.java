package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.types.TypeRolPermiso;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogRolesPermisos extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	// @Inject
	// private EntidadService entidadService;

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private Rol data;

	/** Areas. **/
	private List<Area> areas;

	/** Area seleccionada. **/
	private Area areaSeleccionada;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Rol();
		} else {
			data = new Rol();// entidadService.load(id);
			data.setId(1l);
			data.setTipo(TypeRolPermiso.ROL);
			data.setCodigo("STR_SALUT_DEV");
			data.setDescripcion("Rol de desenvolupament per tràmits de salut");
			data.setAlta(true);
			final Area area = new Area();
			area.setId(1l);
			area.setDescripcion("Area 1");
			data.setArea(area);
		}

		final Area area1 = new Area();
		area1.setId(1l);
		area1.setDescripcion("Area 1");
		final Area area2 = new Area();
		area2.setId(2l);
		area2.setDescripcion("Area 2");
		areas = new ArrayList<>();
		areas.add(area1);
		areas.add(area2);
		setAreaSeleccionada(area1);
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
			 * if (entidadService.load(data.getCodigo()) != null) {
			 * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
			 * "Ya existe dato con ese codigo"); return; } entidadService.add(data);
			 */
			break;
		case EDICION:
			// entidadService.update(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		data.setArea(areaSeleccionada);
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
	public Rol getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Rol data) {
		this.data = data;
	}

	/**
	 * @return the areas
	 */
	public List<Area> getAreas() {
		return areas;
	}

	/**
	 * @param areas
	 *            the areas to set
	 */
	public void setAreas(final List<Area> areas) {
		this.areas = areas;
	}

	/**
	 * @return the areaSeleccionada
	 */
	public Area getAreaSeleccionada() {
		return areaSeleccionada;
	}

	/**
	 * @param areaSeleccionada
	 *            the areaSeleccionada to set
	 */
	public void setAreaSeleccionada(final Area areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

}
