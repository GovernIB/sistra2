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
	 * Id.
	 */
	private Long id;

	/**
	 * Codigo.
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
