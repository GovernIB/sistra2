package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;


/**
 * MassiveScanWebSimpleSignatureParameters
 */
public class MassiveScanWebSimpleSignatureParameters {

  private String functionaryFullName = null;


  private String functionaryAdministrationID = null;


  private String functionayDIR3Unit = null;

  public MassiveScanWebSimpleSignatureParameters functionaryFullName(String functionaryFullName) {
    this.functionaryFullName = functionaryFullName;
    return this;
  }

   /**
   * Get functionaryFullName
   * @return functionaryFullName
  **/
 
  public String getFunctionaryFullName() {
    return functionaryFullName;
  }

  public void setFunctionaryFullName(String functionaryFullName) {
    this.functionaryFullName = functionaryFullName;
  }

  public MassiveScanWebSimpleSignatureParameters functionaryAdministrationID(String functionaryAdministrationID) {
    this.functionaryAdministrationID = functionaryAdministrationID;
    return this;
  }

   /**
   * Get functionaryAdministrationID
   * @return functionaryAdministrationID
  **/
 
  public String getFunctionaryAdministrationID() {
    return functionaryAdministrationID;
  }

  public void setFunctionaryAdministrationID(String functionaryAdministrationID) {
    this.functionaryAdministrationID = functionaryAdministrationID;
  }

  public MassiveScanWebSimpleSignatureParameters functionayDIR3Unit(String functionayDIR3Unit) {
    this.functionayDIR3Unit = functionayDIR3Unit;
    return this;
  }

   /**
   * Get functionayDIR3Unit
   * @return functionayDIR3Unit
  **/
 
  public String getFunctionayDIR3Unit() {
    return functionayDIR3Unit;
  }

  public void setFunctionayDIR3Unit(String functionayDIR3Unit) {
    this.functionayDIR3Unit = functionayDIR3Unit;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleSignatureParameters massiveScanWebSimpleSignatureParameters = (MassiveScanWebSimpleSignatureParameters) o;
    return Objects.equals(this.functionaryFullName, massiveScanWebSimpleSignatureParameters.functionaryFullName) &&
        Objects.equals(this.functionaryAdministrationID, massiveScanWebSimpleSignatureParameters.functionaryAdministrationID) &&
        Objects.equals(this.functionayDIR3Unit, massiveScanWebSimpleSignatureParameters.functionayDIR3Unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(functionaryFullName, functionaryAdministrationID, functionayDIR3Unit);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleSignatureParameters {\n");
    
    sb.append("    functionaryFullName: ").append(toIndentedString(functionaryFullName)).append("\n");
    sb.append("    functionaryAdministrationID: ").append(toIndentedString(functionaryAdministrationID)).append("\n");
    sb.append("    functionayDIR3Unit: ").append(toIndentedString(functionayDIR3Unit)).append("\n");
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
