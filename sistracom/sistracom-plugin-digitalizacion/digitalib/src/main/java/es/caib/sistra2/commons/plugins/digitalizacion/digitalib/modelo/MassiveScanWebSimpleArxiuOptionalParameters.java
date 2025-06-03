package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

public class MassiveScanWebSimpleArxiuOptionalParameters {

  private String procedureName = null;

  private String procedureCode = null;

  private String documentarySerie = null;

  private String custodyIDOrExpedientID = null;

  public MassiveScanWebSimpleArxiuOptionalParameters procedureName(String procedureName) {
    this.procedureName = procedureName;
    return this;
  }

   /**
   * Get procedureName
   * @return procedureName
  **/
 
  public String getProcedureName() {
    return procedureName;
  }

  public void setProcedureName(String procedureName) {
    this.procedureName = procedureName;
  }

  public MassiveScanWebSimpleArxiuOptionalParameters procedureCode(String procedureCode) {
    this.procedureCode = procedureCode;
    return this;
  }

   /**
   * Get procedureCode
   * @return procedureCode
  **/
 
  public String getProcedureCode() {
    return procedureCode;
  }

  public void setProcedureCode(String procedureCode) {
    this.procedureCode = procedureCode;
  }

  public MassiveScanWebSimpleArxiuOptionalParameters documentarySerie(String documentarySerie) {
    this.documentarySerie = documentarySerie;
    return this;
  }

   /**
   * Get documentarySerie
   * @return documentarySerie
  **/
 
  public String getDocumentarySerie() {
    return documentarySerie;
  }

  public void setDocumentarySerie(String documentarySerie) {
    this.documentarySerie = documentarySerie;
  }

  public MassiveScanWebSimpleArxiuOptionalParameters custodyIDOrExpedientID(String custodyIDOrExpedientID) {
    this.custodyIDOrExpedientID = custodyIDOrExpedientID;
    return this;
  }

   /**
   * Get custodyIDOrExpedientID
   * @return custodyIDOrExpedientID
  **/
 
  public String getCustodyIDOrExpedientID() {
    return custodyIDOrExpedientID;
  }

  public void setCustodyIDOrExpedientID(String custodyIDOrExpedientID) {
    this.custodyIDOrExpedientID = custodyIDOrExpedientID;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleArxiuOptionalParameters massiveScanWebSimpleArxiuOptionalParameters = (MassiveScanWebSimpleArxiuOptionalParameters) o;
    return Objects.equals(this.procedureName, massiveScanWebSimpleArxiuOptionalParameters.procedureName) &&
        Objects.equals(this.procedureCode, massiveScanWebSimpleArxiuOptionalParameters.procedureCode) &&
        Objects.equals(this.documentarySerie, massiveScanWebSimpleArxiuOptionalParameters.documentarySerie) &&
        Objects.equals(this.custodyIDOrExpedientID, massiveScanWebSimpleArxiuOptionalParameters.custodyIDOrExpedientID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(procedureName, procedureCode, documentarySerie, custodyIDOrExpedientID);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleArxiuOptionalParameters {\n");
    
    sb.append("    procedureName: ").append(toIndentedString(procedureName)).append("\n");
    sb.append("    procedureCode: ").append(toIndentedString(procedureCode)).append("\n");
    sb.append("    documentarySerie: ").append(toIndentedString(documentarySerie)).append("\n");
    sb.append("    custodyIDOrExpedientID: ").append(toIndentedString(custodyIDOrExpedientID)).append("\n");
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
