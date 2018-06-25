package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Detalle del paso "Registrar".
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetallePasoRegistrar extends DetallePaso {

    /**
     * Lista de documentos a registrar (formularios, anexos y pagos).
     */
    private List<DocumentosRegistroPorTipo> documentos = new ArrayList<>();

    /**
     * Indica presentador. Por defecto el usuario que realiza el trámite. Se
     * debe establecer siempre.
     */
    private Persona presentador;

    /**
     * Indica si existe representado.
     */
    private Persona representado;

    /** Indica si es un preregistro. */
    private TypeSiNo preregistro = TypeSiNo.NO;

    /**
     * Indica si se debe reintentar el registro o iniciarlo de nuevo.
     */
    private TypeSiNo reintentar = TypeSiNo.NO;

    /**
     * Constructor.
     */
    public DetallePasoRegistrar() {
        super();
        this.setTipo(TypePaso.REGISTRAR);
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
     * Método de acceso a presentador.
     *
     * @return presentador
     */
    public Persona getPresentador() {
        return presentador;
    }

    /**
     * Método para establecer presentador.
     *
     * @param pPresentador
     *            presentador a establecer
     */
    public void setPresentador(final Persona pPresentador) {
        presentador = pPresentador;
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

    /**
     * Método de acceso a representado.
     *
     * @return representado
     */
    public Persona getRepresentado() {
        return representado;
    }

    /**
     * Método para establecer representado.
     *
     * @param pRepresentado
     *            representado a establecer
     */
    public void setRepresentado(final Persona pRepresentado) {
        representado = pRepresentado;
    }

    /**
     * Método de acceso a reintentar.
     *
     * @return reintentar
     */
    public TypeSiNo getReintentar() {
        return reintentar;
    }

    /**
     * Método para establecer reintentar.
     *
     * @param pReintentar
     *            reintentar a establecer
     */
    public void setReintentar(final TypeSiNo pReintentar) {
        reintentar = pReintentar;
    }

    @Override
    public String print() {
        final String ident = "    ";
        final int capacity = ConstantesNumero.N2 * ConstantesNumero.N1024;
        final StringBuffer strb = new StringBuffer(capacity);
        strb.append(ident).append("\n");
        strb.append(ident).append("ID paso:" + getId() + "\n");
        strb.append(ident).append("Tipo:" + getTipo() + "\n");
        strb.append(ident).append("Completado:" + getCompletado() + "\n");
        strb.append(ident).append("Sólo lectura:" + getSoloLectura());
        strb.append(ident).append("Reintentar registro:" + getReintentar());
        return strb.toString();
    }

}
