package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2;

/**
 * Tipos de rol
 */
public enum TypeProcedimientoWorkflow {

    /**
     * ESTADO PUBLICADO
     **/
    DEFINITIVO("DEFINITIVO"),
    /**
     * <P>ADMINISTRADOR ENTIDAD</P>
     * <P>Responsable de la configuració del sistema a nivell d’entitat.</P>
     * <P>Puede mantener todos los contenidos de la entidad</P>
     **/
    MODIFICACION("MODIFICACION");

	String perfil;

    TypeProcedimientoWorkflow(String iPerfil) {
        perfil = iPerfil;
    }

    public static TypeProcedimientoWorkflow fromBoolean(String iPerfil) {
        TypeProcedimientoWorkflow tipo = null;
        for (TypeProcedimientoWorkflow typeRol : TypeProcedimientoWorkflow.values()) {
            if (typeRol.getValor().equals(iPerfil)) {
                tipo = typeRol;
                break;
            }
        }
        return tipo;
    }

    public String getValor() {
        return perfil;
    }
}
