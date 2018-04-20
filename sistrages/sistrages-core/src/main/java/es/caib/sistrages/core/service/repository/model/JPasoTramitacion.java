package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.TramitePaso;

/**
 * JPasoTramitacion
 */
@Entity
@Table(name = "STG_PASOTR", uniqueConstraints = @UniqueConstraint(columnNames = { "PTR_IDEPTR", "PTR_CODVTR" }))
public class JPasoTramitacion implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_PASOTR_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_PASOTR_SEQ", sequenceName = "STG_PASOTR_SEQ")
	@Column(name = "PTR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTR_SCRVAR")
	private JScript scriptVariables;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTR_SCRNVG")
	private JScript scriptNavegacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTR_TIPPTR", nullable = false)
	private JTipoPasoTramitacion tipoPasoTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTR_DESCRI")
	private JLiteral descripcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTR_CODVTR", nullable = false)
	private JVersionTramite versionTramite;

	@Column(name = "PTR_IDEPTR", nullable = false, length = 20)
	private String idPasoTramitacion;

	@Column(name = "PTR_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	@Column(name = "PTR_FINAL", nullable = false, precision = 1, scale = 0)
	private boolean pasoFinal;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL)
	private JPasoRellenar pasoRellenar;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL)
	private JPasoInformacion pasoInformacion;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL)
	private JPasoCaptura pasoCaptura;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL)
	private JPasoDebeSaber pasoDebeSaber;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL)
	private JPasoPagos pasoPagos;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL)
	private JPasoRegistrar pasoRegistrar;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL)
	private JPasoAnexar pasoAnexar;

	public JPasoTramitacion() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JScript getScriptVariables() {
		return this.scriptVariables;
	}

	public void setScriptVariables(final JScript scriptVariables) {
		this.scriptVariables = scriptVariables;
	}

	public JScript getScriptNavegacion() {
		return this.scriptNavegacion;
	}

	public void setScriptNavegacion(final JScript scriptNavegacion) {
		this.scriptNavegacion = scriptNavegacion;
	}

	public JTipoPasoTramitacion getTipoPasoTramitacion() {
		return this.tipoPasoTramitacion;
	}

	public void setTipoPasoTramitacion(final JTipoPasoTramitacion tipoPasoTramitacion) {
		this.tipoPasoTramitacion = tipoPasoTramitacion;
	}

	public JLiteral getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final JLiteral descripcion) {
		this.descripcion = descripcion;
	}

	public JVersionTramite getVersionTramite() {
		return this.versionTramite;
	}

	public void setVersionTramite(final JVersionTramite versionTramite) {
		this.versionTramite = versionTramite;
	}

	public String getIdPasoTramitacion() {
		return this.idPasoTramitacion;
	}

	public void setIdPasoTramitacion(final String idPasoTramitacion) {
		this.idPasoTramitacion = idPasoTramitacion;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public boolean isPasoFinal() {
		return this.pasoFinal;
	}

	public void setPasoFinal(final boolean pasoFinal) {
		this.pasoFinal = pasoFinal;
	}

	public JPasoRellenar getPasoRellenar() {
		return this.pasoRellenar;
	}

	public void setPasoRellenar(final JPasoRellenar pasoRellenar) {
		this.pasoRellenar = pasoRellenar;
	}

	public JPasoInformacion getPasoInformacion() {
		return this.pasoInformacion;
	}

	public void setPasoInformacion(final JPasoInformacion pasoInformacion) {
		this.pasoInformacion = pasoInformacion;
	}

	public JPasoCaptura getPasoCaptura() {
		return this.pasoCaptura;
	}

	public void setPasoCaptura(final JPasoCaptura pasoCaptura) {
		this.pasoCaptura = pasoCaptura;
	}

	public JPasoDebeSaber getPasoDebeSaber() {
		return this.pasoDebeSaber;
	}

	public void setPasoDebeSaber(final JPasoDebeSaber pasoDebeSaber) {
		this.pasoDebeSaber = pasoDebeSaber;
	}

	public JPasoPagos getPasoPagos() {
		return this.pasoPagos;
	}

	public void setPasoPagos(final JPasoPagos pasoPagos) {
		this.pasoPagos = pasoPagos;
	}

	public JPasoRegistrar getPasoRegistrar() {
		return this.pasoRegistrar;
	}

	public void setPasoRegistrar(final JPasoRegistrar pasoRegistrar) {
		this.pasoRegistrar = pasoRegistrar;
	}

	public JPasoAnexar getPasoAnexar() {
		return this.pasoAnexar;
	}

	public void setPasoAnexar(final JPasoAnexar pasoAnexar) {
		this.pasoAnexar = pasoAnexar;
	}

	public TramitePaso toModel() {
		final TramitePaso paso = new TramitePaso();
		paso.setCodigo(this.getIdPasoTramitacion());
		paso.setDescripcion(this.getDescripcion().toModel());
		paso.setId(this.getCodigo());
		paso.setOrden(this.getOrden());
		paso.setPasoFinal(this.isPasoFinal());
		return paso;
	}

	public JPasoTramitacion fromModel(final TramitePaso paso) {
		JPasoTramitacion jpaso = null;
		if (paso != null) {
			jpaso = new JPasoTramitacion();
			jpaso.setIdPasoTramitacion(paso.getCodigo());
			if (paso.getDescripcion() != null) {
				final JLiteral jliteral = JLiteral.fromModel(paso.getDescripcion());
				jpaso.setDescripcion(jliteral);
			}
			jpaso.setCodigo(paso.getId());
			jpaso.setOrden(paso.getOrden());
			jpaso.setPasoFinal(paso.isPasoFinal());
		}
		return jpaso;
	}

}
