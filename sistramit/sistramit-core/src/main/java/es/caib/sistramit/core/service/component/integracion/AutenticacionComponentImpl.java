package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.autenticacion.api.AutenticacionPluginException;
import es.caib.sistra2.commons.plugins.autenticacion.api.DatosUsuario;
import es.caib.sistra2.commons.plugins.autenticacion.api.IComponenteAutenticacionPlugin;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoAutenticacion;
import es.caib.sistramit.core.api.exception.AutenticacionException;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.DatosAutenticacionUsuario;

/**
 * Implementación acceso componente autenticación.
 *
 * @author Indra
 *
 */
@Component("autenticacionComponent")
public final class AutenticacionComponentImpl implements AutenticacionComponent {

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public String iniciarSesionAutenticacion(final String codigoEntidad, final String idioma,
			final List<TypeAutenticacion> metodos, final String qaa, final String urlCallback,
			final String urlCallbackError, final boolean pDebugEnabled) {

		final IComponenteAutenticacionPlugin plgAuth = getPlugin();

		final List<TipoAutenticacion> metodosAut = new ArrayList<>();
		for (final TypeAutenticacion t : metodos) {
			metodosAut.add(TipoAutenticacion.fromString(t.toString()));
		}

		try {
			return plgAuth.iniciarSesionAutenticacion(codigoEntidad, idioma, metodosAut, qaa, urlCallback,
					urlCallbackError);
		} catch (final AutenticacionPluginException e) {
			throw new AutenticacionException("Error al iniciar sesión de autenticación", e);
		}

	}

	@Override
	public DatosAutenticacionUsuario validarTicketAutenticacion(final String pTicket) {

		final IComponenteAutenticacionPlugin plgAuth = getPlugin();

		DatosUsuario u;
		try {
			u = plgAuth.validarTicketAutenticacion(pTicket);
		} catch (final AutenticacionPluginException e) {
			throw new AutenticacionException("Error al validar ticket de autenticación", e);
		}
		final DatosAutenticacionUsuario res = new DatosAutenticacionUsuario();
		res.setAutenticacion(TypeAutenticacion.fromString(u.getAutenticacion().toString()));
		if (u.getMetodoAutenticacion() != null) {
			res.setMetodoAutenticacion(TypeMetodoAutenticacion.fromString(u.getMetodoAutenticacion().toString()));
		}
		res.setNif(u.getNif());
		res.setNombre(u.getNombre());
		res.setApellido1(u.getApellido1());
		res.setApellido2(u.getApellido2());
		res.setEmail(u.getEmail());

		return res;
	}

	@Override
	public String iniciarSesionLogout(final String codigoEntidad, final String idioma, final String urlCallback,
			final boolean pDebugEnabled) {
		final IComponenteAutenticacionPlugin plgAuth = getPlugin();
		try {
			return plgAuth.iniciarSesionLogout(codigoEntidad, idioma, urlCallback);
		} catch (final AutenticacionPluginException e) {
			throw new AutenticacionException("Error al iniciar sesión de logout", e);
		}
	}

	/**
	 * Obtiene plugin
	 *
	 * @return plugin
	 */
	private IComponenteAutenticacionPlugin getPlugin() {
		final IComponenteAutenticacionPlugin plgAuth = (IComponenteAutenticacionPlugin) configuracionComponent
				.obtenerPluginGlobal(TypePluginGlobal.LOGIN);
		return plgAuth;
	}

}
