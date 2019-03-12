package es.caib.sistramit.core.ejb;

import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.flujo.AnexoFichero;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FlujoTramitacionServiceBean implements FlujoTramitacionService {

	@Autowired
	private FlujoTramitacionService flujoTramitacionService;

	@Override
	public String iniciarTramite(final UsuarioAutenticadoInfo usuarioAutenticado, final String idTramite,
			final int version, final String idioma, final String idTramiteCatalogo, final String urlInicio,
			final Map<String, String> parametrosInicio) {
		return flujoTramitacionService.iniciarTramite(usuarioAutenticado, idTramite, version, idioma, idTramiteCatalogo,
				urlInicio, parametrosInicio);
	}

	@Override
	public DetalleTramite obtenerDetalleTramite(final String idSesionTramitacion) {
		return flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
	}

	@Override
	public DetallePasos obtenerDetallePasos(final String idSesionTramitacion) {
		return flujoTramitacionService.obtenerDetallePasos(idSesionTramitacion);
	}

	@Override
	public FlujoTramitacionInfo obtenerFlujoTramitacionInfo(final String idSesionTramitacion) {
		return flujoTramitacionService.obtenerFlujoTramitacionInfo(idSesionTramitacion);
	}

	@Override
	public void invalidarFlujoTramitacion(final String idSesionTramitacion) {
		flujoTramitacionService.invalidarFlujoTramitacion(idSesionTramitacion);
	}

	@Override
	public void cargarTramite(final String idSesionTramitacion, final UsuarioAutenticadoInfo usuarioAutenticado) {
		flujoTramitacionService.cargarTramite(idSesionTramitacion, usuarioAutenticado);
	}

	@Override
	public void recargarTramite(final String idSesionTramitacion, final UsuarioAutenticadoInfo userInfo) {
		flujoTramitacionService.recargarTramite(idSesionTramitacion, userInfo);
	}

	@Override
	public ResultadoIrAPaso irAPaso(final String idSesionTramitacion, final String idPaso) {
		return flujoTramitacionService.irAPaso(idSesionTramitacion, idPaso);
	}

	@Override
	public ResultadoIrAPaso irAPasoActual(final String idSesionTramitacion) {
		return flujoTramitacionService.irAPasoActual(idSesionTramitacion);
	}

	@Override
	public ResultadoAccionPaso accionPaso(final String idSesionTramitacion, final String idPaso,
			final TypeAccionPaso accionPaso, final ParametrosAccionPaso parametros) {
		return flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso, accionPaso, parametros);
	}

	@Override
	public String cancelarTramite(final String idSesionTramitacion) {
		return flujoTramitacionService.cancelarTramite(idSesionTramitacion);
	}

	@Override
	public void envioFormularioSoporte(final String idSesionTramitacion, final String nif, final String nombre,
			final String telefono, final String email, final String problemaTipo, final String problemaDesc,
			final AnexoFichero anexo) {
		flujoTramitacionService.envioFormularioSoporte(idSesionTramitacion, nif, nombre, telefono, email, problemaTipo,
				problemaDesc, anexo);
	}

	@Override
	public byte[] obtenerClavePdf(final String idSesionTramitacion) {
		return flujoTramitacionService.obtenerClavePdf(idSesionTramitacion);
	}

	@Override
	public String logoutTramite(String idSesionTramitacion) {
		return flujoTramitacionService.logoutTramite(idSesionTramitacion);
	}

	@Override
	public String obtenerUrlEntidad(String idTramite, int version, String idioma) {
		return flujoTramitacionService.obtenerUrlEntidad(idTramite, version, idioma);
	}

}
