package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.ValoresDominio;
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
	public TramiteVersion loadTramiteVersion(String idTramite, int version) {
		return restApiService.loadTramiteVersion(idTramite, version);
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

	@Override
	@RolesAllowed(ConstantesRolesAcceso.REST)
	public List<TramitePaso> getTramitePasos(Long idTramiteVersion) {
	   return restApiService.getTramitePasos(idTramiteVersion);
	}
	
   @Override
   @RolesAllowed(ConstantesRolesAcceso.REST)
   public DisenyoFormulario getDisenyoFormularioById(Long idForm) {
	   return restApiService.getDisenyoFormularioById(idForm);
   }

   
   @Override
   @RolesAllowed(ConstantesRolesAcceso.REST)
   public List<AvisoEntidad> getAvisosEntidad(final String pIdEntidad) {
	   return restApiService.getAvisosEntidad(pIdEntidad);
   }
   
   @Override
   @RolesAllowed(ConstantesRolesAcceso.REST)
   
   public ValoresDominio realizarConsultaFuenteDatos(String idDominio, List<ValorParametroDominio> parametros) {
	   return restApiService.realizarConsultaFuenteDatos(idDominio, parametros);
   }
    

}
