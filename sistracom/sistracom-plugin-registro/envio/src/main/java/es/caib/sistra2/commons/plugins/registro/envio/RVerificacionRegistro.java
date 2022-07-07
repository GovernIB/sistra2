package es.caib.sistra2.commons.plugins.registro.envio;

import java.io.Serializable;

import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeEstadoRegistro;

/**
 * Datos del resultado de un apunte registral
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class RVerificacionRegistro implements Serializable {

	public RVerificacionRegistro(TypeEstadoRegistro estado, ResultadoRegistro datosRegistro) {
		this.estado = estado;
		this.datosRegistro = datosRegistro;
	}

	public RVerificacionRegistro() {
		// VACIO
	}

	/** Indica estado registro. */
	private TypeEstadoRegistro estado;

	/**
	 * En caso de que el registro se haya realizado, indica los datos del registro.
	 */
	private ResultadoRegistro datosRegistro;

	/**
	 * Método de acceso a estado.
	 *
	 * @return estado
	 */
	public TypeEstadoRegistro getEstado() {
		return estado;
	}

	/**
	 * Método para establecer estado.
	 *
	 * @param estado estado a establecer
	 */
	public void setEstado(final TypeEstadoRegistro estado) {
		this.estado = estado;
	}

	/**
	 * Método de acceso a datosRegistro.
	 *
	 * @return datosRegistro
	 */
	public ResultadoRegistro getDatosRegistro() {
		return datosRegistro;
	}

	/**
	 * Método para establecer datosRegistro.
	 *
	 * @param datosRegistro datosRegistro a establecer
	 */
	public void setDatosRegistro(final ResultadoRegistro datosRegistro) {
		this.datosRegistro = datosRegistro;
	}

}
