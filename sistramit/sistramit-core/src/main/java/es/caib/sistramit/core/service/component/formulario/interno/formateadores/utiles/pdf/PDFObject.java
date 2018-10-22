package es.caib.sistramit.core.service.component.formulario.interno.formateadores.utiles.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;


public interface PDFObject {
	
	abstract void write(PDFDocument document, PdfPTable tabla) throws DocumentException;

}
