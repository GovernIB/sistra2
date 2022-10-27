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
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioSimple;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.service.FormularioInternoService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FormularioInternoServiceBean implements FormularioInternoService {

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
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public DisenyoFormulario getFormularioInterno(final Long pId) {
		return formIntService.getFormularioInterno(pId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioInternoService#
	 * updateFormularioInterno(es.caib.sistrages.core.api.model. DisenyoFormulario)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateFormularioInterno(final DisenyoFormulario pFormInt) {
		formIntService.updateFormularioInterno(pFormInt);

	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public DisenyoFormulario getFormularioInternoCompleto(final Long pId) {
		return formIntService.getFormularioInternoCompleto(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public DisenyoFormulario getFormularioInternoPaginas(final Long pId) {
		return formIntService.getFormularioInternoPaginas(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public PaginaFormulario getPaginaFormulario(final Long pId) {
		return formIntService.getPaginaFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public PaginaFormulario getContenidoPaginaFormulario(final Long pId) {
		return formIntService.getContenidoPaginaFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ObjetoFormulario addComponenteFormulario(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina,
			final Long pIdLinea, final Integer pOrden, final String pPosicion, final Object objeto, boolean isTipoSeccion, String identificadorSeccion, final String idTramiteVersion) {
		return formIntService.addComponenteFormulario(pTipoObjeto, pIdPagina, pIdLinea, pOrden, pPosicion, objeto, isTipoSeccion, identificadorSeccion, idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ObjetoFormulario updateComponenteFormulario(final ComponenteFormulario pComponente) {
		return formIntService.updateComponenteFormulario(pComponente);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ComponenteFormulario getComponenteFormulario(final Long pId) {
		return formIntService.getComponenteFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeComponenteFormulario(final Long pId) {
		formIntService.removeComponenteFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeLineaFormulario(final Long pId) {
		formIntService.removeLineaFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateOrdenComponenteFormulario(final Long pId, final Integer pOrden) {
		formIntService.updateOrdenComponenteFormulario(pId, pOrden);

	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateOrdenLineaFormulario(final Long pId, final Integer pOrden) {
		formIntService.updateOrdenLineaFormulario(pId, pOrden);

	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormulario(final Long pId) {
		return formIntService.getListaPlantillaIdiomaFormulario(pId);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public PlantillaIdiomaFormulario uploadPlantillaIdiomaFormulario(final Long idEntidad, final Long idPlantilla,
			final PlantillaIdiomaFormulario plantilla, final byte[] contents) {
		return formIntService.uploadPlantillaIdiomaFormulario(idEntidad, idPlantilla, plantilla, contents);

	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removePlantillaIdiomaFormulario(final PlantillaIdiomaFormulario plantillaIdiomaFormulario) {
		formIntService.removePlantillaIdiomaFormulario(plantillaIdiomaFormulario);

	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public String getPaginaFormularioHTMLDisenyo(final Long pIdPagina, final String pLang) {
		return formIntService.getPaginaFormularioHTMLDisenyo(pIdPagina, pLang);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public DisenyoFormularioSimple getFormularioInternoSimple(final Long idFormularioTramite, final Long idFormulario,
			final String idComponente, final String idPagina, final boolean cargarPaginasPosteriores, final boolean isSeccion, final String identificadorSeccion) {
		return formIntService.getFormularioInternoSimple(idFormularioTramite, idFormulario, idComponente, idPagina,
				cargarPaginasPosteriores, isSeccion, identificadorSeccion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public String getIdentificadorFormularioInterno(final Long idFormulario) {
		return formIntService.getIdentificadorFormularioInterno(idFormulario);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public String generaPaginaHTMLEditor(final Long pIdForm, final Long pPage, final String pIdComponente,
			final String pLang, final Boolean pMostrarOcultos, final String pContexto) {
		return formIntService.generaPaginaHTMLEditor(pIdForm, pPage, pIdComponente, pLang, pMostrarOcultos, pContexto);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean isIdElementoFormularioDuplicated(final Long idFormulario, final Long codElemento,
			final String identificador) {
		return formIntService.isIdElementoFormularioDuplicated(idFormulario, codElemento, identificador);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public PlantillaFormateador uploadPlantillaFormateador(final Long idEntidad, final Long idPlantillaFormateador,
			final PlantillaFormateador plantillaFormateador, final byte[] contents) {
		return formIntService.uploadPlantillaFormateador(idEntidad, idPlantillaFormateador, plantillaFormateador,
				contents);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removePlantillaFormateador(final PlantillaFormateador plantillaFormateador) {
		formIntService.removePlantillaFormateador(plantillaFormateador);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ObjetoFormulario copyCutComponenteFormulario(final Long pIdPagina, final Long pIdLinea, final Integer pOrden,
			final String pPosicion, final Long idComponenteOriginal, final boolean cut) {
		return formIntService.copyCutComponenteFormulario(pIdPagina, pIdLinea, pOrden, pPosicion, idComponenteOriginal,
				cut);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ObjetoFormulario copyCutLineaFormulario(final Long idPagina, final Long idLinea, final Integer orden,
			final String posicionamiento, final boolean cut) {
		return formIntService.copyCutLineaFormulario(idPagina, idLinea, orden, posicionamiento, cut);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void guardarPagina(final PaginaFormulario pagina) {
		formIntService.guardarPagina(pagina);
	}

}
