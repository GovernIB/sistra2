package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;

/**
 * Fila importar area.
 *
 * @author Indra
 *
 */
public class FilaImportarArea extends FilaImportar {

	/** Area. **/
	private Area area;

	/** Area actual. **/
	private Area areaActual;

	/** Indica el valor de la descripción del area. **/
	private String areaResultado;

	/** Constructor básico. **/
	public FilaImportarArea() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarArea(final TypeImportarAccion iAccion, final TypeImportarEstado iEstado,
			final TypeImportarResultado iTypeAccion, final Area area, final Area areaActual) {
		this.accion = iAccion;
		this.estado = iEstado;
		this.resultado = iTypeAccion;
		this.area = area;
		this.areaActual = areaActual;
		this.areaResultado = area.getDescripcion();
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * @return the areaActual
	 */
	public Area getAreaActual() {
		return areaActual;
	}

	/**
	 * @param areaActual
	 *            the areaActual to set
	 */
	public void setAreaActual(final Area areaActual) {
		this.areaActual = areaActual;
	}

	/**
	 * @return the areaResultado
	 */
	public String getAreaResultado() {
		return areaResultado;
	}

	/**
	 * @param areaResultado
	 *            the areaResultado to set
	 */
	public void setAreaResultado(final String areaResultado) {
		this.areaResultado = areaResultado;
	}

}
