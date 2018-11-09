package es.caib.sistramit.core.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistrages.rest.api.interna.RScript;
import es.caib.sistramit.core.api.exception.ErrorScriptException;
import es.caib.sistramit.core.api.exception.FormatoInvalidoFechaFrontException;
import es.caib.sistramit.core.api.exception.ParametrosEntradaIncorrectosException;
import es.caib.sistramit.core.api.exception.TramiteFinalizadoException;
import es.caib.sistramit.core.api.exception.UsuarioNoPermitidoException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.DetalleTramiteInfo;
import es.caib.sistramit.core.api.model.flujo.Entidad;
import es.caib.sistramit.core.api.model.flujo.EntidadRedesSociales;
import es.caib.sistramit.core.api.model.flujo.EntidadSoporte;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.SoporteOpcion;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.component.script.RespuestaScript;
import es.caib.sistramit.core.service.component.script.ScriptExec;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.script.types.TypeScriptFlujo;

/**
 * Clase de utilidades para flujo de tramitación.
 *
 * @author Indra
 *
 */
public final class UtilsFlujo {

    /** Formato fecha en una cadena según la maneja el front. */
    public static final String FORMATO_FECHA_FRONT = "dd/MM/yyyy HH:mm";

    /** Formato fecha generica (YYYYMMDDHHMISS). */
    public static final String FORMATO_FECHA_GENERICA = "yyyyMMddHHmmss";

    /**
     * Formatea fecha en una cadena según la maneja el front.
     *
     * @param fecha
     *            Fecha
     * @return Fecha formateada en dd/MM/yyyy hh:mm
     */
    public static String formateaFechaFront(final Date fecha) {
        return formateaFecha(fecha, FORMATO_FECHA_FRONT);
    }

    /**
     * Formatea fecha en una cadena
     *
     * @param fecha
     *            Fecha
     * @param formatoFecha
     *            formato fecha
     * @return Fecha formateada
     */
    public static String formateaFecha(final Date fecha,
            final String formatoFecha) {
        String res = null;
        if (fecha != null) {
            final SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
            res = sdf.format(fecha);
        }
        return res;
    }

    /**
     * Deformatea fecha pasada como una cadena según la maneja el front.
     *
     * @param fecha
     *            Fecha formateada en dd/MM/yyyy hh:mm
     * @return Fecha
     */
    public static Date deformateaFechaFront(final String fecha) {
        return deformateaFecha(fecha, FORMATO_FECHA_FRONT);
    }

    /**
     * Deformatea fecha de una cadena
     *
     * @param fecha
     *            Fecha
     * @param formatoFecha
     *            formato fecha
     * @return Fecha deformateada
     */
    public static Date deformateaFecha(final String fecha,
            final String formatoFecha) {
        Date res = null;
        if (fecha != null) {
            final SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
            try {
                res = sdf.parse(fecha);
            } catch (final ParseException e) {
                throw new FormatoInvalidoFechaFrontException(fecha, e);
            }
        }
        return res;
    }

    /**
     * Controla si la fecha esta dentro del plazo indicado.
     *
     * @param fecha
     *            Ahora
     * @param plazoInicio
     *            Plazo desactivacion inicio
     * @param plazoFin
     *            Plazo desactivacion fin
     * @return Indica si la fecha esta dentro del plazo (si las fechas de plazo
     *         son nulas se considera dentro del plazo).
     */
    public static boolean estaDentroPlazo(final Date fecha,
            final Date plazoInicio, final Date plazoFin) {
        boolean dentroPlazo = true;
        if (plazoInicio != null || plazoFin != null) {
            if (plazoInicio != null && plazoFin == null) {
                dentroPlazo = fecha.compareTo(plazoInicio) >= 0;
            } else if (plazoInicio == null && plazoFin != null) {
                dentroPlazo = fecha.compareTo(plazoFin) <= 0;
            } else {
                dentroPlazo = fecha.compareTo(plazoInicio) >= 0
                        && fecha.compareTo(plazoFin) <= 0;
            }
        }
        return dentroPlazo;
    }

