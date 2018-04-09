package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.service.AreaService;
import es.caib.sistrages.core.api.service.RolService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogRolesPermisos extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private RolService rolService;

	@Inject
	private AreaService areaService;

	/**
	 * Id entidad.
	 */
	private Long idEntidad;

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private Rol data;

	/** Areas. **/
	private List<Area> areas;

	/**
	 * Inicialización.
	 */
	public void init() {
		// Id entidad
		idEntidad = UtilJSF.getSessionBean().getEntidad().getId();

		areas = areaService.listArea(idEntidad, null);

		// Modo acceso
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Rol();
		} else {
			if (id != null) {
				data = rolService.getRol(Long.valueOf(id));
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
			rolService.addRol(data);
			break;
		case EDICION:
			rolService.updateRol(data);
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

}
