/**
 * 
 */
package es.caib.sistra2.commons.pdfcaib.model;

import java.util.List;

/**
 * @author Indra
 *
 */
public class FilaTabla {



  private List<String> textoFila;

  /**
   * Constructor
   * @param textoFila
   */
  public FilaTabla(List<String> textoFila) {
    super();
    this.setTextoFila(textoFila);
  }

  /**
   * Constructor vacio
   */
  public FilaTabla() {
  }

  /**
   * @return Devuelve textoFila
   */
  public List<String> getTextoFila() {
    return textoFila;
  }

  /**
   * @param textoFila Modifica textoFila
   */
  public void setTextoFila(List<String> textoFila) {
    this.textoFila = textoFila;
  }



}
