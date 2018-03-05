package es.caib.sistrages.frontend.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Indra
 *
 */
public class UtilJSON {

	/** Constructor privado para evitar problema. */
	private UtilJSON() {
		// not called
	}

	/**
	 * Clase para convertir un objeto a JSON.
	 *
	 * @param objeto
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String toJSON(final Object objeto) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(objeto);

	}

	/**
	 * Clase para convertir de json a objeto.
	 *
	 * @param json
	 * @param clase
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Object fromJSON(final String json, final Class<?> clase) throws IOException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(json, clase);
	}

	/**
	 * Clase para convertir de json a objeto.
	 *
	 * @param json
	 * @param clase
	 * @param claseLista
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<?> fromListJSON(final String json, final Class<?> claseLista) throws IOException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, claseLista));
	}

}
