package es.caib.sistrages.core.api.model;

/**
 * Tipo de tramite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TramiteTipo extends ModelApi {

	/** Código tipo certificado */
	private Long codigo;

	/** Identificador tipo de paso */
	private String identificador;

	/** Descripción corta **/
	private Literal descripcion;

	/** Orden, si forman parte del flujo normal */
	private int orden;

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
	 * @return the descripcion
	 */
	public Literal getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

}
