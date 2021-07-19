package es.caib.sistramit.core.service.component.flujo.pasos.rellenar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistramit.core.api.exception.AccionPasoNoExisteException;
import es.caib.sistramit.core.api.exception.ConfiguracionModificadaException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRellenar;
import es.caib.sistramit.core.api.model.flujo.Formulario;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRellenar;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResDatosInicialesFormulario;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResFirmantes;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRellenar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.ValoresFormulario;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Controlador para paso Rellenar.
 *
 * @author Indra
 *
 */
@Component("controladorPasoRellenar")
public final class ControladorPasoRellenar extends ControladorPasoReferenciaImpl {

	/** Accion abrir formulario. */
	@Autowired
	private AccionAbrirFormulario accionAbrirFormulario;
	/** Accion guardar formulario. */
	@Autowired
	private AccionGuardarFormulario accionGuardarFormulario;
	/** Accion descargar formulario. */
	@Autowired
	private AccionDescargarFormulario accionDescargarFormulario;
	/** Accion descargar xml formulario para debug. */
	@Autowired
	private AccionDescargarXmlFormulario accionDescargarXmlFormulario;

	@Override
	protected void actualizarDatosInternos(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final TypeFaseActualizacionDatosInternos pFaseEjecucion) {

		// Obtenemos datos internos paso rellenar
		final DatosInternosPasoRellenar dipa = (DatosInternosPasoRellenar) pDatosPaso.internalData();
		// Regeneramos datos a partir de persistencia
		regenerarDatos(dipa, pDpp, pDefinicionTramite, pVariablesFlujo);

	}

	@Override
	protected EstadoSubestadoPaso evaluarEstadoPaso(final DatosPaso pDatosPaso) {
		// El estado del paso sera COMPLETADO cuando todos los documentos esten
		// rellenados y firmados (si lo requieren).

		TypeEstadoPaso estado;

		// Obtenemos datos internos paso rellenar
		final DatosInternosPasoRellenar dipa = (DatosInternosPasoRellenar) pDatosPaso.internalData();

		if (isPendiente(dipa)) {
			estado = TypeEstadoPaso.PENDIENTE;
		} else {
			estado = TypeEstadoPaso.COMPLETADO;
		}

		return new EstadoSubestadoPaso(estado, null);
	}

	/**
	 * Calcula si el paso esta pendiente.
	 *
	 * @param pDipa
	 *                  Datos internos paso
	 * @return pendiente
	 */
	private boolean isPendiente(final DatosInternosPasoRellenar pDipa) {

		boolean pendiente = false;

		// Recorremos anexos
		for (final Formulario f : ((DetallePasoRellenar) pDipa.getDetallePaso()).getFormularios()) {

			if (f.getRellenado() == TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE) {
				pendiente = true;
				break;
			}

			if (f.getRellenado() == TypeEstadoDocumento.SIN_RELLENAR
					&& f.getObligatorio() == TypeObligatoriedad.OBLIGATORIO) {
				pendiente = true;
				break;
			}
		}

		return pendiente;
	}

