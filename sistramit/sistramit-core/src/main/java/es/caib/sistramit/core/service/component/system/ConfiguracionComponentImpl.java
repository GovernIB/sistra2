package es.caib.sistramit.core.service.component.system;

import es.caib.sistrages.rest.api.interna.*;
import es.caib.sistramit.core.api.exception.CargaConfiguracionException;
import es.caib.sistramit.core.api.exception.PluginErrorException;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component("configuracionComponent")
public class ConfiguracionComponentImpl implements ConfiguracionComponent {

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales;

	/** Componente STG. */
	@Autowired
	private SistragesComponent sistragesComponent;

	@PostConstruct
	public void init() {
		// Recupera propiedades configuracion especificadas en properties
		propiedadesLocales = recuperarConfiguracionProperties();
	}

	@Override
	public String obtenerPropiedadConfiguracion(final TypePropiedadConfiguracion propiedad, final boolean forceLocal) {
		return readPropiedad(propiedad, forceLocal);
	}

	@Override
	public String obtenerPropiedadConfiguracion(final TypePropiedadConfiguracion propiedad) {
		return readPropiedad(propiedad, false);
	}

	@Override
	public DefinicionTramiteSTG recuperarDefinicionTramite(final String idTramite, final int version,
			final String idioma) {
		final RVersionTramite definicionVersion = sistragesComponent.recuperarDefinicionTramite(idTramite, version,
				idioma);
		final DefinicionTramiteSTG dt = new DefinicionTramiteSTG(new Date(), definicionVersion);
		return dt;
	}

	@Override
	public RConfiguracionEntidad obtenerConfiguracionEntidad(final String idEntidad) {
		return sistragesComponent.obtenerConfiguracionEntidad(idEntidad);
	}

	@Override
	public RAvisosEntidad obtenerAvisosEntidad(final String idEntidad) {
		return sistragesComponent.obtenerAvisosEntidad(idEntidad);
	}

	@Override
	public IPlugin obtenerPluginGlobal(final TypePluginGlobal tipoPlugin) {
		final RConfiguracionGlobal confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
		return createPlugin(confGlobal.getPlugins(), tipoPlugin.toString());
	}

	@Override
	public IPlugin obtenerPluginEntidad(final TypePluginEntidad tipoPlugin, final String idEntidad) {
		final RConfiguracionEntidad confEntidad = sistragesComponent.obtenerConfiguracionEntidad(idEntidad);
		return createPlugin(confEntidad.getPlugins(), tipoPlugin.toString());
	}

	@Override
	public String obtenerUrlResources() {
		final String urlResources = readPropiedad(TypePropiedadConfiguracion.SISTRAMIT_URL, false) + "/resources";
		return urlResources;
	}

