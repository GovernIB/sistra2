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

	/**
	 * Constructor vacio.
	 */
	public JPasoAnexar() {
		// Constructor vacio
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the pasoTramitacion
	 */
	public JPasoTramitacion getPasoTramitacion() {
		return pasoTramitacion;
	}

	/**
	 * @param pasoTramitacion
	 *            the pasoTramitacion to set
	 */
	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	/**
	 * @return the scriptAnexosDinamicos
	 */
	public JScript getScriptAnexosDinamicos() {
		return scriptAnexosDinamicos;
	}

	/**
	 * @param scriptAnexosDinamicos
	 *            the scriptAnexosDinamicos to set
	 */
	public void setScriptAnexosDinamicos(final JScript scriptAnexosDinamicos) {
		this.scriptAnexosDinamicos = scriptAnexosDinamicos;
	}

	/**
	 * @return the anexosTramite
	 */
	public Set<JAnexoTramite> getAnexosTramite() {
		return anexosTramite;
	}

	/**
	 * @param anexosTramite
	 *            the anexosTramite to set
	 */
	public void setAnexosTramite(final Set<JAnexoTramite> anexosTramite) {
		this.anexosTramite = anexosTramite;
	}

	/**
	 * From model.
	 *
	 * @param paso
	 * @return
	 */
	public static JPasoAnexar fromModel(final TramitePasoAnexar paso) {
		JPasoAnexar jpaso = null;
		if (paso != null) {
			jpaso = new JPasoAnexar();
			jpaso.setCodigo(paso.getCodigo());
			if (paso.getDocumentos() != null) {
				final Set<JAnexoTramite> anexos = new HashSet<>();
				for (final Documento doc : paso.getDocumentos()) {
					final JAnexoTramite anexo = JAnexoTramite.fromModel(doc);
					anexo.setPasoAnexar(jpaso);
					anexos.add(anexo);
				}
				jpaso.setAnexosTramite(anexos);
			}
		}
		return jpaso;
	}

}
