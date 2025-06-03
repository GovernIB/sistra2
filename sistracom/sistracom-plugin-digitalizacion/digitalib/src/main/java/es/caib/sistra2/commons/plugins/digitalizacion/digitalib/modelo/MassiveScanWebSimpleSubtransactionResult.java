package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * MassiveScanWebSimpleSubtransactionResult
 */
public class MassiveScanWebSimpleSubtransactionResult {

  private Long transactionID = null;


  private String transactionWebID = null;


  private MassiveScanWebSimpleStatus status = null;


  private MassiveScanWebSimpleFile scannedFile = null;

  private MassiveScanWebSimpleScannedFileInfo scannedFileInfo = null;


  private MassiveScanWebSimpleFile signedFile = null;


  private MassiveScanWebSimpleFile detachedSignatureFile = null;


  private MassiveScanWebSimpleSignedFileInfo signedFileInfo = null;


  private MassiveScanWebSimpleCustodyInfo custodyInfo = null;


  private MassiveScanWebSimpleArxiuInfo arxiuInfo = null;


  private MassiveScanWebSimpleArxiuRequiredParameters arxiuRequiredParameters = null;


  private MassiveScanWebSimpleArxiuOptionalParameters arxiuOptionalParameters = null;


  private List<MassiveScanWebSimpleKeyValue> additionalMetadatas = null;

  public MassiveScanWebSimpleSubtransactionResult transactionID(Long transactionID) {
    this.transactionID = transactionID;
    return this;
  }

   /**
   * Get transactionID
   * @return transactionID
  **/
 
  public Long getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(Long transactionID) {
    this.transactionID = transactionID;
  }

  public MassiveScanWebSimpleSubtransactionResult transactionWebID(String transactionWebID) {
    this.transactionWebID = transactionWebID;
    return this;
  }

   /**
   * Get transactionWebID
   * @return transactionWebID
  **/
 
  public String getTransactionWebID() {
    return transactionWebID;
  }

  public void setTransactionWebID(String transactionWebID) {
    this.transactionWebID = transactionWebID;
  }

