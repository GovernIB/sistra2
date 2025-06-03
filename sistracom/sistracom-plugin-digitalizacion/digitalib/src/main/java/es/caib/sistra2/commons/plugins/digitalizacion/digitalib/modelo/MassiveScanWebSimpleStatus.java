package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;


/**
 * MassiveScanWebSimpleStatus
 */
public class MassiveScanWebSimpleStatus {

  private Integer status = 0;


  private String errorMessage = null;


  private String errorStackTrace = null;

  public MassiveScanWebSimpleStatus status(Integer status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public MassiveScanWebSimpleStatus errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

   /**
   * Get errorMessage
   * @return errorMessage
  **/
 
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public MassiveScanWebSimpleStatus errorStackTrace(String errorStackTrace) {
    this.errorStackTrace = errorStackTrace;
    return this;
  }

   /**
   * Get errorStackTrace
   * @return errorStackTrace
  **/
 
  public String getErrorStackTrace() {
    return errorStackTrace;
  }

  public void setErrorStackTrace(String errorStackTrace) {
    this.errorStackTrace = errorStackTrace;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleStatus massiveScanWebSimpleStatus = (MassiveScanWebSimpleStatus) o;
    return Objects.equals(this.status, massiveScanWebSimpleStatus.status) &&
        Objects.equals(this.errorMessage, massiveScanWebSimpleStatus.errorMessage) &&
        Objects.equals(this.errorStackTrace, massiveScanWebSimpleStatus.errorStackTrace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, errorMessage, errorStackTrace);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleStatus {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    errorStackTrace: ").append(toIndentedString(errorStackTrace)).append("\n");
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
