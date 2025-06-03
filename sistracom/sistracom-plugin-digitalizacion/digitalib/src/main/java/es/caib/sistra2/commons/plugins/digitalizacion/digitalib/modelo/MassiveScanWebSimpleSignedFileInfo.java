package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;


/**
 * MassiveScanWebSimpleSignedFileInfo
 */
public class MassiveScanWebSimpleSignedFileInfo {

  private Integer signOperation = null;


  private String signType = null;


  private String signAlgorithm = null;


  private Integer signMode = null;


  private Integer signaturesTableLocation = null;


  private Boolean timeStampIncluded = null;


  private Boolean policyIncluded = null;


  private String eniTipoFirma = null;


  private String eniPerfilFirma = null;


  private String eniRolFirma = null;


  private String eniSignerName = null;


  private String eniSignerAdministrationId = null;


  private String eniSignLevel = null;


  private MassiveScanWebSimpleValidationInfo validationInfo = null;


  private List<MassiveScanWebSimpleKeyValue> additionInformation = null;

  public MassiveScanWebSimpleSignedFileInfo signOperation(Integer signOperation) {
    this.signOperation = signOperation;
    return this;
  }

   /**
   * Get signOperation
   * @return signOperation
  **/
 
  public Integer getSignOperation() {
    return signOperation;
  }

  public void setSignOperation(Integer signOperation) {
    this.signOperation = signOperation;
  }

  public MassiveScanWebSimpleSignedFileInfo signType(String signType) {
    this.signType = signType;
    return this;
  }

   /**
   * Get signType
   * @return signType
  **/
 
  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public MassiveScanWebSimpleSignedFileInfo signAlgorithm(String signAlgorithm) {
    this.signAlgorithm = signAlgorithm;
    return this;
  }

   /**
   * Get signAlgorithm
   * @return signAlgorithm
  **/
 
  public String getSignAlgorithm() {
    return signAlgorithm;
  }

  public void setSignAlgorithm(String signAlgorithm) {
    this.signAlgorithm = signAlgorithm;
  }

  public MassiveScanWebSimpleSignedFileInfo signMode(Integer signMode) {
    this.signMode = signMode;
    return this;
  }

   /**
   * Get signMode
   * @return signMode
  **/
 
  public Integer getSignMode() {
    return signMode;
  }

  public void setSignMode(Integer signMode) {
    this.signMode = signMode;
  }

  public MassiveScanWebSimpleSignedFileInfo signaturesTableLocation(Integer signaturesTableLocation) {
    this.signaturesTableLocation = signaturesTableLocation;
    return this;
  }

   /**
   * Get signaturesTableLocation
   * @return signaturesTableLocation
  **/
 
  public Integer getSignaturesTableLocation() {
    return signaturesTableLocation;
  }

  public void setSignaturesTableLocation(Integer signaturesTableLocation) {
    this.signaturesTableLocation = signaturesTableLocation;
  }

  public MassiveScanWebSimpleSignedFileInfo timeStampIncluded(Boolean timeStampIncluded) {
    this.timeStampIncluded = timeStampIncluded;
    return this;
  }

   /**
   * Get timeStampIncluded
   * @return timeStampIncluded
  **/
 
  public Boolean isTimeStampIncluded() {
    return timeStampIncluded;
  }

  public void setTimeStampIncluded(Boolean timeStampIncluded) {
    this.timeStampIncluded = timeStampIncluded;
  }

  public MassiveScanWebSimpleSignedFileInfo policyIncluded(Boolean policyIncluded) {
    this.policyIncluded = policyIncluded;
    return this;
  }

   /**
   * Get policyIncluded
   * @return policyIncluded
  **/
 
  public Boolean isPolicyIncluded() {
    return policyIncluded;
  }

  public void setPolicyIncluded(Boolean policyIncluded) {
    this.policyIncluded = policyIncluded;
  }

  public MassiveScanWebSimpleSignedFileInfo eniTipoFirma(String eniTipoFirma) {
    this.eniTipoFirma = eniTipoFirma;
    return this;
  }

