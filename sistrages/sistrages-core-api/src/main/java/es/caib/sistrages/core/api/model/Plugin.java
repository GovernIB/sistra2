package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
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

public class Plugin extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Id. */
	private Long codigo;

	/** Ámbito (G:Global / E:Entidad) */
	private TypeAmbito ambito;

	/** Tipo. */
	private TypePlugin tipo;

	/** Classname. */
	private String classname;

	/** realClassname. */
	private String realClassname;

	/** mockClassname. */
	private String mockClassname;

	/** Descripción. */
	private String descripcion;

	/** Propiedades */
	private List<Propiedad> propiedades = new ArrayList<>();

	/** Prefijo propiedades **/
	private String prefijoPropiedades;

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
	 * @return the realClassname
	 */
	public String getRealClassname() {
		return realClassname;
	}

	/**
	 * @param realClassname
	 *            the realClassname to set
	 */
	public void setRealClassname(final String realClassname) {
		this.realClassname = realClassname;
	}

	/**
	 * @return the mockClassname
	 */
	public String getMockClassname() {
		return mockClassname;
	}

	/**
	 * @param mockClassname
	 *            the mockClassname to set
	 */
	public void setMockClassname(final String mockClassname) {
		this.mockClassname = mockClassname;
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
	 * Obtiene el valor de prefijoPropiedades.
	 *
	 * @return el valor de prefijo propiedades
	 */
	public String getPrefijoPropiedades() {
		return prefijoPropiedades;
	}

	/**
	 * Establece el valor de prefijo propiedades.
	 *
	 * @param prefijo
	 *            propiedades el nuevo valor de prefijo propiedades
	 */
	public void setPrefijoPropiedades(final String prefijoPropiedades) {
		this.prefijoPropiedades = prefijoPropiedades;
	}


	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "Tasa. ");
           texto.append(tabulacion +"\t Codi:" + getCodigo() + "\n");
           if (descripcion != null) {
        	   texto.append(tabulacion +"\t Descripcio:" + descripcion + "\n");
           }
           texto.append(tabulacion +"\t Ambit:" + ambito + "\n");
           texto.append(tabulacion +"\t Tipus:" + tipo + "\n");
           texto.append(tabulacion +"\t Classname:" + classname + "\n");
           texto.append(tabulacion +"\t PrefijoPropietats:" + prefijoPropiedades + "\n");

           if (propiedades != null) {
        	   texto.append(tabulacion +"\t Propietats: \n");
        	   for(Propiedad propiedad : propiedades) {
        		   texto.append(propiedad.toString(tabulacion+"\t", idioma)+ "\n");
        	   }
           }
           return texto.toString();
     }

}
