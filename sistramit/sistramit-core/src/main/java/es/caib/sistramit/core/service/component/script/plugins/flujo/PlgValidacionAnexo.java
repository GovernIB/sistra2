package es.caib.sistramit.core.service.component.script.plugins.flujo;

import org.apache.commons.codec.binary.Base64;

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
	 * Constructor.
	 *
	 * @param pNombreFicheroAnexo
	 *            Nombre fichero anexo.
	 * @param pDatosFicheroAnexo
	 *            Datos fichero anexo.
	 */
	public PlgValidacionAnexo(final String pNombreFicheroAnexo, final byte[] pDatosFicheroAnexo) {
		super();
		nombreFicheroAnexo = pNombreFicheroAnexo;
		datosFicheroAnexo = pDatosFicheroAnexo;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public String getValorPdf(final String pCodigo) {
		// TODO PENDIENTE
		return null;
	}

	@Override
	public String getValorXml(final String pXpath) {
		// TODO PENDIENTE
		return null;
	}

	@Override
	public String getContenidoB64() {
		return new String(Base64.encodeBase64(datosFicheroAnexo));
	}

}
