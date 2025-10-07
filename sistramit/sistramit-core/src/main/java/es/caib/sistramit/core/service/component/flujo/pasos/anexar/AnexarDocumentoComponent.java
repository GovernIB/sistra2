package es.caib.sistramit.core.service.component.flujo.pasos.anexar;

import es.caib.sistra2.commons.pdf.UtilPDF;
import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.ValidacionTipoException;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.*;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.flujo.Fichero;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.integracion.FirmaComponent;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.*;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.ValidacionFirmante;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Componente con lógica compartida del anexo de un documento (anexado y digitalización).
 */
@Component("anexarDocumentoComponent")
public class AnexarDocumentoComponent {

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

    /**
     * Actualiza detalle anexo cuando se anexa fichero.
     * @param anexoDetalle Anexo detalle
     * @param ficheroNombre Nombre del fichero
     * @param titulo Titulo del fichero (para instancias genéricas)
     * @param anexadoFirmado Indica si se ha anexado firmado
     */
    public void actualizarAnexoDetalle(Anexo anexoDetalle, String ficheroNombre, String titulo, TypeSiNo anexadoFirmado) {
        // Marcamos como rellenado
        anexoDetalle.setRellenado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
        // Si es electronico indicamos fichero
        if (anexoDetalle.getPresentacion() == TypePresentacion.ELECTRONICA) {
            // - Creamos fichero
            final Fichero fic = new Fichero();
            fic.setFichero(ficheroNombre);
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
            // - Indicamos si se ha anexado firmado
            anexoDetalle.setAnexadofirmado(anexadoFirmado);
        }
    }


