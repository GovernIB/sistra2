package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFuente extends DialogControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** Id elemento a tratar. */
	private String id;

	/** Id area. **/
	private String idArea;

	/** Ambito. **/
	private String ambito;

	/** Datos elemento. */
	private FuenteDatos data;

	/** Valor seleccionado. **/
	private FuenteDatosCampo valorSeleccionado;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new FuenteDatos();
			data.setAmbito(TypeAmbito.fromString(ambito));
			data.setCampos(new ArrayList<>());
		} else {
			data = dominioService.loadFuenteDato(Long.valueOf(id));
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

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				final FuenteDatosCampo fuenteDatosCampo = (FuenteDatosCampo) respuesta.getResult();
				this.data.getCampos().add(fuenteDatosCampo);

				break;

			case EDICION:
				// Actualizamos fila actual
				final FuenteDatosCampo fuenteDatosCampoEdicion = (FuenteDatosCampo) respuesta.getResult();
				// Muestra dialogo
				final int posicion = this.data.getCampos().indexOf(this.valorSeleccionado);

				this.data.getCampos().remove(posicion);
				this.data.getCampos().add(posicion, fuenteDatosCampoEdicion);
				this.valorSeleccionado = fuenteDatosCampoEdicion;

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
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

		final FuenteDatosCampo fuenteDatosCampo = this.data.getCampos().remove(posicion);
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

		final FuenteDatosCampo fuenteDatosCampo = this.data.getCampos().remove(posicion);
		this.data.getCampos().add(posicion - 1, fuenteDatosCampo);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			this.dominioService.addFuenteDato(this.data, Long.valueOf(idArea));
			break;
		case EDICION:
			this.dominioService.updateFuenteDato(this.data);
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

	// ------- FUNCIONES PRIVADAS ------------------------------
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

	// ------- GETTERS / SETTERS --------------------------------
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
	public FuenteDatos getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FuenteDatos data) {
		this.data = data;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public FuenteDatosCampo getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final FuenteDatosCampo valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
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
