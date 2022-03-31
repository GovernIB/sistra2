package es.caib.sistrages.core.api.model.types;

/**
 * Indica el tipo de acción. El enum se utiliza para cuando se procede a guardar
 * un componente. <br/ > Son las acciones de la barra superior e inferior del
 * dialog de diseño.
 *
 * @author Indra
 *
 */
public enum TypeAccionFormulario {

	/** Propiedades del formulario **/
	PROPIEDADES_FORMULARIO,
	/** Agregar linea **/
	ADD_LINEA,
	/** Agregar seccion **/
	ADD_SECCION,
	/** Agregar aviso **/
	ADD_ETIQUETA,
	/** Agregar textbox **/
	ADD_TEXTO,
	/**
	 * add oculto.
	 */
	ADD_OCULTO,
	/** Agregar select **/
	ADD_SELECTOR,
	/** Agregar checbox **/
	ADD_CHECKBOX,
	/** Agregar captcha **/
	ADD_CAPTCHA,
	/** Copiar componente **/
	COPIAR,
	/** Cortar componente **/
	CORTAR,
	/** Pegar componente **/
	PEGAR,
	/** Mover componente a la izquierda **/
	MOVER_IZQ,
	/** Mover componente a la derecha **/
	MOVER_DER,
	/** Ver página anterior **/
	PAG_ANT,
	/** Ver página siguiente **/
	PAG_NEXT,
	/** Ver estructura **/
	VER_ESTRUCTURA,
	/** Previsualizar **/
	PREVISUALIZAR,
	/** Cerrar **/
	CERRAR,
	/** Seleccionar otro componente para editar. **/
	SELECCIONAR_OTRO_COMPONENTE;

}
