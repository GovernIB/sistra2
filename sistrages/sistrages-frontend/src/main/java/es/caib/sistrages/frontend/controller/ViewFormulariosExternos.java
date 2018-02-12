package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormularioExterno;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de formularios externos.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewFormulariosExternos extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final FormularioExterno formularioExterno1 = new FormularioExterno();
		formularioExterno1.setId(1l);
		formularioExterno1.setCodigo("SADPERDOC");
		formularioExterno1.setDescripcion("Gestión de formularios para trámites de Personal Docent.");
		formularioExterno1.setUrl("http://host:port/sadperdocfront/iniciFormulari");

		final FormularioExterno formularioExterno2 = new FormularioExterno();
		formularioExterno2.setId(2l);
		formularioExterno2.setCodigo("XESTIB");
		formularioExterno2.setDescripcion("Gestión de formularios para trámites d'escolarització.");
		formularioExterno2.setUrl("http://host:port/xestib/iniciFormulari");

		final FormularioExterno formularioExterno3 = new FormularioExterno();
		formularioExterno3.setId(3l);
		formularioExterno3.setCodigo("OPOSEDU");
		formularioExterno3.setDescripcion("Gestión de formularios para trámites d'inscripció de oposiciones");
		formularioExterno3.setUrl("http://host:port/oposeduFront/tramit/inscripció/iniciFormulari");

		final FormularioExterno formularioExterno4 = new FormularioExterno();
		formularioExterno4.setId(4l);
		formularioExterno4.setCodigo("SADPERDOC2");
		formularioExterno4.setDescripcion("Gestión de formularios para trámites 2");
		formularioExterno4.setUrl("http://host:port/sadperdocfrontNuevo/iniciFormulari");

		listaDatos = new ArrayList<>();
		listaDatos.add(formularioExterno1);
		listaDatos.add(formularioExterno2);
		listaDatos.add(formularioExterno3);
		listaDatos.add(formularioExterno4);

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<FormularioExterno> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private FormularioExterno datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null || filtro.isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		// Filtra
		final List<FormularioExterno> formularioExternosFiltradas = new ArrayList<>();
		for (final FormularioExterno formularioExterno : this.listaDatos) {
			if (formularioExterno.getDescripcion() != null
					&& formularioExterno.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				formularioExternosFiltradas.add(formularioExterno);
			}
		}

		this.listaDatos = formularioExternosFiltradas;
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.filtro.ok"));
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
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
	public List<FormularioExterno> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<FormularioExterno> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public FormularioExterno getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final FormularioExterno datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
