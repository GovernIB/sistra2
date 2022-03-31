package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.Firma;
import es.caib.sistramit.core.api.model.flujo.Firmante;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;
import es.caib.sistramit.core.api.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoAnexo;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;

/**
 * Utils paso registrar.
 *
 * @author Indra
 *
 */
public final class UtilsPasoRegistrar {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilsPasoRegistrar.class);

	/**
	 * Singleton.
	 */
	private static UtilsPasoRegistrar instance = new UtilsPasoRegistrar();

	/**
	 * Instancia un nuevo controlador paso registrar helper de
	 * ControladorPasoRegistrarHelper.
	 */
	private UtilsPasoRegistrar() {
	}

	/**
	 * Devuelve singleton.
	 *
	 * @return singleton
	 */
	public static UtilsPasoRegistrar getInstance() {
		return instance;
	}

	/**
	 * Verifica si el documento se puede firmar.
	 *
	 * @param pDatosPaso
	 *                            Datos paso
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 * @param idDocumento
	 *                            id documento
	 * @param instancia
	 *                            instancia
	 * @param nifFirmante
	 *                            nif firmante
	 */
	public void validacionesFirmaDocumento(final DatosPaso pDatosPaso, final VariablesFlujo pVariablesFlujo,
			final String idDocumento, final int instancia, final String nifFirmante) {

		final DatosInternosPasoRegistrar dipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();
		final DetallePasoRegistrar dpr = (DetallePasoRegistrar) dipa.getDetallePaso();

		// Verificamos si el documento debe firmarse
		final DatosDocumento dd = pVariablesFlujo.getDocumento(idDocumento, instancia);
		if (dd.getFirmar() != TypeSiNo.SI) {
			throw new AccionPasoNoPermitidaException(
					"El document " + idDocumento + "-" + instancia + " no està configurat per firmar");
		}
		// Verificamos si la persona esta como firmante del documento
		final Persona firmante = obtieneDatosFirmante(pVariablesFlujo, idDocumento, instancia, nifFirmante);
		if (firmante == null) {
			throw new AccionPasoNoPermitidaException("El document " + idDocumento + "-" + instancia
					+ " no té configurat com signant a " + nifFirmante);
		}
		// Verificamos si la persona ya ha firmado el documento
		final DocumentoRegistro docReg = dpr.buscarDocumentoRegistro(idDocumento, instancia);
		if (docReg == null) {
			throw new AccionPasoNoPermitidaException("El document " + idDocumento + "-" + instancia
					+ " no està a la llista de documents per registre");
		}
		final Firma firma = docReg.getFirma(nifFirmante);
		if (firma == null) {
			throw new AccionPasoNoPermitidaException(
					"No es troba informació de la firma pel document " + idDocumento + "-" + instancia);
		}
		if (firma.getEstadoFirma() == TypeEstadoFirma.FIRMADO) {
			throw new AccionPasoNoPermitidaException(
					"El document " + idDocumento + "-" + instancia + " ja ha estat signat per " + nifFirmante);
		}
	}

	/**
	 * Obtiene datos persona firmante.
	 *
	 * @param pVariablesFlujo
	 *                            variables flujo
	 * @param idDocumento
	 *                            id documento
	 * @param instancia
	 *                            instancia
	 * @param nifFirmante
	 *                            nif firmante
	 * @return firmante
	 */
	public Firmante obtieneDatosFirmante(final VariablesFlujo pVariablesFlujo, final String idDocumento,
			final int instancia, final String nifFirmante) {
		Firmante firmante = null;
		final DatosDocumento dd = pVariablesFlujo.getDocumento(idDocumento, instancia);
		for (final Firmante p : dd.getFirmantes()) {
			if (p.getNif().equals(nifFirmante)) {
				firmante = p;
				break;
			}
		}
		return firmante;
	}

	/**
	 * Obtiene referencia documento a firmar según el tipo de documento.
	 *
	 * @param pVariablesFlujo
	 *                            variables flujo
	 * @param idDocumento
	 *                            id documento
	 * @param instancia
	 *                            instancia
	 * @return fichero a firmar
	 */
	public ReferenciaFichero obtenerReferenciaFicheroFirmar(final VariablesFlujo pVariablesFlujo,
			final String idDocumento, final int instancia) {
		// Buscamos referencia fichero a firmar
		final DatosDocumento dd = pVariablesFlujo.getDocumento(idDocumento, instancia);
		ReferenciaFichero ref;
		switch (dd.getTipo()) {
		case FORMULARIO:
			ref = ((DatosDocumentoFormulario) dd).getPdf();
			break;
		case ANEXO:
			ref = ((DatosDocumentoAnexo) dd).getFichero();
			break;
		default:
			throw new AccionPasoNoPermitidaException(
					"No es permet firma de document " + idDocumento + " - " + instancia);
		}
		return ref;
	}

	/**
	 * Obtiene extensión fichero según tipo firma.
	 *
	 * @param tipoFirma
	 *                      Tipo firma
	 * @return extensión
	 */
	public String getExtensionFirma(final TypeFirmaDigital tipoFirma) {
		String res = null;
		switch (tipoFirma) {
		case PADES:
			res = "pdf";
			break;
		case CADES_DETACHED:
			res = "cades";
			break;
		case CADES_ATTACHED:
			res = "cades";
			break;
		case XADES_DETACHED:
			res = "xades";
			break;
		case XADES_ENVELOPED:
			res = "xades";
			break;
		default:
			throw new TipoNoControladoException("Tipus de firma no controlat: " + tipoFirma);
		}
		return res;
	}

	/**
	 * Carga plantilla mail finalizacion registro.
	 *
	 * @return plantilla mail
	 */
	public String cargarPlantillaMailFinalizacion() {

		// TODO Ver si cachear plantilla

		String plantilla = null;
		try {
			final InputStream is = UtilsPasoRegistrar.class.getResourceAsStream("/mailFinalizarRegistro.html");
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.copy(is, bos);
			plantilla = new String(bos.toByteArray());
			is.close();
			bos.close();
			return plantilla;
		} catch (final IOException ex) {
			// Error al cargar plantilla mail
			LOGGER.error("Error carregant plantilla mail finalitzar registre: " + ex.getMessage(), ex);
		}
		return plantilla;
	}
}
