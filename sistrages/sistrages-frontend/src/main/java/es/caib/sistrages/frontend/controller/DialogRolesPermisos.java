package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.RolService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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
	private TramiteService areaService;

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private Rol data;

	/** Areas. **/
	private List<Area> areas;

	private TypeRolePermisos gestor;
	private TypeRolePermisos helpdesk;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {

		areas = areaService.listArea(UtilJSF.getSessionBean().getEntidad().getCodigo(), null);

		// Modo acceso
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Rol();
		} else {
			if (id != null) {
				data = rolService.getRol(Long.valueOf(id));

				if (data.isAlta()) {
					gestor = TypeRolePermisos.ADMINISTRADOR_AREA;
				} else if (data.isModificacion()) {
					gestor = TypeRolePermisos.DESARROLLADOR_AREA;
				} else if (data.isConsulta()) {
					gestor = TypeRolePermisos.CONSULTA;
				}

				if (data.isHelpdesk()) {
					helpdesk = TypeRolePermisos.HELPDESK;
				}
			}

		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);

		if (gestor == null && helpdesk == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.permiso"));
			return;
		} else {

			if (gestor != null) {
				switch (gestor) {
				case ADMINISTRADOR_AREA:
					data.setAlta(true);
					data.setModificacion(false);
					data.setConsulta(false);
					break;
				case DESARROLLADOR_AREA:
					data.setAlta(false);
					data.setModificacion(true);
					data.setConsulta(false);
					break;
				case CONSULTA:
					data.setAlta(false);
					data.setModificacion(false);
					data.setConsulta(true);
					break;
				default:
					data.setAlta(false);
					data.setModificacion(false);
					data.setConsulta(false);
					break;
				}
			} else {
				data.setAlta(false);
				data.setModificacion(false);
				data.setConsulta(false);
			}

			if (TypeRolePermisos.HELPDESK.equals(helpdesk)) {
				data.setHelpdesk(true);
			} else {
				data.setHelpdesk(false);
			}
		}

		switch (acceso) {
		case ALTA:
			if (!validaRoles()) {
				return;
			}
			rolService.addRol(data);
			break;
		case EDICION:
			if (!validaRoles()) {
				return;
			}
			rolService.updateRol(data);
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
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("rolesPermisosDialog");
	}

	/**
	 * Valida roles.
	 *
	 * @return true, si tiene exito
	 */
	private boolean validaRoles() {
		if (data != null) {
			if (data.isAlta() || data.isModificacion() || data.isConsulta() || data.isHelpdesk()) {
				return true;
			} else {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.permiso"));
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param data the data to set
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
	 * @param areas the areas to set
	 */
	public void setAreas(final List<Area> areas) {
		this.areas = areas;
	}

	public TypeRolePermisos getGestor() {
		return gestor;
	}

	public void setGestor(final TypeRolePermisos gestor) {
		this.gestor = gestor;
	}

	public TypeRolePermisos getHelpdesk() {
		return helpdesk;
	}

	public void setHelpdesk(final TypeRolePermisos helpdesk) {
		this.helpdesk = helpdesk;
	}

}
