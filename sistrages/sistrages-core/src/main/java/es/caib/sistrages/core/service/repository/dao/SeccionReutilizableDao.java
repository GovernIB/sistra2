package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.HistorialSeccionReutilizable;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;
import es.caib.sistrages.core.api.model.types.TypeAmbito;


/**
 * La interface SeccionReutilizableDao.
 */
public interface SeccionReutilizableDao {


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
	 * @param idFormulario idFormulario
	 */
	public SeccionReutilizable addSeccion(Long idEntidad, SeccionReutilizable seccion, Long idFormulario,final String username);

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
	 * Actualiza fecha.
	 * @param id
	 * @param usuario
	 * @param literal
	 */
	public void actualizarFechaSeccion(Long id, String usuario, String literal, TypeAccionHistorial accion);

	/**
	 * Comprueba si ya existe
	 * @param idEntidad
	 * @param identificador
	 * @return
	 */
	public boolean existeIdentificador(Long idEntidad, String identificador);

	/**
	 * Borrado historial
	 *
	 * @param idSeccion
	 * @param username
	 */
	public void borradoHistorial(Long idSeccion, String username);

	/**
	 * Añade una linea mas al historial
	 * @param idSeccionReutilizable
	 * @param username
	 * @param accion
	 * @param detalleCambio
	 */
	void anyadirHistorial(Long idSeccionReutilizable, String username, TypeAccionHistorial accion,
			String detalleCambio);

	/**
	 * Obtiene seccion reutilizable segun identificador.
	 * @param ambito
	 * @param identificador
	 * @param idEntidad
	 * @param idArea
	 * @return
	 */
	public SeccionReutilizable getSeccionReutilizableByIdentificador(TypeAmbito ambito, String identificador,
			Long idEntidad, Long idArea);

	/**
	 * Importar seccion.
	 * @param filaSeccion
	 * @param idEntidad
	 * @return
	 */
	public Long importar(FilaImportarSeccion filaSeccion, Long idEntidad, Long idFormularioAsociado);

	/**
	 * Lista de scripts secciones reuitilizables
	 * @param idSeccionReutilizable
	 * @return
	 */
	public List<ScriptSeccionReutilizable> getScriptsByIdSeccionReutilizable(Long idSeccionReutilizable);

	/**
	 * Obtener script seccion reutilizable
	 * @param idScript
	 * @return
	 */
	public ScriptSeccionReutilizable getScriptById(Long idScript);

	/**
	 * Devuelve todos los id dominios asociados a una seccion reutilizable
	 * @param identificadorSeccion
	 * @return
	 */
	public List<Dominio> getDominiosByIdentificadorSeccion(String identificadorSeccion);


}