package es.caib.sistramit.core.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RAviso;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RLiteral;
import es.caib.sistrages.rest.api.interna.RLiteralIdioma;
import es.caib.sistrages.rest.api.interna.RLiteralScript;
import es.caib.sistrages.rest.api.interna.RPagoTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionPagar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.flujo.AvisoPlataforma;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.types.TypeAvisoEntidad;

/**
 * Utilidades para manejar modelo de STG.
 *
 * @author Indra
 *
 */
public final class UtilsSTG {

	/**
	 * Constructor.
	 */
	private UtilsSTG() {
		super();
	}

	/**
	 * Verifica si esta habilitado el debug.
	 *
	 * @param definicionTramite
	 *            Definicion trámite.
	 * @return debug habilitado
	 */
	public static boolean isDebugEnabled(final DefinicionTramiteSTG definicionTramite) {
		return definicionTramite.getDefinicionVersion().getControlAcceso().isDebug();
	}

	/**
	 * Devuelve definición paso tramitación
	 *
	 * @param pIdPaso
	 *            idPaso
	 * @param definicionTramite
	 *            Definición trámite
	 * @return Paso tramitación
	 */
	public static RPasoTramitacion devuelveDefinicionPaso(final String pIdPaso,
			final DefinicionTramiteSTG definicionTramite) {
		RPasoTramitacion res = null;
		for (final RPasoTramitacion p : definicionTramite.getDefinicionVersion().getPasos()) {
			if (p.getIdentificador().equals(pIdPaso)) {
				res = p;
				break;
			}
		}
		return res;
	}

	/**
	 * Devuelve definición paso tramitación registrar (solo puede existir un paso
	 * registrar).
	 *
	 * @param definicionTramite
	 *            Definición trámite
	 * @return Paso tramitación
	 */
	public static RPasoTramitacionRegistrar devuelveDefinicionPasoRegistrar(
			final DefinicionTramiteSTG definicionTramite) {
		RPasoTramitacion res = null;
		for (final RPasoTramitacion p : definicionTramite.getDefinicionVersion().getPasos()) {
			if (TypePaso.fromString(p.getTipo()) == TypePaso.REGISTRAR) {
				res = p;
				break;
			}
		}
		return (RPasoTramitacionRegistrar) res;
	}

	/**
	 * Obtiene literal para un idioma.
	 *
	 * @param descripcion
	 *            literal multiidioma
	 * @param idioma
	 *            idioma
	 * @return literal
	 */
	public static String obtenerLiteral(final RLiteral descripcion, final String idioma) {
		String res = null;
		if (descripcion != null && descripcion.getLiterales() != null) {
			for (final RLiteralIdioma rl : descripcion.getLiterales()) {
				if (rl.getIdioma().equals(idioma)) {
					res = rl.getDescripcion();
					break;
				}
			}
		}
		return StringUtils.defaultString(res);
	}

	/**
	 * Obtiene avisos aplicables al trámite.
	 *
	 * @param definicionTramiteSTG
	 *            definicion tramite
	 * @param avisosPlataforma
	 *            Avisos
	 * @param string
	 *            Idioma
	 * @param bloqueantes
	 *            Si solo recupera bloqueantes
	 * @return lista avisos
	 */
	public static List<AvisoPlataforma> obtenerAvisosTramite(final DefinicionTramiteSTG definicionTramiteSTG,
			final RAvisosEntidad avisosPlataforma, final String idioma, final boolean bloqueantes) {

		final List<AvisoPlataforma> avisos = new ArrayList<AvisoPlataforma>();
		final Date ahora = new Date();

		if (definicionTramiteSTG != null && avisosPlataforma.getAvisos() != null) {
			for (final RAviso avisoPlataforma : avisosPlataforma.getAvisos()) {

				boolean incluirAviso = false;

				final TypeAvisoEntidad tipoAvisoEntidad = TypeAvisoEntidad.fromString(avisoPlataforma.getTipo());

				if (tipoAvisoEntidad == null) {
					throw new TipoNoControladoException("Tipo de aviso no controlado: " + avisoPlataforma.getTipo());
				}

				switch (tipoAvisoEntidad) {
				case TODOS:
					incluirAviso = true;
					break;
				case AUTENTICADOS:
					incluirAviso = definicionTramiteSTG.getDefinicionVersion().getPropiedades().isAutenticado();
					break;
				case NOAUTENTICADOS:
					incluirAviso = definicionTramiteSTG.getDefinicionVersion().getPropiedades().isNoAutenticado();
					break;
				case FIRMA:
					incluirAviso = UtilsSTG.requiereFirma(definicionTramiteSTG);
					break;
				case TRAMITE_VERSION:
				case LISTA:
					// Filtro de tramites: lista de idTramite-version separados
					// por punto-coma
					final String[] ids = avisoPlataforma.getListaVersiones().split(";");
					incluirAviso = ArrayUtils.contains(ids,
							definicionTramiteSTG.getDefinicionVersion().getIdentificador() + "#"
									+ definicionTramiteSTG.getDefinicionVersion().getVersion());
					break;
				case PAGO:
					final List<RPasoTramitacion> pasosPago = UtilsSTG.devuelveDefinicionPasos(TypePaso.PAGAR,
							definicionTramiteSTG);
					incluirAviso = !pasosPago.isEmpty();
					break;
				case REGISTRO:
					final List<RPasoTramitacion> pasosRegistro = UtilsSTG.devuelveDefinicionPasos(TypePaso.REGISTRAR,
							definicionTramiteSTG);
					incluirAviso = !pasosRegistro.isEmpty();
					break;
				default:
					throw new TipoNoControladoException("Tipo de aviso no controlado: " + avisoPlataforma.getTipo());
				}

				if (incluirAviso) {
					// Verificamos fechas
					if (UtilsFlujo.estaDentroPlazo(ahora,
							UtilsFlujo.deformateaFecha(avisoPlataforma.getFechaInicio(),
									UtilsFlujo.FORMATO_FECHA_GENERICA),
							UtilsFlujo.deformateaFecha(avisoPlataforma.getFechaFin(),
									UtilsFlujo.FORMATO_FECHA_GENERICA))) {

						if (bloqueantes && !avisoPlataforma.isBloquear()) {
							continue;
						}

						avisos.add(AvisoPlataforma.createNewAvisoPlataforma(
								UtilsSTG.obtenerLiteral(avisoPlataforma.getMensaje(), idioma),
								avisoPlataforma.isBloquear()));
					}
				}
			}
		}

		return avisos;
	}

