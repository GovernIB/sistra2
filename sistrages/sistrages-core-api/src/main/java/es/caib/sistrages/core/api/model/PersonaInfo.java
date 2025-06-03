package es.caib.sistrages.core.api.model;

public class PersonaInfo {

    /**
     * Nombre.
     */
    private String nombre;

    /**
     * Apellido 1.
     */
    private String apellido1;

    /**
     * Apellido 2.
     */
    private String apellido2;

    /**
     * Nif.
     */
    private String nif;


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

}
