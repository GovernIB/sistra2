package es.caib.sistramit.core.api.model.system.types;

/**
 * Parámetros estándar para un evento de auditoria.
 *
 * @author Indra
 *
 */
public enum TypeParametroEvento {

	/**
	 * Autenticación.
	 */
	AUTENTICACION("AUT"),
	/**
	 * Autenticación QAA.
	 */
	AUTENTICACION_QAA("AUTQAA"),
	/**
	 * Autenticación Representante.
	 */
	AUTENTICACION_RPTE("AUTRPTE"),
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
	 * Para inicio tramites incorrectos: url inicio.
	 */
	URL_INICIO("URL"),
	/**
	 * Número registro (evento envio/registro).
	 */
	NUMERO_REGISTRO("NUMREG"),
	/**
	 * Pago: Id sesión.
	 */
	PAGO_ID_SESION("PAGIDE"),
	/**
	 * Pago: Pasarela pago.
	 */
	PAGO_PASARELA("PAGPAS"),
	/**
	 * Pago: Importe (cents).
	 */
	PAGO_IMPORTE("PAGIMP"),
	/**
	 * Pago: Error pasarela.
	 */
	PAGO_ERROR("PAGERR"),
	/**
	 * Pago: Método pago seleccionado (depende pasarela).
	 */
	PAGO_METODO("PAGMET"),
	/**
	 * Pago: Localizador pago en pasarela.
	 */
	PAGO_LOCALIZADOR("PAGLOC"),
	/**
	 * Valoración trámite: puntuación.
	 */
	VALORACION_PUNTUACION("VALPUN"),
	/**
	 * Valoración trámite: lista problemas separados por ";".
	 */
	VALORACION_PROBLEMAS("VALPROB"),
	/**
	 * Valoración trámite: observaciones.
	 */
	VALORACION_OBSERVACIONES("VALOBS"),
	/**
	 * Registro: asiento registro.
	 */
	REGISTRO_ASIENTOREGISTRO("REGASIENTO"),
	/**
	 * Registro: indica si es modo entrega.
	 */
	REGISTRO_MODOENTREGA("REGMODOENTREGA"),
	/**
	 * Documento: identificador.
	 */
	DOCUMENTO_ID("DOCIDE"),
	/**
	 * Documento: instancia.
	 */
	DOCUMENTO_INSTANCIA("DOCINS"),
	/**
	 * NIF.
	 */
	NIF("NIF"),
	/**
	 * PROBLEMA FIRMA.
	 */
	FIRMA_ERROR("FIRERROR"),
	/**
	 * FIRMA SESION.
	 */
	FIRMA_SESION("FIRSESION"),
	/**
	 * FIRMA METODO.
	 */
	FIRMA_METODO("FIRMETODO");


	/**
	 * Valor como string.
	 */
	private final String stringValueParametroEvento;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
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
	 *                 Parámetro text
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
