package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo texto identificacion.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesTextoIdentificacion", description = "Descripcion de RPropiedadesTextoIdentificacion")
public class RPropiedadesTextoIdentificacion {

	/** Texto identificación: nif. */
	@ApiModelProperty(value = "Texto identificación: dni")
	private boolean dni;

	/** Texto identificación: nie. */
	@ApiModelProperty(value = "Texto identificación: nie")
	private boolean nie;

	/** Texto identificación: cif. */
	@ApiModelProperty(value = "Texto identificación: nif otros")
	private boolean nifOtros;

	/** Texto identificación: nif. */
	@ApiModelProperty(value = "Texto identificación: nif")
	private boolean nif;

	/** Texto identificación: nss. */
	@ApiModelProperty(value = "Texto identificación: nss")
	private boolean nss;

	@ApiModelProperty(value = "Prevenir pegar")
	private boolean prevenirPegar;

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

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif
	 */
	public boolean isNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param nif nif a establecer
	 */
	public void setNif(final boolean nif) {
		this.nif = nif;
	}

	/**
	 * Método de acceso a nie.
	 *
	 * @return nie
	 */
	public boolean isNie() {
		return nie;
	}

	/**
	 * Método para establecer nie.
	 *
	 * @param nie nie a establecer
	 */
	public void setNie(final boolean nie) {
		this.nie = nie;
	}

	/**
	 * Método de acceso a nss.
	 *
	 * @return nss
	 */
	public boolean isNss() {
		return nss;
	}

	/**
	 * Método para establecer nss.
	 *
	 * @param nss nss a establecer
	 */
	public void setNss(final boolean nss) {
		this.nss = nss;
	}

	/**
	 * @return the dni
	 */
	public final boolean isDni() {
		return dni;
	}

	/**
	 * @param dni the dni to set
	 */
	public final void setDni(final boolean dni) {
		this.dni = dni;
	}

	/**
	 * @return the nifOtros
	 */
	public final boolean isNifOtros() {
		return nifOtros;
	}

	/**
	 * @param nifOtros the nifOtros to set
	 */
	public final void setNifOtros(final boolean nifOtros) {
		this.nifOtros = nifOtros;
	}

}
