package es.caib.sistrages.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.IncidenciaValoracion;
import es.caib.sistrages.core.api.model.PlantillaEntidad;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Rol;

import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.adapter.AvisosEntidadAdapter;
import es.caib.sistrages.rest.adapter.ConfiguracionEntidadAdapter;
import es.caib.sistrages.rest.adapter.ConfiguracionGlobalAdapter;
import es.caib.sistrages.rest.adapter.DominioAdapter;
import es.caib.sistrages.rest.adapter.RolesAdapter;
import es.caib.sistrages.rest.adapter.ValoresDominioAdapter;
import es.caib.sistrages.rest.adapter.VersionTramiteAdapter;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RPermisoHelpDesk;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.exception.NoExisteException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Operaciones requeridas desde el resto de módulos de Sistra2. No requieren
 * versionado.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/interna")
@Api(value = "interna", produces = "application/json")
public class ApiInternaRestController {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiInternaRestController.class);

	/** Servicio negocio. */
	@Autowired
	private RestApiInternaService restApiService;

	/**
	 * ConfiguracionGlobalAdapter
	 */
	@Autowired
	private ConfiguracionGlobalAdapter confGlobalAdapter;

	/**
	 * ConfiguracionEntidadAdapter
	 */
	@Autowired
	private ConfiguracionEntidadAdapter confEntidadAdapter;

	/**
	 * VersionTramiteAdapter
	 */
	@Autowired
	private VersionTramiteAdapter versionTramiteAdapter;

	/**
	 * DominioAdapter
	 */
	@Autowired
	private DominioAdapter dominioAdapter;

	/**
	 * AvisosEntidadAdapter
	 */
	@Autowired
	private AvisosEntidadAdapter avisosEntidadAdapter;

	/**
	 * ValoresDominioAdapter
	 */
	@Autowired
	private ValoresDominioAdapter valoresDominioAdapter;

	@Autowired
	private RolesAdapter rolesAdapter;

	/**
	 * Recupera configuración global.
	 *
	 * @return Configuracion global
	 */

	@ApiOperation(value = "Lista de Propiedades de configuracion global", notes = "Lista de Propiedades de configuracion global", response = RConfiguracionGlobal.class)
	@RequestMapping(value = "/configuracionGlobal", method = RequestMethod.GET)
	public RConfiguracionGlobal obtenerConfiguracionGlobal() {
		final List<ConfiguracionGlobal> cg = restApiService.listConfiguracionGlobal(null);
		final List<Plugin> pg = restApiService.listPlugin(TypeAmbito.GLOBAL, (long) 0, null);
		final List<ConfiguracionAutenticacion> configuraciones = restApiService
				.listConfiguracionAutenticacion(TypeAmbito.GLOBAL, null);
		return confGlobalAdapter.convertir(cg, pg, configuraciones);
	}

	/**
	 * Recupera configuración entidad.
	 *
	 * @param codigoDIR3
	 *                       id entidad
	 * @return Entidad
	 */
	@ApiOperation(value = "Lista de Propiedades de configuracion de entidad", notes = "Lista de Propiedades de configuracion de entidad", response = RConfiguracionEntidad.class)
	@RequestMapping(value = "/entidad/{id}", method = RequestMethod.GET)
	public RConfiguracionEntidad obtenerConfiguracionEntidad(@PathVariable("id") final String codigoDIR3) {
		final Entidad entidad = restApiService.loadEntidad(codigoDIR3);
		final List<Area> areas = restApiService.listAreasByEntidad(entidad.getCodigo());
		final List<FormularioSoporte> formSoporte = restApiService.listOpcionesFormularioSoporte(entidad.getCodigo());
		final FormateadorFormulario formateador = restApiService.getFormateadorPorDefecto(codigoDIR3);
		List<PlantillaFormateador> plantillas = null;
		if (formateador != null) {
			plantillas = restApiService.getPlantillasFormateador(formateador.getCodigo());
		}
		final List<PlantillaEntidad> plantillasEntidad = restApiService.getPlantillasEntidad(entidad.getCodigo());

		List<IncidenciaValoracion> valoraciones = null;
		if (entidad.isValorarTramite()) {
			valoraciones = restApiService.getValoraciones(entidad.getCodigo());
		}

		final List<EnvioRemoto> enviosRemotos = restApiService.listEnvioByEntidad(entidad.getCodigo());

		final List<ConfiguracionAutenticacion> configuraciones = restApiService
				.listConfiguracionAutenticacion(TypeAmbito.ENTIDAD, entidad.getCodigo());

		final List<GestorExternoFormularios> gestores = restApiService
				.listGestorExternoFormularios(entidad.getCodigo());

		return confEntidadAdapter.convertir(entidad, formSoporte, plantillas, valoraciones, plantillasEntidad, areas,
				enviosRemotos, configuraciones, gestores);
	}

	/**
	 * Recupera definición versión de trámite.
	 *
	 * @param idioma
	 *                      Idioma
	 * @param idtramite
	 *                      Id Trámite
	 * @param version
	 *                      Versión trámite
	 * @return versión de trámite
	 * @throws Exception
	 */
	@ApiOperation(value = "Obtiene la definición de la versión del tramite", notes = "Obtiene la definición de la versión del tramite", response = RVersionTramite.class)
	@RequestMapping(value = "/tramite/{idTramite}/{version}/{idioma}", method = RequestMethod.GET)
	public RVersionTramite obtenerDefinicionVersionTramite(@PathVariable("idTramite") final String idtramite,
			@PathVariable("version") final int version, @PathVariable("idioma") final String idioma) {
		final String idiomaDefecto = restApiService.getValorConfiguracionGlobal("definicionTramite.lenguajeDefecto");
		final TramiteVersion tv = restApiService.loadTramiteVersion(idtramite, version);
		if (tv == null) {
			LOGGER.error("No existe el tramite " + idtramite + " version " + version);
			throw new NoExisteException("No existe el tramite " + idtramite + " version " + version);
		}
		return versionTramiteAdapter.convertir(idtramite, tv, idioma, idiomaDefecto);
	}

	/**
	 * Recupera definición dominio.
	 *
	 * @param idDominio
	 * @return versión de dominio
	 * @throws Exception
	 */
	@ApiOperation(value = "Obtiene la definición del dominio", notes = "Obtiene la definición del dominio", response = RDominio.class)
	@RequestMapping(value = "/dominio/{idDominio:.+}", method = RequestMethod.GET)
	public RDominio obtenerDefinicionDominio(@PathVariable("idDominio") final String idDominio) {
		final Dominio dominio = restApiService.loadDominio(idDominio);
		if (dominio == null) {
			LOGGER.error("No existe el dominio " + idDominio);
			throw new NoExisteException("No existe el dominio " + idDominio);
		}
		String idEntidad = null;
		if (dominio.getAmbito() != TypeAmbito.GLOBAL) {
			if (dominio.getAmbito().equals(TypeAmbito.ENTIDAD)) {
				final Entidad entidad = restApiService.loadEntidad(dominio.getEntidad());
				idEntidad = entidad.getIdentificador();
			} else if (dominio.getAmbito().equals(TypeAmbito.AREA)) {
				final Entidad entidad = restApiService.loadEntidadByArea(dominio.getArea().getCodigo());
				idEntidad = entidad.getCodigoDIR3();
			}
		}
		return dominioAdapter.convertir(dominio, idEntidad);
	}

	/**
	 * Obtiene avisos activos entidad.
	 *
	 * @param idEntidad
	 *                      Id entidad
	 * @return avisos
	 */
	@ApiOperation(value = "Obtiene los avisos de una entidad", notes = "Obtiene los avisos de una entidad", response = RAvisosEntidad.class)
	@RequestMapping(value = "/entidad/{id}/avisos", method = RequestMethod.GET)
	public RAvisosEntidad obtenerAvisosEntidad(@PathVariable("id") final String idEntidad) {
		return avisosEntidadAdapter.convertir(idEntidad);
	}

	/**
	 * Recupera valores de un dominio de fuente de datos.
	 *
	 * @param idDominio
	 *                           id dominio
	 * @param parametrosJSON
	 *                           parametros (en formato JSON)
	 * @return Valores dominio
	 */
	@ApiOperation(value = "Obtiene los valores de un dominio", notes = "Obtiene los valores FD de un dominio", response = RValoresDominio.class)
	@RequestMapping(value = "/dominioFuenteDatos/{idDominio:.+}", method = RequestMethod.POST)
	public RValoresDominio obtenerValoresDominioFD(@PathVariable("idDominio") final String idDominio,
			@RequestBody(required = false) final RListaParametros parametros) {

		// Convertimos los parametros a la clase necesaria
		final List<ValorParametroDominio> listaParams = new ArrayList<>();
		if (parametros != null && parametros.getParametros() != null) {
			for (final RValorParametro p : parametros.getParametros()) {
				final ValorParametroDominio param = new ValorParametroDominio();
				param.setCodigo(p.getCodigo());
				param.setValor(p.getValor());
				listaParams.add(param);
			}
		}

		final ValoresDominio res = restApiService.realizarConsultaFuenteDatos(idDominio, listaParams);
		if (res == null) {
			LOGGER.error("No existen los valores de dominio " + idDominio);
			throw new NoExisteException("No existen los valores de dominio " + idDominio);
		}
		return valoresDominioAdapter.convertir(res);
	}

	/**
	 * Recupera valores de un dominio de fuente de datos.
	 *
	 * @param idDominio
	 *                           id dominio
	 * @param parametrosJSON
	 *                           parametros (en formato JSON)
	 * @return Valores dominio
	 */
	@ApiOperation(value = "Obtiene los valores de un dominio", notes = "Obtiene los valores LF de un dominio", response = RValoresDominio.class)
	@RequestMapping(value = "/dominioListaFija/{idDominio:.+}", method = RequestMethod.GET)
	public RValoresDominio obtenerValoresDominioLF(@PathVariable("idDominio") final String idDominio) {

		final ValoresDominio res = restApiService.realizarConsultaListaFija(idDominio);
		if (res == null) {
			LOGGER.error("No existen los valores de dominio " + idDominio);
			throw new NoExisteException("No existen los valores de dominio " + idDominio);
		}
		return valoresDominioAdapter.convertir(res);
	}

	@ApiOperation(value = "Lista de roles con permiso helpdesk", notes = "Lista de roles con permiso helpdesk", response = RPermisoHelpDesk.class, responseContainer = "List")
	@RequestMapping(value = "/permisosHelpdesk", method = RequestMethod.GET)
	public List<RPermisoHelpDesk> obtenerPermisosHelpdesk() {
		final List<Rol> roles = restApiService.obtenerPermisosHelpdesk();
		final List<RPermisoHelpDesk> listaPermisos = rolesAdapter.convertir(roles);

		// recuperamos entidades - areas
		final List<Entidad> listaEntidades = restApiService.listEntidad();
		for (final Entidad entidad : listaEntidades) {
			if (entidad.isActivo()) {
				final RPermisoHelpDesk permiso = new RPermisoHelpDesk();
				permiso.setTipoPermiso("E");
				permiso.setListaIdentificadorArea(restApiService.listIdAreasByEntidad(entidad.getCodigo()));
				permiso.setCodigoDIR3Entidad(entidad.getCodigoDIR3());
				permiso.setValor(entidad.getRolSup());

				listaPermisos.add(permiso);
			}
		}

		return listaPermisos;
	}
}
