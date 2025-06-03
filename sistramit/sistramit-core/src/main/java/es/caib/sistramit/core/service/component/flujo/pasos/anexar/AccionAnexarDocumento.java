package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import es.caib.sistra2.commons.pdf.UtilPDF;
import es.caib.sistramit.core.api.exception.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoAnexar;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.FirmaComponent;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoAnexar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.TransformacionAnexo;
import es.caib.sistramit.core.service.model.flujo.ValidacionAnexo;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.ValidacionFirmante;
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

	/** DAO flujo paso. */
	@Autowired
	private FlujoPasoDao dao;

	/** Motor de ejecución de scritps. */
	@Autowired
	private ScriptExec scriptFlujo;

	/** Firma. */
	@Autowired
	private FirmaComponent firmaComponent;

	@Autowired
	private ConfiguracionComponent configuracionComponent;


	/** Componente con lógica común de anexar. */
	@Autowired
	private AnexarDocumentoComponent anexarDocumentoComponent;

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
		String tituloInstancia = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "titulo", false); // titulo
		// genericos

		// No permitido en modo FH
		if (pVariablesFlujo.isFuncionarioHabilitado()) {
			throw new AccionPasoNoPermitidaException("No se permite acción anexar en modo FH");
		}

		// TODO LIMITAR TAMBIEN EN HTML
		if (StringUtils.isNotBlank(tituloInstancia) && StringUtils.length(tituloInstancia) > 100) {
			tituloInstancia = tituloInstancia.substring(0, 100);
		}

		// Obtenemos datos internos paso anexar
		final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso.internalData();

		// Normalizamos nombre fichero
		if (presentacion == TypePresentacion.ELECTRONICA) {
			nombreFichero = XssFilter.normalizarFilename(nombreFichero);
		}

		// Obtenemos info de detalle para el anexo
		final Anexo anexoDetalle = ((DetallePasoAnexar) dipa.getDetallePaso()).getAnexo(idAnexo);

		// Realizamos validaciones
		final ValidacionAnexo resValidacion = anexarDocumentoComponent.validarAnexo(dipa, anexoDetalle, presentacion, nombreFichero,
				datosFichero, tituloInstancia, pDefinicionTramite, pVariablesFlujo, false);

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
		anexarDocumentoComponent.actualizarAnexoDetalle(anexoDetalle, nombreFichero, tituloInstancia, TypeSiNo.fromBoolean(resValidacion.isAnexadoFirmado()));

		// Actualizamos persistencia
		anexarDocumentoComponent.actualizarPersistenciaAnexar(dipa, pDpp, anexoDetalle, nombreFichero, datosFichero, tituloInstancia, pVariablesFlujo);

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




}
