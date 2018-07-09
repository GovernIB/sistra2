package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.service.FormularioInternoService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FormularoInternoServiceBean implements FormularioInternoService {

    /**
     * formulario interno service.
     */
    @Autowired
    FormularioInternoService formIntService;

    /*
     * (non-Javadoc)
     *
     * @see es.caib.sistrages.core.api.service.FormularioInternoService#
     * getFormularioInterno(java.lang.Long)
     */
    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public DisenyoFormulario getFormularioInterno(final Long pId) {
        return formIntService.getFormularioInterno(pId);
    }

    /*
     * (non-Javadoc)
     *
     * @see es.caib.sistrages.core.api.service.FormularioInternoService#
     * updateFormularioInterno(es.caib.sistrages.core.api.model.
     * DisenyoFormulario)
     */
    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public void updateFormularioInterno(final DisenyoFormulario pFormInt) {
        formIntService.updateFormularioInterno(pFormInt);

    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public DisenyoFormulario getFormularioInternoCompleto(final Long pId) {
        return formIntService.getFormularioInternoCompleto(pId);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public DisenyoFormulario getFormularioInternoPaginas(final Long pId) {
        return formIntService.getFormularioInternoPaginas(pId);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public PaginaFormulario getPaginaFormulario(final Long pId) {
        return formIntService.getPaginaFormulario(pId);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public PaginaFormulario getContenidoPaginaFormulario(final Long pId) {
        return formIntService.getContenidoPaginaFormulario(pId);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public ObjetoFormulario addComponenteFormulario(
            final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina,
            final Long pIdLinea, final Integer pOrden, final String pPosicion) {
        return formIntService.addComponenteFormulario(pTipoObjeto, pIdPagina,
                pIdLinea, pOrden, pPosicion);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public ObjetoFormulario updateComponenteFormulario(
            final ComponenteFormulario pComponente) {
        return formIntService.updateComponenteFormulario(pComponente);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public ComponenteFormulario getComponenteFormulario(final Long pId) {
        return formIntService.getComponenteFormulario(pId);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public void removeComponenteFormulario(final Long pId) {
        formIntService.removeComponenteFormulario(pId);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public void removeLineaFormulario(final Long pId) {
        formIntService.removeLineaFormulario(pId);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public void updateOrdenComponenteFormulario(final Long pId,
            final Integer pOrden) {
        formIntService.updateOrdenComponenteFormulario(pId, pOrden);

    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public void updateOrdenLineaFormulario(final Long pId,
            final Integer pOrden) {
        formIntService.updateOrdenLineaFormulario(pId, pOrden);

    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormulario(
            final Long pId) {
        return formIntService.getListaPlantillaIdiomaFormulario(pId);
    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public PlantillaIdiomaFormulario uploadPlantillaIdiomaFormulario(
            final Long idEntidad, final Long idPlantilla,
            final PlantillaIdiomaFormulario plantilla, final byte[] contents) {
        return formIntService.uploadPlantillaIdiomaFormulario(idEntidad,
                idPlantilla, plantilla, contents);

    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public void removePlantillaIdiomaFormulario(
            final PlantillaIdiomaFormulario plantillaIdiomaFormulario) {
        formIntService
                .removePlantillaIdiomaFormulario(plantillaIdiomaFormulario);

    }

    @Override
    @RolesAllowed({ConstantesRolesAcceso.ADMIN_ENT,
            ConstantesRolesAcceso.DESAR})
    public String getPaginaFormularioHTMLDisenyo(Long pIdPagina,
            String pLang) {
        return formIntService.getPaginaFormularioHTMLDisenyo(pIdPagina,
                pLang);
    }

}
