package es.caib.sistrages.frontend.controller;

import java.io.IOException;
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
import es.caib.sistrages.core.api.model.FuenteDato;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilJSON;

@ManagedBean
@ViewScoped
public class DialogFuenteDatos extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento en JSON. **/
	private String iData;

	/** Datos elemento. */
	private Fuente data;

	/**
	 * Los datos.
	 *
	 * @see Para hacer las columnas mas dinamicas:
	 *      https://www.primefaces.org/showcase/ui/data/datatable/columns.xhtml
	 **/
	private List<FuenteFila> datos;

	/** Fila seleccionada. **/
	private FuenteFila valorSeleccionado;

	/**
	 * Inicialización.
	 *
	 * @throws IOException
	 */
	public void init() throws IOException {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Fuente();
			data.setCampos(new ArrayList<>());
		} else {
			data = (Fuente) UtilJSON.fromJSON(iData, Fuente.class);
		}

		datos = new ArrayList<>();
		final FuenteCampo campo1 = new FuenteCampo();
		campo1.setId(1l);
		campo1.setCodigo("CODI");
		campo1.setClavePrimaria(true);
		final FuenteCampo campo2 = new FuenteCampo();
		campo2.setId(2l);
		campo2.setCodigo("DESC_ES");
		campo2.setClavePrimaria(false);
		final FuenteCampo campo3 = new FuenteCampo();
		campo3.setId(3l);
		campo3.setCodigo("DESC_CA");
		campo3.setClavePrimaria(false);

		final FuenteFila fila1 = new FuenteFila();
		fila1.setId(1l);
		fila1.addFuenteDato(new FuenteDato(11l, campo1, "1"));
		fila1.addFuenteDato(new FuenteDato(12l, campo2, "Norte de Menorca"));
		fila1.addFuenteDato(new FuenteDato(13l, campo3, "Norte de Menorca"));
		final FuenteFila fila2 = new FuenteFila();
		fila2.setId(2l);
		fila2.addFuenteDato(new FuenteDato(21l, campo1, "2"));
		fila2.addFuenteDato(new FuenteDato(22l, campo2, "Bahía de Palma"));
		fila2.addFuenteDato(new FuenteDato(23l, campo3, "Badia de Palma"));
		final FuenteFila fila3 = new FuenteFila();
		fila3.setId(3l);
		fila3.addFuenteDato(new FuenteDato(31l, campo1, "3"));
		fila3.addFuenteDato(new FuenteDato(32l, campo2, "Isla del Toro"));
		fila3.addFuenteDato(new FuenteDato(33l, campo3, "Illa del Toro"));
		final FuenteFila fila4 = new FuenteFila();
		fila4.setId(4l);
		fila4.addFuenteDato(new FuenteDato(31l, campo1, "4"));
		fila4.addFuenteDato(new FuenteDato(32l, campo2, "Levante de Mallorca"));
		fila4.addFuenteDato(new FuenteDato(33l, campo3, "Llevant de Mallorca"));

		datos.add(fila1);
		datos.add(fila2);
		datos.add(fila3);
		datos.add(fila4);
	}

	/**
	 * Retorno dialogo de los botones de FuenteDatosCampoes.
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
				final FuenteFila filaFilaAlta = (FuenteFila) respuesta.getResult();
				this.datos.add(filaFilaAlta);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:
				// Actualizamos fila actual
				final FuenteFila fuenteFilaEdicion = (FuenteFila) respuesta.getResult();
				// Muestra dialogo
				final int posicion = this.datos.indexOf(this.valorSeleccionado);

				this.datos.remove(posicion);
				this.datos.add(posicion, fuenteFilaEdicion);
				this.valorSeleccionado = fuenteFilaEdicion;

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
	 * Crea nueva FuenteDatosCampo.
	 *
	 * @throws JsonProcessingException
	 */
	public void nuevaFuenteDato() throws JsonProcessingException {
		final FuenteFila fila = this.datos.get(0);
		fila.setId(Long.valueOf(this.datos.size() + 1));
		for (final FuenteDato dato : fila.getDatos()) {
			dato.setValor("");
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO2.toString(), UtilJSON.toJSON(this.data.getCampos()));
		UtilJSF.openDialog(DialogFuenteFila.class, TypeModoAcceso.ALTA, params, true, 460,
				calcularY(this.data.getCampos().size()));
	}

	/**
	 * Edita una FuenteDatosCampo.
	 *
	 * @throws JsonProcessingException
	 */
	public void editarFuenteDato() throws JsonProcessingException {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.valorSeleccionado));
		UtilJSF.openDialog(DialogFuenteFila.class, TypeModoAcceso.EDICION, params, true, 460,
				calcularY(this.data.getCampos().size()));
	}

	/**
	 * Calcula el tamaño según el numero de elementos que tiene de campos.
	 * 
	 * @param tamanyo
	 * @return
	 */
	private int calcularY(final int tamanyo) {
		return 70 + (tamanyo * 85);
	}

	/**
	 * Quita una FuenteDatosCampo.
	 */
	public void quitarFuenteDato() {
		if (!verificarFilaSeleccionada())
			return;

		this.datos.remove(this.valorSeleccionado);

	}

	/**
	 * Baja la FuenteDatosCampo de posición.
	 */
	public void bajarFuenteDato() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.datos.indexOf(this.valorSeleccionado);
		if (posicion >= this.datos.size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final FuenteFila fuenteFila = this.datos.remove(posicion);
		this.datos.add(posicion + 1, fuenteFila);
	}

	/**
	 * Sube la FuenteDatosCampo de posición.
	 */
	public void subirFuenteDato() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.datos.indexOf(this.valorSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final FuenteFila fuenteFila = this.datos.remove(posicion);
		this.datos.add(posicion - 1, fuenteFila);
	}

	public void importarCSV() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	public void exportarCSV() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.valorSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		// result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		// result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the datos
	 */
	public List<FuenteFila> getDatos() {
		return datos;
	}

	/**
	 * @param datos
	 *            the datos to set
	 */
	public void setDatos(final List<FuenteFila> datos) {
		this.datos = datos;
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
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

	/**
	 * @return the data
	 */
	public Fuente getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Fuente data) {
		this.data = data;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public FuenteFila getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final FuenteFila valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

}
