package es.caib.sistrages.core.service.repository.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FicheroExternoException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.ContenidoFichero;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.types.TypePropiedadGlobal;
import es.caib.sistrages.core.api.util.GeneradorId;
import es.caib.sistrages.core.service.repository.model.JConfiguracionGlobal;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JFicheroExterno;

/**
 * FicheroExternoDao implementación.
 */
@Repository("ficheroExternoDao")
public class FicheroExternoDaoImpl implements FicheroExternoDao {

	/** LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FicheroExternoDaoImpl.class);

	public FicheroExternoDaoImpl() {
		super();
	}

	/** Entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ContenidoFichero getContentById(final Long id) {
		// Obtiene metadatos fichero
		final JFichero fic = entityManager.find(JFichero.class, id);
		if (fic == null) {
			throw new NoExisteDato("No existe fichero con id: " + id);
		}

		// Obtiene path fichero
		final String pathFile = getPathAbsolutoFichero(id);
		// Obtiene contenido fichero
		try {
			final FileInputStream fis = new FileInputStream(pathFile);
			final byte[] content = IOUtils.toByteArray(fis);
			fis.close();

			final ContenidoFichero cf = new ContenidoFichero();
			cf.setFilename(fic.getNombre());
			cf.setContent(content);

			return cf;
		} catch (final IOException e) {
			throw new FicheroExternoException("Error al acceder al fichero " + id + " con path " + pathFile, e);
		}
	}

	@Override
	public String getPathById(final Long id) {
		return getPathAbsolutoFichero(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void purgarFicheros() {
		final Query query = entityManager.createQuery("select f from JFicheroExterno f where f.borrar = TRUE");
		final List<JFicheroExterno> results = query.getResultList();
		if (results != null) {
			for (final JFicheroExterno jFicheroExterno : results) {
				// Borramos fichero en disco
				final String pathAbsoluteFichero = getPathAlmacenamiento() + jFicheroExterno.getReferenciaExterna();
				final File file = new File(pathAbsoluteFichero);
				final boolean deleted = FileUtils.deleteQuietly(file);
				if (!deleted) {
					LOG.warn("No se ha podido borrar fichero " + pathAbsoluteFichero);
				}
				// Borramos fichero en BD
				entityManager.remove(jFicheroExterno);
			}
		}
	}

	@Override
	public void guardarFichero(final Long idEntidad, final Fichero fichero, final byte[] content) {

		// Si existe fichero anterior, lo marca a borrado
		final JFicheroExterno jFicheroExterno = getById(fichero.getId());
		if (jFicheroExterno != null) {
			jFicheroExterno.setBorrar(true);
			entityManager.merge(jFicheroExterno);
		}

		// Crea nuevo fichero
		final String pathFichero = calcularPathRelativoFichero(idEntidad, fichero);
		final JFicheroExterno jFicheroExternoNew = new JFicheroExterno();
		jFicheroExternoNew.setCodigoFichero(fichero.getId());
		jFicheroExternoNew.setFechaReferencia(new Date());
		jFicheroExternoNew.setReferenciaExterna(pathFichero);
		entityManager.persist(jFicheroExternoNew);

		// Almacena fichero
		final String pathAbsolutoFichero = getPathAlmacenamiento() + "/" + pathFichero;
		try {
			final ByteArrayInputStream bis = new ByteArrayInputStream(content);
			final File file = new File(pathAbsolutoFichero);
			FileUtils.copyInputStreamToFile(bis, file);
			bis.close();
		} catch (final IOException ex) {
			throw new FicheroExternoException(
					"Error al crear fichero externo " + fichero.getId() + " con path " + pathAbsolutoFichero, ex);
		}

	}

	@Override
	public void marcarBorrar(final Long id) {
		// Si existe fichero anterior, lo marca a borrado
		final JFicheroExterno jFicheroExterno = getById(id);
		if (jFicheroExterno != null) {
			jFicheroExterno.setBorrar(true);
			entityManager.merge(jFicheroExterno);
		}
	}

	// ----------- PRIVATE ----------------------
	/**
	 * Obtiene path almacenamiento.
	 *
	 * @return path almacenamiento
	 */
	@SuppressWarnings("unchecked")
	private String getPathAlmacenamiento() {
		final Query query = entityManager
				.createQuery("select p from JConfiguracionGlobal p where p.propiedad = :propiedad");
		query.setParameter("propiedad", TypePropiedadGlobal.PATH_ALMACENAMIENTO_FICHEROS.toString());
		final List<JConfiguracionGlobal> results = query.getResultList();
		if (results == null || results.isEmpty() || results.size() > 1) {
			throw new FicheroExternoException("No existe propiedad de configuracion '"
					+ TypePropiedadGlobal.PATH_ALMACENAMIENTO_FICHEROS.toString() + "'");
		}
		final JConfiguracionGlobal jConfiguracionGlobal = results.get(0);
		return jConfiguracionGlobal.getValor();
	}

	/**
	 * Obtiene path fichero.
	 *
	 * @param id
	 *            id fichero
	 * @return path fichero (nulo si no existe fichero asociado)
	 */
	private String getPathAbsolutoFichero(final Long id) {
		final String pathAlmacenamiento = getPathAlmacenamiento();
		final JFicheroExterno jFicheroExterno = getById(id);
		if (jFicheroExterno == null) {
			throw new FicheroExternoException("No existe fichero externo asociado al fichero");
		}
		final String pathFile = pathAlmacenamiento + jFicheroExterno.getReferenciaExterna();
		return pathFile;
	}

	/**
	 * Calcula path fichero.
	 *
	 * @param idEntidad
	 *            Id entidad
	 * @param fichero
	 *            fichero
	 * @return path fichero
	 */
	private String calcularPathRelativoFichero(final Long idEntidad, final Fichero fichero) {
		String path;
		if (fichero.isPublico()) {
			path = "/publico";
		} else {
			path = "/interno";
		}
		path += "/" + idEntidad + "/" + GeneradorId.generarId() + "." + FilenameUtils.getExtension(fichero.getNombre());
		return path;
	}

	/**
	 * Obtiene fichero externo.
	 *
	 * @param id
	 *            id
	 * @return fichero externo
	 */
	@SuppressWarnings("unchecked")
	private JFicheroExterno getById(final Long id) {
		final Query query = entityManager
				.createQuery("select f from JFicheroExterno f where f.codigoFichero = :id and f.borrar = FALSE");
		query.setParameter("id", id);

		final List<JFicheroExterno> results = query.getResultList();
		if (results == null || results.isEmpty()) {
			return null;
		}
		if (results.size() > 1) {
			throw new FicheroExternoException("Existe mas de un fichero externo asociado al fichero");
		}
		return results.get(0);
	}

}
