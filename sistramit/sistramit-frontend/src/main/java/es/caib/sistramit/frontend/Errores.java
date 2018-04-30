package es.caib.sistramit.frontend;

import es.caib.sistramit.frontend.model.RespuestaJSON;

/**
 *
 * Errores.
 *
 * @author Indra
 *
 */
public interface Errores {

	/**
	 * Genera respuesta json para error.
	 *
	 * @param ex
	 *            Exception
	 * @param idioma
	 *            Idioma
	 * @return Respuesta error
	 */
	RespuestaJSON generarRespuestaJsonExcepcion(final Exception ex, final String idioma);

}
