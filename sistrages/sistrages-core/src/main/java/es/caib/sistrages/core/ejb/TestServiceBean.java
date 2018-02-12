package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.TestData;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.TestService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
public class TestServiceBean implements TestService {

	@Autowired
	TestService testService;

	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN,ConstantesRolesAcceso.ADMIN_ENT})
	public List<TestData> list(String filtro) {
		return testService.list(filtro);
	}

	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN,ConstantesRolesAcceso.ADMIN_ENT})
	public TestData load(String id) {
		return testService.load(id);
	}

	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN,ConstantesRolesAcceso.ADMIN_ENT})
	public void add(TestData testData) {
		testService.add(testData);
	}

	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN,ConstantesRolesAcceso.ADMIN_ENT})
	public void remove(String id) {
		testService.remove(id);
	}

	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN,ConstantesRolesAcceso.ADMIN_ENT})
	public void update(TestData testData) {
		testService.update(testData);
	}

	@Override
	@RolesAllowed({ConstantesRolesAcceso.SUPER_ADMIN,ConstantesRolesAcceso.ADMIN_ENT})
	public void testTransactionNew() {
		testService.testTransactionNew();
	}

}
