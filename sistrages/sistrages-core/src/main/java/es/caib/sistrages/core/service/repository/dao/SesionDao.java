package es.caib.sistrages.core.service.repository.dao;

import es.caib.sistrages.core.api.model.Sesion;

public interface SesionDao {

	Sesion getByUser(String pUsername);

	void updatePerfil(String pUsername, String pPerfil);

	void updateIdioma(String pUsername, String pIdioma);

	void updateEntidad(String pUsername, Long pIdEntidad);

}
