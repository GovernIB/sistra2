package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.api.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.api.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.api.TypeEstadoFirmado;
import es.caib.sistramit.core.api.exception.ErrorNoControladoException;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

// TODO BORRAR
@Component("accionAdTestRetornoFirma")
public final class AccionTestRetornoFirma implements AccionPaso {

    /** Atributo dao de AccionObtenerDatosAnexo. */
    @Autowired
    private FlujoPasoDao dao;

    @Autowired
    private ConfiguracionComponent configuracionComponent;

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

        // Recogemos firma
        try {
            recogerFirma(pDefinicionTramite, pVariablesFlujo, idAnexo,
                    instancia);
        } catch (final FirmaPluginException e) {
            throw new ErrorNoControladoException(e);
        }

        // Devolvemos respuesta con el documento
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

    private void recogerFirma(final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo, final String idAnexo,
            final int instancia) throws FirmaPluginException {

        final String sf = MockFirma.getSesionFirma(idAnexo, instancia);

        final IFirmaPlugin plgFirma = (IFirmaPlugin) configuracionComponent
                .obtenerPluginEntidad(TypePluginEntidad.FIRMA,
                        pDefinicionTramite.getDefinicionVersion()
                                .getIdEntidad());

        // Verifica estado
        final TypeEstadoFirmado estado = plgFirma.obtenerEstadoSesionFirma(sf);
        if (estado != TypeEstadoFirmado.FINALIZADO_OK) {
            throw new ErrorNoControladoException(
                    "Firma no acabada, estado: " + estado.name());
        }

        // Recoge firma
        final FicheroFirmado fic = plgFirma.obtenerFirmaFichero(sf,
                idAnexo + "-" + instancia);

        MockFirma.setFirma(idAnexo, instancia, fic);

        // Cierra sesion
        plgFirma.cerrarSesionFirma(sf);

    }

}
