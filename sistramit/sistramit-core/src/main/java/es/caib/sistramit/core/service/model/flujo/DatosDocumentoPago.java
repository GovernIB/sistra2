package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;

/**
 * Datos de un pago accesibles desde los demás pasos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosDocumentoPago extends DatosDocumento {

    /**
     * Referencia fichero justificante de pago.
     */
    private ReferenciaFichero justificantePago;

    /**
     * Método de acceso a justificantePago.
     *
     * @return justificantePago
     */
    public ReferenciaFichero getJustificantePago() {
        return justificantePago;
    }

    /**
     * Método para establecer justificantePago.
     *
     * @param pJustificantePago
     *            justificantePago a establecer
     */
    public void setJustificantePago(final ReferenciaFichero pJustificantePago) {
        justificantePago = pJustificantePago;
    }

    /**
     * Instancia un nuevo datos documento pago de DatosDocumentoPago.
     */
    public DatosDocumentoPago() {
        super();
        this.setTipo(TypeDocumento.PAGO);
    }

}
