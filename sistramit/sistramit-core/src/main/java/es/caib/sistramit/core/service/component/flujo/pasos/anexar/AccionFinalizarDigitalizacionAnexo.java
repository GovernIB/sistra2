package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.SesionDigitalizacionException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.*;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.DigitalizacionComponent;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.*;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.DigitalizacionRespuesta;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Inicia sesión para digitalizar anexo.
 */
@Component("accionAdFinalizarDigitalizacionAnexo")
public class AccionFinalizarDigitalizacionAnexo implements AccionPaso {

    /** Configuración. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    /** Componente digitalización. */
    @Autowired
    private DigitalizacionComponent digitalizacionComponent;

    /** DAO de flujo paso */
    @Autowired
    private FlujoPasoDao dao;

    /** Componente con lógica común de anexar. */
    @Autowired
    private AnexarDocumentoComponent anexarDocumentoComponent;

    /** Auditoria. */
    @Autowired
    private AuditoriaComponent auditoriaComponent;

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp, TypeAccionPaso pAccionPaso, ParametrosAccionPaso pParametros, DefinicionTramiteSTG pDefinicionTramite, VariablesFlujo pVariablesFlujo) {

        // Recogemos parametros
        final String idDocumento = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "idAnexo", true);

        // Datos internos paso
        final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso.internalData();

        // Recuperar resultado digitalización
        String idSesionDigitalizacion = dipa.recuperarSesionDigitalizacion(idDocumento);
        if (idSesionDigitalizacion == null) {
            throw new AccionPasoNoPermitidaException("No se ha encontrado la sesión de digitalización asociada al documento " + idDocumento);
        }
        DigitalizacionRespuesta resultadoDigitalizacion = digitalizacionComponent.recuperarResultadoDigitalizacionExterna(pDefinicionTramite.getDefinicionVersion().getIdEntidad(), idSesionDigitalizacion);

        // Según resultado validacion actualizamos estado paso y persistencia
        if (resultadoDigitalizacion.isDigitalizado()) {
            // Obtenemos info de detalle para el anexo
            final Anexo anexoDetalle = ((DetallePasoAnexar) dipa.getDetallePaso()).getAnexo(idDocumento);
            // Obtener nombre fichero, titulo instancia y contenido
            String ficheroNombre = XssFilter.normalizarFilename(resultadoDigitalizacion.getFicheroNombre());
            String tituloInstancia = anexoDetalle.getMaxInstancias() > ConstantesNumero.N1 ? StringUtils.substring(resultadoDigitalizacion.getFicheroNombre(), 0, 100) : null;
            byte[] ficheroContenido = resultadoDigitalizacion.getFicheroContenido();
            // Realiza validaciones anexo
            anexarDocumentoComponent.validarAnexo(dipa, anexoDetalle, TypePresentacion.ELECTRONICA, ficheroNombre,
                    ficheroContenido, tituloInstancia, pDefinicionTramite, pVariablesFlujo, true);
            // - Actualizamos detalle
            anexarDocumentoComponent.actualizarAnexoDetalle(anexoDetalle, ficheroNombre, tituloInstancia, TypeSiNo.NO);
            // Actualizamos persistencia
            anexarDocumentoComponent.actualizarPersistenciaAnexar(dipa, pDpp, anexoDetalle, ficheroNombre, ficheroContenido, tituloInstancia, pVariablesFlujo);
        } else {
            // Si hay error, auditamos
            SesionDigitalizacionException exc = new SesionDigitalizacionException("Error en la digitalización del anexo " + idDocumento + ": " + resultadoDigitalizacion.getDetalleError() + "\n" + resultadoDigitalizacion.getTrazaError());
            auditoriaComponent.auditarExcepcionNegocio(pVariablesFlujo.getIdSesionTramitacion(), exc);
        }

        // Devolvemos respuesta
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        final DigitalizacionResultado resultado = new DigitalizacionResultado();
        resultado.setIdSesionDigitalizacion(idSesionDigitalizacion);
        resultado.setDigitalizado(TypeSiNo.fromBoolean(resultadoDigitalizacion.isDigitalizado()));
        resultado.setDetalleError(resultadoDigitalizacion.getDetalleError());
        rp.addParametroRetorno("resultado", resultado);
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }


}
