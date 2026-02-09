package es.caib.sistrahelp.core.service.repository.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypeModoEvaluacionAlerta;

/**
 * JAlerta
 */
@Entity
@Table(name = "STH_HISTAVIS")
public class JHistorialAlerta implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STH_HISTAVIS_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STH_HISTAVIS_SEQ", sequenceName = "STH_HISTAVIS_SEQ")
	@Column(name = "HIST_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HIST_CODIGOAVIS", nullable = false)
	private JAlerta alerta;

	@Column(name = "HIST_EVENTO", nullable = false)
	private String evento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HIST_FECHA", nullable = false)
	private Date fecha;

	@Column(name = "HIST_NOMBRE")
	private String nombre;

	@Column(name = "HIST_TIPO")
	private String tipo;

	@Column(name = "HIST_ENTIDAD")
	private String idEntidad;

	@Column(name = "HIST_AREAS")
	private String listaAreas;

	@Column(name = "HIST_TRAMITE")
	private String tramite;

	@Column(name = "HIST_VERSION")
	private Integer version;

	@Column(name = "HIST_MAIL")
	private String email;

	@Column(name = "HIST_INTEREVA", precision = 3, scale = 0)
	private Integer intervaloEvaluacion;

	@Column(name = "HIST_PERIEVA")
	private String periodoEvaluacion;

	@Column(name = "HIST_MODO_EVALUACION", length = 1)
	private Integer modoEvaluacion;

	@Column(name = "HIST_IDIOMA")
	private String idioma;


	public JHistorialAlerta() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codigoAviso
	 */
	public JAlerta getAlerta() {
		return alerta;
	}

	/**
	 * @param codigoAviso the codigoAviso to set
	 */
	public void setAlerta(JAlerta alerta) {
		this.alerta = alerta;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
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

	public String getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(String listaAreas) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
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

	public Integer getModoEvaluacion() {
		return modoEvaluacion;
	}

	public void setModoEvaluacion(Integer modoEvaluacion) {
		this.modoEvaluacion = modoEvaluacion;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public HistorialAlerta toModel() {
		final HistorialAlerta hist = new HistorialAlerta();
		hist.setCodigo(codigo);
		hist.setAlerta(alerta.toModel());
		hist.setEvento(evento);
		hist.setFecha(fecha);
		hist.setNombre(nombre);
		hist.setTipo(tipo);
		hist.setIdEntidad(idEntidad);
		if (listaAreas != null) {
			hist.setListaAreas(Arrays.asList(listaAreas.split(";")));
		} else {
			hist.setListaAreas(null);
		}
		hist.setTramite(tramite);
		hist.setVersion(version);
		if (email != null) {
			List<String> lEm = Arrays.asList(email.split(";"));
			hist.setEmail(lEm);
		} else {
			hist.setEmail(null);
		}
		hist.setIntervaloEvaluacion(intervaloEvaluacion);
		hist.setPeriodoEvaluacion(periodoEvaluacion);
		if(modoEvaluacion!=null) {
			hist.setModoEvaluacion(TypeModoEvaluacionAlerta.fromCodigo(modoEvaluacion));
		}
		hist.setIdioma(idioma);
		return hist;
	}

	public static JHistorialAlerta fromModel(final HistorialAlerta model) {
		JHistorialAlerta jModel = null;
		if (model != null) {
			jModel = new JHistorialAlerta();
			jModel.setCodigo(model.getCodigo());
			jModel.setAlerta(JAlerta.fromModel(model.getAlerta()));
			jModel.setEvento(model.getEvento());
			jModel.setFecha(model.getFecha());
			jModel.setNombre(model.getNombre());
			jModel.setTipo(model.getTipo());
			jModel.setIdEntidad(model.getIdEntidad());
			if (model.getListaAreas() != null) {
				jModel.setListaAreas(String.join(";", model.getListaAreas()));
			}
			jModel.setTramite(model.getTramite());
			jModel.setVersion(model.getVersion());
			if (model.getEmail() != null) {
				jModel.setEmail(String.join(";", model.getEmail()));
			}
			jModel.setIntervaloEvaluacion(model.getIntervaloEvaluacion());
			jModel.setPeriodoEvaluacion(model.getPeriodoEvaluacion());
			if(model.getModoEvaluacion()!=null) {
				jModel.setModoEvaluacion(model.getModoEvaluacion().getCodigo());
			}
			jModel.setIdioma(model.getIdioma());
		}
		return jModel;
	}

}
