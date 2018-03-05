package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.caib.sistrages.core.api.model.Fuente;
import es.caib.sistrages.core.api.model.FuenteCampo;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilJSON;

/**
 * Mantenimiento de fuente de datos entidad.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewFuentes extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {

		if (ambito == null) {
			return;
		}
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);

		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
		if (typeAmbito == TypeAmbito.AREA) {

			final Fuente fuenteDatos1 = new Fuente();
			fuenteDatos1.setId(1l);
			fuenteDatos1.setCodigo("PESCA_REV_MAR");
			fuenteDatos1.setDescripcion("AREA - Reserva marines de les Illes Balears");

			final Fuente fuenteDatos2 = new Fuente();
			fuenteDatos2.setId(2l);
			fuenteDatos2.setCodigo("PESCA_ESP_AUT");
			fuenteDatos2.setDescripcion("Llista d'espècies de plantes autóctones de les Illes Balears");
			final Fuente fuenteDatos3 = new Fuente();
			fuenteDatos3.setCodigo("EDUC_EQUAL_PROF");
			fuenteDatos3.setId(3l);
			fuenteDatos3.setDescripcion("Llistat de qualificacions profesionals.");

			listaDatos = new ArrayList<>();
			listaDatos.add(fuenteDatos1);
			listaDatos.add(fuenteDatos2);
			listaDatos.add(fuenteDatos3);

			final List<FuenteCampo> campos = new ArrayList<>();
			final FuenteCampo campo1 = new FuenteCampo();
			campo1.setId(1l);
			campo1.setCodigo("CODI");
			campo1.setClavePrimaria(true);
			campos.add(campo1);
			final FuenteCampo campo2 = new FuenteCampo();
			campo2.setId(2l);
			campo2.setCodigo("DESC_ES");
			campo2.setClavePrimaria(false);
			campos.add(campo2);
			final FuenteCampo campo3 = new FuenteCampo();
			campo3.setId(3l);
			campo3.setCodigo("DESC_CA");
			campo3.setClavePrimaria(false);
			campos.add(campo3);
			fuenteDatos1.setCampos(campos);
			fuenteDatos2.setCampos(campos);
			fuenteDatos3.setCampos(campos);

		} else if (typeAmbito == TypeAmbito.ENTIDAD) {

			final Fuente fuenteDatos1 = new Fuente();
			fuenteDatos1.setId(1l);
			fuenteDatos1.setCodigo("PESCA_REV_MAR");
			fuenteDatos1.setDescripcion("ENT - Reserva marines de les Illes Balears");
			final Fuente fuenteDatos2 = new Fuente();
			fuenteDatos2.setId(2l);
			fuenteDatos2.setCodigo("PESCA_ESP_AUT");
			fuenteDatos2.setDescripcion("Llista d'espècies de plantes autóctones de les Illes Balears");
			final Fuente fuenteDatos3 = new Fuente();
			fuenteDatos3.setCodigo("EDUC_EQUAL_PROF");
			fuenteDatos3.setId(3l);
			fuenteDatos3.setDescripcion("Llistat de qualificacions profesionals.");

			listaDatos = new ArrayList<>();
			listaDatos.add(fuenteDatos1);
			listaDatos.add(fuenteDatos2);
			listaDatos.add(fuenteDatos3);

			final List<FuenteCampo> campos = new ArrayList<>();
			final FuenteCampo campo1 = new FuenteCampo();
			campo1.setId(1l);
			campo1.setCodigo("CODIGO");
			campo1.setClavePrimaria(true);
			campos.add(campo1);
			final FuenteCampo campo2 = new FuenteCampo();
			campo2.setId(2l);
			campo2.setCodigo("DESCRIPCION");
			campo2.setClavePrimaria(false);
			campos.add(campo2);
			final FuenteCampo campo3 = new FuenteCampo();
			campo3.setId(3l);
			campo3.setCodigo("DATO");
			campo3.setClavePrimaria(false);
			campos.add(campo3);
			fuenteDatos1.setCampos(campos);
			fuenteDatos2.setCampos(campos);
			fuenteDatos3.setCampos(campos);

		} else {
			listaDatos = new ArrayList<>();
		}

	}

	/** Id. **/
	private String id;

	/** Ambito. **/
	private String ambito;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Lista de datos. */
	private List<Fuente> listaDatos;

	/** Dato seleccionado en la lista. */
	private Fuente datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		// Filtra
		final List<Fuente> fuenteDatosesFiltradas = new ArrayList<>();
		for (final Fuente fuenteDatos : this.listaDatos) {
			if (fuenteDatos.getDescripcion() != null
					&& fuenteDatos.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				fuenteDatosesFiltradas.add(fuenteDatos);
			}
		}

		this.listaDatos = fuenteDatosesFiltradas;
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.filtro.ok"));
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		UtilJSF.openDialog(DialogFuente.class, TypeModoAcceso.ALTA, null, true, 740, 450);
	}

	/**
	 * Abre dialogo para editar dato.
	 *
	 * @throws JsonProcessingException
	 */
	public void editar() throws JsonProcessingException {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.datoSeleccionado));
		UtilJSF.openDialog(DialogFuente.class, TypeModoAcceso.EDICION, params, true, 740, 450);

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
	 * Datos.
	 *
	 * @throws JsonProcessingException
	 */
	public void datos() throws JsonProcessingException {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.datoSeleccionado));
		UtilJSF.openDialog(DialogFuenteDatos.class, TypeModoAcceso.EDICION, params, true, 740, 450);
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
	public List<Fuente> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<Fuente> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public Fuente getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Fuente datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
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

}
