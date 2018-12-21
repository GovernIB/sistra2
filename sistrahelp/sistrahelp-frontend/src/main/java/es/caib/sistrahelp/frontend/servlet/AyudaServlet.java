package es.caib.sistrahelp.frontend.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * Servlet renderizador html.
 *
 * @author Indra
 *
 */
public class AyudaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String contexto;
	private static String lineSeparator;

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);

		setContexto(config.getServletContext().getContextPath());
		setLineSeparator(System.getProperty("line.separator"));
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String id = request.getParameter("id");

		final String lang = request.getParameter("lang");

		if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(lang)) {
			request.getRequestDispatcher("/ayuda/" + lang + "/" + id + ".html").forward(request, response);
		}
	}

	public static String getContexto() {
		return contexto;
	}

	public static void setContexto(final String pContexto) {
		contexto = pContexto;
	}

	public static String getLineSeparator() {
		return lineSeparator;
	}

	public static void setLineSeparator(final String pLineSeparator) {
		lineSeparator = pLineSeparator;
	}

}
