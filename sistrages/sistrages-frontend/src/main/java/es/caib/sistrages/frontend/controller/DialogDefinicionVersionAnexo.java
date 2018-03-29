package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypePresentacion;
import es.caib.sistrages.core.api.model.types.TypeTamanyo;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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
public class DialogDefinicionVersionAnexo extends DialogControllerBase {

	/** Id. **/
	private String id;

	/** Data. **/
	private Documento data;

	/** Documento seleccioando. **/
	private Documento datoSeleccionado;

	/** Tramite version. **/
	private TramiteVersion tramiteVersion;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionAnexo1.
	 */
	public DialogDefinicionVersionAnexo() {
		super();
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoDescripcion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Literal traduccion = (Literal) respuesta.getResult();
				data.setDescripcion(traduccion);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionMod = (Literal) respuesta.getResult();
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

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoAyudaTexto(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Literal traduccion = (Literal) respuesta.getResult();
				data.setAyudaTexto(traduccion);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionMod = (Literal) respuesta.getResult();
				data.setAyudaTexto(traduccionMod);

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
		final Literal tradDesc = new Literal();
		tradDesc.add(new Traduccion("ca", "Descripció"));
		tradDesc.add(new Traduccion("es", "Descripción"));
		data.setDescripcion(tradDesc);
		data.setObligatoriedad(TypeFormularioObligatoriedad.DEPENDIENTE);
		// data.setAyudaFichero(new Fichero(1l, ""));
		data.setAyudaURL("http://www.caib.es");
		final Literal ayudaTexto = new Literal();
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
	 */
	public void editarDescripcion() {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					idiomas, idiomas);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), idiomas, idiomas);
		}
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void editarAyudaTexto() {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		if (data.getAyudaTexto() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, UtilTraducciones.getTraduccionesPorDefecto(),
					idiomas, idiomas);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getAyudaTexto(), idiomas, idiomas);
		}
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
			 * if (entidadService.load(data.getCodigo()) != null) {
			 * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
			 * "Ya existe dato con ese codigo"); return; } entidadService.add(data);
			 */

			break;
		case EDICION:
			// entidadService.update(data);
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
