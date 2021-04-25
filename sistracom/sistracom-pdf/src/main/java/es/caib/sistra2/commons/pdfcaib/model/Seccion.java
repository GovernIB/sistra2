/**
 * 
 */
package es.caib.sistra2.commons.pdfcaib.model;

/**
 * @author Indra
 *
 */
public class Seccion implements LineaObject{

  /**Letra de la sección*/
  private String letra;
  /**Título de la sección*/
  private String titulo;
  /**Posicion horizontal que ocupara la seccion (fijado a 6)*/
  private int layoutCols;

  /**
   * Constructor
   * @param letra
   * @param titulo
   * @param layoutCols
   */
  public Seccion(String letra, String titulo) {
    super();
    this.letra = letra;
    this.titulo = titulo;
    this.layoutCols = 6;
  }

  /**
   * @return Devuelve letra
   */
  public String getLetra() {
    return letra;
  }

  /**
   * @param letra Modifica letra
   */
  public void setLetra(String letra) {
    this.letra = letra;
  }

  /**
   * @return Devuelve titulo
   */
  public String getTitulo() {
    return titulo;
  }

  /**
   * @param titulo Modifica titulo
   */
  public void setTitulo(String titulo) {
    this.titulo = titulo;
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
