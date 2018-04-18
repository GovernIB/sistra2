package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.CSVNoExisteCampoException;
import es.caib.sistrages.core.api.exception.CSVPkException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JCampoFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFilasFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JValorFuenteDatos;

@Repository("fuenteDatoDao")
public class FuenteDatoDaoImpl implements FuenteDatoDao {

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
		FuenteDatosValores fuenteDato = null;
		final JFuenteDatos hfuenteDato = entityManager.find(JFuenteDatos.class, idFuenteDato);
		if (hfuenteDato != null) {
			// Establecemos datos
			fuenteDato = hfuenteDato.toModelV();
		}
		return fuenteDato;
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
	public void addFuenteDatoCampo(final FuenteDatosCampo fuenteDatoCampo, final Long idFuenteDato) {

		final JFuenteDatos jfuenteDato = entityManager.find(JFuenteDatos.class, idFuenteDato);
		final JCampoFuenteDatos jFuenteDatoCampo = new JCampoFuenteDatos();
		jFuenteDatoCampo.setFuenteDatos(jfuenteDato);
		jFuenteDatoCampo.fromModel(fuenteDatoCampo);
		entityManager.persist(jFuenteDatoCampo);

		/** Creamos los datos extras en las filas. */
		final Query query = entityManager
				.createQuery("Select fila from JFilasFuenteDatos fila where fila.fuenteDatos.id = " + idFuenteDato);
		final List<JFilasFuenteDatos> filas = query.getResultList();
		if (filas != null) {
			for (final JFilasFuenteDatos fila : filas) {
				final JValorFuenteDatos valor = new JValorFuenteDatos();
				valor.setCampoFuenteDatos(jFuenteDatoCampo);
				valor.setValor("");
				fila.addValor(valor);
				entityManager.merge(fila);
			}
		}
	}

	@Override
	public void updateFuenteDatoCampo(final FuenteDatosCampo fuenteDatoCampo) {

		final JCampoFuenteDatos jfuenteDato = entityManager.find(JCampoFuenteDatos.class, fuenteDatoCampo.getId());
		jfuenteDato.merge(fuenteDatoCampo);
		entityManager.persist(jfuenteDato);
	}

	@Override
	public void removeFuenteDatoCampo(final Long idFuenteDatoCampo) {
		final JCampoFuenteDatos hfuenteDatoCampo = entityManager.find(JCampoFuenteDatos.class, idFuenteDatoCampo);

		/** Creamos los datos extras en las filas. */
		final Query query = entityManager
				.createQuery("Select fila from JFilasFuenteDatos fila where fila.fuenteDatos.id = "
						+ hfuenteDatoCampo.getFuenteDatos().getCodigo());
		final List<JFilasFuenteDatos> filas = query.getResultList();
		if (filas != null) {
			for (final JFilasFuenteDatos fila : filas) {
				fila.removeValor(hfuenteDatoCampo.getCodigo());
				entityManager.merge(fila);
			}
		}

		if (hfuenteDatoCampo != null) {
			entityManager.remove(hfuenteDatoCampo);
		}
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

		String sql = "SELECT DISTINCT d FROM JFuenteDatos d ";

		if (ambito == TypeAmbito.AREA) {
			sql += " WHERE d.area.id = :id AND d.ambito LIKE '" + TypeAmbito.AREA + "'";
		} else if (ambito == TypeAmbito.ENTIDAD) {
			sql += " WHERE d.entidad.id = :id AND d.ambito LIKE '" + TypeAmbito.ENTIDAD + "'";
		} else if (ambito == TypeAmbito.GLOBAL) {
			sql += " WHERE d.ambito LIKE '" + TypeAmbito.GLOBAL + "'";
		}

		if (StringUtils.isNotBlank(filtro)) {
			sql += " AND ( LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro ) ";
		}

		sql += " ORDER BY d.identificador";

		final Query query = entityManager.createQuery(sql);

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
		final JFuenteDatos jFuenteDato = entityManager.find(JFuenteDatos.class, mFuenteDato.getCodigo());
		// jfuenteDato = JFuenteDatos.mergeModel(jfuenteDato, fuenteDato);

		// Borrar campos no pasados en modelo
		final List<JCampoFuenteDatos> camposBorrar = new ArrayList<>();
		for (final JCampoFuenteDatos jCampo : jFuenteDato.getCampos()) {
			boolean nocontiene = true;
			for (final FuenteDatosCampo mFuenteDatoCampo : mFuenteDato.getCampos()) {
				if (mFuenteDatoCampo.getId() != null && mFuenteDatoCampo.getId().compareTo(jCampo.getCodigo()) == 0) {
					nocontiene = false;
					break;
				}
			}

			if (nocontiene) {
				camposBorrar.add(jCampo);
			}
		}

		// Borramos los campos que han desaparecido en el guardado.
		for (final JCampoFuenteDatos jCampo : camposBorrar) {
			// Borramos en cada fila el valor
			for (final JFilasFuenteDatos fila : jFuenteDato.getFilas()) {
				final JValorFuenteDatos valor = fila.removeValor(jCampo.getCodigo());
				entityManager.remove(valor);
			}
			jFuenteDato.getCampos().remove(jCampo);
		}

		// Actualizamos campos pasados en modelo
		for (final FuenteDatosCampo campo : mFuenteDato.getCampos()) {

			// Obtenemos la fuente de dato (si no la tiene, hay que añadirla)
			JCampoFuenteDatos jcampoFuenteDatos = jFuenteDato.getJFuenteCampo(campo.getId());
			if (jcampoFuenteDatos == null) { // No existe el campo de fuente
				jcampoFuenteDatos = new JCampoFuenteDatos();
				if (campo.isClavePrimaria()) {
					jcampoFuenteDatos.setClavePrimaria("S");
				} else {
					jcampoFuenteDatos.setClavePrimaria("N");
				}
				jcampoFuenteDatos.setIdCampo(campo.getCodigo());
				jcampoFuenteDatos.setCodigo(campo.getId());
				jcampoFuenteDatos.setFuenteDatos(jFuenteDato);
				jcampoFuenteDatos.setOrden(campo.getOrden());
				jFuenteDato.getCampos().add(jcampoFuenteDatos);

				// Si no existe el campo, eso significa que hay que crearlo.
				for (final JFilasFuenteDatos fila : jFuenteDato.getFilas()) {

					final JValorFuenteDatos valor = new JValorFuenteDatos();
					valor.setCampoFuenteDatos(jcampoFuenteDatos);
					valor.setValor("");
					fila.addValor(valor);
				}

			} else {

				if (campo.isClavePrimaria()) {
					jcampoFuenteDatos.setClavePrimaria("S");
				} else {
					jcampoFuenteDatos.setClavePrimaria("N");
				}
				jcampoFuenteDatos.setIdCampo(campo.getCodigo());
				jcampoFuenteDatos.setOrden(campo.getOrden());

			}
		}

		entityManager.merge(jFuenteDato);
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
		final JFilasFuenteDatos jfila = new JFilasFuenteDatos();
		jfila.fromModel(fila);
		jfila.setFuenteDatos(jfuenteDato);
		entityManager.merge(jfila);

	}

