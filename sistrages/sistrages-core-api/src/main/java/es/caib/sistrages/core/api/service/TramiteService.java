package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizableTramite;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.comun.FilaImportar;
import es.caib.sistrages.core.api.model.comun.FilaImportarResultado;
import es.caib.sistrages.core.api.model.comun.ScriptInfo;
import es.caib.sistrages.core.api.model.comun.TramiteSimple;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;

/**
 * La interface TramiteService.
 */
public interface TramiteService {

	/**
	 * Lista de areas.
	 *
	 * @param idEntidad idEntidad
	 * @param filtro    filtro
	 * @return lista de areas
	 */
	public List<Area> listArea(final Long idEntidad, String filtro);

	/**
	 * Obtiene el area.
	 *
	 * @param id identificador
	 * @return area
	 */
	public Area getArea(Long id);

	/**
	 * Obtiene el area de un tramite.
	 *
	 * @param id identificador tramite
	 * @return area
	 */
	public Area getAreaTramite(Long idTramite);

	/**
	 * Añade un area.
	 *
	 * @param idEntidad idEntidad
	 * @param area      area
	 */
	public void addArea(Long idEntidad, Area area);

	/**
	 * Elimina un area.
	 *
	 * @param id identificador
	 * @return true, si se realiza con exito
	 */
	public boolean removeArea(Long id);

	/**
	 * Actualiza un area.
	 *
	 * @param area area
	 */
	public void updateArea(Area area);

	/**
	 * Lista de tramites.
	 *
	 * @param idArea  idArea
	 * @param pFiltro filtro
	 * @return lista de tramites
	 */
	public List<Tramite> listTramite(final Long idArea, String pFiltro);

	/**
	 * Obtiene el tramite.
	 *
	 * @param id identificador
	 * @return {@link Tramite}
	 */
	public Tramite getTramite(Long id);

	/**
	 * Añade un Tramite.
	 *
	 * @param idArea   identificador de area
	 * @param pTramite tramite
	 */
	public void addTramite(Long idArea, Tramite pTramite);

	/**
	 * Cambia un trámite de área.
	 *
	 * @param idArea        id área
	 * @param idTramite     id trámite
	 * @param idAreaAntigua id área antes asociado
	 * @param usuario       Nick del usuario
	 */
	public void changeAreaTramite(Long idArea, Long idTramite, final Long idAreaAntigua, final String usuario);

	/**
	 * Elimina un Tramite.
	 *
	 * @param id identificador
	 * @return true, si se realiza con exito
	 */
	public boolean removeTramite(Long id);

	/**
	 * Actualiza un Tramite.
	 *
	 * @param pTramite tramite
	 */
	public void updateTramite(Tramite pTramite);

	/**
	 * Lista de las versiones de un trámite.
	 *
	 * @param idTramite
	 * @return
	 */
	public List<TramiteVersion> listTramiteVersion(Long idTramite, String filtro);

	/**
	 * Crea una versión de trámite.
	 *
	 * @param tramiteVersion
	 * @param idTramite
	 * @param usuario
	 */
	public Long addTramiteVersion(TramiteVersion tramiteVersion, String idTramite, String usuario);

	/**
	 * Actualiza una versión de trámite.
	 *
	 * @param tramiteVersion
	 */
	public void updateTramiteVersion(TramiteVersion tramiteVersion);

	/**
	 * Actualiza control de acceso.
	 *
	 * @param tramiteVersion
	 * @param avisoEntidad
	 * @param idEntidad
	 */
	public void updateTramiteVersionControlAcceso(TramiteVersion tramiteVersion, AvisoEntidad avisoEntidad,
			final Long idEntidad);

	/**
	 * Borra una versión de tramite.
	 *
	 * @param id
	 * @param numVersion
	 */
	public void removeTramiteVersion(Long id, int numVersion);

	/**
	 * Obtiene la versión de trámite.
	 *
	 * @param id
	 * @return
	 */
	public TramiteVersion getTramiteVersion(Long idTramiteVersion);