    /**
     * Actualiza persistencia al anexar.
     * @param pDipa Datos internos paso anexar
     * @param pDpp Datos persistencia paso
     * @param pAnexoDetalle Anexo detalle
     * @param pNombreFichero Nombre del fichero
     * @param pDatosFichero Datos del fichero
     * @param ptituloInstancia Titulo de la instancia
     * @param pVariablesFlujo Variables de flujo
     */
    public void actualizarPersistenciaAnexar(DatosInternosPasoAnexar pDipa, DatosPersistenciaPaso pDpp, Anexo pAnexoDetalle, String pNombreFichero, byte[] pDatosFichero, String ptituloInstancia, VariablesFlujo pVariablesFlujo) {
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
        // Indicamos si se ha anexado firmado
        doc.setAnexoAnexadoFirmado(pAnexoDetalle.getAnexadofirmado());
        // Guardamos datos documento persistencia
        dao.establecerDatosDocumento(pVariablesFlujo.getIdSesionTramitacion(), pDipa.getIdPaso(), doc);
        // Eliminamos ficheros marcados para borrar (despues de actualizar
        // datos documento)
        for (final ReferenciaFichero ref : ficherosBorrar) {
            dao.eliminarFicheroPersistencia(ref);
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
     * @param tituloInstancia
     *                               Título (para genericos)
     * @param pDefinicionTramite
     *                               Definicion tramite
     * @param pVariablesFlujo
     *                               Variables de flujo
     * @param presentacion
     *                               presentacion
     * @param digitalizado         Indica si el anexo es digitalizado
     *
     * @return Resultado validación anexo
     */
    public ValidacionAnexo validarAnexo(final DatosInternosPasoAnexar dipa, final Anexo anexoDetalle,
                                         final TypePresentacion presentacion, final String nombreFichero, final byte[] datosFichero,
                                         final String tituloInstancia, final DefinicionTramiteSTG pDefinicionTramite,
                                         final VariablesFlujo pVariablesFlujo, final boolean digitalizado) {

        final ValidacionAnexo resultadoValidacion = new ValidacionAnexo();

        // Verificamos que coincide el tipo de presentacion
        if (anexoDetalle.getPresentacion() != presentacion) {
            throw new ParametrosEntradaIncorrectosException("No coincideix el tipus de presentació");
        }

        // Validaciones anexo electronico
        if (presentacion == TypePresentacion.ELECTRONICA) {

            // - Si no es genérico, no dejamos anexar sin haberlo borrado antes (desde asistente no se da opción)
            if (anexoDetalle.getRellenado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE &&
                  anexoDetalle.getMaxInstancias() == ConstantesNumero.N1 ) {
                throw new AccionPasoNoPermitidaException("No es pot annexar un fitxer sense haver esborrat l'anterior");
            }

            // - Validaciones anexo generico
            validacionesAnexoGenerico(anexoDetalle, tituloInstancia);

            // - Validar extensiones y tamaño
            validarExtensionTamanyo(dipa, pVariablesFlujo, anexoDetalle, datosFichero, nombreFichero);

            // Validaciones específicas según sea digitalizado o no
            if (digitalizado) {
                // - Validacion digitalizado: modo FH
                if (!pVariablesFlujo.isFuncionarioHabilitado()) {
                    throw new ParametrosEntradaIncorrectosException("Digitalització només permessa en mode funcionari habilitat");
                }
            } else {
                // Se valida protección por contraseña en cualquier caso
                validarProteccionPassword(anexoDetalle, datosFichero, nombreFichero);
                // Solo si se requiere firma (firma asistente o anexar firmado)
                if (anexoDetalle.getFirmar() == TypeSiNo.SI || anexoDetalle.getAnexarfirmado() == TypeSiNo.SI) {
                    // - Validaciones de anexo firmado
                    final boolean anexadoFirmado = validacionAnexoFirmado(pDefinicionTramite, pVariablesFlujo, anexoDetalle,
                            datosFichero, nombreFichero);
                    resultadoValidacion.setAnexadoFirmado(anexadoFirmado);
                }
                // - Validacion script
                validacionScriptValidacion(pDefinicionTramite, pVariablesFlujo, dipa, anexoDetalle, datosFichero,
                        nombreFichero);
            }

        }

        return resultadoValidacion;

    }

    /**
     * Valida si se puede anexar un PDF protegido por contraseña
     *
     * @param anexoDetalle Anexo detalle
     * @param datosFichero Datos fichero
     * @param nombreFichero Nombre fichero
     */
    private void validarProteccionPassword(Anexo anexoDetalle, byte[] datosFichero, String nombreFichero) {
        if (FilenameUtils.getExtension(nombreFichero).equalsIgnoreCase("PDF")) {
            try {
                if (UtilPDF.esProtegidoPwd(datosFichero)) {
                    throw new AnexarPdfProtegidoException();
                }
            } catch (Exception e) {
                throw new AnexarPdfNoVerificadoProtegidoException(e);
            }
        }
    }

    /**
     * Validaciones para anexo genérico.
     *
     * @param anexoDetalle
     *                            Anexo
     * @param tituloInstancia
     *                            titulo instancia
     */
    protected void validacionesAnexoGenerico(final Anexo anexoDetalle, final String tituloInstancia) {
        // - Parametro nombreFichero obligatorio para genericos
        if (anexoDetalle.getMaxInstancias() > ConstantesNumero.N1) {
            if (StringUtils.isEmpty(tituloInstancia)) {
                throw new ParametrosEntradaIncorrectosException("Falta especificar el títol del document");
            }
            if (!XssFilter.filtroXss(tituloInstancia)) {
                throw new ParametrosEntradaIncorrectosException("Títol instancia conté caràcters no permesos");
            }
        }
        // - Verificamos si es generico y ha llegado al maximo de instancias
        if (anexoDetalle.getMaxInstancias() > ConstantesNumero.N1
                && anexoDetalle.getFicheros().size() == (anexoDetalle.getMaxInstancias())) {
            throw new AccionPasoNoPermitidaException("El límit d'instancies per l'annex " + anexoDetalle.getId()
                    + " es " + anexoDetalle.getMaxInstancias());
        }
    }

    /**
     * Valida extensiones y tamaño
     *
     * @param pVariablesFlujo
     *                            Variables flujo
     * @param anexoDetalle
     *                            Anexo
     * @param datosFichero
     *                            Datos fichero
     *
     * @param dipa
     *                            Datos internos paso
     * @param anexoDetalle
     *                            Anexo
     * @param nombreFichero
     *                            Nombre fichero
     */
    protected void validarExtensionTamanyo(final DatosInternosPasoAnexar dipa, final VariablesFlujo pVariablesFlujo,
                                           final Anexo anexoDetalle, final byte[] datosFichero, final String nombreFichero) {

        // Comprobamos si el fichero anexado está vacío
        if (datosFichero.length == 0) {
            throw new AnexoVacioException("El fitxer a anexar està buit");
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
    }

    /**
     * Ejecuta script validación anexo
     *
     *
     * @param pDefinicionTramite
     *                               Definición trámite
     *
     * @param pVariablesFlujo
     *                               Variables flujo
     * @param anexoDetalle
     *                               Anexo
     * @param datosFichero
     *                               Datos fichero
     *
     * @param dipa
     *                               Datos internos paso
     * @param anexoDetalle
     *                               Anexo
     * @param nombreFichero
     *                               Nombre fichero
     */
    protected void validacionScriptValidacion(final DefinicionTramiteSTG pDefinicionTramite,
                                              final VariablesFlujo pVariablesFlujo, final DatosInternosPasoAnexar dipa, final Anexo anexoDetalle,
                                              final byte[] datosFichero, final String nombreFichero) {
        // Ejecutamos script de validacion de anexo (solo para no dinamicos)
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
                final RespuestaScript rs = scriptFlujo.executeScriptFlujo(TypeScriptFlujo.SCRIPT_VALIDAR_ANEXO,
                        anexoDetalle.getId(), script.getScript(), pVariablesFlujo, variablesScript, null, codigosError,
                        pDefinicionTramite);
            }
        }
    }

    /**
     * Realiza validaciones anexo firmado: en caso de que se haya anexado firmado se verifica firma.
     *
     * @param pDefinicionTramite
     *                               Definición trámite
     * @param pVariablesFlujo
     *                               Variables flujo
     * @param anexoDetalle
     *                               Anexo
     * @param datosFichero
     *                               Datos fichero
     * @param nombreFichero
     *                               Nombre fichero
     * @return true si se ha anexado firmado y se ha podido validar firma
     */
    protected boolean validacionAnexoFirmado(final DefinicionTramiteSTG pDefinicionTramite,
                                             final VariablesFlujo pVariablesFlujo, final Anexo anexoDetalle, final byte[] datosFichero,
                                             final String nombreFichero) {
        // Indicará si se ha anexado firmado
        boolean anexadoFirmado = false;

        // Extensión
        final String extensionFichero = FilenameUtils.getExtension(nombreFichero);

        // En caso de que se permite anexar firmado validamos si se ha anexado firmado
        if (anexoDetalle.getAnexarfirmado() == TypeSiNo.SI) {
            // Id entidad
            final String idEntidad = pDefinicionTramite.getDefinicionVersion().getIdEntidad();
            // Si es un anexo firmado, debe ser un PDF PADES
            anexadoFirmado = extensionFichero.equalsIgnoreCase("PDF") && esPades(datosFichero);
            // Verificamos si se debe anexar obligatoriamente firmado (no permite firma mediante asistente)
            if (!anexadoFirmado && anexoDetalle.getFirmar() == TypeSiNo.NO) {
                throw new AnexarFirmadoFirmaNoFirmadoException("Es obligatorio anexar firmado el anexo");
            }
            // Verificamos firma (y firmantes en caso necesario)
            if (anexadoFirmado) {
                final ValidacionFirmante vf = firmaComponent.validarFirmante(idEntidad, pVariablesFlujo.getIdioma(),
                        datosFichero, datosFichero, anexoDetalle.getFirmantes());
                if (!vf.isCorrecto()) {
                    throw new AnexarFirmadoFirmaIncorrectaException(
                            "La firma no es correcta o no ha sido firmada por todos los firmantes: "
                                    + vf.getDetalleError());
                }
            }
        }

        // En caso de que no se permita anexar firmado no puede ser un PADES
        if (anexoDetalle.getAnexarfirmado() == TypeSiNo.NO && extensionFichero.equalsIgnoreCase("PDF") && esPades(datosFichero) ) {
            throw new AnexarFirmadoFirmaNoPermitidaException("No se permite anexar firmado");
        }

        return anexadoFirmado;
    }

    /**
     * Verifica si es un PDF PADES.
     * @param datosFichero Datos del fichero
     * @return true si es un PDF PADES
     */
    private boolean esPades(byte[] datosFichero) {
        // Verificamos si es un PDF PADES
        try {
            final boolean padesLTV = BooleanUtils.toBoolean(configuracionComponent
                    .obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ANEXOS_ANEXOFIRMADO_LTV));
            return UtilPDF.esPades(datosFichero, padesLTV);
        } catch (Exception e) {
            throw new AnexarVerificarPadesException(e);
        }
    }

}
