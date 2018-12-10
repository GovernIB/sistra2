package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;

/**
 * Documentos mostrado en el paso de registro de un determinado tipo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DocumentosRegistroPorTipo implements ModelApi {

	/**
	 * Tipo documento.
	 */
	private TypeDocumento tipo;

	/**
	 * Listado de documentos del tipo.
	 */
	private List<DocumentoRegistro> listado = new ArrayList<>();

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypeDocumento getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param pTipo
	 *            tipo a establecer
	 */
	public void setTipo(final TypeDocumento pTipo) {
		tipo = pTipo;
	}

	/**
	 * Método de acceso a listado.
	 *
	 * @return listado
	 */
	public List<DocumentoRegistro> getListado() {
		return listado;
	}

	/**
	 * Método para establecer listado.
	 *
	 * @param pListado
	 *            listado a establecer
	 */
	public void setListado(final List<DocumentoRegistro> pListado) {
		listado = pListado;
	}

	/**
	 * Método para Crea new documentos registro por tipo de la clase
	 * DocumentosRegistroPorTipo.
	 *
	 * @return el documentos registro por tipo
	 */
	public static DocumentosRegistroPorTipo createNewDocumentosRegistroPorTipo() {
		return new DocumentosRegistroPorTipo();
	}

}
