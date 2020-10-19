package es.caib.sistrages.core.api.model;

/**
 *
 * Gestor externo formularios.
 *
 * @author Indra
 *
 */
public class GestorExternoFormularios extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Id. */
	private Long codigo;

	/** Identificador **/
	private String identificador;

	/** Descripcion **/
	private String descripcion;

	/** URL **/
	private String url;

	public GestorExternoFormularios() {
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
	 *                   the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the identificador
	 */
	public final String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *                          the identificador to set
	 */
	public final void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public final String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *                        the descripcion to set
	 */
	public final void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *                the url to set
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GestorExternoFormularios)) {
			return false;
		}
		final GestorExternoFormularios other = (GestorExternoFormularios) obj;
		if (identificador == null) {
			if (other.identificador != null) {
				return false;
			}
		} else if (!identificador.equals(other.identificador)) {
			return false;
		}
		if (descripcion == null) {
			if (other.descripcion != null) {
				return false;
			}
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}
}
