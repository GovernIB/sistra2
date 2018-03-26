package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.types.TypeFormularioSoporte;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Mantenimiento de configuracion entidad.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewConfiguracionEntidad extends ViewControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private Entidad data;

	/** Fichero upload. **/
	private Part file;

	/** Fichero logo gestor **/
	private UploadedFile fileLogoGestor;

	/**
	 * Inicializacion.
	 */
	public void init() {

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		data = new Entidad();
		data.setActivo(true);
		data.setEmail("prueba@caib.es");
		data.setLogoAsistente(new Fichero(1l, "logoAsistente.png"));
		// data.setLogoGestor(new Fichero(2l, "logoGestor.png"));
		data.setCss(new Fichero(3l, "estilo.css"));
		final Literal traducciones = new Literal();
		traducciones.add(new Traduccion("ca", "Traduccion pie catala"));
		traducciones.add(new Traduccion("es", "Traduccion pie espanol"));
		data.setPie(traducciones);
		data.setTelefono("telefono");
		data.setTelefonoHabilitado(true);
		data.setEmailHabilitado(true);
		data.setFormularioIncidenciasHabilitado(true);
		data.setUrlSoporte("http://soporte.caib.es");
		data.setUrlSoporteHabilitado(true);

		final FormularioSoporte form1 = new FormularioSoporte();
		form1.setId(1l);
		final Literal tradDescripcion = new Literal();
		tradDescripcion.add(new Traduccion("ca", "Descripcion ca form1"));
		tradDescripcion.add(new Traduccion("es", "Descripcion es form1"));
		form1.setDescripcion(tradDescripcion);

		final Literal tradTipoIncidencia = new Literal();
		tradTipoIncidencia.add(new Traduccion("ca", "TipoIncidencia ca form1"));
		tradTipoIncidencia.add(new Traduccion("es", "TipoIncidencia es form1"));
		form1.setTipoIncidencia(tradTipoIncidencia);
		form1.setDestinatario(TypeFormularioSoporte.RESPONSABLE_DE_INCIDENCIAS);

		final FormularioSoporte form2 = new FormularioSoporte();
		form2.setId(2l);
		final Literal tradDescripcion2 = new Literal();
		tradDescripcion2.add(new Traduccion("ca", "Descripcion ca form2"));
		tradDescripcion2.add(new Traduccion("es", "Descripcion es form2"));
		form2.setDescripcion(tradDescripcion2);

		final Literal tradTipoIncidencia2 = new Literal();
		tradTipoIncidencia2.add(new Traduccion("ca", "TipoIncidencia ca form2"));
		tradTipoIncidencia2.add(new Traduccion("es", "TipoIncidencia es form2"));
		form2.setTipoIncidencia(tradTipoIncidencia2);
		form2.setDestinatario(TypeFormularioSoporte.LISTA_DE_EMAILS);
		form2.setEmails("email1@caib.es, email2@caib.es, email3@caib.es");
		final List<FormularioSoporte> formularios = new ArrayList<>();
		formularios.add(form1);
		formularios.add(form2);
		data.setFormularioIncidencias(formularios);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (data.isTelefonoHabilitado() && (data.getTelefono() == null || data.getTelefono().isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Telefono activado y sin rellenar");
			return;
		}

		if (data.isEmailHabilitado() && (data.getEmail() == null || data.getEmail().isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Email activado y sin rellenar");
			return;
		}

		if (data.isFormularioIncidenciasHabilitado()
				&& (data.getFormularioIncidencias() == null || data.getFormularioIncidencias().isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Formulario activado y sin rellenar");
			return;
		}

		if (data.isUrlSoporteHabilitado() && (data.getUrlSoporte() == null || data.getUrlSoporte().isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Url soporte activado y sin rellenar");
			return;
		}

		// Guardar cambios de la entidad.

	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		// Inicializar datos con los de la base de datos, o llamar a la misma url.
	}

	/**
	 * Para subir ficheros con el uploadEvent en modo Avanzado.
	 *
	 * @param event
	 */
	public void uploadModeAdvanced(final FileUploadEvent event) {
		try {
			if (event.getFile() != null) {
				final String filename = event.getFile().getFileName();
				final InputStream is = event.getFile().getInputstream();
				final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				final byte[] contenido = new byte[16384];

				while ((nRead = is.read(contenido, 0, contenido.length)) != -1) {
					buffer.write(contenido, 0, nRead);
				}

				buffer.flush();

				final Fichero fichero = new Fichero(1l, filename);
				fichero.setContenido(contenido);
				data.setLogoGestor(fichero);
			}
		} catch (final IOException ex) {
			throw new FrontException("Error upload", ex);
		}
	}

	/**
	 * Upload en modo simple.
	 */
	public void uploadModeSimple2() {
		try {
			uploadModeSimple();
		} catch (final FrontException e) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No se puede subir");
		}
	}

	/**
	 * Upload en modo simple.
	 *
	 */
	public void uploadModeSimple3(final FileUploadEvent event) {
		try {
			uploadModeSimple();
		} catch (final FrontException e) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "No se puede subir");
		}
	}

	/**
	 * Upload en modo simple.
	 *
	 */
	public void uploadModeSimple() {
		try {
			if (fileLogoGestor != null) {
				final String filename = fileLogoGestor.getFileName();
				final InputStream is = fileLogoGestor.getInputstream();
				final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				final byte[] contenido = new byte[16384];

				while ((nRead = is.read(contenido, 0, contenido.length)) != -1) {
					buffer.write(contenido, 0, nRead);
				}

				buffer.flush();

				final Fichero fichero = new Fichero(1l, filename);
				fichero.setContenido(contenido);
				data.setLogoGestor(fichero);
			}
		} catch (final IOException ex) {
			throw new FrontException("Error descarga", ex);
		}
	}

	/**
	 * Abre explorar gestion logo.
	 */
	public void explorarGestorLogo() {

		// Muestra dialogo
		/*
		 * final Map<String, String> params = new HashMap<>();
		 * params.put(TypeParametroVentana.ID.toString(),
		 * String.valueOf(this.data.getId())); UtilJSF.openDialog(DialogFichero.class,
		 * TypeModoAcceso.EDICION, params, true, 740, 650);
		 */
		try {
			if (data.getLogoGestor() == null) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "No tienes logo subido");
			} else {
				// Get the FacesContext
				final FacesContext facesContext = FacesContext.getCurrentInstance();

				// Get HTTP response
				final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext()
						.getResponse();

				// Set response headers
				response.reset(); // Reset the response in the first place
				response.setHeader("Content-Type", "application/pdf"); // Set only the content type

				// Open response output stream
				final OutputStream responseOutputStream = response.getOutputStream();

				// Read PDF contents
				final ByteArrayInputStream pdfInputStream = new ByteArrayInputStream(
						data.getLogoGestor().getContenido());

				// Read PDF contents and write them to the output
				final byte[] bytesBuffer = new byte[2048];
				int bytesRead;
				while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
					responseOutputStream.write(bytesBuffer, 0, bytesRead);
				}

				// Make sure that everything is out
				responseOutputStream.flush();

				// Close both streams
				pdfInputStream.close();
				responseOutputStream.close();

				// JSF doc:
				// Signal the JavaServer Faces implementation that the HTTP response for this
				// request has already been generated
				// (such as an HTTP redirect), and that the request processing lifecycle should
				// be terminated
				// as soon as the current phase is completed.
				facesContext.responseComplete();
			}
		} catch (final IOException ex) {
			throw new FrontException("Error descarga", ex);
		}
	}

	private DefaultStreamedContent downloadGestorLogo;

	public DefaultStreamedContent getFicheroGestorLogo() {
		downloadGestorLogo = new DefaultStreamedContent(new ByteArrayInputStream(data.getLogoGestor().getContenido()));
		return downloadGestorLogo;
	}

	public void prepDownload() {
		downloadGestorLogo = new DefaultStreamedContent(new ByteArrayInputStream(data.getLogoGestor().getContenido()));
	}

	public void uploadGestorLogo() {
		try {
			final String filename = file.getName();

			final InputStream is = file.getInputStream();
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			final byte[] contenido = new byte[16384];

			while ((nRead = is.read(contenido, 0, contenido.length)) != -1) {
				buffer.write(contenido, 0, nRead);
			}

			buffer.flush();

			final Fichero fichero = new Fichero(1l, filename);
			fichero.setContenido(contenido);
			data.setLogoGestor(fichero);
		} catch (final IOException ex) {
			throw new FrontException("Error upload", ex);
		}
	}

	/**
	 * Abre explorar asistente logo.
	 */
	public void explorarAssistentLogo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");

		// Muestra dialogo
		/*
		 * final Map<String, String> params = new HashMap<>();
		 * params.put(TypeParametroVentana.ID.toString(),
		 * String.valueOf(this.data.getId())); UtilJSF.openDialog(DialogFichero.class,
		 * TypeModoAcceso.EDICION, params, true, 740, 650);
		 */

	}

	/**
	 * Abre explorar asistente css.
	 */
	public void explorarAssistentCss() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
		// Muestra dialogo
		/*
		 * final Map<String, String> params = new HashMap<>();
		 * params.put(TypeParametroVentana.ID.toString(),
		 * String.valueOf(this.data.getId())); UtilJSF.openDialog(DialogFichero.class,
		 * TypeModoAcceso.EDICION, params, true, 740, 650);
		 */
	}

	/**
	 * Abre explorar asistente pie.
	 *
	 *
	 */
	public void explorarAssistentPie() {

		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		if (data.getPie() == null) {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.ALTA, data.getPie(), idiomas, idiomas);
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getPie(), idiomas, idiomas);
		}
	}

	/**
	 * Abre explorar configuración del formulario de contacto.
	 */
	public void configFormulariContacte() {

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.data.getFormularioIncidencias()));
		UtilJSF.openDialog(ViewFormularioSoporte.class, TypeModoAcceso.EDICION, params, true, 740, 350);

	}

	/**
	 * Gestion de retorno de gestor logo.
	 *
	 * @param respuesta
	 * @return
	 */
	private void returnDialogoGestorLogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		final String message = null;

		if (!respuesta.isCanceled()) {

		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}

	}

	/**
	 * Gestion de retorno de asistente logo.
	 *
	 * @param respuesta
	 * @return
	 */
	private void returnDialogoAsistenteLogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		final String message = null;

		if (!respuesta.isCanceled()) {

		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}

	}

	/**
	 * Gestion de retorno de asistente css.
	 *
	 * @param respuesta
	 * @return
	 */
	private void returnDialogoAsistenteCss(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		final String message = null;

		if (!respuesta.isCanceled()) {

		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}

	}

	/**
	 * Gestión de retorno asistente pie.
	 *
	 * @param event
	 */
	public void returnDialogoAsistentePie(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {
			case ALTA:

				final Literal traducciones = (Literal) respuesta.getResult();
				this.data.setPie(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionesModif = (Literal) respuesta.getResult();
				this.data.setPie(traduccionesModif);

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
	 * Gestion de retorno de asistente formularios de soporte.
	 *
	 * @param respuesta
	 * @return
	 */
	private void returnDialogoAsistenteForm(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		final String message = null;

		if (!respuesta.isCanceled()) {

		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
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
	public Entidad getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Entidad data) {
		this.data = data;
	}

	/**
	 * @return the file
	 */
	public Part getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(final Part file) {
		this.file = file;
	}

	/**
	 * @return the fileLogoGestor
	 */
	public UploadedFile getFileLogoGestor() {
		return fileLogoGestor;
	}

	/**
	 * @param fileLogoGestor
	 *            the fileLogoGestor to set
	 */
	public void setFileLogoGestor(final UploadedFile fileLogoGestor) {
		this.fileLogoGestor = fileLogoGestor;
	}

	/**
	 * @return the downloadGestorLogo
	 */
	public DefaultStreamedContent getDownloadGestorLogo() {
		return downloadGestorLogo;
	}

	/**
	 * @param downloadGestorLogo
	 *            the downloadGestorLogo to set
	 */
	public void setDownloadGestorLogo(final DefaultStreamedContent downloadGestorLogo) {
		this.downloadGestorLogo = downloadGestorLogo;
	}

}
