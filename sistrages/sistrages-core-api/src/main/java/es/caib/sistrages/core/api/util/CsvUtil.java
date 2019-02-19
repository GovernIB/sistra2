package es.caib.sistrages.core.api.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;

// TODO V0 Sacar a lib comun

/**
 * Utilidades para importar/exportar a/de CSV.
 *
 * @author Indra
 *
 */
public class CsvUtil {

	/** Constructor. **/
	private CsvUtil() {
		// Constructor vacio.
	}

	private static final char CHAR_DELIMITIER = ';';

	/**
	 * Devuelve un csv documento a partir de una fuente de datos y su fuente de
	 * datos valores.
	 *
	 * @return
	 * @throws Exception
	 */
	public static CsvDocumento getCsvDocumento(final FuenteDatos fuenteDatos,
			final FuenteDatosValores fuenteDatosValores) throws Exception {
		final CsvDocumento csvDocumento = new CsvDocumento();
		final String[] vcampos = new String[fuenteDatos.getCampos().size()];
		int i = 0;
		for (final java.util.Iterator<FuenteDatosCampo> it = fuenteDatos.getCampos().iterator(); it.hasNext();) {
			final FuenteDatosCampo cfd = it.next();
			vcampos[i] = cfd.getIdentificador();
			i++;
		}
		csvDocumento.setColumnas(vcampos);

		// Ponemos las filas
		for (final FuenteFila ffd : fuenteDatosValores.getFilas()) {
			final int numFilaCsv = csvDocumento.addFila();
			for (int columna = 0; columna < vcampos.length; columna++) {
				final String vfd = ffd.getValorFuenteDatos(vcampos[columna]);
				csvDocumento.setValor(numFilaCsv, vcampos[columna], vfd);
			}
		}

		return csvDocumento;
	}

	/**
	 * Importa csv.
	 *
	 * @param is
	 *            InputStream
	 * @throws Exception
	 */
	public static CsvDocumento importar(final InputStream is) throws Exception {

		final CsvDocumento csvDoc = new CsvDocumento();

		final Reader r = new InputStreamReader(is, "ISO-8859-1");
		final CsvReader products = new CsvReader(r, CHAR_DELIMITIER);

		products.readHeaders();

		final String[] headers = products.getHeaders();
		csvDoc.setColumnas(headers);

		while (products.readRecord()) {
			final int fila = csvDoc.addFila();
			for (int i = 0; i < headers.length; i++) {
				final String columna = headers[i];
				final String valor = products.get(columna);
				csvDoc.setValor(fila, columna, valor);
			}
		}

		products.close();

		return csvDoc;

	}

	/**
	 * Exporta a csv.
	 *
	 * @param is
	 *            InputStream
	 * @throws Exception
	 */
	public static byte[] exportar(final CsvDocumento csv) throws Exception {

		final ByteArrayOutputStream bos = new ByteArrayOutputStream(8192);
		final Writer writer = new OutputStreamWriter(bos, "ISO-8859-1");
		final CsvWriter csvOutput = new CsvWriter(writer, CHAR_DELIMITIER);

		// Headers
		for (int columna = 0; columna < csv.getColumnas().length; columna++) {
			csvOutput.write(csv.getColumnas()[columna]);
		}
		csvOutput.endRecord();

		// Datos
		for (int fila = 0; fila < csv.getNumeroFilas(); fila++) {
			for (int columna = 0; columna < csv.getColumnas().length; columna++) {
				final String col = csv.getColumnas()[columna];
				csvOutput.write(csv.getValor(fila, col));
			}
			csvOutput.endRecord();
		}

		csvOutput.close();

		return bos.toByteArray();

	}

}
