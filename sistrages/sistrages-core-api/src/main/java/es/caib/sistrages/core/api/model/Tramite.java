package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;

/**
 *
 * Trámite.
 *
 * @author Indra
 *
 */

public class Tramite extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Identificador. */
	private String identificador;

	/** Identificador area. **/
	private String identificadorArea;

	/** Identificador entidad. **/
	private String identificadorEntidad;

	/** Descripcion. */
	private String descripcion;

	/** Activo */
	private boolean activo;

	/** Id area. */
	private Long idArea;

	/** Id entidad. */
	private Long idEntidad;

	/**
	 * Crea una nueva instancia de Tramite.
	 */
	public Tramite() {
		super();
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
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(final boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the idArea
	 */
	public Long getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(final Long idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the idEntidad
	 */
	public Long getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param idEntidad
	 *            the idEntidad to set
	 */
	public void setIdEntidad(final Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	/**
	 * @return the identificadorArea
	 */
	public String getIdentificadorArea() {
		return identificadorArea;
	}

	/**
	 * @param identificadorArea the identificadorArea to set
	 */
	public void setIdentificadorArea(String identificadorArea) {
		this.identificadorArea = identificadorArea;
	}

	/**
	 * @return the identificadorEntidad
	 */
	public String getIdentificadorEntidad() {
		return identificadorEntidad;
	}

	/**
	 * @param identificadorEntidad the identificadorEntidad to set
	 */
	public void setIdentificadorEntidad(String identificadorEntidad) {
		this.identificadorEntidad = identificadorEntidad;
	}

	/** Obtener el identificador compuesto **/
	public String getIdentificadorCompuesto() {
		return this.getIdentificadorEntidad()
		+ ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + this.getIdentificadorArea()
		+ ValorIdentificadorCompuesto.SEPARACION_IDENTIFICADOR_COMPUESTO + this.getIdentificador();
	}
}
