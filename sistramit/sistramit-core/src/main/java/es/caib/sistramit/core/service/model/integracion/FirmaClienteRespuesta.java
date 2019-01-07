package es.caib.sistramit.core.service.model.integracion;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.service.model.flujo.types.TypeFirmaDigital;

/**
 * Datos finalización firma cliente.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class FirmaClienteRespuesta implements Serializable {

	/** Indica si se ha finalizado correctamente en el componente cliente. */
	private boolean finalizada;

	/** Indica si se ha cancelado. */
	private boolean cancelada;

	/** Indica si se ha validado correctamente la firma. */
	private boolean valida;

	/** Indica fecha respuesta. */
	private Date fecha;

	/** En caso correcto, indica contenido firma. */
	private byte[] firmaContenido;

	/** En caso correcto, indica tipo firma. */
	private TypeFirmaDigital firmaTipo;

	/** En caso error, indica detalle error. */
	private String detalleError;

	/**
	 * Método de acceso a finalizada.
	 *
	 * @return finalizada
	 */
	public boolean isFinalizada() {
		return finalizada;
	}

	/**
	 * Método para establecer finalizada.
	 *
	 * @param finalizada
	 *            finalizada a establecer
	 */
	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

	/**
	 * Método de acceso a cancelada.
	 *
	 * @return cancelada
	 */
	public boolean isCancelada() {
		return cancelada;
	}

	/**
	 * Método para establecer cancelada.
	 *
	 * @param cancelada
	 *            cancelada a establecer
	 */
	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}

	/**
	 * Método de acceso a firmaContenido.
	 *
	 * @return firmaContenido
	 */
	public byte[] getFirmaContenido() {
		return firmaContenido;
	}

	/**
	 * Método para establecer firmaContenido.
	 *
	 * @param firmaContenido
	 *            firmaContenido a establecer
	 */
	public void setFirmaContenido(byte[] firmaContenido) {
		this.firmaContenido = firmaContenido;
	}

	/**
	 * Método de acceso a firmaTipo.
	 *
	 * @return firmaTipo
	 */
	public TypeFirmaDigital getFirmaTipo() {
		return firmaTipo;
	}

	/**
	 * Método para establecer firmaTipo.
	 *
	 * @param firmaTipo
	 *            firmaTipo a establecer
	 */
	public void setFirmaTipo(TypeFirmaDigital firmaTipo) {
		this.firmaTipo = firmaTipo;
	}

	/**
	 * Método de acceso a fecha.
	 *
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Método para establecer fecha.
	 *
	 * @param fecha
	 *            fecha a establecer
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Método de acceso a detalleError.
	 *
	 * @return detalleError
	 */
	public String getDetalleError() {
		return detalleError;
	}

	/**
	 * Método para establecer detalleError.
	 *
	 * @param detalleError
	 *            detalleError a establecer
	 */
	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}

	/**
	 * Método de acceso a valida.
	 * 
	 * @return valida
	 */
	public boolean isValida() {
		return valida;
	}

	/**
	 * Método para establecer valida.
	 * 
	 * @param valida
	 *            valida a establecer
	 */
	public void setValida(boolean valida) {
		this.valida = valida;
	}

}
