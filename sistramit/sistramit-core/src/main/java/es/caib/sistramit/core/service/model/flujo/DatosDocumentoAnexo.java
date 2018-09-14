package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;

/**
 * Datos de un anexo accesibles desde los demás pasos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosDocumentoAnexo extends DatosDocumento {

    /**
     * Instancia. Un genérico puede tener varias instancias.
     */
    private int instancia = ConstantesNumero.N1;

    /**
     * Instancia un nuevo datos documento anexo de DatosDocumentoAnexo.
     */
    public DatosDocumentoAnexo() {
        super();
        this.setTipo(TypeDocumento.ANEXO);
    }

    /**
     * Crea instancia DatosDocumentoAnexo (uso dentro de bucles).
     *
     * @return DatosDocumentoAnexo
     */
    public static DatosDocumentoAnexo createNewDatosDocumentoAnexo() {
        return new DatosDocumentoAnexo();
    }

    /**
     * Instancia documento anexo.
     *
     * @return instancia documento anexo
     */
    public int getInstancia() {
        return instancia;
    }

    /**
     * Método para establecer instancia documento anexo.
     *
     * @param pInstanciaDocAnexo
     *            instancia a establecer
     */
    public void setInstancia(final int pInstanciaDocAnexo) {
        instancia = pInstanciaDocAnexo;
    }

}
