package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

import java.io.Serializable;
import java.util.List;

/**
 * Opciones particularizadas de configuración de un campo del formulario de tipo
 * Lista Elementos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoListaElementos implements Serializable {

	/**
	 * Operaciones permitidas: IBMOC (Insertar / Borrar / Modificar / Ordenar /
	 * Consultar).
	 */
	private String operaciones = "IBMOC";

	/** Número máximo elementos. */
	private int maxElementos;

	/** Columnas. */
	private List<ListaElementosColumna> columnas;

	/** Evaluar borrado. */
	private TypeSiNo evaluarBorrado = TypeSiNo.NO;

	/**
	 * Método de acceso a maxElementos.
	 *
	 * @return maxElementos
	 */
	public int getMaxElementos() {
		return maxElementos;
	}

	/**
	 * Método para establecer maxElementos.
	 *
	 * @param maxElementos
	 *                         maxElementos a establecer
	 */
	public void setMaxElementos(final int maxElementos) {
		this.maxElementos = maxElementos;
	}

	/**
	 * Método de acceso a columnas.
	 *
	 * @return columnas
	 */
	public List<ListaElementosColumna> getColumnas() {
		return columnas;
	}

	/**
	 * Método para establecer columnas.
	 *
	 * @param columnas
	 *                     columnas a establecer
	 */
	public void setColumnas(final List<ListaElementosColumna> columnas) {
		this.columnas = columnas;
	}

	/**
	 * Método de acceso a operaciones.
	 * 
	 * @return operaciones
	 */
	public String getOperaciones() {
		return operaciones;
	}

	/**
	 * Método para establecer operaciones.
	 * 
	 * @param operaciones
	 *                        operaciones a establecer
	 */
	public void setOperaciones(final String operaciones) {
		this.operaciones = operaciones;
	}

	/**
	 * Método de acceso a evaluarBorrado.
	 *
	 * @return evaluarBorrado
	 */
	public TypeSiNo getEvaluarBorrado() {
		return evaluarBorrado;
	}

	/**
	 * Método para establecer evaluarBorrado.
	 *
	 * @param evaluarBorrado
	 *                        evaluarBorrado a establecer
	 */
	public void setEvaluarBorrado(final TypeSiNo evaluarBorrado) {
		this.evaluarBorrado = evaluarBorrado;
	}

}
