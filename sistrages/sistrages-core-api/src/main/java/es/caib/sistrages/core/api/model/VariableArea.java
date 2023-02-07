package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeCache;
import es.caib.sistrages.core.api.model.types.TypeDominio;

/**
 * Dominio.
 *
 * @author Indra
 *
 */

public class VariableArea extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Id. */
	private Long codigo;

	/** Identificador. **/
	private String identificador;

	/** Descripcion. */
	private String descripcion;

	/**
	 * Url variable de área
	 */
	private String url;

	/** Areas. **/
	private Area area;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public VariableArea() {
		super();
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
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the areas
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setArea(final Area areas) {
		this.area = areas;
	}

}
