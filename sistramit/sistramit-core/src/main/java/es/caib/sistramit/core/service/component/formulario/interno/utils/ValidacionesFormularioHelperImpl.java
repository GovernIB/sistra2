package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistra2.commons.utils.ValidacionTipoException;
import es.caib.sistra2.commons.utils.ValidacionesCadena;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTexto;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoEmail;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoExpReg;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoId;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoNormal;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoNumero;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoPassword;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoTelefono;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoVerificacion;
import es.caib.sistramit.core.api.model.formulario.MensajeValidacion;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoEmail;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoExpReg;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoId;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoNormal;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoNumero;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoPassword;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValoresCampoVerificacion;
import es.caib.sistramit.core.api.model.formulario.types.TypeSeparador;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.VariablesFormulario;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFormulario;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Component("validacionesFormularioHelper")
public final class ValidacionesFormularioHelperImpl implements ValidacionesFormularioHelper {

	/** Motor de ejecución de scritps. */
	@Autowired
	private ScriptExec scriptFormulario;

	/** Literales negocio. */
	@Autowired
	private Literales literales;

	@Override
	public void validarConfiguracionCampos(final DatosSesionFormularioInterno pDatosSesion) {

		boolean validacionCorrecta = true;
		String idCampoError = null;

		for (final ConfiguracionCampo configuracion : pDatosSesion.getDatosFormulario().getPaginaActualFormulario()
				.getConfiguracion()) {

			// Obtenemos valor campo
			final ValorCampo vc = pDatosSesion.getDatosFormulario().getPaginaActualFormulario()
					.getValorCampo(configuracion.getId());

			// Comprobamos si esta vacio el campo
			if (vc.esVacio()) {
				// Si es obligatorio y esta vacio no pasa la validacion
				if (configuracion.getObligatorio() == TypeSiNo.SI) {
					validacionCorrecta = false;
				}
			} else {
				// Comprobamos que contenga valores permitidos
				validacionCorrecta = UtilsFormulario.comprobarCaracteresPermitidos(vc);
				// Validacion especifica por tipo de campo
				if (validacionCorrecta) {
					switch (configuracion.getTipo()) {
					case TEXTO:
						validacionCorrecta = validacionConfiguracionCampoTexto(configuracion, vc);
						break;
					case SELECTOR:
						// No hay comprobaciones posibles
						break;
					case VERIFICACION:
						validacionCorrecta = validacionConfiguracionCampoVerificacion(configuracion, vc);
						break;
					case OCULTO:
						// No hay comprobaciones posibles
						break;
					case CAPTCHA:
						// No hay comprobaciones posibles
						break;
					case LISTA_ELEMENTOS:
						throw new TipoNoControladoException(
								"Tipus de camp NO ESTÀ IMPLEMENTAT: " + configuracion.getTipo());
					default:
						// Tipo no permitido
						throw new TipoNoControladoException("Tipus de camp no controlat: " + configuracion.getTipo());
					}
				}
			}

			// Si se ha producido un error de validacion paramos de validar
			if (!validacionCorrecta) {
				idCampoError = configuracion.getId();
				break;
			}

		}

		// En caso de que se haya producido error de validacion devolvemos error
		// generico
		String errorMsg = null;
		if (!validacionCorrecta) {
			errorMsg = literales.getLiteral(Literales.GESTOR_FORMULARIOS_INTERNO, "validacion.servidor.incorrecta",
					new String[] { idCampoError }, pDatosSesion.getDatosInicioSesion().getIdioma());
			throw new ErrorConfiguracionException(errorMsg);
		}

	}

	@Override
	public MensajeValidacion validarScriptValidacionPagina(final DatosSesionFormularioInterno datosSesion) {

		MensajeValidacion mensaje = null;

		// - Definicion pagina actual
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);

