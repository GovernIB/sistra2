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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAG_PLUGIN")
	private JPlugin plugin;

	@Column(name = "PAG_TIPO", nullable = false, length = 1)
	private String tipo;

	@Column(name = "PAG_SIMULA", nullable = false, precision = 1, scale = 0)
	private boolean simulado;

	public JPagoTramite() {
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
	 * @return the pasoPagos
	 */
	public JPasoPagos getPasoPagos() {
		return pasoPagos;
	}

	/**
	 * @param pasoPagos
	 *            the pasoPagos to set
	 */
	public void setPasoPagos(final JPasoPagos pasoPagos) {
		this.pasoPagos = pasoPagos;
	}

	/**
	 * @return the scriptObligatoriedad
	 */
	public JScript getScriptObligatoriedad() {
		return scriptObligatoriedad;
	}

	/**
	 * @param scriptObligatoriedad
	 *            the scriptObligatoriedad to set
	 */
	public void setScriptObligatoriedad(final JScript scriptObligatoriedad) {
		this.scriptObligatoriedad = scriptObligatoriedad;
	}

	/**
	 * @return the scriptDatosPago
	 */
	public JScript getScriptDatosPago() {
		return scriptDatosPago;
	}

	/**
	 * @param scriptDatosPago
	 *            the scriptDatosPago to set
	 */
	public void setScriptDatosPago(final JScript scriptDatosPago) {
		this.scriptDatosPago = scriptDatosPago;
	}

	/**
	 * @return the descripcion
	 */
	public JLiteral getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final JLiteral descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

	/**
	 * @return the obligatorio
	 */
	public String getObligatorio() {
		return obligatorio;
	}

	/**
	 * @param obligatorio
	 *            the obligatorio to set
	 */
	public void setObligatorio(final String obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * @return the plugin
	 */
	public JPlugin getPlugin() {
		return plugin;
	}

	/**
	 * @param plugin
	 *            the plugin to set
	 */
	public void setPlugin(final JPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the simulado
	 */
	public boolean isSimulado() {
		return simulado;
	}

	/**
	 * @param simulado
	 *            the simulado to set
	 */
	public void setSimulado(final boolean simulado) {
		this.simulado = simulado;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * FromModel.
	 *
	 * @param tasa
	 * @return
	 */
	public static JPagoTramite fromModel(final Tasa tasa) {
		JPagoTramite pago = null;
		if (tasa != null) {
			pago = new JPagoTramite();
			pago.setCodigo(tasa.getId());
			if (tasa.getDescripcion() != null) {
				pago.setDescripcion(JLiteral.fromModel(tasa.getDescripcion()));
			}
			pago.setIdentificador(tasa.getCodigo());
			if (tasa.getObligatoriedad() != null) {
				pago.setObligatorio(tasa.getObligatoriedad().toString());
			}
			pago.setOrden(tasa.getOrden());
			if (tasa.getTipoPlugin() != null) {
				pago.setPlugin(JPlugin.fromModel(tasa.getTipoPlugin()));
			}
			pago.setSimulado(tasa.isSimulado());
			if (tasa.getTipo() != null) {
				pago.setTipo(tasa.getTipo().toString());
			}
			if (tasa.getScriptObligatoriedad() != null) {
				pago.setScriptObligatoriedad(JScript.fromModel(tasa.getScriptObligatoriedad()));
			}
			if (tasa.getScriptPago() != null) {
				pago.setScriptDatosPago(JScript.fromModel(tasa.getScriptPago()));
			}
		}
		return pago;
	}

	/**
	 * toModel.
	 *
	 * @return
	 */
	public Tasa toModel() {
		final Tasa tasa = new Tasa();
		tasa.setId(this.getCodigo());
		if (this.getDescripcion() != null) {
			tasa.setDescripcion(this.getDescripcion().toModel());
		}
		tasa.setCodigo(this.getIdentificador());
		tasa.setObligatoriedad(TypeFormularioObligatoriedad.fromString(this.getObligatorio()));
		tasa.setOrden(this.getOrden());
		if (this.getPlugin() != null) {
			tasa.setTipoPlugin(this.getPlugin().toModel());
		}
		tasa.setSimulado(this.isSimulado());
		tasa.setTipo(TypePago.fromString(this.getTipo()));
		if (this.getScriptDatosPago() != null) {
			tasa.setScriptPago(this.getScriptDatosPago().toModel());
		}
		if (this.getScriptObligatoriedad() != null) {
			tasa.setScriptObligatoriedad(this.getScriptObligatoriedad().toModel());
		}
		return tasa;
	}

}
