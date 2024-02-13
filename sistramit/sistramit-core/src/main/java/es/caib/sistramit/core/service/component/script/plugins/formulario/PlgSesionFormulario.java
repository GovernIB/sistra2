package es.caib.sistramit.core.service.component.script.plugins.formulario;

import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.script.plugins.ClzDatosUsuario;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.script.ClzDatosUsuarioInt;
import es.caib.sistramit.core.service.model.script.formulario.PlgSesionFormularioInt;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Plugin de acceso a los datos de la sesión de tramitación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgSesionFormulario implements PlgSesionFormularioInt {

	/**
	 * Variables formulario.
	 */
	private final VariablesFormulario variablesFormulario;

	/**
	 * Constructor.
	 *
	 * @param pVariablesFormulario
	 *            Variables formulario
	 */
	public PlgSesionFormulario(final VariablesFormulario pVariablesFormulario) {
		variablesFormulario = pVariablesFormulario;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public String getIdSesionTramitacion() {
		return variablesFormulario.getIdSesionTramitacion();
	}

	@Override
	public String getIdTramite() {
		return variablesFormulario.getIdTramite();
	}

	@Override
	public int getVersionTramite() {
		return variablesFormulario.getVersionTramite();
	}

	@Override
	public String getIdioma() {
		return variablesFormulario.getIdioma();
	}

	@Override
	public boolean isAutenticado() {
		return (variablesFormulario.getNivelAutenticacion() == TypeAutenticacion.AUTENTICADO);
	}

	@Override
	public String getMetodoAutenticacion() {
		String res = null;
		if (variablesFormulario.getMetodoAutenticacion() != null) {
			res = variablesFormulario.getMetodoAutenticacion().toString();
		}
		return res;
	}

	@Override
	public ClzDatosUsuarioInt getUsuario() {
		return new ClzDatosUsuario(variablesFormulario.getUsuario());
	}

	@Override
	public ClzDatosUsuarioInt getRepresentante() {
		return new ClzDatosUsuario(UtilsFlujo.getDatosRepresentante(variablesFormulario.getUsuarioAutenticado()));
	}

	@Override
	public String getParametro(final String idParametro) {
		return variablesFormulario.getParametrosApertura().getParametro(idParametro);
	}

}
