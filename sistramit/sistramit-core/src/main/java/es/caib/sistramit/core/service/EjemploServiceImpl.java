package es.caib.sistramit.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.caib.sistramit.core.api.model.EjemploDto;
import es.caib.sistramit.core.api.service.EjemploService;

@Service
public class EjemploServiceImpl implements EjemploService {

	@Override
	public EjemploDto recuperaDato() {
		return new EjemploDto("Spring EJB");
	}
}
