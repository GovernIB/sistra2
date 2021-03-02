package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistramit.core.service.model.script.ClzTramiteCPInt;

/**
 * Datos usuario.
 *
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzTramiteCP implements ClzTramiteCPInt {

	/**
	 * Datos usuario.
	 */
	private final DefinicionTramiteCP tramite;

	/**
	 * Constructor.
	 *
	 * @param ptramite
	 *                     usuario
	 */
	public ClzTramiteCP(final DefinicionTramiteCP ptramite) {
		super();
		tramite = ptramite;
	}

	@Override
	public String getDescripcion() {
		return tramite.getDescripcion();
	}

	@Override
	public String getEmailSoporte() {
		return tramite.getEmailSoporte();
	}

	@Override
	public String getIdentificador() {
		return tramite.getIdentificador();
	}

	@Override
	public String getOrganoDestinoDir3() {
		return tramite.getOrganoDestinoDir3();
	}

	@Override
	public String getPlazoFin() {
		return ValidacionesTipo.getInstance().formateaFecha(tramite.getPlazoFin(), ValidacionesTipo.FORMATO_FECHAHORA);
	}

	@Override
	public String getPlazoInicio() {
		return ValidacionesTipo.getInstance().formateaFecha(tramite.getPlazoInicio(),
				ValidacionesTipo.FORMATO_FECHAHORA);
	}

	@Override
	public ClzProcedimientoCP getProcedimiento() {
		return new ClzProcedimientoCP(tramite.getProcedimiento());
	}

	@Override
	public String getUrlInformacion() {
		return tramite.getUrlInformacion();
	}

	@Override
	public boolean isVigente() {
		return tramite.isVigente();
	}

}
