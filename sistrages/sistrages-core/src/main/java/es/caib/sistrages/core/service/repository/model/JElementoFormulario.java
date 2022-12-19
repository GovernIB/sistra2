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

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "FEL_AYUDA")
	private JLiteral ayuda;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
	private JCampoFormularioCaptcha captchaFormulario;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JCampoFormulario campoFormulario;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JImagenFormulario imagenFormulario;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "elementoFormulario", cascade = CascadeType.ALL)
	private JCampoFormularioSeccionReutilizable seccionReutilizableFormulario;

	public JElementoFormulario() {
		super();
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

	/**
	 * @return the captchaFormulario
	 */
	public JCampoFormularioCaptcha getCaptchaFormulario() {
		return captchaFormulario;
	}

	/**
	 * @param captchaFormulario the captchaFormulario to set
	 */
	public void setCaptchaFormulario(JCampoFormularioCaptcha captchaFormulario) {
		this.captchaFormulario = captchaFormulario;
	}

	/**
	 * @return the seccionReutilizableFormulario
	 */
	public JCampoFormularioSeccionReutilizable getSeccionReutilizableFormulario() {
		return seccionReutilizableFormulario;
	}

	/**
	 * @param seccionReutilizableFormulario the seccionReutilizableFormulario to set
	 */
	public void setSeccionReutilizableFormulario(JCampoFormularioSeccionReutilizable seccionReutilizableFormulario) {
		this.seccionReutilizableFormulario = seccionReutilizableFormulario;
	}

	public <T> ComponenteFormulario toModel(final Class<T> model) {
		ComponenteFormulario newModel = null;

		try {
			newModel = (ComponenteFormulario) model.newInstance();

			newModel.setCodigo(codigo);
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
			final JLineaFormulario pJLinea, final boolean isTipoSeccion, final String identificadorSeccion) {
		final JElementoFormulario jModel = new JElementoFormulario();
		if (isTipoSeccion) {
			jModel.setIdentificador("SRE_"+identificadorSeccion+"_" + System.currentTimeMillis());
		} else {
			jModel.setIdentificador("ID_" + System.currentTimeMillis());
		}
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
			texto.add(new Traduccion("en", "Field"));
			jModel.setNumeroColumnas(1);
			jModel.setNoMostrarTexto(false);
			jModel.setTexto(JLiteral.fromModel(texto));
			break;
		case CAMPO_OCULTO:
			jModel.setNumeroColumnas(0);
			jModel.setNoMostrarTexto(true);
			break;
		case ETIQUETA:
			texto.add(new Traduccion("es", "Mensaje"));
			texto.add(new Traduccion("ca", "Missatge"));
			texto.add(new Traduccion("en", "Message"));
			jModel.setNumeroColumnas(ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA);
			jModel.setNoMostrarTexto(false);
			jModel.setTexto(JLiteral.fromModel(texto));
			break;
		case SECCION:
			texto.add(new Traduccion("es", "Sección"));
			texto.add(new Traduccion("ca", "Secció"));
			texto.add(new Traduccion("en", "Section"));
			jModel.setNumeroColumnas(ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA);
			jModel.setNoMostrarTexto(false);
			jModel.setTexto(JLiteral.fromModel(texto));
			break;
		case SECCION_REUTILIZABLE:
			break;
		case CHECKBOX:
			texto.add(new Traduccion("es", "Casilla de verificación"));
			texto.add(new Traduccion("ca", "Casella de verificació"));
			texto.add(new Traduccion("en", "Checkbox"));
			jModel.setNumeroColumnas(1);
			jModel.setNoMostrarTexto(false);
			jModel.setTexto(JLiteral.fromModel(texto));
			break;
		case SELECTOR:
			texto.add(new Traduccion("es", "Selector"));
			texto.add(new Traduccion("ca", "Selector"));
			texto.add(new Traduccion("en", "Selector"));
			jModel.setNumeroColumnas(1);
			jModel.setNoMostrarTexto(false);
			jModel.setTexto(JLiteral.fromModel(texto));
			break;
		case CAPTCHA:
			texto.add(new Traduccion("es", "Incluye el texto de la imagen"));
			texto.add(new Traduccion("ca", "Inclou el text de la imatge"));
			texto.add(new Traduccion("en", "Includes the text of the image"));
			jModel.setNumeroColumnas(1);
			jModel.setNoMostrarTexto(false);
			jModel.setTexto(JLiteral.fromModel(texto));
			break;
		default:
			jModel.setNoMostrarTexto(true);
			jModel.setNumeroColumnas(1);
		}

		return jModel;
	}

	public static JElementoFormulario clonar(final JElementoFormulario elemento,
			final JLineaFormulario jlineaFormulario, final JPaginaFormulario jpagina, final boolean cambioArea) {
		JElementoFormulario jelemento = null;
		if (elemento != null) {
			jelemento = new JElementoFormulario();
			jelemento.setLineaFormulario(jlineaFormulario);
			jelemento.setAyuda(JLiteral.clonar(elemento.getAyuda()));
			jelemento.setTexto(JLiteral.clonar(elemento.getTexto()));
			jelemento.setIdentificador(elemento.getIdentificador());
			jelemento.setTipo(elemento.getTipo());
			jelemento.setOrden(elemento.getOrden());
			jelemento.setNumeroColumnas(elemento.getNumeroColumnas());
			jelemento.setNoMostrarTexto(elemento.isNoMostrarTexto());
			jelemento.setAlineacionTexto(elemento.getAlineacionTexto());
			jelemento.setMostrarEnListaElementos(elemento.isMostrarEnListaElementos());
			jelemento.setListaElementosAnchoColumna(elemento.getListaElementosAnchoColumna());
			jelemento.setListaElementosFormulario(
					JListaElementosFormulario.clonar(elemento.getListaElementosFormulario(), jelemento, jpagina));
			jelemento.setEtiquetaFormulario(JEtiquetaFormulario.clonar(elemento.getEtiquetaFormulario(), jelemento));
			jelemento.setSeccionFormulario(JSeccionFormulario.clonar(elemento.getSeccionFormulario(), jelemento));
			jelemento.setCaptchaFormulario(JCampoFormularioCaptcha.clonar(elemento.getCaptchaFormulario(), jelemento));
			jelemento.setCampoFormulario(JCampoFormulario.clonar(elemento.getCampoFormulario(), jelemento, cambioArea));
			jelemento.setImagenFormulario(JImagenFormulario.clonar(elemento.getImagenFormulario(), jelemento));
			jelemento.setSeccionReutilizableFormulario(JCampoFormularioSeccionReutilizable.clonar(elemento.getSeccionReutilizableFormulario(), jelemento));
		}
		return jelemento;
	}

}
