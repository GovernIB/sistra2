package es.caib.sistrages.frontend.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.FormularioDisenyo;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.frontend.util.FormularioMockBD;

/**
 * Servlet renderizador html.
 *
 * @author Indra
 *
 */
public class FormRenderServlet extends HttpServlet {

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// TODO MODO TEST. PRIMERA PAGINA EN CAST.

		response.setContentType("text/html");

		final FormularioDisenyo formulario = FormularioMockBD.recuperar(1L);

		final PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<body>");

		for (final LineaComponentesFormulario lc : formulario.getPaginas().get(0).getLineas()) {

			for (final ComponenteFormulario cf : lc.getComponentes()) {

				switch (cf.getTipo()) {
				case CAMPO_TEXTO:
					out.println("<label>" + cf.getTexto().getTraduccion("es") + "</label>");
					out.println("<input id=\"" + cf.getId() + "\" name=\"" + cf.getCodigo()
							+ "\" class=\"editable\" type=\"text \" value=\"\" />");
					break;
				case SELECTOR:
					out.println("<label>" + cf.getTexto().getTraduccion("es") + "</label>");
					out.println("<select id=\"" + cf.getId() + "\" name=\"" + cf.getCodigo()
							+ "\" class=\"editable\" type=\"text \" value=\"\" >");
					out.println("</select>");
					break;
				case ETIQUETA:
					out.println("<div id=\"" + cf.getId() + "\"class=\"editable\">" + cf.getTexto().getTraduccion("es")
							+ "</div>");
					break;
				default:
					out.println("<label>Componente no soportado</label>");
				}

			}

			out.println("<br/>");
		}

		out.println("<script type=\"text/javascript\">");
		out.println("function rcEditarArea(id) { parent.seleccionarElementoCommand([{name:'id', value:id}]);}");
		out.println("var elementos = document.getElementsByClassName(\"editable\");");
		out.println("for(var i = 0 ; i < elementos.length; i++) { ");
		out.println("	var element = elementos[i];");
		out.println("	element.addEventListener(\"click\", function(event){ rcEditarArea (event.target.id); });");
		out.println("}");
		out.println("</script>");

		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();

	}

}
