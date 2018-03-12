package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;

/**
 *
 * Plugin.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Plugin extends ModelApi {

	/** Id. */
	private Long id;

	/** Ámbito (G:Global / E:Entidad) */
	private TypeAmbito ambito;

	/** Tipo. */
	private TypePlugin tipo;

	/** Classname. */
	private String classname;

	/** Instancia. */
	private String instancia;

	/** Descripción. */
	private String descripcion;

	/** Propiedades */
	private List<Propiedad> propiedades;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the ambito
	 */
	public TypeAmbito getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito
	 *            the ambito to set
	 */
	public void setAmbito(final TypeAmbito ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the tipo
	 */
	public TypePlugin getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypePlugin tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the classname
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * @param classname
	 *            the classname to set
	 */
	public void setClassname(final String classname) {
		this.classname = classname;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the propiedades
	 */
	public List<Propiedad> getPropiedades() {
		return propiedades;
	}

	/**
	 * @param propiedades
	 *            the propiedades to set
	 */
	public void setPropiedades(final List<Propiedad> propiedades) {
		this.propiedades = propiedades;
	}

	/**
	 * @return the instancia
	 */
	public String getInstancia() {
		return instancia;
	}

	/**
	 * @param instancia
	 *            the instancia to set
	 */
	public void setInstancia(final String instancia) {
		this.instancia = instancia;
	}
}
