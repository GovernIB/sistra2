package es.caib.sistramit.frontend;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.AccesoNoPermitidoException;
import es.caib.sistramit.core.api.exception.LimiteTramitacionException;
import es.caib.sistramit.core.api.exception.LoginException;
import es.caib.sistramit.core.api.exception.RegistroJustificanteException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.exception.TramiteFinalizadoException;
import es.caib.sistramit.core.api.exception.TramiteNoExisteException;
import es.caib.sistramit.core.api.exception.UsuarioNoPermitidoException;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 * Errores.
 *
 * @author Indra
 *
 */
@Component("errores")
public final class ErroresImpl implements Errores {

	/** Atributo literales. */
	@Autowired
	private LiteralesFront literales;

	/** Configuracion. */
	@Autowired
	private SystemService systemService;

	/** Informacion de sesion de tramitacion. */
	@Autowired
	private SesionHttp sesionHttp;

	/** Flujo tramitacion service. */
	@Autowired
	FlujoTramitacionService flujoTramitacionService;

	/**
	 * Service excepcion para las que no se intentara recargar el tramite.
	 */
	private static final String[] SERVICE_EXCEPTION_NO_RECARGAR = { AccesoNoPermitidoException.class.getName(),
			LoginException.class.getName(), TramiteFinalizadoException.class.getName(),
			TramiteNoExisteException.class.getName(), UsuarioNoPermitidoException.class.getName(),
			LimiteTramitacionException.class.getName(), RegistroJustificanteException.class.getName() };

	@Override
	public RespuestaJSON generarRespuestaJsonExcepcion(final Exception pEx, final String idioma) {

		// Evaluamos excepcion
		TypeRespuestaJSON tipoError;
		String tituloMensaje;
		String textoMensaje;
		String urlMensaje;

		// Establecemos nivel excepcion
		TypeNivelExcepcion nivel;
		if (pEx instanceof ServiceException) {
			final ServiceException ex = (ServiceException) pEx;
			nivel = ex.getNivel();
		} else {
			nivel = TypeNivelExcepcion.FATAL;
		}

		// En funcion del nivel establecemos tipo respuesta y url siguiente
		switch (nivel) {
		case WARNING:
			tipoError = TypeRespuestaJSON.WARNING;
			break;
		case ERROR:
			tipoError = TypeRespuestaJSON.ERROR;
			break;
		default: // FATAL
			tipoError = TypeRespuestaJSON.FATAL;
		}

		// Establecemos titulo / texto excepcion
		tituloMensaje = devolverTituloExcepcion(pEx, idioma, tipoError);
		textoMensaje = devolverMensajeError(pEx, idioma);
		urlMensaje = devolverUrlExcepcion(pEx, idioma, tipoError);

		// Creamos respuesta
		final RespuestaJSON res = new RespuestaJSON();
		res.setEstado(tipoError);
		res.setUrl(urlMensaje);
		res.setMensaje(new MensajeUsuario(tituloMensaje, textoMensaje));

		return res;
	}

	/**
	 * Establece url tras excepción.
	 *
	 * @param pEx
	 *            Excepción
	 * @param pIdioma
	 *            Idioma
	 * @param pTipoError
	 *            Tipo error
	 * @return url tras excepción (nulo si no se establece ninguna)
	 */
	private String devolverUrlExcepcion(final Exception pEx, final String pIdioma, final TypeRespuestaJSON pTipoError) {
		String url = getUrlAsistente() + "/asistente/asistente.html";

		// Url estandar para excepciones recargar tramite
		if (pTipoError == TypeRespuestaJSON.FATAL) {
			url = getUrlAsistente() + "/asistente/recargarTramite.html";
		}

		if (pEx instanceof ServiceException) {
			// Comprobamos si esta particularizado por excepcion
			final String keyLiteralExcepcion = getNombreExcepcion(pEx);
			url = literales.getLiteralFront(LiteralesFront.EXCEPCIONES, "url." + keyLiteralExcepcion, pIdioma, url);
		}

		// Si es una excepción para la que no hay que recargar, intentamos redirigir a
		// entidad
		if (!isExceptionServiceRecargar(pEx) && (pTipoError == TypeRespuestaJSON.FATAL) && sesionHttp != null
				&& sesionHttp.getIdTramite() != null) {
			try {
				final String urlEntidad = flujoTramitacionService.obtenerUrlEntidad(sesionHttp.getIdTramite(),
						sesionHttp.getVersion(), sesionHttp.getIdioma());
				if (StringUtils.isNotBlank(urlEntidad)) {
					url = urlEntidad;
				}
			} catch (final Exception ex) {
				// Mantenemos url defecto
			}
		}

		return url;
	}

