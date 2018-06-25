package es.caib.sistramit.core.api.model.flujo;

import java.util.List;

/**
 * Entidad.
 *
 * @author Indra
 */
@SuppressWarnings("serial")
public final class Entidad implements ModelApi {

    /** Logo (path). */
    private String logo;

    /** Css (path). */
    private String css;

    /** Nombre. */
    private String nombre;

    /** Info LOPD. */
    private String infoLOPD;

    /** Contacto (HTML). */
    private String contacto;

    /** Url carpeta. */
    private String urlCarpeta;

    /** Telefono soporte. */
    private String soporteTelefono;

    /** Email soporte. */
    private String soporteEmail;

    /** Url soporte. */
    private String soporteUrl;

    /** Formulario soporte. */
    private List<SoporteOpcion> soporteOpciones;

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
     * Método de acceso a infoLOPD.
     *
     * @return infoLOPD
     */
    public String getInfoLOPD() {
        return infoLOPD;
    }

    /**
     * Método para establecer infoLOPD.
     *
     * @param infoLOPD
     *            infoLOPD a establecer
     */
    public void setInfoLOPD(String infoLOPD) {
        this.infoLOPD = infoLOPD;
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
     * Método de acceso a soporteTelefono.
     *
     * @return soporteTelefono
     */
    public String getSoporteTelefono() {
        return soporteTelefono;
    }

    /**
     * Método para establecer soporteTelefono.
     *
     * @param soporteTelefono
     *            soporteTelefono a establecer
     */
    public void setSoporteTelefono(String soporteTelefono) {
        this.soporteTelefono = soporteTelefono;
    }

    /**
     * Método de acceso a soporteEmail.
     *
     * @return soporteEmail
     */
    public String getSoporteEmail() {
        return soporteEmail;
    }

    /**
     * Método para establecer soporteEmail.
     *
     * @param soporteEmail
     *            soporteEmail a establecer
     */
    public void setSoporteEmail(String soporteEmail) {
        this.soporteEmail = soporteEmail;
    }

    /**
     * Método de acceso a soporteUrl.
     *
     * @return soporteUrl
     */
    public String getSoporteUrl() {
        return soporteUrl;
    }

    /**
     * Método para establecer soporteUrl.
     *
     * @param soporteUrl
     *            soporteUrl a establecer
     */
    public void setSoporteUrl(String soporteUrl) {
        this.soporteUrl = soporteUrl;
    }

    /**
     * Método de acceso a soporteOpciones.
     *
     * @return soporteOpciones
     */
    public List<SoporteOpcion> getSoporteOpciones() {
        return soporteOpciones;
    }

    /**
     * Método para establecer soporteOpciones.
     *
     * @param soporteOpciones
     *            soporteOpciones a establecer
     */
    public void setSoporteOpciones(List<SoporteOpcion> soporteOpciones) {
        this.soporteOpciones = soporteOpciones;
    }

}
