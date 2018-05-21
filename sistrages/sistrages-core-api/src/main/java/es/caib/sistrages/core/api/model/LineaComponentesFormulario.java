package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

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
	private List<ComponenteFormulario> componentes = new ArrayList<ComponenteFormulario>();

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
		boolean res = false;
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
				default:
					break;
				}

				if (ncolumnas >= 6) {
					res = true;
					break;
				}
			}
		}
		return res;
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
					default:
						break;
					}

					if (ncolumnas > 6) {
						res = false;
						break;
					}
				}
			}
		}
		return res;
	}

}
