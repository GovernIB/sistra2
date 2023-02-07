package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.VariableArea;

/**
 * JDominio
 */
@Entity
@Table(name = "STG_VARARE", uniqueConstraints = @UniqueConstraint(columnNames = "VAR_IDENT"))
public class JVariableArea implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_VARARE_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_VARARE_SEQ", sequenceName = "STG_VARARE_SEQ")
	@Column(name = "VAR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VAR_CODARE", nullable = true)
	private JArea area;

	@Column(name = "VAR_IDENT", unique = true, nullable = false, length = 50)
	private String identificador;

	@Column(name = "VAR_DESCRI", nullable = true)
	private String descripcion;

	@Column(name = "VAR_URL", nullable = false, length = 50)
	private String url;

	public JVariableArea() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public final JArea getArea() {
		return area;
	}

	public final void setArea(JArea area) {
		this.area = area;
	}

	public final String getIdentificador() {
		return identificador;
	}

	public final void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public final String getDescripcion() {
		return descripcion;
	}

	public final void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public final String getUrl() {
		return url;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public VariableArea toModel() {
		final VariableArea va = new VariableArea();
		va.setCodigo(this.codigo);
		va.setArea(this.area.toModel());
		va.setIdentificador(this.identificador);
		va.setDescripcion(this.descripcion);
		va.setUrl(this.url);
		return va;
	}

	public void fromModel(final VariableArea va) {
		if (va != null) {
			this.setCodigo(va.getCodigo());
			this.setIdentificador(va.getIdentificador());
			this.setUrl(va.getUrl());
			this.setArea(JArea.fromModel(va.getArea()));
			this.setDescripcion(va.getDescripcion());

		}
	}

}
