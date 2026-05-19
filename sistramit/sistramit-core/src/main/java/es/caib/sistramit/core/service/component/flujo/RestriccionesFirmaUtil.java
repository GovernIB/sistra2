package es.caib.sistramit.core.service.component.flujo;

import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeNivelSeguridad;


/**
 * Restricciones de firma según nivel autenticación y nivel de seguridad.
 */
public class RestriccionesFirmaUtil {

    /**
     * Indica si hay que verificar el firmante.
     * @param tipoAutenticacion Tipo de autenticación del usuario
     * @param nivelSeguridadAutenticado Nivel de seguridad del usuario autenticado
     * @return true si hay que verificar el firmante del formulario, false en caso contrario
     */
    public static boolean verificarFirmante(TypeAutenticacion tipoAutenticacion, TypeNivelSeguridad nivelSeguridadAutenticado) {
        return  tipoAutenticacion == TypeAutenticacion.AUTENTICADO &&
                (
                    nivelSeguridadAutenticado == TypeNivelSeguridad.SUSTANCIAL_CERTIFICADO ||
                    nivelSeguridadAutenticado == TypeNivelSeguridad.ALTO
                );
    }

    /**
     * Verifica que las configuraciones de firma de anexos cumplen con las restricciones establecidas para cada nivel de seguridad.
     * @param rConfiguracionEntidad Configuracion entidad
     * @param nivelAutenticacion Nivel de autenticación del usuario
     * @param nivelSeguridadAutenticado Nivel de seguridad del usuario autenticado
     * @param anexo Anexo
     */
    public static void verificarRestriccionesFirmaAnexo(RConfiguracionEntidad rConfiguracionEntidad, TypeAutenticacion nivelAutenticacion, TypeNivelSeguridad nivelSeguridadAutenticado, Anexo anexo) {
        // Si esta marcado para firmar, verificamos restricciones por nivel de seguridad
        if (anexo.getFirmar() == TypeSiNo.SI) {
            // Firma no puede ser habilitada para anónimo
            if (nivelAutenticacion == TypeAutenticacion.ANONIMO) {
                throw new ErrorConfiguracionException("Anexo " + anexo.getId()
                        + " no pot ser marcat com a que requereix firma si el nivell d'autenticació és anònim");
            }
            // Validación de firmantes solo debe estar habilitada para nivel alto / sustancial con certificado
            if (nivelSeguridadAutenticado == TypeNivelSeguridad.ALTO || nivelSeguridadAutenticado == TypeNivelSeguridad.SUSTANCIAL_CERTIFICADO) {
                if (anexo.getValidarFirmantes() != TypeSiNo.SI) {
                    throw new ErrorConfiguracionException("Annexe " + anexo.getId()
                            + " ha de ser configurat per validar signants ja que requereix firma i el seu nivell de seguretat és alt/sustancial amb certificat");
                }
            } else {
                if (anexo.getValidarFirmantes() != TypeSiNo.NO) {
                    throw new ErrorConfiguracionException("Annexe " + anexo.getId()
                            + " ha de ser configurat per no validar signants ja que requereix firma i el seu nivell de seguretat és baix/sustancial");
                }
            }
            // Verificamos que las extensiones sean correctas segun nivel seguridad
            String[] extensiones = anexo.getExtensiones().split(",");
            // - Nivel alto / sustancial con certificado: PDF / Otras extensiones (según configuración entidad)
            final RConfiguracionEntidad entidadInfo = rConfiguracionEntidad;
            if (nivelSeguridadAutenticado == TypeNivelSeguridad.ALTO || nivelSeguridadAutenticado == TypeNivelSeguridad.SUSTANCIAL_CERTIFICADO) {
                for (String ext : extensiones) {
                    if (!"pdf".equalsIgnoreCase(ext)
                            && !entidadInfo.isPermitirOtrasExtensionesFirmaCertificado()) {
                        throw new ErrorConfiguracionException("Annexe " + anexo.getId()
                                + " només pot ser configurat per admetre pdf ja que requereix firma (la entitat no admiteix altres extensions per al nivell de seguretat és alt/sustancial amb certificat)");
                    }
                }
            } else {
                // - Nivel bajo/sustancial: solo pdf
                for (String ext : extensiones) {
                    if (!"pdf".equalsIgnoreCase(ext)) {
                        throw new ErrorConfiguracionException("Annexe " + anexo.getId()
                                + " només pot ser configurat per admetre pdf ja que requereix firma amb nivell de seguretat baix/sustancial");
                    }
                }
            }
        }
    }



}
