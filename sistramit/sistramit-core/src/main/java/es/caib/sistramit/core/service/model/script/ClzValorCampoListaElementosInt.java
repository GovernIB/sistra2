package es.caib.sistramit.core.service.model.script;

/**
 * Clase de acceso a un valor de un campo lista elementos desde los plugins.
 *
 * @author Indra
 *
 */
public interface ClzValorCampoListaElementosInt extends PluginClass {

	/**
	 * Añade elemento.
	 * 
	 * @return elemento
	 */
	ClzValorElementoInt addElemento();

	/**
	 * Obtiene numero elementos.
	 * 
	 * @return numero elementos.
	 */
	int getNumeroElementos();

}
