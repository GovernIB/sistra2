package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

import java.util.ArrayList;
import java.util.List;

public class RSignerInfo {

    private String eniRolFirma;

    private String eniSignerName;

    private String eniSignerAdministrationId;

    private String eniSignLevel;

    private String signDate;

    private String serialNumberCert;

    private String issuerCert;

    private String subjectCert;

    private RSignPlugin signPlugin;

    private List<RKeyValue> additionalInformation = new ArrayList();


    public String getEniRolFirma() {
        return eniRolFirma;
    }

    public void setEniRolFirma(String eniRolFirma) {
        this.eniRolFirma = eniRolFirma;
    }

    public String getEniSignerName() {
        return eniSignerName;
    }

    public void setEniSignerName(String eniSignerName) {
        this.eniSignerName = eniSignerName;
    }

    public String getEniSignerAdministrationId() {
        return eniSignerAdministrationId;
    }

    public void setEniSignerAdministrationId(String eniSignerAdministrationId) {
        this.eniSignerAdministrationId = eniSignerAdministrationId;
    }

    public String getEniSignLevel() {
        return eniSignLevel;
    }

    public void setEniSignLevel(String eniSignLevel) {
        this.eniSignLevel = eniSignLevel;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getSerialNumberCert() {
        return serialNumberCert;
    }

    public void setSerialNumberCert(String serialNumberCert) {
        this.serialNumberCert = serialNumberCert;
    }

    public String getIssuerCert() {
        return issuerCert;
    }

    public void setIssuerCert(String issuerCert) {
        this.issuerCert = issuerCert;
    }

    public String getSubjectCert() {
        return subjectCert;
    }

    public void setSubjectCert(String subjectCert) {
        this.subjectCert = subjectCert;
    }

    public RSignPlugin getSignPlugin() {
        return signPlugin;
    }

    public void setSignPlugin(RSignPlugin signPlugin) {
        this.signPlugin = signPlugin;
    }

    public List<RKeyValue> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(List<RKeyValue> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
