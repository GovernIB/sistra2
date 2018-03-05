package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilJSON;

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

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);

		listaDatos = new ArrayList<>();
		switch (TypeAmbito.fromString(ambito)) {
		case GLOBAL:
			final Plugin plugin1 = new Plugin();
			plugin1.setId(1l);
			plugin1.setTipo(TypePlugin.LOGIN);
			plugin1.setDescripcion("Descripcion");
			plugin1.setClassname("GLOBAL es.caib.example.plugins.classPluginLogin");
			final Plugin plugin2 = new Plugin();
			plugin2.setId(2l);
			plugin2.setTipo(TypePlugin.REPRESENTACION);
			plugin2.setClassname("es.caib.example.plugins.classPluginRepresentacio");
			final Plugin plugin3 = new Plugin();
			plugin3.setId(3l);
			plugin3.setTipo(TypePlugin.DOMINIO_REMOTO);
			plugin3.setClassname("es.caib.example.plugins.classPluginDominisRemots");
			final Plugin plugin4 = new Plugin();
			plugin4.setId(4l);
			plugin4.setTipo(TypePlugin.FIRMA);
			plugin4.setClassname("es.caib.example.plugins.classPluginFirma");

			final List<Propiedad> propiedades = new ArrayList<>();
			final Propiedad p1 = new Propiedad();
			p1.setCodigo("COD 1");
			p1.setValor("VAL 1");
			propiedades.add(p1);
			final Propiedad p2 = new Propiedad();
			p2.setCodigo("COD 2");
			p2.setValor("VAL 2");
			propiedades.add(p2);
			plugin1.setPropiedades(propiedades);
			plugin2.setPropiedades(propiedades);
			plugin3.setPropiedades(propiedades);
			plugin4.setPropiedades(propiedades);

			listaDatos.add(plugin1);
			listaDatos.add(plugin2);
			listaDatos.add(plugin3);
			listaDatos.add(plugin4);
			break;
		case ENTIDAD:
			final Plugin pluginEntidad1 = new Plugin();
			pluginEntidad1.setId(1l);
			pluginEntidad1.setTipo(TypePlugin.LOGIN);
			pluginEntidad1.setClassname("ENTIDAD es.caib.example.plugins.classPluginLogin");
			final Plugin pluginEntidad2 = new Plugin();
			pluginEntidad2.setId(2l);
			pluginEntidad2.setTipo(TypePlugin.REPRESENTACION);
			pluginEntidad2.setClassname("es.caib.example.plugins.classPluginRepresentacio");
			final Plugin pluginEntidad3 = new Plugin();
			pluginEntidad3.setId(3l);
			pluginEntidad3.setTipo(TypePlugin.DOMINIO_REMOTO);
			pluginEntidad3.setClassname("es.caib.example.plugins.classPluginDominisRemots");
			final Plugin pluginEntidad4 = new Plugin();
			pluginEntidad4.setId(4l);
			pluginEntidad4.setTipo(TypePlugin.FIRMA);
			pluginEntidad4.setClassname("es.caib.example.plugins.classPluginFirma");

			final List<Propiedad> propiedadesEntidad = new ArrayList<>();
			final Propiedad pEntidad1 = new Propiedad();
			pEntidad1.setCodigo("COD 1");
			pEntidad1.setValor("VAL 1");
			propiedadesEntidad.add(pEntidad1);
			final Propiedad pEntidad2 = new Propiedad();
			pEntidad2.setCodigo("COD 2");
			pEntidad2.setValor("VAL 2");
			propiedadesEntidad.add(pEntidad2);
			pluginEntidad1.setPropiedades(propiedadesEntidad);
			pluginEntidad2.setPropiedades(propiedadesEntidad);
			pluginEntidad3.setPropiedades(propiedadesEntidad);
			pluginEntidad4.setPropiedades(propiedadesEntidad);

			listaDatos.add(pluginEntidad1);
			listaDatos.add(pluginEntidad2);
			listaDatos.add(pluginEntidad3);
			listaDatos.add(pluginEntidad4);
			break;
		case AREA:
			break;
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
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		UtilJSF.openDialog(DialogPlugin.class, TypeModoAcceso.ALTA, params, true, 640, 400);
	}

	/**
	 * Abre dialogo para editar dato.
	 *
	 * @throws JsonProcessingException
	 */
	public void editar() throws JsonProcessingException {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.datoSeleccionado));
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
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

		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				final Plugin formulario = (Plugin) respuesta.getResult();
				this.listaDatos.add(formulario);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				// Actualizamos fila actual
				final Plugin propiedadEdicion = (Plugin) respuesta.getResult();
				// Muestra dialogo
				final int posicion = this.listaDatos.indexOf(this.datoSeleccionado);

				this.listaDatos.remove(posicion);
				this.listaDatos.add(posicion, propiedadEdicion);
				this.datoSeleccionado = propiedadEdicion;

				// Mensaje
				message = UtilJSF.getLiteral("info.modificado.ok");
				break;

			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

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
