package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;

/**
 * Indica el estado de una firma.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Firma implements ModelApi {

    /**
     * Indica si ha firmado.
     */
    private TypeEstadoFirma estadoFirma = TypeEstadoFirma.NO_FIRMADO;

    /**
     * En caso de que se haya firmado indica la fecha (dd/mm/aaaa hh:mm).
     */
    private String fechaFirma;

    /**
     * Constructor.
     */
    public Firma() {
        super();
    }

    /**
     * Constructor.
     *
     * @param pEstadoFirma
     *            Parámetro estado firma
     * @param pFechaFirma
     *            Parámetro fecha firma
     */
    public Firma(final TypeEstadoFirma pEstadoFirma, final String pFechaFirma) {
        super();
        estadoFirma = pEstadoFirma;
        fechaFirma = pFechaFirma;
    }

    /**
     * Crea instancia Firma.
     *
     * @return Firma
     */
    public static Firma createNewFirma() {
        return new Firma();
    }

    /**
     * Método de acceso a estadoFirma.
     *
     * @return estadoFirma
     */
    public TypeEstadoFirma getEstadoFirma() {
        return estadoFirma;
    }

    /**
     * Método para establecer estadoFirma.
     *
     * @param pEstadoFirma
     *            estadoFirma a establecer
     */
    public void setEstadoFirma(final TypeEstadoFirma pEstadoFirma) {
        estadoFirma = pEstadoFirma;
    }

    /**
     * Método de acceso a fechaFirma.
     *
     * @return fechaFirma
     */
    public String getFechaFirma() {
        return fechaFirma;
    }

    /**
     * Método para establecer fechaFirma.
     *
     * @param pFechaFirma
     *            fechaFirma a establecer
     */
    public void setFechaFirma(final String pFechaFirma) {
        fechaFirma = pFechaFirma;
    }

}
