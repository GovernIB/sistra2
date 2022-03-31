package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeConsultaGeneral;

/**
 * La clase ConsultaGeneral.
 */

public class ConsultaGeneral extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Tipo **/
	private TypeConsultaGeneral tipo;

	/** Subtipo **/
	private String subtipo;

	/** Ambito **/
	private TypeAmbito ambito;

	/** Identificador. */
	private String identificador;

	/** Descripcion. */
	private String descripcion;

	/** Identificador del area. */
	private String area;

	/** ID Area **/
	private Long idArea;

	/**
	 * Crea una nueva instancia de FormateadorFormulario.
	 */
	public ConsultaGeneral() {
		super();
	}

	/**
	 * Crea una nueva instancia de FormateadorFormulario.
	 *
	 * @param pCodigo
	 *            the codigo
	 */
	public ConsultaGeneral(final Long pCodigo) {
		super();
		this.codigo = pCodigo;
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
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the tipo
	 */
	public TypeConsultaGeneral getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TypeConsultaGeneral tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the subtipo
	 */
	public String getSubtipo() {
		return subtipo;
	}

	/**
	 * @param subtipo the subtipo to set
	 */
	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
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
	public void setAmbito(TypeAmbito ambito) {
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
	public void setIdentificador(String identificador) {
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
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the idArea
	 */
	public Long getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

}
