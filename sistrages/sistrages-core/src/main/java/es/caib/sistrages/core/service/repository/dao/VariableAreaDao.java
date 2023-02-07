package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.VariableArea;
import es.caib.sistrages.core.api.service.FormularioExternoService;

/**
 * La interface DominioDao.
 */
public interface VariableAreaDao {

	/**
	 * Obtiene el valor de byId.
	 *
	 * @param idVariableAreathe id dominio
	 * @return el valor de byId
	 */
	VariableArea getByCodigo(final Long idDominio);

	/**
	 * Añade.
	 *
	 * @param entidad   the entidad
	 * @param idEntidad the id entidad
	 * @param idArea    the id area
	 */
	Long add(final VariableArea va);

	/**
	 * Actualiza dominio.
	 *
	 * @param VariableAreathe dominio
	 */
	void updateVariableArea(final VariableArea va);

	/**
	 * Obtiene lista de va.
	 *
	 * @param idArea idArea
	 * @param filtro filtro
	 * @return lista de va
	 */
	List<VariableArea> getAllByFiltro(Long idArea, String filtro);

	/**
	 * Devuelve las va que tienen ese identificador.
	 *
	 * @param identificadoresVa
	 * @return
	 */
	VariableArea getVariableAreaByIdentificador(String identificadorVariableArea, final Long idArea);

	/**
	 * Elimina.
	 *
	 * @param idVariableArea the id VariableArea
	 */
	boolean remove(final Long idVa);

	List<Dominio> dominioByVariable(final VariableArea va);

	List<GestorExternoFormularios> gfeByVariable(final VariableArea va);

	List<EnvioRemoto> envioRemotoByVariable(final VariableArea va);

}