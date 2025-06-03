package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

/**
 * MassiveScanWebSimpleValidationInfo
 */



public class MassiveScanWebSimpleValidationInfo {

  private Boolean checkAdministrationIDOfSigner = null;


  private Boolean checkDocumentModifications = null;


  private Boolean checkValidationSignature = null;

  public MassiveScanWebSimpleValidationInfo checkAdministrationIDOfSigner(Boolean checkAdministrationIDOfSigner) {
    this.checkAdministrationIDOfSigner = checkAdministrationIDOfSigner;
    return this;
  }

   /**
   * Get checkAdministrationIDOfSigner
   * @return checkAdministrationIDOfSigner
  **/
 
  public Boolean isCheckAdministrationIDOfSigner() {
    return checkAdministrationIDOfSigner;
  }

  public void setCheckAdministrationIDOfSigner(Boolean checkAdministrationIDOfSigner) {
    this.checkAdministrationIDOfSigner = checkAdministrationIDOfSigner;
  }

  public MassiveScanWebSimpleValidationInfo checkDocumentModifications(Boolean checkDocumentModifications) {
    this.checkDocumentModifications = checkDocumentModifications;
    return this;
  }

   /**
   * Get checkDocumentModifications
   * @return checkDocumentModifications
  **/
 
  public Boolean isCheckDocumentModifications() {
    return checkDocumentModifications;
  }

  public void setCheckDocumentModifications(Boolean checkDocumentModifications) {
    this.checkDocumentModifications = checkDocumentModifications;
  }

  public MassiveScanWebSimpleValidationInfo checkValidationSignature(Boolean checkValidationSignature) {
    this.checkValidationSignature = checkValidationSignature;
    return this;
  }

   /**
   * Get checkValidationSignature
   * @return checkValidationSignature
  **/
 
  public Boolean isCheckValidationSignature() {
    return checkValidationSignature;
  }

  public void setCheckValidationSignature(Boolean checkValidationSignature) {
    this.checkValidationSignature = checkValidationSignature;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleValidationInfo massiveScanWebSimpleValidationInfo = (MassiveScanWebSimpleValidationInfo) o;
    return Objects.equals(this.checkAdministrationIDOfSigner, massiveScanWebSimpleValidationInfo.checkAdministrationIDOfSigner) &&
        Objects.equals(this.checkDocumentModifications, massiveScanWebSimpleValidationInfo.checkDocumentModifications) &&
        Objects.equals(this.checkValidationSignature, massiveScanWebSimpleValidationInfo.checkValidationSignature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(checkAdministrationIDOfSigner, checkDocumentModifications, checkValidationSignature);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleValidationInfo {\n");
    
    sb.append("    checkAdministrationIDOfSigner: ").append(toIndentedString(checkAdministrationIDOfSigner)).append("\n");
    sb.append("    checkDocumentModifications: ").append(toIndentedString(checkDocumentModifications)).append("\n");
    sb.append("    checkValidationSignature: ").append(toIndentedString(checkValidationSignature)).append("\n");
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
