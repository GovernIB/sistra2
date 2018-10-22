package es.caib.sistramit.core.service.component.formulario.interno.formateadores.utiles.pdf;

import com.lowagie.text.pdf.BaseFont;

/**
 *
 * Texto para realizar stamp de un barcode en un pdf
 *
 */
public class BarcodeStamp extends ObjectStamp {

	public static final int BARCODE_128 = 0;
	public static final int BARCODE_PDF417 = 1;

	/**
	 * Tipo de barcode
	 */
	private int tipo = BARCODE_128;

	/**
	 * Fuente
	 */
	private String fontName = BaseFont.HELVETICA;
	/**
	 * Tamanyo fuente
	 */
	private int fontSize = 14;

	/**
	 * Texto a mostrar
	 */
	private String texto;
	/**
	 * Escala X
	 */
	private Float xScale = null;

	/**
	 * Escala Y
	 */
	private Float yScale = null;

	public String getTexto() {
		return texto;
	}

	public void setTexto(final String texto) {
		this.texto = texto;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(final int tipo) {
		this.tipo = tipo;
	}

	public Float getXScale() {
		return xScale;
	}

	public void setXScale(final Float scale) {
		xScale = scale;
	}

	public Float getYScale() {
		return yScale;
	}

	public void setYScale(final Float scale) {
		yScale = scale;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(final String fontName) {
		this.fontName = fontName;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(final int fontSize) {
		this.fontSize = fontSize;
	}

}
