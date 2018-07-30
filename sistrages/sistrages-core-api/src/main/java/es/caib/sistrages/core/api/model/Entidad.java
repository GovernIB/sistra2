package es.caib.sistrages.core.api.model;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * La clase Entidad.
 */
@SuppressWarnings("serial")
public class Entidad extends ModelApi {

	/**
	 * codigo.
	 */
	private Long codigo;

	/**
	 * codigo DIR 3.
	 */
	private String codigoDIR3;

	/**
	 * nombre.
	 */
	private Literal nombre;

	/**
	 * activo.
	 */
	private boolean activo;

	/**
	 * rol.
	 */
	private String rol;

	/**
	 * logo gestor.
	 */
	private Fichero logoGestor;

	/**
	 * logo asistente.
	 */
	private Fichero logoAsistente;

	/**
	 * css.
	 */
	private Fichero css;

	/**
	 * pie.
	 */
	private Literal pie;

	/**
	 * email.
	 */
	private String email;

	/**
	 * email habilitado.
	 */
	private boolean emailHabilitado;

	/**
	 * telefono.
	 */
	private String telefono;

	/**
	 * telefono habilitado.
	 */
	private boolean telefonoHabilitado;

	/**
	 * url soporte.
	 */
	private String urlSoporte;

	/**
	 * url soporte habilitado.
	 */
	private boolean urlSoporteHabilitado;

	/**
	 * formulario incidencias habilitado.
	 */
	private boolean formularioIncidenciasHabilitado;

	// TODO Gestionarlo mejor fuera de la entidad
	/**
	 * formulario incidencias.
	 */
	private List<FormularioSoporte> formularioIncidencias;

	/**
	 * url carpeta ciudadana.
	 */
	private Literal urlCarpetaCiudadana;

	/**
	 * dias preregistro.
	 */
	private Integer diasPreregistro;

	/**
	 * lopd.
	 */
	private Literal lopd;

	/**
	 * mapa web.
	 */
	private Literal mapaWeb;

	/**
	 * aviso legal.
	 */
	private Literal avisoLegal;

	/**
	 * rss.
	 */
	private Literal rss;

	/**
	 * url youtube.
	 */
	private String urlYoutube;

	/**
	 * url instagram.
	 */
	private String urlInstagram;

	/**
	 * url twitter.
	 */
	private String urlTwitter;

	/**
	 * url facebook.
	 */
	private String urlFacebook;

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de codigoDIR3.
	 *
	 * @return el valor de codigoDIR3
	 */
	public String getCodigoDIR3() {
		return codigoDIR3;
	}

	/**
	 * Establece el valor de codigoDIR3.
	 *
	 * @param codigoDIR3
	 *            el nuevo valor de codigoDIR3
	 */
	public void setCodigoDIR3(final String codigoDIR3) {
		this.codigoDIR3 = codigoDIR3;
	}

	/**
	 * Obtiene el valor de nombre.
	 *
	 * @return el valor de nombre
	 */
	public Literal getNombre() {
		return nombre;
	}

	/**
	 * Establece el valor de nombre.
	 *
	 * @param nombre
	 *            el nuevo valor de nombre
	 */
	public void setNombre(final Literal nombre) {
		this.nombre = nombre;
	}

	/**
	 * Verifica si es activo.
	 *
	 * @return true, si es activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Establece el valor de activo.
	 *
	 * @param activo
	 *            el nuevo valor de activo
	 */
	public void setActivo(final boolean activo) {
		this.activo = activo;
	}

