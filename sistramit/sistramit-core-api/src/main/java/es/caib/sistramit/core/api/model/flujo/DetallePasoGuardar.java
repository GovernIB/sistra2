package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Detalle del paso "Guardar". Se guarda el justificante de registro.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetallePasoGuardar extends DetallePaso {

    /**
     * Extracto del justificante de registro.
     */
    private DatosGuardarJustificante justificante;

    /**
     * Instrucciones de tramitación (HTML): permite personalizar el mensaje de
     * finalización del trámite. Este mensaje aparecerá al finalizar el trámite
     * tras registrar.
     */
    private String instruccionesTramitacion;

    /**
     * Instrucciones de presentación (HTML): cuando sea un preregistro y haya
     * que entregar documentación presencial pueden establecerse instrucciones
     * de entrega específicas.
     */
    private String instruccionesPresentacion;

    /**
     * Constructor.
     */
    public DetallePasoGuardar() {
        super();
        this.setTipo(TypePaso.GUARDAR);
    }

    /**
     * Método de acceso a justificante.
     *
     * @return justificante
     */
    public DatosGuardarJustificante getJustificante() {
        return justificante;
    }

    /**
     * Método para establecer justificante.
     *
     * @param pJustificante
     *            justificante a establecer
     */
    public void setJustificante(final DatosGuardarJustificante pJustificante) {
        justificante = pJustificante;
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
        strb.append(ident).append("Sólo lectura:" + getSoloLectura() + "\n");
        return strb.toString();
    }

    /**
     * Método de acceso a instruccionesTramitacion.
     *
     * @return instruccionesTramitacion
     */
    public String getInstruccionesTramitacion() {
        return instruccionesTramitacion;
    }

    /**
     * Método para establecer instruccionesTramitacion.
     *
     * @param instruccionesTramitacion
     *            instruccionesTramitacion a establecer
     */
    public void setInstruccionesTramitacion(String instruccionesTramitacion) {
        this.instruccionesTramitacion = instruccionesTramitacion;
    }

    /**
     * Método de acceso a instruccionesPresentacion.
     *
     * @return instruccionesPresentacion
     */
    public String getInstruccionesPresentacion() {
        return instruccionesPresentacion;
    }

    /**
     * Método para establecer instruccionesPresentacion.
     *
     * @param instruccionesPresentacion
     *            instruccionesPresentacion a establecer
     */
    public void setInstruccionesPresentacion(String instruccionesPresentacion) {
        this.instruccionesPresentacion = instruccionesPresentacion;
    }

}
