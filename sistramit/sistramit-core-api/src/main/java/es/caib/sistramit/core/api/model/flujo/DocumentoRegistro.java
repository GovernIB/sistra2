package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;


// TODO SI SE PASA FIRMA AL PASO DE REGISTRO, HABRIA QUE REPLANTEAR

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

}