	// ----------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// ----------------------------------------------------------------------
	/**
	 * Carga propiedades locales de fichero de properties.
	 *
	 * @return properties
	 */
	private Properties recuperarConfiguracionProperties() {
		final String pathProperties = System.getProperty("es.caib.sistramit.properties.path");
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			final Properties props = new Properties();
			props.load(fis);
			return props;
		} catch (final IOException e) {
			throw new CargaConfiguracionException(
					"Error al cargar la configuracion del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}
	}

	/**
	 * Obtiene valor propiedad de configuracion global.
	 *
	 * @param propiedad
	 *            propiedad
	 * @return valor
	 */
	private String getPropiedadGlobal(final TypePropiedadConfiguracion propiedad) {
		String res = null;
		final RConfiguracionGlobal configuracionGlobal = sistragesComponent.obtenerConfiguracionGlobal();
		if (configuracionGlobal != null && configuracionGlobal.getPropiedades() != null
				&& configuracionGlobal.getPropiedades().getParametros() != null) {
			for (final RValorParametro vp : configuracionGlobal.getPropiedades().getParametros()) {
				if (propiedad.toString().equals(vp.getCodigo())) {
					res = vp.getValor();
					break;
				}
			}
		}
		return res;
	}

	private IPlugin createPlugin(final List<RPlugin> plugins, final String plgTipo) {
		IPlugin plg = null;
		RPlugin rplg = null;
		String classname = null;
		try {
			for (final RPlugin p : plugins) {
				if (p.getTipo().equals(plgTipo)) {
					rplg = p;
					break;
				}
			}

			if (rplg == null) {
				throw new PluginErrorException("No existe plugin de tipo " + plgTipo);
			}

			classname = rplg.getClassname();

			Properties prop = null;
			if (rplg.getPrefijoPropiedades() != null && rplg.getPropiedades() != null
					&& rplg.getPropiedades().getParametros() != null
					&& !rplg.getPropiedades().getParametros().isEmpty()) {
				prop = new Properties();
				for (final RValorParametro parametro : rplg.getPropiedades().getParametros()) {
					prop.put(rplg.getPrefijoPropiedades() + parametro.getCodigo(), parametro.getValor());
				}
			}

			/**
			try {
				prop = new Properties();
				prop.put("plugins.firma.url",
						"http://portafib2.fundaciobit.org/portafib/common/rest/apifirmawebsimple/v1/");
				prop.put("plugins.firma.usr", "ibsalut_sistra2");
				prop.put("plugins.firma.pwd", "ibsalut_sistra2");
				plg = (IPlugin) PluginsManager.instancePluginByClassName(
						"es.caib.sistra2.commons.plugins.mock.firmacliente.ComponenteFirmaSimpleWebPlugin",
						"plugins.firma.", prop);
				final ComponenteFirmaSimpleWebPlugin plugin = (ComponenteFirmaSimpleWebPlugin) plg;

				// Paso 1. Crear sesion
				final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
				infoSesionFirma.setEntidad("12345678C");
				infoSesionFirma.setIdioma("ca");
				infoSesionFirma.setNombreUsuario("jmico");
				final String idSession = plugin.generarSesionFirma(infoSesionFirma);

				// Paso 2. Leer y subir los 2 ficheros.
				//Anyadimos docs
				final InputStream is = new DataInputStream(new FileInputStream("P://hola.pdf"));
				final ByteArrayOutputStream fos = new ByteArrayOutputStream();
				FileUtils.copy(is, fos);
				final FicheroAFirmar fichero = new FicheroAFirmar();
				fichero.setFichero(fos.toByteArray());
				fichero.setMimetypeFichero("application/pdf");
				fichero.setIdioma("ca");
				fichero.setSignNumber(1);
				fichero.setNombreFichero("hola.pdf");
				fichero.setRazon("Fichero prueba1");
				fichero.setSignID("666");
				fichero.setSesion(idSession);
				plugin.ficheroAFirmar(fichero);

				final InputStream is2 = new DataInputStream(new FileInputStream("P://hola2.pdf"));
				final ByteArrayOutputStream fos2 = new ByteArrayOutputStream();
				FileUtils.copy(is2, fos2);
				final FicheroAFirmar fichero2 = new FicheroAFirmar();
				fichero2.setFichero(fos2.toByteArray());
				fichero2.setMimetypeFichero("application/pdf");
				fichero2.setIdioma("ca");
				fichero2.setSignNumber(1);
				fichero2.setNombreFichero("hola2.pdf");
				fichero2.setRazon("Fichero prueba1");
				fichero2.setSignID("777");
				fichero2.setSesion(idSession);
				plugin.ficheroAFirmar(fichero2);

				// Paso 3. Crear transaction y obtener url
				final String url = plugin.iniciarSesionFirma(idSession, null, null);
				System.out.println("URL:" + url);

				// Paso 3.5 Esperando a que se finalice el firmado (4 seg durmiendo)
				TypeEstadoFirmado estado = TypeEstadoFirmado.INICIALIZADO;
				while (estado != TypeEstadoFirmado.FINALIZADO_OK) {
					estado = plugin.obtenerEstadoSesionFirma(idSession);
					System.out.println("estado: " + estado);
					Thread.sleep(4000l);
				}

				// Paso 4. Obtenemos ficheros cuando finalizado
				if (estado == TypeEstadoFirmado.FINALIZADO_OK) {
					final FicheroFirmado ficheroFirmado1 = plugin.obtenerFirmaFichero(idSession, "666");
					final FileOutputStream output = new FileOutputStream(
							new File("P://" + ficheroFirmado1.getNombreFichero()));
					IOUtils.write(ficheroFirmado1.getFirmaFichero(), output);
					output.close();

					final FicheroFirmado ficheroFirmado2 = plugin.obtenerFirmaFichero(idSession, "777");
					final FileOutputStream output2 = new FileOutputStream(
							new File("P://" + ficheroFirmado2.getNombreFichero()));
					IOUtils.write(ficheroFirmado2.getFirmaFichero(), output2);
					output2.close();
				}

				// Paso 5. Cerramos session.
				plugin.cerrarSesionFirma(idSession);

			} catch (final Exception e) {
				e.printStackTrace();
			}*/

			plg = (IPlugin) PluginsManager.instancePluginByClassName(classname, rplg.getPrefijoPropiedades(), prop);

			if (plg == null) {
				throw new PluginErrorException(
						"No se ha podido instanciar plugin de tipo " + plgTipo + " , PluginManager devuelve nulo.");
			}

			return plg;

		} catch (final Exception e) {
			throw new PluginErrorException("Error al instanciar plugin " + plgTipo + " con classname " + classname, e);
		}
	}

	/**
	 * Lee propiedad.
	 *
	 * @param propiedad
	 *            propiedad
	 * @param forceLocal
	 *            si fuerza solo a buscar en el properties local y no buscar en la
	 *            configuración global del STG
	 * @return valor propiedad (nulo si no existe)
	 */
	private String readPropiedad(final TypePropiedadConfiguracion propiedad, final boolean forceLocal) {
		// Busca primero en propiedades locales
		String prop = propiedadesLocales.getProperty(propiedad.toString());
		// Si no, busca en propiedades globales
		if (StringUtils.isBlank(prop) && !forceLocal) {
			prop = getPropiedadGlobal(propiedad);
		}
		return prop;
	}

}
