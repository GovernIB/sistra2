package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.PluginService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;

/**
 * Mantenimiento de plugins (global y de entidad).
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewPlugins extends ViewControllerBase {

	/** Ambito. **/
	private String ambito;

	/** Id entidad activa. */
	private Long idEntidad;

	private Entidad entidad;

	/** Filtro. */
	private String filtro;

	/** Lista de datos. */
	private List<Plugin> listaDatos;

	/** Dato seleccionado en la lista. */
	private Plugin datoSeleccionado;

	/** Plugin service. */
	@Inject
	private PluginService pluginService;

	@Inject
	private SystemService systemService;

	@Inject
	private EntidadService entidadService;

	/**
	 * Inicializacion.
	 */
	public void init() {

		if (ambito == null) {
			throw new FrontException("No se ha indicado ambito");
		}

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);

		final TypeAmbito ambitoType = TypeAmbito.fromString(ambito);

		// Obtenemos entidad activa
		idEntidad = UtilJSF.getIdEntidad();
		if (idEntidad != null) {
			setEntidad(entidadService.loadEntidad(idEntidad));
		}

		switch (ambitoType) {
		case GLOBAL:
			// Verificamos acceso
			UtilJSF.verificarAccesoSuperAdministrador();
			// Recuperamos datos
			listaDatos = pluginService.listPlugin(ambitoType, null, filtro);
			break;
		case ENTIDAD:
			// Verificamos acceso
			UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);
			// Recuperamos datos
			listaDatos = pluginService.listPlugin(ambitoType, idEntidad, filtro);
			break;
		default:
			throw new FrontException("Tipo ambito no permitido: " + ambito);
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
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		abrirDlgPlugin(TypeModoAcceso.ALTA);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		abrirDlgPlugin(TypeModoAcceso.EDICION);
	}

	/**
	 * Abre dialogo para consultar dato.
	 */
	public void consultar() {
		abrirDlgPlugin(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Eliminamos
		if (pluginService.removePlugin(this.datoSeleccionado.getCodigo())) {
			// Refrescamos datos
			buscar();
			// Mostramos mensaje
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}
	}

	/**
	 * Refrescar.
	 */
	public void refrescar() {
		final String urlBase = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_URL);
		final String usuario = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_USER);
		final String pwd = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_PWD);

		String tipo = null;
		String identificador = null;
		switch (TypeAmbito.fromString(ambito)) {
		case GLOBAL:
			tipo = "C";
			break;
		case ENTIDAD:
			tipo = "E";
			if (entidad != null) {
				identificador = entidad.getCodigoDIR3();
			}
			break;
		default:
			break;
		}
		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, tipo, identificador);
		if (resultado.getCodigo() == 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.refrescar"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("error.refrescar") + ": " + resultado.getMensaje());
		}

	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		if (getPermiteEditar()) {
			editar();
		} else {
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
			buscar();
		}
	}

	public void ayuda() {
		UtilJSF.openHelp("plugins");
	}

	/**
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public boolean getPermiteAlta() {
		return getPermiteEditar();
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditar() {
		boolean res = false;
		final TypeAmbito ambitoType = TypeAmbito.fromString(ambito);
		if (ambitoType == TypeAmbito.GLOBAL) {
			res = true;
		} else if (ambitoType == TypeAmbito.ENTIDAD) {
			res = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
		}
		return res;
	}

	// ------- FUNCIONES PRIVADAS ------------------------------
	/**
	 * Buscar datos.
	 */
	private void buscar() {
		// Filtra
		listaDatos = pluginService.listPlugin(TypeAmbito.fromString(ambito), idEntidad, filtro);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Abre dialogo.
	 *
	 * @param modoAccesoDlg
	 *            Modo acceso
	 */
	private void abrirDlgPlugin(final TypeModoAcceso modoAccesoDlg) {
		// Verifica si no hay fila seleccionada
		if (modoAccesoDlg != TypeModoAcceso.ALTA && !verificarFilaSeleccionada())
			return;
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		if (modoAccesoDlg != TypeModoAcceso.ALTA) {
			params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
		}
		UtilJSF.openDialog(DialogPlugin.class, modoAccesoDlg, params, true, 660, 550);
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
	public List<Plugin> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<Plugin> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public Plugin getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Plugin datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
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

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
	}

}
