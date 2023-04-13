package es.caib.sistrahelp.core.service.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.fundaciobit.pluginsib.core.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrahelp.core.api.exception.CargaConfiguracionException;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;

@Component("configuracionComponent")
public class ConfiguracionComponentImpl implements ConfiguracionComponent {

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales;

	/** Componente STG. */
	@Autowired
	private SistragesApiComponent sistragesComponent;

	@PostConstruct
	public void init() {
		// Recupera propiedades configuracion especificadas en properties
		propiedadesLocales = recuperarConfiguracionProperties();
	}

	@Override
	public String obtenerPropiedadConfiguracion(final TypePropiedadConfiguracion propiedad) {
		return readPropiedad(propiedad);
	}

	@Override
	public String obtenerPropiedadConfiguracionSistrages(final TypePropiedadConfiguracion propiedad) {
		return getPropiedadGlobal(propiedad);
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
		final String pathProperties = System.getProperty(TypePropiedadConfiguracion.PATH_PROPERTIES.toString());
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
	 * @param propiedad propiedad
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

	/**
	 * Lee propiedad.
	 *
	 * @param propiedad  propiedad
	 * @param forceLocal si fuerza solo a buscar en el properties local y no buscar
	 *                   en la configuración global del STG
	 * @return valor propiedad (nulo si no existe)
	 */
	private String readPropiedad(final TypePropiedadConfiguracion propiedad) {
		return propiedadesLocales.getProperty(propiedad.toString());
	}

}
