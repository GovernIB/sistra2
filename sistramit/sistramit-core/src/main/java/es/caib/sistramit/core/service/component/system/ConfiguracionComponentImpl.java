package es.caib.sistramit.core.service.component.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.exception.CargaConfiguracionException;
import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.model.system.ConstantesConfiguracion;
import es.caib.sistramit.core.service.repository.dao.InvalidacionDao;

@Component("configuracionComponent")
public class ConfiguracionComponentImpl implements ConfiguracionComponent {

    /** Propiedades configuración. */
    private Map<TypePropiedadConfiguracion, String> propiedades;

    /** Fecha ultima recuperacion. */
    private Date fcRecuperacion = null;

    /** Fecha ultima revision invalidaciones. */
    private Date fcRevisionInvalidaciones = null;

    /** Componente STG. */
    @Autowired
    private SistragesComponent sistragesComponent;

    /** Invalidacion DAO. */
    @Autowired
    private InvalidacionDao invalidacionDao;

    @Override
    public String obtenerPropiedadConfiguracion(
            TypePropiedadConfiguracion propiedad) {
        if (necesitaRefrescarConfiguracion()) {
            recuperarConfiguracion();
        }
        return propiedades.get(propiedad);
    }

    // ----------------------------------------------------------------------
    // FUNCIONES PRIVADAS
    // ----------------------------------------------------------------------

    /**
     * Refresca configuracion.
     */
    private synchronized void recuperarConfiguracion() {
        // Recuperamos propiedades del properties
        final Properties propsProperties = recuperarConfiguracionProperties();
        // Recuperamos propiedades de SISTRAGES
        final Properties propsSTG = sistragesComponent
                .recuperarPropiedadesGlobales();

        // Carga propiedades
        propiedades = new HashMap<>();
        cargaPropiedades(propsSTG);
        cargaPropiedades(propsProperties);
        fcRecuperacion = new Date();
        fcRevisionInvalidaciones = new Date();

    }

    private void cargaPropiedades(final Properties props) {
        for (final Object key : props.keySet()) {
            final TypePropiedadConfiguracion pc = TypePropiedadConfiguracion
                    .fromString(key.toString());
            if (pc != null) {
                propiedades.put(pc, props.getProperty(key.toString()));
            }
        }
    }

    private Properties recuperarConfiguracionProperties() {
        final String pathProperties = System
                .getProperty("es.caib.sistramit.properties.path");
        try (FileInputStream fis = new FileInputStream(pathProperties);) {
            final Properties props = new Properties();
            props.load(fis);
            return props;
        } catch (final IOException e) {
            throw new CargaConfiguracionException(
                    "Error al cargar la configuracion del properties '"
                            + pathProperties + "' : " + e.getMessage(),
                    e);
        }

    }

    /**
     * Verifica si se requiere refrescar la configuración (se refresca cada
     * dia), salvo que se indique expresamente mediante invalidacion en STG.
     *
     * @return boolean
     */
    private boolean necesitaRefrescarConfiguracion() {
        boolean res = false;
        if (fcRecuperacion == null) {
            res = true;
        } else {
            // Refresco diario: Vemos si ha variado el día
            if (getDay(fcRecuperacion) != getDay(new Date())) {
                res = true;
            }
            // Refresco por invalidacion
            if (fcRevisionInvalidaciones != null) {
                fcRevisionInvalidaciones = new Date();
                final String intervalo = StringUtils.defaultIfEmpty(
                        this.propiedades.get(
                                TypePropiedadConfiguracion.REVISION_INVALIDACIONES),
                        ConstantesConfiguracion.INTERVALO_VALIDACION_DEFECTO
                                + "");

                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(fcRevisionInvalidaciones);
                calendar.add(Calendar.MINUTE, Integer.parseInt(intervalo));
                final Date proxRev = calendar.getTime();
                final Date ahora = new Date();
                if (ahora.after(proxRev)) {
                    final List<Invalidacion> invalidaciones = invalidacionDao
                            .obtenerInvalidaciones(
                                    TypeInvalidacion.CONFIGURACION);
                    for (final Invalidacion inv : invalidaciones) {
                        if (inv.getFecha().after(fcRecuperacion)) {
                            res = true;
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * Obtiene dia.
     *
     * @param fecha
     *            Fecha
     * @return dia
     */
    private int getDay(Date fecha) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

}
