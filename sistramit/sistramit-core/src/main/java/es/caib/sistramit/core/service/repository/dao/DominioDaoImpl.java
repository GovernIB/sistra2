package es.caib.sistramit.core.service.repository.dao;

import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.caib.sistramit.core.service.model.integracion.ParametroDominio;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;

/**
 * Implementación DAO Dominio.
 *
 * @author Indra
 */
@Repository("dominiDao")
public final class DominioDaoImpl implements DominioDao {

	/** Path almacenamiento. */
	// @Value("${jndi.path}")
	private final String jndiPath = "java:/";

	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ValoresDominio realizarConsultaBD(final String jndi, final String sql,
			final ParametrosDominio parametrosDominio) {

		final ValoresDominio valoresDominio = new ValoresDominio();

		try {
			final ValoresDominio res;

			final String sqlDecoded = new String(Base64.decodeBase64(sql));

			// Traducimos sql por si esta en formato antiguo
			final String query = transformSql(sqlDecoded, parametrosDominio);

			// Obtenemos datasource
			final DataSource ds = getDataSource(jndiPath, jndi);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
			final MapSqlParameterSource params = new MapSqlParameterSource();
			if (parametrosDominio != null) {
				for (final ParametroDominio param : parametrosDominio.getParametros()) {
					params.addValue(param.getCodigo(), param.getValor());
				}
			}
			// Verificamos si es un select o un insert
			if (query.trim().toUpperCase().startsWith("INSERT")) {
				res = getValoresDominioInsert(query, params, jdbcTemplate);
			} else {
				res = getValoresDominioSelect(query, params, jdbcTemplate);

			}

			return res;

		} catch (final NamingException e) {
			valoresDominio.setError(true);
			valoresDominio.setCodigoError("BBDD.NAMING");
			valoresDominio.setDescripcionError(ExceptionUtils.getMessage(e));

		} catch (final DataAccessException e) {
			valoresDominio.setError(true);
			valoresDominio.setCodigoError("BBDD.DATA");
			valoresDominio.setDescripcionError(ExceptionUtils.getMessage(e));

		} catch (final Exception e) {
			valoresDominio.setError(true);
			valoresDominio.setCodigoError("BBDD.ERROR");
			valoresDominio.setDescripcionError(ExceptionUtils.getMessage(e));

		}
		return valoresDominio;
	}

	/**
	 * Obtiene el valores dominio de una insert
	 *
	 * @param query
	 * @param params
	 * @param jdbcTemplate
	 * @return
	 */
	private ValoresDominio getValoresDominioInsert(final String query, final MapSqlParameterSource params,
			final NamedParameterJdbcTemplate jdbcTemplate) {
		final ValoresDominio res = new ValoresDominio();
		final int rowsUpdated = jdbcTemplate.update(query, params);
		final int numFila = res.addFila();
		res.setValor(numFila, "ROWS_INSERTED", "" + rowsUpdated);
		return res;
	}

	/**
	 * Obtiene el valores dominio de una select
	 *
	 * @param query
	 * @param params
	 * @param jdbcTemplate
	 * @return
	 */
	private ValoresDominio getValoresDominioSelect(final String query, final MapSqlParameterSource params,
			final NamedParameterJdbcTemplate jdbcTemplate) {
		final ValoresDominio res = new ValoresDominio();
		// SELECT
		final List<Map<String, Object>> queryRes = jdbcTemplate.queryForList(query, params);
		// Creamos objeto valores dominio
		for (final Map<String, Object> fila : queryRes) {
			final int numFila = res.addFila();
			for (final Map.Entry<String, Object> entry : fila.entrySet()) {
				if (entry.getValue() != null) {
					res.setValor(numFila, entry.getKey(), entry.getValue().toString());
				} else {
					res.setValor(numFila, entry.getKey(), null);
				}
			}

		}
		return res;
	}

	/**
	 * Obtiene el datasource.
	 *
	 * @param jndiPath
	 * @param jndi
	 * @return
	 * @throws NamingException
	 */
	private DataSource getDataSource(final String jndiPath, final String jndi) throws NamingException {
		final Context ctext = new InitialContext();
		return (DataSource) ctext.lookup(jndiPath + jndi);
	}

	/**
	 * Transform sql.
	 *
	 * @param sql
	 * @param parametrosDominio
	 * @return
	 */
	private String transformSql(final String pSql, final ParametrosDominio parametrosDominio) {
		String sql = pSql;
		if (parametrosDominio != null) {
			for (final ParametroDominio parametro : parametrosDominio.getParametros()) {
				sql = sql.replace("'[#PARAMETROSDOMINIO." + parametro.getCodigo() + "#]'", ":" + parametro.getCodigo());
				sql = sql.replace("[#PARAMETROSDOMINIO." + parametro.getCodigo() + "#]", ":" + parametro.getCodigo());
			}
		}
		return sql;
	}

}
