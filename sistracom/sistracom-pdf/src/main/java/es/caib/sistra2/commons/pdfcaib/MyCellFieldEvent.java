package es.caib.sistra2.commons.pdfcaib;

import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

import es.caib.sistra2.commons.pdfcaib.types.TypeFuente;

/**
 * @author Indra
 *
 */
public class MyCellFieldEvent implements PdfPCellEvent {

	private final Logger log = LoggerFactory.getLogger(MyCellFieldEvent.class);

	protected String fieldname;
	protected String value;
	private final Font font;

	public MyCellFieldEvent(final String fieldname, final String value) {
		this(fieldname, value, FontResourceLoader.getFont(TypeFuente.NOTOSANS.toString(), 9));
	}

	public MyCellFieldEvent(final String fieldname, final String value, final Font font) {
		this.fieldname = fieldname;
		this.value = value;
		this.font = font;
	}

	@Override
	public void cellLayout(final PdfPCell cell, final Rectangle rectangle, final PdfContentByte[] canvases) {
		final PdfWriter writer = canvases[0].getPdfWriter();
		final TextField textField = new TextField(writer, rectangle, fieldname + "-" + new Random().nextInt());
		textField.setText(value);
		textField.setAlignment(Element.ALIGN_TOP);
		textField.setOptions(TextField.MULTILINE | TextField.READ_ONLY);
		textField.setFont(font.getBaseFont());
		textField.setFontSize(font.getSize());
		try {
			final PdfFormField field = textField.getTextField();
			writer.addAnnotation(field);
		} catch (final IOException ioe) {
			throw new ExceptionConverter(ioe);
		} catch (final DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}
}
