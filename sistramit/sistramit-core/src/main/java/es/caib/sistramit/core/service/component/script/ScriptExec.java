package es.caib.sistramit.core.service.component.script;

import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;

/**
 *
 * Componente para ejecución de scripts (flujo y formularios).
 *
 * @author Indra
 *
 */
public interface ScriptExec {

	/**
	 * Ejecuta un script del flujo de tramitación.
	 *
	 * @param pTipoScript
	 *            Tipo de script a ejecutar. Se utiliza para generar el log.
	 * @param pIdElemento
	 *            En caso de que el script se aplique sobre un elemento
	 *            identificable se puede indicar el id (p.e. formulario, anexo,
	 *            etc.). Se utiliza para generar el log.
	 * @param pScript
	 *            Script a ejecutar
	 * @param pVariablesFlujo
	 *            Variables del flujo.
	 * @param pVariablesScript
	 *            Variables pasadas al script (depende del script).
	 * @param pDocumentosPaso
	 *            Documentos rellenados en el paso actual.
	 * @param pCodigosError
	 *            Códigos de error y su literal asociado.
	 * @param pDefinicionTramite
	 *            Definición trámite.
	 * @return Devuelve respuesta del script con la opción de indicar que se ha
	 *         producido un error lógico.
	 */
	RespuestaScript executeScriptFlujo(TypeScriptFlujo pTipoScript, String pIdElemento, String pScript,
			final VariablesFlujo pVariablesFlujo, final Map<String, Object> pVariablesScript,
			final List<DatosDocumento> pDocumentosPaso, final Map<String, String> pCodigosError,
			DefinicionTramiteSTG pDefinicionTramite);

	// TODO Pendiente ejecucion scripts en parte de formulario
}
