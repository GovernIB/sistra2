package es.caib.sistrages.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.adapter.AvisosEntidadAdapter;
import es.caib.sistrages.rest.adapter.ConfiguracionEntidadAdapter;
import es.caib.sistrages.rest.adapter.ConfiguracionGlobalAdapter;
import es.caib.sistrages.rest.adapter.RolesAdapter;
import es.caib.sistrages.rest.adapter.ValoresDominioAdapter;
import es.caib.sistrages.rest.adapter.VersionTramiteAdapter;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RPermisoHelpDesk;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
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

	// TODO Hacer que todas las clases del modelo api rest empiecen con "R" para
	// evitar conflictos con modelo core? RENOMBRAMOS QUE EMPIECEN CON "R"

	// TODO Ver gestion errores, tanto generados en capa Rest como los que
	// vengan de negocio. Ver de gestionar con interceptor. DE MOMENTO QUE
	// DEVUELVA 500 Y LUEGO YA VEMOS.

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
		return confGlobalAdapter.convertir(cg, pg);
	}

	/**
	 * Recupera configuración entidad.
	 *
	 * @param codigoDIR3
	 *            id entidad
	 * @return Entidad
	 */
	@ApiOperation(value = "Lista de Propiedades de configuracion de entidad", notes = "Lista de Propiedades de configuracion de entidad", response = RConfiguracionEntidad.class)
	@RequestMapping(value = "/entidad/{id}", method = RequestMethod.GET)
	public RConfiguracionEntidad obtenerConfiguracionEntidad(@PathVariable("id") final String codigoDIR3) {
		final Entidad entidad = restApiService.loadEntidad(codigoDIR3);
		final List<FormularioSoporte> formSoporte = restApiService.listOpcionesFormularioSoporte(entidad.getCodigo());
		return confEntidadAdapter.convertir(entidad, formSoporte);
	}

	/**
	 * Recupera definición versión de trámite.
	 *
	 * @param idioma
	 *            Idioma
	 * @param idtramite
	 *            Id Trámite
	 * @param version
	 *            Versión trámite
	 * @return versión de trámite
	 * @throws Exception
	 */
	@ApiOperation(value = "Obtiene la definición de la versión del tramite", notes = "Obtiene la definición de la versión del tramite", response = RVersionTramite.class)
	@RequestMapping(value = "/tramite/{idTramite}/{version}/{idioma}", method = RequestMethod.GET)
	public RVersionTramite obtenerDefinicionVersionTramite(@PathVariable("idTramite") final String idtramite,
			@PathVariable("version") final int version, @PathVariable("idioma") final String idioma) throws Exception {

		final String idiomaDefecto = restApiService.getValorConfiguracionGlobal("definicionTramite.lenguajeDefecto");
		final TramiteVersion tv = restApiService.loadTramiteVersion(idtramite, version);
		if (tv == null) {
			throw new Exception("El tramite especificado no existe");
		}
		return versionTramiteAdapter.convertir(idtramite, tv, idioma, idiomaDefecto);
	}

	/**
	 * Obtiene avisos activos entidad.
	 *
	 * @param idEntidad
	 *            Id entidad
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
	 *            id dominio
	 * @param parametrosJSON
	 *            parametros (en formato JSON)
	 * @return Valores dominio
	 */
	@ApiOperation(value = "Obtiene los valores de un dominio", notes = "Obtiene los valores de un dominio", response = RValoresDominio.class)
	@RequestMapping(value = "/dominioFuenteDatos/{idDominio}", method = RequestMethod.POST)
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
		return valoresDominioAdapter.convertir(res);
	}

	/**
	 * Recupera valores de un dominio de fuente de datos.
	 *
	 * @param idDominio
	 *            id dominio
	 * @param parametrosJSON
	 *            parametros (en formato JSON)
	 * @return Valores dominio
	 */
	@ApiOperation(value = "Obtiene los valores de un dominio", notes = "Obtiene los valores de un dominio", response = RValoresDominio.class)
	@RequestMapping(value = "/dominioListaFija/{idDominio}", method = RequestMethod.POST)
	public RValoresDominio obtenerValoresDominioLF(@PathVariable("idDominio") final String idDominio,
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

		final ValoresDominio res = restApiService.realizarConsultaListaFija(idDominio, listaParams);
		return valoresDominioAdapter.convertir(res);
	}

	@ApiOperation(value = "Lista de roles con permiso helpdesk", notes = "Lista de roles con permiso helpdesk", response = RPermisoHelpDesk.class, responseContainer = "List")
	@RequestMapping(value = "/permisosHelpdesk", method = RequestMethod.GET)
	public List<RPermisoHelpDesk> obtenerPermisosHelpdesk() {
		final List<Rol> roles = restApiService.obtenerPermisosHelpdesk();
		return rolesAdapter.convertir(roles);
	}
}
