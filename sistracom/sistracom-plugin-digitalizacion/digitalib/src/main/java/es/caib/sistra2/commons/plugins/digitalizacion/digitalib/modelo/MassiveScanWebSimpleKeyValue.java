package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;

/**
 * MassiveScanWebSimpleKeyValue
 */
public class MassiveScanWebSimpleKeyValue {

  private String key = null;


  private String value = null;

  public MassiveScanWebSimpleKeyValue key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
 
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public MassiveScanWebSimpleKeyValue value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
 
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleKeyValue massiveScanWebSimpleKeyValue = (MassiveScanWebSimpleKeyValue) o;
    return Objects.equals(this.key, massiveScanWebSimpleKeyValue.key) &&
        Objects.equals(this.value, massiveScanWebSimpleKeyValue.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleKeyValue {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
