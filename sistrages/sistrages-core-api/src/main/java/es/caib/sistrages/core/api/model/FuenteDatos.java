package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 *
 * Fuente Datos.
 *
 * @author Indra
 *
 */

public class FuenteDatos extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;
	/** Id. */
	private Long codigo;

	/** Ambito. **/
	private TypeAmbito ambito;

	/** Codigo. */
	private String identificador;

	/** Descripcion. */
	private String descripcion;

	/** Area. **/
	private Area area;

	/** Entidad. **/
	private Entidad entidad;

	/** Campos. **/
	private List<FuenteDatosCampo> campos = new ArrayList<>();

	/**
	 * Get idString.
	 *
	 * @return
	 */
	public String getIdString() {
		if (codigo == null) {
			return null;
		} else {
			return codigo.toString();
		}
	}

	/**
	 * Set idstring.
	 *
	 * @param idString
	 */
	public void setIdString(final String idString) {
		if (idString == null) {
			this.codigo = null;
		} else {
			this.codigo = Long.valueOf(idString);
		}
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
	 * @return the ambito
	 */
	public TypeAmbito getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(final TypeAmbito ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the campos
	 */
	public List<FuenteDatosCampo> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(final List<FuenteDatosCampo> campos) {
		this.campos = campos;
	}

	/**
	 * @return the area
	 */
	public final Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public final void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * @return the entidad
	 */
	public final Entidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public final void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * Metodo para obtener el Campo de fuente de datos segun una id.
	 *
	 * @param id
	 * @return
	 */
	public FuenteDatosCampo getCampoById(final Long id) {
		FuenteDatosCampo campo = null;
		for (final FuenteDatosCampo campoFD : this.campos) {
			if (campoFD.getCodigo() != null && campoFD.getCodigo().compareTo(id) == 0) {
				campo = campoFD;
				break;
			}
		}

		return campo;
	}

	/**
	 * Añade un campo.
	 *
	 * @param fuenteDatosCampo
	 */
	public void addCampo(final FuenteDatosCampo fuenteDatosCampo) {
		this.campos.add(fuenteDatosCampo);
	}

	/**
	 * Borra un campo.
	 *
	 * @param fuenteDatosCampo
	 */
	public void removeCampo(final FuenteDatosCampo fuenteDatosCampo) {
		if (this.campos.contains(fuenteDatosCampo)) {
			this.campos.remove(fuenteDatosCampo);
		}
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
           StringBuilder texto = new StringBuilder(tabulacion + "FontDades. ");
           texto.append(tabulacion +"\t Codi:" + codigo + "\n");
           texto.append(tabulacion +"\t Identificador:" + identificador + "\n");
           texto.append(tabulacion +"\t Descripcio:" + descripcion + "\n");
           texto.append(tabulacion +"\t Ambito:" + ambito + "\n");
           if (area != null) {
        	   texto.append(tabulacion +"\t Area: \n");
        	   texto.append(area.toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (entidad != null) {
        	   texto.append(entidad.toString(tabulacion+"\t", idioma)+ "\n");
           }
            if (campos != null) {
        	   texto.append(tabulacion +"\t Campos: \n");
        	   for(FuenteDatosCampo campo : campos) {
        		   texto.append(campo.toString(tabulacion+"\t", idioma)+ "\n");
        	   }
           }
           return texto.toString();
     }

}
