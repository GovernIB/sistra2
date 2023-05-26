package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.flujo.types.TypeSoporteEstado;
import es.caib.sistramit.core.api.model.system.rest.interno.FormularioSoporte;
import es.caib.sistramit.core.service.model.flujo.DatosFormularioSoporte;

/**
 * Mapeo tabla STT_AVISOS.
 */

@Entity
@Table(name = "STT_SOPORT")
@SuppressWarnings("serial")
public final class HSoporte implements IModelApi {

	/** Atributo codigo. */
	@Id
	@SequenceGenerator(name = "STT_SOPORT_SEQ", sequenceName = "STT_SOPORT_SEQ", allocationSize = ConstantesNumero.N1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_SOPORT_SEQ")
	@Column(name = "SOP_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N20, scale = 0)
	private Long codigo;

	/** Fecha creación. */
	@Column(name = "SOP_FCCREA")
	private Date fechaCreacion;

	/** Sesion tramitacion. */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SOP_CODSTR", nullable = false)
	private HSesionTramitacion sesionTramitacion;

	/** Formulario: Nif . */
	@Column(name = "SOP_NIF")
	private String nif;

	/** Formulario: Nombre . */
	@Column(name = "SOP_NOMBRE")
	private String nombre;

	/** Formulario: Teléfono . */
	@Column(name = "SOP_TELEFO")
	private String telefono;

	/** Formulario: Email . */
	@Column(name = "SOP_EMAIL")
	private String email;

	/** Formulario: Horario . */
	@Column(name = "SOP_HORARI")
	private String horario;

	/** Formulario: Tipo problema . */
	@Column(name = "SOP_PROTIP")
	private String tipoProblema;

	/** Formulario: Tipo problema (descripción) . */
	@Column(name = "SOP_PROTID")
	private String tipoProblemaDescripcion;

	/** Formulario: Descripción problema . */
	@Column(name = "SOP_PRODES")
	private String descripcionProblema;

	/** Formulario: Nombre fichero . */
	@Column(name = "SOP_FICNOM")
	private String nombreFichero;

	/** Formulario: Datos fichero . */
	@Lob
	@Column(name = "SOP_FICDAT")
	private byte[] datosFichero;

	/** Formulario: Estado . */
	@Column(name = "SOP_ESTADO")
	private String estado;

	/** Formulario: Comentarios . */
	@Column(name = "SOP_COMENT")
	private String comentarios;

	/**
	 * Método de acceso a codigo.
	 *
	 * @return codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 *
	 * @param codigo codigo a establecer
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método de acceso a fechaCreacion.
	 *
	 * @return fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Método para establecer fechaCreacion.
	 *
	 * @param fechaCreacion fechaCreacion a establecer
	 */
	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Método de acceso a sesionTramitacion.
	 *
	 * @return sesionTramitacion
	 */
	public HSesionTramitacion getSesionTramitacion() {
		return sesionTramitacion;
	}

	/**
	 * Método para establecer sesionTramitacion.
	 *
	 * @param sesionTramitacion sesionTramitacion a establecer
	 */
	public void setSesionTramitacion(final HSesionTramitacion sesionTramitacion) {
		this.sesionTramitacion = sesionTramitacion;
	}

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param nif nif a establecer
	 */
	public void setNif(final String nif) {
		this.nif = nif;
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre.
	 *
	 * @param nombre nombre a establecer
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método de acceso a telefono.
	 *
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Método para establecer telefono.
	 *
	 * @param telefono telefono a establecer
	 */
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
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
	 * Método de acceso a horario.
	 *
	 * @return horario
	 */
	public String getHorario() {
		return horario;
	}

	/**
	 * Método para establecer horario.
	 *
	 * @param horario horario a establecer
	 */
	public void setHorario(final String horario) {
		this.horario = horario;
	}

	/**
	 * Método de acceso a tipoProblema.
	 *
	 * @return tipoProblema
	 */
	public String getTipoProblema() {
		return tipoProblema;
	}

	/**
	 * Método para establecer tipoProblema.
	 *
	 * @param tipoProblema tipoProblema a establecer
	 */
	public void setTipoProblema(final String tipoProblema) {
		this.tipoProblema = tipoProblema;
	}

	/**
	 * Método de acceso a descripcionProblema.
	 *
	 * @return descripcionProblema
	 */
	public String getDescripcionProblema() {
		return descripcionProblema;
	}

	/**
	 * Método para establecer descripcionProblema.
	 *
	 * @param descripcionProblema descripcionProblema a establecer
	 */
	public void setDescripcionProblema(final String descripcionProblema) {
		this.descripcionProblema = descripcionProblema;
	}

	/**
	 * Método de acceso a nombreFichero.
	 *
	 * @return nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}

	/**
	 * Método para establecer nombreFichero.
	 *
	 * @param nombreFichero nombreFichero a establecer
	 */
	public void setNombreFichero(final String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/**
	 * Método de acceso a datosFichero.
	 *
	 * @return datosFichero
	 */
	public byte[] getDatosFichero() {
		return datosFichero;
	}

	/**
	 * Método para establecer datosFichero.
	 *
	 * @param datosFichero datosFichero a establecer
	 */
	public void setDatosFichero(final byte[] datosFichero) {
		this.datosFichero = datosFichero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(final String estado) {
		this.estado = estado;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(final String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * Convierte a modelo.
	 *
	 * @param h HSoporte
	 * @return DatosFormularioSoporte
	 */
	public DatosFormularioSoporte toModel(final HSoporte h) {
		DatosFormularioSoporte m = null;
		if (h != null) {
			m = new DatosFormularioSoporte();
			m.setFechaCreacion(h.getFechaCreacion());
			m.setNif(h.getNif());
			m.setNombre(h.getNombre());
			m.setEmail(h.getEmail());
			m.setTelefono(h.getTelefono());
			m.setHorarioContacto(h.getHorario());
			m.setProblemaTipo(h.getTipoProblema());
			m.setProblemaTipoDesc(h.getTipoProblemaDescripcion());
			m.setProblemaDesc(h.getDescripcionProblema());
			m.setNombreFichero(h.getNombreFichero());
			m.setDatosFichero(h.getDatosFichero());
			m.setEstado(TypeSoporteEstado.fromString(h.getEstado()));
			m.setComentarios(h.getComentarios());
		}
		return m;
	}

	/**
	 * Convierte a persistencia.
	 *
	 * @param datosFormularioSoporte DatosFormularioSoporte
	 * @return HSoporte
	 */
	public static HSoporte fromModel(final DatosFormularioSoporte datosFormularioSoporte) {
		HSoporte h = null;
		if (datosFormularioSoporte != null) {
			h = new HSoporte();
			h.setFechaCreacion(datosFormularioSoporte.getFechaCreacion());
			h.setNif(datosFormularioSoporte.getNif());
			h.setNombre(datosFormularioSoporte.getNombre());
			h.setEmail(datosFormularioSoporte.getEmail());
			h.setTelefono(datosFormularioSoporte.getTelefono());
			h.setHorario(datosFormularioSoporte.getHorarioContacto());
			h.setTipoProblema(datosFormularioSoporte.getProblemaTipo());
			h.setTipoProblemaDescripcion(datosFormularioSoporte.getProblemaTipoDesc());
			h.setDescripcionProblema(datosFormularioSoporte.getProblemaDesc());
			h.setNombreFichero(datosFormularioSoporte.getNombreFichero());
			h.setDatosFichero(datosFormularioSoporte.getDatosFichero());
			h.setEstado(datosFormularioSoporte.getEstado().toString());
			h.setComentarios(datosFormularioSoporte.getComentarios());
		}
		return h;
	}

	/**
	 * Método de acceso a tipoProblemaDescripcion.
	 *
	 * @return tipoProblemaDescripcion
	 */
	public String getTipoProblemaDescripcion() {
		return tipoProblemaDescripcion;
	}

	/**
	 * Método para establecer tipoProblemaDescripcion.
	 *
	 * @param tipoProblemaDescripcion tipoProblemaDescripcion a establecer
	 */
	public void setTipoProblemaDescripcion(final String tipoProblemaDescripcion) {
		this.tipoProblemaDescripcion = tipoProblemaDescripcion;
	}

}
