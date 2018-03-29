package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Dialogo fichero.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogFichero extends DialogControllerBase {

	/** Id fichero. **/
	private String id;

	/** Si se previsualiza el fichero. **/
	private String previsualizar;

	/** Boolean de previsualizar. **/
	private boolean visiblePrevisualizar = false;

	/** Fichero. **/
	private Fichero data;

	/** Fichero upload. **/
	private Part file;

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
	 * Inicialización.
	 *
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		// TODO REVISAR
		/*
		 * if (id == null) { data = new Fichero(0l, "Fichero0.jpg"); } else if (id ==
		 * "1") { data = new Fichero(1l, "Fichero.png"); previsualizar = "S"; } else if
		 * (id == "2") { data = new Fichero(1l, "Fichero.gif"); previsualizar = "S"; }
		 * else if (id == "2") { data = new Fichero(1l, "Fichero.css"); previsualizar =
		 * "N"; } else { data = new Fichero(0l, "Fichero0.jpg"); }
		 */
		if (previsualizar != null && "S".equals(previsualizar)) {
			visiblePrevisualizar = true;
		} else {
			visiblePrevisualizar = false;
		}

	}

	/**
	 * Descargar.
	 */
	public void aceptar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);

	}

	/**
	 * Devuelve la imagen.
	 *
	 * @return
	 */
	public StreamedContent getImagen() {
		final FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the
			// image bytes.

			// TODO REVISAR
			// return new DefaultStreamedContent(new
			// ByteArrayInputStream(data.getContenido()));
			return null;

		}
	}

	// A PDF to download
	private static final String PDF_URL = "http://caibter.indra.es/descargas/seu/tramite.html";

	/**
	 * This method reads PDF from the URL and writes it back as a response.
	 *
	 */
	public void descargar() {
		try {
			// Get the FacesContext
			final FacesContext facesContext = FacesContext.getCurrentInstance();

			// Get HTTP response
			final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

			// Set response headers
			response.reset(); // Reset the response in the first place
			response.setHeader("Content-Type", "application/pdf"); // Set only the content type

			// Open response output stream
			final OutputStream responseOutputStream = response.getOutputStream();

			// Read PDF contents

			// TODO REVISAR
			// final ByteArrayInputStream pdfInputStream = new
			// ByteArrayInputStream(data.getContenido());
			final ByteArrayInputStream pdfInputStream = new ByteArrayInputStream("Revisar".getBytes());

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
		} catch (final IOException ex) {
			throw new FrontException("Error descarga", ex);
		}
	}

	/**
	 * Descargar.
	 */
	public void descargar2() {
		// TODO REVISAR
		/*
		 * try { final File fileTemporal = File.createTempFile("fichero", "png"); final
		 * InputStream fis = new FileInputStream(fileTemporal); int offset = 0; int
		 * numRead = 0; while ((offset < data.getContenido().length) && ((numRead =
		 * fis.read(data.getContenido(), offset, data.getContenido().length - offset))
		 * >= 0)) { offset += numRead; } fis.close(); final HttpServletResponse response
		 * = (HttpServletResponse) FacesContext.getCurrentInstance()
		 * .getExternalContext().getResponse();
		 *
		 * response.setContentType("application/octet-stream");
		 * response.setHeader("Content-Disposition",
		 * "attachment;filename=instructions.txt");
		 * response.getOutputStream().write(data.getContenido());
		 * response.getOutputStream().flush(); response.getOutputStream().close();
		 * fis.close(); FacesContext.getCurrentInstance().responseComplete(); } catch
		 * (final IOException ex) { throw new FrontException("Error descarga", ex); }
		 */
	}

	/**
	 * Upload.
	 *
	 */
	public void upload() {
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

			data.setNombre(filename);

			// data.setContenido(contenido);
		} catch (final IOException ex) {
			throw new FrontException("Error upload", ex);
		}
	}

	private boolean valido = false;

	/**
	 * Valida el fichero.
	 *
	 * @param ctx
	 * @param comp
	 * @param value
	 */
	public void validateFile(final FacesContext ctx, final UIComponent comp, final Object value) {
		if (file.getSize() > 1024) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Fichero demsiado grande");
			valido = false;
		} else {
			valido = true;
		}
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
	 * @return the previsualizar
	 */
	public String getPrevisualizar() {
		return previsualizar;
	}

	/**
	 * @param previsualizar
	 *            the previsualizar to set
	 */
	public void setPrevisualizar(final String previsualizar) {
		this.previsualizar = previsualizar;
	}

	/**
	 * @return the visiblePrevisualizar
	 */
	public boolean isVisiblePrevisualizar() {
		return visiblePrevisualizar;
	}

	/**
	 * @param visiblePrevisualizar
	 *            the visiblePrevisualizar to set
	 */
	public void setVisiblePrevisualizar(final boolean visiblePrevisualizar) {
		this.visiblePrevisualizar = visiblePrevisualizar;
	}

	/**
	 * @return the data
	 */
	public Fichero getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Fichero data) {
		this.data = data;
	}

}
