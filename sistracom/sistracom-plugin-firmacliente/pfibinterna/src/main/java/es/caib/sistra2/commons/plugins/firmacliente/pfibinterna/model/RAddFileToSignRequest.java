package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;


public class RAddFileToSignRequest {

    private String transactionID;

    private RFileInfoSignature fileInfoSignature;


    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public RFileInfoSignature getFileInfoSignature() {
        return fileInfoSignature;
    }

    public void setFileInfoSignature(RFileInfoSignature fileInfoSignature) {
        this.fileInfoSignature = fileInfoSignature;
    }
}
