package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 *
 * Entidad.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Entidad extends ModelApi {

	/** Código interno. */
	private Long codigo;

	/** Código DIR3. **/
	private String codigoDIR3;

	/** Nombre entidad. **/
	private Literal nombre;

	/** Indica si la entidad está activa. */
	private boolean activo;

	/** Role asociado al administrador de la entidad . */
	private String rol;

	/** Logo entidad Gestor Trámites. */
	private Fichero logoGestor;

	/** Logo entidad Asistente Tramitación. */
	private Fichero logoAsistente;

	/** CSS Asistente Tramitación. */
	private Fichero css;

	/** Pie de página de contacto para Asistente Tramitación (HTML). */
	private Literal pie;

	/** Email contacto genérico. */
	private String email;

	/** Habilitado contacto email. **/
	private boolean emailHabilitado;

	/** Teléfono contacto. */
	private String telefono;

	/** Habilitado contacto teléfono. **/
	private boolean telefonoHabilitado;

	/** Url soporte. */
	private String urlSoporte;

	/** Habilitado url soporte. **/
	private boolean urlSoporteHabilitado;

	/** Habilitado contacto formulario incidencias . **/
	private boolean formularioIncidenciasHabilitado;

	// TODO Gestionarlo mejor fuera de la entidad
	/** Formularios de incidencias. **/
	private List<FormularioSoporte> formularioIncidencias;

	/** Url carpeta ciudadana. **/
	private Literal urlCarpetaCiudadana;

	/** Dias de preregistro. **/
	private Integer diasPreregistro;

	/** Texto respecto lopd. **/
	private Literal lopd;

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
	 * @return the codigoDIR3
	 */
	public String getCodigoDIR3() {
		return codigoDIR3;
	}

	/**
	 * @param codigoDIR3
	 *            the codigoDIR3 to set
	 */
	public void setCodigoDIR3(final String codigoDIR3) {
		this.codigoDIR3 = codigoDIR3;
	}

	/**
	 * @return the nombre
	 */
	public Literal getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(final Literal nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(final boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol
	 *            the rol to set
	 */
	public void setRol(final String rol) {
		this.rol = rol;
	}

	/**
	 * @return the logoGestor
	 */
	public Fichero getLogoGestor() {
		return logoGestor;
	}

	/**
	 * @param logoGestor
	 *            the logoGestor to set
	 */
	public void setLogoGestor(final Fichero logoGestor) {
		this.logoGestor = logoGestor;
	}

	/**
	 * @return the logoAsistente
	 */
	public Fichero getLogoAsistente() {
		return logoAsistente;
	}

	/**
	 * @param logoAsistente
	 *            the logoAsistente to set
	 */
	public void setLogoAsistente(final Fichero logoAsistente) {
		this.logoAsistente = logoAsistente;
	}

	/**
	 * @return the css
	 */
	public Fichero getCss() {
		return css;
	}

	/**
	 * @param css
	 *            the css to set
	 */
	public void setCss(final Fichero css) {
		this.css = css;
	}

	/**
	 * @return the pie
	 */
	public Literal getPie() {
		return pie;
	}

	/**
	 * @param pie
	 *            the pie to set
	 */
	public void setPie(final Literal pie) {
		this.pie = pie;
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
	 * @return the emailHabilitado
	 */
	public boolean isEmailHabilitado() {
		return emailHabilitado;
	}

	/**
	 * @param emailHabilitado
	 *            the emailHabilitado to set
	 */
	public void setEmailHabilitado(final boolean emailHabilitado) {
		this.emailHabilitado = emailHabilitado;
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
	 * @return the telefonoHabilitado
	 */
	public boolean isTelefonoHabilitado() {
		return telefonoHabilitado;
	}

	/**
	 * @param telefonoHabilitado
	 *            the telefonoHabilitado to set
	 */
	public void setTelefonoHabilitado(final boolean telefonoHabilitado) {
		this.telefonoHabilitado = telefonoHabilitado;
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
	 * @return the urlSoporteHabilitado
	 */
	public boolean isUrlSoporteHabilitado() {
		return urlSoporteHabilitado;
	}

	/**
	 * @param urlSoporteHabilitado
	 *            the urlSoporteHabilitado to set
	 */
	public void setUrlSoporteHabilitado(final boolean urlSoporteHabilitado) {
		this.urlSoporteHabilitado = urlSoporteHabilitado;
	}

	/**
	 * @return the formularioIncidenciasHabilitado
	 */
	public boolean isFormularioIncidenciasHabilitado() {
		return formularioIncidenciasHabilitado;
	}

	/**
	 * @param formularioIncidenciasHabilitado
	 *            the formularioIncidenciasHabilitado to set
	 */
	public void setFormularioIncidenciasHabilitado(final boolean formularioIncidenciasHabilitado) {
		this.formularioIncidenciasHabilitado = formularioIncidenciasHabilitado;
	}

	/**
	 * @return the formularioIncidencias
	 */
	public List<FormularioSoporte> getFormularioIncidencias() {
		return formularioIncidencias;
	}

	/**
	 * @param formularioIncidencias
	 *            the formularioIncidencias to set
	 */
	public void setFormularioIncidencias(final List<FormularioSoporte> formularioIncidencias) {
		this.formularioIncidencias = formularioIncidencias;
	}

	/**
	 * @return the urlCarpetaCiudadana
	 */
	public Literal getUrlCarpetaCiudadana() {
		return urlCarpetaCiudadana;
	}

	/**
	 * @param urlCarpetaCiudadana
	 *            the urlCarpetaCiudadana to set
	 */
	public void setUrlCarpetaCiudadana(final Literal urlCarpetaCiudadana) {
		this.urlCarpetaCiudadana = urlCarpetaCiudadana;
	}

	/**
	 * @return the lopd
	 */
	public Literal getLopd() {
		return lopd;
	}

	/**
	 * @param lopd
	 *            the lopd to set
	 */
	public void setLopd(final Literal lopd) {
		this.lopd = lopd;
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

}
