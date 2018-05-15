package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;

/**
 * Datos almacenados en BBDD para un paso de tramitacion.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosPersistenciaPaso implements Serializable {

	/**
	 * Id paso.
	 */
	private String id;

	/**
	 * Tipo paso.
	 */
	private TypePaso tipo;

	/**
	 * Estado.
	 */
	private TypeEstadoPaso estado;

	/**
	 * Lista de documentos del paso.
	 */
	private List<DocumentoPasoPersistencia> documentos = new ArrayList<>();

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
	 * @param pId
	 *            id a establecer
	 */
	public void setId(final String pId) {
		id = pId;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypePaso getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param pTipo
	 *            tipo a establecer
	 */
	public void setTipo(final TypePaso pTipo) {
		tipo = pTipo;
	}

	/**
	 * Método de acceso a estado.
	 *
	 * @return estado
	 */
	public TypeEstadoPaso getEstado() {
		return estado;
	}

	/**
	 * Método para establecer estado.
	 *
	 * @param pEstado
	 *            estado a establecer
	 */
	public void setEstado(final TypeEstadoPaso pEstado) {
		estado = pEstado;
	}

	/**
	 * Método de acceso a documentos.
	 *
	 * @return documentos
	 */
	public List<DocumentoPasoPersistencia> getDocumentos() {
		return documentos;
	}

	/**
	 * Método para establecer documentos.
	 *
	 * @param pDocumentos
	 *            documentos a establecer
	 */
	public void setDocumentos(final List<DocumentoPasoPersistencia> pDocumentos) {
		documentos = pDocumentos;
	}

	/**
	 * Busca un documento en la lista de documentos.
	 *
	 * @param idDocumento
	 *            Id documento.
	 * @param instancia
	 *            Instancia
	 * @return Documento o nulo si no lo encuentra.
	 */
	public DocumentoPasoPersistencia getDocumentoPasoPersistencia(final String idDocumento, final int instancia) {
		DocumentoPasoPersistencia doc = null;

		for (final DocumentoPasoPersistencia dpp : this.getDocumentos()) {
			if (dpp.getId().equals(idDocumento) && dpp.getInstancia() == instancia) {
				doc = dpp;
				break;
			}
		}
		return doc;
	}

	/**
	 * Busca documentos en la lista de documentos por tipo.
	 *
	 * @param tipoDocumento
	 *            Tipo documento
	 * @return Lista documentos
	 */
	public List<DocumentoPasoPersistencia> getDocumentosPasoPersistencia(
			final TypeDocumentoPersistencia tipoDocumento) {
		final List<DocumentoPasoPersistencia> res = new ArrayList<DocumentoPasoPersistencia>();
		for (final DocumentoPasoPersistencia dpp : this.getDocumentos()) {
			if (dpp.getTipo() == tipoDocumento) {
				res.add(dpp);
				break;
			}
		}
		return res;
	}

	/**
	 * Devuelve numero de instancias de un documento.
	 *
	 * @param idDocumento
	 *            Id documento.
	 * @return Número de instancias de un documento.
	 */
	public int getNumeroInstanciasDocumento(final String idDocumento) {
		int res = 0;
		for (final DocumentoPasoPersistencia dpp : this.getDocumentos()) {
			if (dpp.getId().equals(idDocumento)) {
				res++;
			}
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).append(this.estado).append(this.tipo).append(this.documentos)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean respuesta = false;
		if (obj != null) {
			final DatosPersistenciaPaso otroObj = (DatosPersistenciaPaso) obj;
			respuesta = new EqualsBuilder().append(this.getId(), otroObj.getId())
					.append(this.getTipo(), otroObj.getTipo()).append(this.getEstado(), otroObj.getEstado())
					.append(this.getDocumentos(), otroObj.getDocumentos()).isEquals();
		}
		return respuesta;
	}

}
