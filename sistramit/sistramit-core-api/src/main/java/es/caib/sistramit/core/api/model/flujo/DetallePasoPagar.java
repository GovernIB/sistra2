package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Detalle del paso "Pagar".
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetallePasoPagar extends DetallePaso {

    /**
     * Año actual.
     */
    private String anyo;

    /**
     * Lista de pagos.
     */
    private List<Pago> pagos = new ArrayList<>();

    /**
     * Constructor.
     */
    public DetallePasoPagar() {
        super();
        this.setTipo(TypePaso.PAGAR);
    }

    /**
     * Método de acceso a pagos.
     *
     * @return pagos
     */
    public List<Pago> getPagos() {
        return pagos;
    }

    /**
     * Método para establecer pagos.
     *
     * @param pPagos
     *            pagos a establecer
     */
    public void setPagos(final List<Pago> pPagos) {
        pagos = pPagos;
    }

    /**
     * Obtiene pago a partir del id.
     *
     * @param idPago
     *            Identificador pago.
     * @return Pago
     */
    public Pago getPago(final String idPago) {
        Pago r = null;
        for (final Pago f : this.getPagos()) {
            if (f.getId().equals(idPago)) {
                r = f;
                break;
            }
        }
        return r;
    }

    @Override
    public String print() {
        final int capacity = ConstantesNumero.N2 * ConstantesNumero.N1024;
        final StringBuffer strb = new StringBuffer(capacity);

        strb.append("\nDETALLE PASO PAGAR\n");
        strb.append("=====================\n");

        strb.append("Id paso:" + getId() + "\n");
        strb.append("Completado:" + getCompletado() + "\n");
        strb.append("Solo lectura:" + getSoloLectura() + "\n");
        strb.append("Tipo:" + getTipo() + "\n");

        strb.append("PAGOS:\n");
        for (final Pago pago : this.getPagos()) {

            strb.append(" Id:" + pago.getId() + "\n");
            strb.append(" Titulo:" + pago.getTitulo() + "\n");
            strb.append(" Obligatorio:" + pago.getObligatorio() + "\n");
            strb.append(" Rellenado:" + pago.getRellenado() + "\n");
        }

        return strb.toString();
    }

    /**
     * Método de acceso a anyo.
     *
     * @return anyo
     */
    public String getAnyo() {
        return anyo;
    }

    /**
     * Método para establecer anyo.
     *
     * @param pAnyo
     *            anyo a establecer
     */
    public void setAnyo(final String pAnyo) {
        anyo = pAnyo;
    }

}
