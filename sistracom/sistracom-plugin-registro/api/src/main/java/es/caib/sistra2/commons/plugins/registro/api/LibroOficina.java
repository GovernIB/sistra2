package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;

/**
 * Datos de un libro que da servicio a una oficina de registro
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class LibroOficina implements Serializable {

    /** Código del libro */
    private String codigo;

    /** Nombre del libro */
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
