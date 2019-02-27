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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;

/**
 * JPasoRellenar
 */
@Entity
@Table(name = "STG_PASREL")
public class JPasoRellenar implements IModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@Column(name = "PRL_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Paso tramitacion. **/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PRL_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	/** Formularios tramite. **/
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "STG_PRLFTR", joinColumns = {
			@JoinColumn(name = "FPR_CODPRL", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "FPR_CODFOR", nullable = false, updatable = false) })
	@OrderBy("orden ASC")
	private Set<JFormularioTramite> formulariosTramite = new HashSet<>(0);

	/** Constructor. **/
	public JPasoRellenar() {
		super();
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
	 * @return the formulariosTramite
	 */
	public Set<JFormularioTramite> getFormulariosTramite() {
		return formulariosTramite;
	}

	/**
	 * @param formulariosTramite
	 *            the formulariosTramite to set
	 */
	public void setFormulariosTramite(final Set<JFormularioTramite> formulariosTramite) {
		this.formulariosTramite = formulariosTramite;
	}

	/**
	 * From model.
	 *
	 * @param paso
	 * @return
	 */
	public static JPasoRellenar fromModel(final TramitePasoRellenar paso) {
		JPasoRellenar jpasoRellenar = null;
		if (paso != null) {
			jpasoRellenar = new JPasoRellenar();
			jpasoRellenar.setCodigo(paso.getCodigo());
			if (paso.getFormulariosTramite() != null) {
				final Set<JFormularioTramite> jformularios = new HashSet<>();
				for (final FormularioTramite formulario : paso.getFormulariosTramite()) {
					final JFormularioTramite jformulario = JFormularioTramite.fromModel(formulario);
					jformularios.add(jformulario);
				}
				jpasoRellenar.setFormulariosTramite(jformularios);
			}
		}
		return jpasoRellenar;
	}

	/**
	 * Clonar.
	 *
	 * @param origPasoRellenar
	 * @return
	 */
	public static JPasoRellenar clonar(final JPasoRellenar origPasoRellenar, final JPasoTramitacion jpasoTramitacion) {
		JPasoRellenar jpasoRellenar = null;
		if (origPasoRellenar != null) {
			jpasoRellenar = new JPasoRellenar();
			jpasoRellenar.setCodigo(null);
			jpasoRellenar.setPasoTramitacion(jpasoTramitacion);
			if (origPasoRellenar.getFormulariosTramite() != null) {
				jpasoRellenar.setFormulariosTramite(new HashSet<JFormularioTramite>());
				int ordenFormulario = 1;
				final List<JFormularioTramite> formularios = new ArrayList<>(origPasoRellenar.getFormulariosTramite());
				Collections.sort(formularios, new Comparator<JFormularioTramite>() {
					@Override
					public int compare(final JFormularioTramite p1, final JFormularioTramite p2) {
						return Integer.compare(p1.getOrden(), p2.getOrden());
					}

				});
				for (final JFormularioTramite formulario : formularios) {
					final JFormularioTramite jform = JFormularioTramite.clonar(formulario);
					jform.setOrden(ordenFormulario);
					jpasoRellenar.getFormulariosTramite().add(jform);
					ordenFormulario++;
				}
			}
		}
		return jpasoRellenar;
	}

}
