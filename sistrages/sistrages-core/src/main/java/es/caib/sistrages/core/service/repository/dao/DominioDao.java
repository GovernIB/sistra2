package es.caib.sistrages.core.service.repository.dao;

import java.util.Collection;
import java.util.List;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;

/**
 * La interface DominioDao.
 */
public interface DominioDao {

	/**
	 * Obtiene el valor de byId.
	 *
	 * @param idDominio the id dominio
	 * @return el valor de byId
	 */
	Dominio getByCodigo(final Long idDominio);

	/**
	 * Obtiene el valor de all.
	 *
	 * @param ambito the ambito
	 * @param id     the id
	 * @return el valor de all
	 */
	List<Dominio> getAll(TypeAmbito ambito, Long id);

	/**
	 * Obtiene el valor de allByFiltro.
	 *
	 * @param ambito the ambito
	 * @param id     the id
	 * @param filtro the filtro
	 * @return el valor de allByFiltro
	 */
	List<Dominio> getAllByFiltro(TypeAmbito ambito, Long id, String filtro);

	/**
	 * Obtiene el valor de allByFuenteDatos.
	 *
	 * @param idFuenteDatos the id fuente datos
	 * @return el valor de las id de los dominios que tienen esa fuente de datos
	 */
	List<String> getAllByFuenteDatos(Long idFuenteDatos);

	/**
	 * Añade.
	 *
	 * @param entidad   the entidad
	 * @param idEntidad the id entidad
	 * @param idArea    the id area
	 */
	Long add(final Dominio entidad, final Long idEntidad, final Long idArea);

	/**
	 * Elimina.
	 *
	 * @param idDominio the id dominio
	 */
	boolean remove(final Long idDominio);

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
	void updateDominio(Dominio dominio);

	/**
	 * Añade tramite version.
	 *
	 * @param idDominio        the id dominio
	 * @param idTramiteVersion the id tramite version
	 */
	void addTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Elimina tramite version.
	 *
	 * @param idDominio        the id dominio
	 * @param idTramiteVersion the id tramite version
	 */
	void removeTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Tiene tramite version.
	 *
	 * @param idDominio        the id dominio
	 * @param idTramiteVersion the id tramite version
	 * @return true, if successful
	 */
	boolean tieneTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Obtiene lista de dominios.
	 *
	 * @param idTramite idtramite
	 * @param filtro    filtro
	 * @return lista de dominios
	 */
	List<Dominio> getAllByFiltro(Long idTramite, String filtro);

	/**
	 * Importa un DAO según las condiciones.
	 *
	 * @param filaDominio
	 * @param idEntidad
	 * @param idArea
	 * @return El codigo del dominio, tanto cuando se mantiene como cuando se
	 *         reemplaza o crea.
	 * @throws Exception
	 */
	Long importar(FilaImportarDominio filaDominio, Long idEntidad, Long idArea, final JFuenteDatos jfuenteDatos);

	/**
	 * Clona un dominio.
	 *
	 * @param dominioID          Codigo del dominio
	 * @param nuevoIdentificador Nuevo identificador del dominio
	 * @param areaID             Codigo del area si es necesaria.
	 * @param idEntidad          Codigo de la entidad si de ambito entidad.
	 * @param fd				 Fuente de datos a reemplazar
	 */
	void clonar(String dominioID, final String nuevoIdentificador, Long areaID, final Long idEntidad,  final FuenteDatos fd, final ConfiguracionAutenticacion confAut);

	/**
	 * Obtiene el valores dominio.
	 *
	 * @param ambito
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param identificador
	 * @param identificadorEntidad
	 * @param identificadorArea
	 * @return
	 */

	ValoresDominio realizarConsultaListaFija(final TypeAmbito ambito, final Long codigoEntidad, final Long codigoArea,
			final String identificador, String identificadorEntidad, String identificadorArea);

	/**
	 * Borra todos las referencias entre dominios y trámites (normalmente producido
	 * por un cambio de area al mover trámite o clonar)
	 *
	 * @param idTramite
	 * @param idArea
	 */
	void borrarReferencias(Long idTramite, Long idArea);

	/**
	 * Devuelve la lista de dominios que tiene esa configuracion
	 *
	 * @param idConfiguracion
	 * @param idArea
	 * @return
	 */
	List<Dominio> getDominiosByConfAut(TypeAmbito ambito, Long idConfiguracion, Long idArea);

	/**
	 * Devuelve los dominios que tienen ese identificador.
	 *
	 * @param identificadoresDominio
	 * @return
	 */
	List<Dominio> getDominiosByIdentificador(List<String> identificadoresDominio, final Long idEntidad,
			final Long idArea);

	/**
	 * Devuelve los datos que coinciden con el patrón
	 *
	 * @param filtro
	 * @param idioma
	 * @param idEntidad
	 * @param idArea
	 * @param checkAmbitoGlobal
	 * @param checkAmbitoEntidad
	 * @param checkAmbitoArea
	 * @return
	 */
	public List<ConsultaGeneral> listar(String filtro, TypeIdioma idioma, Long idEntidad, Long idArea,
			boolean checkAmbitoGlobal, boolean checkAmbitoEntidad, boolean checkAmbitoArea);

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
	public boolean existeDominioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio);

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

	public Dominio getDominioByIdentificador(TypeAmbito ambito, String identificador, String identificadorEntidad,
			String identificadorArea, Long codigoEntidad, Long codigoArea, Long codigoDominio);

}