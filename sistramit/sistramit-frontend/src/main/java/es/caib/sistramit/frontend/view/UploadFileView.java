package es.caib.sistramit.frontend.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * Vista que renderiza un pagina html que invoca a la función fileUploaded del
 * html parent. La función fileUploaded tendrá como parámetro la respuesta JSON.
 *
 * @author Indra
 *
 */
public final class UploadFileView implements View {

	/**
	 * Instancia view.
	 */
	private static final UploadFileView INSTANCE_UPLOADVIEW = new UploadFileView();

	/**
	 * Nombre del objeto pasado a la view para renderizar a JSON.
	 */
	public static final String JSON_OBJECT = "root";

	/**
	 * Constructor.
	 */
	private UploadFileView() {
	}

	/**
	 * Método de acceso a instance.
	 *
	 * @return instance
	 */
	public static UploadFileView getInstanceUploadview() {
		return INSTANCE_UPLOADVIEW;
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

	@Override
	public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		// Convertimos a json el objeto pasado
		final String json = JsonView.getInstanceJsonview().jsonToString(model.get(JSON_OBJECT));

		// Retornamos html que notificara de que se ha realiza el upload
		response.setContentType("text/html");
		final PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		pw.println("<script type=\"text/javascript\">");
		pw.println("<!--");
		pw.println("json_enviament=eval(" + json + ");");
		pw.println("top.$.documentAnnexat({ json: json_enviament, desde: \"guardar\" });");
		pw.println("-->");
		pw.println("</script>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("</body>");
		pw.println("</html>");
	}

}
