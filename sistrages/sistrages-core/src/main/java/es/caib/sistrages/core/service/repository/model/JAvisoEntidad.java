package es.caib.sistrages.core.service.repository.model;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeAvisoEntidad;

/**
 * JAvisosEntidad
 */
@Entity
@Table(name = "STG_AVIENT")
public class JAvisoEntidad implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_AVIENT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_AVIENT_SEQ", sequenceName = "STG_AVIENT_SEQ")
	@Column(name = "AVI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "AVI_CODENT", nullable = false)
	private JEntidad entidad;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.ALL })
	@JoinColumn(name = "AVI_TRAMEN", nullable = false)
	private JLiteral mensaje;

	@Column(name = "AVI_TIPO", nullable = false, length = 3)
	private String tipo;

	@Column(name = "AVI_BLOQ", nullable = false, precision = 1, scale = 0)
	private boolean bloqueado;

	@Temporal(TemporalType.DATE)
	@Column(name = "AVI_FCINI", length = 7)
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "AVI_FCFIN", length = 7)
	private Date fechaFin;

	@Column(name = "AVI_LSTTRA", length = 1000)
	private String listaSerializadaTramites;

	public JAvisoEntidad() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JEntidad getEntidad() {
		return this.entidad;
	}

	public void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
	}

	public JLiteral getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(final JLiteral mensaje) {
		this.mensaje = mensaje;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public boolean isBloqueado() {
		return this.bloqueado;
	}

	public void setBloqueado(final boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getListaSerializadaTramites() {
		return this.listaSerializadaTramites;
	}

	public void setListaSerializadaTramites(final String listaSerializadaTramites) {
		this.listaSerializadaTramites = listaSerializadaTramites;
	}

	public AvisoEntidad toModel() {
		final AvisoEntidad avisoEntidad = new AvisoEntidad();
		avisoEntidad.setId(codigo);
		avisoEntidad.setTipo(TypeAvisoEntidad.fromString(tipo));
		avisoEntidad.setMensaje(mensaje.toModel());
		avisoEntidad.setBloqueado(bloqueado);
		avisoEntidad.setFechaInicio(fechaInicio);
		avisoEntidad.setFechaFin(fechaFin);
		avisoEntidad.setListaSerializadaTramites(listaSerializadaTramites);
		return avisoEntidad;
	}

	public static JAvisoEntidad fromModel(final AvisoEntidad model) {
		JAvisoEntidad jModel = null;
		if (model != null) {
			jModel = new JAvisoEntidad();
			jModel.setCodigo(model.getId());
			jModel.setMensaje(JLiteral.fromModel(model.getMensaje()));
			jModel.setTipo(model.getTipo().toString());
			jModel.setBloqueado(model.isBloqueado());
			jModel.setFechaInicio(model.getFechaInicio());
			jModel.setFechaFin(model.getFechaFin());
			jModel.setListaSerializadaTramites(model.getListaSerializadaTramites());
		}
		return jModel;
	}

}
