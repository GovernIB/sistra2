package es.caib.sistra2.commons.plugins.registro.envio;

import java.util.Map;

import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.VerificacionRegistro;

/**
 * Utilidades.
 *
 * @author Indra
 *
 */
public class Utilidades {

	/** Constructor. **/
	private Utilidades() {
		// Constructor vacio
	}

	/**
	 * Convierte los datos de registro a ResultadoRegistro
	 *
	 * @param rvaloresDominio
	 * @return
	 */
	public static ResultadoRegistro getResultadoRegistro(final RResultadoRegistro rRes) {
		ResultadoRegistro res = null;
		if (rRes != null) {
			res = new ResultadoRegistro();
			res.setFechaRegistro(rRes.getFechaRegistro());
			res.setNumeroRegistro(rRes.getNumeroRegistro());
		}
		return res;
	}

	public static VerificacionRegistro getValidacion(final RVerificacionRegistro rv) {
		VerificacionRegistro v = null;
		if (rv != null) {
			v = new VerificacionRegistro();
			v.setDatosRegistro(rv.getDatosRegistro());
			v.setEstado(rv.getEstado());
		}
		return v;
	}
}
