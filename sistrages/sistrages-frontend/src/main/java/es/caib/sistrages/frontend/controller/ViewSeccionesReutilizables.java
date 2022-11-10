package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;

/**
 * Mantenimiento de configuracion autenticacion de area.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewSeccionesReutilizables extends ViewControllerBase {

	@Inject
	private SystemService systemService;

	@Inject
	private TramiteService tramiteService;

	@Inject
	private SeccionReutilizableService seccionService;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Lista de datos. */
	private List<SeccionReutilizable> listaDatos;

	/** Dato seleccionado en la lista. */
	private SeccionReutilizable datoSeleccionado;

	/** Id. **/
	private String id;
	private Long idEntidad;

	/** Ambito. **/
	private String ambito;

	/** Area. **/
	private String area;

	boolean permiteAlta = false;
	boolean permiteImportar = false;
	boolean permiteExportar = false;

	/** Mostrar breadcrumb. **/
	private boolean mostrarBreadcrumb;

	/** miga de pan */
	private MenuModel breadCrumb;

	/**
	 * Inicializacion.
	 */
	public void init() {

		// Titulo
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		idEntidad = UtilJSF.getIdEntidad();

		permiteAlta = UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT;

		permisosImportar();

		// Recupera datos
		buscar();

	}

	/**
	 * Los permisos de importar/exportar, sólo si eres administrador entidad
	 */
	private void permisosImportar() {
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			permiteImportar = true;
			permiteExportar = true;
		} else {
			permiteImportar = false;
			permiteExportar = false;
		}
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {
		// Normaliza filtro
		filtro = normalizarFiltro(filtro);
		// Busca
		buscar();
	}

	/**
	 * Buscar datos.
	 */
	private void buscar() {
		// Filtra
		listaDatos = seccionService.listSeccionReutilizable(idEntidad,  filtro, null);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		abrirDlg(TypeModoAcceso.ALTA);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		if (this.datoSeleccionado != null && this.datoSeleccionado.isBloqueado()) {
			abrirDlg(TypeModoAcceso.EDICION);
		} else {
			abrirDlg(TypeModoAcceso.CONSULTA);
		}
	}

	/**
	 * Abre dialogo para consultar dato.
	 */
	public void consultar() {
		abrirDlg(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		// Solo se pueden eliminar versiones desbloqueadas
		if (this.datoSeleccionado.isBloqueado()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewSeccionesReutilizables.eliminar.seccionbloqueada"));
			return;
		}

		boolean existenTramites = tramiteService.existenTramitesBySeccionReutilizable(datoSeleccionado.getCodigo());

		if (existenTramites) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewSeccionesReutilizables.error.tramitesAsociados"));
		} else {

			// Eliminamos
			if (seccionService.removeSeccion(datoSeleccionado.getCodigo())) {
				// Refrescamos datos
				buscar();
				// Mostramos mensaje
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
			}
		}
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

		if (!verificarFilaSeleccionada())
			return;

		if (!isPermiteBloquear()) {
			return;
		}

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.entorno.noDesarrollo"));
			return;
		}

		seccionService.bloquearSeccion(this.datoSeleccionado.getCodigo(),
				UtilJSF.getSessionBean().getUserName());

		Long idSeccion = datoSeleccionado.getCodigo();
		buscar();
		seleccionarByCodigo(idSeccion);
		editar();

	}

	/**
	 * Marca un dato por seleccionado según su id.
	 * @param idSeccion
	 */
	private void seleccionarByCodigo(Long idSeccion) {
		for(SeccionReutilizable dato : listaDatos) {
			if (dato.getCodigo().compareTo(idSeccion) == 0) {
				datoSeleccionado = dato;
				break;
			}
		}
	}

	/**
	 * Bloquear version.
	 */
	public void desbloquear() {

		if (!isPermiteDesbloquear()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());

		UtilJSF.openDialog(DialogSeccionDesbloquear.class, TypeModoAcceso.EDICION, params, true, 500, 380);

	}

	/**
	 * Importar un dominio.
	 */
	public void importar() {

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AMBITO.toString(), this.ambito);
		if (id != null) {
			params.put(TypeParametroVentana.ID.toString(), this.id);
		}
		UtilJSF.openDialog(DialogSeccionImportar.class, TypeModoAcceso.ALTA, params, true, 770, 280);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoImportar(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok"));

			// Refrescamos datos
			buscar();

		}

	}


	/**
	 * Retorno dialogo exportar.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoExportar(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("dialogSeccionExportar.exportadoCorrectamente"));
		}

	}

	/**
	 * Exportar un dominio.
	 */
	public void exportar() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		if (this.datoSeleccionado.isBloqueado()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("dialogSeccionExportar.info.seccionBloqueada"));
			return;
		}
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogSeccionExportar.class, TypeModoAcceso.ALTA, params, true, 500, 200);
	}

	/**
	 * Obtiene el valor de tienePermisosEditarSeccion, se tiene que cumplir que:
	 * <ul>
	 * <li>Tiene que haber una seccion seleccionada.</li>
	 * <li>Tiene que estar bloqueado</li>
	 * <ul> Cumplir una de las siguientes:
	 * 		<li>Si eres adm. entidad ya puedes editarlo.</li>
	 * 		<li>Sino tienes que ser adminisitrador o desarrollador de área y coincidir con el usuario que lo haya bloqueado.</li>
	 * </ul>
	 * </ul>
	 *
	 * @return el valor de tienePermisosTramite
	 */
	public boolean getTienePermisosEditarSeccion() {
		if (this.datoSeleccionado == null || !this.datoSeleccionado.isBloqueado()) {
			return false;
		} else {
			return UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT || UtilJSF.getSessionBean().getUserName().equals(this.datoSeleccionado.getBloqueadoUsuario()) ;
		}
	}


	/**
	 * Obtiene el valor de tienePermisosEditarSeccion, se tiene que cumplir que:
	 * <ul>
	 * <li>Tiene que haber una seccion seleccionada.</li>
	 * <li>Si no está bloqueado, se puede ver en modo consulta</li>
	 * </ul>
	 *
	 * @return el valor de tienePermisosTramite
	 */
	public boolean getTienePermisosConsultarSeccion() {
		if (this.datoSeleccionado == null) {
			return false;
		} else {
			return !getTienePermisosEditarSeccion();
		}
	}

	/**
	 * Refrescar cache.
	 */
	public void refrescarCache() {
		final String urlBase = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_URL.toString());
		final String usuario = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_USER.toString());
		final String pwd = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_PWD.toString());

		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, Constantes.CACHE_COMPLETA);

		if (resultado.getCodigo() == 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.refrescar"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("error.refrescar") + ": " + resultado.getMensaje());
		}

	}

	/**
	 * Calcula si se puede desbloquear el tramite o no. Se puede desblqouear si: -
	 * Estamos en desarrollo. - Si eres ADM. ENTIDAD puedes. - Si eres
	 * desarrollador: - Si eres adm. AREA puedes desbloquear - Si eres des. AREA
	 * sólo puedes desbloquear si eres el que lo bloqueó.
	 *
	 *
	 */
	public boolean isPermiteDesbloquear() {

		// Si no hay versión seleccionada o ya está desbloqueada
		if (this.datoSeleccionado == null || !datoSeleccionado.isBloqueado()) {
			return false;
		}

		// Solo se puede bloquear/desbloquear en desarrollo
		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			return false;
		}

		// El ADM. ENTIDAD puede desbloquearlo
		return UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT;
	}


	/**
	 * Calcula si se puede bloquear el tramite o no.
	 *
	 */
	public boolean isPermiteBloquear() {

		// Si no hay versión seleccionada o está bloqueada
		if (this.datoSeleccionado == null || datoSeleccionado.isBloqueado()) {
			return false;
		}

		// Solo se puede bloquear/desbloquear en desarrollo
		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			return false;
		}

		// El ADM. ENTIDAD puede desbloquearlo
		return UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT;

	}

	/**
	 * Abrir ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("seccionReutilizable");
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
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public boolean getPermiteAlta() {
		return permiteAlta;
	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		if (getTienePermisosEditarSeccion()) {
			editar();
		} else {
			consultar();
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *                  respuesta dialogo
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
				message = UtilJSF.getLiteral("info.modificado.ok");
			}

			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			if (respuesta.getResult() instanceof SeccionReutilizable) {
				Long idSeccion = ((SeccionReutilizable)respuesta.getResult()).getCodigo();
				buscar();
				seleccionarByCodigo(idSeccion);
			} else {
				buscar();
			}
		}
	}

	public void returnDialogoDesbloquear (final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {

			// Refrescamos datos
			Long idSeccion = datoSeleccionado.getCodigo();
			buscar();
			seleccionarByCodigo(idSeccion);
		}
	}

	/**
	 * Historial.
	 */
	public void historialVersion() {

		if (!verificarFilaSeleccionada()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
		UtilJSF.openDialog(DialogHistorialSeccion.class, TypeModoAcceso.CONSULTA, params, true, 900, 520);

	}


	/**
	 * Abrir dialogo.
	 *
	 * @param modoAccesoDlg
	 *                          Modo acceso
	 */
	private void abrirDlg(final TypeModoAcceso modoAccesoDlg) {
		// Verifica si no hay fila seleccionada
		if (modoAccesoDlg != TypeModoAcceso.ALTA && !verificarFilaSeleccionada()) {
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		if (modoAccesoDlg != TypeModoAcceso.ALTA) {
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		}
		params.put(TypeParametroVentana.AMBITO.toString(), TypeAmbito.ENTIDAD.toString());
		UtilJSF.openDialog(DialogSeccionReutilizable.class, modoAccesoDlg, params, true, 550, 240);
	}


	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *                   the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the listaDatos
	 */
	public List<SeccionReutilizable> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *                       the listaDatos to set
	 */
	public void setListaDatos(final List<SeccionReutilizable> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(final String area) {
		this.area = area;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public SeccionReutilizable getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *                             the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final SeccionReutilizable datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	public boolean isMostrarBreadcrumb() {
		return mostrarBreadcrumb;
	}

	public void setMostrarBreadcrumb(final boolean mostrarBreadcrumb) {
		this.mostrarBreadcrumb = mostrarBreadcrumb;
	}

	public MenuModel getBreadCrumb() {
		return breadCrumb;
	}

	public void setBreadCrumb(final MenuModel breadCrumb) {
		this.breadCrumb = breadCrumb;
	}

	/**
	 * @return the permiteImportar
	 */
	public boolean isPermiteImportar() {
		return permiteImportar;
	}

	/**
	 * @param permiteImportar the permiteImportar to set
	 */
	public void setPermiteImportar(boolean permiteImportar) {
		this.permiteImportar = permiteImportar;
	}

	/**
	 * @return the permiteExportar
	 */
	public boolean isPermiteExportar() {
		return permiteExportar;
	}

	/**
	 * @param permiteExportar the permiteExportar to set
	 */
	public void setPermiteExportar(boolean permiteExportar) {
		this.permiteExportar = permiteExportar;
	}

	/**
	 * @param permiteAlta the permiteAlta to set
	 */
	public void setPermiteAlta(boolean permiteAlta) {
		this.permiteAlta = permiteAlta;
	}


}
