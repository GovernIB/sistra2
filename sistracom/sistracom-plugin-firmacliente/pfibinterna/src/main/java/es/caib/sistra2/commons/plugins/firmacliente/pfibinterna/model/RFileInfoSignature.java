package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

public class RFileInfoSignature {

    private RDocument fileToSign;

    private String signID;

    private String name;

    private String reason;

    private String location;

    private Integer signNumber;

    private String languageSign;

    private Long documentType;

    public RDocument getFileToSign() {
        return fileToSign;
    }

    public void setFileToSign(RDocument fileToSign) {
        this.fileToSign = fileToSign;
    }

    public String getSignID() {
        return signID;
    }

    public void setSignID(String signID) {
        this.signID = signID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(Integer signNumber) {
        this.signNumber = signNumber;
    }

    public String getLanguageSign() {
        return languageSign;
    }

    public void setLanguageSign(String languageSign) {
        this.languageSign = languageSign;
    }

    public Long getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Long documentType) {
        this.documentType = documentType;
    }
}
