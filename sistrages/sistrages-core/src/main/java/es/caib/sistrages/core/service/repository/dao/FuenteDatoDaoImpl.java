package es.caib.sistrages.core.service.repository.dao;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.exception.FuenteDatosCSVNoExisteCampoException;
import es.caib.sistrages.core.api.exception.FuenteDatosConsultaDominioPeticionIncorrecta;
import es.caib.sistrages.core.api.exception.FuenteDatosPkException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeClonarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.util.CsvUtil;
import es.caib.sistrages.core.service.model.ConsultaFuenteDatos;
import es.caib.sistrages.core.service.model.FiltroConsultaFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JCampoFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFilasFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JValorFuenteDatos;
import es.caib.sistrages.core.service.utils.FuenteDatosUtil;

@Repository("fuenteDatoDao")
public class FuenteDatoDaoImpl implements FuenteDatoDao {

	/** Path almacenamiento. */
	@Value("${jndi.path}")
	private String jndiPath;

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de FuenteDatoDaoImpl.
	 */
	public FuenteDatoDaoImpl() {
		super();
	}

	@Override
	public FuenteDatos getById(final Long idFuenteDato) {
		FuenteDatos fuenteDato = null;
		final JFuenteDatos hfuenteDato = entityManager.find(JFuenteDatos.class, idFuenteDato);
		if (hfuenteDato != null) {

			// Establecemos datos
			fuenteDato = hfuenteDato.toModel();
		}
		return fuenteDato;
	}

	@Override
	public FuenteDatosValores getValoresById(final Long idFuenteDato) {
		final List<JFilasFuenteDatos> results = getJFilasFuenteDatos(idFuenteDato);
		final FuenteDatosValores valores = new FuenteDatosValores();
		for (final JFilasFuenteDatos f : results) {
			valores.addFila(f.toModel());
		}
		return valores;
	}

