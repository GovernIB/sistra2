/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.caib.sistra2.commons.pdfcaib.exceptions.CampoCheckException;
import es.caib.sistra2.commons.pdfcaib.exceptions.CampoListaCheckException;
import es.caib.sistra2.commons.pdfcaib.exceptions.CampoTextoException;
import es.caib.sistra2.commons.pdfcaib.exceptions.SeccionException;
import es.caib.sistra2.commons.pdfcaib.exceptions.TablaException;
import es.caib.sistra2.commons.pdfcaib.exceptions.TextoException;
import es.caib.sistra2.commons.pdfcaib.model.CampoCheck;
import es.caib.sistra2.commons.pdfcaib.model.CampoListaCheck;
import es.caib.sistra2.commons.pdfcaib.model.CampoTexto;
import es.caib.sistra2.commons.pdfcaib.model.ColumnaTabla;
import es.caib.sistra2.commons.pdfcaib.model.FilaTabla;
import es.caib.sistra2.commons.pdfcaib.model.FormularioPdf;
import es.caib.sistra2.commons.pdfcaib.model.Linea;
import es.caib.sistra2.commons.pdfcaib.model.LineaObject;
import es.caib.sistra2.commons.pdfcaib.model.PersonalizacionTexto;
import es.caib.sistra2.commons.pdfcaib.model.Seccion;
import es.caib.sistra2.commons.pdfcaib.model.Tabla;
import es.caib.sistra2.commons.pdfcaib.model.Texto;
import es.caib.sistra2.commons.pdfcaib.types.TypeFuente;

/**
 * @author amontoyah at http://www.organizacion.web[ORGANIZACION]
 *
 */
public class GeneradorPdf {

	private final Logger log = LoggerFactory.getLogger(GeneradorPdf.class);

	private final Font TEXTO;
	private final Font SECCION;
	private final Font ETIQUETA;
	private final Font VALOR;
	// private PersonalizacionTexto PERSONALIZACION_TEXTO_CABECERA_DEFAULT;
	private final PersonalizacionTexto PERSONALIZACION_TEXTO_SECCION_DEFAULT;

	private final Document document;
	private PdfWriter writer;
	private final ByteArrayOutputStream arrayBytesPdf;
	private final PdfPTable tablaPDF;
	private final static int COLUMNAS_TABLA = 6;
	private final static int TAMANIO_SECCION = 10;
	private final static int TAMANIO_ETIQUETA = 8;
	private final static int TAMANIO_VALOR = 8;
	private final static int TAMANIO_TEXTO = 8;

	/**
	 * Constructor en el que le añadimos propiedades constantes
	 */
	public GeneradorPdf() {
		super();
		document = new Document(PageSize.A4, 20, 20, 130, 25);
		arrayBytesPdf = new ByteArrayOutputStream();

		tablaPDF = new PdfPTable(COLUMNAS_TABLA);
		tablaPDF.setWidthPercentage(100f);
		tablaPDF.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tablaPDF.setSpacingBefore(200f);
		final URL font_path = Thread.currentThread().getContextClassLoader().getResource("NotoSans-Regular.ttf");
		FontFactory.register(font_path.toString(), "fontNotoSans");
		final URL font_path_bond = Thread.currentThread().getContextClassLoader().getResource("NotoSans-Bold.ttf");
		FontFactory.register(font_path_bond.toString(), "fontNotoSansBold");
		// PERSONALIZACION_TEXTO_CABECERA_DEFAULT = new PersonalizacionTexto(true,
		// false, TypeFuente.NOTOSANSBOND, 16);
		PERSONALIZACION_TEXTO_SECCION_DEFAULT = new PersonalizacionTexto(true, false, TypeFuente.NOTOSANSBOND,
				TAMANIO_SECCION);
		VALOR = FontFactory.getFont("fontNotoSans", TAMANIO_VALOR);
		ETIQUETA = FontFactory.getFont("fontNotoSans", TAMANIO_ETIQUETA);
		ETIQUETA.setStyle(Font.BOLD);
		SECCION = FontFactory.getFont("fontNotoSans", TAMANIO_SECCION);
		TEXTO = FontFactory.getFont("fontNotoSans", TAMANIO_TEXTO);
		try {
			writer = PdfWriter.getInstance(document, arrayBytesPdf);
		} catch (final DocumentException e) {
			log.error("Error creación de writer GeneradorPdf");
		}
	}

