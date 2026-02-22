package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

import java.util.ArrayList;
import java.util.List;

public class RTransactionStatusResponse {

    private RProcessStatus transactionStatus;

    private List<RSignatureStatus> signaturesStatusList = new ArrayList();

    private RSignPlugin signPlugin;

    public RProcessStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(RProcessStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public List<RSignatureStatus> getSignaturesStatusList() {
        return signaturesStatusList;
    }

    public void setSignaturesStatusList(List<RSignatureStatus> signaturesStatusList) {
        this.signaturesStatusList = signaturesStatusList;
    }

    public RSignPlugin getSignPlugin() {
        return signPlugin;
    }

    public void setSignPlugin(RSignPlugin signPlugin) {
        this.signPlugin = signPlugin;
    }
}
