package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.IncidenciaValoracion;
import es.caib.sistrages.core.api.model.PlantillaEntidad;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao;
import es.caib.sistrages.core.service.repository.dao.FormularioSoporteDao;
import es.caib.sistrages.core.service.repository.dao.FuenteDatoDao;
import es.caib.sistrages.core.service.repository.dao.IncidenciaValoracionDao;
import es.caib.sistrages.core.service.repository.dao.PluginsDao;

@Service
@Transactional
public class EntidadServiceImpl implements EntidadService {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(EntidadServiceImpl.class);

	/** DAO Entidad. */
	@Autowired
	EntidadDao entidadDao;

	/** DAO Area. */
	@Autowired
	AreaDao areaDao;

	/** DAO Aviso. */
	@Autowired
	IncidenciaValoracionDao avisoDao;

	/** DAO Fichero Externo. */
	@Autowired
	FicheroExternoDao ficheroExternoDao;

	/** DAO Formulario soporte. */
	@Autowired
	FormularioSoporteDao formularioSoporteDao;

	/** DAO Plugins. */
	@Autowired
	PluginsDao pluginsDao;

	/** DAO Dominios. */
	@Autowired
	DominioDao dominiosDao;

	/** DAO Avisos. */
	@Autowired
	AvisoEntidadDao avisosDao;

	/** DAO Formateadores. */
	@Autowired
	FormateadorFormularioDao formateadorFormularioDao;

