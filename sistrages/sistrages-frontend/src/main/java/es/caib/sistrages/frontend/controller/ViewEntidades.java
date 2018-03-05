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
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
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

		final String STR_GOIB_ADMIN = "STR_GOIB_ADMIN";
		final String STR_APB_ADMIN = "STR_APB_ADMIN";
		final String STR_STM_ADMIN = "STR_STM_ADMIN";
		final Entidad entidad1 = new Entidad();
		entidad1.setActivo(true);
		entidad1.setId(1l);
		entidad1.setNombre("Entidad 1");
		entidad1.setCodigoDIR3("DIR3 - 1 ");
		entidad1.setRol(STR_GOIB_ADMIN);
		final Entidad entidad2 = new Entidad();
		entidad2.setActivo(true);
		entidad2.setId(2l);
		entidad2.setNombre("Entidad 2");
		entidad2.setCodigoDIR3("DIR3 - 2 ");
		entidad2.setRol(STR_APB_ADMIN);
		final Entidad entidad3 = new Entidad();
		entidad3.setActivo(true);
		entidad3.setId(3l);
		entidad3.setNombre("Entidad 3");
		entidad3.setCodigoDIR3("DIR3 - 3 ");
		entidad3.setRol(STR_STM_ADMIN);
		final Entidad entidad4 = new Entidad();
		entidad4.setActivo(false);
		entidad4.setId(4l);
		entidad4.setNombre("Entidad 4");
		entidad4.setCodigoDIR3("DIR3 - 4 ");
		entidad4.setRol(STR_APB_ADMIN);
		final Entidad entidad5 = new Entidad();
		entidad5.setActivo(true);
		entidad5.setId(5l);
		entidad5.setNombre("Entidad 5");
		entidad5.setCodigoDIR3("DIR3 - 5 ");
		entidad5.setRol(STR_GOIB_ADMIN);
		final Entidad entidad6 = new Entidad();
		entidad6.setActivo(true);
		entidad6.setId(6l);
		entidad6.setNombre("Entidad 6");
		entidad6.setCodigoDIR3("DIR3 - 6 ");
		entidad6.setRol(STR_GOIB_ADMIN);
		final Entidad entidad7 = new Entidad();
		entidad7.setActivo(false);
		entidad7.setId(7l);
		entidad7.setNombre("Entidad 7");
		entidad7.setCodigoDIR3("DIR3 - 7 ");
		entidad7.setRol(STR_APB_ADMIN);
		final Entidad entidad8 = new Entidad();
		entidad8.setActivo(true);
		entidad8.setId(8l);
		entidad8.setNombre("Entidad 8");
		entidad8.setCodigoDIR3("DIR3 - 8 ");
		entidad8.setRol(STR_STM_ADMIN);
		final Entidad entidad9 = new Entidad();
		entidad9.setActivo(false);
		entidad9.setId(9l);
		entidad9.setNombre("Entidad 9");
		entidad9.setCodigoDIR3("DIR3 - 9 ");
		entidad9.setRol(STR_APB_ADMIN);
		final Entidad entidad10 = new Entidad();
		entidad10.setActivo(true);
		entidad10.setId(10l);
		entidad10.setCodigoDIR3("DIR3 - 10 ");
		entidad10.setNombre("Entidad 10");
		entidad10.setRol(STR_GOIB_ADMIN);
		final Entidad entidad11 = new Entidad();
		entidad11.setActivo(true);
		entidad11.setId(11l);
		entidad11.setNombre("Entidad 11");
		entidad11.setCodigoDIR3("DIR3 - 11 ");
		entidad11.setRol(STR_GOIB_ADMIN);
		final Entidad entidad12 = new Entidad();
		entidad12.setActivo(false);
		entidad12.setId(12l);
		entidad12.setNombre("Entidad 12");
		entidad12.setCodigoDIR3("DIR3 - 12 ");
		entidad12.setRol(STR_APB_ADMIN);
		final Entidad entidad13 = new Entidad();
		entidad13.setActivo(false);
		entidad13.setId(13l);
		entidad13.setNombre("Entidad 13");
		entidad13.setCodigoDIR3("DIR3 - 13 ");
		entidad13.setRol(STR_GOIB_ADMIN);
		final Entidad entidad14 = new Entidad();
		entidad14.setActivo(false);
		entidad14.setId(14l);
		entidad14.setNombre("Entidad 14");
		entidad14.setCodigoDIR3("DIR3 - 14 ");
		entidad14.setRol(STR_APB_ADMIN);
		final Entidad entidad15 = new Entidad();
		entidad15.setActivo(true);
		entidad15.setId(15l);
		entidad15.setNombre("Entidad 15");
		entidad15.setCodigoDIR3("DIR3 - 15 ");
		entidad15.setRol(STR_APB_ADMIN);
		final Entidad entidad16 = new Entidad();
		entidad16.setActivo(true);
		entidad16.setId(16l);
		entidad16.setNombre("Entidad 16");
		entidad16.setCodigoDIR3("DIR3 - 16 ");
		entidad16.setRol(STR_STM_ADMIN);

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
			if (entidad.getNombre() != null && entidad.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
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
		UtilJSF.openDialog(DialogEntidad.class, TypeModoAcceso.ALTA, null, true, 530, 190);
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
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getId()));
		UtilJSF.openDialog(DialogEntidad.class, TypeModoAcceso.EDICION, params, true, 530, 190);
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

		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				// this.filtrar();
				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");
				break;
			case EDICION:
				// Actualizamos fila actual
				// final String id = (String) respuesta.getResult();
				// final Area dataUpdated = testService.load(id);
				// this.areaSeleccionada.setDescripcion(dataUpdated.getDescripcion());
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
