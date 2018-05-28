package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.ClzParametrosDominioInt;
import es.caib.sistramit.core.service.model.script.ClzValoresDominioInt;
import es.caib.sistramit.core.service.model.script.PlgDominiosInt;

/**
 * Plugin de acceso a dominios.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgDominios implements PlgDominiosInt {

	/** Definición trámite. */
	private final DefinicionTramiteSTG definicionTramite;

	public PlgDominios(final DefinicionTramiteSTG pDefinicionTramite) {
		definicionTramite = pDefinicionTramite;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public ClzParametrosDominioInt crearParametros() {
		// TODO PENDIENTE
		return null;
	}

	@Override
	public ClzValoresDominioInt invocarDominio(final String idDominio) {
		// TODO PENDIENTE
		return null;
	}

	@Override
	public ClzValoresDominioInt invocarDominio(final String idDominio, final ClzParametrosDominioInt parametros) {
		// TODO PENDIENTE
		return null;
	}

}
