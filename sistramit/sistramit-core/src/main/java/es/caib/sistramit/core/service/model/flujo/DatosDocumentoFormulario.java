package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;

/**
 * Datos de un formulario accesibles desde los demás pasos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosDocumentoFormulario extends DatosDocumento {

    /**
     * Indica si es un formulario de captura de datos (paso Captura).
     */
    private boolean formularioCaptura;

    /**
     * Referencia PDF de visualización.
     */
    private ReferenciaFichero pdf;

    /**
     * Acceso a los campos de un formulario.
     */
    private ValoresFormulario campos;

    /**
     * Constructor.
     */
    public DatosDocumentoFormulario() {
        super();
        this.setTipo(TypeDocumento.FORMULARIO);
    }

    /**
     * Método para Crea new datos documento formulario de la clase
     * DatosDocumentoFormulario.
     *
     * @return el datos documento formulario
     */
    public static DatosDocumentoFormulario createNewDatosDocumentoFormulario() {
        return new DatosDocumentoFormulario();
    }

    /**
     * Método de acceso a formularioCaptura.
     * 
     * @return formularioCaptura
     */
    public boolean isFormularioCaptura() {
        return formularioCaptura;
    }

    /**
     * Método para establecer formularioCaptura.
     * 
     * @param formularioCaptura
     *            formularioCaptura a establecer
     */
    public void setFormularioCaptura(boolean formularioCaptura) {
        this.formularioCaptura = formularioCaptura;
    }

    /**
     * Método de acceso a pdf.
     * 
     * @return pdf
     */
    public ReferenciaFichero getPdf() {
        return pdf;
    }

    /**
     * Método para establecer pdf.
     * 
     * @param pdf
     *            pdf a establecer
     */
    public void setPdf(ReferenciaFichero pdf) {
        this.pdf = pdf;
    }

    /**
     * Método de acceso a campos.
     * 
     * @return campos
     */
    public ValoresFormulario getCampos() {
        return campos;
    }

    /**
     * Método para establecer campos.
     * 
     * @param campos
     *            campos a establecer
     */
    public void setCampos(ValoresFormulario campos) {
        this.campos = campos;
    }

}
