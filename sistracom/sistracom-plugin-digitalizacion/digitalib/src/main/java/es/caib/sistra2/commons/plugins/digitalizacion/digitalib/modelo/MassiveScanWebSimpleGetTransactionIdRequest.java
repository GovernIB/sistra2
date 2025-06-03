package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;



/**
 * MassiveScanWebSimpleGetTransactionIdRequest
 */

public class MassiveScanWebSimpleGetTransactionIdRequest {

  private String scanWebProfile = null;


  private Integer view = 1;


  private String languageUI = "ca";


  private String transactionName = null;


  private String funcionariUsername = null;


  private MassiveScanWebSimpleSignatureParameters signatureParameters = null;


  private MassiveScanWebSimpleArxiuRequiredParameters arxiuRequiredParameters = null;


  private MassiveScanWebSimpleArxiuOptionalParameters arxiuOptionalParameters = null;

  public MassiveScanWebSimpleGetTransactionIdRequest scanWebProfile(String scanWebProfile) {
    this.scanWebProfile = scanWebProfile;
    return this;
  }

   /**
   * Perfil d&#x27;escaneig
   * @return scanWebProfile
  **/

  public String getScanWebProfile() {
    return scanWebProfile;
  }

  public void setScanWebProfile(String scanWebProfile) {
    this.scanWebProfile = scanWebProfile;
  }

  public MassiveScanWebSimpleGetTransactionIdRequest view(Integer view) {
    this.view = view;
    return this;
  }

   /**
   * Tipus de Vista. Veure MassiveScanWebSimpleConstants.
   * @return view
  **/

  public Integer getView() {
    return view;
  }

  public void setView(Integer view) {
    this.view = view;
  }

  public MassiveScanWebSimpleGetTransactionIdRequest languageUI(String languageUI) {
    this.languageUI = languageUI;
    return this;
  }

   /**
   * Idioma del document en ISO 639-1. Valors permesos &#x27;ca&#x27; i &#x27;es&#x27;
   * @return languageUI
  **/

  public String getLanguageUI() {
    return languageUI;
  }

  public void setLanguageUI(String languageUI) {
    this.languageUI = languageUI;
  }

  public MassiveScanWebSimpleGetTransactionIdRequest transactionName(String transactionName) {
    this.transactionName = transactionName;
    return this;
  }

   /**
   * Nom descriptiu de la transacció d&#x27;escaneig.
   * @return transactionName
  **/

  public String getTransactionName() {
    return transactionName;
  }

  public void setTransactionName(String transactionName) {
    this.transactionName = transactionName;
  }

  public MassiveScanWebSimpleGetTransactionIdRequest funcionariUsername(String funcionariUsername) {
    this.funcionariUsername = funcionariUsername;
    return this;
  }

   /**
   * Username del funcionari.
   * @return funcionariUsername
  **/

  public String getFuncionariUsername() {
    return funcionariUsername;
  }

  public void setFuncionariUsername(String funcionariUsername) {
    this.funcionariUsername = funcionariUsername;
  }

  public MassiveScanWebSimpleGetTransactionIdRequest signatureParameters(MassiveScanWebSimpleSignatureParameters signatureParameters) {
    this.signatureParameters = signatureParameters;
    return this;
  }

   /**
   * Get signatureParameters
   * @return signatureParameters
  **/
 
  public MassiveScanWebSimpleSignatureParameters getSignatureParameters() {
    return signatureParameters;
  }

  public void setSignatureParameters(MassiveScanWebSimpleSignatureParameters signatureParameters) {
    this.signatureParameters = signatureParameters;
  }

  public MassiveScanWebSimpleGetTransactionIdRequest arxiuRequiredParameters(MassiveScanWebSimpleArxiuRequiredParameters arxiuRequiredParameters) {
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

  public MassiveScanWebSimpleGetTransactionIdRequest arxiuOptionalParameters(MassiveScanWebSimpleArxiuOptionalParameters arxiuOptionalParameters) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleGetTransactionIdRequest massiveScanWebSimpleGetTransactionIdRequest = (MassiveScanWebSimpleGetTransactionIdRequest) o;
    return Objects.equals(this.scanWebProfile, massiveScanWebSimpleGetTransactionIdRequest.scanWebProfile) &&
        Objects.equals(this.view, massiveScanWebSimpleGetTransactionIdRequest.view) &&
        Objects.equals(this.languageUI, massiveScanWebSimpleGetTransactionIdRequest.languageUI) &&
        Objects.equals(this.transactionName, massiveScanWebSimpleGetTransactionIdRequest.transactionName) &&
        Objects.equals(this.funcionariUsername, massiveScanWebSimpleGetTransactionIdRequest.funcionariUsername) &&
        Objects.equals(this.signatureParameters, massiveScanWebSimpleGetTransactionIdRequest.signatureParameters) &&
        Objects.equals(this.arxiuRequiredParameters, massiveScanWebSimpleGetTransactionIdRequest.arxiuRequiredParameters) &&
        Objects.equals(this.arxiuOptionalParameters, massiveScanWebSimpleGetTransactionIdRequest.arxiuOptionalParameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scanWebProfile, view, languageUI, transactionName, funcionariUsername, signatureParameters, arxiuRequiredParameters, arxiuOptionalParameters);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleGetTransactionIdRequest {\n");
    
    sb.append("    scanWebProfile: ").append(toIndentedString(scanWebProfile)).append("\n");
    sb.append("    view: ").append(toIndentedString(view)).append("\n");
    sb.append("    languageUI: ").append(toIndentedString(languageUI)).append("\n");
    sb.append("    transactionName: ").append(toIndentedString(transactionName)).append("\n");
    sb.append("    funcionariUsername: ").append(toIndentedString(funcionariUsername)).append("\n");
    sb.append("    signatureParameters: ").append(toIndentedString(signatureParameters)).append("\n");
    sb.append("    arxiuRequiredParameters: ").append(toIndentedString(arxiuRequiredParameters)).append("\n");
    sb.append("    arxiuOptionalParameters: ").append(toIndentedString(arxiuOptionalParameters)).append("\n");
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
