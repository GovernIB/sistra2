package es.caib.sistra2.commons.plugins.registro.envio;

public class REnvioRemoto {

	public REnvioRemoto() {
		// Constructor vacio
	}

	/**
	 * Asiento registral
	 */
	private RAsientoRegistral asiento;

	/**
	 * Id de sesion de envio
	 */
	private String idEnvio;

	public String getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(final String idEnvio) {
		this.idEnvio = idEnvio;
	}

	public final RAsientoRegistral getAsiento() {
		return asiento;
	}

	public final void setAsiento(RAsientoRegistral asiento) {
		this.asiento = asiento;
	}

}
