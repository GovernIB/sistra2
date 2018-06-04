package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;

/**
 * Indica el estado de una firma.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Firma implements Serializable {

	/**
	 * Indica si ha firmado.
	 */
	private TypeEstadoFirma estadoFirma = TypeEstadoFirma.NO_FIRMADO;

	/**
	 * En caso de que se haya firmado o rechazado indica la fecha (dd/mm/aaaa
	 * hh:mm).
	 */
	private String fechaFirma;

	/**
	 * En caso de que se haya rechazado la firma indica el motivo de rechazo.
	 */
	private String rechazo;

	/**
	 * Constructor.
	 */
	public Firma() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param pEstadoFirma
	 *            Parámetro estado firma
	 * @param pFechaFirma
	 *            Parámetro fecha firma
	 * @param pRechazo
	 *            Parámetro rechazo
	 */
	public Firma(final TypeEstadoFirma pEstadoFirma, final String pFechaFirma, final String pRechazo) {
		super();
		estadoFirma = pEstadoFirma;
		fechaFirma = pFechaFirma;
		rechazo = pRechazo;
	}

	/**
	 * Crea instancia Firma.
	 *
	 * @return Firma
	 */
	public static Firma createNewFirma() {
		return new Firma();
	}

	/**
	 * Método de acceso a estadoFirma.
	 *
	 * @return estadoFirma
	 */
	public TypeEstadoFirma getEstadoFirma() {
		return estadoFirma;
	}

	/**
	 * Método para establecer estadoFirma.
	 *
	 * @param pEstadoFirma
	 *            estadoFirma a establecer
	 */
	public void setEstadoFirma(final TypeEstadoFirma pEstadoFirma) {
		estadoFirma = pEstadoFirma;
	}

	/**
	 * Método de acceso a fechaFirma.
	 *
	 * @return fechaFirma
	 */
	public String getFechaFirma() {
		return fechaFirma;
	}

	/**
	 * Método para establecer fechaFirma.
	 *
	 * @param pFechaFirma
	 *            fechaFirma a establecer
	 */
	public void setFechaFirma(final String pFechaFirma) {
		fechaFirma = pFechaFirma;
	}

	/**
	 * Método de acceso a rechazo.
	 *
	 * @return rechazo
	 */
	public String getRechazo() {
		return rechazo;
	}

	/**
	 * Método para establecer rechazo.
	 *
	 * @param pRechazo
	 *            rechazo a establecer
	 */
	public void setRechazo(final String pRechazo) {
		rechazo = pRechazo;
	}

}
