package es.caib.sistramit.core.service.component.integracion;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.service.repository.dao.AuditoriaDao;
import es.caib.sistramit.core.service.repository.dao.InvalidacionDao;
import es.caib.sistramit.core.service.repository.dao.PagoExternoDao;
import es.caib.sistramit.core.service.repository.dao.PurgaTramiteDao;

/**
 * Implementación purga component.
 *
 * @author Indra
 *
 */
@Component("purgaComponent")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public final class PurgaComponentImpl implements PurgaComponent {

	/**
	 * Atributo purga dao de PurgaComponentImpl.
	 */
	@Autowired
	private PurgaTramiteDao purgaDao;

	/**
	 * Atributo ticket dao de PurgaComponentImpl.
	 */
	@Autowired
	private PagoExternoDao pagoExternoDao;

	/**
	 * Atributo auditoria dao de PurgaComponentImpl.
	 */
	@Autowired
	private AuditoriaDao auditoriaDao;

	/** Atributo invalidacionDao. */
	@Autowired
	private InvalidacionDao invalidacionDao;

	@Override
	public int purgaErroresInternos(final Date fechaCaducidad) {
		int total = 0;
		if (fechaCaducidad != null) {
			total = auditoriaDao.deleteLogInterno(fechaCaducidad);
		}
		return total;
	}

	@Override
	public int purgarPagosExternosFinalizados(final Date fechaLimiteInicio) {
		return pagoExternoDao.purgarPagosExternosFinalizados(fechaLimiteInicio);
	}

	@Override
	public int purgarPagosExternosNoFinalizados(final Date fechaLimiteFin) {
		return pagoExternoDao.purgarPagosExternosNoFinalizados(fechaLimiteFin);
	}

	@Override
	public int purgarSesionesFormularioFinalizadas(final Date fechaLimiteInicio) {
		return purgaDao.purgarSesionesFormularioFinalizadas(fechaLimiteInicio);
	}

	@Override
	public int purgarSesionesFormularioNoFinalizadas(final Date fechaLimiteFin) {
		return purgaDao.purgarSesionesFormularioNoFinalizadas(fechaLimiteFin);
	}

	@Override
	public int purgarFicherosHuerfanos() {
		return purgaDao.purgarFicherosHuerfanos();
	}

	@Override
	public void purgarInvalidaciones(final Date pFechaHasta) {
		invalidacionDao.purgarInvalidaciones(pFechaHasta);
	}

	@Override
	public ListaPropiedades marcarPurgarTramites(final Date pFinalizadosHasta, final Date pSinFinalizarHasta,
			final Date pCaducadosHasta, final Date pPendientePurgaPagoRealizadoHasta) {
		return purgaDao.marcarPurgarTramites(pFinalizadosHasta, pSinFinalizarHasta, pCaducadosHasta,
				pPendientePurgaPagoRealizadoHasta);
	}

	@Override
	public int realizarPurgaTramitesMarcadosParaPurgar() {
		return purgaDao.realizarPurgaTramitesMarcadosParaPurgar();
	}

	@Override
	public int eliminarTramitesPurgados(final Date pFechaLimitePurga) {
		return purgaDao.eliminarTramitesPurgados(pFechaLimitePurga);
	}

}
