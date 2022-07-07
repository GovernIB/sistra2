package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.AccionPasoNoExisteException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoAnexar;
import es.caib.sistramit.core.api.model.flujo.Fichero;
import es.caib.sistramit.core.api.model.flujo.Firmante;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.PlantillaAnexo;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoAnexar;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedadFirmante;
import es.caib.sistramit.core.api.model.flujo.types.TypePlantillaAnexo;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.ControladorPasoReferenciaImpl;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ClzAnexoDinamico;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResAnexosDinamicos;
import es.caib.sistramit.core.service.component.script.plugins.flujo.ResFirmantes;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoAnexo;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoAnexar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.EstadoMarcadores;
import es.caib.sistramit.core.service.model.flujo.EstadoSubestadoPaso;
import es.caib.sistramit.core.service.model.flujo.FirmaDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ListaDinamicaAnexos;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeFaseActualizacionDatosInternos;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Controlador para paso Anexar.
 *
 * @author Indra
 *
 */
@Component("controladorPasoAnexar")
public final class ControladorPasoAnexar extends ControladorPasoReferenciaImpl {

	/** Accion anexar documento. */
	@Autowired
	private AccionAnexarDocumento accionAnexarDocumento;
	/** Accion descargar plantilla. */
	@Autowired
	private AccionDescargarPlantilla accionDescargarPlantilla;
	/** Accion obtener datos anexo. */
	@Autowired
	private AccionDescargarAnexo accionDescargarAnexo;
	/** Accion borrar documento. */
	@Autowired
	private AccionBorrarAnexo accionBorrarAnexo;

	/** Marcador estado paso que indica que hay algun anexo pendiente. */
	private static final String MARCADOR_PENDIENTE_ASISTENTE = "pendienteAsistente";

	@Override
	protected void actualizarDatosInternos(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final TypeFaseActualizacionDatosInternos pFaseEjecucion) {
		// Obtenemos datos internos del paso anexar
		final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso.internalData();
		// Regenera datos a partir de persistencia
		regenerarDatosAnexar(dipa, pDpp, pVariablesFlujo, pDefinicionTramite);
	}

	@Override
	protected EstadoSubestadoPaso evaluarEstadoPaso(final DatosPaso pDatosPaso) {
		// El estado del paso sera COMPLETADO cuando todos los documentos esten
		// rellenados y firmados (si lo requieren).
		// El estado del paso sera PENDIENTE_BANDEJA_FIRMA cuando todos los
		// documentos esten
		// rellenados y firmados (si lo requieren) a excepción de los que se
		// requieran que
		// se firmen a través de la bandeja.

		// Obtenemos datos internos del paso anexar
		final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso.internalData();
		// Calculamos marcadores estado
		final EstadoMarcadores marcadores = calcularMarcadoresEstado(dipa);
		// Establecemos estado en funcion de los marcadores
		TypeEstadoPaso estado;
		if (marcadores.getMarcador(MARCADOR_PENDIENTE_ASISTENTE)) {
			estado = TypeEstadoPaso.PENDIENTE;
		} else {
			estado = TypeEstadoPaso.COMPLETADO;
		}
		final EstadoSubestadoPaso res = new EstadoSubestadoPaso(estado, null);
		return res;
	}