	/**
	 * Recupera pasos de un tipo.
	 *
	 * @param tipoPasoFiltro
	 *            Tipo paso
	 * @param definicionTramiteSTG
	 *            Definición trámite
	 * @return pasos de un tipo
	 */
	private static List<RPasoTramitacion> devuelveDefinicionPasos(final TypePaso tipoPasoFiltro,
			final DefinicionTramiteSTG definicionTramiteSTG) {
		final List<RPasoTramitacion> pasosTipo = new ArrayList<RPasoTramitacion>();
		// Recuperamos lista de pasos de un tipo (el paso GUARDAR
		// es virtual en el flujo normalizado)
		for (final RPasoTramitacion paso : definicionTramiteSTG.getDefinicionVersion().getPasos()) {
			final TypePaso tipoPaso = TypePaso.fromString(paso.getTipo());
			if (tipoPasoFiltro == tipoPaso) {
				pasosTipo.add(paso);
			}
		}
		return pasosTipo;
	}

	/**
	 * Verifica si el trámite requiere firma (tiene algún documento marcado para
	 * firmar).
	 *
	 * @param definicionTramiteSTG
	 *            Definición trámite
	 * @return boolean
	 */
	private static boolean requiereFirma(final DefinicionTramiteSTG definicionTramiteSTG) {
		boolean firma = false;

		for (final RPasoTramitacion paso : definicionTramiteSTG.getDefinicionVersion().getPasos()) {
			final TypePaso tipoPaso = TypePaso.fromString(paso.getTipo());

			switch (tipoPaso) {
			case RELLENAR:
				final RPasoTramitacionRellenar pasoRellenar = (RPasoTramitacionRellenar) paso;
				for (final RFormularioTramite f : pasoRellenar.getFormularios()) {
					if (f.isFirmar()) {
						firma = true;
						break;
					}
				}
				break;
			case ANEXAR:
				final RPasoTramitacionAnexar pasoAnexar = (RPasoTramitacionAnexar) paso;
				for (final RAnexoTramite a : pasoAnexar.getAnexos()) {
					if (a.getPresentacionElectronica() != null && a.getPresentacionElectronica().isFirmar()) {
						firma = true;
						break;
					}
				}
				break;
			default:
				break;
			}
		}

		return firma;
	}

	/**
	 * Método para recuperar el indice de un paso.
	 *
	 * @param idPaso
	 *            Id Paso
	 * @param definicionTramite
	 *            definicion tramite
	 * @return Indice del paso (empieza en 0). Si no lo encuentra devuelve -1.
	 */
	public static int devuelveIndicePaso(final String idPaso, final DefinicionTramiteSTG definicionTramite) {
		int indice = 0;
		boolean enc = false;
		for (final RPasoTramitacion paso : definicionTramite.getDefinicionVersion().getPasos()) {
			if (paso.getIdentificador().equals(idPaso)) {
				enc = true;
				break;
			}
			indice++;
		}
		if (!enc) {
			indice = ConstantesNumero.N_1;
		}
		return indice;
	}

	/**
	 * Comprueba si existe script.
	 *
	 * @param script
	 *            Parámetro script
	 * @return boolean
	 */
	public static boolean existeScript(final RScript script) {
		boolean res = false;
		if (script != null && StringUtils.isNotBlank(script.getScript())) {
			res = true;
		}
		return res;
	}

