package es.caib.sistramit.core.service.component.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;

/**
 * Implementación acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("sistragesApiComponent")
public final class SistragesApiComponentImpl implements SistragesApiComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public RConfiguracionGlobal obtenerConfiguracionGlobal() {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/configuracionGlobal", RConfiguracionGlobal.class);
	}

	@Override
	public RConfiguracionEntidad obtenerConfiguracionEntidad(final String idEntidad) {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/entidad/" + idEntidad, RConfiguracionEntidad.class);
	}

	@Override
	public RVersionTramite recuperarDefinicionTramite(final String idTramite, final int version, final String idioma) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		final RVersionTramite rversion = restTemplate
				.getForObject(getUrl() + "/tramite/" + idTramite + "/" + version + "/" + idioma, RVersionTramite.class);
		return rversion;
	}

	@Override
	public RAvisosEntidad obtenerAvisosEntidad(final String idEntidad) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/entidad/" + idEntidad + "/avisos", RAvisosEntidad.class);
	}

	@Override
	public RValoresDominio resuelveDominioFuenteDatos(final RDominio dominio) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/obtenerValoresDominioFD/" + dominio.getIdentificador(),
				RValoresDominio.class);

	}

	@Override
	public RValoresDominio resuelveDominioListaFija(final RDominio dominio) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/obtenerValoresDominioLF/" + dominio.getIdentificador(),
				RValoresDominio.class);

	}

	/**** Private functions. **/
	/**
	 * Obtiene el password.
	 *
	 * @return
	 */
	private String getPassword() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_PWD);
	}

	/**
	 * Obtiene el usuario
	 *
	 * @return
	 */
	private String getUser() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_USR);
	}

	/**
	 * Obtiene la url.
	 *
	 * @return
	 */
	private String getUrl() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_URL);
	}

}
