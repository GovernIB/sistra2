package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 * Acción que permite descargar anexo en el paso Anexar.
 *
 * @author Indra
 *
 */
@Component("accionAdDescargarAnexo")
public final class AccionDescargarAnexo implements AccionPaso {

    /** Atributo dao de AccionObtenerDatosAnexo. */
    @Autowired
    private FlujoPasoDao dao;

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(
            final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
            final TypeAccionPaso pAccionPasoObtenerAnexo,
            final ParametrosAccionPaso pParametros,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {

        // Recogemos parametros
        final String idAnexo = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "idAnexo", true);
        final String instanciaStr = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "instancia", false);
        final int instancia = UtilsFlujo.instanciaStrToInt(instanciaStr);

        // Recuperamos fichero de persistencia
        final DocumentoPasoPersistencia doc = pDpp
                .getDocumentoPasoPersistencia(idAnexo, instancia);

        final DatosFicheroPersistencia dfp = dao
                .recuperarFicheroPersistencia(doc.getFichero());

        // Devolvemos respuesta con el documento
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("datosFichero", dfp.getContenido());
        rp.addParametroRetorno("nombreFichero", dfp.getNombre());

        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

}