   /**
   * Get eniTipoFirma
   * @return eniTipoFirma
  **/
 
  public String getEniTipoFirma() {
    return eniTipoFirma;
  }

  public void setEniTipoFirma(String eniTipoFirma) {
    this.eniTipoFirma = eniTipoFirma;
  }

  public MassiveScanWebSimpleSignedFileInfo eniPerfilFirma(String eniPerfilFirma) {
    this.eniPerfilFirma = eniPerfilFirma;
    return this;
  }

   /**
   * Get eniPerfilFirma
   * @return eniPerfilFirma
  **/
 
  public String getEniPerfilFirma() {
    return eniPerfilFirma;
  }

  public void setEniPerfilFirma(String eniPerfilFirma) {
    this.eniPerfilFirma = eniPerfilFirma;
  }

  public MassiveScanWebSimpleSignedFileInfo eniRolFirma(String eniRolFirma) {
    this.eniRolFirma = eniRolFirma;
    return this;
  }

   /**
   * Get eniRolFirma
   * @return eniRolFirma
  **/
 
  public String getEniRolFirma() {
    return eniRolFirma;
  }

  public void setEniRolFirma(String eniRolFirma) {
    this.eniRolFirma = eniRolFirma;
  }

  public MassiveScanWebSimpleSignedFileInfo eniSignerName(String eniSignerName) {
    this.eniSignerName = eniSignerName;
    return this;
  }

   /**
   * Get eniSignerName
   * @return eniSignerName
  **/
 
  public String getEniSignerName() {
    return eniSignerName;
  }

  public void setEniSignerName(String eniSignerName) {
    this.eniSignerName = eniSignerName;
  }

  public MassiveScanWebSimpleSignedFileInfo eniSignerAdministrationId(String eniSignerAdministrationId) {
    this.eniSignerAdministrationId = eniSignerAdministrationId;
    return this;
  }

   /**
   * Get eniSignerAdministrationId
   * @return eniSignerAdministrationId
  **/
 
  public String getEniSignerAdministrationId() {
    return eniSignerAdministrationId;
  }

  public void setEniSignerAdministrationId(String eniSignerAdministrationId) {
    this.eniSignerAdministrationId = eniSignerAdministrationId;
  }

  public MassiveScanWebSimpleSignedFileInfo eniSignLevel(String eniSignLevel) {
    this.eniSignLevel = eniSignLevel;
    return this;
  }

   /**
   * Get eniSignLevel
   * @return eniSignLevel
  **/
 
  public String getEniSignLevel() {
    return eniSignLevel;
  }

  public void setEniSignLevel(String eniSignLevel) {
    this.eniSignLevel = eniSignLevel;
  }

  public MassiveScanWebSimpleSignedFileInfo validationInfo(MassiveScanWebSimpleValidationInfo validationInfo) {
    this.validationInfo = validationInfo;
    return this;
  }

   /**
   * Get validationInfo
   * @return validationInfo
  **/
 
  public MassiveScanWebSimpleValidationInfo getValidationInfo() {
    return validationInfo;
  }

  public void setValidationInfo(MassiveScanWebSimpleValidationInfo validationInfo) {
    this.validationInfo = validationInfo;
  }

  public MassiveScanWebSimpleSignedFileInfo additionInformation(List<MassiveScanWebSimpleKeyValue> additionInformation) {
    this.additionInformation = additionInformation;
    return this;
  }

  public MassiveScanWebSimpleSignedFileInfo addAdditionInformationItem(MassiveScanWebSimpleKeyValue additionInformationItem) {
    if (this.additionInformation == null) {
      this.additionInformation = new ArrayList<MassiveScanWebSimpleKeyValue>();
    }
    this.additionInformation.add(additionInformationItem);
    return this;
  }

   /**
   * Get additionInformation
   * @return additionInformation
  **/
 
  public List<MassiveScanWebSimpleKeyValue> getAdditionInformation() {
    return additionInformation;
  }

