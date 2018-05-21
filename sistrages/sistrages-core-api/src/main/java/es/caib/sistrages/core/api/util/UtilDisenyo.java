package es.caib.sistrages.core.api.util;

import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;

public class UtilDisenyo {

	/**
	 * Orden de la linea para la insercion .
	 *
	 * @param pPagina
	 *            pagina
	 * @param pLineaSeleccionada
	 *            linea seleccionada
	 * @param pPosicion
	 *            posicion
	 * @return orden
	 */
	public static Integer ordenInsercionLinea(final PaginaFormulario pPagina,
			final LineaComponentesFormulario pLineaSeleccionada, final String pPosicion) {
		Integer orden = 1;

		if (pLineaSeleccionada == null) {
			if (!pPagina.getLineas().isEmpty()) {
				orden += pPagina.getLineas().size();
			}
		} else {
			orden = pLineaSeleccionada.getOrden();
			if (ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR.equals(pPosicion)) {
				orden++;
			}
		}
		return orden;
	}

	/**
	 * Orden del componente para la insercion.
	 *
	 * @param pLineaSeleccionada
	 *            linea seleccionada
	 * @param pOrden
	 *            orden
	 * @param pPosicion
	 *            posicion
	 * @return orden
	 */
	public static Integer ordenInsercionComponente(final LineaComponentesFormulario pLineaSeleccionada,
			final Integer pOrden, final String pPosicion) {
		Integer orden = null;
		if (pLineaSeleccionada != null) {
			if (pLineaSeleccionada.getComponentes().isEmpty()) {
				orden = 1;
			} else if (!pLineaSeleccionada.completa()) {
				if (pOrden == null) {
					if ("A".equals(pPosicion)) {
						orden = 1;
					} else {
						orden = pLineaSeleccionada.getComponentes().size() + 1;
					}
				} else {
					if ("A".equals(pPosicion)) {
						orden = pOrden;
					} else {
						orden = pOrden + 1;
					}

				}
			}
		}
		return orden;
	}
}
