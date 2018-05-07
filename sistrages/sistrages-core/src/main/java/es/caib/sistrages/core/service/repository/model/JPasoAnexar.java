package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;

/**
 * JPasoAnexar
 */
@Entity
@Table(name = "STG_PASANE")
public class JPasoAnexar implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PAN_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PAN_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAN_SCRDIN")
	private JScript scriptAnexosDinamicos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pasoAnexar", cascade = { CascadeType.ALL })
	private Set<JAnexoTramite> anexosTramite = new HashSet<>(0);

	public JPasoAnexar() {
		// Constructor vacio
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

	public static JPasoAnexar fromModel(final TramitePasoAnexar paso) {
		JPasoAnexar jpaso = null;
		if (paso != null) {
			jpaso = new JPasoAnexar();
			jpaso.setCodigo(paso.getId());
			if (paso.getDocumentos() != null) {
				final Set<JAnexoTramite> anexos = new HashSet<>();
				for (final Documento doc : paso.getDocumentos()) {
					anexos.add(JAnexoTramite.fromModel(doc));
				}
				jpaso.setAnexosTramite(anexos);
			}
		}
		return jpaso;
	}

}