	/**
	 * Devuelte titulo excepcion.
	 *
	 * @param pEx
	 *            Excepcion
	 * @param idioma
	 *            Idioma
	 * @param pTipoError
	 *            Tipo error
	 * @return Titulo excepcion
	 */
	private String devolverTituloExcepcion(final Exception pEx, final String idioma,
			final TypeRespuestaJSON pTipoError) {
		// Obtenemos titulo general
		String tituloMensaje = literales.getLiteralFront(LiteralesFront.EXCEPCIONES, "title." + pTipoError.toString(),
				idioma);

		// Comprobamos si esta particularizado por excepcion
		if (pEx instanceof ServiceException) {
			final String keyLiteralExcepcion = getNombreExcepcion(pEx);
			tituloMensaje = literales.getLiteralFront(LiteralesFront.EXCEPCIONES, "title." + keyLiteralExcepcion,
					idioma, tituloMensaje);
		}

		return tituloMensaje;
	}

	/**
	 * Método para devolver mensaje error de la clase TramitacionController.
	 *
	 * @param ex
	 *            Parámetro ex
	 * @param idioma
	 *            Parámetro idioma
	 * @return el string
	 */
	private String devolverMensajeError(final Exception ex, final String idioma) {

		String mensaje = null;

		final String nombreExcepcion = getNombreExcepcion(ex);

		// Obtenemos texto general
		mensaje = literales.getLiteralFront(LiteralesFront.EXCEPCIONES, "text.generica", idioma);

		// Buscamos si existe texto particularizado para la excepcion
		mensaje = literales.getLiteralFront(LiteralesFront.EXCEPCIONES, "text." + nombreExcepcion, idioma, mensaje);

		// Reemplazamos parametros especiales
		mensaje = StringUtils.replace(mensaje, "[#excepcion.nombre#]", nombreExcepcion);
		mensaje = StringUtils.replace(mensaje, "[#excepcion.mensaje#]", ex.getMessage());

		return mensaje;

	}

	/**
	 * Obtiene nombre excepcion.
	 *
	 * @param ex
	 *            Excepcion
	 * @return nombre excepcion
	 */
	private String getNombreExcepcion(final Exception ex) {
		String keyLiteralExcepcion;
		final String name = ex.getClass().getName();
		final int idx = name.lastIndexOf(".");
		keyLiteralExcepcion = name.substring(idx + ConstantesNumero.N1, name.length());
		return keyLiteralExcepcion;
	}

	/**
	 * Obtiene url asistente.
	 *
	 * @return url asistente
	 */
	private String getUrlAsistente() {
		return systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL);
	}

	/**
	 * Comprueba si es una excepcion de servicio para la que se intentara recargar
	 * el tramite.
	 *
	 * @param pEx
	 *            Exception
	 * @return true si se debe intentar recargar el tramite.
	 */
	private boolean isExceptionServiceRecargar(final Exception pEx) {
		boolean res = false;
		if (pEx instanceof ServiceException && ((ServiceException) pEx).getNivel() == TypeNivelExcepcion.FATAL) {
			res = true;
			for (final String exceptionName : SERVICE_EXCEPTION_NO_RECARGAR) {
				if (StringUtils.equals(exceptionName, pEx.getClass().getName())) {
					res = false;
					break;
				}
			}
		}
		return res;
	}
}
