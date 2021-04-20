/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib.model;

/**
 * @author Indra
 *
 */
public class ColumnaTabla {
  /**texto de la ColumnaTabla*/
  private String texto;
  /**Posicion horizontal que ocupara la ColumnaTabla*/
  private int layoutCols;


  /**
   * Constructor
   * @param texto
   * @param layoutCols
   */
  public ColumnaTabla(String texto, int layoutCols) {
    super();
    this.texto = texto;
    this.layoutCols = layoutCols;
  }


  /**
   * @return Devuelve texto
   */
  public String getTexto() {
    return texto;
  }


  /**
   * @param texto Modifica texto
   */
  public void setTexto(String texto) {
    this.texto = texto;
  }


  /**
   * @return Devuelve layoutCols
   */
  public int getLayoutCols() {
    return layoutCols;
  }


  /**
   * @param layoutCols Modifica layoutCols
   */
  public void setLayoutCols(int layoutCols) {
    this.layoutCols = layoutCols;
  }

}
