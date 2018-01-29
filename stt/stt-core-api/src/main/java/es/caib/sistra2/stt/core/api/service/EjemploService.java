package es.caib.sistra2.stt.core.api.service;

import es.caib.sistra2.stt.core.api.model.EjemploDto;

public interface EjemploService {

	public EjemploDto recuperaDato();
	public EjemploDto recuperaDato(String origen);
}
