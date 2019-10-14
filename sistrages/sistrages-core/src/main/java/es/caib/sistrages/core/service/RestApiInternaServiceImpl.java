package es.caib.sistrages.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.IncidenciaValoracion;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.ConfiguracionComponent;
import es.caib.sistrages.core.service.component.FormRenderComponent;
import es.caib.sistrages.core.service.component.FuenteDatosComponent;
import es.caib.sistrages.core.service.repository.dao.AreaDao;
import es.caib.sistrages.core.service.repository.dao.AvisoEntidadDao;
import es.caib.sistrages.core.service.repository.dao.ConfiguracionGlobalDao;
import es.caib.sistrages.core.service.repository.dao.DominioDao;
import es.caib.sistrages.core.service.repository.dao.EntidadDao;
import es.caib.sistrages.core.service.repository.dao.FicheroExternoDao;
import es.caib.sistrages.core.service.repository.dao.FormateadorFormularioDao;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.FormularioSoporteDao;
import es.caib.sistrages.core.service.repository.dao.IncidenciaValoracionDao;
import es.caib.sistrages.core.service.repository.dao.PluginsDao;
import es.caib.sistrages.core.service.repository.dao.RolDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;

@Service
@Transactional
public class RestApiInternaServiceImpl implements RestApiInternaService {

	/** Constante LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RestApiInternaServiceImpl.class);

	/** configuracion global dao. */
	@Autowired
	private ConfiguracionGlobalDao configuracionGlobalDao;

	/** plugin dao. */
	@Autowired
	private PluginsDao pluginDao;

	/** DAO Formulario soporte. */
	@Autowired
	private FormularioSoporteDao formularioSoporteDao;

	/** DAO Entidad. */
	@Autowired
	private EntidadDao entidadDao;

	/** FicheroExterno dao. */
	@Autowired
	private FicheroExternoDao ficheroExternoDao;

	/** DAO Tramite. */
	@Autowired
	private TramiteDao tramiteDao;

	/** DAO Tramite Paso. **/
	@Autowired
	private TramitePasoDao tramitePasoDao;

	/** DAO Dominio. */
	@Autowired
	private DominioDao dominioDao;

	/** DAO formulario. */
	@Autowired
	private FormularioInternoDao formIntDao;

	/** DAO Area. */
	@Autowired
	private AreaDao areaDao;

	/** DAO Area. */
	@Autowired
	private IncidenciaValoracionDao avisoDao;

	/** FormateadorFormulario. */
	@Autowired
	private FormateadorFormularioDao fmtDao;

	/** Aviso entidad dao. */
	@Autowired
	private AvisoEntidadDao avisoEntidadDao;

	/** Aviso entidad dao. */
	@Autowired
	private RolDao rolDao;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Componente fuente de datos. */
	@Autowired
	private FuenteDatosComponent fuenteDatosComponent;

	/** Form renderer. */
	@Autowired
	private FormRenderComponent formRenderComponent;

	/** DAO Formateador Formulario DAO. */
	@Autowired
	private FormateadorFormularioDao formateadorFormularioDAO;

	@Override
	@NegocioInterceptor
	public String test(final String echo) {
		return "Echo: " + echo;
	}

