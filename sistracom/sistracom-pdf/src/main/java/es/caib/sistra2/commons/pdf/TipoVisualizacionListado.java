package es.caib.sistra2.commons.pdf;

/**
 * Indica la visualización de un listado.
 *
 * @author slromero
 *
 */
public enum TipoVisualizacionListado {
	/** El codigo detras entre parentesis. **/
	SUFIJO_PARENTESIS,
	/** El codigo delante entre parentesis. **/
	PREFIJO_PARENTESIS,
	/** El codigo detras separado por un guion. **/
	SUFIJO_GUION,
	/** El codigo delante separado por un guion. **/
	PREFIJO_GUION,
	/** Solo el código. con salto de lineas. **/
	SOLO_CODIGO,
	/** Sólo el código con comas. **/
	SOLO_CODIGO_COMAS,
	/** Sólo la descripción, con salto de lineas. **/
	SOLO_DESCRIPCION,
	/** Sólo la descripción con comas. **/
	SOLO_DESCRIPCION_COMAS;
}
