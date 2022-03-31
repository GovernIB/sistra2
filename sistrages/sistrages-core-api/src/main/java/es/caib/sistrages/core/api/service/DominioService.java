package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * Dominio service.
 *
 * @author Indra.
 *
 */
public interface DominioService {

	/**
	 * Obtener dominio.
	 *
	 * @param codDominio codigo del dominio
	 * @return dominio
	 */
	public Dominio loadDominio(Long codDominio);

	/**
	 * Comprueba si en un ambito, existe un identificador.
	 *
	 * @param ambito Ambito (Entidad, Area, Global)
	 * @param identificador Identificador del dominio
	 * @param codigoEntidad Código de la entidad (necesario si es ambito entidad)
	 * @param codigoArea Código del área (necesario si es ambito area)
	 * @param codigoDominio  Código del propio dominio (en caso de alta, es nulo)
	 * @return
	 */
	public boolean existeDominioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoDominio);

	/**
	 * <p>Comprueba si en un ambito, existe un identificador.</p>
	 * <p>IMPORTANTE, SI NO SE PASAN BIEN TODOS LOS DATOS, SI LA QUERY DEVUELVE MÁS DE UN DATO, ENTONCES SE DEVUELVE EL PRIMERO DE ELLOS </p>
	 *
	 * @param ambito Ambito (Entidad, Area, Global)
	 * @param identificador Identificador del dominio
	 * @param codigoEntidad Código de la entidad (necesario si es ambito entidad)
	 * @param codigoArea Código del área (necesario si es ambito area)
	 * @param codigoDominio  Código del propio dominio (en caso de alta, es nulo)
	 * @return
	 */
	public Dominio loadDominioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea, Long codigoDominio);

	/**
	 * Añade dominio.
	 *
	 * @param dominio   Dominio a crear.
	 * @param idEntidad Id de la entidad
	 * @param idArea    Id del area.
	 */
	public Long addDominio(Dominio dominio, Long idEntidad, Long idArea);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio Dominio con los datos requeridos por superadministrador.
	 */
	public void updateDominio(Dominio dominio);

	/**
	 * Elimina dominio.
	 *
	 * @param idDominio the id dominio
	 * @return true, si se realiza correctamente
	 */
	public boolean removeDominio(Long idDominio);

	/**
	 * Listar dominios.
	 *
	 * @param ambito Ambito GLOBAL(G), ENTIDAD(E) o AREA(A)
	 * @param id     Id de la entidad o area
	 * @param filtro Filro aplicado al código o descripcion.
	 * @return lista de dominios
	 */
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, String filtro);

	/**
	 * Listar dominios.
	 *
	 * @param idTramite Id de la entidad o area
	 * @param filtro    Filro aplicado al código o descripcion.
	 * @return lista de dominios
	 */
	public List<Dominio> listDominio(final Long idTramite, String filtro);

	/**
	 * Obtener fuenteDato.
	 *
	 * @param idFuenteDato ID del fuenteDato
	 * @return fuenteDato
	 */
	public FuenteDatos loadFuenteDato(Long idFuenteDato);

	/**
	 * Obtener fuenteDato.
	 * @param ambito
	 * @param identificador
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param codigoFD
	 * @return
	 */

	public FuenteDatos loadFuenteDato(final TypeAmbito ambito, final String identificador, final Long codigoEntidad, final Long codigoArea, final Long codigoFD);

	/**
	 * Existe fuenteDato.
	 * @param ambito
	 * @param identificador
	 * @param codigoEntidad
	 * @param codigoArea
	 * @param codigoFD
	 * @return
	 */

	public boolean existeFuenteDato(final TypeAmbito ambito, final String identificador, final Long codigoEntidad, final Long codigoArea, final Long codigoFD);



	/**
	 * Obtener fuenteDato.
	 *
	 * @param idFuenteDato ID del fuenteDato
	 * @return fuenteDato
	 */
	public FuenteDatosValores loadFuenteDatoValores(Long idFuenteDato);

	/**
	 * Añade fuenteDato.
	 *
	 * @param fuenteDato FuenteDato a crear.
	 * @param idEntidad  Id de la entidad
	 * @param idArea     Id del area.
	 */
	public void addFuenteDato(FuenteDatos fuenteDato, Long idArea);

	/**
	 * Actualiza fuenteDato.
	 *
	 * @param fuenteDato FuenteDato con los datos requeridos por superadministrador.
	 */
	public void updateFuenteDato(FuenteDatos fuenteDato);

	/**
	 * Borrar fuenteDato.
	 *
	 * @param idFuenteDato idFuenteDato
	 */
	public boolean removeFuenteDato(Long idFuenteDato);

	/**
	 * Listar fuenteDatos.
	 *
	 * @param ambito Ambito GLOBAL(G), ENTIDAD(E) o AREA(A)
	 * @param id     Id de la entidad o area
	 * @param filtro Filro aplicado al código o descripcion.
	 * @return ambito
	 */
	public List<FuenteDatos> listFuenteDato(final TypeAmbito ambito, final Long id, String filtro);

	/**
	 * Obtiene la fuente fila.
	 *
	 * @param idFuenteDatoFila Id de la fuente fila
	 * @return fila
	 */
	public FuenteFila loadFuenteDatoFila(Long idFuenteDatoFila);

	/**
	 * Añade una fila.
	 *
	 * @param fila
	 * @param idFuente
	 */
	public void addFuenteDatoFila(FuenteFila fila, Long idFuente);

	/**
	 * Actualiza una fila.
	 *
	 * @param fila
	 *
	 */
	public void updateFuenteDatoFila(FuenteFila fila);

	/**
	 * Borra un fila.
	 *
	 * @param idFila
	 */
	public void removeFuenteFila(Long idFila);

	/**
	 * Estan todas las PK correctas? No hay repetidos vamos.
	 *
	 * @param fuenteFila
	 * @param idFuenteDato
	 * @return
	 */
	public boolean isCorrectoPK(FuenteFila fuenteFila, Long idFuenteDato);

	/**
	 * Importar csv a la fuente de datos.
	 *
	 * @param idFuenteDatos
	 * @param csv
	 */
	public void importarCSV(Long idFuenteDatos, CsvDocumento csv);

	/**
	 * Comprueba si un tramite versión ya está asignado.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 * @return
	 */
	public boolean tieneTramiteVersion(final Long idDominio, Long idTramiteVersion);

	/**
	 * Borra la relación entre trámite versión y dominio.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 */
	public void removeTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Añade la relación entre trámite versión y dominio.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 */
	public void addTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Importar dominio.
	 *
	 * @param filaDominio
	 * @param idEntidad
	 * @param idArea
	 * @throws Exception
	 */
	public void importarDominio(FilaImportarDominio filaDominio, final Long idEntidad, final Long idArea)
			throws Exception;

	/**
	 * Clonar dominio.
	 *
	 * @param dominioID
	 * @param nuevoIdentificador
	 * @param areaID
	 * @param fdID
	 * @param idEntidad
	 */
	public void clonar(String dominioID, String nuevoIdentificador, Long areaID, Long fdID, final Long idEntidad);

	/**
	 * Devuelve el identificador de los dominios con esa fuente de datos
	 *
	 * @param idFuenteDatos
	 * @return
	 */
	List<String> listDominiosByFD(Long idFuenteDatos);

	/**
	 * Devuelve los dominios remotos que tienen esa configuracion.
	 * @param valueOf
	 * @return
	 */
	public List<Dominio> getDominiosByConfAut(Long idConfiguracion, Long idArea);

	/**
	 * Obtiene dominios segun identificadores
	 * @param identificadoresDominio
	 * @return
	 */
	public List<Dominio> getDominiosByIdentificador(List<String> identificadoresDominio, final Long idEntidad, final Long idArea);

	/**
	 * Obtiene el dominio segun el identificador compuesto.
	 * @param identificador
	 * @return
	 */
	public Dominio loadDominioByIdentificadorCompuesto(String identificador);

}
