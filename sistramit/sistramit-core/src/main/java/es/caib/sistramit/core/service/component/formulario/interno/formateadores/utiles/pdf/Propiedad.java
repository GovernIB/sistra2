package es.caib.sistramit.core.service.component.formulario.interno.formateadores.utiles.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class Propiedad implements PDFObject {

	private String campo;

	private String value;

	private float padding;

	float[] widths = null;

	private final String _fuenteatributo; // fuente que se aplica a la parte atributo
	private final String _fuentevalor; // fuente que se aplica a la parte atributo

	public Propiedad(final String campo, final String value, final float[] widths) {
		super();
		this.campo = campo;
		this.value = value;
		this.padding = 30f;
		this.widths = widths;
		_fuenteatributo = "Titulo2";
		_fuentevalor = null;
	}

	public Propiedad(final String campo, final String value, final float padding) {
		super();
		this.campo = campo;
		this.value = value;
		this.padding = padding;
		_fuenteatributo = "Titulo2";
		_fuentevalor = null;
	}

	public Propiedad(final String campo, final String value) {
		super();
		this.campo = campo;
		this.value = value;
		this.padding = 30f;
		_fuenteatributo = "Titulo2";
		_fuentevalor = null;
	}

	/**
	 * Constructor que se le pasa tambien la fuente que se quiere aplicar
	 * 
	 * @param campo
	 * @param value
	 * @param fuenteatributo
	 * @param fuentevalor
	 */
	public Propiedad(final String campo, final String value, final String fuenteatributo, final String fuentevalor) {
		super();
		this.campo = campo;
		this.value = value;
		this.padding = 30f;
		_fuenteatributo = fuenteatributo;
		_fuentevalor = fuentevalor;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(final String campo) {
		this.campo = campo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public void write(final PDFDocument document, final PdfPTable tabla) throws DocumentException {

		if (this.widths == null) {
			widths = new float[2];
			widths[0] = 40f;
			widths[1] = 60f;
		}
		final PdfPTable innerTable = new PdfPTable(widths);
		String texto = this.campo;
		if (value == null) {
			value = "";
		}
		if (!value.equals("")) {
			texto += ":";
		}
		PdfPCell cell = new PdfPCell(new Phrase(10, texto, document.getContext().getFont(_fuenteatributo)));
		cell.setBorder(Rectangle.NO_BORDER);
		innerTable.addCell(cell);
		final Font fuentevalor = (_fuentevalor == null) ? document.getContext().getDefaultFont()
				: document.getContext().getFont(_fuentevalor);
		cell = new PdfPCell(new Phrase(10, value, fuentevalor));
		cell.setBorder(Rectangle.NO_BORDER);
		innerTable.addCell(cell);
		cell = new PdfPCell(innerTable);
		cell.setPaddingLeft(this.padding);
		cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
		cell.setColspan(2);
		tabla.addCell(cell);

	}

	public float getPadding() {
		return padding;
	}

	public void setPadding(final float padding) {
		this.padding = padding;
	}

}
