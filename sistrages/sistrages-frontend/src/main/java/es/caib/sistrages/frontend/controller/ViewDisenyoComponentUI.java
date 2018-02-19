package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.ToggleEvent;

/**
 * Disenyo de componentes UI. Contiene eventos para colapsar/descolapsar un
 * panel.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDisenyoComponentUI extends ViewControllerBase {

	/**
	 * Crea una nueva instancia de view definicion version.
	 */
	public ViewDisenyoComponentUI() {
		super();
	}

	/**
	 * Indica si esta 'colapsado' el panel de propiedades (a true colapsado).
	 */
	private boolean visiblePropiedades = false;

	/**
	 * Indica si esta 'colapsado' el panel de scripts (a true colapsado).
	 */
	private boolean visibleScripts = true;

	/**
	 * Indica si esta 'colapsado' el panel de estilos (a true colapsado).
	 */
	private boolean visibleEstilos = true;

	/**
	 * Indica el toogle.
	 *
	 * @param event
	 */
	public void onToggle(final ToggleEvent event) {
		if ("VISIBLE".equals(event.getVisibility().name())) {

			if ("panelPropietats".equals(event.getComponent().getId())) {
				setVisiblePropiedades(false);
				setVisibleScripts(true);
				setVisibleEstilos(true);
			}

			if ("panelScripts".equals(event.getComponent().getId())) {
				setVisiblePropiedades(true);
				setVisibleScripts(false);
				setVisibleEstilos(true);
			}

			if ("panelEstilos".equals(event.getComponent().getId())) {
				setVisiblePropiedades(true);
				setVisibleScripts(true);
				setVisibleEstilos(false);
			}
		}

	}

	/**
	 * @return the visiblePropiedades
	 */
	public boolean isVisiblePropiedades() {
		return visiblePropiedades;
	}

	/**
	 * @param visiblePropiedades
	 *            the visiblePropiedades to set
	 */
	public void setVisiblePropiedades(final boolean visiblePropiedades) {
		this.visiblePropiedades = visiblePropiedades;
	}

	/**
	 * @return the visibleScripts
	 */
	public boolean isVisibleScripts() {
		return visibleScripts;
	}

	/**
	 * @param visibleScripts
	 *            the visibleScripts to set
	 */
	public void setVisibleScripts(final boolean visibleScripts) {
		this.visibleScripts = visibleScripts;
	}

	/**
	 * @return the visibleEstilos
	 */
	public boolean isVisibleEstilos() {
		return visibleEstilos;
	}

	/**
	 * @param visibleEstilos
	 *            the visibleEstilos to set
	 */
	public void setVisibleEstilos(final boolean visibleEstilos) {
		this.visibleEstilos = visibleEstilos;
	}
}
