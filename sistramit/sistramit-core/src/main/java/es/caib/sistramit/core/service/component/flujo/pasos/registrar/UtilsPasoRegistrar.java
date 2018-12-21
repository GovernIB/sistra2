package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.Firma;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoAnexo;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeFirmaDigital;

/**
 * Utils paso registrar.
 *
 * @author Indra
 *
 */
public final class UtilsPasoRegistrar {

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
	 *            Datos paso
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @param nifFirmante
	 *            nif firmante
	 */
	public void validacionesFirmaDocumento(DatosPaso pDatosPaso, VariablesFlujo pVariablesFlujo, String idDocumento,
			int instancia, String nifFirmante) {

		final DatosInternosPasoRegistrar dipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();
		final DetallePasoRegistrar dpr = (DetallePasoRegistrar) dipa.getDetallePaso();

		// Verificamos si el documento debe firmarse
		final DatosDocumento dd = pVariablesFlujo.getDocumento(idDocumento, instancia);
		if (dd.getFirmar() != TypeSiNo.SI) {
			throw new AccionPasoNoPermitidaException(
					"El documento " + idDocumento + "-" + instancia + " no esta configurado para firmar");
		}
		// Verificamos si la persona esta como firmante del documento
		final Persona firmante = obtieneDatosFirmante(pVariablesFlujo, idDocumento, instancia, nifFirmante);
		if (firmante == null) {
			throw new AccionPasoNoPermitidaException("El documento " + idDocumento + "-" + instancia
					+ " no tiene configurado como firmante a " + nifFirmante);
		}
		// Verificamos si la persona ya ha firmado el documento
		final DocumentoRegistro docReg = dpr.buscarDocumentoRegistro(idDocumento, instancia);
		if (docReg == null) {
			throw new AccionPasoNoPermitidaException("El documento " + idDocumento + "-" + instancia
					+ " no esta en la lista de documentos para registro");
		}
		final Firma firma = docReg.getFirma(nifFirmante);
		if (firma == null) {
			throw new AccionPasoNoPermitidaException(
					"No se encuentra información de la firma para el documento " + idDocumento + "-" + instancia);
		}
		if (firma.getEstadoFirma() == TypeEstadoFirma.FIRMADO) {
			throw new AccionPasoNoPermitidaException(
					"El documento " + idDocumento + "-" + instancia + " ya ha sido firmado por " + nifFirmante);
		}
	}

	/**
	 * Obtiene datos persona firmante.
	 *
	 * @param pVariablesFlujo
	 *            variables flujo
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @param nifFirmante
	 *            nif firmante
	 * @return firmante
	 */
	public Persona obtieneDatosFirmante(final VariablesFlujo pVariablesFlujo, final String idDocumento,
			final int instancia, final String nifFirmante) {
		Persona firmante = null;
		final DatosDocumento dd = pVariablesFlujo.getDocumento(idDocumento, instancia);
		for (final Persona p : dd.getFirmantes()) {
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
	 *            variables flujo
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
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
					"No se permite firma de documento " + idDocumento + " - " + instancia);
		}
		return ref;
	}

	/**
	 * Obtiene extensión fichero según tipo firma.
	 *
	 * @param tipoFirma
	 *            Tipo firma
	 * @return extensión
	 */
	public String getExtensionFirma(TypeFirmaDigital tipoFirma) {
		String res = null;
		switch (tipoFirma) {
		case PADES:
			res = "pdf";
			break;
		case CADES_DETACHED:
			res = "cades";
			break;
		case XADES_DETACHED:
			res = "xades";
			break;
		default:
			throw new TipoNoControladoException("Tipo de firma no controlado: " + tipoFirma);
		}
		return res;
	}
}
