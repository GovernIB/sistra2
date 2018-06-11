package es.caib.sistramit.core.api.model.comun;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

// TODO PASAR A LIB UTILS COMUN (SE USA TB EN SISTRAGES)

/**
 * Permite establecer una lista de propiedades mediante una lista de
 * propiedad/valor.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ListaPropiedades implements Serializable {

    /**
     * Map con los detalles del error.
     */
    private final Map<String, String> propiedadesError = new LinkedHashMap<>();

    /**
     * Añade un detalle de error.
     *
     * @param propiedad
     *            Propiedad
     * @param valor
     *            Parámetro valor
     */
    public void addPropiedad(final String propiedad, final String valor) {
        propiedadesError.put(propiedad, valor);
    }

    /**
     * Obtiene detalles del error como un Map<String,String>.
     *
     * @return Detalles error como un Map<String,String>
     */
    public Map<String, String> getPropiedades() {
        return this.propiedadesError;
    }

    /**
     * Método para Crea new lista propiedades de la clase ListaPropiedades.
     *
     * @return el lista propiedades
     */
    public static ListaPropiedades createNewListaPropiedades() {
        return new ListaPropiedades();
    }

    /**
     * Método para añadir nuevas propiedades a una ListaPropiedades pasándole
     * otra ListaPropiedades.
     *
     * @param lp
     *            Parámetro lp. ListaPropiedades a añadir
     */
    public void addPropiedades(final Map<String, String> lp) {
        if (lp != null) {
            for (final Map.Entry<String, String> propiedad : lp.entrySet()) {
                this.addPropiedad(propiedad.getKey(), propiedad.getValue());
            }
        }
    }

    /**
     * Método para añadir nuevas propiedades a una ListaPropiedades pasándole
     * otra ListaPropiedades.
     *
     * @param lp
     *            Parámetro lp. ListaPropiedades a añadir
     */
    public void addPropiedades(final ListaPropiedades lp) {
        final Map<String, String> nueva = lp.getPropiedades();
        this.propiedadesError.putAll(nueva);
    }

    /**
     * Obtiene valor propiedad.
     *
     * @param propiedad
     *            Nombre propiedad
     * @return valor propiedad
     */
    public String getPropiedad(final String propiedad) {
        return this.getPropiedades().get(propiedad);
    }
}
