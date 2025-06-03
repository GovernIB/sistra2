
package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

/**
 * MassiveScanWebSimpleAvailableProfile
 */



public class MassiveScanWebSimpleAvailableProfile {

  private String code = null;


  private String name = null;


  private String description = null;


  private Integer profileType = null;

  public MassiveScanWebSimpleAvailableProfile code(String code) {
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
 
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public MassiveScanWebSimpleAvailableProfile name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
 
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MassiveScanWebSimpleAvailableProfile description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
 
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MassiveScanWebSimpleAvailableProfile profileType(Integer profileType) {
    this.profileType = profileType;
    return this;
  }

   /**
   * Get profileType
   * @return profileType
  **/
 
  public Integer getProfileType() {
    return profileType;
  }

  public void setProfileType(Integer profileType) {
    this.profileType = profileType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleAvailableProfile massiveScanWebSimpleAvailableProfile = (MassiveScanWebSimpleAvailableProfile) o;
    return Objects.equals(this.code, massiveScanWebSimpleAvailableProfile.code) &&
        Objects.equals(this.name, massiveScanWebSimpleAvailableProfile.name) &&
        Objects.equals(this.description, massiveScanWebSimpleAvailableProfile.description) &&
        Objects.equals(this.profileType, massiveScanWebSimpleAvailableProfile.profileType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name, description, profileType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleAvailableProfile {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    profileType: ").append(toIndentedString(profileType)).append("\n");
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