    /**
     * Establece la hora a las 00:00:00 horas.
     *
     * @param fecha
     *            Fecha
     * @return Fecha establecida a primera hora
     **/
    public static Date obtenerPrimeraHora(final Date fecha) {
        Date respuesta = null;
        if (fecha != null) {
            final Calendar calendario2 = Calendar.getInstance();
            calendario2.setTime(fecha);
            calendario2.set(Calendar.HOUR_OF_DAY, 0);
            calendario2.set(Calendar.MINUTE, 0);
            calendario2.set(Calendar.SECOND, 0);
            calendario2.set(Calendar.MILLISECOND, 0);
            respuesta = calendario2.getTime();
        }
        return respuesta;
    }

    /**
     * Establece la hora a las 23:59:59 horas.
     *
     * @param fecha
     *            Fecha
     * @return Fecha establecida a primera hora
     **/
    public static Date obtenerUltimaHora(final Date fecha) {
        Date respuesta = null;
        if (fecha != null) {
            final Calendar calendario2 = Calendar.getInstance();
            calendario2.setTime(fecha);
            calendario2.set(Calendar.HOUR_OF_DAY, ConstantesNumero.N23);
            calendario2.set(Calendar.MINUTE, ConstantesNumero.N59);
            calendario2.set(Calendar.SECOND, ConstantesNumero.N59);
            calendario2.set(Calendar.MILLISECOND, 0);
            respuesta = calendario2.getTime();
        }
        return respuesta;
    }

    /**
     * Genera datos usuario a partir info autenticación.
     *
     * @param usuInfo
     *            info autenticación
     * @return datos usuario
     */
    public static DatosUsuario getDatosUsuario(UsuarioAutenticadoInfo usuInfo) {
        DatosUsuario res = null;
        if (usuInfo.getAutenticacion() == TypeAutenticacion.AUTENTICADO) {
            res = new DatosUsuario();
            res.setNif(usuInfo.getNif());
            res.setNombre(usuInfo.getNombre());
            res.setApellido1(usuInfo.getApellido1());
            res.setApellido2(usuInfo.getApellido2());
            res.setCorreo(usuInfo.getEmail());
        }
        return res;
    }

    /**
     * Verifica si el usuario puede cargar el tramite.
     *
     * @param datosPersistenciaTramite
     *            Datos persistencia tramite
     * @param usuarioAutenticadoInfo
     * @param recarga
     *            Indica si la carga viene de una recarga
     */
    public static void controlCargaTramite(
            final DatosPersistenciaTramite datosPersistenciaTramite,
            UsuarioAutenticadoInfo usuarioAutenticadoInfo,
            final boolean recarga) {

        // No dejamos cargar si se ha cancelado o purgado
        if (datosPersistenciaTramite.isCancelado()
                || datosPersistenciaTramite.isPurgado()
                || datosPersistenciaTramite.isMarcadoPurgar()
                || datosPersistenciaTramite
                        .isPurgaPendientePorPagoRealizado()) {
            throw new TramiteFinalizadoException(
                    datosPersistenciaTramite.getIdSesionTramitacion());
        }

        // Solo dejamos cargar un tramite finalizado tras una recarga
        if (!recarga && datosPersistenciaTramite
                .getEstado() == TypeEstadoTramite.FINALIZADO) {
            throw new TramiteFinalizadoException(
                    datosPersistenciaTramite.getIdSesionTramitacion());
        }

        // Controlamos si es el usuario autenticado es el iniciador
        if (usuarioAutenticadoInfo
                .getAutenticacion() != datosPersistenciaTramite
                        .getAutenticacion()
                || (usuarioAutenticadoInfo
                        .getAutenticacion() == TypeAutenticacion.AUTENTICADO
                        && !usuarioAutenticadoInfo.getNif().equals(
                                datosPersistenciaTramite.getNifIniciador()))) {
            throw new UsuarioNoPermitidoException(
                    "Usuario no puede acceder al tramite",
                    usuarioAutenticadoInfo.getAutenticacion(),
                    usuarioAutenticadoInfo.getNif());
        }
    }

