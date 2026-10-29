package es.caib.sistra2.commons.pdfcaib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

import es.caib.sistra2.commons.pdfcaib.types.TypeFuente;

public final class FontResourceLoader {

	private static final Map<String, BaseFont> FONTS = new ConcurrentHashMap<String, BaseFont>();

	private FontResourceLoader() {
		// Utility class.
	}

	public static Font getNotoSansFont(final float size, final boolean bold) {
		final String fontName = bold ? TypeFuente.NOTOSANSBOLD.toString() : TypeFuente.NOTOSANS.toString();
		final String resourceFileName = bold ? "NotoSans-Bold.ttf" : "NotoSans-Regular.ttf";
		try {
			ensureFont(fontName, resourceFileName);
		} catch (final PdfCaibException e) {
			throw new IllegalStateException("No se puede cargar la fuente NotoSans", e);
		}
		return getFont(fontName, size);
	}

	static String resolveFontName(final String fontName) {
		return fontName == null || fontName.trim().isEmpty() ? TypeFuente.NOTOSANS.toString() : fontName;
	}

	static String resolveBoldFontName(final String fontName) {
		return fontName == null || fontName.trim().isEmpty() ? TypeFuente.NOTOSANSBOLD.toString() : fontName;
	}

	static void ensureFont(final String fontName, final String resourceFileName) throws PdfCaibException {
		final String resolvedFontName = resolveFontName(fontName);
		if (TypeFuente.HELVETICA.toString().equalsIgnoreCase(resolvedFontName)) {
			return;
		}
		if (!FONTS.containsKey(resolvedFontName)) {
			synchronized (FONTS) {
				if (!FONTS.containsKey(resolvedFontName)) {
					FONTS.put(resolvedFontName, loadFont(resolvedFontName, resourceFileName));
				}
			}
		}
	}

	static Font getFont(final String fontName, final float size) {
		final String resolvedFontName = resolveFontName(fontName);
		final BaseFont baseFont = FONTS.get(resolvedFontName);
		if (baseFont == null) {
			final Font standardFont = FontFactory.getFont(resolvedFontName, size);
			if (standardFont.getBaseFont() == null) {
				throw new IllegalStateException("La fuente no está cargada: " + resolvedFontName);
			}
			return standardFont;
		}
		return new Font(baseFont, size);
	}

	private static BaseFont loadFont(final String fontName, final String resourceFileName) throws PdfCaibException {
		final String resourceName = String.format("/fonts/%s", resourceFileName);

		try (InputStream inputStream = FontResourceLoader.class.getResourceAsStream(resourceName)) {
			if (inputStream == null) {
				throw new PdfCaibException("No se encuentra la fuente en el classpath: " + resourceName);
			}
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			final byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			return BaseFont.createFont(resourceFileName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, BaseFont.CACHED,
					outputStream.toByteArray(), null);
		} catch (final IOException | DocumentException e) {
			throw new PdfCaibException("No se puede cargar la fuente: " + resourceName, e);
		}
	}
}