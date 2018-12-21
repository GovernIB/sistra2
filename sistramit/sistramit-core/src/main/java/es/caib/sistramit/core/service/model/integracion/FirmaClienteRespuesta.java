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
	private boolean correcta;

	/** Indica si se ha cancelado. */
	private boolean cancelada;

	/** Indica fecha respuesta. */
	private Date fecha;

	/** En caso correcto, indica contenido firma. */
	private byte[] firmaContenido;

	/** En caso correcto, indica tipo firma. */
	private TypeFirmaDigital firmaTipo;

	/**
	 * Método de acceso a finalizada.
	 *
	 * @return finalizada
	 */
	public boolean isCorrecta() {
		return correcta;
	}

	/**
	 * Método para establecer finalizada.
	 *
	 * @param finalizada
	 *            finalizada a establecer
	 */
	public void setCorrecta(boolean finalizada) {
		this.correcta = finalizada;
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

}
