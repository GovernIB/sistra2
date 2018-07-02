package es.caib.sistrages.core.service.repository.dao;

import es.caib.sistrages.core.api.model.ContenidoFichero;
import es.caib.sistrages.core.api.model.Fichero;

/**
 * DAO Fichero externo.
 *
 * @author Indra
 *
 */
public interface FicheroExternoDao {

    /**
     * Obtiene contenido fichero externo asociado a fichero (sin marcado para
     * borrar).
     *
     * @param id
     *            identificador fichero
     * @return fichero externo
     */
    ContenidoFichero getContentById(Long id);

    /**
     * Obtiene path absoluto fichero externo asociado a fichero (sin marcado
     * para borrar).
     *
     * @param id
     *            identificador fichero
     * @return fichero externo
     */
    String getPathById(Long id);

    /**
     * Obtiene path relativo fichero externo asociado a fichero (sin marcado
     * para borrar).
     *
     * @param id
     *            identificador fichero
     * @return fichero externo
     */
    String getReferenciaById(Long id);

    /**
     * Purga ficheros marcados para borrar.
     */
    void purgarFicheros();

    /**
     * Establece fichero asociado (marca para borrar el resto de instancias
     * asociadas).
     *
     * @param idEntidad
     *            idEntidad
     * @param fichero
     *            fichero
     * @param content
     *            contenido
     */
    void guardarFichero(Long idEntidad, Fichero fichero, byte[] content);

    /**
     * Marca para borrar el fichero (se borrará después con el proceso de
     * purga).
     *
     * @param id
     *            id de fichero
     */
    void marcarBorrar(Long id);

}