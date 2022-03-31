package es.caib.sistrages.frontend.model.types;

/**
 * Tipo para indicar parametros comunes para abrir una ventana.
 *
 * @author Indra
 *
 */
public enum TypeParametroVentana {

	/**
	 * Modo acceso.
	 */
	MODO_ACCESO,

	/**
	 * Id elemento accedido.
	 */
	ID,

	/**
	 * Dato
	 */
	DATO,

	/**
	 * Dato2
	 */
	DATO2,

	/**
	 * Ambito (Global, Entidad, Area)
	 */
	AMBITO,

	/**
	 * ENTIDAD
	 */
	ENTIDAD,

	/**
	 * AREA
	 */
	AREA,

	/**
	 * TRAMITE VERSION
	 */
	TRAMITEVERSION,

	/**
	 * campo fichero.
	 */
	CAMPO_FICHERO,

	/**
	 * Tipo script flujo para ventana script.
	 **/
	TIPO_SCRIPT_FLUJO,

	/**
	 * Tipo script formulario para ventana script.
	 **/
	TIPO_SCRIPT_FORMULARIO,

	/**
	 * tramite.
	 **/
	TRAMITE,

	/**
	 * Activa mochila.
	 */
	MOCHILA_ACTIVA,

	/**
	 * tramitepaso.
	 */
	TRAMITEPASO,
	/**
	 * Id del script
	 */
	ID_SCRIPT,
	/**
	 * Id del componente
	 */
	COMPONENTE,
	/**
	 * Nombre del componente
	 */
	COMPONENTE_NOMBRE,
	/**
	 * Página
	 */
	PAGINA,
	/**
	 * Obligatorios
	 */
	OBLIGATORIOS,
	/**
	 * IDIOMAS
	 */
	IDIOMAS,
	/**
	 * Formulario actual.
	 */
	FORMULARIO_ACTUAL,
	/**
	 * Formulario interno
	 */
	FORM_INTERNO_ACTUAL,
	/***
	 * Version
	 */
	VERSION,
	/**
	 * Indica si es opcional la entidad.
	 */
	ES_OPCIONAL,
	/**
	 * Modo importar (CC o IM)
	 */
	MODO_IMPORTAR,
	/**
	 * Caracteres no permitidos
	 */
	CARACT_NO_PERMITIDOS,
	/**
	 * DESACTIVA BOTONERA
	 */
	DESACTIVAR_BOTONERA,
	/**
	 * Caracteres no permitidos
	 */
	TAMANYO_MAX,
	/**
	 * Columnas
	 */
	COLS,
	/**
	 * Filas
	 */
	ROWS,
	/**
	 * Parámetro que indica si hay que poner el editor de texto en html
	 */
	LITERAL_HTML;
}