  public void setAdditionInformation(List<MassiveScanWebSimpleKeyValue> additionInformation) {
    this.additionInformation = additionInformation;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleSignedFileInfo massiveScanWebSimpleSignedFileInfo = (MassiveScanWebSimpleSignedFileInfo) o;
    return Objects.equals(this.signOperation, massiveScanWebSimpleSignedFileInfo.signOperation) &&
        Objects.equals(this.signType, massiveScanWebSimpleSignedFileInfo.signType) &&
        Objects.equals(this.signAlgorithm, massiveScanWebSimpleSignedFileInfo.signAlgorithm) &&
        Objects.equals(this.signMode, massiveScanWebSimpleSignedFileInfo.signMode) &&
        Objects.equals(this.signaturesTableLocation, massiveScanWebSimpleSignedFileInfo.signaturesTableLocation) &&
        Objects.equals(this.timeStampIncluded, massiveScanWebSimpleSignedFileInfo.timeStampIncluded) &&
        Objects.equals(this.policyIncluded, massiveScanWebSimpleSignedFileInfo.policyIncluded) &&
        Objects.equals(this.eniTipoFirma, massiveScanWebSimpleSignedFileInfo.eniTipoFirma) &&
        Objects.equals(this.eniPerfilFirma, massiveScanWebSimpleSignedFileInfo.eniPerfilFirma) &&
        Objects.equals(this.eniRolFirma, massiveScanWebSimpleSignedFileInfo.eniRolFirma) &&
        Objects.equals(this.eniSignerName, massiveScanWebSimpleSignedFileInfo.eniSignerName) &&
        Objects.equals(this.eniSignerAdministrationId, massiveScanWebSimpleSignedFileInfo.eniSignerAdministrationId) &&
        Objects.equals(this.eniSignLevel, massiveScanWebSimpleSignedFileInfo.eniSignLevel) &&
        Objects.equals(this.validationInfo, massiveScanWebSimpleSignedFileInfo.validationInfo) &&
        Objects.equals(this.additionInformation, massiveScanWebSimpleSignedFileInfo.additionInformation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(signOperation, signType, signAlgorithm, signMode, signaturesTableLocation, timeStampIncluded, policyIncluded, eniTipoFirma, eniPerfilFirma, eniRolFirma, eniSignerName, eniSignerAdministrationId, eniSignLevel, validationInfo, additionInformation);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleSignedFileInfo {\n");
    
    sb.append("    signOperation: ").append(toIndentedString(signOperation)).append("\n");
    sb.append("    signType: ").append(toIndentedString(signType)).append("\n");
    sb.append("    signAlgorithm: ").append(toIndentedString(signAlgorithm)).append("\n");
    sb.append("    signMode: ").append(toIndentedString(signMode)).append("\n");
    sb.append("    signaturesTableLocation: ").append(toIndentedString(signaturesTableLocation)).append("\n");
    sb.append("    timeStampIncluded: ").append(toIndentedString(timeStampIncluded)).append("\n");
    sb.append("    policyIncluded: ").append(toIndentedString(policyIncluded)).append("\n");
    sb.append("    eniTipoFirma: ").append(toIndentedString(eniTipoFirma)).append("\n");
    sb.append("    eniPerfilFirma: ").append(toIndentedString(eniPerfilFirma)).append("\n");
    sb.append("    eniRolFirma: ").append(toIndentedString(eniRolFirma)).append("\n");
    sb.append("    eniSignerName: ").append(toIndentedString(eniSignerName)).append("\n");
    sb.append("    eniSignerAdministrationId: ").append(toIndentedString(eniSignerAdministrationId)).append("\n");
    sb.append("    eniSignLevel: ").append(toIndentedString(eniSignLevel)).append("\n");
    sb.append("    validationInfo: ").append(toIndentedString(validationInfo)).append("\n");
    sb.append("    additionInformation: ").append(toIndentedString(additionInformation)).append("\n");
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
