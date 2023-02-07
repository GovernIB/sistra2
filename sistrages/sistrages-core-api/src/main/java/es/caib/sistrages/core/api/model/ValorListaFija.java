package es.caib.sistrages.core.api.model;

/**
 * La clase ValorListaFija.
 */

public class ValorListaFija extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** codigo. */
	private Long codigo;

	/** descripcion. */
	private Literal descripcion;

	/** valor. */
	private String valor;

	/** indica si es el valor por defecto. */
	private boolean porDefecto;

	/** orden. */
	private int orden;

	/**
	 * Crea una nueva instancia de ValorListaFija.
	 */
	public ValorListaFija() {
		super();
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public Literal getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de valor.
	 *
	 * @return el valor de valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Establece el valor de valor.
	 *
	 * @param valor
	 *            el nuevo valor de valor
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

	/**
	 * Verifica si es por defecto.
	 *
	 * @return true, si es por defecto
	 */
	public boolean isPorDefecto() {
		return porDefecto;
	}

	/**
	 * Establece el valor de porDefecto.
	 *
	 * @param porDefecto
	 *            el nuevo valor de porDefecto
	 */
	public void setPorDefecto(final boolean porDefecto) {
		this.porDefecto = porDefecto;
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
           StringBuilder texto = new StringBuilder(tabulacion + "ValorListaFija. ");
           texto.append(tabulacion +"\t Codi:" + codigo + "\n");
           texto.append(tabulacion +"\t Valor:" + valor + "\n");
           texto.append(tabulacion +"\t Ordre:" + orden + "\n");
           texto.append(tabulacion +"\t porDefecto:" + porDefecto + "\n");
           if (descripcion != null) {
        	   texto.append(tabulacion +"\t Descripcio: \n");
        	   texto.append(descripcion.toString(tabulacion, idioma) + "\n");
           }
           return texto.toString();
     }
}
