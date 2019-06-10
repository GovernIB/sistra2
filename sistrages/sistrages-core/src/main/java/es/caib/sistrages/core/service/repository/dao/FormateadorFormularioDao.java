package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.comun.FilaImportarFormateador;

/**
 * La interface FormateadorFormularioDao.
 */
public interface FormateadorFormularioDao {

	/**
	 * Obtiene el Formateador de Formulario.
	 *
	 * @param id
	 *            el identificador
	 * @return el Formateador Formulario
	 */
	FormateadorFormulario getById(Long id);

	/**
	 * Obtiene formateador por código.
	 *
	 * @param codigo
	 *            código
	 * @return formateador
	 */
	FormateadorFormulario getByCodigo(String codigo);

	/**
	 * Añade el Formateador de Formulario.
	 *
	 * @param idEntidad
	 *            id entidad
	 * @param fmt
	 *            el formateador de formulario
	 */
	void add(final Long idEntidad, FormateadorFormulario fmt);

	/**
	 * Elimina el Formateador de Formulario.
	 *
	 * @param id
	 *            el identificador
	 */
	void remove(Long id);

	/**
	 * Elimina el Formateadores de Formulario de entidad.
	 *
	 * @param idEntidad
	 *            el identificador entidad
	 */
	void removeByEntidad(Long idEntidad);

	/**
	 * Actualiza el Formateador de Formulario.
	 *
	 * @param fmt
	 *            el formateador de formulario
	 * @param idEntidad
	 *            código de la entidad
	 */
	void update(FormateadorFormulario fmt, Long idEntidad);

	/**
	 * Obtiene la lista Formateadores de Formulario.
	 *
	 * @param idEntidad
	 *            el identificador entidad
	 * @return lista de Formateadores de Formulario
	 */
	List<FormateadorFormulario> getAll(Long idEntidad);

	/**
	 * Obtiene la lista de Formateadores de Formulario.
	 *
	 * @param idEntidad
	 *            el identificador entidad
	 * @param filtro
	 *            filtro
	 * @param desactivarPersonalizacion
	 * @return la lista de Formateadores de Formulario
	 */
	List<FormateadorFormulario> getAllByFiltro(Long idEntidad, String filtro, final Boolean desactivarPersonalizacion);

	/**
	 * Importa un formateador formulario.
	 *
	 * @param filaFormateador
	 * @param idEntidad
	 * @return
	 */
	Long importar(FilaImportarFormateador filaFormateador, Long idEntidad);

	/**
	 * Comprueba si tiene relaciones el formateador.
	 *
	 * @param idFmt
	 * @return
	 */
	boolean tieneRelacionesFormateadorFormulario(Long idFmt);

	/**
	 * Obtiene la lista de plantilla asociado a un formateador por defecto.
	 *
	 * @param idFormateador
	 * @return
	 */
	List<PlantillaFormateador> getListaPlantillasFormateador(Long idFormateador);

	/**
	 * Sube una plantilla formateador.
	 *
	 * @param idPlantillaFormateador
	 * @param plantillaFormateador
	 * @return
	 */
	PlantillaFormateador uploadPlantillaFormateador(Long idPlantillaFormateador,
			PlantillaFormateador plantillaFormateador);

	/**
	 * Obtiene el formateador por defecto de la entidad.
	 *
	 * @param idEntidad
	 * @param codigoDir3
	 * @return
	 */
	public FormateadorFormulario getFormateadorPorDefecto(final Long idEntidad, final String codigoDir3);

	/**
	 * Borra una plantilla formateador.
	 *
	 * @param codigo
	 */
	void removePlantillaFormateador(Long codigo);

}
