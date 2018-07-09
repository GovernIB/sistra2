package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;

@Service
@Transactional
public class FormularioInternoServiceImpl implements FormularioInternoService {

    /**
     * Constante LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(FormularioInternoServiceImpl.class);

    @Autowired
    FormularioInternoDao formIntDao;

    /** DAO Fichero Externo. */
    @Autowired
    FicheroExternoDao ficheroExternoDao;

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
     * updateFormularioInterno(es.caib.sistrages.core.api.model.
     * DisenyoFormulario)
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
        return formIntDao.getFormularioCompletoById(pId);
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
        return formIntDao.getContenidoPaginaById(pId);
    }

    @Override
    @NegocioInterceptor
    public ObjetoFormulario addComponenteFormulario(
            final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina,
            final Long pIdLinea, final Integer pOrden, final String pPosicion) {
        return formIntDao.addComponente(pTipoObjeto, pIdPagina, pIdLinea,
                pOrden, pPosicion);
    }

    @Override
    @NegocioInterceptor
    public ObjetoFormulario updateComponenteFormulario(
            final ComponenteFormulario pComponente) {
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
    public void updateOrdenComponenteFormulario(final Long pId,
            final Integer pOrden) {
        formIntDao.updateOrdenComponente(pId, pOrden);
    }

    @Override
    @NegocioInterceptor
    public void updateOrdenLineaFormulario(final Long pId,
            final Integer pOrden) {
        formIntDao.updateOrdenLinea(pId, pOrden);

    }

    @Override
    @NegocioInterceptor
    public List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormulario(
            final Long pId) {
        return formIntDao.getListaPlantillaIdiomaFormularioById(pId);
    }

    @Override
    @NegocioInterceptor
    public PlantillaIdiomaFormulario uploadPlantillaIdiomaFormulario(
            final Long idEntidad, final Long idPlantilla,
            final PlantillaIdiomaFormulario plantilla, final byte[] contents) {
        final PlantillaIdiomaFormulario newPlantilla = formIntDao
                .uploadPlantillaIdiomaFormulario(idPlantilla, plantilla);
        ficheroExternoDao.guardarFichero(idEntidad, newPlantilla.getFichero(),
                contents);

        return newPlantilla;
    }

    @Override
    @NegocioInterceptor
    public void removePlantillaIdiomaFormulario(
            final PlantillaIdiomaFormulario plantillaIdiomaFormulario) {
        if (plantillaIdiomaFormulario != null
                && plantillaIdiomaFormulario.getFichero() != null) {
            ficheroExternoDao.marcarBorrar(
                    plantillaIdiomaFormulario.getFichero().getCodigo());
            formIntDao.removePlantillaIdiomaFormulario(
                    plantillaIdiomaFormulario.getCodigo());
        }

    }

    @Override
    @NegocioInterceptor
    public String getPaginaFormularioHTMLDisenyo(Long pIdPagina,
            String pLang) {
        // TODO PENDIENTE

        // La generación del HTML debe externalizarse en un Component para que
        // pueda ser llamado desde FormularioInternoService y RestApiService
        return "<html/>";
    }

}
