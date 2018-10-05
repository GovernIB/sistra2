package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaProcedimiento extends RRespuestaBasicaRolsac {

    /** Resultado. **/
    private RProcedimientoRolsac resultado;

    /** Constructor. **/
    public RRespuestaProcedimiento(final String status, final String mensaje,
            final Integer numeroElementos,
            final RProcedimientoRolsac resultado) {
        super(status, mensaje, numeroElementos);
        this.resultado = resultado;
    }

    /** Constructor. **/
    public RRespuestaProcedimiento() {
        super();
    }

    /**
     * @return the resultado
     */
    public RProcedimientoRolsac getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *            the resultado to set
     */
    public void setResultado(final RProcedimientoRolsac resultado) {
        this.resultado = resultado;
    }
}
