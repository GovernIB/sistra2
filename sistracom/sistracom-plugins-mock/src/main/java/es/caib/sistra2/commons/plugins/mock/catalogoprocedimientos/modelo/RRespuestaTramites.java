package es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.modelo;

/**
 * Tramites de rolsac.
 *
 * @author Indra
 *
 */
public class RRespuestaTramites extends RRespuestaBasicaRolsac {

    /** Resultado. **/
    private RTramiteRolsac[] resultado;

    /** Constructor. **/
    public RRespuestaTramites(final String status, final String mensaje,
            final Integer numeroElementos, final RTramiteRolsac[] resultado) {
        super(status, mensaje, numeroElementos);
        this.resultado = resultado;
    }

    /** Constructor. **/
    public RRespuestaTramites() {
        super();
    }

    /**
     * @return the resultado
     */
    public RTramiteRolsac[] getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *            the resultado to set
     */
    public void setResultado(final RTramiteRolsac[] resultado) {
        this.resultado = resultado;
    }
}
