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

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
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
	private static String lineSeparator;

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
		setLineSeparator(System.getProperty("line.separator"));
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String idForm = request.getParameter("id");
		final String page = request.getParameter("page");

		response.setContentType("text/html");
		final PrintWriter out = response.getWriter();
		final StringBuilder html = new StringBuilder();
		if (StringUtils.isNotEmpty(idForm) && StringUtils.isNotEmpty(page)) {
			paginaHTML(html, idForm, page);
		}

		out.write(html.toString());
		out.flush();
		out.close();

	}

	private void paginaHTML(final StringBuilder pOut, final String idForm, final String page) {

		// final FormularioInterno formulario =
		// FormularioMockBD.recuperar(Long.parseLong(idForm));
		// final PaginaFormulario pagina =
		// FormularioMockBD.recuperarPagina(Long.parseLong(idForm),
		// Integer.parseInt(page));

		final FormularioInterno formulario = formIntService.getFormularioInternoPaginas(Long.parseLong(idForm));
		final PaginaFormulario pagina = formIntService
				.getContenidoPaginaFormulario(formulario.getPaginas().get(Integer.parseInt(page) - 1).getId());

		escribeLinea(pOut, "<!doctype html>", 0);
		escribeLinea(pOut, "<html>", 0);

		cabeceraHTML(pOut);

		escribeLinea(pOut, "<body>", 0);
		escribeLinea(pOut, "<div id=\"imc-forms-contenidor\" >", 1);

		if (formulario.isMostrarCabecera()) {
			cabeceraFormulario(pOut, formulario.getTextoCabecera().getTraduccion("es"));
		}

		escribeLinea(pOut, "<form>", 2);
		escribeLinea(pOut, "<div id=\"imc-forms-formulari\" class=\"imc-forms-formulari imc-form\">", 3);
		escribeLinea(pOut, "<div class=\"imc-form-contingut\">", 4);

		cuerpoHTML(pOut, pagina);

		escribeLinea(pOut, "</div>", 4);
		escribeLinea(pOut, "</div>", 3);
		escribeLinea(pOut, "</form>", 2);
		escribeLinea(pOut, "</div>", 1);

		scripts(pOut);

		escribeLinea(pOut, "</body>", 0);
		escribeLinea(pOut, "</html>", 0);
	}

	private void cabeceraHTML(final StringBuilder pOut) {
		final String urlCssIni = "<link rel=\"stylesheet\" media=\"screen\" href=\"" + getContexto() + "/forms/css/";
		final String urlCssFin = ".css\" />";

		final String urlJsIni = "<script src=\"" + getContexto() + "/forms/js/";
		final String urlJsFin = ".js\"></script>";

		escribeLinea(pOut, "<head>", 0);
		escribeLinea(pOut, "<meta charset=\"utf-8\" />", 1);
		escribeLinea(pOut, "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />", 1);
		escribeLinea(pOut, urlCssIni, "imc-forms", urlCssFin, 1);
		escribeLinea(pOut, urlCssIni, "imc-forms-select", urlCssFin, 1);
		escribeLinea(pOut, urlCssIni, "imc-animate", urlCssFin, 1);
		escribeLinea(pOut, urlCssIni, "imc-destaca", urlCssFin, 1);
		escribeLinea(pOut, "<!--[if lt IE 9]>", 1);
		escribeLinea(pOut, urlJsIni, "html5", urlJsFin, 1);
		escribeLinea(pOut, urlCssIni, "imc-ie8", urlCssFin, 1);
		escribeLinea(pOut, "<![endif]-->", 1);
		escribeLinea(pOut, "<!--[if lt IE 8]>", 1);
		escribeLinea(pOut, urlCssIni, "imc-ie7", urlCssFin, 1);
		escribeLinea(pOut, "<![endif]-->", 1);
		escribeLinea(pOut, urlJsIni, "utils/modernizr-imc-0.3", urlJsFin, 1);
		escribeLinea(pOut, urlJsIni, "utils/jquery-3.3.1.min", urlJsFin, 1);
		escribeLinea(pOut, urlJsIni, "jquery-imc-forms-inicia", urlJsFin, 1);
		escribeLinea(pOut, "</head>", 0);
	}

	private void cabeceraFormulario(final StringBuilder pOut, final String textoCabecera) {
		escribeLinea(pOut, "<header class=\"imc-forms-cap\" id=\"imc-forms-cap\">", 2);
		escribeLinea(pOut, "<div class=\"imc--contingut\">", 3);
		escribeLinea(pOut, "<h2><span>", textoCabecera, "</span></h2>", 4);
		escribeLinea(pOut, "</div>", 3);
		escribeLinea(pOut, "</header>", 2);
	}

	private void cuerpoHTML(final StringBuilder pOut, final PaginaFormulario pPagina) {

		if (pPagina != null) {
			for (final LineaComponentesFormulario lc : pPagina.getLineas()) {

				for (final ComponenteFormulario cf : lc.getComponentes()) {

					switch (cf.getTipo()) {
					case SECCION:
						campoSeccion(pOut, cf);
						break;
					case CAMPO_TEXTO:
						campoTexto(pOut, cf);
						break;
					default:
						break;
					}

					// CAMPO_TEXTO("CT"),
					// SELECTOR("SE"),
					// SECCION("SC"),
					// CHECKBOX("CK"),
					// ETIQUETA("ET"),
					// CAPTCHA("CP"),
					// IMAGEN("IM"),

				}

				escribeLinea(pOut, "<div class=\"imc-separador imc-sep-punt editable\" id=\"L",
						String.valueOf(lc.getId()), "\"></div>", 5);

			}
		}
	}

	private void campoSeccion(final StringBuilder pOut, final ComponenteFormulario pCF) {
		final ComponenteFormularioSeccion componente = (ComponenteFormularioSeccion) pCF;
		final StringBuilder estilo = new StringBuilder();
		estilo.append("imc-el-name-").append(String.valueOf(pCF.getId()));

		escribeLinea(pOut, "<h4 class=\"imc-seccio\">", 5);

		escribeLinea(pOut, "<span class=\"imc-se-marca editable\" id=\"", String.valueOf(pCF.getId()), "\">",
				componente.getLetra(), "</span>", 6);
		if (!pCF.isNoMostrarTexto() && pCF.getTexto() != null) {
			escribeLinea(pOut, "<span class=\"imc-se-titol\">", pCF.getTexto().getTraduccion("es"), "</span>", 6);
		}

		escribeLinea(pOut, "</h4>", 5);

	}

	private void campoTexto(final StringBuilder pOut, final ComponenteFormulario pCF) {
		final StringBuilder estilo = new StringBuilder();
		estilo.append("imc-el-name-").append(String.valueOf(pCF.getId()));

		if (pCF.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(pCF.getNumColumnas());
		}

		escribeLinea(pOut, "<div class=\"imc-element ", estilo.toString(), "\" data-type=\"text\">", 5);

		if (!pCF.isNoMostrarTexto() && pCF.getTexto() != null) {
			escribeLinea(pOut, "<div class=\"imc-el-etiqueta\"><label for=\"", String.valueOf(pCF.getId()), "\">",
					pCF.getTexto().getTraduccion("es"), "</label></div>", 6);
		}
		escribeLinea(pOut, "<div class=\"imc-el-control\"><input class=\"editable\" id=\"", String.valueOf(pCF.getId()),
				"\" name=\"", pCF.getIdComponente(), "\" type=\"text\"/></div>", 6);

		escribeLinea(pOut, "</div>", 5);

	}

	private void scripts(final StringBuilder pOut) {
		escribeLinea(pOut, "<script type=\"text/javascript\">", 1);
		escribeLinea(pOut, "function rcEditarArea(id) { parent.seleccionarElementoCommand([{name:'id', value:id}]);}",
				2);
		escribeLinea(pOut, "var elementos = document.getElementsByClassName(\"editable\");", 2);
		escribeLinea(pOut, "for(var i = 0 ; i < elementos.length; i++) { ", 2);
		escribeLinea(pOut, "var element = elementos[i];", 3);
		escribeLinea(pOut, "element.addEventListener(\"click\", function(event){ rcEditarArea (event.target.id); });",
				3);
		escribeLinea(pOut, "}", 2);
		escribeLinea(pOut, "</script>", 1);
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto, final int pNtab) {
		pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		pOut.append(pTexto);
		pOut.append(lineSeparator);
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final int pNtab) {
		pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(lineSeparator);
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final String pTexto5, final int pNtab) {
		pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		pOut.append(pTexto5);
		pOut.append(lineSeparator);
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