	/**
	 * Obtiene una version de tramite a partir de un num version y un tramite.
	 *
	 * @param numeroVersion
	 * @param idTramite
	 * @return
	 */
	public TramiteVersion getTramiteVersionByNumVersion(final int numeroVersion, final Long idTramite);

	/**
	 * Devuelve los pasos de una versión de trámite
	 *
	 * @param id
	 * @return
	 */
	public List<TramitePaso> getTramitePasos(Long idTramiteVersion);

	/**
	 * Devuelve los dominios de un trámite.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public List<Dominio> getDominioSimpleByTramiteId(Long idTramiteVersion);

	/**
	 * Actualiza un paso de trámite.
	 *
	 * @param tramitePaso
	 */
	public void updateTramitePaso(TramitePaso tramitePaso);

	/**
	 * Borra un formulario de un paso.
	 *
	 * @param idTramitePaso
	 * @param idFormulario
	 */
	public void removeFormulario(Long idTramitePaso, Long idFormulario);

	/**
	 * Obtiene el tramitePaso.
	 *
	 * @param id
	 * @return
	 */
	public TramitePaso getTramitePaso(Long id);

	/**
	 * Devuelve un formulario trámite.
	 *
	 * @param idFormularioTramite
	 * @return
	 */
	public FormularioTramite getFormulario(Long idFormularioTramite);

	/**
	 * Añade un tramite a un paso.
	 *
	 * @param formularioTramite
	 * @param idTramitePaso
	 * @return Id del formulario
	 */
	public FormularioTramite addFormularioTramite(FormularioTramite formularioTramite, Long idTramitePaso);

	/**
	 * Actualiza formulario.
	 *
	 * @param formularioTramite
	 */
	public void updateFormularioTramite(FormularioTramite formularioTramite);

	/**
	 * Obtiene un documento.
	 *
	 * @param idDocumento
	 * @return
	 */
	public Documento getDocumento(Long idDocumento);

	/**
	 * Añade un documento a un paso.
	 *
	 * @param documento
	 * @param idTramitePaso
	 * @return Id del documento
	 */
	public Documento addDocumentoTramite(Documento documento, Long idTramitePaso);

	/**
	 * Actualiza un documento.
	 *
	 * @param documento
	 */
	public void updateDocumentoTramite(Documento documento);

	/**
	 * Borrar documento.
	 *
	 * @param idTramitePaso
	 * @param idDocumento
	 */
	public void removeDocumento(Long idTramitePaso, Long idDocumento);

	/**
	 * Obtiene una tasa.
	 *
	 * @param idTasa
	 * @return
	 */
	public Tasa getTasa(Long idTasa);

	/**
	 * Añade una tasa a un paso.
	 *
	 * @param tasa
	 * @param idTramitePaso
	 * @return Id de la tasa
	 */
	public Tasa addTasaTramite(Tasa tasa, Long idTramitePaso);

	/**
	 * Actualiza una tasa.
	 *
	 * @param tasa
	 */
	public void updateTasaTramite(Tasa tasa);

	/**
	 * Borra una tasa.
	 *
	 * @param idTramitePaso
	 * @param idTasa
	 */
	public void removeTasa(Long idTramitePaso, Long idTasa);

	/**
	 * Sube un documento asociado a un anexo.
	 *
	 * @param idDocumento
	 * @param fichero
	 * @param contents
	 * @param idEntidad
	 */
	public void uploadDocAnexo(Long idDocumento, Fichero fichero, byte[] contents, Long idEntidad);

	/**
	 * Borra el doc de un anexo.
	 *
	 * @param idDocumento
	 */
	public void removeDocAnexo(Long idDocumento);

	/**
	 * Bloquea un tramite
	 *
	 * @param idTramiteVersion
	 * @param username
	 */
	public void bloquearTramiteVersion(Long idTramiteVersion, String username);

