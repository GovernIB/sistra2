/**
 * Copyright (c) 2021 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
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

	/**
	 * Template para la paginacion
	 */
	protected PdfTemplate tplTotal;

	public EndPageEvent(final Cabecera cabecera, final Pie pie, final boolean paginar, final PdfWriter writer) {
		super();
		this.cabecera = cabecera;
		this.pie = pie;
		this.writer = writer;
		tplTotal = writer.getDirectContent().createTemplate(100, 100);
		this.paginar = paginar;

		final URL font_path = Thread.currentThread().getContextClassLoader().getResource("NotoSans-Regular.ttf");
		FontFactory.register(font_path.toString(), "fontNotoSans");
		PERSONALIZACION_TEXTO_CABECERA_DEFAULT = new PersonalizacionTexto(true, false, TypeFuente.NOTOSANS, 16);
		PERSONALIZACION_TEXTO_SECCION_DEFAULT = new PersonalizacionTexto(true, false, TypeFuente.NOTOSANS, 16);
		VALOR = FontFactory.getFont("fontNotoSans", 9);
		ETIQUETA = FontFactory.getFont("fontNotoSans", 10);
		SECCION = FontFactory.getFont("fontNotoSans", 9);
		TEXTO = FontFactory.getFont("fontNotoSans", 9);

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
		if (paginar) {
			onFinPagina2(writer, document);
		} else {
			final Rectangle page = document.getPageSize();

			if (cabecera != null) {
				final PdfPTable head = new PdfPTable(2);
				try {
					// set defaults
					head.setWidths(new int[] { 2, 24 });
					head.setTotalWidth(527);
					head.getDefaultCell().setFixedHeight(40);
					head.getDefaultCell().setBorder(Rectangle.BOTTOM);

					// add text
					final PdfPCell text = new PdfPCell();
					text.setPaddingBottom(15);
					text.setPaddingLeft(10);
					text.setBorder(Rectangle.BOTTOM);

					// add image
					if (cabecera.getLogo() != null) {
						final Image logo = Image.getInstance(cabecera.getLogo());
						head.addCell(logo);
					} else if (cabecera.getLogoByte() != null) {
						final Image logo = Image.getInstance(cabecera.getLogoByte());
						head.addCell(logo);

					} else {
						head.addCell(text);
					}

					Paragraph titulo = new Paragraph(cabecera.getTitulo(), SECCION);
					if (cabecera.getPersonalizacionTextoTitulo() != null) {
						titulo = new Paragraph(cabecera.getTitulo(),
								getFontByPersonalizacionTexto(cabecera.getPersonalizacionTextoTitulo()));
					}
					titulo.setAlignment(Element.ALIGN_CENTER);
					text.addElement(titulo);

					Paragraph subtitulo = new Paragraph(cabecera.getSubtitulo(), ETIQUETA);
					if (cabecera.getPersonalizacionTextoSubtitulo() != null) {
						subtitulo = new Paragraph(cabecera.getSubtitulo(),
								getFontByPersonalizacionTexto(cabecera.getPersonalizacionTextoSubtitulo()));
					}
					subtitulo.setAlignment(Element.ALIGN_CENTER);
					text.addElement(subtitulo);

					head.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
					head.writeSelectedRows(0, -1, document.leftMargin(),
							page.getHeight() - document.topMargin() + head.getTotalHeight(), writer.getDirectContent());

				} catch (final DocumentException de) {
					log.error("Error en la cabecera del documento pdf");
					throw new ExceptionConverter(de);
				} catch (final MalformedURLException e) {
					log.error("Error en la cabecera del documento pdf, imagen no encontrada");
					throw new ExceptionConverter(e);
				} catch (final IOException ioe) {
					log.error("Error en la cabecera del documento pdf");
					throw new ExceptionConverter(ioe);
				}

			}

			// Texto de pie y código de barras

			if (pie != null) {
				final float[] widths = { 4f, 1f };

				final PdfPTable foot = new PdfPTable(widths);
				foot.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				foot.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

				final PdfPCell cell = new PdfPCell(
						new Phrase(16, "", getFontByPersonalizacionTexto(pie.getPersonalizacionTexto())));
				cell.setMinimumHeight(15f);
				cell.setBorder(0);
				foot.addCell(cell);
				foot.addCell(cell);

				final Paragraph bloque = new Paragraph(
						new Chunk(pie.getTexto(), getFontByPersonalizacionTexto(pie.getPersonalizacionTexto())));
				foot.addCell(bloque);
				foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
				foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
						writer.getDirectContent());
			}
		}
	}

	private void onFinPagina2(final PdfWriter writer, final Document document) {
		final Rectangle page = document.getPageSize();
		final PdfContentByte cb = writer.getDirectContent();
		final Color myColor = new Color(195, 0, 69);
		if (cabecera != null) {
			final PdfPTable head = new PdfPTable(2);
			try {
				// set defaults
				head.setWidths(new int[] { 6, 20 });
				head.setTotalWidth(700);
				head.setLockedWidth(true);
				// head.getDefaultCell().setFixedHeight(50);
				head.getDefaultCell().setBorder(Rectangle.BOX);

				// add text
				final PdfPCell text = new PdfPCell();
				text.setBorder(Rectangle.BOX);

				// add image
				if (cabecera.getLogo() != null) {
					final Image logo = Image.getInstance(cabecera.getLogo());
					final PdfPCell image = new PdfPCell();
					image.addElement(logo);
					image.setFixedHeight(90);
					head.addCell(image);
				} else if (cabecera.getLogoByte() != null) {

					final Image logo = Image.getInstance(cabecera.getLogoByte());
					System.out.println(logo.getHeight() + " + " + logo.getWidth());
					logo.scaleToFit(120, 80);
					logo.setAlignment(30);
					System.out.println(logo.getHeight() + " + " + logo.getWidth());
					final PdfPCell image = new PdfPCell();
					image.addElement(logo);
					image.setFixedHeight(90);
					image.setHorizontalAlignment(1000);
					head.addCell(image);

				} else {
					head.addCell(text);
				}

				Paragraph titulo = new Paragraph(cabecera.getTitulo(), SECCION);
				if (cabecera.getPersonalizacionTextoTitulo() != null) {
					titulo = new Paragraph(cabecera.getTitulo(),
							getFontByPersonalizacionTexto(cabecera.getPersonalizacionTextoTitulo()));
				} else {
					final Font fuenteCabecera = getFontByPersonalizacionTexto(PERSONALIZACION_TEXTO_CABECERA_DEFAULT);

					fuenteCabecera.setColor(myColor);
					titulo = new Paragraph(cabecera.getTitulo(), fuenteCabecera);
				}
				titulo.setAlignment(Element.ALIGN_CENTER);
				text.addElement(titulo);

				Paragraph subtitulo = new Paragraph(cabecera.getSubtitulo(), ETIQUETA);

				if (cabecera.getPersonalizacionTextoSubtitulo() != null) {
					final Font fuenteCabecera = getFontByPersonalizacionTexto(PERSONALIZACION_TEXTO_CABECERA_DEFAULT);
					fuenteCabecera.setColor(myColor);
					subtitulo = new Paragraph(cabecera.getSubtitulo(), fuenteCabecera);
				} else {
					final Font fuenteCabecera = getFontByPersonalizacionTexto(PERSONALIZACION_TEXTO_CABECERA_DEFAULT);
					fuenteCabecera.setColor(myColor);
					subtitulo = new Paragraph(cabecera.getSubtitulo(), fuenteCabecera);
				}
				subtitulo.setAlignment(Element.ALIGN_CENTER);
				text.addElement(subtitulo);

				head.addCell(text);
				head.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
				head.writeSelectedRows(0, -1, document.leftMargin(),
						page.getHeight() - document.topMargin() + head.getTotalHeight(), writer.getDirectContent());

			} catch (final DocumentException de) {
				log.error("Error en la cabecera del documento pdf");
				throw new ExceptionConverter(de);

			} catch (final Exception e) {
				log.error("Error en la cabecera del documento pdf");
				throw new ExceptionConverter(e);

			}

		}

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