package es.caib.sistra2.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serializador.
 *
 * @author Indra
 *
 */
public class Serializador {

	/**
	 * Serializa a bytes.
	 *
	 * @param model
	 *            objeto
	 * @return objeto serializado
	 * @throws IOException
	 */
	public static byte[] serialize(final Object model) throws IOException {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(model);
		oos.flush();
		final byte[] content = bos.toByteArray();
		bos.close();
		return content;

	}

	/**
	 * Deserializa objeto.
	 *
	 * @param content
	 *            objeto serializado
	 * @return objeto
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserialize(final byte[] content) throws IOException, ClassNotFoundException {
		final ByteArrayInputStream bin = new ByteArrayInputStream(content);
		final ObjectInputStream ois = new ObjectInputStream(bin);
		final Object clonedObject = ois.readObject();
		return clonedObject;
	}

	/**
	 * Serializa a JSON.
	 *
	 * @param model
	 *            objeto
	 * @return objeto serializado en JSON
	 * @throws JSONUtilException
	 */
	public static String serializeJSON(final Object model) throws JSONUtilException {
		return JSONUtil.toJSON(model);
	}

	/**
	 * Deserializa desd JSON.
	 *
	 * @param json
	 *            JSON
	 * @param clase
	 *            Clase
	 * @return objeto
	 * @throws JSONUtilException
	 */
	public static Object deserializeJSON(final String json, final Class<?> clase) throws JSONUtilException {
		return JSONUtil.fromJSON(json, clase);
	}

}
