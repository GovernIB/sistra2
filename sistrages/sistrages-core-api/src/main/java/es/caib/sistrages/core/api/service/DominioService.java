package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Dominio;
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
	 * @param idDominio
	 *            ID del dominio
	 * @return dominio
	 */
	public Dominio loadDominio(Long idDominio);

	/**
	 * Añade dominio.
	 *
	 * @param dominio
	 *            Dominio a crear.
	 * @param idEntidad
	 *            Id de la entidad
	 * @param idArea
	 *            Id del area.
	 */
	public void addDominio(Dominio dominio, Long idEntidad, Long idArea);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio
	 *            Dominio con los datos requeridos por superadministrador.
	 */
	public void updateDominio(Dominio dominio);

	/**
	 * Borrar dominio.
	 *
	 * @param idDominio
	 *            idDominio
	 */
	public void removeDominio(Long idDominio);

	/**
	 * Listar dominios.
	 *
	 * @param ambito
	 *            Ambito GLOBAL(G), ENTIDAD(E) o AREA(A)
	 * @param id
	 *            Id de la entidad o area
	 * @param filtro
	 *            Filro aplicado al código o descripcion.
	 * @return ambito
	 */
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, String filtro);

}
