package es.caib.sistramit.core.ejb;

import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FlujoTramitacionServiceBean implements FlujoTramitacionService {

	@Autowired
	private FlujoTramitacionService flujoTramitacionService;

	@Override
	public String iniciarTramite(final String idTramite, final int version, final String idioma,
			final String idTramiteCatalogo, final String urlInicio, final Map<String, String> parametrosInicio) {
		return flujoTramitacionService.iniciarTramite(idTramite, version, idioma, idTramiteCatalogo, urlInicio,
				parametrosInicio);
	}

	@Override
	public DetalleTramite obtenerDetalleTramite(final String idSesionTramitacion) {
		return flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);
	}

	@Override
	public DetalleTramite obtenerFlujoTramitacionInfo(final String idSesionTramitacion) {
		return flujoTramitacionService.obtenerFlujoTramitacionInfo(idSesionTramitacion);
	}

	@Override
	public void invalidarFlujoTramitacion(final String idSesionTramitacion) {
		flujoTramitacionService.invalidarFlujoTramitacion(idSesionTramitacion);
	}

	@Override
	public ResultadoIrAPaso cargarTramite(String idSesionTramitacion) {
		return flujoTramitacionService.cargarTramite(idSesionTramitacion);
	}

	@Override
	public ResultadoIrAPaso recargarTramite(String idSesionTramitacion) {
		return flujoTramitacionService.recargarTramite(idSesionTramitacion);
	}

	@Override
	public ResultadoIrAPaso irAPaso(String idSesionTramitacion, String idPaso) {
		return flujoTramitacionService.irAPaso(idSesionTramitacion, idPaso);
	}

	@Override
	public ResultadoAccionPaso accionPaso(String idSesionTramitacion, String idPaso, TypeAccionPaso accionPaso,
			ParametrosAccionPaso parametros) {
		return flujoTramitacionService.accionPaso(idSesionTramitacion, idPaso, accionPaso, parametros);
	}

	@Override
	public void cancelarTramite(String idSesionTramitacion) {
		flujoTramitacionService.cancelarTramite(idSesionTramitacion);
	}

}
