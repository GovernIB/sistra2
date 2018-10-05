package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;
import es.caib.sistramit.core.api.exception.ErrorNoControladoException;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
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
@Component("accionAdTestDescargarFirma")
public final class AccionTestDescargarFirma implements AccionPaso {

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

        // Recuperamos firma

        final FicheroFirmado firma = MockFirma.getFirma(idAnexo, instancia);
        if (firma == null) {
            throw new ErrorNoControladoException("No existe firma");
        }

        // Devolvemos respuesta con el documento
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("datosFichero", firma.getFirmaFichero());
        rp.addParametroRetorno("nombreFichero", firma.getNombreFichero());

        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

}
