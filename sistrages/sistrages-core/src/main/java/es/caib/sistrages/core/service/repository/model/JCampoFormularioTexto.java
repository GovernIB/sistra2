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

	@Column(name = "CTX_IDENIF", nullable = false, precision = 1, scale = 0)
	private boolean identNif;

	@Column(name = "CTX_IDECIF", nullable = false, precision = 1, scale = 0)
	private boolean identCif;

	@Column(name = "CTX_IDENIE", nullable = false, precision = 1, scale = 0)
	private boolean identNie;

	@Column(name = "CTX_IDENSS", nullable = false, precision = 1, scale = 0)
	private boolean identNss;

	@Column(name = "CTX_TELMOV", nullable = false, precision = 1, scale = 0)
	private boolean telefonoMovil;

	@Column(name = "CTX_TELFIJ", nullable = false, precision = 1, scale = 0)
	private boolean telefonoFijo;

	@Column(name = "CTX_PERRAN", nullable = false, precision = 1, scale = 0)
	private boolean permiteRango;

	public JCampoFormularioTexto() {
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

	public boolean isIdentCif() {
		return this.identCif;
	}

	public void setIdentCif(final boolean identCif) {
		this.identCif = identCif;
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
				campoTexto.setIdentNif(identNif);
				campoTexto.setIdentCif(identCif);
				campoTexto.setIdentNie(identNie);
				campoTexto.setIdentNss(identNss);
				campoTexto.setTelefonoMovil(telefonoMovil);
				campoTexto.setTelefonoFijo(telefonoFijo);
				campoTexto.setPermiteRango(permiteRango);
			}

		}

		return campoTexto;
	}

	public static JCampoFormularioTexto createDefault(final int pOrden, final JLineaFormulario pJLinea) {
		final JCampoFormularioTexto jModel = new JCampoFormularioTexto();
		jModel.setOculto(false);
		jModel.setTipo(TypeCampoTexto.NORMAL.name());
		jModel.setNormalMultilinea(false);
		jModel.setNumeroConSigno(false);
		jModel.setIdentNif(false);
		jModel.setIdentCif(false);
		jModel.setIdentNie(false);
		jModel.setIdentNss(false);
		jModel.setTelefonoFijo(false);
		jModel.setTelefonoMovil(false);
		jModel.setPermiteRango(false);
		jModel.setCampoFormulario(JCampoFormulario.createDefault(TypeObjetoFormulario.CAMPO_TEXTO, pOrden, pJLinea));
		return jModel;
	}

}
