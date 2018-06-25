package es.caib.sistrages.rest.api.interna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modela datos de un dominio.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RValoresDominio implements Serializable {

    /** Constante numérica. */
    private static final int N1 = 1;

    /**
     * Filas de valores.
     */
    private final List<Map<String, String>> datos = new ArrayList<>();

    /**
     * Indica si ha habido error.
     */
    private boolean error;

    /**
     * Codigo error.
     */
    private String codigoError;

    /**
     * Texto error.
     */
    private String descripcionError;

    /**
     * Añade una fila de valores.
     *
     * @return Numero de fila añadida
     */
    public int addFila() {
        final Map<String, String> fila = new HashMap<String, String>();
        datos.add(fila);
        return datos.size();
    }

    /**
     * Establece el valor de una columna en una fila.
     *
     * @param numfila
     *            Numero de fila (empiezan desde 1)
     * @param cod
     *            Nombre columna
     * @param val
     *            Valor
     */
    public void setValor(final int numfila, final String cod,
            final String val) {
        final Map<String, String> fila = datos.get(numfila - N1);
        fila.put(cod, val);
    }

    /**
     * Obtiene el valor de una columna en una fila. Si el valor del campo es
     * nulo se devuelve cadena vacía.
     *
     * @param numfila
     *            Numero de fila (empiezan desde 1)
     * @param cod
     *            Nombre columna
     * @return Valor columna para la fila seleccionada
     */
    public String getValor(final int numfila, final String cod) {
        String valor;
        final Map<String, String> fila = datos.get(numfila - N1);
        valor = fila.get(cod);
        if (valor == null) {
            valor = "";
        }
        return valor;
    }

    /**
     * Obtiene numero de filas.
     *
     * @return Numero de filas
     */
    public int getNumeroFilas() {
        return datos.size();
    }

    /**
     * Método de acceso a error.
     *
     * @return error
     */
    public boolean isError() {
        return error;
    }

    /**
     * Método para establecer error.
     *
     * @param pError
     *            error a establecer
     */
    public void setError(final boolean pError) {
        error = pError;
    }

    /**
     * Método de acceso a descripcionError.
     *
     * @return descripcionError
     */
    public String getDescripcionError() {
        return descripcionError;
    }

    /**
     * Método para establecer descripcionError.
     *
     * @param pDescripcionError
     *            descripcionError a establecer
     */
    public void setDescripcionError(final String pDescripcionError) {
        descripcionError = pDescripcionError;
    }

    /**
     * Obtiene los nombres de las columnas.
     *
     * @return el atributo nombre columnas
     */
    public List<String> getNombreColumnas() {
        final List<String> nombresColumnas = new ArrayList<>();
        if (!datos.isEmpty()) {
            final Map<String, String> fila = datos.get(0);
            for (final Map.Entry<String, String> entry : fila.entrySet()) {
                nombresColumnas.add(entry.getKey());
            }
        }
        return nombresColumnas;
    }

    /**
     * Método de acceso a datos.
     *
     * @return el datos
     */
    public List<Map<String, String>> getDatos() {
        return datos;
    }

    /**
     * Método de acceso a codigoError.
     *
     * @return codigoError
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Método para establecer codigoError.
     *
     * @param pCodigoError
     *            codigoError a establecer
     */
    public void setCodigoError(final String pCodigoError) {
        codigoError = pCodigoError;
    }
}
