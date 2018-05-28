package es.caib.sistramit.core.service.model.script;

/**
 * Clase que modeliza fichero.
 *
 * @author Indra
 *
 */
public interface ClzFicheroInt extends PluginClass {

	/**
	 * Nombre fichero con extensión.
	 *
	 * @return nombre fichero
	 */
	String getNombre();

	/**
	 * Descripción del fichero.
	 *
	 * @return descripcion fichero
	 */
	String getDescripcion();

	/**
	 * Nombre fichero con extensión.
	 *
	 * @param pNombre
	 *            nombre a establecer
	 */
	void setNombre(final String pNombre);

	/**
	 * Descripción fichero.
	 *
	 * @param pDescripcion
	 *            descripcion a establecer
	 */
	void setDescripcion(final String pDescripcion);

	/**
	 * Método de acceso a contenidoB64.
	 *
	 * @return contenidoB64
	 */
	String getContenidoB64();

	/**
	 * Método para establecer contenidoB64.
	 *
	 * @param pContenidoB64
	 *            contenidoB64 a establecer
	 */
	void setContenidoB64(final String pContenidoB64);

}
