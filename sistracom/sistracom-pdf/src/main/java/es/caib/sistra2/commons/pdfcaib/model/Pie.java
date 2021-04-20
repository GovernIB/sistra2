/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib.model;

/**
 * @author Indra
 *
 */
public class Pie{

  /**Texto del pie de pagina*/
  private String texto;
  /**personalizacionTexto del pie de pagina*/
  private PersonalizacionTexto personalizacionTexto;



  /**
   * Constructor
   * @param texto
   * @param personalizacionTexto
   */
  public Pie(String texto, PersonalizacionTexto personalizacionTexto) {
    super();
    this.texto = texto;
    this.personalizacionTexto = personalizacionTexto;
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







}
