package es.caib.sistramit.core.api.model.flujo;

/**
 * Fichero anexo.
 *
 * @author Indra
 *
 */
public class AnexoFichero {

    /** Nombre fichero. */
    private String fileName;

    /** Contenido fichero. */
    private byte[] fileContent;

    /** ContenType. */
    private String fileContentType;

    /**
     * Método de acceso a fileName.
     * 
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Método para establecer fileName.
     * 
     * @param fileName
     *            fileName a establecer
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Método de acceso a fileContent.
     * 
     * @return fileContent
     */
    public byte[] getFileContent() {
        return fileContent;
    }

    /**
     * Método para establecer fileContent.
     * 
     * @param fileContent
     *            fileContent a establecer
     */
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * Método de acceso a fileContentType.
     * 
     * @return fileContentType
     */
    public String getFileContentType() {
        return fileContentType;
    }

    /**
     * Método para establecer fileContentType.
     * 
     * @param fileContentType
     *            fileContentType a establecer
     */
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

}
