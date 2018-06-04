package es.caib.sistramit.core.api.model.flujo;

/**
 * Establece datos de un firmante: nif y nombre y estado de la firma.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Firmante extends Persona {

	/**
	 * Constructor.
	 */
	public Firmante() {
		super();
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param pNif
	 *            Nif
	 * @param pNombre
	 *            Nombre
	 */
	public Firmante(final String pNif, final String pNombre) {
		super(pNif, pNombre);
	}

	/**
	 * Crea nueva instancia.
	 *
	 * @return Firmante
	 */
	public static Firmante createNewFirmante() {
		return new Firmante();
	}

	/**
	 * Crea nueva instancia.
	 *
	 * @param pNif
	 *            Nif
	 * @param pNombre
	 *            Nombre
	 * @param pTipoFirma
	 *            Tipo firma
	 * @return Firmante
	 */
	public static Firmante createNewFirmante(final String pNif, final String pNombre) {
		return new Firmante(pNif, pNombre);
	}

}
