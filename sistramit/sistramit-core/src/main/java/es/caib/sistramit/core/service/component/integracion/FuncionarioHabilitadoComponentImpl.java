package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.funcionariohabilitado.api.FuncionarioHabilitadoPluginException;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistra2.commons.plugins.funcionariohabilitado.api.IFuncionarioHabilitadoPlugin;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementación Funcionario Habilitado Component.
 *
 * @author Indra
 *
 */
@Component("funcionarioHabilitadoComponent")
public final class FuncionarioHabilitadoComponentImpl implements FuncionarioHabilitadoComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public String avisarTramiteFinalizado(TramiteFinalizado tramiteFinalizado, boolean debug) {
		String errorAviso = null;
		try {
			// Obtenemos el plugin de funcionario habilitado
			IFuncionarioHabilitadoPlugin plgFH = (IFuncionarioHabilitadoPlugin) configuracionComponent
					.obtenerPluginEntidad(TypePluginEntidad.FUNCIONARIO_HABILITADO, tramiteFinalizado.getIdEntidad());
			// Avisamos a FH
			plgFH.avisarFinalizacionTramite(tramiteFinalizado.getFuncionarioHabilitadoNif(),tramiteFinalizado.getFuncionarioHabilitadoIdActuacion(), tramiteFinalizado.getNumeroRegistro(), tramiteFinalizado.getFechaRegistro(), tramiteFinalizado.getIdioma(), debug);
		} catch (Exception fhe) {
			// Si ha habido excepción al avisar, retornamos mensaje de error
			errorAviso = "Error al avisar a Funcionario Habilitado del trámite finalizado: " + fhe.getMessage();
		}
		// Retornamos error (null si no ha habido error)
		return errorAviso;
	}
}
