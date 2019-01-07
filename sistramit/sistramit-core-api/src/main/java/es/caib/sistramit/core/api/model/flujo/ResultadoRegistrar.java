package es.caib.sistramit.core.api.model.flujo;

import java.util.Date;

import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;

/**
 * Resultado de registrar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoRegistrar implements ModelApi {
	/**
	 * Resultado registro.
	 */
	private TypeResultadoRegistro resultado;
	/**
	 * Número registro.
	 */
	private String numeroRegistro;
	/**
	 * Fecha registro.
	 */
	private Date fechaRegistro;

	/**
	 * Método de acceso a resultado.
	 *
	 * @return resultado
	 */
	public TypeResultadoRegistro getResultado() {
		return resultado;
	}

	/**
	 * Método para establecer resultado.
	 *
	 * @param resultado
	 *            resultado a establecer
	 */
	public void setResultado(TypeResultadoRegistro resultado) {
		this.resultado = resultado;
	}

	/**
	 * Método de acceso a numeroRegistro.
	 *
	 * @return numeroRegistro
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Método para establecer numeroRegistro.
	 *
	 * @param numeroRegistro
	 *            numeroRegistro a establecer
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Método de acceso a fechaRegistro.
	 *
	 * @return fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Método para establecer fechaRegistro.
	 *
	 * @param fechaRegistro
	 *            fechaRegistro a establecer
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
