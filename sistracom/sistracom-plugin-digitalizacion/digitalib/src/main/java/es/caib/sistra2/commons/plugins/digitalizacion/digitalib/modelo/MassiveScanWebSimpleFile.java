package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

/**
 * MassiveScanWebSimpleFile
 */
public class MassiveScanWebSimpleFile {

  private String nom = null;


  private String mime = null;


  private String data = null;

  public MassiveScanWebSimpleFile nom(String nom) {
    this.nom = nom;
    return this;
  }

   /**
   * Get nom
   * @return nom
  **/
 
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public MassiveScanWebSimpleFile mime(String mime) {
    this.mime = mime;
    return this;
  }

   /**
   * Get mime
   * @return mime
  **/
 
  public String getMime() {
    return mime;
  }

  public void setMime(String mime) {
    this.mime = mime;
  }

  public MassiveScanWebSimpleFile data(String data) {
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
 
  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleFile massiveScanWebSimpleFile = (MassiveScanWebSimpleFile) o;
    return Objects.equals(this.nom, massiveScanWebSimpleFile.nom) &&
        Objects.equals(this.mime, massiveScanWebSimpleFile.mime) &&
            Objects.equals(this.data, massiveScanWebSimpleFile.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nom, mime, data);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleFile {\n");
    
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    mime: ").append(toIndentedString(mime)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
