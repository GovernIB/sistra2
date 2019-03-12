package es.caib.sistramit.core.api.model.flujo;

import java.util.List;

/**
 * Entidad soporte.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class EntidadSoporte implements ModelApi {

	/** Telefono soporte. */
	private String telefono;

	/** Email soporte. */
	private String correo;

	/** Url soporte. */
	private String url;

	/** Anexo. */
	private EntidadSoporteAnexo anexo;

	/** Formulario soporte. */
	private List<SoporteOpcion> problemas;

	/**
	 * Método de acceso a telefono.
	 *
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Método para establecer telefono.
	 *
	 * @param telefono
	 *            telefono a establecer
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Método de acceso a correo.
	 *
	 * @return correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Método para establecer correo.
	 *
	 * @param correo
	 *            correo a establecer
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Método de acceso a url.
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Método para establecer url.
	 *
	 * @param url
	 *            url a establecer
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Método de acceso a problemas.
	 *
	 * @return problemas
	 */
	public List<SoporteOpcion> getProblemas() {
		return problemas;
	}

	/**
	 * Método para establecer problemas.
	 *
	 * @param problemas
	 *            problemas a establecer
	 */
	public void setProblemas(List<SoporteOpcion> problemas) {
		this.problemas = problemas;
	}

	/**
	 * Método de acceso a anexo.
	 * 
	 * @return anexo
	 */
	public EntidadSoporteAnexo getAnexo() {
		return anexo;
	}

	/**
	 * Método para establecer anexo.
	 * 
	 * @param anexo
	 *            anexo a establecer
	 */
	public void setAnexo(EntidadSoporteAnexo anexo) {
		this.anexo = anexo;
	}

}