package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Plugin.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPlugin", description = "Descripcion de RPlugin")
public class RPlugin {

	/** Tipo. */
	@ApiModelProperty(value = "Tipo")
	private String tipo;

	/** Classname. */
	@ApiModelProperty(value = "Classname")
	private String classname;

	/** Prefijo. */
	@ApiModelProperty(value = "PrefijoPropiedades")
	private String prefijoPropiedades;

	/** Propiedades. */
	@ApiModelProperty(value = "Propiedades")
	private RListaParametros propiedades;

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo
	 *            tipo a establecer
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método de acceso a classname.
	 *
	 * @return classname
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * Método para establecer classname.
	 *
	 * @param classname
	 *            classname a establecer
	 */
	public void setClassname(final String classname) {
		this.classname = classname;
	}

	/**
	 * Método de acceso a propiedades.
	 *
	 * @return propiedades
	 */
	public RListaParametros getPropiedades() {
		return propiedades;
	}

	/**
	 * Método para establecer propiedades.
	 *
	 * @param propiedades
	 *            propiedades a establecer
	 */
	public void setPropiedades(final RListaParametros parametros) {
		this.propiedades = parametros;
	}

	/**
	 * @return the prefijoPropiedades
	 */
	public String getPrefijoPropiedades() {
		return prefijoPropiedades;
	}

	/**
	 * @param prefijoPropiedades
	 *            the prefijoPropiedades to set
	 */
	public void setPrefijoPropiedades(final String prefijo) {
		this.prefijoPropiedades = prefijo;
	}

}
