package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase disenyo formulario pagina simple.
 *
 * @author Indra
 *
 */
public class DisenyoFormularioPaginaSimple {

	/** Identificador **/
	private Long codigo;

	/** Lista de componentes. **/
	private List<DisenyoFormularioComponenteSimple> componentes = new ArrayList<>();

	/** Constructor. **/
	public DisenyoFormularioPaginaSimple() {
		// Constructor vacio
	}

	/**
	 * @return the componentes
	 */
	public List<DisenyoFormularioComponenteSimple> getComponentes() {
		return componentes;
	}

	/**
	 * @param componentes
	 *            the componentes to set
	 */
	public void setComponentes(final List<DisenyoFormularioComponenteSimple> componentes) {
		this.componentes = componentes;
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

}
