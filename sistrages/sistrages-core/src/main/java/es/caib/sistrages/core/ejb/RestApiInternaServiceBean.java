package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;

/**
 * Servicios de API Rest.
 *
 * @author Indra
 *
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RestApiInternaServiceBean implements RestApiInternaService {

    /** System service. */
    @Autowired
    private RestApiInternaService restApiService;

    @Override
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public String test(String echo) {
        return restApiService.test(echo);
    }

    @Override
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro){
    	return restApiService.listConfiguracionGlobal(filtro);
    }

	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public List<Plugin> listPlugin(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		return restApiService.listPlugin(ambito, idEntidad, filtro);
	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public List<FormularioSoporte> listOpcionesFormularioSoporte(final Long idEntidad) {
		return restApiService.listOpcionesFormularioSoporte(idEntidad);
	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public Entidad loadEntidad(final Long idEntidad) {
		return restApiService.loadEntidad(idEntidad);
	}

	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public String getReferenciaFichero(Long id) {
		return restApiService.getReferenciaFichero(id);
	}


	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public Tramite loadTramite(Long idTramite) {
		return restApiService.loadTramite(idTramite);
	}


	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public Dominio loadDominio(Long idDominio) {
		return restApiService.loadDominio(idDominio);
	}


	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public String getValorConfiguracionGlobal(final String propiedad) {
		return restApiService.getValorConfiguracionGlobal(propiedad);

	}


	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public DisenyoFormulario getFormularioInterno(final Long pId) {
		return restApiService.getFormularioInterno(pId);
	}


	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormularioById(final Long idPlantillaFormulario) {
		return restApiService.getListaPlantillaIdiomaFormularioById(idPlantillaFormulario);
	}


	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public FormateadorFormulario getFormateadorFormulario(final Long idFmt) {
		return restApiService.getFormateadorFormulario(idFmt);
	}


	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public String getPaginaFormularioHTMLAsistente(Long pIdPage, String pLang) {
		return restApiService.getPaginaFormularioHTMLAsistente(pIdPage, pLang);
	}


}
