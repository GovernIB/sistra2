package es.caib.sistramit.core.service.util;

import java.util.ArrayList;
import java.util.Arrays;
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
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RGestorFormularioExterno;
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
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.api.model.formulario.types.TypeSeparador;
import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeListaValores;
import es.caib.sistramit.core.service.model.formulario.interno.types.TypeParametroDominio;
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
	 * @param definicionTramite Definicion trámite.
	 * @return debug habilitado
	 */
	public static boolean isDebugEnabled(final DefinicionTramiteSTG definicionTramite) {
		return definicionTramite.getDefinicionVersion().getControlAcceso().isDebug();
	}

	/**
	 * Devuelve definición paso tramitación
	 *
	 * @param pIdPaso           idPaso
	 * @param definicionTramite Definición trámite
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
	 * @param definicionTramite Definición trámite
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
	 * @param descripcion literal multiidioma
	 * @param idioma      idioma
	 * @param defecto     si no existe el idioma, devuelve uno por defecto
	 * @return literal
	 */
	public static String obtenerLiteral(final RLiteral descripcion, final String idioma, final boolean defecto) {
		String res = null;
		if (descripcion != null && descripcion.getLiterales() != null) {
			for (final RLiteralIdioma rl : descripcion.getLiterales()) {
				if (rl.getIdioma().equals(idioma)) {
					res = rl.getDescripcion();
					break;
				}
			}
		}

		if (res == null && defecto && descripcion != null && descripcion.getLiterales() != null
				&& !descripcion.getLiterales().isEmpty()) {
			res = descripcion.getLiterales().get(0).getDescripcion();
		}

		return StringUtils.defaultString(res);
	}

	/**
	 * Obtiene literal para un idioma.
	 *
	 * @param descripcion literal multiidioma
	 * @param idioma      idioma
	 * @return literal
	 */
	public static String obtenerLiteral(final RLiteral descripcion, final String idioma) {
		return obtenerLiteral(descripcion, idioma, false);
	}

	/**
	 * Obtiene avisos aplicables al trámite.
	 *
	 * @param definicionTramiteSTG definicion tramite
	 * @param avisosPlataforma     Avisos
	 * @param string               Idioma
	 * @param bloqueantes          Si solo recupera bloqueantes
	 * @return lista avisos
	 */
	public static List<AvisoPlataforma> obtenerAvisosTramite(final DefinicionTramiteSTG definicionTramiteSTG,
			final RAvisosEntidad avisosPlataforma, final String idioma, final boolean bloqueantes) {

		final List<AvisoPlataforma> avisos = new ArrayList<>();
		final Date ahora = new Date();

		if (definicionTramiteSTG != null && avisosPlataforma.getAvisos() != null) {
			for (final RAviso avisoPlataforma : avisosPlataforma.getAvisos()) {

				boolean incluirAviso = false;

				final TypeAvisoEntidad tipoAvisoEntidad = TypeAvisoEntidad.fromString(avisoPlataforma.getTipo());

				if (tipoAvisoEntidad == null) {
					throw new TipoNoControladoException("Tipus d'avís no controlat: " + avisoPlataforma.getTipo());
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
				case ANEXO:
					incluirAviso = UtilsSTG.requiereAnexo(definicionTramiteSTG);
					break;
				default:
					throw new TipoNoControladoException("Tipús d'avís no controlat: " + avisoPlataforma.getTipo());
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
	 * @param tipoPasoFiltro       Tipo paso
	 * @param definicionTramiteSTG Definición trámite
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
	 * @param definicionTramiteSTG Definición trámite
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
	 * Verifica si el trámite requiere anexos
	 *
	 * @param definicionTramiteSTG Definición trámite
	 * @return boolean
	 */
	private static boolean requiereAnexo(final DefinicionTramiteSTG definicionTramiteSTG) {
		boolean anexar = false;

		for (final RPasoTramitacion paso : definicionTramiteSTG.getDefinicionVersion().getPasos()) {
			final TypePaso tipoPaso = TypePaso.fromString(paso.getTipo());

			if (tipoPaso.equals(TypePaso.ANEXAR)) {
				final RPasoTramitacionAnexar pasoAnexar = (RPasoTramitacionAnexar) paso;
				if (pasoAnexar.getAnexos().size() > 0 || (pasoAnexar.getScriptAnexosDinamicos() != null)
						&& (!pasoAnexar.getScriptAnexosDinamicos().getScript().isEmpty())) {
					anexar = true;
				}
			}
		}

		return anexar;
	}

	/**
	 * Método para recuperar el indice de un paso.
	 *
	 * @param idPaso            Id Paso
	 * @param definicionTramite definicion tramite
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
	 * @param script Parámetro script
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
	 * @param mensajesScript mensajes script
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
	 * @param definicionPaso Parámetro definicion paso
	 * @param idFormulario   Parámetro id formulario
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
					"No existeix formulari amb id " + idFormulario + " en passa " + definicionPaso.getIdentificador());
		}
		return res;
	}

	/**
	 * Método para recuperar la definición de un anexo de un paso anexar.
	 *
	 * @param definicionPaso Parámetro definicion paso
	 * @param idAnexo        Parámetro id anexo
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
					"No existeix annex amb id " + idAnexo + " en passa " + definicionPaso.getIdentificador());
		}
		return res;
	}

	/**
	 * Método para recuperar la definición de un pago de un paso pagar.
	 *
	 * @param definicionPaso Parámetro definicion paso
	 * @param idPago         Id Pago
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
					"No existeix pagament amb id " + id + " en passa " + definicionPaso.getIdentificador());
		}
		return res;
	}

	/**
	 * Indica si el pago es simulado.
	 *
	 * @param idPaso            id paso
	 * @param idPago            id pago
	 * @param definicionTramite Definición trámite
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
	 * @param defTramite   Definición trámite
	 * @param idPaso       id paso
	 * @param idFormulario id formulario
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
			throw new ErrorConfiguracionException("Tipus de passa no implementada");
		} else {
			throw new ErrorConfiguracionException(
					"S'ha indicat un tipus de passa que no té formularis: " + defPaso.getTipo());
		}
		return defFormulario;
	}

	/**
	 * Traduce tipo campo proveniente de STG.
	 *
	 * @param tipoComponente tipo componente STG
	 * @return tipo campo
	 */
	public static TypeCampo traduceTipoCampo(final String tipoComponente) {
		TypeCampo tipo = null;
		if ("CT".equals(tipoComponente)) {
			tipo = TypeCampo.TEXTO;
		} else if ("SE".equals(tipoComponente)) {
			tipo = TypeCampo.SELECTOR;
		} else if ("CK".equals(tipoComponente)) {
			tipo = TypeCampo.VERIFICACION;
		} else if ("OC".equals(tipoComponente)) {
			tipo = TypeCampo.OCULTO;
		} else if ("CP".equals(tipoComponente)) {
			tipo = TypeCampo.CAPTCHA;
		}
		return tipo;
	}

	/**
	 * Traduce tipo texto proveniente de STG.
	 *
	 * @param tipoTexto tipo texto STG
	 * @return tipo texto
	 */
	public static TypeTexto traduceTipoTexto(final String tipoTexto) {

		TypeTexto res = null;

		if ("NORMAL".equals(tipoTexto)) {
			res = TypeTexto.NORMAL;
		} else if ("NUMERO".equals(tipoTexto)) {
			res = TypeTexto.NUMERO;
		} else if ("EMAIL".equals(tipoTexto)) {
			res = TypeTexto.EMAIL;
		} else if ("ID".equals(tipoTexto)) {
			res = TypeTexto.IDENTIFICADOR;
		} else if ("CP".equals(tipoTexto)) {
			res = TypeTexto.CODIGO_POSTAL;
		} else if ("TELEFONO".equals(tipoTexto)) {
			res = TypeTexto.TELEFONO;
		} else if ("FECHA".equals(tipoTexto)) {
			res = TypeTexto.FECHA;
		} else if ("HORA".equals(tipoTexto)) {
			res = TypeTexto.HORA;
		} else if ("EXPRESION".equals(tipoTexto)) {
			res = TypeTexto.EXPRESION_REGULAR;
		} else if ("IBAN".equals(tipoTexto)) {
			res = TypeTexto.IBAN;
		}

		return res;
	}

	/**
	 * Traduce tipo proveniente de STG.
	 *
	 * @param tipoSelector tipo STG
	 * @return tipo STT
	 */
	public static TypeSelector traduceTipoSelector(final String tipoSelector) {
		TypeSelector res = null;
		if ("DESPLEGABLE".equals(tipoSelector)) {
			res = TypeSelector.LISTA;
		} else if ("MULTIPLE".equals(tipoSelector)) {
			res = TypeSelector.MULTIPLE;
		} else if ("UNICA".equals(tipoSelector)) {
			res = TypeSelector.UNICO;
		} else if ("DINAMICO".equals(tipoSelector)) {
			res = TypeSelector.DINAMICO;
		}
		return res;
	}

	/**
	 * Traduce tipo proveniente de STG.
	 *
	 * @param tipoSeparador tipo STG
	 * @return tipo STT
	 */
	public static TypeSeparador traduceTipoSeparador(final String tipoSeparador) {
		TypeSeparador res = null;
		if ("PC".equals(tipoSeparador)) {
			res = TypeSeparador.PUNTO_COMA;
		} else if ("CP".equals(tipoSeparador)) {
			res = TypeSeparador.COMA_PUNTO;
		}
		return res;
	}

	/**
	 * Traduce tipo proveniente de STG.
	 *
	 * @param tipoListaValores tipo STG
	 * @return tipo STT
	 */
	public static TypeListaValores traduceTipoListaValores(final String tipoListaValores) {
		TypeListaValores res = null;
		if ("F".equals(tipoListaValores)) {
			res = TypeListaValores.FIJA;
		} else if ("D".equals(tipoListaValores)) {
			res = TypeListaValores.DOMINIO;
		} else if ("S".equals(tipoListaValores)) {
			res = TypeListaValores.SCRIPT;
		}
		return res;
	}

	/**
	 * Traduce tipo proveniente de STG.
	 *
	 * @param tipoParametroDominio tipo STG
	 * @return tipo STT
	 */
	public static TypeParametroDominio traduceTipoParametroDominio(final String tipoParametroDominio) {
		TypeParametroDominio res = null;
		if ("C".equals(tipoParametroDominio)) {
			res = TypeParametroDominio.CONSTANTE;
		} else if ("M".equals(tipoParametroDominio)) {
			res = TypeParametroDominio.CAMPO;
		} else if ("P".equals(tipoParametroDominio)) {
			res = TypeParametroDominio.PARAMETRO;
		} else if ("S".equals(tipoParametroDominio)) {
			res = TypeParametroDominio.SESION;
		} else if ("B".equals(tipoParametroDominio)) {
			res = TypeParametroDominio.BUSQUEDA;
		}
		return res;
	}

	/**
	 * Obtiene configuracion gestor formulario
	 *
	 * @param confEntidad                configuracion entidad
	 * @param idGestorFormulariosExterno id gestor formulario
	 * @return configuracion gestor formulario
	 */
	public static RGestorFormularioExterno obtenerConfiguracionGFE(final RConfiguracionEntidad confEntidad,
			final String idGestorFormulariosExterno) {
		RGestorFormularioExterno confGfe = null;
		if (confEntidad.getGestoresFormulariosExternos() != null) {
			for (final RGestorFormularioExterno rgfe : confEntidad.getGestoresFormulariosExternos()) {
				if (rgfe.getIdentificador().equals(idGestorFormulariosExterno)) {
					confGfe = rgfe;
					break;
				}
			}
		}
		if (confGfe == null) {
			throw new ErrorConfiguracionException("No es troba GFE: " + idGestorFormulariosExterno);
		}
		return confGfe;
	}

	/**
	 * Convierte metodos autenticado.
	 *
	 * @param metodosAutenticacion Metodos autenticación
	 * @return metodos autenticado.
	 */
	public static List<TypeMetodoAutenticacion> convertMetodosAutenticado(final String metodosAutenticacion) {
		final List<TypeMetodoAutenticacion> res = new ArrayList<>();
		if (StringUtils.isNotBlank(metodosAutenticacion)) {
			final List<String> list = Arrays.asList(metodosAutenticacion.split(";"));
			if (list.contains("CER")) {
				res.add(TypeMetodoAutenticacion.CLAVE_CERTIFICADO);
			}
			if (list.contains("PIN")) {
				res.add(TypeMetodoAutenticacion.CLAVE_PIN);
			}
			if (list.contains("PER")) {
				res.add(TypeMetodoAutenticacion.CLAVE_PERMANENTE);
			}
		}
		return res;
	}

}
