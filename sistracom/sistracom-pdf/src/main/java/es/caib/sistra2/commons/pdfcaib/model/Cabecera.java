/**
 *
 */
package es.caib.sistra2.commons.pdfcaib.model;

/**
 * @author Indra
 *
 */
public class Cabecera {

	/** Url del logo */
	private String logo;
	/** Titulo de la cabecera */
	private String titulo;
	/** Subtitulo de la cabecera */
	private String subtitulo;

	/** codigo siac de la cabecera */
	private String codigoSia;
	/** array de bytes del logo de la cabecera */
	private byte[] logoByte;
	/** Personalizacion del texto del subtitulo */
	private PersonalizacionTexto personalizacionTextoSubtitulo;
	/** Personalizacion del texto del titulo */
	private PersonalizacionTexto personalizacionTextoTitulo;

	/**
	 * @return Devuelve logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo Modifica logo
	 */
	public void setLogo(final String logo) {
		this.logo = logo;
	}

	/**
	 * @return Devuelve titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo Modifica titulo
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return Devuelve subtitulo
	 */
	public String getSubtitulo() {
		return subtitulo;
	}

	/**
	 * @param subtitulo Modifica subtitulo
	 */
	public void setSubtitulo(final String subtitulo) {
		this.subtitulo = subtitulo;
	}

	/**
	 * @return Devuelve personalizacionTextoSubtitulo
	 */
	public PersonalizacionTexto getPersonalizacionTextoSubtitulo() {
		return personalizacionTextoSubtitulo;
	}

	/**
	 * @param personalizacionTextoSubtitulo Modifica personalizacionTextoSubtitulo
	 */
	public void setPersonalizacionTextoSubtitulo(final PersonalizacionTexto personalizacionTextoSubtitulo) {
		this.personalizacionTextoSubtitulo = personalizacionTextoSubtitulo;
	}

	/**
	 * @return Devuelve personalizacionTextoTitulo
	 */
	public PersonalizacionTexto getPersonalizacionTextoTitulo() {
		return personalizacionTextoTitulo;
	}

	/**
	 * @param personalizacionTextoTitulo Modifica personalizacionTextoTitulo
	 */
	public void setPersonalizacionTextoTitulo(final PersonalizacionTexto personalizacionTextoTitulo) {
		this.personalizacionTextoTitulo = personalizacionTextoTitulo;
	}

	/**
	 * @return Devuelve logoByte
	 */
	public byte[] getLogoByte() {
		return logoByte;
	}

	/**
	 * @param logoByte Modifica logoByte
	 */
	public void setLogoByte(final byte[] logoByte) {
		this.logoByte = logoByte;
	}

	public String getCodigoSia() {
		return codigoSia;
	}

	public void setCodigoSia(final String codigoSiac) {
		this.codigoSia = codigoSiac;
	}

}
