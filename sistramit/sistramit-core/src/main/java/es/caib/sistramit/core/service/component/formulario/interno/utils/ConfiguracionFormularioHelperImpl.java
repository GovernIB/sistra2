package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteCampoOculto;
import es.caib.sistrages.rest.api.interna.RComponenteCheckbox;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPlantillaFormulario;
import es.caib.sistrages.rest.api.interna.RPlantillaIdioma;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.AccionFormulario;
import es.caib.sistramit.core.api.model.formulario.AccionFormularioNormalizada;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoCaptcha;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoOculto;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelector;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelectorDesplegable;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelectorDinamico;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelectorMultiple;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelectorUnico;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoCP;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoEmail;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoExpReg;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoFecha;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoHora;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoId;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoNormal;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoNumero;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoPassword;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoTelefono;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoVerificacion;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
import es.caib.sistramit.core.api.model.formulario.OpcionesSelectorDesplegable;
import es.caib.sistramit.core.api.model.formulario.ValoresCampoVerificacion;
import es.caib.sistramit.core.api.model.formulario.types.TypeAccionFormularioNormalizado;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.api.model.formulario.types.TypeSeparador;
import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResEstadoCampo;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Clase helper que contiene la logica de obtener la configuracion de los campos
 * de formulario.
 *
 * @author Indra
 *
 */
@Component("configuracionFormularioHelper")
public final class ConfiguracionFormularioHelperImpl implements ConfiguracionFormularioHelper {

	/** Motor de ejecución de scritps. */
	@Autowired
	private ScriptExec scriptFormulario;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public ConfiguracionCampo obtenerConfiguracionCampo(final RComponente pCampoDef) {
		// Creamos configuracion campo segun tipo y establecemos props
		// particulares
		ConfiguracionCampo confCampo = null;
		final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(pCampoDef.getTipo());
		if (tipoCampo == null) {
			throw new ErrorConfiguracionException(
					"Camp de tipus " + pCampoDef.getTipo() + " no soportat per camp " + pCampoDef.getIdentificador());
		}
		switch (tipoCampo) {
		case TEXTO:
			confCampo = obtenerConfiguracionCampoTexto((RComponenteTextbox) pCampoDef);
			break;
		case VERIFICACION:
			confCampo = obtenerConfiguracionCampoVerificacion((RComponenteCheckbox) pCampoDef);
			break;
		case SELECTOR:
			confCampo = obtenerConfiguracionCampoSelector((RComponenteSelector) pCampoDef);
			break;
		case OCULTO:
			confCampo = obtenerConfiguracionCampoOculto((RComponenteCampoOculto) pCampoDef);
			break;
		case CAPTCHA:
			confCampo = obtenerConfiguracionCampoCaptcha(pCampoDef);
			break;
		default:
			throw new TipoNoControladoException("Tipus de camp no suportat: " + tipoCampo.name());
		}
		return confCampo;
	}

	@Override
	public List<ConfiguracionModificadaCampo> evaluarEstadoCamposPagina(
			final DatosSesionFormularioInterno pDatosSesion) {

		final List<ConfiguracionModificadaCampo> resultado = new ArrayList<>();

		// Obtenemos definicion pagina actual
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(pDatosSesion);

		// Evaluamos estado dinamico de los campos
		for (final RComponente campoDef : UtilsFormularioInterno.devuelveListaCampos(paginaDef)) {
			final ResEstadoCampo estado = evaluarEstadoCampo(pDatosSesion, campoDef);
			if (estado != null) {
				final ConfiguracionModificadaCampo config = ConfiguracionModificadaCampo
						.createNewConfiguracionModificadaCampo();
				config.setId(campoDef.getIdentificador());
				config.setSoloLectura(estado.getEstadoCampo().getSoloLectura());
				resultado.add(config);
			}
		}

		return resultado;
	}

