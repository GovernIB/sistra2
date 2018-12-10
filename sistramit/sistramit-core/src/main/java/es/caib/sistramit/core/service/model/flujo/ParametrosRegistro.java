package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Datos calculados del paso de registro.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ParametrosRegistro implements Serializable {

    /** Datos de registro destino. */
    private DatosRegistrales datosRegistrales;

    /** Datos de presentacion. */
    private DatosPresentacion datosPresentacion;

    /** Datos representacion. **/
    private DatosRepresentacion datosRepresentacion;

    /**
     * Método de acceso a datosRegistrales.
     *
     * @return datosRegistrales
     */
    public DatosRegistrales getDatosRegistrales() {
        return datosRegistrales;
    }

    /**
     * Método para establecer datosRegistrales.
     *
     * @param pDatosRegistrales
     *            datosRegistrales a establecer
     */
    public void setDatosRegistrales(final DatosRegistrales pDatosRegistrales) {
        datosRegistrales = pDatosRegistrales;
    }

    /**
     * Método de acceso a datosPresentacion.
     *
     * @return datosPresentacion
     */
    public DatosPresentacion getDatosPresentacion() {
        return datosPresentacion;
    }

    /**
     * Método para establecer datosPresentacion.
     *
     * @param pDatosPresentacion
     *            datosPresentacion a establecer
     */
    public void setDatosPresentacion(
            final DatosPresentacion pDatosPresentacion) {
        datosPresentacion = pDatosPresentacion;
    }

    /**
     * Método de acceso a datosRepresentacion.
     *
     * @return datosRepresentacion
     */
    public DatosRepresentacion getDatosRepresentacion() {
        return datosRepresentacion;
    }

    /**
     * Método para establecer datosRepresentacion.
     *
     * @param pDatosRepresentacion
     *            datosRepresentacion a establecer
     */
    public void setDatosRepresentacion(
            final DatosRepresentacion pDatosRepresentacion) {
        datosRepresentacion = pDatosRepresentacion;
    }

}
