package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JPasoRellenar
 */
@Entity
@Table(name = "STG_PASREL")
public class JPasoRellenar implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRL_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PRL_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "STG_PRLFTR", joinColumns = {
			@JoinColumn(name = "FPR_CODPRL", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "FPR_CODFOR", nullable = false, updatable = false) })
	private Set<JFormularioTramite> formulariosTramite = new HashSet<JFormularioTramite>(0);

	public JPasoRellenar() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JPasoTramitacion getPasoTramitacion() {
		return this.pasoTramitacion;
	}

	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	public Set<JFormularioTramite> getFormulariosTramite() {
		return this.formulariosTramite;
	}

	public void setFormulariosTramite(final Set<JFormularioTramite> formulariosTramite) {
		this.formulariosTramite = formulariosTramite;
	}

}
