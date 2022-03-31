package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Documento que puede ser firmado (formulario o anexo).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class DocumentoFirmado extends Documento {

	/**
	 * Indica si se debe firmar (si/no).
	 */
	private TypeSiNo firmar = TypeSiNo.NO;

	/**
	 * Indica lista de firmantes del documento.
	 */
	private List<Firmante> firmantes = new ArrayList<>();

	/**
	 * Método de acceso a firmar.
	 *
	 * @return firmar
	 */
	public final TypeSiNo getFirmar() {
		return firmar;
	}

	/**
	 * Método para establecer firmar.
	 *
	 * @param pFirmar
	 *                    firmar a establecer
	 */
	public final void setFirmar(final TypeSiNo pFirmar) {
		firmar = pFirmar;
	}

	/**
	 * Método de acceso a firmantes.
	 *
	 * @return firmantes
	 */
	public final List<Firmante> getFirmantes() {
		return firmantes;
	}

	/**
	 * Método para establecer firmantes.
	 *
	 * @param pFirmantes
	 *                       firmantes a establecer
	 */
	public final void setFirmantes(final List<Firmante> pFirmantes) {
		firmantes = pFirmantes;
	}

	/**
	 * Indica si un nif debe firmar el documento.
	 *
	 * @param nif
	 *                Nif
	 * @return Devuelve el orden que ocupa el firmante en caso de que el nif deba
	 *         firmar el documento (empieza en 0). Devuelve -1 si no tiene que
	 *         firmarlo.
	 */
	public final int esFirmante(final String nif) {
		boolean enc = false;
		int res = 0;
		for (final Persona f : this.getFirmantes()) {
			if (f.getNif().equals(nif)) {
				enc = true;
				break;
			}
			res++;
		}
		if (!enc) {
			res = ConstantesNumero.N_1;
		}
		return res;
	}

}
