package es.caib.sistra2.commons.pdfcaib;

import java.awt.Color;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import es.caib.sistra2.commons.pdfcaib.model.Cabecera;
import es.caib.sistra2.commons.pdfcaib.model.PersonalizacionTexto;
import es.caib.sistra2.commons.pdfcaib.model.Pie;
import es.caib.sistra2.commons.pdfcaib.types.TypeFuente;

/**
 * @author Indra
 *
 */
/**
 * Evento para cabecera y pie de páginas
 */
public class EndPageEvent extends PdfPageEventHelper {

	private final Logger log = LoggerFactory.getLogger(EndPageEvent.class);

	private Cabecera cabecera = null;
	private Pie pie = null;
	private boolean paginar = false;
	private final PdfWriter writer;

	protected static Font TEXTO;
	protected static Font SECCION;
	protected static Font ETIQUETA;
	protected static Font VALOR;
	protected static PersonalizacionTexto PERSONALIZACION_TEXTO_CABECERA_DEFAULT;
	protected static PersonalizacionTexto PERSONALIZACION_TEXTO_SECCION_DEFAULT;
	protected static PersonalizacionTexto PERSONALIZACION_TEXTO_ETIQUETA_DEFAULT;
	protected float tableHeight;
	/**
	 * Template para la paginacion
	 */
	protected PdfTemplate tplTotal;

	public EndPageEvent(final Document document, final Cabecera cabecera, final Pie pie, final boolean paginar,
			final PdfWriter writer) {
		super();
		this.cabecera = cabecera;
		this.pie = pie;
		this.writer = writer;
		tplTotal = writer.getDirectContent().createTemplate(100, 100);
		this.paginar = paginar;

		PERSONALIZACION_TEXTO_CABECERA_DEFAULT = new PersonalizacionTexto(true, false, TypeFuente.NOTOSANS, 16);
		PERSONALIZACION_TEXTO_SECCION_DEFAULT = new PersonalizacionTexto(true, false, TypeFuente.NOTOSANS, 16);
		PERSONALIZACION_TEXTO_ETIQUETA_DEFAULT = new PersonalizacionTexto(true, false, TypeFuente.NOTOSANS, 10);
		VALOR = FontFactory.getFont(TypeFuente.NOTOSANS.toString(), 9);
		ETIQUETA = FontFactory.getFont(TypeFuente.NOTOSANS.toString(), 10);
		SECCION = FontFactory.getFont(TypeFuente.NOTOSANS.toString(), 9);
		TEXTO = FontFactory.getFont(TypeFuente.NOTOSANS.toString(), 9);

	}

	public static Font getFontByPersonalizacionTexto(final PersonalizacionTexto personalizacionTexto) {
		Font f = TEXTO;

		if (personalizacionTexto != null) {
			// FUENTE
			if (personalizacionTexto.getFuente() != null) {
				f = FontFactory.getFont(personalizacionTexto.getFuente().toString());
			}
			// NEGRITA Y CURSIVA
			if (personalizacionTexto.isNegrita() && personalizacionTexto.isCursiva()) {
				f.setStyle(Font.BOLD | Font.ITALIC);
			} else if (personalizacionTexto.isNegrita()) {
				f.setStyle(Font.BOLD);
			} else if (personalizacionTexto.isCursiva()) {
				f.setStyle(Font.ITALIC);
			}
			// TAMAÑO
			if (personalizacionTexto.getTamanio() != 0) {
				f.setSize(personalizacionTexto.getTamanio());
			}
		}
		return f;
	}

	@Override
	public void onEndPage(final PdfWriter writer, final Document document) {
		EndPagePagination(writer, document);

	}

	private void EndPagePagination(final PdfWriter writer, final Document document) {

		final PdfContentByte cb = writer.getDirectContent();
		// Texto de pie y código de barras

		if (pie != null) {
			/** VRS: cambio para añadir paginacion */
			cb.saveState();

			Font f = TEXTO;
			if (pie.getPersonalizacionTexto() != null) {
				f = getFontByPersonalizacionTexto(pie.getPersonalizacionTexto());
			}

			final String text = pie.getTexto() + "  -  Pág. " + writer.getPageNumber() + " de ";
			final float textBase = document.bottom() - 20;
			final float textSize = f.getBaseFont().getWidthPoint(text, f.getSize());

			cb.restoreState();
			cb.saveState();
			cb.beginText();

			cb.setFontAndSize(f.getBaseFont(), f.getSize());

			float adjust;
			try {
				adjust = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED).getWidthPoint("0",
						f.getSize());
				cb.setTextMatrix(document.right() - textSize - adjust, textBase);
			} catch (final DocumentException e) {
				log.error("Error en la recuperacion del fichero de creacion del fuente del pie de pagina");
				throw new ExceptionConverter(e);
			} catch (final IOException e) {
				log.error("Error en la recuperacion del fichero de creacion del fuente del pie de pagina");
				throw new ExceptionConverter(e);
			}
			cb.setTextMatrix(document.right() - textSize - adjust, textBase);
			cb.setColorFill(Color.GRAY);

			cb.showText(text);
			cb.endText();
			cb.addTemplate(tplTotal, document.right() - adjust, textBase);

			cb.restoreState();
		}
	}

	@Override
	public void onOpenDocument(final PdfWriter writer, final Document document) {
		if (paginar) {
			tplTotal = writer.getDirectContent().createTemplate(100, 100);
			tplTotal.setBoundingBox(new Rectangle(-20, -20, 100, 100));
		}
	}

	@Override
	public void onCloseDocument(final PdfWriter writer, final Document document) {
		if (paginar) {
			tplTotal.beginText();

			Font f = TEXTO;
			if (pie != null && pie.getPersonalizacionTexto() != null) {
				f = getFontByPersonalizacionTexto(pie.getPersonalizacionTexto());
			}

			tplTotal.setFontAndSize(f.getBaseFont(), f.getSize());
			tplTotal.setColorFill(Color.GRAY);
			tplTotal.setTextMatrix(0, 0);
			tplTotal.showText(String.valueOf(writer.getPageNumber() - 1));
			tplTotal.endText();
		}
	}

}