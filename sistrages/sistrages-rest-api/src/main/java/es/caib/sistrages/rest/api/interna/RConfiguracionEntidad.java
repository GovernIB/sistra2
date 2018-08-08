package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Configuración entidad.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RConfiguracionEntidad", description = "Descripcion de RConfiguracionEntidad")
public class RConfiguracionEntidad {

    /** Timestamp recuperacion. */
	@ApiModelProperty(value = "Timestamp recuperacion")
    private String timestamp;

    /** Identificador. */
	@ApiModelProperty(value = "Identificador")
    private String identificador;

    /** Descripción. */
	@ApiModelProperty(value = "Descripción")
    private RLiteral descripcion;

    /** Referencia logo. */
	@ApiModelProperty(value = "Referencia logo")
    private String logo;

    /** Referencia logo. */
	@ApiModelProperty(value = "Referencia logo")
    private String css;

    /** Contacto (HTML). */
	@ApiModelProperty(value = "Contacto (HTML)")
    private RLiteral contactoHTML;

    /** Url carpeta ciudadana. */
	@ApiModelProperty(value = "Url carpeta ciudadana")
    private RLiteral urlCarpeta;

    /** Info LOPD (HTML). */
	@ApiModelProperty(value = "Info LOPD (HTML)")
    private RLiteral infoLopdHTML;

    /** Dias preregistro. */
	@ApiModelProperty(value = "Dias preregistro")
    private int diasPreregistro;

    /** Email. */
	@ApiModelProperty(value = "Email")
    private String email;

    /** Ayuda email. */
	@ApiModelProperty(value = "Ayuda email")
    private boolean ayudaEmail;

    /** Ayuda telefono. */
	@ApiModelProperty(value = "Ayuda telefono")
    private String ayudaTelefono;

    /** Ayuda url. */
	@ApiModelProperty(value = "Ayuda url")
    private String ayudaUrl;

    /** Ayuda formulario soporte. */
	@ApiModelProperty(value = "Ayuda formulario soporte")
    private List<ROpcionFormularioSoporte> ayudaFormulario;

    /** Plugins entidad. */
	@ApiModelProperty(value = "Plugins entidad")
    private List<RPlugin> plugins;
    
    /** Mapa web */
	@ApiModelProperty(value = "Mapa web")
    private RLiteral mapaWeb;
	
    /** Aviso legal */
	@ApiModelProperty(value = "Aviso legal")
    private RLiteral avisoLegal;
	
    /** RSS */
	@ApiModelProperty(value = "RSS")
    private RLiteral rss;
	
    /** url de Facebook */
	@ApiModelProperty(value = "url de Facebook")
    private String urlFacebook;
	
    /** url de Instagram */
	@ApiModelProperty(value = "url de Instagram")
    private String urlInstagram;
	
    /** url de  Twiter */
	@ApiModelProperty(value = "url de  Twiter")
    private String urlTwitter;
	
    /** url de Youtube */
	@ApiModelProperty(value = "url de Youtube")
    private String urlYoutube;
    
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

    /**
     * Método de acceso a descripcion.
     *
     * @return descripcion
     */
    public RLiteral getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion.
     *
     * @param descripcion
     *            descripcion a establecer
     */
    public void setDescripcion(RLiteral descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método de acceso a timestamp.
     * 
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Método para establecer timestamp.
     * 
     * @param timestamp
     *            timestamp a establecer
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

	public RLiteral getMapaWeb() {
		return mapaWeb;
	}

	public void setMapaWeb(RLiteral mapaWeb) {
		this.mapaWeb = mapaWeb;
	}

	public RLiteral getAvisoLegal() {
		return avisoLegal;
	}

	public void setAvisoLegal(RLiteral avisoLegal) {
		this.avisoLegal = avisoLegal;
	}

	public RLiteral getRss() {
		return rss;
	}

	public void setRss(RLiteral rss) {
		this.rss = rss;
	}

	public String getUrlFacebook() {
		return urlFacebook;
	}

	public void setUrlFacebook(String urlFacebook) {
		this.urlFacebook = urlFacebook;
	}

	public String getUrlInstagram() {
		return urlInstagram;
	}

	public void setUrlInstagram(String urlInstagram) {
		this.urlInstagram = urlInstagram;
	}

	public String getUrlTwitter() {
		return urlTwitter;
	}

	public void setUrlTwitter(String urlTwitter) {
		this.urlTwitter = urlTwitter;
	}

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setUrlYoutube(String urlYoutube) {
		this.urlYoutube = urlYoutube;
	}



}
