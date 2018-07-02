package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;

/**
 * Interfaz de acceso a base de datos para los datos del flujo de un paso del
 * tramite.
 *
 * @author Indra
 *
 */
public interface FlujoPasoDao {

    /**
     * Obtiene datos del paso en persistencia.
     *
     * @param idSesionTramitacion
     *            Id sesión tramitación
     * @param idPaso
     *            Id del paso
     * @return Datos de persistencia del paso.
     */
    DatosPersistenciaPaso obtenerPasoPersistencia(String idSesionTramitacion,
            String idPaso);

    /**
     * Cambia el estado de un paso.
     *
     * @param idSesionTramitacion
     *            Id sesión tramitación
     * @param idPaso
     *            Id paso
     * @param estado
     *            Estado trámite
     *
     */
    void cambiarEstadoPaso(String idSesionTramitacion, String idPaso,
            TypeEstadoPaso estado);

    /**
     * Permite anexar al paso un documento. La primera vez que se invoque se
     * creará un registro en la tabla de documentos del paso, las siguientes se
     * actualizarán los datos.
     *
     * @param idSesionTramitacion
     *            Id sesión tramitación
     * @param idPaso
     *            Id paso
     * @param docPaso
     *            Datos del documento
     */
    void establecerDatosDocumento(String idSesionTramitacion, String idPaso,
            DocumentoPasoPersistencia docPaso);

    /**
     * Permite eliminar un documento del paso.
     *
     * @param idSesionTramitacion
     *            Id sesión tramitación
     * @param idPaso
     *            Id paso
     * @param idDocumento
     *            Id documento
     * @param instancia
     *            Parámetro instancia
     */
    void eliminarDocumento(String idSesionTramitacion, String idPaso,
            String idDocumento, int instancia);

    /**
     * Inserta un fichero en la tabla de ficheros. Luego se usará su referencia
     * para enlazarse.
     *
     * @param nombre
     *            Nombre fichero con extensión.
     * @param contenido
     *            Datos del fichero.
     * @param idSesionTramitacion
     *            Sesión tramitación a la que pertenece el fichero (para
     *            optimizar purga).
     * @return Referencia del fichero.
     */
    ReferenciaFichero insertarFicheroPersistencia(String nombre,
            byte[] contenido, String idSesionTramitacion);

    /**
     * Recupera un fichero almacenado en persistencia.
     *
     * @param refFic
     *            Parámetro ref fic
     * @return DatosFicheroPersistencia Datos del fichero.
     */
    DatosFicheroPersistencia recuperarFicheroPersistencia(
            ReferenciaFichero refFic);

    /**
     * Actualiza contenido de un fichero almacenado en persistencia.
     *
     * @param refFic
     *            Parámetro ref fic
     * @param nombre
     *            Nombre fichero con extensión.
     * @param contenido
     *            Datos del fichero.
     */
    void actualizarFicheroPersistencia(ReferenciaFichero refFic, String nombre,
            byte[] contenido);

    /**
     * Eliminar un fichero almacenado en persistencia.
     *
     * @param refFic
     *            Parámetro ref fic
     */
    void eliminarFicheroPersistencia(ReferenciaFichero refFic);

    /**
     * Método para Obtener documento persistencia de la clase FlujoPasoDao.
     *
     * @param pIdSesionTramitacion
     *            Parámetro id sesion tramitacion
     * @param pIdPaso
     *            Parámetro id paso
     * @param idAnexo
     *            Parámetro id anexo
     * @param instancia
     *            Parámetro instancia
     * @return el documento paso persistencia
     */
    DocumentoPasoPersistencia obtenerDocumentoPersistencia(
            String pIdSesionTramitacion, String pIdPaso, String idAnexo,
            int instancia);

    // TODO PENDIENTE

    // TODO VER GESTION FIRMAS

}
