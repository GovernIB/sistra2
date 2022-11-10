package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioSimple;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.FormRenderComponent;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.SeccionReutilizableDao;

@Service
@Transactional
public class FormularioInternoServiceImpl implements FormularioInternoService {

	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FormularioInternoServiceImpl.class);

	@Autowired
	FormateadorFormularioDao formateadorFormularioDao;

	@Autowired
	FormularioInternoDao formIntDao;

	@Autowired
	SeccionReutilizableDao seccionReutilizableDao;

	@Autowired
	DominioDao dominioDao;

	/** DAO Fichero Externo. */
	@Autowired
	FicheroExternoDao ficheroExternoDao;

	@Autowired
	FormRenderComponent formRenderComponent;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getFormularioInterno(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public DisenyoFormulario getFormularioInterno(final Long pId) {
		return formIntDao.getFormularioById(pId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * updateFormularioInterno(es.caib.sistrages.core.api.model. DisenyoFormulario)
	 */
	@Override
	@NegocioInterceptor
	public void updateFormularioInterno(final DisenyoFormulario pFormInt) {
		formIntDao.updateFormulario(pFormInt);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getFormularioInternoPaginas(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public DisenyoFormulario getFormularioInternoCompleto(final Long pId) {
		return formIntDao.getFormularioCompletoById(pId, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getFormularioInternoPaginas(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public DisenyoFormulario getFormularioInternoPaginas(final Long pId) {
		return formIntDao.getFormularioPaginasById(pId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getPaginaFormulario(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public PaginaFormulario getPaginaFormulario(final Long pId) {
		return formIntDao.getPaginaById(pId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * getContenidoPaginaFormulario(java.lang.Long)
	 */
	@Override
	@NegocioInterceptor
	public PaginaFormulario getContenidoPaginaFormulario(final Long pId) {
		return formIntDao.getContenidoPaginaById(pId, false);
	}

	@Override
	@NegocioInterceptor
	public ObjetoFormulario addComponenteFormulario(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina,
			final Long pIdLinea, final Integer pOrden, final String pPosicion, final Object objeto, boolean isTipoSeccion, String identificadorSeccion, String idTramiteVersion) {
		ObjetoFormulario retorno = formIntDao.addComponente(pTipoObjeto, pIdPagina, pIdLinea, pOrden, pPosicion, objeto, isTipoSeccion, identificadorSeccion);
		if (pTipoObjeto == TypeObjetoFormulario.SECCION_REUTILIZABLE) {
			LOG.debug("Extraemos los dominos");
			List<Dominio> dominios = seccionReutilizableDao.getDominiosByIdentificadorSeccion(((SeccionReutilizable)objeto).getIdentificador());
			if (dominios != null && !dominios.isEmpty()) {
				for(Dominio dominio : dominios) {
					if (dominio != null) {
						dominioDao.addTramiteVersion(dominio.getCodigo(), Long.valueOf(idTramiteVersion));
					}
				}
			}
		}
		return retorno;
	}


	@Override
	@NegocioInterceptor
	public ObjetoFormulario updateComponenteFormulario(final ComponenteFormulario pComponente) {
		return formIntDao.updateComponente(pComponente);

	}

	@Override
	@NegocioInterceptor
	public ComponenteFormulario getComponenteFormulario(final Long pId) {
		return formIntDao.getComponenteById(pId);
	}

	@Override
	@NegocioInterceptor
	public void removeComponenteFormulario(final Long pId) {
		formIntDao.removeComponenteFormulario(pId);
	}

	@Override
	@NegocioInterceptor
	public void removeLineaFormulario(final Long pId) {
		formIntDao.removeLineaFormulario(pId);
	}

	@Override
	@NegocioInterceptor
	public void updateOrdenComponenteFormulario(final Long pId, final Integer pOrden) {
		formIntDao.updateOrdenComponente(pId, pOrden);
	}

	@Override
	@NegocioInterceptor
	public void updateOrdenLineaFormulario(final Long pId, final Integer pOrden) {
		formIntDao.updateOrdenLinea(pId, pOrden);

	}

	@Override
	@NegocioInterceptor
	public List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormulario(final Long pId) {
		return formIntDao.getListaPlantillaIdiomaFormularioById(pId);
	}

	@Override
	@NegocioInterceptor
	public PlantillaIdiomaFormulario uploadPlantillaIdiomaFormulario(final Long idEntidad, final Long idPlantilla,
			final PlantillaIdiomaFormulario plantilla, final byte[] contents) {
		final PlantillaIdiomaFormulario newPlantilla = formIntDao.uploadPlantillaIdiomaFormulario(idPlantilla,
				plantilla);
		ficheroExternoDao.guardarFichero(idEntidad, newPlantilla.getFichero(), contents);

		return newPlantilla;
	}

	@Override
	@NegocioInterceptor
	public PlantillaFormateador uploadPlantillaFormateador(final Long idEntidad, final Long idPlantillaFormateador,
			final PlantillaFormateador plantillaFormateador, final byte[] contents) {
		final PlantillaFormateador newPlantilla = formateadorFormularioDao
				.uploadPlantillaFormateador(idPlantillaFormateador, plantillaFormateador);
		ficheroExternoDao.guardarFichero(idEntidad, newPlantilla.getFichero(), contents);

		return newPlantilla;
	}

	@Override
	@NegocioInterceptor
	public void removePlantillaIdiomaFormulario(final PlantillaIdiomaFormulario plantillaIdiomaFormulario) {
		if (plantillaIdiomaFormulario != null && plantillaIdiomaFormulario.getFichero() != null) {
			ficheroExternoDao.marcarBorrar(plantillaIdiomaFormulario.getFichero().getCodigo());
			formIntDao.removePlantillaIdiomaFormulario(plantillaIdiomaFormulario.getCodigo());
		}

	}

	@Override
	@NegocioInterceptor
	public String getPaginaFormularioHTMLDisenyo(final Long pIdPagina, final String pLang) {
		// TODO V0 Ver si esta funcion se esta usando???

		// La generación del HTML debe externalizarse en un Component para que
		// pueda ser llamado desde FormularioInternoService y RestApiService
		return "<html/>";
	}

	@Override
	@NegocioInterceptor
	public DisenyoFormularioSimple getFormularioInternoSimple(final Long idFormularioTramite, final Long idFormulario,
			final String idComponente, final String idPagina, final boolean cargarPaginasPosteriores, final boolean isSeccion, final String identificadorSeccion) {
		return formIntDao.getFormularioInternoSimple(idFormularioTramite, idFormulario, idComponente, idPagina,
				cargarPaginasPosteriores, isSeccion, identificadorSeccion);
	}

	@Override
	@NegocioInterceptor
	public String getIdentificadorFormularioInterno(final Long idFormulario) {
		return formIntDao.getIdentificadorFormularioInterno(idFormulario);
	}

	@Override
	@NegocioInterceptor
	public String generaPaginaHTMLEditor(final Long pIdForm, final Long pPage, final String pIdComponente,
			final String pLang, final Boolean pMostrarOcultos, final String pContexto) {
		return formRenderComponent.generaPaginaHTMLEditor(pIdForm, pPage, pIdComponente, pLang, pMostrarOcultos,
				pContexto);
	}

	@Override
	@NegocioInterceptor
	public boolean isIdElementoFormularioDuplicated(final Long idFormulario, final Long codElemento,
			final String identificador) {
		return formIntDao.isIdElementoFormularioDuplicated(idFormulario, codElemento, identificador);
	}

	@Override
	@NegocioInterceptor
	public void removePlantillaFormateador(final PlantillaFormateador plantillaFormateador) {
		if (plantillaFormateador != null && plantillaFormateador.getFichero() != null) {
			ficheroExternoDao.marcarBorrar(plantillaFormateador.getFichero().getCodigo());
			formateadorFormularioDao.removePlantillaFormateador(plantillaFormateador.getCodigo());
		}

	}

	@Override
	@NegocioInterceptor
	public ObjetoFormulario copyCutComponenteFormulario(final Long pIdPagina, final Long pIdLinea, final Integer pOrden,
			final String pPosicion, final Long idComponenteOriginal, final boolean cut) {
		return formIntDao.copyCutComponenteFormulario(pIdPagina, pIdLinea, pOrden, pPosicion, idComponenteOriginal,
				cut);
	}

	@Override
	@NegocioInterceptor
	public ObjetoFormulario copyCutLineaFormulario(final Long idPagina, final Long idLinea, final Integer orden,
			final String posicionamiento, final boolean cut) {
		return formIntDao.copyCutLineaFormulario(idPagina, idLinea, orden, posicionamiento, cut);
	}

	@Override
	@NegocioInterceptor
	public void guardarPagina(final PaginaFormulario pagina) {
		formIntDao.guardarPagina(pagina);
	}

}
