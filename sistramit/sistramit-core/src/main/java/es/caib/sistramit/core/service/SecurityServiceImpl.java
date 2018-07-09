package es.caib.sistramit.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.flujo.AvisoPlataforma;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.flujo.MockFlujo;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    @Override
    @NegocioInterceptor
    public InfoLoginTramite obtenerInfoLoginTramite(final String codigoTramite,
            final int versionTramite, final String idTramiteCatalogo,
            final String idioma, final String urlInicioTramite) {

        // TODO PENDIENTE
        final InfoLoginTramite res = new InfoLoginTramite();
        res.setIdioma("es");
        res.setTitulo("Tramite 1");
        final List<TypeAutenticacion> niveles = new ArrayList<>();
        niveles.add(TypeAutenticacion.AUTENTICADO);
        niveles.add(TypeAutenticacion.ANONIMO);
        res.setNiveles(niveles);
        res.setEntidad(MockFlujo.generarConfiguracionEntidad());

        final List<AvisoPlataforma> avisos = new ArrayList<>();
        final AvisoPlataforma aviso = new AvisoPlataforma();
        aviso.setMensaje("Mensaje de aviso 1");
        avisos.add(aviso);
        res.setAvisos(avisos);

        return res;
    }

    @Override
    @NegocioInterceptor
    public InfoLoginTramite obtenerInfoLoginTramiteAnonimoPersistente(
            String idSesionTramitacion) {

        // TODO PENDIENTE
        final InfoLoginTramite res = new InfoLoginTramite();
        res.setIdioma("es");
        res.setTitulo("Tramite 1");

        final List<TypeAutenticacion> niveles = new ArrayList<>();
        niveles.add(TypeAutenticacion.ANONIMO);

        res.setNiveles(niveles);
        res.setEntidad(MockFlujo.generarConfiguracionEntidad());

        final List<AvisoPlataforma> avisos = new ArrayList<>();
        final AvisoPlataforma aviso = new AvisoPlataforma();
        aviso.setMensaje("Mensaje de aviso 1");
        avisos.add(aviso);
        res.setAvisos(avisos);

        return res;
    }

    @Override
    @NegocioInterceptor
    public String iniciarSesionAutenticacion(final String lang,
            List<TypeAutenticacion> authList, String qaa,
            final String urlCallback) {
        // TODO Pendiente. Simulamos retorno componente autenticacion.
        String prefix;
        prefix = authList.get(0).toString();
        return ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN
                + "?ticket=" + prefix + "12345";
    }

    @Override
    @NegocioInterceptor
    public String iniciarLogoutSesionClave(final String lang,
            final String urlCallback) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarTicketCarpetaCiudadana(
            final SesionInfo sesionInfo, final String ticket) {

        // TODO PENDIENTE
        return generarUserMock(sesionInfo);
    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarTicketAutenticacion(
            final SesionInfo sesionInfo, final String ticket) {
        // TODO Pendiente
        UsuarioAutenticadoInfo u;
        if (ticket.startsWith(TypeAutenticacion.AUTENTICADO.toString())) {
            u = generarUserMock(sesionInfo);
        } else {
            u = generarUsuarioAnonimo(sesionInfo);
        }
        return u;
    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarTicketGestorFormularios(
            final SesionInfo sesionInfo, final String ticket) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @NegocioInterceptor
    public UsuarioAutenticadoInfo validarTicketPasarelaPagos(
            final SesionInfo sesionInfo, final String ticket) {
        // TODO Auto-generated method stub
        return null;
    }

    private UsuarioAutenticadoInfo generarUserMock(
            final SesionInfo sesionInfo) {
        final UsuarioAutenticadoInfo ui = new UsuarioAutenticadoInfo();
        ui.setAutenticacion(TypeAutenticacion.AUTENTICADO);
        ui.setMetodoAutenticacion(TypeMetodoAutenticacion.CLAVE_CERTIFICADO);
        ui.setUsername("11111111H");
        ui.setNif("11111111H");
        ui.setNombre("José");
        ui.setApellido1("García");
        ui.setApellido2("Gutierrez");
        ui.setSesionInfo(sesionInfo);
        return ui;
    }

    /**
     * Genera usuario anonimo.
     *
     * @param sesionInfo
     *            sesion info
     * @return usuario anonimo
     */
    private UsuarioAutenticadoInfo generarUsuarioAnonimo(
            final SesionInfo sesionInfo) {
        final UsuarioAutenticadoInfo ui = new UsuarioAutenticadoInfo();
        ui.setAutenticacion(TypeAutenticacion.ANONIMO);
        ui.setMetodoAutenticacion(TypeMetodoAutenticacion.ANONIMO);
        ui.setUsername(ConstantesSeguridad.ANONIMO_USER);
        ui.setSesionInfo(sesionInfo);
        return ui;
    }

}
