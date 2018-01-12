package es.caib.sistra2.gte.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistra2.gte.core.api.model.TestData;
import es.caib.sistra2.gte.core.api.service.TestService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class TestServiceBean implements TestService {

	@Autowired
	TestService testService;

	@Override
	@RolesAllowed("STR2_TEST")
	public List<TestData> list(String filtro) {
		return testService.list(filtro);
	}

	@Override
	@RolesAllowed("STR2_TEST")
	public TestData load(String id) {
		return testService.load(id);
	}

	@Override
	@RolesAllowed("STR2_TEST")
	public void add(TestData testData) {
		testService.add(testData);	
	}

	@Override
	@RolesAllowed("STR2_TEST")
	public void remove(String id) {
		testService.remove(id);
	}

	@Override
	@RolesAllowed("STR2_TEST")
	public void update(TestData testData) {
		testService.update(testData);
	}

}