	@Override
	public ResEstadoCampo evaluarEstadoCampo(final DatosSesionFormularioInterno pDatosSesion,
			final RComponente pCampoDef) {

		final RPropiedadesCampo propsCampo = UtilsFormularioInterno.obtenerPropiedadesCampo(pCampoDef);

		ResEstadoCampo rse = null;
		if (!propsCampo.isSoloLectura() && UtilsSTG.existeScript(propsCampo.getScriptEstado())) {
			final VariablesFormulario variablesFormulario = UtilsFormularioInterno
					.generarVariablesFormulario(pDatosSesion, pCampoDef.getIdentificador());
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(propsCampo.getScriptEstado().getLiterales());
			final RespuestaScript rs = scriptFormulario.executeScriptFormulario(TypeScriptFormulario.SCRIPT_ESTADO,
					pCampoDef.getIdentificador(), propsCampo.getScriptEstado().getScript(), variablesFormulario,
					codigosError, pDatosSesion.getDefinicionTramite());
			rse = (ResEstadoCampo) rs.getResultado();
		}
		return rse;
	}

	@Override
	public RPlantillaFormulario obtenerPlantillaPdfVisualizacion(final DatosSesionFormularioInterno pDatosSesion) {

		final RFormularioTramite definicionFormulario = UtilsFormularioInterno
				.obtenerDefinicionFormulario(pDatosSesion);

		RPlantillaFormulario pf = null;

		// Plantillas definidas a nivel de formulario
		if (!definicionFormulario.getFormularioInterno().getPlantillas().isEmpty()) {
			final RScript scriptPlantillas = definicionFormulario.getFormularioInterno().getScriptPlantillas();
			if (UtilsSTG.existeScript(scriptPlantillas)) {
				final Map<String, String> codigosError = UtilsSTG
						.convertLiteralesToMap(scriptPlantillas.getLiterales());
				final VariablesFormulario variablesFormulario = UtilsFormularioInterno
						.generarVariablesFormulario(pDatosSesion, null);
				final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
						TypeScriptFormulario.SCRIPT_PLANTILLA_PDF_DINAMICA, pDatosSesion.getIdFormulario(),
						scriptPlantillas.getScript(), variablesFormulario, codigosError,
						pDatosSesion.getDefinicionTramite());
				final String idPlantilla = (String) rs.getResultado();
				for (final RPlantillaFormulario p : definicionFormulario.getFormularioInterno().getPlantillas()) {
					if (p.getIdentificador().equals(idPlantilla)) {
						pf = p;
						break;
					}
				}
			} else {
				for (final RPlantillaFormulario p : definicionFormulario.getFormularioInterno().getPlantillas()) {
					if (p.isDefecto()) {
						pf = p;
						break;
					}
				}
			}
		} else {
			// Plantilla definida a nivel de entidad
			final RConfiguracionEntidad entidad = configuracionComponent.obtenerConfiguracionEntidad(
					pDatosSesion.getDefinicionTramite().getDefinicionVersion().getIdEntidad());
			final List<RPlantillaIdioma> plantillasDefecto = entidad.getPlantillasDefecto();
			if (plantillasDefecto != null) {
				for (final RPlantillaIdioma pi : plantillasDefecto) {
					if (pi.getIdioma().equals(pDatosSesion.getDatosInicioSesion().getIdioma())) {
						pf = pi.getPlantilla();
						break;
					}
				}
			}
		}

		if (pf == null) {
			throw new ErrorConfiguracionException(
					"No s'ha pogut obtenir plantilla pdf per formulari " + pDatosSesion.getIdFormulario());
		}

