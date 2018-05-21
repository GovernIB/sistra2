package es.caib.sistramit.core.ejb;

import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FlujoTramitacionServiceBean implements FlujoTramitacionService {

	@Autowired
	FlujoTramitacionService flujoTramitacionService;

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

}
