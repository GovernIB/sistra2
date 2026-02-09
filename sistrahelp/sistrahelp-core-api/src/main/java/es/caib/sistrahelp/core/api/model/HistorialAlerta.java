package es.caib.sistrahelp.core.api.model;

import java.util.Date;
import java.util.List;

import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypeModoEvaluacionAlerta;

/**
 * Dominio.
 *
 * @author Indra
 *
 */

public class HistorialAlerta extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	private Long codigo;

	private Alerta alerta;

	private String evento;

	private Date fecha;

    private String nombre;

    private String tipo;

	private String idEntidad;

	private List<String> listaAreas;

	private String tramite;

	private Integer version;

	private List<String> email;

	private Integer intervaloEvaluacion;

	private String periodoEvaluacion;

	private TypeModoEvaluacionAlerta modoEvaluacion;

	private String idioma;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public HistorialAlerta() {
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
	 * @return the codigoAviso
	 */
	public final Alerta getAlerta() {
		return alerta;
	}

	/**
	 * @param codigoAviso the codigoAviso to set
	 */
	public final void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	/**
	 * @return the fecha
	 */
	public final Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public final void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the evento
	 */
	public final String getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public final void setEvento(String evento) {
		this.evento = evento;
	}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public List<String> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List<String> listaAreas) {
		this.listaAreas = listaAreas;
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

	public List<String> getEmail() {
		return email;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public Integer getIntervaloEvaluacion() {
		return intervaloEvaluacion;
	}

	public void setIntervaloEvaluacion(Integer intervaloEvaluacion) {
		this.intervaloEvaluacion = intervaloEvaluacion;
	}

	public String getPeriodoEvaluacion() {
		return periodoEvaluacion;
	}

	public void setPeriodoEvaluacion(String periodoEvaluacion) {
		this.periodoEvaluacion = periodoEvaluacion;
	}

	public TypeModoEvaluacionAlerta getModoEvaluacion() {
		return modoEvaluacion;
	}

	public void setModoEvaluacion(TypeModoEvaluacionAlerta modoEvaluacion) {
		this.modoEvaluacion = modoEvaluacion;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}
