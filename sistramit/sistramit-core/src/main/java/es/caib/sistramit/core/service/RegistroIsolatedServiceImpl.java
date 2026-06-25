package es.caib.sistramit.core.service;

import es.caib.sistra2.commons.plugins.registro.api.*;
import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.JsonException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.flujo.*;
import es.caib.sistramit.core.api.model.flujo.types.*;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeParametroEvento;
import es.caib.sistramit.core.api.service.RegistroIsolatedService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.EnvioRemotoComponent;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional (propagation = Propagation.NOT_SUPPORTED)
public class RegistroIsolatedServiceImpl implements RegistroIsolatedService {

    /** Auditoria. */
    @Autowired
    private AuditoriaComponent auditoriaComponent;

    /** Componente de registro. */
    @Autowired
    private RegistroComponent registroComponent;

    /** Componente de envio remoto. */
    @Autowired
    private EnvioRemotoComponent envioRemotoComponent;

    @Override
    @NegocioInterceptor
    public ResultadoRegistrar registrar(String idSesionTramitacion, RegistroIsolatedData registroIsolated, boolean reintentar) {

        // Realizamos registro
        ResultadoRegistrar resReg = null;
        if (reintentar) {
            // Reintentar registro
            resReg = reintentarRegistrar(idSesionTramitacion, registroIsolated);
        } else {
            // Audita registro
            // TODO -- VER SI EN LUGAR DE AUDITAR AQUI, SE HACE EN FINALIZAR REGISTRO (VER TB XQ NO SE HACE DE FORMA GENERAL)
            auditarRegistro(idSesionTramitacion, registroIsolated);
            // Invoca a registrar
            resReg = realizarRegistro(idSesionTramitacion, registroIsolated);
        }
        return resReg;
    }

    protected void auditarRegistro(String idSesionTramitacion, RegistroIsolatedData registroIsolated) {
        if (registroIsolated.isDebugEnabled()) {
            final ListaPropiedades listaPropiedades = new ListaPropiedades();
            try {
                String asientoJSON = JSONUtil.toJSON(registroIsolated.getAsiento());
                listaPropiedades.addPropiedad(TypeParametroEvento.REGISTRO_ASIENTOREGISTRO.toString(), asientoJSON);
            } catch (JSONUtilException e) {
                // No debugamos contenido asiento
            }
            if (registroIsolated.isModoEntregaHabilitado()) {
                listaPropiedades.addPropiedad(TypeParametroEvento.REGISTRO_MODOENTREGA.toString(), registroIsolated.isModoEntregaInmediato() ? "Inmediato" : "Periódico");
            }
            final EventoAuditoria evento = new EventoAuditoria();
            evento.setIdSesionTramitacion(idSesionTramitacion);
            evento.setFecha(new Date());
            evento.setTipoEvento(TypeEvento.DEBUG_SCRIPT);
            evento.setDescripcion("Debug envio valores registro");
            evento.setPropiedadesEvento(listaPropiedades);
            auditoriaComponent.auditarEventoAplicacion(evento);
        }
    }

    protected ResultadoRegistrar realizarRegistro(String idSesionTramitacion, RegistroIsolatedData registroIsolated) {
        ResultadoRegistrar resReg;
        AsientoRegistral asiento = registroIsolated.getAsiento();
        if (registroIsolated.getDestino() == TypeDestino.REGISTRO) {
            // TIPO REGISTRO: realizamos registro
            resReg = registroComponent.registrar(registroIsolated.getCodigoEntidad(), idSesionTramitacion,
                    registroIsolated.getIdSesionRegistro(), asiento, registroIsolated.isDebugEnabled());
        } else {
            // TIPO ENVIO: realizamos envio remoto en offline (CES2) o en online (envio remoto establecido en el trámite)
            // Verificamos si esta habilitado modo entrega --> envio remoto offline (CES2)
            if (registroIsolated.isModoEntregaHabilitado()) {
                // Si esta habilitado modo entrega no realizamos envio, se realiza offline. Devolvemos id sesion tramitacion como id envio.
                resReg = new ResultadoRegistrar();
                resReg.setResultado(TypeResultadoRegistro.CORRECTO);
                resReg.setNumeroRegistro(registroIsolated.getIdSesionRegistro());
                resReg.setFechaRegistro(new Date());
            } else {
                // Si no esta habilitado modo entrega, realizamos envio remoto --> envio remoto online (envio remoto establecido en el trámite)
                DatosTramitacion datosTramitacion = new DatosTramitacion();
                datosTramitacion.setIdSesionTramitacion(idSesionTramitacion);
                datosTramitacion.setIdTramite(registroIsolated.getIdTramite());
                datosTramitacion.setVersionTramite(registroIsolated.getVersionTramite());
                datosTramitacion.setIdProcedimientoSIA(registroIsolated.getCodigoSIA());
                datosTramitacion.setIdProcedimiento(registroIsolated.getCodigoProcedimiento());
                resReg = envioRemotoComponent.realizarEnvio(registroIsolated.getCodigoEntidad(),
                        registroIsolated.getIdEnvioRemoto(), idSesionTramitacion, registroIsolated.getIdSesionRegistro(),
                        datosTramitacion, asiento, registroIsolated.isDebugEnabled());
            }
        }
        return resReg;
    }

    protected ResultadoRegistrar reintentarRegistrar(String idSesionTramitacion, RegistroIsolatedData registroIsolated) {
        ResultadoRegistrar resReg;
        if (registroIsolated.getDestino() == TypeDestino.REGISTRO) {
            resReg = registroComponent.reintentarRegistro(registroIsolated.getCodigoEntidad(),
                    registroIsolated.getIdSesionRegistro(), registroIsolated.isDebugEnabled());
        } else {
            if (registroIsolated.isModoEntregaHabilitado()) {
                throw new ErrorConfiguracionException("Reintento no permitido si es modo entrega");
            } else{
                resReg = envioRemotoComponent.reintentarEnvio(registroIsolated.getCodigoEntidad(),
                        registroIsolated.getIdEnvioRemoto(), registroIsolated.getIdSesionRegistro(),
                        registroIsolated.isDebugEnabled());
            }
        }
        return resReg;
    }

}
