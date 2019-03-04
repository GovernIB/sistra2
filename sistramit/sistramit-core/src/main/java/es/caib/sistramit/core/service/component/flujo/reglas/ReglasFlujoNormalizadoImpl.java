package es.caib.sistramit.core.service.component.flujo.reglas;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import es.caib.sistramit.core.service.model.flujo.types.TypeFaseEjecucion;

/**
 * Reglas del flujo de tramitación normalizado.
 *
 * @author Indra
 *
 */
@Component("reglasFlujoNormalizado")
public final class ReglasFlujoNormalizadoImpl extends ReglasFlujoImpl {

	/**
	 * Constructor.
	 */
	@PostConstruct
	public void inicializarReglas() {

		// Establecemos reglas

		// INICIAR TRAMITE
		this.addRegla(new RTPasosInicialesNormalizado(), TypeFaseEjecucion.PRE_INICIO);

		// CARGAR TRAMITE
		this.addRegla(new RTBloqueoPasos(), TypeFaseEjecucion.POST_CARGA);

		// IR A PASO
		// - PRE Navegación
		this.addRegla(new RTNavegacionPagoIniciado(), TypeFaseEjecucion.PRE_NAVEGACION);
		this.addRegla(new RTNavegacionRegistroPendienteReintento(), TypeFaseEjecucion.PRE_NAVEGACION);
		this.addRegla(new RTNavegacionPasoFinal(), TypeFaseEjecucion.PRE_NAVEGACION);
		// - POST Navegación
		this.addRegla(new RTNavegacionAccesibilidadNormalizado(), TypeFaseEjecucion.POST_NAVEGACION);

		// ACCION PASO
		// - PRE Acción
		this.addRegla(new RTAccionModificacion(), TypeFaseEjecucion.PRE_ACCION);
		// - POST Acción
		this.addRegla(new RTNavegacionAccesibilidadNormalizado(), TypeFaseEjecucion.POST_ACCION);
		this.addRegla(new RTBloqueoPasos(), TypeFaseEjecucion.POST_ACCION);
		this.addRegla(new RTAccionNavegacionAutoNormalizado(), TypeFaseEjecucion.POST_ACCION);

		// CANCELAR
		this.addRegla(new RTCancelacionPagoIniciado(), TypeFaseEjecucion.PRE_CANCELAR);

	}
}
