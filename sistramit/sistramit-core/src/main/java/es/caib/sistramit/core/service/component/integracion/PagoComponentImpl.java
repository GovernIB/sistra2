package es.caib.sistramit.core.service.component.integracion;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.pago.api.DatosPago;
import es.caib.sistra2.commons.plugins.pago.api.EstadoPago;
import es.caib.sistra2.commons.plugins.pago.api.IComponentePagoPlugin;
import es.caib.sistra2.commons.plugins.pago.api.PagoPluginException;
import es.caib.sistra2.commons.plugins.pago.api.RedireccionPago;
import es.caib.sistra2.commons.plugins.pago.api.TypeEstadoPago;
import es.caib.sistramit.core.api.exception.PagoException;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.PagoComponentRedireccion;
import es.caib.sistramit.core.service.model.integracion.PagoComponentVerificacion;

/**
 * Implementación componente pago.
 *
 * @author Indra
 *
 */
@Component("pagoComponent")
public final class PagoComponentImpl implements PagoComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Configuracion. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    @Override
    public int consultaTasa(String idEntidad, String idPasarela, String idTasa,
            boolean debugEnabled) {
        try {
            if (debugEnabled) {
                log.debug("Consulta tasa " + idTasa + " en pasarela "
                        + idPasarela);
            }
            final IComponentePagoPlugin plugin = obtenerPlugin(idEntidad);
            return plugin.consultaTasa(idPasarela, idTasa);
        } catch (final PagoPluginException e) {
            throw new PagoException("Excepcion consultando tasa " + idTasa
                    + ": " + e.getMessage(), e);
        }
    }

    @Override
    public PagoComponentRedireccion iniciarPagoElectronico(
            DatosSesionPago sesionPago, String urlCallback,
            boolean debugEnabled) {

        if (debugEnabled) {
            log.debug("Inicio sesion pago - simulado: "
                    + sesionPago.isSimulado());
        }

        if (sesionPago.getPresentacion() != TypePresentacion.ELECTRONICA) {
            throw new PagoException(
                    "El pago no tiene presentacion electronica (tipo presentacion: "
                            + sesionPago.getPresentacion());
        }

        PagoComponentRedireccion res = null;
        if (sesionPago.isSimulado()) {
            res = iniciarPagoElectronicoSimulado(sesionPago, urlCallback);
        } else {
            res = iniciarPagoElectronicoPasarela(sesionPago, urlCallback);
        }

        if (debugEnabled) {
            log.debug("Inicio sesion pago -  identificador: "
                    + res.getIdentificador() + " - url: " + res.getUrl());
        }

        return res;
    }

    @Override
    public PagoComponentVerificacion verificarPagoElectronico(
            DatosSesionPago sesionPago, boolean debugEnabled) {

        if (sesionPago.getPresentacion() != TypePresentacion.ELECTRONICA) {
            throw new PagoException(
                    "El pago no tiene presentacion electronica (tipo presentacion: "
                            + sesionPago.getPresentacion());
        }

        PagoComponentVerificacion res = null;
        if (sesionPago.isSimulado()) {
            res = verificarPagoElectronicoSimulado(sesionPago);
        } else {
            res = verificarPagoElectronicoPasarela(sesionPago);
        }

        return res;
    }

    @Override
    public byte[] obtenerCartaPagoPresencial(DatosSesionPago sesionPago,
            boolean debugEnabled) {
        try {
            if (debugEnabled) {
                log.debug("Obtener carta pago presencial en pasarela "
                        + sesionPago.getPasarelaId());
            }

            if (sesionPago.getPresentacion() != TypePresentacion.PRESENCIAL) {
                throw new PagoException(
                        "El pago no tiene presentacion presencial (tipo presentacion: "
                                + sesionPago.getPresentacion());
            }

            byte[] cartaPago = null;
            if (sesionPago.isSimulado()) {
                cartaPago = obtenerCartaPagoPresencialSimulado(sesionPago);
            } else {
                cartaPago = obtenerCartaPagoPresencialPasarela(sesionPago);
            }

            return cartaPago;
        } catch (final PagoPluginException e) {
            throw new PagoException(
                    "Excepcion obteniendo carta de pago presencial: "
                            + e.getMessage(),
                    e);
        }
    }

    // ----------------------------------------------------------------
    // METODOS AUXILIARES
    // ----------------------------------------------------------------

    /**
     * Obtiene plugin de pago.
     *
     * @param entidadId
     *            id entidad
     * @return plugin pago
     */
    private IComponentePagoPlugin obtenerPlugin(String entidadId) {
        return (IComponentePagoPlugin) configuracionComponent
                .obtenerPluginEntidad(TypePluginEntidad.PAGOS, entidadId);
    }

    /**
     * Inicio pago electrónico real.
     *
     * @param sesionPago
     *            datos sesión pago
     * @param urlCallback
     *            urlCallback
     * @return redirección pago
     */
    private PagoComponentRedireccion iniciarPagoElectronicoPasarela(
            DatosSesionPago sesionPago, String urlCallback) {
        try {
            final DatosPago datosPago = crearDatosPago(sesionPago);
            final IComponentePagoPlugin plugin = obtenerPlugin(
                    sesionPago.getEntidadId());
            final RedireccionPago resPlg = plugin
                    .iniciarPagoElectronico(datosPago, urlCallback);
            final PagoComponentRedireccion res = new PagoComponentRedireccion();
            res.setIdentificador(resPlg.getIdentificador());
            res.setUrl(resPlg.getUrlPago());
            return res;
        } catch (final PagoPluginException e) {
            throw new PagoException(
                    "Excepcion al iniciar pago electronico: " + e.getMessage(),
                    e);
        }
    }

    /**
     * Inicio pago electrónico de forma simulada.
     *
     * @param sesionPago
     *            datos sesión pago
     * @param urlCallback
     * @return redirección pago
     */
    private PagoComponentRedireccion iniciarPagoElectronicoSimulado(
            DatosSesionPago sesionPago, String urlCallback) {
        final PagoComponentRedireccion res = new PagoComponentRedireccion();
        res.setIdentificador(System.currentTimeMillis() + "");
        res.setUrl(urlCallback);
        return res;
    }

    /**
     * Crear datos pago.
     *
     * @param sesionPago
     *            sesión pago
     * @return datos pago
     */
    private DatosPago crearDatosPago(DatosSesionPago sesionPago) {
        final DatosPago datosPago = new DatosPago();
        datosPago.setEntidadId(sesionPago.getEntidadId());
        datosPago.setPasarelaId(sesionPago.getPasarelaId());
        datosPago.setOrganismoId(sesionPago.getOrganismoId());
        datosPago.setIdioma(sesionPago.getIdioma());
        datosPago.setModelo(sesionPago.getModelo());
        datosPago.setConcepto(sesionPago.getConcepto());
        datosPago.setTasaId(sesionPago.getTasaId());
        datosPago.setDetallePago(sesionPago.getDetallePago());
        datosPago.setImporte(sesionPago.getImporte());
        datosPago.setSujetoPasivoNif(sesionPago.getSujetoPasivo().getNif());
        datosPago.setSujetoPasivoNombre(
                sesionPago.getSujetoPasivo().getNombre());
        return datosPago;
    }

    /**
     * Verifica pago electronico contra la pasarela.
     *
     * @param sesionPago
     *            sesion pago
     * @return verificacion
     */
    private PagoComponentVerificacion verificarPagoElectronicoPasarela(
            DatosSesionPago sesionPago) {
        try {
            final IComponentePagoPlugin plugin = obtenerPlugin(
                    sesionPago.getEntidadId());

            // Verifica pago
            final EstadoPago resPlg = plugin.verificarPagoElectronico(
                    sesionPago.getIdentificadorPago());

            // Obtiene justificante si esta pagado
            byte[] justif = null;
            if (resPlg.getEstado() == TypeEstadoPago.PAGADO) {
                justif = plugin.obtenerJustificantePagoElectronico(
                        sesionPago.getIdentificadorPago());
            }

            // Devuelve verificacion y justificante
            final PagoComponentVerificacion res = new PagoComponentVerificacion();
            // TODO Ver si algun caso podemos establecer como no verificado
            // (capturar excepcion??)
            res.setVerificado(true);
            res.setPagado(resPlg.getEstado() == TypeEstadoPago.PAGADO);
            res.setLocalizador(resPlg.getLocalizador());
            res.setJustificantePDF(justif);
            res.setCodigoError(resPlg.getCodigoErrorPasarela());
            res.setMensajeError(resPlg.getMensajeErrorPasarela());
            return res;
        } catch (final PagoPluginException e) {
            throw new PagoException(
                    "Excepcion al iniciar pago electronico: " + e.getMessage(),
                    e);
        }
    }

    /**
     * Verifica pago electronico de forma simulada (indica como pagado).
     *
     * @param sesionPago
     *            sesion pago
     * @return verificacion
     */
    private PagoComponentVerificacion verificarPagoElectronicoSimulado(
            DatosSesionPago sesionPago) {

        final boolean verificado = true;
        final boolean realizado = true;

        final PagoComponentVerificacion res = new PagoComponentVerificacion();
        res.setVerificado(verificado);
        res.setPagado(realizado);

        if (verificado && realizado) {
            res.setLocalizador(System.currentTimeMillis() + "");
            res.setJustificantePDF(generaPdfMock());
        }

        if (verificado && !realizado) {
            res.setCodigoError("ERR");
            res.setMensajeError("Error simulado");
        }

        return res;
    }

    /**
     * Genera PDF mock.
     *
     * @return pdf
     */
    private byte[] generaPdfMock() {
        // Lee pdf mock del classpath
        byte[] content = null;
        try (final InputStream isFile = PagoComponentImpl.class.getClassLoader()
                .getResourceAsStream("mock.pdf");) {
            content = IOUtils.toByteArray(isFile);
        } catch (final IOException e) {
            throw new PagoException(
                    "Excepcion al recuperar justificante simulado: "
                            + e.getMessage(),
                    e);
        }
        return content;
    }

    /**
     * Obtiene carta pago presencial simulado.
     *
     * @param sesionPago
     *            sesion pago
     * @return carta pago
     */
    private byte[] obtenerCartaPagoPresencialSimulado(
            DatosSesionPago sesionPago) {
        return generaPdfMock();
    }

    /**
     * Obtiene carta pago presencial.
     *
     * @param sesionPago
     *            sesion pago
     * @return carta pago
     */
    private byte[] obtenerCartaPagoPresencialPasarela(
            DatosSesionPago sesionPago) throws PagoPluginException {
        final IComponentePagoPlugin plugin = obtenerPlugin(
                sesionPago.getEntidadId());
        final DatosPago datosPago = crearDatosPago(sesionPago);
        return plugin.obtenerCartaPagoPresencial(datosPago);
    }

}