    /**
     * Recupera datos entidad
     *
     * @param entidad
     *            entidad
     * @param idioma
     *            idioma
     * @param urlResources
     *            Url recursos
     * @return entidad
     */
    public static Entidad detalleTramiteEntidad(RConfiguracionEntidad entidad,
            String idioma, String urlResources) {

        final Entidad e = new Entidad();
        e.setId(entidad.getIdentificador());
        e.setNombre(UtilsSTG.obtenerLiteral(entidad.getDescripcion(), idioma));
        e.setLogo(urlResources + entidad.getLogo());
        if (StringUtils.isBlank(entidad.getCss())) {
            e.setCss("");
        } else {
            e.setCss(urlResources + entidad.getCss());
        }
        e.setContacto(
                UtilsSTG.obtenerLiteral(entidad.getContactoHTML(), idioma));
        e.setUrlCarpeta(
                UtilsSTG.obtenerLiteral(entidad.getUrlCarpeta(), idioma));
        e.setUrlMapaWeb(UtilsSTG.obtenerLiteral(entidad.getMapaWeb(), idioma));
        e.setUrlAvisoLegal(
                UtilsSTG.obtenerLiteral(entidad.getAvisoLegal(), idioma));
        e.setUrlRss(UtilsSTG.obtenerLiteral(entidad.getRss(), idioma));
        final EntidadRedesSociales redes = new EntidadRedesSociales();
        redes.setFacebook(entidad.getUrlFacebook());
        redes.setInstagram(entidad.getUrlInstagram());
        redes.setTwiter(entidad.getUrlTwitter());
        redes.setYoutube(entidad.getUrlYoutube());
        e.setRedes(redes);

        final List<SoporteOpcion> soporteOpciones = new ArrayList<>();
        if (entidad.getAyudaFormulario() != null
                && !entidad.getAyudaFormulario().isEmpty()) {
            for (final ROpcionFormularioSoporte ro : entidad
                    .getAyudaFormulario()) {
                final SoporteOpcion so = new SoporteOpcion();
                so.setCodigo(ro.getCodigo().toString());
                so.setTitulo(UtilsSTG.obtenerLiteral(ro.getTipo(), idioma));
                so.setDescripcion(
                        UtilsSTG.obtenerLiteral(ro.getDescripcion(), idioma));
                soporteOpciones.add(so);
            }
        }

        final EntidadSoporte soporte = new EntidadSoporte();
        soporte.setCorreo(entidad.getEmail());
        soporte.setTelefono(entidad.getAyudaTelefono());
        soporte.setUrl(entidad.getAyudaUrl());
        soporte.setProblemas(soporteOpciones);
        e.setSoporte(soporte);

        return e;
    }

