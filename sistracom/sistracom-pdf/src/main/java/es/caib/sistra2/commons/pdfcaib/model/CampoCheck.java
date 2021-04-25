/**
 * 
 */
package es.caib.sistra2.commons.pdfcaib.model;

/**
 * @author Indra
 *
 */
public class CampoCheck implements LineaObject{

  /**Posicion horizontal que ocupara el campo check*/
  private int layoutCols;
  /**Etiqueta del campocheck*/
  private String etiqueta;
  /**Boolean que informa de si esta marcado o desmarcado el check*/
  private boolean checked;

  /**
   * @param layoutCols
   * @param etiqueta
   * @param checked
   */
  public CampoCheck(int layoutCols, String etiqueta, boolean checked) {
    super();
    this.layoutCols = layoutCols;
    this.etiqueta = etiqueta;
    this.setChecked(checked);
  }

  /**
   * @return the layoutCols
   */
  public int getLayoutCols() {
    return layoutCols;
  }
  /**
   * @param layoutCols the layoutCols to set
   */
  public void setLayoutCols(int layoutCols) {
    this.layoutCols = layoutCols;
  }
  /**
   * @return the etiqueta
   */
  public String getEtiqueta() {
    return etiqueta;
  }
  /**
   * @param etiqueta the etiqueta to set
   */
  public void setEtiqueta(String etiqueta) {
    this.etiqueta = etiqueta;
  }

  /**
   * @return the checked
   */
  public boolean isChecked() {
    return checked;
  }

  /**
   * @param checked the checked to set
   */
  public void setChecked(boolean checked) {
    this.checked = checked;
  }




}
