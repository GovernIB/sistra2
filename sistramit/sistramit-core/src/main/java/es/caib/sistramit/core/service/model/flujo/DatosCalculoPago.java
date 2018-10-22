package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.Persona;

/**
 * Datos calculados para realizar un pago via pasarela de pagos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosCalculoPago implements Serializable {

    /**
     * Contribuyente. Por defecto el usuario que realiza el trámite.
     */
    private Persona contribuyente;

    /**
     * Código tasa.
     */
    private String codigo;

    /**
     * Fecha devengo (dd/mm/yyyy hh:mi:ss).
     */
    private String fecha;

    /**
     * Modelo de pago.
     */
    private String modelo;

    /**
     * Concepto del pago.
     */
    private String concepto;

    /**
     * Importe (en cents).
     */
    private String importe;

    /**
     * Método de acceso a contribuyente.
     * 
     * @return contribuyente
     */
    public Persona getContribuyente() {
        return contribuyente;
    }

    /**
     * Método para establecer contribuyente.
     * 
     * @param contribuyente
     *            contribuyente a establecer
     */
    public void setContribuyente(Persona contribuyente) {
        this.contribuyente = contribuyente;
    }

    /**
     * Método de acceso a codigo.
     * 
     * @return codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para establecer codigo.
     * 
     * @param codigo
     *            codigo a establecer
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Método de acceso a fecha.
     * 
     * @return fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Método para establecer fecha.
     * 
     * @param fecha
     *            fecha a establecer
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Método de acceso a modelo.
     * 
     * @return modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Método para establecer modelo.
     * 
     * @param modelo
     *            modelo a establecer
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Método de acceso a concepto.
     * 
     * @return concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Método para establecer concepto.
     * 
     * @param concepto
     *            concepto a establecer
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * Método de acceso a importe.
     * 
     * @return importe
     */
    public String getImporte() {
        return importe;
    }

    /**
     * Método para establecer importe.
     * 
     * @param importe
     *            importe a establecer
     */
    public void setImporte(String importe) {
        this.importe = importe;
    }
}
