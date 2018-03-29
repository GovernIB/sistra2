package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.PluginService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

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

	/** Filtro. */
	private String filtro;

	/** Lista de datos. */
	private List<Plugin> listaDatos;

	/** Dato seleccionado en la lista. */
	private Plugin datoSeleccionado;

	/** Plugin service. */
	@Inject
	private PluginService pluginService;

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

		switch (ambitoType) {
		case GLOBAL:
			// Verificamos acceso
			UtilJSF.verificarAccesoSuperAdministrador();
			// Recuperamos datos
			listaDatos = pluginService.listPlugin(ambitoType, null, filtro);
			break;
		case ENTIDAD:
			// Verificamos acceso
			UtilJSF.verificarAccesoAdministradorDesarrolladorEntidad(idEntidad);
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
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		UtilJSF.openDialog(DialogPlugin.class, TypeModoAcceso.ALTA, params, true, 640, 400);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getId().toString());
		UtilJSF.openDialog(DialogPlugin.class, TypeModoAcceso.EDICION, params, true, 640, 400);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Eliminamos
		if (pluginService.removePlugin(this.datoSeleccionado.getId())) {
			// Refrescamos datos
			buscar();
			// Mostramos mensaje
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}
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

}
