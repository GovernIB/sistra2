package es.caib.sistramit.frontend.view;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.View;

import es.caib.sistra2.commons.utils.XssFilter;

/**
 * Vista para renderizar un download de fichero.
 *
 * @author Indra
 */
public final class DownloadFileView implements View {

	/**
	 * Log.
	 */
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Instancia view.
	 */
	private static final DownloadFileView INSTANCE_DOWNLOADVIEW = new DownloadFileView();

	/**
	 * Parámetros view: bytes del documento.
	 */
	public static final String PARAMETER_FILEDATA = "filedata";

	/**
	 * Parámetros view: nombre del documento con extensión.
	 */
	public static final String PARAMETER_FILENAME = "filename";

	/**
	 * Constructor.
	 */
	private DownloadFileView() {
	}

	/**
	 * Método de acceso a instance.
	 *
	 * @return instance
	 */
	public static DownloadFileView getInstanceDownloadview() {
		return INSTANCE_DOWNLOADVIEW;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.web.servlet.View#getContentType()
	 */
	@Override
	public String getContentType() {
		return "application/octet-stream";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.web.servlet.View#render(java.util.Map,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		// Recogemos nombre fichero y datos
		String nombreFichero = (String) model.get(PARAMETER_FILENAME);
		byte[] datosFichero = (byte[]) model.get(PARAMETER_FILEDATA);

		// Volcamos fichero en stream response
		if (datosFichero == null) {
			datosFichero = new byte[0];
		}

		final ByteArrayInputStream bis = new ByteArrayInputStream(datosFichero);

		try {

			// Normalizamos fichero
			nombreFichero = XssFilter.normalizarFilename(nombreFichero);

			// response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" + nombreFichero + ";");

			// Realizamos copia
			IOUtils.copy(bis, response.getOutputStream());

		} catch (final java.io.IOException exc) {
			logger.info("Client aborted");
		} catch (final Exception exc) {
			logger.error("Error descarregant fitxer", exc);
		} finally {
			try {
				if (!response.isCommitted()) {
					response.flushBuffer();
				}
			} catch (final Exception ex) {
				logger.warn("Client flush no realitzat", ex);
			}
			if (bis != null) {
				bis.close();
			}
		}
	}

}