  public MassiveScanWebSimpleSubtransactionResult status(MassiveScanWebSimpleStatus status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
 
  public MassiveScanWebSimpleStatus getStatus() {
    return status;
  }

  public void setStatus(MassiveScanWebSimpleStatus status) {
    this.status = status;
  }

  public MassiveScanWebSimpleSubtransactionResult scannedFile(MassiveScanWebSimpleFile scannedFile) {
    this.scannedFile = scannedFile;
    return this;
  }

   /**
   * Get scannedFile
   * @return scannedFile
  **/
 
  public MassiveScanWebSimpleFile getScannedFile() {
    return scannedFile;
  }

  public void setScannedFile(MassiveScanWebSimpleFile scannedFile) {
    this.scannedFile = scannedFile;
  }

  public MassiveScanWebSimpleSubtransactionResult scannedFileInfo(MassiveScanWebSimpleScannedFileInfo scannedFileInfo) {
    this.scannedFileInfo = scannedFileInfo;
    return this;
  }

   /**
   * Get scannedFileInfo
   * @return scannedFileInfo
  **/
 
  public MassiveScanWebSimpleScannedFileInfo getScannedFileInfo() {
    return scannedFileInfo;
  }

  public void setScannedFileInfo(MassiveScanWebSimpleScannedFileInfo scannedFileInfo) {
    this.scannedFileInfo = scannedFileInfo;
  }

  public MassiveScanWebSimpleSubtransactionResult signedFile(MassiveScanWebSimpleFile signedFile) {
    this.signedFile = signedFile;
    return this;
  }

   /**
   * Get signedFile
   * @return signedFile
  **/
 
  public MassiveScanWebSimpleFile getSignedFile() {
    return signedFile;
  }

  public void setSignedFile(MassiveScanWebSimpleFile signedFile) {
    this.signedFile = signedFile;
  }

  public MassiveScanWebSimpleSubtransactionResult detachedSignatureFile(MassiveScanWebSimpleFile detachedSignatureFile) {
    this.detachedSignatureFile = detachedSignatureFile;
    return this;
  }

   /**
   * Get detachedSignatureFile
   * @return detachedSignatureFile
  **/
 
  public MassiveScanWebSimpleFile getDetachedSignatureFile() {
    return detachedSignatureFile;
  }

  public void setDetachedSignatureFile(MassiveScanWebSimpleFile detachedSignatureFile) {
    this.detachedSignatureFile = detachedSignatureFile;
  }

  public MassiveScanWebSimpleSubtransactionResult signedFileInfo(MassiveScanWebSimpleSignedFileInfo signedFileInfo) {
    this.signedFileInfo = signedFileInfo;
    return this;
  }

   /**
   * Get signedFileInfo
   * @return signedFileInfo
  **/
 
  public MassiveScanWebSimpleSignedFileInfo getSignedFileInfo() {
    return signedFileInfo;
  }

  public void setSignedFileInfo(MassiveScanWebSimpleSignedFileInfo signedFileInfo) {
    this.signedFileInfo = signedFileInfo;
  }

  public MassiveScanWebSimpleSubtransactionResult custodyInfo(MassiveScanWebSimpleCustodyInfo custodyInfo) {
    this.custodyInfo = custodyInfo;
    return this;
  }

   /**
   * Get custodyInfo
   * @return custodyInfo
  **/
 
  public MassiveScanWebSimpleCustodyInfo getCustodyInfo() {
    return custodyInfo;
  }

  public void setCustodyInfo(MassiveScanWebSimpleCustodyInfo custodyInfo) {
    this.custodyInfo = custodyInfo;
  }

  public MassiveScanWebSimpleSubtransactionResult arxiuInfo(MassiveScanWebSimpleArxiuInfo arxiuInfo) {
    this.arxiuInfo = arxiuInfo;
    return this;
  }

   /**
   * Get arxiuInfo
   * @return arxiuInfo
  **/
 
  public MassiveScanWebSimpleArxiuInfo getArxiuInfo() {
    return arxiuInfo;
  }

  public void setArxiuInfo(MassiveScanWebSimpleArxiuInfo arxiuInfo) {
    this.arxiuInfo = arxiuInfo;
  }

  public MassiveScanWebSimpleSubtransactionResult arxiuRequiredParameters(MassiveScanWebSimpleArxiuRequiredParameters arxiuRequiredParameters) {
    this.arxiuRequiredParameters = arxiuRequiredParameters;
    return this;
  }

   /**
   * Get arxiuRequiredParameters
   * @return arxiuRequiredParameters
  **/
 
  public MassiveScanWebSimpleArxiuRequiredParameters getArxiuRequiredParameters() {
    return arxiuRequiredParameters;
  }

  public void setArxiuRequiredParameters(MassiveScanWebSimpleArxiuRequiredParameters arxiuRequiredParameters) {
    this.arxiuRequiredParameters = arxiuRequiredParameters;
  }

  public MassiveScanWebSimpleSubtransactionResult arxiuOptionalParameters(MassiveScanWebSimpleArxiuOptionalParameters arxiuOptionalParameters) {
    this.arxiuOptionalParameters = arxiuOptionalParameters;
    return this;
  }

   /**
   * Get arxiuOptionalParameters
   * @return arxiuOptionalParameters
  **/
 
  public MassiveScanWebSimpleArxiuOptionalParameters getArxiuOptionalParameters() {
    return arxiuOptionalParameters;
  }

  public void setArxiuOptionalParameters(MassiveScanWebSimpleArxiuOptionalParameters arxiuOptionalParameters) {
    this.arxiuOptionalParameters = arxiuOptionalParameters;
  }

  public MassiveScanWebSimpleSubtransactionResult additionalMetadatas(List<MassiveScanWebSimpleKeyValue> additionalMetadatas) {
    this.additionalMetadatas = additionalMetadatas;
    return this;
  }

  public MassiveScanWebSimpleSubtransactionResult addAdditionalMetadatasItem(MassiveScanWebSimpleKeyValue additionalMetadatasItem) {
    if (this.additionalMetadatas == null) {
      this.additionalMetadatas = new ArrayList<MassiveScanWebSimpleKeyValue>();
    }
    this.additionalMetadatas.add(additionalMetadatasItem);
    return this;
  }

   /**
   * Get additionalMetadatas
   * @return additionalMetadatas
  **/
 
  public List<MassiveScanWebSimpleKeyValue> getAdditionalMetadatas() {
    return additionalMetadatas;
  }

  public void setAdditionalMetadatas(List<MassiveScanWebSimpleKeyValue> additionalMetadatas) {
    this.additionalMetadatas = additionalMetadatas;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleSubtransactionResult massiveScanWebSimpleSubtransactionResult = (MassiveScanWebSimpleSubtransactionResult) o;
    return Objects.equals(this.transactionID, massiveScanWebSimpleSubtransactionResult.transactionID) &&
        Objects.equals(this.transactionWebID, massiveScanWebSimpleSubtransactionResult.transactionWebID) &&
        Objects.equals(this.status, massiveScanWebSimpleSubtransactionResult.status) &&
        Objects.equals(this.scannedFile, massiveScanWebSimpleSubtransactionResult.scannedFile) &&
        Objects.equals(this.scannedFileInfo, massiveScanWebSimpleSubtransactionResult.scannedFileInfo) &&
        Objects.equals(this.signedFile, massiveScanWebSimpleSubtransactionResult.signedFile) &&
        Objects.equals(this.detachedSignatureFile, massiveScanWebSimpleSubtransactionResult.detachedSignatureFile) &&
        Objects.equals(this.signedFileInfo, massiveScanWebSimpleSubtransactionResult.signedFileInfo) &&
        Objects.equals(this.custodyInfo, massiveScanWebSimpleSubtransactionResult.custodyInfo) &&
        Objects.equals(this.arxiuInfo, massiveScanWebSimpleSubtransactionResult.arxiuInfo) &&
        Objects.equals(this.arxiuRequiredParameters, massiveScanWebSimpleSubtransactionResult.arxiuRequiredParameters) &&
        Objects.equals(this.arxiuOptionalParameters, massiveScanWebSimpleSubtransactionResult.arxiuOptionalParameters) &&
        Objects.equals(this.additionalMetadatas, massiveScanWebSimpleSubtransactionResult.additionalMetadatas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionID, transactionWebID, status, scannedFile, scannedFileInfo, signedFile, detachedSignatureFile, signedFileInfo, custodyInfo, arxiuInfo, arxiuRequiredParameters, arxiuOptionalParameters, additionalMetadatas);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleSubtransactionResult {\n");
    
    sb.append("    transactionID: ").append(toIndentedString(transactionID)).append("\n");
    sb.append("    transactionWebID: ").append(toIndentedString(transactionWebID)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    scannedFile: ").append(toIndentedString(scannedFile)).append("\n");
    sb.append("    scannedFileInfo: ").append(toIndentedString(scannedFileInfo)).append("\n");
    sb.append("    signedFile: ").append(toIndentedString(signedFile)).append("\n");
    sb.append("    detachedSignatureFile: ").append(toIndentedString(detachedSignatureFile)).append("\n");
    sb.append("    signedFileInfo: ").append(toIndentedString(signedFileInfo)).append("\n");
    sb.append("    custodyInfo: ").append(toIndentedString(custodyInfo)).append("\n");
    sb.append("    arxiuInfo: ").append(toIndentedString(arxiuInfo)).append("\n");
    sb.append("    arxiuRequiredParameters: ").append(toIndentedString(arxiuRequiredParameters)).append("\n");
    sb.append("    arxiuOptionalParameters: ").append(toIndentedString(arxiuOptionalParameters)).append("\n");
    sb.append("    additionalMetadatas: ").append(toIndentedString(additionalMetadatas)).append("\n");
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
