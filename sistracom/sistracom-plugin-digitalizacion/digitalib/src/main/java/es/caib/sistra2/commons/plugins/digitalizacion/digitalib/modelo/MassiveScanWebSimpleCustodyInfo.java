package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

/**
 * MassiveScanWebSimpleCustodyInfo
 */
public class MassiveScanWebSimpleCustodyInfo {

  private String csv = null;


  private String originalFileURL = null;


  private String printableFileURL = null;


  private String eniFileURL = null;


  private String csvValidationWeb = null;


  private String csvGenerationDefinition = null;


  private String validationFileUrl = null;


  private String custodyID = null;

  public MassiveScanWebSimpleCustodyInfo csv(String csv) {
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

  public MassiveScanWebSimpleCustodyInfo originalFileURL(String originalFileURL) {
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

  public MassiveScanWebSimpleCustodyInfo printableFileURL(String printableFileURL) {
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

  public MassiveScanWebSimpleCustodyInfo eniFileURL(String eniFileURL) {
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

  public MassiveScanWebSimpleCustodyInfo csvValidationWeb(String csvValidationWeb) {
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

  public MassiveScanWebSimpleCustodyInfo csvGenerationDefinition(String csvGenerationDefinition) {
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

  public MassiveScanWebSimpleCustodyInfo validationFileUrl(String validationFileUrl) {
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

  public MassiveScanWebSimpleCustodyInfo custodyID(String custodyID) {
    this.custodyID = custodyID;
    return this;
  }

   /**
   * Get custodyID
   * @return custodyID
  **/
 
  public String getCustodyID() {
    return custodyID;
  }

  public void setCustodyID(String custodyID) {
    this.custodyID = custodyID;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleCustodyInfo massiveScanWebSimpleCustodyInfo = (MassiveScanWebSimpleCustodyInfo) o;
    return Objects.equals(this.csv, massiveScanWebSimpleCustodyInfo.csv) &&
        Objects.equals(this.originalFileURL, massiveScanWebSimpleCustodyInfo.originalFileURL) &&
        Objects.equals(this.printableFileURL, massiveScanWebSimpleCustodyInfo.printableFileURL) &&
        Objects.equals(this.eniFileURL, massiveScanWebSimpleCustodyInfo.eniFileURL) &&
        Objects.equals(this.csvValidationWeb, massiveScanWebSimpleCustodyInfo.csvValidationWeb) &&
        Objects.equals(this.csvGenerationDefinition, massiveScanWebSimpleCustodyInfo.csvGenerationDefinition) &&
        Objects.equals(this.validationFileUrl, massiveScanWebSimpleCustodyInfo.validationFileUrl) &&
        Objects.equals(this.custodyID, massiveScanWebSimpleCustodyInfo.custodyID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(csv, originalFileURL, printableFileURL, eniFileURL, csvValidationWeb, csvGenerationDefinition, validationFileUrl, custodyID);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleCustodyInfo {\n");
    
    sb.append("    csv: ").append(toIndentedString(csv)).append("\n");
    sb.append("    originalFileURL: ").append(toIndentedString(originalFileURL)).append("\n");
    sb.append("    printableFileURL: ").append(toIndentedString(printableFileURL)).append("\n");
    sb.append("    eniFileURL: ").append(toIndentedString(eniFileURL)).append("\n");
    sb.append("    csvValidationWeb: ").append(toIndentedString(csvValidationWeb)).append("\n");
    sb.append("    csvGenerationDefinition: ").append(toIndentedString(csvGenerationDefinition)).append("\n");
    sb.append("    validationFileUrl: ").append(toIndentedString(validationFileUrl)).append("\n");
    sb.append("    custodyID: ").append(toIndentedString(custodyID)).append("\n");
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
