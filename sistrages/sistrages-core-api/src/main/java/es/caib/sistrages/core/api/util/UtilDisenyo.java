package es.caib.sistrages.core.api.util;

import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;

public class UtilDisenyo {

	/** Constructor. **/
	private UtilDisenyo() {
		// Constructor vacio.
	}

	/**
	 * Orden de la linea para la insercion .
	 *
	 * @param pPagina            pagina
	 * @param pLineaSeleccionada linea seleccionada
	 * @param pPosicion          posicion
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
	 * @param pLineaSeleccionada linea seleccionada
	 * @param pOrden             orden
	 * @param pPosicion          posicion
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
					if (ConstantesDisenyo.POSICIONAMIENTO_PREVIO.equals(pPosicion)) {
						orden = 1;
					} else {
						orden = pLineaSeleccionada.getComponentes().size() + 1;
					}
				} else {
					if (ConstantesDisenyo.POSICIONAMIENTO_PREVIO.equals(pPosicion)) {
						orden = pOrden;
					} else {
						orden = pOrden + 1;
					}

				}
			}
		}
		return orden;
	}

	/**
	 * Orden del componente oculto para la insercion.
	 *
	 * @param pLineaSeleccionada linea seleccionada
	 * @param pOrden             orden
	 * @param pPosicion          posicion
	 * @return orden
	 */
	public static Integer ordenInsercionComponenteOculto(final LineaComponentesFormulario pLineaSeleccionada,
			final Integer pOrden, final String pPosicion) {
		Integer orden = null;
		if (pLineaSeleccionada != null) {
			if (pLineaSeleccionada.getComponentes().isEmpty()) {
				orden = 1;
			} else {
				if (pOrden == null) {
					if (ConstantesDisenyo.POSICIONAMIENTO_PREVIO.equals(pPosicion)) {
						orden = 1;
					} else {
						orden = pLineaSeleccionada.getComponentes().size() + 1;
					}
				} else {
					if (ConstantesDisenyo.POSICIONAMIENTO_PREVIO.equals(pPosicion)) {
						orden = pOrden;
					} else {
						orden = pOrden + 1;
					}

				}
			}
		}
		return orden;
	}

	/**
	 * Orden de la linea para moverla .
	 *
	 * @param pPagina            pagina
	 * @param pLineaSeleccionada linea seleccionada
	 * @param pPosicion          posicion
	 * @return orden
	 */
	public static Integer ordenMoverLinea(final PaginaFormulario pPagina, final Integer pOrden,
			final String pPosicion) {
		Integer orden = null;

		if (pOrden != null) {
			orden = pOrden;
			if (ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR.equals(pPosicion) && orden < pPagina.getLineas().size()) {
				orden++;
			} else if (ConstantesDisenyo.POSICIONAMIENTO_PREVIO.equals(pPosicion) && orden > 1) {
				orden--;
			}
		}
		return orden;
	}

	/**
	 * Orden del componente para moverlo.
	 *
	 * @param pLineaSeleccionada linea seleccionada
	 * @param pOrden             orden
	 * @param pPosicion          posicion
	 * @return orden
	 */
	public static Integer ordenMoverComponente(final LineaComponentesFormulario pLineaSeleccionada,
			final Integer pOrden, final String pPosicion) {
		Integer orden = null;
		if (pLineaSeleccionada != null) {
			if (pLineaSeleccionada.getComponentes().isEmpty()) {
				orden = 1;
			} else {
				orden = pOrden;
				if (ConstantesDisenyo.POSICIONAMIENTO_PREVIO.equals(pPosicion) && pOrden > 1) {
					orden--;
				} else if (ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR.equals(pPosicion)
						&& orden < pLineaSeleccionada.getComponentes().size()) {
					orden++;
				}

			}
		}
		return orden;
	}

}
