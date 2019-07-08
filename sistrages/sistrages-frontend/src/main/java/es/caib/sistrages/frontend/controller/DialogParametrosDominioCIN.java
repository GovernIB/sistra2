package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.ParametroDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogParametrosDominioCIN extends DialogControllerBase {

	/** Datos elemento. */
	private List<ParametroDominio> data;

	private List<Propiedad> listaParametros;

	private ParametroDominio valorSeleccionado;

	/**
	 * Inicialización.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		if (!mochilaDatos.isEmpty()) {
			data = (List<ParametroDominio>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_PRDCIN);

			listaParametros = (List<Propiedad>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_PARAMDOM);
		}

		if (data == null) {
			data = new ArrayList<>();
		}

		if (listaParametros == null) {
			listaParametros = new ArrayList<>();
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		final String param = parametroInexistente();
		if (param != null) {
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("error.parametro.inexistente", new Object[] { param }));
			return;
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_PRDCIN);
		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_PARAMDOM);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_PRDCIN);
		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_PARAMDOM);
	}

	/**
	 * Crea nuevo valor.
	 */
	public void nuevoValor() {
		UtilJSF.openDialog(DialogParametrosDominio.class, TypeModoAcceso.ALTA, null, true, 350, 170);
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
		UtilJSF.openDialog(DialogParametrosDominio.class, TypeModoAcceso.EDICION, params, true, 350, 170);
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
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaValor() {
		boolean filaSeleccionada = true;
		if (this.valorSeleccionado == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	private boolean parametroDuplicado(final ParametroDominio pNuevo) {
		boolean duplicado = false;

		for (final ParametroDominio paramDom : data) {
			if (paramDom.getParametro().equals(pNuevo.getParametro())) {
				duplicado = true;
				break;
			}
		}

		return duplicado;
	}

	private String parametroInexistente() {
		String inexistente = null;
		final List<String> listaParam = new ArrayList<>();

		for (final Propiedad parametro : listaParametros) {
			listaParam.add(parametro.getCodigo());
		}

		for (final ParametroDominio paramDom : data) {
			if (!listaParam.contains(paramDom.getParametro())) {
				inexistente = paramDom.getParametro();
				break;
			}
		}

		return inexistente;
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
				final ParametroDominio propiedad = (ParametroDominio) respuesta.getResult();

				if (parametroDuplicado(propiedad)) {
					addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
					return;
				}

				this.data.add(propiedad);
				break;
			case EDICION:
				final ParametroDominio propiedadEdicion = (ParametroDominio) respuesta.getResult();

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
	public List<ParametroDominio> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<ParametroDominio> data) {
		this.data = data;
	}

	public ParametroDominio getValorSeleccionado() {
		return valorSeleccionado;
	}

	public void setValorSeleccionado(final ParametroDominio valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	public List<Propiedad> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(final List<Propiedad> listaParametros) {
		this.listaParametros = listaParametros;
	}

}
