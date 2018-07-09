package es.caib.sistramit.core.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.FormatoInvalidoFechaFrontException;
import es.caib.sistramit.core.api.exception.TramiteFinalizadoException;
import es.caib.sistramit.core.api.exception.UsuarioNoPermitidoException;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;

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
            res.setEmail(usuInfo.getEmail());
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
                || !usuarioAutenticadoInfo.getNif()
                        .equals(datosPersistenciaTramite.getNifIniciador())) {
            throw new UsuarioNoPermitidoException(
                    "Usuario no puede acceder al tramite",
                    usuarioAutenticadoInfo.getAutenticacion(),
                    usuarioAutenticadoInfo.getNif());
        }
    }

}
