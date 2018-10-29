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
     * Indica firmante.
     */
    private Persona firmante;

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
     * @param pFirmante
     *            firmante
     * @param pEstadoFirma
     *            Parámetro estado firma
     * @param pFechaFirma
     *            Parámetro fecha firma
     */
    public Firma(final Persona pFirmante, final TypeEstadoFirma pEstadoFirma,
            final String pFechaFirma) {
        super();
        firmante = pFirmante;
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

}
