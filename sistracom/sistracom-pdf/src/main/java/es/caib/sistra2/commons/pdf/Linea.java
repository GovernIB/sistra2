package es.caib.sistra2.commons.pdf;

import java.util.List;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * Linea
 *
 * @author Indra
 *
 */
public class Linea implements PDFObject {

	/** Componentes. **/
	private List<LineaComponente> componentes;

	/** Pintar bordes. **/
	private boolean pintarBordes = true;

	/** Pintar bordes. **/
	private boolean pintarPadding = false;

	/**
	 * Paddings de 4 elementos, sabiendo que: *
	 * <ul>
	 * <li>[0] = top</li>
	 * <li>[1] = bottom</li>
	 * <li>[2] = left</li>
	 * <li>[3] = right</li>
	 * </ul>
	 *
	 **/
	private Float[] paddings = new Float[4];

	/**
	 * Constructor.
	 *
	 * @param url
	 *
	 */
	public Linea(final List<LineaComponente> componentes) {
		super();
		this.setComponentes(componentes);
	}

	@Override
	public void write(final PDFDocument document, final PdfPTable tabla) throws DocumentException {

		final PdfPCell fila = new PdfPCell();
		fila.setColspan(2);
		fila.setPaddingLeft(30f);
		if (pintarBordes) {
			fila.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
		} else {
			fila.setBorder(Rectangle.NO_BORDER);
		}
		padding(fila);

		final Phrase phrase = new Phrase();
		for (final LineaComponente componente : componentes) {
			phrase.add(componente.getPhrase());
		}
		fila.addElement(phrase);
		tabla.addCell(fila);

	}

	/**
	 * Pone el padding, sabiendo que:
	 * <ul>
	 * <li>[0] = top</li>
	 * <li>[1] = bottom</li>
	 * <li>[2] = left</li>
	 * <li>[3] = right</li>
	 * </ul>
	 */
	private void padding(final PdfPCell fila) {
		if (paddings != null && paddings.length == 4) {
			if (paddings[0] != null) {
				fila.setPaddingTop(paddings[0]);
			}
			if (paddings[1] != null) {
				fila.setPaddingBottom(paddings[1]);
			}
			if (paddings[2] != null) {
				fila.setPaddingLeft(paddings[2]);
			}
			if (paddings[3] != null) {
				fila.setPaddingRight(paddings[3]);
			}
		}
	}

	/**
	 * @return the pintarBordes
	 */
	public boolean isPintarBordes() {
		return pintarBordes;
	}

	/**
	 * @param pintarBordes
	 *            the pintarBordes to set
	 */
	public void setPintarBordes(final boolean pintarBordes) {
		this.pintarBordes = pintarBordes;
	}

	public boolean isPintarPadding() {
		return pintarPadding;
	}

	public void setPintarPadding(final boolean pintarPadding) {
		this.pintarPadding = pintarPadding;
	}

	/**
	 * @return the componentes
	 */
	public List<LineaComponente> getComponentes() {
		return componentes;
	}

	/**
	 * @param componentes
	 *            the componentes to set
	 */
	public void setComponentes(final List<LineaComponente> componentes) {
		this.componentes = componentes;
	}

	/**
	 * @return the paddings
	 */
	public Float[] getPaddings() {
		return paddings;
	}

	/**
	 * Set padding top.
	 *
	 * @param padding
	 */
	public void setPaddingTop(final Float padding) {
		this.paddings[0] = padding;
	}

	/**
	 * Set padding bottom.
	 *
	 * @param padding
	 *
	 */
	public void setPaddingBottom(final Float padding) {
		this.paddings[1] = padding;
	}

	/**
	 * Set padding left.
	 *
	 * @param padding
	 *
	 */
	public void setPaddingLeft(final Float padding) {
		this.paddings[2] = padding;
	}

	/**
	 * Set padding right.
	 *
	 * @param padding
	 *
	 */
	public void setPaddingRight(final Float padding) {
		this.paddings[3] = padding;
	}

	/**
	 * @param paddings
	 *            the paddings to set
	 */
	public void setPaddings(final Float[] paddings) {
		this.paddings = paddings;
	}

}
