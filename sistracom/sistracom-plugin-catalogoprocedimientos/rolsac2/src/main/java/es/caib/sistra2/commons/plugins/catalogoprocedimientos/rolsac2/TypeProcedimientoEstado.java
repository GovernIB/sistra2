package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2;

/**
 * Tipos de rol
 */
public enum TypeProcedimientoEstado {


    /**
     * <P>ESTADO MODIFICACON</P>
     **/
    MODIFICACION("M"),
    /**
     * <P>ESTADO MODIFICACIÓN PENDIENTE SUBIR</P>
     */
    PENDIENTE_PUBLICAR("PV"),
    /**
     * <P>PENDIENTE RESERVAR</P>
     */
    PENDIENTE_CERRAR("PT"),
    /**
     * <P>PUBLICADO</P>
     */
    PUBLICADO("P"),
    /**
     * <P>CERRAR</P>
     */
    CERRADO("T");

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
