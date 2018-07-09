package es.caib.sistrages.core.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.FormularioSoporteDao;
import es.caib.sistrages.core.service.repository.dao.PluginsDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;


@Service
@Transactional
public class RestApiInternaServiceImpl implements RestApiInternaService {



	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RestApiInternaServiceImpl.class);

	/**
	 * configuracion global dao.
	 */
	@Autowired
	ConfiguracionGlobalDao configuracionGlobalDao;

	/**
	 * plugin dao.
	 */
	@Autowired
	PluginsDao pluginDao;

	/** DAO Formulario soporte. */
	@Autowired
	FormularioSoporteDao formularioSoporteDao;

	/** DAO Entidad. */
	@Autowired
	EntidadDao entidadDao;

	/** FicheroExterno dao. */
	@Autowired
	FicheroExternoDao ficheroExternoDao;

	/** DAO Tramite. */
	@Autowired
	TramiteDao tramiteDao;

	/**
	 * dominio service.
	 */
	@Autowired
	DominioDao dominioDao;

	@Autowired
	FormularioInternoDao formIntDao;

	/**
	 * FormateadorFormulario
	 */
	@Autowired
	FormateadorFormularioDao fmtDao;


    @Override
    @NegocioInterceptor
    public String test(String echo) {
        return "Echo: " + echo;
    }


	@Override
	@NegocioInterceptor
	public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro) {
		return configuracionGlobalDao.getAllByFiltro(filtro);
	}


	@Override
	@NegocioInterceptor
	public List<Plugin> listPlugin(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		return pluginDao.getAllByFiltro(ambito, idEntidad, filtro);
	}

	@Override
	@NegocioInterceptor
	public List<FormularioSoporte> listOpcionesFormularioSoporte(final Long idEntidad) {
		return formularioSoporteDao.getAll(idEntidad);
	}

	@Override
	@NegocioInterceptor
	public Entidad loadEntidad(final Long idEntidad) {
		Entidad result = null;
		result = entidadDao.getById(idEntidad);
		return result;
	}


	@Override
	@NegocioInterceptor
	public String getReferenciaFichero(Long id) {
		if(id==null) {
			return null;
		}else {
			return ficheroExternoDao.getReferenciaById(id);
		}
	}

	@Override
	@NegocioInterceptor
	public Tramite loadTramite(Long idTramite) {
		return tramiteDao.getById(idTramite);
	}


	@Override
	@NegocioInterceptor
	public Dominio loadDominio(Long idDominio) {
		return dominioDao.getByCodigo(idDominio);
	}


	@Override
	@NegocioInterceptor
	public String getValorConfiguracionGlobal(final String propiedad) {
		if(configuracionGlobalDao.getByPropiedad(propiedad)!=null) {
			return configuracionGlobalDao.getByPropiedad(propiedad).getValor();
		}
		return "";
	}


	@Override
	@NegocioInterceptor
	public DisenyoFormulario getFormularioInterno(final Long pId) {
		return formIntDao.getFormularioById(pId);
	}


	@Override
	@NegocioInterceptor
	public FormateadorFormulario getFormateadorFormulario(final Long idFmt) {
		return fmtDao.getById(idFmt);
	}


	@Override
	@NegocioInterceptor
	public List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormularioById(final Long idPlantillaFormulario) {
		return formIntDao.getListaPlantillaIdiomaFormularioById(idPlantillaFormulario);
	}


    @Override
    @NegocioInterceptor
    public String getPaginaFormularioHTMLAsistente(Long pIdPage, String pLang) {
        // TODO PENDIENTE
        return "<html/>";
    }


}
