package es.caib.sistrahelp.core.service.repository.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrahelp.core.api.model.FiltroAlerta;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.service.repository.model.JHistorialAlerta;

/**
 * La clase VariableAreaDaoImpl.
 */
@Repository("historialAlertaDao")
public class HistorialAlertaDaoImpl implements HistorialAlertaDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de VariableAreaDaoImpl.
	 */
	public HistorialAlertaDaoImpl() {
		super();
	}

	@Override
	public HistorialAlerta getByCodigo(final Long codAlerta) {
		HistorialAlerta al = null;
		final JHistorialAlerta hal = entityManager.find(JHistorialAlerta.class, codAlerta);
		if (hal != null) {
			// Establecemos datos
			al = hal.toModel();
		}
		return al;
	}

	@Override
	public Long add(final HistorialAlerta al) {
		// Añade dominio por superadministrador estableciendo datos minimos
		final JHistorialAlerta hal = JHistorialAlerta.fromModel(al);
		entityManager.persist(hal);
		return hal.getCodigo();
	}

	@Override
	public boolean remove(final Long idAl) {
		boolean retorno = false;
		final JHistorialAlerta hal = entityManager.find(JHistorialAlerta.class, idAl);
		entityManager.remove(hal);
		retorno = true;
		return retorno;
	}

	@Override
	public List<HistorialAlerta> getAllByFiltro(Date desde, Date hasta, FiltroAlerta filtro) {
		return listarHistorialAlerta(desde, hasta, filtro);
	}

	private List<HistorialAlerta> listarHistorialAlerta(final Date desde, final Date hasta, final FiltroAlerta filtro) {
		final List<HistorialAlerta> als = new ArrayList<>();
		final List<JHistorialAlerta> results = listarJHistorialAlerta(desde, hasta, filtro);

		if (results != null && !results.isEmpty()) {
			for (final JHistorialAlerta jal : results) {
				final HistorialAlerta va = jal.toModel();
				als.add(va);
			}
		}

		return als;
	}

	@Override
	public void updateHistorialAlerta(final HistorialAlerta al) {
		final JHistorialAlerta jal = entityManager.find(JHistorialAlerta.class, al.getCodigo());
		jal.fromModel(al);
		entityManager.merge(jal);
	}

	@SuppressWarnings("unchecked")
	private List<JHistorialAlerta> listarJHistorialAlerta(final Date desde, final Date hasta, final FiltroAlerta filtro) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT d FROM JHistorialAlerta d WHERE 1 = 1 ");
		if (desde != null) {
			sql.append(" AND (d.fecha > :desde)");
		}

		if (hasta != null) {
			sql.append(" AND (d.fecha <= :hasta)");
		}

		if (filtro != null) {
			if (StringUtils.isNotBlank(filtro.getIdEntidad())) {
				sql.append(" AND d.idEntidad = :idEntidad");
			}

			if (StringUtils.isNotBlank(filtro.getFiltroTexto())) {
				sql.append(" AND LOWER(d.nombre) LIKE :filtroTexto");
			}

			if (filtro.getListaAreas() != null) {
				if (filtro.getListaAreas().isEmpty()) {
					// Fail-closed: en cuadro de mando, sin áreas permitidas no debe haber historial.
					sql.append(" AND 1 = 0");
				} else {
					sql.append(" AND (");
					for (int i = 0; i < filtro.getListaAreas().size(); i++) {
						if (i > 0) {
							sql.append(" OR ");
						}
						// Coincidencia exacta de token evitando falsos positivos (A1 vs A10)
						sql.append("CONCAT(CONCAT(';', d.listaAreas), ';') LIKE :area").append(i);
					}
					sql.append(")");
				}
			}
		}

		sql.append(" ORDER BY d.fecha DESC");

		final Query query = entityManager.createQuery(sql.toString());

		if (desde != null) {
			query.setParameter("desde", desde);
		}

		if (hasta != null) {
			query.setParameter("hasta", hasta);
		}

		if (filtro != null) {
			if (StringUtils.isNotBlank(filtro.getIdEntidad())) {
				query.setParameter("idEntidad", filtro.getIdEntidad());
			}

			if (StringUtils.isNotBlank(filtro.getFiltroTexto())) {
				query.setParameter("filtroTexto", "%" + filtro.getFiltroTexto().toLowerCase() + "%");
			}

			if (filtro.getListaAreas() != null && !filtro.getListaAreas().isEmpty()) {
				for (int i = 0; i < filtro.getListaAreas().size(); i++) {
					query.setParameter("area" + i, "%;" + filtro.getListaAreas().get(i) + ";%");
				}
			}
		}

		return query.getResultList();
	}

	@Override
	public HistorialAlerta getHistorialAlertaByAlerta(Long codigoAviso) {

		final StringBuilder sql = new StringBuilder("select d from JAlerta d where d.codigoAviso = :codigoAviso ");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("codigoAviso", codigoAviso);

		final List<JHistorialAlerta> jal = query.getResultList();
		HistorialAlerta resultado = null;
		if (jal != null && !jal.isEmpty()) {
			resultado = jal.get(0).toModel();
		}
		return resultado;
	}

	@Override
	public HistorialAlerta getSiguienteHistorialAlerta(Long codigoAviso, Date fechaUltimaEjecucion) {
		final StringBuilder sql = new StringBuilder(
				"SELECT d FROM JHistorialAlerta d WHERE d.alerta.codigo = :codigoAviso AND d.fecha > :fechaUltimaEjecucion ORDER BY d.fecha ASC"
		);

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("codigoAviso", codigoAviso);
		query.setParameter("fechaUltimaEjecucion", fechaUltimaEjecucion);

		query.setMaxResults(1);
		List<JHistorialAlerta> resultados = query.getResultList();

		if (resultados != null && !resultados.isEmpty()) {
			return resultados.get(0).toModel();
		}
		return null;
	}
}
