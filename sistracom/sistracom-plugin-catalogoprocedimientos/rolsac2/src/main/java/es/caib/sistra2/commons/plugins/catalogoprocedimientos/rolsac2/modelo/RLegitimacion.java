package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serveis.
 *
 * @author Indra
 */

public class RLegitimacion {

    private static final Logger LOG = LoggerFactory.getLogger(RLegitimacion.class);

    /**
     * codigo
     **/

    private long codigo;


    private String identificador;


    private String descripcion;


    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final long codigo) {
        this.codigo = codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
