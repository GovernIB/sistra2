package es.caib.sistramit.frontend;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistramit.frontend.model.MensajeAsistente;

/**
 * Bean de sesión http que almacena referencia a la sesión de tramitación.
 *
 * @author Indra
 *
 */
@Component("sesionHttp")
@Scope(value = "session")
@SuppressWarnings("serial")
public final class SesionHttpImpl implements SesionHttp, Serializable {

	/**
	 * User agent sesion http.
	 */
	private String userAgent;

	/**
	 * Id de sesión de tramitación.
	 */
	private String idSesionTramitacion;

	/**
	 * Idioma de tramitación.
	 */
	private String idioma;

	/**
	 * Mensaje a mostrar al cargar el asistente.
	 */
	private MensajeAsistente mensajeAsistente;

	/**
	 * Url inicio.
	 */
	private String urlInicio;

	/**
	 * Constructor.
	 */
	public SesionHttpImpl() {
		super();
	}

	@Override
	public void setIdioma(final String pIdioma) {
		this.idioma = pIdioma;
	}

	@Override
	public String getIdioma() {
		return this.idioma;
	}

	@Override
	public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
		this.idSesionTramitacion = pIdSesionTramitacion;
	}

	@Override
	public String getIdSesionTramitacion() {
		return this.idSesionTramitacion;
	}

	@Override
	public MensajeAsistente getMensajeAsistente() {
		return mensajeAsistente;
	}

	@Override
	public void setMensajeAsistente(final MensajeAsistente pMensajeAsistente) {
		mensajeAsistente = pMensajeAsistente;
	}

	@Override
	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public void setUserAgent(final String pUserAgent) {
		userAgent = pUserAgent;
	}

	@Override
	public String getUrlInicio() {
		return urlInicio;
	}

	@Override
	public void setUrlInicio(final String pUrlInicio) {
		urlInicio = pUrlInicio;
	}

}
