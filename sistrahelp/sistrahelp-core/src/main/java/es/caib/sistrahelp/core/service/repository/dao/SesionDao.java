package es.caib.sistrahelp.core.service.repository.dao;

import es.caib.sistrahelp.core.api.model.Sesion;

public interface SesionDao {

	Sesion getByUser(String pUsername);

	void updatePropiedades(String pUsername, String pPropiedades);
}
