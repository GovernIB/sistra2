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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypePresentacion;
import es.caib.sistrages.core.api.model.types.TypeTamanyo;

/**
 * JAnexoTramite
 */
@Entity
@Table(name = "STG_ANETRA", uniqueConstraints = @UniqueConstraint(columnNames = { "ANE_CODPTR", "ANE_IDEDOC" }))
public class JAnexoTramite implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_ANETRA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_ANETRA_SEQ", sequenceName = "STG_ANETRA_SEQ")
	@Column(name = "ANE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ANE_AYUFIC")
	private JFichero ficheroPlantilla;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANE_CODPTR", nullable = false)
	private JPasoAnexar pasoAnexar;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ANE_SCRVAL")
	private JScript scriptValidacion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ANE_SCRFIR")
	private JScript scriptFirmantes;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ANE_SCROBL")
	private JScript scriptObligatoriedad;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ANE_DESCRIP", nullable = false)
	private JLiteral descripcion;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "ANE_AYUTXT")
	private JLiteral textoAyuda;

	@Column(name = "ANE_IDEDOC", nullable = false, length = 20)
	private String identificadorDocumento;

	@Column(name = "ANE_ORDEN", nullable = false, precision = 2, scale = 0)
	private int orden;

	@Column(name = "ANE_OBLIGA", nullable = false, length = 1)
	private String obligatorio;

	@Column(name = "ANE_AYUULR", length = 250)
	private String plantillaUrl;

	@Column(name = "ANE_TIPPRE", nullable = false, length = 1)
	private String tipoPresentacion;

	@Column(name = "ANE_NUMINS", nullable = false, precision = 2, scale = 0)
	private int numeroInstancia;

	@Column(name = "ANE_EXTPER", nullable = false, length = 1000)
	private String extensionesPermitidas;

	@Column(name = "ANE_TAMMAX", nullable = false, precision = 4, scale = 0)
	private int tamanyoMaximo;

	@Column(name = "ANE_TAMUNI", nullable = false, length = 2)
	private String tamanyoUnidad;

	@Column(name = "ANE_CNVPDF", nullable = false, precision = 1, scale = 0)
	private boolean convertirPdf;

	@Column(name = "ANE_FIRMAR", nullable = false, precision = 1, scale = 0)
	private boolean firmar;

	@Column(name = "ANE_FIRMAD", nullable = false, precision = 1, scale = 0)
	private boolean anexarFirmado;

	@Transient
	private Long codigoClonado;

	public JAnexoTramite() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFichero getFicheroPlantilla() {
		return this.ficheroPlantilla;
	}

	public void setFicheroPlantilla(final JFichero ficheroPlantilla) {
		this.ficheroPlantilla = ficheroPlantilla;
	}

	public JPasoAnexar getPasoAnexar() {
		return this.pasoAnexar;
	}

	public void setPasoAnexar(final JPasoAnexar pasoAnexar) {
		this.pasoAnexar = pasoAnexar;
	}

	public JScript getScriptValidacion() {
		return this.scriptValidacion;
	}

	public void setScriptValidacion(final JScript scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

	public JScript getScriptFirmantes() {
		return this.scriptFirmantes;
	}

	public void setScriptFirmantes(final JScript scriptFirmantes) {
		this.scriptFirmantes = scriptFirmantes;
	}

	public JScript getScriptObligatoriedad() {
		return this.scriptObligatoriedad;
	}

	public void setScriptObligatoriedad(final JScript scriptObligatoriedad) {
		this.scriptObligatoriedad = scriptObligatoriedad;
	}

	public JLiteral getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final JLiteral descripcion) {
		this.descripcion = descripcion;
	}

	public JLiteral getTextoAyuda() {
		return this.textoAyuda;
	}

	public void setTextoAyuda(final JLiteral textoAyuda) {
		this.textoAyuda = textoAyuda;
	}

	public String getIdentificadorDocumento() {
		return this.identificadorDocumento;
	}

	public void setIdentificadorDocumento(final String identificadorDocumento) {
		this.identificadorDocumento = identificadorDocumento;
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

	public String getPlantillaUrl() {
		return this.plantillaUrl;
	}

	public void setPlantillaUrl(final String plantillaUrl) {
		this.plantillaUrl = plantillaUrl;
	}

	public String getTipoPresentacion() {
		return this.tipoPresentacion;
	}

	public void setTipoPresentacion(final String tipoPresentacion) {
		this.tipoPresentacion = tipoPresentacion;
	}

	public int getNumeroInstancia() {
		return this.numeroInstancia;
	}

	public void setNumeroInstancia(final int numeroInstancia) {
		this.numeroInstancia = numeroInstancia;
	}

	public String getExtensionesPermitidas() {
		return this.extensionesPermitidas;
	}

	public void setExtensionesPermitidas(final String extensionesPermitidas) {
		this.extensionesPermitidas = extensionesPermitidas;
	}

	public int getTamanyoMaximo() {
		return this.tamanyoMaximo;
	}

	public void setTamanyoMaximo(final int tamanyoMaximo) {
		this.tamanyoMaximo = tamanyoMaximo;
	}

	public String getTamanyoUnidad() {
		return this.tamanyoUnidad;
	}

	public void setTamanyoUnidad(final String tamanyoUnidad) {
		this.tamanyoUnidad = tamanyoUnidad;
	}

	public boolean isConvertirPdf() {
		return this.convertirPdf;
	}

	public void setConvertirPdf(final boolean aneCnvpdf) {
		this.convertirPdf = aneCnvpdf;
	}

	public boolean isFirmar() {
		return this.firmar;
	}

	public void setFirmar(final boolean firmar) {
		this.firmar = firmar;
	}

	public boolean isAnexarFirmado() {
		return this.anexarFirmado;
	}

	public void setAnexarFirmado(final boolean anexarFirmado) {
		this.anexarFirmado = anexarFirmado;
	}

	/**
	 * @return the codigoClonado
	 */
	public Long getCodigoClonado() {
		return codigoClonado;
	}

	/**
	 * @param codigoClonado
	 *            the codigoClonado to set
	 */
	public void setCodigoClonado(final Long codigoClonado) {
		this.codigoClonado = codigoClonado;
	}

	public static JAnexoTramite fromModel(final Documento doc) {
		JAnexoTramite janexo = null;
		if (doc != null) {
			janexo = new JAnexoTramite();
			janexo.setAnexarFirmado(doc.isDebeAnexarFirmado());
			janexo.setCodigo(doc.getCodigo());
			janexo.setConvertirPdf(doc.isDebeConvertirPDF());
			if (doc.getDescripcion() != null) {
				janexo.setDescripcion(JLiteral.fromModel(doc.getDescripcion()));
			}
			if (doc.getAyudaFichero() != null) {
				janexo.setFicheroPlantilla(JFichero.fromModel(doc.getAyudaFichero()));
			}
			janexo.setExtensionesPermitidas(doc.getExtensiones());
			janexo.setFirmar(doc.isDebeFirmarDigitalmente());
			janexo.setIdentificadorDocumento(doc.getIdentificador());
			janexo.setNumeroInstancia(doc.getNumeroInstancia());
			janexo.setObligatorio(doc.getObligatoriedad().toString());
			janexo.setOrden(doc.getOrden());
			janexo.setPlantillaUrl(doc.getAyudaURL());
			janexo.setTamanyoMaximo(doc.getTamanyoMaximo());
			if (doc.getTipoTamanyo() != null) {
				janexo.setTamanyoUnidad(doc.getTipoTamanyo().toString());
			}
			if (doc.getAyudaTexto() != null) {
				janexo.setTextoAyuda(JLiteral.fromModel(doc.getAyudaTexto()));
			}
			if (doc.getTipoPresentacion() != null) {
				janexo.setTipoPresentacion(doc.getTipoPresentacion().toString());
			}
			janexo.setScriptFirmantes(JScript.fromModel(doc.getScriptFirmarDigitalmente()));
			janexo.setScriptObligatoriedad(JScript.fromModel(doc.getScriptObligatoriedad()));
			janexo.setScriptValidacion(JScript.fromModel(doc.getScriptValidacion()));
		}
		return janexo;
	}

	public Documento toModel() {
		final Documento mdoc = new Documento();

		mdoc.setDebeAnexarFirmado(this.isAnexarFirmado());
		mdoc.setCodigo(this.getCodigo());
		mdoc.setDebeConvertirPDF(this.isConvertirPdf());
		if (this.getDescripcion() != null) {
			mdoc.setDescripcion(this.getDescripcion().toModel());
		}
		if (this.getFicheroPlantilla() != null) {
			mdoc.setAyudaFichero(this.getFicheroPlantilla().toModel());
		}
		mdoc.setExtensiones(this.getExtensionesPermitidas());
		mdoc.setDebeFirmarDigitalmente(this.isFirmar());
		mdoc.setIdentificador(this.getIdentificadorDocumento());
		mdoc.setNumeroInstancia(this.getNumeroInstancia());
		mdoc.setObligatoriedad(TypeFormularioObligatoriedad.fromString(this.getObligatorio()));
		mdoc.setOrden(this.getOrden());
		mdoc.setAyudaURL(this.getPlantillaUrl());
		mdoc.setTamanyoMaximo(this.getTamanyoMaximo());
		mdoc.setTipoTamanyo(TypeTamanyo.fromString(this.getTamanyoUnidad()));
		if (this.getTextoAyuda() != null) {
			mdoc.setAyudaTexto(this.getTextoAyuda().toModel());
		}
		mdoc.setTipoPresentacion(TypePresentacion.fromString(this.getTipoPresentacion()));
		if (this.getScriptFirmantes() != null) {
			mdoc.setScriptFirmarDigitalmente(this.getScriptFirmantes().toModel());
		}
		if (this.getScriptObligatoriedad() != null) {
			mdoc.setScriptObligatoriedad(this.getScriptObligatoriedad().toModel());
		}
		if (this.getScriptValidacion() != null) {
			mdoc.setScriptValidacion(this.getScriptValidacion().toModel());
		}
		return mdoc;
	}

	public static JAnexoTramite clonar(final JAnexoTramite origAnexo) {
		JAnexoTramite janexoTramite = null;
		if (origAnexo != null) {
			janexoTramite = new JAnexoTramite();
			janexoTramite.setCodigo(null);
			janexoTramite.setCodigoClonado(origAnexo.getCodigo());
			janexoTramite.setAnexarFirmado(origAnexo.isAnexarFirmado());
			janexoTramite.setConvertirPdf(origAnexo.isConvertirPdf());
			janexoTramite.setDescripcion(JLiteral.clonar(origAnexo.getDescripcion()));
			janexoTramite.setFicheroPlantilla(JFichero.clonar(origAnexo.getFicheroPlantilla()));
			janexoTramite.setExtensionesPermitidas(origAnexo.getExtensionesPermitidas());
			janexoTramite.setFirmar(origAnexo.isFirmar());
			janexoTramite.setIdentificadorDocumento(origAnexo.getIdentificadorDocumento());
			janexoTramite.setNumeroInstancia(origAnexo.getNumeroInstancia());
			janexoTramite.setObligatorio(origAnexo.getObligatorio());
			janexoTramite.setOrden(origAnexo.getOrden());
			janexoTramite.setPlantillaUrl(origAnexo.getPlantillaUrl());
			janexoTramite.setTamanyoMaximo(origAnexo.getTamanyoMaximo());
			janexoTramite.setTamanyoUnidad(origAnexo.getTamanyoUnidad());
			janexoTramite.setTextoAyuda(JLiteral.clonar(origAnexo.getTextoAyuda()));
			janexoTramite.setTipoPresentacion(origAnexo.getTipoPresentacion());
			janexoTramite.setScriptFirmantes(JScript.clonar(origAnexo.getScriptFirmantes()));
			janexoTramite.setScriptObligatoriedad(JScript.clonar(origAnexo.getScriptObligatoriedad()));
			janexoTramite.setScriptValidacion(JScript.clonar(origAnexo.getScriptValidacion()));
		}
		return janexoTramite;
	}

}