package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.Entidad;

/**
 * JEntidad.
 */
@Entity
@Table(name = "STG_ENTIDA", uniqueConstraints = @UniqueConstraint(columnNames = "ENT_DIR3"))
public class JEntidad implements IModelApi {

	/** Serial Version UID. **/
	private static final long serialVersionUID = 1L;

	/** Código interno */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_ENTIDA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_ENTIDA_SEQ", sequenceName = "STG_ENTIDA_SEQ")
	@Column(name = "ENT_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Nombre Entidad */
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_NOMBRE", nullable = false)
	private JLiteral nombre;

	/** Código DIR3 */
	@Column(name = "ENT_DIR3", unique = true, nullable = false, length = 10)
	private String codigoDir3;

	/** Indica si la entidad está activa */
	@Column(name = "ENT_ACTIVA", nullable = false, precision = 1, scale = 0)
	private boolean activa;

	/** Role asociado al administrador de la entidad */
	@Column(name = "ENT_ROLADM", nullable = false, length = 100)
	private String roleAdministrador;

	/** Logo entidad GestorExternoFormularios Trámites */
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_LOGOTT")
	private JFichero logoAsistenteTramitacion;

	/** CSS Asistente Tramitación **/
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_CSSTT")
	private JFichero cssAsistenteTramitacion;

	/** Logo entidad Asistente Tramitación */
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_LOGOTG")
	private JFichero logoGestorTramites;

	/** Pie de página de contacto para Asistente Tramitación (HTML) */
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_PIETT")
	private JLiteral piePaginaAsistenteTramitacion;

	/** Email contacto genérico */
	@Column(name = "ENT_EMAIL", length = 500)
	private String email;

	/** Habilitado contacto email */
	@Column(name = "ENT_CNTEMA", nullable = false, precision = 1, scale = 0)
	private boolean contactoEmail;

	/** Habilitado contacto teléfono */
	@Column(name = "ENT_CNTTEL", nullable = false, precision = 1, scale = 0)
	private boolean contactoTelefono;

	/** Habilitado contacto url */
	@Column(name = "ENT_CNTURL", nullable = false, precision = 1, scale = 0)
	private boolean contactoUrl;

	/** Habilitado contacto formulario incidencias */
	@Column(name = "ENT_CNTFOR", nullable = false, precision = 1, scale = 0)
	private boolean contactoFormularioIncidencias;

	/** Teléfono contacto */
	@Column(name = "ENT_TELEFO", length = 10)
	private String telefono;

	/** Url soporte */
	@Column(name = "ENT_URLSOP", length = 500)
	private String urlSoporte;

	/** Url Carpeta Ciudadana */
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_URLCAR")
	private JLiteral urlCarpetaCiudadana;

	/** Dias preregistro */
	@Column(name = "ENT_PRGDIA")
	private Integer diasPreregistro;

	/** Texto respecto LOPD. **/
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_LOPD")
	private JLiteral lopd;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_MAPAW")
	private JLiteral mapaWeb;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_AVISOL")
	private JLiteral avisoLegal;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_RSS")
	private JLiteral rss;

	@Column(name = "ENT_YOUTUB", length = 255)
	private String urlYoutube;

	@Column(name = "ENT_INSTAG", length = 255)
	private String urlInstagram;

	@Column(name = "ENT_TWITTR", length = 255)
	private String urlTwitter;

	@Column(name = "ENT_FACEBK", length = 255)
	private String urlFacebook;

	/** Role asociado al administrador de la entidad */
	@Column(name = "ENT_ROLSUP", nullable = false, length = 100)
	private String roleSup;

	/** Texto respecto LOPD (introducción). **/
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_LOPDI")
	private JLiteral lopdIntroduccion;

	/** Permite subsanación paso anexar. **/
	@Column(name = "ENT_SUBANE", nullable = false, precision = 1, scale = 0)
	private boolean permiteSubsanarAnexar;

	/** Permite subsanación paso pagar. **/
	@Column(name = "ENT_SUBPAG", nullable = false, precision = 1, scale = 0)
	private boolean permiteSubsanarPagar;

	/** Permite subsanación paso registrar. **/
	@Column(name = "ENT_SUBREG", nullable = false, precision = 1, scale = 0)
	private boolean permiteSubsanarRegistrar;

	/** Instrucciones subsanación */
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_SUBINS")
	private JLiteral instruccionesSubsanacion;

	/** Días a mantener los trámites presenciales. **/
	@Column(name = "ENT_PRSDIA", nullable = false, precision = 1, scale = 0)
	private Integer diasTramitesPresenciales;

	/** Instrucciones presencial */
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ENT_PRSINS")
	private JLiteral instruccionesPresencial;

	/** Habilitado registro centralizado */
	@Column(name = "ENT_REGCEN", nullable = false, precision = 1, scale = 0)
	private boolean registroCentralizado;

	/** Codigo de oficina registro centralizado. **/
	@Column(name = "ENT_REGOFI", length = 20)
	private String oficinaRegistroCentralizado;

	/** Habilitado valorar trámites */
	@Column(name = "ENT_VALTRA", nullable = false, precision = 1, scale = 0)
	private boolean valorarTramite;

	/** Registro centralizado. */
	@Column(name = "ENT_REGDOC", nullable = false, precision = 1, scale = 0)
	private boolean registroOcultarDescargaDocumentos;

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nombre
	 */
	public JLiteral getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(final JLiteral nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the codigoDir3
	 */
	public String getCodigoDir3() {
		return codigoDir3;
	}

	/**
	 * @param codigoDir3 the codigoDir3 to set
	 */
	public void setCodigoDir3(final String codigoDir3) {
		this.codigoDir3 = codigoDir3;
	}

	/**
	 * @return the activa
	 */
	public boolean isActiva() {
		return activa;
	}

	/**
	 * @param activa the activa to set
	 */
	public void setActiva(final boolean activa) {
		this.activa = activa;
	}

	/**
	 * @return the roleAdministrador
	 */
	public String getRoleAdministrador() {
		return roleAdministrador;
	}

	/**
	 * @param roleAdministrador the roleAdministrador to set
	 */
	public void setRoleAdministrador(final String roleAdministrador) {
		this.roleAdministrador = roleAdministrador;
	}

	/**
	 * @return the logoAsistenteTramitacion
	 */
	public JFichero getLogoAsistenteTramitacion() {
		return logoAsistenteTramitacion;
	}

	/**
	 * @param logoAsistenteTramitacion the logoAsistenteTramitacion to set
	 */
	public void setLogoAsistenteTramitacion(final JFichero logoAsistenteTramitacion) {
		this.logoAsistenteTramitacion = logoAsistenteTramitacion;
	}

	/**
	 * @return the cssAsistenteTramitacion
	 */
	public JFichero getCssAsistenteTramitacion() {
		return cssAsistenteTramitacion;
	}

	/**
	 * @param cssAsistenteTramitacion the cssAsistenteTramitacion to set
	 */
	public void setCssAsistenteTramitacion(final JFichero cssAsistenteTramitacion) {
		this.cssAsistenteTramitacion = cssAsistenteTramitacion;
	}

	/**
	 * @return the logoGestorTramites
	 */
	public JFichero getLogoGestorTramites() {
		return logoGestorTramites;
	}

	/**
	 * @param logoGestorTramites the logoGestorTramites to set
	 */
	public void setLogoGestorTramites(final JFichero logoGestorTramites) {
		this.logoGestorTramites = logoGestorTramites;
	}

	/**
	 * @return the piePaginaAsistenteTramitacion
	 */
	public JLiteral getPiePaginaAsistenteTramitacion() {
		return piePaginaAsistenteTramitacion;
	}

	/**
	 * @param piePaginaAsistenteTramitacion the piePaginaAsistenteTramitacion to set
	 */
	public void setPiePaginaAsistenteTramitacion(final JLiteral piePaginaAsistenteTramitacion) {
		this.piePaginaAsistenteTramitacion = piePaginaAsistenteTramitacion;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the contactoEmail
	 */
	public boolean isContactoEmail() {
		return contactoEmail;
	}

	/**
	 * @param contactoEmail the contactoEmail to set
	 */
	public void setContactoEmail(final boolean contactoEmail) {
		this.contactoEmail = contactoEmail;
	}

	/**
	 * @return the contactoTelefono
	 */
	public boolean isContactoTelefono() {
		return contactoTelefono;
	}

	/**
	 * @param contactoTelefono the contactoTelefono to set
	 */
	public void setContactoTelefono(final boolean contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}

	/**
	 * @return the contactoUrl
	 */
	public boolean isContactoUrl() {
		return contactoUrl;
	}

	/**
	 * @param contactoUrl the contactoUrl to set
	 */
	public void setContactoUrl(final boolean contactoUrl) {
		this.contactoUrl = contactoUrl;
	}

	/**
	 * @return the contactoFormularioIncidencias
	 */
	public boolean isContactoFormularioIncidencias() {
		return contactoFormularioIncidencias;
	}

	/**
	 * @param contactoFormularioIncidencias the contactoFormularioIncidencias to set
	 */
	public void setContactoFormularioIncidencias(final boolean contactoFormularioIncidencias) {
		this.contactoFormularioIncidencias = contactoFormularioIncidencias;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the urlSoporte
	 */
	public String getUrlSoporte() {
		return urlSoporte;
	}

	/**
	 * @param urlSoporte the urlSoporte to set
	 */
	public void setUrlSoporte(final String urlSoporte) {
		this.urlSoporte = urlSoporte;
	}

	/**
	 * @return the urlCarpetaCiudadana
	 */
	public JLiteral getUrlCarpetaCiudadana() {
		return urlCarpetaCiudadana;
	}

	/**
	 * @param urlCarpetaCiudadana the urlCarpetaCiudadana to set
	 */
	public void setUrlCarpetaCiudadana(final JLiteral urlCarpetaCiudadana) {
		this.urlCarpetaCiudadana = urlCarpetaCiudadana;
	}

	/**
	 * @return the diasPreregistro
	 */
	public Integer getDiasPreregistro() {
		return diasPreregistro;
	}

	/**
	 * @param diasPreregistro the diasPreregistro to set
	 */
	public void setDiasPreregistro(final Integer diasPreregistro) {
		this.diasPreregistro = diasPreregistro;
	}

	/**
	 * @return the lopd
	 */
	public JLiteral getLopd() {
		return lopd;
	}

	/**
	 * @param lopd the lopd to set
	 */
	public void setLopd(final JLiteral lopd) {
		this.lopd = lopd;
	}

	public JLiteral getMapaWeb() {
		return mapaWeb;
	}

	public void setMapaWeb(final JLiteral mapaWeb) {
		this.mapaWeb = mapaWeb;
	}

	public JLiteral getAvisoLegal() {
		return avisoLegal;
	}

	public void setAvisoLegal(final JLiteral avisoLegal) {
		this.avisoLegal = avisoLegal;
	}

	public JLiteral getRss() {
		return rss;
	}

	public void setRss(final JLiteral rss) {
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

	public String getRoleSup() {
		return roleSup;
	}

	public void setRoleSup(final String roleSup) {
		this.roleSup = roleSup;
	}

	/**
	 * @return the lopdIntroduccion
	 */
	public JLiteral getLopdIntroduccion() {
		return lopdIntroduccion;
	}

	/**
	 * @param lopdIntroduccion the lopdIntroduccion to set
	 */
	public void setLopdIntroduccion(final JLiteral lopdIntroduccion) {
		this.lopdIntroduccion = lopdIntroduccion;
	}

	/**
	 * @return the permiteSubsanarAnexar
	 */
	public boolean isPermiteSubsanarAnexar() {
		return permiteSubsanarAnexar;
	}

	/**
	 * @param permiteSubsanarAnexar the permiteSubsanarAnexar to set
	 */
	public void setPermiteSubsanarAnexar(final boolean permiteSubsanarAnexar) {
		this.permiteSubsanarAnexar = permiteSubsanarAnexar;
	}

	/**
	 * @return the permiteSubsanarPagar
	 */
	public boolean isPermiteSubsanarPagar() {
		return permiteSubsanarPagar;
	}

	/**
	 * @param permiteSubsanarPagar the permiteSubsanarPagar to set
	 */
	public void setPermiteSubsanarPagar(final boolean permiteSubsanarPagar) {
		this.permiteSubsanarPagar = permiteSubsanarPagar;
	}

	/**
	 * @return the permiteSubsanarRegistrar
	 */
	public boolean isPermiteSubsanarRegistrar() {
		return permiteSubsanarRegistrar;
	}

	/**
	 * @param permiteSubsanarRegistrar the permiteSubsanarRegistrar to set
	 */
	public void setPermiteSubsanarRegistrar(final boolean permiteSubsanarRegistrar) {
		this.permiteSubsanarRegistrar = permiteSubsanarRegistrar;
	}

	/**
	 * @return the instruccionesSubsanacion
	 */
	public JLiteral getInstruccionesSubsanacion() {
		return instruccionesSubsanacion;
	}

	/**
	 * @param instruccionesSubsanacion the instruccionesSubsanacion to set
	 */
	public void setInstruccionesSubsanacion(final JLiteral instruccionesSubsanacion) {
		this.instruccionesSubsanacion = instruccionesSubsanacion;
	}

	/**
	 * @return the diasTramitesPresenciales
	 */
	public Integer isDiasTramitesPresenciales() {
		return diasTramitesPresenciales;
	}

	/**
	 * @param diasTramitesPresenciales the diasTramitesPresenciales to set
	 */
	public void setDiasTramitesPresenciales(final Integer diasTramitesPresenciales) {
		this.diasTramitesPresenciales = diasTramitesPresenciales;
	}

	/**
	 * @return the instruccionesPresencial
	 */
	public JLiteral getInstruccionesPresencial() {
		return instruccionesPresencial;
	}

	/**
	 * @param instruccionesPresencial the instruccionesPresencial to set
	 */
	public void setInstruccionesPresencial(final JLiteral instruccionesPresencial) {
		this.instruccionesPresencial = instruccionesPresencial;
	}

	/**
	 * @return the registroCentralizado
	 */
	public boolean isRegistroCentralizado() {
		return registroCentralizado;
	}

	/**
	 * @param registroCentralizado the registroCentralizado to set
	 */
	public void setRegistroCentralizado(final boolean registroCentralizado) {
		this.registroCentralizado = registroCentralizado;
	}

	/**
	 * @return the diasTramitesPresenciales
	 */
	public Integer getDiasTramitesPresenciales() {
		return diasTramitesPresenciales;
	}

	/**
	 * @return the oficinaRegistroCentralizado
	 */
	public String getOficinaRegistroCentralizado() {
		return oficinaRegistroCentralizado;
	}

	/**
	 * @param oficinaRegistroCentralizado the oficinaRegistroCentralizado to set
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
	 * @param valorarTramite the valorarTramite to set
	 */
	public void setValorarTramite(final boolean valorarTramite) {
		this.valorarTramite = valorarTramite;
	}

	/**
	 * @return the registroOcultarDescargaDocumentos
	 */
	public boolean isRegistroOcultarDescargaDocumentos() {
		return registroOcultarDescargaDocumentos;
	}

	/**
	 * @param registroOcultarDescargaDocumentos the
	 *                                          registroOcultarDescargaDocumentos to
	 *                                          set
	 */
	public void setRegistroOcultarDescargaDocumentos(final boolean registroOcultarDescargaDocumentos) {
		this.registroOcultarDescargaDocumentos = registroOcultarDescargaDocumentos;
	}

	/**
	 * toModel.
	 */
	public Entidad toModel() {
		final Entidad entidad = new Entidad();
		entidad.setCodigo(this.codigo);
		entidad.setCodigoDIR3(this.codigoDir3);
		entidad.setNombre(this.nombre.toModel());
		entidad.setEmail(this.email);
		entidad.setRol(this.roleAdministrador);
		entidad.setActivo(this.activa);
		if (this.cssAsistenteTramitacion != null) {
			entidad.setCss(this.cssAsistenteTramitacion.toModel());
		}
		entidad.setEmailHabilitado(contactoEmail);
		entidad.setFormularioIncidenciasHabilitado(contactoFormularioIncidencias);
		if (this.logoAsistenteTramitacion != null) {
			entidad.setLogoAsistente(this.logoAsistenteTramitacion.toModel());
		}
		if (this.logoGestorTramites != null) {
			entidad.setLogoGestor(this.logoGestorTramites.toModel());
		}
		if (this.piePaginaAsistenteTramitacion != null) {
			entidad.setPie(this.piePaginaAsistenteTramitacion.toModel());
		}
		entidad.setRol(this.roleAdministrador);
		entidad.setTelefono(this.telefono);
		entidad.setTelefonoHabilitado(contactoTelefono);
		entidad.setUrlSoporte(urlSoporte);
		entidad.setUrlSoporteHabilitado(contactoUrl);
		if (this.getUrlCarpetaCiudadana() != null) {
			entidad.setUrlCarpetaCiudadana(this.getUrlCarpetaCiudadana().toModel());
		}
		if (this.lopd != null) {
			entidad.setLopd(this.lopd.toModel());
		}
		entidad.setDiasPreregistro(this.diasPreregistro);

		if (this.mapaWeb != null) {
			entidad.setMapaWeb(this.mapaWeb.toModel());
		}

		if (this.avisoLegal != null) {
			entidad.setAvisoLegal(this.avisoLegal.toModel());
		}

		if (this.rss != null) {
			entidad.setRss(this.rss.toModel());
		}

		entidad.setUrlYoutube(urlYoutube);
		entidad.setUrlInstagram(urlInstagram);
		entidad.setUrlTwitter(urlTwitter);
		entidad.setUrlFacebook(urlFacebook);

		entidad.setRolSup(roleSup);

		if (lopdIntroduccion != null) {
			entidad.setLopdIntroduccion(lopdIntroduccion.toModel());
		}
		entidad.setPermiteSubsanarAnexar(permiteSubsanarAnexar);
		entidad.setPermiteSubsanarPagar(permiteSubsanarPagar);
		entidad.setPermiteSubsanarRegistrar(permiteSubsanarRegistrar);
		if (instruccionesSubsanacion != null) {
			entidad.setInstruccionesSubsanacion(instruccionesSubsanacion.toModel());
		}
		if (instruccionesPresencial != null) {
			entidad.setInstruccionesPresencial(instruccionesPresencial.toModel());
		}
		entidad.setDiasTramitesPresenciales(diasTramitesPresenciales);
		entidad.setRegistroCentralizado(registroCentralizado);
		entidad.setOficinaRegistroCentralizado(oficinaRegistroCentralizado);
		entidad.setValorarTramite(valorarTramite);
		entidad.setRegistroOcultarDescargaDocumentos(registroOcultarDescargaDocumentos);
		return entidad;
	}

}
