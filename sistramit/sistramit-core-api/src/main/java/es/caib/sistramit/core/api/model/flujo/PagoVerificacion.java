package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Datos verificación pago.
 *
 * @author Indra
 *
 */
public final class PagoVerificacion {

    /** Indica si se ha podido verificar. */
    private TypeSiNo verificado;
    /** Indica si se ha realizado. */
    private TypeSiNo realizado;

    // TODO Puede ser necesario establecer datos pago para auditar

    /**
     * Método de acceso a verificado.
     * 
     * @return verificado
     */
    public TypeSiNo getVerificado() {
        return verificado;
    }

    /**
     * Método para establecer verificado.
     * 
     * @param verificado
     *            verificado a establecer
     */
    public void setVerificado(TypeSiNo verificado) {
        this.verificado = verificado;
    }

    /**
     * Método de acceso a realizado.
     * 
     * @return realizado
     */
    public TypeSiNo getRealizado() {
        return realizado;
    }

    /**
     * Método para establecer realizado.
     * 
     * @param realizado
     *            realizado a establecer
     */
    public void setRealizado(TypeSiNo realizado) {
        this.realizado = realizado;
    }
}
