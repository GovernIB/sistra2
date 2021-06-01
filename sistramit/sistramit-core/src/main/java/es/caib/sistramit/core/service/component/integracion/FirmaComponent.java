package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.service.model.integracion.FirmaClienteRespuesta;
import es.caib.sistramit.core.service.model.integracion.RedireccionFirma;
import es.caib.sistramit.core.service.model.integracion.ValidacionFirmante;

/**
 * Acceso a Componente Firma.
 *
 * @author Indra
 *
 */
public interface FirmaComponent {

	/**
	 * Redirección a firma externa.
	 *
	 * @param idEntidad
	 *                           Entidad
	 * @param firmante
	 *                           Firmante
	 * @param representante
	 *                           En caso de estar autenticado con un certificado de
	 *                           representación, indica el representante (quien
	 *                           firmará con su cert de representación)
	 * @param fileId
	 *                           Id fichero
	 * @param fileContent
	 *                           Contenido fichero
	 * @param fileName
	 *                           Nombre fichero con extensión
	 * @param tipoDocumental
	 *                           Tipo documental
	 * @param urlCallBack
	 *                           url callback
	 * @param idioma
	 *                           idioma
	 * @return Datos redirección firma
	 */
	RedireccionFirma redireccionFirmaExterna(String idEntidad, Persona firmante, final Persona representante,
			String fileId, byte[] fileContent, String fileName, String tipoDocumental, String urlCallBack,
			String idioma);

	/**
	 * Recupera resultado firma externa.
	 *
	 * @param idEntidad
	 *                        Entidad
	 * @param sesionFirma
	 *                        Id sesión firma
	 * @param fileId
	 *                        Id fichero
	 * @return Resultado firma
	 */
	FirmaClienteRespuesta recuperarResultadoFirmaExterna(String idEntidad, String sesionFirma, String fileId);

	/**
	 * Realiza validación de firma verificando si ha firmado el firmante.
	 *
	 * @param idEntidad
	 *                           Entidad
	 * @param idioma
	 *                           Idiona
	 * @param signedDocument
	 *                           Fichero a firmar
	 * @param signature
	 *                           Firma fichero
	 * @param firmante
	 *                           Nif firmante
	 * @return Validación firmante
	 */
	ValidacionFirmante validarFirmante(String idEntidad, String idioma, byte[] signedDocument, byte[] signature,
			String nifFirmante);

}
