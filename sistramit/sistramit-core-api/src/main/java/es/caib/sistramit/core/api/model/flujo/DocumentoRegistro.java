package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;

/**
 * Documento mostrado en el paso de registro.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DocumentoRegistro implements ModelApi {

	/**
	 * Identificador documento
	 */
	private String id;

	/**
	 * Instancia documento (para anexos genéricos).
	 */
	private int instancia = 1;

	/**
	 * Titulo documento.
	 */
	private String titulo;

	/**
	 * Presentación.
	 */
	private TypePresentacion presentacion; // TODO REVISAR SEGUN NUEVOS REQUISITOS

	/**
	 * Indica si se permite descargar el documento desde la lista.
	 */
	private TypeSiNo descargable = TypeSiNo.NO;

	/**
	 * Indica si se debe firmar.
	 */
	private TypeSiNo firmar = TypeSiNo.NO;

	/** Información de firmas. */
	private List<Firma> firmas = new ArrayList<>();

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
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param pTitulo
	 *            titulo a establecer
	 */
	public void setTitulo(final String pTitulo) {
		titulo = pTitulo;
	}

	/**
	 * Método de acceso a download.
	 *
	 * @return download
	 */
	public TypeSiNo getDescargable() {
		return descargable;
	}

	/**
	 * Método para establecer download.
	 *
	 * @param pDownload
	 *            download a establecer
	 */
	public void setDescargable(final TypeSiNo pDownload) {
		descargable = pDownload;
	}

	/**
	 * Método para Crea new documento registro de la clase DocumentoRegistro.
	 *
	 * @return el documento registro
	 */
	public static DocumentoRegistro createNewDocumentoRegistro() {
		return new DocumentoRegistro();
	}

	/**
	 * Método de acceso a firmar.
	 *
	 * @return firmar
	 */
	public TypeSiNo getFirmar() {
		return firmar;
	}

	/**
	 * Método para establecer firmar.
	 *
	 * @param firmar
	 *            firmar a establecer
	 */
	public void setFirmar(TypeSiNo firmar) {
		this.firmar = firmar;
	}

	/**
	 * Método de acceso a firmas.
	 *
	 * @return firmas
	 */
	public List<Firma> getFirmas() {
		return firmas;
	}

	/**
	 * Método para establecer firmas.
	 *
	 * @param firmas
	 *            firmas a establecer
	 */
	public void setFirmas(List<Firma> firmas) {
		this.firmas = firmas;
	}

	/**
	 * Comprueba si esta firmado.
	 *
	 * @return Si es firmado
	 */
	public TypeSiNo getFirmado() {
		TypeSiNo firmado = TypeSiNo.SI;
		if (this.firmas.isEmpty()) {
			firmado = TypeSiNo.NO;
		} else {
			for (final Firma f : this.firmas) {
				if (f.getEstadoFirma() != TypeEstadoFirma.FIRMADO) {
					firmado = TypeSiNo.NO;
					break;
				}
			}
		}
		return firmado;
	}

	/**
	 * Comprueba si el formulario ha sido firmado por el firmante.
	 *
	 * @param indiceFirmante
	 *            Parámetro indice firmante
	 * @return True si ha sido firmado por todos los firmantes.
	 */
	public TypeSiNo getFirmado(final int indiceFirmante) {
		TypeSiNo firmado = TypeSiNo.SI;
		if (this.firmas.size() == 0) {
			firmado = TypeSiNo.NO;
		} else {
			final Firma f = this.firmas.get(indiceFirmante);
			if (f.getEstadoFirma() != TypeEstadoFirma.FIRMADO) {
				firmado = TypeSiNo.NO;
			}
		}
		return firmado;
	}

	/**
	 * Método de acceso a instancia.
	 *
	 * @return instancia
	 */
	public int getInstancia() {
		return instancia;
	}

	/**
	 * Método para establecer instancia.
	 *
	 * @param instancia
	 *            instancia a establecer
	 */
	public void setInstancia(int instancia) {
		this.instancia = instancia;
	}

	/**
	 * Método de acceso a presentacion.
	 *
	 * @return presentacion
	 */
	public TypePresentacion getPresentacion() {
		return presentacion;
	}

	/**
	 * Método para establecer presentacion.
	 *
	 * @param presentacion
	 *            presentacion a establecer
	 */
	public void setPresentacion(TypePresentacion presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * Devuelve estado firma del firmante.
	 *
	 * @param nifFirmante
	 *            Parámetro nif firmante
	 * @return Estado firma
	 */
	public Firma getFirma(final String nifFirmante) {
		Firma res = null;
		for (final Firma f : firmas) {
			if (f.getFirmante().getNif().equals(nifFirmante)) {
				res = f;
				break;
			}
		}
		return res;
	}

}
