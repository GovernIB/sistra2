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

}
