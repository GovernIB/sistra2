package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeAlineacionTexto;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La clase ComponenteFormulario.
 */

public abstract class ComponenteFormulario extends ObjetoFormulario implements Comparable<ComponenteFormulario> {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * id componente.
	 */
	private String idComponente;

	/**
	 * tipo.
	 */
	private TypeObjetoFormulario tipo;

	/**
	 * orden.
	 */
	private int orden;

	/**
	 * num columnas.
	 */
	private int numColumnas = 1;

	/**
	 * texto.
	 */
	private Literal texto;

	/**
	 * ayuda.
	 */
	private Literal ayuda;

	/**
	 * no mostrar texto.
	 */
	private boolean noMostrarTexto = true;

	/**
	 * alineacion texto.
	 */
	private TypeAlineacionTexto alineacionTexto = TypeAlineacionTexto.IZQUIERDA;

	/**
	 * Indica si el componente está dentro de una seccion reutilizable. NO SE ALMACENA EN BBDD.
	 */
	private boolean tipoSeccionReutilizable = false;
	/**
	 * Indica cuando el componente es de tipo seccion reutilizable, el id del form (disenyo formulario)
	 */
	private Long idFormSeccion;
	/**
	 * Obtiene el valor de idComponente.
	 *
	 * @return el valor de idComponente
	 */
	public String getIdComponente() {
		return idComponente;
	}

	/**
	 * Establece el valor de idComponente.
	 *
	 * @param codigo
	 *            el nuevo valor de idComponente
	 */
	public void setIdComponente(final String codigo) {
		this.idComponente = codigo;
	}

	/**
	 * Obtiene el valor de tipo.
	 *
	 * @return el valor de tipo
	 */
	public TypeObjetoFormulario getTipo() {
		return tipo;
	}

	/**
	 * Establece el valor de tipo.
	 *
	 * @param tipo
	 *            el nuevo valor de tipo
	 */
	public void setTipo(final TypeObjetoFormulario tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el valor de orden.
	 *
	 * @return el valor de orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * Establece el valor de orden.
	 *
	 * @param orden
	 *            el nuevo valor de orden
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

	/**
	 * Obtiene el valor de numColumnas.
	 *
	 * @return el valor de numColumnas
	 */
	public int getNumColumnas() {
		return numColumnas;
	}

	/**
	 * Establece el valor de numColumnas.
	 *
	 * @param columnas
	 *            el nuevo valor de numColumnas
	 */
	public void setNumColumnas(final int columnas) {
		this.numColumnas = columnas;
	}

	/**
	 * Obtiene el valor de texto.
	 *
	 * @return el valor de texto
	 */
	public Literal getTexto() {
		return texto;
	}

	/**
	 * Establece el valor de texto.
	 *
	 * @param texto
	 *            el nuevo valor de texto
	 */
	public void setTexto(final Literal texto) {
		this.texto = texto;
	}

	/**
	 * Obtiene el valor de ayuda.
	 *
	 * @return el valor de ayuda
	 */
	public Literal getAyuda() {
		return ayuda;
	}

	/**
	 * Establece el valor de ayuda.
	 *
	 * @param ayuda
	 *            el nuevo valor de ayuda
	 */
	public void setAyuda(final Literal ayuda) {
		this.ayuda = ayuda;
	}

	/**
	 * Verifica si es no mostrar texto.
	 *
	 * @return true, si es no mostrar texto
	 */
	public boolean isNoMostrarTexto() {
		return noMostrarTexto;
	}

	/**
	 * Establece el valor de noMostrarTexto.
	 *
	 * @param noMostrarTexto
	 *            el nuevo valor de noMostrarTexto
	 */
	public void setNoMostrarTexto(final boolean noMostrarTexto) {
		this.noMostrarTexto = noMostrarTexto;
	}

	/**
	 * Obtiene el valor de alineacionTexto.
	 *
	 * @return el valor de alineacionTexto
	 */
	public TypeAlineacionTexto getAlineacionTexto() {
		return alineacionTexto;
	}

	/**
	 * Establece el valor de alineacionTexto.
	 *
	 * @param alineacionTexto
	 *            el nuevo valor de alineacionTexto
	 */
	public void setAlineacionTexto(final TypeAlineacionTexto alineacionTexto) {
		this.alineacionTexto = alineacionTexto;
	}

	/**
	 * @return the tipoSeccionReutilizable
	 */
	public boolean isTipoSeccionReutilizable() {
		return tipoSeccionReutilizable;
	}

	/**
	 * @param tipoSeccionReutilizable the tipoSeccionReutilizable to set
	 */
	public void setTipoSeccionReutilizable(boolean tipoSeccionReutilizable) {
		this.tipoSeccionReutilizable = tipoSeccionReutilizable;
	}

	/**
	 * @return the idFormSeccion
	 */
	public Long getIdFormSeccion() {
		return idFormSeccion;
	}

	/**
	 * @param idFormSeccion the idFormSeccion to set
	 */
	public void setIdFormSeccion(Long idFormSeccion) {
		this.idFormSeccion = idFormSeccion;
	}

	@Override
	public int compareTo(final ComponenteFormulario arg0) {
		return Integer.compare(this.getOrden(), arg0.getOrden());
	}
}
