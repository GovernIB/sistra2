package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedadFirmante;

/**
 *
 * Firmante.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Firmante extends Persona {

	/** Indica si firmante obligatorio. */
	private TypeObligatoriedadFirmante obligatorio = TypeObligatoriedadFirmante.OBLIGATORIO;

	/**
	 * Constructor.
	 */
	public Firmante() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param pNif
	 *                    Nif
	 * @param pNombre
	 *                    Nombre completo
	 */
	public Firmante(final String pNif, final String pNombre, final TypeObligatoriedadFirmante pObligatorio) {
		super(pNif, pNombre);
		obligatorio = pObligatorio;
	}

	/**
	 * Método para Crea new Firmante de la clase Firmante.
	 *
	 * @return el Firmante
	 */
	public static Firmante createNewFirmante() {
		return new Firmante();
	}

	/**
	 * Método de acceso a obligatorio.
	 *
	 * @return obligatorio
	 */
	public TypeObligatoriedadFirmante getObligatorio() {
		return obligatorio;
	}

	/**
	 * Método para establecer obligatorio.
	 *
	 * @param obligatorio
	 *                        obligatorio a establecer
	 */
	public void setObligatorio(final TypeObligatoriedadFirmante obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * Método para mostrar el contenido de la clase Persona.
	 *
	 * @return el string
	 */
	@Override
	public final String print() {
		return "Firmante [nif=" + this.getNif() + ", nombre=" + this.getNombre() + ", obligatorio = " + obligatorio
				+ "]";
	}

}
