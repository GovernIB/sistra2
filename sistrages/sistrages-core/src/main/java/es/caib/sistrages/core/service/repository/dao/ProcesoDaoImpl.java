package es.caib.sistrages.core.service.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * Proceso DAO.
 */
@Repository("procesoDao")
public class ProcesoDaoImpl implements ProcesoDao {

	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean verificarMaestro(final String instanciaId) {
		// TODO Auto-generated method stub
		return false;
	}

}
