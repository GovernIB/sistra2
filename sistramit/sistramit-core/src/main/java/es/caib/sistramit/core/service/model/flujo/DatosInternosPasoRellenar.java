package es.caib.sistramit.core.service.model.flujo;

import java.util.HashMap;
import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;

/**
 *
 * Datos internos paso Rellenar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoRellenar
        extends DatosInternosPasoReferencia {

    /**
     * Datos de los formularios. Cacheamos datos de formularios para optimizar
     * scripts.
     */
    private final Map<String, ValoresFormulario> datosFormularios = new HashMap<>();

    /**
     * Constructor.
     *
     * @param idSesionTramitacion
     *            Parámetro id sesion tramitacion
     * @param idPaso
     *            Parámetro id paso
     */
    public DatosInternosPasoRellenar(final String idSesionTramitacion,
            final String idPaso) {
        this.setTipo(TypePaso.RELLENAR);
        this.setIdSesionTramitacion(idSesionTramitacion);
        this.setIdPaso(idPaso);
    }

    /**
     * Añade los datos de un formulario.
     *
     * @param idForm
     *            Identificador formulario
     * @param xmlFormulario
     *            Xml de datos del formulario
     */
    public void addDatosFormulario(final String idForm,
            final byte[] xmlFormulario) {
        // Cachea datos formulario
        final ValoresFormulario df = new ValoresFormulario(xmlFormulario);
        datosFormularios.put(idForm, df);
    }

    /**
     * Obtiene el atributo datos formulario de DatosInternosPasoRellenar.
     *
     * @param idForm
     *            Parámetro id form
     * @return el atributo datos formulario
     */
    public byte[] getDatosFormulario(final String idForm) {
        // Recupera el byte de datosFormulario
        byte[] res = null;

        final ValoresFormulario valores = datosFormularios.get(idForm);
        if (valores != null) {
            res = valores.getXml();
        }
        return res;
    }

    /**
     * Método para Elimina datos formulario de la clase
     * DatosInternosPasoRellenar.
     *
     * @param idForm
     *            Parámetro id form
     */
    public void removeDatosFormulario(final String idForm) {
        datosFormularios.remove(idForm);
    }

    /**
     * Obtiene el valor de un campo.
     *
     * @param idForm
     *            Identificador formulario
     * @param campo
     *            Identificador campo
     * @return Valor del campo
     */
    public ValorCampo getValorFormulario(final String idForm,
            final String campo) {
        final ValoresFormulario df = datosFormularios.get(idForm);
        return df.getValorCampo(campo);
    }

    /**
     * Obtiene los valores de un formulario.
     *
     * @param idForm
     *            Identificador formulario
     * @return Valores del formulario
     */
    public ValoresFormulario getValoresFormulario(final String idForm) {
        final ValoresFormulario df = datosFormularios.get(idForm);
        return df;
    }

}
