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
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.types.TypePaso;

/**
 * JPasoTramitacion
 */
@Entity
@Table(name = "STG_PASOTR", uniqueConstraints = @UniqueConstraint(columnNames = { "PTR_IDEPTR", "PTR_CODVTR" }))
public class JPasoTramitacion implements IModelApi {

	/** Serial Version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_PASOTR_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_PASOTR_SEQ", sequenceName = "STG_PASOTR_SEQ")
	@Column(name = "PTR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Script variables. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTR_SCRVAR")
	private JScript scriptVariables;

	/** Script navegacion. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTR_SCRNVG")
	private JScript scriptNavegacion;

	/** Tipo paso tramitacion. **/
	@Column(name = "PTR_TIPPAS", nullable = false)
	private String tipoPaso;

	/** Descripcion. **/
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PTR_DESCRI")
	private JLiteral descripcion;

	/** Version tramite. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTR_CODVTR", nullable = false)
	private JVersionTramite versionTramite;

	/** Id paso tramitacion. **/
	@Column(name = "PTR_IDEPTR", nullable = false, length = 20)
	private String idPasoTramitacion;

	/** Orden. **/
	@Column(name = "PTR_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	/** Paso final. **/
	@Column(name = "PTR_FINAL", nullable = false, precision = 1, scale = 0)
	private boolean pasoFinal;

	/** Paso rellenar. **/
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoRellenar pasoRellenar;

	/** Paso informacion. **/
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoInformacion pasoInformacion;

	/** Paso captura. **/
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoCaptura pasoCaptura;

	/** Paso debe saber. **/
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoDebeSaber pasoDebeSaber;

	/** Paso pagos. **/
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoPagos pasoPagos;

	/** Paso registrar. **/
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoRegistrar pasoRegistrar;

	/** Paso anexar. **/
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pasoTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private JPasoAnexar pasoAnexar;

	/** Constructor. **/
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

	/**
	 * @return the tipoPaso
	 */
	public String getTipoPaso() {
		return tipoPaso;
	}

	/**
	 * @param tipoPaso
	 *            the tipoPaso to set
	 */
	public void setTipoPaso(final String tipoPaso) {
		this.tipoPaso = tipoPaso;
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

		} else if (this.getPasoRegistrar() != null) {

			return this.toModelRegistrar();

		} else {

			final TramitePaso paso = new TramitePaso();
			paso.setIdPasoTramitacion(this.getIdPasoTramitacion());
			paso.setDescripcion(this.getDescripcion().toModel());
			paso.setCodigo(this.getCodigo());
			paso.setOrden(this.getOrden());
			paso.setPasoFinal(this.isPasoFinal());
			return paso;

		}

	}

	private TramitePasoRegistrar toModelRegistrar() {
		final TramitePasoRegistrar paso = new TramitePasoRegistrar();
		paso.setIdPasoTramitacion(this.getIdPasoTramitacion());
		paso.setDescripcion(this.getDescripcion().toModel());
		paso.setCodigo(this.getCodigo());
		paso.setOrden(this.getOrden());
		paso.setPasoFinal(this.isPasoFinal());
		paso.setAdmiteRepresentacion(this.getPasoRegistrar().isAdmiteRepresentacion());
		paso.setCodigoLibroRegistro(this.getPasoRegistrar().getCodigoLibroRegistro());
		paso.setCodigoOficinaRegistro(this.getPasoRegistrar().getCodigoOficinaRegistro());
		paso.setCodigoTipoAsunto(this.getPasoRegistrar().getCodigoTipoAsunto());
		if (this.getPasoRegistrar().getInstruccionesFinTramitacion() != null) {
			paso.setInstruccionesFinTramitacion(this.getPasoRegistrar().getInstruccionesFinTramitacion().toModel());
		}
		if (this.getPasoRegistrar().getInstruccionesPresentacion() != null) {
			paso.setInstruccionesPresentacion(this.getPasoRegistrar().getInstruccionesPresentacion().toModel());
		}
		paso.setTipo(TypePaso.fromString(this.getTipoPaso()));
		paso.setValidaRepresentacion(this.getPasoRegistrar().isValidaRepresentacion());
		if (this.getPasoRegistrar().getScriptDestinoRegistro() != null) {
			paso.setScriptDestinoRegistro(this.getPasoRegistrar().getScriptDestinoRegistro().toModel());
		}
		if (this.getPasoRegistrar().getScriptPresentador() != null) {
			paso.setScriptPresentador(this.getPasoRegistrar().getScriptPresentador().toModel());
		}
		if (this.getPasoRegistrar().getScriptRepresentante() != null) {
			paso.setScriptRepresentante(this.getPasoRegistrar().getScriptRepresentante().toModel());
		}
		if (this.getPasoRegistrar().getScriptValidarRegistrar() != null) {
			paso.setScriptValidarRegistrar(this.getPasoRegistrar().getScriptValidarRegistrar().toModel());
		}

		return paso;
	}

