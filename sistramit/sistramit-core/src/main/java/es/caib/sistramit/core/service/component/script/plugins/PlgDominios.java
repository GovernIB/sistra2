package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistramit.core.service.component.integracion.DominiosComponent;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
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

	/** Definición trámite. */
	private final DominiosComponent dominiosComponent;

	public PlgDominios(final DefinicionTramiteSTG pDefinicionTramite, final DominiosComponent pDominiosComponent) {
		definicionTramite = pDefinicionTramite;
		dominiosComponent = pDominiosComponent;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public ClzParametrosDominioInt crearParametros() {
		return new ClzParametrosDominio();
	}

	@Override
	public ClzValoresDominioInt invocarDominio(final String idDominio) {
		final ValoresDominio valoresDominio = dominiosComponent.recuperarDominio(idDominio, null, definicionTramite);
		return new ClzValoresDominio(valoresDominio);
	}

	@Override
	public ClzValoresDominioInt invocarDominio(final String idDominio, final ClzParametrosDominioInt parametros) {
		ParametrosDominio params = null;
		if (parametros != null) {
			params = parametros.getParametros();
		}
		final ValoresDominio valoresDominio = dominiosComponent.recuperarDominio(idDominio, params, definicionTramite);
		return new ClzValoresDominio(valoresDominio);
	}

}
