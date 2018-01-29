package es.caib.sistra2.sth.core.service;

import org.springframework.stereotype.Service;

import es.caib.sistra2.sth.core.api.model.EjemploDto;
import es.caib.sistra2.sth.core.api.service.EjemploService;

@Service("ejemploService")
public class EjemploServiceImpl implements EjemploService {

	@Override
	public EjemploDto recuperaDato() {
		return recuperaDato(null);
	}

	@Override
	public EjemploDto recuperaDato(String origen) {
		if (origen == null) {
			return new EjemploDto("Spring");
		}
		else {
			return new EjemploDto("EJB");
		}
	}

}
