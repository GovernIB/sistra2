package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;

import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.VariableArea;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;

import es.caib.sistrages.core.api.service.VariablesAreaService;

/**
 * La clase variablesAreaServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class VariblesAreaServiceBean implements VariablesAreaService {

	/**
	 * VariableArea service.
	 */
	@Autowired
	VariablesAreaService variablesAreaService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public VariableArea loadVariableArea(final Long codVa) {
		return variablesAreaService.loadVariableArea(codVa);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Long addVariableArea(final VariableArea va) {
		return variablesAreaService.addVariableArea(va);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeVariableArea(final Long idVa) {
		return variablesAreaService.removeVariableArea(idVa);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateVariableArea(final VariableArea va) {
		variablesAreaService.updateVariableArea(va);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<VariableArea> listVariableArea(final Long area, final String filtro) {
		return variablesAreaService.listVariableArea(area, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public VariableArea loadVariableAreaByIdentificador(String identificador, Long codigoArea) {
		return variablesAreaService.loadVariableAreaByIdentificador(identificador, codigoArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Dominio> dominioByVariable(final VariableArea va) {
		return variablesAreaService.dominioByVariable(va);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<GestorExternoFormularios> gfeByVariable(final VariableArea va) {
		return variablesAreaService.gfeByVariable(va);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<EnvioRemoto> envioRemotoByVariable(final VariableArea va) {
		return variablesAreaService.envioRemotoByVariable(va);
	}
}
