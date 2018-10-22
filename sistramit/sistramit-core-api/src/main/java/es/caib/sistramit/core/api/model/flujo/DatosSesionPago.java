package es.caib.sistramit.core.api.model.flujo;

import java.util.Date;

/**
 * Datos almacenados en la sesión de pago.
 *
 * @author Indra
 *
 */
public class DatosSesionPago {

    /** Identificador pago. */
    private String identificadorPago;

    /** Fecha pago en caso de estar pagado. */
    private Date fechaPago;

    /** Identificador pago pasarela en caso de estar pagado. */
    private String localizador;

    // TODO Ver si hacen falta mas campos

    /**
     * Deserializa.
     *
     * @param content
     *            content
     * @return DatosSesionPago
     */
    public static DatosSesionPago deserializa(byte[] content) {
        // TODO Pendiente
        throw new RuntimeException("Pendiente implementar");
    }

    /**
     * Serializa.
     *
     * @return json
     */
    public byte[] serializa() {
        // TODO Pendiente
        throw new RuntimeException("Pendiente implementar");
    }

    /**
     * Método para establecer identificadorPago.
     *
     * @param identificadorPago
     *            identificadorPago a establecer
     */
    public void setIdentificadorPago(String identificadorPago) {
        this.identificadorPago = identificadorPago;
    }

    /**
     * Método de acceso a identificadorPago.
     *
     * @return identificadorPago
     */
    public String getIdentificadorPago() {
        return identificadorPago;
    }

    /**
     * Método de acceso a fechaPago.
     * 
     * @return fechaPago
     */
    public Date getFechaPago() {
        return fechaPago;
    }

    /**
     * Método para establecer fechaPago.
     * 
     * @param fechaPago
     *            fechaPago a establecer
     */
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * Método de acceso a localizador.
     * 
     * @return localizador
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Método para establecer localizador.
     * 
     * @param localizador
     *            localizador a establecer
     */
    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }
}
