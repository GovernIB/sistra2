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
		if (!StringUtils.isEmpty(nombreFicheroAnexo) && !StringUtils.isEmpty(codigo)
				&& nombreFicheroAnexo.toLowerCase().indexOf(".pdf") >= 0) {

			if (datosPDFAnexo == null) {
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

					respuesta = datosPDFAnexo.get(codigo);

				} catch (final IOException ex) {
					datosPDFAnexo = null;
				}

			} else {
				respuesta = datosPDFAnexo.get(codigo);
			}

		}
		if (respuesta == null) {
			respuesta = "";
		}
		return respuesta;
	}

	@Override
	public String getValorXml(final String xpath) {
		String respuesta = "";
		final Node nodo;
		if (!StringUtils.isEmpty(nombreFicheroAnexo) && !StringUtils.isEmpty(xpath)
				&& nombreFicheroAnexo.toLowerCase().indexOf(".xml") >= 0) {
			if (documentoXML == null) {
				final SAXReader reader = new SAXReader();
				try {
					documentoXML = reader.read(new ByteArrayInputStream(this.datosFicheroAnexo));
					nodo = documentoXML.selectSingleNode(xpath);
					respuesta = nodo.getText();
				} catch (final DocumentException e) {
					log.warn("Error extrayendo valor xml: " + xpath + ". Devolvemos valor vacío.", e);
					respuesta = "";
				}
			}
		} else {
			nodo = documentoXML.selectSingleNode(xpath);
			respuesta = nodo.getText();
		}

		if (respuesta == null) {
			respuesta = "";
		}
		return respuesta;
	}

	@Override
	public String getContenidoB64() {
		return new String(Base64.encodeBase64(datosFicheroAnexo));
	}

}
