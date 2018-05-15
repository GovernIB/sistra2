package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;

/**
 * Datos de un documento de un paso accesibles desde los demás pasos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class DatosDocumento implements Serializable {
	/**
	 * Id documento.
	 */
	private String id;
	/**
	 * Tipo documento.
	 */
	private TypeDocumento tipo;
	/**
	 * Título.
	 */
	private String titulo;
	/**
	 * Referencia fichero (se indica referencia fichero o contenido fichero).
	 */
	private ReferenciaFichero fichero;

	/**
	 * Contenido fichero (se indica referencia fichero o contenido fichero).
	 */
	private ContenidoFichero contenidoFichero;

	/**
	 * Representa el numero asignado de forma automática por la plataforma según se
	 * dan de alta los pasos de una versión.
	 */
	private String numeroDocumento;

	/**
	 * Método de acceso a id DatosDocumento.
	 *
	 * @return id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Método para establecer id DatosDocumento.
	 *
	 * @param pId
	 *            id a establecer
	 */
	public final void setId(final String pId) {
		id = pId;
	}

	/**
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public final String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param pTitulo
	 *            titulo a establecer
	 */
	public final void setTitulo(final String pTitulo) {
		titulo = pTitulo;
	}

	/**
	 * Método de acceso a datosFichero.
	 *
	 * @return datosFichero
	 */
	public final ReferenciaFichero getFichero() {
		return fichero;
	}

	/**
	 * Método para establecer datosFichero.
	 *
	 * @param pDatosFichero
	 *            datosFichero a establecer
	 */
	public final void setFichero(final ReferenciaFichero pDatosFichero) {
		fichero = pDatosFichero;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public final TypeDocumento getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param pTipo
	 *            tipo a establecer
	 */
	protected final void setTipo(final TypeDocumento pTipo) {
		tipo = pTipo;
	}

	/**
	 * Método para obtener el campo numeroIdentificadorDocumento.
	 *
	 * @return the numeroIdentificadorDocumento
	 */
	public final String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * Método para settear el campo numeroDocumento.
	 *
	 * @param pNumeroDocumento
	 *            el campo numeroIdentificadorDocumento a settear
	 */
	public final void setNumeroDocumento(final String pNumeroDocumento) {
		numeroDocumento = pNumeroDocumento;
	}

	/**
	 * Método de acceso a contenidoFichero.
	 *
	 * @return contenidoFichero
	 */
	public ContenidoFichero getContenidoFichero() {
		return contenidoFichero;
	}

	/**
	 * Método para establecer contenidoFichero.
	 *
	 * @param pContenidoFichero
	 *            contenidoFichero a establecer
	 */
	public void setContenidoFichero(final ContenidoFichero pContenidoFichero) {
		contenidoFichero = pContenidoFichero;
	}

}
