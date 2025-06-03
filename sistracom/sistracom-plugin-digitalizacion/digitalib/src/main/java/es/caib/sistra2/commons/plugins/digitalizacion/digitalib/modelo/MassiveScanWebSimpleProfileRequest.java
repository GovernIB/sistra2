package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;


/**
 * MassiveScanWebSimpleProfileRequest
 */
public class MassiveScanWebSimpleProfileRequest {

  private String profileCode = null;


  private String locale = null;

  public MassiveScanWebSimpleProfileRequest profileCode(String profileCode) {
    this.profileCode = profileCode;
    return this;
  }

   /**
   * Get profileCode
   * @return profileCode
  **/
 
  public String getProfileCode() {
    return profileCode;
  }

  public void setProfileCode(String profileCode) {
    this.profileCode = profileCode;
  }

  public MassiveScanWebSimpleProfileRequest locale(String locale) {
    this.locale = locale;
    return this;
  }

   /**
   * Get locale
   * @return locale
  **/
 
  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleProfileRequest massiveScanWebSimpleProfileRequest = (MassiveScanWebSimpleProfileRequest) o;
    return Objects.equals(this.profileCode, massiveScanWebSimpleProfileRequest.profileCode) &&
        Objects.equals(this.locale, massiveScanWebSimpleProfileRequest.locale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(profileCode, locale);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleProfileRequest {\n");
    
    sb.append("    profileCode: ").append(toIndentedString(profileCode)).append("\n");
    sb.append("    locale: ").append(toIndentedString(locale)).append("\n");
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