		// Ejecutamos script validacion pagina
		if (UtilsSTG.existeScript(paginaDef.getScriptValidacion())) {
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(paginaDef.getScriptValidacion().getLiterales());
			final VariablesFormulario variablesFormulario = UtilsFormularioInterno
					.generarVariablesFormulario(datosSesion, null);

			// Id pagina
			final String idPagina = datosSesion.getDatosFormulario().getPaginaActualFormulario().getIdentificador();

			final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
					TypeScriptFormulario.SCRIPT_VALIDACION_PAGINA, idPagina,
					paginaDef.getScriptValidacion().getScript(), variablesFormulario, codigosError,
					datosSesion.getDefinicionTramite());
			mensaje = rs.getMensajeValidacion();
		}

		return mensaje;

	}

	@Override
	public MensajeValidacion validarScriptValidacionCampos(final DatosSesionFormularioInterno datosSesion) {

		MensajeValidacion mensaje = null;

		// - Definicion pagina actual
		final RPaginaFormulario paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaActual(datosSesion);

		for (final RComponente campo : UtilsFormularioInterno.devuelveListaCampos(paginaDef)) {
			final RPropiedadesCampo propsCampo = UtilsFormularioInterno.obtenerPropiedadesCampo(campo);
			if (UtilsSTG.existeScript(propsCampo.getScriptValidacion())) {
				// Ejecutamos script validacion
				final Map<String, String> codigosError = UtilsSTG
						.convertLiteralesToMap(propsCampo.getScriptValidacion().getLiterales());
				final VariablesFormulario variablesFormulario = UtilsFormularioInterno
						.generarVariablesFormulario(datosSesion, campo.getIdentificador());
				final RespuestaScript rs = scriptFormulario.executeScriptFormulario(
						TypeScriptFormulario.SCRIPT_VALIDACION_CAMPO, campo.getIdentificador(),
						propsCampo.getScriptValidacion().getScript(), variablesFormulario, codigosError,
						datosSesion.getDefinicionTramite());
				// Al validar en conjunto los campos solo paramos si hay error
				if (UtilsFlujo.isErrorValidacion(rs.getMensajeValidacion())) {
					mensaje = rs.getMensajeValidacion();
					break;
				}
			}
		}

		return mensaje;
	}

	// ---------------------------------------------
	// METODOS PRIVADOS
	// ---------------------------------------------

	/**
	 * Valida campo texto en funcion de su configuracion.
	 *
	 * @param configuracion
	 *                          Configuracion
	 * @param vc
	 *                          Valor campo
	 * @return boolean
	 */
	private boolean validacionConfiguracionCampoTexto(final ConfiguracionCampo configuracion, final ValorCampo vc) {

		boolean validacionCorrecta = true;

		final ValorCampoSimple vcs = (ValorCampoSimple) vc;
		final ConfiguracionCampoTexto confTexto = (ConfiguracionCampoTexto) configuracion;

		switch (confTexto.getContenido()) {
		case NORMAL:
			validacionCorrecta = validacionConfiguracionCampoTextoNormal(vcs,
					(ConfiguracionCampoTextoNormal) confTexto);
			break;
		case CODIGO_POSTAL:
			if (!ValidacionesTipo.getInstance().esCP(vcs.getValor(), "")) {
				validacionCorrecta = false;
			}
			break;
		case EMAIL:
			validacionCorrecta = validacionConfiguracionCampoTextoEmail(vcs, (ConfiguracionCampoTextoEmail) confTexto);
			break;
		case EXPRESION_REGULAR:
			validacionCorrecta = validacionConfiguracionCampoTextoExpReg(vcs,
					(ConfiguracionCampoTextoExpReg) confTexto);
			break;
		case TELEFONO:
			validacionCorrecta = validacionConfiguracionCampoTextoTelefono(vcs,
					(ConfiguracionCampoTextoTelefono) confTexto);
			break;
		case FECHA:
			if (!ValidacionesTipo.getInstance().esFecha(vcs.getValor(), Constantes.FORMATO_FECHA_FRONTAL)) {
				validacionCorrecta = false;
			}
			break;
		case HORA:
			if (!ValidacionesTipo.getInstance().esHora(vcs.getValor())) {
				validacionCorrecta = false;
			}
			break;
		case IDENTIFICADOR:
			validacionCorrecta = validacionConfiguracionCampoTextoIdentificador(vcs,
					(ConfiguracionCampoTextoId) confTexto);
			break;
		case NUMERO:
			validacionCorrecta = validacionConfiguracionCampoTextoNumero(vcs,
					(ConfiguracionCampoTextoNumero) confTexto);
			break;
		case PASSWORD:
			validacionCorrecta = validacionConfiguracionCampoTextoPassword(vcs,
					(ConfiguracionCampoTextoPassword) confTexto);
			break;
		case IBAN:
			validacionCorrecta = ValidacionesTipo.getInstance().esNumeroCuentaIbanValido(vcs.getValor());
			break;
		default:
			// Tipo no controlado
			validacionCorrecta = false;
		}

		return validacionCorrecta;
	}

	/**
	 * Validacion campo texto numero.
	 *
	 * @param vcs
	 *                       Valor campo
	 * @param confNumero
	 *                       Configuracion campo
	 * @return boolean indicando si la validacion es correcta
	 */
	private boolean validacionConfiguracionCampoTextoNumero(final ValorCampoSimple vcs,
			final ConfiguracionCampoTextoNumero confNumero) {

		final OpcionesCampoTextoNumero op = confNumero.getOpciones();
		final String valor = vcs.getValor();

		// Validamos formato numero, número dígitos de parte entera / decimal y
		// rango
		final boolean validacion = validarFormatoNumero(valor, op) && validarPrecisionNumero(valor, op)
				&& validarRangoNumero(valor, op);

		return validacion;
	}

	/**
	 * Valida si el numero tiene que estar entre un rango.
	 *
	 * @param valorCampox
	 *                          Valor campo
	 * @param opcionesCampo
	 *                          Opciones campo
	 * @return True si pasa la validacion
	 */
	protected boolean validarRangoNumero(final String valorCampox, final OpcionesCampoTextoNumero opcionesCampo) {

		// Normaliza numero en formato java
		final String valorCampo = normalizarNumeroJava(valorCampox, opcionesCampo);

		boolean validacion = true;
		if (opcionesCampo.getRangoMin() != null) {
			final BigDecimal bd = new BigDecimal(valorCampo);
			if (!ValidacionesTipo.getInstance().validaRango(bd.intValue(), opcionesCampo.getRangoMin(),
					opcionesCampo.getRangoMax())) {
				validacion = false;
			}
		}
		return validacion;
	}

	/**
	 * Valida numero de enteros y numero de decimales.
	 *
	 * @param valorCampo
	 *                          Valor campo
	 * @param opcionesCampo
	 *                          Opciones campo
	 * @return True si pasa la validacion
	 */
	protected boolean validarPrecisionNumero(final String valorCampo, final OpcionesCampoTextoNumero opcionesCampo) {
		boolean validacion = true;
		// Obtenemos numero normalizado
		String numeroNormalizado = normalizarNumeroJava(valorCampo, opcionesCampo);
		// Quitamos el signo
		if (numeroNormalizado.startsWith("-")) {
			numeroNormalizado = numeroNormalizado.substring(ConstantesNumero.N1);
		}
		// Verificamos numero de enteros y decimales
		final int indiceDecimales = numeroNormalizado.indexOf(".");
		if (indiceDecimales >= 0) {
			final int numeroDigitosEnteros = indiceDecimales;
			final int numeroDigitosDecimales = numeroNormalizado.length()
					- (numeroDigitosEnteros + ConstantesNumero.N1);

			if (opcionesCampo.getEnteros() < numeroDigitosEnteros) {
				validacion = false;
			}
			if (opcionesCampo.getDecimales() < numeroDigitosDecimales) {
				validacion = false;
			}
		} else {
			if (opcionesCampo.getDecimales() != 0) {
				validacion = false;
			}
			if (opcionesCampo.getEnteros() < numeroNormalizado.length()) {
				validacion = false;
			}
		}
		return validacion;
	}

	/**
	 * Valida formato numerico.
	 *
	 * @param valorCampo
	 *                          Valor del campo numero
	 * @param opcionesCampo
	 *                          Opciones del campo numero
	 * @return true si cumple el formato
	 */
	private boolean validarFormatoNumero(final String valorCampo, final OpcionesCampoTextoNumero opcionesCampo) {

		// Normaliza numero en formato java
		final String valor = normalizarNumeroJava(valorCampo, opcionesCampo);

		// Comprobamos que el formato es correcto en función
		// de los separadores. Convertimos el valor normalizado (valorLimpio)
		// en valor formateado y luego comparamos éste con el valor original
		final String valorLimpio = valor;
		final String valorFormateado = formateaValorNumerico(valorLimpio, opcionesCampo.getSeparador(),
				opcionesCampo.getDecimales());

		boolean validacion = valorFormateado.equals(valorCampo);

		// Valida si admite negativo
		if (!validacion && opcionesCampo.getNegativo() == TypeSiNo.NO && valor.startsWith("-")) {
			validacion = false;
		}
		return validacion;
	}

	/**
	 * Normaliza numero en formato Java (. para decimales)
	 *
	 * @param valorCampo
	 *                          Valor campo
	 * @param opcionesCampo
	 *                          Opciones campo
	 * @return valor normalizado
	 */
	protected String normalizarNumeroJava(final String valorCampo, final OpcionesCampoTextoNumero opcionesCampo) {
		// Normalizamos separadores miles/decimales en formato java
		String valor;
		if (opcionesCampo.getSeparador() == null) {
			valor = valorCampo;
		} else {
			switch (opcionesCampo.getSeparador()) {
			case COMA_PUNTO:
				valor = StringUtils.replace(valorCampo, ",", "");
				break;
			case PUNTO_COMA:
				valor = StringUtils.replace(valorCampo, ".", "");
				valor = StringUtils.replace(valor, ",", ".");
				break;
			default:
				throw new TipoNoControladoException(
						"Tipus de separador numèric no controlat: " + opcionesCampo.getSeparador());
			}
		}
		return valor;
	}

	/**
	 * Formatea valor en funcion del separador.
	 *
	 * @param valorNumerico
	 *                            Valor numerico en notacion java (decimales
	 *                            separados por .)
	 * @param tipoSeparador
	 *                            Tipo separador
	 * @param numeroDecimales
	 *                            Número decimales
	 * @return Valor formateado
	 */

	private String formateaValorNumerico(final String valorNumerico, final TypeSeparador tipoSeparador,
			final int numeroDecimales) {
		String valorFormateado = null;
		double valorNumericoDouble = 0;
		try {
			valorNumericoDouble = Double.parseDouble(valorNumerico);
			if (tipoSeparador == null) {
				valorFormateado = ValidacionesTipo.getInstance().formateaDoubleACadena(valorNumericoDouble, "", ".", 0);
			} else {
				switch (tipoSeparador) {
				case COMA_PUNTO:
					valorFormateado = ValidacionesTipo.getInstance().formateaDoubleACadena(valorNumericoDouble, ",",
							".", numeroDecimales);
					break;
				case PUNTO_COMA:
					valorFormateado = ValidacionesTipo.getInstance().formateaDoubleACadena(valorNumericoDouble, ".",
							",", numeroDecimales);
					break;
				default:
					throw new TipoNoControladoException("Tipus de separador numèrico no controlat: " + tipoSeparador);
				}
			}
		} catch (NumberFormatException | ValidacionTipoException ex) {
			// Retornamos esta cadena xa que falle la validacion
			valorFormateado = "VALOR-INCORRECTO";
		}
		return valorFormateado;
	}

	/**
	 * Validacion campo texto identificador.
	 *
	 * @param vcs
	 *                 Valor campo
	 * @param conf
	 *                 Configuracion campo
	 * @return boolean indicando si la validacion es correcta
	 */
	private boolean validacionConfiguracionCampoTextoIdentificador(final ValorCampoSimple vcs,
			final ConfiguracionCampoTextoId conf) {
		final OpcionesCampoTextoId opciones = conf.getOpciones();
		boolean valido = false;

		// Pasamos el valor del identificador a mayúsculas
		if (!StringUtils.isEmpty(vcs.getValor())) {
			vcs.setValor(vcs.getValor().toUpperCase());
		}
		if (opciones.getDni() == TypeSiNo.SI) {
			valido = NifUtils.esDni(vcs.getValor());
		}
		if (!valido && opciones.getNie() == TypeSiNo.SI) {
			valido = NifUtils.esNie(vcs.getValor());
		}
		if (!valido && opciones.getNifOtros() == TypeSiNo.SI) {
			valido = NifUtils.esNifOtros(vcs.getValor());
		}
		if (!valido && opciones.getNifPJ() == TypeSiNo.SI) {
			valido = NifUtils.esNifPersonaJuridica(vcs.getValor());
		}
		if (!valido && opciones.getNss() == TypeSiNo.SI) {
			valido = NifUtils.esNSS(vcs.getValor());
		}
		return valido;
	}

	/**
	 * Validacion campo texto expresion regular.
	 *
	 * @param vcs
	 *                 Valor campo
	 * @param conf
	 *                 Configuracion campo
	 * @return boolean indicando si la validacion es correcta
	 */
	private boolean validacionConfiguracionCampoTextoTelefono(final ValorCampoSimple vcs,
			final ConfiguracionCampoTextoTelefono conf) {

		final boolean esFijo = ValidacionesTipo.getInstance().esTelefonoFijo(vcs.getValor());
		final boolean esMovil = ValidacionesTipo.getInstance().esTelefonoMovil(vcs.getValor());

		final boolean result = (conf.getOpciones().getFijo() == TypeSiNo.SI && esFijo)
				|| (conf.getOpciones().getMovil() == TypeSiNo.SI && esMovil);

		return result;

	}

	/**
	 * Validacion campo texto expresion regular.
	 *
	 * @param vcs
	 *                 Valor campo
	 * @param conf
	 *                 Configuracion campo
	 * @return boolean indicando si la validacion es correcta
	 */
	private boolean validacionConfiguracionCampoTextoExpReg(final ValorCampoSimple vcs,
			final ConfiguracionCampoTextoExpReg conf) {
		boolean validacionCorrecta = true;
		final OpcionesCampoTextoExpReg opcionesCampoExp = conf.getOpciones();
		if (!ValidacionesCadena.getInstance().validaExpresionRegular(vcs.getValor(), opcionesCampoExp.getRegexp())) {
			validacionCorrecta = false;
		}
		return validacionCorrecta;
	}

	/**
	 * Validacion campo texto normal.
	 *
	 * @param vcs
	 *                            Valor campo
	 * @param confTextoNormal
	 *                            Configuracion campo
	 * @return boolean indicando si la validacion es correcta
	 */
	private boolean validacionConfiguracionCampoTextoNormal(final ValorCampoSimple vcs,
			final ConfiguracionCampoTextoNormal confTextoNormal) {
		boolean validacionCorrecta = true;
		final OpcionesCampoTextoNormal opcionesCampoTextoNormal = confTextoNormal.getOpciones();
		// Tamaño maximo
		if (opcionesCampoTextoNormal.getTamanyo() > 0
				&& vcs.getValor().length() > opcionesCampoTextoNormal.getTamanyo()) {
			validacionCorrecta = false;
		}

		return validacionCorrecta;
	}

	/**
	 * Validacion campo texto email.
	 *
	 * @param vcs
	 *                           Valor campo
	 * @param confTextoEmail
	 *                           Configuracion campo
	 * @return boolean indicando si la validacion es correcta
	 */
	private boolean validacionConfiguracionCampoTextoEmail(final ValorCampoSimple vcs,
			final ConfiguracionCampoTextoEmail confTextoEmail) {
		boolean validacionCorrecta = true;
		final OpcionesCampoTextoEmail opcionesCampoTextoNormal = confTextoEmail.getOpciones();
		// Tamaño maximo
		if (opcionesCampoTextoNormal.getTamanyo() > 0
				&& vcs.getValor().length() > opcionesCampoTextoNormal.getTamanyo()) {
			validacionCorrecta = false;
		}
		// Formato email
		if (validacionCorrecta && !ValidacionesTipo.getInstance().esEmail(vcs.getValor())) {
			validacionCorrecta = false;
		}
		return validacionCorrecta;
	}

	/**
	 * Validacion campo texto password.
	 *
	 * @param vcs
	 *                              Valor campo
	 * @param confTextoPassword
	 *                              Configuracion campo password
	 * @return boolean indicando si la validacion es correcta
	 */
	private boolean validacionConfiguracionCampoTextoPassword(final ValorCampoSimple vcs,
			final ConfiguracionCampoTextoPassword confTextoPassword) {
		boolean validacionCorrecta = true;
		final OpcionesCampoTextoPassword opcionesCampoTextoPassword = confTextoPassword.getOpciones();
		// Tamaño maximo
		if (opcionesCampoTextoPassword.getTamanyo() > 0
				&& vcs.getValor().length() > opcionesCampoTextoPassword.getTamanyo()) {
			validacionCorrecta = false;
		}

		return validacionCorrecta;
	}

	/**
	 * Realiza validacion campo verificacion.
	 *
	 * @param configuracion
	 *                          Configuracion
	 * @param vc
	 *                          Valor campo
	 * @return boolean
	 */
	private boolean validacionConfiguracionCampoVerificacion(final ConfiguracionCampo configuracion,
			final ValorCampo vc) {
		boolean validacion = true;
		final ConfiguracionCampoVerificacion confVerificacion = (ConfiguracionCampoVerificacion) configuracion;
		final ValoresCampoVerificacion valores = confVerificacion.getValores();

		final ValorCampoSimple valorCampoVerificacion = (ValorCampoSimple) vc;

		if (!valorCampoVerificacion.getValor().equals(valores.getChecked())
				&& !valorCampoVerificacion.getValor().equals(valores.getNoChecked())) {
			validacion = false;
		}
		return validacion;
	}

}
