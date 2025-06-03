package es.caib.sistramit.core.service.model.integracion;

import java.io.Serializable;

/**
 * Datos finalización digitalizacion.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DigitalizacionRespuesta implements Serializable {

	/** Indica si se ha finalizado correctamente en el componente cliente. */
	private boolean digitalizado;

	/** En caso correcto, indica nombre fichero. */
	private String ficheroNombre;

	/** En caso correcto, indica contenido digitalizado. */
	private byte[] ficheroContenido;

	/** En caso error, indica detalle error. */
	private String detalleError;

	/** En caso error, indica traza error. */
	private String trazaError;

	/** Id sesión digitalización. */
	private String idSesionDigitalizacion;

	public String getFicheroNombre() {
		return ficheroNombre;
	}

	public void setFicheroNombre(String ficheroNombre) {
		this.ficheroNombre = ficheroNombre;
	}

	public boolean isDigitalizado() {
		return digitalizado;
	}

	public void setDigitalizado(boolean digitalizado) {
		this.digitalizado = digitalizado;
	}

	public byte[] getFicheroContenido() {
		return ficheroContenido;
	}

	public void setFicheroContenido(byte[] ficheroContenido) {
		this.ficheroContenido = ficheroContenido;
	}

	public String getDetalleError() {
		return detalleError;
	}

	public void setDetalleError(String detalleError) {
		this.detalleError = detalleError;
	}

	public String getIdSesionDigitalizacion() {
		return idSesionDigitalizacion;
	}

	public void setIdSesionDigitalizacion(String idSesionDigitalizacion) {
		this.idSesionDigitalizacion = idSesionDigitalizacion;
	}

	public String getTrazaError() {
		return trazaError;
	}

	public void setTrazaError(String trazaError) {
		this.trazaError = trazaError;
	}
}
