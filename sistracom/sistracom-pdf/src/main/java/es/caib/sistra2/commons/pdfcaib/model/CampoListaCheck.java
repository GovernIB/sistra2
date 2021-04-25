/**
 * 
 */
package es.caib.sistra2.commons.pdfcaib.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Indra
 *
 */
public class CampoListaCheck implements LineaObject{

  /**Posicion horizontal que ocupara la lista check*/
  private int layoutCols;
  /**Etiqueta de la lista check*/
  private String etiqueta;
  /**Array de campos check que contiene la lista*/
  private ArrayList<CampoCheck> opciones;

  /**
   * Constructor
   * @param layoutCols
   * @param etiqueta
   * @param opciones
   */
  public CampoListaCheck(int layoutCols, String etiqueta, List<CampoCheck> opciones) {
    super();
    this.layoutCols = layoutCols;
    this.etiqueta = etiqueta;
    this.opciones = (ArrayList<CampoCheck>) opciones;
  }

  /**
   * @return Devuelve opciones
   */
  public ArrayList<CampoCheck> getOpciones() {
    if(opciones==null) { opciones = new ArrayList<CampoCheck>();}
    return opciones;
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
   * @param opciones Modifica opciones
   */
  public void setOpciones(ArrayList<CampoCheck> opciones) {
    this.opciones = opciones;
  }




}
