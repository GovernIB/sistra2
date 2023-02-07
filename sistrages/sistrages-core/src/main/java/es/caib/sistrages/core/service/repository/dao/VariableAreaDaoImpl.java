package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.VariableArea;
import es.caib.sistrages.core.api.model.comun.ConstantesDominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JConfiguracionAutenticacion;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JEnvioRemoto;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JGestorExternoFormularios;
import es.caib.sistrages.core.service.repository.model.JVariableArea;
import es.caib.sistrages.core.service.repository.model.JVersionTramite;

/**
 * La clase VariableAreaDaoImpl.
 */
@Repository("variableAreaDao")
public class VariableAreaDaoImpl implements VariableAreaDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de VariableAreaDaoImpl.
	 */
	public VariableAreaDaoImpl() {
		super();
	}

	@Override
	public VariableArea getByCodigo(final Long codVarAre) {
		VariableArea va = null;
		final JVariableArea hva = entityManager.find(JVariableArea.class, codVarAre);
		if (hva != null) {
			// Establecemos datos
			va = hva.toModel();
		}
		return va;
	}

	@Override
	public Long add(final VariableArea va) {
		// Añade dominio por superadministrador estableciendo datos minimos
		final JVariableArea jVa = new JVariableArea();
		jVa.fromModel(va);
		entityManager.persist(jVa);
		return jVa.getCodigo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.VariableAreaDao#remove(java.
	 * lang. Long)
	 */
	@Override
	public boolean remove(final Long idVa) {
		boolean retorno = false;
		final JVariableArea hva = entityManager.find(JVariableArea.class, idVa);
		entityManager.remove(hva);
		retorno = true;
		return retorno;
	}

	@Override
	public List<VariableArea> getAllByFiltro(final Long idArea, final String filtro) {
		return listarVariableArea(idArea, filtro);
	}

	/**
	 * Listar VariableArea.
	 *
	 * @param id     id
	 * @param filtro filtro
	 * @return lista de VariableArea
	 */
	private List<VariableArea> listarVariableArea(final Long id, final String filtro) {
		final List<VariableArea> vas = new ArrayList<>();
		final List<JVariableArea> results = listarJVariableArea(id, filtro);

		if (results != null && !results.isEmpty()) {
			for (final JVariableArea jva : results) {
				final VariableArea va = jva.toModel();
				vas.add(va);
			}
		}

		return vas;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.DominioDao#updateDominio(es.
	 * caib.sistrages.core.api.model.Dominio)
	 */
	@Override
	public void updateVariableArea(final VariableArea va) {
		final JVariableArea jva = entityManager.find(JVariableArea.class, va.getCodigo());
		jva.fromModel(va);
		entityManager.merge(jva);
	}

	/**
	 * Lista VariableArea.
	 *
	 * @param id     id
	 * @param filtro filtro
	 * @return VariableArea
	 */
	@SuppressWarnings("unchecked")
	private List<JVariableArea> listarJVariableArea(final Long id, final String filtro) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT d FROM JVariableArea d WHERE 1 = 1 ");
		if (StringUtils.isNotBlank(filtro)) {
			sql.append(
					" AND (LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro OR LOWER(d.url) LIKE :filtro)");
		}

		sql.append("AND ( d.area.id = :id ) ");

		sql.append(" ORDER BY d.identificador");

		final Query query = entityManager.createQuery(sql.toString());

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		query.setParameter("id", id);

		return query.getResultList();
	}

	public VariableArea getVariableAreaByIdentificador(String identificadorVariableArea, final Long idArea) {

		final StringBuilder sql = new StringBuilder(
				"select d from JVariableArea d where d.identificador = :identificador ");

		sql.append(" AND d.area.id = :idArea");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificador", identificadorVariableArea);
		query.setParameter("idArea", idArea);

		final List<JVariableArea> jVa = query.getResultList();
		VariableArea resultado = null;
		if (jVa != null && !jVa.isEmpty()) {
			resultado = jVa.get(0).toModel();
		}
		return resultado;
	}

	@Override
	public List<Dominio> dominioByVariable(VariableArea va) {
		final StringBuilder sql = new StringBuilder(
				"select d from JDominio d where d.area.id = :idArea AND d.tipo = 'R' AND d.servicioRemotoUrl = :url");
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idArea", va.getArea().getCodigo());
		query.setParameter("url", "{@@" + va.getIdentificador() + "@@}");
		final List<JDominio> jDom = query.getResultList();
		List<Dominio> resultado = new ArrayList<Dominio>();
		if (jDom != null && !jDom.isEmpty()) {
			for (JDominio jDomAux : jDom) {
				resultado.add(jDomAux.toModel());
			}
		}
		return resultado;
	}

	@Override
	public List<GestorExternoFormularios> gfeByVariable(VariableArea va) {
		final StringBuilder sql = new StringBuilder(
				"select d from JGestorExternoFormularios d where d.area.id = :idArea AND d.url = :url");
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idArea", va.getArea().getCodigo());
		query.setParameter("url", "{@@" + va.getIdentificador() + "@@}");
		final List<JGestorExternoFormularios> jGfe = query.getResultList();
		List<GestorExternoFormularios> resultado = new ArrayList<GestorExternoFormularios>();
		if (jGfe != null && !jGfe.isEmpty()) {
			for (JGestorExternoFormularios jGfeAux : jGfe) {
				resultado.add(jGfeAux.toModel());
			}
		}
		return resultado;
	}

	@Override
	public List<EnvioRemoto> envioRemotoByVariable(VariableArea va) {
		final StringBuilder sql = new StringBuilder(
				"select d from JEnvioRemoto d where d.area.id = :idArea AND d.url = :url");
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idArea", va.getArea().getCodigo());
		query.setParameter("url", "{@@" + va.getIdentificador() + "@@}");
		final List<JEnvioRemoto> jEr = query.getResultList();
		List<EnvioRemoto> resultado = new ArrayList<EnvioRemoto>();
		if (jEr != null && !jEr.isEmpty()) {
			for (JEnvioRemoto jErAux : jEr) {
				resultado.add(jErAux.toModel());
			}
		}
		return resultado;
	}

}
