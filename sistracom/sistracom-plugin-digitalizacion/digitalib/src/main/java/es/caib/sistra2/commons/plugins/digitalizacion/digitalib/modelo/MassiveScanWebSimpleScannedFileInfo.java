

package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

/**
 * MassiveScanWebSimpleScannedFileInfo
 */
public class MassiveScanWebSimpleScannedFileInfo {

  private String transactionName = null;


  private String scanDate = null;


  private Integer pixelType = null;


  private Integer pppResolution = null;


  private String formatFile = null;


  private Boolean ocr = null;


  private Boolean duplex = null;


  private String paperSize = null;


  private String documentLanguage = null;


  private String documentType = null;

  public MassiveScanWebSimpleScannedFileInfo transactionName(String transactionName) {
    this.transactionName = transactionName;
    return this;
  }

   /**
   * Get transactionName
   * @return transactionName
  **/
 
  public String getTransactionName() {
    return transactionName;
  }

  public void setTransactionName(String transactionName) {
    this.transactionName = transactionName;
  }

  public MassiveScanWebSimpleScannedFileInfo scanDate(String scanDate) {
    this.scanDate = scanDate;
    return this;
  }

   /**
   * Get scanDate
   * @return scanDate
  **/
 
  public String getScanDate() {
    return scanDate;
  }

  public void setScanDate(String scanDate) {
    this.scanDate = scanDate;
  }

  public MassiveScanWebSimpleScannedFileInfo pixelType(Integer pixelType) {
    this.pixelType = pixelType;
    return this;
  }

   /**
   * Get pixelType
   * @return pixelType
  **/
 
  public Integer getPixelType() {
    return pixelType;
  }

  public void setPixelType(Integer pixelType) {
    this.pixelType = pixelType;
  }

  public MassiveScanWebSimpleScannedFileInfo pppResolution(Integer pppResolution) {
    this.pppResolution = pppResolution;
    return this;
  }

   /**
   * Get pppResolution
   * @return pppResolution
  **/
 
  public Integer getPppResolution() {
    return pppResolution;
  }

  public void setPppResolution(Integer pppResolution) {
    this.pppResolution = pppResolution;
  }

  public MassiveScanWebSimpleScannedFileInfo formatFile(String formatFile) {
    this.formatFile = formatFile;
    return this;
  }

   /**
   * Get formatFile
   * @return formatFile
  **/
 
  public String getFormatFile() {
    return formatFile;
  }

  public void setFormatFile(String formatFile) {
    this.formatFile = formatFile;
  }

  public MassiveScanWebSimpleScannedFileInfo ocr(Boolean ocr) {
    this.ocr = ocr;
    return this;
  }

   /**
   * Get ocr
   * @return ocr
  **/
 
  public Boolean isOcr() {
    return ocr;
  }

  public void setOcr(Boolean ocr) {
    this.ocr = ocr;
  }

  public MassiveScanWebSimpleScannedFileInfo duplex(Boolean duplex) {
    this.duplex = duplex;
    return this;
  }

   /**
   * Get duplex
   * @return duplex
  **/
 
  public Boolean isDuplex() {
    return duplex;
  }

  public void setDuplex(Boolean duplex) {
    this.duplex = duplex;
  }

  public MassiveScanWebSimpleScannedFileInfo paperSize(String paperSize) {
    this.paperSize = paperSize;
    return this;
  }

   /**
   * Get paperSize
   * @return paperSize
  **/
 
  public String getPaperSize() {
    return paperSize;
  }

  public void setPaperSize(String paperSize) {
    this.paperSize = paperSize;
  }

  public MassiveScanWebSimpleScannedFileInfo documentLanguage(String documentLanguage) {
    this.documentLanguage = documentLanguage;
    return this;
  }

   /**
   * Get documentLanguage
   * @return documentLanguage
  **/
 
  public String getDocumentLanguage() {
    return documentLanguage;
  }

  public void setDocumentLanguage(String documentLanguage) {
    this.documentLanguage = documentLanguage;
  }

  public MassiveScanWebSimpleScannedFileInfo documentType(String documentType) {
    this.documentType = documentType;
    return this;
  }

   /**
   * Get documentType
   * @return documentType
  **/
 
  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleScannedFileInfo massiveScanWebSimpleScannedFileInfo = (MassiveScanWebSimpleScannedFileInfo) o;
    return Objects.equals(this.transactionName, massiveScanWebSimpleScannedFileInfo.transactionName) &&
        Objects.equals(this.scanDate, massiveScanWebSimpleScannedFileInfo.scanDate) &&
        Objects.equals(this.pixelType, massiveScanWebSimpleScannedFileInfo.pixelType) &&
        Objects.equals(this.pppResolution, massiveScanWebSimpleScannedFileInfo.pppResolution) &&
        Objects.equals(this.formatFile, massiveScanWebSimpleScannedFileInfo.formatFile) &&
        Objects.equals(this.ocr, massiveScanWebSimpleScannedFileInfo.ocr) &&
        Objects.equals(this.duplex, massiveScanWebSimpleScannedFileInfo.duplex) &&
        Objects.equals(this.paperSize, massiveScanWebSimpleScannedFileInfo.paperSize) &&
        Objects.equals(this.documentLanguage, massiveScanWebSimpleScannedFileInfo.documentLanguage) &&
        Objects.equals(this.documentType, massiveScanWebSimpleScannedFileInfo.documentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionName, scanDate, pixelType, pppResolution, formatFile, ocr, duplex, paperSize, documentLanguage, documentType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleScannedFileInfo {\n");
    
    sb.append("    transactionName: ").append(toIndentedString(transactionName)).append("\n");
    sb.append("    scanDate: ").append(toIndentedString(scanDate)).append("\n");
    sb.append("    pixelType: ").append(toIndentedString(pixelType)).append("\n");
    sb.append("    pppResolution: ").append(toIndentedString(pppResolution)).append("\n");
    sb.append("    formatFile: ").append(toIndentedString(formatFile)).append("\n");
    sb.append("    ocr: ").append(toIndentedString(ocr)).append("\n");
    sb.append("    duplex: ").append(toIndentedString(duplex)).append("\n");
    sb.append("    paperSize: ").append(toIndentedString(paperSize)).append("\n");
    sb.append("    documentLanguage: ").append(toIndentedString(documentLanguage)).append("\n");
    sb.append("    documentType: ").append(toIndentedString(documentType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
