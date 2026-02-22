package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

public class RValidationInfo {

    private Boolean checkAdministrationIDOfSigner;

    private Boolean checkDocumentModifications;

    private Boolean checkValidationSignature;

    private String noCheckValidationReason;

    public Boolean getCheckAdministrationIDOfSigner() {
        return checkAdministrationIDOfSigner;
    }

    public void setCheckAdministrationIDOfSigner(Boolean checkAdministrationIDOfSigner) {
        this.checkAdministrationIDOfSigner = checkAdministrationIDOfSigner;
    }

    public Boolean getCheckDocumentModifications() {
        return checkDocumentModifications;
    }

    public void setCheckDocumentModifications(Boolean checkDocumentModifications) {
        this.checkDocumentModifications = checkDocumentModifications;
    }

    public Boolean getCheckValidationSignature() {
        return checkValidationSignature;
    }

    public void setCheckValidationSignature(Boolean checkValidationSignature) {
        this.checkValidationSignature = checkValidationSignature;
    }

    public String getNoCheckValidationReason() {
        return noCheckValidationReason;
    }

    public void setNoCheckValidationReason(String noCheckValidationReason) {
        this.noCheckValidationReason = noCheckValidationReason;
    }
}
