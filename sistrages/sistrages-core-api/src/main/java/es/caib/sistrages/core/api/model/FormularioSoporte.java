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

	/** Literal tipo de incidencia. */
	private Literal tipoIncidencia;

	/** Literal descripción tipo de incidencia. */
	private Literal descripcion;

	/** Tipo destinatario, tipo enum. **/
	private TypeFormularioSoporte tipoDestinatario;

	/** Lista emails separados por ; para tipo destinario E */
	private String listaEmails;

	/**
	 * Crea una nueva instancia de FormularioSoporte.
	 */
	public FormularioSoporte() {
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
	 * @return the tipoIncidencia
	 */
	public Literal getTipoIncidencia() {
		return tipoIncidencia;
	}

	/**
	 * @param tipoIncidencia
	 *            the tipoIncidencia to set
	 */
	public void setTipoIncidencia(final Literal tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
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
	 * @return the emails
	 */
	public String getListaEmails() {
		return listaEmails;
	}

	/**
	 * @param emails
	 *            the emails to set
	 */
	public void setListaEmails(final String emails) {
		this.listaEmails = emails;
	}

	/**
	 * @return the destinatario
	 */
	public TypeFormularioSoporte getTipoDestinatario() {
		return tipoDestinatario;
	}

	/**
	 * @param destinatario
	 *            the destinatario to set
	 */
	public void setTipoDestinatario(final TypeFormularioSoporte destinatario) {
		this.tipoDestinatario = destinatario;
	}

}
