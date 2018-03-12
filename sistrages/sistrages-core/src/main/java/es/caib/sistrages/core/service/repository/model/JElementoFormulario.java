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

/**
 * JElementoFormulario
 */
@Entity
@Table(name = "STG_FORELE")
public class JElementoFormulario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORELE_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORELE_SEQ", sequenceName = "STG_FORELE_SEQ")
	@Column(name = "FEL_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEL_CODFLS", nullable = false)
	private JLineaSeccionFormulario lineaSeccionFormulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEL_AYUDA")
	private JLiterales ayuda;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEL_TEXTO")
	private JLiterales texto;

	@Column(name = "FEL_IDENTI", nullable = false, length = 50)
	private String identificador;

	@Column(name = "FEL_TIPO", nullable = false, length = 2)
	private String tipo;

	@Column(name = "FEL_ORDEN", nullable = false, precision = 3, scale = 0)
	private int orden;

	@Column(name = "FEL_NUMCOL", nullable = false, precision = 2, scale = 0)
	private int numeroColumnas;

	@Column(name = "FEL_TEXTNO", nullable = false, precision = 1, scale = 0)
	private boolean noMostrarTexto;

	@Column(name = "FEL_TEXTAL", nullable = false, length = 1)
	private String alineacionTexto;

	@Column(name = "FEL_LELMOS", nullable = false, precision = 1, scale = 0)
	private boolean mostrarEnListaElementos;

	@Column(name = "FEL_LELCOL", precision = 1, scale = 0)
	private Boolean listaElementosAnchoColumna;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JListaElementosFormulario listaElementosFormulario;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JEtiquetaFormulario etiquetaFormulario;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JSeccionFormulario seccionFormulario;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JCampoFormulario campoFormulario;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JImagenFormulario imagenFormulario;

	public JElementoFormulario() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JLineaSeccionFormulario getLineaSeccionFormulario() {
		return this.lineaSeccionFormulario;
	}

	public void setLineaSeccionFormulario(final JLineaSeccionFormulario lineaSeccionFormulario) {
		this.lineaSeccionFormulario = lineaSeccionFormulario;
	}

	public JLiterales getAyuda() {
		return this.ayuda;
	}

	public void setAyuda(final JLiterales ayuda) {
		this.ayuda = ayuda;
	}

	public JLiterales getTexto() {
		return this.texto;
	}

	public void setTexto(final JLiterales texto) {
		this.texto = texto;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public int getNumeroColumnas() {
		return this.numeroColumnas;
	}

	public void setNumeroColumnas(final int numeroColumnas) {
		this.numeroColumnas = numeroColumnas;
	}

	public boolean isNoMostrarTexto() {
		return this.noMostrarTexto;
	}

	public void setNoMostrarTexto(final boolean noMostrarTexto) {
		this.noMostrarTexto = noMostrarTexto;
	}

	public String getAlineacionTexto() {
		return this.alineacionTexto;
	}

	public void setAlineacionTexto(final String alineacionTexto) {
		this.alineacionTexto = alineacionTexto;
	}

	public boolean isMostrarEnListaElementos() {
		return this.mostrarEnListaElementos;
	}

	public void setMostrarEnListaElementos(final boolean mostrarEnListaElementos) {
		this.mostrarEnListaElementos = mostrarEnListaElementos;
	}

	public Boolean getListaElementosAnchoColumna() {
		return this.listaElementosAnchoColumna;
	}

	public void setListaElementosAnchoColumna(final Boolean listaElementosAnchoColumna) {
		this.listaElementosAnchoColumna = listaElementosAnchoColumna;
	}

	public JListaElementosFormulario getListaElementosFormulario() {
		return this.listaElementosFormulario;
	}

	public void setListaElementosFormulario(final JListaElementosFormulario listaElementosFormulario) {
		this.listaElementosFormulario = listaElementosFormulario;
	}

	public JEtiquetaFormulario getEtiquetaFormulario() {
		return this.etiquetaFormulario;
	}

	public void setEtiquetaFormulario(final JEtiquetaFormulario etiquetaFormulario) {
		this.etiquetaFormulario = etiquetaFormulario;
	}

	public JSeccionFormulario getSeccionFormulario() {
		return this.seccionFormulario;
	}

	public void setSeccionFormulario(final JSeccionFormulario seccionFormulario) {
		this.seccionFormulario = seccionFormulario;
	}

	public JCampoFormulario getCampoFormulario() {
		return this.campoFormulario;
	}

	public void setCampoFormulario(final JCampoFormulario campoFormulario) {
		this.campoFormulario = campoFormulario;
	}

	public JImagenFormulario getImagenFormulario() {
		return this.imagenFormulario;
	}

	public void setImagenFormulario(final JImagenFormulario imagenFormulario) {
		this.imagenFormulario = imagenFormulario;
	}

}
