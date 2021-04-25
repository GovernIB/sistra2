package es.caib.sistra2.commons.pdfcaib;

/**
 * Excepcion al generar PDF.
 *
 * @author Indra
 *
 */
public class PdfCaibException extends Exception {

	/**
	 * Constructor.
	 *
	 * @param message
	 *                    Mensaje
	 * @param cause
	 *                    Causa
	 */
	public PdfCaibException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *                    Mensaje
	 */
	public PdfCaibException(final String message) {
		super(message);
	}

}
