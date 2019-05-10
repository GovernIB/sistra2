package es.caib.sistrages.core.api.model;

/**
 * La clase Entidad.
 */

public class Entidad extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** codigo. */
	private Long codigo;

	/** codigo DIR 3. */
	private String codigoDIR3;

	/** nombre. */
	private Literal nombre;

	/** activo. */
	private boolean activo;

	/** rol. */
	private String rol;

	/** logo gestor. */
	private Fichero logoGestor;

	/** logo asistente. */
	private Fichero logoAsistente;

	/** css. */
	private Fichero css;

	/** pie. */
	private Literal pie;

	/** email. */
	private String email;

	/** email habilitado. */
	private boolean emailHabilitado;

	/** telefono. */
	private String telefono;

	/** telefono habilitado. */
	private boolean telefonoHabilitado;

	/** url soporte. */
	private String urlSoporte;

	/** url soporte habilitado. */
	private boolean urlSoporteHabilitado;

	/** formulario incidencias habilitado. */
	private boolean formularioIncidenciasHabilitado;

	/** url carpeta ciudadana. */
	private Literal urlCarpetaCiudadana;

	/** dias preregistro. */
	private Integer diasPreregistro;

	/** lopd. */
	private Literal lopd;

	/** mapa web. */
	private Literal mapaWeb;

	/** aviso legal. */
	private Literal avisoLegal;

	/** rss. */
	private Literal rss;

	/** url youtube. */
	private String urlYoutube;

	/** url instagram. */
	private String urlInstagram;

	/** url twitter. */
	private String urlTwitter;

	/** url facebook. */
	private String urlFacebook;

	/** Rol. **/
	private String rolSup;

	/** Texto respecto LOPD (introducción). **/
	private Literal lopdIntroduccion;

	/** Permite subsanación paso anexar. **/
	private boolean permiteSubsanarAnexar;

	/** Permite subsanación paso pagar. **/
	private boolean permiteSubsanarPagar;

	/** Permite subsanación paso registrar. **/
	private boolean permiteSubsanarRegistrar;

	/** Instrucciones subsanación */
	private Literal instruccionesSubsanacion;

	/** Días a mantener los trámites presenciales. **/
	private Integer diasTramitesPresenciales;

	/** Instrucciones presencial */
	private Literal instruccionesPresencial;

	/** Habilitado registro centralizado */
	private boolean registroCentralizado;

	/** Codigo de oficina registro centralizado. **/
	private String oficinaRegistroCentralizado;

	/** Permite valorar tramite. **/
	private boolean valorarTramite;

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

	public void setMapaWeb(final Literal mapaWeb) {
		this.mapaWeb = mapaWeb;
	}

	public Literal getAvisoLegal() {
		return avisoLegal;
	}

	public void setAvisoLegal(final Literal avisoLegal) {
		this.avisoLegal = avisoLegal;
	}

	public Literal getRss() {
		return rss;
	}

	public void setRss(final Literal rss) {
		this.rss = rss;
	}

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setUrlYoutube(final String urlYoutube) {
		this.urlYoutube = urlYoutube;
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

	public String getUrlFacebook() {
		return urlFacebook;
	}

	public void setUrlFacebook(final String urlFacebook) {
		this.urlFacebook = urlFacebook;
	}

	public String getRolSup() {
		return rolSup;
	}

	public void setRolSup(final String rolSup) {
		this.rolSup = rolSup;
	}

	/**
	 * @return the lopdIntroduccion
	 */
	public final Literal getLopdIntroduccion() {
		return lopdIntroduccion;
	}

	/**
	 * @param lopdIntroduccion
	 *            the lopdIntroduccion to set
	 */
	public final void setLopdIntroduccion(final Literal lopdIntroduccion) {
		this.lopdIntroduccion = lopdIntroduccion;
	}

	/**
	 * @return the permiteSubsanarAnexar
	 */
	public final boolean isPermiteSubsanarAnexar() {
		return permiteSubsanarAnexar;
	}

	/**
	 * @param permiteSubsanarAnexar
	 *            the permiteSubsanarAnexar to set
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
	 * @param permiteSubsanarPagar
	 *            the permiteSubsanarPagar to set
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
	 * @param permiteSubsanarRegistrar
	 *            the permiteSubsanarRegistrar to set
	 */
	public final void setPermiteSubsanarRegistrar(final boolean permiteSubsanarRegistrar) {
		this.permiteSubsanarRegistrar = permiteSubsanarRegistrar;
	}

	/**
	 * @return the instruccionesSubsanacion
	 */
	public final Literal getInstruccionesSubsanacion() {
		return instruccionesSubsanacion;
	}

	/**
	 * @param instruccionesSubsanacion
	 *            the instruccionesSubsanacion to set
	 */
	public final void setInstruccionesSubsanacion(final Literal instruccionesSubsanacion) {
		this.instruccionesSubsanacion = instruccionesSubsanacion;
	}

	/**
	 * @return the instruccionesPresencial
	 */
	public final Literal getInstruccionesPresencial() {
		return instruccionesPresencial;
	}

	/**
	 * @param instruccionesPresencial
	 *            the instruccionesPresencial to set
	 */
	public final void setInstruccionesPresencial(final Literal instruccionesPresencial) {
		this.instruccionesPresencial = instruccionesPresencial;
	}

	/**
	 * @return the diasTramitesPresenciales
	 */
	public Integer getDiasTramitesPresenciales() {
		return diasTramitesPresenciales;
	}

	/**
	 * @param diasTramitesPresenciales
	 *            the diasTramitesPresenciales to set
	 */
	public void setDiasTramitesPresenciales(final Integer diasTramitesPresenciales) {
		this.diasTramitesPresenciales = diasTramitesPresenciales;
	}

	/**
	 * @return the registroCentralizado
	 */
	public final boolean isRegistroCentralizado() {
		return registroCentralizado;
	}

	/**
	 * @param registroCentralizado
	 *            the registroCentralizado to set
	 */
	public final void setRegistroCentralizado(final boolean registroCentralizado) {
		this.registroCentralizado = registroCentralizado;
	}

	/**
	 * @return the oficinaRegistroCentralizado
	 */
	public String getOficinaRegistroCentralizado() {
		return oficinaRegistroCentralizado;
	}

	/**
	 * @param oficinaRegistroCentralizado
	 *            the oficinaRegistroCentralizado to set
	 */
	public void setOficinaRegistroCentralizado(final String oficinaRegistroCentralizado) {
		this.oficinaRegistroCentralizado = oficinaRegistroCentralizado;
	}

	/**
	 * @return the valorarTramite
	 */
	public boolean isValorarTramite() {
		return valorarTramite;
	}

	/**
	 * @param valorarTramite
	 *            the valorarTramite to set
	 */
	public void setValorarTramite(final boolean valorarTramite) {
		this.valorarTramite = valorarTramite;
	}

}
