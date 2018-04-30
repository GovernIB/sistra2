package es.caib.sistramit.frontend.view;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.View;

/**
 * Vista que renderiza un pagina html/css pasada como parámetro.
 *
 * @author Indra
 *
 */
public final class HtmlView implements View {

	/**
	 * Instancia view.
	 */
	private static final HtmlView INSTANCE_HTMLVIEW = new HtmlView();

	/**
	 * Nombre del objeto pasado a la view para renderizar el html/css.
	 */
	public static final String CONTENT = "content";

	/**
	 * Nombre del objeto pasado a la view para indicar el content type: html/css.
	 */
	public static final String CONTENT_TYPE = "contentType";

	/**
	 * Indica que el content type es css.
	 */
	public static final String CONTENT_TYPE_CSS = "css";

	/**
	 * Indica que el content type es imagen.
	 */
	public static final String CONTENT_TYPE_IMAGE = "image/jpeg";

	/**
	 * Indica que el content type es html.
	 */
	public static final String CONTENT_TYPE_HTML = "html";

	/**
	 * Text/html.
	 */
	private static final String TEXT_HTML = "text/html";

	/**
	 * Constructor.
	 */
	private HtmlView() {
	}

	/**
	 * Método de acceso a instance.
	 *
	 * @return instance
	 */
	public static HtmlView getInstanceHtmlview() {
		return INSTANCE_HTMLVIEW;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.web.servlet.View#getContentType()
	 */
	@Override
	public String getContentType() {
		return TEXT_HTML;
	}

	@Override
	public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		// Establecemos content type
		final String contentType = (String) model.get(CONTENT_TYPE);
		if (CONTENT_TYPE_HTML.equals(contentType)) {
			response.setContentType(TEXT_HTML);
		} else if (CONTENT_TYPE_IMAGE.equals(contentType)) {
			response.setContentType("image/jpeg");
		} else {
			response.setContentType("text/css");
		}
		// Establecemos content
		final byte[] content = (byte[]) model.get(CONTENT);
		response.setContentLength(content.length);
		IOUtils.copy(new ByteArrayInputStream(content), response.getOutputStream());
		response.flushBuffer();
	}
}
