package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Datos registrales.
 */
@SuppressWarnings("serial")
public final class DatosRegistrales implements Serializable {

	/** Entidad. */
	private String codigoEntidad;

	/** codigo de la unidad sobre la que se realizara el asiento */
	private String codigoOrganoDestino;

	/** Oficina. */
	private String oficina;

	/** Libro. */
	private String libro;

	/** Expediente (para tramites continuacion). */
	private String numeroExpediente;

	/** texto Expone (para solicitudes genericas). */
	private String textoExpone;

	/** texto Solicita (para solicitudes genericas). */
	private String textoSolicita;

	/** extracto del asiento */
	private String extracto;

	/**
	 * Método de acceso a libro.
	 *
	 * @return libro
	 */
	public String getLibro() {
		return libro;
	}

	/**
	 * Método para establecer libro.
	 *
	 * @param libro
	 *                  libro a establecer
	 */
	public void setLibro(final String libro) {
		this.libro = libro;
	}

	/**
	 * Método de acceso a numeroExpediente.
	 *
	 * @return numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	/**
	 * Método para establecer numeroExpediente.
	 *
	 * @param numeroExpediente
	 *                             numeroExpediente a establecer
	 */
	public void setNumeroExpediente(final String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	/**
	 * Método de acceso a oficina.
	 *
	 * @return oficina
	 */
	public String getOficina() {
		return oficina;
	}

	/**
	 * Método para establecer oficina.
	 *
	 * @param oficina
	 *                    oficina a establecer
	 */
	public void setOficina(final String oficina) {
		this.oficina = oficina;
	}

	/**
	 * Método de acceso a textoExpone.
	 *
	 * @return textoExpone
	 */
	public String getTextoExpone() {
		return textoExpone;
	}

	/**
	 * Método para establecer textoExpone.
	 *
	 * @param textoExpone
	 *                        textoExpone a establecer
	 */
	public void setTextoExpone(final String textoExpone) {
		this.textoExpone = textoExpone;
	}

	/**
	 * Método de acceso a textoSolicita.
	 *
	 * @return textoSolicita
	 */
	public String getTextoSolicita() {
		return textoSolicita;
	}

	/**
	 * Método para establecer textoSolicita.
	 *
	 * @param textoSolicita
	 *                          textoSolicita a establecer
	 */
	public void setTextoSolicita(final String textoSolicita) {
		this.textoSolicita = textoSolicita;
	}

	/**
	 * Método de acceso a codigoOrganoDestino.
	 *
	 * @return codigoOrganoDestino
	 */
	public String getCodigoOrganoDestino() {
		return codigoOrganoDestino;
	}

	/**
	 * Método para establecer codigoOrganoDestino.
	 *
	 * @param codigoOrganoDestino
	 *                                codigoOrganoDestino a establecer
	 */
	public void setCodigoOrganoDestino(final String codigoOrganoDestino) {
		this.codigoOrganoDestino = codigoOrganoDestino;
	}

	/**
	 * Método de acceso a extracto.
	 *
	 * @return extracto
	 */
	public String getExtracto() {
		return extracto;
	}

	/**
	 * Método para establecer extracto.
	 *
	 * @param extracto
	 *                     extracto a establecer
	 */
	public void setExtracto(final String extracto) {
		this.extracto = extracto;
	}

	/**
	 * Imprime contenido en un String.
	 *
	 * @return contenido en un String
	 */
	public String print() {
		String res = "Datos registrales: ";
		res += " - Oficina: " + this.getOficina() + "\n";
		res += " - Libro: " + this.getLibro() + "\n";
		res += " - Organo destino: " + this.getCodigoOrganoDestino() + "\n";
		res += " - N.Expediente: " + this.getNumeroExpediente() + "\n";
		res += " - Expone: " + this.getTextoExpone() + "\n";
		res += " - Solicita: " + this.getTextoSolicita() + "\n";
		res += " - Extracto: " + this.getExtracto() + "\n";
		return res;
	}

	/**
	 * Método de acceso a codigoEntidad.
	 * 
	 * @return codigoEntidad
	 */
	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	/**
	 * Método para establecer codigoEntidad.
	 * 
	 * @param codigoEntidad
	 *                          codigoEntidad a establecer
	 */
	public void setCodigoEntidad(final String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}

}
