package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Documento mostrado en el paso de registro.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DocumentoRegistro implements Serializable {

    /**
     * Identificador documento. Contiene el número de instancia. (id + "-" +
     * instancia).
     */
    private String id;

    /**
     * Titulo documento.
     */
    private String titulo;

    /**
     * Indica si se permite descargar el documento desde la lista.
     */
    private TypeSiNo descargable = TypeSiNo.NO;

    /**
     * Indica si se debe firmar.
     */
    private TypeSiNo firmar = TypeSiNo.NO;

    /**
     * Si se debe firmar indica datos firmante.
     */
    private Persona firmante;

    /**
     * Indica si se ha firmado.
     */
    private TypeSiNo firmado = TypeSiNo.NO;

    /**
     * Método de acceso a id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Método para establecer id.
     *
     * @param pId
     *            id a establecer
     */
    public void setId(final String pId) {
        id = pId;
    }

    /**
     * Método de acceso a titulo.
     *
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Método para establecer titulo.
     *
     * @param pTitulo
     *            titulo a establecer
     */
    public void setTitulo(final String pTitulo) {
        titulo = pTitulo;
    }

    /**
     * Método de acceso a download.
     *
     * @return download
     */
    public TypeSiNo getDescargable() {
        return descargable;
    }

    /**
     * Método para establecer download.
     *
     * @param pDownload
     *            download a establecer
     */
    public void setDescargable(final TypeSiNo pDownload) {
        descargable = pDownload;
    }

    /**
     * Método para Crea new documento registro de la clase DocumentoRegistro.
     *
     * @return el documento registro
     */
    public static DocumentoRegistro createNewDocumentoRegistro() {
        return new DocumentoRegistro();
    }

    /**
     * Método de acceso a firmar.
     * 
     * @return firmar
     */
    public TypeSiNo getFirmar() {
        return firmar;
    }

    /**
     * Método para establecer firmar.
     * 
     * @param firmar
     *            firmar a establecer
     */
    public void setFirmar(TypeSiNo firmar) {
        this.firmar = firmar;
    }

    /**
     * Método de acceso a firmante.
     * 
     * @return firmante
     */
    public Persona getFirmante() {
        return firmante;
    }

    /**
     * Método para establecer firmante.
     * 
     * @param firmante
     *            firmante a establecer
     */
    public void setFirmante(Persona firmante) {
        this.firmante = firmante;
    }

    /**
     * Método de acceso a firmado.
     * 
     * @return firmado
     */
    public TypeSiNo getFirmado() {
        return firmado;
    }

    /**
     * Método para establecer firmado.
     * 
     * @param firmado
     *            firmado a establecer
     */
    public void setFirmado(TypeSiNo firmado) {
        this.firmado = firmado;
    }

}
