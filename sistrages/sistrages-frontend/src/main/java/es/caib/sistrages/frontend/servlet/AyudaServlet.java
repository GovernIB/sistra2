package es.caib.sistrages.frontend.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.SystemService;

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

	private List<String> listaPlugin;

	private List<String> listaMetodo;

	private boolean volver;

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
		String plugin = request.getParameter("plugin");
		String metodo = request.getParameter("metodo");

		// Parametros ficheros css, js, imagenes
		final String css = request.getParameter("css");
		final String js = request.getParameter("js");
		final String jpg = request.getParameter("jpg");

		final String boton = request.getParameter("boton");
		volver = false;

		if(StringUtils.isNotEmpty(boton) && boton.equals("AYUDA")) {
			listaPlugin = new ArrayList<>();
			listaMetodo = new ArrayList<>();
		}

		if(StringUtils.isNotEmpty(boton) && boton.equals("VOLVER")) {
			if(listaPlugin.size()==1) {
				plugin = listaPlugin.get(listaPlugin.size()-1);
				metodo = listaMetodo.get(listaMetodo.size()-1);
			}else {
				listaPlugin.remove(listaPlugin.size()-1);
				listaMetodo.remove(listaMetodo.size()-1);
				plugin = listaPlugin.get(listaPlugin.size()-1);
				metodo = listaMetodo.get(listaMetodo.size()-1);
				volver = true;
			}
		}

		// Obtenemos url pagina ayuda
		String url = null;
		String mimeType = null;
		if (StringUtils.isNotEmpty(id)) {
			url = lang + "/" + id + ".html";
			mimeType = "text/html";
		} else if (StringUtils.isNotEmpty(plugin)) {
			if (StringUtils.isEmpty(metodo)) {
				url = lang + "/plugins/" + plugin + "/index.html";
				if(!volver) {
					listaPlugin.add(plugin);
					listaMetodo.add("index");
				}
			} else {
				url = lang + "/plugins/" + plugin + "/" + metodo + ".html";
				if(!volver) {
					listaPlugin.add(plugin);
					listaMetodo.add(metodo);
				}
			}
			mimeType = "text/html";
		} else if (StringUtils.isNotEmpty(css)) {
			url = css + ".css";
			mimeType = "text/css";
		} else if (StringUtils.isNotEmpty(js)) {
			url = js + ".js";
			mimeType = "text/javascript";
		} else if (StringUtils.isNotEmpty(jpg)) {
			url = jpg + ".jpg";
			mimeType = "image/jpeg";
		}

		// Retornamos contenido
		retornarContenidoAyuda(url, mimeType, request, response);

	}

	private void retornarContenidoAyuda(String url, final String mimeType, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException, ServletException {

		// Obtiene directorio externo ayuda
		final String dirAyudaExterna = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_AYUDA_PATH.toString());

		// Retornamos contenido
		if (StringUtils.isEmpty(url))
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		else if (StringUtils.isBlank(dirAyudaExterna)) {
			url = "/ayuda/" + url;
			request.getRequestDispatcher(url).forward(request, response);
		} else {
			if (StringUtils.isBlank(mimeType)) {
				response.setHeader("Content-Type", "application/octet-stream");
			} else {
				response.setHeader("Content-Type", mimeType);
			}
			url = dirAyudaExterna + "/" + url;
			final File f = new File(url);
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
