package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * MassiveScanWebSimpleAvailableProfiles
 */



public class MassiveScanWebSimpleAvailableProfiles {

  private List<MassiveScanWebSimpleAvailableProfile> availableProfiles = null;

  public MassiveScanWebSimpleAvailableProfiles availableProfiles(List<MassiveScanWebSimpleAvailableProfile> availableProfiles) {
    this.availableProfiles = availableProfiles;
    return this;
  }

  public MassiveScanWebSimpleAvailableProfiles addAvailableProfilesItem(MassiveScanWebSimpleAvailableProfile availableProfilesItem) {
    if (this.availableProfiles == null) {
      this.availableProfiles = new ArrayList<MassiveScanWebSimpleAvailableProfile>();
    }
    this.availableProfiles.add(availableProfilesItem);
    return this;
  }

   /**
   * Get availableProfiles
   * @return availableProfiles
  **/
 
  public List<MassiveScanWebSimpleAvailableProfile> getAvailableProfiles() {
    return availableProfiles;
  }

  public void setAvailableProfiles(List<MassiveScanWebSimpleAvailableProfile> availableProfiles) {
    this.availableProfiles = availableProfiles;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleAvailableProfiles massiveScanWebSimpleAvailableProfiles = (MassiveScanWebSimpleAvailableProfiles) o;
    return Objects.equals(this.availableProfiles, massiveScanWebSimpleAvailableProfiles.availableProfiles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(availableProfiles);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleAvailableProfiles {\n");
    
    sb.append("    availableProfiles: ").append(toIndentedString(availableProfiles)).append("\n");
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