	@Override
	public void updateFuenteDatoFila(final FuenteFila fila) {
		final JFilasFuenteDatos jfila = entityManager.find(JFilasFuenteDatos.class, fila.getId());
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

		final JFilasFuenteDatos fila = new JFilasFuenteDatos();
		fila.fromModel(fuenteFila);
		jFuenteDatos.addFila(fila);

		return contraintPK(jFuenteDatos);
	}

	@Override
	public void removeByEntidad(final Long idEntidad) {
		final List<FuenteDatos> listaFD = listarFuenteDatos(TypeAmbito.ENTIDAD, idEntidad, null);
		for (final FuenteDatos fd : listaFD) {
			borrarFuenteDatos(fd.getCodigo());
		}
	}

	/**
	 * Verifica si tras modificar una Fuente de Datos se mantiene la constraint de
	 * PK.
	 *
	 * @param fuenteDatos
	 *            Fuente datos
	 * @return true en caso de que se cumpla FK
	 */
	private boolean contraintPK(final JFuenteDatos fuenteDatos) {

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
				for (int i = 0; i < fuenteDatos.getFilas().size(); i++) {
					final JFilasFuenteDatos fila = fuenteDatos.getFilaFuenteDatos(i + 1);
					String pk = "";
					for (final Iterator<JCampoFuenteDatos> it = keys.iterator(); it.hasNext();) {
						final String idCampoPK = it.next().getIdCampo();
						pk = pk + separador + StringUtils.defaultString(fila.getValorFuenteDatos(idCampoPK));
					}
					if (pks.contains(pk)) {
						entityManager.detach(fuenteDatos);
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
	 * @param idFuenteDato
	 *            Fuente datos
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
					throw new CSVNoExisteCampoException("No existe el campo para fila:" + fila + " col:" + col,
							exception);
				}

				final JCampoFuenteDatos campo = jfuenteDato.getJFuenteCampo(col);

				if (campo == null) {
					throw new CSVNoExisteCampoException("No existe el campo " + col);
				}
				final JValorFuenteDatos jvalor = new JValorFuenteDatos();
				jvalor.setCampoFuenteDatos(campo);
				jvalor.setFilaFuenteDatos(jfila);
				jvalor.setValor(valor);
				jfila.addValor(jvalor);
				jfila.setFuenteDatos(jfuenteDato);
				jfuenteDato.getFilas().add(jfila);
				entityManager.persist(jfila);
			}
		}

		if (!this.contraintPK(jfuenteDato)) {
			throw new CSVPkException("PK repetida");
		}
	}

}
