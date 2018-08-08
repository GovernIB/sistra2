package es.caib.sistramit.core.service.component.flujo.pasos.rellenar;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite descargar el xml de un formulario en el paso Rellenar
 * (para debug).
 *
 * @author Indra
 *
 */
@Component("accionRfDescargarXmlFormulario")
public final class AccionDescargarXmlFormulario implements AccionPaso {

    /** DAO acceso BBDD. */
    @Resource(name = "flujoPasoDao")
    private FlujoPasoDao dao;

    /**
     * Parametro id formulario.
     */
    private static final String PARAM_ID_FORMULARIO = "idFormulario";

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(DatosPaso pDatosPaso,
            DatosPersistenciaPaso pDpp, TypeAccionPaso pAccionPaso,
            ParametrosAccionPaso pParametros,
            DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo) {
        // Recogemos parametros
        final String idFormulario = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, PARAM_ID_FORMULARIO,
                        true);

        // Obtenemos info persistencia formulario
        final DocumentoPasoPersistencia dpp = pDpp.getDocumentoPasoPersistencia(
                idFormulario, ConstantesNumero.N1);

        // Devolvemos xml formulario
        DatosFicheroPersistencia fichero = null;
        fichero = dao.recuperarFicheroPersistencia(dpp.getFichero());
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("xml", fichero.getContenido());

        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

}
