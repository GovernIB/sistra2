package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;


public class MassiveScanWebSimpleArxiuInfo {
 
  private String csv = null;

  private String originalFileURL = null;
  
  private String printableFileURL = null;

  private String eniFileURL = null;

  private String csvValidationWeb = null;

  private String csvGenerationDefinition = null;

  private String validationFileUrl = null;

  private String expedientID = null;

  private String documentID = null;

  public MassiveScanWebSimpleArxiuInfo csv(String csv) {
    this.csv = csv;
    return this;
  }

   /**
   * Get csv
   * @return csv
  **/
 
  public String getCsv() {
    return csv;
  }

  public void setCsv(String csv) {
    this.csv = csv;
  }

  public MassiveScanWebSimpleArxiuInfo originalFileURL(String originalFileURL) {
    this.originalFileURL = originalFileURL;
    return this;
  }

   /**
   * Get originalFileURL
   * @return originalFileURL
  **/
 
  public String getOriginalFileURL() {
    return originalFileURL;
  }

  public void setOriginalFileURL(String originalFileURL) {
    this.originalFileURL = originalFileURL;
  }

  public MassiveScanWebSimpleArxiuInfo printableFileURL(String printableFileURL) {
    this.printableFileURL = printableFileURL;
    return this;
  }

   /**
   * Get printableFileURL
   * @return printableFileURL
  **/
 
  public String getPrintableFileURL() {
    return printableFileURL;
  }

  public void setPrintableFileURL(String printableFileURL) {
    this.printableFileURL = printableFileURL;
  }

  public MassiveScanWebSimpleArxiuInfo eniFileURL(String eniFileURL) {
    this.eniFileURL = eniFileURL;
    return this;
  }

   /**
   * Get eniFileURL
   * @return eniFileURL
  **/
 
  public String getEniFileURL() {
    return eniFileURL;
  }

  public void setEniFileURL(String eniFileURL) {
    this.eniFileURL = eniFileURL;
  }

  public MassiveScanWebSimpleArxiuInfo csvValidationWeb(String csvValidationWeb) {
    this.csvValidationWeb = csvValidationWeb;
    return this;
  }

   /**
   * Get csvValidationWeb
   * @return csvValidationWeb
  **/
 
  public String getCsvValidationWeb() {
    return csvValidationWeb;
  }

  public void setCsvValidationWeb(String csvValidationWeb) {
    this.csvValidationWeb = csvValidationWeb;
  }

  public MassiveScanWebSimpleArxiuInfo csvGenerationDefinition(String csvGenerationDefinition) {
    this.csvGenerationDefinition = csvGenerationDefinition;
    return this;
  }

   /**
   * Get csvGenerationDefinition
   * @return csvGenerationDefinition
  **/
 
  public String getCsvGenerationDefinition() {
    return csvGenerationDefinition;
  }

  public void setCsvGenerationDefinition(String csvGenerationDefinition) {
    this.csvGenerationDefinition = csvGenerationDefinition;
  }

  public MassiveScanWebSimpleArxiuInfo validationFileUrl(String validationFileUrl) {
    this.validationFileUrl = validationFileUrl;
    return this;
  }

   /**
   * Get validationFileUrl
   * @return validationFileUrl
  **/
 
  public String getValidationFileUrl() {
    return validationFileUrl;
  }

  public void setValidationFileUrl(String validationFileUrl) {
    this.validationFileUrl = validationFileUrl;
  }

  public MassiveScanWebSimpleArxiuInfo expedientID(String expedientID) {
    this.expedientID = expedientID;
    return this;
  }

   /**
   * Get expedientID
   * @return expedientID
  **/
 
  public String getExpedientID() {
    return expedientID;
  }

  public void setExpedientID(String expedientID) {
    this.expedientID = expedientID;
  }

  public MassiveScanWebSimpleArxiuInfo documentID(String documentID) {
    this.documentID = documentID;
    return this;
  }

   /**
   * Get documentID
   * @return documentID
  **/
 
  public String getDocumentID() {
    return documentID;
  }

  public void setDocumentID(String documentID) {
    this.documentID = documentID;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleArxiuInfo massiveScanWebSimpleArxiuInfo = (MassiveScanWebSimpleArxiuInfo) o;
    return Objects.equals(this.csv, massiveScanWebSimpleArxiuInfo.csv) &&
        Objects.equals(this.originalFileURL, massiveScanWebSimpleArxiuInfo.originalFileURL) &&
        Objects.equals(this.printableFileURL, massiveScanWebSimpleArxiuInfo.printableFileURL) &&
        Objects.equals(this.eniFileURL, massiveScanWebSimpleArxiuInfo.eniFileURL) &&
        Objects.equals(this.csvValidationWeb, massiveScanWebSimpleArxiuInfo.csvValidationWeb) &&
        Objects.equals(this.csvGenerationDefinition, massiveScanWebSimpleArxiuInfo.csvGenerationDefinition) &&
        Objects.equals(this.validationFileUrl, massiveScanWebSimpleArxiuInfo.validationFileUrl) &&
        Objects.equals(this.expedientID, massiveScanWebSimpleArxiuInfo.expedientID) &&
        Objects.equals(this.documentID, massiveScanWebSimpleArxiuInfo.documentID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(csv, originalFileURL, printableFileURL, eniFileURL, csvValidationWeb, csvGenerationDefinition, validationFileUrl, expedientID, documentID);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleArxiuInfo {\n");
    
    sb.append("    csv: ").append(toIndentedString(csv)).append("\n");
    sb.append("    originalFileURL: ").append(toIndentedString(originalFileURL)).append("\n");
    sb.append("    printableFileURL: ").append(toIndentedString(printableFileURL)).append("\n");
    sb.append("    eniFileURL: ").append(toIndentedString(eniFileURL)).append("\n");
    sb.append("    csvValidationWeb: ").append(toIndentedString(csvValidationWeb)).append("\n");
    sb.append("    csvGenerationDefinition: ").append(toIndentedString(csvGenerationDefinition)).append("\n");
    sb.append("    validationFileUrl: ").append(toIndentedString(validationFileUrl)).append("\n");
    sb.append("    expedientID: ").append(toIndentedString(expedientID)).append("\n");
    sb.append("    documentID: ").append(toIndentedString(documentID)).append("\n");
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
