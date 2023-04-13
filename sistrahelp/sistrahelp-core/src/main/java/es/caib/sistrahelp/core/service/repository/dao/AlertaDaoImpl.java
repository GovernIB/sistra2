package es.caib.sistrahelp.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.service.repository.model.JAlerta;

/**
 * La clase VariableAreaDaoImpl.
 */
@Repository("alertaDao")
public class AlertaDaoImpl implements AlertaDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de VariableAreaDaoImpl.
	 */
	public AlertaDaoImpl() {
		super();
	}

	@Override
	public Alerta getByCodigo(final Long codAlerta) {
		Alerta al = null;
		final JAlerta hal = entityManager.find(JAlerta.class, codAlerta);
		if (hal != null) {
			// Establecemos datos
			al = hal.toModel();
		}
		return al;
	}

	@Override
	public Long add(final Alerta al) {
		// Añade dominio por superadministrador estableciendo datos minimos
		final JAlerta hal = JAlerta.fromModel(al);
		entityManager.persist(hal);
		return hal.getCodigo();
	}

	@Override
	public boolean remove(final Long idAl) {
		boolean retorno = false;
		final JAlerta hal = entityManager.find(JAlerta.class, idAl);
		entityManager.remove(hal);
		retorno = true;
		return retorno;
	}

	@Override
	public List<Alerta> getAllByFiltro(String filtro) {
		return listarAlerta(filtro);
	}

	private List<Alerta> listarAlerta(final String filtro) {
		final List<Alerta> als = new ArrayList<>();
		final List<JAlerta> results = listarJAlerta(filtro);

		if (results != null && !results.isEmpty()) {
			for (final JAlerta jal : results) {
				final Alerta va = jal.toModel();
				als.add(va);
			}
		}

		return als;
	}

	@Override
	public List<Alerta> listarAlertaActivo(final String filtro, final boolean activo) {
		final List<Alerta> als = new ArrayList<>();
		final List<JAlerta> results = listarJAlertaActivo(filtro, activo);

		if (results != null && !results.isEmpty()) {
			for (final JAlerta jal : results) {
				final Alerta va = jal.toModel();
				als.add(va);
			}
		}

		return als;
	}

	@Override
	public void updateAlerta(final Alerta al) {
		final JAlerta jal = JAlerta.fromModel(al);
		entityManager.merge(jal);
	}

	@SuppressWarnings("unchecked")
	private List<JAlerta> listarJAlerta(final String filtro) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT d FROM JAlerta d WHERE 1 = 1 ");
		if (StringUtils.isNotBlank(filtro)) {
			sql.append(" AND (LOWER(d.nombre) LIKE :filtro OR LOWER(d.email)");
		}
		sql.append(" ORDER BY d.nombre");

		final Query query = entityManager.createQuery(sql.toString());

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<JAlerta> listarJAlertaActivo(final String filtro, final boolean activo) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT d FROM JAlerta d WHERE d.eliminar = :activo");
		if (StringUtils.isNotBlank(filtro)) {
			sql.append(" AND (LOWER(d.nombre) LIKE :filtro OR LOWER(d.email)");
		}
		sql.append(" ORDER BY d.nombre");

		final Query query = entityManager.createQuery(sql.toString());

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if (activo) {
			query.setParameter("activo", "F");
		} else {
			query.setParameter("activo", "T");
		}

		return query.getResultList();
	}

	@Override
	public Alerta getAlertaByNombre(String nombreAlerta) {

		final StringBuilder sql = new StringBuilder(
				"select d from JAlerta d where d.nombre = :nombre AND d.eliminar = :activo");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("nombre", nombreAlerta);
		query.setParameter("activo", "F");
		final List<JAlerta> jal = query.getResultList();
		Alerta resultado = null;
		if (jal != null && !jal.isEmpty()) {
			resultado = jal.get(0).toModel();
		}
		return resultado;
	}

}
