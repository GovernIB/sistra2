package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JPasoAnexar
 */
@Entity
@Table(name = "STG_PASANE")
public class JPasoAnexar implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PAN_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PAN_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAN_SCRDIN")
	private JScript scriptAnexosDinamicos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pasoAnexar")
	private Set<JAnexoTramite> anexosTramite = new HashSet<JAnexoTramite>(0);

	public JPasoAnexar() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JPasoTramitacion getPasoTramitacion() {
		return this.pasoTramitacion;
	}

	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	public JScript getScriptAnexosDinamicos() {
		return this.scriptAnexosDinamicos;
	}

	public void setScriptAnexosDinamicos(final JScript scriptAnexosDinamicos) {
		this.scriptAnexosDinamicos = scriptAnexosDinamicos;
	}

	public Set<JAnexoTramite> getAnexosTramite() {
		return this.anexosTramite;
	}

	public void setAnexosTramite(final Set<JAnexoTramite> anexosTramite) {
		this.anexosTramite = anexosTramite;
	}

}
