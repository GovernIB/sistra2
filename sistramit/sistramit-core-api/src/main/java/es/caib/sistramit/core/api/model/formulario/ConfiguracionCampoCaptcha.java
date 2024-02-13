package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;

/**
 * Configuración de un campo del formulario de tipo captcha.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoCaptcha extends ConfiguracionCampo {

	/** Opciones particularizadas. */
	private OpcionesCampoCaptcha opciones = new OpcionesCampoCaptcha();

	/**
	 * Constructor.
	 *
	 * @param idCampo
	 *                    id campo
	 */
	public ConfiguracionCampoCaptcha(final String idCampo) {
		super();
		setTipo(TypeCampo.CAPTCHA);
		setId(idCampo);
		setModificable(TypeSiNo.NO);
	}

	/**
	 * Crea instancia ConfiguracionCampoCaptcha.
	 *
	 * @param idCampo
	 *                    Parámetro id campo
	 * @return configuracion campo captcha
	 */
	public static ConfiguracionCampoCaptcha createNewConfiguracionCampoCaptcha(final String idCampo) {
		return new ConfiguracionCampoCaptcha(idCampo);
	}

	/**
	 * Método de acceso a opciones.
	 *
	 * @return opciones
	 */
	public OpcionesCampoCaptcha getOpciones() {
		return opciones;
	}

	/**
	 * Método para establecer opciones.
	 *
	 * @param opciones
	 *                     opciones a establecer
	 */
	public void setOpciones(OpcionesCampoCaptcha opciones) {
		this.opciones = opciones;
	}

}
