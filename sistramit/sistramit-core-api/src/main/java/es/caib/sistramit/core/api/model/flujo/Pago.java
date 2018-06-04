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
     * Indica detalle del estado cuando el pago es incorrecto.
     *
     * @return detalle del estado cuando el pago es incorrecto
     */
    public DetalleEstadoPagoIncorrecto getEstadoIncorrecto() {
        return estadoIncorrecto;
    }

    /**
     * Indica detalle del estado cuando el pago es incorrecto.
     *
     * @param pEstadoIncorrecto
     *            detalle del estado cuando el pago es incorrecto
     */
    public void setEstadoIncorrecto(
            final DetalleEstadoPagoIncorrecto pEstadoIncorrecto) {
        estadoIncorrecto = pEstadoIncorrecto;
    }

	public TypePresentacion getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(TypePresentacion presentacion) {
		this.presentacion = presentacion;
	}
}
