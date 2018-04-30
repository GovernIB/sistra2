package es.caib.sistramit.frontend.literales;

import java.util.Properties;

/**
 * Componente para recuperar literales de la capa de negocio.
 *
 * El fichero de literales están estructurado por secciones para que los
 * diferentes elementos no compartan literales.
 *
 * @author Indra
 *
 */
public interface LiteralesFront {

	// Estructura del fichero de literales: cada elemento tiene sus literales.
	// Los literales de cada elemento
	// comenzarán con el prefijo correspondiente.

	/**
	 * Literales login (página de login).
	 */
	String LOGIN = "login";

	/**
	 * Literales aplicacion (literales empleados empleados en las páginas de la
	 * aplicación).
	 */
	String APLICACION = "aplicacion";

	/**
	 * Literales asistente (mensajes asistente).
	 */
	String ASISTENTE = "asistente";

	/**
	 * Literales excepciones.
	 */
	String EXCEPCIONES = "exception";

	/**
	 * Obtiene literales por sección.
	 *
	 * @param seccion
	 *            Parámetro seccion
	 * @param idioma
	 *            Idioma
	 * @return Literal
	 */
	Properties getLiteralesSeccion(String seccion, String idioma);

	/**
	 * Obtiene literal front.
	 *
	 * @param prefijo
	 *            Parámetro prefijo
	 * @param codigo
	 *            Codigo literal
	 * @param idioma
	 *            Idioma
	 * @return Literal
	 */
	String getLiteralFront(String prefijo, String codigo, String idioma);

	/**
	 * Obtiene literal.
	 *
	 * @param prefijo
	 *            Parámetro prefijo
	 * @param codigo
	 *            Codigo literal
	 * @param idioma
	 *            Idioma
	 * @param defecto
	 *            defecto si no se encuentra literal
	 * @return Literal
	 */
	String getLiteralFront(String prefijo, String codigo, String idioma, String defecto);

	/**
	 * Obtiene literal.
	 *
	 * @param elemento
	 *            Elemento
	 * @param codigo
	 *            Codigo literal
	 * @param parametros
	 *            Parámetros a substituir en el literal
	 * @param idioma
	 *            Idioma
	 * @return Literal
	 */
	String getLiteralFront(String elemento, String codigo, String[] parametros, String idioma);

}
