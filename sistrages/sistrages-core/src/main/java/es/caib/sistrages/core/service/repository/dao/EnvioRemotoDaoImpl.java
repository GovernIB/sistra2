package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.comun.ConstantesDominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JConfiguracionAutenticacion;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JEnvioRemoto;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JVersionTramite;

/**
 * La clase DominioDaoImpl.
 */
@Repository("envioRemotoDao")
public class EnvioRemotoDaoImpl implements EnvioRemotoDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de DominioDaoImpl.
	 */
	public EnvioRemotoDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#getById(java.lang.
	 * Long)
	 */
	@Override
	public EnvioRemoto getByCodigo(final Long codEnvio) {
		EnvioRemoto envio = null;
		final JEnvioRemoto henvio = entityManager.find(JEnvioRemoto.class, codEnvio);
		if (henvio != null) {
			// Establecemos datos
			envio = henvio.toModel();
		}
		return envio;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#add(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	public Long add(final EnvioRemoto envio, final Long idEntidad, final Long idArea) {
		// Añade dominio por superadministrador estableciendo datos minimos
		final JEnvioRemoto jenvio = new JEnvioRemoto();
		jenvio.fromModel(envio);

		if (idEntidad != null) {
			final JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
			jenvio.setEntidad(jentidad);
		}
		if (idArea != null) {
			final JArea jarea = entityManager.find(JArea.class, idArea);
			jenvio.setArea(jarea);
			final JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
			jenvio.setEntidad(jentidad);
		}
		entityManager.persist(jenvio);
		return jenvio.getCodigo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#remove(java.lang.
	 * Long)
	 */
	@Override
	public boolean remove(final Long idEnvio) {
		boolean retorno;
		final JEnvioRemoto henvio = entityManager.find(JEnvioRemoto.class, idEnvio);
		if (henvio == null) {
			// No existe
			retorno = false;
		} else {
			entityManager.remove(henvio);
			retorno = true;
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#getAllByFiltro(es.
	 * caib.sistrages.core.api.model.types.TypeAmbito, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<EnvioRemoto> getAllByFiltro(final TypeAmbito ambito, final Long id, final String filtro) {
		return listarEnvios(ambito, id, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.DominioDao#getAll(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long)
	 */
	@Override
	public List<EnvioRemoto> getAll(final TypeAmbito ambito, final Long id) {
		return listarEnvios(ambito, id, null);
	}

	/**
	 * Listar dominios.
	 *
	 * @param ambito ambito
	 * @param id     id
	 * @param filtro filtro
	 * @return lista de dominios
	 */
	private List<EnvioRemoto> listarEnvios(final TypeAmbito ambito, final Long id, final String filtro) {
		final List<EnvioRemoto> envios = new ArrayList<>();

		final List<JEnvioRemoto> results = listarJEnvioRemoto(ambito, id, filtro);

		if (results != null && !results.isEmpty()) {
			for (final JEnvioRemoto jenvio : results) {
				final EnvioRemoto envio = jenvio.toModel();
				envios.add(envio);
			}
		}

		return envios;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#updateDominio(es.
	 * caib.sistrages.core.api.model.Dominio)
	 */
	@Override
	public void updateEnvio(final EnvioRemoto envio) {
		final JEnvioRemoto jenvio = entityManager.find(JEnvioRemoto.class, envio.getCodigo());
		jenvio.fromModel(envio);
		entityManager.merge(jenvio);
	}

	@Override
	public void removeByEntidad(final Long idEntidad) {
		final List<JEnvioRemoto> envios = listarJEnvioRemoto(TypeAmbito.ENTIDAD, idEntidad, null);
		for (final JEnvioRemoto d : envios) {
			remove(d.getCodigo());
		}
	}

	@Override
	public void removeByArea(final Long idArea) {
		final List<JEnvioRemoto> envios = listarJEnvioRemoto(TypeAmbito.AREA, idArea, null);
		for (final JEnvioRemoto d : envios) {
			remove(d.getCodigo());
		}
	}

	/**
	 * Lista dominios.
	 *
	 * @param ambito Ambito
	 * @param id     id
	 * @param filtro filtro
	 * @return dominios
	 */
	@SuppressWarnings("unchecked")
	private List<JEnvioRemoto> listarJEnvioRemoto(final TypeAmbito ambito, final Long id, final String filtro) {
		String sql = "SELECT DISTINCT d FROM JEnvioRemoto d ";

		if (ambito == TypeAmbito.AREA) {
			sql += " WHERE d.area.id = :id AND d.ambito = '" + TypeAmbito.AREA + "'";
		} else if (ambito == TypeAmbito.ENTIDAD) {
			sql += " WHERE d.entidad.id = :id AND d.ambito = '" + TypeAmbito.ENTIDAD + "'";
		}

		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND (LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro)";
		}

		sql += " ORDER BY d.identificador";

		final Query query = entityManager.createQuery(sql);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if (id != null) {
			query.setParameter("id", id);
		}

		return query.getResultList();
	}

	/**
	 * Limpiamos los campos id del dominio y, en caso de pasarse area y fd, se
	 * asocian.
	 */
	@Override
	public void clonar(final String envioID, final String nuevoIdentificador, final Long areaID, final Long fdID,
			final Long idEntidad) {

		JArea jareas = null;
		if (areaID != null) {
			jareas = entityManager.find(JArea.class, areaID);
		}

		JEntidad jentidad = null;
		if (idEntidad != null) {
			jentidad = entityManager.find(JEntidad.class, idEntidad);
		}
		final JEnvioRemoto henvio = JEnvioRemoto.clonar(entityManager.find(JEnvioRemoto.class, Long.valueOf(envioID)),
				nuevoIdentificador, jareas, jentidad);
		entityManager.persist(henvio);
	}

	@Override
	public List<EnvioRemoto> getEnviosByConfAut(Long idConfiguracion, Long idArea) {
		final String sql = "select d from JEnvioRemoto d where d.ambito = 'A' and d.area.codigo = :idArea and d.configuracionAutenticacion.codigo = :idConf ORDER BY d.identificador ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idConf", idConfiguracion);
		query.setParameter("idArea", idArea);

		final List<JEnvioRemoto> jenvios = query.getResultList();
		final List<EnvioRemoto> envios = new ArrayList<>();
		for (final JEnvioRemoto jenvio : jenvios) {
			envios.add(jenvio.toModel());
		}
		return envios;
	}

	@Override
	public List<EnvioRemoto> listEnviosByEntidad(Long idEntidad) {
		final String sql = "select d from JEnvioRemoto d where d in (select j from JEnvioRemoto j where j.entidad.codigo = :idEntidad) OR d in (select p from JEnvioRemoto p where p.area.entidad.codigo = :idEntidad) ORDER BY d.identificador ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idEntidad", idEntidad);

		final List<JEnvioRemoto> jenvios = query.getResultList();
		final List<EnvioRemoto> envios = new ArrayList<>();
		for (final JEnvioRemoto jenvio : jenvios) {
			envios.add(jenvio.toModel());
		}
		return envios;
	}

	@Override
	public List<EnvioRemoto> getEnviosByIdentificador(List<String> identificadoresDominio, final Long idEntidad,
			final Long idArea) {

		final List<EnvioRemoto> envios = new ArrayList<>();
		for (String identificadorDominio : identificadoresDominio) {
			ValorIdentificadorCompuesto valor = new ValorIdentificadorCompuesto(identificadorDominio);
			EnvioRemoto env = getEnviosByIdentificador(valor.getIdentificador(), valor.getAmbito(),
					valor.getIdentificadorEntidad(), valor.getIdentificadorArea());
			if (env != null) {
				if (env.getAmbito() == TypeAmbito.GLOBAL) {
					envios.add(env);
				} else if (env.getAmbito() == TypeAmbito.ENTIDAD) {
					// Solo se agregan dominios de la misma entidad
					if (env.getEntidad().getCodigo().compareTo(idEntidad) == 0) {
						envios.add(env);
					}
				} else if (env.getAmbito() == TypeAmbito.AREA) {
					// Solo se agregan dominios de la misma area
					if (env.getArea().getCodigo().compareTo(idArea) == 0) {
						envios.add(env);
						break;
					}
				}
			}
		}
		return envios;
	}

	private EnvioRemoto getEnviosByIdentificador(String identificadorEnvio, final TypeAmbito ambito,
			final String idEntidad, final String idArea) {

		final StringBuilder sql = new StringBuilder(
				"select d from JEnvioRemoto d where d.ambito = :ambito and d.identificador = :identificador ");
		if (ambito == TypeAmbito.ENTIDAD) {
			sql.append(" AND d.entidad.identificador = :idEntidad");
		}
		if (ambito == TypeAmbito.AREA) {
			sql.append(" AND d.area.identificador = :idArea");
			sql.append(" AND d.area.entidad.identificador = :idEntidad");
		}

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificador", identificadorEnvio);
		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("idEntidad", idEntidad);
		}
		if (ambito == TypeAmbito.AREA) {
			query.setParameter("idArea", idArea);
			query.setParameter("idEntidad", idEntidad);
		}

		final List<JEnvioRemoto> jenvios = query.getResultList();
		EnvioRemoto resultado = null;
		if (jenvios != null && !jenvios.isEmpty()) {
			resultado = jenvios.get(0).toModel();

		}
		return resultado;
	}

	@Override
	public boolean existeEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		Query query = getQuery(true, ambito, identificador, null, null, codigoEntidad, codigoArea, codigoDominio);
		final Long cuantos = (Long) query.getSingleResult();
		return cuantos != 0l;
	}

	private Query getQuery(boolean isTotal, TypeAmbito ambito, String identificador, String identificadorEntidad,
			String identificadorArea, Long codigoEntidad, Long codigoArea, Long codigoEnvio) {
		final StringBuilder sql = new StringBuilder("select ");
		if (isTotal) {
			sql.append(" count(d) ");
		} else {
			sql.append(" d ");
		}
		sql.append(" from JEnvioRemoto d where d.ambito like :ambito ");
		if (ambito == TypeAmbito.AREA) {
			if (codigoArea != null) {
				sql.append(" AND d.area.codigo = :codigoArea");
			}
			if (identificadorArea != null && !identificadorArea.isEmpty()) {
				sql.append(" AND d.area.identificador = :identificadorArea");
			}
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			if (codigoEntidad != null) {
				sql.append(" AND d.entidad.codigo = :codigoEntidad");
			}
			if (identificadorEntidad != null && !identificadorEntidad.isEmpty()) {
				sql.append(" AND d.entidad.identificador = :identificadorEntidad");
			}

		}
		if (identificador != null && !identificador.isEmpty()) {
			sql.append(" AND d.identificador = :identificador");
		}
		if (codigoEnvio != null) {
			sql.append(" AND d.codigo != :codigoEnvio");
		}

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.AREA) {

			if (codigoArea != null) {
				query.setParameter("codigoArea", codigoArea);
			}
			if (identificadorArea != null && !identificadorArea.isEmpty()) {
				query.setParameter("identificadorArea", identificadorArea);
			}
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			if (codigoEntidad != null) {
				query.setParameter("codigoEntidad", codigoEntidad);
			}
			if (identificadorEntidad != null && !identificadorEntidad.isEmpty()) {
				query.setParameter("identificadorEntidad", identificadorEntidad);
			}

		}
		if (identificador != null && !identificador.isEmpty()) {
			query.setParameter("identificador", identificador);
		}
		if (codigoEnvio != null) {
			query.setParameter("codigoEnvio", codigoEnvio);
		}

		return query;
	}

	@Override
	public EnvioRemoto getEnvioByIdentificador(TypeAmbito ambito, String identificador, String identificadorEntidad,
			String identificadorArea, Long codigoEntidad, Long codigoArea, Long codigoEnvio) {
		return getEnvioByIdentificadorPrivado(ambito, identificador, identificadorEntidad, identificadorArea,
				codigoEntidad, codigoArea, codigoEnvio);
	}

	private EnvioRemoto getEnvioByIdentificadorPrivado(TypeAmbito ambito, String identificador,
			String identificadorEntidad, String identificadorArea, Long codigoEntidad, Long codigoArea,
			Long codigoEnvio) {
		Query query = getQuery(false, ambito, identificador, identificadorEntidad, identificadorArea, codigoEntidad,
				codigoArea, codigoEnvio);
		final List<JEnvioRemoto> envios = query.getResultList();
		final EnvioRemoto envio;
		if (envios == null || envios.isEmpty()) {
			envio = null;
		} else {
			envio = envios.get(0).toModel();
		}

		return envio;
	}

}
