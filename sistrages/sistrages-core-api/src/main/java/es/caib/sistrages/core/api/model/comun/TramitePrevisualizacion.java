package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.ModelApi;

/**
 *
 * Tramite previsualizacion para almacenarlo en la sesion y poder reutilizarlo.
 *
 * @author Indra
 *
 */

public final class TramitePrevisualizacion extends ModelApi {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** Procedimiento. **/
	private String procedimiento;

	/** Idioma del tramite. **/
	private String idioma;

	/**
	 * Lista de valores (identificador - valor)
	 */
	private List<Propiedad> parametros = new ArrayList<>();

	/**
	 * @return the procedimiento
	 */
	public String getProcedimiento() {
		return procedimiento;
	}

	/**
	 * @param procedimiento
	 *            the procedimiento to set
	 */
	public void setProcedimiento(final String procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma
	 *            the idioma to set
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the parametros
	 */
	public List<Propiedad> getParametros() {
		return parametros;
	}

	/**
	 * @param parametros
	 *            the parametros to set
	 */
	public void setParametros(final List<Propiedad> parametros) {
		this.parametros = parametros;
	}
}
