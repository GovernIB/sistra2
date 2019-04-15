package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.JLiteralErrorScript;
import es.caib.sistrages.core.service.repository.model.JScript;

/**
 * La clase ScriptDaoImpl.
 */
@Repository("scriptDao")
public class ScriptDaoImpl implements ScriptDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/** Literal. **/
	private static final String LITERAL_FALTAIDENTIFICADOR = "Falta el identificador";

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
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}

		final JScript jScript = entityManager.find(JScript.class, idScript);
		if (jScript == null) {
			throw new NoExisteDato("No existe el script " + idScript);
		} else {
			script = jScript.toModel();
		}

		return script;
	}

	@Override
	public List<LiteralScript> getLiterales(final Long idScript) {
		final List<LiteralScript> resultado = new ArrayList<>();

		final String sql = "Select t From JLiteralErrorScript t where t.script.codigo = :idScript ORDER BY t.codigo";

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idScript", idScript);

		final List<JLiteralErrorScript> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final Iterator<JLiteralErrorScript> iterator = results.iterator(); iterator.hasNext();) {
				final JLiteralErrorScript jLiteral = iterator.next();
				resultado.add(jLiteral.toModel());
			}
		}

		return resultado;
	}

	@Override
	public LiteralScript getLiteralScript(final Long idLiteralScript) {
		LiteralScript literalScript = null;
		if (idLiteralScript == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}

		final JLiteralErrorScript jLiteralScript = entityManager.find(JLiteralErrorScript.class, idLiteralScript);
		if (jLiteralScript == null) {
			throw new NoExisteDato("No existe el literal script " + idLiteralScript);
		} else {
			literalScript = jLiteralScript.toModel();
		}

		return literalScript;
	}

	@Override
	public LiteralScript addLiteralScript(final LiteralScript literalScript, final Long idScript) {
		if (idScript == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}

		final JScript jScript = entityManager.find(JScript.class, idScript);
		if (jScript == null) {
			throw new NoExisteDato("No existe el script " + idScript);
		}

		final JLiteralErrorScript jLiteralErrorScript = JLiteralErrorScript.fromModel(literalScript, jScript);
		entityManager.persist(jLiteralErrorScript);

		return jLiteralErrorScript.toModel();
	}

	@Override
	public void updateLiteralScript(final LiteralScript literalScript) {
		if (literalScript == null) {
			throw new FaltanDatosException("Falta el literal script");
		}

		if (literalScript.getCodigo() == null) {
			throw new FaltanDatosException("No se puede actualizar porque no existe.");
		}

		final JLiteralErrorScript jLiteralScript = entityManager.find(JLiteralErrorScript.class,
				literalScript.getCodigo());
		jLiteralScript.setIdentificador(literalScript.getIdentificador());
		jLiteralScript.setLiteral(JLiteral.fromModel(literalScript.getLiteral()));
		entityManager.merge(jLiteralScript);
	}

	@Override
	public void removeLiteralScript(final Long idliteralScript) {
		if (idliteralScript == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}

		final JLiteralErrorScript jLiteralScript = entityManager.find(JLiteralErrorScript.class, idliteralScript);
		if (jLiteralScript == null) {
			throw new NoExisteDato("No existe el literal script " + idliteralScript);
		}

		entityManager.remove(jLiteralScript);
	}

	@Override
	public boolean checkIdentificadorRepetido(final String identificador, final Long codigo, final Long idScript) {
		String sql = "Select count(t) From JLiteralErrorScript t where t.identificador = :identificador and t.script.codigo = :idScript";
		if (codigo != null) {
			sql += " and t.codigo != :codigo";
		}

		final Query query = entityManager.createQuery(sql);

		query.setParameter("identificador", identificador);
		query.setParameter("idScript", idScript);
		if (codigo != null) {
			query.setParameter("codigo", codigo);
		}

		final Long cuantos = (Long) query.getSingleResult();

		boolean repetido;
		if (cuantos == 0) {
			repetido = false;
		} else {
			repetido = true;
		}
		return repetido;
	};

	@Override
	public void updateScript(final Script pScript) {
		if (pScript == null) {
			throw new FaltanDatosException("Falta el script");
		}

		if (pScript.getCodigo() == null) {
			throw new FaltanDatosException("No se puede actualizar porque no existe.");
		}

		final JScript jScript = JScript.fromModel(pScript);
		entityManager.merge(jScript);
	}
}
