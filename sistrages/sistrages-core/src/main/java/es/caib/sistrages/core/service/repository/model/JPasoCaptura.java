package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JPasoCaptura
 */
@Entity
@Table(name = "STG_PASCAP")
public class JPasoCaptura implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PCA_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PCA_CODFOR")
	private JFormularioTramite formularioTramite;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PCA_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	public JPasoCaptura() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFormularioTramite getFormularioTramite() {
		return this.formularioTramite;
	}

	public void setFormularioTramite(final JFormularioTramite formularioTramite) {
		this.formularioTramite = formularioTramite;
	}

	public JPasoTramitacion getPasoTramitacion() {
		return this.pasoTramitacion;
	}

	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

}
