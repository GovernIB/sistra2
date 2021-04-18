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

	/** Alias **/
	private String alias;

	/** Alias **/
	private Integer orden;

	/** Alias **/
	private boolean seleccionable;

	/** Lista de componentes. **/
	private List<DisenyoFormularioComponenteSimple> componentes = new ArrayList<>();

	/** Constructor. **/
	public DisenyoFormularioPaginaSimple() {
		// Por defecto la pagina no es seleccionable
		seleccionable = false;
	}

	/**
	 * @return the componentes
	 */
	public List<DisenyoFormularioComponenteSimple> getComponentes() {
		return componentes;
	}

	/**
	 * @param componentes the componentes to set
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
	 * @param codigo the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(final String alias) {
		this.alias = alias;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(final Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return the seleccionable
	 */
	public boolean isSeleccionable() {
		return seleccionable;
	}

	/**
	 * @param seleccionable the seleccionable to set
	 */
	public void setSeleccionable(final boolean seleccionable) {
		this.seleccionable = seleccionable;
	}

}
