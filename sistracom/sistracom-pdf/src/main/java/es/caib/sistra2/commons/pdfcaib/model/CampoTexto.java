/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib.model;


/**
 * @author Indra
 *
 */
public class CampoTexto implements LineaObject {

  /**Posicion horizontal que ocupara el campo texto*/
  private int layoutCols;
  /**Booleano para permitir(true) o no permitir(false) que el campo texto sea multilinea*/
  private boolean multiLinea;
  /**Etiqueta del CampoTexto*/
  private String etiqueta;
  /**valor del CampoTexto*/
  private String texto;

  /**
   * Constructor
   * @param layoutCols
   * @param layoutRows
   * @param etiqueta
   * @param texto
   */
  public CampoTexto(int layoutCols, boolean multiLinea, String etiqueta,
      String texto) {
    super();
    this.layoutCols = layoutCols;
    this.multiLinea = multiLinea;
    this.etiqueta = etiqueta;
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

  /**
   * @return Devuelve multiLinea
   */
  public boolean isMultiLinea() {
    return multiLinea;
  }

  /**
   * @param multiLinea Modifica multiLinea
   */
  public void setMultiLinea(boolean multiLinea) {
    this.multiLinea = multiLinea;
  }

  /**
   * @return Devuelve etiqueta
   */
  public String getEtiqueta() {
    return etiqueta;
  }

  /**
   * @param etiqueta Modifica etiqueta
   */
  public void setEtiqueta(String etiqueta) {
    this.etiqueta = etiqueta;
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

}
