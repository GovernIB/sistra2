package es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
/**
 * MassiveScanWebSimpleSubTransactionsOfTransaction
 */



public class MassiveScanWebSimpleSubTransactionsOfTransaction {

  private String transactionID = null;


  private List<String> subtransacions = null;

  public MassiveScanWebSimpleSubTransactionsOfTransaction transactionID(String transactionID) {
    this.transactionID = transactionID;
    return this;
  }

   /**
   * Get transactionID
   * @return transactionID
  **/
 
  public String getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(String transactionID) {
    this.transactionID = transactionID;
  }

  public MassiveScanWebSimpleSubTransactionsOfTransaction subtransacions(List<String> subtransacions) {
    this.subtransacions = subtransacions;
    return this;
  }

  public MassiveScanWebSimpleSubTransactionsOfTransaction addSubtransacionsItem(String subtransacionsItem) {
    if (this.subtransacions == null) {
      this.subtransacions = new ArrayList<String>();
    }
    this.subtransacions.add(subtransacionsItem);
    return this;
  }

   /**
   * Get subtransacions
   * @return subtransacions
  **/
 
  public List<String> getSubtransacions() {
    return subtransacions;
  }

  public void setSubtransacions(List<String> subtransacions) {
    this.subtransacions = subtransacions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MassiveScanWebSimpleSubTransactionsOfTransaction massiveScanWebSimpleSubTransactionsOfTransaction = (MassiveScanWebSimpleSubTransactionsOfTransaction) o;
    return Objects.equals(this.transactionID, massiveScanWebSimpleSubTransactionsOfTransaction.transactionID) &&
        Objects.equals(this.subtransacions, massiveScanWebSimpleSubTransactionsOfTransaction.subtransacions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionID, subtransacions);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MassiveScanWebSimpleSubTransactionsOfTransaction {\n");
    
    sb.append("    transactionID: ").append(toIndentedString(transactionID)).append("\n");
    sb.append("    subtransacions: ").append(toIndentedString(subtransacions)).append("\n");
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
