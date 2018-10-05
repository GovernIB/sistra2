package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaTramite extends RRespuestaBasicaRolsac {

    /** Resultado. **/
    private RTramiteRolsac resultado;

    /** Constructor. **/
    public RRespuestaTramite(final String status, final String mensaje,
            final Integer numeroElementos, final RTramiteRolsac resultado) {
        super(status, mensaje, numeroElementos);
        this.resultado = resultado;
    }

    /** Constructor. **/
    public RRespuestaTramite() {
        super();
    }

    /**
     * @return the resultado
     */
    public RTramiteRolsac getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *            the resultado to set
     */
    public void setResultado(final RTramiteRolsac resultado) {
        this.resultado = resultado;
    }
}
