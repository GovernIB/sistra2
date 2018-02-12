package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroDialogo;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de entidades.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewEntidades extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final Entidad entidad1 = new Entidad();
		entidad1.setActivo(true);
		entidad1.setCodigo(1l);
		entidad1.setDescripcion("Entidad 1");
		entidad1.setRol("STR_GOIB_ADMIN");
		final Entidad entidad2 = new Entidad();
		entidad2.setActivo(true);
		entidad2.setCodigo(2l);
		entidad2.setDescripcion("Entidad 2");
		entidad2.setRol("STR_APB_ADMIN");
		final Entidad entidad3 = new Entidad();
		entidad3.setActivo(true);
		entidad3.setCodigo(3l);
		entidad3.setDescripcion("Entidad 3");
		entidad3.setRol("STR_STM_ADMIN");
		final Entidad entidad4 = new Entidad();
		entidad4.setActivo(false);
		entidad4.setCodigo(4l);
		entidad4.setDescripcion("Entidad 4");
		entidad4.setRol("STR_APB_ADMIN");
		final Entidad entidad5 = new Entidad();
		entidad5.setActivo(true);
		entidad5.setCodigo(5l);
		entidad5.setDescripcion("Entidad 5");
		entidad5.setRol("STR_GOIB_ADMIN");
		final Entidad entidad6 = new Entidad();
		entidad6.setActivo(true);
		entidad6.setCodigo(6l);
		entidad6.setDescripcion("Entidad 6");
		entidad6.setRol("STR_GOIB_ADMIN");
		final Entidad entidad7 = new Entidad();
		entidad7.setActivo(false);
		entidad7.setCodigo(7l);
		entidad7.setDescripcion("Entidad 7");
		entidad7.setRol("STR_APB_ADMIN");
		final Entidad entidad8 = new Entidad();
		entidad8.setActivo(true);
		entidad8.setCodigo(8l);
		entidad8.setDescripcion("Entidad 8");
		entidad8.setRol("STR_STM_ADMIN");
		final Entidad entidad9 = new Entidad();
		entidad9.setActivo(false);
		entidad9.setCodigo(9l);
		entidad9.setDescripcion("Entidad 9");
		entidad9.setRol("STR_APB_ADMIN");
		final Entidad entidad10 = new Entidad();
		entidad10.setActivo(true);
		entidad10.setCodigo(10l);
		entidad10.setDescripcion("Entidad 10");
		entidad10.setRol("STR_GOIB_ADMIN");
		final Entidad entidad11 = new Entidad();
		entidad11.setActivo(true);
		entidad11.setCodigo(11l);
		entidad11.setDescripcion("Entidad 11");
		entidad11.setRol("STR_GOIB_ADMIN");
		final Entidad entidad12 = new Entidad();
		entidad12.setActivo(false);
		entidad12.setCodigo(12l);
		entidad12.setDescripcion("Entidad 12");
		entidad12.setRol("STR_APB_ADMIN");
		final Entidad entidad13 = new Entidad();
		entidad13.setActivo(false);
		entidad13.setCodigo(13l);
		entidad13.setDescripcion("Entidad 13");
		entidad13.setRol("STR_GOIB_ADMIN");
		final Entidad entidad14 = new Entidad();
		entidad14.setActivo(false);
		entidad14.setCodigo(14l);
		entidad14.setDescripcion("Entidad 14");
		entidad14.setRol("STR_APB_ADMIN");
		final Entidad entidad15 = new Entidad();
		entidad15.setActivo(true);
		entidad15.setCodigo(15l);
		entidad15.setDescripcion("Entidad 15");
		entidad15.setRol("STR_APB_ADMIN");
		final Entidad entidad16 = new Entidad();
		entidad16.setActivo(true);
		entidad16.setCodigo(16l);
		entidad16.setDescripcion("Entidad 16");
		entidad16.setRol("STR_STM_ADMIN");

		listaDatos = new ArrayList<>();
		listaDatos.add(entidad1);
		listaDatos.add(entidad2);
		listaDatos.add(entidad3);
		listaDatos.add(entidad4);
		listaDatos.add(entidad5);
		listaDatos.add(entidad6);
		listaDatos.add(entidad7);
		listaDatos.add(entidad8);
		listaDatos.add(entidad9);
		listaDatos.add(entidad10);
		listaDatos.add(entidad11);
		listaDatos.add(entidad12);
		listaDatos.add(entidad13);
		listaDatos.add(entidad14);
		listaDatos.add(entidad15);
		listaDatos.add(entidad16);

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<Entidad> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Entidad datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		this.buscar();
	}

	/**
	 * Método final que se encarga de realizar la búsqueda
	 */
	private void buscar() {

		if (this.filtro == null) {
			return;
		}

		// Filtra
		final List<Entidad> entidadesFiltradas = new ArrayList<>();
		for (final Entidad entidad : this.listaDatos) {
			if (entidad.getDescripcion() != null
					&& entidad.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				entidadesFiltradas.add(entidad);
			}
		}

		this.listaDatos = entidadesFiltradas;
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.filtro.ok"));
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		UtilJSF.openDialog(DialogEntidad.class, TypeModoAcceso.ALTA, null, true, 540, 200);
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
		UtilJSF.openDialog(DialogEntidad.class, TypeModoAcceso.EDICION, params, true, 540, 200);
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
	public List<Entidad> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<Entidad> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public Entidad getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Entidad datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
