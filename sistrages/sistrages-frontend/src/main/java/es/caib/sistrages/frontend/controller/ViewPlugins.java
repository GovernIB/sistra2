package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroDialogo;
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

	/** Id. **/
	private String id;

	/**
	 * Inicializacion.
	 */
	public void init() {

		if (ambito == null) {
			return;
		}

		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);

		listaDatos = new ArrayList<>();
		switch (typeAmbito) {
		case GLOBAL:
			final Plugin plugin1 = new Plugin();
			plugin1.setId(1l);
			plugin1.setTipo("Plugin de login GLOBAL");
			plugin1.setClassname("es.caib.example.plugins.classPluginLogin");
			final Plugin plugin2 = new Plugin();
			plugin2.setId(2l);
			plugin2.setTipo("Plugin de representació");
			plugin2.setClassname("es.caib.example.plugins.classPluginRepresentacio");
			final Plugin plugin3 = new Plugin();
			plugin3.setId(3l);
			plugin3.setTipo("Plugin de dominis remots");
			plugin3.setClassname("es.caib.example.plugins.classPluginDominisRemots");
			final Plugin plugin4 = new Plugin();
			plugin4.setId(4l);
			plugin4.setTipo("Plugin de firma");
			plugin4.setClassname("es.caib.example.plugins.classPluginFirma");

			listaDatos.add(plugin1);
			listaDatos.add(plugin2);
			listaDatos.add(plugin3);
			listaDatos.add(plugin4);
		case ENTIDAD:
			final Plugin pluginEntidad1 = new Plugin();
			pluginEntidad1.setId(1l);
			pluginEntidad1.setTipo("Plugin de login  Entidad");
			pluginEntidad1.setClassname("es.caib.example.plugins.classPluginLogin");
			final Plugin pluginEntidad2 = new Plugin();
			pluginEntidad2.setId(2l);
			pluginEntidad2.setTipo("Plugin de representació");
			pluginEntidad2.setClassname("es.caib.example.plugins.classPluginRepresentacio");
			final Plugin pluginEntidad3 = new Plugin();
			pluginEntidad3.setId(3l);
			pluginEntidad3.setTipo("Plugin de dominis remots");
			pluginEntidad3.setClassname("es.caib.example.plugins.classPluginDominisRemots");
			final Plugin pluginEntidad4 = new Plugin();
			pluginEntidad4.setId(4l);
			pluginEntidad4.setTipo("Plugin de firma");
			pluginEntidad4.setClassname("es.caib.example.plugins.classPluginFirma");

			listaDatos.add(pluginEntidad1);
			listaDatos.add(pluginEntidad2);
			listaDatos.add(pluginEntidad3);
			listaDatos.add(pluginEntidad4);

		}

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<Plugin> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Plugin datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null || filtro.isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		// Filtra
		final List<Plugin> pluginesFiltradas = new ArrayList<>();
		for (final Plugin plugin : this.listaDatos) {
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

		final Map<String, String> params = new HashMap<>();
		params.put("AMBITO", ambito);
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
		params.put(TypeParametroDialogo.ID.toString(), String.valueOf(this.datoSeleccionado.getId()));
		params.put("AMBITO", ambito);
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

}
