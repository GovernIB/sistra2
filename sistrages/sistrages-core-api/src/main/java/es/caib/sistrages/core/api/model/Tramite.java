package es.caib.sistrages.core.api.model;

/**
 *
 * Trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Tramite extends ModelApi {

	/**
	 * Codigo.
	 */
	private Long codigo;

	/**
	 * Identificador.
	 */
	private String identificador;

	/**
	 * Descripcion.
	 */
	private String descripcion;

	/**
	 * Activo
	 */
	private boolean activo;

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
	public void setCodigo(final Long id) {
		this.codigo = id;
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

}
