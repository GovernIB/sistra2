package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.pdf.PdfReader;

import es.caib.sistramit.core.service.model.script.flujo.PlgValidacionAnexoInt;

/**
 * Plugin de acceso a datos de formularios.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgValidacionAnexo implements PlgValidacionAnexoInt {

	/**
	 * Nombre fichero anexo.
	 */
	private final String nombreFicheroAnexo;

	/**
	 * Datos fichero anexo.
	 */
	private final byte[] datosFicheroAnexo;

	/**
	 * Datos del pdf anexo.
	 */
	private Map<String, String> datosPDFAnexo;

	/**
	 * Documento dom4j del xml anexo.
	 */
	private Document documentoXML;

	/**
	 * Log.
	 */
	private static Logger log = LoggerFactory.getLogger(PlgValidacionAnexo.class);

	/**
	 * Constructor.
	 *
	 * @param nombreFicheroAnexo
	 *            Nombre fichero anexo.
	 * @param datosFicheroAnexo
	 *            Datos fichero anexo.
	 */
	public PlgValidacionAnexo(final String nombreFicheroAnexo, final byte[] datosFicheroAnexo) {
		super();
		this.nombreFicheroAnexo = nombreFicheroAnexo;
		this.datosFicheroAnexo = datosFicheroAnexo;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public String getValorPdf(final String codigo) {
		String respuesta = "";

		// Leemos PDF si no se ha leído antes
		if (datosPDFAnexo == null && !StringUtils.isEmpty(nombreFicheroAnexo) && !StringUtils.isEmpty(codigo)
				&& nombreFicheroAnexo.toLowerCase().indexOf(".pdf") >= 0) {
			datosPDFAnexo = new HashMap<>();
			final InputStream is = new ByteArrayInputStream(this.datosFicheroAnexo);
			PdfReader reader;
			try {
				reader = new PdfReader(is);
				final Map mapac = reader.getAcroFields().getFields();
				for (final Iterator it = mapac.keySet().iterator(); it.hasNext();) {
					final String key = (String) it.next();
					datosPDFAnexo.put(key, reader.getAcroFields().getField(key));
				}
			} catch (final IOException ex) {
				log.warn("Error extraient valor PDF: " + codigo + ". Devolvemos valor vacío.", ex);
				datosPDFAnexo = null;
			}
		}

		// Leemos campo formulario
		if (datosPDFAnexo != null) {
			respuesta = datosPDFAnexo.get(codigo);
		}

		// Devolvemos respuesta
		return StringUtils.defaultString(respuesta);
	}

	@Override
	public String getValorXml(final String xpath) {
		String respuesta = "";
		Node nodo = null;

		// Leemos XML si no se ha leído antes
		if (documentoXML == null && !StringUtils.isEmpty(nombreFicheroAnexo) && !StringUtils.isEmpty(xpath)
				&& nombreFicheroAnexo.toLowerCase().indexOf(".xml") >= 0) {
			final SAXReader reader = new SAXReader();
			try {
				documentoXML = reader.read(new ByteArrayInputStream(this.datosFicheroAnexo));
			} catch (final DocumentException e) {
				log.warn("Error extraient valor xml: " + xpath + ". Devolvemos valor vacío.", e);
				documentoXML = null;
			}
		}

		// Obtenemos XPATH
		if (documentoXML != null) {
			nodo = documentoXML.selectSingleNode(xpath);
			if (nodo != null) {
				respuesta = nodo.getText();
			}
		}

		// Devolvemos repuesta
		return StringUtils.defaultString(respuesta);
	}

	@Override
	public String getContenidoB64() {
		return new String(Base64.encodeBase64(datosFicheroAnexo));
	}

}
