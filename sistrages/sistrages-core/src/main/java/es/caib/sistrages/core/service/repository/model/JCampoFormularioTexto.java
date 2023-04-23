package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.types.TypeCampoTexto;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypeSeparadorNumero;

/**
 * JCampoFormularioTexto
 */
@Entity
@Table(name = "STG_FORCTX")
public class JCampoFormularioTexto implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CTX_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "CTX_CODIGO")
	private JCampoFormulario campoFormulario;

	@Column(name = "CTX_OCULTO", nullable = false, precision = 1, scale = 0)
	private boolean oculto;

	@Column(name = "CTX_TIPO", nullable = false, length = 10)
	private String tipo;

	@Column(name = "CTX_NORTAM", precision = 4, scale = 0)
	private Integer normalTamanyo;

	@Column(name = "CTX_NORMUL", nullable = false, precision = 1, scale = 0)
	private boolean normalMultilinea;

	@Column(name = "CTX_NORLIN", precision = 3, scale = 0)
	private Integer normalNumeroLineas;

	@Column(name = "CTX_NOREXP", length = 4000)
	private String normalExpresionRegular;

	@Column(name = "CTX_NUMENT", precision = 2, scale = 0)
	private Integer numeroDigitosEnteros;

	@Column(name = "CTX_NUMDEC", precision = 1, scale = 0)
	private Integer numeroDigitosDecimales;

	@Column(name = "CTX_NUMSEP", length = 2)
	private String numeroSeparador;

	@Column(name = "CTX_NUMRMI", precision = 10, scale = 0)
	private Long numeroRangoMinimo;

	@Column(name = "CTX_NUMRMX", precision = 10, scale = 0)
	private Long numeroRangoMaximo;

	@Column(name = "CTX_NUMSIG", nullable = false, precision = 1, scale = 0)
	private boolean numeroConSigno;

	@Column(name = "CTX_IDEDNI", nullable = false, precision = 1, scale = 0)
	private boolean identDni;

	@Column(name = "CTX_IDENIE", nullable = false, precision = 1, scale = 0)
	private boolean identNie;

	@Column(name = "CTX_IDENIO", nullable = false, precision = 1, scale = 0)
	private boolean identNifOtros;

	@Column(name = "CTX_IDENIJ", nullable = false, precision = 1, scale = 0)
	private boolean identNif;

	@Column(name = "CTX_IDENSS", nullable = false, precision = 1, scale = 0)
	private boolean identNss;

	@Column(name = "CTX_TELMOV", nullable = false, precision = 1, scale = 0)
	private boolean telefonoMovil;

	@Column(name = "CTX_TELFIJ", nullable = false, precision = 1, scale = 0)
	private boolean telefonoFijo;

	@Column(name = "CTX_PERRAN", nullable = false, precision = 1, scale = 0)
	private boolean permiteRango;

	@Column(name = "CTX_NORMAY", nullable = false, precision = 1, scale = 0)
	private boolean forzarMayusculas;

	@Column(name = "CTX_PREVPEG", nullable = false, precision = 1, scale = 0)
	private boolean prevenirPegar;

	public JCampoFormularioTexto() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JCampoFormulario getCampoFormulario() {
		return this.campoFormulario;
	}

	public void setCampoFormulario(final JCampoFormulario campoFormulario) {
		this.campoFormulario = campoFormulario;
	}

	public boolean isOculto() {
		return this.oculto;
	}

	public void setOculto(final boolean oculto) {
		this.oculto = oculto;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public Integer getNormalTamanyo() {
		return this.normalTamanyo;
	}

	public void setNormalTamanyo(final Integer normalTamanyo) {
		this.normalTamanyo = normalTamanyo;
	}

	public boolean isNormalMultilinea() {
		return this.normalMultilinea;
	}

	public void setNormalMultilinea(final boolean normalMultilinea) {
		this.normalMultilinea = normalMultilinea;
	}

	public Integer getNormalNumeroLineas() {
		return this.normalNumeroLineas;
	}

	public void setNormalNumeroLineas(final Integer normalNumeroLineas) {
		this.normalNumeroLineas = normalNumeroLineas;
	}

	public String getNormalExpresionRegular() {
		return this.normalExpresionRegular;
	}

	public void setNormalExpresionRegular(final String normalExpresionRegular) {
		this.normalExpresionRegular = normalExpresionRegular;
	}

	public Integer getNumeroDigitosEnteros() {
		return this.numeroDigitosEnteros;
	}

	public void setNumeroDigitosEnteros(final Integer numeroDigitosEnteros) {
		this.numeroDigitosEnteros = numeroDigitosEnteros;
	}

	public Integer getNumeroDigitosDecimales() {
		return this.numeroDigitosDecimales;
	}

	public void setNumeroDigitosDecimales(final Integer numeroDigitosDecimales) {
		this.numeroDigitosDecimales = numeroDigitosDecimales;
	}

	public String getNumeroSeparador() {
		return this.numeroSeparador;
	}

	public void setNumeroSeparador(final String numeroSeparador) {
		this.numeroSeparador = numeroSeparador;
	}

	public Long getNumeroRangoMinimo() {
		return this.numeroRangoMinimo;
	}

	public void setNumeroRangoMinimo(final Long numeroRangoMinimo) {
		this.numeroRangoMinimo = numeroRangoMinimo;
	}

	public Long getNumeroRangoMaximo() {
		return this.numeroRangoMaximo;
	}

	public void setNumeroRangoMaximo(final Long numeroRangoMaximo) {
		this.numeroRangoMaximo = numeroRangoMaximo;
	}

	public boolean isNumeroConSigno() {
		return this.numeroConSigno;
	}

	public void setNumeroConSigno(final boolean numeroConSigno) {
		this.numeroConSigno = numeroConSigno;
	}

	public boolean isIdentNif() {
		return this.identNif;
	}

	public void setIdentNif(final boolean identNif) {
		this.identNif = identNif;
	}

	public boolean isIdentNie() {
		return this.identNie;
	}

	public void setIdentNie(final boolean identNie) {
		this.identNie = identNie;
	}

	public boolean isIdentNss() {
		return this.identNss;
	}

	public void setIdentNss(final boolean identNss) {
		this.identNss = identNss;
	}

	/**
	 * @return the identDni
	 */
	public boolean isIdentDni() {
		return identDni;
	}

	/**
	 * @param identDni the identDni to set
	 */
	public void setIdentDni(final boolean identDni) {
		this.identDni = identDni;
	}

	/**
	 * @return the identNifOtros
	 */
	public boolean isIdentNifOtros() {
		return identNifOtros;
	}

	/**
	 * @param identNifOtros the identNifOtros to set
	 */
	public void setIdentNifOtros(final boolean identNifOtros) {
		this.identNifOtros = identNifOtros;
	}

	public boolean isTelefonoMovil() {
		return this.telefonoMovil;
	}

	public void setTelefonoMovil(final boolean telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public boolean isTelefonoFijo() {
		return this.telefonoFijo;
	}

	public void setTelefonoFijo(final boolean telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public boolean isPermiteRango() {
		return this.permiteRango;
	}

	public void setPermiteRango(final boolean permiteRango) {
		this.permiteRango = permiteRango;
	}

	public boolean isForzarMayusculas() {
		return forzarMayusculas;
	}

	public void setForzarMayusculas(final boolean forzarMayusculas) {
		this.forzarMayusculas = forzarMayusculas;
	}

	public boolean isPrevenirPegar() {
		return prevenirPegar;
	}

	public void setPrevenirPegar(boolean prevenirPegar) {
		this.prevenirPegar = prevenirPegar;
	}

	public ComponenteFormularioCampoTexto toModel() {
		ComponenteFormularioCampoTexto campoTexto = null;

		if (campoFormulario != null) {
			campoTexto = (ComponenteFormularioCampoTexto) campoFormulario.toModel(ComponenteFormularioCampoTexto.class);
			if (campoTexto != null) {
				campoTexto.setOculto(oculto);
				campoTexto.setTipoCampoTexto(TypeCampoTexto.valueOf(tipo));
				campoTexto.setNormalTamanyo(normalTamanyo);
				campoTexto.setNormalMultilinea(normalMultilinea);
				campoTexto.setNormalNumeroLineas(normalNumeroLineas);
				campoTexto.setExpresionRegular(normalExpresionRegular);
				campoTexto.setNumeroDigitosEnteros(numeroDigitosEnteros);
				campoTexto.setNumeroDigitosDecimales(numeroDigitosDecimales);
				if (numeroSeparador != null) {
					campoTexto.setNumeroSeparador(TypeSeparadorNumero.fromString(numeroSeparador));
				}
				campoTexto.setNumeroRangoMinimo(numeroRangoMinimo);
				campoTexto.setNumeroRangoMaximo(numeroRangoMaximo);
				campoTexto.setNumeroConSigno(numeroConSigno);
				campoTexto.setIdentDni(identDni);
				campoTexto.setIdentNie(identNie);
				campoTexto.setIdentNifOtros(identNifOtros);
				campoTexto.setIdentNif(identNif);
				campoTexto.setIdentNss(identNss);
				campoTexto.setTelefonoMovil(telefonoMovil);
				campoTexto.setTelefonoFijo(telefonoFijo);
				campoTexto.setPermiteRango(permiteRango);
				campoTexto.setForzarMayusculas(forzarMayusculas);
				campoTexto.setPrevenirPegar(prevenirPegar);
			}

		}

		return campoTexto;
	}

	public static JCampoFormularioTexto createDefault(final int pOrden, final JLineaFormulario pJLinea,
			final boolean isTipoSeccion, final String identificadorSeccion) {
		final JCampoFormularioTexto jModel = new JCampoFormularioTexto();
		jModel.setOculto(false);
		jModel.setTipo(TypeCampoTexto.NORMAL.name());
		jModel.setNormalMultilinea(false);
		jModel.setNumeroConSigno(false);
		jModel.setIdentDni(false);
		jModel.setIdentNie(false);
		jModel.setIdentNifOtros(false);
		jModel.setIdentNif(false);
		jModel.setIdentNss(false);
		jModel.setTelefonoFijo(false);
		jModel.setTelefonoMovil(false);
		jModel.setPermiteRango(false);
		jModel.setCampoFormulario(JCampoFormulario.createDefault(TypeObjetoFormulario.CAMPO_TEXTO, pOrden, pJLinea,
				isTipoSeccion, identificadorSeccion));
		jModel.setNumeroSeparador("PC"); // Punto y coma
		jModel.setNormalTamanyo(50);
		jModel.setForzarMayusculas(false);
		jModel.setPrevenirPegar(false);
		return jModel;
	}

	public static JCampoFormularioTexto clonar(final JCampoFormularioTexto campoFormularioTexto,
			final JCampoFormulario jcampo) {
		JCampoFormularioTexto jcampoTexto = null;
		if (campoFormularioTexto != null) {
			jcampoTexto = new JCampoFormularioTexto();
			jcampoTexto.setCampoFormulario(jcampo);
			jcampoTexto.setIdentDni(campoFormularioTexto.isIdentDni());
			jcampoTexto.setIdentNie(campoFormularioTexto.isIdentNie());
			jcampoTexto.setIdentNifOtros(campoFormularioTexto.isIdentNifOtros());
			jcampoTexto.setIdentNif(campoFormularioTexto.isIdentNif());
			jcampoTexto.setIdentNss(campoFormularioTexto.isIdentNss());
			jcampoTexto.setNormalExpresionRegular(campoFormularioTexto.getNormalExpresionRegular());
			jcampoTexto.setNormalMultilinea(campoFormularioTexto.isNormalMultilinea());
			jcampoTexto.setNormalNumeroLineas(campoFormularioTexto.getNormalNumeroLineas());
			jcampoTexto.setNormalTamanyo(campoFormularioTexto.getNormalTamanyo());
			jcampoTexto.setNumeroConSigno(campoFormularioTexto.isNumeroConSigno());
			jcampoTexto.setNumeroDigitosDecimales(campoFormularioTexto.getNumeroDigitosDecimales());
			jcampoTexto.setNumeroDigitosEnteros(campoFormularioTexto.getNumeroDigitosEnteros());
			jcampoTexto.setNumeroRangoMaximo(campoFormularioTexto.getNumeroRangoMaximo());
			jcampoTexto.setNumeroRangoMinimo(campoFormularioTexto.getNumeroRangoMinimo());
			jcampoTexto.setNumeroSeparador(campoFormularioTexto.getNumeroSeparador());
			jcampoTexto.setOculto(campoFormularioTexto.isOculto());
			jcampoTexto.setPermiteRango(campoFormularioTexto.isPermiteRango());
			jcampoTexto.setTelefonoFijo(campoFormularioTexto.isTelefonoFijo());
			jcampoTexto.setTelefonoMovil(campoFormularioTexto.isTelefonoMovil());
			jcampoTexto.setTipo(campoFormularioTexto.getTipo());
			jcampoTexto.setForzarMayusculas(campoFormularioTexto.isForzarMayusculas());
			jcampoTexto.setPrevenirPegar(campoFormularioTexto.isPrevenirPegar());
		}
		return jcampoTexto;
	}
}
