package es.caib.sistra2.commons.plugins.registro.regweb3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import es.caib.regweb3.ws.api.v3.LibroWs;
import es.caib.regweb3.ws.api.v3.OficinaWs;
import es.caib.regweb3.ws.api.v3.RegWebInfoWs;
import es.caib.regweb3.ws.api.v3.TipoAsuntoWs;
import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.TipoAsunto;
import es.caib.sistra2.commons.plugins.registro.api.TypeRegistro;
import es.caib.sistra2.commons.plugins.registro.regweb3.UtilsRegweb3;


/**
 * Plugin mock catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class RegistroRegweb3Plugin extends AbstractPluginProperties
        implements IRegistroPlugin {

    /** Prefix. */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "regweb3.";

    public RegistroRegweb3Plugin(final String prefijoPropiedades,
            final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    @Override
    public List<OficinaRegistro> obtenerOficinasRegistro(
            final String codigoEntidad, TypeRegistro tipoRegistro )
            		throws RegistroPluginException {

        final List<OficinaRegistro> res = new ArrayList<>();

        try {
        	RegWebInfoWs service = UtilsRegweb3.getRegistroInfoService(codigoEntidad,
        			getPropiedad("endpoint_info"), getPropiedad("usr"), getPropiedad("pwd"));

        	Long regType = null;
			if (tipoRegistro == TypeRegistro.REGISTRO_ENTRADA) {
				regType = ConstantesRegweb3.REGISTRO_ENTRADA;
			} else if (tipoRegistro == TypeRegistro.REGISTRO_SALIDA) {
				regType = ConstantesRegweb3.REGISTRO_SALIDA;
			} else {
				throw new RegistroPluginException("Tipo registro no soportado: " + tipoRegistro);
			}

        	List<OficinaWs> resWs = service.listarOficinas(codigoEntidad, regType);

        	for (OficinaWs ofWs : resWs) {
				OficinaRegistro of = new OficinaRegistro();
				of.setCodigo(ofWs.getCodigo());
				of.setNombre(ofWs.getNombre());
				res.add(of);
			}

        } catch (Exception ex) {
        	throw new RegistroPluginException("Error consultando oficinas registro: " + ex.getMessage(), ex);
        }

        return res;

    }


	@Override
	public List<LibroOficina> obtenerLibrosOficina(
			String codigoEntidad, String codigoOficina, TypeRegistro tipoRegistro )
			throws RegistroPluginException {
		final List<LibroOficina> res = new ArrayList<>();

        try {
        	RegWebInfoWs service = UtilsRegweb3.getRegistroInfoService(codigoEntidad,
        			getPropiedad("endpoint_info"), getPropiedad("usr"), getPropiedad("pwd"));

        	Long regType = null;
			if (tipoRegistro == TypeRegistro.REGISTRO_ENTRADA) {
				regType = ConstantesRegweb3.REGISTRO_ENTRADA;
			} else if (tipoRegistro == TypeRegistro.REGISTRO_SALIDA) {
				regType = ConstantesRegweb3.REGISTRO_SALIDA;
			} else {
				throw new RegistroPluginException("Tipo registro no soportado: " + tipoRegistro);
			}

        	List<LibroWs> resWs = service.listarLibros(codigoEntidad, codigoOficina, regType);

        	for (LibroWs liWs : resWs) {
        		LibroOficina li = new LibroOficina();
				li.setCodigo(liWs.getCodigoLibro());
				li.setNombre(liWs.getNombreCorto());
				res.add(li);
			}

        } catch (Exception ex) {
        	throw new RegistroPluginException("Error consultando libros de la oficina " + codigoOficina + ": " + ex.getMessage(), ex);
        }

        return res;
	}

	@Override
	public List<TipoAsunto> obtenerTiposAsunto(String codigoEntidad) throws RegistroPluginException {
		final List<TipoAsunto> res = new ArrayList<>();

        try {
        	RegWebInfoWs service = UtilsRegweb3.getRegistroInfoService(codigoEntidad,
        			getPropiedad("endpoint_info"), getPropiedad("usr"), getPropiedad("pwd"));

        	List<TipoAsuntoWs> resWs = service.listarTipoAsunto(codigoEntidad);

        	for (TipoAsuntoWs taWs : resWs) {
        		TipoAsunto ta = new TipoAsunto();
				ta.setCodigo(taWs.getCodigo());
				ta.setNombre(taWs.getNombre());
				res.add(ta);
			}

        } catch (Exception ex) {
        	throw new RegistroPluginException("Error consultando tipos asunto: " + ex.getMessage(), ex);
        }

        return res;
	}

	@Override
	public ResultadoRegistro registroEntrada(String codigoEntidad, AsientoRegistral asientoRegistral)
			throws RegistroPluginException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoRegistro registroSalida(String codigoEntidad, AsientoRegistral asientoRegistral)
			throws RegistroPluginException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] obtenerJustificanteRegistro(String codigoEntidad, String numeroRegistro)
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
    private String getPropiedad(String propiedad) throws RegistroPluginException {
        final String res = getProperty(
                REGISTRO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
        if (res == null) {
            throw new RegistroPluginException("No se ha especificado parametro "
                    + propiedad + " en propiedades");
        }
        return res;
    }

}
