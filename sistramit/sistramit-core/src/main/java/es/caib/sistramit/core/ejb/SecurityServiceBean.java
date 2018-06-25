package es.caib.sistramit.core.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.service.SecurityService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SecurityServiceBean implements SecurityService {

    @Autowired
    private SecurityService securityService;

    @Override
    public InfoLoginTramite obtenerInfoLoginTramite(final String codigoTramite,
            final int versionTramite, final String idTramiteCatalogo,
            final String idioma, final String urlInicioTramite) {
        return securityService.obtenerInfoLoginTramite(codigoTramite,
                versionTramite, idTramiteCatalogo, idioma, urlInicioTramite);
    }

    @Override
    public String iniciarSesionAutenticacion(final String lang,
            List<TypeAutenticacion> authList, String qaa,
            final String urlCallback) {
        return securityService.iniciarSesionAutenticacion(lang, authList, qaa,
                urlCallback);
    }

    @Override
    public String iniciarLogoutSesionClave(final String lang,
            final String urlCallback) {
        return securityService.iniciarLogoutSesionClave(lang, urlCallback);
    }

    @Override
    public UsuarioAutenticadoInfo validarTicketCarpetaCiudadana(
            final SesionInfo sesionInfo, final String ticket) {
        return securityService.validarTicketCarpetaCiudadana(sesionInfo,
                ticket);
    }

    @Override
    public UsuarioAutenticadoInfo validarTicketAutenticacion(
            final SesionInfo sesionInfo, final String ticket) {
        return securityService.validarTicketAutenticacion(sesionInfo, ticket);
    }

    @Override
    public UsuarioAutenticadoInfo validarTicketGestorFormularios(
            final SesionInfo sesionInfo, final String ticket) {
        return securityService.validarTicketGestorFormularios(sesionInfo,
                ticket);
    }

    @Override
    public UsuarioAutenticadoInfo validarTicketPasarelaPagos(
            final SesionInfo sesionInfo, final String ticket) {
        return securityService.validarTicketPasarelaPagos(sesionInfo, ticket);
    }

    @Override
    public InfoLoginTramite obtenerInfoLoginTramiteAnonimoPersistente(
            String idSesionTramitacion) {
        return securityService
                .obtenerInfoLoginTramiteAnonimoPersistente(idSesionTramitacion);
    }

}
