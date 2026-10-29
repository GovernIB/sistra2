/**
 * 
 */
package es.caib.sistra2.commons.pdfcaib.model;

import java.util.List;

/**
 * @author Indra
 *
 */
public class FormularioPdf {

  /**Cabecera del formulario*/
  private Cabecera cabecera;
  /**Lista con todas las lineas del formulario*/
  private List<Linea> lineas;
  /**Pie del formulario*/
  private Pie pie;
  /**Configuración de fuentes del formulario*/
  private ConfiguracionFuentes configuracionFuentes;

  /**
   * @return Devuelve cabecera
   */
  public Cabecera getCabecera() {
    return cabecera;
  }
  /**
   * @param cabecera Modifica cabecera
   */
  public void setCabecera(Cabecera cabecera) {
    this.cabecera = cabecera;
  }
  /**
   * @return Devuelve lineas
   */
  public List<Linea> getLineas() {
    return lineas;
  }
  /**
   * @param lineas Modifica lineas
   */
  public void setLineas(List<Linea> lineas) {
    this.lineas = lineas;
  }
  /**
   * @return Devuelve pie
   */
  public Pie getPie() {
    return pie;
  }
  /**
   * @param pie Modifica pie
   */
  public void setPie(Pie pie) {
    this.pie = pie;
  }
  /**
   * @return Devuelve la configuración de fuentes del formulario
   */
  public ConfiguracionFuentes getConfiguracionFuentes() {
    return configuracionFuentes;
  }
  /**
   * @param configuracionFuentes Modifica la configuración de fuentes del formulario
   */
  public void setConfiguracionFuentes(ConfiguracionFuentes configuracionFuentes) {
    this.configuracionFuentes = configuracionFuentes;
  }
}
