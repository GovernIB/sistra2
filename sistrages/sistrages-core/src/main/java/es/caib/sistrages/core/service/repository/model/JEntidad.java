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

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
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
	 * @param nombre
	 *            the nombre to set
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
	 * @param codigoDir3
	 *            the codigoDir3 to set
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
	 * @param activa
	 *            the activa to set
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
	 * @param roleAdministrador
	 *            the roleAdministrador to set
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
	 * @param logoAsistenteTramitacion
	 *            the logoAsistenteTramitacion to set
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
	 * @param cssAsistenteTramitacion
	 *            the cssAsistenteTramitacion to set
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
	 * @param logoGestorTramites
	 *            the logoGestorTramites to set
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
	 * @param piePaginaAsistenteTramitacion
	 *            the piePaginaAsistenteTramitacion to set
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
	 * @param email
	 *            the email to set
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
	 * @param contactoEmail
	 *            the contactoEmail to set
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
	 * @param contactoTelefono
	 *            the contactoTelefono to set
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
	 * @param contactoUrl
	 *            the contactoUrl to set
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
	 * @param contactoFormularioIncidencias
	 *            the contactoFormularioIncidencias to set
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
	 * @param telefono
	 *            the telefono to set
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
	 * @param urlSoporte
	 *            the urlSoporte to set
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
	 * @param urlCarpetaCiudadana
	 *            the urlCarpetaCiudadana to set
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
	 * @param diasPreregistro
	 *            the diasPreregistro to set
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
	 * @param lopd
	 *            the lopd to set
	 */
	public void setLopd(final JLiteral lopd) {
		this.lopd = lopd;
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
		return entidad;
	}

}
