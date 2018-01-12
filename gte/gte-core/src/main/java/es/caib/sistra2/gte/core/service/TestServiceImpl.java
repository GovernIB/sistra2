package es.caib.sistra2.gte.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import es.caib.sistra2.gte.core.api.exception.TestException;
import es.caib.sistra2.gte.core.api.model.TestData;
import es.caib.sistra2.gte.core.api.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	
	private static List<TestData> DB;
	
	@PostConstruct
	private void initialize() {
		DB = new ArrayList<TestData>();
		for (int i = 0; i < 50; i++) {
			TestData td = new TestData();
			td.setCodigo(i+"");
			td.setDescripcion("Data " + i);
			DB.add(td);
		}
	}
	
	@Override
	public List<TestData> list(String filtro) {		
		ArrayList<TestData> result = new ArrayList<TestData>();
		for (TestData td : DB) {
			if (filtro == null || filtro.trim().length() == 0 ||  td.getDescripcion().toLowerCase().indexOf(filtro) != -1) {
				result.add(cloneTestData(td));			
			}
		}			
		return result;
	}

	@Override
	public TestData load(String id) {
		TestData result = null;		
		for (TestData td : DB) {
			if (td.getCodigo().equals(id)) {
				result = cloneTestData(td);
				break;
			}
		}
		return result;
	}

	@Override
	public void add(TestData testData) {
		if (load(testData.getCodigo()) != null) {
			throw new TestException();
		}
		DB.add(cloneTestData(testData));
		
	}

	@Override
	public void remove(String id) {
		int index = 0;
		for (TestData td : DB) {
			if (td.getCodigo().equals(id)) {
				DB.remove(index);
				break;
			}
			index++;
		}		
		
	}

	@Override
	public void update(TestData testData) {
		for (TestData td : DB) {
			if (td.getCodigo().equals(testData.getCodigo())) {
				td.setDescripcion(testData.getDescripcion());
				break;
			}
		}
	}

	private TestData cloneTestData(TestData td) {
		TestData result;
		result = new TestData();
		result.setCodigo(td.getCodigo());
		result.setDescripcion(td.getDescripcion());
		return result;
	}


}
