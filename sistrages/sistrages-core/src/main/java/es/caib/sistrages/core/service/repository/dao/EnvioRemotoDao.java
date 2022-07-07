package es.caib.sistrages.core.service.repository.dao;

import java.util.Collection;
import java.util.List;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;

/**
 * La interface DominioDao.
 */
public interface EnvioRemotoDao {

	/**
	 * Obtiene el valor de byId.
	 *
	 * @param idDominio the id dominio
	 * @return el valor de byId
	 */
	EnvioRemoto getByCodigo(final Long idEnvio);

	/**
	 * Obtiene el valor de all.
	 *
	 * @param ambito the ambito
	 * @param id     the id
	 * @return el valor de all
	 */
	List<EnvioRemoto> getAll(TypeAmbito ambito, Long id);

	/**
	 * Obtiene el valor de allByFiltro.
	 *
	 * @param ambito the ambito
	 * @param id     the id
	 * @param filtro the filtro
	 * @return el valor de allByFiltro
	 */
	List<EnvioRemoto> getAllByFiltro(TypeAmbito ambito, Long id, String filtro);

	/**
	 * Añade.
	 *
	 * @param entidad   the entidad
	 * @param idEntidad the id entidad
	 * @param idArea    the id area
	 */
	Long add(final EnvioRemoto envio, final Long idEntidad, final Long idArea);

	/**
	 * Elimina.
	 *
	 * @param idDominio the id dominio
	 */
	boolean remove(final Long idEnvio);

	/**
	 * Elimina by entidad.
	 *
	 * @param idEntidad the id entidad
	 */
	void removeByEntidad(final Long idEntidad);

	/**
	 * Elimina by area.
	 *
	 * @param idArea the id area
	 */
	void removeByArea(final Long idArea);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio the dominio
	 */
	void updateEnvio(EnvioRemoto envio);

	/**
	 * Clona un dominio.
	 *
	 * @param dominioID          Codigo del dominio
	 * @param nuevoIdentificador Nuevo identificador del dominio
	 * @param areaID             Codigo del area si es necesaria.
	 * @param fdID               Codigo de la FD si es necesaria.
	 * @param idEntidad          Codigo de la entidad si de ambito entidad.
	 */
	void clonar(String envioID, final String nuevoIdentificador, Long areaID, Long fdID, final Long idEntidad);

	/**
	 * Devuelve la lista de dominios que tiene esa configuracion
	 *
	 * @param idConfiguracion
	 * @param idArea
	 * @return
	 */
	List<EnvioRemoto> getEnviosByConfAut(Long idConfiguracion, Long idArea);

	/**
	 * Devuelve los dominios que tienen ese identificador.
	 *
	 * @param identificadoresDominio
	 * @return
	 */
	List<EnvioRemoto> getEnviosByIdentificador(List<String> identificadoresEnvio, final Long idEntidad,
			final Long idArea);

	/**
	 * Comprueba si existe un dominio según el identificador.
	 *
	 * @param ambito
	 * @param identificador
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param codigoDominio
	 * @return
	 */
	public boolean existeEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoEnvio);

	/**
	 * Obtiene el dominio según el identificador.
	 *
	 * @param ambito
	 * @param identificador
	 * @param identificadorEntidad
	 * @param identificadorArea
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param codigoDominio
	 * @return
	 */

	public EnvioRemoto getEnvioByIdentificador(TypeAmbito ambito, String identificador, String identificadorEntidad,
			String identificadorArea, Long codigoEntidad, Long codigoArea, Long codigoDominio);

	List<EnvioRemoto> listEnviosByEntidad(Long idEntidad);

}