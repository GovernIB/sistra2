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

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;
import es.caib.sistrages.core.api.model.types.TypeAlineacionTexto;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JElementoFormulario
 */
@Entity
@Table(name = "STG_FORELE")
public class JElementoFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORELE_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORELE_SEQ", sequenceName = "STG_FORELE_SEQ")
	@Column(name = "FEL_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEL_CODFLS", nullable = false)
	private JLineaFormulario lineaFormulario;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FEL_AYUDA")
	private JLiteral ayuda;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FEL_TEXTO")
	private JLiteral texto;

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

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JListaElementosFormulario listaElementosFormulario;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JEtiquetaFormulario etiquetaFormulario;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JSeccionFormulario seccionFormulario;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JCampoFormulario campoFormulario;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JImagenFormulario imagenFormulario;

	public JElementoFormulario() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JLineaFormulario getLineaFormulario() {
		return this.lineaFormulario;
	}

	public void setLineaFormulario(final JLineaFormulario lineaFormulario) {
		this.lineaFormulario = lineaFormulario;
	}

	public JLiteral getAyuda() {
		return this.ayuda;
	}

	public void setAyuda(final JLiteral ayuda) {
		this.ayuda = ayuda;
	}

	public JLiteral getTexto() {
		return this.texto;
	}

	public void setTexto(final JLiteral texto) {
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

	public <T> ComponenteFormulario toModel(final Class<T> model) {
		ComponenteFormulario newModel = null;

		try {
			newModel = (ComponenteFormulario) model.newInstance();

			newModel.setId(codigo);
			newModel.setIdComponente(identificador);
			newModel.setTipo(TypeObjetoFormulario.fromString(tipo));
			newModel.setOrden(orden);
			newModel.setNumColumnas(numeroColumnas);

			if (texto != null) {
				newModel.setTexto(texto.toModel());
			}

			if (ayuda != null) {
				newModel.setAyuda(ayuda.toModel());
			}

			newModel.setNoMostrarTexto(noMostrarTexto);
			newModel.setAlineacionTexto(TypeAlineacionTexto.fromString(alineacionTexto));
		} catch (InstantiationException | IllegalAccessException e) {
			newModel = null;
		}

		return newModel;

	}

	public static JElementoFormulario createDefault(final TypeObjetoFormulario pTipoObjeto, final int pOrden,
			final JLineaFormulario pJLinea) {
		final JElementoFormulario jModel = new JElementoFormulario();
		jModel.setIdentificador("DEFAULT_" + System.currentTimeMillis());
		jModel.setTipo(pTipoObjeto.toString());
		jModel.setOrden(pOrden);

		jModel.setAlineacionTexto(TypeAlineacionTexto.IZQUIERDA.toString());
		jModel.setMostrarEnListaElementos(false);
		pJLinea.addElemento(jModel);

		// ponemos texto segun componente
		final Literal texto = new Literal();
		switch (pTipoObjeto) {
		case CAMPO_TEXTO:
			texto.add(new Traduccion("es", "Campo"));
			texto.add(new Traduccion("ca", "Camp"));
			jModel.setNumeroColumnas(1);
			jModel.setNoMostrarTexto(false);
			break;
		case ETIQUETA:
			texto.add(new Traduccion("es", "Mensaje"));
			texto.add(new Traduccion("ca", "Missatge"));
			jModel.setNumeroColumnas(ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA);
			jModel.setNoMostrarTexto(false);
			break;
		case SECCION:
			texto.add(new Traduccion("es", "Sección"));
			texto.add(new Traduccion("ca", "Secció"));
			jModel.setNumeroColumnas(ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA);
			jModel.setNoMostrarTexto(false);
			break;
		case CHECKBOX:
			texto.add(new Traduccion("es", "Casilla de verificación"));
			texto.add(new Traduccion("ca", "Casella de verificació"));
			jModel.setNumeroColumnas(1);
			jModel.setNoMostrarTexto(false);
			break;
		default:
			jModel.setNoMostrarTexto(true);
			jModel.setNumeroColumnas(1);
		}
		jModel.setTexto(JLiteral.fromModel(texto));
		return jModel;
	}

}
