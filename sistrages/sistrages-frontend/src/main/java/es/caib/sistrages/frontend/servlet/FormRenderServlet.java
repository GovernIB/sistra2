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
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.types.TypeAlineacionTexto;
import es.caib.sistrages.core.api.model.types.TypeCampoTexto;
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
		final String idComponente = request.getParameter("idComponente");

		response.setContentType("text/html");
		final PrintWriter out = response.getWriter();
		final StringBuilder html = new StringBuilder();
		if (StringUtils.isNotEmpty(idForm) && StringUtils.isNotEmpty(page)) {
			paginaHTML(html, idForm, page, idComponente);
		}

		out.write(html.toString());
		out.flush();
		out.close();

	}

	private void paginaHTML(final StringBuilder pOut, final String pIdForm, final String pPage,
			final String pIdComponente) {

		final FormularioInterno formulario = formIntService.getFormularioInternoPaginas(Long.parseLong(pIdForm));
		final PaginaFormulario pagina = formIntService
				.getContenidoPaginaFormulario(formulario.getPaginas().get(Integer.parseInt(pPage) - 1).getId());

		escribeLinea(pOut, "<!doctype html>", 0);
		escribeLinea(pOut, "<html>", 0);

		cabeceraHTML(pOut);

		escribeLinea(pOut, "<body>", 0);
		escribeLinea(pOut, "<div id=\"imc-contenidor\" class=\"imc-contenidor\" >", 1);

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

		scripts(pOut, pIdComponente);

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
		escribeLinea(pOut, urlCssIni, "imc-forms-taula-iframe", urlCssFin, 1);
		escribeLinea(pOut, urlCssIni, "imc-forms--edicio", urlCssFin, 1);

		escribeLinea(pOut, urlJsIni, "utils/modernizr-imc-0.3", urlJsFin, 1);
		escribeLinea(pOut, urlJsIni, "utils/jquery-3.3.1.min", urlJsFin, 1);
		escribeLinea(pOut, urlJsIni, "imc-forms--edicio", urlJsFin, 1);
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
					case ETIQUETA:
						campoEtiqueta(pOut, cf);
						break;
					case CHECKBOX:
						campoCheckBox(pOut, cf);
						break;
					case SELECTOR:
						campoSelector(pOut, cf);
						break;
					default:
						break;
					}

				}

				if (lc.getComponentes().isEmpty()) {
					escribeLinea(pOut, "<br/><br/>", 6);
				}

				escribeLinea(pOut, "<div class=\"imc-element imc-separador imc-sep-salt-carro\" id=\"L",
						String.valueOf(lc.getId()), "\"></div>", 5);

			}
		}
	}

	private void campoSeccion(final StringBuilder pOut, final ComponenteFormulario pCF) {
		final ComponenteFormularioSeccion componente = (ComponenteFormularioSeccion) pCF;

		escribeLinea(pOut, "<h4 ", escribeId(pCF.getId()), "class=\"imc-element imc-seccio\">", 5);

		escribeLinea(pOut, "<span class=\"imc-se-marca\">", componente.getLetra(), "</span>", 6);
		if (!pCF.isNoMostrarTexto() && pCF.getTexto() != null) {
			escribeLinea(pOut, "<span class=\"imc-se-titol\">", pCF.getTexto().getTraduccion("es"), "</span>", 6);
		}

		escribeLinea(pOut, "</h4>", 5);

	}

	private void campoTexto(final StringBuilder pOut, final ComponenteFormulario pCF) {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) pCF;

		final StringBuilder estilo = new StringBuilder();
		estilo.append("imc-el-name-").append(String.valueOf(campo.getId()));

		String tipo = null;
		final StringBuilder elemento = new StringBuilder();

		if (campo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(campo.getNumColumnas());
		}

		if (!campo.isNoMostrarTexto() && campo.getTexto() != null) {
			if (TypeAlineacionTexto.CENTRO.equals(campo.getAlineacionTexto())) {
				estilo.append(" imc-el-centre");
			} else if (TypeAlineacionTexto.DERECHA.equals(campo.getAlineacionTexto())) {
				estilo.append(" imc-el-dreta");
			}

			if (campo.isObligatorio()) {
				estilo.append(" imc-el-obligatori");
			}

		}

		if (TypeCampoTexto.NORMAL.equals(campo.getTipoCampoTexto())) {
			if (campo.isNormalMultilinea()) {
				tipo = "textarea";
				Integer nfilas = campo.getNormalNumeroLineas();
				if (nfilas == null) {
					nfilas = 1;
				}
				estilo.append(" imc-el-files-").append(String.valueOf(nfilas));

				elemento.append("<textarea name=\"").append(campo.getIdComponente()).append("\" cols=\"20\" rows=\"")
						.append(nfilas).append("\"></textarea>");
			} else {
				tipo = "text";
			}
		} else if (TypeCampoTexto.FECHA.equals(campo.getTipoCampoTexto())) {
			tipo = "date";
		} else if (TypeCampoTexto.HORA.equals(campo.getTipoCampoTexto())) {
			tipo = "time";
		} else {
			tipo = "text";
		}

		if (tipo != null && !"textarea".equals(tipo)) {
			elemento.append("<input name=\"").append(campo.getIdComponente()).append("\" type=\"" + tipo + "\"/>");
		}

		escribeLinea(pOut, "<div ", escribeId(pCF.getId()), " class=\"imc-element ", estilo.toString(),
				"\" data-type=\"", tipo, "\">", 5);

		if (!campo.isNoMostrarTexto() && campo.getTexto() != null) {
			escribeLinea(pOut, "<div class=\"imc-el-etiqueta\"><label for=\"", String.valueOf(campo.getId()), "\">",
					campo.getTexto().getTraduccion("es"), "</label></div>", 6);
		}

		escribeLinea(pOut, "<div class=\"imc-el-control\">", elemento.toString(), "</div>", 6);

		escribeLinea(pOut, "</div>", 5);

	}

	private void campoCheckBox(final StringBuilder pOut, final ComponenteFormulario pCF) {
		final ComponenteFormularioCampoCheckbox campo = (ComponenteFormularioCampoCheckbox) pCF;

		final StringBuilder estilo = new StringBuilder();
		String texto = "";

		if (campo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(campo.getNumColumnas());
		}

		estilo.append(" imc-el-name-").append(String.valueOf(campo.getId()));

		if (!campo.isNoMostrarTexto() && campo.getTexto() != null) {
			texto = "<label for=\"" + campo.getId() + "\">" + campo.getTexto().getTraduccion("es") + "</label>";
		}
		escribeLinea(pOut, "<div ", escribeId(pCF.getId()), "class=\"imc-element imc-el-check", estilo.toString(),
				"\" data-type=\"check\">", 5);
		escribeLinea(pOut, "<div class=\"imc-el-control\">", 6);
		escribeLinea(pOut, "<div class=\"imc-input-check\">", 7);
		escribeLinea(pOut, "<input name=\"", String.valueOf(campo.getIdComponente()), "\" type=\"checkbox\">", 8);
		escribeLinea(pOut, texto, 8);

		escribeLinea(pOut, "</div>", 7);
		escribeLinea(pOut, "</div>", 6);
		escribeLinea(pOut, "</div>", 5);

	}

	private void campoSelector(final StringBuilder pOut, final ComponenteFormulario pCF) {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) pCF;

		final StringBuilder estilo = new StringBuilder();
		String texto = "";

		if (campo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(campo.getNumColumnas());
		}

		estilo.append(" imc-el-name-").append(String.valueOf(campo.getId()));

		if (!campo.isNoMostrarTexto() && campo.getTexto() != null) {
			texto = "<div class=\"imc-el-etiqueta\"><label for=\"" + campo.getId() + "\">"
					+ campo.getTexto().getTraduccion("es") + "</label></div>";
		}

		escribeLinea(pOut, "<div ", escribeId(pCF.getId()), "class=\"imc-element imc-el-selector", estilo.toString(),
				"\" data-type=\"select\">", 5);

		escribeLinea(pOut, texto, 6);

		escribeLinea(pOut, "<div class=\"imc-el-control\">", 6);
		escribeLinea(pOut, "<div class=\"imc-select imc-opcions\">", 7);

		escribeLinea(pOut, "<a class=\"imc-select\" tabindex=\"0\" href=\"javascript:;\" style=\"\"></a>", 8);
		escribeLinea(pOut, "<input name=\"", String.valueOf(campo.getIdComponente()), "\" type=\"hidden\">", 8);

		escribeLinea(pOut, "</div>", 7);
		escribeLinea(pOut, "</div>", 6);
		escribeLinea(pOut, "</div>", 5);
	}

	private void campoEtiqueta(final StringBuilder pOut, final ComponenteFormulario pCF) {
		final ComponenteFormularioEtiqueta componente = (ComponenteFormularioEtiqueta) pCF;
		final StringBuilder estilo = new StringBuilder();

		switch (componente.getTipoEtiqueta()) {
		case INFO:
			estilo.append("imc-missatge-en-linia-info");
			break;
		case WARNING:
			estilo.append("imc-missatge-en-linia-alerta");
			break;
		case ERROR:
			estilo.append("imc-missatge-en-linia-error");
			break;
		}

		escribeLinea(pOut, "<div class=\"imc-element imc-missatge-en-linia imc-missatge-en-linia-icona-sup ",
				estilo.toString(), "\" ", escribeId(pCF.getId()), "><p>", 5);
		if (pCF.getTexto() != null) {
			escribeLinea(pOut, pCF.getTexto().getTraduccion("es"), 6);
		}
		escribeLinea(pOut, "</p></div>", 5);
	}

	private void scripts(final StringBuilder pOut, final String pIdComponente) {
		escribeLinea(pOut, "<script type=\"text/javascript\">", 1);
		escribeLinea(pOut,
				"function rcEditarComponente(id) {parent.seleccionarElementoCommand([{name:'id', value:id}]);}", 2);

		escribeLinea(pOut, "var idComponente=\"" + pIdComponente + "\"", 2);
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
			final String pTexto3, final String pTexto4, final int pNtab) {
		pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
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

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final String pTexto5, final String pTexto6,
			final String pTexto7, final int pNtab) {
		pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		pOut.append(pTexto5);
		pOut.append(pTexto6);
		pOut.append(pTexto7);
		pOut.append(lineSeparator);
	}

	private String escribeId(final Long pId) {
		return escribeId(pId, null);
	}

	private String escribeId(final Long pId, final Integer pOrden) {
		String id = null;
		if (pOrden == null) {
			id = "id=\"" + pId + "\"";
		} else {
			id = "id=\"" + pId + "." + pOrden + "\"";
		}
		return id;
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
