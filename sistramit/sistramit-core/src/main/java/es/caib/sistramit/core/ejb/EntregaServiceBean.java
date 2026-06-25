package es.caib.sistramit.core.ejb;

import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;
import es.caib.sistramit.core.api.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors({
		SpringBeanAutowiringInterceptor.class,
		EJBExceptionInterceptor.class
})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EntregaServiceBean implements EntregaService {

	@Autowired
	private EntregaService entregaService;

	@Override
	public ResultadoProcesoProgramado procesarEnvioRemotoFinalizadosInmediatos() {
		return entregaService.procesarEnvioRemotoFinalizadosInmediatos();
	}

	@Override
	public ResultadoProcesoProgramado procesarEnvioRemotoFinalizadosPeriodicos() {
		return entregaService.procesarEnvioRemotoFinalizadosPeriodicos();
	}

	@Override
	public ResultadoProcesoProgramado procesarFuncionarioHabilitadoFinalizados() {
		return entregaService.procesarFuncionarioHabilitadoFinalizados();
	}
}
