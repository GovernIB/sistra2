package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Datos de destino del asiento
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DatosDestino implements Serializable {

	/** codigo de entidad destino del asiento registral */
	private String codigoEntidadRegistralDestino;

	public String getCodigoEntidadRegistralDestino() {
		return codigoEntidadRegistralDestino;
	}

	public void setCodigoEntidadRegistralDestino(String codigoEntidadRegistralDestino) {
		this.codigoEntidadRegistralDestino = codigoEntidadRegistralDestino;
	}


}
