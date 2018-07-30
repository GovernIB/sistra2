package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Detalle del paso "Rellenar".
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetallePasoRellenar extends DetallePaso {

    /**
     * Lista de formularios.
     */
    private List<Formulario> formularios = new ArrayList<>();

    /**
     * Constructor.
     */
    public DetallePasoRellenar() {
        super();
        this.setTipo(TypePaso.RELLENAR);
    }

    /**
     * Método de acceso a formularios.
     *
     * @return formularios
     */
    public List<Formulario> getFormularios() {
        return formularios;
    }

    /**
     * Método para establecer formularios.
     *
     * @param pFormularios
     *            formularios a establecer
     */
    public void setFormularios(final List<Formulario> pFormularios) {
        formularios = pFormularios;
    }

    /**
     * Obtiene formulario a partir del id.
     *
     * @param idFormulario
     *            Id formulario
     * @return Formulario Formulario
     */
    public Formulario getFormulario(final String idFormulario) {
        Formulario r = null;
        for (final Formulario f : this.getFormularios()) {
            if (f.getId().equals(idFormulario)) {
                r = f;
                break;
            }
        }
        return r;
    }

    @Override
    public String print() {
        final String ident = "    ";
        final String identForms = "        ";
        final StringBuffer strb = new StringBuffer(
                ConstantesNumero.N8 * ConstantesNumero.N1024);
        strb.append(ident).append("\n");
        strb.append(ident).append("ID paso:" + getId() + "\n");
        strb.append(ident).append("Tipo:" + getTipo() + "\n");
        strb.append(ident).append("Completado:" + getCompletado() + "\n");
        strb.append(ident).append("Sólo lectura:" + getSoloLectura() + "\n");

        strb.append(ident).append("Formularios:\n");
        for (final Formulario formulario : this.getFormularios()) {

            strb.append(identForms).append(ident)
                    .append(" Id:" + formulario.getId() + "\n");
            strb.append(identForms).append(ident)
                    .append(" Titulo:" + formulario.getTitulo() + "\n");

            strb.append(identForms).append(ident)
                    .append(" Firmar:" + formulario.getFirmar() + "\n");
            strb.append(identForms).append(ident).append(
                    " Obligatorio:" + formulario.getObligatorio() + "\n");
            strb.append(identForms).append(ident)
                    .append(" Rellenado:" + formulario.getRellenado() + "\n");

            strb.append(identForms).append(ident).append(" Firmantes:\n");
            for (final Persona firmante : formulario.getFirmantes()) {
                strb.append(identForms).append(ident).append(ident)
                        .append("   NIF:" + firmante.getNif() + "\n");
                strb.append(identForms).append(ident).append(ident)
                        .append("   nombre:" + firmante.getNombre() + "\n");
            }

        }
        return strb.toString();

    }

}
