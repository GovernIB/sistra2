package es.caib.sistra2.stg.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistra2.stg.core.api.model.Rol;
import es.caib.sistra2.stg.frontend.model.DialogResult;
import es.caib.sistra2.stg.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.stg.frontend.util.UtilJSF;

/**
 * Mantenimiento de roles y permisos.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewRolesPermisos extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final Rol rol1 = new Rol();
		rol1.setId(1l);
		rol1.setCodigo("STR_SALUT_DEV");
		rol1.setDescripcion("Rol de desenvolupament per tràmits de salut");
		rol1.setPermisos("Alta - Baixa - Modificació");
		rol1.setArea("Area 1");
		final Rol rol2 = new Rol();
		rol2.setId(2l);
		rol2.setCodigo("STR_TEST_CONS");
		rol2.setDescripcion("Rol de consulta de tràmits de funcionalitats.");
		rol2.setPermisos("Consulta");
		rol2.setArea("Area 2");
		final Rol rol3 = new Rol();
		rol3.setId(3l);
		rol3.setCodigo("STR_PESCA_HELPDESK");
		rol3.setDescripcion("Rol per resolució d'incidències de pesca.");
		rol3.setPermisos("Helpdesk");
		rol3.setArea("Area 3");

		listaDatos = new ArrayList<>();
		listaDatos.add(rol1);
		listaDatos.add(rol2);
		listaDatos.add(rol3);

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<Rol> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Rol datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "Rellene el filtro");
			return;
		}

		// Filtra
		final List<Rol> rolesFiltradas = new ArrayList<>();
		for (final Rol rol : this.listaDatos) {
			if (rol.getDescripcion() != null && rol.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				rolesFiltradas.add(rol);
			}
		}

		this.listaDatos = rolesFiltradas;
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
	public List<Rol> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<Rol> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public Rol getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Rol datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
