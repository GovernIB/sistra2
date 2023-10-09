package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

/**
 * ProcedimientoDocumento.
 *
 * @author Indra
 *
 */
public class RProcedimientoDocumento {

    /**
     * Orden
     */
    private Integer orden;

    /*Título del documento*/
    private String titulo;

    /*Descripción del documento*/
    private String descripcion;

	/** codigo **/
	private Long codigo;

	/** Fichero asociado al documento **/
	private RLink link_fichero;

	private Long fichero;

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	 * @return the link_fichero
	 */
	public RLink getLink_fichero() {
		return link_fichero;
	}

	/**
	 * @param link_fichero the link_fichero to set
	 */
	public void setLink_fichero(RLink link_fichero) {
		this.link_fichero = link_fichero;
	}

	/**
	 * @return the fichero
	 */
	public Long getFichero() {
		return fichero;
	}

	/**
	 * @param fichero the fichero to set
	 */
	public void setFichero(Long fichero) {
		this.fichero = fichero;
	}

}
