package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.ConsultaGeneralService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ConsultaGeneralServiceBean implements ConsultaGeneralService {

	@Autowired
	ConsultaGeneralService consultaGeneralService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<ConsultaGeneral> listar(String filtro,TypeIdioma typeIdioma, Long idEntidad, Long idArea,boolean checkAmbitoGlobal, boolean checkAmbitoEntidad,
			boolean checkAmbitoArea, boolean checkDominios, boolean checkConfAut, boolean checkGFE) {
		return consultaGeneralService.listar(filtro, typeIdioma, idEntidad, idArea, checkAmbitoGlobal, checkAmbitoEntidad, checkAmbitoArea, checkDominios, checkConfAut, checkGFE);
	}
}
