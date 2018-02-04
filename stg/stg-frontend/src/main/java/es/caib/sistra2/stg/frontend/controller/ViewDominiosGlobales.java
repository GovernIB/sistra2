package es.caib.sistra2.stg.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistra2.stg.core.api.model.DominioGlobal;
import es.caib.sistra2.stg.frontend.model.DialogResult;
import es.caib.sistra2.stg.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.stg.frontend.util.UtilJSF;

/**
 * Mantenimiento de dominios globales.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDominiosGlobales extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final DominioGlobal dominioGlobal1 = new DominioGlobal();
		dominioGlobal1.setCacheable(true);
		dominioGlobal1.setCodigo(1l);
		dominioGlobal1.setDescripcion("DominioGlobal 1");
		dominioGlobal1.setTipo("Consulta de BBDD");
		final DominioGlobal dominioGlobal2 = new DominioGlobal();
		dominioGlobal2.setCacheable(true);
		dominioGlobal2.setCodigo(2l);
		dominioGlobal2.setDescripcion("DominioGlobal 2");
		dominioGlobal2.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal3 = new DominioGlobal();
		dominioGlobal3.setCacheable(true);
		dominioGlobal3.setCodigo(3l);
		dominioGlobal3.setDescripcion("DominioGlobal 3");
		dominioGlobal3.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal4 = new DominioGlobal();
		dominioGlobal4.setCacheable(false);
		dominioGlobal4.setCodigo(4l);
		dominioGlobal4.setDescripcion("DominioGlobal 4");
		dominioGlobal4.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal5 = new DominioGlobal();
		dominioGlobal5.setCacheable(true);
		dominioGlobal5.setCodigo(5l);
		dominioGlobal5.setDescripcion("DominioGlobal 5");
		dominioGlobal5.setTipo("Consulta de BBDD");
		final DominioGlobal dominioGlobal6 = new DominioGlobal();
		dominioGlobal6.setCacheable(true);
		dominioGlobal6.setCodigo(6l);
		dominioGlobal6.setDescripcion("DominioGlobal 6");
		dominioGlobal6.setTipo("Consulta de BBDD");
		final DominioGlobal dominioGlobal7 = new DominioGlobal();
		dominioGlobal7.setCacheable(false);
		dominioGlobal7.setCodigo(7l);
		dominioGlobal7.setDescripcion("DominioGlobal 7");
		dominioGlobal7.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal8 = new DominioGlobal();
		dominioGlobal8.setCacheable(true);
		dominioGlobal8.setCodigo(8l);
		dominioGlobal8.setDescripcion("DominioGlobal 8");
		dominioGlobal8.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal9 = new DominioGlobal();
		dominioGlobal9.setCacheable(false);
		dominioGlobal9.setCodigo(9l);
		dominioGlobal9.setDescripcion("DominioGlobal 9");
		dominioGlobal9.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal10 = new DominioGlobal();
		dominioGlobal10.setCacheable(true);
		dominioGlobal10.setCodigo(10l);
		dominioGlobal10.setDescripcion("DominioGlobal 10");
		dominioGlobal10.setTipo("Consulta de BBDD");
		final DominioGlobal dominioGlobal11 = new DominioGlobal();
		dominioGlobal11.setCacheable(true);
		dominioGlobal11.setCodigo(11l);
		dominioGlobal11.setDescripcion("DominioGlobal 11");
		dominioGlobal11.setTipo("Consulta de BBDD");
		final DominioGlobal dominioGlobal12 = new DominioGlobal();
		dominioGlobal12.setCacheable(false);
		dominioGlobal12.setCodigo(12l);
		dominioGlobal12.setDescripcion("DominioGlobal 12");
		dominioGlobal12.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal13 = new DominioGlobal();
		dominioGlobal13.setCacheable(false);
		dominioGlobal13.setCodigo(13l);
		dominioGlobal13.setDescripcion("DominioGlobal 13");
		dominioGlobal13.setTipo("Consulta de BBDD");
		final DominioGlobal dominioGlobal14 = new DominioGlobal();
		dominioGlobal14.setCacheable(false);
		dominioGlobal14.setCodigo(14l);
		dominioGlobal14.setDescripcion("DominioGlobal 14");
		dominioGlobal14.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal15 = new DominioGlobal();
		dominioGlobal15.setCacheable(true);
		dominioGlobal15.setCodigo(15l);
		dominioGlobal15.setDescripcion("DominioGlobal 15");
		dominioGlobal15.setTipo("Llista de valors");
		final DominioGlobal dominioGlobal16 = new DominioGlobal();
		dominioGlobal16.setCacheable(true);
		dominioGlobal16.setCodigo(16l);
		dominioGlobal16.setDescripcion("DominioGlobal 16");
		dominioGlobal16.setTipo("Llista de valors");

		listaDatos = new ArrayList<>();
		listaDatos.add(dominioGlobal1);
		listaDatos.add(dominioGlobal2);
		listaDatos.add(dominioGlobal3);
		listaDatos.add(dominioGlobal4);
		listaDatos.add(dominioGlobal5);
		listaDatos.add(dominioGlobal6);
		listaDatos.add(dominioGlobal7);
		listaDatos.add(dominioGlobal8);
		listaDatos.add(dominioGlobal9);
		listaDatos.add(dominioGlobal10);
		listaDatos.add(dominioGlobal11);
		listaDatos.add(dominioGlobal12);
		listaDatos.add(dominioGlobal13);
		listaDatos.add(dominioGlobal14);
		listaDatos.add(dominioGlobal15);
		listaDatos.add(dominioGlobal16);

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<DominioGlobal> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private DominioGlobal datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "Rellene el filtro");
			return;
		}

		// Filtra
		final List<DominioGlobal> dominioGlobalesFiltradas = new ArrayList<>();
		for (final DominioGlobal dominioGlobal : this.listaDatos) {
			if (dominioGlobal.getDescripcion() != null
					&& dominioGlobal.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				dominioGlobalesFiltradas.add(dominioGlobal);
			}
		}

		this.listaDatos = dominioGlobalesFiltradas;
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Aplicado filtro");
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
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Se ha eliminado fila");
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "No se ha seleccionado fila");
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
	public List<DominioGlobal> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<DominioGlobal> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public DominioGlobal getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final DominioGlobal datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
