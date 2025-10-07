package es.caib.sistra2.commons.plugins.funcionariohabilitado.mock;

import java.util.Date;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.caib.sistra2.commons.plugins.funcionariohabilitado.api.FuncionarioHabilitadoPluginException;
import es.caib.sistra2.commons.plugins.funcionariohabilitado.api.IFuncionarioHabilitadoPlugin;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

/**
 * Plugin mock componente funcionario habilitado.
 *
 * @author Indra
 *
 */
public class ComponenteFuncionarioHabilitadoPluginMock extends AbstractPluginProperties implements IFuncionarioHabilitadoPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "mock.";

	/** Log.*/
	private static final Logger LOGGER = LoggerFactory.getLogger(ComponenteFuncionarioHabilitadoPluginMock.class);

	public ComponenteFuncionarioHabilitadoPluginMock(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}


	@Override
	public void avisarFinalizacionTramite(String nifFH, String idActuacionFH, String numeroRegistro, Date fechaRegistro, String idioma, boolean debug) throws FuncionarioHabilitadoPluginException {
		LOGGER.debug("Mock - Avisar finalizacion tramite FH: {} con registro {}", idActuacionFH, numeroRegistro);
	}
}
