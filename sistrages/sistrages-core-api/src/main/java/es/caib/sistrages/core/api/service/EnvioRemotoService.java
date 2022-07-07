package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * Dominio service.
 *
 * @author Indra.
 *
 */
public interface EnvioRemotoService {

	/**
	 * Obtener dominio.
	 *
	 * @param codDominio codigo del dominio
	 * @return dominio
	 */
	public EnvioRemoto loadEnvio(Long codEnvio);

	/**
	 * Comprueba si en un ambito, existe un identificador.
	 *
	 * @param ambito        Ambito (Entidad, Area, Global)
	 * @param identificador Identificador del dominio
	 * @param codigoEntidad Código de la entidad (necesario si es ambito entidad)
	 * @param codigoArea    Código del área (necesario si es ambito area)
	 * @param codigoDominio Código del propio dominio (en caso de alta, es nulo)
	 * @return
	 */
	public boolean existeEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoEnvio);
	/**
	 * Devuelve si en un ambito, existe un identificador.
	 *
	 * @param ambito        Ambito (Entidad, Area, Global)
	 * @param identificador Identificador del dominio
	 * @param codigoEntidad Código de la entidad (necesario si es ambito entidad)
	 * @param codigoArea    Código del área (necesario si es ambito area)
	 * @param codigoDominio Código del propio dominio (en caso de alta, es nulo)
	 * @return
	 */
	public EnvioRemoto getEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoEnvio);

	/**
	 * <p>
	 * Comprueba si en un ambito, existe un identificador.
	 * </p>
	 * <p>
	 * IMPORTANTE, SI NO SE PASAN BIEN TODOS LOS DATOS, SI LA QUERY DEVUELVE MÁS DE
	 * UN DATO, ENTONCES SE DEVUELVE EL PRIMERO DE ELLOS
	 * </p>
	 *
	 * @param ambito        Ambito (Entidad, Area, Global)
	 * @param identificador Identificador del dominio
	 * @param codigoEntidad Código de la entidad (necesario si es ambito entidad)
	 * @param codigoArea    Código del área (necesario si es ambito area)
	 * @param codigoDominio Código del propio dominio (en caso de alta, es nulo)
	 * @return
	 */
	public EnvioRemoto loadEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoEnvio);

	/**
	 * Añade dominio.
	 *
	 * @param dominio   Dominio a crear.
	 * @param idEntidad Id de la entidad
	 * @param idArea    Id del area.
	 */
	public Long addEnvio(EnvioRemoto envio, Long idEntidad, Long idArea);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio Dominio con los datos requeridos por superadministrador.
	 */
	public void updateEnvio(EnvioRemoto envio);

	/**
	 * Elimina dominio.
	 *
	 * @param idDominio the id dominio
	 * @return true, si se realiza correctamente
	 */
	public boolean removeEnvio(Long idEnvio);

	/**
	 * Listar dominios.
	 *
	 * @param ambito Ambito GLOBAL(G), ENTIDAD(E) o AREA(A)
	 * @param id     Id de la entidad o area
	 * @param filtro Filro aplicado al código o descripcion.
	 * @return lista de dominios
	 */
	public List<EnvioRemoto> listEnvio(final TypeAmbito ambito, final Long id, String filtro);

	/**
	 * Clonar dominio.
	 *
	 * @param dominioID
	 * @param nuevoIdentificador
	 * @param areaID
	 * @param fdID
	 * @param idEntidad
	 */
	public void clonar(String envioID, String nuevoIdentificador, Long areaID, Long fdID, final Long idEntidad);

	/**
	 * Devuelve los dominios remotos que tienen esa configuracion.
	 *
	 * @param valueOf
	 * @return
	 */
	public List<EnvioRemoto> getEnviosByConfAut(Long idConfiguracion, Long idArea);

	/**
	 * Obtiene dominios segun identificadores
	 *
	 * @param identificadoresDominio
	 * @return
	 */
	public List<EnvioRemoto> getEnviosByIdentificador(List<String> identificadoresEnvio, final Long idEntidad,
			final Long idArea);

	/**
	 * Obtiene el dominio segun el identificador compuesto.
	 *
	 * @param identificador
	 * @return
	 */
	public EnvioRemoto loadEnvioByIdentificadorCompuesto(String identificador);

}
