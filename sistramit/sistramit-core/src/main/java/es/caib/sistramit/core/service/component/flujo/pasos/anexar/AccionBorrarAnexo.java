package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoAnexar;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoAnexar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite descargar una plantilla en el paso Anexar.
 *
 * @author Indra
 *
 */
@Component("accionAdBorrarAnexo")
public final class AccionBorrarAnexo implements AccionPaso {

    @Autowired
    private FlujoPasoDao dao;

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(DatosPaso pDatosPaso,
            DatosPersistenciaPaso pDpp, TypeAccionPaso pAccionPaso,
            ParametrosAccionPaso pParametros,
            DefinicionTramiteSTG pDefinicionTramite,
            VariablesFlujo pVariablesFlujo) {

        // Recogemos parametros
        final String idAnexo = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "idAnexo", true);
        final String instanciaStr = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "instancia", false);
        final int instancia = UtilsFlujo.instanciaStrToInt(instanciaStr);

        // Obtenemos datos internos paso anexar
        final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso
                .internalData();

        // Actualizamos detalle (borramos fichero y actualizamos estado
        // rellenado anexo)
        actualizarDetalleAnexo(dipa, idAnexo, instancia);

        // Actualizar persistencia (actualizamos estado documento y borramos
        // fichero)
        actualizarPersistencia(dipa, pDpp, idAnexo, instancia, pVariablesFlujo);

        // Devolvemos respuesta vacia
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

    /**
     * Actualiza persistencia.
     *
     * @param pDipa
     *            Datos internos paso
     * @param pDpp
     *            Datos persistencia paso
     * @param pIdAnexo
     *            Id anexo
     * @param pInstancia
     *            Instancia
     * @param pVariablesFlujo
     *            Variables flujo
     */
    private void actualizarPersistencia(final DatosInternosPasoAnexar pDipa,
            final DatosPersistenciaPaso pDpp, final String pIdAnexo,
            final int pInstancia, final VariablesFlujo pVariablesFlujo) {

        // Recuperamos instancia documento
        final DocumentoPasoPersistencia docPersistencia = pDpp
                .getDocumentoPasoPersistencia(pIdAnexo, pInstancia);
        if (docPersistencia == null) {
            throw new AccionPasoNoPermitidaException(
                    "No existe instancia del documento");
        }

        // - Marcamos los ficheros para borrar (fichero anexo y ficheros firmas)
        final List<ReferenciaFichero> listaFicherosBorrar = new ArrayList<>();
        listaFicherosBorrar.addAll(
                docPersistencia.obtenerReferenciasFicherosAnexo(true, true));

        // Si es un generico y tiene mas de una instancia, eliminamos documento
        // en persistencia
        if (pDpp.getNumeroInstanciasDocumento(pIdAnexo) > ConstantesNumero.N1) {
            // Eliminamos documento en persistencia
            dao.eliminarDocumento(pVariablesFlujo.getIdSesionTramitacion(),
                    pDipa.getIdPaso(), pIdAnexo, pInstancia);
            // Reorganizamos datos persistencia (eliminamos doc y reordenamos
            // instancias)
            int index = 0;
            for (final DocumentoPasoPersistencia d : pDpp.getDocumentos()) {
                if (d.equals(docPersistencia)) {
                    break;
                }
                index++;
            }
            pDpp.getDocumentos().remove(docPersistencia);
            for (int i = index; i < pDpp.getDocumentos().size(); i++) {
                final DocumentoPasoPersistencia d = pDpp.getDocumentos().get(i);
                if (d.getId().equals(pIdAnexo)) {
                    d.setInstancia(d.getInstancia() - ConstantesNumero.N1);
                }
            }
        } else {
            docPersistencia.setFichero(null);
            docPersistencia.setAnexoDescripcionInstancia(null);
            docPersistencia.setAnexoNombreFichero(null);
            docPersistencia.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
            docPersistencia.setTipo(TypeDocumentoPersistencia.ANEXO);

            dao.establecerDatosDocumento(
                    pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(),
                    docPersistencia);
        }
        // - Eliminamos ficheros marcados para borrar
        for (final ReferenciaFichero ref : listaFicherosBorrar) {
            dao.eliminarFicheroPersistencia(ref);
        }
    }

    /**
     * Actualiza detalle anexo.
     *
     * @param pDipa
     *            Datos internos paso
     * @param pIdAnexo
     *            Id Anexo
     * @param pInstancia
     *            Instancia
     */
    private void actualizarDetalleAnexo(final DatosInternosPasoAnexar pDipa,
            final String pIdAnexo, final int pInstancia) {
        final Anexo anexoDetalle = ((DetallePasoAnexar) pDipa.getDetallePaso())
                .getAnexo(pIdAnexo);
        if (anexoDetalle.getPresentacion() == TypePresentacion.ELECTRONICA) {
            anexoDetalle.borrarFichero(pInstancia);
            if (anexoDetalle.getFicheros().isEmpty()) {
                anexoDetalle.setRellenado(TypeEstadoDocumento.SIN_RELLENAR);
            }
        } else {
            anexoDetalle.setRellenado(TypeEstadoDocumento.SIN_RELLENAR);
        }
    }

}
