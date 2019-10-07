package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.autenticacion.api.AutenticacionPluginException;
import es.caib.sistra2.commons.plugins.autenticacion.api.DatosUsuario;
import es.caib.sistra2.commons.plugins.autenticacion.api.IComponenteAutenticacionPlugin;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoQAA;
import es.caib.sistramit.core.api.exception.AutenticacionException;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.DatosAutenticacionRepresentante;
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
			final List<TypeAutenticacion> metodos, final TypeQAA qaa, final String urlCallback,
			final String urlCallbackError, final boolean pDebugEnabled) {

		final IComponenteAutenticacionPlugin plgAuth = getPlugin();

		final List<TipoAutenticacion> metodosAut = new ArrayList<>();
		for (final TypeAutenticacion t : metodos) {
			metodosAut.add(TipoAutenticacion.fromString(t.toString()));
		}

		TipoQAA tipoQaa = null;
		if (qaa != null) {
			tipoQaa = TipoQAA.fromString(qaa.toString());
		}

		try {
			return plgAuth.iniciarSesionAutenticacion(codigoEntidad, idioma, metodosAut, tipoQaa, urlCallback,
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
		if (res.getAutenticacion() != TypeAutenticacion.ANONIMO) {
			if (u.getQaa() == null) {
				throw new AutenticacionException("No se ha retornado QAA");
			}
			res.setQaa(TypeQAA.fromString(u.getQaa().toString()));
		}

		if (u.getRepresentante() != null) {
			final DatosAutenticacionRepresentante representante = new DatosAutenticacionRepresentante();
			representante.setNif(u.getRepresentante().getNif());
			representante.setNombre(u.getRepresentante().getNombre());
			representante.setApellido1(u.getRepresentante().getApellido1());
			representante.setApellido2(u.getRepresentante().getApellido2());
			representante.setEmail(u.getRepresentante().getEmail());
			res.setRepresentante(representante);
		}

		// TODO REPRESENTANTE

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
