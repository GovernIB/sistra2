package es.caib.sistra2.commons.plugins.registro.regweb3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.regweb3.ws.api.v3.AnexoWs;
import es.caib.regweb3.ws.api.v3.AsientoRegistralWs;
import es.caib.regweb3.ws.api.v3.DatosInteresadoWs;
import es.caib.regweb3.ws.api.v3.InteresadoWs;
import es.caib.regweb3.ws.api.v3.JustificanteReferenciaWs;
import es.caib.regweb3.ws.api.v3.JustificanteWs;
import es.caib.regweb3.ws.api.v3.LibroWs;
import es.caib.regweb3.ws.api.v3.OficinaWs;
import es.caib.regweb3.ws.api.v3.RegWebAsientoRegistralWs;
import es.caib.regweb3.ws.api.v3.RegWebInfoWs;
import es.caib.regweb3.ws.api.v3.TipoAsuntoWs;
import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.DocumentoAsiento;
import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.Interesado;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoJustificante;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.TipoAsunto;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumental;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaAsiento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeInteresado;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeJustificante;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;

/**
 * Implementacion REGWEB3 del plugin registro.
 *
 * @author Indra
 *
 */
public class RegistroRegweb3Plugin extends AbstractPluginProperties implements IRegistroPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "regweb3.";

	public RegistroRegweb3Plugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public List<OficinaRegistro> obtenerOficinasRegistro(final String codigoEntidad, final TypeRegistro tipoRegistro)
			throws RegistroPluginException {

		final List<OficinaRegistro> res = new ArrayList<>();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			final RegWebInfoWs service = UtilsRegweb3.getRegistroInfoService(codigoEntidad,
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_INFO), getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR),
					getPropiedad(ConstantesRegweb3.PROP_USUARIO), getPropiedad(ConstantesRegweb3.PROP_PASSWORD),
					logCalls);

			Long regType = null;
			if (tipoRegistro == TypeRegistro.REGISTRO_ENTRADA) {
				regType = ConstantesRegweb3.REGISTRO_ENTRADA;
			} else if (tipoRegistro == TypeRegistro.REGISTRO_SALIDA) {
				regType = ConstantesRegweb3.REGISTRO_SALIDA;
			} else {
				throw new RegistroPluginException("Tipo registro no soportado: " + tipoRegistro);
			}

			final List<OficinaWs> resWs = service.listarOficinas(codigoEntidad, regType);

			for (final OficinaWs ofWs : resWs) {
				final OficinaRegistro of = new OficinaRegistro();
				of.setCodigo(ofWs.getCodigo());
				of.setNombre(ofWs.getNombre());
				res.add(of);
			}

		} catch (final Exception ex) {
			throw new RegistroPluginException("Error consultando oficinas registro: " + ex.getMessage(), ex);
		}

		return res;

	}

	@Override
	public List<LibroOficina> obtenerLibrosOficina(final String codigoEntidad, final String codigoOficina,
			final TypeRegistro tipoRegistro) throws RegistroPluginException {
		final List<LibroOficina> res = new ArrayList<>();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			final RegWebInfoWs service = UtilsRegweb3.getRegistroInfoService(codigoEntidad,
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_INFO), getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR),
					getPropiedad(ConstantesRegweb3.PROP_USUARIO), getPropiedad(ConstantesRegweb3.PROP_PASSWORD),
					logCalls);

			Long regType = null;
			if (tipoRegistro == TypeRegistro.REGISTRO_ENTRADA) {
				regType = ConstantesRegweb3.REGISTRO_ENTRADA;
			} else if (tipoRegistro == TypeRegistro.REGISTRO_SALIDA) {
				regType = ConstantesRegweb3.REGISTRO_SALIDA;
			} else {
				throw new RegistroPluginException("Tipo registro no soportado: " + tipoRegistro);
			}

			final List<LibroWs> resWs = service.listarLibros(codigoEntidad, codigoOficina, regType);

			for (final LibroWs liWs : resWs) {
				final LibroOficina li = new LibroOficina();
				li.setCodigo(liWs.getCodigoLibro());
				li.setNombre(liWs.getNombreCorto());
				res.add(li);
			}

		} catch (final Exception ex) {
			throw new RegistroPluginException(
					"Error consultando libros de la oficina " + codigoOficina + ": " + ex.getMessage(), ex);
		}

		return res;
	}

	@Override
	public List<TipoAsunto> obtenerTiposAsunto(final String codigoEntidad) throws RegistroPluginException {
		final List<TipoAsunto> res = new ArrayList<>();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			final RegWebInfoWs service = UtilsRegweb3.getRegistroInfoService(codigoEntidad,
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_INFO), getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR),
					getPropiedad(ConstantesRegweb3.PROP_USUARIO), getPropiedad(ConstantesRegweb3.PROP_PASSWORD),
					logCalls);

			final List<TipoAsuntoWs> resWs = service.listarTipoAsunto(codigoEntidad);

			for (final TipoAsuntoWs taWs : resWs) {
				final TipoAsunto ta = new TipoAsunto();
				ta.setCodigo(taWs.getCodigo());
				ta.setNombre(taWs.getNombre());
				res.add(ta);
			}

		} catch (final Exception ex) {
			throw new RegistroPluginException("Error consultando tipos asunto: " + ex.getMessage(), ex);
		}

		return res;
	}

	@Override
	public ResultadoRegistro registroEntrada(final AsientoRegistral asientoRegistral) throws RegistroPluginException {

		// Mapea parametros ws
		final AsientoRegistralWs paramEntrada = mapearParametrosRegistro(asientoRegistral);

		AsientoRegistralWs result = new AsientoRegistralWs();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			// Invoca a Regweb3
			final RegWebAsientoRegistralWs service = UtilsRegweb3.getAsientoRegistralService(
					asientoRegistral.getDatosOrigen().getCodigoEntidad(),
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_ASIENTO),
					getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR), getPropiedad(ConstantesRegweb3.PROP_USUARIO),
					getPropiedad(ConstantesRegweb3.PROP_PASSWORD), logCalls);

			// creacion de asiento registral de entrada con tipo de operacion normal
			result = service.crearAsientoRegistral(asientoRegistral.getDatosOrigen().getCodigoEntidad(), paramEntrada,
					null, true);
		} catch (final Exception ex) {
			throw new RegistroPluginException("Error realizando registro de entrada : " + ex.getMessage(), ex);
		}

		// Devuelve resultado registro
		final ResultadoRegistro resReg = new ResultadoRegistro();
		resReg.setFechaRegistro(result.getFechaRegistro());
		resReg.setNumeroRegistro(result.getNumeroRegistroFormateado());
		return resReg;
	}

	@Override
	public ResultadoRegistro registroSalida(final AsientoRegistral asientoRegistral) throws RegistroPluginException {

		// Mapea parametros ws
		final AsientoRegistralWs paramEntrada = mapearParametrosRegistro(asientoRegistral);

		AsientoRegistralWs result = new AsientoRegistralWs();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			// Invoca a Regweb3
			final RegWebAsientoRegistralWs service = UtilsRegweb3.getAsientoRegistralService(
					asientoRegistral.getDatosOrigen().getCodigoEntidad(),
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_ASIENTO),
					getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR), getPropiedad(ConstantesRegweb3.PROP_USUARIO),
					getPropiedad(ConstantesRegweb3.PROP_PASSWORD), logCalls);
			// creacion de asiento registral de salida con tipo de operacion normal
			result = service.crearAsientoRegistral(asientoRegistral.getDatosOrigen().getCodigoEntidad(), paramEntrada,
					ConstantesRegweb3.OPERACION_NORMAL, false);
		} catch (final Exception ex) {
			throw new RegistroPluginException("Error realizando registro de salida : " + ex.getMessage(), ex);
		}

		// Devuelve resultado registro
		final ResultadoRegistro resReg = new ResultadoRegistro();
		resReg.setFechaRegistro(result.getFechaRegistro());
		resReg.setNumeroRegistro(result.getNumeroRegistroFormateado());
		return resReg;
	}

	@Override
	public ResultadoJustificante obtenerJustificanteRegistro(final String codigoEntidad, final String numeroRegistro)
			throws RegistroPluginException {

		byte[] content = null;
		String url = null;

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			// Invoca a Regweb3
			final RegWebAsientoRegistralWs service = UtilsRegweb3.getAsientoRegistralService(codigoEntidad,
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_ASIENTO),
					getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR), getPropiedad(ConstantesRegweb3.PROP_USUARIO),
					getPropiedad(ConstantesRegweb3.PROP_PASSWORD), logCalls);

			// Devuelve justificante según método de descarga
			switch (descargaJustificantes()) {
			case URL_EXTERNA:
				final JustificanteReferenciaWs referencia = service.obtenerReferenciaJustificante(codigoEntidad,
						numeroRegistro);
				url = referencia.getUrl();
				break;
			case FICHERO:
				final JustificanteWs result = service.obtenerJustificante(codigoEntidad, numeroRegistro,
						ConstantesRegweb3.REGISTRO_ENTRADA);
				content = result.getJustificante();
				break;
			default:
				// Redireccion a carpeta. No debe entrar aquí.
				throw new RegistroPluginException("Si se redirige a carpeta no debe descargarse");
			}

		} catch (final Exception ex) {
			throw new RegistroPluginException(
					"S'ha produit un error al descarregar el justificant de registre d'entrada. Per favor, tornau a provar-ho passats uns minuts."
							+ ex.getMessage(),
					ex);
		}

		final ResultadoJustificante res = new ResultadoJustificante();
		res.setUrl(url);
		res.setContenido(content);
		return res;

	}

	@Override
	public LibroOficina obtenerLibroOrganismo(final String codigoEntidad, final String codigoOrganismo)
			throws RegistroPluginException {

		LibroWs libroWs = new LibroWs();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			final RegWebInfoWs service = UtilsRegweb3.getRegistroInfoService(codigoEntidad,
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_INFO), getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR),
					getPropiedad(ConstantesRegweb3.PROP_USUARIO), getPropiedad(ConstantesRegweb3.PROP_PASSWORD),
					logCalls);

			libroWs = service.listarLibroOrganismo(codigoEntidad, codigoOrganismo);

		} catch (final Exception ex) {
			throw new RegistroPluginException("Error consultando oficinas registro: " + ex.getMessage(), ex);
		}

		final LibroOficina res = new LibroOficina();
		res.setCodigo(libroWs.getCodigoLibro());
		res.setNombre(libroWs.getNombreCorto());

		return res;
	}

	@Override
	public TypeJustificante descargaJustificantes() throws RegistroPluginException {
		TypeJustificante tipoJustificante = TypeJustificante
				.fromString(getPropiedad(ConstantesRegweb3.PROP_JUSTIFICANTE_DESCARGA));
		if (tipoJustificante == null) {
			tipoJustificante = TypeJustificante.FICHERO;
		}
		return tipoJustificante;
	}

	// ----------- Funciones auxiliares

	/**
	 * Mapea datos asiento a parametro ws.
	 *
	 * @param asiento
	 *            asiento registral
	 * @throws RegistroPluginException
	 */
	private AsientoRegistralWs mapearParametrosRegistro(final AsientoRegistral asiento) throws RegistroPluginException {

		// Crea parametros segun sea registro entrada o salida
		final boolean esRegistroSalida = (asiento.getDatosOrigen().getTipoRegistro() == TypeRegistro.REGISTRO_SALIDA);

		final AsientoRegistralWs asientoWs = new AsientoRegistralWs();

		if (esRegistroSalida) {
			asientoWs.setTipoRegistro(ConstantesRegweb3.REGISTRO_SALIDA);
		} else {
			asientoWs.setTipoRegistro(ConstantesRegweb3.REGISTRO_ENTRADA);
		}

		// Datos aplicacion
		asientoWs.setAplicacionTelematica(getPropiedad(ConstantesRegweb3.PROP_APLICACION_CODIGO));

		asientoWs.setCodigoUsuario(getPropiedad(ConstantesRegweb3.PROP_USUARIO));

		// Datos oficina registro
		asientoWs.setEntidadRegistralOrigenCodigo(asiento.getDatosOrigen().getCodigoOficinaRegistro());
		asientoWs.setLibroCodigo(asiento.getDatosOrigen().getLibroOficinaRegistro());
		if (esRegistroSalida) {
			asientoWs.setUnidadTramitacionOrigenCodigo(asiento.getDatosAsunto().getCodigoOrganoDestino());
		} else {
			asientoWs.setUnidadTramitacionDestinoCodigo(asiento.getDatosAsunto().getCodigoOrganoDestino());
		}

		// Datos asunto
		asientoWs.setResumen(asiento.getDatosAsunto().getExtractoAsunto());
		asientoWs.setTipoDocumentacionFisicaCodigo(ConstantesRegweb3.TIPO_DOCFIS_DIGTL);
		asientoWs.setTipoAsunto(asiento.getDatosAsunto().getTipoAsunto());
		switch (asiento.getDatosAsunto().getIdiomaAsunto()) {
		case "es":
			asientoWs.setIdioma(ConstantesRegweb3.IDIOMA_CASTELLANO);
			break;
		case "ca":
			asientoWs.setIdioma(ConstantesRegweb3.IDIOMA_CATALAN);
			break;
		case "en":
			asientoWs.setIdioma(ConstantesRegweb3.IDIOMA_INGLES);
			break;
		default:
			asientoWs.setIdioma(ConstantesRegweb3.IDIOMA_OTROS);
		}
		asientoWs.setNumeroExpediente(asiento.getDatosAsunto().getNumeroExpediente());
		asientoWs.setExpone(asiento.getDatosAsunto().getTextoExpone());
		asientoWs.setSolicita(asiento.getDatosAsunto().getTextoSolicita());
		asientoWs.setCodigoSia(Long.parseLong(asiento.getDatosAsunto().getCodigoSiaProcedimiento()));

		// Se indica que el asiento no se hace de forma presencial
		asientoWs.setPresencial(false);

		// Datos interesado: representante / representado
		final Interesado representanteAsiento = UtilsRegweb3.obtenerDatosInteresadoAsiento(asiento,
				TypeInteresado.REPRESENTANTE);
		final Interesado representadoAsiento = UtilsRegweb3.obtenerDatosInteresadoAsiento(asiento,
				TypeInteresado.REPRESENTADO);

		DatosInteresadoWs interesado = null;
		DatosInteresadoWs representante = null;

		if (representadoAsiento != null) {
			interesado = UtilsRegweb3.crearInteresado(representadoAsiento);
			representante = UtilsRegweb3.crearInteresado(representanteAsiento);
		} else {
			interesado = UtilsRegweb3.crearInteresado(representanteAsiento);
		}

		final InteresadoWs interesadoWs = new InteresadoWs();
		interesadoWs.setInteresado(interesado);
		interesadoWs.setRepresentante(representante);
		asientoWs.getInteresados().add(interesadoWs);

		// Anexos
		if ("true".equals(getPropiedad(ConstantesRegweb3.PROP_INSERTADOCS))) {

			final boolean anexarInternos = "true".equals(getPropiedad(ConstantesRegweb3.PROP_INSERTADOCS_INT));
			final boolean anexarFormateados = "true".equals(getPropiedad(ConstantesRegweb3.PROP_INSERTADOCS_FOR));

			Integer origenDocumento;
			String tipoDocumental;

			if (esRegistroSalida) {
				origenDocumento = ConstantesRegweb3.ORIGEN_DOCUMENTO_ADMINISTRACION;
				tipoDocumental = ConstantesRegweb3.TIPO_DOCUMENTAL_NOTIFICACION;
			} else {
				origenDocumento = ConstantesRegweb3.ORIGEN_DOCUMENTO_CIUDADANO;
				tipoDocumental = ConstantesRegweb3.TIPO_DOCUMENTAL_SOLICITUD;
			}

			// - Ficheros asiento
			for (final Iterator it = asiento.getDocumentosRegistro().iterator(); it.hasNext();) {

				AnexoWs anexoWs = null;

				final DocumentoAsiento dr = (DocumentoAsiento) it.next();

				if (dr.getTipoDocumento() == TypeDocumental.FICHERO_TECNICO && anexarInternos) {
					anexoWs = generarAnexoWs(dr);
					asientoWs.getAnexos().add(anexoWs);
				}

				if (!(dr.getTipoDocumento() == TypeDocumental.FICHERO_TECNICO) && anexarFormateados) {
					anexoWs = generarAnexoWs(dr);
					asientoWs.getAnexos().add(anexoWs);
				}

			}
		}

		return asientoWs;

	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *            propiedad
	 * @return valor
	 * @throws RegistroPluginException
	 */
	private String getPropiedad(final String propiedad) throws RegistroPluginException {
		final String res = getProperty(REGISTRO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new RegistroPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
		}
		return res;
	}

	/**
	 * Genera AnexoWS en funcion documento REDOSE
	 *
	 * @param refRDS
	 * @param tipoDocumento
	 * @param tipoDocumental
	 * @param origenDocumento
	 * @return
	 */
	private AnexoWs generarAnexoWs(final DocumentoAsiento dr) throws RegistroPluginException {

		final AnexoWs anexoAsiento = new AnexoWs();
		anexoAsiento.setTitulo(dr.getTituloDoc());
		anexoAsiento.setTipoDocumental(dr.getTipoDocumental());
		anexoAsiento.setTipoDocumento(dr.getTipoDocumento().toString());
		anexoAsiento.setOrigenCiudadanoAdmin(dr.getOrigenDocumento().intValue());
		anexoAsiento.setModoFirma(dr.getModoFirma().intValue());
		anexoAsiento.setValidezDocumento(dr.getValidez().toString());
		anexoAsiento.setNombreFicheroAnexado(dr.getNombreFichero());
		anexoAsiento.setFicheroAnexado(dr.getContenidoFichero());
		anexoAsiento.setTipoMIMEFicheroAnexado(MimeType.getMimeTypeForExtension(getExtension(dr.getNombreFichero())));

		if (dr.getModoFirma() != TypeFirmaAsiento.SIN_FIRMA) {
			if (dr.getModoFirma() == TypeFirmaAsiento.FIRMA_DETACHED) {
				anexoAsiento.setNombreFicheroAnexado(dr.getNombreFichero());
				anexoAsiento.setFicheroAnexado(dr.getContenidoFichero());
				anexoAsiento.setTipoMIMEFicheroAnexado(
						MimeType.getMimeTypeForExtension(getExtension(dr.getNombreFichero())));
				anexoAsiento.setFirmaAnexada(dr.getContenidoFirma());
				anexoAsiento.setNombreFirmaAnexada(dr.getNombreFirmaAnexada());
				anexoAsiento.setTipoMIMEFirmaAnexada(
						MimeType.getMimeTypeForExtension(getExtension(dr.getNombreFirmaAnexada())));
			}
		}

		return anexoAsiento;
	}

	/**
	 * Obtiene extension fichero.
	 */
	private String getExtension(final String filename) {
		if (filename.lastIndexOf(".") != -1) {
			return filename.substring(filename.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

}
