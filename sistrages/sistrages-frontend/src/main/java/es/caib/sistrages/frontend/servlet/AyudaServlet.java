package es.caib.sistrages.frontend.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.comun.Constantes;

/**
 * Servlet renderizador html.
 *
 * @author Indra
 *
 */
public class AyudaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private SystemService systemService;

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// Idioma
		final String lang = request.getParameter("lang");

		// Parametros ayudas pantallas
		final String id = request.getParameter("id");

		// Parametros ayudas scripts
		final String plugin = request.getParameter("plugin");
		final String metodo = request.getParameter("metodo");

		// Parametros ficheros css, js, imagenes
		final String css = request.getParameter("css");
		final String js = request.getParameter("js");
		final String jpg = request.getParameter("jpg");

		// Obtenemos url pagina ayuda
		String url = null;
		String mimeType = null;
		if (StringUtils.isNotEmpty(id)) {
			url = "/ayuda/" + lang + "/" + id + ".html";
			mimeType = "text/html";
		} else if (StringUtils.isNotEmpty(plugin)) {
			if (StringUtils.isEmpty(metodo)) {
				url = "/ayuda/" + lang + "/plugins/" + plugin + "/index.html";
			} else {
				url = "/ayuda/" + lang + "/plugins/" + plugin + "/" + metodo + ".html";
			}
			mimeType = "text/html";
		} else if (StringUtils.isNotEmpty(css)) {
			url = "/ayuda/" + css + ".css";
			mimeType = "text/css";
		} else if (StringUtils.isNotEmpty(js)) {
			url = "/ayuda/" + js + ".js";
			mimeType = "text/javascript";
		} else if (StringUtils.isNotEmpty(jpg)) {
			url = "/ayuda/" + jpg + ".jpg";
			mimeType = "image/jpeg";
		}

		// Retornamos contenido
		retornarContenidoAyuda(url, mimeType, request, response);

	}

	private void retornarContenidoAyuda(final String url, final String mimeType, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException, ServletException {

		// Obtiene directorio externo ayuda
		final String dirAyudaExterna = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAGES_AYUDA_PATH);

		// Retornamos contenido
		if (StringUtils.isEmpty(url))
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		else if (StringUtils.isBlank(dirAyudaExterna)) {
			request.getRequestDispatcher(url).forward(request, response);
		} else {
			if (StringUtils.isBlank(mimeType)) {
				response.setHeader("Content-Type", "application/octet-stream");
			} else {
				response.setHeader("Content-Type", mimeType);
			}
			final File f = new File(dirAyudaExterna + url);
			if (f.exists()) {
				final FileInputStream fis = new FileInputStream(f);
				final byte[] content = IOUtils.toByteArray(fis);
				final ByteArrayInputStream bis = new ByteArrayInputStream(content);
				IOUtils.copy(bis, response.getOutputStream());
				bis.close();
				fis.close();
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}

	}

}
