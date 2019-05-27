package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Componente textbox.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RComponenteTextbox", description = "Descripcion de RComponenteTextbox")
public class RComponenteTextbox extends RComponente {

	/** Propiedades campo. */
	@ApiModelProperty(value = "Propiedades campo")
	private RPropiedadesCampo propiedadesCampo;

	/**
	 * Tipo texto: NORMAL, NUMERO, EMAIL, ID, CP, TELEFONO, FECHA, HORA, EXPRESION,
	 * IMPORTE.
	 */
	@ApiModelProperty(value = "Tipo texto: NORMAL, NUMERO, EMAIL, ID, CP, TELEFONO, FECHA, HORA, EXPRESION, IMPORTE")
	private String tipoTexto;

	/** Propiedades texto normal. */
	@ApiModelProperty(value = "Propiedades texto normal")
	private RPropiedadesTextoNormal textoNormal;

	/** Propiedades texto email. */
	@ApiModelProperty(value = "Propiedades texto email")
	private RPropiedadesTextoEmail textoEmail;

	/** Propiedades texto numero. */
	@ApiModelProperty(value = "Propiedades texto numero")
	private RPropiedadesTextoNumero textoNumero;

	/** Propiedades texto identificacion. */
	@ApiModelProperty(value = "Propiedades texto identificacion")
	private RPropiedadesTextoIdentificacion textoIdentificacion;

	/** Propiedades texto expresión regular. */
	@ApiModelProperty(value = "Propiedades texto expresión regular")
	private RPropiedadesTextoExpRegular textoExpRegular;

	/** Propiedades texto teléfono. */
	@ApiModelProperty(value = "Propiedades texto teléfono")
	private RPropiedadesTextoTelefono textoTelefono;

	/**
	 * Método de acceso a propiedadesCampo.
	 *
	 * @return propiedadesCampo
	 */
	public RPropiedadesCampo getPropiedadesCampo() {
		return propiedadesCampo;
	}

	/**
	 * Método para establecer propiedadesCampo.
	 *
	 * @param propiedadesCampo
	 *            propiedadesCampo a establecer
	 */
	public void setPropiedadesCampo(final RPropiedadesCampo propiedadesCampo) {
		this.propiedadesCampo = propiedadesCampo;
	}

	/**
	 * Método de acceso a tipoTexto.
	 *
	 * @return tipoTexto
	 */
	public String getTipoTexto() {
		return tipoTexto;
	}

	/**
	 * Método para establecer tipoTexto.
	 *
	 * @param tipoTexto
	 *            tipoTexto a establecer
	 */
	public void setTipoTexto(final String tipoTexto) {
		this.tipoTexto = tipoTexto;
	}

	/**
	 * Método de acceso a textoNormal.
	 *
	 * @return textoNormal
	 */
	public RPropiedadesTextoNormal getTextoNormal() {
		return textoNormal;
	}

	/**
	 * Método para establecer textoNormal.
	 *
	 * @param textoNormal
	 *            textoNormal a establecer
	 */
	public void setTextoNormal(final RPropiedadesTextoNormal textoNormal) {
		this.textoNormal = textoNormal;
	}

	/**
	 * Método de acceso a textoNumero.
	 *
	 * @return textoNumero
	 */
	public RPropiedadesTextoNumero getTextoNumero() {
		return textoNumero;
	}

	/**
	 * Método para establecer textoNumero.
	 *
	 * @param textoNumero
	 *            textoNumero a establecer
	 */
	public void setTextoNumero(final RPropiedadesTextoNumero textoNumero) {
		this.textoNumero = textoNumero;
	}

	/**
	 * Método de acceso a textoIdentificacion.
	 *
	 * @return textoIdentificacion
	 */
	public RPropiedadesTextoIdentificacion getTextoIdentificacion() {
		return textoIdentificacion;
	}

	/**
	 * Método para establecer textoIdentificacion.
	 *
	 * @param textoIdentificacion
	 *            textoIdentificacion a establecer
	 */
	public void setTextoIdentificacion(final RPropiedadesTextoIdentificacion textoIdentificacion) {
		this.textoIdentificacion = textoIdentificacion;
	}

	/**
	 * Método de acceso a textoExpRegular.
	 *
	 * @return textoExpRegular
	 */
	public RPropiedadesTextoExpRegular getTextoExpRegular() {
		return textoExpRegular;
	}

	/**
	 * Método para establecer textoExpRegular.
	 *
	 * @param textoExpRegular
	 *            textoExpRegular a establecer
	 */
	public void setTextoExpRegular(final RPropiedadesTextoExpRegular textoExpRegular) {
		this.textoExpRegular = textoExpRegular;
	}

	/**
	 * Método de acceso a textoTelefono.
	 *
	 * @return textoTelefono
	 */
	public RPropiedadesTextoTelefono getTextoTelefono() {
		return textoTelefono;
	}

	/**
	 * Método para establecer textoTelefono.
	 *
	 * @param textoTelefono
	 *            textoTelefono a establecer
	 */
	public void setTextoTelefono(final RPropiedadesTextoTelefono textoTelefono) {
		this.textoTelefono = textoTelefono;
	}

	/**
	 * Método de acceso a textoEmail.
	 * 
	 * @return textoEmail
	 */
	public RPropiedadesTextoEmail getTextoEmail() {
		return textoEmail;
	}

	/**
	 * Método para establecer textoEmail.
	 * 
	 * @param textoEmail
	 *            textoEmail a establecer
	 */
	public void setTextoEmail(final RPropiedadesTextoEmail textoEmail) {
		this.textoEmail = textoEmail;
	}

}
