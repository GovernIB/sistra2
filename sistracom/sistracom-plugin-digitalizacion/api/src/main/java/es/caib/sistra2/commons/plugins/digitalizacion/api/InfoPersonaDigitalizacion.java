package es.caib.sistra2.commons.plugins.digitalizacion.api;

import org.apache.commons.lang3.StringUtils;

/**
 * Datos del FH que realiza la digitalización.
 */
public class InfoPersonaDigitalizacion {

    /** Username. */
    private String userName;

    /** Usuario. */
    private String nombre;

    /** Apellido 1. */
    private String apellido1;

    /** Apellido 2.*/
    private String apellido2;

    /** Nif. */
    private String nif;

    /**
     * Constructor.
     */
    public InfoPersonaDigitalizacion() {
    }

    /**
     * Constructor.
     */
    public InfoPersonaDigitalizacion(String userName, String nombre, String nombre1, String apellido1, String apellido2, String nif) {
        this.userName = userName;
        this.nombre = nombre;
        this.nombre = nombre1;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nif = nif;
    }

    /**
     * @return the nombre
     */
    public final String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public final void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido1
     */
    public final String getApellido1() {
        return apellido1;
    }

    /**
     * @param apellido1 the apellido1 to set
     */
    public final void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * @return the apellido2
     */
    public final String getApellido2() {
        return apellido2;
    }

    /**
     * @param apellido2 the apellido2 to set
     */
    public final void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * @return the nif
     */
    public final String getNif() {
        return nif;
    }

    /**
     * @param nif the nif to set
     */
    public final void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Devuelve userName.
     * @return the userName
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * Establece userName.
     * @param userName the userName to set
     */
    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final String getNombreApellidos() {
        final StringBuffer res = new StringBuffer(100);
        if (this.getNombre() != null) {
            res.append(this.getNombre());
        }
        if (StringUtils.isNotBlank(this.getApellido1())) {
            res.append(" ").append(this.getApellido1());
        }
        if (StringUtils.isNotBlank(this.getApellido2())) {
            res.append(" ").append(this.getApellido2());
        }
        return res.toString();
    }

}