	@Override
	protected List<DatosDocumento> obtenerDocumentosCompletadosPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final DefinicionTramiteSTG pDefinicionTramite) {
		final List<DatosDocumento> resultado = new ArrayList<>();
		// Obtemos datos internos paso
		final DatosInternosPasoAnexar dipa = ((DatosInternosPasoAnexar) pDatosPaso.internalData());
		// Buscamos los documentos completados (para los genericos
		// devolvemos uno por instancia).
		for (final Anexo anexo : ((DetallePasoAnexar) dipa.getDetallePaso()).getAnexos()) {
			if (anexo.getRellenado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {

				if (anexo.getPresentacion() == TypePresentacion.ELECTRONICA) {
					addFicherosAnexo(pDpp, anexo, resultado);
				} else {
					addAnexoPresencial(pDpp, anexo, resultado);
				}
			}
		}
		return resultado;
	}

	@Override
	protected RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso,
			final DatosPersistenciaPaso pDpp, final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {
		// Ejecutamos accion
		AccionPaso accionPaso = null;
		switch ((TypeAccionPasoAnexar) pAccionPaso) {
		case ANEXAR_DOCUMENTO:
			accionPaso = accionAnexarDocumento;
			break;
		case DESCARGAR_PLANTILLA:
			accionPaso = accionDescargarPlantilla;
			break;
		case DESCARGAR_ANEXO:
			accionPaso = accionDescargarAnexo;
			break;
		case BORRAR_ANEXO:
			accionPaso = accionBorrarAnexo;
			break;
		default:
			throw new AccionPasoNoExisteException("No existeix acció " + pAccionPaso + " a la passa Anexar");
		}
		final RespuestaEjecutarAccionPaso rp = accionPaso.ejecutarAccionPaso(pDatosPaso, pDpp, pAccionPaso, pParametros,
				pDefinicionTramite, pVariablesFlujo);
		return rp;
	}

	// -----------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// -----------------------------------------------------------------------

	/**
	 * Regenera datos paso anexar a partir de persistencia.
	 *
	 * @param pDipa
	 *                               Datos internos paso anexar
	 * @param pDpp
	 *                               Datos persistencia paso
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 *
	 */
	private void regenerarDatosAnexar(final DatosInternosPasoAnexar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo, final DefinicionTramiteSTG pDefinicionTramite) {
		// Calculamos detalle del paso a partir de la definicion
		final DetallePasoAnexar detalleNew = calcularDetalle(pDipa, pDipa.getIdPaso(), pDefinicionTramite,
				pVariablesFlujo);
		// Inicializamos detalle
		pDipa.setDetallePaso(detalleNew);
		// Regeneramos anexos existentes en persistencia
		regenerarAnexosExistentes(pDipa, pDpp, pVariablesFlujo);
		// Regeneramos anexos que todavia no existen en persistencia
		regenerarAnexosNuevos(pDipa, pDpp, pVariablesFlujo);
	}

	/**
	 * Calcula el detalle del paso a partir de la definición del trámite y de las
	 * variables de flujo.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param idPaso
	 *                               Id paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables paso
	 * @return el detalle paso anexar
	 */
	private DetallePasoAnexar calcularDetalle(final DatosInternosPasoAnexar pDipa, final String idPaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Obtenemos definición del paso
		final RPasoTramitacionAnexar defPaso = (RPasoTramitacionAnexar) UtilsSTG.devuelveDefinicionPaso(idPaso,
				pDefinicionTramite);

		// Recorremos la lista fija de documentos
		final List<Anexo> anexosFij = calcularDetalleListaFijaAnexos(defPaso, pDefinicionTramite, pVariablesFlujo);

		// Evaluamos lista dinamica de documentos
		final ListaDinamicaAnexos listDin = calcularDetalleListaDinamicaAnexos(pDipa, defPaso, pDefinicionTramite,
				pVariablesFlujo);
		final List<Anexo> anexosDin = listDin.getAnexos();
		final boolean precedenciaDin = listDin.isPrecedenciaSobreAnexosFijos();

		// Verifica si existen ids anexos repetidos entre fijos y dinamicos
		verificarIdAnexoRepetido(anexosFij, anexosDin);

		// Si hay algun doc presencial, todos deberan ser presenciales
		revisarDocumentosPresenciales(anexosFij, anexosDin);

		// Creamos detalle paso
		final DetallePasoAnexar dpa = new DetallePasoAnexar();
		dpa.setId(defPaso.getIdentificador());
		// - Añadimos los anexos poniendo primero los obligatorios y luego los
		// opcionales. En segundo nivel de orden, se tiene en cuenta si se quieren
		// primero los fijos o los dinámicos.
		if (precedenciaDin) {
			dpa.getAnexos().addAll(obtenerAnexos(TypeObligatoriedad.OBLIGATORIO, anexosDin));
			dpa.getAnexos().addAll(obtenerAnexos(TypeObligatoriedad.OBLIGATORIO, anexosFij));
			dpa.getAnexos().addAll(obtenerAnexos(TypeObligatoriedad.OPCIONAL, anexosDin));
			dpa.getAnexos().addAll(obtenerAnexos(TypeObligatoriedad.OPCIONAL, anexosFij));
		} else {
			dpa.getAnexos().addAll(obtenerAnexos(TypeObligatoriedad.OBLIGATORIO, anexosFij));
			dpa.getAnexos().addAll(obtenerAnexos(TypeObligatoriedad.OBLIGATORIO, anexosDin));
			dpa.getAnexos().addAll(obtenerAnexos(TypeObligatoriedad.OPCIONAL, anexosFij));
			dpa.getAnexos().addAll(obtenerAnexos(TypeObligatoriedad.OPCIONAL, anexosDin));
		}
		dpa.setCompletado(TypeSiNo.NO);

		return dpa;
	}

	/**
	 * Verifica si hay anexo repetido.
	 *
	 * @param anexosFij
	 *                      Anexos fijos
	 * @param anexosDin
	 *                      Anexos dinámicos
	 */
	private void verificarIdAnexoRepetido(final List<Anexo> anexosFij, final List<Anexo> anexosDin) {
		final List<String> ids = new ArrayList<>();
		final List<Anexo> anexos = new ArrayList<>();
		anexos.addAll(anexosFij);
		anexos.addAll(anexosDin);
		for (final Anexo a : anexos) {
			if (ids.contains(a.getId())) {
				throw new ErrorConfiguracionException("Id annex repetit: " + a.getId());
			}
			ids.add(a.getId());
		}
	}

	/**
	 * Si hay algun doc presencial, todos deberan ser presenciales.
	 *
	 * @param anexosFij
	 *                      Anexos fijos
	 * @param anexosDin
	 *                      Anexos dinamicos
	 */
	private void revisarDocumentosPresenciales(final List<Anexo> anexosFij, final List<Anexo> anexosDin) {
		if (existeDocPresencial(anexosFij) || existeDocPresencial(anexosDin)) {
			convertirDocPresencial(anexosFij);
			convertirDocPresencial(anexosDin);
		}
	}

	/**
	 * Comprueba si existe algún documento presencial.
	 *
	 * @param listaAnexos
	 *                        Lista anexos
	 * @return boolean
	 */
	private boolean existeDocPresencial(final List<Anexo> listaAnexos) {
		boolean res = false;
		if (listaAnexos != null) {
			for (final Anexo a : listaAnexos) {
				if (a.getPresentacion() == TypePresentacion.PRESENCIAL) {
					res = true;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Convierte los documentos electrónicos en presenciales.
	 *
	 * @param listaAnexos
	 *                        Lista anexos
	 */
	private void convertirDocPresencial(final List<Anexo> listaAnexos) {
		if (listaAnexos != null) {
			for (final Anexo a : listaAnexos) {
				if (a.getPresentacion() == TypePresentacion.ELECTRONICA) {
					a.setPresentacion(TypePresentacion.PRESENCIAL);
					a.setAnexarfirmado(TypeSiNo.NO);
					a.setConvertirPDF(TypeSiNo.NO);
					a.setExtensiones(null);
					a.setFirmantes(null);
					a.setFirmar(TypeSiNo.NO);
					a.setMaxInstancias(ConstantesNumero.N1);
					a.setTamMax(null);
				}
			}
		}
	}

	/**
	 * Obtiene anexos segun obligatoriedad.
	 *
	 * @param pObligatorio
	 *                         Obligatoriedad
	 * @param pAnexos
	 *                         Lista anexos
	 * @return Lista anexos
	 */
	private List<Anexo> obtenerAnexos(final TypeObligatoriedad pObligatorio, final List<Anexo> pAnexos) {
		final List<Anexo> res = new ArrayList<>();
		if (pAnexos != null) {
			for (final Anexo a : pAnexos) {
				if (a.getObligatorio() == pObligatorio) {
					res.add(a);
				}
			}
		}
		return res;
	}

	/**
	 * Calcula lista dinámica de anexos en función de la definición del paso y de
	 * las variables del flujo.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param defPaso
	 *                               Definición paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @return Lista dinámica de anexos
	 */
	private ListaDinamicaAnexos calcularDetalleListaDinamicaAnexos(final DatosInternosPasoAnexar pDipa,
			final RPasoTramitacionAnexar defPaso, final DefinicionTramiteSTG pDefinicionTramite,
			final VariablesFlujo pVariablesFlujo) {
		final List<Anexo> anexos = new ArrayList<>();
		boolean precedencia = false;
		// Comprobamos si existe script de anexos dinamicos
		if (UtilsSTG.existeScript(defPaso.getScriptAnexosDinamicos())) {
			// Ejecutamos script
			final Map<String, String> codigosError = UtilsSTG
					.convertLiteralesToMap(defPaso.getScriptAnexosDinamicos().getLiterales());
			final RespuestaScript rs = getScriptFlujo().executeScriptFlujo(TypeScriptFlujo.SCRIPT_LISTA_DINAMICA_ANEXOS,
					defPaso.getIdentificador(), defPaso.getScriptAnexosDinamicos().getScript(), pVariablesFlujo, null,
					null, codigosError, pDefinicionTramite);

			// Evaluamos resultado
			final ResAnexosDinamicos rsa = (ResAnexosDinamicos) rs.getResultado();
			precedencia = rsa.isPrecedenciaSobreAnexosFijos();
			for (final ClzAnexoDinamico anexd : rsa.getAnexos()) {

				if (verificarIdAnexoRepetido(anexos, anexd.getIdentificador())) {
					throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_LISTA_DINAMICA_ANEXOS.name(),
							pVariablesFlujo.getIdSesionTramitacion(), null,
							"Id annex repetit: " + anexd.getIdentificador());
				}

				final Anexo anexo = Anexo.createNewAnexo();
				anexo.setDinamico(TypeSiNo.SI);
				anexo.setId(anexd.getIdentificador());
				anexo.setTitulo(anexd.getDescripcion());
				anexo.setTipoENI("TD99");
				anexo.setAyuda("");
				anexo.setPresentacion(TypePresentacion.ELECTRONICA);
				anexo.setExtensiones(calcularExtensionesPermitidas(anexd.getExtensiones()));
				if (StringUtils.isNotBlank(anexd.getTamanyoMaximo())
						&& !("0KB".equals(StringUtils.deleteWhitespace(anexd.getTamanyoMaximo().toUpperCase())))) {
					anexo.setTamMax(anexd.getTamanyoMaximo());
				}
				if (StringUtils.isNotBlank(anexd.getUrlPlantilla())) {
					anexo.setPlantilla(PlantillaAnexo.createNewPlantillaAnexo(TypePlantillaAnexo.EXTERNA,
							anexd.getUrlPlantilla()));
				}
				if (anexd.isObligatorio()) {
					anexo.setObligatorio(TypeObligatoriedad.OBLIGATORIO);
				} else {
					anexo.setObligatorio(TypeObligatoriedad.OPCIONAL);
				}
				if (anexd.isConvertirPDF()) {
					anexo.setConvertirPDF(TypeSiNo.SI);
				}
				if (anexd.isFirmar()) {
					anexo.setFirmar(TypeSiNo.SI);
					final Firmante f = Firmante.createNewFirmante();
					f.setNif(pVariablesFlujo.getUsuario().getNif());
					f.setNombre(pVariablesFlujo.getUsuario().getNombreApellidos());
					anexo.getFirmantes().add(f);
				}
				anexo.setMaxInstancias(anexd.getMaxInstancias());

				anexos.add(anexo);
			}
		}

		final ListaDinamicaAnexos res = new ListaDinamicaAnexos();
		res.setAnexos(anexos);
		res.setPrecedenciaSobreAnexosFijos(precedencia);
		return res;
	}

	/**
	 * Verifica si id anexo esta repetido.
	 *
	 * @param anexos
	 *                          anexos
	 * @param identificador
	 *                          identificador
	 * @return true si esta repetido
	 */
	private boolean verificarIdAnexoRepetido(final List<Anexo> anexos, final String identificador) {
		boolean res = false;
		if (anexos != null) {
			for (final Anexo a : anexos) {
				if (a.getId().equals(identificador)) {
					res = true;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Calcula lista fija de anexos del paso.
	 *
	 * @param defPaso
	 *                               Parámetro def paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables flujo.
	 * @return el list
	 */
	private List<Anexo> calcularDetalleListaFijaAnexos(final RPasoTramitacionAnexar defPaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		final List<Anexo> anexos = new ArrayList<>();

		for (final RAnexoTramite anexoDef : defPaso.getAnexos()) {
			// Establece detalle anexo
			final Anexo anexoDetalle = Anexo.createNewAnexo();
			anexoDetalle.setId(anexoDef.getIdentificador());
			anexoDetalle.setTitulo(anexoDef.getDescripcion());
			anexoDetalle.setTipoENI(anexoDef.getTipoDocumental());
			anexoDetalle.setPresentacion(TypePresentacion.fromString(anexoDef.getPresentacion()));
			anexoDetalle.setAyuda(StringUtils.defaultString(anexoDef.getAyuda().getMensajeHtml()));
			if (anexoDef.getAyuda().getFichero() != null) {
				anexoDetalle.setPlantilla(PlantillaAnexo.createNewPlantillaAnexo(TypePlantillaAnexo.INTERNA, null));
			} else if (StringUtils.isNotEmpty(anexoDef.getAyuda().getUrl())) {
				anexoDetalle.setPlantilla(PlantillaAnexo.createNewPlantillaAnexo(TypePlantillaAnexo.EXTERNA,
						anexoDef.getAyuda().getUrl()));
			}

			if (anexoDetalle.getPresentacion() == TypePresentacion.ELECTRONICA) {
				// Max instancias
				anexoDetalle.setMaxInstancias(anexoDef.getPresentacionElectronica().getInstancias());
				// Conversion PDF
				if (anexoDef.getPresentacionElectronica().isConvertirPDF()) {
					anexoDetalle.setConvertirPDF(TypeSiNo.SI);
				}
				// Extensiones y tamanyo
				if (anexoDef.getPresentacionElectronica().getTamanyoMax() > 0) {
					anexoDetalle.setTamMax(anexoDef.getPresentacionElectronica().getTamanyoMax()
							+ anexoDef.getPresentacionElectronica().getTamanyoUnidad());
				}
				String extensiones = "";
				if (anexoDef.getPresentacionElectronica().getExtensiones() != null
						&& !anexoDef.getPresentacionElectronica().getExtensiones().isEmpty()) {
					for (final String ext : anexoDef.getPresentacionElectronica().getExtensiones()) {
						if (StringUtils.isNotBlank(extensiones)) {
							extensiones += ",";
						}
						extensiones += ext;
					}
				}
				anexoDetalle.setExtensiones(calcularExtensionesPermitidas(extensiones));
				// Comprobamos si debe firmarse digitalmente
				if (anexoDef.getPresentacionElectronica().isAnexarFirmado()) {
					anexoDetalle.setAnexarfirmado(TypeSiNo.SI);
				}
				if (anexoDef.getPresentacionElectronica().isFirmar()
						&& anexoDetalle.getObligatorio() != TypeObligatoriedad.DEPENDIENTE) {
					anexoDetalle.setFirmar(TypeSiNo.SI);
					calcularFirmantes(anexoDef, anexoDetalle, pDefinicionTramite, pVariablesFlujo);
				} else {
					anexoDetalle.setFirmar(TypeSiNo.NO);
				}

			}

			// Comprobamos obligatoriedad
			final TypeObligatoriedad obligatoriedad = evaluarObligatoriedad(pDefinicionTramite, anexoDef,
					pVariablesFlujo);
			anexoDetalle.setObligatorio(obligatoriedad);

			// Si es un dependiente no lo añadimos a la lista de anexos
			if (anexoDetalle.getObligatorio() != TypeObligatoriedad.DEPENDIENTE) {
				anexos.add(anexoDetalle);
			}
		}

		return anexos;
	}

	/**
	 * Calcula extensiones permitidas.
	 *
	 * @param extensionesDef
	 *                           Extensiones establecidas en definición trámite.
	 * @return extensiones permitidas
	 */
	private String calcularExtensionesPermitidas(final String extensionesDef) {
		String res = extensionesDef;
		if (StringUtils.isBlank(extensionesDef)) {
			// Establecemos extensiones por defecto
			final String extDefecto = this.getConfig()
					.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ANEXOS_EXTENSIONES_PERMITIDAS);
			if (StringUtils.isNotBlank(extDefecto)) {
				res = extDefecto;
			}
		}
		return res;
	}

	/**
	 * Calcula los firmantes de un anexo.
	 *
	 * @param anexoDetalle
	 *                               Anexo para establecer la informacion de los
	 *                               firmantes
	 * @param anexoDef
	 *                               Definicion del anexo
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables de flujo
	 *
	 */
	private void calcularFirmantes(final RAnexoTramite anexoDef, final Anexo anexoDetalle,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Si tiene script de firmantes lo ejecutamos y añadimos firmantes
		final RScript scriptFirmantes = anexoDef.getPresentacionElectronica().getScriptFirmantes();
		if (UtilsSTG.existeScript(scriptFirmantes)) {
			final Map<String, String> codigosError = UtilsSTG.convertLiteralesToMap(scriptFirmantes.getLiterales());

			final RespuestaScript rs = getScriptFlujo().executeScriptFlujo(TypeScriptFlujo.SCRIPT_FIRMANTES,
					anexoDef.getIdentificador(), scriptFirmantes.getScript(), pVariablesFlujo, null, null, codigosError,
					pDefinicionTramite);

			final ResFirmantes resf = (ResFirmantes) rs.getResultado();
			final List<Firmante> firmantes = resf.calcularFirmantes();
			if (firmantes.isEmpty()) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_FIRMANTES.name(),
						pVariablesFlujo.getIdSesionTramitacion(), anexoDef.getIdentificador(),
						"No s'han especificat signants");
			}
			anexoDetalle.setFirmantes(firmantes);

		} else {
			// Si no tiene script de firmantes, el único firmante sería el
			// iniciador. En caso de que el acceso sea no autenticado generamos
			// error ya que no sabremos nif iniciador
			if (pVariablesFlujo.getNivelAutenticacion() == TypeAutenticacion.ANONIMO) {
				throw new ErrorScriptException(TypeScriptFlujo.SCRIPT_FIRMANTES.name(),
						pVariablesFlujo.getIdSesionTramitacion(), anexoDef.getIdentificador(),
						"El tràmit és anònim i no s'ha establert script de firmants");
			}
			final Persona f = new Persona();
			f.setNif(pVariablesFlujo.getUsuario().getNif());
			f.setNombre(pVariablesFlujo.getUsuario().getNombreApellidos());
			anexoDetalle.getFirmantes()
					.add(new Firmante(f.getNif(), f.getNombre(), TypeObligatoriedadFirmante.OBLIGATORIO));
		}
	}

	/**
	 *
	 * Evalua obligatoriedad anexo.
	 *
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param detalle
	 *                               Detalle anexo
	 * @param pVariablesFlujo
	 *                               Variables flujo
	 * @return Obligatoriedad
	 */
	private TypeObligatoriedad evaluarObligatoriedad(final DefinicionTramiteSTG pDefinicionTramite,
			final RAnexoTramite detalle, final VariablesFlujo pVariablesFlujo) {
		TypeObligatoriedad rs = TypeObligatoriedad.fromString(detalle.getObligatoriedad());
		if (rs == null) {
			throw new TipoNoControladoException("Tipus d'obligatoriedad no controlat");
		}
		// Si es dependiente ejecutamos script de dependencia
		if (rs == TypeObligatoriedad.DEPENDIENTE) {

			rs = UtilsFlujo.ejecutarScriptDependenciaDocumento(detalle.getScriptDependencia(),
					detalle.getIdentificador(), pVariablesFlujo, null, getScriptFlujo(), pDefinicionTramite);
		}
		return rs;
	}

	/**
	 * Regenera anexos que todavia no existen en persistencia.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pDpp
	 *                            Datos persistencia
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 */
	private void regenerarAnexosNuevos(final DatosInternosPasoAnexar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo) {
		// Creamos en persistencia los documentos que existen en el detalle pero
		// que aun no existen en persistencia
		for (final Anexo anexo : ((DetallePasoAnexar) pDipa.getDetallePaso()).getAnexos()) {
			if (pDpp.getNumeroInstanciasDocumento(anexo.getId()) == 0) {
				final DocumentoPasoPersistencia docDpp = DocumentoPasoPersistencia.createDocumentoPersistencia();
				docDpp.setId(anexo.getId());
				docDpp.setTipo(TypeDocumentoPersistencia.ANEXO);
				docDpp.setEstado(TypeEstadoDocumento.SIN_RELLENAR);
				docDpp.setInstancia(ConstantesNumero.N1);
				getDao().establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docDpp);
				pDpp.getDocumentos().add(docDpp);
			}
		}
	}

	/**
	 * Regenera anexos que existen en persistencia.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param pDpp
	 *                            Datos persistencia
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 */
	private void regenerarAnexosExistentes(final DatosInternosPasoAnexar pDipa, final DatosPersistenciaPaso pDpp,
			final VariablesFlujo pVariablesFlujo) {
		// Recorremos documentos existentes en persistencia para actualizar
		// la informacion del documento en el detalle del paso
		for (final DocumentoPasoPersistencia docDpp : pDpp.getDocumentos()) {
			// Obtenemos anexo en detalle
			final Anexo docDpa = ((DetallePasoAnexar) pDipa.getDetallePaso()).getAnexo(docDpp.getId());
			// Si no existe en la lista de anexos o es dependiente no hay que
			// anexarlo
			if (docDpa == null || docDpa.getObligatorio() == TypeObligatoriedad.DEPENDIENTE) {
				// Borramos documento del paso
				eliminarDocumentoPersistencia(pDipa, docDpp, pVariablesFlujo);
			} else {
				// Comprobamos si se ha anexado anteriormente y actualizamos su
				// informacion
				if (docDpp.getEstado() != TypeEstadoDocumento.SIN_RELLENAR) {
					actualizarDocumentoExistente(pDipa, docDpp, pVariablesFlujo);
				}
			}
		}
	}

	/**
	 * Elimina documento existente en persistencia.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param docDpp
	 *                            Datos documento persistente
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 *
	 */
	private void eliminarDocumentoPersistencia(final DatosInternosPasoAnexar pDipa,
			final DocumentoPasoPersistencia docDpp, final VariablesFlujo pVariablesFlujo) {
		// Eliminamos documento en persistencia
		getDao().eliminarDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docDpp.getId(),
				docDpp.getInstancia());
		// Borramos ficheros asociados
		final List<ReferenciaFichero> refs = docDpp.obtenerReferenciasFicherosAnexo(true, true);
		for (final ReferenciaFichero ref : refs) {
			getDao().eliminarFicheroPersistencia(ref);
		}
	}

	/**
	 * Actualiza documento existente.
	 *
	 * @param pDipa
	 *                            Datos internos paso
	 * @param docDpp
	 *                            Datos documento persistente
	 * @param pVariablesFlujo
	 *                            Variables flujo
	 */
	private void actualizarDocumentoExistente(final DatosInternosPasoAnexar pDipa,
			final DocumentoPasoPersistencia docDpp, final VariablesFlujo pVariablesFlujo) {

		final Anexo docDpa = ((DetallePasoAnexar) pDipa.getDetallePaso()).getAnexo(docDpp.getId());

		// - Estado rellenado
		docDpa.setRellenado(docDpp.getEstado());

		boolean updateBD = false;
		final List<ReferenciaFichero> ficsPersistenciaBorrar = new ArrayList<>();

		if (docDpa.getPresentacion() == TypePresentacion.ELECTRONICA) {
			// - Fichero anexado y sus firmas
			final Fichero fichero = new Fichero();
			docDpa.getFicheros().add(fichero);
			fichero.setFichero(docDpp.getAnexoNombreFichero());
			fichero.setTitulo(docDpp.getAnexoDescripcionInstancia());

			// Eliminamos firmas sobrantes
			// - Recorremos firmas almacenadas y vemos si deben de estar
			// o no
			for (final FirmaDocumentoPersistencia fp : docDpp.obtenerFirmasFichero(docDpp.getFichero().getId())) {
				// Si no hay que firmar el documento o el firmante no
				// debe firmarlo eliminamos de persistencia firma y fichero
				// asociado
				if (docDpa.getFirmar() == TypeSiNo.NO || docDpa.esFirmante(fp.getNif()) == ConstantesNumero.N_1) {
					// Indicamos que se ha de actualizar el doc en BBDD
					updateBD = true;
					// Eliminamos firma
					docDpp.removeFirma(fp);
					// Marcamos el fichero para borrar
					if (fp.getFirma() != null) {
						ficsPersistenciaBorrar.add(fp.getFirma());
					}
				}
			}
		} else {
			// Presentacion presencial: hay que borrar todos los documentos
			// TODO PENDIENTE

		}

		// Miramos si hay que actualizar BD
		if (updateBD) {
			// Actualizamos documento
			getDao().establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), docDpp);
			// Borramos ficheros de firma marcados para borrar
			for (final ReferenciaFichero rf : ficsPersistenciaBorrar) {
				getDao().eliminarFicheroPersistencia(rf);
			}
		}
	}

	/**
	 * Calcula marcadores estado.
	 *
	 * @param pDipa
	 *                  Datos internos paso
	 * @return marcadores estado
	 */
	private EstadoMarcadores calcularMarcadoresEstado(final DatosInternosPasoAnexar pDipa) {
		final EstadoMarcadores marcadores = new EstadoMarcadores();

		// Recorremos anexos
		for (final Anexo a : ((DetallePasoAnexar) pDipa.getDetallePaso()).getAnexos()) {
			// Evaluamos en funcion de si se ha rellenado o no
			switch (a.getRellenado()) {
			case RELLENADO_CORRECTAMENTE:
				break;
			case SIN_RELLENAR:
				// Si es obligatorio comprobamos si lo debe anexar una tercera
				// persona a traves de la bandeja
				if (a.getObligatorio() == TypeObligatoriedad.OBLIGATORIO) {
					// Si no esta rellenado debe rellenarlo el iniciador
					marcadores.addMarcador(MARCADOR_PENDIENTE_ASISTENTE, true);
				}
				break;
			default:
				// El tipo RELLENADO_INCORRECTAMENTE no aplica a anexos
				throw new TipoNoControladoException(
						"Tipus emplenat " + a.getRellenado().name() + " no vàlid per annex");
			}

			// Si esta pendiente de asistente no hace falta seguir mirando
			if (marcadores.getMarcador(MARCADOR_PENDIENTE_ASISTENTE)) {
				break;
			}

		}
		return marcadores;
	}

	/**
	 * Añade los ficheros del anexo a la lista de documentos completados.
	 *
	 * @param pDpp
	 *                              Datos persistencia paso
	 * @param pDetalleAnexo
	 *                              Detalle anexo
	 * @param pListaCompletados
	 *                              Lista de documentos completados
	 */
	private void addFicherosAnexo(final DatosPersistenciaPaso pDpp, final Anexo pDetalleAnexo,
			final List<DatosDocumento> pListaCompletados) {

		int numInstancia = ConstantesNumero.N1;

		for (final Fichero fichero : pDetalleAnexo.getFicheros()) {
			final DatosDocumentoAnexo ddf = DatosDocumentoAnexo.createNewDatosDocumentoAnexo();
			ddf.setIdPaso(pDpp.getId());
			ddf.setId(pDetalleAnexo.getId());
			ddf.setInstancia(numInstancia);
			ddf.setPresentacion(pDetalleAnexo.getPresentacion());
			ddf.setFirmar(pDetalleAnexo.getFirmar());
			ddf.setFirmantes(pDetalleAnexo.getFirmantes());
			ddf.setTipoENI(pDetalleAnexo.getTipoENI());
			if (pDetalleAnexo.getMaxInstancias() > ConstantesNumero.N1) {
				ddf.setTitulo(fichero.getTitulo());
			} else {
				ddf.setTitulo(pDetalleAnexo.getTitulo());
			}
			final DocumentoPasoPersistencia docPers = pDpp.getDocumentoPasoPersistencia(pDetalleAnexo.getId(),
					numInstancia);
			final ReferenciaFichero refAnexo = ReferenciaFichero
					.createNewReferenciaFichero(docPers.getFichero().getId(), docPers.getFichero().getClave());
			ddf.setFichero(refAnexo);

			pListaCompletados.add(ddf);
			numInstancia++;
		}
	}

	/**
	 * Añade anexo presencial a la lista de documentos completados.
	 *
	 * @param pDpp
	 *                              Datos persistencia paso
	 * @param pDetalleAnexo
	 *                              Detalle anexo
	 * @param pListaCompletados
	 *                              Lista de documentos completados
	 */
	private void addAnexoPresencial(final DatosPersistenciaPaso pDpp, final Anexo pDetalleAnexo,
			final List<DatosDocumento> pListaCompletados) {
		final DatosDocumentoAnexo ddf = DatosDocumentoAnexo.createNewDatosDocumentoAnexo();
		ddf.setIdPaso(pDpp.getId());
		ddf.setId(pDetalleAnexo.getId());
		ddf.setInstancia(ConstantesNumero.N1);
		ddf.setPresentacion(pDetalleAnexo.getPresentacion());
		ddf.setTitulo(pDetalleAnexo.getTitulo());
		ddf.setFirmar(pDetalleAnexo.getFirmar());
		ddf.setFirmantes(pDetalleAnexo.getFirmantes());
		ddf.setTipoENI(pDetalleAnexo.getTipoENI());
		pListaCompletados.add(ddf);
	}

}