	private List<JValorFuenteDatos> getJValoresFuenteDatos(Long idFuenteDato) {
		final String sql = "SELECT d FROM JValorFuenteDatos d where d.filaFuenteDatos.fuenteDatos.codigo = :idFuenteDato  order by d.codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFuenteDato", idFuenteDato);
		final List<JValorFuenteDatos> results = query.getResultList();
		return results;
	}

	private List<JFilasFuenteDatos> getJFilasFuenteDatos(final Long idFuenteDato) {
		final String sql = "SELECT d FROM JFilasFuenteDatos d where d.fuenteDatos.codigo = :idFuenteDato  order by d.codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFuenteDato", idFuenteDato);
		final List<JFilasFuenteDatos> results = query.getResultList();
		return results;
	}

	private List<JCampoFuenteDatos> getJFuenteCampos(final Long idFuenteDato) {
		final String sql = "SELECT d FROM JCampoFuenteDatos d where d.fuenteDatos.codigo = :idFuenteDato  order by d.codigo";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFuenteDato", idFuenteDato);
		final List<JCampoFuenteDatos> results = query.getResultList();
		return results;
	}

	@Override
	public void add(final FuenteDatos fuenteDato, final Long id) {
		// Añade fuenteDato por superadministrador estableciendo datos minimos
		final JFuenteDatos jFuenteDato = new JFuenteDatos();
		jFuenteDato.fromModel(fuenteDato);
		if (fuenteDato.getAmbito() == TypeAmbito.AREA) {
			final JArea jarea = entityManager.find(JArea.class, id);
			jFuenteDato.setArea(jarea);
		}
		if (fuenteDato.getAmbito() == TypeAmbito.ENTIDAD) {
			final JEntidad jentidad = entityManager.find(JEntidad.class, id);
			jFuenteDato.setEntidad(jentidad);
		}

		if (jFuenteDato.getCampos() != null) {
			for (final JCampoFuenteDatos campo : jFuenteDato.getCampos()) {
				campo.setFuenteDatos(jFuenteDato);
			}
		}

		entityManager.persist(jFuenteDato);

	}

	@Override
	public void remove(final Long idFuenteDato) {
		borrarFuenteDatos(idFuenteDato);
	}

	@Override
	public List<FuenteDatos> getAllByFiltro(final TypeAmbito ambito, final Long id, final String filtro) {
		return listarFuenteDatos(ambito, id, filtro);
	}

	@Override
	public List<FuenteDatos> getAll(final TypeAmbito ambito, final Long id) {
		return listarFuenteDatos(ambito, id, null);
	}

	@SuppressWarnings("unchecked")
	private List<FuenteDatos> listarFuenteDatos(final TypeAmbito ambito, final Long id, final String filtro) {
		final List<FuenteDatos> fuenteDatoes = new ArrayList<>();

		StringBuilder sql = new StringBuilder("SELECT DISTINCT d FROM JFuenteDatos d ");

		if (ambito == TypeAmbito.AREA) {
			sql.append(" WHERE d.area.id = :id AND d.ambito LIKE '" + TypeAmbito.AREA + "'");
		} else if (ambito == TypeAmbito.ENTIDAD) {
			sql.append(" WHERE d.entidad.id = :id AND d.ambito LIKE '" + TypeAmbito.ENTIDAD + "'");
		} else if (ambito == TypeAmbito.GLOBAL) {
			sql.append(" WHERE d.ambito LIKE '" + TypeAmbito.GLOBAL + "'");
		}

		if (StringUtils.isNotBlank(filtro)) {
			sql.append(" AND ( LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro ) ");
		}

		sql.append(" ORDER BY d.identificador");

		final Query query = entityManager.createQuery(sql.toString());

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		if (id != null) {
			query.setParameter("id", id);
		}

		final List<JFuenteDatos> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			for (final JFuenteDatos jfuenteDato : results) {
				final FuenteDatos fuenteDato = jfuenteDato.toModel();
				fuenteDatoes.add(fuenteDato);
			}
		}

		return fuenteDatoes;
	}

	@Override
	public void updateFuenteDato(final FuenteDatos mFuenteDato) {

		// Obtiene fuente datos almacenada
		final JFuenteDatos jFuenteDato = entityManager.find(JFuenteDatos.class, mFuenteDato.getCodigo());

		// Obtiene lista campos a borrar
		final List<JCampoFuenteDatos> listaCamposBorrar = obtieneCamposBorrar(mFuenteDato, jFuenteDato);

		// Obtiene lista campos a anyadir
		final List<FuenteDatosCampo> listaCamposAnyadir = obtieneCamposAnyadir(mFuenteDato, jFuenteDato);

		// Borra valores campos no existentes
		borrarValoresCamposNoExistentes(listaCamposBorrar);

		// Actualiza estructura fuente datos
		actualizaEstructuraFuenteDatos(mFuenteDato, jFuenteDato, listaCamposBorrar);

		// Crea valores nuevos en las filas
		anyadirValoresCampos(jFuenteDato, listaCamposAnyadir);

		// Verificacion de PK
		if (!this.contraintPK(jFuenteDato)) {
			throw new FuenteDatosPkException("PK repetida");
		}

	}

	private void actualizaEstructuraFuenteDatos(final FuenteDatos mFuenteDato, final JFuenteDatos jFuenteDato,
			final List<JCampoFuenteDatos> listaCamposBorrar) {
		// - Propiedades comunes
		jFuenteDato.setIdentificador(mFuenteDato.getIdentificador());
		jFuenteDato.setDescripcion(mFuenteDato.getDescripcion());
		// - Quita campos a borrar
		for (final JCampoFuenteDatos campo : listaCamposBorrar) {
			campo.setFuenteDatos(null);
			jFuenteDato.getCampos().remove(campo);
		}
		// - Actualiza y añade campos
		for (final FuenteDatosCampo campo : mFuenteDato.getCampos()) {
			// Verificamos si existe el campo
			JCampoFuenteDatos jcampoFuenteDatos = jFuenteDato.getJFuenteCampo(campo.getCodigo());
			// Campo no existe: creamos
			if (jcampoFuenteDatos == null) {
				jcampoFuenteDatos = new JCampoFuenteDatos();
				jcampoFuenteDatos.setFuenteDatos(jFuenteDato);
				jFuenteDato.getCampos().add(jcampoFuenteDatos);
			} else {
				jcampoFuenteDatos.setCodigo(campo.getCodigo());
			}
			// Actualizamos propiedades campo
			jcampoFuenteDatos.setClavePrimaria((campo.isClavePrimaria() ? "S" : "N"));
			jcampoFuenteDatos.setIdCampo(campo.getIdentificador());
			jcampoFuenteDatos.setFuenteDatos(jFuenteDato);
			jcampoFuenteDatos.setOrden(campo.getOrden());

		}

		// - Persiste informacion estructura (forzamos flush porque luego se
		// vuelve a
		// recuperar)
		entityManager.merge(jFuenteDato);
		entityManager.flush();
	}

	private void anyadirValoresCampos(final JFuenteDatos jFuenteDato, final List<FuenteDatosCampo> listaCamposAnyadir) {
		// Establece campos fuente datos
		// Creamos el campo en las filas
		final List<JFilasFuenteDatos> results = getJFilasFuenteDatos(jFuenteDato.getCodigo());
		for (final JFilasFuenteDatos f : results) {
			for (final FuenteDatosCampo campo : listaCamposAnyadir) {
				final JCampoFuenteDatos jcampoFuenteDatos = jFuenteDato.getJFuenteCampo(campo.getIdentificador());
				final JValorFuenteDatos valor = new JValorFuenteDatos();
				valor.setCampoFuenteDatos(jcampoFuenteDatos);
				valor.setValor("");
				f.addValor(valor);
				entityManager.persist(f);
			}
		}
	}

	private List<JCampoFuenteDatos> obtieneCamposBorrar(final FuenteDatos mFuenteDato, final JFuenteDatos jFuenteDato) {
		final List<JCampoFuenteDatos> camposBorrar = new ArrayList<>();
		for (final JCampoFuenteDatos jCampo : jFuenteDato.getCampos()) {
			boolean nocontiene = true;
			for (final FuenteDatosCampo mFuenteDatoCampo : mFuenteDato.getCampos()) {
				if (mFuenteDatoCampo.getCodigo() != null
						&& mFuenteDatoCampo.getCodigo().compareTo(jCampo.getCodigo()) == 0) {
					nocontiene = false;
					break;
				}
			}
			if (nocontiene) {
				camposBorrar.add(jCampo);
			}
		}
		return camposBorrar;
	}

	private List<FuenteDatosCampo> obtieneCamposAnyadir(final FuenteDatos mFuenteDato, final JFuenteDatos jFuenteDato) {
		final List<FuenteDatosCampo> camposAnyadir = new ArrayList<>();
		for (final FuenteDatosCampo mFuenteDatoCampo : mFuenteDato.getCampos()) {
			boolean nocontiene = true;
			for (final JCampoFuenteDatos jCampo : jFuenteDato.getCampos()) {
				if (mFuenteDatoCampo.getCodigo() != null
						&& mFuenteDatoCampo.getCodigo().compareTo(jCampo.getCodigo()) == 0) {
					nocontiene = false;
					break;
				}
			}
			if (nocontiene) {
				camposAnyadir.add(mFuenteDatoCampo);
			}
		}
		return camposAnyadir;
	}

	private void borrarValoresCamposNoExistentes(final List<JCampoFuenteDatos> listaCamposBorrar) {
		// Borramos valores los campos que han desaparecido en el guardado.
		for (final JCampoFuenteDatos jCampo : listaCamposBorrar) {
			// Borramos en cada fila el valor
			final String sql = "DELETE FROM JValorFuenteDatos d where d.campoFuenteDatos.codigo = :codigoCampo";
			final Query query = entityManager.createQuery(sql);
			query.setParameter("codigoCampo", jCampo.getCodigo());
			query.executeUpdate();
		}
	}

	@Override
	public FuenteFila loadFuenteDatoFila(final Long idFuenteDatoFila) {
		FuenteFila fuenteFila = null;
		final JFilasFuenteDatos hfuenteFila = entityManager.find(JFilasFuenteDatos.class, idFuenteDatoFila);
		if (hfuenteFila != null) {
			// Establecemos datos
			fuenteFila = hfuenteFila.toModel();
		}
		return fuenteFila;
	}

	@Override
	public void addFuenteDatoFila(final FuenteFila fila, final Long idFuente) {
		final JFuenteDatos jfuenteDato = entityManager.find(JFuenteDatos.class, idFuente);
		final JFilasFuenteDatos jfila = JFilasFuenteDatos.fromModel(fila);
		jfila.setFuenteDatos(jfuenteDato);
		entityManager.merge(jfila);
	}

	@Override
	public void updateFuenteDatoFila(final FuenteFila fila) {
		final JFilasFuenteDatos jfila = entityManager.find(JFilasFuenteDatos.class, fila.getCodigo());
		jfila.merge(fila);
		entityManager.merge(jfila);
	}

	@Override
	public void removeFuenteFila(final Long idFila) {
		final JFilasFuenteDatos hfila = entityManager.find(JFilasFuenteDatos.class, idFila);
		if (hfila != null) {
			entityManager.remove(hfila);
		}
	}

	@Override
	public boolean isCorrectoPK(final FuenteFila fuenteFila, final Long idFuenteDato) {
		final JFuenteDatos jFuenteDatos = entityManager.find(JFuenteDatos.class, idFuenteDato);
		final JFilasFuenteDatos fila = JFilasFuenteDatos.fromModel(fuenteFila);
		final boolean result = contraintPK(jFuenteDatos, fila);
		entityManager.detach(jFuenteDatos);
		return result;
	}

	@Override
	public void removeByEntidad(final Long idEntidad) {
		final List<FuenteDatos> listaFD = listarFuenteDatos(TypeAmbito.ENTIDAD, idEntidad, null);
		for (final FuenteDatos fd : listaFD) {
			borrarFuenteDatos(fd.getCodigo());
		}
	}

	@Override
	public void removeByArea(final Long idArea) {
		final List<FuenteDatos> listaFD = listarFuenteDatos(TypeAmbito.AREA, idArea, null);
		for (final FuenteDatos fd : listaFD) {
			borrarFuenteDatos(fd.getCodigo());
		}
	}

	private boolean contraintPK(final JFuenteDatos fuenteDatos) {
		return contraintPK(fuenteDatos, null);
	}

	/**
	 * Verifica si tras modificar una Fuente de Datos se mantiene la constraint de
	 * PK.
	 *
	 * @param fuenteDatos Fuente datos
	 * @param fuenteFila  fila adicional o modificada
	 * @return true en caso de que se cumpla FK
	 */
	private boolean contraintPK(final JFuenteDatos fuenteDatos, final JFilasFuenteDatos fuenteFila) {

		final Set<String> pks = new HashSet<>();
		if (fuenteDatos != null && fuenteDatos.getCampos() != null) {
			final List<JCampoFuenteDatos> keys = new ArrayList<>();
			for (final java.util.Iterator<JCampoFuenteDatos> it = fuenteDatos.getCampos().iterator(); it.hasNext();) {
				final JCampoFuenteDatos cd = it.next();
				if ("S".equals(cd.getClavePrimaria())) {
					keys.add(cd);
				}
			}
			if (!keys.isEmpty()) {
				final String separador = "@#-#@";

				final List<JFilasFuenteDatos> filasFD = getJFilasFuenteDatos(fuenteDatos.getCodigo());
				if (fuenteFila != null) {
					// Si existe la sustituimos, si no la añadimos
					boolean existe = false;
					int i = 0;
					for (final JFilasFuenteDatos fd : filasFD) {
						if (fd.getCodigo() == fuenteFila.getCodigo()) {
							existe = true;
							break;
						}
						i++;
					}
					if (existe) {
						filasFD.set(i, fuenteFila);
					} else {
						filasFD.add(fuenteFila);
					}
				}

				for (final JFilasFuenteDatos fila : filasFD) {
					String pk = "";
					for (final Iterator<JCampoFuenteDatos> it = keys.iterator(); it.hasNext();) {
						final String idCampoPK = it.next().getIdCampo();
						pk = pk + separador + StringUtils.defaultString(fila.getValorFuenteDatos(idCampoPK));
					}
					if (pks.contains(pk)) {
						return false;
					} else {
						pks.add(pk);
					}
				}
			}

		}

		entityManager.detach(fuenteDatos);
		return true;

	}

	/**
	 * Borra fuente de datos y valores asociados.
	 *
	 * @param idFuenteDato Fuente datos
	 */
	private void borrarFuenteDatos(final Long idFuenteDato) {
		// Recuperamos fuente de datos
		final JFuenteDatos hfuenteDato = entityManager.find(JFuenteDatos.class, idFuenteDato);
		if (hfuenteDato == null) {
			throw new NoExisteDato("No existe fuente de datos con id " + idFuenteDato);
		}

		// Borramos filas de datos de la fuente de datos
		final String sqlValores = "delete from JValorFuenteDatos as a where a.filaFuenteDatos.codigo in (select fila from JFilasFuenteDatos as fila where fila.fuenteDatos.codigo = :idFuenteDato)";
		final Query queryValores = entityManager.createQuery(sqlValores);
		queryValores.setParameter("idFuenteDato", idFuenteDato);
		queryValores.executeUpdate();

		// Borramos filas de datos de la fuente de datos
		final String sql = "delete from JFilasFuenteDatos as a where a.fuenteDatos.codigo = :idFuenteDato";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFuenteDato", idFuenteDato);
		query.executeUpdate();

		// Borramos fuente de datos
		entityManager.remove(hfuenteDato);
	}

	@Override
	public void importarCSV(final Long idFuenteDatos, final CsvDocumento csv) {
		// Borramos filas de datos de la fuente de datos
		final String sqlValores = "delete from JValorFuenteDatos as a where a.filaFuenteDatos.codigo in (select fila from JFilasFuenteDatos as fila where fila.fuenteDatos.codigo = :idFuenteDato)";
		final Query queryValores = entityManager.createQuery(sqlValores);
		queryValores.setParameter("idFuenteDato", idFuenteDatos);
		queryValores.executeUpdate();

		// Borramos filas de datos de la fuente de datos
		final String sql = "delete from JFilasFuenteDatos as a where a.fuenteDatos.codigo = :idFuenteDato";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFuenteDato", idFuenteDatos);
		query.executeUpdate();

		final JFuenteDatos jfuenteDato = entityManager.find(JFuenteDatos.class, idFuenteDatos);

		// Insertamos nuevas filas
		for (int fila = 0; fila < csv.getNumeroFilas(); fila++) {
			final JFilasFuenteDatos jfila = new JFilasFuenteDatos();
			jfila.setFuenteDatos(jfuenteDato);
			for (int columna = 0; columna < csv.getColumnas().length; columna++) {
				final String col = csv.getColumnas()[columna];
				String valor = null;
				try {
					valor = csv.getValor(fila, col);
				} catch (final Exception exception) {
					throw new FuenteDatosCSVNoExisteCampoException(
							"No existe el campo para fila:" + fila + " col:" + col, exception);
				}

				final JCampoFuenteDatos campo = jfuenteDato.getJFuenteCampo(col);

				if (campo == null) {
					throw new FuenteDatosCSVNoExisteCampoException("No existe el campo " + col);
				}
				final JValorFuenteDatos jvalor = new JValorFuenteDatos();
				jvalor.setCampoFuenteDatos(campo);
				jvalor.setFilaFuenteDatos(jfila);
				jvalor.setValor(valor);
				jfila.addValor(jvalor);
				jfila.setFuenteDatos(jfuenteDato);
			}

			entityManager.persist(jfila);
		}

		if (!this.contraintPK(jfuenteDato)) {
			throw new FuenteDatosPkException("PK repetida");
		}
	}

	@Override
	public FuenteDatos getByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea,
			Long codigoFD) {
		Query query = getQuery(false, ambito, identificador, codigoEntidad, codigoArea, codigoFD);
		final List<JFuenteDatos> fuentesDatos = query.getResultList();
		final FuenteDatos fd;
		if (fuentesDatos == null || fuentesDatos.isEmpty()) {
			fd = null;
		} else {
			fd = fuentesDatos.get(0).toModel();
		}
		return fd;
	}

	@Override
	public boolean existeFDByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea,
			Long codigoFD) {
		Query query = getQuery(true, ambito, identificador, codigoEntidad, codigoArea, codigoFD);
		final Long cuantos = (Long) query.getSingleResult();
		return cuantos != 0l;
	}

	private Query getQuery(boolean isTotal, TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoFD) {
		final StringBuilder sql = new StringBuilder("select ");
		if (isTotal) {
			sql.append(" count(d) ");
		} else {
			sql.append(" d ");
		}
		sql.append(" from JFuenteDatos d where d.ambito like :ambito ");
		if (ambito == TypeAmbito.AREA) {
			sql.append(" AND d.area.codigo = :codigoArea");
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			sql.append(" AND d.entidad.codigo = :codigoEntidad");
		}
		if (identificador != null && !identificador.isEmpty()) {
			sql.append(" AND d.identificador = :identificador");
		}
		if (codigoFD != null) {
			sql.append(" AND d.codigo != :codigoFD");
		}

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.AREA) {
			query.setParameter("codigoArea", codigoArea);
		}
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("codigoEntidad", codigoEntidad);
		}
		if (identificador != null && !identificador.isEmpty()) {
			query.setParameter("identificador", identificador);
		}
		if (codigoFD != null) {
			query.setParameter("codigoFD", codigoFD);
		}

		return query;
	}

	@Override
	public ValoresDominio realizarConsultaFuenteDatos(final TypeAmbito ambito, final String idEntidad,
			final String idArea, final String idDominio, final List<ValorParametroDominio> parametros) {

		// TODO MODIFICAR PARA SACAR LA LOGICA A UN COMPONENT Y DEJAR EN EL DAO
		// SOLO EL ACCESO A DATOS

		// Obtenemos el dominio
		Dominio result = null;
		final StringBuilder sql = new StringBuilder(
				"SELECT d FROM JDominio d where d.ambito =:ambito and d.identificador = :idDominio");
		if (ambito == TypeAmbito.ENTIDAD) {
			sql.append(" and d.entidad.identificador = :idEntidad");
		}
		if (ambito == TypeAmbito.AREA) {
			sql.append(" and d.area.entidad.identificador = :idEntidad and d.area.identificador = :idArea");
		}
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idDominio", idDominio);
		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("idEntidad", idEntidad);
		}
		if (ambito == TypeAmbito.AREA) {
			query.setParameter("idEntidad", idEntidad);
			query.setParameter("idArea", idArea);
		}

		final List<JDominio> list = query.getResultList();

		if (!list.isEmpty()) {
			result = list.get(0).toModel();
		}

		if (result == null) {
			throw new FuenteDatosConsultaDominioPeticionIncorrecta("No se puede consultar el dominio indicado");
		}

		if (result.getParametros() != null && result.getParametros().size() != parametros.size()) {
			throw new FuenteDatosConsultaDominioPeticionIncorrecta("No coincide número de parámetros");
		}

		return consultaFuenteDatos(result.getSqlDecoded(), parametros, ambito, idEntidad, idArea);

	}

	private ValoresDominio consultaFuenteDatos(final String sql, final List<ValorParametroDominio> parametros,
			TypeAmbito ambito, String idEntidad, String idArea) {
		// Verificamos estructura fuente de datos
		final ConsultaFuenteDatos cfd = FuenteDatosUtil.decodificarConsulta(sql);

		// Recuperamos filas
		final List<JFilasFuenteDatos> filas = realizarConsulta(cfd.getIdFuenteDatos(), cfd.getFiltros(), parametros,
				ambito, idEntidad, idArea);

		// Ordenamos filas
		FuenteDatosUtil.ordenarFilas(filas, cfd.getCampoOrden());

		// Creamos valores fuente de datos
		final ValoresDominio vfd = FuenteDatosUtil.generarValores(filas, cfd.getCampos());

		return vfd;

	}

	private List<JFilasFuenteDatos> realizarConsulta(final String idFuenteDatos,
			final List<FiltroConsultaFuenteDatos> list, final List<ValorParametroDominio> parametros, TypeAmbito ambito,
			String idEntidad, String idArea) {

		final String sql = generarSql(list, ambito, idEntidad, idArea);

		final Query query = entityManager.createQuery(sql);

		query.setParameter("idFuenteDatos", idFuenteDatos);
		if (list != null && list.size() > 0) {
			for (int numFiltro = 0; numFiltro < list.size(); numFiltro++) {
				final FiltroConsultaFuenteDatos ffd = list.get(numFiltro);
				query.setParameter("campoFiltro" + numFiltro, ffd.getCampo().toUpperCase());
				final String valorParametro = FuenteDatosUtil.getValorParametro(ffd.getParametro(), parametros);
				if (valorParametro == null) {
					throw new FuenteDatosConsultaDominioPeticionIncorrecta(
							"No existe valor para parametro " + ffd.getParametro());
				}
				query.setParameter("valorFiltro" + numFiltro, valorParametro);
			}
		}

		if (ambito.equals(TypeAmbito.AREA)) {
			query.setParameter("area", idArea);
			query.setParameter("entidad", idEntidad);
		} else if (ambito.equals(TypeAmbito.ENTIDAD)) {
			query.setParameter("entidad", idEntidad);
		}

		return query.getResultList();
	}

	private String generarSql(final List<FiltroConsultaFuenteDatos> filtros, TypeAmbito ambito, String idEntidad,
			String idArea) {
		StringBuilder select = new StringBuilder("SELECT DISTINCT f \nFROM  JFilasFuenteDatos f");
		final String orderBy = "\n ORDER BY f.codigo";

		if (filtros != null && !filtros.isEmpty()) {
			select.append(", ");
			for (int i = 0; i < filtros.size(); i++) {
				if (i > 0) {
					select.append(", ");
				}
				select.append("JValorFuenteDatos v" + i);
			}
		}

		StringBuilder where = new StringBuilder("\nWHERE \n");

		where.append("   f.fuenteDatos.identificador = :idFuenteDatos");

		if (filtros != null && !filtros.isEmpty()) {
			where.append(" AND \n   ");
			for (int i = 0; i < filtros.size(); i++) {
				if (i > 0) {
					where.append(" AND ");
				}
				where.append("v" + i + ".filaFuenteDatos = f");
			}
		}

		where.append("\n");

		if (filtros != null && !filtros.isEmpty()) {
			where.append(" AND ( ");
			for (int numFiltro = 0; numFiltro < filtros.size(); numFiltro++) {
				final FiltroConsultaFuenteDatos ffd = filtros.get(numFiltro);
				where.append((numFiltro == 0) ? "" : ffd.getConector());
				where.append("\n     ( ");
				where.append(" (upper(v" + numFiltro + ".campoFuenteDatos.idCampo) = :campoFiltro" + numFiltro);

				if (FiltroConsultaFuenteDatos.LIKE.equals(ffd.getOperador())) {
					where.append(" AND upper(v" + numFiltro + ".valor) LIKE '%' || upper(:valorFiltro" + numFiltro
							+ ") || '%' ) ");
				} else {
					where.append(" AND upper(v" + numFiltro + ".valor) = upper(:valorFiltro" + numFiltro + ") ) ");
				}

				where.append(") ");
			}
			where.append("\n ) ");
		}

		if (ambito == TypeAmbito.ENTIDAD) {
			where.append(" and f.fuenteDatos.entidad.identificador = :entidad");
		}
		if (ambito == TypeAmbito.AREA) {
			where.append(
					" and f.fuenteDatos.area.entidad.identificador = :entidad and f.fuenteDatos.area.identificador = :area");
		}

		return select.toString() + where.toString() + orderBy;
	}

	@Override
	public JFuenteDatos importarFD(final FilaImportarDominio filaDominio, final TypeAmbito ambito, final Long idEntidad,
			final Long idArea) throws Exception {

		if (filaDominio.getAccion() == TypeImportarAccion.MANTENER) {
			// No debería entrar por aquí porque por regla general, sólo se
			// llama si
			// reemplaza, pero por si acaso.
			if (filaDominio.getFuenteDatosActual() == null) {
				return null;
			} else {
				return entityManager.find(JFuenteDatos.class, filaDominio.getFuenteDatosActual().getCodigo());
			}

		} else {

			// Si no existe tanto el fuente de datos como el por identificador, lo creamos.
			if (filaDominio.getFuenteDatosActual() == null) {

				if (this.existeFDByIdentificador(ambito, filaDominio.getFuenteDatos().getIdentificador(), idEntidad,
						idArea, null)) {
					// Ha sido importado ya por otro dominio
					return entityManager.find(JFuenteDatos.class, this.getByIdentificador(ambito,
							filaDominio.getFuenteDatos().getIdentificador(), idEntidad, idArea, null).getCodigo());

				} else {

					final FuenteDatos fd = filaDominio.getFuenteDatos();
					fd.setCodigo(null);
					final JFuenteDatos jfuente = new JFuenteDatos();
					jfuente.fromModel(fd);
					final List<FuenteDatosCampo> campos = fd.getCampos();
					final Set<JCampoFuenteDatos> jcampos = new HashSet<>();
					jfuente.setCampos(jcampos);
					if (ambito == TypeAmbito.ENTIDAD) {
						final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
						jfuente.setEntidad(jEntidad);
					}
					if (ambito == TypeAmbito.AREA) {
						final JArea jArea = entityManager.find(JArea.class, idArea);
						jfuente.setArea(jArea);
					}
					entityManager.persist(jfuente);

					for (final FuenteDatosCampo campo : campos) {
						final JCampoFuenteDatos jcampo = new JCampoFuenteDatos();
						jcampo.fromModel(campo);
						jcampo.setCodigo(null);
						jcampo.setFuenteDatos(jfuente);

						jcampos.add(jcampo);
					}

					jfuente.setCampos(jcampos);
					entityManager.merge(jfuente);

					// Flusheamos los datos creados por si acaso
					entityManager.flush();

					final CsvDocumento csvDocumento = CsvUtil
							.importar(new ByteArrayInputStream(filaDominio.getFuenteDatosContent()));
					this.importarCSV(jfuente.getCodigo(), csvDocumento);

					return jfuente;
				}
			} else {

				// Borramos filas de datos de la fuente de datos
				final String sqlValores = "delete from JValorFuenteDatos as a where a.filaFuenteDatos.codigo in (select fila from JFilasFuenteDatos as fila where fila.fuenteDatos.codigo = :idFuenteDato)";
				final Query queryValores = entityManager.createQuery(sqlValores);
				queryValores.setParameter("idFuenteDato", filaDominio.getFuenteDatosActual().getCodigo());
				queryValores.executeUpdate();

				// Borramos filas de datos de la fuente de datos
				final String sql = "delete from JFilasFuenteDatos as a where a.fuenteDatos.codigo = :idFuenteDato";
				final Query query = entityManager.createQuery(sql);
				query.setParameter("idFuenteDato", filaDominio.getFuenteDatosActual().getCodigo());
				query.executeUpdate();

				// Borramos filas de datos de la fuente de datos
				final String sqlCampos = "delete from JCampoFuenteDatos as a where a.fuenteDatos.codigo = :idFuenteDato";
				final Query queryCampos = entityManager.createQuery(sqlCampos);
				queryCampos.setParameter("idFuenteDato", filaDominio.getFuenteDatosActual().getCodigo());
				queryCampos.executeUpdate();

				// Flusheamos los datos borrados por si acaso
				entityManager.flush();

				final JFuenteDatos jfuenteDatos = entityManager.find(JFuenteDatos.class,
						filaDominio.getFuenteDatosActual().getCodigo());
				for (final FuenteDatosCampo campo : filaDominio.getFuenteDatos().getCampos()) {
					campo.setCodigo(null);
					final JCampoFuenteDatos jcampo = new JCampoFuenteDatos();
					jcampo.fromModel(campo);
					jcampo.setFuenteDatos(jfuenteDatos);
					entityManager.persist(jcampo);
				}

				// Flusheamos los datos vueltos a crear
				entityManager.flush();

				final CsvDocumento csvDocumento = CsvUtil
						.importar(new ByteArrayInputStream(filaDominio.getFuenteDatosContent()));
				this.importarCSV(jfuenteDatos.getCodigo(), csvDocumento);
				return jfuenteDatos;
			}
		}
	}

	@Override
	public ValoresDominio realizarConsultaBD(final String jndi, final String sql,
			final List<ValorParametroDominio> parametros) {

		final ValoresDominio valoresDominio = new ValoresDominio();

		try {
			final ValoresDominio res = new ValoresDominio();

			// Traducimos sql por si esta en formato antiguo
			final String query = transformSql(sql, parametros);

			// Obtenemos datasource
			final Context ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiPath + jndi);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
			final MapSqlParameterSource params = new MapSqlParameterSource();
			if (parametros != null) {
				for (final ValorParametroDominio param : parametros) {
					params.addValue(param.getCodigo(), param.getValor());
				}
			}
			// Verificamos si es un select o un insert
			if (query.trim().toUpperCase().startsWith("INSERT")) {
				// INSERT
				final int rowsUpdated = jdbcTemplate.update(query, params);
				final int numFila = res.addFila();
				res.setValor(numFila, "ROWS_INSERTED", "" + rowsUpdated);
			} else {
				// SELECT
				final List<Map<String, Object>> queryRes = jdbcTemplate.queryForList(query, params);
				// Creamos objeto valores dominio
				for (final Map<String, Object> fila : queryRes) {
					final int numFila = res.addFila();
					for (final String column : fila.keySet()) {
						final Object valor = fila.get(column);
						if (valor != null) {
							res.setValor(numFila, column, valor.toString());
						} else {
							res.setValor(numFila, column, null);
						}
					}
				}

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
	 * Transforma [#PARAMETROSDOMINIO.CODIGO#] a :CODIGO.
	 *
	 * @param pTramiteDef Def tramite
	 * @param pSql        Sql
	 * @param pParametros Parametros
	 * @return Sql transformada
	 */
	private String transformSql(final String pSql, final List<ValorParametroDominio> pParametros) {
		String sql = pSql;
		if (pParametros != null) {
			for (final ValorParametroDominio parametro : pParametros) {
				sql = sql.replace("'[#PARAMETROSDOMINIO." + parametro.getCodigo() + "#]'", ":" + parametro.getCodigo());
				sql = sql.replace("[#PARAMETROSDOMINIO." + parametro.getCodigo() + "#]", ":" + parametro.getCodigo());
			}
		}
		return sql;
	}

	@Override
	public FuenteDatos clonar(String dominioID, TypeClonarAccion accion, FuenteDatos ifd, final Long idEntidad,
			final Long areaID) {
		JDominio dominio = entityManager.find(JDominio.class, Long.valueOf(dominioID));
		JFuenteDatos fd = null;
		JFuenteDatos fdOriginal = dominio.getFuenteDatos();

		// Paso extra, por si acaso, al crear, resulta que ya existe
		// En caso de que ya existe y la accion fuese crear, pasa automáticamente a
		// reemplazar
		if (accion == TypeClonarAccion.CREAR && existeFDByIdentificador(TypeAmbito.fromString(fdOriginal.getAmbito()),
				fdOriginal.getIdentificador(), idEntidad, areaID, null)) {
			accion = TypeClonarAccion.REEMPLAZAR;
		}

		if (accion == TypeClonarAccion.REEMPLAZAR) {
			fd = entityManager.find(JFuenteDatos.class, ifd.getCodigo());
			fd.setDescripcion(fdOriginal.getDescripcion());
			entityManager.merge(fd);

			///// BORRAMOS LOS CAMPOS, FILAS Y DATOS DE LA FUENTE DE DATOS.
			// Borramos filas de datos de la fuente de datos
			final String sqlValores = "delete from JValorFuenteDatos as a where a.filaFuenteDatos.codigo in (select fila from JFilasFuenteDatos as fila where fila.fuenteDatos.codigo = :idFuenteDato)";
			final Query queryValores = entityManager.createQuery(sqlValores);
			queryValores.setParameter("idFuenteDato", fd.getCodigo());
			queryValores.executeUpdate();

			// Borramos filas de datos de la fuente de datos
			final String sql = "delete from JFilasFuenteDatos as a where a.fuenteDatos.codigo = :idFuenteDato";
			final Query query = entityManager.createQuery(sql);
			query.setParameter("idFuenteDato", fd.getCodigo());
			query.executeUpdate();

			// Borramos filas de datos de la fuente de datos
			final String sqlCampos = "delete from JCampoFuenteDatos as a where a.fuenteDatos.codigo = :idFuenteDato";
			final Query queryCampos = entityManager.createQuery(sqlCampos);
			queryCampos.setParameter("idFuenteDato", fd.getCodigo());
			queryCampos.executeUpdate();

			// Flusheamos los datos borrados por si acaso
			entityManager.flush();

		} else if (accion == TypeClonarAccion.CREAR) { // CREAMOS

			fd = new JFuenteDatos();
			fd.setDescripcion(fdOriginal.getDescripcion());
			fd.setAmbito(fdOriginal.getAmbito());
			JArea area = null;
			if (areaID != null) {
				area = entityManager.find(JArea.class, areaID);
			}
			fd.setArea(area);
			JEntidad entidad = null;
			if (idEntidad != null) {
				entidad = entityManager.find(JEntidad.class, idEntidad);
			}
			fd.setEntidad(entidad);
			fd.setIdentificador(fdOriginal.getIdentificador());
			entityManager.persist(fd);
		}

		Map<Long, JCampoFuenteDatos> campos = new HashMap<>();
		Map<Long, JFilasFuenteDatos> filas = new HashMap<>();

		/// Duplicamos los campos.
		for (final JCampoFuenteDatos jcampo : this.getJFuenteCampos(fdOriginal.getCodigo())) {

			JCampoFuenteDatos jcamponew = new JCampoFuenteDatos();
			jcamponew.setClavePrimaria(jcampo.getClavePrimaria());
			jcamponew.setFuenteDatos(fd);
			jcamponew.setIdCampo(jcampo.getIdCampo());
			jcamponew.setOrden(jcampo.getOrden());
			entityManager.persist(jcamponew);
			campos.put(jcampo.getCodigo(), jcamponew);
		}

		// Duplicamos las filas
		for (JFilasFuenteDatos jfila : this.getJFilasFuenteDatos(fdOriginal.getCodigo())) {

			JFilasFuenteDatos jfilanew = new JFilasFuenteDatos();
			jfilanew.setFuenteDatos(fd);
			Set<JValorFuenteDatos> valoresFuenteDatos = new HashSet<>();
			jfilanew.setValoresFuenteDatos(valoresFuenteDatos);
			entityManager.persist(jfilanew);
			filas.put(jfila.getCodigo(), jfilanew);
		}

		// Duplicamos los datos
		for (JValorFuenteDatos valor : this.getJValoresFuenteDatos(fdOriginal.getCodigo())) {
			JValorFuenteDatos jvalornew = new JValorFuenteDatos();
			jvalornew.setCampoFuenteDatos(campos.get(valor.getCampoFuenteDatos().getCodigo()));
			jvalornew.setFilaFuenteDatos(filas.get(valor.getFilaFuenteDatos().getCodigo()));
			jvalornew.setValor(valor.getValor());
			entityManager.persist(jvalornew);
		}

		// Flusheamos los datos vueltos a crear
		entityManager.flush();

		return fd.toModel();

	}

}
