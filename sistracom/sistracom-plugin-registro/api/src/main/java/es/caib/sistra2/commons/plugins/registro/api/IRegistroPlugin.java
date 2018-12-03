package es.caib.sistra2.commons.plugins.registro.api;

import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;

import java.text.ParseException;
import java.util.List;

/**
 * Interface registro.
 *
 * @author Indra
 *
 */
public interface IRegistroPlugin extends IPlugin {

	/** Prefix. */
	public static final String REGISTRO_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES
			+ "registro.";

	/**
	 * Recupera oficinas de registro de la entidad.
	 *
	 * @param codigoEntidad
	 *            codigo DIR3 de la entidad
	 * @throws RegistroPluginException
	 */
	List<OficinaRegistro> obtenerOficinasRegistro(String codigoEntidad, TypeRegistro tipoRegistro) throws RegistroPluginException;

	/**
	 * Recupera los libros a los que una oficina da servicio
	 *
	 * @param codigoEntidad
	 *            codigo DIR3 de la entidad
	 * @param codigoOficina
	 *            codigo DIR3 de la oficina a consultar
	 * @param tipoRegistro
	 *            tipo de registro
	 * @throws RegistroPluginException
	 */
	List<LibroOficina> obtenerLibrosOficina(String codigoEntidad, String codigoOficina, TypeRegistro tipoRegistro)
			throws RegistroPluginException;

	/**
	 * Recupera los tipos de asunto de una entidad
	 *
	 * @param codigoEntidad
	 *            codigo DIR3 de la entidad
	 * @throws RegistroPluginException
	 */
	List<TipoAsunto> obtenerTiposAsunto(String codigoEntidad)
			throws RegistroPluginException;

	/**
	 * Realiza un apunte registral de entrada sobre la aplicacion de registro
	 *
	 * @param codigoEntidad
	 *            codigo DIR3 de la entidad
	 * @param asientoRegistral
	 *            asiento con los datos requeridos para el registro de entrada
	 * @throws RegistroPluginException
	 */
	ResultadoRegistro registroEntrada(String codigoEntidad, AsientoRegistral asientoRegistral)
			throws RegistroPluginException;

	/**
	 * Realiza un apunte registral de salida sobre la aplicacion de registro
	 *
	 * @param codigoEntidad
	 *            codigo DIR3 de la entidad
	 * @param asientoRegistral
	 *            asiento con los datos requeridos para el registro de salida
	 * @throws RegistroPluginException
	 */
	ResultadoRegistro registroSalida(String codigoEntidad, AsientoRegistral asientoRegistral)
			throws RegistroPluginException;

	/**
	 * Obtiene el justificante de registro correspondiente a un apunte registral
	 *
	 * @param codigoEntidad
	 *            codigo DIR3 de la entidad
	 * @param numeroRegistro
	 *            numero de registro asignado al apunte registral
	 * @throws RegistroPluginException
	 */
	byte[] obtenerJustificanteRegistro(String codigoEntidad, String numeroRegistro)
			throws RegistroPluginException;

}