	@Override
	@NegocioInterceptor
	public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro) {
		final List<ConfiguracionGlobal> config = configuracionGlobalDao.getAllByFiltro(filtro);
		final List<ConfiguracionGlobal> res = new ArrayList<>();
		for (final ConfiguracionGlobal c : config) {
			final ConfiguracionGlobal c1 = new ConfiguracionGlobal(c.getCodigo(), c.getPropiedad(),
					configuracionComponent.replacePlaceholders(c.getValor()), c.getDescripcion(), c.isNoModificable());
			res.add(c1);
		}
		return res;
	}

	@Override
	@NegocioInterceptor
	public List<Plugin> listPlugin(final TypeAmbito ambito, final Long idEntidad, final String filtro) {
		// Obtenemos plugins
		final List<Plugin> plugins = pluginDao.getAllByFiltro(ambito, idEntidad, filtro);
		// Reemplazamos system placeholders
		for (final Plugin plg : plugins) {
			for (final Propiedad prop : plg.getPropiedades()) {
				prop.setValor(configuracionComponent.replacePlaceholders(prop.getValor()));
			}
		}
		return plugins;
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
	public Area loadArea(final Long idArea) {
		Area result = null;
		result = areaDao.getById(idArea);
		return result;
	}

	@Override
	@NegocioInterceptor
	public Entidad loadEntidad(final String codigoDir3) {
		Entidad result = null;
		result = entidadDao.getByCodigo(codigoDir3);
		return result;
	}

	@Override
	@NegocioInterceptor
	public String getReferenciaFichero(final Long id) {
		if (id == null) {
			return null;
		} else {
			return ficheroExternoDao.getReferenciaById(id);
		}
	}

	@Override
	@NegocioInterceptor
	public Tramite loadTramite(final Long idTramite) {
		return tramiteDao.getById(idTramite);
	}

	@Override
	@NegocioInterceptor
	public TramiteVersion loadTramiteVersion(final String idTramite, final int version) {
		final TramiteVersion tramiteVersion = tramiteDao.getTramiteVersionByNumVersion(idTramite, version);
		if (tramiteVersion != null) {

			final List<Long> dominiosId = new ArrayList<>();
			final List<Dominio> dominiosSimples = tramiteDao.getDominioSimpleByTramiteId(tramiteVersion.getCodigo());
			for (final Dominio dominioSimple : dominiosSimples) {
				dominiosId.add(dominioSimple.getCodigo());
			}
			tramiteVersion.setListaDominios(dominiosId);
		}
		return tramiteVersion;
	}

	@Override
	@NegocioInterceptor
	public Dominio loadDominio(final String idDominio) {
		return dominioDao.getByIdentificador(idDominio);
	}

	@Override
	@NegocioInterceptor
	public Dominio loadDominio(final Long idDominio) {
		return dominioDao.getByCodigo(idDominio);
	}

	@Override
	@NegocioInterceptor
	public List<String> getIdentificadoresDominiosByTV(final Long idTramiteVersion) {
		return tramiteDao.getTramiteDominiosIdentificador(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public String getValorConfiguracionGlobal(final String propiedad) {
		if (configuracionGlobalDao.getByPropiedad(propiedad) != null) {
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
	public DisenyoFormulario getDisenyoFormularioById(final Long idForm) {
		return formIntDao.getFormularioCompletoById(idForm);
	}

	@Override
	@NegocioInterceptor
	public String getPaginaFormularioHTMLAsistente(final Long pIdPage, final String pLang) {
		return formRenderComponent.generaPaginaHTMLAsistente(pIdPage, pLang);
	}

	@Override
	@NegocioInterceptor
	public List<TramitePaso> getTramitePasos(final Long idTramiteVersion) {
		return tramitePasoDao.getTramitePasos(idTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public List<AvisoEntidad> getAvisosEntidad(final String pIdEntidad) {
		return avisoEntidadDao.getAll(pIdEntidad);
	}

	@Override
	@NegocioInterceptor
	public ValoresDominio realizarConsultaFuenteDatos(final String idDominio,
			final List<ValorParametroDominio> parametros) {
		return fuenteDatosComponent.realizarConsultaFuenteDatos(idDominio, parametros);
	}

	@Override
	@NegocioInterceptor
	public ValoresDominio realizarConsultaListaFija(final String idDominio) {
		return fuenteDatosComponent.realizarConsultaListaFija(idDominio);
	}

	@Override
	@NegocioInterceptor
	public List<Rol> obtenerPermisosHelpdesk() {
		return rolDao.getAllByHelpDesk();
	}

	@Override
	@NegocioInterceptor
	public FormateadorFormulario getFormateadorPorDefecto(final String codigoDir3) {
		return formateadorFormularioDAO.getFormateadorPorDefecto(null, codigoDir3);
	}

	@Override
	@NegocioInterceptor
	public List<PlantillaFormateador> getPlantillasFormateador(final Long idFormateador) {
		return formateadorFormularioDAO.getListaPlantillasFormateador(idFormateador);
	}

	@Override
	@NegocioInterceptor
	public List<String> listIdAreasByEntidad(final Long pIdEntidad) {
		final List<Area> listaAreas = areaDao.getAll(pIdEntidad);
		final List<String> listaIdAreas = new ArrayList<>();
		for (final Area area : listaAreas) {
			listaIdAreas.add(area.getIdentificador());
		}

		return listaIdAreas;
	}

	@Override
	@NegocioInterceptor
	public List<Entidad> listEntidad() {
		return entidadDao.getAll();
	}

	@Override
	@NegocioInterceptor
	public String getIdentificadorByCodigoVersion(final Long codigoTramiteVersion) {
		return tramiteDao.getIdentificadorByCodigoVersion(codigoTramiteVersion);
	}

	@Override
	@NegocioInterceptor
	public List<IncidenciaValoracion> getValoraciones(final Long codigo) {
		return avisoDao.getValoraciones(codigo);
	}

}
