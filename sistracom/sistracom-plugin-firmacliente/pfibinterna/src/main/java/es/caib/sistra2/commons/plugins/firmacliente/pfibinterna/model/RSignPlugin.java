package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

public class RSignPlugin {

    private String signaturePluginId;

    private String signaturePluginCode;

    private String signaturePluginNameInternal;

    private String signaturePluginNamePublic;

    private String signaturePluginDescriptionPublic;

    public String getSignaturePluginId() {
        return signaturePluginId;
    }

    public void setSignaturePluginId(String signaturePluginId) {
        this.signaturePluginId = signaturePluginId;
    }

    public String getSignaturePluginCode() {
        return signaturePluginCode;
    }

    public void setSignaturePluginCode(String signaturePluginCode) {
        this.signaturePluginCode = signaturePluginCode;
    }

    public String getSignaturePluginNameInternal() {
        return signaturePluginNameInternal;
    }

    public void setSignaturePluginNameInternal(String signaturePluginNameInternal) {
        this.signaturePluginNameInternal = signaturePluginNameInternal;
    }

    public String getSignaturePluginNamePublic() {
        return signaturePluginNamePublic;
    }

    public void setSignaturePluginNamePublic(String signaturePluginNamePublic) {
        this.signaturePluginNamePublic = signaturePluginNamePublic;
    }

    public String getSignaturePluginDescriptionPublic() {
        return signaturePluginDescriptionPublic;
    }

    public void setSignaturePluginDescriptionPublic(String signaturePluginDescriptionPublic) {
        this.signaturePluginDescriptionPublic = signaturePluginDescriptionPublic;
    }
}
