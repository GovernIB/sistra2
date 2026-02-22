package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

public class RSignatureStatus {

    private String signID;

    private RProcessStatus status;

    public String getSignID() {
        return signID;
    }

    public void setSignID(String signID) {
        this.signID = signID;
    }

    public RProcessStatus getStatus() {
        return status;
    }

    public void setStatus(RProcessStatus status) {
        this.status = status;
    }
}
