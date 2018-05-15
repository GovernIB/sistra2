package es.caib.sistramit.core.service.component.flujo.pasos;

import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramite;

/**
 * Acción de un paso de tramitación.
 *
 * @author Indra
 *
 */
public interface AccionPaso {

	/**
	 * Ejecuta la acción del paso de tramitación.
	 *
	 * @param pDatosPaso
	 *            Datos paso
	 * @param pDpp
	 *            Datos persistencia del paso
	 * @param pAccionPaso
	 *            Acción paso a ejecutar
	 * @param pParametros
	 *            Parametros de la acción
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @return Respuesta de la ejecución del paso
	 */
	RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramite pDefinicionTramite, final VariablesFlujo pVariablesFlujo);

}
