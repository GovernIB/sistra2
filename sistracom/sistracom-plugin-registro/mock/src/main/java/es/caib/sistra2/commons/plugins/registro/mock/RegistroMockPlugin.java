package es.caib.sistra2.commons.plugins.registro.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.TipoAsunto;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;

/**
 * Plugin mock registro.
 *
 * @author Indra
 *
 */
public class RegistroMockPlugin extends AbstractPluginProperties implements IRegistroPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "mock.";

	public RegistroMockPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public List<OficinaRegistro> obtenerOficinasRegistro(final String codigoEntidad, final TypeRegistro tipoRegistro)
			throws RegistroPluginException {

		final List<OficinaRegistro> res = new ArrayList<>();
		final OficinaRegistro of1 = new OficinaRegistro();
		of1.setCodigo("codofi1");
		of1.setNombre("oficina registro 1");
		res.add(of1);
		final OficinaRegistro of2 = new OficinaRegistro();
		of2.setCodigo("codofi2");
		of2.setNombre("oficina registro 2");
		res.add(of2);
		final OficinaRegistro of3 = new OficinaRegistro();
		of3.setCodigo("codofi3");
		of3.setNombre("oficina registro 3");
		res.add(of3);
		return res;

	}

	@Override
	public List<LibroOficina> obtenerLibrosOficina(final String codigoEntidad, final String codigoOficina,
			final TypeRegistro tipoRegistro) throws RegistroPluginException {
		final List<LibroOficina> res = new ArrayList<>();
		final LibroOficina lo1 = new LibroOficina();
		lo1.setCodigo("codlib." + codigoOficina + ".1");
		lo1.setNombre("libro oficina (" + codigoOficina + ") 1");
		res.add(lo1);
		final LibroOficina lo2 = new LibroOficina();
		lo2.setCodigo("codlib." + codigoOficina + ".2");
		lo2.setNombre("libro oficina (" + codigoOficina + ") 2");
		res.add(lo2);
		final LibroOficina lo3 = new LibroOficina();
		lo3.setCodigo("codlib." + codigoOficina + ".3");
		lo3.setNombre("libro oficina (" + codigoOficina + ") 3");
		res.add(lo3);
		return res;
	}

	@Override
	public List<TipoAsunto> obtenerTiposAsunto(final String codigoEntidad) throws RegistroPluginException {
		final List<TipoAsunto> res = new ArrayList<>();
		final TipoAsunto ta1 = new TipoAsunto();
		ta1.setCodigo("codtip1");
		ta1.setNombre("tipo asunto 1");
		res.add(ta1);
		final TipoAsunto ta2 = new TipoAsunto();
		ta2.setCodigo("codtip2");
		ta2.setNombre("tipo asunto 2");
		res.add(ta2);
		final TipoAsunto ta3 = new TipoAsunto();
		ta3.setCodigo("codtip3");
		ta3.setNombre("tipo asunto 3");
		res.add(ta3);
		return res;
	}

	@Override
	public ResultadoRegistro registroEntrada(final String codigoEntidad, final AsientoRegistral asientoRegistral)
			throws RegistroPluginException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoRegistro registroSalida(final String codigoEntidad, final AsientoRegistral asientoRegistral)
			throws RegistroPluginException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] obtenerJustificanteRegistro(final String codigoEntidad, final String numeroRegistro)
			throws RegistroPluginException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *            propiedad
	 * @return valor
	 * @throws RegistroPluginException
	 */
	private String getPropiedad(final String propiedad) throws RegistroPluginException {
		final String res = getProperty(REGISTRO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new RegistroPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
		}
		return res;
	}

}
