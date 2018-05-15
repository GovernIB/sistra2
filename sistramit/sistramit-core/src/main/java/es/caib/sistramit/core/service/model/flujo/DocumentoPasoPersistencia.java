package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;

/**
 *
 * Datos de un documento de un paso en persistencia.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DocumentoPasoPersistencia implements Serializable {
	/**
	 * Id documento.
	 */
	private String id;
	/**
	 * Instancia documento (1 para todos excepto para los anexos multiinstancia).
	 */
	private int instancia;
	/**
	 * Tipo documento (formulario, anexo, pago, justificante y variable flujo).
	 */
	private TypeDocumentoPersistencia tipo;
	/**
	 * Estado documento (vacío, rellenado ok, rellenado ko).
	 */
	private TypeEstadoDocumento estado;

	// TODO PENDIENTE DEFINIR PROPIEDADES

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public int getInstancia() {
		return instancia;
	}

	public void setInstancia(final int instancia) {
		this.instancia = instancia;
	}

	public TypeDocumentoPersistencia getTipo() {
		return tipo;
	}

	public void setTipo(final TypeDocumentoPersistencia tipo) {
		this.tipo = tipo;
	}

	public TypeEstadoDocumento getEstado() {
		return estado;
	}

	public void setEstado(final TypeEstadoDocumento estado) {
		this.estado = estado;
	}

}