	private TramitePasoAnexar toModelAnexar() {
		final TramitePasoAnexar paso = new TramitePasoAnexar();
		paso.setIdPasoTramitacion(this.getIdPasoTramitacion());
		paso.setDescripcion(this.getDescripcion().toModel());
		paso.setCodigo(this.getCodigo());
		paso.setOrden(this.getOrden());
		paso.setPasoFinal(this.isPasoFinal());
		paso.setTipo(TypePaso.fromString(this.getTipoPaso()));

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

		final TramitePasoTasa paso = new TramitePasoTasa();
		paso.setIdPasoTramitacion(this.getIdPasoTramitacion());
		paso.setDescripcion(this.getDescripcion().toModel());
		paso.setCodigo(this.getCodigo());
		paso.setOrden(this.getOrden());
		paso.setPasoFinal(this.isPasoFinal());
		paso.setTipo(TypePaso.fromString(this.getTipoPaso()));

		if (this.getPasoPagos().getPagosTramite() != null) {
			final List<Tasa> tasas = new ArrayList<>();
			for (final JPagoTramite jpago : this.getPasoPagos().getPagosTramite()) {
				tasas.add(jpago.toModel());
			}
			paso.setTasas(tasas);
		}
		return paso;
	}

	private TramitePasoRellenar toModelRellenar() {
		final TramitePasoRellenar paso = new TramitePasoRellenar();
		paso.setIdPasoTramitacion(this.getIdPasoTramitacion());
		paso.setDescripcion(this.getDescripcion().toModel());
		paso.setCodigo(this.getCodigo());
		paso.setIdPasoRelacion(this.getPasoRellenar().getCodigo());
		paso.setOrden(this.getOrden());
		paso.setPasoFinal(this.isPasoFinal());
		paso.setTipo(TypePaso.fromString(this.getTipoPaso()));
		if (this.getPasoRellenar().getFormulariosTramite() != null) {
			final List<FormularioTramite> formularios = new ArrayList<>();
			for (final JFormularioTramite jformulario : this.getPasoRellenar().getFormulariosTramite()) {
				final FormularioTramite formulario = jformulario.toModel();
				formularios.add(formulario);
			}
			paso.setFormulariosTramite(formularios);
		}

		return paso;
	}

	private TramitePasoDebeSaber toModelDebeSaber() {
		final TramitePasoDebeSaber paso = new TramitePasoDebeSaber();
		paso.setIdPasoRelacion(this.getPasoDebeSaber().getCodigo());
		paso.setIdPasoTramitacion(this.getIdPasoTramitacion());
		paso.setDescripcion(this.getDescripcion().toModel());
		paso.setCodigo(this.getCodigo());
		paso.setOrden(this.getOrden());
		paso.setPasoFinal(this.isPasoFinal());
		paso.setTipo(TypePaso.fromString(this.getTipoPaso()));
		if (this.getPasoDebeSaber().getInstruccionesInicio() != null) {
			final Literal instruccionesIniciales = this.getPasoDebeSaber().getInstruccionesInicio().toModel();
			paso.setInstruccionesIniciales(instruccionesIniciales);
		}
		return paso;
	}

	public static JPasoTramitacion fromModel(final TramitePaso paso) {
		JPasoTramitacion jpaso = null;
		if (paso != null) {
			jpaso = new JPasoTramitacion();
			jpaso.setIdPasoTramitacion(paso.getIdPasoTramitacion());

			if (paso.getDescripcion() != null) {
				final JLiteral jliteral = JLiteral.fromModel(paso.getDescripcion());
				jpaso.setDescripcion(jliteral);
			}
			jpaso.setCodigo(paso.getCodigo());
			jpaso.setOrden(paso.getOrden());
			jpaso.setPasoFinal(paso.isPasoFinal());
			if (paso.getTipo() != null) {
				jpaso.setTipoPaso(paso.getTipo().toString());
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

			} else if (paso instanceof TramitePasoRegistrar) {

				final JPasoRegistrar jpasoRegistrar = JPasoRegistrar.fromModel((TramitePasoRegistrar) paso);
				jpaso.setPasoRegistrar(jpasoRegistrar);
				jpaso.getPasoRegistrar().setPasoTramitacion(jpaso);
			}
		}
		return jpaso;
	}

	/**
	 * Clonar
	 *
	 * @param origPaso
	 * @return
	 */
	public static JPasoTramitacion clonar(final JPasoTramitacion origPaso, final JVersionTramite jTramiteVersion) {
		JPasoTramitacion jpaso = null;
		if (origPaso != null) {
			jpaso = new JPasoTramitacion();
			jpaso.setVersionTramite(jTramiteVersion);
			jpaso.setCodigo(null);
			jpaso.setDescripcion(JLiteral.clonar(origPaso.getDescripcion()));
			jpaso.setIdPasoTramitacion(origPaso.getIdPasoTramitacion());
			jpaso.setOrden(origPaso.getOrden());
			jpaso.setPasoFinal(origPaso.isPasoFinal());
			jpaso.setTipoPaso(origPaso.getTipoPaso());

			// Los pasos
			jpaso.setPasoAnexar(JPasoAnexar.clonar(origPaso.getPasoAnexar(), jpaso));
			jpaso.setPasoCaptura(JPasoCaptura.clonar(origPaso.getPasoCaptura(), jpaso));
			jpaso.setPasoDebeSaber(JPasoDebeSaber.clonar(origPaso.getPasoDebeSaber(), jpaso));
			jpaso.setPasoInformacion(JPasoInformacion.clonar(origPaso.getPasoInformacion(), jpaso));
			jpaso.setPasoPagos(JPasoPagos.clonar(origPaso.getPasoPagos(), jpaso));
			jpaso.setPasoRegistrar(JPasoRegistrar.clonar(origPaso.getPasoRegistrar(), jpaso));
			jpaso.setPasoRellenar(JPasoRellenar.clonar(origPaso.getPasoRellenar(), jpaso));

		}
		return jpaso;
	}

}