    /**
     * Detalle tramite info.
     *
     * @param pDatosSesion
     *            datos sesion
     * @return Detalle tramite info.
     */
    public static DetalleTramiteInfo detalleTramiteInfo(
            final DatosSesionTramitacion pDatosSesion) {
        final DetalleTramiteInfo detalleTramiteInfo = new DetalleTramiteInfo();
        detalleTramiteInfo.setIdSesion(
                pDatosSesion.getDatosTramite().getIdSesionTramitacion());
        detalleTramiteInfo.setId(pDatosSesion.getDatosTramite().getIdTramite());
        detalleTramiteInfo
                .setVersion(pDatosSesion.getDatosTramite().getVersionTramite());
        // Indicamos si el tramite esta recien abierto (en el primer paso)
        if (esTramiteNuevo(pDatosSesion)) {
            detalleTramiteInfo.setNuevo(TypeSiNo.SI);
        } else {
            detalleTramiteInfo.setNuevo(TypeSiNo.NO);
        }
        detalleTramiteInfo
                .setIdioma(pDatosSesion.getDatosTramite().getIdioma());
        detalleTramiteInfo
                .setTitulo(pDatosSesion.getDatosTramite().getTituloTramite());
        if (pDatosSesion.getDefinicionTramite().getDefinicionVersion()
                .getPropiedades().isPersistente()) {
            detalleTramiteInfo.setPersistente(TypeSiNo.SI);
            detalleTramiteInfo.setDiasPersistencia(
                    pDatosSesion.getDefinicionTramite().getDefinicionVersion()
                            .getPropiedades().getDiasPersistencia());
        } else {
            detalleTramiteInfo.setPersistente(TypeSiNo.NO);
        }
        detalleTramiteInfo
                .setTipoFlujo(pDatosSesion.getDatosTramite().getTipoFlujo());
        detalleTramiteInfo.setAutenticacion(
                pDatosSesion.getDatosTramite().getNivelAutenticacion());
        detalleTramiteInfo.setMetodoAutenticacion(
                pDatosSesion.getDatosTramite().getMetodoAutenticacionInicio());
        return detalleTramiteInfo;
    }

    /**
     * Genera detalle tramite.
     *
     * @param pDatosSesion
     *            Datos sesion
     * @param entidadInfo
     *            entidad info
     * @param urlResources
     *            Url recursos
     * @return detalle tramite
     */
    public static DetalleTramite detalleTramite(
            final DatosSesionTramitacion pDatosSesion,
            final RConfiguracionEntidad entidadInfo, String urlResources) {
        final DetalleTramite detalleTramite = new DetalleTramite();
        detalleTramite.setFechaDefinicion(UtilsFlujo.formateaFechaFront(
                pDatosSesion.getDefinicionTramite().getFechaRecuperacion()));
        detalleTramite.setDebug(TypeSiNo.fromBoolean(
                UtilsSTG.isDebugEnabled(pDatosSesion.getDefinicionTramite())));
        detalleTramite.setEntorno(pDatosSesion.getDatosTramite().getEntorno());
        detalleTramite.setIdPasoActual(
                pDatosSesion.getDatosTramite().getIdPasoActual());
        detalleTramite.setTramite(detalleTramiteInfo(pDatosSesion));
        detalleTramite
                .setUsuario(pDatosSesion.getDatosTramite().getIniciador());
        detalleTramite.setEntidad(detalleTramiteEntidad(entidadInfo,
                pDatosSesion.getDatosTramite().getIdioma(), urlResources));
        return detalleTramite;
    }

