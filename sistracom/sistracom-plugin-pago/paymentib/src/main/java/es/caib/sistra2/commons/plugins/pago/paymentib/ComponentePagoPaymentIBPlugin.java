package es.caib.sistra2.commons.plugins.pago.paymentib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.caib.paymentib.rest.api.v1.RDatosInicioPago;
import es.caib.paymentib.rest.api.v1.RDatosPago;
import es.caib.paymentib.rest.api.v1.REstadoPago;
import es.caib.sistra2.commons.plugins.pago.api.DatosPago;
import es.caib.sistra2.commons.plugins.pago.api.EstadoPago;
import es.caib.sistra2.commons.plugins.pago.api.IComponentePagoPlugin;
import es.caib.sistra2.commons.plugins.pago.api.PagoPluginException;
import es.caib.sistra2.commons.plugins.pago.api.RedireccionPago;
import es.caib.sistra2.commons.plugins.pago.api.TypeEstadoPago;

/**
 * Clase componente pago de paymentib.
 *
 * @author Indra
 *
 */
public class ComponentePagoPaymentIBPlugin extends AbstractPluginProperties
        implements IComponentePagoPlugin {

    /** Prefix. */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "paymentib.";

    /**
     * Inicia pago electrónico.
     *
     * @param datosPago
     *            Datos pago.
     * @param urlCallback
     *            Url callback.
     * @return Redirección al pago (identificador pago + url)
     */
    @Override
    public RedireccionPago iniciarPagoElectronico(final DatosPago datosPago,
            final String urlCallback) throws PagoPluginException {

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(
                getPropiedad("usr"), getPropiedad("pwd")));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final RDatosInicioPago datos = new RDatosInicioPago();
        final RDatosPago rdatosPago = new RDatosPago();
        rdatosPago.setAplicacionId(getPropiedad("idAplicacion"));
        rdatosPago.setConcepto(datosPago.getConcepto());
        rdatosPago.setDetallePago(datosPago.getDetallePago());
        rdatosPago.setEntidadId(datosPago.getEntidadId());
        rdatosPago.setIdioma(datosPago.getIdioma());
        rdatosPago.setImporte(datosPago.getImporte());
        rdatosPago.setModelo(datosPago.getModelo());
        rdatosPago.setOrganismoId(datosPago.getOrganismoId());
        rdatosPago.setPasarelaId(datosPago.getPasarelaId());
        rdatosPago.setSujetoPasivoNif(datosPago.getSujetoPasivoNif());
        rdatosPago.setSujetoPasivoNombre(datosPago.getSujetoPasivoNombre());
        rdatosPago.setTasaId(datosPago.getTasaId());
        datos.setDatosPago(rdatosPago);
        datos.setUrlCallback(urlCallback);

        final HttpEntity<RDatosInicioPago> request = new HttpEntity<>(datos,
                headers);
        final ResponseEntity<RedireccionPago> response = restTemplate
                .postForEntity(getPropiedad("url") + "/iniciarPagoElectronico/",
                        request, RedireccionPago.class);

        return response.getBody();
    }

    /**
     * Verifica estado pago contra pasarela de pago.
     *
     * @param identificador
     *            identificador pago
     * @return estado pago
     */
    @Override
    public EstadoPago verificarPagoElectronico(final String identificador)
            throws PagoPluginException {

        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(
                getPropiedad("usr"), getPropiedad("pwd")));

        final REstadoPago resRest = restTemplate
                .getForObject(getPropiedad("url") + "/verificarPagoElectronico/"
                        + identificador, REstadoPago.class);

        final EstadoPago res = new EstadoPago();
        res.setEstado(TypeEstadoPago.fromString(resRest.getEstado()));
        res.setFechaPago(deformateaFecha(resRest.getFechaPago()));
        res.setLocalizador(resRest.getLocalizador());
        res.setCodigoErrorPasarela(resRest.getCodigoErrorPasarela());
        res.setMensajeErrorPasarela(resRest.getMensajeErrorPasarela());
        return res;

    }

    /**
     * Obtiene justificante de pago
     *
     * @param identificador
     *            identificador pago
     * @return Justificante de pago (nulo si la pasarela no genera
     *         justificante).
     */
    @Override
    public byte[] obtenerJustificantePagoElectronico(final String identificador)
            throws PagoPluginException {

        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(
                getPropiedad("usr"), getPropiedad("pwd")));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        // Obtener procedimiento.
        final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(
                map, headers);
        final ResponseEntity<byte[]> response = restTemplate.postForEntity(
                getPropiedad("url") + "/obtenerJustificantePagoElectronico/"
                        + identificador,
                requestProc, byte[].class);

        return response.getBody();

    }

    /**
     * Obtiene importe tasa.
     *
     * @param idTasa
     *            id tasa
     * @return importe (en cents)
     * @throws PagoPluginException
     */
    @Override
    public int consultaTasa(final String idPasarela, final String idTasa)
            throws PagoPluginException {

        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(
                getPropiedad("usr"), getPropiedad("pwd")));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        // Obtener procedimiento.
        final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(
                map, headers);
        final ResponseEntity<Integer> response = restTemplate
                .postForEntity(
                        getPropiedad("url") + "/consultaTasa/" + idPasarela
                                + "/" + idTasa + "/",
                        requestProc, Integer.class);

        return response.getBody();
    }

    /**
     * Obtiene carta de pago presencial (PDF).
     *
     * @param datosPago
     *            Datos pago
     * @return carta de pago presencial
     */
    @Override
    public byte[] obtenerCartaPagoPresencial(final DatosPago datosPago)
            throws PagoPluginException {
        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(
                getPropiedad("usr"), getPropiedad("pwd")));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("aplicacionId", getPropiedad("idAplicacion"));
        map.add("concepto", datosPago.getConcepto());
        map.add("detallePago", datosPago.getDetallePago());
        map.add("entidadId", datosPago.getEntidadId());
        map.add("idioma", datosPago.getIdioma());
        map.add("importe", String.valueOf(datosPago.getImporte()));
        map.add("modelo", datosPago.getModelo());
        map.add("organismoId", datosPago.getOrganismoId());
        map.add("pasarelaId", datosPago.getPasarelaId());
        map.add("sujetoPasivoNif", datosPago.getSujetoPasivoNif());
        map.add("sujetoPasivoNombre", datosPago.getSujetoPasivoNombre());
        map.add("tasaId", datosPago.getTasaId());

        // Obtener procedimiento.
        final HttpEntity<MultiValueMap<String, String>> requestProc = new HttpEntity<>(
                map, headers);
        final ResponseEntity<byte[]> response = restTemplate.postForEntity(
                getPropiedad("url") + "/obtenerCartaPagoPresencial/",
                requestProc, byte[].class);

        return response.getBody();
    }

    /**
     * Obtiene propiedad.
     *
     * @param propiedad
     *            propiedad
     * @return valor
     * @throws FirmaPluginException
     */
    private String getPropiedad(String propiedad) throws PagoPluginException {
        final String res = getProperty(
                PAGO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
        if (res == null) {
            throw new PagoPluginException("No se ha especificado parametro "
                    + propiedad + " en propiedades");
        }
        return res;
    }

    private Date deformateaFecha(final String pFecha)
            throws PagoPluginException {
        Date res = null;
        if (pFecha != null) {
            final SimpleDateFormat df = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm:ss");
            df.setLenient(false);
            try {
                res = df.parse(pFecha);
                return res;
            } catch (final ParseException e) {
                throw new PagoPluginException(
                        "Error al interpretar fecha: " + pFecha, e);
            }
        }
        return res;
    }
}
