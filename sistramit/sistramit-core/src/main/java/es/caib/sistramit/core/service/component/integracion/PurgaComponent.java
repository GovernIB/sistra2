package es.caib.sistramit.core.service.component.integracion;

import java.util.Date;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;

/**
 * Acceso a componente Purga
 *
 * @author Indra
 *
 */
public interface PurgaComponent {

	public int purgaErroresInternos(final Date fechaCaducidad);

	public int purgarPagosExternosFinalizados(final Date fechaLimiteInicio);

	public int purgarPagosExternosNoFinalizados(final Date fechaLimiteFin);

	public int purgarSesionesFormularioFinalizadas(final Date fechaLimiteInicio);

	public int purgarSesionesFormularioNoFinalizadas(final Date fechaLimiteFin);

	public int purgarFicherosHuerfanos();

	public void purgarInvalidaciones(final Date pFechaHasta);

	public ListaPropiedades marcarPurgarTramites(final Date pFinalizadosHasta, final Date pSinCaducidadHasta,
			final Date pSinFinalizarHasta, final Date pCaducadosHasta, final Date pPendientePurgaPagoRealizadoHasta);

	public int realizarPurgaTramitesMarcadosParaPurgar();

	public int eliminarTramitesPurgados(final Date pFechaLimitePurga);

}