	@Override
	protected List<DatosDocumento> obtenerDocumentosCompletadosPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final DefinicionTramiteSTG pDefinicionTramite) {
		// Obtiene datos internos paso
		final DatosInternosPasoRellenar dipa = ((DatosInternosPasoRellenar) pDatosPaso.internalData());
		return UtilsPasoRellenar.obtenerDocumentosCompletados(dipa, pDpp, pDefinicionTramite, null);
	}

	@Override
	protected RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		RespuestaEjecutarAccionPaso rp = null;

		AccionPaso accionPaso = null;
		switch ((TypeAccionPasoRellenar) pAccionPaso) {
		case ABRIR_FORMULARIO:
			accionPaso = accionAbrirFormulario;
			break;
		case GUARDAR_FORMULARIO:
			accionPaso = accionGuardarFormulario;
			break;
		case DESCARGAR_FORMULARIO:
			accionPaso = accionDescargarFormulario;
			break;
		case DESCARGAR_XML:
			accionPaso = accionDescargarXmlFormulario;
			break;
		default:
			throw new AccionPasoNoExisteException("No existe acción " + pAccionPaso + " en el paso Debe Rellenar");
		}

		rp = accionPaso.ejecutarAccionPaso(pDatosPaso, pDpp, pAccionPaso, pParametros, pDefinicionTramite,
				pVariablesFlujo);
		return rp;
	}

	// ----------------------------------------------------------------

	/**
	 * Regenera datos paso en funcion de los datos de persistencia.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pDpp
	 *                               Datos persistencia
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 *
	 */
	private void regenerarDatos(final DatosInternosPasoRellenar pDipa, final DatosPersistenciaPaso pDpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Inicializamosdetalle del paso a partir de la definicion
		final DetallePasoRellenar dpaNew = calcularDetalle(pDipa.getIdPaso(), pDefinicionTramite, pVariablesFlujo,
				pDpp);
		pDipa.setDetallePaso(dpaNew);

		// Recorremos documentos existentes en persistencia para actualizar
		// la informacion del documento en el detalle del paso
		regenerarDatosDocumentosExistentes(pDipa, pDpp, pVariablesFlujo);

		// Creamos en persistencia los documentos que existen en el detalle pero
		// que aun no existen en persistencia (ocurrirá al inicializar paso)
		regenerarDatosDocumentosNuevos(pDipa, pDpp, pDefinicionTramite, pVariablesFlujo);

	}

	/**
	 * Método para calcular el detalle del paso en base a la definición del trámite.
	 *
	 * @param idPaso
	 *                               Parámetro id paso
	 * @param pDefinicionTramite
	 *                               Parámetro definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo con los datos de pasos
	 *                               anteriores.
	 * @param pDpp
	 *                               Datos persistencia paso. Necesario para ir
	 *                               recuperando los formularios correctos en este
	 *                               paso.
	 * @return el detalle paso rellenar
	 */
	private DetallePasoRellenar calcularDetalle(final String idPaso, final DefinicionTramiteSTG pDefinicionTramite,
			final VariablesFlujo pVariablesFlujo, final DatosPersistenciaPaso pDpp) {

		// Obtenemos definición del paso
		final RPasoTramitacionRellenar defPaso = (RPasoTramitacionRellenar) UtilsSTG.devuelveDefinicionPaso(idPaso,
				pDefinicionTramite);

		// Recorremos la lista fija de documentos
		final List<Formulario> formularios = calcularDetalleListaFijaFormularios(pDefinicionTramite, defPaso,
				pVariablesFlujo, pDpp);

		// Debe existir al menos 1 formulario
		if (formularios.isEmpty()) {
			throw new ErrorConfiguracionException("Paso rellenar debe tener al menos 1 formulario");
		}

		// Creamos detalle paso
		final DetallePasoRellenar dpa = new DetallePasoRellenar();
		dpa.setCompletado(TypeSiNo.NO);
		dpa.setId(pDpp.getId());
		dpa.getFormularios().addAll(formularios);
		return dpa;
	}

	/**
	 * Recalcula detalle formularios.
	 *
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pPasoDef
	 *                               Parámetro paso
	 * @param pVariablesFlujo
	 *                               Parámetro plugins paso
	 * @param pDpp
	 *                               Datos persistencia paso
	 * @return Lista de detalles de formulario
	 */
	private List<Formulario> calcularDetalleListaFijaFormularios(final DefinicionTramiteSTG pDefinicionTramite,
			final RPasoTramitacionRellenar pPasoDef, final VariablesFlujo pVariablesFlujo,
			final DatosPersistenciaPaso pDpp) {

		// Lista de formularios a devolver
		final List<Formulario> formularios = new ArrayList<>();

		// Lista de formularios que se han ido completando para irlos pasando a
		// los scripts
		final List<DatosDocumento> formulariosCompletados = new ArrayList<>();

		// Recorremos definicion del paso para calcular la lista de formularios
		for (final RFormularioTramite formularioDef : pPasoDef.getFormularios()) {

			// Calculamos el detalle del formulario
			final Formulario formulario = calcularDetalleFormularioFijo(pVariablesFlujo, formulariosCompletados,
					pDefinicionTramite, formularioDef);

			// Añadimos formulario a la lista
			formularios.add(formulario);

			// Si no queda como dependiente y en persistencia esta como
			// completado correctamente lo añadimos a lista de formularios
			// completados para que este disponible en la evaluación de los
			// scripts de los siguientes documentos
			final DocumentoPasoPersistencia docPer = pDpp.getDocumentoPasoPersistencia(formulario.getId(),
					ConstantesNumero.N1);
			if (formulario.getObligatorio() != TypeObligatoriedad.DEPENDIENTE && docPer != null
					&& docPer.getEstado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
				// Recuperamos el xml del formulario de persistencia
				final DatosFicheroPersistencia ficPer = getDao().recuperarFicheroPersistencia(docPer.getFichero());
				// Creamos datos formulario y lo añadimos a lista de formularios
				// completados
				final DatosDocumentoFormulario ddf = UtilsPasoRellenar.crearDatosDocumentoFormulario(pDpp, formulario,
						ValoresFormulario.createValoresFormulario(ficPer.getContenido()));
				formulariosCompletados.add(ddf);
			}

			// Si se tiene que firmar y está completado, calculamos firmantes
			if (formulario.getFirmar() == TypeSiNo.SI && docPer != null
					&& docPer.getEstado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
				// Si tiene script de firmantes lo ejecutamos
				if (UtilsSTG.existeScript(formularioDef.getScriptFirmantes())) {
					final ResFirmantes resf = ejecutarScriptFirmantes(pVariablesFlujo, pDefinicionTramite,
							formularioDef, formulariosCompletados);
					formulario.setFirmantes(resf.getFirmantes());
				} else {
					// Si no tiene script de firmantes, pues el único
					// firmante sería el iniciador.
					// En caso de que el acceso sea no autenticado generamos
					// error ya que no sabremos nif iniciador
					if (pVariablesFlujo.getNivelAutenticacion() == TypeAutenticacion.ANONIMO) {
						throw new ErrorConfiguracionException(
								"No se ha establecido script de firmantes para formulario "
										+ formularioDef.getIdentificador());
					}

					final Persona f = UtilsFlujo.usuarioPersona(pVariablesFlujo.getUsuario());
					formulario.getFirmantes().add(f);
				}
			}

		}

		return formularios;
	}

	/**
	 * Calcula el detalle de un formulario fijo.
	 *
	 * @param pVariablesFlujo
	 *                                    Variables flujo
	 * @param pFormulariosCompletados
	 *                                    Formularios completados anteriores
	 * @param pDefinicionTramite
	 *                                    Definición trámite
	 * @param pFormularioDef
	 *                                    Definición formulario
	 * @return Detalle formulario
	 */
	private Formulario calcularDetalleFormularioFijo(final VariablesFlujo pVariablesFlujo,
			final List<DatosDocumento> pFormulariosCompletados, final DefinicionTramiteSTG pDefinicionTramite,
			final RFormularioTramite pFormularioDef) {

		final Formulario formulario = Formulario.createNewFormulario();
		formulario.setId(pFormularioDef.getIdentificador());
		formulario.setRellenado(TypeEstadoDocumento.SIN_RELLENAR);
		formulario.setTitulo(pFormularioDef.getDescripcion());

		// Evaluamos script obligatoriedad
		final TypeObligatoriedad rs = calcularObligatoriedadFormularioFijo(pDefinicionTramite, pFormularioDef,
				pFormulariosCompletados, pVariablesFlujo);
		formulario.setObligatorio(rs);

		// Evaluamos si se debe firmar digitalmente
		if (pFormularioDef.isFirmar() && formulario.getObligatorio() != TypeObligatoriedad.DEPENDIENTE) {
			formulario.setFirmar(TypeSiNo.SI);
		} else {
			formulario.setFirmar(TypeSiNo.NO);
		}
		return formulario;
	}

	/**
	 * Calcula obligatoriedad formulario fijo.
	 *
	 * @param pDefinicionTramite
	 *                                    Def tramite
	 * @param pFormularioDef
	 *                                    Def formulario
	 * @param pFormulariosCompletados
	 *                                    List forms completados
	 * @param pVariablesFlujo
	 *                                    Vbles flujo
	 * @return obligatoriedad formulario fijo
	 */
	private TypeObligatoriedad calcularObligatoriedadFormularioFijo(final DefinicionTramiteSTG pDefinicionTramite,
			final RFormularioTramite pFormularioDef, final List<DatosDocumento> pFormulariosCompletados,
			final VariablesFlujo pVariablesFlujo) {

		TypeObligatoriedad rs = TypeObligatoriedad.fromString(pFormularioDef.getObligatoriedad());
		if (rs == null) {
			throw new TipoNoControladoException(
					"Valor " + pFormularioDef.getObligatoriedad() + " no definido para TypeObligatoriedad");
		}

		if (rs == TypeObligatoriedad.DEPENDIENTE) {
			rs = UtilsFlujo.ejecutarScriptDependenciaDocumento(pFormularioDef.getScriptObligatoriedad(),
					pFormularioDef.getIdentificador(), pVariablesFlujo, pFormulariosCompletados, getScriptFlujo(),
					pDefinicionTramite);
		}
		return rs;
	}

	/**
	 * Ejecuta script de firmantes.
	 *
	 * @param pVariablesFlujo
	 *                                    Variables flujo
	 * @param pDefinicionTramite
	 *                                    Definición trámite
	 * @param formularioDef
	 *                                    Definición formulario
	 * @param pFormulariosCompletados
	 *                                    Formularios completados anteriormente al
	 *                                    formulario actual
	 * @return el res firmantes
	 */
	private ResFirmantes ejecutarScriptFirmantes(final VariablesFlujo pVariablesFlujo,
			final DefinicionTramiteSTG pDefinicionTramite, final RFormularioTramite formularioDef,
			final List<DatosDocumento> pFormulariosCompletados) {

		// Ejecutamos script
		final Map<String, String> codigosError = UtilsSTG
				.convertLiteralesToMap(formularioDef.getScriptFirmantes().getLiterales());
		final RespuestaScript rs = getScriptFlujo().executeScriptFlujo(TypeScriptFlujo.SCRIPT_FIRMANTES,
				formularioDef.getIdentificador(), formularioDef.getScriptFirmantes().getScript(), pVariablesFlujo, null,
				pFormulariosCompletados, codigosError, pDefinicionTramite);

		// Evaluamos resultado con la lista de firmantes
		final ResFirmantes resf = (ResFirmantes) rs.getResultado();
		if (resf.getFirmantes().isEmpty()) {
			throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_FIRMANTES.name(),
					pVariablesFlujo.getIdSesionTramitacion(), formularioDef.getIdentificador(),
					"No se han especificado firmantes");
		}
		return resf;
	}

	/**
	 * Regenera datos de los documentos existentes en persistencia.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pDpp
	 *                            Datos persistencia
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 */
	private void regenerarDatosDocumentosExistentes(final DatosInternosPasoRellenar pDipa,
			final DatosPersistenciaPaso pDpp, final VariablesFlujo pVariablesFlujo) {
		for (final DocumentoPasoPersistencia docDpp : pDpp.getDocumentos()) {

			// Si no existe en la lista de formularios generamos un error
			// de configuración modificada
			if (((DetallePasoRellenar) pDipa.getDetallePaso()).getFormulario(docDpp.getId()) == null) {
				throw new ConfiguracionModificadaException(
						"El formulario " + docDpp.getId() + " no existe en configuración");
			}

			// Obtenemos detalle formulario recalculado
			final Formulario detalleForm = ((DetallePasoRellenar) pDipa.getDetallePaso()).getFormulario(docDpp.getId());

			// Regeneramos doc existente
			regenerarDocumentoExistente(pDipa.getIdPaso(), detalleForm, docDpp, pVariablesFlujo);

			// Cacheamos datos formulario
			final DatosFicheroPersistencia dfp = getDao().recuperarFicheroPersistencia(docDpp.getFichero());
			pDipa.addDatosFormulario(docDpp.getId(), dfp.getContenido());
		}
	}

	/**
	 * Regenera informacion de detalle front y de persistencia para un documento
	 * existente.
	 *
	 * @param pIdPaso
	 *                               Id paso
	 * @param pDetalleFormulario
	 *                               Detalle formulario
	 * @param pDocDpp
	 *                               Datos del documento en persistencia
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 */
	private void regenerarDocumentoExistente(final String pIdPaso, final Formulario pDetalleFormulario,
			final DocumentoPasoPersistencia pDocDpp, final VariablesFlujo pVariablesFlujo) {

		// Actualiza persistencia en funcion del detalle de formulario
		// recalculado
		actualizarPersistenciaExistente(pIdPaso, pDocDpp, pDetalleFormulario, pVariablesFlujo);

		// Actualiza detalle formulario en funcion de la persistencia resultante
		actualizarDetalleExistente(pDetalleFormulario, pDocDpp);

	}

	/**
	 * Actualiza el detalle de un documento existente a partir de los datos de
	 * persistencia y los datos recalculados.
	 *
	 * @param detalleForm
	 *                        Detalle formulario
	 * @param pDocDpp
	 *                        Datos documento persistencia
	 */
	private void actualizarDetalleExistente(final Formulario detalleForm, final DocumentoPasoPersistencia pDocDpp) {

		// Actualizar estado
		detalleForm.setRellenado(pDocDpp.getEstado());

	}

	/**
	 * Actualiza persistencia a partir del detalle del formulario recalculado.
	 *
	 * @param idPaso
	 *                            Id paso
	 * @param pDocDpp
	 *                            Datos documento persistencia
	 * @param detalleForm
	 *                            Detalle formulario
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 */
	private void actualizarPersistenciaExistente(final String idPaso, final DocumentoPasoPersistencia pDocDpp,
			final Formulario detalleForm, final VariablesFlujo pVariablesFlujo) {

		// Indica si debemos updatear persistencia
		boolean updateBD = false;

		// Ficheros a eliminar
		final List<ReferenciaFichero> ficsPersistenciaBorrar = new ArrayList<>();

		// Estado: el de persistencia excepto si pasa a dependiente
		if (detalleForm.getObligatorio() == TypeObligatoriedad.DEPENDIENTE) {
			if (pDocDpp.getEstado() != TypeEstadoDocumento.SIN_RELLENAR) {
				updateBD = true;
			}
			pDocDpp.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
		}

		// Si no esta completado borramos pdf y firmas
		if (pDocDpp.getEstado() != TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
			// Buscamos pdf y firmas
			final List<ReferenciaFichero> ficsBorrar = pDocDpp.obtenerReferenciasFicherosFormulario(false, true, true);
			if (!ficsBorrar.isEmpty()) {
				updateBD = true;
				ficsPersistenciaBorrar.addAll(ficsBorrar);
				pDocDpp.setFormularioPdf(null);
			}
		}

		// En caso de haber modificado el documento actualizamos BBDD
		if (updateBD) {
			updateDB(idPaso, pDocDpp, pVariablesFlujo, ficsPersistenciaBorrar);
		}
	}

	/**
	 * Actualiza en BD el documento.
	 *
	 * @param idPaso
	 *                                   id paso
	 * @param pDocDpp
	 *                                   datos documento persistencia
	 * @param pVariablesFlujo
	 *                                   variables flujo
	 * @param ficsPersistenciaBorrar
	 *                                   ficheros a borrar
	 */
	private void updateDB(final String idPaso, final DocumentoPasoPersistencia pDocDpp,
			final VariablesFlujo pVariablesFlujo, final List<ReferenciaFichero> ficsPersistenciaBorrar) {
		getDao().establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), idPaso, pDocDpp);
		for (final ReferenciaFichero refFic : ficsPersistenciaBorrar) {
			getDao().eliminarFicheroPersistencia(refFic);
		}
	}

	/**
	 * Creamos en persistencia los documentos que existen en el detalle pero que aun
	 * no existen en persistencia (ocurrirá al inicializar paso).
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pDpp
	 *                               Datos persistencia
	 * @param pDefinicionTramite
	 *                               Definición támite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 */
	private void regenerarDatosDocumentosNuevos(final DatosInternosPasoRellenar pDipa, final DatosPersistenciaPaso pDpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		final RPasoTramitacionRellenar pasoDef = (RPasoTramitacionRellenar) UtilsSTG
				.devuelveDefinicionPaso(pDipa.getIdPaso(), pDefinicionTramite);
		for (final Formulario formulario : ((DetallePasoRellenar) pDipa.getDetallePaso()).getFormularios()) {
			if (pDpp.getNumeroInstanciasDocumento(formulario.getId()) == 0) {
				// Obtenemos datos iniciales formulario
				final byte[] xml = obtenerDatosIniciales(pVariablesFlujo, pDefinicionTramite, pasoDef, formulario);

				// Cacheamos datos formulario
				pDipa.addDatosFormulario(formulario.getId(), xml);

				// Creamos documento en BBDD
				final ReferenciaFichero refXml = getDao().insertarFicheroPersistencia(formulario.getId() + ".xml", xml,
						pVariablesFlujo.getIdSesionTramitacion());
				final DocumentoPasoPersistencia docDpp = DocumentoPasoPersistencia.createDocumentoPersistencia();
				docDpp.setId(formulario.getId());
				docDpp.setTipo(TypeDocumentoPersistencia.FORMULARIO);
				docDpp.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
				docDpp.setInstancia(ConstantesNumero.N1);
				docDpp.setFichero(refXml);
				getDao().establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docDpp);
				pDpp.getDocumentos().add(docDpp);
			}
		}
	}

	/**
	 * Método para Obtener datos iniciales de la clase ControladorPasoRellenarImpl.
	 *
	 * @param pVariablesFlujo
	 *                               Parámetro variables flujo
	 * @param pDefinicionTramite
	 *                               Definición trámite
	 * @param pasoDef
	 *                               Parámetro paso def
	 * @param formulario
	 *                               Parámetro formulario
	 * @return el byte[]
	 */
	private byte[] obtenerDatosIniciales(final VariablesFlujo pVariablesFlujo,
			final DefinicionTramiteSTG pDefinicionTramite, final RPasoTramitacionRellenar pasoDef,
			final Formulario formulario) {
		// Inicializamos datos del formulario (script datos iniciales)
		final ValoresFormulario vf = ValoresFormulario.createValoresFormularioVacio();
		final RFormularioTramite formDef = UtilsSTG.devuelveDefinicionFormulario(pasoDef, formulario.getId());
		if (UtilsSTG.existeScript(formDef.getScriptDatosIniciales())) {
			// Ejecutamos script
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(formDef.getScriptDatosIniciales().getLiterales());
			final RespuestaScript rs = getScriptFlujo().executeScriptFlujo(
					TypeScriptFlujo.SCRIPT_DATOS_INICIALES_FORMULARIO, formDef.getIdentificador(),
					formDef.getScriptDatosIniciales().getScript(), pVariablesFlujo, null, null, codigosError,
					pDefinicionTramite);

			// Recuperamos datos iniciales
			final ResDatosInicialesFormulario rsd = (ResDatosInicialesFormulario) rs.getResultado();
			vf.modificarValoresCampos((rsd.getDatosIniciales()));
		}
		return vf.getXml();
	}
}
