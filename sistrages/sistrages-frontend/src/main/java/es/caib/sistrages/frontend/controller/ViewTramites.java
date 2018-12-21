package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.TramiteVersiones;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;

/**
 * Mantenimiento de tramites.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewTramites extends ViewControllerBase {

	/**
	 * Service.
	 */
	@Inject
	private DominioService dominioService;

	/**
	 * Service.
	 */
	@Inject
	private TramiteService tramiteService;

	/**
	 * security service.
	 */
	@Inject
	private SecurityService securityService;

	@Inject
	private SystemService systemService;

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Id Area.
	 */
	private String idArea;

	/**
	 * Dato seleccionado en la lista.
	 */
	private TramiteVersiones tramiteSeleccionada;

	/** Lista de datos. */
	private List<TramiteVersiones> listaTramiteVersiones;

	/** Dato seleccionado en la lista. */
	private TramiteVersion versionSeleccionada;

	private List<Area> listaAreas;

	private List<Area> listaAreasSeleccionadas;

	/**
	 * Para evitar el retrase en las comprobaciones en perfil desarrollador.
	 **/
	private List<TypeRolePermisos> permisosCacheados = new ArrayList<>();
	private Long idAreaCacheada = -1l;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Id entidad
		final Long idEntidad = UtilJSF.getIdEntidad();
		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		// Recupera areas
		// Para obtener el idArea
		idArea = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getParameter("area");
		buscarAreas();
	}

	/**
	 * Abre dialogo para consultar datos de Area.
	 */
	public void consultarArea() {
		abrirArea(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Abre dialogo para editar dato de Area.
	 */
	public void editarArea() {
		abrirArea(TypeModoAcceso.EDICION);
	}

	/**
	 * Abre dialogo de nueva Area.
	 */
	public void nuevaArea() {
		UtilJSF.openDialog(DialogArea.class, TypeModoAcceso.ALTA, null, true, 520, 160);
	}

	/**
	 * Abre dialogo para eliminar area.
	 */
	public void eliminarArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		if (tramiteService.removeArea(area.getCodigo())) {
			listaAreasSeleccionadas.clear();
			this.buscarAreas();
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}

	}

	/**
	 * Dominios de area.
	 */
	public void dominioArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		UtilJSF.redirectJsfPage(
				"/secure/app/viewDominios.xhtml?ambito=A&id=" + area.getCodigo() + "&area=" + area.getIdentificador());
	}

	/**
	 * Fuente datos area.
	 */
	public void datosArea() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		UtilJSF.redirectJsfPage(
				"/secure/app/viewFuentes.xhtml?ambito=A&id=" + area.getCodigo() + "&area=" + area.getIdentificador());
	}

	/**
	 * Abre dialogo para consultar datos de tramite.
	 */
	public void consultarTramite() {
		abrirTramite(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Abre dialogo para editar tramite.
	 */
	public void editarTramite() {

		abrirTramite(TypeModoAcceso.EDICION);
	}

	/**
	 * Importar versión.
	 */
	public void importar() {
		final Map<String, String> params = new HashMap<>();
		UtilJSF.openDialog(DialogTramiteImportar.class, TypeModoAcceso.EDICION, params, true, 900, 520);
	}

	/**
	 * Return dialogo de importar.
	 *
	 * @param event
	 */
	public final void returnDialogoImportar(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {

			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok"));

			// Refrescamos datos
			this.buscarTramites();
		}
	}

	/**
	 * Abre dialogo de nuevo tramite.
	 */
	public void nuevoTramite() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();

		final Area area = listaAreasSeleccionadas.get(0);
		params.put(TypeParametroVentana.AREA.toString(), String.valueOf(area.getCodigo()));
		UtilJSF.openDialog(DialogTramite.class, TypeModoAcceso.ALTA, params, true, 540, 220);
	}

	/**
	 * Abre dialogo para eliminar tramite.
	 */
	public void eliminarTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		if (tramiteService.tieneTramiteVersion(tramiteSeleccionada.getTramite().getCodigo())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.errorTramiteVersion"));
			return;
		}

		if (tramiteService.removeTramite(tramiteSeleccionada.getTramite().getCodigo())) {
			buscarTramites();
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}

	}

	/**
	 * Abre dialogo para editar el control de acceso de un trámite.
	 */
	public void controlAcceso() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteControlAcceso.class, TypeModoAcceso.EDICION, params, true, 1000, 500);

	}

	/**
	 * Retorno dialogo control acceso.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoControlAcceso(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			final String message = UtilJSF.getLiteral("info.modificado.ok");
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarAreas();
		}
	}

	/**
	 * Abre un dialogo para previsualizar tramite.
	 */
	public void previsualizar() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.versionSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogTramiteVersionPrevisualizar.class, TypeModoAcceso.CONSULTA, params, true, 750, 400);
	}

	/**
	 * Retorno dialogo area.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoArea(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarAreas();
		}
	}

	/**
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public boolean getPermiteAltaArea() {
		boolean retorno;
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			retorno = true;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.DESARROLLO) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditarArea() {
		boolean retorno;
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			retorno = true;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.DESARROLLO) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public boolean getPermiteAltaTramite() {
		boolean res = false;

		// Verifica si no hay fila seleccionada
		if (!verificarFilasSeleccionadasArea()) {
			return false;
		}

		// Admin entidad
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			res = true;
		}

		// Desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.DESARROLLO) {
			final Area areaSeleccionada = listaAreasSeleccionadas.get(0);
			if (areaSeleccionada != null) {
				if (areaSeleccionada.getCodigo().compareTo(idAreaCacheada) != 0) {
					permisosCacheados = securityService
							.getPermisosDesarrolladorEntidadByArea(areaSeleccionada.getCodigo());
				}

				res = permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisosCacheados.contains(TypeRolePermisos.DESARROLLADOR_AREA);
				idAreaCacheada = areaSeleccionada.getCodigo();

			}
		}
		return res;
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditarTramite() {
		boolean res = false;

		// Verifica si no hay fila seleccionada
		if (!verificarFilasSeleccionadasArea()) {
			return false;
		}

		// Admin entidad
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			res = true;
		}

		// Desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.DESARROLLO) {
			final Area areaSeleccionada = listaAreasSeleccionadas.get(0);
			if (areaSeleccionada != null) {
				if (areaSeleccionada.getCodigo().compareTo(idAreaCacheada) != 0) {
					permisosCacheados = securityService
							.getPermisosDesarrolladorEntidadByArea(areaSeleccionada.getCodigo());
				}
				res = (permisosCacheados.contains(TypeRolePermisos.DESARROLLADOR_AREA)
						|| permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA));
				idAreaCacheada = areaSeleccionada.getCodigo();
			}
		}

		return res;
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteConsultarTramite() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return false;
		}

		final Area areaSeleccionada = listaAreasSeleccionadas.get(0);
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			if (areaSeleccionada == null) {
				return false;
			} else {
				if (areaSeleccionada.getCodigo().compareTo(idAreaCacheada) != 0) {
					permisosCacheados = securityService
							.getPermisosDesarrolladorEntidadByArea(areaSeleccionada.getCodigo());
				}
				if (TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.DESARROLLO) {
					return (permisosCacheados.contains(TypeRolePermisos.CONSULTA));
				} else {
					return (permisosCacheados.contains(TypeRolePermisos.CONSULTA)
							|| permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
							|| permisosCacheados.contains(TypeRolePermisos.DESARROLLADOR_AREA));
				}
			}
		}
		return false;
	}

	/**
	 * Método publico de filtrar
	 */
	public void filtrar() {
		this.buscarTramites(filtro);
	}

	/**
	 * Retorno dialogo tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTramite(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarTramites();
		}
	}

	/**
	 * Retorno dialogo mover tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoMoverTramite(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {
			// Mensaje
			String message = null;
			message = UtilJSF.getLiteral("info.modificado.ok");
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarTramites();
		}
	}

	/**
	 * Procedimientos tramite.
	 */
	public void procedimientosTramite() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	private boolean verificarFilasSeleccionadasArea() {
		boolean filaSeleccionada = true;
		if (this.listaAreasSeleccionadas == null || listaAreasSeleccionadas.isEmpty()) {
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Verifica si hay fila seleccionada de area.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaArea() {
		boolean filaSeleccionada = true;
		if (this.listaAreasSeleccionadas == null || listaAreasSeleccionadas.isEmpty()
				|| listaAreasSeleccionadas.size() > 1) {
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	public boolean getSeleccionadasAreas() {
		return verificarFilasSeleccionadasArea();
	}

	public boolean getSeleccionadaArea() {
		return verificarFilaSeleccionadaArea();
	}

	/**
	 * Verifica si hay fila seleccionada de tramite.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaTramite() {
		boolean filaSeleccionada = true;
		if (this.tramiteSeleccionada == null) {
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Verifica si hay fila seleccionada de version.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaVersion() {
		boolean filaSeleccionada = true;
		if (this.versionSeleccionada == null) {
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Buscar areas.
	 */
	private void buscarAreas() {
		final List<Area> listaTodasAreas = tramiteService.listArea(UtilJSF.getSessionBean().getEntidad().getCodigo(),
				this.filtro);

		if (getListaAreas() == null) {
			setListaAreas(new ArrayList<>());
		} else {
			getListaAreas().clear();
		}

		// filtramos las areas del desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			setListaAreas(listaTodasAreas);
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			for (final Area area : listaTodasAreas) {
				final List<TypeRolePermisos> permisos = securityService
						.getPermisosDesarrolladorEntidadByArea(area.getCodigo());

				if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
						|| permisos.contains(TypeRolePermisos.CONSULTA)) {
					getListaAreas().add(area);
				}
			}

		}

		if (StringUtils.isNotEmpty(idArea)) {
			for (final Area area : getListaAreas()) {
				if (area.getCodigo().compareTo(Long.valueOf(idArea)) == 0) {
					if (listaAreasSeleccionadas == null) {
						listaAreasSeleccionadas = new ArrayList<>();
					}
					listaAreasSeleccionadas.add(area);
				}
			}
		} else {
			if (listaAreasSeleccionadas == null) {
				listaAreasSeleccionadas = new ArrayList<>();
			}

			for (final Area area : getListaAreas()) {
				listaAreasSeleccionadas.add(area);
			}
		}

		// Lo desactivamos
		idArea = null;

		buscarTramites();
	}

	private void buscarTramites() {
		buscarTramites(null);
	}

	/**
	 * Buscar tramites.
	 */
	private void buscarTramites(final String filtro) {
		// Quitamos seleccion de dato
		tramiteSeleccionada = null;
		versionSeleccionada = null;

		if (listaTramiteVersiones == null) {
			listaTramiteVersiones = new ArrayList<>();
		} else {
			listaTramiteVersiones.clear();
		}

		// Verifica si no hay fila seleccionada
		if (!verificarFilasSeleccionadasArea()) {
			return;
		}
		for (final Area areaSeleccionada : listaAreasSeleccionadas) {

			final List<Tramite> tramites = tramiteService.listTramite(areaSeleccionada.getCodigo(), this.filtro);

			// Obtenemos activa a los tramites que tengan alguna version activa
			final List<Long> idTramites = tramiteService.listTramiteVersionActiva(areaSeleccionada.getCodigo());

			for (final Tramite tramite : tramites) {
				if (idTramites.contains(tramite.getCodigo())) {
					tramite.setActivo(true);
				}

				final List<TramiteVersion> listaVersiones = tramiteService.listTramiteVersion(tramite.getCodigo(),
						null);

				if ((StringUtils.isNotEmpty(filtro)
						&& (tramite.getIdentificador().toUpperCase().contains(filtro.toUpperCase())
								|| tramite.getDescripcion().toUpperCase().contains(filtro.toUpperCase())))
						|| StringUtils.isEmpty(filtro))
					listaTramiteVersiones.add(new TramiteVersiones(tramite, listaVersiones));
			}

		}

		Collections.sort(listaTramiteVersiones,
				(o1, o2) -> o1.getTramite().getIdentificador().compareTo((o2.getTramite().getIdentificador())));

	}

	public String getIdentificadorArea(final String idArea) {

		for (final Area area : listaAreasSeleccionadas) {
			if (area.getCodigo().equals(Long.valueOf(idArea))) {
				return area.getIdentificador();
			}
		}
		return "";

	}

	/**
	 * Abre dialogo para editar dato de Area.
	 */
	private void abrirArea(final TypeModoAcceso modoAcceso) {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}
		final Area areaSeleccionada = listaAreasSeleccionadas.get(0);

		params.put(TypeParametroVentana.ID.toString(), String.valueOf(areaSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogArea.class, modoAcceso, params, true, 520, 160);
	}

	/**
	 * Abre dialogo para editar tramite.
	 */
	private void abrirTramite(final TypeModoAcceso modoAcceso) {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		params.put(TypeParametroVentana.ID.toString(),
				String.valueOf(this.tramiteSeleccionada.getTramite().getCodigo()));
		UtilJSF.openDialog(DialogTramite.class, modoAcceso, params, true, 540, 220);
	}

	/**
	 * Abre dialogo para mover tramite.
	 */
	public void moverTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		// Verificamos que los dominiosId que tiene asignado, alguna de sus
		// versiones,
		// no
		// sea ninguno de ambito area.
		if (checkDominioArea()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("viewTramites.error.tramiteConDominioAreas"));
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(),
				String.valueOf(this.tramiteSeleccionada.getTramite().getCodigo()));
		UtilJSF.openDialog(DialogMoverTramite.class, TypeModoAcceso.EDICION, params, true, 540, 180);
	}

	/**
	 * Comprobamos que no tiene ningun dominio de tipo area asignado.
	 *
	 * @return
	 */
	private boolean checkDominioArea() {
		final List<TramiteVersion> tramitesVersion = tramiteService
				.listTramiteVersion(this.tramiteSeleccionada.getTramite().getCodigo(), null);
		if (tramitesVersion != null) {
			for (final TramiteVersion tramiteVersion : tramitesVersion) {
				final List<Long> dominiosId = tramiteService.getTramiteDominiosId(tramiteVersion.getCodigo());
				if (dominiosId != null) {
					for (final Long dominioId : dominiosId) {
						final Dominio dominio = dominioService.loadDominio(dominioId);
						if (dominio.getAmbito() == TypeAmbito.AREA) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void unSelectTramite() {
		tramiteSeleccionada = null;
	}

	public void unSelectVersionTramite() {
		versionSeleccionada = null;
	}

	/**
	 * Abre dialogo para nueva version.
	 */
	public void nuevaVersion() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		Long idTramite = null;
		if (this.versionSeleccionada != null) {
			idTramite = this.versionSeleccionada.getIdTramite();
		} else {
			idTramite = this.tramiteSeleccionada.getTramite().getCodigo();
		}
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(idTramite));
		UtilJSF.openDialog(DialogTramiteVersion.class, TypeModoAcceso.ALTA, params, true, 400, 150);

	}

	/**
	 * Retorno dialogo nueva version.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoNuevaVersion(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarTramites();
		}
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion())
			return;

		// Muestra dialogo
		final Map<String, List<String>> params = new HashMap<>();
		// params.put(TypeParametroVentana.ID.toString(),

		params.put(TypeParametroVentana.ID.toString(), Arrays.asList(versionSeleccionada.getCodigo().toString()));
		UtilJSF.redirectJsfPage("/secure/app/viewDefinicionVersion.xhtml", params);

	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void consultarVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion())
			return;

		// Muestra dialogo
		final Map<String, List<String>> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), Arrays.asList(String.valueOf(versionSeleccionada.getCodigo())));
		params.put(TypeParametroVentana.MODO_ACCESO.toString(), Arrays.asList(TypeModoAcceso.CONSULTA.name()));
		UtilJSF.redirectJsfPage("/secure/app/viewDefinicionVersion.xhtml", params);

	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminarVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion())
			return;

		// Eliminamos
		this.tramiteService.removeTramiteVersion(this.versionSeleccionada.getCodigo());

		// Refrescamos datos
		buscarTramites();

		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
	}

	/**
	 * Bloquear version. Pautas a seguir:
	 * <ul>
	 * <li>Revisar si el tramite versión está bloqueado.</li>
	 * <li>Revisar si tiene permiso de edición.</li>
	 * <li>Sólo se puede bloquear en el entorno de desarrollo.</li>
	 * </ul>
	 */
	public void bloquear() {

		if (!verificarFilaSeleccionadaVersion())
			return;

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.entorno.noDesarrollo"));
			return;
		}

		if (this.versionSeleccionada.getBloqueada()) {
			// Si está bloqueada, entonces no puedes bloquearlo.
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.bloquear.errorYaBloqueado"));
			return;
		}

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final Area areaSeleccionada = listaAreasSeleccionadas.get(0);
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(areaSeleccionada.getCodigo());
			// Si no puedes editar, no puedes bloquear
			if (!permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.permisos.insuficientes"));
				return;
			}
		}

		tramiteService.bloquearTramiteVersion(this.versionSeleccionada.getCodigo(),
				UtilJSF.getSessionBean().getUserName());

		editarVersion();
		// buscarTramites();

	}

	/**
	 * Bloquear version.
	 */
	public void desbloquear() {

		if (!verificarFilaSeleccionadaVersion())
			return;

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.entorno.noDesarrollo"));
			return;
		}

		if (!this.versionSeleccionada.getBloqueada()) {
			// Si está desbloqueada, entonces no puedes bloquearlo.
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.bloquear.errorYaDesbloqueado"));
			return;
		}

		if ((UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& !this.versionSeleccionada.getDatosUsuarioBloqueo().equals(UtilJSF.getSessionBean().getUserName()))) {
			// Si no puedes editar, no puedes bloquear
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.permisos.insuficientes"));
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());

		UtilJSF.openDialog(DialogTramiteDesbloquear.class, TypeModoAcceso.EDICION, params, true, 500, 320);

	}

	/**
	 * El returnDialog de desbloquear.
	 *
	 * @param event
	 */
	public void returnDialogoDesbloquear(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {
			buscarTramites();
		}
	}

	/**
	 * Calcula si se puede bloquear el tramite o no.
	 *
	 */
	public boolean isPermiteBloquear() {
		boolean resultado = true;

		if (this.versionSeleccionada == null) {
			resultado = false;
		} else if (versionSeleccionada.getBloqueada()) {
			resultado = false;
		}

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			resultado = false;
		}

		return resultado;

	}

	/**
	 * Calcula si se puede desbloquear el tramite o no.
	 *
	 */
	public boolean isPermiteDesbloquear() {
		boolean permiteDesbloquear = true;

		if (this.versionSeleccionada == null) {
			permiteDesbloquear = false;
		} else if (!versionSeleccionada.getBloqueada()) {
			permiteDesbloquear = false;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& !this.versionSeleccionada.getDatosUsuarioBloqueo().equals(UtilJSF.getSessionBean().getUserName())) {
			permiteDesbloquear = false;
		}

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			permiteDesbloquear = false;
		}

		return permiteDesbloquear;
	}

	/**
	 * Exportar version.
	 */
	public void exportarVersion() {
		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteExportar.class, TypeModoAcceso.EDICION, params, true, 900, 520);
	}

	/**
	 * Metodo prinicpal para refrescar la cache de un tramite (es llamado desde
	 * viewTramites y viewDefinicionVersion)
	 *
	 * @param identificadorTramite
	 */
	public void refrescarCache(final String identificadorTramite, final int numeroVersion) {
		if (identificadorTramite != null) {
			final String urlBase = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_URL);
			final String usuario = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_USER);
			final String pwd = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_PWD);

			final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, "T",
					identificadorTramite + "#" + numeroVersion);
			if (resultado.getCodigo() == 1) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.refrescar"));
			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("error.refrescar") + ": " + resultado.getMensaje());
			}
		}
	}

	/**
	 * Regrescar.
	 */
	public void refrescar() {
		String identificador = null;

		for (final TramiteVersiones tramite : listaTramiteVersiones) {
			if (tramite.getTramite().getCodigo().equals(versionSeleccionada.getIdTramite())) {
				identificador = tramite.getTramite().getIdentificador();
				break;
			}
		}

		if (identificador != null) {
			refrescarCache(identificador, versionSeleccionada.getNumeroVersion());
		}
	}

	/**
	 * Retorno dialogo clonar.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoClonar(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			final String message = UtilJSF.getLiteral("info.clonado.ok");
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Mensaje dialogo
			if (respuesta.getMensaje() != null) {
				UtilJSF.addMessageContext(respuesta.getMensaje().getNivel(), respuesta.getMensaje().getMensaje());
			}

			// Refrescamos datos
			buscarTramites();
		}

	}

	/**
	 * Duplicar.
	 */
	public void duplicarVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.versionSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogTramiteClonar.class, TypeModoAcceso.ALTA, params, true, 500, 150);

	}

	/**
	 * Historial.
	 */
	public void historialVersion() {

		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());
		UtilJSF.openDialog(DialogHistorialVersion.class, TypeModoAcceso.CONSULTA, params, true, 900, 520);

	}

	/**
	 * Obtiene el valor de filtro.
	 *
	 * @return el valor de filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * Establece el valor de filtro.
	 *
	 * @param filtro
	 *            el nuevo valor de filtro
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * Obtiene el valor de tramiteSeleccionada.
	 *
	 * @return el valor de tramiteSeleccionada
	 */
	public TramiteVersiones getTramiteSeleccionada() {
		return tramiteSeleccionada;
	}

	/**
	 * Establece el valor de tramiteSeleccionada.
	 *
	 * @param tramiteSeleccionada
	 *            el nuevo valor de tramiteSeleccionada
	 */
	public void setTramiteSeleccionada(final TramiteVersiones tramiteSeleccionada) {
		this.tramiteSeleccionada = tramiteSeleccionada;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	public TramiteVersion getVersionSeleccionada() {
		return versionSeleccionada;
	}

	public void setVersionSeleccionada(final TramiteVersion versionSeleccionado) {
		this.versionSeleccionada = versionSeleccionado;
	}

	public List<TramiteVersiones> getListaTramiteVersiones() {
		return listaTramiteVersiones;
	}

	public void setListaTramiteVersiones(final List<TramiteVersiones> listaTramiteVersiones) {
		this.listaTramiteVersiones = listaTramiteVersiones;
	}

	public List<Area> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<Area> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public List<Area> getListaAreasSeleccionadas() {
		return listaAreasSeleccionadas;
	}

	public void setListaAreasSeleccionadas(final List<Area> listaAreasSeleccionadas) {
		this.listaAreasSeleccionadas = listaAreasSeleccionadas;
	}

}
