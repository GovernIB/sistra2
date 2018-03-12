package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * JEntidades
 */
@Entity
@Table(name = "STG_ENTIDA", uniqueConstraints = @UniqueConstraint(columnNames = "ENT_DIR3"))
public class JEntidades implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_ENTIDA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_ENTIDA_SEQ", sequenceName = "STG_ENTIDA_SEQ")
	@Column(name = "ENT_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENT_LOGOTT")
	private JFichero logoAsistenteTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENT_CSSTT")
	private JFichero cssAsistenteTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENT_LOGOTG")
	private JFichero logoGestorTramites;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENT_NOMBRE", nullable = false)
	private JLiterales nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENT_PIETT")
	private JLiterales piePaginaAsistenteTramitacion;

	@Column(name = "ENT_DIR3", unique = true, nullable = false, length = 10)
	private String codigoDir3;

	@Column(name = "ENT_ACTIVA", nullable = false, precision = 1, scale = 0)
	private boolean activa;

	@Column(name = "ENT_ROLADM", nullable = false, length = 100)
	private String roleAdministrador;

	@Column(name = "ENT_EMAIL", length = 500)
	private String email;

	@Column(name = "ENT_CNTEMA", nullable = false, precision = 1, scale = 0)
	private boolean contactoEmail;

	@Column(name = "ENT_CNTTEL", nullable = false, precision = 1, scale = 0)
	private boolean contactoTelefono;

	@Column(name = "ENT_CNTURL", nullable = false, precision = 1, scale = 0)
	private boolean contactoUrl;

	@Column(name = "ENT_CNTFOR", nullable = false, precision = 1, scale = 0)
	private boolean contactoFormularioIncidencias;

	@Column(name = "ENT_TELEFO", length = 10)
	private String telefono;

	@Column(name = "ENT_URLSOP", length = 500)
	private String urlSoporte;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_DOMENT", joinColumns = {
			@JoinColumn(name = "DEN_CODENT", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DEN_CODDOM", nullable = false, updatable = false) })
	private Set<JDominio> dominios = new HashSet<JDominio>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_PLGENT", joinColumns = {
			@JoinColumn(name = "PLE_CODENT", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "PLE_CODPLG", nullable = false, updatable = false) })
	private Set<JPlugin> plugins = new HashSet<JPlugin>(0);

	public JEntidades() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
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

	public JLiterales getNombre() {
		return this.nombre;
	}

	public void setNombre(final JLiterales nombre) {
		this.nombre = nombre;
	}

	public JLiterales getPiePaginaAsistenteTramitacion() {
		return this.piePaginaAsistenteTramitacion;
	}

	public void setPiePaginaAsistenteTramitacion(final JLiterales piePaginaAsistenteTramitacion) {
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

	public Set<JDominio> getDominios() {
		return this.dominios;
	}

	public void setDominios(final Set<JDominio> dominios) {
		this.dominios = dominios;
	}

	public Set<JPlugin> getPlugins() {
		return this.plugins;
	}

	public void setPlugins(final Set<JPlugin> plugins) {
		this.plugins = plugins;
	}

}