    /**
     * Ejecuta script de dependencia de un documento.
     *
     * @param script
     *            Script
     * @param idDocumento
     *            Id documento
     * @param pVariablesFlujo
     *            Variables flujo
     * @param pFormulariosCompletadosPaso
     *            Formularios anteriores completados en el paso actual
     * @param scriptFlujo
     *            Engine script
     * @param pDefinicionTramite
     *            Definicion tramite
     *
     * @return Obligatoriedad
     */
    public static TypeObligatoriedad ejecutarScriptDependenciaDocumento(
            final RScript script, final String idDocumento,
            final VariablesFlujo pVariablesFlujo,
            final List<DatosDocumento> pFormulariosCompletadosPaso,
            final ScriptExec scriptFlujo,
            final DefinicionTramiteSTG pDefinicionTramite) {

        // Si es dependiente ejecutamos script de dependencia
        final Map<String, String> codigosError = UtilsSTG
                .convertLiteralesToMap(script.getLiterales());

        final RespuestaScript rs = scriptFlujo.executeScriptFlujo(
                TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO, idDocumento,
                script.getScript(), pVariablesFlujo, null,
                pFormulariosCompletadosPaso, codigosError, pDefinicionTramite);
        if (rs.isError()) {
            throw new ErrorScriptException(
                    TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO.name(),
                    pVariablesFlujo.getIdSesionTramitacion(), idDocumento,
                    rs.getMensajeError());
        }

        // El resultado nos tiene que devolver:
        // s (obligatorio)/n(opcional)/d(no tienes que rellenarlo)
        String dependencia = ((String) rs.getResultado());
        if (StringUtils.isBlank(dependencia)) {
            throw new ErrorScriptException(
                    TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO.name(),
                    pVariablesFlujo.getIdSesionTramitacion(), idDocumento,
                    "El script de dependencia no ha devuelto valor");
        }
        dependencia = dependencia.toLowerCase();
        TypeObligatoriedad obligatorio;
        if (TypeObligatoriedad.OBLIGATORIO.toString().equals(dependencia)) {
            obligatorio = TypeObligatoriedad.OBLIGATORIO;
        } else if (TypeObligatoriedad.OPCIONAL.toString().equals(dependencia)) {
            obligatorio = TypeObligatoriedad.OPCIONAL;
        } else if (TypeObligatoriedad.DEPENDIENTE.toString()
                .equals(dependencia)) {
            obligatorio = TypeObligatoriedad.DEPENDIENTE;
        } else {
            throw new ErrorScriptException(
                    TypeScriptFlujo.SCRIPT_DEPENDENCIA_DOCUMENTO.name(),
                    pVariablesFlujo.getIdSesionTramitacion(), idDocumento,
                    "El script de dependencia no ha devuelto un valor válido: "
                            + dependencia);
        }

        return obligatorio;
    }

    /**
     * Método para recuperar un parámetro de una acción de un paso.
     *
     * @param pParametros
     *            Parametros acción
     * @param pParametro
     *            Nombre parámetro
     * @param pObligatorio
     *            Obligatorio
     * @return Valor parámetro
     */
    public static Object recuperaParametroAccionPaso(
            final ParametrosAccionPaso pParametros, final String pParametro,
            final boolean pObligatorio) {
        final Object valor = pParametros.getParametroEntrada(pParametro);
        if (pObligatorio && ((valor == null) || ((valor instanceof String)
                && StringUtils.isEmpty((String) valor)))) {
            throw new ParametrosEntradaIncorrectosException(
                    "Falta especificar parametro " + pParametro);
        }
        return valor;
    }

    /**
     * Convierte instancia pasada como string a int.
     *
     * @param instanciaStr
     *            Instancia como string
     * @return Devuelve entero
     */
    public static int instanciaStrToInt(final String instanciaStr) {
        int instancia = ConstantesNumero.N1;
        if (instanciaStr != null) {
            try {
                instancia = Integer.parseInt(instanciaStr);
            } catch (final NumberFormatException nfe) {
                throw new ParametrosEntradaIncorrectosException(
                        "El parametro instancia no es un numero", nfe);
            }
        }
        return instancia;
    }

    /**
     * Indicamos si el tramite esta recien abierto (en el primer paso).
     *
     * @param pDatosSesion
     *            Datos sesion
     * @return indica si es nuevo
     */
    private static boolean esTramiteNuevo(
            final DatosSesionTramitacion pDatosSesion) {
        boolean nuevo = false;
        // Miramos si estamos en el primer paso
        if (pDatosSesion.getDatosTramite().getIndiceDatosPasoActual() == 0) {
            // Si no hay mas pasos, consideramos que es nuevo
            if (pDatosSesion.getDatosTramite().getDatosPasos()
                    .size() == ConstantesNumero.N1) {
                nuevo = true;
            } else {
                // Si hay mas pasos y el siguiente no esta inicializado,
                // consideramos que es nuevo
                if (pDatosSesion.getDatosTramite().getDatosPasos()
                        .get(ConstantesNumero.N1)
                        .getEstado() == TypeEstadoPaso.NO_INICIALIZADO) {
                    nuevo = true;
                }
            }
        }
        return nuevo;
    }

}
