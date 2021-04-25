/**
 * 
 */
package es.caib.sistra2.commons.pdfcaib.model;



/**
 * @author Indra
 *
 */
public class Texto implements LineaObject {

  /**Texto del campo texto*/
  private String texto;
  /**personalizacionTexto del campo texto*/
  private PersonalizacionTexto personalizacionTexto;
  /**Posicion horizontal que ocupara el campo texto*/
  private int layoutCols;

  /**
   * Constructor
   * @param layoutCols
   * @param layoutRows
   * @param etiqueta
   * @param texto
   */
  public Texto(PersonalizacionTexto personalizacicionTexto,
      String texto,int layoutCols) {
    super();
    this.personalizacionTexto=personalizacicionTexto;
    this.texto = texto;
    this.setLayoutCols(layoutCols);
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
   * @return Devuelve personalizacionTexto
   */
  public PersonalizacionTexto getPersonalizacionTexto() {
    return personalizacionTexto;
  }

  /**
   * @param personalizacionTexto Modifica personalizacionTexto
   */
  public void setPersonalizacionTexto(PersonalizacionTexto personalizacionTexto) {
    this.personalizacionTexto = personalizacionTexto;
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
