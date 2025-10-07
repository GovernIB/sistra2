package es.caib.sistrahelp.core.api.model;

import es.caib.sistrahelp.core.api.model.types.TypeModoEvaluacionAlerta;

import java.util.Date;
import java.util.List;

/**
 * Dominio.
 *
 * @author Indra
 *
 */

public class Alerta extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Eventos. */
	private List<String> eventos;

	/** Nombre. **/
	private String nombre;

	/** Dir3Ent. **/
	private String idEntidad;

	/** Emails. */
	private List<String> email;

	/** Periodo Evaluacion */
	private Integer periodoEvaluacion;

	/** Intervalo Evaluacion. **/
	private String intervaloEvaluacion;

	/** Areas. **/
	private List<String> listaAreas;

	/** Eliminar. **/
	private boolean eliminar;

	/** Tipo. **/
	private String tipo;

	/** Tramite. **/
	private String tramite;

	/** Tramite. **/
	private Integer version;

	/** Fecha última verificación. **/
	private Date fecha;

	/** Alerta activa. **/
	private boolean activo;

	/** horaResumen. **/
	private String horaResumen;

	private String idioma;

	private TypeModoEvaluacionAlerta modoEvaluacion;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public Alerta() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public final Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public final void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nombre
	 */
	public final String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the email
	 */
	public final List<String> getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public final void setEmail(List<String> email) {
		this.email = email;
	}

	/**
	 * @return the periodoEvaluacion
	 */
	public final Integer getPeriodoEvaluacion() {
		return periodoEvaluacion;
	}

	/**
	 * @param periodoEvaluacion the periodoEvaluacion to set
	 */
	public final void setPeriodoEvaluacion(Integer periodoEvaluacion) {
		this.periodoEvaluacion = periodoEvaluacion;
	}

	/**
	 * @return the intervaloEvaluacion
	 */
	public final String getIntervaloEvaluacion() {
		return intervaloEvaluacion;
	}

	/**
	 * @param intervaloEvaluacion the intervaloEvaluacion to set
	 */
	public final void setIntervaloEvaluacion(String intervaloEvaluacion) {
		this.intervaloEvaluacion = intervaloEvaluacion;
	}

	/**
	 * @return the eventos
	 */
	public final List<String> getEventos() {
		return eventos;
	}

	/**
	 * @param eventos the eventos to set
	 */
	public final void setEventos(List<String> eventos) {
		this.eventos = eventos;
	}

	/**
	 * @return the listaAreas
	 */
	public final List<String> getListaAreas() {
		return listaAreas;
	}

	/**
	 * @param listaAreas the listaAreas to set
	 */
	public final void setListaAreas(List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}

	/**
	 * @return the eliminar
	 */
	public final boolean isEliminar() {
		return eliminar;
	}

	/**
	 * @param eliminar the eliminar to set
	 */
	public final void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getHoraResumen() {
		return horaResumen;
	}

	public void setHoraResumen(String horaResumen) {
		this.horaResumen = horaResumen;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public TypeModoEvaluacionAlerta getModoEvaluacion() {
		return modoEvaluacion;
	}

	public void setModoEvaluacion(TypeModoEvaluacionAlerta modoEvaluacion) {
		this.modoEvaluacion = modoEvaluacion;
	}

	@Override
	public Alerta clone() {

		Alerta clonada = new Alerta();

		clonada.setActivo(this.isActivo());
		clonada.setNombre(this.getNombre());
		clonada.setCodigo(this.getCodigo());
		clonada.setEmail(this.getEmail());
		clonada.setEventos(this.getEventos());
		clonada.setEliminar(this.isEliminar());
		clonada.setFecha(this.getFecha());
		clonada.setHoraResumen(this.getHoraResumen());
		clonada.setIdEntidad(this.getIdEntidad());
		clonada.setIdioma(this.getIdioma());
		clonada.setIntervaloEvaluacion(this.getIntervaloEvaluacion());
		clonada.setListaAreas(this.getListaAreas());
		clonada.setModoEvaluacion(this.getModoEvaluacion());

		clonada.setPeriodoEvaluacion(this.getPeriodoEvaluacion());
		clonada.setTipo(this.getTipo());
		clonada.setTramite(this.getTramite());
		clonada.setVersion(this.getVersion());

		return clonada;
	}
}