	/**
	 * Desbloquea un tramite.
	 *
	 * @param idTramiteVersion
	 * @param username
	 * @param detalle
	 */
	public void desbloquearTramiteVersion(final Long idTramiteVersion, final String username, final String detalle);

	/**
	 * Obtiene una lista de historial de un tramite version.
	 *
	 * @param idTramiteVersion
	 * @param filtro
	 * @return
	 */
	public List<HistorialVersion> listHistorialVersion(Long idTramiteVersion, String filtro);

	/**
	 * Obtiene un historial version.
	 *
	 * @param idHistorialVersion
	 * @return
	 */
	public HistorialVersion getHistorialVersion(Long idHistorialVersion);

	/**
	 * Comprueba si tiene versiones de trámite.
	 *
	 * @param idTramite
	 * @return
	 */
	public boolean tieneTramiteVersion(Long idTramite);

	/**
	 * Comprueba si tiene la version repetida.
	 *
	 * @param idTramite
	 * @param version
	 * @return
	 */
	public boolean tieneTramiteNumVersionRepetida(Long idTramite, int numVersion);

	/**
	 * Obtiene la num version de un trámite.
	 *
	 * @param idTramite
	 * @return Release máxima, siendo 0 el valor por defecto.
	 */
	public int getTramiteNumVersionMaximo(Long idTramite);

	/**
	 * Clonar trámite version.
	 *
	 * @param idTramiteVersion
	 * @param usuario
	 * @param idArea
	 * @param idTramite
	 */
	public void clonadoTramiteVersion(Long idTramiteVersion, final String usuario, Long idArea, Long idTramite);

	/**
	 * Devuelve una lista con el id de los tramites que tengan una versión activa
	 * dentro de un area.
	 *
	 * @param idArea
	 * @return
	 */
	public List<Long> listTramiteVersionActiva(Long idArea);

	/**
	 * Devuelve los formateadores de un tramite version.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public List<FormateadorFormulario> getFormateadoresTramiteVersion(Long idTramiteVersion);

	/**
	 * Devuelve la lista de formularios de un tramite version.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public List<DisenyoFormulario> getFormulariosTramiteVersion(Long idTramiteVersion);

	/**
	 * Devuelve una lista de ficheros de un tramite version.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public List<Fichero> getFicherosTramiteVersion(Long idTramiteVersion);

	/**
	 * Obtiene area por identificador.
	 *
	 * @param identificador
	 * @return
	 */
	public Area getAreaByIdentificador(String identificadorEntidad, String identificador);

	/**
	 * Obtiene tramite por identificador.
	 *
	 * @param identificador
	 * @param idArea
	 * @param identificadorArea
	 * @param codigoTramite
	 * @return
	 */

	public Tramite getTramiteByIdentificador(String identificador, final Long idArea, String identificadorArea,
			Long codigoTramite);

	/**
	 * Obtiene el tramite version con el num. version más alto.
	 *
	 * @param idTramite
	 * @return
	 */
	public TramiteVersion getTramiteVersionMaxNumVersion(Long idTramite);

	/**
	 * Intercambia el orden de dos formularios.
	 *
	 * @param idFormulario1
	 * @param idFormulario2
	 */
	public void intercambiarFormularios(final Long idFormulario1, final Long idFormulario2);

	/**
	 * Obtiene la lista de tramite versión que tienen ese dominio.
	 *
	 * @param idDominio
	 * @return
	 */
	public List<DominioTramite> getTramiteVersionByDominio(Long idDominio);

	/**
	 * Obtiene la lista de tramite versión que tienen ese envio remoto.
	 *
	 * @param idDominio
	 * @return
	 */
	public List<DominioTramite> getTramiteVersionByEnvioRemoto(Long idEnvioRemoto);

	/**
	 * Obtiene la lista de tramite versión que tienen ese gestor de formulario
	 * externo.
	 *
	 * @param idDominio
	 * @return
	 */
	public List<DominioTramite> getTramiteVersionByGfe(Long idGfe);

