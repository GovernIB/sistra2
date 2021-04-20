/**
 * Copyright (c) 2021 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib;

import java.awt.Color;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseField;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RadioCheckField;

/**
 * @author Indra
 *
 */
public class CheckboxCellEvent implements PdfPCellEvent {

	private final Logger log = LoggerFactory.getLogger(CheckboxCellEvent.class);

	// The name of the check box field
	protected String name;
	protected Boolean cheked;

	// We create a cell event
	public CheckboxCellEvent(final String name, final Boolean cheked) {
		this.name = name;
		this.cheked = cheked;
	}

	// We create and add the check box field
	@Override
	public void cellLayout(final PdfPCell cell, final Rectangle position, final PdfContentByte[] canvases) {
		final PdfWriter writer = canvases[0].getPdfWriter();

		// define the coordinates of the middle
		final float x = position.getLeft() + 8;
		final float y = (position.getTop() + position.getBottom()) / 2 - 8;

		// define the position of a check box that measures 20 by 20
		final Rectangle rect = new Rectangle(x - 5, y - 5, x + 6, y + 6);

		// define the check box
		final RadioCheckField checkbox = new RadioCheckField(writer, rect, name, "Yes");
		checkbox.setCheckType(RadioCheckField.TYPE_CHECK);
		checkbox.setBorderWidth(BaseField.BORDER_WIDTH_THIN);

		checkbox.setBorderColor(Color.BLACK);
		checkbox.setBackgroundColor(Color.WHITE);
		checkbox.setOptions(RadioCheckField.READ_ONLY);
		checkbox.setChecked(cheked);

		try {
			final PdfFormField myField = checkbox.getCheckField();

			writer.addAnnotation(myField);
		} catch (final IOException ioe) {
			log.error("Error al crear checkbox: " + name);
			throw new ExceptionConverter(ioe);
		} catch (final DocumentException de) {
			log.error("Error al crear checkbox: " + name);
			throw new ExceptionConverter(de);
		}
	}
}