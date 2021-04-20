/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.pdfcaib.model;


import java.util.List;


/**
 * @author Indra
 *
 */
public class Tabla implements LineaObject{

  /**Posicion horizontal que ocupara la tabla*/
  private int layoutCols;
  /**Título de la tabla la tabla*/
  private String tituloTabla;
  /**Headers de la tabla*/
  private HeaderTabla cabecera;
  /**Filas de la tabla*/
  private List<FilaTabla> filaTablas;


  /**
   * Constructor
   * @param cabecera
   * @param filaTablas
   */
  public Tabla(int layoutCols, HeaderTabla cabecera, List<FilaTabla> filaTablas,String tituloTabla) {
    super();
    this.layoutCols = layoutCols;
    this.cabecera = cabecera;
    this.filaTablas = filaTablas;
    this.setTituloTabla(tituloTabla);
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
   * @return Devuelve tituloTabla
   */
  public String getTituloTabla() {
    return tituloTabla;
  }


  /**
   * @param tituloTabla Modifica tituloTabla
   */
  public void setTituloTabla(String tituloTabla) {
    this.tituloTabla = tituloTabla;
  }


  /**
   * @return Devuelve cabecera
   */
  public HeaderTabla getCabecera() {
    return cabecera;
  }


  /**
   * @param cabecera Modifica cabecera
   */
  public void setCabecera(HeaderTabla cabecera) {
    this.cabecera = cabecera;
  }


  /**
   * @return Devuelve filaTablas
   */
  public List<FilaTabla> getFilaTablas() {
    return filaTablas;
  }


  /**
   * @param filaTablas Modifica filaTablas
   */
  public void setFilaTablas(List<FilaTabla> filaTablas) {
    this.filaTablas = filaTablas;
  }










}
