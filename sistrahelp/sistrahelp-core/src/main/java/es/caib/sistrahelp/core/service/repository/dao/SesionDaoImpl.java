package es.caib.sistrahelp.core.service.repository.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.caib.sistrahelp.core.api.model.Sesion;
import es.caib.sistrahelp.core.service.repository.model.JSesion;

@Repository("sesionDao")
public class SesionDaoImpl implements SesionDao {

	public SesionDaoImpl() {
		super();
	}

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Sesion getByUser(final String pUsername) {
		Sesion sesion = null;
		final JSesion jSesion = entityManager.find(JSesion.class, pUsername);
		if (jSesion != null) {
			sesion = jSesion.toModel();
		}
		return sesion;
	}

	@Override
	public void updatePropiedades(final String pUsername, final String pPropiedades) {
		JSesion jSesion = entityManager.find(JSesion.class, pUsername);

		if (jSesion == null) {
			jSesion = new JSesion();
			jSesion.setUsuario(pUsername);
			jSesion.setFecha(new Date());
			jSesion.setPropiedades(pPropiedades);
			entityManager.persist(jSesion);
		} else {
			jSesion.setPropiedades(pPropiedades);
			jSesion.setFecha(new Date());
			entityManager.merge(jSesion);
		}
	}

}
