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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.TramiteTipo;

/**
 * JTipoPasoTramitacion
 */
@Entity
@Table(name = "STG_TIPPTR", uniqueConstraints = @UniqueConstraint(columnNames = "TIP_PASO"))
public class JTipoPasoTramitacion implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_TIPPTR_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_TIPPTR_SEQ", sequenceName = "STG_TIPPTR_SEQ")
	@Column(name = "TIP_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIP_DESCOR", nullable = false)
	private JLiteral descripcionCorta;

	@Column(name = "TIP_PASO", unique = true, nullable = false, length = 20)
	private String tipoPaso;

	@Column(name = "TIP_ORDEN", precision = 2, scale = 0)
	private Byte orden;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoPasoTramitacion")
	private Set<JPasoTramitacion> pasoTramite = new HashSet<JPasoTramitacion>(0);

	public JTipoPasoTramitacion() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JLiteral getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(final JLiteral descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getTipoPaso() {
		return this.tipoPaso;
	}

	public void setTipoPaso(final String tipoPaso) {
		this.tipoPaso = tipoPaso;
	}

	public Byte getOrden() {
		return this.orden;
	}

	public void setOrden(final Byte orden) {
		this.orden = orden;
	}

	public Set<JPasoTramitacion> getPasoTramite() {
		return this.pasoTramite;
	}

	public void setPasoTramite(final Set<JPasoTramitacion> pasoTramite) {
		this.pasoTramite = pasoTramite;
	}

	public TramiteTipo toModel() {
		final TramiteTipo tipo = new TramiteTipo();
		tipo.setCodigo(this.getTipoPaso());
		tipo.setId(this.getCodigo());
		tipo.setOrden(this.getOrden());
		tipo.setDescripcion(this.getDescripcionCorta().toModel());
		return tipo;
	}

	public void fromModel(final TramiteTipo tipo) {
		this.setTipoPaso(tipo.getCodigo());
		this.setCodigo(tipo.getId());
		if (tipo.getDescripcion() != null) {
			final JLiteral desc = JLiteral.fromModel(tipo.getDescripcion());
			this.setDescripcionCorta(desc);
		}
		this.setOrden((byte) tipo.getOrden());
	}

}
