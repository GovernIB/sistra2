package es.caib.sistramit.frontend.model;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;

/**
 * Informacion debug sesion tramitación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DebugPurga implements Serializable {

	/**
	 * Id sesion tramitacion.
	 */
	private String idSesionTramitacion;

	/**
	 * Entorno.
	 */
	private String entorno;

	/**
	 * Id tramite.
	 */
	private String idTramite;

	/** Resultado. **/
	private ResultadoProcesoProgramado resultado;

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(final String entorno) {
		this.entorno = entorno;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the resultado
	 */
	public ResultadoProcesoProgramado getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(final ResultadoProcesoProgramado resultado) {
		this.resultado = resultado;
	}

	@Override
	public String toString() {
		final StringBuilder resultado = new StringBuilder();
		if (this.resultado == null || this.resultado.getDetalles() == null
				|| this.resultado.getDetalles().getPropiedades() == null) {
			resultado.append(" Resultado , detalles o propiedades a nulo ");
		} else {
			resultado.append("Detalles total : \n");
			for (final String key : this.resultado.getDetalles().getPropiedades().keySet()) {
				resultado.append(key);
				resultado.append(" - ");
				resultado.append(this.resultado.getDetalles().getPropiedad(key));
				resultado.append("  \n  ");
			}

		}
		return resultado.toString();
	}

}
