package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.documentconverter.IDocumentConverterPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.ValidacionTipoException;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.AnexoVacioException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ExtensionAnexoNoValidaException;
import es.caib.sistramit.core.api.exception.ParametrosEntradaIncorrectosException;
import es.caib.sistramit.core.api.exception.TamanyoMaximoAnexosAlcanzadoException;
import es.caib.sistramit.core.api.exception.TransformacionPdfException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoAnexar;
import es.caib.sistramit.core.api.model.flujo.Fichero;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoAnexar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.TransformacionAnexo;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que permite descargar una plantilla en el paso Anexar.
 *
 * @author Indra
 */
@Component("accionAdAnexarDocumento")
public final class AccionAnexarDocumento implements AccionPaso {

	/**
	 * Atributo dao.
	 */
	@Autowired
	private FlujoPasoDao dao;
	/**
	 * Motor de ejecución de scritps.
	 */
	@Autowired
	private ScriptExec scriptFlujo;

	/**
	 * Configuracion.
	 */
	// @Autowired
	// private SystemService systemService;

	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Recogemos parametros
		final String idAnexo = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "idAnexo", true);
		final TypePresentacion presentacion = (TypePresentacion) UtilsFlujo.recuperaParametroAccionPaso(pParametros,
				"presentacion", true);
		String nombreFichero = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "nombreFichero", false);
		byte[] datosFichero = (byte[]) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "datosFichero", false);
		final String tituloInstancia = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "titulo", false); // titulo
		// genericos

		// Obtenemos datos internos paso anexar
		final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso.internalData();

		// Normalizamos nombre fichero
		if (presentacion == TypePresentacion.ELECTRONICA) {
			nombreFichero = XssFilter.normalizarFilename(nombreFichero);
		}

		// Obtenemos info de detalle para el anexo
		final Anexo anexoDetalle = ((DetallePasoAnexar) dipa.getDetallePaso()).getAnexo(idAnexo);

		// Realizamos validaciones
		validarAnexo(dipa, anexoDetalle, presentacion, nombreFichero, datosFichero, tituloInstancia, pDefinicionTramite,
				pVariablesFlujo);

		// Verificamos si el anexo se debe transformar el anexo (a PDF)
		boolean conversionPDF = false;
		if (presentacion == TypePresentacion.ELECTRONICA) {
			final TransformacionAnexo transf = transformarAnexo(anexoDetalle, nombreFichero, datosFichero,
					pVariablesFlujo.isDebugEnabled());
			nombreFichero = transf.getNombreFichero();
			datosFichero = transf.getDatosFichero();
			conversionPDF = transf.isConvertido();
		}

		// Actualizamos detalle
		actualizarDetalleAnexo(anexoDetalle, nombreFichero, tituloInstancia);

		// Actualizamos persistencia
		actualizarPersistencia(dipa, pDpp, anexoDetalle, nombreFichero, datosFichero, tituloInstancia, pVariablesFlujo);

		// Devolvemos respuesta vacia
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rp.addParametroRetorno("conversionPDF", Boolean.toString(conversionPDF));
		rep.setRespuestaAccionPaso(rp);
		return rep;
	}

	/**
	 * Transforma anexo (en caso de que este configurado).
	 *
	 * @param pAnexoDetalle
	 *                           Detalle anexo
	 * @param pNombreFichero
	 *                           Nombre fichero
	 * @param pDatosFichero
	 *                           Datos fichero
	 * @param pDebugEnabled
	 *                           Debug enabled
	 * @return Transformacion realizada (si no se realiza transformación se
	 *         devuelven los datos originales).
	 */
	private TransformacionAnexo transformarAnexo(final Anexo pAnexoDetalle, final String pNombreFichero,
			final byte[] pDatosFichero, final boolean pDebugEnabled) {
		final TransformacionAnexo res = new TransformacionAnexo();
		res.setNombreFichero(pNombreFichero);
		res.setDatosFichero(pDatosFichero);
		res.setConvertido(false);
		if (pAnexoDetalle.getConvertirPDF() == TypeSiNo.SI) {
			try {
				final IDocumentConverterPlugin plgDC = (IDocumentConverterPlugin) configuracionComponent
						.obtenerPluginGlobal(TypePluginGlobal.CONVERSION_PDF);

				final String extensionOrigen = FilenameUtils.getExtension(pNombreFichero);
				final String extensionDestino = "pdf";

				// Si es pdf, no hay que convertir
				if (!StringUtils.equalsIgnoreCase(extensionOrigen, "pdf")) {
					final OutputStream datosDestino = new ByteArrayOutputStream();
					final InputStream datosOrigen = new ByteArrayInputStream(pDatosFichero);

					plgDC.convertDocumentByExtension(datosOrigen, extensionOrigen, datosDestino, extensionDestino);

					res.setConvertido(true);
					res.setNombreFichero(FilenameUtils.removeExtension(pNombreFichero) + ".pdf");
					res.setDatosFichero(((ByteArrayOutputStream) datosDestino).toByteArray());
				}
			} catch (final Exception e) {
				throw new TransformacionPdfException("Error al transformar a PDF" + e.getMessage());
			}
		}
		return res;
	}

	/**
	 * Actualiza el detalle del anexo.
	 *
	 * @param anexoDetalle
	 *                          Detalle anexo
	 * @param nombreFichero
	 *                          Nombre fichero
	 * @param titulo
	 *                          Título (para genericos)
	 */
	private void actualizarDetalleAnexo(final Anexo anexoDetalle, final String nombreFichero, final String titulo) {
		// Marcamos como rellenado
		anexoDetalle.setRellenado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
		// Si es electronico indicamos fichero
		if (anexoDetalle.getPresentacion() == TypePresentacion.ELECTRONICA) {
			// - Creamos fichero
			final Fichero fic = new Fichero();
			fic.setFichero(nombreFichero);
			fic.setTitulo(titulo);
			// - Añadimos / reemplazamos fichero segun sea generico o no
			if (anexoDetalle.getMaxInstancias() > ConstantesNumero.N1) {
				// Es generico, añadimos
				anexoDetalle.getFicheros().add(fic);
			} else {
				// No es generico, reemplazamos
				if (!anexoDetalle.getFicheros().isEmpty()) {
					anexoDetalle.borrarFichero(ConstantesNumero.N1);
				}
				anexoDetalle.getFicheros().add(fic);
			}
		}
	}

	/**
	 * Realiza las validaciones al subir el anexo.
	 *
	 * @param dipa
	 *                               Datos internos paso
	 * @param anexoDetalle
	 *                               Detalle anexo
	 * @param presentacion
	 * @param nombreFichero
	 *                               Nombre fichero
	 * @param datosFichero
	 *                               Datos fichero
	 * @param titulo
	 *                               Título (para genericos)
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pVariablesFlujo
	 *                               Variables de flujo
	 * @param presentacion
	 *                               presentacion
	 * @return
	 */
	private void validarAnexo(final DatosInternosPasoAnexar dipa, final Anexo anexoDetalle,
			final TypePresentacion presentacion, final String nombreFichero, final byte[] datosFichero,
			final String titulo, final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Verificamos que coincide el tipo de presentacion
		if (anexoDetalle.getPresentacion() != presentacion) {
			throw new ParametrosEntradaIncorrectosException("No coincideix el tipus de presentació");
		}

		// Validaciones anexo electronico
		if (presentacion == TypePresentacion.ELECTRONICA) {
			// Comprobamos si el fichero anexado está vacío
			if (datosFichero.length == 0) {
				throw new AnexoVacioException("El fitxer a anexar està buit");
			}

			// Realizamos validaciones
			// - Parametro nombreFichero obligatorio para genericos
			if (anexoDetalle.getMaxInstancias() > ConstantesNumero.N1) {
				if (StringUtils.isEmpty(titulo)) {
					throw new ParametrosEntradaIncorrectosException("Falta especificar el títol del document");
				}
				if (!XssFilter.filtroXss(titulo)) {
					throw new ParametrosEntradaIncorrectosException(
							"Títol instancia conté caràcters no permesos");
				}
			}
			// - Verificamos si es generico y ha llegado al maximo de instancias
			if (anexoDetalle.getMaxInstancias() > ConstantesNumero.N1
					&& anexoDetalle.getFicheros().size() == (anexoDetalle.getMaxInstancias())) {
				throw new AccionPasoNoPermitidaException("El límit d'instancies per l'annex " + anexoDetalle.getId()
						+ " es " + anexoDetalle.getMaxInstancias());
			}
			// - Verificar extensiones
			final String extensionFichero = FilenameUtils.getExtension(nombreFichero);
			if (anexoDetalle.getExtensiones() != null && (anexoDetalle.getExtensiones().toLowerCase() + ",")
					.indexOf(extensionFichero.toLowerCase() + ",") == ConstantesNumero.N_1) {
				throw new ExtensionAnexoNoValidaException(
						"Extensió '" + extensionFichero + "' no permesa per annex " + anexoDetalle.getId());
			} else {
				if (StringUtils.isBlank(extensionFichero)) {
					throw new ExtensionAnexoNoValidaException(
							"El fitxer ha de tenir extensió per a annex " + anexoDetalle.getId());
				}
			}
			// - Verificar tamaño maximo individual anexo
			if (StringUtils.isBlank(anexoDetalle.getTamMax())) {
				throw new ErrorConfiguracionException(
						"No s'ha configurat la mida màxima per l'annex: " + anexoDetalle.getId());
			}
			UtilsFlujo.verificarTamanyoMaximo(anexoDetalle.getTamMax(), datosFichero.length);

			// - Verificar tamaño máximo total anexos
			final String tamanyoTotalAnexosPropStr = configuracionComponent
					.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ANEXOS_TAMANYO_TOTAL);
			int tamanyoTotalAnexosPropBytes = 0;
			try {
				tamanyoTotalAnexosPropBytes = ValidacionesTipo.getInstance()
						.convertirTamanyoBytes(tamanyoTotalAnexosPropStr);
			} catch (final ValidacionTipoException e) {
				throw new ErrorConfiguracionException(
						"Error al interpretar propietat " + TypePropiedadConfiguracion.ANEXOS_TAMANYO_TOTAL.toString());
			}

			if (tamanyoTotalAnexosPropBytes > 0) {
				final long tamanyo = dao.calcularTamañoFicherosPaso(pVariablesFlujo.getIdSesionTramitacion(),
						dipa.getIdPaso(), false);
				final long tamanyoTotal = tamanyo + datosFichero.length;
				if (tamanyoTotal > tamanyoTotalAnexosPropBytes) {
					throw new TamanyoMaximoAnexosAlcanzadoException(tamanyoTotalAnexosPropStr);
				}
			}

			// - Verificar si debe anexarse firmado
			if (anexoDetalle.getAnexarfirmado() == TypeSiNo.SI) {
				// TODO Pendiente implementar
				throw new RuntimeException("Per implementar");
			}

			// - Ejecutamos script de validacion de anexo (solo para no dinamicos)
			if (anexoDetalle.getDinamico() == TypeSiNo.NO) {
				final RPasoTramitacionAnexar defPaso = (RPasoTramitacionAnexar) UtilsSTG
						.devuelveDefinicionPaso(dipa.getIdPaso(), pDefinicionTramite);
				final RAnexoTramite defAnexo = UtilsSTG.devuelveDefinicionAnexo(defPaso, anexoDetalle.getId());
				if (defAnexo != null
						&& UtilsSTG.existeScript(defAnexo.getPresentacionElectronica().getScriptValidacion())) {
					final RScript script = defAnexo.getPresentacionElectronica().getScriptValidacion();
					final Map<String, String> codigosError = UtilsSTG.convertLiteralesToMap(script.getLiterales());
					final Map<String, Object> variablesScript = new HashMap<String, Object>();
					variablesScript.put("nombreFichero", nombreFichero);
					variablesScript.put("datosFichero", datosFichero);
					final RespuestaScript rs = this.scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_VALIDAR_ANEXO,
							anexoDetalle.getId(), script.getScript(), pVariablesFlujo, variablesScript, null,
							codigosError, pDefinicionTramite);
				}
			}

		}

	}

	/**
	 * Actualiza persistencia.
	 *
	 * @param pDipa
	 *                             Datos internos paso
	 * @param pDpp
	 *                             Datos persistencia
	 * @param pAnexoDetalle
	 *                             Detalle anexo
	 * @param pNombreFichero
	 *                             Nombre fichero
	 * @param pDatosFichero
	 *                             Datos fichero
	 * @param ptituloInstancia
	 *                             titulo (para genericos)
	 * @param pVariablesFlujo
	 *                             Variables flujo
	 */
	private void actualizarPersistencia(final DatosInternosPasoAnexar pDipa, final DatosPersistenciaPaso pDpp,
			final Anexo pAnexoDetalle, final String pNombreFichero, final byte[] pDatosFichero,
			final String ptituloInstancia, final VariablesFlujo pVariablesFlujo) {

		DocumentoPasoPersistencia doc;

		// Si es genérico y hay más de una instancia añadimos nuevo documento
		if (pAnexoDetalle.getMaxInstancias() > ConstantesNumero.N1
				&& pAnexoDetalle.getFicheros().size() > ConstantesNumero.N1) {
			doc = new DocumentoPasoPersistencia();
			doc.setId(pAnexoDetalle.getId());
			doc.setTipo(TypeDocumentoPersistencia.ANEXO);
			doc.setInstancia(pAnexoDetalle.getFicheros().size());
			// Añadimos a datos persistencia (insertamos detras de la ultima
			// instancia)
			int index = 0;
			boolean enc = false;
			boolean medio = false;
			for (final DocumentoPasoPersistencia d : pDpp.getDocumentos()) {
				if (d.getId().equals(pAnexoDetalle.getId())) {
					enc = true;
				}
				if (enc && !d.getId().equals(pAnexoDetalle.getId())) {
					medio = true;
					break;
				}
				index++;
			}
			if (!medio) {
				pDpp.getDocumentos().add(doc);
			} else {
				pDpp.getDocumentos().add(index, doc);
			}

		} else {
			// Si no, actualizamos la existente
			doc = pDpp.getDocumentoPasoPersistencia(pAnexoDetalle.getId(), ConstantesNumero.N1);
		}

		// Marcamos para borrar el fichero y firmas
		final List<ReferenciaFichero> ficherosBorrar = new ArrayList<>();
		ficherosBorrar.addAll(doc.obtenerReferenciasFicherosAnexo(true, true));

		// Insertamos nuevo fichero
		if (pAnexoDetalle.getPresentacion() == TypePresentacion.ELECTRONICA) {
			final ReferenciaFichero rfp = dao.insertarFicheroPersistencia(pNombreFichero, pDatosFichero,
					pVariablesFlujo.getIdSesionTramitacion());
			doc.setFichero(rfp);
		}
		// Actualizamos el estado
		doc.setEstado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
		// Actualizamos el nombre del fichero
		doc.setAnexoNombreFichero(pNombreFichero);
		// Actualizamos titulo instancia
		doc.setAnexoDescripcionInstancia(ptituloInstancia);
		// Guardamos datos documento persistencia
		dao.establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), doc);
		// Eliminamos ficheros marcados para borrar (despues de actualizar
		// datos documento)
		for (final ReferenciaFichero ref : ficherosBorrar) {
			dao.eliminarFicheroPersistencia(ref);
		}

	}

}
