package es.caib.sistra2.commons.plugins.autenticacion.mock;

import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.autenticacion.api.AutenticacionPluginException;
import es.caib.sistra2.commons.plugins.autenticacion.api.DatosUsuario;
import es.caib.sistra2.commons.plugins.autenticacion.api.IComponenteAutenticacionPlugin;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoQAA;

/**
 * Plugin mock componente autenticación.
 *
 * @author Indra
 *
 */
public class ComponenteAutenticacionPluginMock extends AbstractPluginProperties
		implements IComponenteAutenticacionPlugin {

	/**
	 * Punto de entrada retorno componente
	 * es.caib.sistra2.commons.plugins.autenticacion.
	 */
	private static final String PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN = "/asistente/retornoAutenticacion.html";

	private static TipoQAA lastQaa;

	public ComponenteAutenticacionPluginMock() {
	}

	public ComponenteAutenticacionPluginMock(final String prefijoPropiedades, final Properties properties) {
	}

	@Override
	public String iniciarSesionAutenticacion(final String codigoEntidad, final String idioma,
			final List<TipoAutenticacion> metodos, final TipoQAA qaa, final String callback, final String callbackError)
			throws AutenticacionPluginException {
		// Simulamos directamente retorno componente
		// es.caib.sistra2.commons.plugins.autenticacion. Ponemos la
		// primera letra del ticket el primer tipo de
		// es.caib.sistra2.commons.plugins.autenticacion para simular
		// ese metodo.
		String prefix;
		prefix = metodos.get(0).toString();
		lastQaa = qaa;
		return PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN + "?ticket=" + prefix + "12345";
	}

	@Override
	public DatosUsuario validarTicketAutenticacion(final String pTicket) throws AutenticacionPluginException {
		// Simulamos segun primera letra del ticket
		DatosUsuario u;
		if (pTicket.startsWith(TipoAutenticacion.AUTENTICADO.toString())) {
			u = generarUserMock();
		} else {
			u = generarUsuarioAnonimo();
		}
		return u;
	}

	@Override
	public String iniciarSesionLogout(final String codigoEntidad, final String pIdioma, final String pCallback)
			throws AutenticacionPluginException {
		throw new RuntimeException("No implementado en versión mock");
	}

	/**
	 * Genera usuario mock autenticado.
	 *
	 * @return usuario mock autenticado
	 */
	private DatosUsuario generarUserMock() {
		final DatosUsuario ui = new DatosUsuario();
		ui.setAutenticacion(TipoAutenticacion.AUTENTICADO);
		ui.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_CERTIFICADO);
		ui.setQaa(lastQaa);
		ui.setNif("00000000T");
		ui.setNombre("NOM");
		ui.setApellido1("APE1");
		ui.setApellido2("APE2");
		ui.setEmail("correo@email.es");
		return ui;
	}

	/**
	 * Genera usuario anonimo.
	 *
	 * @return usuario anonimo
	 */
	private DatosUsuario generarUsuarioAnonimo() {
		final DatosUsuario ui = new DatosUsuario();
		ui.setAutenticacion(TipoAutenticacion.ANONIMO);
		ui.setMetodoAutenticacion(TipoMetodoAutenticacion.ANONIMO);
		return ui;
	}

}
