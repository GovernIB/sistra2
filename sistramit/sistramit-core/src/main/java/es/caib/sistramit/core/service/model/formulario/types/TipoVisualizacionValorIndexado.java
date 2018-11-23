package es.caib.sistramit.core.service.model.formulario.types;

/**
 * Indica la visualización de un listado.
 *
 * @author Indra
 *
 */
public enum TipoVisualizacionValorIndexado {
	/** descripcion (codigo). **/
	DESCRIPCION_CODIGO_CON_PARENTESIS,
	/** (codigo) descripcion. **/
	CODIGO_DESCRIPCION_CON_PARENTESIS,
	/** codigo - descripcion. **/
	CODIGO_DESCRIPCION_CON_GUION,
	/** descripcion - codigo. **/
	DESCRIPCION_CODIGO_CON_GUION,
	/** Sólo la descripción, con salto de lineas. **/
	DESCRIPCION;
}
