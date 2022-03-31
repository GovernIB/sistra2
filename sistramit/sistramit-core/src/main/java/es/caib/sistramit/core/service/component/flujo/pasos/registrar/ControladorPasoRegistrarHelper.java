package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.AvisoUsuario;
import es.caib.sistramit.core.api.model.flujo.DatosContacto;
import es.caib.sistramit.core.api.model.flujo.DatosInteresado;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoRepresentante;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResAviso;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPersona;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResRegistro;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResRepresentacion;
import es.caib.sistramit.core.service.model.flujo.DatosPresentacion;
import es.caib.sistramit.core.service.model.flujo.DatosRepresentacion;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Helper controlador paso registrar.
 *
 * @author Indra
 *
 */
public final class ControladorPasoRegistrarHelper {

	/**
	 * Singleton.
	 */
	private static ControladorPasoRegistrarHelper instance = new ControladorPasoRegistrarHelper();

	/**
	 * Instancia un nuevo controlador paso registrar helper de
	 * ControladorPasoRegistrarHelper.
	 */
	private ControladorPasoRegistrarHelper() {
	}

	/**
	 * Devuelve singleton.
	 *
	 * @return singleton
	 */
	public static ControladorPasoRegistrarHelper getInstance() {
		return instance;
	}

	/**
	 * Ejecuta script de parametros registro.
	 *
	 * @param pidPaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param scriptFlujo
	 *                               motor script flujo
	 * @return Devuelve resultado script. Si no existe script devuelve resultado
	 *         script con los datos vacíos.
	 */
	public ResRegistro ejecutarScriptParametrosRegistro(final String pidPaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final ScriptExec scriptFlujo) {
		ResRegistro resRegistro = null;
		// Obtenemos definicion paso
		final RPasoTramitacionRegistrar defPaso = (RPasoTramitacionRegistrar) UtilsSTG.devuelveDefinicionPaso(pidPaso,
				pDefinicionTramite);
		final RScript scriptDestino = defPaso.getScriptDestino();
		if (UtilsSTG.existeScript(scriptDestino)) {
			// Ejecutamos script
			final Map<String, String> codigosErrorParametros = UtilsSTG
					.convertLiteralesToMap(scriptDestino.getLiterales());
			final RespuestaScript resultadoScriptParametros = scriptFlujo.executeScriptFlujo(
					TypeScriptFlujo.SCRIPT_PARAMETROS_REGISTRO, pidPaso, scriptDestino.getScript(), pVariablesFlujo,
					null, pVariablesFlujo.getDocumentos(), codigosErrorParametros, pDefinicionTramite);
			resRegistro = (ResRegistro) resultadoScriptParametros.getResultado();
		} else {
			// No existe script, creamos vacío
			resRegistro = new ResRegistro();
		}
		return resRegistro;
	}

