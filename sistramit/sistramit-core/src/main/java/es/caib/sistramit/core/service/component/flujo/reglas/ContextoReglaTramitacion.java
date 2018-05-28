package es.caib.sistramit.core.service.component.flujo.reglas;

import es.caib.sistramit.core.service.ApplicationContextProvider;
import es.caib.sistramit.core.service.component.flujo.ModificacionesFlujo;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Acceso al contexto de una regla de tramitación. Habilita acceso a la lógica
 * del flujo desde la regla.
 *
 * @author Indra
 *
 */
public final class ContextoReglaTramitacion {

	/**
	 * Lógica de modificación del flujo.
	 */
	private final ModificacionesFlujo modificacionesFlujo;

	/**
	 * Datos de la sesión de tramitación.
	 */
	private final DatosSesionTramitacion datosSesion;

	/**
	 * Indica si se debe redirigir hacia el siguiente paso (solo para fase post
	 * accion paso).
	 */
	private boolean pasarSiguientePaso;

	/**
	 * Constructor.
	 *
	 * @param pDatosSesion
	 *            Datos de la sesión de tramitación.
	 */
	public ContextoReglaTramitacion(final DatosSesionTramitacion pDatosSesion) {
		super();
		datosSesion = pDatosSesion;
		modificacionesFlujo = (ModificacionesFlujo) ApplicationContextProvider.getApplicationContext()
				.getBean("modificacionesFlujo");
	}

	/**
	 * Definición trámite.
	 *
	 * @return Definición trámite.
	 */
	public DefinicionTramiteSTG getDefinicionTramite() {
		return this.datosSesion.getDefinicionTramite();
	}

	// TODO Pendiente analizar metodos necesarios

}
