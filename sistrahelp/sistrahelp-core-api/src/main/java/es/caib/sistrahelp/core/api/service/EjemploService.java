package es.caib.sistrahelp.core.api.service;

import es.caib.sistrahelp.core.api.model.EjemploDto;

public interface EjemploService {

	public EjemploDto recuperaDato();
	public EjemploDto recuperaDato(String origen);
}
