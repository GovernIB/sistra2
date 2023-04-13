package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeCampoTexto;
import es.caib.sistrages.core.api.model.types.TypeSeparadorNumero;

/**
 * La clase ComponenteFormularioCampoTexto.
 */

public final class ComponenteFormularioCampoTexto extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * oculto.
	 */
	private boolean oculto;

	/**
	 * oculto.
	 */
	private boolean prevenirPegar;

	/**
	 * tipo campo texto.
	 */
	private TypeCampoTexto tipoCampoTexto;

	/**
	 * normal tamanyo.
	 */
	private Integer normalTamanyo;

	/**
	 * normal multilinea.
	 */
	private boolean normalMultilinea;

	/**
	 * normal numero lineas.
	 */
	private Integer normalNumeroLineas;

	/**
	 * expresion regular.
	 */
	private String expresionRegular;

	/**
	 * numero digitos enteros.
	 */
	private Integer numeroDigitosEnteros;

	/**
	 * numero digitos decimales.
	 */
	private Integer numeroDigitosDecimales;

	/**
	 * numero separador.
	 */
	private TypeSeparadorNumero numeroSeparador;

	/**
	 * numero rango minimo.
	 */
	private Long numeroRangoMinimo;

	/**
	 * numero rango maximo.
	 */
	private Long numeroRangoMaximo;

	/**
	 * numero con signo.
	 */
	private boolean numeroConSigno;

	/**
	 * ident dni.
	 */
	private boolean identDni;

	/**
	 * ident nie.
	 */
	private boolean identNie;

	/**
	 * ident nif.
	 */
	private boolean identNifOtros;

	/**
	 * ident nif.
	 */
	private boolean identNif;

	/**
	 * ident nss.
	 */
	private boolean identNss;

	/**
	 * telefono movil.
	 */
	private boolean telefonoMovil;

	/**
	 * telefono fijo.
	 */
	private boolean telefonoFijo;

	/**
	 * permite rango.
	 */
	private boolean permiteRango;

	/**
	 * forzar mayusculas.
	 */
	private boolean forzarMayusculas;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoTexto.
	 */
	public ComponenteFormularioCampoTexto() {
		super();
	}

	/**
	 * Verifica si es oculto.
	 *
	 * @return true, si es oculto
	 */
	public boolean isOculto() {
		return oculto;
	}

	/**
	 * Establece el valor de oculto.
	 *
	 * @param oculto el nuevo valor de oculto
	 */
	public void setOculto(final boolean oculto) {
		this.oculto = oculto;
	}

	/**
	 * Obtiene el valor de tipoCampoTexto.
	 *
	 * @return el valor de tipoCampoTexto
	 */
	public TypeCampoTexto getTipoCampoTexto() {
		return tipoCampoTexto;
	}

	/**
	 * Establece el valor de tipoCampoTexto.
	 *
	 * @param tipoTexto el nuevo valor de tipoCampoTexto
	 */
	public void setTipoCampoTexto(final TypeCampoTexto tipoTexto) {
		this.tipoCampoTexto = tipoTexto;
	}

	/**
	 * Obtiene el valor de normalTamanyo.
	 *
	 * @return el valor de normalTamanyo
	 */
	public Integer getNormalTamanyo() {
		return normalTamanyo;
	}

	/**
	 * Establece el valor de normalTamanyo.
	 *
	 * @param normalTamanyo el nuevo valor de normalTamanyo
	 */
	public void setNormalTamanyo(final Integer normalTamanyo) {
		this.normalTamanyo = normalTamanyo;
	}

	/**
	 * Verifica si es normal multilinea.
	 *
	 * @return true, si es normal multilinea
	 */
	public boolean isNormalMultilinea() {
		return normalMultilinea;
	}

	/**
	 * Establece el valor de normalMultilinea.
	 *
	 * @param normalMultilinea el nuevo valor de normalMultilinea
	 */
	public void setNormalMultilinea(final boolean normalMultilinea) {
		this.normalMultilinea = normalMultilinea;
	}

	/**
	 * Obtiene el valor de normalNumeroLineas.
	 *
	 * @return el valor de normalNumeroLineas
	 */
	public Integer getNormalNumeroLineas() {
		return normalNumeroLineas;
	}

	/**
	 * Establece el valor de normalNumeroLineas.
	 *
	 * @param normalNumeroLineas el nuevo valor de normalNumeroLineas
	 */
	public void setNormalNumeroLineas(final Integer normalNumeroLineas) {
		this.normalNumeroLineas = normalNumeroLineas;
	}

	/**
	 * Obtiene el valor de expresionRegular.
	 *
	 * @return el valor de expresionRegular
	 */
	public String getExpresionRegular() {
		return expresionRegular;
	}

	/**
	 * Establece el valor de expresionRegular.
	 *
	 * @param normalExpresionRegular el nuevo valor de expresionRegular
	 */
	public void setExpresionRegular(final String normalExpresionRegular) {
		this.expresionRegular = normalExpresionRegular;
	}

	/**
	 * Obtiene el valor de numeroDigitosEnteros.
	 *
	 * @return el valor de numeroDigitosEnteros
	 */
	public Integer getNumeroDigitosEnteros() {
		return numeroDigitosEnteros;
	}

	/**
	 * Establece el valor de numeroDigitosEnteros.
	 *
	 * @param numeroDigitosEnteros el nuevo valor de numeroDigitosEnteros
	 */
	public void setNumeroDigitosEnteros(final Integer numeroDigitosEnteros) {
		this.numeroDigitosEnteros = numeroDigitosEnteros;
	}

	/**
	 * Obtiene el valor de numeroDigitosDecimales.
	 *
	 * @return el valor de numeroDigitosDecimales
	 */
	public Integer getNumeroDigitosDecimales() {
		return numeroDigitosDecimales;
	}

	/**
	 * Establece el valor de numeroDigitosDecimales.
	 *
	 * @param numeroDigitosDecimales el nuevo valor de numeroDigitosDecimales
	 */
	public void setNumeroDigitosDecimales(final Integer numeroDigitosDecimales) {
		this.numeroDigitosDecimales = numeroDigitosDecimales;
	}

	/**
	 * Obtiene el valor de numeroSeparador.
	 *
	 * @return el valor de numeroSeparador
	 */
	public TypeSeparadorNumero getNumeroSeparador() {
		return numeroSeparador;
	}

	/**
	 * Establece el valor de numeroSeparador.
	 *
	 * @param numeroSeparador el nuevo valor de numeroSeparador
	 */
	public void setNumeroSeparador(final TypeSeparadorNumero numeroSeparador) {
		this.numeroSeparador = numeroSeparador;
	}

	/**
	 * Obtiene el valor de numeroRangoMinimo.
	 *
	 * @return el valor de numeroRangoMinimo
	 */
	public Long getNumeroRangoMinimo() {
		return numeroRangoMinimo;
	}

	/**
	 * Establece el valor de numeroRangoMinimo.
	 *
	 * @param numeroRangoMinimo el nuevo valor de numeroRangoMinimo
	 */
	public void setNumeroRangoMinimo(final Long numeroRangoMinimo) {
		this.numeroRangoMinimo = numeroRangoMinimo;
	}

	/**
	 * Obtiene el valor de numeroRangoMaximo.
	 *
	 * @return el valor de numeroRangoMaximo
	 */
	public Long getNumeroRangoMaximo() {
		return numeroRangoMaximo;
	}

	/**
	 * Establece el valor de numeroRangoMaximo.
	 *
	 * @param numeroRangoMaximo el nuevo valor de numeroRangoMaximo
	 */
	public void setNumeroRangoMaximo(final Long numeroRangoMaximo) {
		this.numeroRangoMaximo = numeroRangoMaximo;
	}

	/**
	 * Verifica si es numero con signo.
	 *
	 * @return true, si es numero con signo
	 */
	public boolean isNumeroConSigno() {
		return numeroConSigno;
	}

	/**
	 * Establece el valor de numeroConSigno.
	 *
	 * @param numeroConSigno el nuevo valor de numeroConSigno
	 */
	public void setNumeroConSigno(final boolean numeroConSigno) {
		this.numeroConSigno = numeroConSigno;
	}

	/**
	 * Verifica si es ident nif.
	 *
	 * @return true, si es ident nif
	 */
	public boolean isIdentNif() {
		return identNif;
	}

	/**
	 * Establece el valor de identNif.
	 *
	 * @param identNif el nuevo valor de identNif
	 */
	public void setIdentNif(final boolean identNif) {
		this.identNif = identNif;
	}

	/**
	 * Verifica si es ident nie.
	 *
	 * @return true, si es ident nie
	 */
	public boolean isIdentNie() {
		return identNie;
	}

	/**
	 * Establece el valor de identNie.
	 *
	 * @param identNie el nuevo valor de identNie
	 */
	public void setIdentNie(final boolean identNie) {
		this.identNie = identNie;
	}

	/**
	 * Verifica si es ident nss.
	 *
	 * @return true, si es ident nss
	 */
	public boolean isIdentNss() {
		return identNss;
	}

	/**
	 * Establece el valor de identNss.
	 *
	 * @param identNss el nuevo valor de identNss
	 */
	public void setIdentNss(final boolean identNss) {
		this.identNss = identNss;
	}

	/**
	 * Verifica si es telefono movil.
	 *
	 * @return true, si es telefono movil
	 */
	public boolean isTelefonoMovil() {
		return telefonoMovil;
	}

	/**
	 * Establece el valor de telefonoMovil.
	 *
	 * @param telefonoMovil el nuevo valor de telefonoMovil
	 */
	public void setTelefonoMovil(final boolean telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	/**
	 * Verifica si es telefono fijo.
	 *
	 * @return true, si es telefono fijo
	 */
	public boolean isTelefonoFijo() {
		return telefonoFijo;
	}

	/**
	 * Establece el valor de telefonoFijo.
	 *
	 * @param telefonoFijo el nuevo valor de telefonoFijo
	 */
	public void setTelefonoFijo(final boolean telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	/**
	 * Verifica si es permite rango.
	 *
	 * @return true, si es permite rango
	 */
	public boolean isPermiteRango() {
		return permiteRango;
	}

	/**
	 * Establece el valor de permiteRango.
	 *
	 * @param permiteRango el nuevo valor de permiteRango
	 */
	public void setPermiteRango(final boolean permiteRango) {
		this.permiteRango = permiteRango;
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

	public boolean isForzarMayusculas() {
		return forzarMayusculas;
	}

	public void setForzarMayusculas(final boolean forzarMayusculas) {
		this.forzarMayusculas = forzarMayusculas;
	}

	/**
	 * @return the prevenirPegar
	 */
	public final boolean isPrevenirPegar() {
		return prevenirPegar;
	}

	/**
	 * @param prevenirPegar the prevenirPegar to set
	 */
	public final void setPrevenirPegar(boolean prevenirPegar) {
		this.prevenirPegar = prevenirPegar;
	}

	@Override
	public String toString() {
		return toString("", "ca");
	}

	/**
	 * Método to string
	 * 
	 * @param tabulacion Indica el texto anterior de la linea para que haya
	 *                   tabulacion.
	 * @return El texto
	 */
	public String toString(String tabulacion, String idioma) {
		StringBuilder texto = new StringBuilder(tabulacion + "ComponenteFormularioCampoTexto. ");
		if (this.getIdComponente() != null) {
			texto.append(tabulacion + "\t IdComponente:" + this.getIdComponente() + "\n");
		}
		if (this.getTipo() != null) {
			texto.append(tabulacion + "\t Tipus:" + this.getTipo() + "\n");
		}
		texto.append(tabulacion + "\t Ordre:" + this.getOrden() + "\n");
		texto.append(tabulacion + "\t NumColumnas:" + this.getNumColumnas() + "\n");
		texto.append(tabulacion + "\t NoMostrarText:" + this.isNoMostrarTexto() + "\n");
		if (this.getAlineacionTexto() != null) {
			texto.append(tabulacion + "\t AlineacioText:" + this.getAlineacionTexto() + "\n");
		}
		texto.append(tabulacion + "\t TipoSeccionReutilizable:" + this.isTipoSeccionReutilizable() + "\n");
		if (this.getIdFormSeccion() != null) {
			texto.append(tabulacion + "\t IdFormSeccion:" + this.getIdFormSeccion() + "\n");
		}

		if (this.getTexto() != null) {
			texto.append(tabulacion + "\t Text: \n");
			texto.append(this.getTexto().toString(tabulacion, idioma) + "\n");
		}
		if (this.getAyuda() != null) {
			texto.append(tabulacion + "\t Ajuda: \n");
			texto.append(this.getAyuda().toString(tabulacion, idioma) + "\n");
		}
		texto.append(tabulacion + "\t Oculto:" + oculto + "\n");
		texto.append(tabulacion + "\t TipusCampoText:" + tipoCampoTexto + "\n");
		if (normalTamanyo != null) {
			texto.append(tabulacion + "\t NormalTamanyo:" + normalTamanyo + "\n");
		}
		texto.append(tabulacion + "\t NormalMultilinea:" + normalMultilinea + "\n");
		if (normalNumeroLineas != null) {
			texto.append(tabulacion + "\t NormalNumeroLineas:" + normalNumeroLineas + "\n");
		}
		if (expresionRegular != null) {
			texto.append(tabulacion + "\t ExpresionRegular:" + expresionRegular + "\n");
		}
		if (numeroDigitosEnteros != null) {
			texto.append(tabulacion + "\t NumeroDigitosEnteros:" + numeroDigitosEnteros + "\n");
		}
		if (numeroSeparador != null) {
			texto.append(tabulacion + "\t NumeroSeparador:" + numeroSeparador + "\n");
		}
		if (numeroRangoMinimo != null) {
			texto.append(tabulacion + "\t NumeroRangoMinimo:" + numeroRangoMinimo + "\n");
		}
		if (numeroRangoMaximo != null) {
			texto.append(tabulacion + "\t NumeroRangoMaximo:" + numeroRangoMaximo + "\n");
		}
		texto.append(tabulacion + "\t NumeroConSigno:" + numeroConSigno + "\n");
		texto.append(tabulacion + "\t IdentDni:" + identDni + "\n");
		texto.append(tabulacion + "\t IdentNie:" + identNie + "\n");
		texto.append(tabulacion + "\t IdentNifOtros:" + identNifOtros + "\n");
		texto.append(tabulacion + "\t IdentNif:" + identNif + "\n");
		texto.append(tabulacion + "\t IdentNss:" + identNss + "\n");
		texto.append(tabulacion + "\t TelefonoMovil:" + telefonoMovil + "\n");
		texto.append(tabulacion + "\t TelefonoFijo:" + telefonoFijo + "\n");
		texto.append(tabulacion + "\t PermetRango:" + permiteRango + "\n");
		texto.append(tabulacion + "\t ForzarMayusculas:" + forzarMayusculas + "\n");
		texto.append(tabulacion + "\t PrevenirAferrar:" + prevenirPegar + "\n");

		return texto.toString();
	}

}
