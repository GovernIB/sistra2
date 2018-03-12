package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de Anexar Documentos.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionAnexarDocumentos extends ViewControllerBase {

	/** Id. **/
	private String id;

	/** Data. **/
	private TramitePasoAnexar data;

	/** Documento seleccioando. **/
	private Documento datoSeleccionado;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionAnexo1.
	 */
	public ViewDefinicionVersionAnexarDocumentos() {
		super();
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

				final Documento documento = (Documento) respuesta.getResult();
				data.getDocumentos().add(documento);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Documento documentoMod = (Documento) respuesta.getResult();
				final int posicion = data.getDocumentos().indexOf(this.datoSeleccionado);

				data.getDocumentos().remove(posicion);
				data.getDocumentos().add(posicion, documentoMod);
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

	@PostConstruct
	public void init() {
		// TODO tendría que obtener el tramitePasoRellenar a partir de la id.
		id = "1";
		/* inicializa documentos */
		final Documento documento1 = new Documento();
		documento1.setId(1L);
		documento1.setCodigo("Anexo1");
		final Traducciones traducciones1 = new Traducciones();
		traducciones1.add(new Traduccion("ca", "Certificat de penals"));
		traducciones1.add(new Traduccion("es", "Certificado de penales"));
		documento1.setDescripcion(traducciones1);
		documento1.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		final Documento documento2 = new Documento();
		documento2.setId(2L);
		documento2.setCodigo("Anexo2");
		final Traducciones traducciones2 = new Traducciones();
		traducciones2.add(new Traduccion("ca", "Titols acadèmics"));
		traducciones2.add(new Traduccion("es", "Titulos academicos"));
		documento2.setDescripcion(traducciones2);
		documento2.setObligatoriedad(TypeFormularioObligatoriedad.DEPENDIENTE);

		final List<Documento> listaDocumentos = new ArrayList<>();
		listaDocumentos.add(documento1);
		listaDocumentos.add(documento2);
		data = new TramitePasoAnexar();
		data.setDocumentos(listaDocumentos);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevo() {
		UtilJSF.openDialog(DialogDefinicionVersionAnexarDocumentos.class, TypeModoAcceso.ALTA, null, true, 600, 200);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void script() {
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, null, true, 950, 700);
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
		UtilJSF.openDialog(DialogDefinicionVersionAnexo.class, TypeModoAcceso.EDICION, params, true, 950, 575);
	}

	/**
	 * Eliminar.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		this.data.getDocumentos().remove(this.datoSeleccionado);
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

		final int posicion = this.data.getDocumentos().indexOf(this.datoSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Documento documento = this.data.getDocumentos().remove(posicion);
		this.data.getDocumentos().add(posicion - 1, documento);

		for (int i = 0; i < this.data.getDocumentos().size(); i++) {
			this.data.getDocumentos().get(i).setOrden(i + 1);
		}

	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajar() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getDocumentos().indexOf(this.datoSeleccionado);
		if (posicion >= this.data.getDocumentos().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Documento documento = this.data.getDocumentos().remove(posicion);
		this.data.getDocumentos().add(posicion + 1, documento);

		for (int i = 0; i < this.data.getDocumentos().size(); i++) {
			this.data.getDocumentos().get(i).setOrden(i + 1);
		}

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
	public TramitePasoAnexar getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final TramitePasoAnexar data) {
		this.data = data;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public Documento getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Documento datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}
}
