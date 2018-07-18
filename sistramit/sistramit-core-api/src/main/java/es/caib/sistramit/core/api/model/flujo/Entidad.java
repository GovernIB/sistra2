package es.caib.sistramit.core.api.model.flujo;

/**
 * Entidad.
 *
 * @author Indra
 */
@SuppressWarnings("serial")
public final class Entidad implements ModelApi {

    /** Id entidad. */
    private String id;

    /** Logo (path). */
    private String logo;

    /** Css (path). */
    private String css;

    /** Nombre. */
    private String nombre;

    /** Contacto (HTML). */
    private String contacto;

    /** Url carpeta. */
    private String urlCarpeta;

    /** Soporte. */
    private EntidadSoporte soporte;

    /**
     * Método de acceso a logo.
     *
     * @return logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Método para establecer logo.
     *
     * @param logo
     *            logo a establecer
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * Método de acceso a css.
     *
     * @return css
     */
    public String getCss() {
        return css;
    }

    /**
     * Método para establecer css.
     *
     * @param css
     *            css a establecer
     */
    public void setCss(String css) {
        this.css = css;
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

    /**
     * Método de acceso a contacto.
     *
     * @return contacto
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * Método para establecer contacto.
     *
     * @param contacto
     *            contacto a establecer
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * Método de acceso a urlCarpeta.
     *
     * @return urlCarpeta
     */
    public String getUrlCarpeta() {
        return urlCarpeta;
    }

    /**
     * Método para establecer urlCarpeta.
     *
     * @param urlCarpeta
     *            urlCarpeta a establecer
     */
    public void setUrlCarpeta(String urlCarpeta) {
        this.urlCarpeta = urlCarpeta;
    }

    /**
     * Método de acceso a soporte.
     *
     * @return soporte
     */
    public EntidadSoporte getSoporte() {
        return soporte;
    }

    /**
     * Método para establecer soporte.
     *
     * @param soporte
     *            soporte a establecer
     */
    public void setSoporte(EntidadSoporte soporte) {
        this.soporte = soporte;
    }

    /**
     * Método de acceso a id.
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Método para establecer id.
     * 
     * @param id
     *            id a establecer
     */
    public void setId(String id) {
        this.id = id;
    }

}
