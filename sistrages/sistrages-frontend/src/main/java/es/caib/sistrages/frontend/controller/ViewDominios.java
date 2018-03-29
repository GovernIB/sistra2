package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de dominios (ambito global, entidad y area).
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDominios extends ViewControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Id. **/
	private String id;

	/** Ambito. **/
	private String ambito;

	/**
	 * Lista de datos.
	 */
	private List<Dominio> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Dominio datoSeleccionado;

	/**
	 * Inicializacion.
	 */
	public void init() {

		if (ambito == null) {
			return;
		}

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);
		buscar(null);

	}

	/**
	 *
	 * @param filtro
	 */
	public void buscar(final String filtro) {
		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
		Long lId = null;
		if (id != null) {
			lId = Long.valueOf(id);
		}
		listaDatos = dominioService.listDominio(typeAmbito, lId, filtro);
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		this.buscar(filtro);

		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
		if (typeAmbito == TypeAmbito.AREA) {
			params.put("AREA", id);
		}
		if (typeAmbito == TypeAmbito.ENTIDAD) {
			params.put("ENTIDAD", id);
		}
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.ALTA, params, true, 770, 680);
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
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);

		if (typeAmbito == TypeAmbito.AREA) {
			params.put("AREA", id);
		}
		if (typeAmbito == TypeAmbito.ENTIDAD) {
			params.put("ENTIDAD", id);
		}
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.EDICION, params, true, 770, 680);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Eliminamos
		if (this.dominioService.removeDominio(this.datoSeleccionado.getId())) {
			// Refrescamos datos
			filtrar();

			// Mostramos mensaje
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}
	}

	/**
	 * Ping.
	 */
	public void ping() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Refrescar cache.
	 */
	public void refrescarCache() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscar(filtro);
		}

	}

	// ------- FUNCIONES PRIVADAS ------------------------------
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

	// ------- GETTERS / SETTERS --------------------------------
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
	public List<Dominio> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<Dominio> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public Dominio getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Dominio datoSeleccionado) {
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
