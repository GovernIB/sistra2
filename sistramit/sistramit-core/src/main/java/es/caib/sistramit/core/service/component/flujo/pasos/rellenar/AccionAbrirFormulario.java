package es.caib.sistramit.core.service.component.flujo.pasos.rellenar;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.flujo.AbrirFormulario;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRellenar;
import es.caib.sistramit.core.api.model.flujo.Formulario;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeFormulario;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.formulario.externo.ControladorGestorFormulariosExterno;
import es.caib.sistramit.core.service.component.formulario.interno.ControladorGestorFormulariosInterno;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResParametrosFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRellenar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.formulario.ParametrosAperturaFormulario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que permite abrir un formulario en el paso Rellenar.
 *
 * @author Indra
 *
 */
@Component("accionRfAbrirFormulario")
public final class AccionAbrirFormulario implements AccionPaso {

	/** Controlador para acceso a formularios internos. */
	@Autowired
	private ControladorGestorFormulariosInterno controladorGFInterno;

	/** Controlador para acceso a formularios externos. */
	@Autowired
	private ControladorGestorFormulariosExterno controladorGFExterno;

	/** Script flujo. */
	@Autowired
	private ScriptExec scriptFlj;

	/** Parametro id formulario. */
	private static final String PARAM_ID_FORMULARIO = "idFormulario";

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Recogemos parametros
		final String idFor = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, PARAM_ID_FORMULARIO, true);

		// Obtenemos datos internos paso rellenar
		final DatosInternosPasoRellenar dipa = (DatosInternosPasoRellenar) pDatosPaso.internalData();

		// Si el formulario es dependiente, no debería dejar abrirlo
		final Formulario formularioDetalle = ((DetallePasoRellenar) dipa.getDetallePaso()).getFormulario(idFor);
		if (formularioDetalle == null) {
			throw new AccionPasoNoPermitidaException(
					"No existeix formulari " + idFor + " a la passa " + dipa.getIdPaso());
		}
		if (formularioDetalle.getObligatorio() == TypeObligatoriedad.DEPENDIENTE) {
			throw new AccionPasoNoPermitidaException("Un formulari dependent no pot ser obert");
		}

		// Obtenemos el paso rellenar
		final RPasoTramitacionRellenar pasoDef = (RPasoTramitacionRellenar) UtilsSTG
				.devuelveDefinicionPaso(dipa.getIdPaso(), pDefinicionTramite);

		// Obtenemos la definicion del formulario
		final RFormularioTramite formularioDef = UtilsSTG.devuelveDefinicionFormulario(pasoDef, idFor);

		// Comprobamos si el formulario tiene parametros de apertura
		final Map<String, String> parametros = calcularParametrosApertura(dipa, pDpp, pDefinicionTramite, formularioDef,
				pVariablesFlujo);

		// Generamos sesion en el controlador de formularios (interno o externo)
		final AbrirFormulario af = generarSesionGestorFormulario(dipa, pDefinicionTramite, formularioDef,
				pVariablesFlujo, parametros);

		// Devolvemos respuesta
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		rp.addParametroRetorno("referencia", af);
		rep.setRespuestaAccionPaso(rp);
		return rep;

	}

	/**
	 * Calcula parametros de apertura del formulario ejecutando script de
	 * parametros.
	 *
	 * @param pDipa
	 *                               Datos internor paso
	 * @param pDpp
	 *                               Datos persistencia paso
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pFormularioDef
	 *                               Definicion formulario
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 *
	 * @return Parametros de apertura del formulario
	 */
	private Map<String, String> calcularParametrosApertura(final DatosInternosPasoRellenar pDipa,
			final DatosPersistenciaPaso pDpp, final DefinicionTramiteSTG pDefinicionTramite,
			final RFormularioTramite pFormularioDef, final VariablesFlujo pVariablesFlujo) {
		Map<String, String> paramApertura = null;
		if (UtilsSTG.existeScript(pFormularioDef.getScriptParametrosApertura())) {
			// Obtenemos formularios completados anteriores al formulario actual
			// en este paso
			final List<DatosDocumento> documentosPaso = UtilsPasoRellenar.obtenerDocumentosCompletados(pDipa, pDpp,
					pDefinicionTramite, pFormularioDef.getIdentificador());

			// Ejecutamos script de parametros de apertura
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(pFormularioDef.getScriptParametrosApertura().getLiterales());
			final RespuestaScript rs = scriptFlj.executeScriptFlujo(TypeScriptFlujo.SCRIPT_PARAMETROS_FORMULARIO,
					pFormularioDef.getIdentificador(), pFormularioDef.getScriptParametrosApertura().getScript(),
					pVariablesFlujo, null, documentosPaso, codigosError, pDefinicionTramite);
			// Establecemos parametros
			final ResParametrosFormulario rsp = (ResParametrosFormulario) rs.getResultado();
			paramApertura = rsp.getParametros();
		}
		return paramApertura;
	}

	/**
	 * Crea sesion en controlador de formularios (interno/externo).
	 *
	 * @param pDipa
	 *                                  Datos internos paso
	 * @param pDefinicionTramite
	 *                                  Definicion del tramite
	 * @param pDefinicionFormulario
	 *                                  Definicion formulario
	 * @param pVariablesFlujo
	 *                                  Variables flujo
	 * @param pParametrosApertura
	 *                                  Parametros apertura
	 * @return Sesion abierta en el controlador de formularios
	 */
	private AbrirFormulario generarSesionGestorFormulario(final DatosInternosPasoRellenar pDipa,
			final DefinicionTramiteSTG pDefinicionTramite, final RFormularioTramite pDefinicionFormulario,
			final VariablesFlujo pVariablesFlujo, final Map<String, String> pParametrosApertura) {

		final DatosInicioSesionFormulario difi = new DatosInicioSesionFormulario();
		difi.setEntidad(pDefinicionTramite.getDefinicionVersion().getIdEntidad());
		difi.setIdioma(pVariablesFlujo.getIdioma());
		difi.setIdTramite(pDefinicionTramite.getDefinicionVersion().getIdentificador());
		difi.setVersionTramite(pDefinicionTramite.getDefinicionVersion().getVersion());
		difi.setReleaseTramite(pDefinicionTramite.getDefinicionVersion().getRelease());
		difi.setIdFormulario(pDefinicionFormulario.getIdentificador());
		difi.setIdPaso(pDipa.getIdPaso());
		difi.setInterno(pDefinicionFormulario.isInterno());
		difi.setIdSesionTramitacion(pVariablesFlujo.getIdSesionTramitacion());
		difi.setInfoAutenticacion(pVariablesFlujo.getUsuarioAutenticado());
		difi.setXmlDatosActuales(pDipa.getDatosFormulario(pDefinicionFormulario.getIdentificador()));
		difi.setTituloProcedimiento(pVariablesFlujo.getDatosTramiteCP().getProcedimiento().getDescripcion());
		difi.setCodigoSiaProcedimiento(pVariablesFlujo.getDatosTramiteCP().getProcedimiento().getIdProcedimientoSIA());
		difi.setDir3ResponsableProcedimiento(
				pVariablesFlujo.getDatosTramiteCP().getProcedimiento().getOrganoResponsableDir3());
		final ParametrosAperturaFormulario p = new ParametrosAperturaFormulario();
		if (!pDefinicionFormulario.isInterno()) {
			difi.setIdGestorFormulariosExterno(
					pDefinicionFormulario.getFormularioExterno().getIdentificadorGestorFormularios());
			difi.setIdFormularioExterno(pDefinicionFormulario.getFormularioExterno().getIdentificadorFormulario());
		}
		if (pParametrosApertura != null) {
			for (final String codigo : pParametrosApertura.keySet()) {
				p.addParametro(codigo, pParametrosApertura.get(codigo));
			}
		}
		difi.setParametros(p);

		String urlFormulario = null;
		String ticket = null;
		TypeFormulario tipoFormulario;
		if (pDefinicionFormulario.isInterno()) {
			// Formulario interno
			tipoFormulario = TypeFormulario.INTERNO;
			ticket = controladorGFInterno.iniciarSesion(difi);
		} else {
			// Formulario externo
			tipoFormulario = TypeFormulario.EXTERNO;
			urlFormulario = controladorGFExterno.iniciarSesion(difi);
		}

		final AbrirFormulario af = new AbrirFormulario();
		af.setTipo(tipoFormulario);
		af.setUrl(urlFormulario);
		af.setTicket(ticket);
		return af;
	}

}
