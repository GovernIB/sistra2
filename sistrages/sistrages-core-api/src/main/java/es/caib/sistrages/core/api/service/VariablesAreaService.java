package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.VariableArea;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeClonarAccion;

/**
 * Dominio service.
 *
 * @author Indra.
 *
 */
public interface VariablesAreaService {

	/**
	 * Obtener VariableArea.
	 *
	 * @param codVa codigo del VariableArea
	 * @return VariableArea
	 */
	public VariableArea loadVariableArea(Long codVa);

	/**
	 * Obtener VariableArea by identificafor.
	 *
	 * @param identificador identificador del VariableArea
	 * @return VariableArea
	 */
	public VariableArea loadVariableAreaByIdentificador(String identificador, Long codigoArea);

	/**
	 * Añade VariableArea.
	 *
	 * @param va VariableArea a crear.
	 *
	 */
	public Long addVariableArea(VariableArea va);

	/**
	 * Actualiza VariableArea.
	 *
	 * @param va VariableArea con los datos requeridos.
	 */
	public void updateVariableArea(VariableArea va);

	/**
	 * Elimina dominio.
	 *
	 * @param idDominio the id VariableArea
	 * @return true, si se realiza correctamente
	 */
	public boolean removeVariableArea(Long idVa);

	/**
	 * Listar VariableArea.
	 *
	 * @param idVa   Id de la area
	 * @param filtro Filro aplicado al código o descripcion.
	 * @return lista de VariableArea
	 */
	public List<VariableArea> listVariableArea(final Long idArea, String filtro);

	public List<Dominio> dominioByVariable(final VariableArea va);

	public List<GestorExternoFormularios> gfeByVariable(final VariableArea va);

	public List<EnvioRemoto> envioRemotoByVariable(final VariableArea va);

}