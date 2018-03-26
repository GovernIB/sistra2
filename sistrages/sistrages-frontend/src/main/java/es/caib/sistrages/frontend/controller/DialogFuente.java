package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Fuente;
import es.caib.sistrages.core.api.model.FuenteCampo;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFuente extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento en JSON. **/
	private String iData;

	/** Datos elemento. */
	private Fuente data;

	/** Valor seleccionado. **/
	private FuenteCampo valorSeleccionado;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Fuente();
			data.setCampos(new ArrayList<>());
		} else {
			data = (Fuente) UtilJSON.fromJSON(iData, Fuente.class);

		}
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
				final FuenteCampo fuenteDatosCampo = (FuenteCampo) respuesta.getResult();
				this.data.getCampos().add(fuenteDatosCampo);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:
				// Actualizamos fila actual
				final FuenteCampo fuenteDatosCampoEdicion = (FuenteCampo) respuesta.getResult();
				// Muestra dialogo
				final int posicion = this.data.getCampos().indexOf(this.valorSeleccionado);

				this.data.getCampos().remove(posicion);
				this.data.getCampos().add(posicion, fuenteDatosCampoEdicion);
				this.valorSeleccionado = fuenteDatosCampoEdicion;

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
	 */
	public void nuevaFuenteDatosCampo() {
		UtilJSF.openDialog(DialogFuenteCampo.class, TypeModoAcceso.ALTA, null, true, 410, 200);
	}

	/**
	 * Edita una FuenteDatosCampo.
	 */
	public void editarFuenteDatosCampo() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.valorSeleccionado));
		UtilJSF.openDialog(DialogFuenteCampo.class, TypeModoAcceso.EDICION, params, true, 410, 200);
	}

	/**
	 * Quita una FuenteDatosCampo.
	 */
	public void quitarFuenteDatosCampo() {
		if (!verificarFilaSeleccionada())
			return;

		this.data.getCampos().remove(this.valorSeleccionado);

	}

	/**
	 * Baja la FuenteDatosCampo de posición.
	 */
	public void bajarFuenteDatosCampo() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getCampos().indexOf(this.valorSeleccionado);
		if (posicion >= this.data.getCampos().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final FuenteCampo fuenteDatosCampo = this.data.getCampos().remove(posicion);
		this.data.getCampos().add(posicion + 1, fuenteDatosCampo);
	}

	/**
	 * Sube la FuenteDatosCampo de posición.
	 */
	public void subirFuenteDatosCampo() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getCampos().indexOf(this.valorSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final FuenteCampo fuenteDatosCampo = this.data.getCampos().remove(posicion);
		this.data.getCampos().add(posicion - 1, fuenteDatosCampo);
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
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			/*
			 * if (FuenteDatosGlobalService.load(data.getCodigo()) != null) {
			 * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
			 * "Ya existe dato con ese codigo"); return; }
			 * FuenteDatosGlobalService.add(data);
			 */

			break;
		case EDICION:
			// FuenteDatosGlobalService.update(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
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
	public FuenteCampo getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final FuenteCampo valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
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

}
