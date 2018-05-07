package es.caib.sistramit.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	@Override
	@NegocioInterceptor
	public InfoLoginTramite obtenerInfoLoginTramite(final String codigoTramite, final int versionTramite,
			final String idTramiteCatalogo, final String idioma, final String urlInicioTramite) {
		// TODO Auto-generated method stub
		final InfoLoginTramite res = new InfoLoginTramite();
		res.setTitulo("Tramite 1");
		res.setUrlEntidad("http://www.caib.es");
		final List<TypeAutenticacion> niveles = new ArrayList<>();
		niveles.add(TypeAutenticacion.ANONIMO);
		niveles.add(TypeAutenticacion.AUTENTICADO);
		res.setNiveles(niveles);
		return res;
	}

	@Override
	@NegocioInterceptor
	public String iniciarSesionClave(final String lang, final String urlCallback) {
		// TODO Pendiente. Simulamos retorno Clave.
		return "/asistente/accesoClave.html?ticket=12345";
	}

	@Override
	@NegocioInterceptor
	public String iniciarLogoutSesionClave(final String lang, final String urlCallback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarUsuarioAnonimo(final SesionInfo sesionInfo) {
		final UsuarioAutenticadoInfo ui = new UsuarioAutenticadoInfo();
		ui.setAutenticacion(TypeAutenticacion.ANONIMO);
		ui.setMetodoAutenticacion(TypeMetodoAutenticacion.ANONIMO);
		ui.setUsername(ConstantesSeguridad.ANONIMO_USER);
		ui.setSesionInfo(sesionInfo);
		return ui;
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketCarpetaCiudadana(final SesionInfo sesionInfo, final String ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketClave(final SesionInfo sesionInfo, final String ticket) {
		// TODO Pendiente
		return generarUserMock(sesionInfo);
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketGestorFormularios(final SesionInfo sesionInfo, final String ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketPasarelaPagos(final SesionInfo sesionInfo, final String ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	private UsuarioAutenticadoInfo generarUserMock(final SesionInfo sesionInfo) {
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

}
