package es.caib.sistra2.commons.plugins.mock.firmacliente;

import java.util.Properties;

import org.fundaciobit.plugins.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.autenticacion.DatosUsuario;
import es.caib.sistra2.commons.plugins.autenticacion.TipoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.firmacliente.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.FicheroFirmado;
import es.caib.sistra2.commons.plugins.firmacliente.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.InfoSesionFirma;
import es.caib.sistra2.commons.plugins.firmacliente.TypeEstadoFirmado;

/**
 * Plugin mock componente firma.
 *
 * @author Indra
 *
 */
public class ComponenteFirmaPluginMock extends AbstractPluginProperties implements IFirmaPlugin {

	public ComponenteFirmaPluginMock() {
	}

	public ComponenteFirmaPluginMock(final String prefijoPropiedades, final Properties properties) {
	}

	/**
	 * Genera usuario mock autenticado.
	 *
	 * @return usuario mock autenticado
	 */
	public DatosUsuario generarUserMock() {
		final DatosUsuario ui = new DatosUsuario();
		ui.setAutenticacion(TipoAutenticacion.AUTENTICADO);
		ui.setMetodoAutenticacion(TipoMetodoAutenticacion.CLAVE_CERTIFICADO);
		ui.setNif("11111111H");
		ui.setNombre("José");
		ui.setApellido1("García");
		ui.setApellido2("Gutierrez");
		ui.setEmail("correo@email.es");
		return ui;
	}

	/**
	 * Genera usuario anonimo.
	 *
	 * @param sesionInfo
	 *            sesion info
	 * @return usuario anonimo
	 */
	public DatosUsuario generarUsuarioAnonimo() {
		final DatosUsuario ui = new DatosUsuario();
		ui.setAutenticacion(TipoAutenticacion.ANONIMO);
		ui.setMetodoAutenticacion(TipoMetodoAutenticacion.ANONIMO);
		return ui;
	}

	@Override
	public String generarSesionFirma(final InfoSesionFirma infoSesionFirma) {
		return "A00000001";
	}

	@Override
	public void ficheroAFirmar(final FicheroAFirmar ficheroAFirmar) {
		// Vacio
	}

	@Override
	public String iniciarSesionFirma(final String idSesionFirma, final String urlCallBack, final String paramAdic) {
		return "localhost:8080/sistramit";
	}

	@Override
	public TypeEstadoFirmado obtenerEstadoSesionFirma(final String idSesionFirma) {
		return TypeEstadoFirmado.EN_PROGRESO;
	}

	@Override
	public FicheroFirmado obtenerFirmaFichero(final String idSesionFirma, final String idFicheroFirma) {
		final FicheroFirmado fichero = new FicheroFirmado();
		final byte[] contenido = new byte[666];
		fichero.setFirmaFichero(contenido);
		fichero.setMimetypeFichero("application/pdf");
		fichero.setNombreFichero("fichero.pdf");
		return fichero;
	}

	@Override
	public void cerrarSesionFirma(final String idSesionFirma) {
		// Vacio
	}

}
