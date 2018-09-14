package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;

/**
 *
 * Información sobre un pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Pago extends Documento {

    /**
     * Tipo pago.
     */
    private TypePresentacion presentacion;

    /**
     * Detalle del estado cuando el pago es incorrecto.
     */
    private DetalleEstadoPagoIncorrecto estadoIncorrecto;

    /**
     * Crea clase PagoPasarela (para uso en bucles).
     *
     * @return el pago
     */
    public static Pago createNewPago() {
        return new Pago();
    }

    /**
     * Método de acceso a presentacion.
     *
     * @return presentacion
     */
    public TypePresentacion getPresentacion() {
        return presentacion;
    }

    /**
     * Método para establecer presentacion.
     *
     * @param presentacion
     *            presentacion a establecer
     */
    public void setPresentacion(TypePresentacion presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * Método de acceso a estadoIncorrecto.
     * 
     * @return estadoIncorrecto
     */
    public DetalleEstadoPagoIncorrecto getEstadoIncorrecto() {
        return estadoIncorrecto;
    }

    /**
     * Método para establecer estadoIncorrecto.
     * 
     * @param estadoIncorrecto
     *            estadoIncorrecto a establecer
     */
    public void setEstadoIncorrecto(
            DetalleEstadoPagoIncorrecto estadoIncorrecto) {
        this.estadoIncorrecto = estadoIncorrecto;
    }

}
