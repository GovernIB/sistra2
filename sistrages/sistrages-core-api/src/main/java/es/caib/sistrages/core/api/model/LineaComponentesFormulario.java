package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;

/**
 * Línea componentes para una página de formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class LineaComponentesFormulario extends ObjetoFormulario {

	private int orden;

	/** Componentes. */
	private List<ComponenteFormulario> componentes = new ArrayList<>();

	public List<ComponenteFormulario> getComponentes() {
		return componentes;
	}

	public void setComponentes(final List<ComponenteFormulario> componentes) {
		this.componentes = componentes;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public boolean completa() {
		return (!componentes.isEmpty() && columnasComponentes() >= ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA);
	}

	public int columnasComponentes() {
		int ncolumnas = 0;
		if (!componentes.isEmpty()) {
			for (final ComponenteFormulario elementoFormulario : componentes) {
				switch (elementoFormulario.getTipo()) {
				case SECCION:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				case ETIQUETA:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				case CAMPO_TEXTO:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				case SELECTOR:
					ncolumnas += elementoFormulario.getNumColumnas();
					break;
				default:
					break;
				}
			}
		}
		return ncolumnas;
	}

	public boolean cabenComponentes(final ComponenteFormulario pComponente) {
		boolean res = true;
		int ncolumnas = pComponente.getNumColumnas();
		if (!componentes.isEmpty()) {
			for (final ComponenteFormulario elementoFormulario : componentes) {
				if (!elementoFormulario.getId().equals(pComponente.getId())) {
					switch (elementoFormulario.getTipo()) {
					case SECCION:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					case ETIQUETA:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					case CAMPO_TEXTO:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					case SELECTOR:
						ncolumnas += elementoFormulario.getNumColumnas();
						break;
					default:
						break;
					}

					if (ncolumnas > ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA) {
						res = false;
						break;
					}
				}
			}
		}
		return res;
	}

}
