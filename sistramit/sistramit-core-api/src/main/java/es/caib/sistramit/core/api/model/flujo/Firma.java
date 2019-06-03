package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;
import es.caib.sistramit.core.api.model.flujo.types.TypeFirmaDigital;

/**
 * Indica el estado de una firma.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Firma implements ModelApi {

	/**
	 * Indica firmante.
	 */
	private Persona firmante;

	/**
	 * Indica si se permite descargar la firma desde la lista.
	 */
	private TypeSiNo descargable = TypeSiNo.NO;

	/**
	 * Indica si ha firmado.
	 */
	private TypeEstadoFirma estadoFirma = TypeEstadoFirma.NO_FIRMADO;

	/**
	 * En caso de que se haya firmado indica la fecha (dd/mm/aaaa hh:mm).
	 */
	private String fechaFirma;

	/**
	 * Tipo de firma.
	 */
	private TypeFirmaDigital tipoFirma;

	/**
	 * Constructor.
	 */
	public Firma() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param pFirmante
	 *            firmante
	 * @param pEstadoFirma
	 *            Parámetro estado firma
	 * @param pFechaFirma
	 *            Parámetro fecha firma
	 */
	public Firma(final Persona pFirmante, final TypeEstadoFirma pEstadoFirma, final String pFechaFirma) {
		super();
		firmante = pFirmante;
		estadoFirma = pEstadoFirma;
		fechaFirma = pFechaFirma;
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
	 * Método de acceso a firmante.
	 *
	 * @return firmante
	 */
	public Persona getFirmante() {
		return firmante;
	}

	/**
	 * Método para establecer firmante.
	 *
	 * @param firmante
	 *            firmante a establecer
	 */
	public void setFirmante(final Persona firmante) {
		this.firmante = firmante;
	}

	/**
	 * Método de acceso a descargable.
	 *
	 * @return descargable
	 */
	public TypeSiNo getDescargable() {
		return descargable;
	}

	/**
	 * Método para establecer descargable.
	 *
	 * @param descargable
	 *            descargable a establecer
	 */
	public void setDescargable(final TypeSiNo descargable) {
		this.descargable = descargable;
	}

	/**
	 * Método de acceso a tipoFirma.
	 * 
	 * @return tipoFirma
	 */
	public TypeFirmaDigital getTipoFirma() {
		return tipoFirma;
	}

	/**
	 * Método para establecer tipoFirma.
	 * 
	 * @param tipoFirma
	 *            tipoFirma a establecer
	 */
	public void setTipoFirma(final TypeFirmaDigital tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

}
