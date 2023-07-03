package es.caib.sistrahelp.core.api.model;

import java.util.Date;
import java.util.List;

import es.caib.sistrahelp.core.api.model.types.TypeEvento;

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

}
