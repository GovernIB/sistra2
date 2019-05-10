package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.IncidenciaValoracion;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.RIncidenciaValoracion;

@Repository("incidenciaValoracionDao")
public class IncidenciaValoracionDaoImpl implements IncidenciaValoracionDao {

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de EntidadDaoImpl.
	 */
	public IncidenciaValoracionDaoImpl() {
		super();
	}

	@Override
	public List<IncidenciaValoracion> getValoraciones(final Long idEntidad) {
		final List<IncidenciaValoracion> valoraciones = new ArrayList<>();

		final String sql = "select valoracion from RIncidenciaValoracion valoracion where valoracion.entidad.codigo = "
				+ idEntidad;
		final Query query = entityManager.createQuery(sql);

		final List<RIncidenciaValoracion> results = query.getResultList();
		if (results != null) {
			for (final RIncidenciaValoracion jvaloracion : results) {
				final IncidenciaValoracion valoracion = new IncidenciaValoracion();
				valoracion.setCodigo(jvaloracion.getCodigo());
				if (jvaloracion.getDescripcion() != null) {
					valoracion.setDescripcion(jvaloracion.getDescripcion().toModel());
				}
				valoracion.setIdentificador(jvaloracion.getIdentificador());
				valoraciones.add(valoracion);
			}
		}
		return valoraciones;
	}

	@Override
	public IncidenciaValoracion loadValoracion(final Long idValoracion) {
		IncidenciaValoracion valoracion = null;
		final RIncidenciaValoracion jValoracion = entityManager.find(RIncidenciaValoracion.class, idValoracion);
		if (jValoracion != null) {
			// Establecemos datos
			valoracion = jValoracion.toModel();
		}
		return valoracion;
	}

	@Override
	public void addValoracion(final Long idEntidad, final IncidenciaValoracion valoracion) {
		final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
		if (jEntidad == null) {
			throw new NoExisteDato("No existe entidad " + idEntidad);
		}
		final RIncidenciaValoracion jValoracion = new RIncidenciaValoracion();
		jValoracion.setDescripcion(JLiteral.fromModel(valoracion.getDescripcion()));
		jValoracion.setIdentificador(valoracion.getIdentificador());
		jValoracion.setEntidad(jEntidad);
		entityManager.persist(jValoracion);

	}

	@Override
	public void updateValoracion(final IncidenciaValoracion valoracion) {
		final RIncidenciaValoracion jValoracion = entityManager.find(RIncidenciaValoracion.class,
				valoracion.getCodigo());
		if (jValoracion == null) {
			throw new NoExisteDato("No existe valoracion " + valoracion.getCodigo());
		}

		jValoracion.setDescripcion(JLiteral.fromModel(valoracion.getDescripcion()));
		jValoracion.setIdentificador(valoracion.getIdentificador());
		entityManager.merge(jValoracion);

	}

	@Override
	public void removeValoracion(final Long idValoracion) {
		final RIncidenciaValoracion jValoracion = entityManager.find(RIncidenciaValoracion.class, idValoracion);
		if (jValoracion == null) {
			throw new NoExisteDato("No existe valoracion " + idValoracion);
		}

		entityManager.remove(jValoracion);
	}

	@Override
	public boolean existeIdentificador(final String identificador, final Long idEntidad, final Long idIncidencia) {
		String sql = "Select count(t) From RIncidenciaValoracion t where t.entidad.codigo = :idEntidad and t.identificador = :identificador";
		if (idIncidencia != null) {
			sql += " and t.codigo != :idIncidencia";
		}
		final Query query = entityManager.createQuery(sql);

		query.setParameter("identificador", identificador);
		query.setParameter("idEntidad", idEntidad);

		if (idIncidencia != null) {
			query.setParameter("idIncidencia", idIncidencia);
		}

		final Long cuantos = (Long) query.getSingleResult();

		boolean repetido;
		if (cuantos == 0) {
			repetido = false;
		} else {
			repetido = true;
		}
		return repetido;
	}
}
