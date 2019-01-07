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
		this.addRegla(new RTNavegacionAccesibilidadNormalizado(), TypeFaseEjecucion.POST_NAVEGACION);

		// ACCION PASO
		this.addRegla(new RTAccionModificacion(), TypeFaseEjecucion.PRE_ACCION);
		this.addRegla(new RTNavegacionAccesibilidadNormalizado(), TypeFaseEjecucion.POST_ACCION);
		this.addRegla(new RTBloqueoPasos(), TypeFaseEjecucion.POST_ACCION);
		this.addRegla(new RTAccionNavegacionAutoNormalizado(), TypeFaseEjecucion.POST_ACCION);

		// CANCELAR
		this.addRegla(new RTCancelacionPagoIniciado(), TypeFaseEjecucion.PRE_CANCELAR);

	}
}
