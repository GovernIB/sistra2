package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.DominioEntidad;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de dominios entidad.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDominiosEntidad extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final DominioEntidad dominioEntidad1 = new DominioEntidad();
		dominioEntidad1.setCacheable(true);
		dominioEntidad1.setId(1l);
		dominioEntidad1.setCodigo("GOIB_ORGANISME");
		dominioEntidad1.setDescripcion("Dominio que retorna el organisme de la entitat");
		dominioEntidad1.setTipo("Consulta de BBDD");
		final DominioEntidad dominioEntidad2 = new DominioEntidad();
		dominioEntidad2.setCacheable(true);
		dominioEntidad2.setId(2l);
		dominioEntidad2.setCodigo("GOIB_PSTDOCUME");
		dominioEntidad2.setDescripcion("Llistat de punts de lliurament de documentació");
		dominioEntidad2.setTipo("Consulta de BBDD");
		final DominioEntidad dominioEntidad3 = new DominioEntidad();
		dominioEntidad3.setCacheable(false);
		dominioEntidad3.setCodigo("GOIB_ENTBNCOL");
		dominioEntidad3.setId(3l);
		dominioEntidad3.setDescripcion("Llistat de entitats bancaries");
		dominioEntidad3.setTipo("Consulta remota");

		listaDatos = new ArrayList<>();
		listaDatos.add(dominioEntidad1);
		listaDatos.add(dominioEntidad2);
		listaDatos.add(dominioEntidad3);

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<DominioEntidad> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private DominioEntidad datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		// Filtra
		final List<DominioEntidad> dominioEntidadesFiltradas = new ArrayList<>();
		for (final DominioEntidad dominioEntidad : this.listaDatos) {
			if (dominioEntidad.getDescripcion() != null
					&& dominioEntidad.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				dominioEntidadesFiltradas.add(dominioEntidad);
			}
		}

		this.listaDatos = dominioEntidadesFiltradas;
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
	 * Abre explorar gestion logo.
	 */
	public void explorarGestorLogo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
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
	public List<DominioEntidad> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<DominioEntidad> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public DominioEntidad getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final DominioEntidad datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
