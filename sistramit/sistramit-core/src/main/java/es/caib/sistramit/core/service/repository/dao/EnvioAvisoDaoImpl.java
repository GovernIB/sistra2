package es.caib.sistramit.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import es.caib.sistramit.core.service.model.system.EnvioAviso;
import es.caib.sistramit.core.service.repository.model.HEnvio;

/**
 * Implementación DAO Envio.
 *
 * @author Indra
 */
@Repository("envioDao")
public final class EnvioAvisoDaoImpl implements EnvioAvisoDao {

	@Value("${envios.reintentos}")
	private Integer reintentos;

	/** Entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addEnvio(final EnvioAviso envio) {
		final HEnvio h = HEnvio.fromModel(envio);
		h.setFechaCreacion(new Date());
		h.setFechaReintento(null);
		h.setFechaEnvio(null);
		h.setMensajeError(null);
		entityManager.persist(h);
	}

	@Override
	public void guardarCorrecto(final Long idEnvio) {
		final HEnvio henvio = entityManager.find(HEnvio.class, idEnvio);
		henvio.setFechaEnvio(new Date());
		henvio.setFechaReintento(null);
		henvio.setMensajeError(null);
		entityManager.merge(henvio);
	}

	@Override
	public void errorEnvio(final Long idEnvio, final String mensaje) {
		final HEnvio henvio = entityManager.find(HEnvio.class, idEnvio);
		henvio.setFechaReintento(new Date());
		henvio.setMensajeError(mensaje);
		entityManager.merge(henvio);
	}

	@Override
	public List<EnvioAviso> listaInmediatos() {
		final String sql = "SELECT e FROM HEnvio e WHERE e.fechaEnvio is null and e.fechaReintento is null";
		final Query query = entityManager.createQuery(sql);
		final List<EnvioAviso> envios = new ArrayList<>();
		final List<HEnvio> henvios = query.getResultList();
		if (henvios != null) {
			for (final HEnvio henvio : henvios) {
				envios.add(henvio.toModel());
			}
		}
		return envios;
	}

	@Override
	public List<EnvioAviso> listaReintentos() {
		final String sql = "SELECT e FROM HEnvio e WHERE e.fechaEnvio is null and e.fechaReintento is not null and e.fechaCreacion > :fecha";
		final Calendar calendar = Calendar.getInstance();
		if (reintentos == null) {
			// En caso de nulo, por defecto 30 dias
			calendar.add(Calendar.DAY_OF_YEAR, -30);
		} else {
			calendar.add(Calendar.DAY_OF_YEAR, -1 * reintentos);
		}
		final Query query = entityManager.createQuery(sql);
		query.setParameter("fecha", calendar.getTime());
		final List<EnvioAviso> envios = new ArrayList<>();
		final List<HEnvio> henvios = query.getResultList();
		if (henvios != null) {
			for (final HEnvio henvio : henvios) {
				envios.add(henvio.toModel());
			}
		}
		return envios;
	}

	/**
	 * @return the reintentos
	 */
	public final Integer getReintentos() {
		return reintentos;
	}

	/**
	 * @param reintentos the reintentos to set
	 */
	public final void setReintentos(final Integer reintentos) {
		this.reintentos = reintentos;
	}

}
