package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.firmacliente.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.InfoSesionFirma;
import es.caib.sistramit.core.api.exception.ErrorNoControladoException;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
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

// TODO BORRAR
@Component("accionAdTestFirma")
public final class AccionTestFirma implements AccionPaso {

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

        // Recuperamos fichero de persistencia
        final DocumentoPasoPersistencia doc = pDpp
                .getDocumentoPasoPersistencia(idAnexo, instancia);

        final DatosFicheroPersistencia dfp = dao
                .recuperarFicheroPersistencia(doc.getFichero());

        // Enviamos fichero a firmar
        String urlRedireccion;
        try {
            urlRedireccion = enviarFirma(pDatosPaso, pDefinicionTramite,
                    pVariablesFlujo, dfp, idAnexo, instancia);
        } catch (final FirmaPluginException e) {
            throw new ErrorNoControladoException(e);
        }

        // Devolvemos respuesta con el documento
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        rp.addParametroRetorno("url", urlRedireccion);

        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

    public String enviarFirma(DatosPaso pDatosPaso,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo,
            final DatosFicheroPersistencia dfp, final String idAnexo,
            final int instancia) throws FirmaPluginException {

        // TODO Donde se indica el tipo de firma?? En FirmaWeb ??

        final IFirmaPlugin plgFirma = (IFirmaPlugin) configuracionComponent
                .obtenerPluginEntidad(TypePluginEntidad.FIRMA,
                        pDefinicionTramite.getDefinicionVersion()
                                .getIdEntidad());

        // Crea sesion de firma
        final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
        infoSesionFirma.setEntidad(pVariablesFlujo.getUsuario().getNif());
        infoSesionFirma.setNombreUsuario(
                pVariablesFlujo.getUsuario().getNombreApellidos());
        infoSesionFirma.setIdioma(pVariablesFlujo.getIdioma());
        final String sf = plgFirma.generarSesionFirma(infoSesionFirma);

        // Añade fichero
        final FicheroAFirmar fichero = new FicheroAFirmar();
        fichero.setFichero(dfp.getContenido());
        if (dfp.getNombre().endsWith(".pdf")) {
            fichero.setMimetypeFichero("application/pdf");
        } else {
            fichero.setMimetypeFichero("application/octet-stream");
        }
        fichero.setIdioma(pVariablesFlujo.getIdioma());
        fichero.setSignNumber(1);
        fichero.setNombreFichero(dfp.getNombre());
        fichero.setRazon(dfp.getNombre());
        fichero.setSignID(idAnexo + "-" + instancia);
        fichero.setSesion(sf);
        plgFirma.ficheroAFirmar(fichero);

        // Iniciar sesion firma
        final String urlRedireccion = plgFirma.iniciarSesionFirma(sf,
                configuracionComponent.obtenerPropiedadConfiguracion(
                        TypePropiedadConfiguracion.SISTRAMIT_URL)
                        + "/asistente/ad/testRetornoFirma.html?idPaso="
                        + pDatosPaso.getIdPaso() + "&idAnexo=" + idAnexo
                        + "&instancia=" + instancia,
                null);

        // Almacena sesion firma
        MockFirma.setSesionFirma(idAnexo, instancia, sf);

        return urlRedireccion;
    }

}
