package es.caib.sistra2.commons.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utilidades conversion JSON.
 *
 * @author Indra
 *
 */
public class JSONUtil {

    /** Constructor privado. */
    private JSONUtil() {
        // not called
    }

    /**
     * Clase para convertir un objeto a JSON.
     *
     * @param objeto
     * @return
     * @throws JSONUtilException
     * @throws JsonProcessingException
     */
    public static String toJSON(final Object objeto) throws JSONUtilException {
        try {
            String res = null;
            if (objeto != null) {
                final ObjectMapper mapper = new ObjectMapper();
                res = mapper.writeValueAsString(objeto);
            }
            return res;
        } catch (final JsonProcessingException ex) {
            throw new JSONUtilException("Excepcion convirtiendo a JSON", ex);
        }
    }

    /**
     * Clase para convertir de json a objeto.
     *
     * @param json
     * @param clase
     * @return
     * @throws JSONUtilException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object fromJSON(final String json, final Class<?> clase)
            throws JSONUtilException {
        try {
            Object res = null;
            if (json != null) {
                final ObjectMapper mapper = new ObjectMapper();
                mapper.configure(
                        com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
                res = mapper.readValue(json, clase);
            }
            return res;
        } catch (final IOException ex) {
            throw new JSONUtilException("Excepcion convirtiendo desde JSON",
                    ex);
        }
    }

    /**
     * Clase para convertir de json a objeto.
     *
     * @param json
     * @param clase
     * @param claseLista
     * @return
     * @throws JSONUtilException
     */
    public static List<?> fromListJSON(final String json,
            final Class<?> claseLista) throws JSONUtilException {
        try {
            List<?> res = null;
            if (json != null) {
                final ObjectMapper mapper = new ObjectMapper();
                mapper.configure(
                        com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
                res = mapper.readValue(json, mapper.getTypeFactory()
                        .constructCollectionType(List.class, claseLista));
            }
            return res;
        } catch (final IOException e) {
            throw new JSONUtilException(
                    "Error convirtiendo lista JSON: " + e.getMessage(), e);
        }
    }

}
