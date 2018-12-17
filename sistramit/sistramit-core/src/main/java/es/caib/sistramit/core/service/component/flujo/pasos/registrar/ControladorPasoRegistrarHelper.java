package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.DocumentosRegistroPorTipo;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResPersona;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResRegistro;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoAnexo;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
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
	 *            Id paso
	 * @param pDefinicionTramite
	 *            Definicion tramite
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param scriptFlujo
	 *            motor script flujo
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
			if (resultadoScriptParametros.isError()) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_PARAMETROS_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), pidPaso, resultadoScriptParametros.getMensajeError());
			}
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
	 *            Id paso
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param scriptFlujo
	 *            Motor script
	 * @return Presentador
	 */
	public DatosUsuario ejecutarScriptPresentador(final String idPaso, final DefinicionTramiteSTG pDefinicionTramite,
			final VariablesFlujo pVariablesFlujo, final ScriptExec scriptFlujo) {

		DatosUsuario presentador = null;

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
			if (resultadoScriptParametros.isError()) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_PRESENTADOR_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), idPaso, resultadoScriptParametros.getMensajeError());
			}
			final ResPersona resPresentador = (ResPersona) resultadoScriptParametros.getResultado();
			// Validamos que se haya establecido presentador
			if (StringUtils.isBlank(resPresentador.getNif()) || StringUtils.isBlank(resPresentador.getNombre())) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_PRESENTADOR_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), idPaso,
						"No se ha especificado presentador en el script");
			}
			// Devolvemos presentador establecido en el script
			presentador = new DatosUsuario(resPresentador.getNif(), resPresentador.getNombre(),
					resPresentador.getApellido1(), resPresentador.getApellido2());
		} else {
			// Si no hay script, se pone como presentador el propio usuario
			// siempre y cuando el tipo de autenticación sea por certificado,
			// en caso contrario lanzamos excepción
			if (pVariablesFlujo.getNivelAutenticacion() == TypeAutenticacion.ANONIMO) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_PRESENTADOR_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), idPaso,
						"No se ha especificado presentador en el script (obligatorio si iniciador es anónimo)");
			}
			// Devolvemos presentador como iniciador
			presentador = pVariablesFlujo.getUsuario();
		}

		return presentador;
	}

	/**
	 * Cálcula representado del trámite teniendo en cuenta el script de
	 * representado.
	 *
	 * @param idPaso
	 *            Id paso
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param scriptFlujo
	 *            Motor script
	 * @return Representado (o nulo si no existe script)
	 */
	public DatosUsuario ejecutarScriptRepresentado(final String idPaso, final DefinicionTramiteSTG pDefinicionTramite,
			final VariablesFlujo pVariablesFlujo, final ScriptExec scriptFlujo) {

		DatosUsuario representado = null;

		// Obtenemos definicion paso
		final RPasoTramitacionRegistrar defPaso = (RPasoTramitacionRegistrar) UtilsSTG.devuelveDefinicionPaso(idPaso,
				pDefinicionTramite);

		final RScript scriptRepresentante = defPaso.getScriptRepresentante();

		// Ejecutamos script representado (si existe script y admite
		// representación)
		if (defPaso.isAdmiteRepresentacion() && UtilsSTG.existeScript(scriptRepresentante)) {
			final Map<String, String> codigosErrorParametros = UtilsSTG
					.convertLiteralesToMap(scriptRepresentante.getLiterales());
			final RespuestaScript resultadoScriptParametros = scriptFlujo.executeScriptFlujo(
					TypeScriptFlujo.SCRIPT_REPRESENTADO_REGISTRO, idPaso, scriptRepresentante.getScript(),
					pVariablesFlujo, null, pVariablesFlujo.getDocumentos(), codigosErrorParametros, pDefinicionTramite);
			if (resultadoScriptParametros.isError()) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_REPRESENTADO_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), idPaso, resultadoScriptParametros.getMensajeError());
			}

			final ResPersona resRepresentado = (ResPersona) resultadoScriptParametros.getResultado();
			// Validamos que se haya establecido representado
			if (StringUtils.isBlank(resRepresentado.getNif()) || StringUtils.isBlank(resRepresentado.getNombre())) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_REPRESENTADO_REGISTRO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), idPaso,
						"No se ha especificado representado en el script");
			}
			// Devolvemos representado establecido en el script
			representado = new DatosUsuario(resRepresentado.getNif(), resRepresentado.getNombre(),
					resRepresentado.getApellido1(), resRepresentado.getApellido2());

		} else {
			// Si no hay script no hay representado
			representado = null;
		}

		// Si no se ha establecido representado y se admite representacion
		// generamos error
		if (defPaso.isAdmiteRepresentacion() && (representado == null || StringUtils.isBlank(representado.getNif())
				|| StringUtils.isBlank(representado.getNombre()))) {
			throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_REPRESENTADO_REGISTRO.name(),
					pVariablesFlujo.getIdSesionTramitacion(), idPaso,
					"El trámite admite representacion y no se ha especificado representado en el script");
		}

		return representado;
	}

	/**
	 * Ejecuta script permitir registrar.
	 *
	 * @param pIdPaso
	 *            Id paso
	 * @param pDefinicionTramite
	 *            Definicion tramite
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param detalleRegistrar
	 *            Detalle paso registrar
	 * @param scriptFlujo
	 *            Motor script
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
	 * Busca los documentos para registrar generados en el flujo de tramitación.
	 *
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @return Lista de documentos que se registrarán
	 */
	public List<DocumentosRegistroPorTipo> buscarDocumentosParaRegistrar(final VariablesFlujo pVariablesFlujo) {

		final List<DocumentoRegistro> listaFormularios = new ArrayList<>();
		final List<DocumentoRegistro> listaAnexos = new ArrayList<>();
		final List<DocumentoRegistro> listaPagos = new ArrayList<>();

		// TODO FALTA INFO FIRMAS

		for (final DatosDocumento datosDocumento : pVariablesFlujo.getDocumentos()) {
			final DocumentoRegistro dr = DocumentoRegistro.createNewDocumentoRegistro();
			dr.setDescargable(TypeSiNo.SI);
			dr.setTitulo(datosDocumento.getTitulo());

			switch (datosDocumento.getTipo()) {
			case FORMULARIO:
				// Si es un formulario de tipo captura no se registra
				final DatosDocumentoFormulario datosDocumentoFormulario = (DatosDocumentoFormulario) datosDocumento;
				if (!datosDocumentoFormulario.isFormularioCaptura()) {
					dr.setId(datosDocumento.getId());
					dr.setInstancia(ConstantesNumero.N1);
					// Si el formulario no tiene pdf de visualizacion no se
					// podra descargar
					if (datosDocumentoFormulario.getPdf() == null) {
						dr.setDescargable(TypeSiNo.NO);
					}
					listaFormularios.add(dr);
				}
				break;
			case ANEXO:
				dr.setId(datosDocumento.getId());
				dr.setInstancia(((DatosDocumentoAnexo) datosDocumento).getInstancia());
				listaAnexos.add(dr);
				break;
			case PAGO:
				dr.setId(datosDocumento.getId());
				dr.setInstancia(ConstantesNumero.N1);
				listaPagos.add(dr);
				break;
			default:
			}
		}

		final List<DocumentosRegistroPorTipo> docsRegPorTipo = new ArrayList<DocumentosRegistroPorTipo>();
		if (!listaFormularios.isEmpty()) {
			final DocumentosRegistroPorTipo drpt = DocumentosRegistroPorTipo.createNewDocumentosRegistroPorTipo();
			drpt.setTipo(TypeDocumento.FORMULARIO);
			drpt.getListado().addAll(listaFormularios);
			docsRegPorTipo.add(drpt);
		}
		if (!listaAnexos.isEmpty()) {
			final DocumentosRegistroPorTipo drpt = DocumentosRegistroPorTipo.createNewDocumentosRegistroPorTipo();
			drpt.setTipo(TypeDocumento.ANEXO);
			drpt.getListado().addAll(listaAnexos);
			docsRegPorTipo.add(drpt);
		}
		if (!listaPagos.isEmpty()) {
			final DocumentosRegistroPorTipo drpt = DocumentosRegistroPorTipo.createNewDocumentosRegistroPorTipo();
			drpt.setTipo(TypeDocumento.PAGO);
			drpt.getListado().addAll(listaPagos);
			docsRegPorTipo.add(drpt);
		}
		return docsRegPorTipo;
	}

}
