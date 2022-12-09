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

import es.caib.sistrages.core.api.model.Tramite;

/**
 * JTramite
 */
@Entity
@Table(name = "STG_TRAMIT", uniqueConstraints = @UniqueConstraint(columnNames = "TRM_IDENTI"))
public class JTramite implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_TRAMIT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_TRAMIT_SEQ", sequenceName = "STG_TRAMIT_SEQ")
	@Column(name = "TRM_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRM_CODARE", nullable = false)
	private JArea area;

	@Column(name = "TRM_IDENTI", unique = true, nullable = false, length = 50)
	private String identificador;

	@Column(name = "TRM_DESCR", nullable = false, length = 1000)
	private String descripcion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tramite")
	private Set<JVersionTramite> versionTramite = new HashSet<>(0);

	public JTramite() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JArea getArea() {
		return this.area;
	}

	public void setArea(final JArea area) {
		this.area = area;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<JVersionTramite> getVersionTramite() {
		return this.versionTramite;
	}

	public void setVersionTramite(final Set<JVersionTramite> versionTramite) {
		this.versionTramite = versionTramite;
	}

	public Tramite toModel() {
		final Tramite tramite = new Tramite();
		tramite.setCodigo(codigo);
		tramite.setDescripcion(descripcion);
		tramite.setIdentificador(identificador);
		tramite.setIdArea(this.area.getCodigo());
		tramite.setIdentificadorArea(this.area.getIdentificador());
		tramite.setIdEntidad(this.area.getEntidad().getCodigo());
		tramite.setIdentificadorEntidad(this.area.getEntidad().getIdentificador());
		return tramite;
	}

	public static JTramite fromModel(final Tramite model) {
		JTramite jModel = null;
		if (model != null) {
			jModel = new JTramite();
			jModel.setCodigo(model.getCodigo());
			jModel.setIdentificador(model.getIdentificador());
			jModel.setDescripcion(model.getDescripcion());
		}
		return jModel;
	}
}
