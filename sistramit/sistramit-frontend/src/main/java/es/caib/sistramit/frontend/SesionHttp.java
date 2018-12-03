package es.caib.sistramit.frontend;

import es.caib.sistramit.frontend.model.MensajeAsistente;

/**
 * Bean de sesión http que almacena referencia a la sesión de tramitación.
 *
 * @author Indra
 *
 */
public interface SesionHttp {

    /**
     * Obtiene user agent sesion http.
     *
     * @return user agent sesion http.
     */
    String getUserAgent();

    /**
     * Establece user agent sesion http.
     *
     * @param userAgent
     *            user agent sesion http.
     */
    void setUserAgent(String userAgent);

    /**
     * Método para guardar idioma a nivel de sesión web.
     *
     * @param idioma
     *            Idioma de tramitación
     */
    void setIdioma(final String idioma);

    /**
     * Método para obtener idioma tramitación.
     *
     * @return Idioma de tramitación
     */
    String getIdioma();

    /**
     * Método para guardar identificador de sesión de tramitación.
     *
     * @param idSesionTramitacion
     *            Id sesión de tramitación
     */
    void setIdSesionTramitacion(final String idSesionTramitacion);

    /**
     * Método para obtener id sesión tramitación.
     *
     * @return idSesionTramitacion Id sesión de tramitación
     */
    String getIdSesionTramitacion();

    /**
     * Indica si de hace debug de la sesión de tramitación
     *
     * @param debug
     *            Debug
     */
    void setDebugSesionTramitacion(boolean debug);

    /**
     * Indica si de hace debug de la sesión de tramitación
     *
     * @return debug
     */
    boolean isDebugSesionTramitacion();

    /**
     * Url inicio.
     *
     * @param url
     *            url
     */
    void setUrlInicio(String url);

    /**
     * Url inicio.
     *
     * @return url
     */
    String getUrlInicio();

    /**
     * Método para establecer mensajeAsistente.
     *
     * @param pMensajeAsistente
     *            mensajeAsistente a establecer
     */
    void setMensajeAsistente(final MensajeAsistente pMensajeAsistente);

    /**
     * Método de acceso a mensajeAsistente.
     *
     * @return mensajeAsistente
     */
    MensajeAsistente getMensajeAsistente();

    /**
     * Obtiene id sesion formulario.
     * 
     * @return id sesion formulario
     */
    String getIdSesionFormulario();

    /**
     * Establece id sesion formulario.
     * 
     * @param idSesionFormulario
     *            id sesion formulario
     */
    void setIdSesionFormulario(String idSesionFormulario);

}