	/**
	 * Cálcula presentador del trámite teniendo en cuenta el script de presentador.
	 *
	 * @param idPaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param scriptFlujo
	 *                               Motor script
	 * @return Presentador
	 */
	public DatosInteresado ejecutarScriptPresentador(final String idPaso, final DefinicionTramiteSTG pDefinicionTramite,
			final VariablesFlujo pVariablesFlujo, final ScriptExec scriptFlujo) {

		DatosInteresado presentador = null;

		// Obtenemos definicion paso
		final RPasoTramitacionRegistrar defPaso = (RPasoTramitacionRegistrar) UtilsSTG.devuelveDefinicionPaso(idPaso,
				pDefinicionTramite);

		// Ejecutamos script presentador (si existe script)
		final RScript scriptPresentador = defPaso.getScriptPresentador();
		if (UtilsSTG.existeScript(scriptPresentador)) {
			final Map<String, String> codigosErrorParametros = UtilsSTG
					.convertLiteralesToMap(scriptPresentador.getLiterales());
			final RespuestaScript resultadoScriptParametros = scriptFlujo.executeScriptFlujo(
					TypeScriptFlujo.SCRIPT_PRESENTADOR_REGISTRO, idPaso, scriptPresentador.getScript(), pVariablesFlujo,
					null, pVariablesFlujo.getDocumentos(), codigosErrorParametros, pDefinicionTramite);
			final ResPersona resPresentador = (ResPersona) resultadoScriptParametros.getResultado();
			// Validamos que se haya establecido presentador
			if (resPresentador.isNulo() || StringUtils.isBlank(resPresentador.getNif())
					|| StringUtils.isBlank(resPresentador.getNombre())) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_PRESENTADOR_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), idPaso,
						"No s'ha especificat presentador al script");
			}
			// Devolvemos presentador establecido en el script
			presentador = new DatosInteresado(resPresentador.getNif(), resPresentador.getNombre(),
					resPresentador.getApellido1(), resPresentador.getApellido2(), generarDatosContacto(resPresentador));
		} else {
			// Si no hay script, se pone como presentador el propio usuario
			// siempre y cuando el tipo de autenticación sea por certificado,
			// en caso contrario lanzamos excepción
			if (pVariablesFlujo.getNivelAutenticacion() == TypeAutenticacion.ANONIMO) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_PRESENTADOR_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), idPaso,
						"No s'ha especificat presentador al script (obligatori si iniciador és anònim)");
			}
			// Devolvemos presentador como iniciador
			final DatosUsuario iniciador = pVariablesFlujo.getUsuario();
			presentador = new DatosInteresado(iniciador.getNif(), iniciador.getNombre(), iniciador.getApellido1(),
					iniciador.getApellido2());
		}

		return presentador;
	}

	/**
	 * Cálcula datos representación del trámite teniendo en cuenta el script de
	 * representación.
	 *
	 * @param idPaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param datosPresentacion
	 *                               Datos presentador
	 * @param scriptFlujo
	 *                               Motor script
	 * @return Datos representación (o nulo si no existe script)
	 */
	public DatosRepresentacion calcularDatosRepresentacion(final String idPaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final DatosPresentacion datosPresentacion, final ScriptExec scriptFlujo) {

		DatosRepresentacion resultado = null;

		// Obtenemos definicion paso
		final RPasoTramitacionRegistrar defPaso = (RPasoTramitacionRegistrar) UtilsSTG.devuelveDefinicionPaso(idPaso,
				pDefinicionTramite);

		// Verificamos si se ha autenticado un representante
		final boolean autenticacionRepresentante = pVariablesFlujo.getUsuarioAutenticado() == null
				|| pVariablesFlujo.getUsuarioAutenticado().getRepresentante() != null;

		final RScript scriptRepresentante = defPaso.getScriptRepresentacion();

		// Si admite representación, evaluamos representación
		if (defPaso.isAdmiteRepresentacion()) {

			// Si existe script, ejecutamos script para establecer representación
			if (UtilsSTG.existeScript(scriptRepresentante)) {
				resultado = ejecutarScriptRepresentacion(pDefinicionTramite, idPaso, pVariablesFlujo, datosPresentacion,
						scriptFlujo);
			} else {
				// Si no existe script, si esta autenticado con cert representación establecemos
				// datos representación por defecto
				if (autenticacionRepresentante && datosPresentacion.getPresentador().getNif()
						.equals(pVariablesFlujo.getUsuarioAutenticado().getNif())) {
					final UsuarioAutenticadoRepresentante rpte = pVariablesFlujo.getUsuarioAutenticado()
							.getRepresentante();
					resultado = new DatosRepresentacion();
					resultado.setRepresentante(new DatosInteresado(rpte.getNif(), rpte.getNombre(), rpte.getApellido1(),
							rpte.getApellido2()));
					resultado.setRepresentado(datosPresentacion.getPresentador());
				}
			}

		}

		// Si el presentador es una PJ, es obligatorio que se informe la representación
		if (ValidacionesTipo.getInstance().esNifPersonaJuridica(datosPresentacion.getPresentador().getNif())
				&& resultado == null) {
			throw new ErrorConfiguracionException(
					"El nif del presentador és de persona jurídica però el tràmite no està configurat per establir la informació de representació");
		}

		return resultado;
	}

	/**
	 * Ejecuta script permitir registrar.
	 *
	 * @param pIdPaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param detalleRegistrar
	 *                               Detalle paso registrar
	 * @param scriptFlujo
	 *                               Motor script
	 * @return Respuesta script
	 */
	public RespuestaScript ejecutarScriptPermitirRegistrar(final String pIdPaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final ScriptExec scriptFlujo) {

		// Obtenemos definicion paso
		final RPasoTramitacionRegistrar defPaso = (RPasoTramitacionRegistrar) UtilsSTG.devuelveDefinicionPaso(pIdPaso,
				pDefinicionTramite);

		final RScript scriptValidar = defPaso.getScriptValidar();

		final Map<String, String> codigosErrorParametros = UtilsSTG.convertLiteralesToMap(scriptValidar.getLiterales());

		final RespuestaScript rs = scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_PERMITIR_REGISTRO, pIdPaso,
				scriptValidar.getScript(), pVariablesFlujo, null, pVariablesFlujo.getDocumentos(),
				codigosErrorParametros, pDefinicionTramite);
		return rs;
	}

	/**
	 * Ejecuta script aviso fin trámite.
	 *
	 * @param pIdPaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param detalleRegistrar
	 *                               Detalle paso registrar
	 * @param scriptFlujo
	 *                               Motor script
	 * @return Respuesta script
	 */
	public AvisoUsuario ejecutarScriptAvisoFinalizar(final String pIdPaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final ScriptExec scriptFlujo) {

		// Obtenemos definicion paso
		final RPasoTramitacionRegistrar defPaso = (RPasoTramitacionRegistrar) UtilsSTG.devuelveDefinicionPaso(pIdPaso,
				pDefinicionTramite);

		// Ejecutamos script si hay que avisar
		final AvisoUsuario aviso = new AvisoUsuario();
		if (defPaso.isAvisoAlFinalizar()) {
			final RScript scriptValidar = defPaso.getScriptAlFinalizar();
			final Map<String, String> codigosErrorParametros = UtilsSTG
					.convertLiteralesToMap(scriptValidar.getLiterales());
			final RespuestaScript rs = scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_AVISO, pIdPaso,
					scriptValidar.getScript(), pVariablesFlujo, null, pVariablesFlujo.getDocumentos(),
					codigosErrorParametros, pDefinicionTramite);
			final ResAviso resAviso = (ResAviso) rs.getResultado();
			if (StringUtils.isNotBlank(resAviso.getEmail())) {
				// TODO VER SI GENERAR EXCEPCION O NO GENERAR AVISO
				if (!ValidacionesTipo.getInstance().esEmail(resAviso.getEmail())) {
					throw new ErrorConfiguracionException(
							"No s'ha especificat email vàlid per avís: " + resAviso.getEmail());
				}
				aviso.setAvisar(TypeSiNo.SI);
				aviso.setEmail(resAviso.getEmail());
			}
		}

		return aviso;
	}

	/**
	 * Ejecuta script representación.
	 *
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param idPaso
	 *                               id paso
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param datosPresentacion
	 *                               Datos presentación
	 * @param scriptFlujo
	 *                               Motor script
	 * @return Datos representación
	 */
	private DatosRepresentacion ejecutarScriptRepresentacion(final DefinicionTramiteSTG pDefinicionTramite,
			final String idPaso, final VariablesFlujo pVariablesFlujo, final DatosPresentacion datosPresentacion,
			final ScriptExec scriptFlujo) {

		DatosRepresentacion resultado = null;

		// Obtenemos definicion paso
		final RPasoTramitacionRegistrar defPaso = (RPasoTramitacionRegistrar) UtilsSTG.devuelveDefinicionPaso(idPaso,
				pDefinicionTramite);

		final RScript scriptRepresentante = defPaso.getScriptRepresentacion();

		final Map<String, String> codigosErrorParametros = UtilsSTG
				.convertLiteralesToMap(scriptRepresentante.getLiterales());
		final RespuestaScript resultadoScriptParametros = scriptFlujo.executeScriptFlujo(
				TypeScriptFlujo.SCRIPT_REPRESENTACION_REGISTRO, idPaso, scriptRepresentante.getScript(),
				pVariablesFlujo, null, pVariablesFlujo.getDocumentos(), codigosErrorParametros, pDefinicionTramite);
		final ResRepresentacion resRepresentacion = (ResRepresentacion) resultadoScriptParametros.getResultado();
		final ResPersona resRepresentado = resRepresentacion.getRepresentado();
		final ResPersona resRepresentante = resRepresentacion.getRepresentante();

		// Comprobamos si en el script se indica que existe representación
		if (resRepresentacion.isActivarRepresentacion()) {
			DatosInteresado representado = null;
			DatosInteresado representante = null;
			// Representado
			// - Si admite representación, al menos debe indicarse representado
			if (resRepresentado == null || resRepresentado.isNulo()) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_REPRESENTACION_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), idPaso,
						"El tràmite admet representació i no s'ha especificat representat al script");
			}
			representado = new DatosInteresado(resRepresentado.getNif(), resRepresentado.getNombre(),
					resRepresentado.getApellido1(), resRepresentado.getApellido2(),
					generarDatosContacto(resRepresentado));

			// Representante
			// - Verificamos si se indica representante en script
			if (resRepresentante != null && !resRepresentante.isNulo()) {
				representante = new DatosInteresado(resRepresentante.getNif(), resRepresentante.getNombre(),
						resRepresentante.getApellido1(), resRepresentante.getApellido2(),
						generarDatosContacto(resRepresentante));
			}
			// - Si no se indica representante en script, por defecto se establece el
			// presentador
			if (representante == null) {
				representante = datosPresentacion.getPresentador();
			}

			// Retorna resultado en caso de representación
			resultado = new DatosRepresentacion();
			resultado.setRepresentante(representante);
			resultado.setRepresentado(representado);
		}

		return resultado;
	}

	/**
	 * Genera datos contacto a partir ResPersona.
	 *
	 * @param resPersona
	 *                       ResPersona
	 */
	private DatosContacto generarDatosContacto(final ResPersona resPersona) {
		DatosContacto contacto = null;
		if (resPersona != null && !resPersona.isContactoNulo()) {
			contacto = new DatosContacto();
			contacto.setPais(resPersona.getPais());
			contacto.setProvincia(resPersona.getProvincia());
			contacto.setMunicipio(resPersona.getMunicipio());
			contacto.setDireccion(resPersona.getDireccion());
			contacto.setCodigoPostal(resPersona.getCodigoPostal());
			contacto.setEmail(resPersona.getEmail());
			contacto.setTelefono(resPersona.getTelefono());
		}
		return contacto;
	}

}
