package es.caib.sistramit.core.api.service;

import java.util.List;

import es.caib.sistramit.core.api.model.flujo.RetornoFormularioExterno;
import es.caib.sistramit.core.api.model.flujo.RetornoPago;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
public interface SecurityService {

	/**
	 * Obtiene información para mostrar el login al iniciar nuevo trámite.
	 *
	 * @param codigoTramite
	 *                              código trámite
	 * @param versionTramite
	 *                              versión trámite
	 * @param idTramiteCatalogo
	 *                              id trámite en catálogo trámites
	 * @param servicioCatalogo
	 *                              indica si es servicio
	 * @param idioma
	 *                              idioma
	 * @param urlInicioTramite
	 *                              url inicio trámite
	 * @return información para mostrar login
	 */
	InfoLoginTramite obtenerInfoLoginTramite(String codigoTramite, int versionTramite, String idTramiteCatalogo,
			final boolean servicioCatalogo, String idioma, String urlInicioTramite);

	/**
	 * Obtiene información para mostrar el login en la carga trámite anónimo
	 * persistente.
	 *
	 * @param idSesionTramitacion
	 *                                id sesion tramitación
	 * @return información para mostrar login
	 */
	InfoLoginTramite obtenerInfoLoginTramiteAnonimoPersistente(String idSesionTramitacion);

	/**
	 * Inicio sesión en Clave.
	 *
	 * @param idEntidad
	 *                             codigo entidad
	 * @param lang
	 *                             idioma
	 * @param authList
	 *                             Lista metodos autenticación
	 * @param qaa
	 *                             QAA
	 * @param urlCallback
	 *                             url callback asistente en caso correcto
	 * @param urlCallbackError
	 *                             url callback asistente en caso de error
	 * @param debug
	 *                             debug
	 * @return url redireccion a Clave
	 */
	String iniciarSesionAutenticacion(final String idEntidad, final String lang, List<TypeMetodoAutenticacion> authList,
			TypeQAA qaa, final String urlCallback, final String urlCallbackError, final boolean debug);

	/**
	 * Cierre sesión en Clave.
	 *
	 * @param idEntidad
	 *                        codigo entidad
	 * @param lang
	 *                        idioma
	 * @param urlCallback
	 *                        url callback asistente
	 * @param debug
	 *                        debug
	 * @return url redireccion a Clave
	 */
	String iniciarLogoutSesion(final String idEntidad, final String lang, final String urlCallback,
			final boolean debug);

	/**
	 * Valida acceso usuario desde Carpeta Ciudadana.
	 *
	 * @param sesionInfo
	 *                       Info sesión
	 * @param ticket
	 *                       Ticket
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarTicketCarpetaCiudadana(SesionInfo sesionInfo, String ticket);

	/**
	 * Valida acceso usuario desde componente autenticacion.
	 *
	 * @param sesionInfo
	 *                       Info sesión
	 * @param ticket
	 *                       Ticket
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarTicketAutenticacion(SesionInfo sesionInfo, String ticket);

	/**
	 * Valida acceso usuario desde Gestor Formularios.
	 *
	 * @param sesionInfo
	 *                          Info sesión
	 * @param ticketExterno
	 *                          ticket generado por GFE
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarTicketFormularioExterno(SesionInfo sesionInfo, String ticketExterno);

	/**
	 * Obtiene información formulario.
	 *
	 * @param ticketExterno
	 *                          ticket generado por GFE
	 * @return información ticket acceso
	 */
	RetornoFormularioExterno obtenerTicketFormularioExterno(String ticketExterno);

	/**
	 * Valida acceso usuario desde Pasarela de Pagos.
	 *
	 * @param sesionInfo
	 *                       Info sesión
	 * @param ticket
	 *                       Ticket
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarTicketPasarelaPagos(SesionInfo sesionInfo, String ticket);

	/**
	 * Obtiene datos retorno pago.
	 *
	 * @return
	 */
	RetornoPago obtenerTicketPago(String ticket);

	/**
	 * Crea datos usuario info para login anonimo automatico.
	 *
	 * @param sesionInfo
	 *                       Sesion info
	 * @return info usuario
	 */
	UsuarioAutenticadoInfo validarUsuarioAnonimo(SesionInfo sesionInfo);

	/**
	 * Obtiene información ticket acceso CDC.
	 *
	 * @param ticket
	 *                   ticket
	 * @return información ticket acceso
	 */
	InfoTicketAcceso obtenerTicketAccesoCDC(String ticket);

}
