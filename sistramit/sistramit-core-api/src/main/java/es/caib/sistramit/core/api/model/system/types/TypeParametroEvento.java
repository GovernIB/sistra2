package es.caib.sistramit.core.api.model.system.types;

/**
 * Parámetros estándar para un evento de auditoria.
 *
 * @author Indra
 *
 */
public enum TypeParametroEvento {

	/**
	 * Sistema operativo.
	 */
	SISTEMA_OPERATIVO("SO"),
	/**
	 * Versión sistema operativo.
	 */
	VERSION_SISTEMA_OPERATIVO("VSO"),
	/**
	 * Navegador.
	 */
	NAVEGADOR("NA"),
	/**
	 * Versión navegador.
	 */
	VERSION_NAVEGADOR("VNA"),
	/**
	 * Idioma.
	 */
	IDIOMA("ID"),

	/**
	 * Para inicio tramites incorrectos: url inicio.
	 */
	URL_INICIO("URL"),
	/**
	 * Para inicio tramites incorrectos: excepcion asociada.
	 */
	EXCEPCION_ORIGEN("EXCEPCIONORIGEN"),
	/**
	 * Id sesión.
	 */
	ID_SESION("IDS"),
	/**
	 * Autenticado (S/N).
	 */
	AUTENTICADO("AUT"),
	/**
	 * METODO_AUTENTICACION.
	 */
	METODO_AUTENTICACION("AUTMET"),
	/**
	 * NIF.
	 */
	NIF("NIF"),
	/**
	 * Nombre.
	 */
	NOMBRE("NOMBRE"),
	/**
	 * Email.
	 */
	EMAIL("EMAIL"),
	/**
	 * Id tramite.
	 */
	TRAMITE_ID("IDTRAM"),
	/**
	 * Version tramite.
	 */
	TRAMITE_VERSION("VERTRAM"),
	/**
	 * ID SIA procedimiento.
	 */
	PROCEDIMIENTO_IDSIA("IDSIA"),
	/**
	 * Número registro (evento envio/registro).
	 */
	NUMERO_REGISTRO("NUMREG");

	/**
	 * Valor como string.
	 */
	private final String stringValueParametroEvento;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeParametroEvento(final String value) {
		stringValueParametroEvento = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueParametroEvento;
	}

	/**
	 * Método para From string de la clase TypeAutenticacion.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type autenticacion
	 */
	public static TypeParametroEvento fromString(final String text) {
		TypeParametroEvento respuesta = null;
		if (text != null) {
			for (final TypeParametroEvento b : TypeParametroEvento.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
