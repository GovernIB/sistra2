package es.caib.sistramit.core.service.component.script.plugins.formulario;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.service.component.script.plugins.ClzValorCampoCompuesto;
import es.caib.sistramit.core.service.model.script.formulario.ResValoresPosiblesInt;

/**
 *
 * Valores posibles de un selector.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResValoresPosibles implements ResValoresPosiblesInt {

	/**
	 * Valores posibles.
	 */
	private final List<ClzValorCampoCompuesto> valoresPosibles = new ArrayList<ClzValorCampoCompuesto>();

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void addValorPosible(final String pCodigo, final String pDescripcion) {
		valoresPosibles.add(new ClzValorCampoCompuesto(pCodigo, pDescripcion));
	}

	/**
	 * Devuelve valores posibles.
	 *
	 * @return Valores posibles
	 */
	public List<ClzValorCampoCompuesto> getValoresPosibles() {
		return valoresPosibles;
	}

}
