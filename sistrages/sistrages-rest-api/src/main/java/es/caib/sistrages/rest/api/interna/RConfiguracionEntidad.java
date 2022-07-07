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

	/** Título. */
	@ApiModelProperty(value = "Titulo")
	private RLiteral titulo;

	/** Referencia logo. */
	@ApiModelProperty(value = "Referencia logo")
	private String logo;

	/** Referencia icono. */
	@ApiModelProperty(value = "Referencia icono")
	private String icono;

	/** Referencia logo. */
	@ApiModelProperty(value = "Referencia logo")
	private String css;

	/** Contacto (HTML). */
	@ApiModelProperty(value = "Contacto (HTML)")
	private RLiteral contactoHTML;

	/** Url carpeta ciudadana. */
	@ApiModelProperty(value = "Url carpeta ciudadana")
	private RLiteral urlCarpeta;

	/** Url sede electrónica. */
	@ApiModelProperty(value = "Url sede electrónica")
	private RLiteral urlSede;

	/** Info LOPD (HTML). */
	@ApiModelProperty(value = "Info LOPD (HTML)")
	private RLiteral infoLopdHTML;

	/** Dias preregistro. */
	@ApiModelProperty(value = "Dias preregistro")
	private int diasPreregistro;

	@ApiModelProperty(value = "Areas de la entidad")
	private List<RArea> area;

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

	/** url de Twiter */
	@ApiModelProperty(value = "url de  Twiter")
	private String urlTwitter;

	/** url de Youtube */
	@ApiModelProperty(value = "url de Youtube")
	private String urlYoutube;

	/** logo gestor. */
	@ApiModelProperty(value = "logo Gestor")
	private String logoGestor;

	/** Plantillas por defecto para visualización formularios. */
	@ApiModelProperty(value = "Plantillas")
	private List<RPlantillaIdioma> plantillasDefecto;

	/** Texto respecto LOPD (introducción). **/
	@ApiModelProperty(value = "lopd introduccion")
	private RLiteral lopdIntroduccion;

	/** Permite subsanación paso anexar. **/
	@ApiModelProperty(value = "permite subsanar anexar")
	private boolean permiteSubsanarAnexar;

	/** Permite subsanación paso pagar. **/
	@ApiModelProperty(value = "permite subsanar pagar")
	private boolean permiteSubsanarPagar;

	/** Permite subsanación paso registrar. **/
	@ApiModelProperty(value = "permite subsanar registrar")
	private boolean permiteSubsanarRegistrar;

	/** Instrucciones subsanación */
	@ApiModelProperty(value = "instrucciones subsanacion")
	private RLiteral instruccionesSubsanacion;

	/** Días a mantener los trámites presenciales. **/
	@ApiModelProperty(value = "dias tramites presenciales")
	private Integer diasTramitesPresenciales;

	/** Instrucciones presencial */
	@ApiModelProperty(value = "instrucciones presencial")
	private RLiteral instruccionesPresencial;

	/** Registro centralizado. */
	@ApiModelProperty(value = "Registro centralizado")
	private boolean registroCentralizado;

	/** Registro ocultar descarga documentos. */
	@ApiModelProperty(value = "Ocultar descarga documentos en paso Guardar")
	private boolean registroOcultarDescargaDocumentos;

	/** Codigo de oficina registro si es registro centralizado */
	@ApiModelProperty(value = "Codigo de oficina registro si es registro centralizado")
	private String oficinaRegistroCentralizado;

	/** Permite valorar tramite. **/
	@ApiModelProperty(value = "Permite Valorar tramites")
	private boolean valorarTramite;

	/** Lista de incidencias valoraciones. */
	private List<RIncidenciaValoracion> incidenciasValoracion;

	/** Plantillas entidad. */
	@ApiModelProperty(value = "Plantillas entidad")
	private List<RPlantillaEntidad> plantillas;

	/** Envío remoto. */
	@ApiModelProperty(value = "Envios remoto")
	private List<REnvioRemoto> enviosRemoto;

	/** Configuraciones autenticación. */
	@ApiModelProperty(value = "Configuraciones autenticación entidad y sus areas")
	private List<RConfiguracionAutenticacion> configuracionesAutenticacion;

	/** Gestores formularios externos entidad. */
	@ApiModelProperty(value = "Gestores formularios externos entidad y sus areas")
	private List<RGestorFormularioExterno> gestoresFormulariosExternos;

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
	 * @param logo logo a establecer
	 */
	public void setLogo(final String logo) {
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
	 * @param css css a establecer
	 */
	public void setCss(final String css) {
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
	 * @param contactoHTML contactoHTML a establecer
	 */
	public void setContactoHTML(final RLiteral contactoHTML) {
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
	 * @param urlCarpeta urlCarpeta a establecer
	 */
	public void setUrlCarpeta(final RLiteral urlCarpeta) {
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
	 * @param email email a establecer
	 */
	public void setEmail(final String email) {
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
	 * @param ayudaEmail ayudaEmail a establecer
	 */
	public void setAyudaEmail(final boolean ayudaEmail) {
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
	 * @param ayudaTelefono ayudaTelefono a establecer
	 */
	public void setAyudaTelefono(final String ayudaTelefono) {
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
	 * @param ayudaUrl ayudaUrl a establecer
	 */
	public void setAyudaUrl(final String ayudaUrl) {
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
	 * @param ayudaFormulario ayudaFormulario a establecer
	 */
	public void setAyudaFormulario(final List<ROpcionFormularioSoporte> ayudaFormulario) {
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
	 * @param plugins plugins a establecer
	 */
	public void setPlugins(final List<RPlugin> plugins) {
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
	 * @param identificador identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
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
	 * @param infoLopdHTML infoLopdHTML a establecer
	 */
	public void setInfoLopdHTML(final RLiteral infoLopdHTML) {
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
	 * @param diasPreregistro diasPreregistro a establecer
	 */
	public void setDiasPreregistro(final int diasPreregistro) {
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
	 * @param descripcion descripcion a establecer
	 */
	public void setDescripcion(final RLiteral descripcion) {
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
	 * @param timestamp timestamp a establecer
	 */
	public void setTimestamp(final String timestamp) {
		this.timestamp = timestamp;
	}

	public RLiteral getMapaWeb() {
		return mapaWeb;
	}

	public void setMapaWeb(final RLiteral mapaWeb) {
		this.mapaWeb = mapaWeb;
	}

	public RLiteral getAvisoLegal() {
		return avisoLegal;
	}

	public void setAvisoLegal(final RLiteral avisoLegal) {
		this.avisoLegal = avisoLegal;
	}

	public RLiteral getRss() {
		return rss;
	}

	public void setRss(final RLiteral rss) {
		this.rss = rss;
	}

	public String getUrlFacebook() {
		return urlFacebook;
	}

	public void setUrlFacebook(final String urlFacebook) {
		this.urlFacebook = urlFacebook;
	}

	public String getUrlInstagram() {
		return urlInstagram;
	}

	public void setUrlInstagram(final String urlInstagram) {
		this.urlInstagram = urlInstagram;
	}

	public String getUrlTwitter() {
		return urlTwitter;
	}

	public void setUrlTwitter(final String urlTwitter) {
		this.urlTwitter = urlTwitter;
	}

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setUrlYoutube(final String urlYoutube) {
		this.urlYoutube = urlYoutube;
	}

	public String getLogoGestor() {
		return logoGestor;
	}

	public void setLogoGestor(final String logoGestor) {
		this.logoGestor = logoGestor;
	}

	/**
	 * Método de acceso a plantillasDefecto.
	 *
	 * @return plantillasDefecto
	 */
	public List<RPlantillaIdioma> getPlantillasDefecto() {
		return plantillasDefecto;
	}

	/**
	 * Método para establecer plantillasDefecto.
	 *
	 * @param plantillasDefecto plantillasDefecto a establecer
	 */
	public void setPlantillasDefecto(final List<RPlantillaIdioma> plantillasDefecto) {
		this.plantillasDefecto = plantillasDefecto;
	}

	/**
	 * @return the lopdIntroduccion
	 */
	public final RLiteral getLopdIntroduccion() {
		return lopdIntroduccion;
	}

	/**
	 * @param lopdIntroduccion the lopdIntroduccion to set
	 */
	public final void setLopdIntroduccion(final RLiteral lopdIntroduccion) {
		this.lopdIntroduccion = lopdIntroduccion;
	}

	/**
	 * @return the permiteSubsanarAnexar
	 */
	public final boolean isPermiteSubsanarAnexar() {
		return permiteSubsanarAnexar;
	}

	/**
	 * @param permiteSubsanarAnexar the permiteSubsanarAnexar to set
	 */
	public final void setPermiteSubsanarAnexar(final boolean permiteSubsanarAnexar) {
		this.permiteSubsanarAnexar = permiteSubsanarAnexar;
	}

	/**
	 * @return the permiteSubsanarPagar
	 */
	public final boolean isPermiteSubsanarPagar() {
		return permiteSubsanarPagar;
	}

	/**
	 * @param permiteSubsanarPagar the permiteSubsanarPagar to set
	 */
	public final void setPermiteSubsanarPagar(final boolean permiteSubsanarPagar) {
		this.permiteSubsanarPagar = permiteSubsanarPagar;
	}

	/**
	 * @return the permiteSubsanarRegistrar
	 */
	public final boolean isPermiteSubsanarRegistrar() {
		return permiteSubsanarRegistrar;
	}

	/**
	 * @param permiteSubsanarRegistrar the permiteSubsanarRegistrar to set
	 */
	public final void setPermiteSubsanarRegistrar(final boolean permiteSubsanarRegistrar) {
		this.permiteSubsanarRegistrar = permiteSubsanarRegistrar;
	}

	/**
	 * @return the instruccionesSubsanacion
	 */
	public final RLiteral getInstruccionesSubsanacion() {
		return instruccionesSubsanacion;
	}

	/**
	 * @param instruccionesSubsanacion the instruccionesSubsanacion to set
	 */
	public final void setInstruccionesSubsanacion(final RLiteral instruccionesSubsanacion) {
		this.instruccionesSubsanacion = instruccionesSubsanacion;
	}

	/**
	 * @return the diasTramitesPresenciales
	 */
	public final Integer getDiasTramitesPresenciales() {
		return diasTramitesPresenciales;
	}

	/**
	 * @param diasTramitesPresenciales the diasTramitesPresenciales to set
	 */
	public final void setDiasTramitesPresenciales(final Integer diasTramitesPresenciales) {
		this.diasTramitesPresenciales = diasTramitesPresenciales;
	}

	/**
	 * @return the instruccionesPresencial
	 */
	public final RLiteral getInstruccionesPresencial() {
		return instruccionesPresencial;
	}

	/**
	 * @param instruccionesPresencial the instruccionesPresencial to set
	 */
	public final void setInstruccionesPresencial(final RLiteral instruccionesPresencial) {
		this.instruccionesPresencial = instruccionesPresencial;
	}

	/**
	 * @return the registroCentralizado
	 */
	public final boolean isRegistroCentralizado() {
		return registroCentralizado;
	}

	/**
	 * @param registroCentralizado the registroCentralizado to set
	 */
	public final void setRegistroCentralizado(final boolean registroCentralizado) {
		this.registroCentralizado = registroCentralizado;
	}

	/**
	 * @return the oficinaRegistroCentralizado
	 */
	public final String getOficinaRegistroCentralizado() {
		return oficinaRegistroCentralizado;
	}

	/**
	 * @param oficinaRegistroCentralizado the oficinaRegistroCentralizado to set
	 */
	public final void setOficinaRegistroCentralizado(final String oficinaRegistroCentralizado) {
		this.oficinaRegistroCentralizado = oficinaRegistroCentralizado;
	}

	/**
	 * @return the valorarTramite
	 */
	public final boolean isValorarTramite() {
		return valorarTramite;
	}

	/**
	 * @param valorarTramite the valorarTramite to set
	 */
	public final void setValorarTramite(final boolean valorarTramite) {
		this.valorarTramite = valorarTramite;
	}

	/**
	 * @return the incidenciasValoracion
	 */
	public List<RIncidenciaValoracion> getIncidenciasValoracion() {
		return incidenciasValoracion;
	}

	/**
	 * @param incidenciasValoracion the incidenciasValoracion to set
	 */
	public void setIncidenciasValoracion(final List<RIncidenciaValoracion> incidenciasValoracion) {
		this.incidenciasValoracion = incidenciasValoracion;
	}

	/**
	 * Método de acceso a registroOcultarDescargaDocumentos.
	 *
	 * @return registroOcultarDescargaDocumentos
	 */
	public boolean isRegistroOcultarDescargaDocumentos() {
		return registroOcultarDescargaDocumentos;
	}

	/**
	 * Método para establecer registroOcultarDescargaDocumentos.
	 *
	 * @param registroOcultarDescargaDocumentos registroOcultarDescargaDocumentos a
	 *                                          establecer
	 */
	public void setRegistroOcultarDescargaDocumentos(final boolean registroOcultarDescargaDocumentos) {
		this.registroOcultarDescargaDocumentos = registroOcultarDescargaDocumentos;
	}

	/**
	 * Método de acceso a urlSede.
	 *
	 * @return urlSede
	 */
	public RLiteral getUrlSede() {
		return urlSede;
	}

	/**
	 * Método para establecer urlSede.
	 *
	 * @param urlSede urlSede a establecer
	 */
	public void setUrlSede(final RLiteral urlSede) {
		this.urlSede = urlSede;
	}

	/**
	 * Método de acceso a plantillas.
	 *
	 * @return plantillas
	 */
	public List<RPlantillaEntidad> getPlantillas() {
		return plantillas;
	}

	/**
	 * Método para establecer plantillas.
	 *
	 * @param plantillas plantillas a establecer
	 */
	public void setPlantillas(final List<RPlantillaEntidad> plantillas) {
		this.plantillas = plantillas;
	}

	/**
	 * @return the area
	 */
	public final List<RArea> getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public final void setArea(final List<RArea> area) {
		this.area = area;
	}

	/**
	 * Método de acceso a enviosRemoto.
	 *
	 * @return enviosRemoto
	 */
	public List<REnvioRemoto> getEnviosRemoto() {
		return enviosRemoto;
	}

	/**
	 * Método para establecer enviosRemoto.
	 *
	 * @param enviosRemoto enviosRemoto a establecer
	 */
	public void setEnviosRemoto(final List<REnvioRemoto> enviosRemoto) {
		this.enviosRemoto = enviosRemoto;
	}

	/**
	 * Método de acceso a configuracionesAutenticacion.
	 *
	 * @return configuracionesAutenticacion
	 */
	public List<RConfiguracionAutenticacion> getConfiguracionesAutenticacion() {
		return configuracionesAutenticacion;
	}

	/**
	 * Método para establecer configuracionesAutenticacion.
	 *
	 * @param configuracionesAutenticacion configuracionesAutenticacion a establecer
	 */
	public void setConfiguracionesAutenticacion(final List<RConfiguracionAutenticacion> configuracionesAutenticacion) {
		this.configuracionesAutenticacion = configuracionesAutenticacion;
	}

	/**
	 * Método de acceso a gestoresFormulariosExternos.
	 *
	 * @return gestoresFormulariosExternos
	 */
	public List<RGestorFormularioExterno> getGestoresFormulariosExternos() {
		return gestoresFormulariosExternos;
	}

	/**
	 * @return the titulo
	 */
	public final RLiteral getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public final void setTitulo(RLiteral titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the icono
	 */
	public final String getIcono() {
		return icono;
	}

	/**
	 * @param icono the icono to set
	 */
	public final void setIcono(String icono) {
		this.icono = icono;
	}

	/**
	 * Método para establecer gestoresFormulariosExternos.
	 *
	 * @param gestoresFormulariosExternos gestoresFormulariosExternos a establecer
	 */
	public void setGestoresFormulariosExternos(final List<RGestorFormularioExterno> gestoresFormulariosExternos) {
		this.gestoresFormulariosExternos = gestoresFormulariosExternos;
	}

}
