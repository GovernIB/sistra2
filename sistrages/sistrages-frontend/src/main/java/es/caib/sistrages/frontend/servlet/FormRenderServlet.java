package es.caib.sistrages.frontend.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.service.FormularioInternoService;

/**
 * Servlet renderizador html.
 *
 * @author Indra
 *
 */
public class FormRenderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String contexto;

	@Inject
	FormularioInternoService formIntService;

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);

		setContexto(config.getServletContext().getContextPath());
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String idForm = request.getParameter("id");
		final String page = request.getParameter("page");
		final String lang = request.getParameter("lang");
		final String mostrarOcultos = request.getParameter("showHidden");
		final String idComponente = request.getParameter("idComponente");

		response.setContentType("text/html");
		final PrintWriter out = response.getWriter();
		String html = null;
		if (StringUtils.isNotEmpty(idForm) && StringUtils.isNotEmpty(page)) {
			html = formIntService.generaPaginaHTMLEditor(Long.parseLong(idForm), Long.parseLong(page), idComponente,
					lang, Boolean.parseBoolean(mostrarOcultos), getContexto());
		} else {
			html = "";
		}

		out.write(html);
		out.flush();
		out.close();

	}

	public static String getContexto() {
		return contexto;
	}

	public static void setContexto(final String pContexto) {
		contexto = pContexto;
	}

}
