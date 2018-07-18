package es.caib.sistra2.commons.plugins.autenticacion;

/**
 * Datos usario retornados por Clave.
 *
 * @author Indra
 *
 */
public class DatosUsuario {

    /** Nif. */
    private String nif;

    /** Nombre. */
    private String nombre;

    /** Apellido 1. */
    private String apellido1;

    /** Apellido 2. */
    private String apellido2;

    /** Email. */
    private String email;

    /**
     * Tipo autenticacion.
     */
    private TipoAutenticacion autenticacion;

    /** Metodo autenticacion. */
    private TipoMetodoAutenticacion metodoAutenticacion;

    /**
     * Método de acceso a nif.
     *
     * @return nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * Método para establecer nif.
     *
     * @param pNif
     *            nif a establecer
     */
    public void setNif(final String pNif) {
        nif = pNif;
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
     * @param pNombre
     *            nombre a establecer
     */
    public void setNombre(final String pNombre) {
        nombre = pNombre;
    }

    /**
     * Método de acceso a apellido1.
     *
     * @return apellido1
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Método para establecer apellido1.
     *
     * @param pApellido1
     *            apellido1 a establecer
     */
    public void setApellido1(final String pApellido1) {
        apellido1 = pApellido1;
    }

    /**
     * Método de acceso a apellido2.
     *
     * @return apellido2
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Método para establecer apellido2.
     *
     * @param pApellido2
     *            apellido2 a establecer
     */
    public void setApellido2(final String pApellido2) {
        apellido2 = pApellido2;
    }

    /**
     * Método de acceso a metodoAutenticacion.
     *
     * @return metodoAutenticacion
     */
    public TipoMetodoAutenticacion getMetodoAutenticacion() {
        return metodoAutenticacion;
    }

    /**
     * Método para establecer metodoAutenticacion.
     *
     * @param pMetodoAutenticacion
     *            metodoAutenticacion a establecer
     */
    public void setMetodoAutenticacion(
            final TipoMetodoAutenticacion pMetodoAutenticacion) {
        metodoAutenticacion = pMetodoAutenticacion;
    }

    /**
     * Método de acceso a email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método para establecer email.
     *
     * @param email
     *            email a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método de acceso a autenticacion.
     *
     * @return autenticacion
     */
    public TipoAutenticacion getAutenticacion() {
        return autenticacion;
    }

    /**
     * Método para establecer autenticacion.
     *
     * @param autenticacion
     *            autenticacion a establecer
     */
    public void setAutenticacion(TipoAutenticacion autenticacion) {
        this.autenticacion = autenticacion;
    }

}
