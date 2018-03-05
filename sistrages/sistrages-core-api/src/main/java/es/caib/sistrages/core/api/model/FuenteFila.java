package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Fila de una fuente de datos.
 *
 * @author Indra
 *
 */
public class FuenteFila {

	/** Id. **/
	private Long id;

	/** Lista de fuentes de datos. **/
	private List<FuenteDato> datos;

	/** Constructor basico. **/
	public FuenteFila() {
		datos = new ArrayList<>();
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
	 * @return the idString
	 */
	public String getIdString() {
		if (id == null) {
			return null;
		} else {
			return id.toString();
		}
	}

	/**
	 * @param idString
	 *            the id to set
	 */
	public void setIdString(final String idString) {
		if (id == null) {
			this.id = null;
		} else {
			this.id = Long.valueOf(idString);
		}
	}

	/**
	 * @return the datos
	 */
	public List<FuenteDato> getDatos() {
		return datos;
	}

	/**
	 * @param datos
	 *            the datos to set
	 */
	public void setDatos(final List<FuenteDato> datos) {
		this.datos = datos;
	}

	/**
	 * Agrega una fuente de datos.
	 *
	 * @param dato11
	 */
	public void addFuenteDato(final FuenteDato dato11) {
		this.datos.add(dato11);
	}

	/**
	 * Borra una fuente de datos.
	 *
	 * @param dato11
	 */
	public void removeFuenteDato(final FuenteDato dato11) {
		this.datos.remove(dato11);
	}
}
