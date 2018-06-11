package es.caib.sistramit.core.api.model.system;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;

/**
 * Invalidacion de definicion de tramites o de datos de dominio.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class Invalidacion implements Serializable {

    /**
     * Tipo.
     */
    private TypeInvalidacion tipo;

    /**
     * Identificador.
     */
    private String identificador;

    /**
     * Fecha invalidación.
     */
    private Date fecha;

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public TypeInvalidacion getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(TypeInvalidacion tipo) {
        this.tipo = tipo;
    }

    /**
     * Método de acceso a identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     *
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a fecha.
     *
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Método para establecer fecha.
     *
     * @param fecha
     *            fecha a establecer
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