	/** DAO Fuente Datos. */
	@Autowired
	FuenteDatoDao fuenteDatoDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#loadEntidad(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public Entidad loadEntidad(final Long idEntidad) {
		Entidad result = null;
		result = entidadDao.getById(idEntidad);
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#loadEntidadByArea(java.lang
	 * .Long)
	 */
	@Override
	@NegocioInterceptor
	public Entidad loadEntidadByArea(final Long idArea) {
		Entidad result = null;
		result = entidadDao.getByArea(idArea);
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#addEntidad(es.caib.
	 * sistrages.core.api.model.Entidad)
	 */
	@Override
	@NegocioInterceptor
	public void addEntidad(final Entidad entidad) {
		entidadDao.add(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeEntidad(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeEntidad(final Long idEntidad) {

		// Recuperamos entidad
		final Entidad entidad = entidadDao.getById(idEntidad);
		if (entidad == null) {
			throw new NoExisteDato("No existen entidad con id " + idEntidad);
		}

		// Verificar dependencias: si tiene areas no dejamos borrar
		if (!areaDao.getAll(idEntidad).isEmpty()) {
			return false;
		}

		// Borramos plugins
		pluginsDao.removeByEntidad(idEntidad);

		// Borramos dominios
		dominiosDao.removeByEntidad(idEntidad);

		// Borramos fuente de datos
		fuenteDatoDao.removeByEntidad(idEntidad);

		// Borramos mensajes aviso
		avisosDao.removeByEntidad(idEntidad);

		// Borramos formateadores
		formateadorFormularioDao.removeByEntidad(idEntidad);

		// Borramos ficheros asociados
		borrarLogoGestor(idEntidad);
		borrarLogoAsistente(idEntidad);
		borrarCssAsistente(idEntidad);

		// Borramos entidad
		entidadDao.remove(idEntidad);

		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateEntidadSuperAdministrador(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	@NegocioInterceptor
	public void updateEntidadSuperAdministrador(final Entidad entidad) {
		entidadDao.updateSuperAdministrador(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateEntidadAdministradorEntidad(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	@NegocioInterceptor
	public void updateEntidadAdministradorEntidad(final Entidad entidad) {
		entidadDao.updateAdministradorEntidad(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#listEntidad(es.caib.
	 * sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	@NegocioInterceptor
	public List<Entidad> listEntidad(final TypeIdioma idioma, final String filtro) {
		return entidadDao.getAllByFiltro(idioma, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeLogoGestorEntidad(
	 * java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeLogoGestorEntidad(final Long idEntidad) {
		borrarLogoGestor(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeLogoAsistenteEntidad(
	 * java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeLogoAsistenteEntidad(final Long idEntidad) {
		borrarLogoAsistente(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeLogoAsistenteEntidad(
	 * java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeIconoAsistenteEntidad(final Long idEntidad) {
		borrarIconoAsistente(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeCssEntidad(java.lang.
	 * Long)
	 */
	@Override
	@NegocioInterceptor
	public void removeCssEntidad(final Long idEntidad) {
		borrarCssAsistente(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#uploadLogoGestorEntidad(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero, byte[])
	 */
	@Override
	@NegocioInterceptor
	public void uploadLogoGestorEntidad(final Long idEntidad, final Fichero fichero, final byte[] content) {

		final Fichero newFichero = entidadDao.uploadLogoGestor(idEntidad, fichero);

		ficheroExternoDao.guardarFichero(idEntidad, newFichero, content);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#uploadLogoAsistenteEntidad(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero, byte[])
	 */
	@Override
	@NegocioInterceptor
	public void uploadLogoAsistenteEntidad(final Long idEntidad, final Fichero fichero, final byte[] content) {
		final Fichero newFichero = entidadDao.uploadLogoAsistente(idEntidad, fichero);

		ficheroExternoDao.guardarFichero(idEntidad, newFichero, content);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#uploadLogoAsistenteEntidad(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero, byte[])
	 */
	@Override
	@NegocioInterceptor
	public void uploadIconoAsistenteEntidad(final Long idEntidad, final Fichero fichero, final byte[] content) {
		final Fichero newFichero = entidadDao.uploadIconoAsistente(idEntidad, fichero);

		ficheroExternoDao.guardarFichero(idEntidad, newFichero, content);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#uploadCssEntidad(java.lang.
	 * Long, es.caib.sistrages.core.api.model.Fichero, byte[])
	 */
	@Override
	@NegocioInterceptor
	public void uploadCssEntidad(final Long idEntidad, final Fichero fichero, final byte[] content) {
		final Fichero newFichero = entidadDao.uploadCssAsistente(idEntidad, fichero);

		ficheroExternoDao.guardarFichero(idEntidad, newFichero, content);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * listOpcionesFormularioSoporte(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public List<FormularioSoporte> listOpcionesFormularioSoporte(final Long idEntidad) {
		return formularioSoporteDao.getAll(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#loadOpcionFormularioSoporte
	 * (java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public FormularioSoporte loadOpcionFormularioSoporte(final Long id) {
		return formularioSoporteDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#addOpcionFormularioSoporte(
	 * java.lang.Long, es.caib.sistrages.core.api.model.FormularioSoporte)
	 */
	@Override
	@NegocioInterceptor
	public void addOpcionFormularioSoporte(final Long idEntidad, final FormularioSoporte fst) {
		formularioSoporteDao.add(idEntidad, fst);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateOpcionFormularioSoporte(es.caib.sistrages.core.api.model.
	 * FormularioSoporte)
	 */
	@Override
	@NegocioInterceptor
	public void updateOpcionFormularioSoporte(final FormularioSoporte fst) {
		formularioSoporteDao.update(fst);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * removeOpcionFormularioSoporte(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public boolean removeOpcionFormularioSoporte(final Long id) {
		formularioSoporteDao.remove(id);
		return true;
	}

	private void borrarCssAsistente(final Long idEntidad) {
		final Entidad entidad = entidadDao.getById(idEntidad);
		if (entidad != null && entidad.getCss() != null) {
			ficheroExternoDao.marcarBorrar(entidad.getCss().getCodigo());
			entidadDao.removeCssAsistente(idEntidad);
		}
	}

	private void borrarLogoAsistente(final Long idEntidad) {
		final Entidad entidad = entidadDao.getById(idEntidad);
		if (entidad != null && entidad.getLogoAsistente() != null) {
			ficheroExternoDao.marcarBorrar(entidad.getLogoAsistente().getCodigo());
			entidadDao.removeLogoAsistente(idEntidad);
		}
	}

	private void borrarIconoAsistente(final Long idEntidad) {
		final Entidad entidad = entidadDao.getById(idEntidad);
		if (entidad != null && entidad.getIconoAsistenteTramitacion() != null) {
			ficheroExternoDao.marcarBorrar(entidad.getIconoAsistenteTramitacion().getCodigo());
			entidadDao.removeIconoAsistente(idEntidad);
		}
	}

	private void borrarLogoGestor(final Long idEntidad) {
		final Entidad entidad = entidadDao.getById(idEntidad);
		if (entidad != null && entidad.getLogoGestor() != null) {
			ficheroExternoDao.marcarBorrar(entidad.getLogoGestor().getCodigo());
			entidadDao.removeLogoGestor(idEntidad);
		}
	}

	@Override
	@NegocioInterceptor
	public List<IncidenciaValoracion> getValoraciones(final Long idEntidad) {
		return avisoDao.getValoraciones(idEntidad);
	}

	@Override
	@NegocioInterceptor
	public IncidenciaValoracion loadValoracion(final Long idValoracion) {
		return avisoDao.loadValoracion(idValoracion);
	}

	@Override
	@NegocioInterceptor
	public void addValoracion(final Long idEntidad, final IncidenciaValoracion valoracion) {
		avisoDao.addValoracion(idEntidad, valoracion);
	}

	@Override
	@NegocioInterceptor
	public void updateValoracion(final IncidenciaValoracion valoracion) {
		avisoDao.updateValoracion(valoracion);
	}

	@Override
	@NegocioInterceptor
	public void removeValoracion(final Long idValoracion) {
		avisoDao.removeValoracion(idValoracion);
	}

	@Override
	@NegocioInterceptor
	public boolean existeCodigoDIR3(final String codigoDIR3, final Long idEntidad) {
		return entidadDao.existeCodigoDIR3(codigoDIR3, idEntidad);
	}

	@Override
	@NegocioInterceptor
	public boolean existeIdentificadorValoracion(final String identificador, final Long idEntidad, final Long codigo) {
		return avisoDao.existeIdentificador(identificador, idEntidad, codigo);
	}

	@Override
	@NegocioInterceptor
	public List<PlantillaEntidad> getListaPlantillasEmailFin(Long codEntidad) {
		return entidadDao.getListaPlantillasEmailFin(codEntidad);
	}

	@Override
	@NegocioInterceptor
	public PlantillaEntidad uploadPlantillasEmailFin(Long idEntidad, Long idPlantillaEntidad,
			PlantillaEntidad plantillaEntidad, byte[] contents) {

		final PlantillaEntidad newPlantilla = entidadDao.uploadPlantillasEmailFin(idPlantillaEntidad, plantillaEntidad,
				idEntidad);
		ficheroExternoDao.guardarFichero(idEntidad, newPlantilla.getFichero(), contents);

		return newPlantilla;
	}

	@Override
	@NegocioInterceptor
	public void removePlantillaEmailFin(PlantillaEntidad plantillaEntidad) {
		if (plantillaEntidad != null && plantillaEntidad.getFichero() != null) {
			ficheroExternoDao.marcarBorrar(plantillaEntidad.getFichero().getCodigo());
			entidadDao.removePlantillaEmailFin(plantillaEntidad.getCodigo());
		}
	}

	@Override
	@NegocioInterceptor
	public boolean existeEntidad(String identificador, Long codigo) {
		return entidadDao.existeFormulario(identificador, codigo);
	}
}
