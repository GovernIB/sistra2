package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;

/**
 * Datos del tipo de asunto de una entidad
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TipoAsunto implements Serializable {

    /** Código del tipo de asunto */
    private String codigo;

    /** Nombre del tipo de asunto */
    private String nombre;

    /**
     * Método de acceso a codigo.
     * 
     * @return codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para establecer codigo.
     * 
     * @param codigo
     *            codigo a establecer
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Método de acceso a nombre.
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para establecer nombre.
     * 
     * @param nombre
     *            nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
