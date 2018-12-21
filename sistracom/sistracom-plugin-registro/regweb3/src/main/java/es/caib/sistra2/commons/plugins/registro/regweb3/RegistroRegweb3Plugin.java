package es.caib.sistra2.commons.plugins.registro.regweb3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.regweb3.ws.api.v3.AnexoWs;
import es.caib.regweb3.ws.api.v3.DatosInteresadoWs;
import es.caib.regweb3.ws.api.v3.IdentificadorWs;
import es.caib.regweb3.ws.api.v3.InteresadoWs;
import es.caib.regweb3.ws.api.v3.JustificanteWs;
import es.caib.regweb3.ws.api.v3.LibroWs;
import es.caib.regweb3.ws.api.v3.OficinaWs;
import es.caib.regweb3.ws.api.v3.RegWebInfoWs;
import es.caib.regweb3.ws.api.v3.RegWebRegistroEntradaWs;
import es.caib.regweb3.ws.api.v3.RegWebRegistroSalidaWs;
import es.caib.regweb3.ws.api.v3.RegistroEntradaWs;
import es.caib.regweb3.ws.api.v3.RegistroSalidaWs;
import es.caib.regweb3.ws.api.v3.RegistroWs;
import es.caib.regweb3.ws.api.v3.TipoAsuntoWs;
import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.DocumentoAsiento;
import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.Interesado;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.TipoAsunto;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumental;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaAsiento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeInteresado;
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
	public List<OficinaRegistro> obtenerOficinasRegistro(final String codigoEntidad, TypeRegistro tipoRegistro)
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
	public List<LibroOficina> obtenerLibrosOficina(String codigoEntidad, String codigoOficina,
			TypeRegistro tipoRegistro) throws RegistroPluginException {
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
	public List<TipoAsunto> obtenerTiposAsunto(String codigoEntidad) throws RegistroPluginException {
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
	public ResultadoRegistro registroEntrada(AsientoRegistral asientoRegistral) throws RegistroPluginException {

		// Mapea parametros ws
		final RegistroEntradaWs paramEntrada = (RegistroEntradaWs) mapearParametrosRegistro(asientoRegistral);

		IdentificadorWs result = new IdentificadorWs();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			// Invoca a Regweb3
			final RegWebRegistroEntradaWs service = UtilsRegweb3.getRegistroEntradaService(
					asientoRegistral.getDatosOrigen().getCodigoEntidad(),
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_ENTRADA),
					getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR), getPropiedad(ConstantesRegweb3.PROP_USUARIO),
					getPropiedad(ConstantesRegweb3.PROP_PASSWORD), logCalls);

			result = service.nuevoRegistroEntrada(asientoRegistral.getDatosOrigen().getCodigoEntidad(), paramEntrada);
		} catch (final Exception ex) {
			throw new RegistroPluginException("Error realizando registro de entrada : " + ex.getMessage(), ex);
		}

		// Devuelve resultado registro
		final ResultadoRegistro resReg = new ResultadoRegistro();
		resReg.setFechaRegistro(result.getFecha());
		resReg.setNumeroRegistro(result.getNumeroRegistroFormateado());
		return resReg;
	}

	@Override
	public ResultadoRegistro registroSalida(AsientoRegistral asientoRegistral) throws RegistroPluginException {

		// Mapea parametros ws
		final RegistroSalidaWs paramEntrada = (RegistroSalidaWs) mapearParametrosRegistro(asientoRegistral);

		IdentificadorWs result = new IdentificadorWs();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			// Invoca a Regweb3
			final RegWebRegistroSalidaWs service = UtilsRegweb3.getRegistroSalidaService(
					asientoRegistral.getDatosOrigen().getCodigoEntidad(),
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_SALIDA), getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR),
					getPropiedad(ConstantesRegweb3.PROP_USUARIO), getPropiedad(ConstantesRegweb3.PROP_PASSWORD),
					logCalls);
			result = service.nuevoRegistroSalida(asientoRegistral.getDatosOrigen().getCodigoEntidad(), paramEntrada);
		} catch (final Exception ex) {
			throw new RegistroPluginException("Error realizando registro de salida : " + ex.getMessage(), ex);
		}

		// Devuelve resultado registro
		final ResultadoRegistro resReg = new ResultadoRegistro();
		resReg.setFechaRegistro(result.getFecha());
		resReg.setNumeroRegistro(result.getNumeroRegistroFormateado());
		return resReg;
	}

	@Override
	public byte[] obtenerJustificanteRegistro(String codigoEntidad, String numeroRegistro)
			throws RegistroPluginException {

		byte[] resultado = null;

		JustificanteWs result = new JustificanteWs();

		try {

			final boolean logCalls = (getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES) != null
					? "true".equals(getPropiedad(ConstantesRegweb3.PROP_LOG_PETICIONES))
					: false);

			// Invoca a Regweb3
			final RegWebRegistroEntradaWs service = UtilsRegweb3.getRegistroEntradaService(codigoEntidad,
					getPropiedad(ConstantesRegweb3.PROP_ENDPOINT_ENTRADA),
					getPropiedad(ConstantesRegweb3.PROP_WSDL_DIR), getPropiedad(ConstantesRegweb3.PROP_USUARIO),
					getPropiedad(ConstantesRegweb3.PROP_PASSWORD), logCalls);
			result = service.obtenerJustificante(codigoEntidad, numeroRegistro);

		} catch (final Exception ex) {
			throw new RegistroPluginException(
					"S'ha produit un error al descarregar el justificant de registre d'entrada. Per favor, tornau a provar-ho passats uns minuts."
							+ ex.getMessage(),
					ex);
		}

		resultado = result.getJustificante();

		return resultado;
	}

	// ----------- Funciones auxiliares

	/**
	 * Mapea datos asiento a parametro ws.
	 *
	 * @param asiento
	 *            asiento registral
	 * @throws RegistroPluginException
	 */
	private RegistroWs mapearParametrosRegistro(AsientoRegistral asiento) throws RegistroPluginException {

		// Crea parametros segun sea registro entrada o salida
		final boolean esRegistroSalida = (asiento.getDatosOrigen().getTipoRegistro() == TypeRegistro.REGISTRO_SALIDA);

		RegistroWs registroWs = null;
		if (esRegistroSalida) {
			registroWs = new RegistroSalidaWs();
		} else {
			registroWs = new RegistroEntradaWs();
		}

		// Datos aplicacion
		registroWs.setAplicacion(getPropiedad(ConstantesRegweb3.PROP_APLICACION_CODIGO));
		registroWs.setVersion(getPropiedad(ConstantesRegweb3.PROP_APLICACION_VERSION));

		registroWs.setCodigoUsuario(getPropiedad(ConstantesRegweb3.PROP_USUARIO));

		// Datos oficina registro
		registroWs.setOficina(asiento.getDatosOrigen().getCodigoOficinaRegistro());
		registroWs.setLibro(asiento.getDatosOrigen().getLibroOficinaRegistro());
		if (esRegistroSalida) {
			((RegistroSalidaWs) registroWs).setOrigen(asiento.getDatosAsunto().getCodigoOrganoDestino());
		} else {
			((RegistroEntradaWs) registroWs).setDestino(asiento.getDatosAsunto().getCodigoOrganoDestino());
		}

		// Datos asunto
		registroWs.setExtracto(asiento.getDatosAsunto().getExtractoAsunto());
		registroWs.setDocFisica(new Long(ConstantesRegweb3.DOC_FISICA_REQUERIDA));
		registroWs.setIdioma(asiento.getDatosAsunto().getIdiomaAsunto());
		registroWs.setTipoAsunto(asiento.getDatosAsunto().getTipoAsunto());

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
		registroWs.getInteresados().add(interesadoWs);

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
				final String tipoDocumento = ConstantesRegweb3.TIPO_DOCUMENTO_ANEXO;
				final String validezDocumento = ConstantesRegweb3.VALIDEZ_DOCUMENTO_COPIA;
				final boolean anexarInterno = false;

				final DocumentoAsiento dr = (DocumentoAsiento) it.next();

				if (dr.getTipoDocumento() == TypeDocumental.FICHERO_TECNICO && anexarInternos) {
					anexoWs = generarAnexoWs(dr);
					registroWs.getAnexos().add(anexoWs);
				}

				if (!(dr.getTipoDocumento() == TypeDocumental.FICHERO_TECNICO) && anexarFormateados) {
					anexoWs = generarAnexoWs(dr);
					registroWs.getAnexos().add(anexoWs);
				}

			}
		}

		return registroWs;

	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *            propiedad
	 * @return valor
	 * @throws RegistroPluginException
	 */
	private String getPropiedad(String propiedad) throws RegistroPluginException {
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
	private AnexoWs generarAnexoWs(DocumentoAsiento dr) throws RegistroPluginException {

		final AnexoWs anexoAsiento = new AnexoWs();
		anexoAsiento.setTitulo(dr.getTituloDoc());
		anexoAsiento.setTipoDocumental(dr.getTipoDocumental());
		anexoAsiento.setTipoDocumento(dr.getTipoDocumento().toString());
		anexoAsiento.setOrigenCiudadanoAdmin(dr.getOrigenDocumento().intValue());
		anexoAsiento.setModoFirma(dr.getModoFirma().intValue());
		anexoAsiento.setValidezDocumento(dr.getValidez().toString());

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
			} else {
				anexoAsiento.setFirmaAnexada(dr.getContenidoFirma());
				anexoAsiento.setNombreFirmaAnexada(dr.getNombreFirmaAnexada());
				anexoAsiento.setTipoMIMEFirmaAnexada(
						MimeType.getMimeTypeForExtension(getExtension(dr.getNombreFirmaAnexada())));
			}
		} else {
			anexoAsiento.setNombreFicheroAnexado(dr.getNombreFichero());
			anexoAsiento.setFicheroAnexado(dr.getContenidoFichero());
			anexoAsiento
					.setTipoMIMEFicheroAnexado(MimeType.getMimeTypeForExtension(getExtension(dr.getNombreFichero())));
		}

		return anexoAsiento;
	}

	/**
	 * Obtiene extension fichero.
	 */
	private String getExtension(String filename) {
		if (filename.lastIndexOf(".") != -1) {
			return filename.substring(filename.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

}
