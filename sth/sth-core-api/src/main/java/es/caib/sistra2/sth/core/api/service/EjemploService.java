package es.caib.sistra2.sth.core.api.service;

import es.caib.sistra2.sth.core.api.model.EjemploDto;

public interface EjemploService {

	public EjemploDto recuperaDato();
	public EjemploDto recuperaDato(String origen);
}