	/**
	 * Método en el que completamos la linea
	 */
	public static void completaLinea(final PdfPTable tablaPDF, final int columnasLinea) {
		if (columnasLinea < COLUMNAS_TABLA) {
			tablaPDF.completeRow();
		}
	}

	/**
	 * Método encargado de la escritura del pdf completo, linea a linea comprobando
	 * que objeto hay dentro de cada una.
	 *
	 * @param formularioPdf
	 * @throws Exception
	 *
	 */
	public byte[] generarPdf(final FormularioPdf formularioPdf) throws Exception {

		document.open();
		writer.setPageEvent(new EndPageEvent(formularioPdf.getCabecera(), formularioPdf.getPie(), true, writer));

		if (formularioPdf.getLineas() != null) {
			/** Para cada linea */
			for (final Linea linea : formularioPdf.getLineas()) {
				int columnasLinea = 0;
				/** Para cada objeto dentro de la linea */
				for (final LineaObject objetoLinea : linea.getObjetosLinea()) {
					/** CampoCheck */
					if (objetoLinea instanceof CampoCheck) {
						final CampoCheck campoCheck = (CampoCheck) objetoLinea;
						columnasLinea += campoCheck.getLayoutCols();
						this.writeCampoCheck(campoCheck);
						/** CampoListaCheck */
					} else if (objetoLinea instanceof CampoListaCheck) {
						final CampoListaCheck campoListaCheck = (CampoListaCheck) objetoLinea;
						columnasLinea += campoListaCheck.getLayoutCols();
						this.writeCampoListaCheck(campoListaCheck);
						/** CampoTexto */
					} else if (objetoLinea instanceof CampoTexto) {
						final CampoTexto campoTexto = (CampoTexto) objetoLinea;
						columnasLinea += campoTexto.getLayoutCols();
						this.writeCampoTexto(campoTexto);
						/** Tabla */
					} else if (objetoLinea instanceof Tabla) {
						final Tabla tabla = (Tabla) objetoLinea;
						columnasLinea += tabla.getLayoutCols();
						this.writeTabla(tabla);
						/** Seccion */
					} else if (objetoLinea instanceof Seccion) {
						final Seccion seccion = (Seccion) objetoLinea;
						columnasLinea += COLUMNAS_TABLA;
						this.writeSeccion(seccion);
						/** Texto */
					} else if (objetoLinea instanceof Texto) {
						final Texto texto = (Texto) objetoLinea;
						columnasLinea += texto.getLayoutCols();
						this.writeTexto(texto);
					}
				}
				if (columnasLinea < COLUMNAS_TABLA) {
					completaLinea(tablaPDF, columnasLinea);
				}
				if (columnasLinea > COLUMNAS_TABLA) {
					log.error(
							"Una linea no puede contener mas de 6 columnas, compruebe el layoutCols de los objetos introducidos en la linea. ");
					return "".getBytes();
				}
			}
		}

		document.add(tablaPDF);

		document.close();

		return arrayBytesPdf.toByteArray();

	}

