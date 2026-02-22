package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

import java.util.ArrayList;
import java.util.List;

public class RSignedFileInfo {

    private Integer signOperation;

    private String signType;

    private String signAlgorithm;

    private Integer signMode;

    private Integer signaturesTableLocation;

    private Boolean timeStampIncluded;

    private Boolean policyIncluded;

    private String eniTipoFirma;

    private String eniPerfilFirma;

    private List<RSignerInfo> signers = new ArrayList();

    private RCustodyInfo custodyInfo;

    private RValidationInfo validationInfo;

    public Integer getSignOperation() {
        return signOperation;
    }

    public void setSignOperation(Integer signOperation) {
        this.signOperation = signOperation;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignAlgorithm() {
        return signAlgorithm;
    }

    public void setSignAlgorithm(String signAlgorithm) {
        this.signAlgorithm = signAlgorithm;
    }

    public Integer getSignMode() {
        return signMode;
    }

    public void setSignMode(Integer signMode) {
        this.signMode = signMode;
    }

    public Integer getSignaturesTableLocation() {
        return signaturesTableLocation;
    }

    public void setSignaturesTableLocation(Integer signaturesTableLocation) {
        this.signaturesTableLocation = signaturesTableLocation;
    }

    public Boolean getTimeStampIncluded() {
        return timeStampIncluded;
    }

    public void setTimeStampIncluded(Boolean timeStampIncluded) {
        this.timeStampIncluded = timeStampIncluded;
    }

    public Boolean getPolicyIncluded() {
        return policyIncluded;
    }

    public void setPolicyIncluded(Boolean policyIncluded) {
        this.policyIncluded = policyIncluded;
    }

    public String getEniTipoFirma() {
        return eniTipoFirma;
    }

    public void setEniTipoFirma(String eniTipoFirma) {
        this.eniTipoFirma = eniTipoFirma;
    }

    public String getEniPerfilFirma() {
        return eniPerfilFirma;
    }

    public void setEniPerfilFirma(String eniPerfilFirma) {
        this.eniPerfilFirma = eniPerfilFirma;
    }

    public List<RSignerInfo> getSigners() {
        return signers;
    }

    public void setSigners(List<RSignerInfo> signers) {
        this.signers = signers;
    }

    public RCustodyInfo getCustodyInfo() {
        return custodyInfo;
    }

    public void setCustodyInfo(RCustodyInfo custodyInfo) {
        this.custodyInfo = custodyInfo;
    }

    public RValidationInfo getValidationInfo() {
        return validationInfo;
    }

    public void setValidationInfo(RValidationInfo validationInfo) {
        this.validationInfo = validationInfo;
    }
}
