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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypePago;

/**
 * JPagoTramite
 */
@Entity
@Table(name = "STG_PAGTRA", uniqueConstraints = @UniqueConstraint(columnNames = { "PAG_CODPTR", "PAG_IDENTI" }))
public class JPagoTramite implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_PAGTRA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_PAGTRA_SEQ", sequenceName = "STG_PAGTRA_SEQ")
	@Column(name = "PAG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAG_CODPTR", nullable = false)
	private JPasoPagos pasoPagos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAG_SCROBL")
	private JScript scriptObligatoriedad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAG_SCRDPG")
	private JScript scriptDatosPago;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PAG_DESCRIP", nullable = false)
	private JLiteral descripcion;

	@Column(name = "PAG_IDENTI", nullable = false, length = 20)
	private String identificador;

	@Column(name = "PAG_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	@Column(name = "PAG_OBLIGA", nullable = false, length = 1)
	private String obligatorio;

	@Column(name = "PAG_PLUGIN", nullable = false, length = 20)
	private String plugin;

	@Column(name = "PAG_TIPO", nullable = false, length = 1)
	private String tipo;

	@Column(name = "PAG_SIMULA", nullable = false, precision = 1, scale = 0)
	private boolean simulado;

	public JPagoTramite() {
		// Constructor vacio
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JPasoPagos getPasoPagos() {
		return this.pasoPagos;
	}

	public void setPasoPagos(final JPasoPagos pasoPagos) {
		this.pasoPagos = pasoPagos;
	}

	public JScript getScriptObligatoriedad() {
		return this.scriptObligatoriedad;
	}

	public void setScriptObligatoriedad(final JScript scriptObligatoriedad) {
		this.scriptObligatoriedad = scriptObligatoriedad;
	}

	public JScript getScriptDatosPago() {
		return this.scriptDatosPago;
	}

	public void setScriptDatosPago(final JScript scriptDatosPago) {
		this.scriptDatosPago = scriptDatosPago;
	}

	public JLiteral getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final JLiteral descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public String getObligatorio() {
		return this.obligatorio;
	}

	public void setObligatorio(final String obligatorio) {
		this.obligatorio = obligatorio;
	}

	public String getPlugin() {
		return this.plugin;
	}

	public void setPlugin(final String plugin) {
		this.plugin = plugin;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public boolean isSimulado() {
		return this.simulado;
	}

	public void setSimulado(final boolean simulado) {
		this.simulado = simulado;
	}

	public static JPagoTramite fromModel(final Tasa tasa) {
		JPagoTramite pago = null;
		if (tasa != null) {
			pago = new JPagoTramite();
			pago.setCodigo(tasa.getId());
			if (tasa.getDescripcion() != null) {
				pago.setDescripcion(JLiteral.fromModel(tasa.getDescripcion()));
			}
			pago.setIdentificador(tasa.getCodigo());
			pago.setObligatorio(tasa.getObligatoriedad().toString());
			pago.setOrden(tasa.getOrden());
			pago.setPlugin(tasa.getTipoPlugin());
			pago.setSimulado(tasa.isSimulado());
			pago.setTipo(tasa.getTipo().toString());
		}
		return pago;
	}

	public Tasa toModel() {
		final Tasa tasa = new Tasa();
		tasa.setId(this.getCodigo());
		if (tasa.getDescripcion() != null) {
			tasa.setDescripcion(this.getDescripcion().toModel());
		}
		tasa.setCodigo(this.getIdentificador());
		tasa.setObligatoriedad(TypeFormularioObligatoriedad.fromString(this.getObligatorio()));
		tasa.setOrden(this.getOrden());
		tasa.setTipoPlugin(this.getPlugin());
		tasa.setSimulado(this.isSimulado());
		tasa.setTipo(TypePago.fromString(this.getTipo()));
		return tasa;
	}

}
