package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Configuración entidad.
 *
 * @author Indra
 *
 */
public class RConfiguracionEntidad {

    /** Identificador. */
    private String identificador;

    /** Referencia logo. */
    private String logo;

    /** Referencia logo. */
    private String css;

    /** Contacto (HTML). */
    private RLiteral contactoHTML;

    /** Url carpeta ciudadana. */
    private RLiteral urlCarpeta;

    /** Info LOPD (HTML). */
    private RLiteral infoLopdHTML;

    /** Dias preregistro. */
    private int diasPreregistro;

    /** Email. */
    private String email;

    /** Ayuda email. */
    private boolean ayudaEmail;

    /** Ayuda telefono. */
    private String ayudaTelefono;

    /** Ayuda url. */
    private String ayudaUrl;

    /** Ayuda formulario soporte. */
    private List<ROpcionFormularioSoporte> ayudaFormulario;

    /** Plugins entidad. */
    private List<RPlugin> plugins;

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
     * Método de acceso a contactoHTML.
     *
     * @return contactoHTML
     */
    public RLiteral getContactoHTML() {
        return contactoHTML;
    }

    /**
     * Método para establecer contactoHTML.
     *
     * @param contactoHTML
     *            contactoHTML a establecer
     */
    public void setContactoHTML(RLiteral contactoHTML) {
        this.contactoHTML = contactoHTML;
    }

    /**
     * Método de acceso a urlCarpeta.
     *
     * @return urlCarpeta
     */
    public RLiteral getUrlCarpeta() {
        return urlCarpeta;
    }

    /**
     * Método para establecer urlCarpeta.
     *
     * @param urlCarpeta
     *            urlCarpeta a establecer
     */
    public void setUrlCarpeta(RLiteral urlCarpeta) {
        this.urlCarpeta = urlCarpeta;
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
     * Método de acceso a ayudaEmail.
     *
     * @return ayudaEmail
     */
    public boolean isAyudaEmail() {
        return ayudaEmail;
    }

    /**
     * Método para establecer ayudaEmail.
     *
     * @param ayudaEmail
     *            ayudaEmail a establecer
     */
    public void setAyudaEmail(boolean ayudaEmail) {
        this.ayudaEmail = ayudaEmail;
    }

    /**
     * Método de acceso a ayudaTelefono.
     *
     * @return ayudaTelefono
     */
    public String getAyudaTelefono() {
        return ayudaTelefono;
    }

    /**
     * Método para establecer ayudaTelefono.
     *
     * @param ayudaTelefono
     *            ayudaTelefono a establecer
     */
    public void setAyudaTelefono(String ayudaTelefono) {
        this.ayudaTelefono = ayudaTelefono;
    }

    /**
     * Método de acceso a ayudaUrl.
     *
     * @return ayudaUrl
     */
    public String getAyudaUrl() {
        return ayudaUrl;
    }

    /**
     * Método para establecer ayudaUrl.
     *
     * @param ayudaUrl
     *            ayudaUrl a establecer
     */
    public void setAyudaUrl(String ayudaUrl) {
        this.ayudaUrl = ayudaUrl;
    }

    /**
     * Método de acceso a ayudaFormulario.
     *
     * @return ayudaFormulario
     */
    public List<ROpcionFormularioSoporte> getAyudaFormulario() {
        return ayudaFormulario;
    }

    /**
     * Método para establecer ayudaFormulario.
     *
     * @param ayudaFormulario
     *            ayudaFormulario a establecer
     */
    public void setAyudaFormulario(
            List<ROpcionFormularioSoporte> ayudaFormulario) {
        this.ayudaFormulario = ayudaFormulario;
    }

    /**
     * Método de acceso a plugins.
     *
     * @return plugins
     */
    public List<RPlugin> getPlugins() {
        return plugins;
    }

    /**
     * Método para establecer plugins.
     *
     * @param plugins
     *            plugins a establecer
     */
    public void setPlugins(List<RPlugin> plugins) {
        this.plugins = plugins;
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
     * Método de acceso a infoLopdHTML.
     *
     * @return infoLopdHTML
     */
    public RLiteral getInfoLopdHTML() {
        return infoLopdHTML;
    }

    /**
     * Método para establecer infoLopdHTML.
     *
     * @param infoLopdHTML
     *            infoLopdHTML a establecer
     */
    public void setInfoLopdHTML(RLiteral infoLopdHTML) {
        this.infoLopdHTML = infoLopdHTML;
    }

    /**
     * Método de acceso a diasPreregistro.
     *
     * @return diasPreregistro
     */
    public int getDiasPreregistro() {
        return diasPreregistro;
    }

    /**
     * Método para establecer diasPreregistro.
     *
     * @param diasPreregistro
     *            diasPreregistro a establecer
     */
    public void setDiasPreregistro(int diasPreregistro) {
        this.diasPreregistro = diasPreregistro;
    }

}
