package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.PluginGlobal;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroDialogo;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de plugins global.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewPluginsGlobales extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final PluginGlobal plugin1 = new PluginGlobal();
		plugin1.setCodigo(1l);
		plugin1.setTipo("Plugin de login");
		plugin1.setClassname("es.caib.example.plugins.classPluginLogin");
		final PluginGlobal plugin2 = new PluginGlobal();
		plugin2.setCodigo(2l);
		plugin2.setTipo("Plugin de representació");
		plugin2.setClassname("es.caib.example.plugins.classPluginRepresentacio");
		final PluginGlobal plugin3 = new PluginGlobal();
		plugin3.setCodigo(3l);
		plugin3.setTipo("Plugin de dominis remots");
		plugin3.setClassname("es.caib.example.plugins.classPluginDominisRemots");
		final PluginGlobal plugin4 = new PluginGlobal();
		plugin4.setCodigo(4l);
		plugin4.setTipo("Plugin de firma");
		plugin4.setClassname("es.caib.example.plugins.classPluginFirma");

		listaDatos = new ArrayList<>();
		listaDatos.add(plugin1);
		listaDatos.add(plugin2);
		listaDatos.add(plugin3);
		listaDatos.add(plugin4);

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<PluginGlobal> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private PluginGlobal datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null || filtro.isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		// Filtra
		final List<PluginGlobal> pluginesFiltradas = new ArrayList<>();
		for (final PluginGlobal plugin : this.listaDatos) {
			if (plugin.getClassname() != null && plugin.getClassname().toLowerCase().contains(filtro.toLowerCase())) {
				pluginesFiltradas.add(plugin);
			}
		}

		this.listaDatos = pluginesFiltradas;
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.filtro.ok"));
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		UtilJSF.openDialog(DialogPluginGlobal.class, TypeModoAcceso.ALTA, null, true, 640, 450);
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
		params.put(TypeParametroDialogo.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogPluginGlobal.class, TypeModoAcceso.EDICION, params, true, 640, 450);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Eliminamos
		listaDatos.remove(this.datoSeleccionado);
		// Refrescamos datos
		filtrar();
		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
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

		final String message = null;

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

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
	public List<PluginGlobal> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<PluginGlobal> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public PluginGlobal getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final PluginGlobal datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
