package es.caib.sistra2.stg.core.api.model;

/**
 *
 * Plugin.
 *
 * @author Indra
 *
 */
public class Plugin {

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
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the classname
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * @param classname the classname to set
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}
}
