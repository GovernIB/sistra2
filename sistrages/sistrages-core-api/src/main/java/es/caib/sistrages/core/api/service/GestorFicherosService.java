package es.caib.sistrages.core.api.service;

import es.caib.sistrages.core.api.model.ContenidoFichero;

/**
 * Gestor fichero Service.
 */
public interface GestorFicherosService {

	/**
	 * Purgar ficheros.
	 */
	void purgarFicheros();

	/**
	 * Obtener contenido fichero.
	 */
	ContenidoFichero obtenerContenidoFichero(Long id);

}
