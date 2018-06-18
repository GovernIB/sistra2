package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Devuelve la información del estado del trámite: lista de pasos y detalle paso
 * actual.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetallePasos implements Serializable {

    /**
     * Estado trámite.
     */
    private TypeEstadoTramite estado = TypeEstadoTramite.RELLENANDO;

    /**
     * Lista de pasos.
     */
    private List<PasoLista> pasos;

    /**
     * Detalle del paso actual.
     */
    private DetallePaso actual;

    /**
     * Id del paso anterior en caso de que se pueda acceder a él.
     */
    private String anterior;

    /**
     * Id del paso siguiente en caso de que se pueda acceder a él.
     */
    private String siguiente;

    /**
     * Método de acceso a listaPasos.
     *
     * @return listaPasos
     */
    public List<PasoLista> getPasos() {
        return pasos;
    }

    /**
     * Método para establecer listaPasos.
     *
     * @param pListaPasos
     *            listaPasos a establecer
     */
    public void setPasos(final List<PasoLista> pListaPasos) {
        pasos = pListaPasos;
    }

    /**
     * Método de acceso a paso.
     *
     * @return paso
     */
    public DetallePaso getActual() {
        return actual;
    }

    /**
     * Método para establecer paso.
     *
     * @param pPaso
     *            paso a establecer
     */
    public void setActual(final DetallePaso pPaso) {
        actual = pPaso;
    }

    /**
     * Método de acceso a estado.
     *
     * @return estado
     */
    public TypeEstadoTramite getEstado() {
        return estado;
    }

    /**
     * Método para establecer estado.
     *
     * @param pEstado
     *            estado a establecer
     */
    public void setEstado(final TypeEstadoTramite pEstado) {
        estado = pEstado;
    }

    /**
     * Método de acceso a pasoAnterior.
     *
     * @return pasoAnterior
     */
    public String getAnterior() {
        return anterior;
    }

    /**
     * Método para establecer pasoAnterior.
     *
     * @param pPasoAnterior
     *            pasoAnterior a establecer
     */
    public void setAnterior(final String pPasoAnterior) {
        anterior = pPasoAnterior;
    }

    /**
     * Método de acceso a pasoSiguiente.
     *
     * @return pasoSiguiente
     */
    public String getSiguiente() {
        return siguiente;
    }

    /**
     * Método para establecer pasoSiguiente.
     *
     * @param pPasoSiguiente
     *            pasoSiguiente a establecer
     */
    public void setSiguiente(final String pPasoSiguiente) {
        siguiente = pPasoSiguiente;
    }

    /**
     * Imprime pasos.
     *
     * @return Cadena con la info.
     */
    public String print() {
        final String ident = "  ";
        final String identPasos = "    ";
        final StringBuffer strb = new StringBuffer(8000);
        strb.append(ident).append("Estado:" + getEstado() + "\n");
        strb.append(ident).append("Pasos\n");
        int i = ConstantesNumero.N1;
        for (final PasoLista pl : getPasos()) {
            strb.append(identPasos).append(" Paso " + i + "\n");
            strb.append(identPasos).append(identPasos).append(ident)
                    .append(" Id:" + pl.getId() + "\n");
            strb.append(identPasos).append(identPasos).append(ident)
                    .append(" Tipo:" + pl.getTipo() + "\n");
            strb.append(identPasos).append(identPasos).append(ident)
                    .append(" Accesible:" + pl.getAccesible() + "\n");
            strb.append(identPasos).append(identPasos).append(ident)
                    .append(" Completado:" + pl.getCompletado() + "\n");
            i++;
        }
        strb.append(ident).append("Anterior:" + getAnterior() + "\n");
        strb.append(ident).append("Siguiente:" + getSiguiente() + "\n");
        strb.append(ident).append("Actual:" + getActual().print() + "\n");

        return strb.toString();
    }
}
