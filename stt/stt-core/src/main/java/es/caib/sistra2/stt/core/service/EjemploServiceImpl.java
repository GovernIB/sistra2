package es.caib.sistra2.stt.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.caib.sistra2.stt.core.api.model.EjemploDto;
import es.caib.sistra2.stt.core.api.service.ContextService;
import es.caib.sistra2.stt.core.api.service.EjemploService;

@Service
public class EjemploServiceImpl implements EjemploService {

	@Autowired
	ContextService contextService;

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
			//return new EjemploDto(origen);
			// Obtenemos usuario y verificamos role
			return new EjemploDto(origen + " user: " + contextService.getUsername() + " - " + contextService.hashRole("STR2_TEST"));
		}
	}

}
