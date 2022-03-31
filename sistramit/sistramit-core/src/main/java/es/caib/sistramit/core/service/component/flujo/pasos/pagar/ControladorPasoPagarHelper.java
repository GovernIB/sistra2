package es.caib.sistramit.core.service.component.flujo.pasos.pagar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import es.caib.sistra2.commons.xml.pago.v1.model.CONFIRMACIONPAGO;
import es.caib.sistra2.commons.xml.pago.v1.model.CONTRIBUYENTE;
import es.caib.sistra2.commons.xml.pago.v1.model.DATOSPAGO;
import es.caib.sistra2.commons.xml.pago.v1.model.PAGO;
import es.caib.sistra2.commons.xml.pago.v1.model.PAGODocument;
import es.caib.sistramit.core.api.exception.XmlPagoException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoPagoIncorrecto;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.model.flujo.DatosCalculoPago;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoPagar;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Helper controlador paso pagar.
 *
 * @author Indra
 *
 */
public final class ControladorPasoPagarHelper {

    /** Formato fecha. */
    private static final String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss";

    /**
     * Singleton.
     */
    private static ControladorPasoPagarHelper instance = new ControladorPasoPagarHelper();

    /**
     * Instancia un nuevo controlador paso registrar helper de
     * ControladorPasoPagarHelper.
     */
    private ControladorPasoPagarHelper() {
    }

    /**
     * Devuelve singleton.
     *
     * @return singleton
     */
    public static ControladorPasoPagarHelper getInstance() {
        return instance;
    }

    /**
     * Genera mensaje estado incorrecto pago.
     *
     * @param literales
     *            Literales
     * @param idioma
     *            idioma
     * @param estadoPago
     *            estado pago
     * @param codErrorPasarela
     *            codigo error pasarela
     * @param msgErrorPasarela
     *            mensaje error pasarela
     * @return mensaje error
     */
    public String generarMensajeEstadoIncorrecto(final Literales literales,
            final String idioma, final TypeEstadoPagoIncorrecto estadoPago,
            final String codErrorPasarela, final String msgErrorPasarela) {
        String res = "";
        if (estadoPago != null) {
            String literalError = null;
            switch (estadoPago) {
            case PAGO_INICIADO:
                literalError = "iniciado.noFinalizado";
                break;
            case TIEMPO_EXCEDIDO:
                literalError = "iniciado.tiempoExcedido";
                break;
            default:
                res = "";
                break;
            }

            res = literales.getLiteral(Literales.PASO_PAGAR, literalError,
                    idioma);
            if (codErrorPasarela != null || msgErrorPasarela != null) {
                res += " [" + codErrorPasarela + " - " + msgErrorPasarela + "]";
            }

        }
        return res;
    }

    /**
     * Deserializa a XML.
     *
     * @param content
     *            content
     * @return DatosSesionPago
     */
    public DatosSesionPago fromXML(byte[] content) {
        try {

            final DatosSesionPago res = new DatosSesionPago();

            final String xmlStr = new String(content, Constantes.UTF8);
            final PAGODocument doc = PAGODocument.Factory.parse(xmlStr);

            res.setIdentificadorPago(doc.getPAGO().getIDENTIFICADOR());
            res.setEntidadId(doc.getPAGO().getDATOSPAGO().getENTIDADID());
            res.setSimulado(doc.getPAGO().getDATOSPAGO().getSIMULADO());
            res.setPresentacion(TypePresentacion.fromString(
                    doc.getPAGO().getDATOSPAGO().getPRESENTACION()));
            res.setIdioma(doc.getPAGO().getDATOSPAGO().getIDIOMA());
            res.setPasarelaId(doc.getPAGO().getDATOSPAGO().getPASARELAID());
            res.setModelo(doc.getPAGO().getDATOSPAGO().getMODELO());
            res.setConcepto(doc.getPAGO().getDATOSPAGO().getCONCEPTO());
            res.setTasaId(doc.getPAGO().getDATOSPAGO().getTASA());
            res.setOrganismoId(doc.getPAGO().getDATOSPAGO().getORGANISMO());
            res.setImporte(doc.getPAGO().getDATOSPAGO().getIMPORTE());
            res.setDetallePago(doc.getPAGO().getDATOSPAGO().getDETALLEPAGO());
            res.setSujetoPasivo(new Persona(
                    doc.getPAGO().getDATOSPAGO().getCONTRIBUYENTE().getNIF(),
                    doc.getPAGO().getDATOSPAGO().getCONTRIBUYENTE()
                            .getNOMBRE()));
            if (doc.getPAGO().getCONFIRMACIONPAGO() != null) {
                res.setFechaPago(stringToDate(
                        doc.getPAGO().getCONFIRMACIONPAGO().getFECHAPAGO()));
                res.setLocalizador(
                        doc.getPAGO().getCONFIRMACIONPAGO().getLOCALIZADOR());
            }

            return res;
        } catch (final UnsupportedEncodingException uee) {
            throw new XmlPagoException("Encoding UTF-8 no suportat", uee);
        } catch (final XmlException | ParseException e) {
            throw new XmlPagoException("Error al interpretar xml", e);
        }
    }

