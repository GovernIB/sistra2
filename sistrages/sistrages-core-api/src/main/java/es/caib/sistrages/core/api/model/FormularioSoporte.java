package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeFormularioSoporte;

/**
 *
 * Formulario de soporte.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FormularioSoporte extends ModelApi {

	/** Id. */
	private Long id;

	/** Entidad. */
	private Entidad entidad;

	/** Literal tipo de incidencia. */
	private Traducciones tipoIncidencia;

	/** Literal descripción tipo de incidencia. */
	private Traducciones descripcion;

	/** Tipo destinatario, tipo enum. **/
	private TypeFormularioSoporte destinatario;

	/** Lista emails separados por ; para tipo destinario E */
	private String emails;

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
	 *            the idString to set
	 */
	public void setIdString(final String idString) {
		this.id = Long.valueOf(idString);
	}

	/**
	 * @return the entidad
	 */
	public Entidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(final Entidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the tipoIncidencia
	 */
	public Traducciones getTipoIncidencia() {
		return tipoIncidencia;
	}

	/**
	 * @param tipoIncidencia
	 *            the tipoIncidencia to set
	 */
	public void setTipoIncidencia(final Traducciones tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	/**
	 * @return the descripcion
	 */
	public Traducciones getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final Traducciones descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the emails
	 */
	public String getEmails() {
		return emails;
	}

	/**
	 * @param emails
	 *            the emails to set
	 */
	public void setEmails(final String emails) {
		this.emails = emails;
	}

	/**
	 * @return the destinatario
	 */
	public TypeFormularioSoporte getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario
	 *            the destinatario to set
	 */
	public void setDestinatario(final TypeFormularioSoporte destinatario) {
		this.destinatario = destinatario;
	}

}
