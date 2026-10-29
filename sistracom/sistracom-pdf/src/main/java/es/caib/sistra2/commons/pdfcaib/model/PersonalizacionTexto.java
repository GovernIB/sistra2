/**
 * 
 */
package es.caib.sistra2.commons.pdfcaib.model;

import es.caib.sistra2.commons.pdfcaib.types.TypeFuente;

/**
 * @author Indra
 *
 */
public class PersonalizacionTexto {

  /**Boolean que indica si el texto se vera en negrita*/
  private boolean negrita;
  /**Boolean que indica si el texto se vera en cursiva*/
  private boolean cursiva;
    /**Type que contiene el tipo de fuente*/
  private TypeFuente fuente;
  /**Type que contiene el nombre del tipo de fuente*/
  private String nombreFuente;
  /**Tamaño del texto*/
  private int tamanio;
  /** Indica que es HTML. */
  private boolean html = false;

  /**
   * Constructor
   * @param negrita
   * @param cursiva
   * @param fuente
   * @param tamaño
   */
  public PersonalizacionTexto(boolean negrita, boolean cursiva,
      TypeFuente fuente, int tamanio) {
    super();
    this.negrita = negrita;
    this.cursiva = cursiva;
    this.fuente = fuente;
    this.tamanio = tamanio;
  }

    public PersonalizacionTexto(boolean negrita, boolean cursiva,
      String nombreFuente, int tamanio) {
    super();
    this.negrita = negrita;
    this.cursiva = cursiva;
    this.nombreFuente = nombreFuente;
    this.tamanio = tamanio;
  }

  /**
   * @return Devuelve negrita
   */
  public boolean isNegrita() {
    return negrita;
  }

  /**
   * @param negrita Modifica negrita
   */
  public void setNegrita(boolean negrita) {
    this.negrita = negrita;
  }

  /**
   * @return Devuelve cursiva
   */
  public boolean isCursiva() {
    return cursiva;
  }

  /**
   * @param cursiva Modifica cursiva
   */
  public void setCursiva(boolean cursiva) {
    this.cursiva = cursiva;
  }

  /**
   * @return Devuelve fuente
   */
  public TypeFuente getFuente() {
    return fuente;
  }

  /**
   * @return Devuelve nombreFuente
   */
  public String getNombreFuente() {
    return nombreFuente;
  }

  /**
   * @param fuente Modifica fuente
   */
  public void setFuente(TypeFuente fuente) {
    this.fuente = fuente;
  }
  
  /**
   * @param nombreFuente Modifica nombreFuente
   */
  public void setNombreFuente(String nombreFuente) {
    this.nombreFuente = nombreFuente;
  }

  /**
   * @return Devuelve tamanio
   */
  public int getTamanio() {
    return tamanio;
  }



  /**
   * @param tamanio Modifica tamanio
   */
  public void setTamanio(int tamanio) {
    this.tamanio = tamanio;
  }

  /**
   * Indica si el texto es HTML.
   * @return Devuelve html
   */
  public boolean isHtml() {
    return html;
  }

  /**
   * Indica si el texto es HTML.
   * @param html indica si el texto es HTML
   */
  public void setHtml(boolean html) {
    this.html = html;
  }
}
