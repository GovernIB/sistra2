package es.caib.sistrahelp.core.service.repository.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;

/**
 * JAlerta
 */
@Entity
@Table(name = "STH_AVISCONFIG")
public class JAlerta implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STH_AVISCONFIG_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STH_AVISCONFIG_SEQ", sequenceName = "STH_AVISCONFIG_SEQ")
	@Column(name = "AVI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@Column(name = "AVI_EVENTOS", nullable = false)
	private String eventos;

	@Column(name = "AVI_NOMBRE", nullable = false)
	private String nombre;

	@Column(name = "AVI_MAIL", nullable = false)
	private String email;

	@Column(name = "AVI_PERIEVA", nullable = false, precision = 3, scale = 0)
	private Integer periodoEvaluacion;

	@Column(name = "AVI_INTEREVA")
	private String intervaloEvaluacion;

	@Column(name = "AVI_AREAS")
	private String listaAreas;

	@Column(name = "AVI_ELIMINAR")
	private String eliminar;

	@Column(name = "AVI_TIPO")
	private String tipo;

	@Column(name = "AVI_TRAMITE")
	private String tramite;

	@Column(name = "AVI_VERSION")
	private Integer version;

	@Column(name = "AVI_ENTIDAD")
	private String idEntidad;

	public JAlerta() {
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the periodoEvaluacion
	 */
	public Integer getPeriodoEvaluacion() {
		return periodoEvaluacion;
	}

	/**
	 * @param periodoEvaluacion the periodoEvaluacion to set
	 */
	public void setPeriodoEvaluacion(Integer periodoEvaluacion) {
		this.periodoEvaluacion = periodoEvaluacion;
	}

	/**
	 * @return the intervaloEvaluacion
	 */
	public String getIntervaloEvaluacion() {
		return intervaloEvaluacion;
	}

	/**
	 * @param intervaloEvaluacion the intervaloEvaluacion to set
	 */
	public void setIntervaloEvaluacion(String intervaloEvaluacion) {
		this.intervaloEvaluacion = intervaloEvaluacion;
	}

	/**
	 * @return the eventos
	 */
	public String getEventos() {
		return eventos;
	}

	/**
	 * @param eventos the eventos to set
	 */
	public void setEventos(String eventos) {
		this.eventos = eventos;
	}

	/**
	 * @return the listaAreas
	 */
	public String getListaAreas() {
		return listaAreas;
	}

	/**
	 * @param listaAreas the listaAreas to set
	 */
	public void setListaAreas(String listaAreas) {
		this.listaAreas = listaAreas;
	}

	/**
	 * @return the eliminar
	 */
	public String getEliminar() {
		return eliminar;
	}

	/**
	 * @param eliminar the eliminar to set
	 */
	public void setEliminar(String eliminar) {
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

	public Alerta toModel() {
		final Alerta alerta = new Alerta();
		alerta.setCodigo(codigo);
		if (eventos != null) {
			String[] parts = eventos.split(";");
			List<String> lEv = new ArrayList<String>();
			for (String ev : parts) {
				lEv.add(ev);
			}
			alerta.setEventos(lEv);
		} else {
			alerta.setEventos(null);
		}
		alerta.setNombre(nombre);
		alerta.setTipo(tipo);
		alerta.setTramite(tramite);
		alerta.setVersion(version);
		alerta.setIdEntidad(idEntidad);
		if (email != null) {
			String[] aEm = email.split(";");
			List<String> lEm = new ArrayList<String>();
			for (String em : aEm) {
				lEm.add(em);
			}
			alerta.setEmail(lEm);
		} else {
			alerta.setEmail(null);
		}
		alerta.setPeriodoEvaluacion(periodoEvaluacion);
		alerta.setIntervaloEvaluacion(intervaloEvaluacion);
		if (listaAreas != null) {
			String[] aAr = listaAreas.split(";");
			List<String> lAr = new ArrayList<String>();
			for (String ar : aAr) {
				lAr.add(ar);
			}
			alerta.setListaAreas(lAr);
		} else {
			alerta.setListaAreas(null);
		}
		if (eliminar.equals("T")) {
			alerta.setEliminar(true);
		} else {
			alerta.setEliminar(false);
		}
		return alerta;
	}

	public static JAlerta fromModel(final Alerta model) {
		JAlerta jModel = null;
		if (model != null) {
			jModel = new JAlerta();
			jModel.setCodigo(model.getCodigo());
			String aux = "";
			if (model.getEventos() != null) {
				for (String ev : model.getEventos()) {
					if (aux.isEmpty()) {
						aux = aux + ev.toString();
					} else {
						aux = aux + ";" + ev.toString();
					}
				}
			}
			jModel.setEventos(aux);
			jModel.setNombre(model.getNombre());
			jModel.setTipo(model.getTipo());
			jModel.setTramite(model.getTramite());
			jModel.setVersion(model.getVersion());
			jModel.setIdEntidad(model.getIdEntidad());
			aux = "";
			if (model.getEmail() != null) {

				for (String em : model.getEmail()) {
					if (aux.isEmpty()) {
						aux = aux + em.toString();
					} else {
						aux = aux + ";" + em.toString();
					}
				}
			}
			jModel.setEmail(aux);
			jModel.setPeriodoEvaluacion(model.getPeriodoEvaluacion());
			jModel.setIntervaloEvaluacion(model.getIntervaloEvaluacion());
			jModel.setCodigo(model.getCodigo());
			aux = "";
			if (model.getListaAreas() != null) {
				for (String ar : model.getListaAreas()) {
					if (aux.isEmpty()) {
						aux = aux + ar.toString();
					} else {
						aux = aux + ";" + ar.toString();
					}
				}
			}
			jModel.setListaAreas(aux);
			if (model.isEliminar()) {
				jModel.setEliminar("T");
			} else {
				jModel.setEliminar("F");
			}
		}
		return jModel;
	}

}
