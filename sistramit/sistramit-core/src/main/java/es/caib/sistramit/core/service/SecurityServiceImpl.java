package es.caib.sistramit.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionTramiteCP;
import es.caib.sistrages.rest.api.interna.RAviso;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistramit.core.api.exception.ErrorNoControladoException;
import es.caib.sistramit.core.api.model.flujo.AvisoPlataforma;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.AutenticacionComponent;
import es.caib.sistramit.core.service.component.integracion.CatalogoProcedimientosComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.integracion.DatosAutenticacionUsuario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoTramiteDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    /** Acceso configuración. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    /** Acceso Componente Autenticacion. */
    @Autowired
    private AutenticacionComponent autenticacionComponent;

    /** Acceso Catalogo Procedimientos. */
    @Autowired
    private CatalogoProcedimientosComponent catalogoProcedimientosComponent;

    /** DAO Flujo tramite. */
    @Autowired
    private FlujoTramiteDao flujoTramiteDao;

    @Override
    @NegocioInterceptor
    public InfoLoginTramite obtenerInfoLoginTramite(final String codigoTramite,
            final int versionTramite, final String idTramiteCatalogo,
            final String idioma, final String urlInicioTramite) {
        return generarInfoLoginTramite(codigoTramite, versionTramite,
                idTramiteCatalogo, idioma);
    }

    @Override
    @NegocioInterceptor
    public InfoLoginTramite obtenerInfoLoginTramiteAnonimoPersistente(
            String idSesionTramitacion) {
        final DatosPersistenciaTramite dpt = flujoTramiteDao
                .obtenerTramitePersistencia(idSesionTramitacion);
        final InfoLoginTramite infoLogin = generarInfoLoginTramite(
                dpt.getIdTramite(), dpt.getVersionTramite(),
                dpt.getIdTramiteCP(), dpt.getIdioma());
        infoLogin.setLoginAnonimoAuto(true);
        return infoLogin;
    }

    @Override
    @NegocioInterceptor
    public String iniciarSesionAutenticacion(final String idEntidad,
            final String lang, List<TypeAutenticacion> authList, String qaa,
            final String urlCallback, final boolean debug) {
        final String urlAutenticacion = autenticacionComponent
                .iniciarSesionAutenticacion(idEntidad, lang, authList, qaa,
                        urlCallback, debug);
        return urlAutenticacion;
    }

    @Override
    @NegocioInterceptor
    public String iniciarLogoutSesion(final String idEntidad, final String lang,
            final String urlCallback, final boolean debug) {
        final String urlAutenticacion = autenticacionComponent
                .iniciarSesionLogout(idEntidad, lang, urlCallback, debug);
        return urlAutenticacion;
    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarTicketAutenticacion(
            final SesionInfo sesionInfo, final String ticket) {

        final DatosAutenticacionUsuario usuario = autenticacionComponent
                .validarTicketAutenticacion(ticket);

        final UsuarioAutenticadoInfo u = new UsuarioAutenticadoInfo();
        if (usuario
                .getMetodoAutenticacion() == TypeMetodoAutenticacion.ANONIMO) {
            u.setUsername(ConstantesSeguridad.ANONIMO_USER);
        } else {
            u.setUsername(usuario.getNif());
        }
        u.setNif(usuario.getNif());
        u.setNombre(usuario.getNombre());
        u.setApellido1(usuario.getApellido1());
        u.setApellido2(usuario.getApellido2());
        u.setEmail(usuario.getEmail());
        u.setAutenticacion(usuario.getAutenticacion());
        u.setMetodoAutenticacion(usuario.getMetodoAutenticacion());
        u.setSesionInfo(sesionInfo);

        return u;

    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarUsuarioAnonimo(SesionInfo sesionInfo) {
        final UsuarioAutenticadoInfo u = new UsuarioAutenticadoInfo();
        u.setUsername(ConstantesSeguridad.ANONIMO_USER);
        u.setAutenticacion(TypeAutenticacion.ANONIMO);
        u.setSesionInfo(sesionInfo);
        return u;
    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarTicketGestorFormularios(
            final SesionInfo sesionInfo, final String ticket) {
        // TODO PENDIENTE
        throw new ErrorNoControladoException("Pendiente implementar");
    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarTicketPasarelaPagos(
            final SesionInfo sesionInfo, final String ticket) {
        // TODO PENDIENTE
        throw new ErrorNoControladoException("Pendiente implementar");
    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarTicketCarpetaCiudadana(
            final SesionInfo sesionInfo, final String ticket) {
        // TODO PENDIENTE
        throw new ErrorNoControladoException("Pendiente implementar");
    }

    // ------------------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // ------------------------------------------------------------------------
    private InfoLoginTramite generarInfoLoginTramite(final String codigoTramite,
            final int versionTramite, final String idTramiteCatalogo,
            final String idioma) {
        final DefinicionTramiteSTG defTramite = configuracionComponent
                .recuperarDefinicionTramite(codigoTramite, versionTramite,
                        idioma);
        final RConfiguracionEntidad entidad = configuracionComponent
                .obtenerConfiguracionEntidad(
                        defTramite.getDefinicionVersion().getIdEntidad());
        final RAvisosEntidad avisosEntidad = configuracionComponent
                .obtenerAvisosEntidad(
                        defTramite.getDefinicionVersion().getIdEntidad());
        final DefinicionTramiteCP defTramiteCP = catalogoProcedimientosComponent
                .obtenerDefinicionTramite(entidad.getIdentificador(),
                        idTramiteCatalogo, idioma);

        final InfoLoginTramite res = new InfoLoginTramite();
        res.setIdioma(idioma);
        res.setTitulo(defTramiteCP.getDescripcion());

        final List<TypeAutenticacion> niveles = new ArrayList<>();
        if (defTramite.getDefinicionVersion().getPropiedades()
                .isAutenticado()) {
            niveles.add(TypeAutenticacion.AUTENTICADO);
        }
        if (defTramite.getDefinicionVersion().getPropiedades()
                .isNoAutenticado()) {
            niveles.add(TypeAutenticacion.ANONIMO);
        }
        res.setNiveles(niveles);
        res.setQaa(String.valueOf(defTramite.getDefinicionVersion().getPropiedades().getNivelQAA()));
		res.setEntidad(UtilsFlujo.detalleTramiteEntidad(entidad, idioma));

        final List<AvisoPlataforma> avisos = new ArrayList<>();
        for (final RAviso a : avisosEntidad.getAvisos()) {
            final AvisoPlataforma aviso = new AvisoPlataforma();
            aviso.setMensaje(UtilsSTG.obtenerLiteral(a.getMensaje(), idioma));
            aviso.setBloquearAcceso(a.isBloquear());
            avisos.add(aviso);
        }
        res.setAvisos(avisos);

        res.setDebug(UtilsSTG.isDebugEnabled(defTramite));
        return res;
    }
}
