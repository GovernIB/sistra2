package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Datos del justificante en el paso guardar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosGuardarJustificante implements Serializable {

    /**
     * Indica si es preregistro.
     */
    private TypeSiNo preregistro = TypeSiNo.NO;

    /**
     * Número de registro.
     */
    private String numero;

    /**
     * Fecha de registro.
     */
    private String fecha;

    /**
     * Asunto.
     */
    private String asunto;

    /**
     * Solicitante.
     */
    private Persona solicitante;

    /**
     * Documentos registrados.
     */
    private List<DocumentosRegistroPorTipo> documentos;

    /**
     * Método de acceso a numero.
     *
     * @return numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Método para establecer numero.
     *
     * @param pNumero
     *            numero a establecer
     */
    public void setNumero(final String pNumero) {
        numero = pNumero;
    }

    /**
     * Método de acceso a fecha.
     *
     * @return fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Método para establecer fecha.
     *
     * @param pFecha
     *            fecha a establecer
     */
    public void setFecha(final String pFecha) {
        fecha = pFecha;
    }

    /**
     * Método de acceso a asunto.
     *
     * @return asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Método para establecer asunto.
     *
     * @param pAsunto
     *            asunto a establecer
     */
    public void setAsunto(final String pAsunto) {
        asunto = pAsunto;
    }

    /**
     * Método de acceso a solicitante.
     *
     * @return solicitante
     */
    public Persona getSolicitante() {
        return solicitante;
    }

    /**
     * Método para establecer solicitante.
     *
     * @param pSolicitante
     *            solicitante a establecer
     */
    public void setSolicitante(final Persona pSolicitante) {
        solicitante = pSolicitante;
    }

    /**
     * Método de acceso a documentos.
     *
     * @return documentos
     */
    public List<DocumentosRegistroPorTipo> getDocumentos() {
        return documentos;
    }

    /**
     * Método para establecer documentos.
     *
     * @param pDocumentos
     *            documentos a establecer
     */
    public void setDocumentos(
            final List<DocumentosRegistroPorTipo> pDocumentos) {
        documentos = pDocumentos;
    }

    /**
     * Método de acceso a preregistro.
     *
     * @return preregistro
     */
    public TypeSiNo getPreregistro() {
        return preregistro;
    }

    /**
     * Método para establecer preregistro.
     *
     * @param pPreregistro
     *            preregistro a establecer
     */
    public void setPreregistro(final TypeSiNo pPreregistro) {
        preregistro = pPreregistro;
    }

}
