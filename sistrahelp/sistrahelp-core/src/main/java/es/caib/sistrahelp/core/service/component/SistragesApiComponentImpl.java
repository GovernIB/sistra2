package es.caib.sistrahelp.core.service.component;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.RPermisoHelpDesk;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.Literal;
import es.caib.sistrahelp.core.api.model.Traduccion;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;

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
	public List<RPermisoHelpDesk> obtenerPermisosHelpdesk() {
		List<RPermisoHelpDesk> resultado = null;
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

		final RPermisoHelpDesk[] listaPermisos = restTemplate.getForObject(getUrl() + "/permisosHelpdesk",
				RPermisoHelpDesk[].class);

		if (listaPermisos != null) {
			resultado = Arrays.asList(listaPermisos);
		}

		return resultado;
	}

	@Override
	public Entidad obtenerDatosEntidad(final String idEntidad) {

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		final RConfiguracionEntidad configuracionEntidad = restTemplate.getForObject(getUrl() + "/entidad/" + idEntidad,
				RConfiguracionEntidad.class);

		Entidad entidad = null;

		if (configuracionEntidad != null) {
			entidad = new Entidad();

			entidad.setCodigoDIR3(configuracionEntidad.getIdentificador());

			if (configuracionEntidad.getDescripcion() != null) {
				final Literal literal = new Literal();

				for (final RLiteralIdioma rLiteral : configuracionEntidad.getDescripcion().getLiterales()) {
					final Traduccion trad = new Traduccion(rLiteral.getIdioma(), rLiteral.getDescripcion());
					literal.add(trad);
				}

				entidad.setNombre(literal);
			}

			entidad.setLogoGestor(configuracionEntidad.getLogoGestor());

		}
		return entidad;

	}

	private String getPassword() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_PWD);
	}

	private String getUser() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_USR);
	}

	private String getUrl() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_URL);
	}

}
