package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoAnexar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que permite descargar una plantilla en el paso Anexar.
 *
 * @author Indra
 *
 */
@Component("accionAdDescargarPlantilla")
public final class AccionDescargarPlantilla implements AccionPaso {

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(
            final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
            final TypeAccionPaso pAccionPasoDescargarPlantilla,
            final ParametrosAccionPaso pParametros,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {

        // Obtenemos datos internos paso anexar
        final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso
                .internalData();

        // Recogemos parametros
        final String idAnexo = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "idAnexo", true);

        // Obtenemos definicion anexo
        final RPasoTramitacionAnexar defPaso = (RPasoTramitacionAnexar) UtilsSTG
                .devuelveDefinicionPaso(dipa.getIdPaso(), pDefinicionTramite);
        final RAnexoTramite defAnexo = UtilsSTG.devuelveDefinicionAnexo(defPaso,
                idAnexo);

        // Verificamos si realmente tiene plantilla
        if (defAnexo == null || defAnexo.getAyuda() == null
                || defAnexo.getAyuda().getFichero() == null) {
            throw new AccionPasoNoPermitidaException("El anexo " + idAnexo
                    + " no tiene definida plantilla interna");
        }

        // Recuperamos plantilla de repositorio de ficheros
        // TODO VER COMO RECUPERAR FICHERO
        final String path = defAnexo.getAyuda().getFichero();
        if (true)
            throw new RuntimeException("Pendiente implementar");
        final String nombreFic = "xxx";
        final byte[] contenidoFic = "xxx".getBytes();

        // Devolvemos respuesta
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("nombreFichero", nombreFic);
        rp.addParametroRetorno("datosFichero", contenidoFic);
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

}