    /**
     * Serializa a XML.
     *
     * @return XML pago
     */
    public byte[] toXML(DatosSesionPago datosSesionPago) {
        try {
            final PAGODocument fd = PAGODocument.Factory.newInstance();
            final PAGO pago = fd.addNewPAGO();
            pago.setIDENTIFICADOR(datosSesionPago.getIdentificadorPago());
            final DATOSPAGO datosPago = pago.addNewDATOSPAGO();
            datosPago.setENTIDADID(datosSesionPago.getEntidadId());
            datosPago.setSIMULADO(datosSesionPago.isSimulado());
            datosPago.setPASARELAID(datosSesionPago.getPasarelaId());
            datosPago.setPRESENTACION(
                    datosSesionPago.getPresentacion().toString());
            datosPago.setIDIOMA(datosSesionPago.getIdioma());
            datosPago.setMODELO(datosSesionPago.getModelo());
            datosPago.setCONCEPTO(datosSesionPago.getConcepto());
            datosPago.setIMPORTE(datosSesionPago.getImporte());
            if (StringUtils.isNotBlank(datosSesionPago.getTasaId())) {
                datosPago.setTASA(datosSesionPago.getTasaId());
            }
            datosPago.setDETALLEPAGO(datosSesionPago.getDetallePago());
            final CONTRIBUYENTE contribuyente = datosPago.addNewCONTRIBUYENTE();
            contribuyente.setNIF(datosSesionPago.getSujetoPasivo().getNif());
            contribuyente
                    .setNOMBRE(datosSesionPago.getSujetoPasivo().getNombre());
            datosPago.setCONTRIBUYENTE(contribuyente);
            if (StringUtils.isNotBlank(datosSesionPago.getOrganismoId())) {
                datosPago.setORGANISMO(datosSesionPago.getOrganismoId());
            }
            if (datosSesionPago.getFechaPago() != null) {
                final CONFIRMACIONPAGO confirmacionPago = pago
                        .addNewCONFIRMACIONPAGO();
                confirmacionPago.setFECHAPAGO(
                        dateToString(datosSesionPago.getFechaPago()));
                confirmacionPago
                        .setLOCALIZADOR(datosSesionPago.getLocalizador());
            }
            byte[] xml = null;
            final XmlOptions xmlOptions = new XmlOptions();
            xmlOptions.setCharacterEncoding("UTF-8");
            xmlOptions.setSavePrettyPrint();
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
            fd.save(baos, xmlOptions);
            xml = baos.toByteArray();
            baos.close();
            return xml;
        } catch (final IOException e) {
            throw new XmlPagoException("Error al interpretar xml", e);
        }
    }

    /**
     * Crea sesion de pago estableciendo los datos de la sesion de pago.
     *
     * @param presentacion
     *            presentacion
     * @param idSesionTramitacion
     *            id sesion tramitacion
     * @param pDipa
     *            Datos internos paso
     * @param idPago
     *            idPago
     * @param pIdioma
     *            Parámetro idioma
     * @param pDefinicionTramite
     *            Definicion tramite
     * @param pDebug
     *            Debug
     * @return Sesion de pago
     */
    public DatosSesionPago crearSesionPagoPasarela(
            TypePresentacion presentacion, String idSesionTramitacion,
            final DatosInternosPasoPagar pDipa, final String idPago,
            final String pIdioma,
            final DefinicionTramiteSTG pDefinicionTramite) {

        // - Recuperamos datos del calculo de pago (refrescamos fecha para
        // forzar fecha actual)
        final DatosCalculoPago datosPago = pDipa.recuperarCalculoPago(idPago);
        datosPago.setFecha(UtilsFlujo.formateaFechaFront(new Date()));

        // Verifica si el pago es simulado
        final boolean pagoSimulado = UtilsSTG.isPagoSimulado(pDipa.getIdPaso(),
                idPago, pDefinicionTramite);

        // - Establecemos datos sesion de pago
        final DatosSesionPago datosSesionPago = new DatosSesionPago();
        datosSesionPago.setEntidadId(
                pDefinicionTramite.getDefinicionVersion().getIdEntidad());
        datosSesionPago.setPasarelaId(datosPago.getPasarelaId());
        datosSesionPago.setPresentacion(presentacion);
        datosSesionPago.setSimulado(pagoSimulado);
        datosSesionPago.setIdioma(pIdioma);
        datosSesionPago.setSujetoPasivo(datosPago.getContribuyente());
        datosSesionPago.setDetallePago("[" + idSesionTramitacion + "]");
        datosSesionPago.setModelo(datosPago.getModelo());
        datosSesionPago.setConcepto(datosPago.getConcepto());
        datosSesionPago.setTasaId(datosPago.getTasa());
        datosSesionPago.setImporte(datosPago.getImporte());
        datosSesionPago.setOrganismoId(datosPago.getOrganismo());

        return datosSesionPago;
    }

    /**
     * Convierte fecha en string.
     *
     * @param fecha
     *            Fecha
     * @return Fecha en formato string
     */
    private String dateToString(Date fecha) {
        return new SimpleDateFormat(FORMATO_FECHA).format(fecha);
    }

    /**
     * Convierte cadena en fecha.
     *
     * @param fecha
     *            fecha en formato string
     * @return fecha
     * @throws ParseException
     */
    private Date stringToDate(String fecha) throws ParseException {
        return new SimpleDateFormat(FORMATO_FECHA).parse(fecha);
    }
}
