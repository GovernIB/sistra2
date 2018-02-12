package es.caib.sistrages.core.api.model;

/**
 *
 * Plugin entidad.
 *
 * @author Indra
 *
 */
public class PluginEntidad {

	/**
	 * Codigo.
	 */
	private Long codigo;

	/**
	 * Tipo.
	 */
	private String tipo;

	/**
	 * Classname.
	 */
	private String classname;

	/**
	 * Instancia.
	 */
	private String instancia;

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

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final String tipo) {
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
