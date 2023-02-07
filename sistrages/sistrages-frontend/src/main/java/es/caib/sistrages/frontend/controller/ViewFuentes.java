package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.Sesion;
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
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de fuente de datos entidad.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewFuentes extends ViewControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** security service. */
	@Inject
	private SecurityService securityService;

	/** sistem service. */
	@Inject
	private SystemService systemService;

	/** tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id. **/
	private String id;

	/** Ambito. **/
	private String ambito;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Lista de datos. */
	private List<FuenteDatos> listaDatos;

	/** Dato seleccionado en la lista. */
	private FuenteDatos datoSeleccionado;

	/** Paginacion */
	private Integer paginacion;

	/** Permite editar. **/
	private Boolean permiteEditar = false;

	/** Permite consultar. **/
	private Boolean permiteConsultar = false;

	/** Permite mostrar el breadcrumb. **/
	private Boolean mostrarBreadcrumb = false;

	/** Miga de pan. **/
	private DefaultMenuModel breadCrumb;

	/** Area. **/
	private String area;

	/** Accion. **/
	private String accion;

	/** Lista de areas. */
	private List<Area> listaAreas;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicializacion.
	 */
	public void init() {

		final Long idEntidad = UtilJSF.getIdEntidad();
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);

		if (ambito == null) {
			return;
		}
		setAccion("F");

		paginacion = UtilJSF.getPaginacion("viewFuentes");

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);
		checkPermisos();
		buscar(null);

		if (ambito.equals(TypeAmbito.AREA.toString())) {
			Sesion sesion = null;

			// Recupera info usuario
			String userName = securityService.getUsername();

			// recuperamos datos por defecto del usuario
			if (!userName.isEmpty()) {
				sesion = systemService.getSesion(userName);
				listaAreas = tramiteService.listArea(sesion.getEntidad(), null);
				if (!tramiteService.getArea(Long.parseLong(id)).getIdentificador().equals(area)) {
					area = tramiteService.getArea(Long.parseLong(id)).getIdentificador();
				}
			}

			mostrarBreadcrumb = true;
			/* inicializa breadcrum y lo creamos */
			breadCrumb = new DefaultMenuModel();

			DefaultMenuItem item = null;

			item = new DefaultMenuItem(area);
			item.setUrl("/secure/app/viewTramites.xhtml?area=" + id);
			breadCrumb.addElement(item);

		} else {
			mostrarBreadcrumb = false;
		}
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		buscar(filtro);

		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Cambia de acción
	 */
	public void cambiarAccion() {
		switch (accion) {
		case "V":
			UtilJSF.redirectJsfPage("/secure/app/viewVariablesArea.xhtml?id=" + id);
		case "D":
			UtilJSF.redirectJsfPage("/secure/app/viewDominios.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "F":
			break;
		case "S":
			UtilJSF.redirectJsfPage("/secure/app/viewFormulariosExternos.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "C":
			UtilJSF.redirectJsfPage(
					"/secure/app/viewConfiguracionAutenticacion.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "E":
			UtilJSF.redirectJsfPage("/secure/app/viewEnviosRemotos.xhtml?ambito=A&id=" + id + "&area=" + area);
		default:
			break;
		}
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		// Verifica si tiene permisos
		if (!permiteEditar) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AREA.toString(), this.id);
		params.put(TypeParametroVentana.AMBITO.toString(), this.ambito);
		UtilJSF.openDialog(DialogFuente.class, TypeModoAcceso.ALTA, params, true, 740, 450);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		abrirDialogo(TypeModoAcceso.EDICION);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void consultar() {
		abrirDialogo(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Verifica si tiene permisos
		if (!permiteEditar) {
			return;
		}

		// Eliminamos
		final List<String> identificadorDominios = this.dominioService
				.listDominiosByFD(this.datoSeleccionado.getCodigo());
		if (!dominioService.removeFuenteDato(this.datoSeleccionado.getCodigo())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		} else {
			// Refrescamos datos
			filtrar();
			// Mostramos mensaje
			String message = "";
			ResultadoError re = null;
			boolean hayError = false;
			String msgErr = "";
			for (final String identificador : identificadorDominios) {
				re = this.refrescar();
				if (re.getCodigo() != 1) {
					hayError = true;
					msgErr = re.getMensaje();
				}
			}
			if (hayError) {
				message = UtilJSF.getLiteral("info.borrado.ok") + ". " + UtilJSF.getLiteral("error.refrescarCache")
						+ ": " + msgErr;
			} else if (!hayError && !identificadorDominios.isEmpty()) {
				message = UtilJSF.getLiteral("info.borrado.ok") + ". " + UtilJSF.getLiteral("info.cache.ok");
			} else {
				message = UtilJSF.getLiteral("info.borrado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

		}
	}

	/**
	 * Datos.
	 */
	public void datos() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		TypeModoAcceso modoAccesoType;
		if (permiteEditar) {
			modoAccesoType = TypeModoAcceso.EDICION;
		} else {
			modoAccesoType = TypeModoAcceso.CONSULTA;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
		UtilJSF.openDialog(DialogFuenteDatos.class, modoAccesoType, params, true, 740, 330);
	}

	/**
	 * Abrir ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("fuentes");
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;

			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				final List<String> identificadorDominios = this.dominioService
						.listDominiosByFD(this.datoSeleccionado.getCodigo());
				ResultadoError re = null;
				boolean hayError = false;
				String msgErr = "";
				for (final String identificador : identificadorDominios) {
					re = this.refrescar();
					if (re.getCodigo() != 1) {
						hayError = true;
						msgErr = re.getMensaje();
					}
				}
				if (hayError) {
					message = UtilJSF.getLiteral("info.modificado.ok") + ". "
							+ UtilJSF.getLiteral("error.refrescarCache") + ": " + msgErr;
				} else if (!hayError && !identificadorDominios.isEmpty()) {
					message = UtilJSF.getLiteral("info.modificado.ok") + ". " + UtilJSF.getLiteral("info.cache.ok");
				} else {
					message = UtilJSF.getLiteral("info.modificado.ok");
				}
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscar(filtro);
		}
	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		if (permiteEditar) {
			editar();
		} else if (permiteConsultar) {
			consultar();
		}
	}

	/**
	 * Obtiene el valor de filaSeleccionada.
	 *
	 * @return el valor de filaSeleccionada
	 */
	public boolean getFilaSeleccionada() {
		return verificarFilaSeleccionada();
	}

	/**
	 * Método global que prepara las variables permiteEditar o permiteConsultar.
	 * <br />
	 * - El ambito es global, sólo si tienes permiso superadmin.<br />
	 * - El ambito es entidad, revisar el metodo para ver como se controla los
	 * permisos. - El ambito es area, revisar el metodo para ver como se controla
	 * los permisos.
	 */
	public void checkPermisos() {

		permiteEditar = false;
		permiteConsultar = false;
		final TypeAmbito ambitoType = TypeAmbito.fromString(ambito);
		switch (ambitoType) {
		case GLOBAL:
			permiteEditar = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN);
			break;
		case ENTIDAD:
			if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
				permiteEditar = true;
			} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
				// if (UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
				// permiteEditar = true;
				// } else {
				permiteConsultar = true;
				// }
			}
			break;
		case AREA:
			checkPermisosArea();
			break;
		}

	}

	/**
	 * Checkea permisos area. Las reglas son las siguientes: <br />
	 * - Si eres administrador de entidad, puedes editar en cualquier entorno.
	 * <br />
	 * - Si eres desarrollador de entidad, depende del entorno y tus permisos:
	 * <br />
	 * ---> Permiso admin. area en des/pre/pro puedes tot <br />
	 * ---> Permiso desa. area en desarrollo puedes tot mientras que en pre/pro
	 * puede consultar <br />
	 * ---> Permiso consulta en des/pre/pro solo consulta.
	 *
	 */
	private void checkPermisosArea() {
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			permiteEditar = true;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(Long.valueOf(id));

			if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				permiteEditar = true;
			} else if (permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {
				if (UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
					permiteEditar = true;
				} else {
					permiteConsultar = true;
				}
			} else if (permisos.contains(TypeRolePermisos.CONSULTA)) {
				permiteConsultar = true;
			}
		}
	}

	// ------- FUNCIONES PRIVADAS ------------------------------
	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Filtro de buscar
	 *
	 * @param iFiltro
	 */
	private void buscar(final String iFiltro) {
		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
		listaDatos = dominioService.listFuenteDato(typeAmbito, Long.valueOf(id), iFiltro);

	}

	private void abrirDialogo(final TypeModoAcceso modo) {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Verifica si tiene permisos
		if (modo == TypeModoAcceso.EDICION && !permiteEditar) {
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
		params.put(TypeParametroVentana.AMBITO.toString(), this.ambito);
		params.put(TypeParametroVentana.AREA.toString(), this.id);
		UtilJSF.openDialog(DialogFuente.class, modo, params, true, 740, 450);

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

	// ------- GETTERS / SETTERS --------------------------------

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the listaDatos
	 */
	public List<FuenteDatos> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public void setListaDatos(final List<FuenteDatos> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public FuenteDatos getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final FuenteDatos datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
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
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the permiteEditar
	 */
	public Boolean getPermiteEditar() {
		return permiteEditar;
	}

	/**
	 * @param permiteEditar the permiteEditar to set
	 */
	public void setPermiteEditar(final Boolean permiteEditar) {
		this.permiteEditar = permiteEditar;
	}

	/**
	 * @return the permiteConsultar
	 */
	public Boolean getPermiteConsultar() {
		return permiteConsultar;
	}

	/**
	 * @param permiteConsultar the permiteConsultar to set
	 */
	public void setPermiteConsultar(final Boolean permiteConsultar) {
		this.permiteConsultar = permiteConsultar;
	}

	/**
	 * @return the mostrarBreadcrumb
	 */
	public Boolean getMostrarBreadcrumb() {
		return mostrarBreadcrumb;
	}

	/**
	 * @param mostrarBreadcrumb the mostrarBreadcrumb to set
	 */
	public void setMostrarBreadcrumb(final Boolean mostrarBreadcrumb) {
		this.mostrarBreadcrumb = mostrarBreadcrumb;
	}

	/**
	 * @return the breadCrumb
	 */
	public DefaultMenuModel getBreadCrumb() {
		return breadCrumb;
	}

	/**
	 * @param breadCrumb the breadCrumb to set
	 */
	public void setBreadCrumb(final DefaultMenuModel breadCrumb) {
		this.breadCrumb = breadCrumb;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(final String area) {
		this.area = area;
	}

	/**
	 * @return the listaAreas
	 */
	public final List<Area> getListaAreas() {
		return listaAreas;
	}

	/**
	 * @param listaAreas the listaAreas to set
	 */
	public final void setListaAreas(List<Area> listaAreas) {
		this.listaAreas = listaAreas;
	}

	/**
	 * @return the paginacion
	 */
	public final Integer getPaginacion() {
		return paginacion;
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public final void setPaginacion(Integer paginacion) {
		this.paginacion = paginacion;
		UtilJSF.setPaginacion(paginacion, "viewFuentes");
	}

	/**
	 * @return the accion
	 */
	public final String getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public final void setAccion(String accion) {
		this.accion = accion;
	}
}
