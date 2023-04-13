package es.caib.sistrahelp.core.ejb;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.stereotype.Repository;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrahelp.core.api.service.AlertaService;

/**
 * La clase alertaServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AlertaServiceBean implements AlertaService {

	/**
	 * Alerta service.
	 */
	@Autowired
	AlertaService alertaService;

	@Override
	@PermitAll
	public Alerta loadAlerta(final Long codVa) {
		return alertaService.loadAlerta(codVa);
	}

	@Override
	@PermitAll
	public Long addAlerta(final Alerta va) {
		return alertaService.addAlerta(va);
	}

	@Override
	@PermitAll
	public boolean removeAlerta(final Long idVa) {
		return alertaService.removeAlerta(idVa);
	}

	@Override
	@PermitAll
	public void updateAlerta(final Alerta va) {
		alertaService.updateAlerta(va);
	}

	@Override
	@PermitAll
	public List<Alerta> listAlerta(final String filtro) {
		return alertaService.listAlerta(filtro);
	}

	@Override
	@PermitAll
	public List<Alerta> listAlertaActivo(final String filtro, final boolean activo) {
		return alertaService.listAlertaActivo(filtro, activo);
	}

	@Override
	@PermitAll
	public Alerta loadAlertaByNombre(String identificador) {
		return alertaService.loadAlertaByNombre(identificador);
	}

}
