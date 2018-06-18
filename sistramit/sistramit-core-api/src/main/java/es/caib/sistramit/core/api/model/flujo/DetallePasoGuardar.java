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
        strb.append(ident).append("Titulo:" + getTitulo() + "\n");
        strb.append(ident).append("Completado:" + getCompletado() + "\n");
        strb.append(ident).append("Sólo lectura:" + getSoloLectura() + "\n");
        return strb.toString();
    }

}
