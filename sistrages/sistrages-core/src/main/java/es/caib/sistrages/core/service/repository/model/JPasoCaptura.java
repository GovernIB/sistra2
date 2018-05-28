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

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@Column(name = "PCA_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Formulario tramite. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PCA_CODFOR")
	private JFormularioTramite formularioTramite;

	/** Paso tramitacion. **/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PCA_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	/** Constructor. **/
	public JPasoCaptura() {
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
	 * @return the formularioTramite
	 */
	public JFormularioTramite getFormularioTramite() {
		return formularioTramite;
	}

	/**
	 * @param formularioTramite
	 *            the formularioTramite to set
	 */
	public void setFormularioTramite(final JFormularioTramite formularioTramite) {
		this.formularioTramite = formularioTramite;
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
	 * Clonar.
	 *
	 * @param origPasoCaptura
	 * @return
	 */
	public static JPasoCaptura clonar(final JPasoCaptura origPasoCaptura, final JPasoTramitacion jpasoTramitacion) {
		JPasoCaptura jpasoCaptura = null;
		if (origPasoCaptura != null) {
			jpasoCaptura = new JPasoCaptura();
			jpasoCaptura.setCodigo(null);
			jpasoCaptura.setPasoTramitacion(jpasoTramitacion);
			jpasoCaptura.setFormularioTramite(JFormularioTramite.clonar(origPasoCaptura.getFormularioTramite()));
		}
		return jpasoCaptura;
	}

}
