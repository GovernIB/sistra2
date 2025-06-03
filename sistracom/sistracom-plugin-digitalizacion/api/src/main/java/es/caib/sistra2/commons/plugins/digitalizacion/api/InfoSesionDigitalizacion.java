package es.caib.sistra2.commons.plugins.digitalizacion.api;

/**
 * Datos relativos a la sesión de digitalización.
 *
 * @author Indra
 *
 */
public class InfoSesionDigitalizacion {

	/** Nombre documento. */
	private String nombreDocumento;

	/** Ciudadano. */
	private InfoPersonaDigitalizacion ciudadano;

	/** Funcionario habilitado. */
	private InfoPersonaDigitalizacion funcionarioHabilitado;

	/** Idioma. **/
	private String idioma;

	/** Código de la entidad. **/
	private String entidad;

	/** DIR3 asociado a digitalización. **/
	private String codigoDIR3;

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getCodigoDIR3() {
		return codigoDIR3;
	}

	public void setCodigoDIR3(String codigoDIR3) {
		this.codigoDIR3 = codigoDIR3;
	}

	public InfoPersonaDigitalizacion getFuncionarioHabilitado() {
		return funcionarioHabilitado;
	}

	public void setFuncionarioHabilitado(InfoPersonaDigitalizacion funcionarioHabilitado) {
		this.funcionarioHabilitado = funcionarioHabilitado;
	}

	public InfoPersonaDigitalizacion getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(InfoPersonaDigitalizacion ciudadano) {
		this.ciudadano = ciudadano;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

}
