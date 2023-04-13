package es.caib.sistrahelp.core.service.repository.model;

import java.util.Date;

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

	public JHistorialAlerta() {
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
	public final JAlerta getAlerta() {
		return alerta;
	}

	/**
	 * @param codigoAviso the codigoAviso to set
	 */
	public final void setAlerta(JAlerta alerta) {
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

	public HistorialAlerta toModel() {
		final HistorialAlerta hist = new HistorialAlerta();
		hist.setCodigo(codigo);
		hist.setAlerta(alerta.toModel());
		hist.setEvento(evento);
		hist.setFecha(fecha);
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
		}
		return jModel;
	}

}
