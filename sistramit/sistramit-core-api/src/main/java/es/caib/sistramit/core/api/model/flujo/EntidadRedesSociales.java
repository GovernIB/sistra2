package es.caib.sistramit.core.api.model.flujo;

/**
 * Entidad redes sociales.
 *
 * Si no se especifica en conjunto o una en particular implica que no tiene.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class EntidadRedesSociales implements ModelApi {

    /** Url youtube. */
    private String youtube;

    /** Url instagram. */
    private String instagram;

    /** Url twitter. */
    private String twiter;

    /** Url facebook. */
    private String facebook;

    /**
     * Método de acceso a youtube.
     *
     * @return youtube
     */
    public String getYoutube() {
        return youtube;
    }

    /**
     * Método para establecer youtube.
     *
     * @param youtube
     *            youtube a establecer
     */
    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    /**
     * Método de acceso a instagram.
     *
     * @return instagram
     */
    public String getInstagram() {
        return instagram;
    }

    /**
     * Método para establecer instagram.
     *
     * @param instagram
     *            instagram a establecer
     */
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    /**
     * Método de acceso a twiter.
     *
     * @return twiter
     */
    public String getTwiter() {
        return twiter;
    }

    /**
     * Método para establecer twiter.
     *
     * @param twiter
     *            twiter a establecer
     */
    public void setTwiter(String twiter) {
        this.twiter = twiter;
    }

    /**
     * Método de acceso a facebook.
     *
     * @return facebook
     */
    public String getFacebook() {
        return facebook;
    }

    /**
     * Método para establecer facebook.
     *
     * @param facebook
     *            facebook a establecer
     */
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

}