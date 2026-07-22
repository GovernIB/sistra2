package es.caib.sistrahelp.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.FiltroAlerta;
import es.caib.sistrahelp.core.service.repository.model.JAlerta;
import es.caib.sistrahelp.core.service.repository.model.JProceso;

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
	public List<Alerta> listarAlertaActivo(final FiltroAlerta filtro, final boolean activo) {
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
	private List<JAlerta> listarJAlertaActivo(final FiltroAlerta filtro, final boolean activo) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT d FROM JAlerta d WHERE d.eliminar = :activo");
		
		// Aplica filtro textual si viene en el objeto filtro
		if (filtro != null && StringUtils.isNotBlank(filtro.getFiltroTexto())) {
			sql.append(" AND (LOWER(d.nombre) LIKE :filtroTexto OR LOWER(d.email) LIKE :filtroTexto)");
		}

		// Aplica filtro de entidad si viene en el objeto filtro
		if (filtro != null && StringUtils.isNotBlank(filtro.getIdEntidad())) {
			sql.append(" AND d.idEntidad = :idEntidad");
		}

		// Aplica filtro de áreas si viene en el objeto filtro (solo si hay al menos una área)
		if (filtro != null && filtro.getListaAreas() != null && !filtro.getListaAreas().isEmpty()) {
			// Genera condición: las áreas de la alerta (separadas por ;) deben contener alguna de las permitidas
			StringBuilder areasCondition = new StringBuilder(" AND (");
			for (int i = 0; i < filtro.getListaAreas().size(); i++) {
				if (i > 0) {
					areasCondition.append(" OR ");
				}
				areasCondition.append("CONCAT(CONCAT(';', d.listaAreas), ';') LIKE :area").append(i);
			}
			areasCondition.append(")");
			sql.append(areasCondition);
		}

		sql.append(" ORDER BY d.nombre");

		final Query query = entityManager.createQuery(sql.toString());

		// Parámetro: activo
		if (activo) {
			query.setParameter("activo", "F");
		} else {
			query.setParameter("activo", "T");
		}

		// Parámetro: filtro textual
		if (filtro != null && StringUtils.isNotBlank(filtro.getFiltroTexto())) {
			query.setParameter("filtroTexto", "%" + filtro.getFiltroTexto().toLowerCase() + "%");
		}

		// Parámetro: entidad
		if (filtro != null && StringUtils.isNotBlank(filtro.getIdEntidad())) {
			query.setParameter("idEntidad", filtro.getIdEntidad());
		}

		// Parámetros: áreas
		if (filtro != null && filtro.getListaAreas() != null && !filtro.getListaAreas().isEmpty()) {
			for (int i = 0; i < filtro.getListaAreas().size(); i++) {
				query.setParameter("area" + i, "%;" + filtro.getListaAreas().get(i) + ";%");
			}
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

	@Override
    public void updateUltimaVerificacion(final Long codigoAlerta) {
    	final Date fechaActual = new Date();

        // Recuperamos info actual (debe existir siempre)
        final JAlerta jAlerta = entityManager.find(JAlerta.class,
                codigoAlerta);

        if(jAlerta != null) {
        	final String sql = "UPDATE JAlerta p SET p.fecha = :fechaActual WHERE p.codigo = :codigo";
            final Query query = entityManager.createQuery(sql);
            query.setParameter("fechaActual", fechaActual);
            query.setParameter("codigo", jAlerta.getCodigo());
            query.executeUpdate();
        }
    }

    @Override
    public Date getUltimaVerificacion(final Long codigoAlerta) {
    	final StringBuilder sql = new StringBuilder("select p.fecha from JAlerta p where p.codigo = :codigo");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("codigo", codigoAlerta);

		if(!query.getResultList().isEmpty()) {
			return (Date) query.getSingleResult();
		}

		return null;
    }

}
