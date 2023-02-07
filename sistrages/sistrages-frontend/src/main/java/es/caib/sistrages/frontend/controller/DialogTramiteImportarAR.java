package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.comun.FilaImportarArea;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarAR extends DialogControllerBase {

	@Inject
	private SecurityService securityService;

	@Inject
	private TramiteService tramiteService;

	/** Fila importar. */
	private FilaImportarArea data;

	/** Mensaje. **/
	private String mensaje;

	/** Areas de la entidad. **/
	private List<Area> areas = new ArrayList<>();

	/** Area seleccionada. **/
	private Long areaSeleccionada;

	/** Accion. **/
	private String accion;

	/** Identificador. **/
	private String identificador;

	/** Descripcion. **/
	private String descripcion;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = (FilaImportarArea) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_IMPORTAR);
		if (data.getAccion() == null) {
			accion = TypeImportarAccion.SELECCIONAR.toString();
		} else if (data.getAccion() == TypeImportarAccion.CREAR || data.getAccion() == TypeImportarAccion.SELECCIONAR) {
			accion = data.getAccion().toString();
		}

		identificador = this.data.getIdentificador();
		descripcion = this.data.getDescripcion();

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			initAdministradorEntidad();
		} else {
			initDesarrolladorEntidad();
		}

		/** Seleccionamos el area con el que venga. **/
		if (data.getArea() != null) {
			for (final Area area : areas) {
				if (area.getIdentificador().equals(data.getArea().getIdentificador())) {
					areaSeleccionada = area.getCodigo();
				}
			}
		}
	}

	/**
	 * El desarrollador de entidad, sólo puede seleccionar areas que pertenezcan a
	 * sus permisos (como adm. area o desarrollador de area).
	 */
	private void initDesarrolladorEntidad() {
		final List<Area> areasEntidad = tramiteService.listArea(UtilJSF.getIdEntidad(), null);
		for (final Area areaEntidad : areasEntidad) {
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(areaEntidad.getCodigo());

			/** ¿Tiene el permiso para seleccionarla? **/
			if (permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
					|| permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				areas.add(areaEntidad);
			}
		}
	}

	/**
	 * El adm. de entidad puede crear area, si no existe. Además, puede seleccionar
	 * cualquier area de la entidad.
	 */
	private void initAdministradorEntidad() {
		areas = tramiteService.listArea(UtilJSF.getIdEntidad(), null);
	}

	/** Consultar. **/
	public void consultarArea() {

		if (this.data.getAreaActual() != null) {
			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getAreaActual().getCodigo()));
			UtilJSF.openDialog(DialogArea.class, TypeModoAcceso.CONSULTA, params, true, 520, 160);
		}
	}

	/**
	 * Guardar resultado.
	 */
	public void guardar() {

		final TypeImportarAccion typeAccion = TypeImportarAccion.fromString(accion);
		this.data.setAccion(typeAccion);
		if (typeAccion == TypeImportarAccion.SELECCIONAR) {
			if (areaSeleccionada == null) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogTramiteImportarAR.error.seleccionarArea"));
				return;
			} else {
				final Area area = tramiteService.getArea(areaSeleccionada);
				this.data.setArea(area);
				this.data.setAreaActual(area);
				this.data.setIdentificador(area.getIdentificador());
				this.data.setDescripcion(area.getDescripcion());
				this.data.setExiste(TypeImportarExiste.EXISTE);
				this.data.setDestinoDescripcion(area.getDescripcion());
				this.data.setDestinoIdentificador(area.getIdentificador());
			}
		} else if (typeAccion == TypeImportarAccion.CREAR) {
			if (this.identificador == null || this.identificador.isEmpty() || this.descripcion == null
					|| this.descripcion.isEmpty()) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogTramiteImportarAR.error.vaciodatos"));
				return;
			}
			if (tramiteService.checkIdentificadorAreaRepetido(identificador, null)) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogTramiteImportarAR.error.identificadorrepetido"));
				return;
			}
			this.data.setIdentificador(identificador);
			this.data.setDescripcion(descripcion);
			this.data.setExiste(TypeImportarExiste.NO_EXISTE);
			this.data.setDestinoDescripcion(descripcion);
			this.data.setDestinoIdentificador(identificador);
		} else {
			addMessageContext(TypeNivelGravedad.WARNING, "Sin implementar");
			return;
		}

		this.data.setMensaje(null);
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.data);

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
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
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the data
	 */
	public FilaImportarArea getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final FilaImportarArea data) {
		this.data = data;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
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

	/**
	 * @return the areaSeleccionada
	 */
	public Long getAreaSeleccionada() {
		return areaSeleccionada;
	}

	/**
	 * @param areaSeleccionada the areaSeleccionada to set
	 */
	public void setAreaSeleccionada(final Long areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public void setAccion(final String accion) {
		this.accion = accion;
	}

	/**
	 * @return the identificador
	 */
	public final String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public final void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public final String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public final void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

}
