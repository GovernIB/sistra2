package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

public class RCommonInfo {

    private String signProfile;

    private String languageUI;

    private String username;

    private String administrationID;

    private String organizationID;

    private String signerEmail;

    public String getSignProfile() {
        return signProfile;
    }

    public void setSignProfile(String signProfile) {
        this.signProfile = signProfile;
    }

    public String getLanguageUI() {
        return languageUI;
    }

    public void setLanguageUI(String languageUI) {
        this.languageUI = languageUI;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdministrationID() {
        return administrationID;
    }

    public void setAdministrationID(String administrationID) {
        this.administrationID = administrationID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getSignerEmail() {
        return signerEmail;
    }

    public void setSignerEmail(String signerEmail) {
        this.signerEmail = signerEmail;
    }
}