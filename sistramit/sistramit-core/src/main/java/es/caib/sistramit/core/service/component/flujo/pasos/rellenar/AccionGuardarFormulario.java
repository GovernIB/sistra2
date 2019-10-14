package es.caib.sistramit.core.service.component.flujo.pasos.rellenar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.ParametrosEntradaIncorrectosException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRellenar;
import es.caib.sistramit.core.api.model.flujo.Formulario;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.formulario.externo.ControladorGestorFormulariosExterno;
import es.caib.sistramit.core.service.component.formulario.interno.ControladorGestorFormulariosInterno;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResModificacionFormularios;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRellenar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.ValoresFormulario;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que permite guardar un formulario en el paso Rellenar. También permite
 * borrar un formulario opcional.
 *
 * @author Indra
 *
 */
@Component("accionRfGuardarFormulario")
public final class AccionGuardarFormulario implements AccionPaso {

	/** DAO acceso BBDD. */
	@Autowired
	private FlujoPasoDao dao;

	/** Controlador para acceso a formularios internos. */
	@Autowired
	private ControladorGestorFormulariosInterno controladorGestorFormulariosInterno;

	/** Controlador para acceso a formularios externos. */
	@Autowired
	private ControladorGestorFormulariosExterno controladorGestorFormulariosExterno;

	/** Script flujo. */
	@Autowired
	private ScriptExec scriptFlujo;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Datos finalizacion formulario (cuando se guarda)
		DatosFinalizacionFormulario dff = null;

