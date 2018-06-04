package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;

/**
 *
 * Información sobre un formulario de paso rellenar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Formulario extends DocumentoFirmado {

	/**
	 * Firmas del formulario. Se generan según el orden de los firmantes.
	 */
	private List<Firma> firmas = new ArrayList<>();  // TODO REPENSAR SI SE PASA FIRMA AL PASO DE REGISTRO

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
	 * @param pFirmas
	 *            firmas a establecer
	 */
	public void setFirmas(final List<Firma> pFirmas) {
		firmas = pFirmas;
	}

	/**
	 * Método para Crea new formulario de la clase Formulario.
	 *
	 * @return el formulario
	 */
	public static Formulario createNewFormulario() {
		return new Formulario();
	}

	/**
	 * Comprueba si es true firmado de Formulario.
	 *
	 * @return true, si es firmado
	 */
	public TypeSiNo getFirmado() {
		TypeSiNo firmado = TypeSiNo.SI;
		if (this.firmas.size() == 0) {
			firmado = TypeSiNo.NO;
		}

		for (final Firma f : this.firmas) {
			if (f.getEstadoFirma() != TypeEstadoFirma.FIRMADO) {
				firmado = TypeSiNo.NO;
				break;
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

}
