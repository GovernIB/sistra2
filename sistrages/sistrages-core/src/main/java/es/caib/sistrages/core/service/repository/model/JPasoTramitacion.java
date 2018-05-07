package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.List;

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

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;

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

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoRellenar pasoRellenar;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoInformacion pasoInformacion;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoCaptura pasoCaptura;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoDebeSaber pasoDebeSaber;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoPagos pasoPagos;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoRegistrar pasoRegistrar;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoAnexar pasoAnexar;

	public JPasoTramitacion() {
		// Constructor vacio
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
		if (this.getPasoRellenar() != null) {
			return this.toModelRellenar();

		} else if (this.getPasoDebeSaber() != null) {

			return this.toModelDebeSaber();

		} else if (this.getPasoPagos() != null) {

			return this.toModelPagos();

		} else if (this.getPasoAnexar() != null) {

			return this.toModelAnexar();

		} else {

			final TramitePaso paso = new TramitePaso();
			paso.setCodigo(this.getIdPasoTramitacion());
			paso.setDescripcion(this.getDescripcion().toModel());
			paso.setId(this.getCodigo());
			paso.setOrden(this.getOrden());
			paso.setPasoFinal(this.isPasoFinal());
			return paso;

		}

	}

	private TramitePasoAnexar toModelAnexar() {
		final TramitePasoAnexar paso = new TramitePasoAnexar();
		paso.setCodigo(this.getIdPasoTramitacion());
		paso.setDescripcion(this.getDescripcion().toModel());
		paso.setId(this.getCodigo());
		paso.setOrden(this.getOrden());
		paso.setPasoFinal(this.isPasoFinal());

		if (this.getPasoAnexar().getAnexosTramite() != null) {
			final List<Documento> docs = new ArrayList<>();
			for (final JAnexoTramite doc : this.getPasoAnexar().getAnexosTramite()) {
				docs.add(doc.toModel());
			}
			paso.setDocumentos(docs);
		}

		return paso;
	}

	private TramitePasoTasa toModelPagos() {

		final TramitePasoTasa pago = new TramitePasoTasa();
		pago.setCodigo(this.getIdPasoTramitacion());
		pago.setDescripcion(this.getDescripcion().toModel());
		pago.setId(this.getCodigo());
		pago.setOrden(this.getOrden());
		pago.setPasoFinal(this.isPasoFinal());
		if (this.getPasoPagos().getPagosTramite() != null) {
			final List<Tasa> tasas = new ArrayList<>();
			for (final JPagoTramite jpago : this.getPasoPagos().getPagosTramite()) {
				tasas.add(jpago.toModel());
			}
			pago.setTasas(tasas);
		}
		return pago;
	}

	private TramitePasoRellenar toModelRellenar() {
		final TramitePasoRellenar mpasoRellenar = new TramitePasoRellenar();
		mpasoRellenar.setCodigo(this.getIdPasoTramitacion());
		mpasoRellenar.setDescripcion(this.getDescripcion().toModel());
		mpasoRellenar.setId(this.getCodigo());
		mpasoRellenar.setIdPasoRelacion(this.getPasoRellenar().getCodigo());
		mpasoRellenar.setOrden(this.getOrden());
		mpasoRellenar.setPasoFinal(this.isPasoFinal());
		if (this.getPasoRellenar().getFormulariosTramite() != null) {
			final List<FormularioTramite> formularios = new ArrayList<>();
			for (final JFormularioTramite jformulario : this.getPasoRellenar().getFormulariosTramite()) {
				final FormularioTramite formulario = jformulario.toModel();
				formularios.add(formulario);
			}
			mpasoRellenar.setFormulariosTramite(formularios);
		}

		return mpasoRellenar;
	}

	private TramitePasoDebeSaber toModelDebeSaber() {
		final TramitePasoDebeSaber mpasoDebeSaber = new TramitePasoDebeSaber();
		mpasoDebeSaber.setIdPasoRelacion(this.getPasoDebeSaber().getCodigo());
		mpasoDebeSaber.setCodigo(this.getIdPasoTramitacion());
		mpasoDebeSaber.setDescripcion(this.getDescripcion().toModel());
		mpasoDebeSaber.setId(this.getCodigo());
		mpasoDebeSaber.setOrden(this.getOrden());
		mpasoDebeSaber.setPasoFinal(this.isPasoFinal());
		if (this.getPasoDebeSaber().getInstruccionesInicio() != null) {
			final Literal instruccionesIniciales = this.getPasoDebeSaber().getInstruccionesInicio().toModel();
			mpasoDebeSaber.setInstruccionesIniciales(instruccionesIniciales);
		}
		return mpasoDebeSaber;
	}

	public static JPasoTramitacion fromModel(final TramitePaso paso) {
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
			if (paso.getTipo() != null) {
				final JTipoPasoTramitacion tipo = new JTipoPasoTramitacion();
				tipo.fromModel(paso.getTipo());
				jpaso.setTipoPasoTramitacion(tipo);
			}

			// Generamos los tipos
			if (paso instanceof TramitePasoRellenar) {

				final JPasoRellenar jpasoRellenar = JPasoRellenar.fromModel((TramitePasoRellenar) paso);
				jpaso.setPasoRellenar(jpasoRellenar);
				jpaso.getPasoRellenar().setPasoTramitacion(jpaso);

			} else if (paso instanceof TramitePasoDebeSaber) {

				final JPasoDebeSaber jpasoRellenar = JPasoDebeSaber.fromModel((TramitePasoDebeSaber) paso);
				jpaso.setPasoDebeSaber(jpasoRellenar);
				jpaso.getPasoDebeSaber().setPasoTramitacion(jpaso);

			} else if (paso instanceof TramitePasoTasa) {

				final JPasoPagos jpasoPagos = JPasoPagos.fromModel((TramitePasoTasa) paso);
				jpaso.setPasoPagos(jpasoPagos);
				jpaso.getPasoPagos().setPasoTramitacion(jpaso);

			} else if (paso instanceof TramitePasoAnexar) {

				final JPasoAnexar jpasoAnexar = JPasoAnexar.fromModel((TramitePasoAnexar) paso);
				jpaso.setPasoAnexar(jpasoAnexar);
				jpaso.getPasoAnexar().setPasoTramitacion(jpaso);

			}
		}
		return jpaso;
	}

}
