package es.caib.sistrages.core.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import es.caib.sistrages.core.api.exception.CloneModelException;
import es.caib.sistrages.core.api.model.ModelApi;

/**
 * Utilidades.
 *
 * @author Indra
 *
 */
public final class UtilCoreApi {

	/**
	 * Constructor.
	 */
	private UtilCoreApi() {
		super();
	}

	/**
	 * Serializa un objeto ModelApi y lo devuelve en byte[]
	 *
	 * @param model
	 * @return
	 */
	public static byte[] serialize(final ModelApi model) {
		try {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(model);
			oos.flush();
			final byte[] content = bos.toByteArray();
			bos.close();
			return content;
		} catch (final IOException ioe) {
			throw new CloneModelException(ioe);
		}
	}

	/**
	 * Deserializa byte[] y lo devuelve en objeto ModelApi
	 *
	 * @param content
	 * @return
	 */
	public static ModelApi deserialize(final byte[] content) {
		try {
			final ByteArrayInputStream bin = new ByteArrayInputStream(content);
			final ObjectInputStream ois = new ObjectInputStream(bin);
			final Object clonedObject = ois.readObject();
			return (ModelApi) clonedObject;
		} catch (final IOException ioe) {
			throw new CloneModelException(ioe);
		} catch (final ClassNotFoundException cne) {
			throw new CloneModelException(cne);
		}
	}

	/**
	 * Clona objeto del API.
	 *
	 * @param model
	 *            objeto
	 * @return objeto clonado
	 */
	public static ModelApi cloneModelApi(final ModelApi model) {
		// Clona objeto mediante serializacion
		try {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(model);
			oos.flush();
			final ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			final ObjectInputStream ois = new ObjectInputStream(bin);
			final Object clonedObject = ois.readObject();
			return (ModelApi) clonedObject;
		} catch (final IOException ioe) {
			throw new CloneModelException(ioe);
		} catch (final ClassNotFoundException cne) {
			throw new CloneModelException(cne);
		}
	}

	/**
	 * Compara si los objetos son iguales.
	 *
	 * @param model1
	 *            objeto 1
	 * @param model2
	 *            objeto 2
	 * @return Indica si son iguales
	 */
	public static boolean equalsModelApi(final ModelApi model1, final ModelApi model2) {
		return deepCompare(model1, model2);
		// return EqualsBuilder.reflectionEquals(model1, model2);
		// return Objects.deepEquals(model1, model2);
	}

	private static boolean deepCompare(final Object o1, final Object o2) {
		try {
			final ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
			final ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
			oos1.writeObject(o1);
			oos1.close();

			final ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			final ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
			oos2.writeObject(o2);
			oos2.close();

			return Arrays.equals(baos1.toByteArray(), baos2.toByteArray());
		} catch (final IOException e) {
			throw new CloneModelException(e);
		}
	}

}
