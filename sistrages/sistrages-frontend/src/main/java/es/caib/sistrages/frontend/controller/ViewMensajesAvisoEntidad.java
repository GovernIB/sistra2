package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.MensajeAviso;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de mensajes de aviso entidad.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewMensajesAvisoEntidad extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		final MensajeAviso mensajeAviso1 = new MensajeAviso();
		mensajeAviso1.setActivo(true);
		mensajeAviso1.setId(1l);
		mensajeAviso1.setDescripcion("La versió d'aquest tràmit està desactivat.");
		mensajeAviso1.setTipo("Llista de tràmits");
		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 2, 2);
		mensajeAviso1.setFechaInicio(calendar.getTime());
		calendar.set(2018, 3, 3);
		mensajeAviso1.setFechaFin(calendar.getTime());
		final MensajeAviso mensajeAviso2 = new MensajeAviso();
		mensajeAviso2.setActivo(true);
		mensajeAviso2.setId(2l);
		mensajeAviso2.setDescripcion("Per la tramitació que realitzarà necessitarà disposar de DNI electrònic.");
		mensajeAviso2.setTipo("Tràmits amb firma");
		final MensajeAviso mensajeAviso3 = new MensajeAviso();
		mensajeAviso3.setActivo(false);
		mensajeAviso3.setId(3l);
		mensajeAviso3.setDescripcion("Per problemes técnics aquest tràmit està donat de baixa.");
		mensajeAviso3.setTipo("Tots els tràmits");

		listaDatos = new ArrayList<>();
		listaDatos.add(mensajeAviso1);
		listaDatos.add(mensajeAviso2);
		listaDatos.add(mensajeAviso3);

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<MensajeAviso> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private MensajeAviso datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		// Filtra
		final List<MensajeAviso> mensajeAvisoesFiltradas = new ArrayList<>();
		for (final MensajeAviso mensajeAviso : this.listaDatos) {
			if (mensajeAviso.getDescripcion() != null
					&& mensajeAviso.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				mensajeAvisoesFiltradas.add(mensajeAviso);
			}
		}

		this.listaDatos = mensajeAvisoesFiltradas;
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
	 * Refrescar cache.
	 */
	public void refrescarCache() {
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
	public List<MensajeAviso> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<MensajeAviso> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public MensajeAviso getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final MensajeAviso datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
