/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib.model;

import java.util.List;

/**
 * @author Indra
 *
 */
public class HeaderTabla {

  /**Lista de las cabeceras de las columnas de la tabla*/
  private List<ColumnaTabla> columnaTablas;


  /**
   * Constructor
   * @param columnaTablas
   */
  public HeaderTabla(List<ColumnaTabla> columnaTablas) {
    super();
    this.setColumnaTablas(columnaTablas);
  }


  /**
   * @return Devuelve columnaTablas
   */
  public List<ColumnaTabla> getColumnaTablas() {
    return columnaTablas;
  }


  /**
   * @param columnaTablas Modifica columnaTablas
   */
  public void setColumnaTablas(List<ColumnaTabla> columnaTablas) {
    this.columnaTablas = columnaTablas;
  }



}
