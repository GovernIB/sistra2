package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2;

/**
 * Tipos de rol
 */
public enum TypeProcedimientoEstado {


    /**
     * <P>ESTADO MODIFICACON</P>
     **/
    MODIFICACION("MODIFICACION"),

    /**
     * <P>ESTADO MODIFICACIÓN PENDIENTE SUBIR</P>
     */
    PENDIENTE_PUBLICAR("PENDIENTE_PUBLICAR"),
    /**
     * <P>PENDIENTE RESERVAR</P>
     */
    PENDIENTE_RESERVAR("PENDIENTE_RESERVAR"),
    /**
     * <P>PENDIENTE RESERVAR</P>
     */
    PENDIENTE_BORRAR("PENDIENTE_BORRAR"),
    /**
     * <P>PUBLICADO</P>
     */
    PUBLICADO("PUBLICADO"),
    /**
     * <P>BORRADO</P>
     */
    BORRADO("BORRADO"),

    /**
     * <P>RESERVA</P>
     */
    RESERVA("RESERVA");

    String perfil;

    TypeProcedimientoEstado(String iPerfil) {
        perfil = iPerfil;
    }

    public static TypeProcedimientoEstado fromString(String iPerfil) {
        TypeProcedimientoEstado tipo = null;
        for (TypeProcedimientoEstado typeRol : TypeProcedimientoEstado.values()) {
            if (typeRol.toString().equals(iPerfil)) {
                tipo = typeRol;
                break;
            }
        }
        return tipo;
    }

    public String toString() {
        return perfil;
    }
}
