package es.caib.sistrages.frontend.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.model.ContenidoFichero;
import es.caib.sistrages.core.api.service.GestorFicherosService;

/**
 * Servlet gestor de ficheros.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class GestorFicherosServlet extends HttpServlet {

	@Inject
	private GestorFicherosService gestorFicherosService;

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String idFichero = request.getParameter("id");

		final ContenidoFichero cf = gestorFicherosService.obtenerContenidoFichero(Long.parseLong(idFichero));

		String mimeType = getServletContext().getMimeType(cf.getFilename());
		if (StringUtils.isBlank(mimeType)) {
			mimeType = "application/octet-stream";
		}
		response.setHeader("Content-Type", mimeType);
		response.setHeader("Content-Length", String.valueOf(cf.getContent().length));
		response.setHeader("Content-Disposition", "attachment; filename=" + cf.getFilename() + ";");

		final ByteArrayInputStream bis = new ByteArrayInputStream(cf.getContent());
		IOUtils.copy(bis, response.getOutputStream());
		bis.close();
	}

}
