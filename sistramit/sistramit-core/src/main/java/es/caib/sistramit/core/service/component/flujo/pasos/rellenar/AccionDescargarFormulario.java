package es.caib.sistramit.core.service.component.flujo.pasos.rellenar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.NoExistePdfFormularioException;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRellenar;
import es.caib.sistramit.core.api.model.flujo.Formulario;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRellenar;
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
 * Acción que permite descargar un formulario en el paso Rellenar (en caso de
 * que tenga visualizacion pdf).
 *
 * @author Indra
 *
 */
@Component("accionRfDescargarFormulario")
public final class AccionDescargarFormulario implements AccionPaso {

    /** DAO acceso BBDD. */
    @Autowired
    private FlujoPasoDao dao;

    /** Parametro id formulario. */
    private static final String PARAM_ID_FORMULARIO = "idFormulario";

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(DatosPaso pDatosPaso,
            DatosPersistenciaPaso pDpp, TypeAccionPaso pAccionPaso,
            ParametrosAccionPaso pParametros,
            DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo) {

        // Obtenemos datos internos paso rellenar
        final DatosInternosPasoRellenar dipa = (DatosInternosPasoRellenar) pDatosPaso
                .internalData();

        // Recogemos parametros
        final String idFormulario = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, PARAM_ID_FORMULARIO,
                        true);

        // Obtenemos detalle formulario
        final Formulario formulario = ((DetallePasoRellenar) dipa
                .getDetallePaso()).getFormulario(idFormulario);

        // Obtenemos info persistencia formulario
        final DocumentoPasoPersistencia dpp = pDpp.getDocumentoPasoPersistencia(
                idFormulario, ConstantesNumero.N1);

        // Validaciones
        // - Miramos si el formulario está rellenado correctamente
        if (formulario
                .getRellenado() != TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
            throw new AccionPasoNoPermitidaException(
                    "No es pot descarregar el formulari " + idFormulario
                            + " perqué no està emplenat");
        }
        // - Comprobamos si dispone de pdf de visualizacion.
        // Si no existe generamos excepcion para que sea capturada por el front
        // y genere mensaje específico.
        if (dpp.getFormularioPdf() == null) {
            throw new NoExistePdfFormularioException(formulario.getId());
        }

        // Recuperamos datos de persistencia
        final DatosFicheroPersistencia fichero = dao
                .recuperarFicheroPersistencia(dpp.getFormularioPdf());

        // Devolvemos datos pdf de visualizacion
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("nombreFichero", fichero.getNombre());
        rp.addParametroRetorno("datosFichero", fichero.getContenido());

        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;

    }

}
