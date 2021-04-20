/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib.model;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Indra
 *
 */
public class Linea {

  /** Lista que contiene todos los objetos que contiene una linea */
  List<LineaObject> objetosLinea;

  /**
   * @return Devuelve objetosLinea
   */
  public List<LineaObject> getObjetosLinea() {
    if (objetosLinea == null) {
       objetosLinea = new ArrayList<LineaObject>();
    }
    return objetosLinea;
  }

  /**
   * @param objetosLinea Modifica objetosLinea
   */
  public void setObjetosLinea(List<LineaObject> objetosLinea) {
    this.objetosLinea = objetosLinea;
  }



}
