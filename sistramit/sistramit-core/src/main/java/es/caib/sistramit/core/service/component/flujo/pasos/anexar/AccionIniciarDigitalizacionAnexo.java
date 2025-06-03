package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoAnexar;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.RedireccionDigitalizacion;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.DigitalizacionComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.*;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Inicia sesión para digitalizar anexo.
 */
@Component("accionAdIniciarDigitalizacionAnexo")
public class AccionIniciarDigitalizacionAnexo implements AccionPaso {

    /** Configuración. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    /** Componente digitalización. */
    @Autowired
    private DigitalizacionComponent digitalizacionComponent;

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(DatosPaso pDatosPaso, DatosPersistenciaPaso pDpp, TypeAccionPaso pAccionPaso, ParametrosAccionPaso pParametros, DefinicionTramiteSTG pDefinicionTramite, VariablesFlujo pVariablesFlujo) {

        // Recogemos parametros
        final String idDocumento = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "idAnexo", true);

        // Obtenemos datos internos paso anexar
        final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso.internalData();

        // Obtenemos info de detalle para el anexo
        final Anexo anexoDetalle = ((DetallePasoAnexar) dipa.getDetallePaso()).getAnexo(idDocumento);

        // Solo permitido en modo FH
        if (!pVariablesFlujo.isFuncionarioHabilitado()) {
            throw new AccionPasoNoPermitidaException("Solo se permite acción digitalizar en modo FH");
        }

        // Calcula url Callback
        final String urlCallBack = configuracionComponent
                .obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
                + ConstantesSeguridad.PUNTOENTRADA_RETORNO_DIGITALIZACION + "?idPaso=" + pDatosPaso.getIdPaso()
                + "&idDocumento=" + idDocumento;

        // Inicia sesión digitalización
        final RedireccionDigitalizacion res = digitalizacionComponent.redireccionDigitalizacion(anexoDetalle.getTitulo(), pDefinicionTramite.getDefinicionVersion().getIdEntidad(), urlCallBack, pVariablesFlujo);

        // Almacena sesion firma en datos internos paso
        dipa.guardarSesionDigitalizacion(idDocumento, res.getIdSesionDigitalizacion());

        // Devolvemos url componente firma
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("redireccion", res);
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

}
