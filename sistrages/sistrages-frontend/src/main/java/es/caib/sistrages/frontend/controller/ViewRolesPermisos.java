package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

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

		final Area area1 = new Area();
		area1.setId(1l);
		area1.setDescripcion("Area 1 desc");
		area1.setCodigo("Area 1");
		final Area area2 = new Area();
		area2.setId(2l);
		area2.setDescripcion("Area 2 desc");
		area2.setCodigo("Area 2");

		final Rol rol1 = new Rol();
		rol1.setId(1l);
		rol1.setCodigo("STR_SALUT_DEV");
		rol1.setDescripcion("Rol de desenvolupament per tràmits de salut");
		rol1.setAlta(true);
		rol1.setModificacion(true);
		rol1.setArea(area1);
		final Rol rol2 = new Rol();
		rol2.setId(2l);
		rol2.setCodigo("STR_TEST_CONS");
		rol2.setDescripcion("Rol de consulta de tràmits de funcionalitats.");
		rol2.setConsulta(true);
		rol2.setArea(area2);
		final Rol rol3 = new Rol();
		rol3.setId(3l);
		rol3.setCodigo("STR_PESCA_HELPDESK");
		rol3.setDescripcion("Rol per resolució d'incidències de pesca.");
		rol3.setHelpdesk(true);
		rol3.setArea(area1);

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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
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
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.filtro.ok"));
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {

		UtilJSF.openDialog(DialogRolesPermisos.class, TypeModoAcceso.ALTA, null, true, 630, 220);

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
		UtilJSF.openDialog(DialogRolesPermisos.class, TypeModoAcceso.EDICION, params, true, 630, 220);

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
