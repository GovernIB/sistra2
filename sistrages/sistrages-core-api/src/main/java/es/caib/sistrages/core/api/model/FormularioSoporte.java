package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeFormularioSoporte;

/**
 * La clase FormularioSoporte.
 */

public class FormularioSoporte extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * codigo.
	 */
	private Long codigo;

	/**
	 * tipo de incidencia.
	 */
	private Literal tipoIncidencia;

	/**
	 * descripcion.
	 */
	private Literal descripcion;

	/**
	 * tipo destinatario.
	 */
	private List<TypeFormularioSoporte> tipoDestinatario;

	/**
	 * lista de emails.
	 */
	private String listaEmails;

	/**
	 * Crea una nueva instancia de FormularioSoporte.
	 */
	public FormularioSoporte() {
		super();
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de tipoIncidencia.
	 *
	 * @return el valor de tipoIncidencia
	 */
	public Literal getTipoIncidencia() {
		return tipoIncidencia;
	}

	/**
	 * Establece el valor de tipoIncidencia.
	 *
	 * @param tipoIncidencia el nuevo valor de tipoIncidencia
	 */
	public void setTipoIncidencia(final Literal tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public Literal getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion el nuevo valor de descripcion
	 */
	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de listaEmails.
	 *
	 * @return el valor de listaEmails
	 */
	public String getListaEmails() {
		return listaEmails;
	}

	/**
	 * Establece el valor de listaEmails.
	 *
	 * @param emails el nuevo valor de listaEmails
	 */
	public void setListaEmails(final String emails) {
		this.listaEmails = emails;
	}

	/**
	 * Obtiene el valor de tipoDestinatario.
	 *
	 * @return el valor de tipoDestinatario
	 */
	public List<TypeFormularioSoporte> getTipoDestinatario() {
		return tipoDestinatario;
	}

	/**
	 * Establece el valor de tipoDestinatario.
	 *
	 * @param destinatario el nuevo valor de tipoDestinatario
	 */
	public void setTipoDestinatario(final List<TypeFormularioSoporte> destinatario) {
		this.tipoDestinatario = destinatario;
	}

}
