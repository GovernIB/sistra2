package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypePresentacion;
import es.caib.sistrages.core.api.model.types.TypeTamanyo;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n Anexo.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionAnexo extends ViewControllerBase {

	/** Id. **/
	private String id;

	/** Data. **/
	private Documento data;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionAnexo1.
	 */
	public ViewDefinicionVersionAnexo() {
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

				final Traducciones traduccion = (Traducciones) respuesta.getResult();
				data.setDescripcion(traduccion);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Traducciones traduccionMod = (Traducciones) respuesta.getResult();
				data.setDescripcion(traduccionMod);

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
		data = new Documento();
		data.setId(1L);
		data.setCodigo("Anexo1");
		final Traducciones tradDesc = new Traducciones();
		tradDesc.add(new Traduccion("ca", "Descripció"));
		tradDesc.add(new Traduccion("es", "Descripción"));
		data.setDescripcion(tradDesc);
		data.setObligatoriedad(TypeFormularioObligatoriedad.DEPENDIENTE);
		data.setAyudaFichero(new Fichero(1l, ""));
		data.setAyudaURL("http://www.caib.es");
		final Traducciones ayudaTexto = new Traducciones();
		ayudaTexto.add(new Traduccion("ca", "Texte ajuda"));
		ayudaTexto.add(new Traduccion("es", "Texto ayuda"));
		data.setAyudaTexto(ayudaTexto);
		data.setTipoPresentacion(TypePresentacion.ELECTRONICA);
		data.setNumeroInstancia(3);
		data.setTamanyoMaximo(200);
		data.setTipoTamanyo(TypeTamanyo.KILOBYTES);
		data.setDebeConvertirPDF(true);
		data.setDebeAnexarFirmado(true);

		tramiteVersion = new TramiteVersion();
		tramiteVersion.setIdiomasSoportados("ca;es;en");

	}

	/**
	 * Abre un di&aacute;logo para scripts.
	 */
	public void script() {
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, null, true, 950, 700);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 *

	 */
	public void editarDescripcion() {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, data.getDescripcion(), idiomas, idiomas);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 *

	 */
	public void editarAyudaTexto() {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.CONSULTA, data.getAyudaTexto(), idiomas, idiomas);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getId()));
		UtilJSF.openDialog(DialogDefinicionVersionAnexo.class, TypeModoAcceso.EDICION, params, true, 950, 575);
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
	public Documento getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Documento data) {
		this.data = data;
	}

}
