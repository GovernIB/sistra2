package es.caib.sistra2.commons.pdf;

import java.awt.Color;

import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;

import es.caib.sistra2.commons.pdfcaib.FontResourceLoader;

/**
 * Linea
 *
 * @author Indra
 *
 */
public class LineaComponente {

	/** Texto. **/
	private String texto;

	/** Font. **/
	private Font font;

	/** Url. **/
	private String url;

	/** Constructor básico. **/
	public LineaComponente(final String texto, final Font font) {
		this.texto = texto;
		this.font = font;
	}

	/** Constructor con enlace. **/
	public LineaComponente(final String texto, final Font font, final String url) {
		this.texto = texto;
		this.font = font;
		this.url = url;
	}

	/** Obtiene la frase. **/
	public Phrase getPhrase() {
		Phrase phrase;
		if (url != null && !url.isEmpty()) {
			final Chunk chunkEnlace = new Chunk(texto, font);
			chunkEnlace.setAnchor(url);
			phrase = new Phrase(chunkEnlace);
		} else {
			phrase = new Phrase(texto, font);
		}
		return phrase;
	}

	/**
	 * Devuelve fuente de tipo enlace
	 *
	 * @param document
	 * @return
	 */
	public static Font getFontEnlace(final PDFDocument document) {
		final Font defaultFont = document.getContext().getDefaultFont();
		return new Font(defaultFont.getBaseFont(), defaultFont.getSize(), Font.NORMAL, new Color(0f, 0f, 1f));
	}

	/**
	 * Devuelve fuente de tipo negrita.
	 *
	 * @param document
	 * @return
	 */
	public static Font getFontNegrita(final PDFDocument document) {
		final Font defaultFont = document.getContext().getDefaultFont();
		final Font font = FontResourceLoader.getNotoSansFont(defaultFont.getSize(), true);
		font.setColor(defaultFont.getColor());
		return font;
	}

	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto
	 *            the texto to set
	 */
	public void setTexto(final String texto) {
		this.texto = texto;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(final Font font) {
		this.font = font;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

}