		return pf;
	}

	@Override
	public List<AccionFormulario> evaluarAccionesPaginaActual(final DatosSesionFormularioInterno datosSesion) {
		// TODO PERSONALIZADO PENDIENTE ACCIONES PERSONALIZADAS
		final int indiceDefPagina = datosSesion.getDatosFormulario().getPaginaActualFormulario().getIndiceDef();
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPagina(datosSesion,
				indiceDefPagina);
		final List<AccionFormulario> acciones = new ArrayList<>();
		acciones.add(new AccionFormularioNormalizada(TypeAccionFormularioNormalizado.CANCELAR));
		if (datosSesion.getDatosFormulario().getIndicePaginaActual() > 1) {
			acciones.add(new AccionFormularioNormalizada(TypeAccionFormularioNormalizado.ANTERIOR));
		}
		if (!paginaDef.isPaginaFinal()) {
			acciones.add(new AccionFormularioNormalizada(TypeAccionFormularioNormalizado.SIGUIENTE));
		}
		if (paginaDef.isPaginaFinal()) {
			acciones.add(new AccionFormularioNormalizada(TypeAccionFormularioNormalizado.FINALIZAR));
		}
		return acciones;
	}

	@Override
	public String evaluarScriptNavegacionPaginaActual(final DatosSesionFormularioInterno pDatosSesion) {

		String idPaginaDestino = null;

		// - Definicion pagina actual
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(pDatosSesion);

		// Ejecutamos script validacion pagina
		if (UtilsSTG.existeScript(paginaDef.getScriptNavegacion())) {
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(paginaDef.getScriptNavegacion().getLiterales());
			final VariablesFormulario variablesFormulario = UtilsFormularioInterno
					.generarVariablesFormulario(pDatosSesion, null);
			final String idPagina = pDatosSesion.getDatosFormulario().getPaginaActualFormulario().getIdentificador();
			final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
					TypeScriptFormulario.SCRIPT_VALIDACION_PAGINA, idPagina,
					paginaDef.getScriptNavegacion().getScript(), variablesFormulario, codigosError,
					pDatosSesion.getDefinicionTramite());
			idPaginaDestino = (String) rs.getResultado();
		} else {
			// Si no hay script navegacion, sera la siguiente pagina
			final int indiceDefDestino = pDatosSesion.getDatosFormulario().getPaginaActualFormulario().getIndiceDef()
					+ 1;
			final RPaginaFormulario pagDestino = UtilsFormularioInterno.obtenerDefinicionPagina(pDatosSesion,
					indiceDefDestino);
			idPaginaDestino = pagDestino.getIdentificador();
		}

		return idPaginaDestino;

	}

	// -----------------------------------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -----------------------------------------------------------------------------------------------

	/**
	 * Obtiene configuracion campo selector.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return configuracion campo selector.
	 */
	private ConfiguracionCampoSelector obtenerConfiguracionCampoSelector(final RComponenteSelector pCampoDef) {
		final TypeSelector tipoSelector = UtilsSTG.traduceTipoSelector(pCampoDef.getTipoSelector());
		if (tipoSelector == null) {
			throw new ErrorConfiguracionException("Camp de tipus selector " + pCampoDef.getTipoSelector()
					+ " no suportat per camp " + pCampoDef.getIdentificador());
		}
		ConfiguracionCampoSelector confCampoSelector = null;
		switch (tipoSelector) {
		case LISTA:
			confCampoSelector = new ConfiguracionCampoSelectorDesplegable();
			if (pCampoDef.isIndiceAlfabetico()) {
				final OpcionesSelectorDesplegable opcs = new OpcionesSelectorDesplegable();
				opcs.setIndice(TypeSiNo.SI);
				((ConfiguracionCampoSelectorDesplegable) confCampoSelector).setOpciones(opcs);
			}
			break;
		case UNICO:
			confCampoSelector = new ConfiguracionCampoSelectorUnico();
			break;
		case MULTIPLE:
			confCampoSelector = new ConfiguracionCampoSelectorMultiple();
			break;
		case DINAMICO:
			confCampoSelector = new ConfiguracionCampoSelectorDinamico();
			break;
		default:
			throw new ErrorConfiguracionException("Camp de tipus selector " + pCampoDef.getTipoSelector()
					+ " no suportat per camp " + pCampoDef.getIdentificador());
		}

		// Establecemos propiedades generales
		establecerPropiedadesGenerales(pCampoDef, confCampoSelector);

		return confCampoSelector;
	}

	/**
	 * Obtiene configuracion campo verificacion.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return configuracion campo verificacion
	 */
	private ConfiguracionCampoVerificacion obtenerConfiguracionCampoVerificacion(final RComponenteCheckbox pCampoDef) {
		final ConfiguracionCampoVerificacion confCampoVerif = new ConfiguracionCampoVerificacion();
		// Establecemos propiedades generales
		establecerPropiedadesGenerales(pCampoDef, confCampoVerif);
		// Establecemos propiedades específicas
		final ValoresCampoVerificacion valoresVerif = new ValoresCampoVerificacion();
		valoresVerif.setChecked(pCampoDef.getValorChecked());
		valoresVerif.setNoChecked(pCampoDef.getValorNoChecked());
		confCampoVerif.setValores(valoresVerif);
		return confCampoVerif;
	}

	/**
	 * Obtiene configuración campo oculto.
	 *
	 * @param pCampoDef
	 *                      Definición campo
	 * @return configuración campo oculto
	 */
	private ConfiguracionCampo obtenerConfiguracionCampoOculto(final RComponenteCampoOculto pCampoDef) {
		final ConfiguracionCampoOculto confCampoOculto = new ConfiguracionCampoOculto();
		// Establecemos propiedades generales
		establecerPropiedadesGenerales(pCampoDef, confCampoOculto);
		return confCampoOculto;
	}

	/**
	 * Obtiene configuración de campo de tipo texto.
	 *
	 * @param pCampoDef
	 *                      Definición campo
	 * @return configuración
	 */
	private ConfiguracionCampo obtenerConfiguracionCampoTexto(final RComponenteTextbox pCampoDef) {

		ConfiguracionCampo confCampo = null;

		// Creamos configuración específica según el tipo de campo de texto
		final TypeTexto typeTexto = UtilsSTG.traduceTipoTexto(pCampoDef.getTipoTexto());
		if (typeTexto == null) {
			throw new ErrorConfiguracionException("Camp de text de tipus " + pCampoDef.getTipoTexto()
					+ " no soportat per camp " + pCampoDef.getIdentificador());
		}

		switch (typeTexto) {
		case NORMAL: // Normal
			confCampo = obtenerConfCampoTextoNormal(pCampoDef);
			break;
		case NUMERO: // Numero
			confCampo = obtenerConfCampoTextoNumero(pCampoDef);
			break;
		case CODIGO_POSTAL: // Codigo postal
			final ConfiguracionCampoTextoCP confCampoCP = new ConfiguracionCampoTextoCP();
			confCampo = confCampoCP;
			break;
		case EMAIL: // Email
			confCampo = obtenerConfCampoTextoEmail(pCampoDef);
			break;
		case FECHA: // Fecha
			final ConfiguracionCampoTextoFecha confCampoFecha = new ConfiguracionCampoTextoFecha();
			confCampo = confCampoFecha;
			break;
		case HORA: // Fecha
			final ConfiguracionCampoTextoHora confCampoHora = new ConfiguracionCampoTextoHora();
			confCampo = confCampoHora;
			break;
		case IDENTIFICADOR: // Identificador
			confCampo = obtenerConfCampoTextoIdentificador(pCampoDef);
			break;
		case TELEFONO: // Telefono
			confCampo = obtenerConfCampoTextoTelefono(pCampoDef);
			break;
		case EXPRESION_REGULAR: // Expresion regular
			confCampo = obtenerConfCampoTextoExpreg(pCampoDef);
			break;
		case PASSWORD: // Password
			confCampo = new ConfiguracionCampoTextoPassword();
			break;
		default:
			throw new TipoNoControladoException("Tipus de camp texto no controlat: " + pCampoDef.getTipoTexto());
		}

		// Establecemos propiedades generales
		establecerPropiedadesGenerales(pCampoDef, confCampo);

		return confCampo;
	}

	/**
	 * Configuracion campo texto exp regular.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoExpreg(final RComponenteTextbox pCampoDef) {
		final ConfiguracionCampoTextoExpReg confCampoExp = new ConfiguracionCampoTextoExpReg();
		confCampoExp.getOpciones().setRegexp(pCampoDef.getTextoExpRegular().getExpresionRegular());
		return confCampoExp;
	}

	/**
	 * Configuracion campo texto telefono.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoTelefono(final RComponenteTextbox pCampoDef) {
		final ConfiguracionCampoTextoTelefono confCampoTel = new ConfiguracionCampoTextoTelefono();
		confCampoTel.getOpciones().setFijo(TypeSiNo.fromBoolean(pCampoDef.getTextoTelefono().isFijo()));
		confCampoTel.getOpciones().setMovil(TypeSiNo.fromBoolean(pCampoDef.getTextoTelefono().isMovil()));
		return confCampoTel;
	}

	/**
	 * Configuracion campo texto normal.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoNormal(final RComponenteTextbox pCampoDef) {
		ConfiguracionCampo confCampo;
		final ConfiguracionCampoTextoNormal confCampoNormal = new ConfiguracionCampoTextoNormal();
		confCampo = confCampoNormal;
		confCampoNormal.getOpciones().setLineas(pCampoDef.getTextoNormal().getLineas());
		confCampoNormal.getOpciones().setTamanyo(pCampoDef.getTextoNormal().getTamanyoMax());
		confCampoNormal.getOpciones()
				.setMayusculas(TypeSiNo.fromBoolean(pCampoDef.getTextoNormal().isForzarMayusculas()));
		return confCampo;
	}

	/**
	 * Configuracion campo texto email.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoEmail(final RComponenteTextbox pCampoDef) {
		ConfiguracionCampo confCampo;
		final ConfiguracionCampoTextoEmail confCampoEmail = new ConfiguracionCampoTextoEmail();
		confCampo = confCampoEmail;
		confCampoEmail.getOpciones().setTamanyo(pCampoDef.getTextoEmail().getTamanyoMax());
		return confCampo;
	}

	/**
	 * Configuracion campo texto numero.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoNumero(final RComponenteTextbox pCampoDef) {

		final ConfiguracionCampoTextoNumero confCampoNumero = new ConfiguracionCampoTextoNumero();

		final TypeSeparador typeSeparador = UtilsSTG
				.traduceTipoSeparador(pCampoDef.getTextoNumero().getFormatoNumero());

		confCampoNumero.getOpciones().setSeparador(typeSeparador);

		confCampoNumero.getOpciones().setEnteros(pCampoDef.getTextoNumero().getPrecisionEntera());
		confCampoNumero.getOpciones().setDecimales(pCampoDef.getTextoNumero().getPrecisionDecimal());
		if (pCampoDef.getTextoNumero().isNegativos()) {
			confCampoNumero.getOpciones().setNegativo(TypeSiNo.SI);
		}

		if (pCampoDef.getTextoNumero().getRangoDesde() >= 0 && pCampoDef.getTextoNumero().getRangoHasta() > 0) {
			confCampoNumero.getOpciones().setRangoMin(pCampoDef.getTextoNumero().getRangoDesde());
			confCampoNumero.getOpciones().setRangoMax(pCampoDef.getTextoNumero().getRangoHasta());
		}

		return confCampoNumero;
	}

	/**
	 * Configuracion campo texto identificador.
	 *
	 * @param pCampoDef
	 *                      Definicion campo
	 * @return configuracion campo
	 */
	private ConfiguracionCampo obtenerConfCampoTextoIdentificador(final RComponenteTextbox pCampoDef) {
		final ConfiguracionCampoTextoId confCampoId = new ConfiguracionCampoTextoId();
		confCampoId.getOpciones().setDni(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isDni()));
		confCampoId.getOpciones().setNie(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isNie()));
		confCampoId.getOpciones().setNifOtros(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isNifOtros()));
		confCampoId.getOpciones().setNifPJ(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isNif()));
		confCampoId.getOpciones().setNss(TypeSiNo.fromBoolean(pCampoDef.getTextoIdentificacion().isNss()));
		return confCampoId;
	}

	/**
	 * Establece propiedades generales campo.
	 *
	 * @param pCampoDef
	 *                      Definición campo
	 * @param confCampo
	 *                      Configuración campo
	 */
	private void establecerPropiedadesGenerales(final RComponente pCampoDef, final ConfiguracionCampo confCampo) {
		// Establecemos props generales
		confCampo.setAyuda(pCampoDef.getAyuda());
		final RPropiedadesCampo propsGenerales = UtilsFormularioInterno.obtenerPropiedadesCampo(pCampoDef);
		confCampo.setId(pCampoDef.getIdentificador());
		if (propsGenerales.isObligatorio()) {
			confCampo.setObligatorio(TypeSiNo.SI);
		} else {
			confCampo.setObligatorio(TypeSiNo.NO);
		}
		if (propsGenerales.isSoloLectura()) {
			confCampo.setSoloLectura(TypeSiNo.SI);
		} else {
			confCampo.setSoloLectura(TypeSiNo.NO);
		}
		if (propsGenerales.isNoModificable()) {
			confCampo.setModificable(TypeSiNo.NO);
		} else {
			confCampo.setModificable(TypeSiNo.SI);
		}
		confCampo.setEvaluar(TypeSiNo.fromBoolean(UtilsSTG.existeScript(propsGenerales.getScriptValidacion())));
	}

	/**
	 * Obtiene configuración campo captcha.
	 *
	 * @param pCampoDef
	 *                      Definición campo
	 * @return Configuración campo
	 */
	private ConfiguracionCampo obtenerConfiguracionCampoCaptcha(final RComponente pCampoDef) {
		final ConfiguracionCampoCaptcha confCampo = ConfiguracionCampoCaptcha
				.createNewConfiguracionCampoCaptcha(pCampoDef.getIdentificador());
		return confCampo;
	}

}
