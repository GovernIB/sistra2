package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model;

public class RCustodyInfo {

    private String custodyID;

    private String csv;

    private String csvValidationWeb;

    private String validationFileUrl;

    private String csvGenerationDefinition;

    private String originalFileDirectURL;

    private String printableFileDirectUrl;

    private String eniFileDirectUrl;

    private String expedientID;

    private String documentID;

    public String getCustodyID() {
        return custodyID;
    }

    public void setCustodyID(String custodyID) {
        this.custodyID = custodyID;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

    public String getCsvValidationWeb() {
        return csvValidationWeb;
    }

    public void setCsvValidationWeb(String csvValidationWeb) {
        this.csvValidationWeb = csvValidationWeb;
    }

    public String getValidationFileUrl() {
        return validationFileUrl;
    }

    public void setValidationFileUrl(String validationFileUrl) {
        this.validationFileUrl = validationFileUrl;
    }

    public String getCsvGenerationDefinition() {
        return csvGenerationDefinition;
    }

    public void setCsvGenerationDefinition(String csvGenerationDefinition) {
        this.csvGenerationDefinition = csvGenerationDefinition;
    }

    public String getOriginalFileDirectURL() {
        return originalFileDirectURL;
    }

    public void setOriginalFileDirectURL(String originalFileDirectURL) {
        this.originalFileDirectURL = originalFileDirectURL;
    }

    public String getPrintableFileDirectUrl() {
        return printableFileDirectUrl;
    }

    public void setPrintableFileDirectUrl(String printableFileDirectUrl) {
        this.printableFileDirectUrl = printableFileDirectUrl;
    }

    public String getEniFileDirectUrl() {
        return eniFileDirectUrl;
    }

    public void setEniFileDirectUrl(String eniFileDirectUrl) {
        this.eniFileDirectUrl = eniFileDirectUrl;
    }

    public String getExpedientID() {
        return expedientID;
    }

    public void setExpedientID(String expedientID) {
        this.expedientID = expedientID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
}
