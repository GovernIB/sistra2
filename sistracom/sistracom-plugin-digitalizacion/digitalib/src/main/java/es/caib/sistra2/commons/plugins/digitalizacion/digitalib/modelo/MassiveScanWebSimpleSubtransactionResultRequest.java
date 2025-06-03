package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

/**
 * MassiveScanWebSimpleSubtransactionResultRequest
 */
public class MassiveScanWebSimpleSubtransactionResultRequest {

  private String subtransactionID = null;


  private Boolean returnScannedFile = null;


  private Boolean returnSignedFile = null;

  public MassiveScanWebSimpleSubtransactionResultRequest subtransactionID(String subtransactionID) {
    this.subtransactionID = subtransactionID;
    return this;
  }

   /**
   * Get subtransactionID
   * @return subtransactionID
  **/
 
  public String getSubtransactionID() {
    return subtransactionID;
  }

  public void setSubtransactionID(String subtransactionID) {
    this.subtransactionID = subtransactionID;
  }

  public MassiveScanWebSimpleSubtransactionResultRequest returnScannedFile(Boolean returnScannedFile) {
    this.returnScannedFile = returnScannedFile;
    return this;
  }

   /**
   * Get returnScannedFile
   * @return returnScannedFile
  **/
 
  public Boolean isReturnScannedFile() {
    return returnScannedFile;
  }

  public void setReturnScannedFile(Boolean returnScannedFile) {
    this.returnScannedFile = returnScannedFile;
  }

  public MassiveScanWebSimpleSubtransactionResultRequest returnSignedFile(Boolean returnSignedFile) {
    this.returnSignedFile = returnSignedFile;
    return this;
  }

   /**
   * Get returnSignedFile
   * @return returnSignedFile
  **/
 
  public Boolean isReturnSignedFile() {
    return returnSignedFile;
  }

  public void setReturnSignedFile(Boolean returnSignedFile) {
    this.returnSignedFile = returnSignedFile;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleSubtransactionResultRequest massiveScanWebSimpleSubtransactionResultRequest = (MassiveScanWebSimpleSubtransactionResultRequest) o;
    return Objects.equals(this.subtransactionID, massiveScanWebSimpleSubtransactionResultRequest.subtransactionID) &&
        Objects.equals(this.returnScannedFile, massiveScanWebSimpleSubtransactionResultRequest.returnScannedFile) &&
        Objects.equals(this.returnSignedFile, massiveScanWebSimpleSubtransactionResultRequest.returnSignedFile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subtransactionID, returnScannedFile, returnSignedFile);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleSubtransactionResultRequest {\n");
    
    sb.append("    subtransactionID: ").append(toIndentedString(subtransactionID)).append("\n");
    sb.append("    returnScannedFile: ").append(toIndentedString(returnScannedFile)).append("\n");
    sb.append("    returnSignedFile: ").append(toIndentedString(returnSignedFile)).append("\n");
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
