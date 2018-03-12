package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de pagar tasas.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionPagarTasas extends ViewControllerBase {

	/** Id. **/
	private String id;

	/** Tramite Paso Tasa. **/
	private TramitePasoTasa data;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Tasa datoSeleccionado;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionPagarTasas.
	 */
	public ViewDefinicionVersionPagarTasas() {
		super();
	}

	@PostConstruct
	public void init() {
		// TODO tendría que obtener el tramitePasoRellenar a partir de la id.
		id = "1";
		/* inicializa documentos */
		/* inicializa tasas */
		final Tasa tasa1 = new Tasa();
		tasa1.setId(1L);
		tasa1.setCodigo("Tasa1");
		tasa1.setDescripcion("Tasa de incripción");
		tasa1.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		tasa1.setTipo("Telemático");
		final Tasa tasa2 = new Tasa();
		tasa2.setId(2L);
		tasa2.setCodigo("Tasa2");
		tasa2.setDescripcion("Tasa de incripción");
		tasa2.setObligatoriedad(TypeFormularioObligatoriedad.OPCIONAL);
		tasa2.setTipo("Telemático");

		final List<Tasa> tasas = new ArrayList<>();
		tasas.add(tasa1);
		tasas.add(tasa2);
		data = new TramitePasoTasa();
		data.setTasas(tasas);
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
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

				final Tasa tasa = (Tasa) respuesta.getResult();
				data.getTasas().add(tasa);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Tasa documentoMod = (Tasa) respuesta.getResult();
				final int posicion = data.getTasas().indexOf(this.datoSeleccionado);

				data.getTasas().remove(posicion);
				data.getTasas().add(posicion, documentoMod);
				this.datoSeleccionado = documentoMod;

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
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
		//UtilJSF.openDialog(DialogDefinicionVersionAnexarDocumentos.class, TypeModoAcceso.ALTA, null, true, 600, 300);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
		/*
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getId()));
		UtilJSF.openDialog(DialogDefinicionVersionAnexarDocumentos.class, TypeModoAcceso.EDICION, params, true, 600,
				300);
				*/
	}

	/**
	 * Eliminar.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		this.data.getTasas().remove(this.datoSeleccionado);
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
	 * Sube la propiedad de posición.
	 */
	public void subir() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getTasas().indexOf(this.datoSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Tasa tasa = this.data.getTasas().remove(posicion);
		this.data.getTasas().add(posicion - 1, tasa);

		for (int i = 0; i < this.data.getTasas().size(); i++) {
			this.data.getTasas().get(i).setOrden(i + 1);
		}

	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajar() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getTasas().indexOf(this.datoSeleccionado);
		if (posicion >= this.data.getTasas().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Tasa tasa = this.data.getTasas().remove(posicion);
		this.data.getTasas().add(posicion + 1, tasa);

		for (int i = 0; i < this.data.getTasas().size(); i++) {
			this.data.getTasas().get(i).setOrden(i + 1);
		}

	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public Tasa getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Tasa datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the data
	 */
	public TramitePasoTasa getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final TramitePasoTasa data) {
		this.data = data;
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
