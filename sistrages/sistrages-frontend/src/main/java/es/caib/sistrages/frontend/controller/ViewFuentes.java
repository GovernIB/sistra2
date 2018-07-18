package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.frontend.model.DialogResult;
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

	/** Permite editar. **/
	private Boolean permiteEditar = false;

	/** Permite consultar. **/
	private Boolean permiteConsultar = false;

	/**
	 * Inicializacion.
	 */
	public void init() {

		final Long idEntidad = UtilJSF.getIdEntidad();
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);

		if (ambito == null) {
			return;
		}
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);
		checkPermisos();
		buscar(null);

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
		if (!dominioService.removeFuenteDato(this.datoSeleccionado.getCodigo())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		} else {
			// Refrescamos datos
			filtrar();
			// Mostramos mensaje
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
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
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
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
			permiteEditar = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
			permiteConsultar = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR);
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
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT
				|| UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN) {
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

	// ------- GETTERS / SETTERS --------------------------------
	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *            the filtro to set
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
	 * @param listaDatos
	 *            the listaDatos to set
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
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
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
	 * @param id
	 *            the id to set
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
	 * @param ambito
	 *            the ambito to set
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
	 * @param permiteEditar
	 *            the permiteEditar to set
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
	 * @param permiteConsultar
	 *            the permiteConsultar to set
	 */
	public void setPermiteConsultar(final Boolean permiteConsultar) {
		this.permiteConsultar = permiteConsultar;
	}

}
