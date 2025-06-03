package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;


/**
 *
 * Estructura de dades utilitzada per passar informació d&#x27;un error
 */
public class RestExceptionInfo {

  private Integer code = null;


  private String errorMessage = null;


  private String stackTrace = null;


  private String causeException = null;


  private String causeStackTrace = null;

  public RestExceptionInfo code(Integer code) {
    this.code = code;
    return this;
  }

   /**
   * Codi de HTTP de l&#x27;error. Veure https://en.wikipedia.org/wiki/List_of_HTTP_status_codes.
   * @return code
  **/

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public RestExceptionInfo errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

   /**
   * Missatge de l&#x27;error
   * @return errorMessage
  **/

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public RestExceptionInfo stackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
    return this;
  }

   /**
   * Stacktrace de l&#x27;excepció si n&#x27;hi hagués.
   * @return stackTrace
  **/

  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  public RestExceptionInfo causeException(String causeException) {
    this.causeException = causeException;
    return this;
  }

   /**
   * Tipus de l&#x27;excepció origen (cause) si n&#x27;hi hagués.
   * @return causeException
  **/

  public String getCauseException() {
    return causeException;
  }

  public void setCauseException(String causeException) {
    this.causeException = causeException;
  }

  public RestExceptionInfo causeStackTrace(String causeStackTrace) {
    this.causeStackTrace = causeStackTrace;
    return this;
  }

   /**
   * Stacktrace de l&#x27;excepció origen (cause) si n&#x27;hi hagués.
   * @return causeStackTrace
  **/

  public String getCauseStackTrace() {
    return causeStackTrace;
  }

  public void setCauseStackTrace(String causeStackTrace) {
    this.causeStackTrace = causeStackTrace;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestExceptionInfo restExceptionInfo = (RestExceptionInfo) o;
    return Objects.equals(this.code, restExceptionInfo.code) &&
        Objects.equals(this.errorMessage, restExceptionInfo.errorMessage) &&
        Objects.equals(this.stackTrace, restExceptionInfo.stackTrace) &&
        Objects.equals(this.causeException, restExceptionInfo.causeException) &&
        Objects.equals(this.causeStackTrace, restExceptionInfo.causeStackTrace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, errorMessage, stackTrace, causeException, causeStackTrace);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestExceptionInfo {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    stackTrace: ").append(toIndentedString(stackTrace)).append("\n");
    sb.append("    causeException: ").append(toIndentedString(causeException)).append("\n");
    sb.append("    causeStackTrace: ").append(toIndentedString(causeStackTrace)).append("\n");
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
