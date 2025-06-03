package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

/**
 * MassiveScanWebSimpleStartTransactionRequest
 */
public class MassiveScanWebSimpleStartTransactionRequest {

  private String transactionID = null;


  private String returnUrl = null;

  public MassiveScanWebSimpleStartTransactionRequest transactionID(String transactionID) {
    this.transactionID = transactionID;
    return this;
  }

   /**
   * Get transactionID
   * @return transactionID
  **/
 
  public String getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(String transactionID) {
    this.transactionID = transactionID;
  }

  public MassiveScanWebSimpleStartTransactionRequest returnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
    return this;
  }

   /**
   * Get returnUrl
   * @return returnUrl
  **/
 
  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleStartTransactionRequest massiveScanWebSimpleStartTransactionRequest = (MassiveScanWebSimpleStartTransactionRequest) o;
    return Objects.equals(this.transactionID, massiveScanWebSimpleStartTransactionRequest.transactionID) &&
        Objects.equals(this.returnUrl, massiveScanWebSimpleStartTransactionRequest.returnUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionID, returnUrl);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleStartTransactionRequest {\n");
    
    sb.append("    transactionID: ").append(toIndentedString(transactionID)).append("\n");
    sb.append("    returnUrl: ").append(toIndentedString(returnUrl)).append("\n");
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
