package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.ValorListaFija;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogListaValoresFijaCIN extends DialogControllerBase {

	/** Datos elemento. */
	private List<ValorListaFija> data;
	private List<String> idiomas;
	private ValorListaFija valorSeleccionado;

	/**
	 * Inicialización.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		if (!mochilaDatos.isEmpty()) {
			data = (List<ValorListaFija>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_LVFCIN);
			setIdiomas((List<String>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO));
		}

		if (data == null) {
			data = new ArrayList<>();
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_LVFCIN);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_LVFCIN);
	}

	/**
	 * Crea nuevo valor.
	 */
	public void nuevoValor() {
		UtilJSF.openDialog(DialogListaValoresFija.class, TypeModoAcceso.ALTA, null, true, 430, 170);
	}

	/**
	 * Edita una propiedad.
	 */
	public void editarValor() {

		if (!verificarFilaSeleccionadaValor()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.valorSeleccionado));
		UtilJSF.openDialog(DialogListaValoresFija.class, TypeModoAcceso.EDICION, params, true, 430, 170);
	}

	/**
	 * Quita una propiedad.
	 */
	public void quitarValor() {
		if (!verificarFilaSeleccionadaValor()) {
			return;
		}

		this.data.remove(this.valorSeleccionado);
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirValor() {
		if (!verificarFilaSeleccionadaValor()) {
			return;
		}

		final int posicion = this.data.indexOf(this.valorSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final ValorListaFija vlf = this.data.remove(posicion);
		this.data.add(posicion - 1, vlf);
	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		final int posicion = this.data.indexOf(this.valorSeleccionado);
		if (posicion >= this.data.size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final ValorListaFija vlf = this.data.remove(posicion);
		this.data.add(posicion + 1, vlf);
	}

	/**
	 * Limpia la propiedad de por defecto.
	 */
	private void limpiarPorDefecto() {

		for (final ValorListaFija valorListaFija : data) {
			valorListaFija.setPorDefecto(false);
		}

	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaValor() {
		boolean filaSeleccionada = true;
		if (this.valorSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	private boolean valida(final ValorListaFija pNuevo) {
		boolean valida = true;

		for (final ValorListaFija valorListaFija : data) {
			if (valorListaFija.getValor().equals(pNuevo.getValor())
					&& !valorListaFija.getCodigo().equals(pNuevo.getCodigo())) {
				valida = false;
				break;
			}
		}

		return valida;
	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				final ValorListaFija propiedad = (ValorListaFija) respuesta.getResult();

				if (!valida(propiedad)) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
					return;
				}

				if (propiedad.isPorDefecto()) {
					limpiarPorDefecto();
				}

				this.data.add(propiedad);
				break;
			case EDICION:
				final ValorListaFija propiedadEdicion = (ValorListaFija) respuesta.getResult();

				if (!valida(propiedadEdicion)) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
					return;
				}

				if (!valorSeleccionado.isPorDefecto() && propiedadEdicion.isPorDefecto()) {
					limpiarPorDefecto();
				}

				// Muestra dialogo
				final int posicion = this.data.indexOf(this.valorSeleccionado);
				this.data.remove(posicion);
				this.data.add(posicion, propiedadEdicion);
				this.valorSeleccionado = propiedadEdicion;
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * @return the data
	 */
	public List<ValorListaFija> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<ValorListaFija> data) {
		this.data = data;
	}

	public ValorListaFija getValorSeleccionado() {
		return valorSeleccionado;
	}

	public void setValorSeleccionado(final ValorListaFija valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<String> idiomas) {
		this.idiomas = idiomas;
	}

}
