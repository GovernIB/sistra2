package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.service.repository.model.JArea;
import es.caib.sistrages.core.service.repository.model.JCampoFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;

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
		// final Set<JCampoFuenteDatos> campos = jFuenteDato.getCampos();
		// jFuenteDato.setCampos(null);
		entityManager.persist(jFuenteDato);
		// jFuenteDato.setCampos(campos);
		// entityManager.persist(jFuenteDato);

	}

	@Override
	public void remove(final Long idFuenteDato) {
		final JFuenteDatos hfuenteDato = entityManager.find(JFuenteDatos.class, idFuenteDato);
		if (hfuenteDato != null) {
			entityManager.remove(hfuenteDato);
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
			sql += " AND LOWER(d.descripcion) LIKE :filtro OR LOWER(d.identificador) LIKE :filtro";
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
	public void updateFuenteDato(final FuenteDatos fuenteDato) {
		final JFuenteDatos jfuenteDato = entityManager.find(JFuenteDatos.class, fuenteDato.getCodigo());

		// Buscamos entre la fuente de datos original, aquellos que han desaparecido
		// (comparándolo con los campos que nos llegan por la entrada)
		final List<Long> camposParaBorrar = new ArrayList<>();
		for (final JCampoFuenteDatos campoOriginal : jfuenteDato.getCampos()) {
			boolean noencontrado = true;
			for (final FuenteDatosCampo campoNuevo : fuenteDato.getCampos()) {
				if (campoNuevo.getId() != null && campoNuevo.getId().compareTo(campoOriginal.getCodigo()) == 0) {
					noencontrado = false;
					break;
				}
			}
			if (noencontrado) {
				camposParaBorrar.add(campoOriginal.getCodigo());
			}
		}

		for (final Long idCampo : camposParaBorrar) {
			final JCampoFuenteDatos jcampoFuenteDatos = entityManager.find(JCampoFuenteDatos.class, idCampo);
			entityManager.remove(jcampoFuenteDatos);
		}

		// Convertir el dato que nos pasan en el J
		jfuenteDato.fromModel(fuenteDato);
		if (jfuenteDato.getCampos() != null) {
			for (final JCampoFuenteDatos campo : jfuenteDato.getCampos()) {
				if (campo.getFuenteDatos() == null) {
					campo.setFuenteDatos(jfuenteDato);
				}
			}
		}

		entityManager.merge(jfuenteDato);
	}
}
