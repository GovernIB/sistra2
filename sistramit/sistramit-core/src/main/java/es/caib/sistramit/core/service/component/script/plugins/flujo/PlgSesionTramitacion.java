package es.caib.sistramit.core.service.component.script.plugins.flujo;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.script.plugins.ClzDatosUsuario;
import es.caib.sistramit.core.service.component.script.plugins.ClzTramiteCP;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.script.ClzDatosUsuarioInt;
import es.caib.sistramit.core.service.model.script.ClzTramiteCPInt;
import es.caib.sistramit.core.service.model.script.flujo.PlgSesionTramitacionInt;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Plugin de acceso a los datos de la sesión de tramitación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgSesionTramitacion implements PlgSesionTramitacionInt {

	/**
	 * Almacenamos variables flujo para permitir acceso a las propiedades generales
	 * del trámite.
	 */
	private final VariablesFlujo variablesFlujo;

	/**
	 * Constructor.
	 *
	 * @param pVariablesFlujo
	 *                            Variables del flujo
	 */
	public PlgSesionTramitacion(final VariablesFlujo pVariablesFlujo) {
		super();
		this.variablesFlujo = pVariablesFlujo;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public String getIdSesionTramitacion() {
		return variablesFlujo.getIdSesionTramitacion();
	}

	@Override
	public boolean isAutenticado() {
		return (variablesFlujo.getNivelAutenticacion() == TypeAutenticacion.AUTENTICADO);
	}

	@Override
	public String getMetodoAutenticacion() {
		String res = null;
		if (variablesFlujo.getMetodoAutenticacion() != null) {
			res = variablesFlujo.getMetodoAutenticacion().toString();
		}
		return res;
	}

	@Override
	public ClzDatosUsuarioInt getUsuario() {
		return new ClzDatosUsuario(variablesFlujo.getUsuario());
	}

	@Override
	public ClzDatosUsuarioInt getRepresentante() {
		return new ClzDatosUsuario(UtilsFlujo.getDatosRepresentante(variablesFlujo.getUsuarioAutenticado()));
	}

	@Override
	public String getParametroInicio(final String parametro) {
		String valor = "";
		if (StringUtils.isNotEmpty(parametro) && variablesFlujo.getParametrosInicio() != null
				&& variablesFlujo.getParametrosInicio().containsKey(parametro)) {
			valor = variablesFlujo.getParametrosInicio().get(parametro);
		}
		return valor;
	}

	@Override
	public String getIdioma() {
		return variablesFlujo.getIdioma();
	}

	@Override
	public String getUrlInicioTramite() {
		return this.variablesFlujo.getUrlInicioTramite();
	}

	@Override
	public ClzTramiteCPInt getTramiteCP() {
		return new ClzTramiteCP(this.variablesFlujo.getDatosTramiteCP());
	}

	@Override
	public String getEntorno() {
		return variablesFlujo.getEntorno().toString();
	}

}
