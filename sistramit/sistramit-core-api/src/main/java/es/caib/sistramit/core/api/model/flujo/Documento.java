package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;

/**
 * Documento asociado a un trámite: formulario, pago o anexo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class Documento implements ModelApi {

    /**
     * Identificador documento.
     */
    private String id;

    /**
     * Indica si se ha rellenado un documento (vacío, rellenado con errores y
     * rellenado correcto).
     */
    private TypeEstadoDocumento rellenado = TypeEstadoDocumento.SIN_RELLENAR;

    /**
     * Indica la obligatoriedad actual de un documento: obligatorio, opcional y
     * dependiente.
     */
    private TypeObligatoriedad obligatorio = TypeObligatoriedad.OBLIGATORIO;

    /**
     * Titulo del documento.
     */
    private String titulo;

    /**
     * Método de acceso a id.
     *
     * @return id
     */
    public final String getId() {
        return id;
    }

    /**
     * Método para establecer id.
     *
     * @param pId
     *            id a establecer
     */
    public final void setId(final String pId) {
        id = pId;
    }

    /**
     * Método de acceso a rellenado.
     *
     * @return rellenado
     */
    public final TypeEstadoDocumento getRellenado() {
        return rellenado;
    }

    /**
     * Método para establecer rellenado.
     *
     * @param pRellenado
     *            rellenado a establecer
     */
    public final void setRellenado(final TypeEstadoDocumento pRellenado) {
        rellenado = pRellenado;
    }

    /**
     * Método de acceso a obligatorio.
     *
     * @return obligatorio
     */
    public final TypeObligatoriedad getObligatorio() {
        return obligatorio;
    }

    /**
     * Método para establecer obligatorio.
     *
     * @param pObligatorio
     *            obligatorio a establecer
     */
    public final void setObligatorio(final TypeObligatoriedad pObligatorio) {
        obligatorio = pObligatorio;
    }

    /**
     * Método de acceso a titulo.
     *
     * @return descripcion
     */
    public final String getTitulo() {
        return titulo;
    }

    /**
     * Método para establecer descripcion.
     *
     * @param pTitulo
     *            Titulo a establecer
     */
    public final void setTitulo(final String pTitulo) {
        titulo = pTitulo;
    }

}