	/**
	 * Método encargado de la escritura de una sección
	 *
	 * @param seccion
	 * @throws SeccionException
	 */
	private void writeSeccion(final Seccion seccion) throws SeccionException {

		final PdfPTable header = new PdfPTable(2);

		// set defaults
		try {
			Paragraph paragraph;
			header.setWidths(new int[] { 1, 24 });

			/** añadimos titulo */
			final PdfPCell text2 = new PdfPCell();
			text2.setPaddingBottom(4);
			text2.setPaddingLeft(5);
			text2.setColspan(5);
			text2.setBorder(Rectangle.NO_BORDER);

			final Font fuenteCabecera = getFontByPersonalizacionTexto(PERSONALIZACION_TEXTO_SECCION_DEFAULT);
			final Color myColor = new Color(195, 0, 69);
			fuenteCabecera.setColor(myColor);
			paragraph = new Paragraph(seccion.getTitulo(), fuenteCabecera);

			text2.addElement(paragraph);
			header.addCell(text2);

			final PdfPCell cell = new PdfPCell(header);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(COLUMNAS_TABLA);
			tablaPDF.addCell(cell);

		} catch (final DocumentException e) {
			log.error("Error al introducir el ancho de la sección");
			throw new SeccionException("Error al introducir el ancho de la sección", e);
		}

	}

	/**
	 * Método encargado de la escritura de una tabla
	 *
	 * @param tabla
	 * @param tablaPdf
	 * @throws TablaException
	 */
	private void writeTabla(final Tabla tabla) throws TablaException {
		try {
			final int numeroColumnas = tabla.getCabecera().getColumnaTablas().size();
			final PdfPTable table = new PdfPTable(numeroColumnas);
			PdfPCell cell;
			// escribe titulo
			final Paragraph titulo = new Paragraph(tabla.getTituloTabla(), VALOR);

			cell = new PdfPCell();
			cell.setColspan(numeroColumnas);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingBottom(5f);
			cell.addElement(titulo);
			table.addCell(cell);
			// añadimos las cabeceras
			for (final ColumnaTabla columnaTabla : tabla.getCabecera().getColumnaTablas()) {
				cell = new PdfPCell(new Paragraph(columnaTabla.getTexto(), SECCION));
				cell.setBackgroundColor(Color.LIGHT_GRAY);
				table.addCell(cell);
			}
			// añadimos los campos
			for (final FilaTabla filaTabla : tabla.getFilaTablas()) {
				for (final String textoFila : filaTabla.getTextoFila()) {
					cell = new PdfPCell(new Paragraph(textoFila, ETIQUETA));
					// cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(1);
					table.addCell(cell);
				}
			}

			final PdfPCell cellTable = new PdfPCell(table);
			// cellTable.setBorder(Rectangle.NO_BORDER);
			cellTable.setColspan(tabla.getLayoutCols());

			cellTable.setPadding(10f);
			tablaPDF.addCell(cellTable);
		} catch (final Exception e) {
			throw new TablaException("Error en la creacion de la tabla", e);
		}

	}

	/**
	 * Método encargado de la escritura de un campo texto
	 *
	 * @param campoTexto
	 * @param tablaLinea
	 * @throws CampoTextoException
	 */
	private void writeCampoTexto(final CampoTexto campoTexto) throws CampoTextoException {

		try {
			final PdfPTable table = new PdfPTable(2);
			table.setWidths(new int[] { 100, 200 });
			// añado etiqueta del texto
			PdfPCell cell;
			cell = new PdfPCell(new Paragraph(campoTexto.getEtiqueta(), ETIQUETA));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setBackgroundColor(Color.WHITE);

			table.addCell(cell);

			cell = new PdfPCell();

			if (campoTexto.isMultiLinea()) {
				// si es multilinea, creamos un paragraph
				cell = new PdfPCell(new Paragraph(campoTexto.getTexto(), TEXTO));
			} else {
				// Si no es multilinea, creamos un textfield
				cell.setCellEvent(new MyCellFieldEvent(campoTexto.getEtiqueta(), campoTexto.getTexto()));
				cell.setPaddingBottom(5f);
				cell.setRowspan(1);
				cell.setPaddingTop(8);
			}
			// cell.setBorder(Rectangle.NO_BORDER);
			cell.setRowspan(1);
			table.addCell(cell);

			/** añadir todo el contenido a la celda padre y lo añade a la tabla global */
			PdfPCell c2;
			c2 = new PdfPCell(table);
			c2.setBorder(Rectangle.BOX);

			c2.setColspan(campoTexto.getLayoutCols());
			tablaPDF.addCell(c2);
		} catch (final Exception e) {
			throw new CampoTextoException("Error en la creacion de campo texto", e);
		}

	}

