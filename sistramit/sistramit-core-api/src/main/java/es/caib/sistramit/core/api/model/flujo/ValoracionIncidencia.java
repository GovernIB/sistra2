package es.caib.sistramit.core.api.model.flujo;

/**
 * Incidencia valoración trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ValoracionIncidencia implements ModelApi {

	/**
	 * Id.
	 */
	private String id;

	/**
	 * Descripción.
	 */
	private String descripcion;

	/**
	 * Método de acceso a id.
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Método para establecer id.
	 *
	 * @param id
	 *            id a establecer
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Método de acceso a descripcion.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método para establecer descripcion.
	 *
	 * @param descripcion
	 *            descripcion a establecer
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

}
