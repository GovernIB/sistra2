package es.caib.sistra2.commons.plugins.registro.api;

import java.util.List;

import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.sistra2.commons.plugins.registro.api.types.TypeJustificante;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;

/**
 * Interface registro.
 *
 * @author Indra
 *
 */
public interface IRegistroPlugin extends IPlugin {

	/** Prefix. */
	public static final String REGISTRO_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES + "registro.";

	/**
	 * Recupera oficinas de registro de la entidad.
	 *
	 * @param codigoEntidad
	 *                          codigo DIR3 de la entidad
	 * @throws RegistroPluginException
	 */
	List<OficinaRegistro> obtenerOficinasRegistro(String codigoEntidad, TypeRegistro tipoRegistro)
			throws RegistroPluginException;

	/**
	 * Recupera los libros a los que una oficina da servicio
	 *
	 * @param codigoEntidad
	 *                          codigo DIR3 de la entidad
	 * @param codigoOficina
	 *                          codigo DIR3 de la oficina a consultar
	 * @param tipoRegistro
	 *                          tipo de registro
	 * @throws RegistroPluginException
	 */
	List<LibroOficina> obtenerLibrosOficina(String codigoEntidad, String codigoOficina, TypeRegistro tipoRegistro)
			throws RegistroPluginException;

	/**
	 * Recupera libro asociado a organismo.
	 *
	 * @param codigoEntidad
	 *                            codigo DIR3 entidad
	 * @param codigoOrganismo
	 *                            codigo DIR3 organismo
	 * @return libro asociado a organismo
	 */
	LibroOficina obtenerLibroOrganismo(String codigoEntidad, String codigoOrganismo) throws RegistroPluginException;

	/**
	 * Realiza un apunte registral de entrada sobre la aplicacion de registro
	 *
	 * @param asientoRegistral
	 *                             asiento con los datos requeridos para el registro
	 *                             de entrada
	 * @throws RegistroPluginException
	 */
	ResultadoRegistro registroEntrada(AsientoRegistral asientoRegistral) throws RegistroPluginException;

	/**
	 * Realiza un apunte registral de salida sobre la aplicacion de registro
	 *
	 * @param codigoEntidad
	 *                          codigo DIR3 de la entidad
	 * @throws RegistroPluginException
	 */
	ResultadoRegistro registroSalida(AsientoRegistral asientoRegistral) throws RegistroPluginException;

	/**
	 * Obtiene el justificante de registro correspondiente a un apunte registral
	 *
	 * @param codigoEntidad
	 *                           codigo DIR3 de la entidad
	 * @param numeroRegistro
	 *                           numero de registro asignado al apunte registral
	 * @return Devuelve justificante según como se descarga el justificante: fichero
	 *         (contenido), url (url externa absoluta) y carpeta (url relativa a
	 *         carpeta).
	 * @throws RegistroPluginException
	 */
	ResultadoJustificante obtenerJustificanteRegistro(String codigoEntidad, String numeroRegistro)
			throws RegistroPluginException;

	/**
	 * Indica como se realiza la descarga de los justificantes.
	 *
	 * @return Indica como se realiza la descarga de los justificantes.
	 */
	TypeJustificante descargaJustificantes() throws RegistroPluginException;

}
