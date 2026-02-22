package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

public class RStartTransactionRequest {

    private String transactionID;

    private String returnUrl;

    private String view;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
