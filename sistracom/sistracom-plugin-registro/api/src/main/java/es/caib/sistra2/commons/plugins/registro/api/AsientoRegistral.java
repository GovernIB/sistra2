package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;

/**
 * Datos del asiento registral
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class AsientoRegistral implements Serializable {

    /** Datos origen del asiento */
    private DatosOrigen datosOrigen;

	public DatosOrigen getDatosOrigen() {
		return datosOrigen;
	}

	public void setDatosOrigen(DatosOrigen datosOrigen) {
		this.datosOrigen = datosOrigen;
	}


}
