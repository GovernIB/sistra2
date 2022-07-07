package es.caib.sistra2.commons.plugins.registro.envio;

public class RInicioSesion {

	/**
	 * Id de la entidad
	 */
	private String idEntidad;

	/**
	 * Id del envio remoto
	 */
	private String idEnvioRemoto;

	/**
	 * @return  idEntidad
	 */
	public final String getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param para establecer el idEntidad
	 */
	public final void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	/**
	 * @return idEnvioRemoto
	 */
	public final String getIdEnvioRemoto() {
		return idEnvioRemoto;
	}

	/**
	 * @param para establecer el idEnvioRemoto
	 */
	public final void setIdEnvioRemoto(String idEnvioRemoto) {
		this.idEnvioRemoto = idEnvioRemoto;
	}



}
