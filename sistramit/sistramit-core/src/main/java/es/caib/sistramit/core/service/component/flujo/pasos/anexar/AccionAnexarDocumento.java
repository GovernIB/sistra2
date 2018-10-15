package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.documentconverter.ConversionDocumentException;
import org.fundaciobit.pluginsib.documentconverter.IDocumentConverterPlugin;
import org.fundaciobit.plugins.documentconverter.openoffice.OpenOfficeDocumentConverterPlugin;
import org.fundaciobit.pluginsib.documentconverter.InputDocumentNotSupportedException;
import org.fundaciobit.pluginsib.documentconverter.OutputDocumentNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.exception.AnexoVacioException;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ExtensionAnexoNoValidaException;
import es.caib.sistramit.core.api.exception.ParametrosEntradaIncorrectosException;
import es.caib.sistramit.core.api.exception.TamanyoMaximoAnexoException;
import es.caib.sistramit.core.api.exception.ValidacionAnexoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoAnexar;
import es.caib.sistramit.core.api.model.flujo.Fichero;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
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
 *
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

    @Override
    public RespuestaEjecutarAccionPaso ejecutarAccionPaso(
            final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
            final TypeAccionPaso pAccionPaso,
            final ParametrosAccionPaso pParametros,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {

        // Recogemos parametros
        String nombreFichero = (String) UtilsFlujo.recuperaParametroAccionPaso(
                pParametros, "nombreFichero", true);
        final String idAnexo = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "idAnexo", true);
        byte[] datosFichero = (byte[]) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "datosFichero", true);
        final String tituloInstancia = (String) UtilsFlujo
                .recuperaParametroAccionPaso(pParametros, "titulo", false); // titulo
                                                                            // genericos

        // Obtenemos datos internos paso anexar
        final DatosInternosPasoAnexar dipa = (DatosInternosPasoAnexar) pDatosPaso
                .internalData();

        // Normalizamos nombre fichero
        nombreFichero = XssFilter.normalizarFilename(nombreFichero);

        // Obtenemos info de detalle para el anexo
        final Anexo anexoDetalle = ((DetallePasoAnexar) dipa.getDetallePaso())
                .getAnexo(idAnexo);

        // Realizamos validaciones
        validarAnexo(dipa, anexoDetalle, nombreFichero, datosFichero,
                tituloInstancia, pDefinicionTramite, pVariablesFlujo);

        // Verificamos si el anexo se debe transformar el anexo (a PDF)
        final TransformacionAnexo transf = transformarAnexo(anexoDetalle,
                nombreFichero, datosFichero, pVariablesFlujo.isDebugEnabled());
        nombreFichero = transf.getNombreFichero();
        datosFichero = transf.getDatosFichero();

        // Actualizamos detalle
        actualizarDetalleAnexo(anexoDetalle, nombreFichero, tituloInstancia);

        // Actualizamos persistencia
        actualizarPersistencia(dipa, pDpp, anexoDetalle, nombreFichero,
                datosFichero, tituloInstancia, pVariablesFlujo);

        // Devolvemos respuesta vacia
        final RespuestaAccionPaso rp = new RespuestaAccionPaso();
        final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
        rp.addParametroRetorno("conversionPDF",
                Boolean.toString(transf.isConvertido()));
        rep.setRespuestaAccionPaso(rp);
        return rep;
    }

    /**
     * Transforma anexo (en caso de que este configurado).
     *
     * @param pAnexoDetalle
     *            Detalle anexo
     * @param pNombreFichero
     *            Nombre fichero
     * @param pDatosFichero
     *            Datos fichero
     * @param pDebugEnabled
     *            Debug enabled
     * @return Transformacion realizada (si no se realiza transformación se
     *         devuelven los datos originales).
     */
    private TransformacionAnexo transformarAnexo(final Anexo pAnexoDetalle,
            final String pNombreFichero, final byte[] pDatosFichero,
            final boolean pDebugEnabled) {

        final TransformacionAnexo res = new TransformacionAnexo();
        if (pAnexoDetalle.getConvertirPDF() == TypeSiNo.SI) {

            final IDocumentConverterPlugin plgDC = new OpenOfficeDocumentConverterPlugin();


            // Enviamos mail
            // final IEmailPlugin plgEmail = (IEmailPlugin)
            // configuracionComponent.obtenerPluginGlobal(TypePluginGlobal.EMAIL);

            // TODO Pendiente transformación PDF. Controlar si la extension se
            // puede transformar??

            final String extensionOrigen = FilenameUtils
                    .getExtension(pNombreFichero);
            final String extensionDestino= "pdf";

            OutputStream datosDestino = new ByteArrayOutputStream();
            InputStream datosOrigen =  new ByteArrayInputStream(pDatosFichero);

            try {
                plgDC.convertDocumentByExtension(datosOrigen, extensionOrigen, datosDestino,
                        extensionDestino);

               res.setConvertido(true);
               res.setNombreFichero(FilenameUtils.removeExtension(pNombreFichero)+".pdf");
               res.setDatosFichero(((ByteArrayOutputStream) datosDestino).toByteArray());
            } catch (InputDocumentNotSupportedException e) {
                e.printStackTrace();
            } catch (OutputDocumentNotSupportedException e) {
                e.printStackTrace();
            } catch (ConversionDocumentException e) {
                e.printStackTrace();
            }

            throw new RuntimeException("Pendiente transformacion PDF");
        } else {
            res.setNombreFichero(pNombreFichero);
            res.setDatosFichero(pDatosFichero);
            res.setConvertido(false);
        }
        return res;
    }

    /**
     * Actualiza el detalle del anexo.
     *
     * @param anexoDetalle
     *            Detalle anexo
     * @param nombreFichero
     *            Nombre fichero
     * @param titulo
     *            Título (para genericos)
     */
    private void actualizarDetalleAnexo(final Anexo anexoDetalle,
            final String nombreFichero, final String titulo) {
        anexoDetalle.setRellenado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
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

    /**
     * Realiza las validaciones al subir el anexo.
     *
     * @param dipa
     *            Datos internos paso
     * @param anexoDetalle
     *            Detalle anexo
     * @param nombreFichero
     *            Nombre fichero
     * @param datosFichero
     *            Datos fichero
     * @param titulo
     *            Título (para genericos)
     * @param pDefinicionTramite
     *            Definicion tramite
     * @param pVariablesFlujo
     *            Variables de flujo
     * @return
     */
    private void validarAnexo(final DatosInternosPasoAnexar dipa,
            final Anexo anexoDetalle, final String nombreFichero,
            final byte[] datosFichero, final String titulo,
            final DefinicionTramiteSTG pDefinicionTramite,
            final VariablesFlujo pVariablesFlujo) {

        // Comprobamos si el fichero anexado está vacío
        if (datosFichero.length == 0) {
            throw new AnexoVacioException("El fichero a anexar está vacío");
        }

        // Realizamos validaciones
        // - Parametro nombreFichero obligatorio para genericos
        if (anexoDetalle.getMaxInstancias() > ConstantesNumero.N1) {
            if (StringUtils.isEmpty(titulo)) {
                throw new ParametrosEntradaIncorrectosException(
                        "Falta especificar el título del documento");
            }
            if (!XssFilter.filtroXss(titulo)) {
                throw new ParametrosEntradaIncorrectosException(
                        "Titulo instancia contiene carácteres no permitidos");
            }
        }
        // - Verificamos si es generico y ha llegado al maximo de instancias
        if (anexoDetalle.getMaxInstancias() > ConstantesNumero.N1
                && anexoDetalle.getFicheros()
                        .size() == (anexoDetalle.getMaxInstancias())) {
            throw new AccionPasoNoPermitidaException(
                    "El limite de instancias para el anexo "
                            + anexoDetalle.getId() + " es "
                            + anexoDetalle.getMaxInstancias());
        }
        // - Verificar extensiones
        final String extensionFichero = FilenameUtils
                .getExtension(nombreFichero);
        if (anexoDetalle.getExtensiones() != null
                && (anexoDetalle.getExtensiones().toLowerCase() + ",")
                        .indexOf(extensionFichero.toLowerCase()
                                + ",") == ConstantesNumero.N_1) {
            throw new ExtensionAnexoNoValidaException("Extension '"
                    + extensionFichero + "' no permitida para anexo "
                    + anexoDetalle.getId());
        } else {
            if (StringUtils.isBlank(extensionFichero)) {
                throw new ExtensionAnexoNoValidaException(
                        "El fichero debe tener extensión para anexo "
                                + anexoDetalle.getId());
            }
        }
        // - Verificar tamaño maximo
        if (StringUtils.isBlank(anexoDetalle.getTamMax())) {
            throw new ErrorConfiguracionException(
                    "No se ha configurado el tamaño maximo para el anexo: "
                            + anexoDetalle.getId());
        }
        verificarTamanyoMaximo(anexoDetalle.getTamMax(), datosFichero.length);
        // - Verificar si debe anexarse firmado
        if (anexoDetalle.getAnexarfirmado() == TypeSiNo.SI) {
            // TODO Pendiente implementar
            throw new RuntimeException("Pendiente implementar");
        }

        // - Ejecutamos script de validacion de anexo
        final RPasoTramitacionAnexar defPaso = (RPasoTramitacionAnexar) UtilsSTG
                .devuelveDefinicionPaso(dipa.getIdPaso(), pDefinicionTramite);
        final RAnexoTramite defAnexo = UtilsSTG.devuelveDefinicionAnexo(defPaso,
                anexoDetalle.getId());
        if (defAnexo != null && UtilsSTG.existeScript(
                defAnexo.getPresentacionElectronica().getScriptValidacion())) {
            final RScript script = defAnexo.getPresentacionElectronica()
                    .getScriptValidacion();
            final Map<String, String> codigosError = UtilsSTG
                    .convertLiteralesToMap(script.getLiterales());
            final Map<String, Object> variablesScript = new HashMap<String, Object>();
            variablesScript.put("nombreFichero", nombreFichero);
            variablesScript.put("datosFichero", datosFichero);
            final RespuestaScript rs = this.scriptFlujo.executeScriptFlujo(
                    TypeScriptFlujo.SCRIPT_VALIDAR_ANEXO, anexoDetalle.getId(),
                    script.getScript(), pVariablesFlujo, variablesScript, null,
                    codigosError, pDefinicionTramite);
            if (rs.isError()) {
                // En caso de marcarse el error como script implica que no se ha
                // pasado la validación del anexo
                throw new ValidacionAnexoException(rs.getMensajeError(),
                        anexoDetalle.getId());
            }
        }

    }

    /**
     * Verifica el tamaño máximo. Genera una excepción en caso de que se
     * sobrepase.
     *
     * @param tamMax
     *            Tamaño máximo (con sufijo MB o KB)
     * @param numBytes
     *            Número de bytes del fichero
     */
    private void verificarTamanyoMaximo(final String tamMax,
            final int numBytes) {

        final String tam = tamMax.trim();

        int num = 0;
        try {
            final String numStr = tam
                    .substring(0, tam.length() - ConstantesNumero.N2).trim();
            num = Integer.parseInt(numStr);
        } catch (final NumberFormatException nfe) {
            throw new TamanyoMaximoAnexoException(
                    "No se ha podido verificar el tamaño maximo. La especificación de tamaño máximo no tiene un formato correcto: "
                            + tamMax,
                    nfe);
        }

        if (tam.endsWith("MB")) {
            num = num * ConstantesNumero.N1024 * ConstantesNumero.N1024;
        } else if (tam.endsWith("KB")) {
            num = num * ConstantesNumero.N1024;
        } else {
            throw new TamanyoMaximoAnexoException(
                    "No se ha podido verificar el tamaño maximo. La especificación de tamaño máximo no tiene un formato correcto: "
                            + tamMax);
        }

        if (numBytes > num) {
            throw new TamanyoMaximoAnexoException(
                    "Se ha sobrepasado el tamaño máximo");
        }

    }

    /**
     * Actualiza persistencia.
     *
     * @param pDipa
     *            Datos internos paso
     * @param pDpp
     *            Datos persistencia
     * @param pAnexoDetalle
     *            Detalle anexo
     * @param pNombreFichero
     *            Nombre fichero
     * @param pDatosFichero
     *            Datos fichero
     * @param ptituloInstancia
     *            titulo (para genericos)
     * @param pVariablesFlujo
     *            Variables flujo
     */
    private void actualizarPersistencia(final DatosInternosPasoAnexar pDipa,
            final DatosPersistenciaPaso pDpp, final Anexo pAnexoDetalle,
            final String pNombreFichero, final byte[] pDatosFichero,
            final String ptituloInstancia,
            final VariablesFlujo pVariablesFlujo) {

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
            doc = pDpp.getDocumentoPasoPersistencia(pAnexoDetalle.getId(),
                    ConstantesNumero.N1);
        }

        // Marcamos para borrar el fichero y firmas
        final List<ReferenciaFichero> ficherosBorrar = new ArrayList<>();
        ficherosBorrar.addAll(doc.obtenerReferenciasFicherosAnexo(true, true));

        // Insertamos nuevo fichero
        final ReferenciaFichero rfp = dao.insertarFicheroPersistencia(
                pNombreFichero, pDatosFichero,
                pVariablesFlujo.getIdSesionTramitacion());
        doc.setFichero(rfp);
        // Actualizamos el estado
        doc.setEstado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
        // Actualizamos el nombre del fichero
        doc.setAnexoNombreFichero(pNombreFichero);
        // Actualizamos titulo instancia
        doc.setAnexoDescripcionInstancia(ptituloInstancia);
        // Guardamos datos documento persistencia
        dao.establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(),
                pDipa.getIdPaso(), doc);
        // Eliminamos ficheros marcados para borrar (despues de actualizar
        // datos documento)
        for (final ReferenciaFichero ref : ficherosBorrar) {
            dao.eliminarFicheroPersistencia(ref);
        }

    }

}
