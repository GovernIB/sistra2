package es.caib.sistrahelp.core.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrahelp.core.api.service.HistorialAlertaService;

/**
 * La clase HistorialAlertaServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class HistorialAlertaServiceBean implements HistorialAlertaService {

	/**
	 * HistorialAlerta service.
	 */
	@Autowired
	HistorialAlertaService historialAlertaService;

	@Override
	@PermitAll
	public HistorialAlerta loadHistorialAlerta(final Long codVa) {
		return historialAlertaService.loadHistorialAlerta(codVa);
	}

	@Override
	@PermitAll
	public Long addHistorialAlerta(final HistorialAlerta va) {
		return historialAlertaService.addHistorialAlerta(va);
	}

	@Override
	@PermitAll
	public boolean removeHistorialAlerta(final Long idVa) {
		return historialAlertaService.removeHistorialAlerta(idVa);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public void updateHistorialAlerta(final HistorialAlerta va) {
		historialAlertaService.updateHistorialAlerta(va);
	}

	@Override
	@PermitAll
	public List<HistorialAlerta> listHistorialAlerta(final Date desde, final Date hasta) {
		return historialAlertaService.listHistorialAlerta(desde, hasta);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.HELPDESK, ConstantesRolesAcceso.SUPERVISOR_ENTIDAD })
	public HistorialAlerta loadHistorialAlertaByAlerta(Long al) {
		return historialAlertaService.loadHistorialAlertaByAlerta(al);
	}

}
