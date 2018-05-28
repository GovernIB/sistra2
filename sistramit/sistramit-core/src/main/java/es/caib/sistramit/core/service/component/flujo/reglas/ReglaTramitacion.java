package es.caib.sistramit.core.service.component.flujo.reglas;

/**
 * Regla de tramitación. Sirve para particularizar comportamientos entre flujo
 * normalizado y personalizado.
 *
 * @author Indra
 *
 */
public interface ReglaTramitacion {

	/**
	 * Ejecuta regla.
	 *
	 * @param ctx
	 *            Contexto tramitación.
	 * @param vars
	 *            Variables que dependen de la fase de ejecución de la regla.
	 * @return Indica si la regla se ha ejecutado correctamente.
	 */
	boolean execute(ContextoReglaTramitacion ctx, Object[] vars);

}