	/**
	 * Convierte literales mensajes script a un map.
	 *
	 * @param mensajesScript
	 *            mensajes script
	 * @return map
	 */
	public static Map<String, String> convertLiteralesToMap(final List<RLiteralScript> mensajesScript) {
		Map<String, String> res = null;
		if (mensajesScript != null && !mensajesScript.isEmpty()) {
			res = new HashMap<>();
			for (final RLiteralScript m : mensajesScript) {
				res.put(m.getIdentificador(), StringUtils.defaultString(m.getLiteral()));
			}
		}
		return res;
	}

	/**
	 * Método para recuperar la definición de un formulario de un paso rellenar.
	 *
	 * @param definicionPaso
	 *            Parámetro definicion paso
	 * @param idFormulario
	 *            Parámetro id formulario
	 * @return Definición del paso
	 */
	public static RFormularioTramite devuelveDefinicionFormulario(final RPasoTramitacionRellenar definicionPaso,
			final String idFormulario) {
		RFormularioTramite res = null;
		for (final RFormularioTramite form : definicionPaso.getFormularios()) {
			if (form.getIdentificador().equals(idFormulario)) {
				res = form;
				break;
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException(
					"No existe formulario con id " + idFormulario + " en paso " + definicionPaso.getIdentificador());
		}
		return res;
	}

	/**
	 * Método para recuperar la definición de un anexo de un paso anexar.
	 *
	 * @param definicionPaso
	 *            Parámetro definicion paso
	 * @param idAnexo
	 *            Parámetro id anexo
	 * @return Definición del paso
	 */
	public static RAnexoTramite devuelveDefinicionAnexo(final RPasoTramitacionAnexar definicionPaso,
			final String idAnexo) {
		RAnexoTramite res = null;
		for (final RAnexoTramite anexo : definicionPaso.getAnexos()) {
			if (anexo.getIdentificador().equals(idAnexo)) {
				res = anexo;
				break;
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException(
					"No existe anexo con id " + idAnexo + " en paso " + definicionPaso.getIdentificador());
		}
		return res;
	}

	/**
	 * Método para recuperar la definición de un pago de un paso pagar.
	 *
	 * @param definicionPaso
	 *            Parámetro definicion paso
	 * @param idPago
	 *            Id Pago
	 * @return Definición del pago
	 */
	public static RPagoTramite devuelveDefinicionPago(final RPasoTramitacionPagar definicionPaso, final String id) {
		RPagoTramite res = null;
		for (final RPagoTramite anexo : definicionPaso.getPagos()) {
			if (anexo.getIdentificador().equals(id)) {
				res = anexo;
				break;
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException(
					"No existe pago con id " + id + " en paso " + definicionPaso.getIdentificador());
		}
		return res;
	}

	/**
	 * Indica si el pago es simulado.
	 *
	 * @param idPaso
	 *            id paso
	 * @param idPago
	 *            id pago
	 * @param definicionTramite
	 *            Definición trámite
	 * @return indica si el pago es simulado.
	 */
	public static boolean isPagoSimulado(final String idPaso, final String idPago,
			final DefinicionTramiteSTG definicionTramite) {
		final RPasoTramitacionPagar pasoPagar = (RPasoTramitacionPagar) devuelveDefinicionPaso(idPaso,
				definicionTramite);
		final RPagoTramite pago = devuelveDefinicionPago(pasoPagar, idPago);
		return pago.isSimularPago();
	}

	/**
	 * Comprueba si el identificador de dominio existe en la lista de dominios de la
	 * definicion de version.
	 *
	 * @param idDominio
	 * @param defTramite
	 * @return
	 */
	public static boolean existeDominioDefinicion(final String idDominio, final DefinicionTramiteSTG defTramite) {

		boolean existe = false;
		if (defTramite != null && defTramite.getDefinicionVersion() != null
				&& defTramite.getDefinicionVersion().getDominios() != null) {
			for (final String dom : defTramite.getDefinicionVersion().getDominios()) {
				if (dom.equals(idDominio)) {
					existe = true;
					break;
				}
			}
		}
		return existe;
	}

	/**
	 * Devuelve definición formulario.
	 *
	 * @param defTramite
	 *            Definición trámite
	 * @param idPaso
	 *            id paso
	 * @param idFormulario
	 *            id formulario
	 * @return definición formulario
	 */
	public static RFormularioTramite devuelveDefinicionFormulario(final String idPaso, final String idFormulario,
			final DefinicionTramiteSTG defTramite) {
		RFormularioTramite defFormulario = null;
		final RPasoTramitacion defPaso = UtilsSTG.devuelveDefinicionPaso(idPaso, defTramite);
		final TypePaso tipoPaso = TypePaso.fromString(defPaso.getTipo());
		if (tipoPaso == TypePaso.RELLENAR) {
			defFormulario = UtilsSTG.devuelveDefinicionFormulario((RPasoTramitacionRellenar) defPaso, idFormulario);
		} else if (tipoPaso == TypePaso.CAPTURAR) {
			// TODO Pendiente
			throw new ErrorConfiguracionException("Tipo de paso no implementado");
		} else {
			throw new ErrorConfiguracionException(
					"Se ha indicado un tipo de paso que no tiene formularios: " + defPaso.getTipo());
		}
		return defFormulario;
	}

}