	/**
	 * Método encargado de la escritura de un campo lista check
	 *
	 * @param campoListaCheck
	 * @param tablaPdf
	 * @throws CampoListaCheckException
	 */
	private void writeCampoListaCheck(final CampoListaCheck campoListaCheck) throws CampoListaCheckException {
		try {
			PdfPCell cell;
			final PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100f);

			cell = new PdfPCell(new Phrase(campoListaCheck.getEtiqueta(), ETIQUETA));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);

			// añadimos los campos check con su etiqueta
			for (final CampoCheck check : campoListaCheck.getOpciones()) {
				// añadimos campo check
				cell = new PdfPCell();
				cell.setCellEvent(new CheckboxCellEvent(check.getEtiqueta(), check.isChecked()));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);

				// añadimos etiqueta del check
				cell = new PdfPCell(new Paragraph(check.getEtiqueta(), VALOR));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setPaddingLeft(16);
				cell.setPaddingTop(0);
				cell.setPaddingBottom(5);
				table.addCell(cell);

			}

			// añadir todo el contenido a la celda padre y lo añade a la tabla global
			PdfPCell c2;
			c2 = new PdfPCell(table);
			c2.setBorder(Rectangle.BOX);
			c2.setColspan(campoListaCheck.getLayoutCols());
			c2.setPadding(0);
			tablaPDF.addCell(c2);
		} catch (final Exception e) {
			throw new CampoListaCheckException("Error en el campo lista check", e);
		}
	}

	/**
	 * Método encargado de la escritura de un campo check
	 *
	 * @param campoCheck
	 * @param tablaLinea
	 * @param tablaPdf
	 * @throws CampoCheckException
	 */
	private void writeCampoCheck(final CampoCheck campoCheck) throws CampoCheckException {
		try {
			final PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100f);
			PdfPCell cell;

			// añadimos campo check
			cell = new PdfPCell();
			cell.setCellEvent(new CheckboxCellEvent(campoCheck.getEtiqueta(), campoCheck.isChecked()));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);

			// añadimos etiqueta del check
			cell = new PdfPCell(new Paragraph(campoCheck.getEtiqueta(), VALOR));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setPaddingLeft(16);
			cell.setPaddingTop(0);
			cell.setBackgroundColor(Color.WHITE);
			table.addCell(cell);

			// añadir todo el contenido a la celda padre y lo añade a la tabla global
			PdfPCell c2;
			c2 = new PdfPCell(table);
			c2.setBorder(Rectangle.BOX);
			c2.setColspan(campoCheck.getLayoutCols());
			c2.setPadding(0);

			tablaPDF.addCell(c2);
		} catch (final Exception e) {
			throw new CampoCheckException("Error en la creacion del campo check", e);
		}

	}

	/**
	 * Método encargado de la escritura de un campo texto
	 *
	 * @param texto
	 * @throws TextoException
	 */
	public void writeTexto(final Texto texto) throws TextoException {
		try {
			final Font f = getFontByPersonalizacionTexto(texto.getPersonalizacionTexto());

			// añade bloque texto
			final Paragraph bloque = new Paragraph(new Chunk(texto.getTexto(), f));
			final PdfPCell cell = new PdfPCell(bloque);
			cell.setBorder(Rectangle.BOX);

			cell.setColspan(texto.getLayoutCols());

			// se añade a la tabla principal
			tablaPDF.addCell(cell);
		} catch (final Exception e) {
			throw new TextoException("Error en la creacion del campo texto", e);
		}
	}

	/**
	 * Método encargado de la personalizacion del texto
	 *
	 * @param campoCheck
	 * @param tablaLinea
	 * @param tablaPdf
	 * @throws CampoCheckException
	 */
	public Font getFontByPersonalizacionTexto(final PersonalizacionTexto personalizacionTexto) {
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

}
