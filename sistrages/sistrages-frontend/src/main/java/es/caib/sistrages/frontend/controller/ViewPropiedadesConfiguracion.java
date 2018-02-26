package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.PropiedadConfiguracion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroDialogo;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de propiedades de configuracion.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewPropiedadesConfiguracion extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final PropiedadConfiguracion propiedadConfiguracion1 = new PropiedadConfiguracion();
		propiedadConfiguracion1.setCodigo("es.caib.sistra2.propietat1");
		propiedadConfiguracion1.setDescripcion("PropiedadConfiguracion 1 descripcion");
		propiedadConfiguracion1.setValor("http://www.caib.es");
		final PropiedadConfiguracion propiedadConfiguracion2 = new PropiedadConfiguracion();
		propiedadConfiguracion2.setCodigo("es.caib.sistra2.propietat2");
		propiedadConfiguracion2.setDescripcion("PropiedadConfiguracion 2 descripcion");
		propiedadConfiguracion2.setValor("localhost");
		final PropiedadConfiguracion propiedadConfiguracion3 = new PropiedadConfiguracion();
		propiedadConfiguracion3.setCodigo("es.caib.sistra2.propietat3");
		propiedadConfiguracion3.setDescripcion("PropiedadConfiguracion 3 descripcion");
		propiedadConfiguracion3.setValor("340");
		final PropiedadConfiguracion propiedadConfiguracion4 = new PropiedadConfiguracion();
		propiedadConfiguracion4.setCodigo("es.caib.sistra2.propietat4");
		propiedadConfiguracion4.setDescripcion("PropiedadConfiguracion 4 descripcion");
		propiedadConfiguracion4.setValor("usuario");

		listaDatos = new ArrayList<>();
		listaDatos.add(propiedadConfiguracion1);
		listaDatos.add(propiedadConfiguracion2);
		listaDatos.add(propiedadConfiguracion3);
		listaDatos.add(propiedadConfiguracion4);

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<PropiedadConfiguracion> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private PropiedadConfiguracion datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		buscar();
	}

	/**
	 * El autentico buscador.
	 */
	private void buscar() {
		if (this.filtro == null) {
			return;
		}

		// Filtra
		final List<PropiedadConfiguracion> propiedadConfiguracionesFiltradas = new ArrayList<>();
		for (final PropiedadConfiguracion propiedadConfiguracion : this.listaDatos) {
			if (propiedadConfiguracion.getDescripcion() != null
					&& propiedadConfiguracion.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				propiedadConfiguracionesFiltradas.add(propiedadConfiguracion);
			}
		}

		this.listaDatos = propiedadConfiguracionesFiltradas;
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.filtro.ok"));
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		UtilJSF.openDialog(DialogPropiedadConfiguracion.class, TypeModoAcceso.ALTA, null, true, 540, 150);
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
		UtilJSF.openDialog(DialogPropiedadConfiguracion.class, TypeModoAcceso.EDICION, params, true, 540, 150);
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
		buscar();
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
	public List<PropiedadConfiguracion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<PropiedadConfiguracion> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public PropiedadConfiguracion getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final PropiedadConfiguracion datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