	/**
	 * Obtiene el valor de rol.
	 *
	 * @return el valor de rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Establece el valor de rol.
	 *
	 * @param rol
	 *            el nuevo valor de rol
	 */
	public void setRol(final String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el valor de logoGestor.
	 *
	 * @return el valor de logoGestor
	 */
	public Fichero getLogoGestor() {
		return logoGestor;
	}

	/**
	 * Establece el valor de logoGestor.
	 *
	 * @param logoGestor
	 *            el nuevo valor de logoGestor
	 */
	public void setLogoGestor(final Fichero logoGestor) {
		this.logoGestor = logoGestor;
	}

	/**
	 * Obtiene el valor de logoAsistente.
	 *
	 * @return el valor de logoAsistente
	 */
	public Fichero getLogoAsistente() {
		return logoAsistente;
	}

	/**
	 * Establece el valor de logoAsistente.
	 *
	 * @param logoAsistente
	 *            el nuevo valor de logoAsistente
	 */
	public void setLogoAsistente(final Fichero logoAsistente) {
		this.logoAsistente = logoAsistente;
	}

	/**
	 * Obtiene el valor de css.
	 *
	 * @return el valor de css
	 */
	public Fichero getCss() {
		return css;
	}

	/**
	 * Establece el valor de css.
	 *
	 * @param css
	 *            el nuevo valor de css
	 */
	public void setCss(final Fichero css) {
		this.css = css;
	}

	/**
	 * Obtiene el valor de pie.
	 *
	 * @return el valor de pie
	 */
	public Literal getPie() {
		return pie;
	}

	/**
	 * Establece el valor de pie.
	 *
	 * @param pie
	 *            el nuevo valor de pie
	 */
	public void setPie(final Literal pie) {
		this.pie = pie;
	}

	/**
	 * Obtiene el valor de email.
	 *
	 * @return el valor de email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el valor de email.
	 *
	 * @param email
	 *            el nuevo valor de email
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Verifica si es email habilitado.
	 *
	 * @return true, si es email habilitado
	 */
	public boolean isEmailHabilitado() {
		return emailHabilitado;
	}

	/**
	 * Establece el valor de emailHabilitado.
	 *
	 * @param emailHabilitado
	 *            el nuevo valor de emailHabilitado
	 */
	public void setEmailHabilitado(final boolean emailHabilitado) {
		this.emailHabilitado = emailHabilitado;
	}

	/**
	 * Obtiene el valor de telefono.
	 *
	 * @return el valor de telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el valor de telefono.
	 *
	 * @param telefono
	 *            el nuevo valor de telefono
	 */
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Verifica si es telefono habilitado.
	 *
	 * @return true, si es telefono habilitado
	 */
	public boolean isTelefonoHabilitado() {
		return telefonoHabilitado;
	}

	/**
	 * Establece el valor de telefonoHabilitado.
	 *
	 * @param telefonoHabilitado
	 *            el nuevo valor de telefonoHabilitado
	 */
	public void setTelefonoHabilitado(final boolean telefonoHabilitado) {
		this.telefonoHabilitado = telefonoHabilitado;
	}

	/**
	 * Obtiene el valor de urlSoporte.
	 *
	 * @return el valor de urlSoporte
	 */
	public String getUrlSoporte() {
		return urlSoporte;
	}

	/**
	 * Establece el valor de urlSoporte.
	 *
	 * @param urlSoporte
	 *            el nuevo valor de urlSoporte
	 */
	public void setUrlSoporte(final String urlSoporte) {
		this.urlSoporte = urlSoporte;
	}

	/**
	 * Verifica si es url soporte habilitado.
	 *
	 * @return true, si es url soporte habilitado
	 */
	public boolean isUrlSoporteHabilitado() {
		return urlSoporteHabilitado;
	}

	/**
	 * Establece el valor de urlSoporteHabilitado.
	 *
	 * @param urlSoporteHabilitado
	 *            el nuevo valor de urlSoporteHabilitado
	 */
	public void setUrlSoporteHabilitado(final boolean urlSoporteHabilitado) {
		this.urlSoporteHabilitado = urlSoporteHabilitado;
	}

	/**
	 * Verifica si es formulario incidencias habilitado.
	 *
	 * @return true, si es formulario incidencias habilitado
	 */
	public boolean isFormularioIncidenciasHabilitado() {
		return formularioIncidenciasHabilitado;
	}

	/**
	 * Establece el valor de formularioIncidenciasHabilitado.
	 *
	 * @param formularioIncidenciasHabilitado
	 *            el nuevo valor de formularioIncidenciasHabilitado
	 */
	public void setFormularioIncidenciasHabilitado(final boolean formularioIncidenciasHabilitado) {
		this.formularioIncidenciasHabilitado = formularioIncidenciasHabilitado;
	}

	/**
	 * Obtiene el valor de formularioIncidencias.
	 *
	 * @return el valor de formularioIncidencias
	 */
	public List<FormularioSoporte> getFormularioIncidencias() {
		return formularioIncidencias;
	}

	/**
	 * Establece el valor de formularioIncidencias.
	 *
	 * @param formularioIncidencias
	 *            el nuevo valor de formularioIncidencias
	 */
	public void setFormularioIncidencias(final List<FormularioSoporte> formularioIncidencias) {
		this.formularioIncidencias = formularioIncidencias;
	}

	/**
	 * Obtiene el valor de urlCarpetaCiudadana.
	 *
	 * @return el valor de urlCarpetaCiudadana
	 */
	public Literal getUrlCarpetaCiudadana() {
		return urlCarpetaCiudadana;
	}

	/**
	 * Establece el valor de urlCarpetaCiudadana.
	 *
	 * @param urlCarpetaCiudadana
	 *            el nuevo valor de urlCarpetaCiudadana
	 */
	public void setUrlCarpetaCiudadana(final Literal urlCarpetaCiudadana) {
		this.urlCarpetaCiudadana = urlCarpetaCiudadana;
	}

	/**
	 * Obtiene el valor de lopd.
	 *
	 * @return el valor de lopd
	 */
	public Literal getLopd() {
		return lopd;
	}

	/**
	 * Establece el valor de lopd.
	 *
	 * @param lopd
	 *            el nuevo valor de lopd
	 */
	public void setLopd(final Literal lopd) {
		this.lopd = lopd;
	}

	/**
	 * Obtiene el valor de diasPreregistro.
	 *
	 * @return el valor de diasPreregistro
	 */
	public Integer getDiasPreregistro() {
		return diasPreregistro;
	}

	/**
	 * Establece el valor de diasPreregistro.
	 *
	 * @param diasPreregistro
	 *            el nuevo valor de diasPreregistro
	 */
	public void setDiasPreregistro(final Integer diasPreregistro) {
		this.diasPreregistro = diasPreregistro;
	}

	public Literal getMapaWeb() {
		return mapaWeb;
	}

	public void setMapaWeb(Literal mapaWeb) {
		this.mapaWeb = mapaWeb;
	}

	public Literal getAvisoLegal() {
		return avisoLegal;
	}

	public void setAvisoLegal(Literal avisoLegal) {
		this.avisoLegal = avisoLegal;
	}

	public Literal getRss() {
		return rss;
	}

	public void setRss(Literal rss) {
		this.rss = rss;
	}

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setUrlYoutube(String urlYoutube) {
		this.urlYoutube = urlYoutube;
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

	public String getUrlFacebook() {
		return urlFacebook;
	}

	public void setUrlFacebook(String urlFacebook) {
		this.urlFacebook = urlFacebook;
	}

}
