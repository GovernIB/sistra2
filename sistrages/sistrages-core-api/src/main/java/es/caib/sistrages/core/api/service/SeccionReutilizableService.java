package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.HistorialSeccionReutilizable;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * La interface SeccionReutilizableService.
 */
public interface SeccionReutilizableService {

	/**
	 * Lista de secciones reutilizables.
	 *
	 * @param idEntidad idEntidad
	 * @param filtro    filtro
	 * @return lista de secciones
	 */
	public List<SeccionReutilizable> listSeccionReutilizable(final Long idEntidad, String filtro, Boolean activo);

	/**
	 * Obtiene el seccion reutilizable.
	 *
	 * @param id identificador
	 * @return area
	 */
	public SeccionReutilizable getSeccionReutilizable(Long id);

	/**
	 * Añade una seccion.
	 *
	 * @param idEntidad idEntidad
	 * @param seccion      seccion
	 */
	public SeccionReutilizable addSeccion(Long idEntidad, SeccionReutilizable seccion, final String username);

	/**
	 * Elimina una seccion.
	 *
	 * @param id identificador
	 * @return true, si se realiza con exito
	 */
	public boolean removeSeccion(Long id);

	/**
	 * Actualiza una seleccion reutilizable.
	 * @param seccion
	 * @param scripts
	 */
	public void updateSeccionReutilizable(SeccionReutilizable seccion, List<ScriptSeccionReutilizable> scripts);

	/**
	 * Bloquea una seccion
	 *
	 * @param idSeccion
	 * @param username
	 */
	public void bloquearSeccion(Long idSeccion, String username);

	/**
	 * Desbloquea un seccion.
	 *
	 * @param idSeccion
	 * @param username
	 * @param detalle
	 */
	public void desbloquearSeccion(final Long idSeccion, final String username, final String detalle);

	/**
	 * Obtiene una lista de historial de un seccion reutilizable.
	 *
	 * @param idTramiteVersion
	 * @param filtro
	 * @return
	 */
	public List<HistorialSeccionReutilizable> listHistorialSeccionReutilizable(Long idSeccion, String filtro);

	/**
	 * Obtiene un historial seccion.
	 *
	 * @param idHistorialSeccion
	 * @return
	 */
	public HistorialSeccionReutilizable getHistorialSeccionReutilizable(Long idHistorialSeccion);

	/**
	 * Actualiza la fecha.
	 * @param parseLong
	 * @param userName
	 * @param string
	 */
	public void actualizarFechaSeccion(long parseLong, String userName, String string);

	/**
	 * Comprueba si ya existe un identificador.
	 * @param idEntidad
	 * @param identificador
	 * @return
	 */
	public boolean existeIdentificador(Long idEntidad, String identificador);

	/**
	 * Borrado historial
	 * @param idSeccion
	 * @param userName
	 */
	public void borradoHistorial(Long idSeccion, String userName);

	/** Obtener seccion reutilizable por identificador. **/
	public SeccionReutilizable getSeccionReutilizableByIdentificador(TypeAmbito ambito, String identificador,
			Long idEntidad, Long idArea);

	/**
	 * Devuelve la lista de scripts.
	 * @param valueOf
	 * @return
	 */
	public List<ScriptSeccionReutilizable> getScriptsByIdSeccionReutilizable(Long idSeccionReutilizable);

	/**
	 * Devuelve la info de un script seccion reutilizable.
	 * @param idScript
	 * @return
	 */
	public ScriptSeccionReutilizable getScriptById(Long idScript);

	/**
	 * Accion de importar
	 * @param filaSeccion
	 * @param idEntidad
	 */
	public void importarSeccion(FilaImportarSeccion filaSeccion, Long idEntidad);

	/**
	 * Método que obtiene el diseño formulario de una seccion reutilizable
	 * @param seccionFormID
	 * @return
	 */
	public DisenyoFormulario getFormularioInternoCompleto(Long seccionFormID);

	/**
	 * Devuelve los dominios de una seccion
	 * @param identificadorSeccion
	 * @return
	 */
	public List<Dominio> getDominiosByIdentificadorSeccion(String identificadorSeccion);



}