		// Recogemos parametros
		final String idFormulario = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "idFormulario", true);
		final String ticket = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "ticket", false);
		TypeSiNo borrarOpcional = (TypeSiNo) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "borrarOpcional",
				false);
		if (borrarOpcional == null) {
			borrarOpcional = TypeSiNo.NO;
		}

		// Obtenemos datos internos paso rellenar
		final DatosInternosPasoRellenar dipa = (DatosInternosPasoRellenar) pDatosPaso.internalData();

		// Validaciones guardar/cancelar formulario
		if (borrarOpcional == TypeSiNo.NO) {
			validarGuardarFormulario(dipa, idFormulario);
			// Verificamos que se ha pasado parametro ticket
			if (StringUtils.isBlank(ticket)) {
				throw new ParametrosEntradaIncorrectosException("Falta especificar parametro ticket");
			}
			// Obtenemos datos finalizacion gestor formulario
			dff = obtenerDatosFinalizacionGestor(dipa, idFormulario, ticket, pDefinicionTramite);
		} else {
			validarCancelarFormulario(dipa, idFormulario);
		}

		// Si se guarda y se ha cancelado indicamos que se ha cancelado
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		if (dff != null && dff.isCancelado()) {
			rp.addParametroRetorno("cancelado", TypeSiNo.SI);
		} else {
			// Si no se ha cancelado guardamos formulario

			// Ejecutamos script post guardar
			final RespuestaScript respuestaScriptPostGuardar = ejecutarScriptPostGuardar(dipa, pDpp, idFormulario, dff,
					pDefinicionTramite, pVariablesFlujo, borrarOpcional);

			// Actualizamos persistencia para despues recalcular detalle (los datos de este
			// formulario y los modificados en el script de postguardar).
			if (borrarOpcional == TypeSiNo.NO) {
				actualizarPersistenciaGuardar(dipa, pDpp, pVariablesFlujo, idFormulario, dff.getXml(), dff.getPdf(),
						respuestaScriptPostGuardar);
			} else {
				actualizarPersistenciaCancelarFormulario(dipa, pDpp, pVariablesFlujo, idFormulario,
						respuestaScriptPostGuardar);
			}

			// Devolvemos si se ha podido guardar el formulario o se ha marcado
			// con estado incorrecto
			rp.addParametroRetorno("cancelado", TypeSiNo.NO);
			rp.addParametroRetorno("correcto", TypeSiNo.SI);

		}

		// Indicamos que se recalculen los datos del paso en funcion de los
		// datos de persistencia
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		rep.setRecalcularDatosInternos(true);
		return rep;

	}

	/**
	 * Obtiene datos de finalizacion del gestor de formularios.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pIdFormulario
	 *                               Id formulario
	 * @param pTicket
	 *                               Ticket de finalizacion del gestor
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @return Datos finalizacion gestor formularios
	 */
	private DatosFinalizacionFormulario obtenerDatosFinalizacionGestor(final DatosInternosPasoRellenar pDipa,
			final String pIdFormulario, final String pTicket, final DefinicionTramiteSTG pDefinicionTramite) {
		// Recogemos datos de finalizacion del formulario consultando al
		// controlador (interno/externo)
		// (En este paso no tiene sentido la accion personalizada, solo para
		// paso captura)

		// Obtenemos definicion del paso
		final RPasoTramitacionRellenar pasoDef = (RPasoTramitacionRellenar) UtilsSTG
				.devuelveDefinicionPaso(pDipa.getIdPaso(), pDefinicionTramite);
		final RFormularioTramite formularioActualDef = UtilsSTG.devuelveDefinicionFormulario(pasoDef, pIdFormulario);
		DatosFinalizacionFormulario dff;

		if (formularioActualDef.isInterno()) {
			dff = controladorGestorFormulariosInterno.obtenerDatosFinalizacionFormulario(pTicket);
		} else {
			dff = controladorGestorFormulariosExterno.obtenerDatosFinalizacionFormulario(pTicket);
		}
		return dff;
	}

	/**
	 * Valida si se puede guardar formulario.
	 *
	 * @param pDipa
	 *                          Datos internos paso
	 * @param pIdFormulario
	 *                          Id formulario
	 *
	 */
	private void validarGuardarFormulario(final DatosInternosPasoRellenar pDipa, final String pIdFormulario) {
		// Si el formulario es dependiente, no debería dejar guardarlo
		final Formulario formularioDetalle = ((DetallePasoRellenar) pDipa.getDetallePaso())
				.getFormulario(pIdFormulario);
		if (formularioDetalle == null) {
			throw new AccionPasoNoPermitidaException("El formulario no existe: " + pIdFormulario);
		}
		if (formularioDetalle.getObligatorio() == TypeObligatoriedad.DEPENDIENTE) {
			throw new AccionPasoNoPermitidaException("Un formulario dependiente no puede ser guardado");
		}
	}

	/**
	 * Ejecuta script post guardar (si existe).
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pDpp
	 *                               Datos persistencia
	 * @param pIdFormulario
	 *                               Definición formulario
	 * @param pDff
	 *                               Datos del formulario que se intentan guardar
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param pBorrarOpcional
	 *                               Indica si se esta borrando formulario opcional
	 * @return el respuesta script
	 */
	private RespuestaScript ejecutarScriptPostGuardar(final DatosInternosPasoRellenar pDipa,
			final DatosPersistenciaPaso pDpp, final String pIdFormulario, final DatosFinalizacionFormulario pDff,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final TypeSiNo pBorrarOpcional) {

		final RPasoTramitacionRellenar pasoDef = (RPasoTramitacionRellenar) UtilsSTG
				.devuelveDefinicionPaso(pDipa.getIdPaso(), pDefinicionTramite);
		final RFormularioTramite formularioDef = UtilsSTG.devuelveDefinicionFormulario(pasoDef, pIdFormulario);

		RespuestaScript rs = null;

		// Si se esta borrando formulario opcional verificamos si se debe
		// ejecutar script
		if (UtilsSTG.existeScript(formularioDef.getScriptPostguardar()) && (pBorrarOpcional == TypeSiNo.NO)) {

			// Obtenemos formularios completados anteriores al formulario
			// actual en este paso
			final List<DatosDocumento> documentosPaso = UtilsPasoRellenar.obtenerDocumentosCompletados(pDipa, pDpp,
					pDefinicionTramite, formularioDef.getIdentificador());

			// Añadimos datos que se estan guardando para el formulario
			// actual
			if (pDff != null) {
				final DatosDocumentoFormulario dFormActual = new DatosDocumentoFormulario();
				dFormActual.setIdPaso(pDpp.getId());
				dFormActual.setId(formularioDef.getIdentificador());
				dFormActual.setTipoENI("TD14");
				dFormActual.setTitulo(formularioDef.getDescripcion());
				dFormActual.setCampos(new ValoresFormulario(pDff.getXml()));
				dFormActual.setFichero(null);
				dFormActual.setPdf(null);
				documentosPaso.add(dFormActual);
			}

			// Ejecutamos script
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(formularioDef.getScriptPostguardar().getLiterales());
			rs = scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO,
					formularioDef.getIdentificador(), formularioDef.getScriptPostguardar().getScript(), pVariablesFlujo,
					null, documentosPaso, codigosError, pDefinicionTramite);

			// Si se ha ejecutado correctamente comprobamos que los cambios
			// a realizar son sobre los demas formularios del paso
			final ResModificacionFormularios resp = (ResModificacionFormularios) rs.getResultado();
			// Verificamos que se pueden modificar los formularios que
			// se han modificado (pasaran a estado incorrecto)
			verificarFormulariosModificados(pDipa, pasoDef, formularioDef, resp, pVariablesFlujo);
			// Verificamos que se pueden modificar los formularios que
			// se han marcado como incorrectos
			verificarFormulariosIncorrectos(pDipa, pasoDef, formularioDef, resp, pVariablesFlujo);

		}

		return rs;
	}

	/**
	 * Verificamos que se pueden modificar los formularios que se han marcado como
	 * incorrectos.
	 *
	 * @param pDipa
	 *                            Datos internos del paso
	 * @param pPasoDef
	 *                            Definicion paso
	 * @param pFormularioDef
	 *                            Definición formulario
	 * @param pRespModifForms
	 *                            Respuesta script modificacion formularios
	 * @param pVariablesFlujo
	 *                            Variables de flujo
	 */
	private void verificarFormulariosIncorrectos(final DatosInternosPasoRellenar pDipa,
			final RPasoTramitacionRellenar pPasoDef, final RFormularioTramite pFormularioDef,
			final ResModificacionFormularios pRespModifForms, final VariablesFlujo pVariablesFlujo) {
		for (final String idFormModif : pRespModifForms.getFormulariosIncorrectos()) {
			if (idFormModif.equals(pFormularioDef.getIdentificador())) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), pFormularioDef.getIdentificador(),
						"Se ha intentado modificar estado del formulario actual");
			}
			if (((DetallePasoRellenar) pDipa.getDetallePaso()).getFormulario(idFormModif) == null) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), pFormularioDef.getIdentificador(),
						"Se ha intentado modificar estado de formulario de otro paso (" + idFormModif + ")");
			}
			if (!esFormularioPosterior(pFormularioDef.getIdentificador(), idFormModif, pPasoDef)) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), pFormularioDef.getIdentificador(),
						"Se ha intentado modificar estado de formulario de un formulario anterior (" + idFormModif
								+ ")");
			}
		}
	}

	/**
	 * Verifica que el formulario a modificar es posterior al formulario que se esta
	 * guardando.
	 *
	 * @param pIdFormGuardar
	 *                           Id formulario a guardar
	 * @param pIdFormModif
	 *                           Id formulario a modificar
	 * @param pPasoDef
	 *                           Definición paso
	 * @return Indica si es posterior (true) o anterior (false).
	 */
	private boolean esFormularioPosterior(final String pIdFormGuardar, final String pIdFormModif,
			final RPasoTramitacionRellenar pPasoDef) {
		boolean posterior = false;
		for (final RFormularioTramite formDef : pPasoDef.getFormularios()) {
			if (formDef.getIdentificador().equals(pIdFormGuardar)) {
				posterior = true;
			} else if (formDef.getIdentificador().equals(pIdFormModif)) {
				break;
			}
		}
		return posterior;
	}

	/**
	 * Verificamos que se pueden modificar los formularios que se han modificado.
	 *
	 * @param pDipa
	 *                            Datos internos del paso
	 * @param pPasoDef
	 *                            Definicion paso
	 * @param pFormularioDef
	 *                            Definición formulario
	 * @param pRespModifForms
	 *                            Respuesta script modificacion formularios
	 * @param pVariablesFlujo
	 *                            Variables de flujo
	 */
	private void verificarFormulariosModificados(final DatosInternosPasoRellenar pDipa,
			final RPasoTramitacionRellenar pPasoDef, final RFormularioTramite pFormularioDef,
			final ResModificacionFormularios pRespModifForms, final VariablesFlujo pVariablesFlujo) {

		// - Validamos que solo se modifiquen otros formularios del
		// propio paso
		for (final String idFormModif : pRespModifForms.getFormulariosModificados()) {

			// No se puede modificar el formulario actual (podría cambiar datos
			// PDF).
			if (idFormModif.equals(pFormularioDef.getIdentificador())) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), pFormularioDef.getIdentificador(),
						"Se ha intentado modificar datos del formulario actual");
			}

			if (((DetallePasoRellenar) pDipa.getDetallePaso()).getFormulario(idFormModif) == null) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), pFormularioDef.getIdentificador(),
						"Se ha intentado modificar el formulario de otro paso (" + idFormModif + ")");
			}
			if (!esFormularioPosterior(pFormularioDef.getIdentificador(), idFormModif, pPasoDef)) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_POSTGUARDAR_FORMULARIO.name(),
						pVariablesFlujo.getIdSesionTramitacion(), pFormularioDef.getIdentificador(),
						"Se ha intentado modificar un formulario anterior (" + idFormModif + ")");
			}
		}
	}

	/**
	 * Actualiza datos de persistencia en el paso de guardar formulario.
	 *
	 * Deberá actualizar los datos del formulario que se guarda y los datos/estado
	 * de los formularios que se modifiquen en el script de postguardar.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pDpp
	 *                            Datos persistencia paso
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 * @param idFormulario
	 *                            Id formulario que se guarda
	 * @param xml
	 *                            Xml de datos del formulario que se guarda
	 * @param pdf
	 *                            Pdf de visualizacion del formulario que se guarda
	 * @param rs
	 *                            Respuesta del script de postguardar formulario
	 */
	private void actualizarPersistenciaGuardar(final DatosInternosPasoRellenar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo, final String idFormulario, final byte[] xml, final byte[] pdf,
			final RespuestaScript rs) {

		// Actualizamos datos formulario actual
		actualizarFormularioActual(pDipa, pDpp, idFormulario, xml, pdf, rs, pVariablesFlujo);

		// Actualizamos formularios modificados en post guardar
		actualizarFormulariosPostGuardar(pDipa, pDpp, idFormulario, rs, pVariablesFlujo);

	}

	/**
	 * Actualiza en persistencia los datos del formulario actual.
	 *
	 * @param pDipa
	 *                            Datos interno paso
	 * @param pDpp
	 *                            Datos persistencia
	 * @param idFormulario
	 *                            id formulario
	 * @param xml
	 *                            XML de datos
	 * @param pdf
	 *                            PDF de visualización
	 * @param rs
	 *                            Resultado script post guardar
	 * @param pVariablesFlujo
	 *                            Variables de flujo
	 */
	private void actualizarFormularioActual(final DatosInternosPasoRellenar pDipa, final DatosPersistenciaPaso pDpp,
			final String idFormulario, final byte[] xml, final byte[] pdf, final RespuestaScript rs,
			final VariablesFlujo pVariablesFlujo) {

		byte[] xmlFormActual = xml;

		final List<ReferenciaFichero> ficherosBorrar = new ArrayList<>();

		// Comprobamos si el formulario actual se ha modificado en el script de
		// postguardar
		if (rs != null) {
			final ResModificacionFormularios rsp = (ResModificacionFormularios) rs.getResultado();
			if (rsp.getFormulariosModificados().contains(idFormulario)) {
				final ValoresFormulario valoresFormulario = new ValoresFormulario(xml);
				final List<ValorCampo> valoresModificados = rsp.getDatosModificadosFormulario(idFormulario);
				valoresFormulario.modificarValoresCampos(valoresModificados);
				xmlFormActual = valoresFormulario.toXml();
			}
		}

		// Obtenemos datos actuales en persistencia del formulario
		final DocumentoPasoPersistencia docPaso = pDpp.getDocumentoPasoPersistencia(idFormulario, ConstantesNumero.N1);

		// - Marcamos para borrar fichero, pdf y firmas
		ficherosBorrar.addAll(docPaso.obtenerReferenciasFicherosFormulario(true, true, true));
		// - Insertamos nuevos ficheros de datos y pdf
		ReferenciaFichero referenciaXML = null;
		ReferenciaFichero referenciaPDF = null;
		referenciaXML = dao.insertarFicheroPersistencia(idFormulario + ".xml", xmlFormActual,
				pVariablesFlujo.getIdSesionTramitacion());
		if (pdf != null) {
			referenciaPDF = dao.insertarFicheroPersistencia(idFormulario + ".pdf", pdf,
					pVariablesFlujo.getIdSesionTramitacion());
		}

		docPaso.setFichero(referenciaXML);
		docPaso.setFormularioPdf(referenciaPDF);

		// - Borramos firmas
		docPaso.removeFirmasFicheros();

		// - Establecemos estado
		docPaso.setEstado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);

		// - Actualizamos datos en BBDD
		dao.establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docPaso);
		for (final ReferenciaFichero ref : ficherosBorrar) {
			dao.eliminarFicheroPersistencia(ref);
		}
	}

	/**
	 * Actualiza formularios indicados en el script de postguardar.
	 *
	 * @param pDipa
	 *                                Datos internos paso
	 * @param pDpp
	 *                                Datos persistencia
	 * @param pIdFormularioActual
	 *                                Id formulario actual
	 * @param rs
	 *                                Resultado script post guardar
	 * @param pVariablesFlujo
	 *                                Variables flujo
	 */
	private void actualizarFormulariosPostGuardar(final DatosInternosPasoRellenar pDipa,
			final DatosPersistenciaPaso pDpp, final String pIdFormularioActual, final RespuestaScript rs,
			final VariablesFlujo pVariablesFlujo) {

		if (rs != null) {
			final List<ReferenciaFichero> ficherosBorrar = new ArrayList<ReferenciaFichero>();

			final ResModificacionFormularios rsp = (ResModificacionFormularios) rs.getResultado();

			ReferenciaFichero referenciaXML;

			// Para formularios en los que se han modificado datos: modificamos
			// estado formulario modificado, actualizamos xml y borramos pdf
			for (final String idFormModif : rsp.getFormulariosModificados()) {

				// Si es el formulario actual, ya se ha modificado antes
				if (idFormModif.equals(pIdFormularioActual)) {
					continue;
				}

				final DocumentoPasoPersistencia docPasoModif = pDpp.getDocumentoPasoPersistencia(idFormModif,
						ConstantesNumero.N1);

				// Marcamos para borrar ficheros anteriores (xml, pdf, firmas )
				ficherosBorrar.addAll(docPasoModif.obtenerReferenciasFicherosFormulario(true, true, true));

				// Modificamos fichero xml actual y lo guardamos
				final ValoresFormulario vf = pDipa.getValoresFormulario(idFormModif);
				vf.modificarValoresCampos(rsp.getDatosModificadosFormulario(idFormModif));
				referenciaXML = dao.insertarFicheroPersistencia(idFormModif + ".xml", vf.getXml(),
						pVariablesFlujo.getIdSesionTramitacion());

				// Establecemos nuevos datos
				if (docPasoModif.getEstado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
					docPasoModif.setEstado(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE);
				}
				docPasoModif.setFichero(referenciaXML);
				docPasoModif.setFormularioPdf(null);

				// Actualizamos datos en BBDD
				dao.establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docPasoModif);
			}

			// Para formularios en los que se ha modificado el estado:
			// modificamos estado formulario modificado,
			// borramos pdf y firmas (mantenemos xml)
			for (final String idFormModif : rsp.getFormulariosIncorrectos()) {

				final DocumentoPasoPersistencia docPasoModif = pDpp.getDocumentoPasoPersistencia(idFormModif,
						ConstantesNumero.N1);

				// Si ya se ha modificado estado al haber modificado datos no lo
				// actualizamos
				if (rsp.getFormulariosModificados().contains(idFormModif)) {
					continue;
				}

				// Marcamos para borrar ficheros anteriores (solo pdf y firmas,
				// mantenemos xml)
				ficherosBorrar.addAll(docPasoModif.obtenerReferenciasFicherosFormulario(false, true, true));

				// Establecemos nuevos datos
				if (docPasoModif.getEstado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
					docPasoModif.setEstado(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE);
				}
				docPasoModif.setFormularioPdf(null);

				// Actualizamos datos en BBDD
				dao.establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docPasoModif);
			}

			// Eliminamos ficheros marcados para borrar
			for (final ReferenciaFichero ref : ficherosBorrar) {
				dao.eliminarFicheroPersistencia(ref);
			}
		}
	}

	/**
	 * Valida si se puede guardar formulario.
	 *
	 * @param pDipa
	 *                          Datos internos paso
	 * @param pIdFormulario
	 *                          Id formulario
	 *
	 */
	private void validarCancelarFormulario(final DatosInternosPasoRellenar pDipa, final String pIdFormulario) {
		final Formulario formularioDetalle = ((DetallePasoRellenar) pDipa.getDetallePaso())
				.getFormulario(pIdFormulario);
		if (formularioDetalle == null) {
			throw new AccionPasoNoPermitidaException("El formulario no existe: " + pIdFormulario);
		}
		if (formularioDetalle.getObligatorio() != TypeObligatoriedad.OPCIONAL) {
			throw new AccionPasoNoPermitidaException("Solo puede ser cancelado un formulario opcional");
		}
		if (formularioDetalle.getRellenado() == TypeEstadoDocumento.SIN_RELLENAR) {
			throw new AccionPasoNoPermitidaException(
					"El formulario no esta rellenado (ni correcta ni incorrectamente)");
		}
	}

	/**
	 * Actualiza datos de persistencia en el paso de cancelar formulario opcional.
	 *
	 * @param pDipa
	 *                                        Datos internos paso pagar
	 * @param pDpp
	 *                                        Datos persistencia paso pagar
	 * @param pVariablesFlujo
	 *                                        Variables flujo
	 * @param pIdFormulario
	 *                                        Id formulario opcional que se cancela
	 * @param pRespuestaScriptPostGuardar
	 *                                        Respuesta script postguardar
	 */
	private void actualizarPersistenciaCancelarFormulario(final DatosInternosPasoRellenar pDipa,
			final DatosPersistenciaPaso pDpp, final VariablesFlujo pVariablesFlujo, final String pIdFormulario,
			final RespuestaScript pRespuestaScriptPostGuardar) {

		final List<ReferenciaFichero> ficherosBorrar = new ArrayList<>();

		// Obtenemos datos actuales en persistencia del formulario
		final DocumentoPasoPersistencia docPaso = pDpp.getDocumentoPasoPersistencia(pIdFormulario, ConstantesNumero.N1);

		// Marcamos para borrar pdf y firmas asociadas. Mantenemos datos
		// formulario.
		ficherosBorrar.addAll(docPaso.obtenerReferenciasFicherosFormulario(false, true, true));

		// - Eliminamos pdf
		docPaso.setFormularioPdf(null);

		// - Establecemos estado
		docPaso.setEstado(TypeEstadoDocumento.SIN_RELLENAR);

		// - Borramos firmas
		docPaso.removeFirmasFicheros();

		// - Actualizamos datos en BBDD
		dao.establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docPaso);
		for (final ReferenciaFichero ref : ficherosBorrar) {
			dao.eliminarFicheroPersistencia(ref);
		}

		// Actualizamos formularios modificados en post guardar
		actualizarFormulariosPostGuardar(pDipa, pDpp, pIdFormulario, pRespuestaScriptPostGuardar, pVariablesFlujo);

	}
}