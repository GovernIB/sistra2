package es.caib.sistramit.core.service.model.script.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.component.script.plugins.flujo.ClzAnexoDinamico;
import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * * Datos para establecer los anexos de forma dinámica indicando la siguiente
 * información por anexo: identificador, descripcion, extensiones, tamaño
 * maximo, url plantilla, obligatorio, convertir pdf y firmar.
 *
 * @author Indra
 *
 */
public interface ResAnexosDinamicosInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_ANEXOSDINAMICOS";

	/**
	 * Crear anexo dinámico para establecer sus propiedades.
	 * 
	 * @return anexo
	 */
	ClzAnexoDinamico crearAnexo();

	/**
	 * Añade un nuevo anexo.
	 *
	 * @param anexo
	 *                  anexo dinámico
	 * @throws ScriptException
	 *                             Excepcion
	 */
	void addAnexo(final ClzAnexoDinamico anexo) throws ScriptException;

}
