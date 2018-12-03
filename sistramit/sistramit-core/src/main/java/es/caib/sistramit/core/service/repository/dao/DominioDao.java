package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;

/**
 * Interface de acceso a base de datos para servicios de dominio.
 */
public interface DominioDao {

	/**
	 * Devuelve los valores dominio de una consulta BBDD
	 *
	 * @param jndi
	 * @param jndiPath
	 * @param sql
	 * @param parametrosDominio
	 * @return
	 */
	public ValoresDominio realizarConsultaBD(final String jndi, final String sql,
			final ParametrosDominio parametrosDominio);

}
