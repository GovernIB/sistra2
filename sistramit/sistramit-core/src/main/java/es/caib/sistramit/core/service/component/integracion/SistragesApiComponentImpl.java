package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;

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
		return restTemplate.getForObject(getUrl() + "/tramite/" + idTramite + "/" + version + "/" + idioma,
				RVersionTramite.class);
	}

	@Override
	public RDominio recuperarDefinicionDominio(final String idDominio) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/dominio/" + idDominio, RDominio.class);
	}

	@Override
	public RAvisosEntidad obtenerAvisosEntidad(final String idEntidad) {
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/entidad/" + idEntidad + "/avisos", RAvisosEntidad.class);
	}

	@Override
	public RValoresDominio resuelveDominioFuenteDatos(final RDominio dominio,
			final ParametrosDominio parametrosDominio) {

		// Convertimos lista parametros
		final List<RValorParametro> parametros = new ArrayList<>();
		if (parametrosDominio != null && parametrosDominio.getParametros() != null
				&& !parametrosDominio.getParametros().isEmpty()) {
			for (final es.caib.sistramit.core.service.model.integracion.ParametroDominio parametroDominio : parametrosDominio
					.getParametros()) {
				final RValorParametro rValorParametro = new RValorParametro();
				rValorParametro.setCodigo(parametroDominio.getCodigo());
				rValorParametro.setValor(parametroDominio.getValor());
				parametros.add(rValorParametro);
			}
		}
		final RListaParametros listaParametros = new RListaParametros();
		listaParametros.setParametros(parametros);

		// Realizamos llamada
		final RestTemplate restTemplate = new RestTemplate();
		final String url = getUrl();
		final String usuario = getUser();
		final String pass = getPassword();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(usuario, pass));
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<RListaParametros> request = new HttpEntity<>(listaParametros, headers);
		final ResponseEntity<RValoresDominio> responseRest = restTemplate.postForEntity(
				url + "/dominioFuenteDatos/" + dominio.getIdentificador(), request, RValoresDominio.class);
		return responseRest.getBody();
	}

	@Override
	public RValoresDominio resuelveDominioListaFija(final RDominio dominio) {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		return restTemplate.getForObject(getUrl() + "/dominioListaFija/" + dominio.getIdentificador(),
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
