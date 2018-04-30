package es.caib.sistrages.core.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import es.caib.sistrages.core.api.exception.CloneModelException;
import es.caib.sistrages.core.api.model.ModelApi;

// TODO Sacar a lib comun

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
