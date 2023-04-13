package es.caib.sistrahelp.core.service;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.digester.plugins.PluginException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.fundaciobit.pluginsib.core.IPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.caib.sistrahelp.core.api.exception.FicheroExternoException;
import es.caib.sistrahelp.core.api.model.ContenidoFichero;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;
import es.caib.sistrahelp.core.service.component.ConfiguracionComponent;
import es.caib.sistrahelp.core.service.component.SistragesApiComponent;

@Service("configuracionService")
public class ConfiguracionServiceImpl implements ConfiguracionService {

	@Autowired
	private SistragesApiComponent sistragesApiComponent;

	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	@NegocioInterceptor
	public Entidad obtenerDatosEntidad(final String idEntidad) {
		return sistragesApiComponent.obtenerDatosEntidad(idEntidad);
	}

	@Override
	@NegocioInterceptor
	public IPlugin obtenerPluginGlobal(final TypePluginGlobal tipoPlugin) throws PluginException {
		return sistragesApiComponent.obtenerPluginGlobal(tipoPlugin);
	}

	@Override
	@NegocioInterceptor
	public ContenidoFichero getContentFicheroByPath(final String fichero) {

		final String rutafichero = configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PATH_FICHEROS_EXTERNOS) + fichero;

		// Obtiene contenido fichero
		try {
			final FileInputStream fis = new FileInputStream(rutafichero);
			final byte[] content = IOUtils.toByteArray(fis);
			fis.close();

			final ContenidoFichero cf = new ContenidoFichero();

			cf.setFilename(FilenameUtils.getName(fichero));
			cf.setContent(content);

			return cf;
		} catch (final IOException e) {
			throw new FicheroExternoException("Error al acceder al fichero con path " + rutafichero, e);
		}
	}

	@Override
	@NegocioInterceptor
	public String obtenerPropiedadConfiguracion(final TypePropiedadConfiguracion propiedad) {
		return configuracionComponent.obtenerPropiedadConfiguracion(propiedad);
	}

	@Override
	@NegocioInterceptor
	public String obtenerPropiedadConfiguracionSistrages(final TypePropiedadConfiguracion propiedad) {
		return configuracionComponent.obtenerPropiedadConfiguracionSistrages(propiedad);
	}
}
