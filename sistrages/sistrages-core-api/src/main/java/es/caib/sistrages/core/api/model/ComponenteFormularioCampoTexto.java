package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeCampoTexto;
import es.caib.sistrages.core.api.model.types.TypeSeparadorNumero;

/**
 * Componente formulario de tipo campo texto.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ComponenteFormularioCampoTexto extends ComponenteFormularioCampo {

	private boolean oculto;

	private TypeCampoTexto tipoCampoTexto;

	private Integer normalTamanyo;

	private boolean normalMultilinea;

	private Integer normalNumeroLineas;

	private String expresionRegular;

	private Integer numeroDigitosEnteros;

	private Integer numeroDigitosDecimales;

	private TypeSeparadorNumero numeroSeparador;

	private Long numeroRangoMinimo;

	private Long numeroRangoMaximo;

	private boolean numeroConSigno;

	private boolean identNif;

	private boolean identCif;

	private boolean identNie;

	private boolean identNss;

	private boolean telefonoMovil;

	private boolean telefonoFijo;

	private boolean permiteRango;

	public ComponenteFormularioCampoTexto() {
		super();
	}

	public boolean isOculto() {
		return oculto;
	}

	public void setOculto(final boolean oculto) {
		this.oculto = oculto;
	}

	public TypeCampoTexto getTipoCampoTexto() {
		return tipoCampoTexto;
	}

	public void setTipoCampoTexto(final TypeCampoTexto tipoTexto) {
		this.tipoCampoTexto = tipoTexto;
	}

	public Integer getNormalTamanyo() {
		return normalTamanyo;
	}

	public void setNormalTamanyo(final Integer normalTamanyo) {
		this.normalTamanyo = normalTamanyo;
	}

	public boolean isNormalMultilinea() {
		return normalMultilinea;
	}

	public void setNormalMultilinea(final boolean normalMultilinea) {
		this.normalMultilinea = normalMultilinea;
	}

	public Integer getNormalNumeroLineas() {
		return normalNumeroLineas;
	}

	public void setNormalNumeroLineas(final Integer normalNumeroLineas) {
		this.normalNumeroLineas = normalNumeroLineas;
	}

	public String getExpresionRegular() {
		return expresionRegular;
	}

	public void setExpresionRegular(final String normalExpresionRegular) {
		this.expresionRegular = normalExpresionRegular;
	}

	public Integer getNumeroDigitosEnteros() {
		return numeroDigitosEnteros;
	}

	public void setNumeroDigitosEnteros(final Integer numeroDigitosEnteros) {
		this.numeroDigitosEnteros = numeroDigitosEnteros;
	}

	public Integer getNumeroDigitosDecimales() {
		return numeroDigitosDecimales;
	}

	public void setNumeroDigitosDecimales(final Integer numeroDigitosDecimales) {
		this.numeroDigitosDecimales = numeroDigitosDecimales;
	}

	public TypeSeparadorNumero getNumeroSeparador() {
		return numeroSeparador;
	}

	public void setNumeroSeparador(final TypeSeparadorNumero numeroSeparador) {
		this.numeroSeparador = numeroSeparador;
	}

	public Long getNumeroRangoMinimo() {
		return numeroRangoMinimo;
	}

	public void setNumeroRangoMinimo(final Long numeroRangoMinimo) {
		this.numeroRangoMinimo = numeroRangoMinimo;
	}

	public Long getNumeroRangoMaximo() {
		return numeroRangoMaximo;
	}

	public void setNumeroRangoMaximo(final Long numeroRangoMaximo) {
		this.numeroRangoMaximo = numeroRangoMaximo;
	}

	public boolean isNumeroConSigno() {
		return numeroConSigno;
	}

	public void setNumeroConSigno(final boolean numeroConSigno) {
		this.numeroConSigno = numeroConSigno;
	}

	public boolean isIdentNif() {
		return identNif;
	}

	public void setIdentNif(final boolean identNif) {
		this.identNif = identNif;
	}

	public boolean isIdentCif() {
		return identCif;
	}

	public void setIdentCif(final boolean identCif) {
		this.identCif = identCif;
	}

	public boolean isIdentNie() {
		return identNie;
	}

	public void setIdentNie(final boolean identNie) {
		this.identNie = identNie;
	}

	public boolean isIdentNss() {
		return identNss;
	}

	public void setIdentNss(final boolean identNss) {
		this.identNss = identNss;
	}

	public boolean isTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(final boolean telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public boolean isTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(final boolean telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public boolean isPermiteRango() {
		return permiteRango;
	}

	public void setPermiteRango(final boolean permiteRango) {
		this.permiteRango = permiteRango;
	}

}
