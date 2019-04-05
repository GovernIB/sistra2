package es.caib.sistrages.core.service.repository.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.model.Sesion;
import es.caib.sistrages.core.service.repository.model.JSesion;

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
	public void updatePerfil(final String pUsername, final String pPerfil) {
		JSesion jSesion = entityManager.find(JSesion.class, pUsername);

		if (jSesion == null) {
			jSesion = new JSesion();
			jSesion.setUsuario(pUsername);
			jSesion.setFecha(new Date());
			jSesion.setPerfil(pPerfil);
			entityManager.persist(jSesion);
		} else {
			jSesion.setPerfil(pPerfil);
			jSesion.setFecha(new Date());
			entityManager.merge(jSesion);
		}
	}

	@Override
	public void updateIdioma(final String pUsername, final String pIdioma) {
		JSesion jSesion = entityManager.find(JSesion.class, pUsername);

		if (jSesion == null) {
			jSesion = new JSesion();
			jSesion.setUsuario(pUsername);
			jSesion.setFecha(new Date());
			jSesion.setIdioma(pIdioma);
			entityManager.persist(jSesion);
		} else {
			jSesion.setIdioma(pIdioma);
			jSesion.setFecha(new Date());
			entityManager.merge(jSesion);
		}
	}

	@Override
	public void updateEntidad(final String pUsername, final Long pIdEntidad) {
		JSesion jSesion = entityManager.find(JSesion.class, pUsername);

		if (jSesion == null) {
			jSesion = new JSesion();
			jSesion.setUsuario(pUsername);
			jSesion.setFecha(new Date());
			jSesion.setEntidad(pIdEntidad);
			entityManager.persist(jSesion);
		} else {
			jSesion.setEntidad(pIdEntidad);
			jSesion.setFecha(new Date());
			entityManager.merge(jSesion);
		}
	}

}
