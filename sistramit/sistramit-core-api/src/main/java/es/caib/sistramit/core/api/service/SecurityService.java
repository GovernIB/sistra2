package es.caib.sistramit.core.api.service;

import java.util.List;

import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;

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
     *            código trámite
     * @param versionTramite
     *            versión trámite
     * @param idTramiteCatalogo
     *            id trámite en catálogo trámites
     * @param idioma
     *            idioma
     * @param urlInicioTramite
     *            url inicio trámite
     * @return información para mostrar login
     */
    InfoLoginTramite obtenerInfoLoginTramite(String codigoTramite,
            int versionTramite, String idTramiteCatalogo, String idioma,
            String urlInicioTramite);

    /**
     * Obtiene información para mostrar el login en la carga trámite anónimo
     * persistente.
     *
     * @param idSesionTramitacion
     *            id sesion tramitación
     * @return información para mostrar login
     */
    InfoLoginTramite obtenerInfoLoginTramiteAnonimoPersistente(
            String idSesionTramitacion);

    /**
     * Inicio sesión en Clave.
     *
     * @param lang
     *            idioma
     * @param authList
     *            Lista metodos autenticación
     * @param qaa
     *            QAA
     * @param urlCallback
     *            url callback asistente
     * @return url redireccion a Clave
     */
    String iniciarSesionAutenticacion(String lang,
            List<TypeAutenticacion> authList, String qaa, String urlCallback);

    /**
     * Cierre sesión en Clave.
     *
     * @param lang
     *            idioma
     * @param urlCallback
     *            url callback asistente
     * @return url redireccion a Clave
     */
    String iniciarLogoutSesionClave(String lang, String urlCallback);

    /**
     * Valida acceso usuario desde Carpeta Ciudadana.
     *
     * @param sesionInfo
     *            Info sesión
     * @param ticket
     *            Ticket
     * @return info usuario
     */
    UsuarioAutenticadoInfo validarTicketCarpetaCiudadana(SesionInfo sesionInfo,
            String ticket);

    /**
     * Valida acceso usuario desde componente autenticacion.
     *
     * @param sesionInfo
     *            Info sesión
     * @param ticket
     *            Ticket
     * @return info usuario
     */
    UsuarioAutenticadoInfo validarTicketAutenticacion(SesionInfo sesionInfo,
            String ticket);

    /**
     * Valida acceso usuario desde Gestor Formularios.
     *
     * @param sesionInfo
     *            Info sesión
     * @param ticket
     *            Ticket
     * @return info usuario
     */
    UsuarioAutenticadoInfo validarTicketGestorFormularios(SesionInfo sesionInfo,
            String ticket);

    /**
     * Valida acceso usuario desde Pasarela de Pagos.
     *
     * @param sesionInfo
     *            Info sesión
     * @param ticket
     *            Ticket
     * @return info usuario
     */
    UsuarioAutenticadoInfo validarTicketPasarelaPagos(SesionInfo sesionInfo,
            String ticket);

}
