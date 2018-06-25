package es.caib.sistrages.rest.api.interna;

/**
 * Componente aviso.
 *
 * @author Indra
 *
 */
public class RComponenteAviso extends RComponente {

    /** Tipo aviso. */
    private String tipoAviso;

    /**
     * Método de acceso a tipoAviso.
     *
     * @return tipoAviso
     */
    public String getTipoAviso() {
        return tipoAviso;
    }

    /**
     * Método para establecer tipoAviso.
     *
     * @param tipoAviso
     *            tipoAviso a establecer
     */
    public void setTipoAviso(String tipoAviso) {
        this.tipoAviso = tipoAviso;
    }

}
