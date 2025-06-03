
package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * MassiveScanWebSimpleArxiuRequiredParameters
 */



public class MassiveScanWebSimpleArxiuRequiredParameters {

  private String citizenAdministrationID = null;


  private String citizenFullName = null;


  private String documentElaborationState = null;


  private Integer documentOrigen = null;


  private List<String> interestedPersons = null;

  private List<String> affectedOrganisms = null;

  public MassiveScanWebSimpleArxiuRequiredParameters citizenAdministrationID(String citizenAdministrationID) {
    this.citizenAdministrationID = citizenAdministrationID;
    return this;
  }

   /**
   * Get citizenAdministrationID
   * @return citizenAdministrationID
  **/
 
  public String getCitizenAdministrationID() {
    return citizenAdministrationID;
  }

  public void setCitizenAdministrationID(String citizenAdministrationID) {
    this.citizenAdministrationID = citizenAdministrationID;
  }

  public MassiveScanWebSimpleArxiuRequiredParameters citizenFullName(String citizenFullName) {
    this.citizenFullName = citizenFullName;
    return this;
  }

   /**
   * Get citizenFullName
   * @return citizenFullName
  **/
 
  public String getCitizenFullName() {
    return citizenFullName;
  }

  public void setCitizenFullName(String citizenFullName) {
    this.citizenFullName = citizenFullName;
  }

  public MassiveScanWebSimpleArxiuRequiredParameters documentElaborationState(String documentElaborationState) {
    this.documentElaborationState = documentElaborationState;
    return this;
  }

   /**
   * Get documentElaborationState
   * @return documentElaborationState
  **/
 
  public String getDocumentElaborationState() {
    return documentElaborationState;
  }

  public void setDocumentElaborationState(String documentElaborationState) {
    this.documentElaborationState = documentElaborationState;
  }

  public MassiveScanWebSimpleArxiuRequiredParameters documentOrigen(Integer documentOrigen) {
    this.documentOrigen = documentOrigen;
    return this;
  }

   /**
   * Get documentOrigen
   * @return documentOrigen
  **/
 
  public Integer getDocumentOrigen() {
    return documentOrigen;
  }

  public void setDocumentOrigen(Integer documentOrigen) {
    this.documentOrigen = documentOrigen;
  }

  public MassiveScanWebSimpleArxiuRequiredParameters interestedPersons(List<String> interestedPersons) {
    this.interestedPersons = interestedPersons;
    return this;
  }

  public MassiveScanWebSimpleArxiuRequiredParameters addInterestedPersonsItem(String interestedPersonsItem) {
    if (this.interestedPersons == null) {
      this.interestedPersons = new ArrayList<String>();
    }
    this.interestedPersons.add(interestedPersonsItem);
    return this;
  }

   /**
   * Get interestedPersons
   * @return interestedPersons
  **/
 
  public List<String> getInterestedPersons() {
    return interestedPersons;
  }

  public void setInterestedPersons(List<String> interestedPersons) {
    this.interestedPersons = interestedPersons;
  }

  public MassiveScanWebSimpleArxiuRequiredParameters affectedOrganisms(List<String> affectedOrganisms) {
    this.affectedOrganisms = affectedOrganisms;
    return this;
  }

  public MassiveScanWebSimpleArxiuRequiredParameters addAffectedOrganismsItem(String affectedOrganismsItem) {
    if (this.affectedOrganisms == null) {
      this.affectedOrganisms = new ArrayList<String>();
    }
    this.affectedOrganisms.add(affectedOrganismsItem);
    return this;
  }

   /**
   * Get affectedOrganisms
   * @return affectedOrganisms
  **/
 
  public List<String> getAffectedOrganisms() {
    return affectedOrganisms;
  }

  public void setAffectedOrganisms(List<String> affectedOrganisms) {
    this.affectedOrganisms = affectedOrganisms;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleArxiuRequiredParameters massiveScanWebSimpleArxiuRequiredParameters = (MassiveScanWebSimpleArxiuRequiredParameters) o;
    return Objects.equals(this.citizenAdministrationID, massiveScanWebSimpleArxiuRequiredParameters.citizenAdministrationID) &&
        Objects.equals(this.citizenFullName, massiveScanWebSimpleArxiuRequiredParameters.citizenFullName) &&
        Objects.equals(this.documentElaborationState, massiveScanWebSimpleArxiuRequiredParameters.documentElaborationState) &&
        Objects.equals(this.documentOrigen, massiveScanWebSimpleArxiuRequiredParameters.documentOrigen) &&
        Objects.equals(this.interestedPersons, massiveScanWebSimpleArxiuRequiredParameters.interestedPersons) &&
        Objects.equals(this.affectedOrganisms, massiveScanWebSimpleArxiuRequiredParameters.affectedOrganisms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(citizenAdministrationID, citizenFullName, documentElaborationState, documentOrigen, interestedPersons, affectedOrganisms);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleArxiuRequiredParameters {\n");
    
    sb.append("    citizenAdministrationID: ").append(toIndentedString(citizenAdministrationID)).append("\n");
    sb.append("    citizenFullName: ").append(toIndentedString(citizenFullName)).append("\n");
    sb.append("    documentElaborationState: ").append(toIndentedString(documentElaborationState)).append("\n");
    sb.append("    documentOrigen: ").append(toIndentedString(documentOrigen)).append("\n");
    sb.append("    interestedPersons: ").append(toIndentedString(interestedPersons)).append("\n");
    sb.append("    affectedOrganisms: ").append(toIndentedString(affectedOrganisms)).append("\n");
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
