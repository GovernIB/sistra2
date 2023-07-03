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
	 * Indica el número máximo de elementos en la lista de elementos.
	 */
	private int numeroMaximoElementos = 0;

	/**
	 * Para campos de un componente lista de elementos, indica si sale en la lista
	 */
	private boolean listaElementosVisible;

	/**
	 * Para campos de un componente lista de elementos, si sale en la lista indica el ancho de columna
	 */
	private Integer listaElementosAnchoColumna;

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



	/**
	 * @return the numeroMaximoElementos
	 */
	public int getNumeroMaximoElementos() {
		return numeroMaximoElementos;
	}

	/**
	 * @param numeroMaximoElementos the numeroMaximoElementos to set
	 */
	public void setNumeroMaximoElementos(int numeroMaximoElementos) {
		this.numeroMaximoElementos = numeroMaximoElementos;
	}


	/**
	 * @return the listaElementosVisible
	 */
	public boolean isListaElementosVisible() {
		return listaElementosVisible;
	}

	/**
	 * @param listaElementosVisible the listaElementosVisible to set
	 */
	public void setListaElementosVisible(boolean listaElementosVisible) {
		this.listaElementosVisible = listaElementosVisible;
	}

	/**
	 * @return the listaElementosAnchoColumna
	 */
	public Integer getListaElementosAnchoColumna() {
		return listaElementosAnchoColumna;
	}

	/**
	 * @param listaElementosAnchoColumna the listaElementosAnchoColumna to set
	 */
	public void setListaElementosAnchoColumna(Integer listaElementosAnchoColumna) {
		this.listaElementosAnchoColumna = listaElementosAnchoColumna;
	}

	@Override
	public int compareTo(final ComponenteFormulario arg0) {
		return Integer.compare(this.getOrden(), arg0.getOrden());
	}

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "ComponenteFormulario. ");
           texto.append(tabulacion +"\t IdComponente:" + idComponente + "\n");
           texto.append(tabulacion +"\t Tipus:" + tipo + "\n");
           texto.append(tabulacion +"\t Ordre:" + orden + "\n");
           texto.append(tabulacion +"\t NumColumnas:" + numColumnas + "\n");
           texto.append(tabulacion +"\t NoMostrarText:" + noMostrarTexto + "\n");
           texto.append(tabulacion +"\t AlineacioText:" + alineacionTexto + "\n");
           texto.append(tabulacion +"\t TipoSeccionReutilizable:" + tipoSeccionReutilizable + "\n");
           texto.append(tabulacion +"\t IdFormSeccion:" + idFormSeccion + "\n");
           texto.append(tabulacion +"\t NumeroMaximoElementos:" + numeroMaximoElementos + "\n");
           texto.append(tabulacion +"\t ListaElementosVisible:" + listaElementosVisible + "\n");
           texto.append(tabulacion +"\t ListaElementosAnchoColumna:" + listaElementosAnchoColumna + "\n");
           if (this.texto != null) {
        	   texto.append(tabulacion +"\t Text: \n");
        	   texto.append(this.texto.toString(tabulacion, idioma) + "\n");
           }
           if (this.ayuda != null) {
        	   texto.append(tabulacion +"\t Ajuda: \n");
        	   texto.append(this.ayuda.toString(tabulacion, idioma) + "\n");
           }
           return texto.toString();
     }
}
