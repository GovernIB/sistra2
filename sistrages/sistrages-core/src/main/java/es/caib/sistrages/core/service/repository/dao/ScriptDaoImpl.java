package es.caib.sistrages.core.service.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.service.repository.model.JScript;

/**
 * La clase TramiteDaoImpl.
 */
@Repository("scriptDao")
public class ScriptDaoImpl implements ScriptDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de ScriptDaoImpl.
	 */
	public ScriptDaoImpl() {
		super();
	}

	@Override
	public Script getScript(final Long idScript) {
		Script script = null;
		if (idScript == null) {
			throw new FaltanDatosException("Falta el identificador");
		}

		final JScript jScript = entityManager.find(JScript.class, idScript);
		if (jScript == null) {
			throw new NoExisteDato("No existe el script " + idScript);
		} else {
			script = jScript.toModel();
		}

		return script;
	};

}
