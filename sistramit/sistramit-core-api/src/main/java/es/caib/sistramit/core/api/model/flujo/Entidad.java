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

    /** Url mapa web. */
    private String urlMapaWeb;

    /** Url aviso legal. */
    private String urlAvisoLegal;

    /** Url rss. */
    private String urlRss;

    /** Soporte. */
    private EntidadSoporte soporte;

    /** Redes sociales. */
    private EntidadRedesSociales redes;

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

    /**
     * Método de acceso a urlMapaWeb.
     * 
     * @return urlMapaWeb
     */
    public String getUrlMapaWeb() {
        return urlMapaWeb;
    }

    /**
     * Método para establecer urlMapaWeb.
     * 
     * @param urlMapaWeb
     *            urlMapaWeb a establecer
     */
    public void setUrlMapaWeb(String urlMapaWeb) {
        this.urlMapaWeb = urlMapaWeb;
    }

    /**
     * Método de acceso a urlAvisoLegal.
     * 
     * @return urlAvisoLegal
     */
    public String getUrlAvisoLegal() {
        return urlAvisoLegal;
    }

    /**
     * Método para establecer urlAvisoLegal.
     * 
     * @param urlAvisoLegal
     *            urlAvisoLegal a establecer
     */
    public void setUrlAvisoLegal(String urlAvisoLegal) {
        this.urlAvisoLegal = urlAvisoLegal;
    }

    /**
     * Método de acceso a urlRss.
     * 
     * @return urlRss
     */
    public String getUrlRss() {
        return urlRss;
    }

    /**
     * Método para establecer urlRss.
     * 
     * @param urlRss
     *            urlRss a establecer
     */
    public void setUrlRss(String urlRss) {
        this.urlRss = urlRss;
    }

    /**
     * Método de acceso a redes.
     * 
     * @return redes
     */
    public EntidadRedesSociales getRedes() {
        return redes;
    }

    /**
     * Método para establecer redes.
     * 
     * @param redes
     *            redes a establecer
     */
    public void setRedes(EntidadRedesSociales redes) {
        this.redes = redes;
    }

}
