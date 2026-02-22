package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

public class RSignatureResponse {

    private RDocument signedFile;

    private RSignedFileInfo signedFileInfo;

    private String signID;

    private RProcessStatus status;

    public RDocument getSignedFile() {
        return signedFile;
    }

    public void setSignedFile(RDocument signedFile) {
        this.signedFile = signedFile;
    }

    public RSignedFileInfo getSignedFileInfo() {
        return signedFileInfo;
    }

    public void setSignedFileInfo(RSignedFileInfo signedFileInfo) {
        this.signedFileInfo = signedFileInfo;
    }

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
