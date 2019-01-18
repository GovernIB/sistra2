package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Variables accesibles por los scripts dentro de un formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class VariablesFormulario implements Serializable {

	/**
	 * Sesión de tramitación desde la que se ha lanzado el formulario.
	 */
	private String idSesionTramitacion;
	/**
	 * Metodo autenticación iniciador.
	 */
	private TypeMetodoAutenticacion metodoAutenticacion;
	/**
	 * Nivel autenticacion usuario que rellena el formulario.
	 */
	private TypeAutenticacion nivelAutenticacion;
	/**
	 * Usuario que rellena el formulario (si esta autenticado).
	 */
	private DatosUsuario usuario;
	/**
	 * Parámetros de apertura del formulario.
	 */
	private ParametrosAperturaFormulario parametrosApertura;
	/**
	 * Entorno.
	 */
	private TypeEntorno entorno;
	/**
	 * Idioma.
	 */
	private String idioma;
	/**
	 * Valores del formulario.
	 */
	private List<ValorCampo> valoresCampo;
	/**
	 * Indica si se debe debugear el trámite.
	 */
	private boolean debugEnabled;

	/**
	 * Método para establecer idSesionTramitacion.
	 *
	 * @param pIdSesionTramitacion
	 *            idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
		idSesionTramitacion = pIdSesionTramitacion;
	}

	/**
	 * Método de acceso a parametrosApertura.
	 *
	 * @return parametrosApertura
	 */
	public ParametrosAperturaFormulario getParametrosApertura() {
		return parametrosApertura;
	}

	/**
	 * Método para establecer parametrosApertura.
	 *
	 * @param pParametrosApertura
	 *            parametrosApertura a establecer
	 */
	public void setParametrosApertura(final ParametrosAperturaFormulario pParametrosApertura) {
		parametrosApertura = pParametrosApertura;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método de acceso a idSesionTramitacion.
	 *
	 * @return idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param pIdioma
	 *            idioma a establecer
	 */
	public void setIdioma(final String pIdioma) {
		idioma = pIdioma;
	}

	/**
	 * Método de acceso a nivelAutenticacion.
	 *
	 * @return nivelAutenticacion
	 */
	public TypeAutenticacion getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	/**
	 * Método para establecer nivelAutenticacion.
	 *
	 * @param pNivelAutenticacion
	 *            nivelAutenticacion a establecer
	 */
	public void setNivelAutenticacion(final TypeAutenticacion pNivelAutenticacion) {
		nivelAutenticacion = pNivelAutenticacion;
	}

	/**
	 * Método de acceso a usuario.
	 *
	 * @return usuario
	 */
	public DatosUsuario getUsuario() {
		return usuario;
	}

	/**
	 * Método para establecer usuario.
	 *
	 * @param pUsuario
	 *            usuario a establecer
	 */
	public void setUsuario(final DatosUsuario pUsuario) {
		usuario = pUsuario;
	}

	/**
	 * Método de acceso a valoresCampo.
	 *
	 * @return valoresCampo
	 */
	public List<ValorCampo> getValoresCampo() {
		return valoresCampo;
	}

	/**
	 * Método para establecer valoresCampo.
	 *
	 * @param pValoresCampo
	 *            valoresCampo a establecer
	 */
	public void setValoresCampo(final List<ValorCampo> pValoresCampo) {
		valoresCampo = pValoresCampo;
	}

	/**
	 * Método de acceso a entorno.
	 *
	 * @return entorno
	 */
	public TypeEntorno getEntorno() {
		return entorno;
	}

	/**
	 * Método para establecer entorno.
	 *
	 * @param pEntorno
	 *            entorno a establecer
	 */
	public void setEntorno(final TypeEntorno pEntorno) {
		entorno = pEntorno;
	}

	/**
	 * Método de acceso a debugEnabled.
	 *
	 * @return debugEnabled
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * Método para establecer debugEnabled.
	 *
	 * @param pDebugEnabled
	 *            debugEnabled a establecer
	 */
	public void setDebugEnabled(final boolean pDebugEnabled) {
		debugEnabled = pDebugEnabled;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 * 
	 * @return metodoAutenticacion
	 */
	public TypeMetodoAutenticacion getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 * 
	 * @param metodoAutenticacion
	 *            metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(TypeMetodoAutenticacion metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}

}