	/**
	 * Mira si la tasa esta repetida.
	 *
	 * @param idTramiteVersion
	 * @param identificador
	 * @param idTasa           Puede ser nulo en caso de alta
	 * @return
	 */
	public boolean checkTasaRepetida(Long idTramiteVersion, String identificador, Long idTasa);

	/**
	 * Mira si el anexo esta repetido.
	 *
	 * @param idTramiteVersion
	 * @param identificador
	 * @param idAnexo          Puede ser nulo en caso de alta
	 * @return
	 */
	public boolean checkAnexoRepetido(Long idTramiteVersion, String identificador, Long idAnexo);

	/**
	 * Comprueba si el identificador está repetido.
	 *
	 * @param identificador
	 * @param codigo
	 * @return true, si existe
	 */
	public boolean checkIdentificadorRepetido(String identificador, Long codigo, Long idArea);

	/**
	 * Comprueba si el identificador de area está repetido.
	 *
	 * @param identificador
	 * @param codigo
	 * @return true, si existe
	 */
	public boolean checkIdentificadorAreaRepetido(String identificador, Long codigo);

	/**
	 * Métod que se encarga de importar un trámite.
	 *
	 * @param filaImportar
	 * @throws Exception
	 */
	public FilaImportarResultado importar(FilaImportar filaImportar) throws Exception;

	/**
	 * Comprueba si el identificador de formulario está repetido.
	 *
	 * @param idTramiteVersion
	 * @param identificador
	 * @param idFormulario
	 * @return
	 */
	boolean checkFormularioRepetido(Long idTramiteVersion, String identificador, Long idFormulario);

	/**
	 * Genera el tramitesimple
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public TramiteSimple getTramiteSimple(String idTramiteVersion);

	/**
	 * Obtiene los idiomas disponbiles.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public String getIdiomasDisponibles(String idTramiteVersion);

	/**
	 * Valida que no tenga errores una version de un tramite.
	 *
	 * @param pId     id. version tramite
	 * @param pIdioma idioma para mostrar los errores
	 * @return lista de errores de validacion
	 */
	public List<ErrorValidacion> validarVersionTramite(Long pId, String pIdioma);

	/**
	 * Valida que no tenga errores una version de un tramite.
	 *
	 * @param pTramiteVersion tramite version
	 * @param pIdioma         idioma para mostrar los errores
	 * @return lista de errores de validacion
	 */
	public List<ErrorValidacion> validarVersionTramite(TramiteVersion pTramiteVersion, String pIdioma);

	/**
	 * Valida que no tenga errores un script de un tramite.
	 *
	 * @param pScript                script a validar
	 * @param pListaDominios         lista dominios de la version
	 * @param pIdiomasTramiteVersion idiomas definidos en la version
	 * @param pIdioma                idioma para mostrar los errores
	 * @return lista de errores de validacion
	 */
	public List<ErrorValidacion> validarScript(Script pScript, List<Dominio> pListaDominios,
			List<String> pIdiomasTramiteVersion, String pIdioma);

	/**
	 * Lista de trámites de la entidad.
	 *
	 * @param idEntidad
	 * @return
	 */
	public List<Tramite> listTramiteByEntidad(Long idEntidad);

	/**
	 * Obtiene el identificador del trámite a partir del código de una version.
	 *
	 * @param tramTramite
	 * @return
	 */
	public String getIdentificadorByCodigoVersion(Long codigo);

	/**
	 * Método que activar/desactivar el permitir subsanar documentos a un paso del
	 * trámite.
	 *
	 * @param idPaso
	 * @param activarSubsanacion
	 */
	public void permiteSubsanacion(Long idPaso, boolean activarSubsanacion);

	/**
	 * Actualiza literal.
	 *
	 * @param pLiteral literal
	 */
	void updateLiteral(Literal pLiteral);

