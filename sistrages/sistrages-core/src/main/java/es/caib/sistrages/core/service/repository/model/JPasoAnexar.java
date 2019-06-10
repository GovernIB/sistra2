package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;

/**
 * JPasoAnexar
 */
@Entity
@Table(name = "STG_PASANE")
public class JPasoAnexar implements IModelApi {

	/** Serial Version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@Column(name = "PAN_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Paso de tramitacion. **/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PAN_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	/** Script para anexos dinámicos. **/
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PAN_SCRDIN")
	private JScript scriptAnexosDinamicos;

	/** Anexos. **/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pasoAnexar", cascade = { CascadeType.ALL })
	@OrderBy("orden ASC")
	private Set<JAnexoTramite> anexosTramite = new HashSet<>(0);

	/** Indica si se habilita subsanación. **/
	@Column(name = "PAN_SUBSAN", nullable = false, precision = 1, scale = 0)
	private boolean permiteSubsanar;

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
	 * @return the permiteSubsanar
	 */
	public boolean isPermiteSubsanar() {
		return permiteSubsanar;
	}

	/**
	 * @param permiteSubsanar
	 *            the permiteSubsanar to set
	 */
	public void setPermiteSubsanar(final boolean permiteSubsanar) {
		this.permiteSubsanar = permiteSubsanar;
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
			jpaso.setPermiteSubsanar(paso.isPermiteSubsanar());
			if (paso.getDocumentos() != null) {
				final Set<JAnexoTramite> anexos = new HashSet<>();
				for (final Documento doc : paso.getDocumentos()) {
					final JAnexoTramite anexo = JAnexoTramite.fromModel(doc);
					anexo.setPasoAnexar(jpaso);
					anexos.add(anexo);
				}
				jpaso.setAnexosTramite(anexos);
			}
			jpaso.setScriptAnexosDinamicos(JScript.fromModel(paso.getScriptAnexosDinamicos()));
		}
		return jpaso;
	}

	/**
	 * Clonar.
	 *
	 * @param origPasoAnexar
	 * @return
	 */
	public static JPasoAnexar clonar(final JPasoAnexar origPasoAnexar, final JPasoTramitacion jpasoTramitacion) {
		JPasoAnexar jpasoAnexar = null;
		if (origPasoAnexar != null) {
			jpasoAnexar = new JPasoAnexar();
			jpasoAnexar.setCodigo(null);
			jpasoAnexar.setPasoTramitacion(jpasoTramitacion);
			jpasoAnexar.setScriptAnexosDinamicos(JScript.clonar(origPasoAnexar.getScriptAnexosDinamicos()));
			jpasoAnexar.setPermiteSubsanar(origPasoAnexar.isPermiteSubsanar());
			if (origPasoAnexar.getAnexosTramite() != null) {
				jpasoAnexar.setAnexosTramite(new HashSet<JAnexoTramite>());
				int ordenAnexo = 1;
				final List<JAnexoTramite> anexos = new ArrayList<>(origPasoAnexar.getAnexosTramite());
				Collections.sort(anexos, new Comparator<JAnexoTramite>() {
					@Override
					public int compare(final JAnexoTramite p1, final JAnexoTramite p2) {
						return Integer.compare(p1.getOrden(), p2.getOrden());
					}

				});
				for (final JAnexoTramite origAnexo : anexos) {
					if (origAnexo != null) {
						final JAnexoTramite janexo = JAnexoTramite.clonar(origAnexo);
						janexo.setPasoAnexar(jpasoAnexar);
						janexo.setOrden(ordenAnexo);
						jpasoAnexar.getAnexosTramite().add(janexo);
					}
					ordenAnexo++;
				}
			}
		}
		return jpasoAnexar;
	}

}
