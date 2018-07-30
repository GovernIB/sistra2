package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;

/**
 * Clase de acceso a un valor indexado desde los plugins.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzValorCampoCompuesto implements ClzValorCampoCompuestoInt {

    /**
     * Código valor.
     */
    private String codigo;

    /**
     * Descripción valor.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param pCodigo
     *            Código
     * @param pDescripcion
     *            Descripción
     */
    public ClzValorCampoCompuesto(final String pCodigo,
            final String pDescripcion) {
        super();
        codigo = pCodigo;
        descripcion = pDescripcion;
    }

    /**
     * Constructor.
     */
    public ClzValorCampoCompuesto() {
        super();
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public void setCodigo(final String pCodigo) {
        codigo = pCodigo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public void setDescripcion(final String pDescripcion) {
        descripcion = pDescripcion;
    }

}
