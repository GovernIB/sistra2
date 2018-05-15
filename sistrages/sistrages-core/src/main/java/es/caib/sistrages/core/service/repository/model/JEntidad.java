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

	/** Logo entidad Gestor Trámites */
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

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFichero getLogoAsistenteTramitacion() {
		return this.logoAsistenteTramitacion;
	}

	public void setLogoAsistenteTramitacion(final JFichero logoAsistenteTramitacion) {
		this.logoAsistenteTramitacion = logoAsistenteTramitacion;
	}

	public JFichero getCssAsistenteTramitacion() {
		return this.cssAsistenteTramitacion;
	}

	public void setCssAsistenteTramitacion(final JFichero cssAsistenteTramitacion) {
		this.cssAsistenteTramitacion = cssAsistenteTramitacion;
	}

	public JFichero getLogoGestorTramites() {
		return this.logoGestorTramites;
	}

	public void setLogoGestorTramites(final JFichero logoGestorTramites) {
		this.logoGestorTramites = logoGestorTramites;
	}

	public JLiteral getNombre() {
		return this.nombre;
	}

	public void setNombre(final JLiteral nombre) {
		this.nombre = nombre;
	}

	public JLiteral getPiePaginaAsistenteTramitacion() {
		return this.piePaginaAsistenteTramitacion;
	}

	public void setPiePaginaAsistenteTramitacion(final JLiteral piePaginaAsistenteTramitacion) {
		this.piePaginaAsistenteTramitacion = piePaginaAsistenteTramitacion;
	}

	public String getCodigoDir3() {
		return this.codigoDir3;
	}

	public void setCodigoDir3(final String codigoDir3) {
		this.codigoDir3 = codigoDir3;
	}

	public boolean isActiva() {
		return this.activa;
	}

	public void setActiva(final boolean activa) {
		this.activa = activa;
	}

	public String getRoleAdministrador() {
		return this.roleAdministrador;
	}

	public void setRoleAdministrador(final String roleAdministrador) {
		this.roleAdministrador = roleAdministrador;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public boolean isContactoEmail() {
		return this.contactoEmail;
	}

	public void setContactoEmail(final boolean contactoEmail) {
		this.contactoEmail = contactoEmail;
	}

	public boolean isContactoTelefono() {
		return this.contactoTelefono;
	}

	public void setContactoTelefono(final boolean contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}

	public boolean isContactoUrl() {
		return this.contactoUrl;
	}

	public void setContactoUrl(final boolean contactoUrl) {
		this.contactoUrl = contactoUrl;
	}

	public boolean isContactoFormularioIncidencias() {
		return this.contactoFormularioIncidencias;
	}

	public void setContactoFormularioIncidencias(final boolean contactoFormularioIncidencias) {
		this.contactoFormularioIncidencias = contactoFormularioIncidencias;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	public String getUrlSoporte() {
		return this.urlSoporte;
	}

	public void setUrlSoporte(final String urlSoporte) {
		this.urlSoporte = urlSoporte;
	}

	public JLiteral getUrlCarpetaCiudadana() {
		return urlCarpetaCiudadana;
	}

	public void setUrlCarpetaCiudadana(final JLiteral urlCarpetaCiudadana) {
		this.urlCarpetaCiudadana = urlCarpetaCiudadana;
	}

	public Integer getDiasPreregistro() {
		return diasPreregistro;
	}

	public void setDiasPreregistro(final Integer diasPreregistro) {
		this.diasPreregistro = diasPreregistro;
	}

	public Entidad toModel() {
		final Entidad entidad = new Entidad();
		entidad.setId(this.codigo);
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

		return entidad;
	}

}