	/**
	 * Actualiza script.
	 *
	 * @param pScript script
	 */
	void updateScript(Script pScript);

	/**
	 * Crea TramiteVersion por defecto.
	 *
	 * @param pNumVersion          num version
	 * @param pIdiomasSoportados   idiomas soportados
	 * @param pDatosUsuarioBloqueo datos usuario bloqueo
	 * @return the tramite version
	 */

	TramiteVersion createTramiteVersionDefault(Integer pNumVersion, String pIdiomasSoportados,
			String pDatosUsuarioBloqueo);

	/**
	 * Crea lista de pasos normalizado.
	 *
	 * @return lista de pasos normalizado
	 */
	List<TramitePaso> createListaPasosNormalizado();

	Documento createDocumentoDefault();

	Tasa createTasaDefault();

	FormularioTramite createFormularioTramiteDefault();

	/**
	 * Borrado de historial
	 *
	 * @param idTramiteVersion
	 * @param username
	 */
	public void borradoHistorial(Long idTramiteVersion, final String username);

	/**
	 * Obtiene una lista de info de los scripts que están rellenos.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public List<ScriptInfo> listScriptsInfo(Long idTramiteVersion);

	/**
	 * Borra los scrips seleccionados.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public void borrarScriptsVersion(Long idTramiteVersion, boolean propiedades, boolean rellenar, boolean anexo,
			boolean tasas, boolean registrar, boolean propcaptura);

	/**
	 * Obtiene los formularios gestores externos dependiente el idTramiteVersion
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public List<GestorExternoFormularios> getGFEByTramiteVersion(Long idTramiteVersion);

	/**
	 * Busca los trámites según el id entidad, las areas y el filtro
	 *
	 * @param idEntidad
	 * @param areas
	 * @param filtro
	 * @return
	 */
	public List<Tramite> listTramite(Long idEntidad, List<Long> areas, String filtro);

	public int listTramiteTotal(Long idEntidad, List<Long> areas, String filtro);

	/**
	 * Busca los trámites según paginación
	 *
	 * @param idEntidad
	 * @param areas
	 * @param filtro
	 * @return
	 */
	public List<Tramite> listTramite(int first, int pageSize, String sortField, boolean asc, Long idEntidad,
			List<Long> areas, String filtro);

	/**
	 * Si algún identificador no está agregado, mira de añadirlo.
	 *
	 * @param idTramiteVersion
	 * @param dominios
	 */
	public void actualizarDominios(TramiteVersion idTramiteVersion, final List<ValorIdentificadorCompuesto> dominios);

	/**
	 * Devuelve la lista de datos que utilizan un dominio
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 * @param idioma
	 * @return
	 */
	public List<ErrorValidacion> checkDominioNoUtilizado(Long idDominio, Long idTramiteVersion, final String idioma);

	/**
	 * Devuelve si hay tramites asociados a la GFE
	 *
	 * @param id
	 * @return
	 */
	public boolean getCountTramiteVersionByGfe(Long id);

	/**
	 * Actualiza la fecha del tramite versión (cuando se toca cualquier cosa del trámite)
	 * @param idTramiteVersion
	 * @param username
	 * @param detalle
	 */
	void actualizarFechaTramiteVersion(Long idTramiteVersion, String username, String detalle);

	/**
	 * Devueve los tramites que utilizan la seccion reutilizable
	 * @param valueOf
	 * @return
	 */
	public List<SeccionReutilizableTramite> getTramiteVersionBySeccionReutilizable(Long idSeccionReutilizable);

	/**
	 * Lista de secciones reutilizables del tramite.
	 * @param idTramiteVersion
	 * @return
	 */
	public List<SeccionReutilizable> getSeccionesTramite(Long idTramiteVersion);

	/**
	 * Indica si hay tramites asociados.
	 * @param codigo
	 * @return
	 */
	public boolean existenTramitesBySeccionReutilizable(Long codigo);

}
