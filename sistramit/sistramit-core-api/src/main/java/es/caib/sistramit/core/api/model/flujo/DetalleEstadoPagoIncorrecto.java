package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoPagoIncorrecto;


/**
 * Detalle de un pago incorrecto.
 *
 * @author Indra
 */
@SuppressWarnings("serial")
public final class DetalleEstadoPagoIncorrecto implements Serializable {

    /**
     * Constructor.
     *
     * @param pEstado
     *            Estado incorrecto
     * @param pMensaje
     *            Mensaje
     */
    public DetalleEstadoPagoIncorrecto(final TypeEstadoPagoIncorrecto pEstado,
            final String pMensaje) {
        super();
        estado = pEstado;
        mensaje = pMensaje;
    }

    /**
     * Constructor.
     */
    public DetalleEstadoPagoIncorrecto() {
        super();
    }

    /**
     * Detalle del estado cuando el pago es incorrecto.
     */
    private TypeEstadoPagoIncorrecto estado;

    /**
     * Mensaje descriptivo del estado.
     */
    private String mensaje;

    /**
     * Código error pasarela pagos.
     */
    private String codigoErrorPasarela;

    /**
     * Mensaje error pasarela pagos.
     */
    private String mensajeErrorPasarela;

    /**
     * Método de acceso a estado.
     *
     * @return estado
     */
    public TypeEstadoPagoIncorrecto getEstado() {
        return estado;
    }

    /**
     * Método para establecer estado.
     *
     * @param pEstado
     *            estado a establecer
     */
    public void setEstado(final TypeEstadoPagoIncorrecto pEstado) {
        estado = pEstado;
    }

    /**
     * Método de acceso a mensaje.
     *
     * @return mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Método para establecer mensaje.
     *
     * @param pMensaje
     *            mensaje a establecer
     */
    public void setMensaje(final String pMensaje) {
        mensaje = pMensaje;
    }

	public String getCodigoErrorPasarela() {
		return codigoErrorPasarela;
	}

	public void setCodigoErrorPasarela(String codigoErrorPasarela) {
		this.codigoErrorPasarela = codigoErrorPasarela;
	}

	public String getMensajeErrorPasarela() {
		return mensajeErrorPasarela;
	}

	public void setMensajeErrorPasarela(String mensajeErrorPasarela) {
		this.mensajeErrorPasarela = mensajeErrorPasarela;
	}
}
